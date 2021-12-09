package com.ewyboy.worldstripper.network.packets;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.network.IPacket;
import com.ewyboy.worldstripper.settings.Settings;
import com.ewyboy.worldstripper.stripclub.BlockUpdater;
import com.ewyboy.worldstripper.workers.DressWorker;
import com.ewyboy.worldstripper.workers.WorldWorker;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class PacketDressWorker implements IPacket {

    public static final ResourceLocation ID = new ResourceLocation(WorldStripper.MOD_ID, "dress_worker_packet");

    public PacketDressWorker() {}

    @Override
    public void read(FriendlyByteBuf packetByteBuf) {}

    @Override
    public void write(FriendlyByteBuf packetByteBuf) {}

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    public static class Handler implements ServerPlayNetworking.PlayChannelHandler {

        @Override
        public void receive(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
            int fillSizeX = (Settings.SETTINGS.stripRadiusX / 2);
            int fillSizeZ = (Settings.SETTINGS.stripRadiusZ / 2);

            if (player != null) {
                if (player.isSpectator() || player.isCreative()) {
                    player.sendMessage(new TextComponent(ChatFormatting.BOLD + "" + ChatFormatting.RED + "WARNING! " + ChatFormatting.WHITE + "World Dressing Initialized! Lag May Occur.."), ChatType.GAME_INFO, player.getUUID());
                    WorldWorker.addWorker(new DressWorker(new BlockPos(player.position()), fillSizeX, fillSizeZ, player.getLevel(), 4096, BlockUpdater.getBlockUpdateFlag()));
                } else {
                    player.sendMessage(new TextComponent(ChatFormatting.DARK_RED + "Error: " + ChatFormatting.WHITE + "You have to be in creative mode to use this feature!"), ChatType.GAME_INFO, player.getUUID());
                }
            }
        }
    }

}
