package com.ewyboy.worldstripper.common.network.messages.stripping;

import com.ewyboy.worldstripper.common.WorldStrippingWorker;
import com.ewyboy.worldstripper.common.config.ConfigOptions;
import com.ewyboy.worldstripper.common.stripclub.BlockUpdater;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraftforge.common.WorldWorkerManager;
import net.minecraftforge.network.NetworkEvent;

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

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.z);
    }

    public static MessageStripWorker decode(FriendlyByteBuf buf) {
        return new MessageStripWorker(buf.readInt(), buf.readInt());
    }

    public static void handle(MessageStripWorker message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();

            if (player.isSpectator() || player.isCreative()) {

                int chunkClearSizeX = message.getX() < 0 ? ConfigOptions.Stripping.blocksToStripX : message.getX();
                int chunkClearSizeZ = message.getZ() < 0 ? ConfigOptions.Stripping.blocksToStripZ : message.getZ();

                player.displayClientMessage(new TextComponent(ChatFormatting.BOLD + "" + ChatFormatting.RED + "WARNING! " + ChatFormatting.WHITE + "World Stripping Initialized! Lag May Occur.."), false);
                WorldWorkerManager.addWorker(new WorldStrippingWorker(player.createCommandSourceStack(), player.blockPosition(), chunkClearSizeX / 2, chunkClearSizeZ / 2, player.getLevel(), 4096, BlockUpdater.getBlockUpdateFlag()));
            } else {
                player.displayClientMessage(new TextComponent(ChatFormatting.RED + "Error: You have to be in creative mode to use this feature!"), false);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
