package com.ewyboy.worldstripper.networking.message;

import com.ewyboy.worldstripper.club.BlockUpdater;
import com.ewyboy.worldstripper.json.WSConfigLoader;
import com.ewyboy.worldstripper.networking.NetworkHandler;
import com.ewyboy.worldstripper.workers.DressWorker;
import com.ewyboy.worldstripper.workers.WorldWorker;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseC2SMessage;
import dev.architectury.networking.simple.MessageType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class MessageDressWorld extends BaseC2SMessage {

    public MessageDressWorld() {}

    public MessageDressWorld(FriendlyByteBuf friendlyByteBuf) {}

    @Override
    public MessageType getType() {
        return NetworkHandler.DRESS_WORLD;
    }

    @Override
    public void write(FriendlyByteBuf buf) {}

    @Override
    public void handle(NetworkManager.PacketContext ctx) {
        int chunkClearSizeX = (WSConfigLoader.getInstance().getConfig().stripRadiusX() / 2);
        int chunkClearSizeZ = (WSConfigLoader.getInstance().getConfig().stripRadiusZ() / 2);

        ServerPlayer player = (ServerPlayer) ctx.getPlayer();
        ServerLevel serverLevel = player.serverLevel();

        if (player.isCreative()) {
            player.displayClientMessage(Component.literal(ChatFormatting.BOLD + String.valueOf(ChatFormatting.RED) + "WARNING! " + ChatFormatting.WHITE + "World Dressing Initialized! Lag May Occur.."), true);
            WorldWorker.addWorker(new DressWorker(player.blockPosition(), chunkClearSizeX, chunkClearSizeZ, serverLevel, -1, BlockUpdater.getBlockUpdateFlag()));
        } else {
            player.displayClientMessage(Component.literal(ChatFormatting.BOLD + String.valueOf(ChatFormatting.DARK_RED) + "WARNING! " + ChatFormatting.WHITE + "You must be in creative mode to use this feature!"), true);
        }
    }
}
