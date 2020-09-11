package net.minecraft.world.gen.blockplacer;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;

public abstract class BlockPlacer {
   public static final Codec<BlockPlacer> field_236435_a_ = Registry.BLOCK_PLACER_TYPE.dispatch(BlockPlacer::func_230368_a_, BlockPlacerType::func_236437_a_);

   public abstract void func_225567_a_(IWorld p_225567_1_, BlockPos p_225567_2_, BlockState p_225567_3_, Random p_225567_4_);

   protected abstract BlockPlacerType<?> func_230368_a_();
}