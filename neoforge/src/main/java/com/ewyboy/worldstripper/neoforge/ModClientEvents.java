package com.ewyboy.worldstripper.neoforge;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.client.Keymappings;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = WorldStripper.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerKeymappings(RegisterKeyMappingsEvent event) {
        Keymappings.registerKeymappings(event :: register);
    }

}
