package com.ewyboy.worldstripper.settings;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

@Config(name = "worldstripper")
public class Settings implements ConfigData {

    public Integer radiusX = 48;
    public Integer radiusZ = 48;

    public String replacementBlock = "minecraft:air";
    public Integer selectedProfile = 1;

    public List<String> stripProfile1 = new ArrayList<>(SettingsLoader.defaultStripList);
    public List<String> stripProfile2 = new ArrayList<>(SettingsLoader.defaultStripList);
    public List<String> stripProfile3 = new ArrayList<>(SettingsLoader.defaultStripList);
    public List<String> stripProfile4 = new ArrayList<>(SettingsLoader.defaultStripList);
    public List<String> stripProfile5 = new ArrayList<>(SettingsLoader.defaultStripList);

    @ConfigEntry.Gui.CollapsibleObject
    BlockUpdateSettings updateSettings = new BlockUpdateSettings();
    public static class BlockUpdateSettings {
        public static Boolean notifyNeighbors = false;
        public static Boolean blockUpdate = true;
        public static Boolean noRender = false;
        public static Boolean renderMainThread = false;
        public static Boolean updateNeighbors = true;
    }

    @Override
    public void validatePostLoad() throws ValidationException {
        ConfigData.super.validatePostLoad();
    }
}
