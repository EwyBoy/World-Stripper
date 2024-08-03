package com.ewyboy.worldstripper.forge;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.WorldStripperClient;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = WorldStripper.MOD_ID)
public class ClientEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent.Post event)
    {
        WorldStripperClient.checkForKeymappings();
    }

}
