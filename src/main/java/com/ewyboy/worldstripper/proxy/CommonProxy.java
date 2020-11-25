package com.ewyboy.worldstripper.proxy;

import com.ewyboy.worldstripper.common.network.MessageHandler;
import com.ewyboy.worldstripper.common.config.temp.ConfigOld;
import com.ewyboy.worldstripper.common.config.temp.ConfigOld.Settings;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

public class CommonProxy implements IModProxy {

    public CommonProxy() {}

    public void construct() {
        //Settings.initStripList();
        //Settings.initStripProfiles();
        //ModLoadingContext.get().registerConfig(Type.COMMON, ConfigOld.settingSpec);
        //ModLoadingContext.get().registerConfig(Type.COMMON, ConfigTest.CONFIG);
    }

    public void setup() {
        MessageHandler.init();
    }
}
