package com.ewyboy.worldstripper.club;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.PalettedContainer;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * What dressing puts back: per dimension and chunk section, a palette copy of the original states
 * plus a bit per stripped block (index = y << 8 | z << 4 | x, the palette's own order).
 * A stone-heavy section costs ~2.5 KB here instead of ~4096 HashMap entries.
 */
public class StripperCache {

    private static final Map<ResourceKey<Level>, Long2ObjectMap<Section>> CACHE = new HashMap<>();

    public static Long2ObjectMap<Section> of(ServerLevel level) {
        return CACHE.computeIfAbsent(level.dimension(), key -> new Long2ObjectOpenHashMap<>());
    }

    public static void clear() {
        CACHE.clear();
    }

    public static final class Section {
        public final PalettedContainer<BlockState> states;
        public final BitSet stripped = new BitSet(4096);

        public Section(PalettedContainer<BlockState> states) {
            this.states = states;
        }
    }
}
