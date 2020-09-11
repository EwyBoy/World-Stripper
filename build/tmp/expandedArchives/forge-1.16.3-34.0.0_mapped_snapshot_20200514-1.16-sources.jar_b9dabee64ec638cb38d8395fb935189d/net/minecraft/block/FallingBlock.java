package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FallingBlock extends Block {
   public FallingBlock(AbstractBlock.Properties properties) {
      super(properties);
   }

   public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
      worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.func_230329_c_());
   }

   /**
    * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
    * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
    * returns its solidified counterpart.
    * Note that this method should ideally consider only the specific face passed in.
    */
   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, this.func_230329_c_());
      return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
   }

   public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
      if (worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0) {
         FallingBlockEntity fallingblockentity = new FallingBlockEntity(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, worldIn.getBlockState(pos));
         this.onStartFalling(fallingblockentity);
         worldIn.addEntity(fallingblockentity);
      }
   }

   protected void onStartFalling(FallingBlockEntity fallingEntity) {
   }

   protected int func_230329_c_() {
      return 2;
   }

   public static boolean canFallThrough(BlockState state) {
      Material material = state.getMaterial();
      return state.isAir() || state.func_235714_a_(BlockTags.field_232872_am_) || material.isLiquid() || material.isReplaceable();
   }

   public void onEndFalling(World worldIn, BlockPos pos, BlockState fallingState, BlockState hitState, FallingBlockEntity p_176502_5_) {
   }

   public void onBroken(World worldIn, BlockPos pos, FallingBlockEntity p_190974_3_) {
   }

   /**
    * Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
    * this method is unrelated to {@link randomTick} and {@link #needsRandomTick}, and will always be called regardless
    * of whether the block can receive random update ticks
    */
   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      if (rand.nextInt(16) == 0) {
         BlockPos blockpos = pos.down();
         if (worldIn.isAirBlock(blockpos) || canFallThrough(worldIn.getBlockState(blockpos))) {
            double d0 = (double)pos.getX() + rand.nextDouble();
            double d1 = (double)pos.getY() - 0.05D;
            double d2 = (double)pos.getZ() + rand.nextDouble();
            worldIn.addParticle(new BlockParticleData(ParticleTypes.FALLING_DUST, stateIn), d0, d1, d2, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   @OnlyIn(Dist.CLIENT)
   public int getDustColor(BlockState state, IBlockReader p_189876_2_, BlockPos p_189876_3_) {
      return -16777216;
   }
}