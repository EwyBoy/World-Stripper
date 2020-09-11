package net.minecraft.world.gen.feature.structure;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class SwampHutPiece extends ScatteredStructurePiece {
   private boolean witch;
   private boolean field_214822_f;

   public SwampHutPiece(Random random, int x, int z) {
      super(IStructurePieceType.TESH, random, x, 64, z, 7, 7, 9);
   }

   public SwampHutPiece(TemplateManager p_i51340_1_, CompoundNBT p_i51340_2_) {
      super(IStructurePieceType.TESH, p_i51340_2_);
      this.witch = p_i51340_2_.getBoolean("Witch");
      this.field_214822_f = p_i51340_2_.getBoolean("Cat");
   }

   /**
    * (abstract) Helper method to read subclass data from NBT
    */
   protected void readAdditional(CompoundNBT tagCompound) {
      super.readAdditional(tagCompound);
      tagCompound.putBoolean("Witch", this.witch);
      tagCompound.putBoolean("Cat", this.field_214822_f);
   }

   public boolean func_230383_a_(ISeedReader p_230383_1_, StructureManager p_230383_2_, ChunkGenerator p_230383_3_, Random p_230383_4_, MutableBoundingBox p_230383_5_, ChunkPos p_230383_6_, BlockPos p_230383_7_) {
      if (!this.isInsideBounds(p_230383_1_, p_230383_5_, 0)) {
         return false;
      } else {
         this.fillWithBlocks(p_230383_1_, p_230383_5_, 1, 1, 1, 5, 1, 7, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithBlocks(p_230383_1_, p_230383_5_, 1, 4, 2, 5, 4, 7, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithBlocks(p_230383_1_, p_230383_5_, 2, 1, 0, 4, 1, 0, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithBlocks(p_230383_1_, p_230383_5_, 2, 2, 2, 3, 3, 2, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithBlocks(p_230383_1_, p_230383_5_, 1, 2, 3, 1, 3, 6, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithBlocks(p_230383_1_, p_230383_5_, 5, 2, 3, 5, 3, 6, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithBlocks(p_230383_1_, p_230383_5_, 2, 2, 7, 4, 3, 7, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithBlocks(p_230383_1_, p_230383_5_, 1, 0, 2, 1, 3, 2, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LOG.getDefaultState(), false);
         this.fillWithBlocks(p_230383_1_, p_230383_5_, 5, 0, 2, 5, 3, 2, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LOG.getDefaultState(), false);
         this.fillWithBlocks(p_230383_1_, p_230383_5_, 1, 0, 7, 1, 3, 7, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LOG.getDefaultState(), false);
         this.fillWithBlocks(p_230383_1_, p_230383_5_, 5, 0, 7, 5, 3, 7, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LOG.getDefaultState(), false);
         this.setBlockState(p_230383_1_, Blocks.OAK_FENCE.getDefaultState(), 2, 3, 2, p_230383_5_);
         this.setBlockState(p_230383_1_, Blocks.OAK_FENCE.getDefaultState(), 3, 3, 7, p_230383_5_);
         this.setBlockState(p_230383_1_, Blocks.AIR.getDefaultState(), 1, 3, 4, p_230383_5_);
         this.setBlockState(p_230383_1_, Blocks.AIR.getDefaultState(), 5, 3, 4, p_230383_5_);
         this.setBlockState(p_230383_1_, Blocks.AIR.getDefaultState(), 5, 3, 5, p_230383_5_);
         this.setBlockState(p_230383_1_, Blocks.POTTED_RED_MUSHROOM.getDefaultState(), 1, 3, 5, p_230383_5_);
         this.setBlockState(p_230383_1_, Blocks.CRAFTING_TABLE.getDefaultState(), 3, 2, 6, p_230383_5_);
         this.setBlockState(p_230383_1_, Blocks.CAULDRON.getDefaultState(), 4, 2, 6, p_230383_5_);
         this.setBlockState(p_230383_1_, Blocks.OAK_FENCE.getDefaultState(), 1, 2, 1, p_230383_5_);
         this.setBlockState(p_230383_1_, Blocks.OAK_FENCE.getDefaultState(), 5, 2, 1, p_230383_5_);
         BlockState blockstate = Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH);
         BlockState blockstate1 = Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.EAST);
         BlockState blockstate2 = Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.WEST);
         BlockState blockstate3 = Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH);
         this.fillWithBlocks(p_230383_1_, p_230383_5_, 0, 4, 1, 6, 4, 1, blockstate, blockstate, false);
         this.fillWithBlocks(p_230383_1_, p_230383_5_, 0, 4, 2, 0, 4, 7, blockstate1, blockstate1, false);
         this.fillWithBlocks(p_230383_1_, p_230383_5_, 6, 4, 2, 6, 4, 7, blockstate2, blockstate2, false);
         this.fillWithBlocks(p_230383_1_, p_230383_5_, 0, 4, 8, 6, 4, 8, blockstate3, blockstate3, false);
         this.setBlockState(p_230383_1_, blockstate.with(StairsBlock.SHAPE, StairsShape.OUTER_RIGHT), 0, 4, 1, p_230383_5_);
         this.setBlockState(p_230383_1_, blockstate.with(StairsBlock.SHAPE, StairsShape.OUTER_LEFT), 6, 4, 1, p_230383_5_);
         this.setBlockState(p_230383_1_, blockstate3.with(StairsBlock.SHAPE, StairsShape.OUTER_LEFT), 0, 4, 8, p_230383_5_);
         this.setBlockState(p_230383_1_, blockstate3.with(StairsBlock.SHAPE, StairsShape.OUTER_RIGHT), 6, 4, 8, p_230383_5_);

         for(int i = 2; i <= 7; i += 5) {
            for(int j = 1; j <= 5; j += 4) {
               this.replaceAirAndLiquidDownwards(p_230383_1_, Blocks.OAK_LOG.getDefaultState(), j, -1, i, p_230383_5_);
            }
         }

         if (!this.witch) {
            int l = this.getXWithOffset(2, 5);
            int i1 = this.getYWithOffset(2);
            int k = this.getZWithOffset(2, 5);
            if (p_230383_5_.isVecInside(new BlockPos(l, i1, k))) {
               this.witch = true;
               WitchEntity witchentity = EntityType.WITCH.create(p_230383_1_.getWorld());
               witchentity.enablePersistence();
               witchentity.setLocationAndAngles((double)l + 0.5D, (double)i1, (double)k + 0.5D, 0.0F, 0.0F);
               witchentity.onInitialSpawn(p_230383_1_, p_230383_1_.getDifficultyForLocation(new BlockPos(l, i1, k)), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
               p_230383_1_.func_242417_l(witchentity);
            }
         }

         this.func_214821_a(p_230383_1_, p_230383_5_);
         return true;
      }
   }

   private void func_214821_a(IServerWorld p_214821_1_, MutableBoundingBox p_214821_2_) {
      if (!this.field_214822_f) {
         int i = this.getXWithOffset(2, 5);
         int j = this.getYWithOffset(2);
         int k = this.getZWithOffset(2, 5);
         if (p_214821_2_.isVecInside(new BlockPos(i, j, k))) {
            this.field_214822_f = true;
            CatEntity catentity = EntityType.CAT.create(p_214821_1_.getWorld());
            catentity.enablePersistence();
            catentity.setLocationAndAngles((double)i + 0.5D, (double)j, (double)k + 0.5D, 0.0F, 0.0F);
            catentity.onInitialSpawn(p_214821_1_, p_214821_1_.getDifficultyForLocation(new BlockPos(i, j, k)), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
            p_214821_1_.func_242417_l(catentity);
         }
      }

   }
}