package com.ewyboy.worldstripper.forge;

import dev.architectury.platform.forge.EventBuses;
import com.ewyboy.worldstripper.WorldStripper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WorldStripper.MOD_ID)
public class WorldStripperForge {
    public WorldStripperForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(WorldStripper.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        WorldStripper.init();
    }
}