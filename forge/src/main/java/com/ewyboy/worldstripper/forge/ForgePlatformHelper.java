package com.ewyboy.worldstripper.forge;

import com.ewyboy.worldstripper.networking.PacketBase;
import com.ewyboy.worldstripper.services.IPlatformHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.network.PacketDistributor;

import java.nio.file.Path;
import java.util.function.BiConsumer;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Forge";
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
        BiConsumer<T, CustomPayloadEvent.Context> serverHandler = (packet, ctx) -> {
            if (ctx.isServerSide()) {
                ctx.setPacketHandled(true);
                ctx.enqueueWork(() -> handler.accept(packet, ctx.getSender()));
            }
        };
        WorldStripperForge.network.messageBuilder(clazz).codec((StreamCodec<FriendlyByteBuf, T>) codec).consumerMainThread(serverHandler).add();
    }

    @Override
    public <T extends PacketBase, B extends FriendlyByteBuf> void registerClientboundPacket(CustomPacketPayload.Type<T> type, Class<T> clazz, StreamCodec<B, T> codec, BiConsumer<T, Player> handler, Object... args) {
        BiConsumer<T, CustomPayloadEvent.Context> clientHandler = (packet, ctx) -> {
            if (ctx.isClientSide()) {
                ctx.setPacketHandled(true);
                ctx.enqueueWork(() -> handler.accept(packet, ctx.getSender()));
            }
        };
        WorldStripperForge.network.messageBuilder(clazz).codec((StreamCodec<FriendlyByteBuf, T>) codec).consumerMainThread(clientHandler).add();
    }

    @Override
    public void sendPacketToServer(ResourceLocation id, PacketBase packet) {
        WorldStripperForge.network.send(packet, PacketDistributor.SERVER.noArg());
    }

    @Override
    public void sendPacketToPlayer(ResourceLocation id, PacketBase packet, ServerPlayer player) {
        WorldStripperForge.network.send(packet, PacketDistributor.PLAYER.noArg());
    }
}
