package com.ewyboy.worldstripper.proxy;

import com.ewyboy.worldstripper.client.KeyBindingHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * Created by EwyBoy
 **/
public class ClientProxy extends CommonProxy {

    @Override
    public void registerKeybindings() {
        KeyBindingHandler.initKeyBinding();
        ClientRegistry.registerKeyBinding(KeyBindingHandler.strip);
        ClientRegistry.registerKeyBinding(KeyBindingHandler.dress);
        MinecraftForge.EVENT_BUS.register(new KeyBindingHandler());
    }
}
