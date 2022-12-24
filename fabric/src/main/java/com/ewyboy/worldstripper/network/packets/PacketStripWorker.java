package com.ewyboy.worldstripper.network.packets;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.json.StripListHandler;
import com.ewyboy.worldstripper.network.IPacket;
import com.ewyboy.worldstripper.settings.Settings;
import com.ewyboy.worldstripper.stripclub.BlockUpdater;
import com.ewyboy.worldstripper.workers.StripWorker;
import com.ewyboy.worldstripper.workers.WorldWorker;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
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
            int chunkClearSizeX = (Settings.SETTINGS.stripRadiusX / 2);
            int chunkClearSizeZ = (Settings.SETTINGS.stripRadiusZ / 2);

            BlockState replacementBlock = Objects.requireNonNull(BuiltInRegistries.BLOCK.get(new ResourceLocation(Settings.SETTINGS.replacementBlock)).defaultBlockState());
            List<String> stripList = StripListHandler.stripList.getEntries();

            if (player != null) {
                if (player.isSpectator() || player.isCreative()) {
                    player.sendSystemMessage(Component.literal(ChatFormatting.BOLD + "" + ChatFormatting.RED + "WARNING! " + ChatFormatting.WHITE + "World Stripping Initialized! Lag May Occur.."));
                    WorldWorker.addWorker(new StripWorker(new BlockPos(player.position()), chunkClearSizeX, chunkClearSizeZ, player.getLevel(), 4096, BlockUpdater.getBlockUpdateFlag(), replacementBlock, stripList));
                } else {
                    player.sendSystemMessage(Component.literal(ChatFormatting.DARK_RED + "Error: " + ChatFormatting.WHITE + "You have to be in creative mode to use this feature!"));
                }
            }
        }
    }
}
