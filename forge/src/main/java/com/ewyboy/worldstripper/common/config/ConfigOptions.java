package com.ewyboy.worldstripper.common.config;

import java.util.List;

public class ConfigOptions {

    public static class Stripping {
        public static int blocksToStripX;
        public static int blocksToStripZ;
        public static boolean liveStripping;
        public static boolean stripBedrock;
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

    public static class BlockUpdate {
        public static boolean notifyNeighbors;
        public static boolean blockUpdate;
        public static boolean noRender;
        public static boolean renderMainThread;
        public static boolean updateNeighbors;
    }

}