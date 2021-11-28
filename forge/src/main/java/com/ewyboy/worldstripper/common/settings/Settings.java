package com.ewyboy.worldstripper.common.settings;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "worldstripper/config")
public class Settings implements ConfigData {

    @ConfigEntry.BoundedDiscrete(min=1, max=512)
    public Integer stripRadiusX = 48;

    @ConfigEntry.BoundedDiscrete(min=1, max=512)
    public Integer stripRadiusZ = 48;

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

}