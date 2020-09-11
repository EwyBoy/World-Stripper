package com.ewyboy.worldstripper.common.network;

import com.ewyboy.worldstripper.common.network.messages.MessageDressWorld;
import com.ewyboy.worldstripper.common.network.messages.MessageStripWorld;
import java.util.HashMap;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkRegistry.ChannelBuilder;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class MessageHandler {
    private static int messageID = 0;
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static final SimpleChannel CHANNEL;
    public static final HashMap<BlockPos, BlockState> hashedBlockCache;

    public MessageHandler() {
    }

    private static int nextID() {
        return messageID++;
    }

    public static void init() {
        CHANNEL.registerMessage(nextID(), MessageStripWorld.class, MessageStripWorld::encode, MessageStripWorld::decode, MessageStripWorld::handle);
        CHANNEL.registerMessage(nextID(), MessageDressWorld.class, MessageDressWorld::encode, MessageDressWorld::decode, MessageDressWorld::handle);
    }

    static {
        ChannelBuilder channelBuilder = ChannelBuilder.named(new ResourceLocation("worldstripper", "network"));
        String version = PROTOCOL_VERSION;
        channelBuilder = channelBuilder.clientAcceptedVersions(version::equals);
        version = PROTOCOL_VERSION;
        CHANNEL = channelBuilder.serverAcceptedVersions(version::equals).networkProtocolVersion(() -> {
            return PROTOCOL_VERSION;
        }).simpleChannel();
        hashedBlockCache = new HashMap();
    }
}
