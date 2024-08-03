package com.ewyboy.worldstripper.networking.packets;

import com.ewyboy.worldstripper.club.BlockUpdater;
import com.ewyboy.worldstripper.json.StrippablesHandler;
import com.ewyboy.worldstripper.json.WSConfigLoader;
import com.ewyboy.worldstripper.networking.PacketBase;
import com.ewyboy.worldstripper.networking.Packets;
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

public record ServerboundStripWorldKeyPressedPacket(boolean pressed) implements PacketBase {

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundStripWorldKeyPressedPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, ServerboundStripWorldKeyPressedPacket :: pressed,
            ServerboundStripWorldKeyPressedPacket :: new
    );

    public static final Type<ServerboundStripWorldKeyPressedPacket> TYPE = new Type<>(Packets.PACKET_ID_STRIP_KEY_PRESSED);

    @Override
    public void handlePacket(Player player) {
        var config = WSConfigLoader.getInstance().getConfig();

        int chunkClearSizeX = (config.stripRadiusX() / 2);
        int chunkClearSizeZ = (config.stripRadiusZ() / 2);

        var namespace = config.replacementBlock().split(":")[0];
        var path = config.replacementBlock().split(":")[1];

        BlockState replacementBlock = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(namespace, path)).defaultBlockState();
        List<String> strippables = StrippablesHandler.strippables.strippables();

        ServerPlayer serverPlayer = (ServerPlayer) player;
        ServerLevel serverLevel = serverPlayer.serverLevel();

        if (serverPlayer.isCreative()) {
            serverPlayer.displayClientMessage(Component.literal(ChatFormatting.BOLD + String.valueOf(ChatFormatting.RED) + "WARNING! " + ChatFormatting.WHITE + "World Stripping Initialized! Lag May Occur.."), true);
            WorldWorker.addWorker(new StripWorker(serverPlayer.blockPosition(), chunkClearSizeX, chunkClearSizeZ, serverLevel, -1, BlockUpdater.getBlockUpdateFlag(), replacementBlock, strippables));
        } else {
            serverPlayer.displayClientMessage(Component.literal(ChatFormatting.BOLD + String.valueOf(ChatFormatting.DARK_RED) + "WARNING! " + ChatFormatting.WHITE + "You must be in creative mode to use this feature!"), true);
        }
    }

    @Override
    public @NotNull Type<ServerboundStripWorldKeyPressedPacket> type() {
        return TYPE;
    }
}
