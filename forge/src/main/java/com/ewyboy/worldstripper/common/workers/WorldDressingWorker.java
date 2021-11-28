package com.ewyboy.worldstripper.common.workers;

import com.ewyboy.worldstripper.common.network.MessageHandler;
import net.minecraft.command.CommandSource;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.WorldWorkerManager;

import java.util.Deque;
import java.util.LinkedList;

public class WorldDressingWorker implements WorldWorkerManager.IWorker {

    private final CommandSource listener;
    protected final BlockPos start;
    protected final int radiusX;
    protected final int radiusZ;
    private final int total;
    private final ServerWorld dim;
    private final Deque<BlockPos> queue;
    private final int notificationFrequency;
    private int lastNotification = 0;
    private long lastNotificationTime = 0;
    private final Boolean keepingLoaded = false;
    private int blockUpdateFlag;

    public WorldDressingWorker(CommandSource listener, BlockPos start, int radiusX, int radiusZ, ServerWorld dim, int interval, int blockUpdateFlag) {
        this.listener = listener;
        this.start = start;
        this.radiusX = radiusX;
        this.radiusZ = radiusZ;
        this.dim = dim;
        this.queue = dressQueue();
        this.total = queue.size();
        this.notificationFrequency = interval != -1 ? interval : Math.max(((radiusX + radiusZ) / 2) / 10, 100); //Every 5% or every 100, whichever is more.
        this.lastNotificationTime = System.currentTimeMillis(); //We also notify at least once every 60 seconds, to show we haven't froze.
        this.blockUpdateFlag = blockUpdateFlag;
    }

    private Deque<BlockPos> dressQueue() {
        final Deque<BlockPos> queue = new LinkedList<>();
        final BlockPos neg = new BlockPos(start.getX() - radiusX, 0, start.getZ() - radiusZ);
        final BlockPos pos = new BlockPos(start.getX() + radiusX, 255, start.getZ() + radiusZ);
        BlockPos.betweenClosedStream(neg, pos)
                .map(BlockPos::immutable)
                .filter(MessageHandler.hashedBlockCache::containsKey)
                .forEach(queue::add);
        return queue;
    }

    /**
     * Perform a task, returning true from this will have the manager call this function again this tick if there is time left.
     * Returning false will skip calling this worker until next tick.
     */
    public boolean doWork() {
        BlockPos next;
        do {
            next = queue.pollLast();
        } while ((next == null || !isInStrippedCache(next)) && !queue.isEmpty());

        if (next != null) {
            if (++lastNotification >= notificationFrequency || lastNotificationTime < System.currentTimeMillis() - 60 * 1000) {
                // listener.sendFeedback(new TranslationTextComponent("commands.worldstripper.strip.progress", total - queue.size(), total), true);
                listener.sendSuccess(new StringTextComponent(String.format("Progress: %.02f%%", (float) (total - queue.size()) / total * 100F)), false);
                lastNotification = 0;
                lastNotificationTime = System.currentTimeMillis();
            }
            dim.setBlock(next, MessageHandler.hashedBlockCache.remove(next), blockUpdateFlag);
        }

        if (queue.size() == 0) {
            //  listener.sendFeedback(new TranslationTextComponent("commands.worldstripper.strip.finished"), true);
            listener.sendSuccess(new StringTextComponent("Progress: 100%"), false);
            listener.sendSuccess(new StringTextComponent("World Dressing operation successfully executed!"), false);
            return false;
        }
        return true;
    }

    private boolean isInStrippedCache(BlockPos next) {
        return MessageHandler.hashedBlockCache.containsKey(next);
    }

    public boolean hasWork() {
        return queue.size() > 0;
    }

    private CachedBlockInfo blockInfo(BlockPos pos) {
        return new CachedBlockInfo(dim, pos, keepingLoaded);
    }
}
