package com.ewyboy.worldstripper.common.config;

import java.util.List;

public class ConfigOptions {

    public static class General {
        public static int testInt1;
        public static int testInt2;
        public static boolean testBool;
        public static List<String> testList;
        public static Enum<Config.General.Test> testEnum;
    }

    public static class Stripping {
        public static int blocksToStripX;
        public static int blocksToStripZ;
        public static int updateFlag;
        public static String replacementBlock;
    }

    public static class Profiles {
        public static Enum<Config.Profiles.Profile> profile;
        public static List<String> profile1;
        public static List<String> profile2;
        public static List<String> profile3;
        public static List<String> profile4;
        public static List<String> profile5;
    }

}