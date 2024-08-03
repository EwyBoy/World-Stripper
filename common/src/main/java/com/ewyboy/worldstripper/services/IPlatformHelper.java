package com.ewyboy.worldstripper.services;

import com.ewyboy.worldstripper.networking.PacketBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.nio.file.Path;
import java.util.function.BiConsumer;

public interface IPlatformHelper {

    /**
     * Gets the name of the platform this is running on
     * @return The name of the platform this is running on
     */
    String getPlatformName();

    /**
     * Gets the directory where the platform stores its configuration files
     * @return The directory where the platform stores its configuration files
     */
    Path getPlatformConfigDir();

    /**
     * Gets the version of the platform this is running on
     * @return The version of the platform this is running on
     */
    boolean isDevelopmentEnvironment();

    /**
     * Registers a serverbound packet
     */
    <T extends PacketBase, B extends FriendlyByteBuf> void  registerServerboundPacket(CustomPacketPayload.Type<T> type, Class<T> clazz, StreamCodec<B, T> codec, BiConsumer<T, Player> handler, Object... args);

    /**
     * Registers a clientbound packet
     */
    <T extends PacketBase, B extends FriendlyByteBuf> void  registerClientboundPacket(CustomPacketPayload.Type<T> type, Class<T> clazz, StreamCodec<B, T> codec, BiConsumer<T, Player> handler, Object... args);

    /**
     * Sends a packet to the server
     */
    void sendPacketToServer(ResourceLocation id, PacketBase packet);

    /**
     * Sends a packet to a player
     */
    void sendPacketToPlayer(ResourceLocation id, PacketBase packet, ServerPlayer player);

}
