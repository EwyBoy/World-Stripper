package com.ewyboy.worldstripper.network.messages;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageRemoveBlock {

    public MessageRemoveBlock() {}

    public static void encode(MessageRemoveBlock pkt, PacketBuffer buf) {}

    public static MessageRemoveBlock decode(PacketBuffer buf) {
        return new MessageRemoveBlock();
    }

    public static void handle(MessageRemoveBlock message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
        });
        ctx.get().setPacketHandled(true);
    }

}
