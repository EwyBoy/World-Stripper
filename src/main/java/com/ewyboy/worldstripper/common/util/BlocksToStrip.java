package com.ewyboy.worldstripper.common.util;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EwyBoy
 **/
public class BlocksToStrip {

    public static List<Block> blockList = new ArrayList<>();

    static {
        blockList.add(Blocks.STONE);
        blockList.add(Blocks.GRASS);
        blockList.add(Blocks.DIRT);
        blockList.add(Blocks.SAND);
        blockList.add(Blocks.SANDSTONE);
        blockList.add(Blocks.GRAVEL);
        blockList.add(Blocks.LAVA);
        blockList.add(Blocks.FLOWING_LAVA);
        blockList.add(Blocks.WATER);
        blockList.add(Blocks.FLOWING_WATER);
        blockList.add(Blocks.LOG);
        blockList.add(Blocks.LOG2);
        blockList.add(Blocks.LEAVES);
        blockList.add(Blocks.LEAVES2);
    }

}
