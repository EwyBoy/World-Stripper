package com.ewyboy.worldstripper.common;

import com.ewyboy.worldstripper.common.config.ConfigOptions;
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
    protected final int radius;
    private final int total;
    private final ServerWorld dim;
    private final Queue<CachedBlockInfo> queue;
    private final int notificationFrequency;
    private int lastNotification = 0;
    private long lastNotifcationTime = 0;
    private int genned = 0;
    private Boolean keepingLoaded;

    public WorldStrippingWorker(CommandSource listener, BlockPos start, int radius, ServerWorld dim, int interval) {
        this.listener = listener;
        this.start = start;
        this.radius = radius;
        this.dim = dim;
        this.queue = buildQueue();
        this.total = queue.size();
        this.notificationFrequency = interval != -1 ? interval : Math.max(radius / 20, 100); //Every 5% or every 100, whichever is more.
        this.lastNotifcationTime = System.currentTimeMillis(); //We also notify at least once every 60 seconds, to show we haven't froze.
    }

    private Queue<CachedBlockInfo> buildQueue() {
        Queue<CachedBlockInfo> ret = new LinkedList<>();
        ret.add(blockInfo(start));

        for (int y = 255 - start.getY(); y >= -start.getY(); y--)
            for (int x = -this.radius; x <= this.radius; x++)
                for (int z = -this.radius; z <= this.radius; z++)
                    ret.add(blockInfo(start.add(x, y, z)));

        return ret;
    }

    /**
     * Perform a task, returning true from this will have the manager call this function again this tick if there is time left.
     * Returning false will skip calling this worker until next tick.
     */
    public boolean doWork() {
        CachedBlockInfo next = queue.poll();

        while (next == null || !isReplaceableBlock(next) && !queue.isEmpty()) {
            next = queue.poll();
        }
        if (++lastNotification >= notificationFrequency || lastNotifcationTime < System.currentTimeMillis() - 60 * 1000) {
            // TODO: Send update message using:
//                   listener.sendFeedback(new TranslationTextComponent("commands.worldstripper.strip.progress", total - queue.size(), total), true);
            listener.sendFeedback(new StringTextComponent(String.format("HAHA got %.02f%% of the way there", (float) (total - queue.size()) / total * 100F)), false);
            lastNotification = 0;
            lastNotifcationTime = System.currentTimeMillis();
        }
        dim.setBlockState(next.getPos(), Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ConfigOptions.Stripping.replacementBlock))).getDefaultState(), ConfigOptions.Stripping.updateFlag);

        if (queue.size() == 0) {
            // TODO: Send chat message saying that the work is done:
            //   listener.sendFeedback(new TranslationTextComponent("commands.worldstripper.strip.finished"), true);
            listener.sendFeedback(new StringTextComponent("HAHA I FINSIHED!"), false);
            return false;
        }
        return true;
    }

    private boolean isReplaceableBlock(CachedBlockInfo next) {
        return next.getBlockState().isAir(next.getWorld(), next); // TODO: make this check the config of replaced blocks
    }

    public boolean hasWork() {
        return queue.size() > 0;
    }

    private CachedBlockInfo blockInfo(BlockPos pos) {
        return new CachedBlockInfo(dim, pos, false); // TODO: maybe make this true if you wanna make this force load the world
    }
}
