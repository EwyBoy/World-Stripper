package com.ewyboy.worldstripper;

import net.minecraft.entity.player.EntityPlayer;

import java.util.LinkedHashMap;

public class CommonProxy {

    private static LinkedHashMap REMOVED_CACHE = new LinkedHashMap();

    public CommonProxy() {}

    public static void addPlayerRemovedBlockCache(EntityPlayer player, RemovedBlockCache cache) {
        REMOVED_CACHE.put(player, cache);
    }

    public static RemovedBlockCache getPlayerRemovedBlockCache(EntityPlayer player) {
        return (RemovedBlockCache)REMOVED_CACHE.remove(player);
    }

    static void clearRemovedCache() {
        REMOVED_CACHE.clear();
    }

    public void registerClientOnlyEvents() {}

    public void registerKeyBinds() {}

}