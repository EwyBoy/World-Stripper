package com.ewyboy.worldstripper.networking;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.networking.message.MessageAddBlock;
import com.ewyboy.worldstripper.networking.message.MessageDressWorld;
import com.ewyboy.worldstripper.networking.message.MessageRemoveBlock;
import com.ewyboy.worldstripper.networking.message.MessageStripWorld;
import dev.architectury.networking.simple.MessageType;
import dev.architectury.networking.simple.SimpleNetworkManager;

public interface NetworkHandler {
    SimpleNetworkManager MAIN = SimpleNetworkManager.create(WorldStripper.MOD_ID);
    MessageType STRIP_WORLD = MAIN.registerC2S("strip_world", MessageStripWorld :: new);
    MessageType DRESS_WORLD = MAIN.registerC2S("dress_world", MessageDressWorld :: new);
    MessageType ADD_BLOCK = MAIN.registerC2S("add_block", MessageAddBlock :: new);
    MessageType REMOVE_BLOCK = MAIN.registerC2S("remove_block", MessageRemoveBlock:: new);
    static void init() {}
}
