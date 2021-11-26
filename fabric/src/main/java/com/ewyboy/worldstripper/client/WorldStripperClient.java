package com.ewyboy.worldstripper.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class WorldStripperClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Keybindings.setup();
    }

}
