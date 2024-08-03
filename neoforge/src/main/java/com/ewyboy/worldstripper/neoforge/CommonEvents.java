package com.ewyboy.worldstripper.neoforge;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.workers.WorldWorker;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.*;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = WorldStripper.MOD_ID)
public class CommonEvents {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event)
    {
        WorldStripper.registerCommands(event.getDispatcher(), event.getBuildContext());
    }

    @SubscribeEvent
    public static void onServerTickPre(ServerTickEvent.Pre ignoredEvent)
    {
        WorldWorker.tick(true);
    }

    @SubscribeEvent
    public static void onServerTickPost(ServerTickEvent.Post ignoredEvent)
    {
        WorldWorker.tick(false);
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event)
    {
        WorldWorker.clear();
    }

}
