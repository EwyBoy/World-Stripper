package com.ewyboy.worldstripper.neoforge;

import com.ewyboy.worldstripper.networking.PacketBase;
import com.ewyboy.worldstripper.services.IPlatformHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.nio.file.Path;
import java.util.function.BiConsumer;

public class NeoForgePlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public Path getPlatformConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public <T extends PacketBase, B extends FriendlyByteBuf> void registerServerboundPacket(CustomPacketPayload.Type<T> type, Class<T> clazz, StreamCodec<B, T> codec, BiConsumer<T, Player> handler, Object... args) {
        PayloadRegistrar registrar = (PayloadRegistrar) args[0];

        IPayloadHandler<T> serverHandler = (packet, ctx) -> ctx.enqueueWork(() -> handler.accept(packet, ctx.player()));
        registrar.playToServer(type, (StreamCodec<RegistryFriendlyByteBuf, T>) codec, serverHandler);
    }

    @Override
    public <T extends PacketBase, B extends FriendlyByteBuf> void registerClientboundPacket(CustomPacketPayload.Type<T> type, Class<T> clazz, StreamCodec<B, T> codec, BiConsumer<T, Player> handler, Object... args) {
        PayloadRegistrar registrar = (PayloadRegistrar) args[0];

        IPayloadHandler<T> clientHandler = (packet, ctx) -> ctx.enqueueWork(() -> handler.accept(packet, ctx.player()));
        registrar.playToClient(type, (StreamCodec<RegistryFriendlyByteBuf, T>) codec, clientHandler);
    }

    @Override
    public void sendPacketToServer(ResourceLocation id, PacketBase packet) {
        PacketDistributor.sendToServer(packet);
    }

    @Override
    public void sendPacketToPlayer(ResourceLocation id, PacketBase packet, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, packet);
    }
}
