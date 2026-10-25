package com.ewyboy.worldstripper.dev;

import com.ewyboy.worldstripper.json.WSConfigLoader;
import com.ewyboy.worldstripper.util.ModLogger;
import com.ewyboy.worldstripper.workers.DressWorker;
import com.ewyboy.worldstripper.workers.StripWorker;
import com.ewyboy.worldstripper.workers.WorldWorker;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.concurrent.CompletableFuture;

/**
 * Dev-only benchmark, inert unless started with -Dworldstripper.bench=24,64,128 (radii in blocks).
 * Strips and dresses a box around world spawn for each radius, verifies the result and halts the server.
 */
public final class Bench {

    private static final String PROP = System.getProperty("worldstripper.bench");

    private static Deque<Integer> radii;
    private static int phase, r, run;
    private static ServerLevel level;
    private static BlockPos center;
    private static WorldWorker.IWorker worker;
    private static CompletableFuture<?> light;
    private static long t0, tStrip, tStripLight, tDress, peak, heap0, retained;
    private static long[] before, afterStrip;
    private static int[] beforeIds;

    public static void tick(MinecraftServer server) {
        if (PROP == null) return;
        long now = System.nanoTime();
        switch (phase) {
            case 0 -> {
                radii = new ArrayDeque<>(Arrays.stream(PROP.split(",")).map(String::trim).map(Integer::parseInt).toList());
                level = server.overworld();
                server.getGameRules().getRule(GameRules.RULE_RANDOMTICKING).set(0, server); // grass spread would make the world drift
                phase = 1;
            }
            case 1 -> { // prepare: force-load the box, record the pristine state
                if (radii.isEmpty()) {
                    ModLogger.info("BENCH done");
                    server.halt(false);
                    phase = 99;
                    return;
                }
                r = radii.poll();
                center = level.getSharedSpawnPos().offset(1100 * run++, 0, 0); // fresh terrain per radius, so runs never see each other's leftovers
                for (int cx = (center.getX() - r >> 4) - 1; cx <= (center.getX() + r >> 4) + 1; cx++)
                    for (int cz = (center.getZ() - r >> 4) - 1; cz <= (center.getZ() + r >> 4) + 1; cz++) {
                        level.setChunkForced(cx, cz, true);
                        level.getChunk(cx, cz);
                    }
                t0 = now;
                phase = 6;
            }
            case 6 -> { // let freshly loaded chunks finish their post-load ticks (leaf distances etc.)
                if (now - t0 < 5_000_000_000L) return;
                before = scan();
                beforeIds = ids;
                heap0 = usedAfterGc();
                peak = 0;
                t0 = System.nanoTime();
                worker = StripWorker.start(level, center, r, r);
                phase = 2;
            }
            case 2 -> {
                peak = Math.max(peak, used());
                if (!worker.hasWork()) {
                    tStrip = now - t0;
                    light = lightSettled();
                    phase = 3;
                }
            }
            case 3 -> {
                if (!light.isDone()) return;
                tStripLight = now - t0;
                retained = usedAfterGc() - heap0;
                afterStrip = scan();
                t0 = System.nanoTime();
                worker = DressWorker.start(level, center, r, r);
                phase = 4;
            }
            case 4 -> {
                if (!worker.hasWork()) {
                    tDress = now - t0;
                    light = lightSettled();
                    phase = 5;
                }
            }
            case 5 -> {
                if (!light.isDone()) return;
                long tDressLight = now - t0;
                long[] after = scan();
                int diffs = 0;
                for (int i = 0; i < ids.length; i++)
                    if (ids[i] != beforeIds[i] && diffs++ < 5)
                        ModLogger.info("BENCH diff #%d: %s -> %s", i, Block.stateById(beforeIds[i]), Block.stateById(ids[i]));
                if (diffs > 0) ModLogger.info("BENCH %d blocks differ after dress", diffs);
                ModLogger.info("BENCH r=%d strip=%dms strip+light=%dms dress=%dms dress+light=%dms peakHeap=%dMB retained=%dMB"
                                + " | stripped: blocks=%x light=%d heightmapBad=%d skyBad=%d | restored blocks=%b light=%b",
                        r, tStrip / 1_000_000, tStripLight / 1_000_000, tDress / 1_000_000, tDressLight / 1_000_000,
                        peak >> 20, retained >> 20, afterStrip[0], afterStrip[1], afterStrip[2], afterStrip[3],
                        after[0] == before[0], after[1] == before[1]);
                phase = 1;
            }
            default -> { }
        }
    }

    /** Completes once the light engine has worked through everything queued so far for the box. */
    private static CompletableFuture<?> lightSettled() {
        var engine = level.getChunkSource().getLightEngine();
        var futures = new java.util.ArrayList<CompletableFuture<?>>();
        for (int cx = (center.getX() - r >> 4) - 1; cx <= (center.getX() + r >> 4) + 1; cx++)
            for (int cz = (center.getZ() - r >> 4) - 1; cz <= (center.getZ() + r >> 4) + 1; cz++)
                futures.add(engine.waitForPendingTasks(cx, cz));
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    private static int[] ids;

    /** [block checksum, light sum, columns whose heightmap is wrong, columns whose lowest open block is not sky-lit 15]. */
    private static long[] scan() {
        var config = WSConfigLoader.getInstance().getConfig();
        int minY = Math.max(config.stripEndY(), level.getMinBuildHeight());
        int maxY = Math.min(config.stripStartY(), level.getMaxBuildHeight() - 1);
        long hash = 1, lightSum = 0, hmBad = 0, skyBad = 0;
        ids = new int[(2 * r + 1) * (2 * r + 1) * (maxY - minY + 1)];
        int n = 0;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = center.getX() - r; x <= center.getX() + r; x++)
            for (int z = center.getZ() - r; z <= center.getZ() + r; z++) {
                for (int y = minY; y <= maxY; y++) {
                    pos.set(x, y, z);
                    hash = hash * 31 + (ids[n++] = Block.getId(level.getBlockState(pos)));
                    lightSum += level.getBrightness(LightLayer.SKY, pos) * 16L + level.getBrightness(LightLayer.BLOCK, pos);
                }
                int top = level.getMaxBuildHeight() - 1;
                while (top >= level.getMinBuildHeight() && level.getBlockState(pos.set(x, top, z)).isAir()) top--;
                if (level.getChunk(x >> 4, z >> 4).getHeight(Heightmap.Types.WORLD_SURFACE, x & 15, z & 15) != top) hmBad++;
                // columns topped by fluid are skipped: water flowing in after the strip is still being relit
                if (top + 1 < level.getMaxBuildHeight() && level.getFluidState(pos.set(x, top, z)).isEmpty() && level.getBrightness(LightLayer.SKY, pos.set(x, top + 1, z)) != 15 && skyBad++ < 3)
                    ModLogger.info("BENCH sky %d at %s above %s", level.getBrightness(LightLayer.SKY, pos), pos.toShortString(), level.getBlockState(pos.below()));
            }
        return new long[]{hash, lightSum, hmBad, skyBad};
    }

    private static long used() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }

    private static long usedAfterGc() {
        System.gc();
        return used();
    }
}
