package com.ewyboy.worldstripper.neoforge;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.client.Keymappings;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = WorldStripper.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void registerKeymappings(RegisterKeyMappingsEvent event) {
        event.registerCategory(Keymappings.CATEGORY);
        Keymappings.registerKeymappings(event :: register);
    }

}
