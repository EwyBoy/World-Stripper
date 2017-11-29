package com.ewyboy.worldstripper.common.util;

/**
 * Created by EwyBoy
 **/
public class Reference {

    public static final class ModInfo {
        public static final String MOD_ID = "worldstripper";
        public static final String MOD_NAME = "World Stripper";
    }

    public static final class Paths {
        public static final String CLIENT_PROXY = "com.ewyboy.worldstripper.proxy.ClientProxy";
        public static final String COMMON_PROXY = "com.ewyboy.worldstripper.proxy.CommonProxy";
        public static final String GUI_PATH = "com.ewyboy.worldstripper.client.gui.GuiFactoryWorldStripper";
    }

    public static final class Keybinding {
        public static final String KeybindingNameStrip = "Strip World";
        public static final String KeybindingNameDress = "Dress World";
        public static final String KeybindingNameInspect = "Inspect Block";

        public static final String KeybindingCategory = ModInfo.MOD_NAME;
    }
}
