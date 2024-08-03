package com.ewyboy.worldstripper.forge;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.client.Keymappings;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = WorldStripper.MOD_ID)
public class ModClientEvents {

    @SubscribeEvent
    public static void registerKeymappings(RegisterKeyMappingsEvent event) {
        Keymappings.registerKeymappings(event :: register);
    }

}
