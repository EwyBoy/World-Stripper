package com.ewyboy.worldstripper.networking.packets;

import com.ewyboy.worldstripper.json.WSConfigLoader;
import com.ewyboy.worldstripper.networking.PacketBase;
import com.ewyboy.worldstripper.networking.Packets;
import com.ewyboy.worldstripper.workers.DressWorker;
import com.ewyboy.worldstripper.workers.StripWorker;
import net.minecraft.ChatFormatting;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;


public record ServerboundDressWorldKeyPressedPacket(boolean pressed) implements PacketBase {

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundDressWorldKeyPressedPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, ServerboundDressWorldKeyPressedPacket :: pressed,
            ServerboundDressWorldKeyPressedPacket :: new
    );

    public static final Type<ServerboundDressWorldKeyPressedPacket> TYPE = new Type<>(Packets.PACKET_ID_DRESS_KEY_PRESSED);

    @Override
    public void handlePacket(Player player) {
        var config = WSConfigLoader.getInstance().getConfig();
        ServerPlayer serverPlayer = (ServerPlayer) player;

        if (serverPlayer.isCreative()) {
            serverPlayer.sendOverlayMessage(Component.literal(ChatFormatting.BOLD + String.valueOf(ChatFormatting.RED) + "WARNING! " + ChatFormatting.WHITE + "World Dressing Initialized! Lag May Occur.."));
            DressWorker.start(serverPlayer.level(), serverPlayer.blockPosition(), config.stripRadiusX() / 2, config.stripRadiusZ() / 2);
        } else {
            serverPlayer.sendOverlayMessage(Component.literal(ChatFormatting.BOLD + String.valueOf(ChatFormatting.DARK_RED) + "WARNING! " + ChatFormatting.WHITE + "You must be in creative mode to use this feature!"));
        }
    }

    @Override
    public @NotNull Type<ServerboundDressWorldKeyPressedPacket> type() {
        return TYPE;
    }
}
