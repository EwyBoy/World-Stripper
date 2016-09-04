package com.ewyboy.worldstripper.common.network;

import com.ewyboy.worldstripper.common.network.packets.PacketDressWorld;
import com.ewyboy.worldstripper.common.network.packets.PacketStripWorld;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by EwyBoy
 **/
public class PacketHandler {

    private static int packetID = 0;
    public static SimpleNetworkWrapper INSTANCE = null;

    public PacketHandler() {}

    private static int nextID() {
        return packetID++;
    }

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
        registerMessages();
    }

    public static void registerMessages() {
        INSTANCE.registerMessage(PacketStripWorld.Handler.class, PacketStripWorld.class, nextID(), Side.SERVER);
        INSTANCE.registerMessage(PacketDressWorld.Handler.class, PacketDressWorld.class, nextID(), Side.SERVER);
    }

}
