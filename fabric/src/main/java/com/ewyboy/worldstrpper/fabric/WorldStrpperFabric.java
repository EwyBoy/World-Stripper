package com.ewyboy.worldstrpper.fabric;

import com.ewyboy.worldstrpper.WorldStrpper;
import net.fabricmc.api.ModInitializer;

public class WorldStrpperFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        WorldStrpper.init();
    }
}