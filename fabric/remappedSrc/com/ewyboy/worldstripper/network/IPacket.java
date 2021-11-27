package com.ewyboy.worldstripper.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface IPacket {

    void read(FriendlyByteBuf buf);

    void write(FriendlyByteBuf buf);

    ResourceLocation getID();

}
