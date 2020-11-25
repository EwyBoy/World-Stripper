package com.ewyboy.worldstripper.common.network.messages;

import com.ewyboy.worldstripper.client.gui.config.GuiConfigMain;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageOpenConfig {

    public MessageOpenConfig(){}

    public static void encode(MessageOpenConfig packet, PacketBuffer buffer){}

    public static MessageOpenConfig decode(PacketBuffer buffer) {
        return new MessageOpenConfig();
    }

    public static void handle(final MessageOpenConfig message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> Minecraft.getInstance().displayGuiScreen(new GuiConfigMain()));
        ctx.get().setPacketHandled(true);
    }

}
