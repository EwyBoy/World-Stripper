package com.ewyboy.worldstripper.common.workers;

import com.ewyboy.worldstripper.common.network.MessageHandler;
import com.ewyboy.worldstripper.common.stripclub.StripperCache;
import net.minecraft.command.CommandSource;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.WorldWorkerManager;

import java.util.Deque;
import java.util.LinkedList;

public class DressWorker implements WorldWorkerManager.IWorker {

    protected final BlockPos start;
    protected final int radiusX;
    protected final int radiusZ;
    private final ServerWorld dim;
    private final Deque<BlockPos> queue;
    private final int notificationFrequency;
    private int lastNotification = 0;
    private long lastNotificationTime;
    private final int blockUpdateFlag;

    public DressWorker(BlockPos start, int radiusX, int radiusZ, ServerWorld dim, int interval, int blockUpdateFlag) {
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
        final BlockPos neg = new BlockPos(start.getX() - radiusX, 0, start.getZ() - radiusZ);
        final BlockPos pos = new BlockPos(start.getX() + radiusX, 255, start.getZ() + radiusZ);

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
