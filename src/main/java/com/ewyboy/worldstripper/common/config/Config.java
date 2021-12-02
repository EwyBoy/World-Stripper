package com.ewyboy.worldstripper.common.config;

import com.ewyboy.worldstripper.WorldStripper;
import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class Config {

    public static class Stripping {
        ForgeConfigSpec.IntValue blocksToStripX;
        ForgeConfigSpec.IntValue blocksToStripZ;
        ForgeConfigSpec.BooleanValue liveStripping;
        ForgeConfigSpec.BooleanValue stripBedrock;
        ForgeConfigSpec.ConfigValue<String> replacementBlock;

        ForgeConfigSpec.IntValue stripStartY;
        ForgeConfigSpec.IntValue stripStopY;
    }

    public static class Profiles {
        ForgeConfigSpec.EnumValue profile;

        public enum Profile {
            PROFILE_1,
            PROFILE_2,
            PROFILE_3,
            PROFILE_4,
            PROFILE_5
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

    public static class BlockUpdate {
        ForgeConfigSpec.BooleanValue notifyNeighbors;
        ForgeConfigSpec.BooleanValue blockUpdate;
        ForgeConfigSpec.BooleanValue noRender;
        ForgeConfigSpec.BooleanValue renderMainThread;
        ForgeConfigSpec.BooleanValue updateNeighbors;
    }

    public final Stripping stripping = new Stripping();
    public final Profiles profiles = new Profiles();
    public final BlockUpdate blockUpdate = new BlockUpdate();

    Config(final ForgeConfigSpec.Builder builder) {
        builder.comment(WorldStripper.NAME + " Config File").push("WorldStripper"); {

            builder.comment("Stripper Settings").push("Stripping"); {
                stripping.blocksToStripX = builder
                        .comment("Amount of blocks to strip in on the x-axis")
                        .translation("worldstripper.config.stripping.blocksToStripX")
                        .defineInRange("blocks_to_strip_x", 48, 0, 320
                );
                stripping.blocksToStripZ = builder
                        .comment("Amount of blocks to strip in on the z-axis")
                        .translation("worldstripper.config.stripping.blocksToStripZ")
                        .defineInRange("blocks_to_strip_z", 48, 0, 320
                );
                stripping.liveStripping = builder
                        .comment("Toggles realtime world stripping / dressing")
                        .translation("worldstripper.config.stripping.liveStripping")
                        .define("live_stripping",true
                );
                stripping.stripBedrock = builder
                        .comment("Should bedrock be removed?")
                        .translation("worldstripper.config.stripping.stripBedrock")
                        .define("strip_bedrock", false
                );
                stripping.replacementBlock = builder
                        .comment("Replaces every block touched by the stripper with this block")
                        .translation("worldstripper.config.stripping.replacementBlock")
                        .define("replacement_block", "minecraft:air"
                );

                stripping.stripStartY = builder
                        .comment("World Y-Level to start stripping from")
                        .translation("worldstripper.config.stripping.stripStartY")
                        .defineInRange("strip_start_y", 255, -1073741823,  1073741823
                );

                stripping.stripStopY = builder
                        .comment("World Y-Level to stop stripping at")
                        .translation("worldstripper.config.stripping.stripStopY")
                        .defineInRange("strip_stop_y", -64, -1073741823,  1073741823
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
                        .define("profile_1", Lists.newArrayList(Profiles.defaultStripList)
                );
                profiles.profile2 = builder
                        .comment("Profile 2 - A list of blocks to strip away from the world")
                        .translation("worldstripper.config.profiles.profile2")
                        .define("profile_2", Lists.newArrayList(Profiles.defaultStripList)
                );
                profiles.profile3 = builder
                        .comment("Profile 3 - A list of blocks to strip away from the world")
                        .translation("worldstripper.config.profiles.profile3")
                        .define("profile_3", Lists.newArrayList(Profiles.defaultStripList)
                );
                profiles.profile4 = builder
                        .comment("Profile 4 - A list of blocks to strip away from the world")
                        .translation("worldstripper.config.profiles.profile4")
                        .define("profile_4", Lists.newArrayList(Profiles.defaultStripList)
                );
                profiles.profile5 = builder
                        .comment("Profile 5 - A list of blocks to strip away from the world")
                        .translation("worldstripper.config.profiles.profile5")
                        .define("profile_5", Lists.newArrayList(Profiles.defaultStripList)
                );

            } builder.pop();

            builder.comment("Block Update Settings").push("BlockUpdate"); {

                builder.comment("ADVANCED SETTINGS");
                builder.comment("Don't touch unless you know what you are doing");

                blockUpdate.notifyNeighbors = builder
                        .comment("Calls neighborChanged on surrounding blocks (with isMoving as false)")
                        .translation("worldstripper.config.blockupdate.notifyNeighbors")
                        .define("notify_neighbors", false
                );
                blockUpdate.blockUpdate = builder
                        .comment("Calls for block update")
                        .translation("worldstripper.config.blockupdate.blockUpdate")
                        .define("block_update", true
                );
                blockUpdate.noRender = builder
                        .comment("Stops the blocks from being marked for a render update")
                        .translation("worldstripper.config.blockupdate.noRender")
                        .define("no_render", false
                );
                blockUpdate.renderMainThread = builder
                        .comment("Makes the block be re-rendered immediately, on the main thread")
                        .translation("worldstripper.config.blockupdate.renderMainThread")
                        .define("render_main_thread", false
                );
                blockUpdate.updateNeighbors = builder
                        .comment("Causes neighbor updates to be sent to ALL surrounding blocks")
                        .translation("worldstripper.config.blockupdate.updateNeighbors")
                        .define("update_neighbors", true
                );

            } builder.pop();


        } builder.pop();
    }
}
