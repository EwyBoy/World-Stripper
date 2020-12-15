package com.ewyboy.worldstripper.common.stripclub;

import com.ewyboy.worldstripper.common.config.ConfigOptions;
import net.minecraftforge.common.util.Constants;

public class BlockUpdater {

    private static int notifyNeighbors() {
        return ConfigOptions.BlockUpdate.notifyNeighbors ? Constants.BlockFlags.NOTIFY_NEIGHBORS : 0;
    }

    private static int blockUpdate() {
        return ConfigOptions.BlockUpdate.blockUpdate ? Constants.BlockFlags.BLOCK_UPDATE : 0;
    }

    private static int noRender() {
        return ConfigOptions.BlockUpdate.noRender ? Constants.BlockFlags.NO_RERENDER : 0;
    }

    private static int renderMainThread() {
        return ConfigOptions.BlockUpdate.renderMainThread ? Constants.BlockFlags.RERENDER_MAIN_THREAD : 0;
    }

    private static int updateNeighbors() {
        return ConfigOptions.BlockUpdate.updateNeighbors ? Constants.BlockFlags.UPDATE_NEIGHBORS : 0;
    }

    public static int getBlockUpdateFlag() {
        return notifyNeighbors() | blockUpdate() | noRender() | renderMainThread() | updateNeighbors();
    }

}
