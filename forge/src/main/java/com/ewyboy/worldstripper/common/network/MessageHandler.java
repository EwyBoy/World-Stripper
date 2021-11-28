package com.ewyboy.worldstripper.common.network;

import com.ewyboy.worldstripper.common.network.messages.MessageAddBlock;
import com.ewyboy.worldstripper.common.network.messages.MessageCycleProfile;
import com.ewyboy.worldstripper.common.network.messages.MessageRemoveBlock;
import com.ewyboy.worldstripper.common.network.messages.MessageDressWorker;
import com.ewyboy.worldstripper.common.network.messages.MessageStripWorker;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkRegistry.ChannelBuilder;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.HashMap;

public class MessageHandler {

    private static int messageID = 0;
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static final SimpleChannel CHANNEL;
    public static final HashMap<BlockPos, BlockState> hashedBlockCache;

    public MessageHandler() {}

    private static int nextID() {
        return messageID++;
    }

    public static void init() {
        CHANNEL.registerMessage(nextID(), MessageStripWorker.class, MessageStripWorker :: encode, MessageStripWorker :: decode, MessageStripWorker :: handle);
        CHANNEL.registerMessage(nextID(), MessageDressWorker.class, MessageDressWorker :: encode, MessageDressWorker :: decode, MessageDressWorker :: handle);
        CHANNEL.registerMessage(nextID(), MessageAddBlock.class, MessageAddBlock :: encode, MessageAddBlock :: decode, MessageAddBlock :: handle);
        CHANNEL.registerMessage(nextID(), MessageRemoveBlock.class, MessageRemoveBlock :: encode, MessageRemoveBlock :: decode, MessageRemoveBlock :: handle);
        CHANNEL.registerMessage(nextID(), MessageCycleProfile.class, MessageCycleProfile :: encode, MessageCycleProfile :: decode, MessageCycleProfile :: handle);
    }

    static {
        ChannelBuilder channelBuilder = ChannelBuilder.named(new ResourceLocation("worldstripper", "network"));
        String version = PROTOCOL_VERSION;
        channelBuilder = channelBuilder.clientAcceptedVersions(version :: equals);
        version = PROTOCOL_VERSION;
        CHANNEL = channelBuilder.serverAcceptedVersions(version :: equals).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();
        hashedBlockCache = new HashMap();
    }
}