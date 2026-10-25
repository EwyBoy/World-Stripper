package com.ewyboy.worldstripper.workers;

import com.ewyboy.worldstripper.json.WSConfigLoader;
import com.ewyboy.worldstripper.util.ModLogger;
import net.minecraft.core.BlockPos;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.objects.Reference2BooleanOpenHashMap;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ThreadedLevelLightEngine;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.lighting.LayerLightEventListener;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.lighting.LightEngine;

import java.util.function.Predicate;

/**
 * Walks a box one chunk section per {@link #doWork()} call: chunk columns x-fastest then z,
 * and within a column top-down (strip) or bottom-up (dress, so falling blocks land on restored ground).
 */
public abstract class SectionWorker implements WorldWorker.IWorker {

    protected final ServerLevel level;
    protected final int minX, maxX, minY, maxY, minZ, maxZ;
    private final int minCX, maxCX, maxCZ, firstSection, lastSection, step;
    private final String verb;
    private final long startNanos = System.nanoTime();
    protected int cx, cz, section, changed;
    private final LongArrayList lightChecks = new LongArrayList();

    protected SectionWorker(ServerLevel level, BlockPos center, int radiusX, int radiusZ, boolean downwards, String verb) {
        var config = WSConfigLoader.getInstance().getConfig();
        this.level = level;
        this.verb = verb;
        this.minX = center.getX() - radiusX;
        this.maxX = center.getX() + radiusX;
        this.minZ = center.getZ() - radiusZ;
        this.maxZ = center.getZ() + radiusZ;
        this.minY = Math.max(config.stripEndY(), level.getMinBuildHeight());
        this.maxY = Math.min(config.stripStartY(), level.getMaxBuildHeight() - 1);
        this.minCX = minX >> 4;
        this.maxCX = maxX >> 4;
        this.maxCZ = maxZ >> 4;
        this.firstSection = level.getSectionIndex(downwards ? maxY : minY);
        this.lastSection = level.getSectionIndex(downwards ? minY : maxY);
        this.step = downwards ? -1 : 1;
        this.cx = minCX;
        this.cz = minZ >> 4;
        this.section = firstSection;
    }

    /** Handles the section at (cx, section, cz). */
    protected abstract void processSection();

    public boolean hasWork() {
        return cz <= maxCZ && minY <= maxY;
    }

    public boolean doWork() {
        if (!hasWork()) return false;
        processSection();
        flushLightChecks();
        if (section != lastSection) {
            section += step;
        } else {
            section = firstSection;
            if (++cx > maxCX) {
                cx = minCX;
                cz++;
            }
        }
        if (!hasWork()) {
            ModLogger.info("%s %d blocks in %d ms", verb, changed, (System.nanoTime() - startNanos) / 1_000_000);
        }
        return hasWork();
    }

    private static final Heightmap.Types[] HEIGHTMAPS = {
            Heightmap.Types.MOTION_BLOCKING, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Heightmap.Types.OCEAN_FLOOR, Heightmap.Types.WORLD_SURFACE};
    private static final Predicate<BlockState> POI = state -> PoiTypes.forState(state).isPresent();
    private final Heightmap[] heightmaps = new Heightmap[HEIGHTMAPS.length];
    private LevelChunk heightmapChunk;
    private final Reference2BooleanOpenHashMap<BlockState> hasPoi = new Reference2BooleanOpenHashMap<>();

    /**
     * {@code level.setBlock(pos, state, flags)} for a block in the current section, minus the per-call overhead:
     * writes the section directly and then does exactly what LevelChunk/Level would do server-side (heightmaps,
     * light, onRemove/onPlace, POI, client sync), except re-checking mob paths. Neighbour/shape updates and new
     * block entities still go through vanilla. {@code onPlace = false} skips the placed block's onPlace
     * (which is how gravel schedules its fall and fluids their flow).
     */
    protected void setBlock(LevelChunk chunk, LevelChunkSection levelSection, int x, int y, int z, BlockState state, int flags, boolean onPlace) {
        if ((flags & (Block.UPDATE_NEIGHBORS | Block.UPDATE_KNOWN_SHAPE)) != Block.UPDATE_KNOWN_SHAPE || state.hasBlockEntity()) {
            level.setBlock(new BlockPos(x, y, z), state, flags);
            return;
        }
        boolean wasEmpty = levelSection.hasOnlyAir();
        BlockState old = levelSection.setBlockState(x & 15, y & 15, z & 15, state, false);
        if (old == state) return;
        BlockPos pos = new BlockPos(x, y, z);
        if (chunk != heightmapChunk) { // getOrCreateHeightmapUnprimed is an EnumMap.computeIfAbsent: too slow per block
            heightmapChunk = chunk;
            for (int i = 0; i < HEIGHTMAPS.length; i++) heightmaps[i] = chunk.getOrCreateHeightmapUnprimed(HEIGHTMAPS[i]);
        }
        for (Heightmap heightmap : heightmaps) heightmap.update(x & 15, y, z & 15, state);
        LevelLightEngine light = level.getChunkSource().getLightEngine();
        if (wasEmpty != levelSection.hasOnlyAir()) light.updateSectionStatus(pos, levelSection.hasOnlyAir());
        if (LightEngine.hasDifferentLightProperties(chunk, pos, old, state)) {
            chunk.getSkyLightSources().update(chunk, x & 15, y, z & 15);
            lightChecks.add(pos.asLong());
        }
        old.onRemove(level, pos, state, false);
        if (onPlace && levelSection.getBlockState(x & 15, y & 15, z & 15).is(state.getBlock())) state.onPlace(level, pos, old, false);
        chunk.setUnsaved(true);
        if ((flags & Block.UPDATE_CLIENTS) != 0) {
            level.getChunkSource().blockChanged(pos); // ChunkHolder batches these into one packet per section per tick
            level.getPathTypeCache().invalidate(pos);
        }
        if (hasPoi.computeIfAbsent(old, POI) || hasPoi.computeIfAbsent(state, POI)) level.onBlockStateChange(pos, old, state);
    }

    /**
     * Hands the section's light checks to the light thread as one task. ThreadedLevelLightEngine.checkBlock
     * queues a prioritised task per block, which made lighting take 4x longer than the strip itself.
     * The batch runs on the light thread after everything already queued for this chunk (section status
     * changes included) and is processed on its next update, same as vanilla checks.
     */
    private void flushLightChecks() {
        if (lightChecks.isEmpty()) return;
        long[] nodes = lightChecks.toLongArray();
        lightChecks.clear();
        ThreadedLevelLightEngine light = level.getChunkSource().getLightEngine();
        LayerLightEventListener blockLight = light.getLayerListener(LightLayer.BLOCK);
        LayerLightEventListener skyLight = light.getLayerListener(LightLayer.SKY);
        light.waitForPendingTasks(cx, cz).thenRun(() -> {
            for (long node : nodes) {
                BlockPos pos = BlockPos.of(node);
                blockLight.checkBlock(pos);
                skyLight.checkBlock(pos);
            }
        });
    }

    protected long sectionKey() {
        return SectionPos.asLong(cx, level.getSectionYFromSectionIndex(section), cz);
    }

    // Clip of the box to the current section, in world coordinates.
    protected int x0() { return Math.max(minX, cx << 4); }
    protected int x1() { return Math.min(maxX, (cx << 4) + 15); }
    protected int z0() { return Math.max(minZ, cz << 4); }
    protected int z1() { return Math.min(maxZ, (cz << 4) + 15); }
    protected int y0() { return Math.max(minY, level.getSectionYFromSectionIndex(section) << 4); }
    protected int y1() { return Math.min(maxY, (level.getSectionYFromSectionIndex(section) << 4) + 15); }
}
