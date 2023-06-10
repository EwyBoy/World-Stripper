package com.ewyboy.worldstripper.workers;

import com.ewyboy.worldstripper.settings.Settings;
import com.ewyboy.worldstripper.stripclub.StripperCache;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraftforge.common.WorldWorkerManager;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class StripWorker implements WorldWorkerManager.IWorker {

    protected final BlockPos start;
    protected final int radiusX;
    protected final int radiusZ;
    private final int total;
    private final ServerLevel dim;
    private final Queue<BlockInWorld> queue;
    private final int notificationFrequency;
    private final List<String> stripList;
    private final int blockUpdateFlag;
    private final BlockState replacementBlock;
    private int lastNotification = 0;
    private long lastNotificationTime;
    private static float progress = 0;

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
        this.stripList = stripList;
    }

    private BlockInWorld blockInfo(BlockPos pos) {
        return new BlockInWorld(dim, pos, true);
    }

    private Queue<BlockInWorld> stripQueue() {
        final Queue<BlockInWorld> queue = new LinkedList<>();
        final int minX = start.getX() - radiusX;
        final int maxX = start.getX() + radiusX;
        final int minY = Settings.SETTINGS.stripStopY.get();
        final int maxY = Settings.SETTINGS.stripStartY.get();
        final int minZ = start.getZ() - radiusZ;
        final int maxZ = start.getZ() + radiusZ;

        for (BlockPos pos : BlockPos.betweenClosed(minX, minY, minZ, maxX, maxY, maxZ)) {
            queue.add(blockInfo(pos));
        }

        return queue;
    }

    private boolean isReplaceableBlock(BlockInWorld next) {
        return this.stripList.contains(BuiltInRegistries.BLOCK.getKey(next.getState().getBlock()).toString());
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
        }

        if (queue.size() == 0) {
            setProgress(100);
            return false;
        }

        return true;
    }
}
