package com.ewyboy.worldstripper.fabric;

import com.ewyboy.worldstripper.WorldStripper;
import net.fabricmc.api.ModInitializer;

public class WorldStripperFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        WorldStripper.init();
    }

}