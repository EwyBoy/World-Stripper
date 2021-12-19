package com.ewyboy.worldstripper.workers;

import com.ewyboy.worldstripper.json.StripListHandler;
import com.ewyboy.worldstripper.settings.Settings;
import com.ewyboy.worldstripper.stripclub.ModLogger;
import com.ewyboy.worldstripper.stripclub.StripperCache;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraftforge.common.WorldWorkerManager;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class StripWorker implements WorldWorkerManager.IWorker {

    protected final BlockPos start;
    protected final int radiusX;
    protected final int radiusZ;
    private final int total;
    private final ServerLevel dim;
    private final Queue<BlockInWorld> queue;
    private final int notificationFrequency;
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

    public StripWorker(BlockPos start, int radiusX, int radiusZ, ServerLevel dim, int notificationFrequency, int blockUpdateFlag, BlockState replacementBlock) {
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
    }

    private BlockInWorld blockInfo(BlockPos pos) {
        return new BlockInWorld(dim, pos, true);
    }

    private Queue<BlockInWorld> stripQueue() {
        final Queue<BlockInWorld> queue = new LinkedList<>();
        List<String> stripList = StripListHandler.stripList.getEntries();
        final BlockPos neg = new BlockPos(start.getX() - radiusX, Settings.SETTINGS.stripStopY.get(), start.getZ() - radiusZ);
        final BlockPos pos = new BlockPos(start.getX() + radiusX, Settings.SETTINGS.stripStartY.get(), start.getZ() + radiusZ);

        BlockPos.betweenClosedStream(neg, pos)
                .filter(target -> blockInfo(target).getState() != Blocks.AIR.defaultBlockState())
                .filter(target -> stripList.contains(Objects.requireNonNull(blockInfo(target).getState().getBlock().getRegistryName()).toString()))
                .map(BlockPos :: immutable)
                .map(this :: blockInfo)
                .forEach(queue :: add);
        ModLogger.info("Queue Size :: " + queue.size());

        return queue;
    }

    public boolean hasWork() {
        return !queue.isEmpty();
    }

    public boolean doWork() {
        BlockInWorld next;

        do {
            next = queue.poll();
        } while(
            (next == null) && !queue.isEmpty()
        );

        if (next != null) {
            if(++lastNotification >= notificationFrequency || lastNotificationTime < System.currentTimeMillis() - 60 * 1000) {
                setProgress((float) (total - queue.size()) / total * 100F);
                lastNotification = 0;
                lastNotificationTime = System.currentTimeMillis();
            }

            //StripperCache.hashedBlockCache.put(next.getPos(), next.getState());
            dim.setBlock(next.getPos(), replacementBlock, blockUpdateFlag);
        }

        if(queue.size() == 0) {
            setProgress(100);
            return false;
        }

        return true;
    }
}
