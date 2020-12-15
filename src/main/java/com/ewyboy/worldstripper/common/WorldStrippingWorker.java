package com.ewyboy.worldstripper.common;

import com.ewyboy.worldstripper.common.config.ConfigHelper;
import com.ewyboy.worldstripper.common.config.ConfigOptions;
import com.ewyboy.worldstripper.common.network.MessageHandler;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.WorldWorkerManager;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class WorldStrippingWorker implements WorldWorkerManager.IWorker {

    private final CommandSource listener;
    protected final BlockPos start;
    protected final int radiusX;
    protected final int radiusZ;
    private final int total;
    private final ServerWorld dim;
    private final Queue<CachedBlockInfo> queue;
    private final int notificationFrequency;
    private int lastNotification = 0;
    private long lastNotificationTime = 0;
    private final Boolean keepingLoaded = true;
    private int blockUpdateFlag;

    public WorldStrippingWorker(CommandSource listener, BlockPos start, int radiusX, int radiusZ, ServerWorld dim, int interval, int blockUpdateFlag) {
        this.listener = listener;
        this.start = start;
        this.radiusX = radiusX;
        this.radiusZ = radiusZ;
        this.dim = dim;
        this.queue = stripQueue();
        this.total = queue.size();
        this.notificationFrequency = interval != -1 ? interval : Math.max(((radiusX + radiusZ) / 2) / 10, 100); // Every 5% or every 100, whichever is more.
        this.lastNotificationTime = System.currentTimeMillis(); // We also notify at least once every 60 seconds, to show we haven't froze.
        this.blockUpdateFlag = blockUpdateFlag;
    }

    private Queue<CachedBlockInfo> stripQueue() {
        final Queue<CachedBlockInfo> queue = new LinkedList<>();

        final BlockPos neg = new BlockPos(start.getX() - radiusX, 0, start.getZ() - radiusZ);
        final BlockPos pos = new BlockPos(start.getX() + radiusX, 255, start.getZ() + radiusZ);

        BlockPos.getAllInBox(neg, pos).map(BlockPos :: toImmutable).map(this :: blockInfo).forEach(queue :: add);

        /*
            for (int y = 255 - start.getY(); y >= -start.getY(); y--)
                for (int x = -this.radius; x <= this.radius; x++)
                    for (int z = -this.radius; z <= this.radius; z++)
                        ret.add(blockInfo(start.add(x, y, z)));
        */

        return queue;
    }

    /**
     * Perform a task, returning true from this will have the manager call this function again this tick if there is time left.
     * Returning false will skip calling this worker until next tick.
     */
    public boolean doWork() {
        CachedBlockInfo next;
        do {
            next = queue.poll();
        } while(
            (next == null || !isReplaceableBlock(next)) && !queue.isEmpty()
        );

        if(next != null) {
            if(++lastNotification >= notificationFrequency || lastNotificationTime < System.currentTimeMillis() - 60 * 1000) {
                // TODO:listener.sendFeedback(new TranslationTextComponent("commands.worldstripper.strip.progress", total - queue.size(), total), true);
                listener.sendFeedback(new StringTextComponent(String.format("Progress: %.02f%%", (float) (total - queue.size()) / total * 100F)), false);
                lastNotification = 0;
                lastNotificationTime = System.currentTimeMillis();
            }
            MessageHandler.hashedBlockCache.put(next.getPos(), next.getBlockState());
            dim.setBlockState(next.getPos(), Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ConfigOptions.Stripping.replacementBlock))).getDefaultState(), blockUpdateFlag);
        }

        if(queue.size() == 0) {
            //  listener.sendFeedback(new TranslationTextComponent("commands.worldstripper.strip.finished"), true);
            listener.sendFeedback(new StringTextComponent("Progress: 100%"), false);
            listener.sendFeedback(new StringTextComponent("World Stripping operation successfully executed!"), false);
            return false;
        }
        return true;
    }

    private boolean isReplaceableBlock(CachedBlockInfo next) {
        if(ConfigHelper.profileMap.get(ConfigOptions.Profiles.profile).contains(Objects.requireNonNull(next.getBlockState().getBlock().getRegistryName()).toString())) {
            return true;
        } else if (ConfigOptions.Stripping.stripBedrock) {
            return next.getBlockState().getBlock() == Blocks.BEDROCK;
        } else {
            return false;
        }
    }

    public boolean hasWork() {
        return queue.size() > 0;
    }

    private CachedBlockInfo blockInfo(BlockPos pos) {
        return new CachedBlockInfo(dim, pos, keepingLoaded);
    }

}
