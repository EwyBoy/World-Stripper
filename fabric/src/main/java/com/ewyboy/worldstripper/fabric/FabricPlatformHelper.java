package com.ewyboy.worldstripper.fabric;

import com.ewyboy.worldstripper.WorldStripperClient;
import com.ewyboy.worldstripper.networking.PacketBase;
import com.ewyboy.worldstripper.services.IPlatformHelper;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.nio.file.Path;
import java.util.function.BiConsumer;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public Path getPlatformConfigDir() {
        return FabricLoaderImpl.INSTANCE.getConfigDir();
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public <T extends PacketBase, B extends FriendlyByteBuf> void registerServerboundPacket(CustomPacketPayload.Type<T> type, Class<T> clazz, StreamCodec<B, T> codec, BiConsumer<T, Player> handler, Object... args) {
        PayloadTypeRegistry.playC2S().register(type, (StreamCodec<RegistryFriendlyByteBuf, T>)codec);

        ServerPlayNetworking.registerGlobalReceiver(type, (T packet, ServerPlayNetworking.Context context) -> {
            context.server().execute(() -> handler.accept(packet, context.player()));
        });
    }

    @Override
    public <T extends PacketBase, B extends FriendlyByteBuf> void registerClientboundPacket(CustomPacketPayload.Type<T> type, Class<T> clazz, StreamCodec<B, T> codec, BiConsumer<T, Player> handler, Object... args) {
        boolean client = (boolean)args[0];
        if(!client) {
            PayloadTypeRegistry.playS2C().register(type, (StreamCodec<RegistryFriendlyByteBuf, T>)codec);
        } else {
            WorldStripperClientFabric.registerClientboundPacket(type, handler);
        }
    }

    @Override
    public void sendPacketToServer(ResourceLocation id, PacketBase packet) {
        WorldStripperClientFabric.sendPacketToServer(packet);
    }

    @Override
    public void sendPacketToPlayer(ResourceLocation id, PacketBase packet, ServerPlayer player) {
        ServerPlayNetworking.send(player, packet);
    }
}
