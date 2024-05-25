package com.ewyboy.worldstripper.forge;

import dev.architectury.platform.forge.EventBuses;
import com.ewyboy.worldstripper.WorldStripper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WorldStripper.MOD_ID)
public class WorldStripperForge {

    public WorldStripperForge() {
        EventBuses.registerModEventBus(WorldStripper.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        WorldStripper.init();
    }

    public void onClient(FMLClientSetupEvent event) {
        WorldStripper.initClient();
    }

}