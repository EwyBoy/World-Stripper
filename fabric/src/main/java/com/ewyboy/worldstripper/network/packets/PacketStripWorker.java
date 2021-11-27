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
import net.minecraft.block.BlockState;
import net.minecraft.network.MessageType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.Objects;

public class PacketStripWorker implements IPacket {

    public static final Identifier ID = new Identifier(WorldStripper.MOD_ID, "strip_worker_packet");

    public PacketStripWorker() {}

    @Override
    public void read(PacketByteBuf packetByteBuf) {}

    @Override
    public void write(PacketByteBuf packetByteBuf) {}

    @Override
    public Identifier getID() {
        return ID;
    }

    public static class Handler implements ServerPlayNetworking.PlayChannelHandler {

        @Override
        public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            Settings settings = SettingsLoader.SETTINGS;

            int chunkClearSizeX = (settings.stripRadiusX / 2);
            int chunkClearSizeZ = (settings.stripRadiusZ / 2);

            BlockState replacementBlock = Objects.requireNonNull(Registry.BLOCK.get(new Identifier(settings.replacementBlock))).getDefaultState();
            List<String> stripList = JsonHandler.stripList.getEntries();

            if (player != null) {
                if (player.isSpectator() || player.isCreative()) {
                    player.sendMessage(new LiteralText(Formatting.BOLD + "" + Formatting.RED + "WARNING! " + Formatting.WHITE + "World Stripping Initialized! Lag May Occur.."), MessageType.GAME_INFO, player.getUuid());
                    WorldWorker.addWorker(new StripWorker(new BlockPos(player.getPos()), chunkClearSizeX, chunkClearSizeZ, player.getServerWorld(), 4096, BlockUpdater.getBlockUpdateFlag(), replacementBlock, stripList));
                } else {
                    player.sendMessage(new LiteralText(Formatting.RED + "Error: You have to be in creative mode to use this feature!"), MessageType.GAME_INFO, player.getUuid());
                }
            }
        }
    }
}
