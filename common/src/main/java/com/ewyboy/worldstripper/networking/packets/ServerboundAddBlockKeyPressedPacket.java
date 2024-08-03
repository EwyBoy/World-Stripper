package com.ewyboy.worldstripper.networking.packets;

import com.ewyboy.worldstripper.club.StripperAccessories;
import com.ewyboy.worldstripper.json.StrippablesHandler;
import com.ewyboy.worldstripper.networking.PacketBase;
import com.ewyboy.worldstripper.networking.Packets;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public record ServerboundAddBlockKeyPressedPacket(boolean pressed) implements PacketBase {

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundAddBlockKeyPressedPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, ServerboundAddBlockKeyPressedPacket :: pressed,
            ServerboundAddBlockKeyPressedPacket :: new
    );

    public static final CustomPacketPayload.Type<ServerboundAddBlockKeyPressedPacket> TYPE = new Type<>(Packets.PACKET_ID_ADD_BLOCK_KEY_PRESSED);

    @Override
    public void handlePacket(Player player) {
        ServerPlayer serverPlayer = (ServerPlayer) player;

        if(serverPlayer.isCreative()) {
            BlockState state = StripperAccessories.getStateFromRaytrace();
            if (state != null) {
                String entry = BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();
                if (StrippablesHandler.addEntry(entry)) {
                    serverPlayer.displayClientMessage(Component.literal(ChatFormatting.GREEN + entry + ChatFormatting.WHITE + " added to strip list"), true);
                } else {
                    serverPlayer.displayClientMessage(Component.literal(ChatFormatting.DARK_RED + "ERROR: " + ChatFormatting.RED + entry + ChatFormatting.WHITE + " is already found in strip list"), true);
                }
            }
        } else {
            serverPlayer.displayClientMessage(Component.literal(ChatFormatting.DARK_RED + "Error: " + ChatFormatting.WHITE + "You have to be in creative mode to use this feature!"), true);
        }
    }

    @Override
    public @NotNull Type<ServerboundAddBlockKeyPressedPacket> type() {
        return TYPE;
    }
}
