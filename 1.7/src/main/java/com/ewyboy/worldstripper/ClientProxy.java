package com.ewyboy.worldstripper;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy {

    public ClientProxy() {}

    public void registerClientOnlyEvents() {
        FMLCommonHandler.instance().bus().register(new KeyPressEvent());
    }

    public void registerKeyBinds() {
        ClientRegistry.registerKeyBinding(Utils.strip);
        ClientRegistry.registerKeyBinding(Utils.replace);
    }
}
