package com.ewyboy.worldstripper.common.config;

import net.minecraft.util.ResourceLocation;

import java.util.Set;

public class ConfigOptions {

    public static class General {
        public static int testInt1;
        public static int testInt2;
        public static boolean testBool;
        public static Set<ResourceLocation> testList;
    }


    public static class Stripping {
        public static int blocksToStripX;
        public static int blocksToStripZ;
        public static int updateFlag;
        public static String replacementBlock;
    }


    public static class Profiles {
        public static int selectedProfile;
        public static Set<ResourceLocation> profile1;
        public static Set<ResourceLocation> profile2;
        public static Set<ResourceLocation> profile3;
        public static Set<ResourceLocation> profile4;
        public static Set<ResourceLocation> profile5;
    }

}