package com.ewyboy.worldstripper.common.stripclub;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IWorldReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StripperCache {
    // TODO maybe rename this StripperCash

    public static final Codec<BlockPos> BLOCK_POS_STRING_CODEC = Codec.STRING.xmap(Long::parseLong, String::valueOf).xmap(BlockPos::fromLong, BlockPos::toLong);

    public static final Codec<StripperCache> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            DimensionType.CODEC.fieldOf("dimension").forGetter(it -> it.dimensionType),
            Codec.unboundedMap(BLOCK_POS_STRING_CODEC, BlockState.CODEC).fieldOf("strippedBlocks").forGetter(it -> it.strippedBlocksMap)
    ).apply(instance, StripperCache :: new));

    // private final long stripperId; TODO
    private final DimensionType dimensionType;
    private final Map<BlockPos, BlockState> strippedBlocksMap;

    private StripperCache(DimensionType dimensionType, Map<BlockPos, BlockState> strippedBlocksMap) {
        this.dimensionType = dimensionType;
        this.strippedBlocksMap = strippedBlocksMap;
    }

    public StripperCache(IWorldReader world) {
        this(world.getDimensionType(), new HashMap<>());
    }

    public void addStrippedBlock(CachedBlockInfo blockInfo) {
        this.strippedBlocksMap.put(blockInfo.getPos(), blockInfo.getBlockState());
    }

    public Set<BlockPos> getCachedPositions() {
        return this.strippedBlocksMap.keySet();
    }

    /**
     * @param pos The position to get the cached block pos from
     * @return The cached block position or null if there is no cached blockstate.
     */
    public BlockState getCachedState(BlockPos pos) {
        return this.strippedBlocksMap.get(pos);
    }
}
