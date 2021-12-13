package com.ewyboy.worldstripper.settings;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

@Config(name = "worldstripper")
public class Settings implements ConfigData {

    public Integer stripRadiusX = 48;
    public Integer stripRadiusZ = 48;

    public Integer stripStartY = 256;
    public Integer stripStopY = -64;

    public String replacementBlock = "minecraft:air";

    @ConfigEntry.Gui.CollapsibleObject
    public BlockUpdateSettings updateSettings = new BlockUpdateSettings();

    public static class BlockUpdateSettings {
        public Boolean notifyNeighbors = false;
        public Boolean blockUpdate = true;
        public Boolean noRender = false;
        public Boolean renderMainThread = false;
        public Boolean updateNeighbors = true;
    }

    public static Settings SETTINGS;

    public static void setup() {
        AutoConfig.register(Settings.class, GsonConfigSerializer :: new);
        SETTINGS = AutoConfig.getConfigHolder(Settings.class).getConfig();
    }


}