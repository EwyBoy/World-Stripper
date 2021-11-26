package com.ewyboy.worldstripper.network;

import net.fabricmc.fabric.api.network.PacketConsumer;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;

public abstract class MessageHandler<T extends IPacket> implements PacketConsumer {

    @Override
    public void accept(PacketContext ctx, PacketByteBuf buf) {
        T packet = create();
        packet.read(buf);
        ctx.getTaskQueue().execute(() -> handle(ctx, packet));
    }

    abstract protected T create();

    abstract protected void handle(PacketContext ctx, T message);

}
