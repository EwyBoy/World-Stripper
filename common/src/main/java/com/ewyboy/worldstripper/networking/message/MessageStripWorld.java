package com.ewyboy.worldstripper.networking.message;

import com.ewyboy.worldstripper.club.BlockUpdater;
import com.ewyboy.worldstripper.json.StrippablesHandler;
import com.ewyboy.worldstripper.json.WSConfigLoader;
import com.ewyboy.worldstripper.networking.NetworkHandler;
import com.ewyboy.worldstripper.workers.StripWorker;
import com.ewyboy.worldstripper.workers.WorldWorker;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseC2SMessage;
import dev.architectury.networking.simple.MessageType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class MessageStripWorld extends BaseC2SMessage {

    public MessageStripWorld() {}

    public MessageStripWorld(FriendlyByteBuf friendlyByteBuf) {}

    @Override
    public MessageType getType() {
        return NetworkHandler.STRIP_WORLD;
    }

    @Override
    public void write(FriendlyByteBuf buf) {}

    @Override
    public void handle(NetworkManager.PacketContext ctx) {
        int chunkClearSizeX = (WSConfigLoader.getInstance().getConfig().stripRadiusX() / 2);
        int chunkClearSizeZ = (WSConfigLoader.getInstance().getConfig().stripRadiusZ() / 2);

        BlockState replacementBlock = BuiltInRegistries.BLOCK.get(new ResourceLocation(WSConfigLoader.getInstance().getConfig().replacementBlock())).defaultBlockState();
        List<String> strippables = StrippablesHandler.strippables.strippables();

        ServerPlayer player = (ServerPlayer) ctx.getPlayer();
        ServerLevel serverLevel = player.serverLevel();

        if (player.isCreative()) {
            player.displayClientMessage(Component.literal(ChatFormatting.BOLD + String.valueOf(ChatFormatting.RED) + "WARNING! " + ChatFormatting.WHITE + "World Stripping Initialized! Lag May Occur.."), true);
            WorldWorker.addWorker(new StripWorker(player.blockPosition(), chunkClearSizeX, chunkClearSizeZ, serverLevel, -1, BlockUpdater.getBlockUpdateFlag(), replacementBlock, strippables));
        } else {
            player.displayClientMessage(Component.literal(ChatFormatting.BOLD + String.valueOf(ChatFormatting.DARK_RED) + "WARNING! " + ChatFormatting.WHITE + "You must be in creative mode to use this feature!"), true);
        }
    }
}
