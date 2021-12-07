package com.ewyboy.worldstripper.common.stripclub;

import com.ewyboy.worldstripper.common.settings.Settings;
import net.minecraftforge.common.util.Constants;

public class BlockUpdater {

    public static Settings.CommonSettings settings = Settings.SETTINGS;

    private static int notifyNeighbors() {
        return settings.notifyNeighbors.get() ? Constants.BlockFlags.NOTIFY_NEIGHBORS : 0;
    }

    private static int blockUpdate() {
        return settings.blockUpdate.get() ? Constants.BlockFlags.BLOCK_UPDATE : 0;
    }

    private static int noRender() {
        return settings.noRender.get() ? Constants.BlockFlags.NO_RERENDER : 0;
    }

    private static int renderMainThread() {
        return settings.renderMainThread.get() ? Constants.BlockFlags.RERENDER_MAIN_THREAD : 0;
    }

    private static int updateNeighbors() {
        return settings.updateNeighbors.get() ? Constants.BlockFlags.UPDATE_NEIGHBORS : 0;
    }

    public static int getBlockUpdateFlag() {
        return notifyNeighbors() | blockUpdate() | noRender() | renderMainThread() | updateNeighbors();
    }

}
