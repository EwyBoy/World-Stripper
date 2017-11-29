package com.ewyboy.worldstripper.proxy;

import com.ewyboy.worldstripper.client.KeyBindingHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by EwyBoy
 **/
public class ClientProxy extends CommonProxy {

    @Override
    public void registerKeybindings() {
        KeyBindingHandler.initKeyBinding();
            ClientRegistry.registerKeyBinding(KeyBindingHandler.strip);
            ClientRegistry.registerKeyBinding(KeyBindingHandler.dress);
            ClientRegistry.registerKeyBinding(KeyBindingHandler.inspect);
        MinecraftForge.EVENT_BUS.register(new KeyBindingHandler());
    }
}
