package com.ewyboy.worldstripper.network.packets;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.json.JsonHandler;
import com.ewyboy.worldstripper.network.IPacket;
import com.ewyboy.worldstripper.settings.Settings;
import com.ewyboy.worldstripper.settings.SettingsLoader;
import com.ewyboy.worldstripper.stripclub.BlockUpdater;
import com.ewyboy.worldstripper.workers.StripWorker;
import com.ewyboy.worldstripper.workers.WorldWorker;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.block.state.BlockState;
import java.util.List;
import java.util.Objects;

public class PacketStripWorker implements IPacket {

    public static final ResourceLocation ID = new ResourceLocation(WorldStripper.MOD_ID, "strip_worker_packet");

    public PacketStripWorker() {}

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
            Settings settings = SettingsLoader.SETTINGS;

            int chunkClearSizeX = (settings.stripRadiusX / 2);
            int chunkClearSizeZ = (settings.stripRadiusZ / 2);

            BlockState replacementBlock = Objects.requireNonNull(Registry.BLOCK.get(new ResourceLocation(settings.replacementBlock))).defaultBlockState();
            List<String> stripList = JsonHandler.stripList.getEntries();

            if (player != null) {
                if (player.isSpectator() || player.isCreative()) {
                    player.sendMessage(new TextComponent(ChatFormatting.BOLD + "" + ChatFormatting.RED + "WARNING! " + ChatFormatting.WHITE + "World Stripping Initialized! Lag May Occur.."), ChatType.GAME_INFO, player.getUUID());
                    WorldWorker.addWorker(new StripWorker(new BlockPos(player.position()), chunkClearSizeX, chunkClearSizeZ, player.getLevel(), 4096, BlockUpdater.getBlockUpdateFlag(), replacementBlock, stripList));
                } else {
                    player.sendMessage(new TextComponent(ChatFormatting.RED + "Error: You have to be in creative mode to use this feature!"), ChatType.GAME_INFO, player.getUUID());
                }
            }
        }
    }
}
