package com.ewyboy.worldstripper.common.config;

import com.ewyboy.worldstripper.WorldStripper;
import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class Config {

    public static class General {

        ForgeConfigSpec.IntValue testInt1;
        ForgeConfigSpec.IntValue testInt2;
        ForgeConfigSpec.BooleanValue testBool;
        ForgeConfigSpec.ConfigValue<List<String>> testList;
        ForgeConfigSpec.EnumValue testEnum;

        public enum Test {
            TEST_1,
            TEST_2,
            TEST_3,
            TEST_4,
            TEST_5
        }

    }

    public static class Stripping {

        ForgeConfigSpec.IntValue blocksToStripX;
        ForgeConfigSpec.IntValue blocksToStripZ;
        ForgeConfigSpec.IntValue updateFlag;
        ForgeConfigSpec.ConfigValue<String> replacementBlock;

    }

    public static class Profiles {

        ForgeConfigSpec.EnumValue profile;

        public enum Profile {
            PROFILE_1,
            PROFILE_2,
            PROFILE_3,
            PROFILE_4,
            PROFILE_5,
        }

        ForgeConfigSpec.ConfigValue<List<? extends String>> profile1;
        ForgeConfigSpec.ConfigValue<List<? extends String>> profile2;
        ForgeConfigSpec.ConfigValue<List<? extends String>> profile3;
        ForgeConfigSpec.ConfigValue<List<? extends String>> profile4;
        ForgeConfigSpec.ConfigValue<List<? extends String>> profile5;

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
        }

    }

    public final General general = new General();
    public final Stripping stripping = new Stripping();
    public final Profiles profiles = new Profiles();

    Config(final ForgeConfigSpec.Builder builder) {
        builder.comment(WorldStripper.NAME + " Config File").push("WorldStripper"); {
            builder.comment("General Settings").push("General"); {
                general.testInt1 = builder
                        .comment("Test int 1")
                        .translation("worldstripper.config.common.general.testInt1")
                        .defineInRange("test_int_1", 8, 1, 16
                );
                general.testInt2 = builder
                        .comment("Test int 2")
                        .translation("worldstripper.config.common.general.testInt2")
                        .defineInRange("test_int_2", 8, 1, 16
                );
                general.testBool = builder
                        .comment("Test boolean")
                        .translation("worldstripper.config.common.general.testBool")
                        .define("test_bool", false
                );
                general.testList = builder
                        .comment("Test list")
                        .translation("worldstripper.config.common.general.testList")
                        .define("test_list", Lists.newArrayList("test1", "test2", "test3"), o -> o instanceof String
                );
                general.testEnum = builder
                        .comment("Test enum")
                        .translation("worldstripper.config.common.general.testEnum")
                        .defineEnum("test_enum", General.Test.TEST_1
                );
            } builder.pop();

            builder.comment("Stripper Settings").push("Stripping"); {
                stripping.blocksToStripX = builder
                        .comment("Amount of blocks to strip in on the x-axis")
                        .translation("worldstripper.config.stripping.blocksToStripX")
                        .defineInRange("blocks_to_strip_x", 48, 0, 4096
                );
                stripping.blocksToStripZ = builder
                        .comment("Amount of blocks to strip in on the z-axis")
                        .translation("worldstripper.config.stripping.blocksToStripZ")
                        .defineInRange("blocks_to_strip_z", 48, 0, 4096
                );
                stripping.updateFlag = builder
                        .comment("Read more at www.testurl.com/blockflags")
                        .translation("worldstripper.config.stripping.updateFlag")
                        .defineInRange("update_flag", 18, 1, 64
                );
                stripping.replacementBlock = builder
                        .comment("Replaces every block touched by the stripper with this block")
                        .translation("worldstripper.config.stripping.replacementBlock")
                        .define("replacement_block", "minecraft:air"
                );

            } builder.pop();

            builder.comment("Stripper Profile Settings").push("Profiles"); {
                profiles.profile = builder
                        .comment("Selected profile")
                        .translation("worldstripper.config.common.profiles.profile")
                        .defineEnum("profile", Profiles.Profile.PROFILE_1
                );
                profiles.profile1 = builder
                        .comment("Profile 1 - A list of blocks to strip away from the world")
                        .translation("worldstripper.config.profiles.profile1")
                        .define("profile_1", Lists.newArrayList(Profiles.defaultStripList), o -> o instanceof String
                );
                profiles.profile2 = builder
                        .comment("Profile 2 - A list of blocks to strip away from the world")
                        .translation("worldstripper.config.profiles.profile2")
                        .define("profile_2", Lists.newArrayList(Profiles.defaultStripList), o -> o instanceof String
                );
                profiles.profile3 = builder
                        .comment("Profile 3 - A list of blocks to strip away from the world")
                        .translation("worldstripper.config.profiles.profile3")
                        .define("profile_3", Lists.newArrayList(Profiles.defaultStripList), o -> o instanceof String
                );
                profiles.profile4 = builder
                        .comment("Profile 4 - A list of blocks to strip away from the world")
                        .translation("worldstripper.config.profiles.profile4")
                        .define("profile_4", Lists.newArrayList(Profiles.defaultStripList), o -> o instanceof String
                );
                profiles.profile5 = builder
                        .comment("Profile 5 - A list of blocks to strip away from the world")
                        .translation("worldstripper.config.profiles.profile5")
                        .define("profile_5", Lists.newArrayList(Profiles.defaultStripList), o -> o instanceof String
                );
            } builder.pop();

        } builder.pop();
    }
}
