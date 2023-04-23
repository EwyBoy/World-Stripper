package com.ewyboy.worldstripper.network.messages;

import com.ewyboy.worldstripper.settings.Settings;
import com.ewyboy.worldstripper.stripclub.BlockUpdater;
import com.ewyboy.worldstripper.workers.DressWorker;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.WorldWorkerManager;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageDressWorker {

    public int x;
    public int z;

    public MessageDressWorker() {
        this.x = -1;
        this.z = -1;
    }

    public MessageDressWorker(int x, int z) {
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

    public static MessageDressWorker decode(FriendlyByteBuf buf) {
        return new MessageDressWorker(buf.readInt(), buf.readInt());
    }

    public static void handle(MessageDressWorker message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            int fillSizeX = (Settings.SETTINGS.stripRadiusX.get() / 2);
            int fillSizeZ = (Settings.SETTINGS.stripRadiusZ.get() / 2);

            if (player != null) {
                if (player.isSpectator() || player.isCreative()) {
                    player.sendSystemMessage(Component.literal(ChatFormatting.BOLD + "" + ChatFormatting.RED + "WARNING! " + ChatFormatting.WHITE + "World Dressing Initialized! Lag May Occur.."));
                    WorldWorkerManager.addWorker(new DressWorker(BlockPos.containing(player.position()), fillSizeX, fillSizeZ, player.getLevel(), 4096, BlockUpdater.getBlockUpdateFlag()));
                } else {
                    player.sendSystemMessage(Component.literal(ChatFormatting.RED + "Error: You have to be in creative mode to use this feature!"));
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
