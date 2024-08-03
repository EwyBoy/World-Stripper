package com.ewyboy.worldstripper.fabric;

import com.ewyboy.worldstripper.WorldStripperClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class ClientEvents {

    public static void registerEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> WorldStripperClient.checkForKeymappings());
    }

}
