package com.ewyboy.worldstripper.workers;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.stripclub.StripperCache;
import net.minecraft.block.BlockState;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class StripWorker implements WorldWorker.IWorker {

    protected final BlockPos start;
    protected final int radiusX;
    protected final int radiusZ;

    private final int total;
    private final ServerWorld dim;
    private final Queue<CachedBlockPosition> queue;
    private final int notificationFrequency;
    private final List<String> stripList;
    private final int blockUpdateFlag;
    private final BlockState replacementBlock;

    private int lastNotification = 0;
    private long lastNotificationTime = 0;

    private static float progress = 0;

    public static float getProgress() {
        return progress;
    }

    public static void setProgress(float progress) {
        StripWorker.progress = progress;
    }

    public StripWorker(BlockPos start, int radiusX, int radiusZ, ServerWorld dim, int notificationFrequency, int blockUpdateFlag, BlockState replacementBlock, List<String> stripList) {
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
        this.stripList = stripList;
    }

    private CachedBlockPosition blockInfo(BlockPos pos) {
        return new CachedBlockPosition(dim, pos, true);
    }

    private Queue<CachedBlockPosition> stripQueue() {
        final Queue<CachedBlockPosition> queue = new LinkedList<>();
        final BlockPos neg = new BlockPos(start.getX() - radiusX, 0, start.getZ() - radiusZ);
        final BlockPos pos = new BlockPos(start.getX() + radiusX, 255, start.getZ() + radiusZ);

        BlockPos.stream(neg, pos)
                .map(BlockPos :: toImmutable)
                .map(this :: blockInfo)
                .forEach(queue :: add);
        return queue;
    }

    private boolean isReplaceableBlock(CachedBlockPosition next) {
        return this.stripList.contains(Registry.BLOCK.getId(next.getBlockState().getBlock()).toString());
    }

    public boolean hasWork() {
        return queue.size() > 0;
    }

    public boolean doWork() {
        CachedBlockPosition next;

        do {
            next = queue.poll();
        } while(
            (next == null || !isReplaceableBlock(next)) && !queue.isEmpty()
        );

        if (next != null) {
            if(++lastNotification >= notificationFrequency || lastNotificationTime < System.currentTimeMillis() - 60 * 1000) {
                setProgress((float) (total - queue.size()) / total * 100F);
                lastNotification = 0;
                lastNotificationTime = System.currentTimeMillis();
            }

            StripperCache.hashedBlockCache.put(next.getBlockPos(), next.getBlockState());
            dim.setBlockState(next.getBlockPos(), replacementBlock, blockUpdateFlag);
        }

        if(queue.size() == 0) {
            setProgress(100);
            return false;
        }

        return true;
    }

}
