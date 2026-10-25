package com.ewyboy.worldstripper.workers;

import com.ewyboy.worldstripper.club.BlockUpdater;
import com.ewyboy.worldstripper.club.StripperCache;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;

/** Puts back every stripped block in the box that {@link StripperCache} remembers, bottom-up. */
public class DressWorker extends SectionWorker {

    private final int blockUpdateFlag;

    public DressWorker(BlockPos center, int radiusX, int radiusZ, ServerLevel level, int blockUpdateFlag) {
        super(level, center, radiusX, radiusZ, false, "Dressed");
        this.blockUpdateFlag = blockUpdateFlag;
    }

    /** Queues restoring every stripped block in the box around {@code center}; radii are in blocks. */
    public static DressWorker start(ServerLevel level, BlockPos center, int radiusX, int radiusZ) {
        DressWorker worker = new DressWorker(center, radiusX, radiusZ, level, BlockUpdater.getBlockUpdateFlag());
        WorldWorker.addWorker(worker);
        return worker;
    }

    @Override
    protected void processSection() {
        var cache = StripperCache.of(level);
        long key = sectionKey();
        StripperCache.Section undo = cache.get(key);
        if (undo == null) return;

        int x0 = x0() & 15, x1 = x1() & 15, y0 = y0() & 15, y1 = y1() & 15, z0 = z0() & 15, z1 = z1() & 15;
        int baseX = cx << 4, baseY = level.getSectionYFromSectionIndex(section) << 4, baseZ = cz << 4;
        LevelChunk chunk = level.getChunk(cx, cz);
        LevelChunkSection levelSection = chunk.getSection(section);
        // Bit order is y-major, so this restores bottom-up.
        for (int i = undo.stripped.nextSetBit(0); i >= 0; i = undo.stripped.nextSetBit(i + 1)) {
            int x = i & 15, z = i >> 4 & 15, y = i >> 8;
            if (x < x0 || x > x1 || y < y0 || y > y1 || z < z0 || z > z1) continue;
            setBlock(chunk, levelSection, baseX + x, baseY + y, baseZ + z, undo.states.get(x, y, z), blockUpdateFlag);
            undo.stripped.clear(i);
            changed++;
        }
        if (undo.stripped.isEmpty()) cache.remove(key);
    }
}
