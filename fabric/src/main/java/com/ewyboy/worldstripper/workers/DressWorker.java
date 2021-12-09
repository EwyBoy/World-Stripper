package com.ewyboy.worldstripper.workers;

import com.ewyboy.worldstripper.settings.Settings;
import com.ewyboy.worldstripper.stripclub.StripperCache;
import java.util.Deque;
import java.util.LinkedList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class DressWorker implements WorldWorker.IWorker {

    protected final BlockPos start;
    protected final int radiusX;
    protected final int radiusZ;
    private final ServerLevel dim;
    private final Deque<BlockPos> queue;
    private final int notificationFrequency;
    private int lastNotification = 0;
    private long lastNotificationTime;
    private final int blockUpdateFlag;

    public DressWorker(BlockPos start, int radiusX, int radiusZ, ServerLevel dim, int interval, int blockUpdateFlag) {
        this.start = start;
        this.radiusX = radiusX;
        this.radiusZ = radiusZ;
        this.dim = dim;
        this.queue = dressQueue();
        this.notificationFrequency = interval != -1 ? interval : Math.max(((radiusX + radiusZ) / 2) / 10, 100); // Every 5% or every 100, whichever is more.
        this.lastNotificationTime = System.currentTimeMillis(); // We also notify at least once every 60 seconds, to show we haven't frozen.
        this.blockUpdateFlag = blockUpdateFlag;
    }

    private Deque<BlockPos> dressQueue() {
        final Deque<BlockPos> queue = new LinkedList<>();
        final BlockPos neg = new BlockPos(start.getX() - radiusX, Settings.SETTINGS.stripStopY, start.getZ() - radiusZ);
        final BlockPos pos = new BlockPos(start.getX() + radiusX, Settings.SETTINGS.stripStartY, start.getZ() + radiusZ);

        BlockPos.betweenClosedStream(neg, pos)
                .map(BlockPos :: immutable)
                .filter(StripperCache.hashedBlockCache :: containsKey)
                .forEach(queue :: add);
        return queue;
    }

    private boolean isBlockCached(BlockPos next) {
        return StripperCache.hashedBlockCache.containsKey(next);
    }

    public boolean hasWork() {
        return queue.size() > 0;
    }

    public boolean doWork() {
        BlockPos next;
        do {
            next = queue.pollLast();
        } while (
            (next == null || !isBlockCached(next)) && !queue.isEmpty()
        );

        if (next != null) {
            if (++lastNotification >= notificationFrequency || lastNotificationTime < System.currentTimeMillis() - 60 * 1000) {
                lastNotification = 0;
                lastNotificationTime = System.currentTimeMillis();
            }
            dim.setBlock(next, StripperCache.hashedBlockCache.remove(next), blockUpdateFlag);
        }

        return queue.size() != 0;
    }

}