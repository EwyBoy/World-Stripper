package com.ewyboy.worldstripper.workers;

import com.ewyboy.worldstripper.json.WSConfigLoader;
import com.ewyboy.worldstripper.util.ModLogger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;

/**
 * Walks a box one chunk section per {@link #doWork()} call: chunk columns x-fastest then z,
 * and within a column top-down (strip) or bottom-up (dress, so falling blocks land on restored ground).
 */
public abstract class SectionWorker implements WorldWorker.IWorker {

    protected final ServerLevel level;
    protected final int minX, maxX, minY, maxY, minZ, maxZ;
    private final int minCX, maxCX, maxCZ, firstSection, lastSection, step;
    private final String verb;
    private final long startNanos = System.nanoTime();
    protected int cx, cz, section, changed;

    protected SectionWorker(ServerLevel level, BlockPos center, int radiusX, int radiusZ, boolean downwards, String verb) {
        var config = WSConfigLoader.getInstance().getConfig();
        this.level = level;
        this.verb = verb;
        this.minX = center.getX() - radiusX;
        this.maxX = center.getX() + radiusX;
        this.minZ = center.getZ() - radiusZ;
        this.maxZ = center.getZ() + radiusZ;
        this.minY = Math.max(config.stripEndY(), level.getMinBuildHeight());
        this.maxY = Math.min(config.stripStartY(), level.getMaxBuildHeight() - 1);
        this.minCX = minX >> 4;
        this.maxCX = maxX >> 4;
        this.maxCZ = maxZ >> 4;
        this.firstSection = level.getSectionIndex(downwards ? maxY : minY);
        this.lastSection = level.getSectionIndex(downwards ? minY : maxY);
        this.step = downwards ? -1 : 1;
        this.cx = minCX;
        this.cz = minZ >> 4;
        this.section = firstSection;
    }

    /** Handles the section at (cx, section, cz). */
    protected abstract void processSection();

    public boolean hasWork() {
        return cz <= maxCZ && minY <= maxY;
    }

    public boolean doWork() {
        if (!hasWork()) return false;
        processSection();
        if (section != lastSection) {
            section += step;
        } else {
            section = firstSection;
            if (++cx > maxCX) {
                cx = minCX;
                cz++;
            }
        }
        if (!hasWork()) {
            ModLogger.info("%s %d blocks in %d ms", verb, changed, (System.nanoTime() - startNanos) / 1_000_000);
        }
        return hasWork();
    }

    protected long sectionKey() {
        return SectionPos.asLong(cx, level.getSectionYFromSectionIndex(section), cz);
    }

    // Clip of the box to the current section, in world coordinates.
    protected int x0() { return Math.max(minX, cx << 4); }
    protected int x1() { return Math.min(maxX, (cx << 4) + 15); }
    protected int z0() { return Math.max(minZ, cz << 4); }
    protected int z1() { return Math.min(maxZ, (cz << 4) + 15); }
    protected int y0() { return Math.max(minY, level.getSectionYFromSectionIndex(section) << 4); }
    protected int y1() { return Math.min(maxY, (level.getSectionYFromSectionIndex(section) << 4) + 15); }
}
