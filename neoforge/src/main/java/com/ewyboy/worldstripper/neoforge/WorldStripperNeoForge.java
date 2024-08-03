package com.ewyboy.worldstripper.neoforge;

import com.ewyboy.worldstripper.WorldStripper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(WorldStripper.MOD_ID)
public final class WorldStripperNeoForge {

    public WorldStripperNeoForge(IEventBus bus) {
        WorldStripper.init();
        bus.addListener(this :: registerPackets);
    }

    public void registerPackets(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1.0.0");
        WorldStripper.registerServerPackets(registrar);
        WorldStripper.registerClientPackets(registrar);
    }

}