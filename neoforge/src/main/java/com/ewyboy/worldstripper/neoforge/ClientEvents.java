package com.ewyboy.worldstripper.neoforge;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.WorldStripperClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = WorldStripper.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event)
    {
        WorldStripperClient.checkForKeymappings();
    }

}
