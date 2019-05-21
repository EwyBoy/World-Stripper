package com.ewyboy.worldstripper.proxy;

import com.ewyboy.worldstripper.client.KeyBindingHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Created by EwyBoy
 **/
@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void construct() {
        super.construct();
    }

    @Override
    public void setup() {
        super.setup();
        KeyBindingHandler.initKeyBinding();
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, KeyBindingHandler :: onKeyInput);
    }
}
