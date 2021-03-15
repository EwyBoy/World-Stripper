package com.ewyboy.worldstripper.proxy;

import com.ewyboy.worldstripper.client.gui.config.GuiConfigMain;
import com.ewyboy.worldstripper.client.keybinding.KeyBindingHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    public ClientProxy() {}

    public void construct() {
        super.construct();
    }

    @OnlyIn(Dist.CLIENT)
    public void setup() {
        super.setup();
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (mc, screen) -> new GuiConfigMain(screen));
        KeyBindingHandler.initKeyBinding();
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, KeyBindingHandler :: onKeyInput);
    }
}