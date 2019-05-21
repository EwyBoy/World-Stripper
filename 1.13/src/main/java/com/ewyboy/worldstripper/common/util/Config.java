package com.ewyboy.worldstripper.common.util;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {


    public static class Common {

        private static List<String> stripList = new ArrayList<>();
        public static Map<Integer, String[]> profileMap = new HashMap<>();

        private static String[] stripProfile1;
        private static String[] stripProfile2;
        private static String[] stripProfile3;
        private static String[] stripProfile4;
        private static String[] stripProfile5;

        public static void initStripList () {
            stripList.add("minecraft:dirt");
            stripList.add("minecraft:grass");
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
        }

        public static void initStripProfiles () {
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

        public final ForgeConfigSpec.ConfigValue<Integer> selectedProfile;
        public final ForgeConfigSpec.ConfigValue<Integer> chunkRadiusX;
        public final ForgeConfigSpec.ConfigValue<Integer> chunkRadiusZ;
        public final ForgeConfigSpec.ConfigValue<Integer> blockStateFlag;
        public final ForgeConfigSpec.ConfigValue<String> replacementBlock;

        public final ForgeConfigSpec.ConfigValue<List<String>> stripProfile1Config;
        public final ForgeConfigSpec.ConfigValue<List<String>> stripProfile2Config;
        public final ForgeConfigSpec.ConfigValue<List<String>> stripProfile3Config;
        public final ForgeConfigSpec.ConfigValue<List<String>> stripProfile4Config;
        public final ForgeConfigSpec.ConfigValue<List<String>> stripProfile5Config;

        Common(ForgeConfigSpec.Builder builder) {

            builder.comment("Config file for World Stripper").push("common");

            chunkRadiusX = builder
                    .comment("Sets the amount of chunks to strip / dress on X-axis")
                    .translation("worldstripper.config.chunkRadiusX")
                    .defineInRange("chunkRadiusX", 3, 1, 256)
            ;

            chunkRadiusZ = builder
                    .comment("Sets the amount of chunks to strip / dress on Z-axis")
                    .translation("worldstripper.config.chunkRadiusZ")
                    .defineInRange("chunkRadiusZ", 3, 1, 256)
            ;

            replacementBlock = builder
                    .comment("Replaces every block removed by the stripper with this block")
                    .translation("worldstripper.config.replacementBlock")
                    .define("replacementBlock", "minecraft:air")
            ;

            blockStateFlag = builder
                    .comment(
                        "Flag 1 will cause a block update. Flag 2 will send the change to clients. Flag 4 will prevent the block from\n" +
                        "being re-rendered, if this is a client world. Flag 8 will force any re-renders to run on the main thread instead\n" +
                        "of the worker pool, if this is a client world and flag 4 is clear. Flag 16 will prevent observers from seeing this change\n" +
                        "Flags can be added together to obtain multiple flag properties"
                    )
                    .translation("worldstripper.config.blockStateFlag")
                    .defineInRange("blockStateFlag", 3, 1, 16)
            ;

            selectedProfile = builder
                    .comment("Selected Profile")
                    .translation("worldstripper.config.selectedProfile")
                    .defineInRange("selectedProfile", 1, 1, 5)
            ;

            stripProfile1Config = builder
                    .comment("Profile 1 - A list of blocks to strip away from the world")
                    .translation("worldstripper.config.profile1")
                    .define("profile1", Lists.newArrayList(stripProfile1))
            ;
            profileMap.replace(1, stripProfile1);

            stripProfile2Config = builder
                    .comment("Profile 2 - A list of blocks to strip away from the world")
                    .translation("worldstripper.config.profile2")
                    .define("profile2", Lists.newArrayList(stripProfile2))
            ;
            profileMap.replace(2, stripProfile2);

            stripProfile3Config = builder
                    .comment("Profile 3 - A list of blocks to strip away from the world")
                    .translation("worldstripper.config.profile3")
                    .define("profile3", Lists.newArrayList(stripProfile3))
            ;
            profileMap.replace(3, stripProfile3);


            stripProfile4Config = builder
                    .comment("Profile 4 - A list of blocks to strip away from the world")
                    .translation("worldstripper.config.profile4")
                    .define("profile4", Lists.newArrayList(stripProfile4))
            ;
            profileMap.replace(4, stripProfile4);

            stripProfile5Config = builder
                    .comment("Profile 5 - A list of blocks to strip away from the world")
                    .translation("worldstripper.config.profile5")
                    .define("profile5", Lists.newArrayList(stripProfile5))
            ;
            profileMap.replace(5, stripProfile5);
        }
    }

    public static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }

}
