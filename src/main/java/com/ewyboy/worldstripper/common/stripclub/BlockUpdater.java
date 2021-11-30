package com.ewyboy.worldstripper.common.stripclub;

import com.ewyboy.worldstripper.common.config.ConfigOptions;

public class BlockUpdater {

    private static int notifyNeighbors() {
        return ConfigOptions.BlockUpdate.notifyNeighbors ? (1 << 0) : 0;
    }

    private static int blockUpdate() {
        return ConfigOptions.BlockUpdate.blockUpdate ? (1 << 1) : 0;
    }

    private static int noRender() {
        return ConfigOptions.BlockUpdate.noRender ?( 1 << 2) : 0;
    }

    private static int renderMainThread() {
        return ConfigOptions.BlockUpdate.renderMainThread ? (1 << 3) : 0;
    }

    private static int updateNeighbors() {
        return ConfigOptions.BlockUpdate.updateNeighbors ? (1 << 4) : 0;
    }

    public static int getBlockUpdateFlag() {
        return notifyNeighbors() | blockUpdate() | noRender() | renderMainThread() | updateNeighbors();
    }

}
