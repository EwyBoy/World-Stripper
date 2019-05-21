package com.ewyboy.worldstripper.common.network;

import com.ewyboy.worldstripper.common.network.messages.MessageDressWorld;
import com.ewyboy.worldstripper.common.network.messages.MessageStripWorld;
import com.ewyboy.worldstripper.common.util.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.HashMap;

public class MessageHandler {

    private static int messageID = 0;
    private static final String PROTOCOL_VERSION = Integer.toString(1);

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
        .named(new ResourceLocation(Reference.ModInfo.MOD_ID, "network"))
        .clientAcceptedVersions(PROTOCOL_VERSION :: equals)
        .serverAcceptedVersions(PROTOCOL_VERSION :: equals)
        .networkProtocolVersion(() -> PROTOCOL_VERSION)
        .simpleChannel()
    ;

    private static int nextID() {
        return messageID++;
    }

    public static void init() {
        CHANNEL.registerMessage(nextID(), MessageStripWorld.class, MessageStripWorld :: encode, MessageStripWorld :: decode, MessageStripWorld::handle);
        CHANNEL.registerMessage(nextID(), MessageDressWorld.class, MessageDressWorld :: encode, MessageDressWorld :: decode, MessageDressWorld::handle);
    }

    public static final HashMap<BlockPos, IBlockState> hashedBlockCache = new HashMap<>();
}
