package com.ewyboy.worldstripper.proxy;

import com.ewyboy.worldstripper.common.network.MessageHandler;
import com.ewyboy.worldstripper.common.util.Config;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

/**
 * Created by EwyBoy
 **/
public class CommonProxy implements IModProxy {

    @Override
    public void construct() {
        Config.Common.initStripList();
        Config.Common.initStripProfiles();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.commonSpec);
    }

    @Override
    public void setup() {
        MessageHandler.init();
    }

}
