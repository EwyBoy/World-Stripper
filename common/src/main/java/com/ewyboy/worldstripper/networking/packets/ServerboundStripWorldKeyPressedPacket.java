package com.ewyboy.worldstripper.networking.packets;

import com.ewyboy.worldstripper.json.WSConfigLoader;
import com.ewyboy.worldstripper.networking.PacketBase;
import com.ewyboy.worldstripper.networking.Packets;
import com.ewyboy.worldstripper.workers.StripWorker;
import net.minecraft.ChatFormatting;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;


public record ServerboundStripWorldKeyPressedPacket(boolean pressed) implements PacketBase {

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundStripWorldKeyPressedPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, ServerboundStripWorldKeyPressedPacket :: pressed,
            ServerboundStripWorldKeyPressedPacket :: new
    );

    public static final Type<ServerboundStripWorldKeyPressedPacket> TYPE = new Type<>(Packets.PACKET_ID_STRIP_KEY_PRESSED);

    @Override
    public void handlePacket(Player player) {
        var config = WSConfigLoader.getInstance().getConfig();
        ServerPlayer serverPlayer = (ServerPlayer) player;

        if (serverPlayer.isCreative()) {
            serverPlayer.displayClientMessage(Component.literal(ChatFormatting.BOLD + String.valueOf(ChatFormatting.RED) + "WARNING! " + ChatFormatting.WHITE + "World Stripping Initialized! Lag May Occur.."), true);
            StripWorker.start(serverPlayer.serverLevel(), serverPlayer.blockPosition(), config.stripRadiusX() / 2, config.stripRadiusZ() / 2);
        } else {
            serverPlayer.displayClientMessage(Component.literal(ChatFormatting.BOLD + String.valueOf(ChatFormatting.DARK_RED) + "WARNING! " + ChatFormatting.WHITE + "You must be in creative mode to use this feature!"), true);
        }
    }

    @Override
    public @NotNull Type<ServerboundStripWorldKeyPressedPacket> type() {
        return TYPE;
    }
}
