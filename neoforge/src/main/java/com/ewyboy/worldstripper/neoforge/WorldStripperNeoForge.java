package com.ewyboy.worldstripper.neoforge;

import com.ewyboy.worldstripper.WorldStripper;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(WorldStripper.MOD_ID)
public final class WorldStripperNeoForge {

    public WorldStripperNeoForge() {
        WorldStripper.init();
    }

    public void onClient(FMLClientSetupEvent event) {
        WorldStripper.initClient();
    }

}