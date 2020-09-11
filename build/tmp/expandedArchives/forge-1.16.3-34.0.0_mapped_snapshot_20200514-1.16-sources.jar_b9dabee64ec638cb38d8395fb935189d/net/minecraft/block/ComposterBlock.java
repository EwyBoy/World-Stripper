package net.minecraft.block;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ISidedInventoryProvider;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ComposterBlock extends Block implements ISidedInventoryProvider {
   public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_0_8;
   public static final Object2FloatMap<IItemProvider> CHANCES = new Object2FloatOpenHashMap<>();
   private static final VoxelShape field_220300_c = VoxelShapes.fullCube();
   private static final VoxelShape[] field_220301_d = Util.make(new VoxelShape[9], (p_220291_0_) -> {
      for(int i = 0; i < 8; ++i) {
         p_220291_0_[i] = VoxelShapes.combineAndSimplify(field_220300_c, Block.makeCuboidShape(2.0D, (double)Math.max(2, 1 + i * 2), 2.0D, 14.0D, 16.0D, 14.0D), IBooleanFunction.ONLY_FIRST);
      }

      p_220291_0_[8] = p_220291_0_[7];
   });

   public static void init() {
      CHANCES.defaultReturnValue(-1.0F);
      float f = 0.3F;
      float f1 = 0.5F;
      float f2 = 0.65F;
      float f3 = 0.85F;
      float f4 = 1.0F;
      registerCompostable(0.3F, Items.JUNGLE_LEAVES);
      registerCompostable(0.3F, Items.OAK_LEAVES);
      registerCompostable(0.3F, Items.SPRUCE_LEAVES);
      registerCompostable(0.3F, Items.DARK_OAK_LEAVES);
      registerCompostable(0.3F, Items.ACACIA_LEAVES);
      registerCompostable(0.3F, Items.BIRCH_LEAVES);
      registerCompostable(0.3F, Items.OAK_SAPLING);
      registerCompostable(0.3F, Items.SPRUCE_SAPLING);
      registerCompostable(0.3F, Items.BIRCH_SAPLING);
      registerCompostable(0.3F, Items.JUNGLE_SAPLING);
      registerCompostable(0.3F, Items.ACACIA_SAPLING);
      registerCompostable(0.3F, Items.DARK_OAK_SAPLING);
      registerCompostable(0.3F, Items.BEETROOT_SEEDS);
      registerCompostable(0.3F, Items.DRIED_KELP);
      registerCompostable(0.3F, Items.GRASS);
      registerCompostable(0.3F, Items.KELP);
      registerCompostable(0.3F, Items.MELON_SEEDS);
      registerCompostable(0.3F, Items.PUMPKIN_SEEDS);
      registerCompostable(0.3F, Items.SEAGRASS);
      registerCompostable(0.3F, Items.SWEET_BERRIES);
      registerCompostable(0.3F, Items.WHEAT_SEEDS);
      registerCompostable(0.5F, Items.DRIED_KELP_BLOCK);
      registerCompostable(0.5F, Items.TALL_GRASS);
      registerCompostable(0.5F, Items.CACTUS);
      registerCompostable(0.5F, Items.SUGAR_CANE);
      registerCompostable(0.5F, Items.VINE);
      registerCompostable(0.5F, Items.field_234717_bA_);
      registerCompostable(0.5F, Items.field_234718_bB_);
      registerCompostable(0.5F, Items.field_234719_bC_);
      registerCompostable(0.5F, Items.MELON_SLICE);
      registerCompostable(0.65F, Items.SEA_PICKLE);
      registerCompostable(0.65F, Items.LILY_PAD);
      registerCompostable(0.65F, Items.PUMPKIN);
      registerCompostable(0.65F, Items.CARVED_PUMPKIN);
      registerCompostable(0.65F, Items.MELON);
      registerCompostable(0.65F, Items.APPLE);
      registerCompostable(0.65F, Items.BEETROOT);
      registerCompostable(0.65F, Items.CARROT);
      registerCompostable(0.65F, Items.COCOA_BEANS);
      registerCompostable(0.65F, Items.POTATO);
      registerCompostable(0.65F, Items.WHEAT);
      registerCompostable(0.65F, Items.BROWN_MUSHROOM);
      registerCompostable(0.65F, Items.RED_MUSHROOM);
      registerCompostable(0.65F, Items.MUSHROOM_STEM);
      registerCompostable(0.65F, Items.field_234722_bw_);
      registerCompostable(0.65F, Items.field_234723_bx_);
      registerCompostable(0.65F, Items.NETHER_WART);
      registerCompostable(0.65F, Items.field_234724_by_);
      registerCompostable(0.65F, Items.field_234725_bz_);
      registerCompostable(0.65F, Items.field_234792_ro_);
      registerCompostable(0.65F, Items.DANDELION);
      registerCompostable(0.65F, Items.POPPY);
      registerCompostable(0.65F, Items.BLUE_ORCHID);
      registerCompostable(0.65F, Items.ALLIUM);
      registerCompostable(0.65F, Items.AZURE_BLUET);
      registerCompostable(0.65F, Items.RED_TULIP);
      registerCompostable(0.65F, Items.ORANGE_TULIP);
      registerCompostable(0.65F, Items.WHITE_TULIP);
      registerCompostable(0.65F, Items.PINK_TULIP);
      registerCompostable(0.65F, Items.OXEYE_DAISY);
      registerCompostable(0.65F, Items.CORNFLOWER);
      registerCompostable(0.65F, Items.LILY_OF_THE_VALLEY);
      registerCompostable(0.65F, Items.WITHER_ROSE);
      registerCompostable(0.65F, Items.FERN);
      registerCompostable(0.65F, Items.SUNFLOWER);
      registerCompostable(0.65F, Items.LILAC);
      registerCompostable(0.65F, Items.ROSE_BUSH);
      registerCompostable(0.65F, Items.PEONY);
      registerCompostable(0.65F, Items.LARGE_FERN);
      registerCompostable(0.85F, Items.HAY_BLOCK);
      registerCompostable(0.85F, Items.BROWN_MUSHROOM_BLOCK);
      registerCompostable(0.85F, Items.RED_MUSHROOM_BLOCK);
      registerCompostable(0.85F, Items.NETHER_WART_BLOCK);
      registerCompostable(0.85F, Items.field_234751_hk_);
      registerCompostable(0.85F, Items.BREAD);
      registerCompostable(0.85F, Items.BAKED_POTATO);
      registerCompostable(0.85F, Items.COOKIE);
      registerCompostable(1.0F, Items.CAKE);
      registerCompostable(1.0F, Items.PUMPKIN_PIE);
   }

   private static void registerCompostable(float chance, IItemProvider itemIn) {
      CHANCES.put(itemIn.asItem(), chance);
   }

   public ComposterBlock(AbstractBlock.Properties properties) {
      super(properties);
      this.setDefaultState(this.stateContainer.getBaseState().with(LEVEL, Integer.valueOf(0)));
   }

   @OnlyIn(Dist.CLIENT)
   public static void playEvent(World p_220292_0_, BlockPos p_220292_1_, boolean p_220292_2_) {
      BlockState blockstate = p_220292_0_.getBlockState(p_220292_1_);
      p_220292_0_.playSound((double)p_220292_1_.getX(), (double)p_220292_1_.getY(), (double)p_220292_1_.getZ(), p_220292_2_ ? SoundEvents.BLOCK_COMPOSTER_FILL_SUCCESS : SoundEvents.BLOCK_COMPOSTER_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
      double d0 = blockstate.getShape(p_220292_0_, p_220292_1_).max(Direction.Axis.Y, 0.5D, 0.5D) + 0.03125D;
      double d1 = (double)0.13125F;
      double d2 = (double)0.7375F;
      Random random = p_220292_0_.getRandom();

      for(int i = 0; i < 10; ++i) {
         double d3 = random.nextGaussian() * 0.02D;
         double d4 = random.nextGaussian() * 0.02D;
         double d5 = random.nextGaussian() * 0.02D;
         p_220292_0_.addParticle(ParticleTypes.COMPOSTER, (double)p_220292_1_.getX() + (double)0.13125F + (double)0.7375F * (double)random.nextFloat(), (double)p_220292_1_.getY() + d0 + (double)random.nextFloat() * (1.0D - d0), (double)p_220292_1_.getZ() + (double)0.13125F + (double)0.7375F * (double)random.nextFloat(), d3, d4, d5);
      }

   }

   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return field_220301_d[state.get(LEVEL)];
   }

   public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return field_220300_c;
   }

   public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return field_220301_d[0];
   }

   public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
      if (state.get(LEVEL) == 7) {
         worldIn.getPendingBlockTicks().scheduleTick(pos, state.getBlock(), 20);
      }

   }

   public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
      int i = state.get(LEVEL);
      ItemStack itemstack = player.getHeldItem(handIn);
      if (i < 8 && CHANCES.containsKey(itemstack.getItem())) {
         if (i < 7 && !worldIn.isRemote) {
            BlockState blockstate = func_235487_b_(state, worldIn, pos, itemstack);
            worldIn.playEvent(1500, pos, state != blockstate ? 1 : 0);
            if (!player.abilities.isCreativeMode) {
               itemstack.shrink(1);
            }
         }

         return ActionResultType.func_233537_a_(worldIn.isRemote);
      } else if (i == 8) {
         func_235489_d_(state, worldIn, pos);
         return ActionResultType.func_233537_a_(worldIn.isRemote);
      } else {
         return ActionResultType.PASS;
      }
   }

   public static BlockState func_235486_a_(BlockState p_235486_0_, ServerWorld p_235486_1_, ItemStack p_235486_2_, BlockPos p_235486_3_) {
      int i = p_235486_0_.get(LEVEL);
      if (i < 7 && CHANCES.containsKey(p_235486_2_.getItem())) {
         BlockState blockstate = func_235487_b_(p_235486_0_, p_235486_1_, p_235486_3_, p_235486_2_);
         p_235486_2_.shrink(1);
         return blockstate;
      } else {
         return p_235486_0_;
      }
   }

   public static BlockState func_235489_d_(BlockState p_235489_0_, World p_235489_1_, BlockPos p_235489_2_) {
      if (!p_235489_1_.isRemote) {
         float f = 0.7F;
         double d0 = (double)(p_235489_1_.rand.nextFloat() * 0.7F) + (double)0.15F;
         double d1 = (double)(p_235489_1_.rand.nextFloat() * 0.7F) + (double)0.060000002F + 0.6D;
         double d2 = (double)(p_235489_1_.rand.nextFloat() * 0.7F) + (double)0.15F;
         ItemEntity itementity = new ItemEntity(p_235489_1_, (double)p_235489_2_.getX() + d0, (double)p_235489_2_.getY() + d1, (double)p_235489_2_.getZ() + d2, new ItemStack(Items.BONE_MEAL));
         itementity.setDefaultPickupDelay();
         p_235489_1_.addEntity(itementity);
      }

      BlockState blockstate = func_235490_d_(p_235489_0_, p_235489_1_, p_235489_2_);
      p_235489_1_.playSound((PlayerEntity)null, p_235489_2_, SoundEvents.BLOCK_COMPOSTER_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
      return blockstate;
   }

   private static BlockState func_235490_d_(BlockState p_235490_0_, IWorld p_235490_1_, BlockPos p_235490_2_) {
      BlockState blockstate = p_235490_0_.with(LEVEL, Integer.valueOf(0));
      p_235490_1_.setBlockState(p_235490_2_, blockstate, 3);
      return blockstate;
   }

   private static BlockState func_235487_b_(BlockState p_235487_0_, IWorld p_235487_1_, BlockPos p_235487_2_, ItemStack p_235487_3_) {
      int i = p_235487_0_.get(LEVEL);
      float f = CHANCES.getFloat(p_235487_3_.getItem());
      if ((i != 0 || !(f > 0.0F)) && !(p_235487_1_.getRandom().nextDouble() < (double)f)) {
         return p_235487_0_;
      } else {
         int j = i + 1;
         BlockState blockstate = p_235487_0_.with(LEVEL, Integer.valueOf(j));
         p_235487_1_.setBlockState(p_235487_2_, blockstate, 3);
         if (j == 7) {
            p_235487_1_.getPendingBlockTicks().scheduleTick(p_235487_2_, p_235487_0_.getBlock(), 20);
         }

         return blockstate;
      }
   }

   public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
      if (state.get(LEVEL) == 7) {
         worldIn.setBlockState(pos, state.func_235896_a_(LEVEL), 3);
         worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_COMPOSTER_READY, SoundCategory.BLOCKS, 1.0F, 1.0F);
      }

   }

   /**
    * @deprecated call via {@link IBlockState#hasComparatorInputOverride()} whenever possible. Implementing/overriding
    * is fine.
    */
   public boolean hasComparatorInputOverride(BlockState state) {
      return true;
   }

   /**
    * @deprecated call via {@link IBlockState#getComparatorInputOverride(World,BlockPos)} whenever possible.
    * Implementing/overriding is fine.
    */
   public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
      return blockState.get(LEVEL);
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(LEVEL);
   }

   public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
      return false;
   }

   public ISidedInventory createInventory(BlockState p_219966_1_, IWorld p_219966_2_, BlockPos p_219966_3_) {
      int i = p_219966_1_.get(LEVEL);
      if (i == 8) {
         return new ComposterBlock.FullInventory(p_219966_1_, p_219966_2_, p_219966_3_, new ItemStack(Items.BONE_MEAL));
      } else {
         return (ISidedInventory)(i < 7 ? new ComposterBlock.PartialInventory(p_219966_1_, p_219966_2_, p_219966_3_) : new ComposterBlock.EmptyInventory());
      }
   }

   static class EmptyInventory extends Inventory implements ISidedInventory {
      public EmptyInventory() {
         super(0);
      }

      public int[] getSlotsForFace(Direction side) {
         return new int[0];
      }

      /**
       * Returns true if automation can insert the given item in the given slot from the given side.
       */
      public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
         return false;
      }

      /**
       * Returns true if automation can extract the given item in the given slot from the given side.
       */
      public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
         return false;
      }
   }

   static class FullInventory extends Inventory implements ISidedInventory {
      private final BlockState state;
      private final IWorld world;
      private final BlockPos pos;
      private boolean extracted;

      public FullInventory(BlockState p_i50463_1_, IWorld p_i50463_2_, BlockPos p_i50463_3_, ItemStack p_i50463_4_) {
         super(p_i50463_4_);
         this.state = p_i50463_1_;
         this.world = p_i50463_2_;
         this.pos = p_i50463_3_;
      }

      /**
       * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
       */
      public int getInventoryStackLimit() {
         return 1;
      }

      public int[] getSlotsForFace(Direction side) {
         return side == Direction.DOWN ? new int[]{0} : new int[0];
      }

      /**
       * Returns true if automation can insert the given item in the given slot from the given side.
       */
      public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
         return false;
      }

      /**
       * Returns true if automation can extract the given item in the given slot from the given side.
       */
      public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
         return !this.extracted && direction == Direction.DOWN && stack.getItem() == Items.BONE_MEAL;
      }

      /**
       * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think
       * it hasn't changed and skip it.
       */
      public void markDirty() {
         ComposterBlock.func_235490_d_(this.state, this.world, this.pos);
         this.extracted = true;
      }
   }

   static class PartialInventory extends Inventory implements ISidedInventory {
      private final BlockState state;
      private final IWorld world;
      private final BlockPos pos;
      private boolean inserted;

      public PartialInventory(BlockState p_i50464_1_, IWorld p_i50464_2_, BlockPos p_i50464_3_) {
         super(1);
         this.state = p_i50464_1_;
         this.world = p_i50464_2_;
         this.pos = p_i50464_3_;
      }

      /**
       * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
       */
      public int getInventoryStackLimit() {
         return 1;
      }

      public int[] getSlotsForFace(Direction side) {
         return side == Direction.UP ? new int[]{0} : new int[0];
      }

      /**
       * Returns true if automation can insert the given item in the given slot from the given side.
       */
      public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
         return !this.inserted && direction == Direction.UP && ComposterBlock.CHANCES.containsKey(itemStackIn.getItem());
      }

      /**
       * Returns true if automation can extract the given item in the given slot from the given side.
       */
      public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
         return false;
      }

      /**
       * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think
       * it hasn't changed and skip it.
       */
      public void markDirty() {
         ItemStack itemstack = this.getStackInSlot(0);
         if (!itemstack.isEmpty()) {
            this.inserted = true;
            BlockState blockstate = ComposterBlock.func_235487_b_(this.state, this.world, this.pos, itemstack);
            this.world.playEvent(1500, this.pos, blockstate != this.state ? 1 : 0);
            this.removeStackFromSlot(0);
         }

      }
   }
}