package com.ewyboy.worldstripper.common.network.messages.stripping;

import com.ewyboy.worldstripper.common.WorldStrippingWorker;
import com.ewyboy.worldstripper.common.config.ConfigOptions;
import com.ewyboy.worldstripper.common.stripclub.BlockUpdater;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.WorldWorkerManager;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class MessageStripWorker {

    public int x;
    public int z;

    public MessageStripWorker() {
        this.x = -1;
        this.z = -1;
    }

    public MessageStripWorker(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void encode(PacketBuffer buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.z);
    }

    public static MessageStripWorker decode(PacketBuffer buf) {
        return new MessageStripWorker(buf.readInt(), buf.readInt());
    }

    public static void handle(MessageStripWorker message, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();

            if (player.isSpectator() || player.isCreative()) {

                int chunkClearSizeX = message.getX() < 0 ? ConfigOptions.Stripping.blocksToStripX : message.getX();
                int chunkClearSizeZ = message.getZ() < 0 ? ConfigOptions.Stripping.blocksToStripZ : message.getZ();

                player.sendStatusMessage(new StringTextComponent(TextFormatting.BOLD + "" + TextFormatting.RED + "WARNING! " + TextFormatting.WHITE + "World Stripping Initialized! Lag May Occur.."), false);
                WorldWorkerManager.addWorker(new WorldStrippingWorker(player.getCommandSource(), player.getPosition(), chunkClearSizeX / 2, chunkClearSizeZ / 2, player.getServerWorld(), 4096, BlockUpdater.getBlockUpdateFlag()));
            } else {
                player.sendStatusMessage(new StringTextComponent(TextFormatting.RED + "Error: You have to be in creative mode to use this feature!"), false);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
