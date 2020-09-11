package com.ewyboy.worldstripper.proxy;

import com.ewyboy.worldstripper.common.network.MessageHandler;
import com.ewyboy.worldstripper.common.util.Config;
import com.ewyboy.worldstripper.common.util.Config.Settings;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

public class CommonProxy implements IModProxy {
    public CommonProxy() {
    }

    public void construct() {
        Settings.initStripList();
        Settings.initStripProfiles();
        ModLoadingContext.get().registerConfig(Type.COMMON, Config.settingSpec);
    }

    public void setup() {
        MessageHandler.init();
    }
}
