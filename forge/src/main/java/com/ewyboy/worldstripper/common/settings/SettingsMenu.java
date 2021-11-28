package com.ewyboy.worldstripper.common.settings;

import me.shedaniel.autoconfig.AutoConfig;

public class SettingsMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(Settings.class, parent).get();
    }

}