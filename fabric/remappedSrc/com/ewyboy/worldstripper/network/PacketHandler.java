package com.ewyboy.worldstripper.network;

import com.ewyboy.worldstripper.network.packets.PacketAddBlock;
import com.ewyboy.worldstripper.network.packets.PacketDressWorker;
import com.ewyboy.worldstripper.network.packets.PacketRemoveBlock;
import com.ewyboy.worldstripper.network.packets.PacketStripWorker;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import java.util.Iterator;

public class PacketHandler {

    public static void setup() {
        ServerPlayNetworking.registerGlobalReceiver(PacketStripWorker.ID, new PacketStripWorker.Handler());
        ServerPlayNetworking.registerGlobalReceiver(PacketDressWorker.ID, new PacketDressWorker.Handler());
        ServerPlayNetworking.registerGlobalReceiver(PacketAddBlock.ID, new PacketAddBlock.Handler());
        ServerPlayNetworking.registerGlobalReceiver(PacketRemoveBlock.ID, new PacketRemoveBlock.Handler());
    }

    @Environment(EnvType.CLIENT)
    public static void sendToServer(IPacket packet) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        packet.write(buf);

        ClientPacketListener netHandler = Minecraft.getInstance().getConnection();
        if (netHandler != null) {
            netHandler.getConnection().send(new ServerboundCustomPayloadPacket(packet.getID(), buf));
        }
    }

    public static void sendToClient(IPacket packet, ServerPlayer player) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        packet.write(buf);
        player.connection.send(new ClientboundCustomPayloadPacket(packet.getID(), buf));
    }

    public static void sendToAllAround(IPacket packet, Level world, BlockPos center, int radius) {
        if (world instanceof ServerLevel) {
            Iterator<Player> iter = PlayerStream.around(world, center, radius).iterator();
            while (iter.hasNext()) {
                Player player = iter.next();
                sendToClient(packet, (ServerPlayer) player);
            }
        }
    }

}
