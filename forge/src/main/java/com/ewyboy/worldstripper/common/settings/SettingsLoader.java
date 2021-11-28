package com.ewyboy.worldstripper.common.settings;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

public class SettingsLoader {

    public static Settings SETTINGS;

    public static void setup() {
        AutoConfig.register(Settings.class, GsonConfigSerializer :: new);
        SETTINGS = AutoConfig.getConfigHolder(Settings.class).getConfig();
    }

}