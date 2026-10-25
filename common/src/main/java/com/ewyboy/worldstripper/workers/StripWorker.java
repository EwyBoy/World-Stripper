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
import net.minecraft.world.level.block.state.pattern.BlockInWorld;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class StripWorker implements WorldWorker.IWorker {

    protected final BlockPos start;
    protected final int radiusX;
    protected final int radiusZ;
    private final int total;
    private final ServerLevel dim;
    private final Queue<BlockInWorld> queue;
    private final int notificationFrequency;
    private final Set<Block> strippables;
    private final int blockUpdateFlag;
    private final BlockState replacementBlock;
    private int lastNotification = 0;
    private long lastNotificationTime;
    private static float progress = 0;
    private final long startNanos = System.nanoTime();
    private int stripped = 0;

    public static float getProgress() {
        return progress;
    }

    public static void setProgress(float progress) {
        StripWorker.progress = progress;
    }

    public StripWorker(BlockPos start, int radiusX, int radiusZ, ServerLevel dim, int notificationFrequency, int blockUpdateFlag, BlockState replacementBlock, List<String> stripList) {
        this.start = start;
        this.radiusX = radiusX;
        this.radiusZ = radiusZ;
        this.dim = dim;
        this.queue = stripQueue();
        this.total = queue.size();
        this.notificationFrequency = notificationFrequency != -1 ? notificationFrequency : Math.max(((radiusX + radiusZ) / 2) / 10, 100); // Every 5% or every 100, whichever is more.
        this.lastNotificationTime = System.currentTimeMillis(); // We also notify at least once every 60 seconds, to show we haven't frozen.
        this.blockUpdateFlag = blockUpdateFlag;
        this.replacementBlock = replacementBlock;
        // Resolve the id list once: the old per-block getKey().toString() + List.contains dominated the cost.
        Set<String> ids = new HashSet<>(stripList);
        this.strippables = new ReferenceOpenHashSet<>();
        for (Block block : BuiltInRegistries.BLOCK) {
            if (ids.contains(BuiltInRegistries.BLOCK.getKey(block).toString())) strippables.add(block);
        }
    }

    /** Queues a strip of the configured box around {@code center}; radii are in blocks. */
    public static StripWorker start(ServerLevel level, BlockPos center, int radiusX, int radiusZ) {
        var config = WSConfigLoader.getInstance().getConfig();
        BlockState replacement = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(config.replacementBlock())).defaultBlockState();
        StripWorker worker = new StripWorker(center, radiusX, radiusZ, level, -1, BlockUpdater.getBlockUpdateFlag(), replacement, StrippablesHandler.strippables.strippables());
        WorldWorker.addWorker(worker);
        return worker;
    }

    private BlockInWorld blockInfo(BlockPos pos) {
        return new BlockInWorld(dim, pos, true);
    }

    private Queue<BlockInWorld> stripQueue() {
        final Queue<BlockInWorld> queue = new LinkedList<>();
        final int minX = start.getX() - radiusX;
        final int maxX = start.getX() + radiusX;
        final int minY = WSConfigLoader.getInstance().getConfig().stripEndY();
        final int maxY = WSConfigLoader.getInstance().getConfig().stripStartY();
        final int minZ = start.getZ() - radiusZ;
        final int maxZ = start.getZ() + radiusZ;

        for (BlockPos pos : BlockPos.betweenClosed(minX, minY, minZ, maxX, maxY, maxZ)) {
            queue.add(blockInfo(pos));
        }

        return queue;
    }

    private boolean isReplaceableBlock(BlockInWorld next) {
        return this.strippables.contains(next.getState().getBlock());
    }

    public boolean hasWork() {
        return !queue.isEmpty();
    }

    public boolean doWork() {
        BlockInWorld next = null;

        while (!queue.isEmpty()) {
            next = queue.poll();
            if (next != null && isReplaceableBlock(next)) {
                break;
            }
        }

        if (next != null) {
            if (++lastNotification >= notificationFrequency || lastNotificationTime < System.currentTimeMillis() - 60 * 1000) {
                setProgress((float) (total - queue.size()) / total * 100F);
                lastNotification = 0;
                lastNotificationTime = System.currentTimeMillis();
            }

            StripperCache.hashedBlockCache.put(next.getPos(), next.getState());
            dim.setBlock(next.getPos(), replacementBlock, blockUpdateFlag);
            stripped++;
        }

        if (queue.isEmpty()) {
            setProgress(100);
            ModLogger.info("Stripped %d blocks in %d ms", stripped, (System.nanoTime() - startNanos) / 1_000_000);
            return false;
        }

        return true;
    }
}
