package com.ewyboy.worldstripper.settings;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

public class SettingsHandler {

    public static Settings SETTINGS;

    public static void setup() {
        AutoConfig.register(Settings.class, GsonConfigSerializer:: new);
        SETTINGS = AutoConfig.getConfigHolder(Settings.class).getConfig();
    }


}
