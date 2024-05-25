package com.ewyboy.worldstripper.fabric;

import com.ewyboy.worldstripper.WorldStripper;
import net.fabricmc.api.ClientModInitializer;

public class WorldStripperClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        WorldStripper.initClient();
    }
}