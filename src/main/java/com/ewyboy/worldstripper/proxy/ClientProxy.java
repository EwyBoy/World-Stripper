package com.ewyboy.worldstripper.proxy;

import com.ewyboy.worldstripper.client.keybinding.KeyBindingHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    public ClientProxy() {}

    public void construct() {
        super.construct();
    }

    public void setup() {
        super.setup();
        KeyBindingHandler.initKeyBinding();
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, KeyBindingHandler :: onKeyInput);
    }
}