package com.ewyboy.worldstripper.workers;

import com.ewyboy.worldstripper.club.BlockUpdater;
import com.ewyboy.worldstripper.club.StripperCache;
import com.ewyboy.worldstripper.json.StrippablesHandler;
import com.ewyboy.worldstripper.json.WSConfigLoader;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Replaces every strippable block in the box, remembering the originals in {@link StripperCache}. */
public class StripWorker extends SectionWorker {

    private final Set<Block> strippables;
    private final int blockUpdateFlag;
    private final BlockState replacementBlock;

    public StripWorker(BlockPos center, int radiusX, int radiusZ, ServerLevel level, int blockUpdateFlag, BlockState replacementBlock, List<String> stripList) {
        super(level, center, radiusX, radiusZ, true, "Stripped");
        this.blockUpdateFlag = blockUpdateFlag;
        this.replacementBlock = replacementBlock;

        // Resolve the id list once: the old per-block getKey().toString() + List.contains dominated the cost.
        Set<String> ids = new HashSet<>(stripList);
        this.strippables = new ReferenceOpenHashSet<>();
        for (Block block : BuiltInRegistries.BLOCK) {
            if (ids.contains(BuiltInRegistries.BLOCK.getKey(block).toString())) strippables.add(block);
        }
    }

    /** Queues a strip of the configured box around {@code center}; radii are in blocks. */
    public static StripWorker start(ServerLevel level, BlockPos center, int radiusX, int radiusZ) {
        var config = WSConfigLoader.getInstance().getConfig();
        BlockState replacement = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(config.replacementBlock())).defaultBlockState();
        StripWorker worker = new StripWorker(center, radiusX, radiusZ, level, BlockUpdater.getBlockUpdateFlag(), replacement, StrippablesHandler.strippables.strippables());
        WorldWorker.addWorker(worker);
        return worker;
    }

    @Override
    protected void processSection() {
        LevelChunkSection levelSection = level.getChunk(cx, cz).getSection(section);
        if (levelSection.hasOnlyAir() || !levelSection.maybeHas(state -> strippables.contains(state.getBlock()))) return;

        var cache = StripperCache.of(level);
        long key = sectionKey();
        StripperCache.Section undo = cache.get(key);
        boolean fresh = undo == null;
        if (fresh) undo = new StripperCache.Section(levelSection.getStates().copy()); // originals, before we touch anything

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int y = y1(), y0 = y0(); y >= y0; y--) {
            for (int z = z0(), z1 = z1(); z <= z1; z++) {
                for (int x = x0(), x1 = x1(); x <= x1; x++) {
                    BlockState state = levelSection.getBlockState(x & 15, y & 15, z & 15);
                    if (strippables.contains(state.getBlock())) {
                        if (!fresh) undo.states.getAndSetUnchecked(x & 15, y & 15, z & 15, state);
                        undo.stripped.set((y & 15) << 8 | (z & 15) << 4 | (x & 15));
                        level.setBlock(pos.set(x, y, z), replacementBlock, blockUpdateFlag);
                        changed++;
                    }
                }
            }
        }
        if (fresh && !undo.stripped.isEmpty()) cache.put(key, undo);
    }
}
