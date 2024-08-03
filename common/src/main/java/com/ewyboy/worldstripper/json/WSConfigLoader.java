package com.ewyboy.worldstripper.json;

import com.ewyboy.worldstripper.services.Services;

import java.io.File;

public class WSConfigLoader {

    private static WSConfigLoader INSTANCE;
    private static final File CONFIG_FILE = new File(Services.PLATFORM.getPlatformConfigDir().toString() + "/worldstripper/config.json");

    private final WSConfig config;

    private WSConfigLoader() {
        this.config = ConfigLoader.loadConfig(WSConfig.class, CONFIG_FILE);
    }

    public static WSConfigLoader getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WSConfigLoader();
        }
        return INSTANCE;
    }

    public WSConfig getConfig() {
        return config;
    }

    public static void init() {
        ConfigLoader.loadConfig(WSConfig.class, CONFIG_FILE);
    }

    public record WSConfig(
            String replacementBlock,
            int stripRadiusX,
            int stripRadiusZ,
            int stripStartY,
            int stripEndY,
            WSBlockStates blockStates
    ) {
        public WSConfig() {
            this("minecraft:air", 48, 48, 256, -64, new WSBlockStates());
        }
    }

    public record WSBlockStates(
            boolean notifyNeighbors,
            boolean updateBlock,
            boolean noRender,
            boolean renderMainThread,
            boolean updateNeighbors
    ) {
        public WSBlockStates() {
            this(true, false, true, true, false);
        }
    }

}
