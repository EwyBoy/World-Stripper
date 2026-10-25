package com.ewyboy.worldstripper.workers;

import com.ewyboy.worldstripper.club.BlockUpdater;
import com.ewyboy.worldstripper.club.StripperCache;
import com.ewyboy.worldstripper.json.StrippablesHandler;
import com.ewyboy.worldstripper.json.WSConfigLoader;
import com.ewyboy.worldstripper.util.ModLogger;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Strips one chunk section per {@link #doWork()} call, walking the box chunk by chunk and top to bottom.
 * Sections whose palette holds no strippable state are skipped without touching a single block.
 */
public class StripWorker implements WorldWorker.IWorker {

    private final ServerLevel dim;
    private final Set<Block> strippables;
    private final int blockUpdateFlag;
    private final BlockState replacementBlock;
    private final int minX, maxX, minY, maxY, minZ, maxZ;
    private final int minCX, maxCX, maxCZ, minSection;
    private int cx, cz, section;
    private final long startNanos = System.nanoTime();
    private int stripped = 0;

    public StripWorker(BlockPos start, int radiusX, int radiusZ, ServerLevel dim, int blockUpdateFlag, BlockState replacementBlock, List<String> stripList) {
        this.dim = dim;
        this.blockUpdateFlag = blockUpdateFlag;
        this.replacementBlock = replacementBlock;

        // Resolve the id list once: the old per-block getKey().toString() + List.contains dominated the cost.
        Set<String> ids = new HashSet<>(stripList);
        this.strippables = new ReferenceOpenHashSet<>();
        for (Block block : BuiltInRegistries.BLOCK) {
            if (ids.contains(BuiltInRegistries.BLOCK.getKey(block).toString())) strippables.add(block);
        }

        var config = WSConfigLoader.getInstance().getConfig();
        this.minX = start.getX() - radiusX;
        this.maxX = start.getX() + radiusX;
        this.minZ = start.getZ() - radiusZ;
        this.maxZ = start.getZ() + radiusZ;
        this.minY = Math.max(config.stripEndY(), dim.getMinBuildHeight());
        this.maxY = Math.min(config.stripStartY(), dim.getMaxBuildHeight() - 1);
        this.minCX = minX >> 4;
        this.maxCX = maxX >> 4;
        this.maxCZ = maxZ >> 4;
        this.minSection = dim.getSectionIndex(minY);
        this.cx = minCX;
        this.cz = minZ >> 4;
        this.section = dim.getSectionIndex(maxY);
    }

    /** Queues a strip of the configured box around {@code center}; radii are in blocks. */
    public static StripWorker start(ServerLevel level, BlockPos center, int radiusX, int radiusZ) {
        var config = WSConfigLoader.getInstance().getConfig();
        BlockState replacement = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(config.replacementBlock())).defaultBlockState();
        StripWorker worker = new StripWorker(center, radiusX, radiusZ, level, BlockUpdater.getBlockUpdateFlag(), replacement, StrippablesHandler.strippables.strippables());
        WorldWorker.addWorker(worker);
        return worker;
    }

    public boolean hasWork() {
        return cz <= maxCZ && minY <= maxY;
    }

    public boolean doWork() {
        if (!hasWork()) return false;
        LevelChunk chunk = dim.getChunk(cx, cz);
        LevelChunkSection levelSection = chunk.getSection(section);
        if (!levelSection.hasOnlyAir() && levelSection.maybeHas(state -> strippables.contains(state.getBlock()))) {
            stripSection(levelSection);
        }
        advance();
        if (!hasWork()) {
            ModLogger.info("Stripped %d blocks in %d ms", stripped, (System.nanoTime() - startNanos) / 1_000_000);
        }
        return hasWork();
    }

    private void stripSection(LevelChunkSection levelSection) {
        int baseY = dim.getSectionYFromSectionIndex(section) << 4;
        int x0 = Math.max(minX, cx << 4), x1 = Math.min(maxX, (cx << 4) + 15);
        int z0 = Math.max(minZ, cz << 4), z1 = Math.min(maxZ, (cz << 4) + 15);
        int y0 = Math.max(minY, baseY), y1 = Math.min(maxY, baseY + 15);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int y = y1; y >= y0; y--) {
            for (int z = z0; z <= z1; z++) {
                for (int x = x0; x <= x1; x++) {
                    BlockState state = levelSection.getBlockState(x & 15, y & 15, z & 15);
                    if (strippables.contains(state.getBlock())) {
                        pos.set(x, y, z);
                        StripperCache.hashedBlockCache.put(pos.immutable(), state);
                        dim.setBlock(pos, replacementBlock, blockUpdateFlag);
                        stripped++;
                    }
                }
            }
        }
    }

    /** Next section downwards, then the next chunk column (x fastest, then z). */
    private void advance() {
        if (--section >= minSection) return;
        section = dim.getSectionIndex(maxY);
        if (++cx > maxCX) {
            cx = minCX;
            cz++;
        }
    }
}
