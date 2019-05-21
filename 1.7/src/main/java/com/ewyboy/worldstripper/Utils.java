package com.ewyboy.worldstripper;

import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;

import java.util.LinkedList;

public class Utils {

    public static final KeyBinding strip = new KeyBinding("Strip chunks", 0xD3, "The Stripper mod");
    public static final KeyBinding replace = new KeyBinding("Replace", 0xD2, "The Stripper mod");

    public static final LinkedList<Block> BLOCK_LIST = new LinkedList();

    public Utils() {}

    public static void init() {
        BLOCK_LIST.add(Blocks.dirt);
        BLOCK_LIST.add(Blocks.grass);
        BLOCK_LIST.add(Blocks.stone);
        BLOCK_LIST.add(Blocks.gravel);
        BLOCK_LIST.add(Blocks.sand);
        BLOCK_LIST.add(Blocks.sandstone);
        BLOCK_LIST.add(Blocks.log);
        BLOCK_LIST.add(Blocks.log2);
        BLOCK_LIST.add(Blocks.leaves);
        BLOCK_LIST.add(Blocks.leaves2);
        BLOCK_LIST.add(Blocks.water);
        BLOCK_LIST.add(Blocks.flowing_water);
        BLOCK_LIST.add(Blocks.lava);
        BLOCK_LIST.add(Blocks.flowing_lava);
        BLOCK_LIST.add(Blocks.tallgrass);
        BLOCK_LIST.add(Blocks.double_plant);
        BLOCK_LIST.add(Blocks.netherrack);
        BLOCK_LIST.add(Blocks.end_stone);
        BLOCK_LIST.add(Blocks.red_flower);
        BLOCK_LIST.add(Blocks.yellow_flower);
        BLOCK_LIST.add(Blocks.brown_mushroom);
        BLOCK_LIST.add(Blocks.red_mushroom);
        BLOCK_LIST.add(Blocks.cactus);
    }
}
