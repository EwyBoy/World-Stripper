package com.ewyboy.worldstripper.fabric;

import com.ewyboy.worldstripper.command.WSCommands;
import com.ewyboy.worldstripper.workers.WorldWorker;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class CommonEvents {

    public static void registerEvents() {
        ServerTickEvents.START_SERVER_TICK.register(server -> WorldWorker.tick(true));
        ServerTickEvents.END_SERVER_TICK.register(server -> WorldWorker.tick(false));
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> WorldWorker.clear());
        CommandRegistrationCallback.EVENT.register(((dispatcher, context, selection) -> {
            WSCommands.register(dispatcher, context);
        }));
    }

}
