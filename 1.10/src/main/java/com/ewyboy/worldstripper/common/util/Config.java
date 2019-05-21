package com.ewyboy.worldstripper.common.util;

import net.minecraftforge.common.config.Configuration;

import java.util.*;

/*** Created by EwyBoy**/
public class Config {

    public static int blockStateFlag;
    public static int chuckRadius = 3;
    public static int profile = 1;

    public static List<String> stripList = new ArrayList<>();

    static {
        stripList.add("minecraft:dirt");
        stripList.add("minecraft:grass");
        stripList.add("minecraft:stone");
        stripList.add("minecraft:gravel");
        stripList.add("minecraft:sand");
        stripList.add("minecraft:sandstone");
        stripList.add("minecraft:log");
        stripList.add("minecraft:log2");
        stripList.add("minecraft:leaves");
        stripList.add("minecraft:leaves2");
        stripList.add("minecraft:water");
        stripList.add("minecraft:flowing_water");
        stripList.add("minecraft:lava");
        stripList.add("minecraft:flowing_lava");
        stripList.add("minecraft:tallgrass");
        stripList.add("minecraft:netherrack");
        stripList.add("minecraft:end_stone");
        stripList.add("minecraft:red_flower");
        stripList.add("minecraft:double_plant");
    }

    private static String[] stripProfile1 = new String[] {};
    private static String[] stripProfile2 = new String[] {};
    private static String[] stripProfile3 = new String[] {};
    private static String[] stripProfile4 = new String[] {};
    private static String[] stripProfile5 = new String[] {};

    public static Map<Integer, String[]> profileMap = new HashMap<>();

    public static Configuration config;

    static {
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

    public static void syncConfig() {
        chuckRadius = config.getInt("Chunk Radius", Configuration.CATEGORY_GENERAL, chuckRadius, 1, Integer.MAX_VALUE, "Sets the amount of chunks to clear (Chunk * Chunk)");
        profile = config.getInt("Selected Profile:", Configuration.CATEGORY_GENERAL, 1, 1, 5, "Selected Profile");

        stripProfile1 = config.getStringList("Profile 1", Configuration.CATEGORY_GENERAL, stripProfile1, "A list of blocks to strip away from the world");
        profileMap.replace(1, stripProfile1);
        stripProfile2 = config.getStringList("Profile 2", Configuration.CATEGORY_GENERAL, stripProfile2, "A list of blocks to strip away from the world");
        profileMap.replace(2, stripProfile2);
        stripProfile3 = config.getStringList("Profile 3", Configuration.CATEGORY_GENERAL, stripProfile3, "A list of blocks to strip away from the world");
        profileMap.replace(3, stripProfile3);
        stripProfile4 = config.getStringList("Profile 4", Configuration.CATEGORY_GENERAL, stripProfile4, "A list of blocks to strip away from the world");
        profileMap.replace(4, stripProfile4);
        stripProfile5 = config.getStringList("Profile 5", Configuration.CATEGORY_GENERAL, stripProfile5, "A list of blocks to strip away from the world");
        profileMap.replace(5, stripProfile5);

        blockStateFlag = config.getInt(
                "Block State Flag", Configuration.CATEGORY_GENERAL, 3, 1, 16, "" +
                        "Flag 1 will cause a block update. Flag 2 will send the change to clients. Flag 4 will prevent the block from\n" +
                        "being re-rendered, if this is a client world. Flag 8 will force any re-renders to run on the main thread instead\n" +
                        "of the worker pool, if this is a client world and flag 4 is clear. Flag 16 will prevent observers from seeing this change\n" +
                        "Flags can be added together to obtain multiple flag properties"
        );

        if (config.hasChanged()) config.save();
    }
}