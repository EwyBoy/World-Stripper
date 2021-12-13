package com.ewyboy.worldstripper.network;

import com.ewyboy.worldstripper.network.messages.MessageAddBlock;
import com.ewyboy.worldstripper.network.messages.MessageRemoveBlock;
import com.ewyboy.worldstripper.network.messages.MessageDressWorker;
import com.ewyboy.worldstripper.network.messages.MessageStripWorker;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.HashMap;

public class MessageHandler {

    private static int messageID = 0;
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static final SimpleChannel CHANNEL;

    public MessageHandler() {}

    private static int nextID() {
        return messageID++;
    }

    public static void setup() {
        CHANNEL.registerMessage(nextID(), MessageStripWorker.class, MessageStripWorker :: encode, MessageStripWorker :: decode, MessageStripWorker :: handle);
        CHANNEL.registerMessage(nextID(), MessageDressWorker.class, MessageDressWorker :: encode, MessageDressWorker :: decode, MessageDressWorker :: handle);
        CHANNEL.registerMessage(nextID(), MessageAddBlock.class, MessageAddBlock :: encode, MessageAddBlock :: decode, MessageAddBlock :: handle);
        CHANNEL.registerMessage(nextID(), MessageRemoveBlock.class, MessageRemoveBlock :: encode, MessageRemoveBlock :: decode, MessageRemoveBlock :: handle);
    }

    static {
        NetworkRegistry.ChannelBuilder channelBuilder = NetworkRegistry.ChannelBuilder.named(new ResourceLocation("worldstripper", "network"));
        String version = PROTOCOL_VERSION;
        channelBuilder = channelBuilder.clientAcceptedVersions(version :: equals);
        version = PROTOCOL_VERSION;
        CHANNEL = channelBuilder.serverAcceptedVersions(version :: equals).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();
    }
}
