package com.ewyboy.worldstrpper.forge;

import dev.architectury.platform.forge.EventBuses;
import com.ewyboy.worldstrpper.WorldStrpper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WorldStrpper.MOD_ID)
public class WorldStrpperForge {
    public WorldStrpperForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(WorldStrpper.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        WorldStrpper.init();
    }
}