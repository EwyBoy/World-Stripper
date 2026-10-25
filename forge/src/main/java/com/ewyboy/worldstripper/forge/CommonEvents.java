package com.ewyboy.worldstripper.forge;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.dev.Bench;
import com.ewyboy.worldstripper.workers.WorldWorker;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = WorldStripper.MOD_ID)
public class CommonEvents {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event)
    {
        WorldStripper.registerCommands(event.getDispatcher(), event.getBuildContext());
    }

    @SubscribeEvent
    public static void onServerTickPre(TickEvent.ServerTickEvent.Pre ignoredEvent)
    {
        WorldWorker.tick(true);
    }

    @SubscribeEvent
    public static void onServerTickPost(TickEvent.ServerTickEvent.Post event)
    {
        WorldWorker.tick(false);
        Bench.tick(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event)
    {
        WorldWorker.clear();
    }

}
