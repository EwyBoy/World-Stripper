package com.ewyboy.worldstripper.common.config.temp;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigOld {

    public static final ForgeConfigSpec settingSpec;
    public static final ConfigOld.Settings SETTINGS;

    public ConfigOld() {}

    static {
        Pair<ConfigOld.Settings, ForgeConfigSpec> specPair = (new Builder()).configure(ConfigOld.Settings::new);
        settingSpec = specPair.getRight();
        SETTINGS = specPair.getLeft();
    }

    public static class Settings {

        private static final List<String> stripList = new ArrayList();
        static Map<Integer, String[]> profileMap = new HashMap();
        private static String[] stripProfile1;
        private static String[] stripProfile2;
        private static String[] stripProfile3;
        private static String[] stripProfile4;
        private static String[] stripProfile5;
        public final ConfigValue<Integer> selectedProfile;
        public final ConfigValue<Integer> chunkRadiusX;
        public final ConfigValue<Integer> chunkRadiusZ;
        public final ConfigValue<Integer> blockStateFlag;
        public final ConfigValue<String> replacementBlock;
        final ConfigValue<List<String>> stripProfile1Config;
        final ConfigValue<List<String>> stripProfile2Config;
        final ConfigValue<List<String>> stripProfile3Config;
        final ConfigValue<List<String>> stripProfile4Config;
        final ConfigValue<List<String>> stripProfile5Config;

        public static void initStripList() {
            stripList.add("minecraft:dirt");
            stripList.add("minecraft:grass");
            stripList.add("minecraft:grass_path");
            stripList.add("minecraft:tall_grass");
            stripList.add("minecraft:grass_block");
            stripList.add("minecraft:stone");
            stripList.add("minecraft:diorite");
            stripList.add("minecraft:granite");
            stripList.add("minecraft:andesite");
            stripList.add("minecraft:gravel");
            stripList.add("minecraft:sand");
            stripList.add("minecraft:sandstone");
            stripList.add("minecraft:oak_log");
            stripList.add("minecraft:dark_oak_log");
            stripList.add("minecraft:spruce_log");
            stripList.add("minecraft:birch_log");
            stripList.add("minecraft:jungle_log");
            stripList.add("minecraft:acacia_log");
            stripList.add("minecraft:oak_leaves");
            stripList.add("minecraft:dark_oak_leaves");
            stripList.add("minecraft:spruce_leaves");
            stripList.add("minecraft:birch_leaves");
            stripList.add("minecraft:jungle_leaves");
            stripList.add("minecraft:acacia_leaves");
            stripList.add("minecraft:water");
            stripList.add("minecraft:flowing_water");
            stripList.add("minecraft:lava");
            stripList.add("minecraft:flowing_lava");
            stripList.add("minecraft:netherrack");
            stripList.add("minecraft:end_stone");
            stripList.add("minecraft:podzol");
            stripList.add("minecraft:bamboo");
            stripList.add("minecraft:seagrass");
            stripList.add("minecraft:tall_seagrass");
        }

        public static void initStripProfiles() {
            stripProfile1 = stripList.toArray(new String[0]);
            profileMap.put(1, stripProfile1);
            stripProfile2 = stripList.toArray(new String[0]);
            profileMap.put(2, stripProfile2);
            stripProfile3 = stripList.toArray(new String[0]);
            profileMap.put(3, stripProfile3);
            stripProfile4 = stripList.toArray(new String[0]);
            profileMap.put(4, stripProfile4);
            stripProfile5 = stripList.toArray(new String[0]);
            profileMap.put(5, stripProfile5);
        }

        Settings(Builder builder) {
            builder.comment("Config file for World Stripper").push("World Stripper - Settings"); {

                builder.push("Values:"); {
                    this.chunkRadiusX = builder.comment("Sets the amount of chunks to strip / dress on X-axis").translation("worldstripper.config.chunkRadiusX").defineInRange("chunkRadiusX", 3, 1, 256);
                    this.chunkRadiusZ = builder.comment("Sets the amount of chunks to strip / dress on Z-axis").translation("worldstripper.config.chunkRadiusZ").defineInRange("chunkRadiusZ", 3, 1, 256);

                } builder.pop();

                builder.push("Tweaks:"); {
                    this.replacementBlock = builder.comment("Replaces every block removed by the stripper with this block").translation("worldstripper.config.replacementBlock").define("replacementBlock", "minecraft:air");
                    this.blockStateFlag = builder.comment("Flag 1 will cause a block update. Flag 2 will send the change to clients. Flag 4 will prevent the block from\nbeing re-rendered, if this is a client world. Flag 8 will force any re-renders to run on the main thread instead\nof the worker pool, if this is a client world and flag 4 is clear. Flag 16 will prevent observers from seeing this change\nFlags can be added together to obtain multiple flag properties").translation("worldstripper.config.blockStateFlag").defineInRange("blockStateFlag", 3, 1, 16);
                    this.selectedProfile = builder.comment("Selected Profile").translation("worldstripper.config.selectedProfile").defineInRange("selectedProfile", 1, 1, 5);

                } builder.pop();

                builder.push("Profiles:"); {
                    this.stripProfile1Config = builder.comment("Profile 1 - A list of blocks to strip away from the world").translation("worldstripper.config.profile1").define("profile1", Lists.newArrayList(stripProfile1));
                    profileMap.replace(1, stripProfile1);
                    this.stripProfile2Config = builder.comment("Profile 2 - A list of blocks to strip away from the world").translation("worldstripper.config.profile2").define("profile2", Lists.newArrayList(stripProfile2));
                    profileMap.replace(2, stripProfile2);
                    this.stripProfile3Config = builder.comment("Profile 3 - A list of blocks to strip away from the world").translation("worldstripper.config.profile3").define("profile3", Lists.newArrayList(stripProfile3));
                    profileMap.replace(3, stripProfile3);
                    this.stripProfile4Config = builder.comment("Profile 4 - A list of blocks to strip away from the world").translation("worldstripper.config.profile4").define("profile4", Lists.newArrayList(stripProfile4));
                    profileMap.replace(4, stripProfile4);
                    this.stripProfile5Config = builder.comment("Profile 5 - A list of blocks to strip away from the world").translation("worldstripper.config.profile5").define("profile5", Lists.newArrayList(stripProfile5));
                    profileMap.replace(5, stripProfile5);
                } builder.pop();

            } builder.pop();
        }

        public void reload() {
            stripProfile1 = this.stripProfile1Config.get().toArray(new String[0]);
        }
    }


}
