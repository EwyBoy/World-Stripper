package com.ewyboy.worldstripper.network;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.network.packets.PacketDressWorker;
import com.ewyboy.worldstripper.network.packets.PacketStripWorker;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Iterator;

public class PacketHandler {

    public static void setup() {
        ServerSidePacketRegistry.INSTANCE.register(PacketStripWorker.ID, new PacketStripWorker.Handler());
        ServerSidePacketRegistry.INSTANCE.register(PacketDressWorker.ID, new PacketDressWorker.Handler());
    }

    @Environment(EnvType.CLIENT)
    public static void sendToServer(IPacket packet) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        packet.write(buf);

        ClientPlayNetworkHandler netHandler = MinecraftClient.getInstance().getNetworkHandler();
        if (netHandler != null) {
            netHandler.getConnection().send(new CustomPayloadC2SPacket(packet.getID(), buf));
        }
    }

    public static void sendToClient(IPacket packet, ServerPlayerEntity player) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        packet.write(buf);
        player.networkHandler.sendPacket(new CustomPayloadS2CPacket(packet.getID(), buf));
    }

    public static void sendToAllAround(IPacket packet, World world, BlockPos center, int radius) {
        if (world instanceof ServerWorld) {
            Iterator<PlayerEntity> iter = PlayerStream.around(world, center, radius).iterator();
            while (iter.hasNext()) {
                PlayerEntity player = iter.next();
                sendToClient(packet, (ServerPlayerEntity) player);
            }
        }
    }

}
