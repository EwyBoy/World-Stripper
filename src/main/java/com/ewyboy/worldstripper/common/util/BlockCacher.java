package com.ewyboy.worldstripper.common.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;

/**
 * Created by EwyBoy
 **/
public class BlockCacher {
    public static final HashMap<BlockPos, IBlockState> hashedBlockCache = new HashMap<>();
}
