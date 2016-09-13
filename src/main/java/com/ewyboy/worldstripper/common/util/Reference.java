package com.ewyboy.worldstripper.common.util;

/**
 * Created by EwyBoy
 **/
public class Reference {

    public static final class ModInfo {
        public static final String MOD_ID = "worldstripper";
        public static final String MOD_NAME = "World Stripper";
        static final int MAJOR_VERSION = 1;
        static final int MINOR_VERSION = 1;
        static final int PATCH_VERSION = 1;
        static final String MINECRAFT_VERSION = "1.10.2";
        public static final String BUILD_VERSION = MAJOR_VERSION + "." + MINOR_VERSION + "." + PATCH_VERSION + "-" + MINECRAFT_VERSION;
    }

    public static final class Paths {
        public static final String CLIENT_PROXY = "com.ewyboy.worldstripper.proxy.ClientProxy";
        public static final String COMMON_PROXY = "com.ewyboy.worldstripper.proxy.CommonProxy";
    }

    public static final class Keybinding {
        public static final String KeybindingNameStrip = "Strip World";
        public static final String KeybindingNameDress = "Dress World";

        public static final String KeybindingCategory = ModInfo.MOD_NAME;
    }
}
