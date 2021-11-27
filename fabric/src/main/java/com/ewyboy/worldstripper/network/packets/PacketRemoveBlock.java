package com.ewyboy.worldstripper.network.packets;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.json.JsonHandler;
import com.ewyboy.worldstripper.network.IPacket;
import com.ewyboy.worldstripper.stripclub.StripperAccessories;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.network.MessageType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PacketRemoveBlock implements IPacket {

    public static final Identifier ID = new Identifier(WorldStripper.MOD_ID, "remove_block_packet");

    public PacketRemoveBlock() {}

    @Override
    public void read(PacketByteBuf packetByteBuf) {}

    @Override
    public void write(PacketByteBuf packetByteBuf) {}

    @Override
    public Identifier getID() {
        return ID;
    }

    public static class Handler implements ServerPlayNetworking.PlayChannelHandler {

        @Override
        public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            if(player.isSpectator() || player.isCreative()) {
                BlockState state = StripperAccessories.getStateFromRaytrace();
                if (state != null) {
                    String entry = Registry.BLOCK.getId(state.getBlock()).toString();
                    if (JsonHandler.removeEntry(entry)) {
                        player.sendMessage(new LiteralText(Formatting.RED + entry + Formatting.WHITE + " removed from config"), true);
                    } else {
                        //TODO: fix color for text
                        player.sendMessage(new LiteralText(Formatting.RED + "ERROR: " + entry + Formatting.WHITE + " not found in config"), MessageType.GAME_INFO, player.getUuid());
                    }
                }
            } else {
                player.sendMessage(new LiteralText(Formatting.RED + "Error: You have to be in creative mode to use this feature!"), MessageType.GAME_INFO, player.getUuid());
            }
        }
    }

}
