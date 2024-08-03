package com.ewyboy.worldstripper.networking.packets;

import com.ewyboy.worldstripper.club.BlockUpdater;
import com.ewyboy.worldstripper.json.StrippablesHandler;
import com.ewyboy.worldstripper.json.WSConfigLoader;
import com.ewyboy.worldstripper.networking.PacketBase;
import com.ewyboy.worldstripper.networking.Packets;
import com.ewyboy.worldstripper.workers.DressWorker;
import com.ewyboy.worldstripper.workers.StripWorker;
import com.ewyboy.worldstripper.workers.WorldWorker;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record ServerboundDressWorldKeyPressedPacket(boolean pressed) implements PacketBase {

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundDressWorldKeyPressedPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, ServerboundDressWorldKeyPressedPacket :: pressed,
            ServerboundDressWorldKeyPressedPacket :: new
    );

    public static final Type<ServerboundDressWorldKeyPressedPacket> TYPE = new Type<>(Packets.PACKET_ID_DRESS_KEY_PRESSED);

    @Override
    public void handlePacket(Player player) {
        var config = WSConfigLoader.getInstance().getConfig();

        int chunkClearSizeX = (config.stripRadiusX() / 2);
        int chunkClearSizeZ = (config.stripRadiusZ() / 2);

        ServerPlayer serverPlayer = (ServerPlayer) player;
        ServerLevel serverLevel = serverPlayer.serverLevel();

        if (serverPlayer.isCreative()) {
            serverPlayer.displayClientMessage(Component.literal(ChatFormatting.BOLD + String.valueOf(ChatFormatting.RED) + "WARNING! " + ChatFormatting.WHITE + "World Dressing Initialized! Lag May Occur.."), true);
            WorldWorker.addWorker(new DressWorker(serverPlayer.blockPosition(), chunkClearSizeX, chunkClearSizeZ, serverLevel, -1, BlockUpdater.getBlockUpdateFlag()));
        } else {
            serverPlayer.displayClientMessage(Component.literal(ChatFormatting.BOLD + String.valueOf(ChatFormatting.DARK_RED) + "WARNING! " + ChatFormatting.WHITE + "You must be in creative mode to use this feature!"), true);
        }
    }

    @Override
    public @NotNull Type<ServerboundDressWorldKeyPressedPacket> type() {
        return TYPE;
    }
}
