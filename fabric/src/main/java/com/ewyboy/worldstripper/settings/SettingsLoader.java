package com.ewyboy.worldstripper.settings;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

import java.util.ArrayList;
import java.util.List;

public class SettingsLoader {

    public static Settings SETTINGS;

    public static void setup() {
        AutoConfig.register(Settings.class, GsonConfigSerializer :: new);
        SETTINGS = AutoConfig.getConfigHolder(Settings.class).getConfig();
    }

    public static List<String> defaultStripList = new ArrayList<>();

    static {
        defaultStripList.add("minecraft:dirt");
        defaultStripList.add("minecraft:grass");
        defaultStripList.add("minecraft:grass_path");
        defaultStripList.add("minecraft:tall_grass");
        defaultStripList.add("minecraft:grass_block");
        defaultStripList.add("minecraft:stone");
        defaultStripList.add("minecraft:diorite");
        defaultStripList.add("minecraft:granite");
        defaultStripList.add("minecraft:andesite");
        defaultStripList.add("minecraft:gravel");
        defaultStripList.add("minecraft:sand");
        defaultStripList.add("minecraft:sandstone");
        defaultStripList.add("minecraft:oak_log");
        defaultStripList.add("minecraft:dark_oak_log");
        defaultStripList.add("minecraft:spruce_log");
        defaultStripList.add("minecraft:birch_log");
        defaultStripList.add("minecraft:jungle_log");
        defaultStripList.add("minecraft:acacia_log");
        defaultStripList.add("minecraft:oak_leaves");
        defaultStripList.add("minecraft:dark_oak_leaves");
        defaultStripList.add("minecraft:spruce_leaves");
        defaultStripList.add("minecraft:birch_leaves");
        defaultStripList.add("minecraft:jungle_leaves");
        defaultStripList.add("minecraft:acacia_leaves");
        defaultStripList.add("minecraft:water");
        defaultStripList.add("minecraft:flowing_water");
        defaultStripList.add("minecraft:lava");
        defaultStripList.add("minecraft:flowing_lava");
        defaultStripList.add("minecraft:netherrack");
        defaultStripList.add("minecraft:end_stone");
        defaultStripList.add("minecraft:podzol");
        defaultStripList.add("minecraft:bamboo");
        defaultStripList.add("minecraft:seagrass");
        defaultStripList.add("minecraft:tall_seagrass");
        defaultStripList.add("minecraft:kelp");
        defaultStripList.add("minecraft:kelp_plant");
    }

}
