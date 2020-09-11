package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.Property;
import net.minecraft.state.StateHolder;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.BlockVoxelShape;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.EmptyBlockReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//TODO, Delegates are weird here now, because Block extends this.
public abstract class AbstractBlock extends net.minecraftforge.registries.ForgeRegistryEntry<Block> {
   protected static final Direction[] UPDATE_ORDER = new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.DOWN, Direction.UP};
   protected final Material material;
   protected final boolean field_235688_at_;
   protected final float field_235689_au_;
   /**
    * Flags whether or not this block is of a type that needs random ticking. Ref-counted by ExtendedBlockStorage in
    * order to broadly cull a chunk from the random chunk update list for efficiency's sake.
    */
   protected final boolean ticksRandomly;
   protected final SoundType soundType;
   /** Determines how much velocity is maintained while moving on top of this block */
   protected final float slipperiness;
   protected final float speedFactor;
   protected final float jumpFactor;
   protected final boolean variableOpacity;
   protected final AbstractBlock.Properties field_235684_aB_;
   @Nullable
   protected ResourceLocation lootTable;

   public AbstractBlock(AbstractBlock.Properties p_i241196_1_) {
      this.material = p_i241196_1_.material;
      this.field_235688_at_ = p_i241196_1_.blocksMovement;
      this.lootTable = p_i241196_1_.lootTable;
      this.field_235689_au_ = p_i241196_1_.resistance;
      this.ticksRandomly = p_i241196_1_.ticksRandomly;
      this.soundType = p_i241196_1_.soundType;
      this.slipperiness = p_i241196_1_.slipperiness;
      this.speedFactor = p_i241196_1_.speedFactor;
      this.jumpFactor = p_i241196_1_.jumpFactor;
      this.variableOpacity = p_i241196_1_.variableOpacity;
      this.field_235684_aB_ = p_i241196_1_;
      final ResourceLocation lootTableCache = p_i241196_1_.lootTable;
      this.lootTableSupplier = lootTableCache != null ? () -> lootTableCache : p_i241196_1_.lootTableSupplier != null ? p_i241196_1_.lootTableSupplier : () -> new ResourceLocation(this.getRegistryName().getNamespace(), "blocks/" + this.getRegistryName().getPath());
   }

   /**
    * performs updates on diagonal neighbors of the target position and passes in the flags. The flags can be referenced
    * from the docs for {@link IWorldWriter#setBlockState(IBlockState, BlockPos, int)}.
    */
   @Deprecated
   public void updateDiagonalNeighbors(BlockState state, IWorld worldIn, BlockPos pos, int flags, int p_196248_5_) {
   }

   @Deprecated
   public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
      switch(type) {
      case LAND:
         return !state.func_235785_r_(worldIn, pos);
      case WATER:
         return worldIn.getFluidState(pos).isTagged(FluidTags.WATER);
      case AIR:
         return !state.func_235785_r_(worldIn, pos);
      default:
         return false;
      }
   }

   /**
    * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
    * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
    * returns its solidified counterpart.
    * Note that this method should ideally consider only the specific face passed in.
    */
   @Deprecated
   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      return stateIn;
   }

   @Deprecated
   @OnlyIn(Dist.CLIENT)
   public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
      return false;
   }

   @Deprecated
   public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      DebugPacketSender.func_218806_a(worldIn, pos);
   }

   @Deprecated
   public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
   }

   @Deprecated
   public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
      if (state.hasTileEntity() && (!state.isIn(newState.getBlock()) || !newState.hasTileEntity())) {
         worldIn.removeTileEntity(pos);
      }

   }

   @Deprecated
   public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
      return ActionResultType.PASS;
   }

   /**
    * Called on server when World#addBlockEvent is called. If server returns true, then also called on the client. On
    * the Server, this may perform additional changes to the world, like pistons replacing the block with an extended
    * base. On the client, the update may involve replacing tile entities or effects such as sounds or particles
    * @deprecated call via {@link IBlockState#onBlockEventReceived(World,BlockPos,int,int)} whenever possible.
    * Implementing/overriding is fine.
    */
   @Deprecated
   public boolean eventReceived(BlockState state, World worldIn, BlockPos pos, int id, int param) {
      return false;
   }

   /**
    * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
    * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
    * @deprecated call via {@link IBlockState#getRenderType()} whenever possible. Implementing/overriding is fine.
    */
   @Deprecated
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.MODEL;
   }

   @Deprecated
   public boolean isTransparent(BlockState state) {
      return false;
   }

   /**
    * Can this block provide power. Only wire currently seems to have this change based on its state.
    * @deprecated call via {@link IBlockState#canProvidePower()} whenever possible. Implementing/overriding is fine.
    */
   @Deprecated
   public boolean canProvidePower(BlockState state) {
      return false;
   }

   /**
    * @deprecated call via {@link IBlockState#getMobilityFlag()} whenever possible. Implementing/overriding is fine.
    */
   @Deprecated
   public PushReaction getPushReaction(BlockState state) {
      return this.material.getPushReaction();
   }

   @Deprecated
   public FluidState getFluidState(BlockState state) {
      return Fluids.EMPTY.getDefaultState();
   }

   /**
    * @deprecated call via {@link IBlockState#hasComparatorInputOverride()} whenever possible. Implementing/overriding
    * is fine.
    */
   @Deprecated
   public boolean hasComparatorInputOverride(BlockState state) {
      return false;
   }

   /**
    * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
    */
   public AbstractBlock.OffsetType getOffsetType() {
      return AbstractBlock.OffsetType.NONE;
   }

   /**
    * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
    * blockstate.
    * @deprecated call via {@link IBlockState#withRotation(Rotation)} whenever possible. Implementing/overriding is
    * fine.
    */
   @Deprecated
   public BlockState rotate(BlockState state, Rotation rot) {
      return state;
   }

   /**
    * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
    * blockstate.
    * @deprecated call via {@link IBlockState#withMirror(Mirror)} whenever possible. Implementing/overriding is fine.
    */
   @Deprecated
   public BlockState mirror(BlockState state, Mirror mirrorIn) {
      return state;
   }

   @Deprecated
   public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
      return state.getMaterial().isReplaceable() && (useContext.getItem().isEmpty() || useContext.getItem().getItem() != this.asItem());
   }

   @Deprecated
   public boolean isReplaceable(BlockState p_225541_1_, Fluid p_225541_2_) {
      return this.material.isReplaceable() || !this.material.isSolid();
   }

   @Deprecated
   public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
      ResourceLocation resourcelocation = this.getLootTable();
      if (resourcelocation == LootTables.EMPTY) {
         return Collections.emptyList();
      } else {
         LootContext lootcontext = builder.withParameter(LootParameters.BLOCK_STATE, state).build(LootParameterSets.BLOCK);
         ServerWorld serverworld = lootcontext.getWorld();
         LootTable loottable = serverworld.getServer().getLootTableManager().getLootTableFromLocation(resourcelocation);
         return loottable.generate(lootcontext);
      }
   }

   /**
    * Return a random long to be passed to {@link IBakedModel#getQuads}, used for random model rotations
    */
   @Deprecated
   @OnlyIn(Dist.CLIENT)
   public long getPositionRandom(BlockState state, BlockPos pos) {
      return MathHelper.getPositionRandom(pos);
   }

   @Deprecated
   public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return state.getShape(worldIn, pos);
   }

   @Deprecated
   public VoxelShape func_230335_e_(BlockState p_230335_1_, IBlockReader p_230335_2_, BlockPos p_230335_3_) {
      return this.getCollisionShape(p_230335_1_, p_230335_2_, p_230335_3_, ISelectionContext.dummy());
   }

   @Deprecated
   public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return VoxelShapes.empty();
   }

   @Deprecated
   public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
      if (state.isOpaqueCube(worldIn, pos)) {
         return worldIn.getMaxLightLevel();
      } else {
         return state.propagatesSkylightDown(worldIn, pos) ? 0 : 1;
      }
   }

   @Nullable
   @Deprecated
   public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
      return null;
   }

   @Deprecated
   public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
      return true;
   }

   @Deprecated
   @OnlyIn(Dist.CLIENT)
   public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return state.func_235785_r_(worldIn, pos) ? 0.2F : 1.0F;
   }

   /**
    * @deprecated call via {@link IBlockState#getComparatorInputOverride(World,BlockPos)} whenever possible.
    * Implementing/overriding is fine.
    */
   @Deprecated
   public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
      return 0;
   }

   @Deprecated
   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return VoxelShapes.fullCube();
   }

   @Deprecated
   public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return this.field_235688_at_ ? state.getShape(worldIn, pos) : VoxelShapes.empty();
   }

   @Deprecated
   public VoxelShape func_230322_a_(BlockState p_230322_1_, IBlockReader p_230322_2_, BlockPos p_230322_3_, ISelectionContext p_230322_4_) {
      return this.getCollisionShape(p_230322_1_, p_230322_2_, p_230322_3_, p_230322_4_);
   }

   /**
    * Performs a random tick on a block.
    */
   @Deprecated
   public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
      this.tick(state, worldIn, pos, random);
   }

   @Deprecated
   public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
   }

   /**
    * Get the hardness of this Block relative to the ability of the given player
    * @deprecated call via {@link IBlockState#getPlayerRelativeBlockHardness(EntityPlayer,World,BlockPos)} whenever
    * possible. Implementing/overriding is fine.
    */
   @Deprecated
   public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
      float f = state.getBlockHardness(worldIn, pos);
      if (f == -1.0F) {
         return 0.0F;
      } else {
         int i = net.minecraftforge.common.ForgeHooks.canHarvestBlock(state, player, worldIn, pos) ? 30 : 100;
         return player.getDigSpeed(state, pos) / f / (float)i;
      }
   }

   /**
    * Perform side-effects from block dropping, such as creating silverfish
    */
   @Deprecated
   public void spawnAdditionalDrops(BlockState state, ServerWorld worldIn, BlockPos pos, ItemStack stack) {
   }

   @Deprecated
   public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
   }

   /**
    * @deprecated call via {@link IBlockState#getWeakPower(IBlockAccess,BlockPos,EnumFacing)} whenever possible.
    * Implementing/overriding is fine.
    */
   @Deprecated
   public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
      return 0;
   }

   @Deprecated
   public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
   }

   /**
    * @deprecated call via {@link IBlockState#getStrongPower(IBlockAccess,BlockPos,EnumFacing)} whenever possible.
    * Implementing/overriding is fine.
    */
   @Deprecated
   public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
      return 0;
   }

   @Deprecated //Forge: Use state.hasTileEntity()
   public final boolean func_235695_q_() {
      return this instanceof ITileEntityProvider;
   }

   public final ResourceLocation getLootTable() {
      if (this.lootTable == null) {
         this.lootTable = this.lootTableSupplier.get();
      }

      return this.lootTable;
   }

   @Deprecated
   public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
   }

   public abstract Item asItem();

   protected abstract Block func_230328_p_();

   public MaterialColor func_235697_s_() {
      return this.field_235684_aB_.field_235800_b_.apply(this.func_230328_p_().getDefaultState());
   }

   /* ======================================== FORGE START ===================================== */
   private final java.util.function.Supplier<ResourceLocation> lootTableSupplier;
   /* ========================================= FORGE END ====================================== */

   public abstract static class AbstractBlockState extends StateHolder<Block, BlockState> {
      private final int lightLevel;
      private final boolean transparent;
      private final boolean field_235702_f_;
      private final Material field_235703_g_;
      private final MaterialColor field_235704_h_;
      private final float field_235705_i_;
      private final boolean field_235706_j_;
      private final boolean field_235707_k_;
      private final AbstractBlock.IPositionPredicate field_235708_l_;
      private final AbstractBlock.IPositionPredicate field_235709_m_;
      private final AbstractBlock.IPositionPredicate field_235710_n_;
      private final AbstractBlock.IPositionPredicate field_235711_o_;
      private final AbstractBlock.IPositionPredicate field_235712_p_;
      @Nullable
      protected AbstractBlock.AbstractBlockState.Cache cache;

      protected AbstractBlockState(Block p_i231870_1_, ImmutableMap<Property<?>, Comparable<?>> p_i231870_2_, MapCodec<BlockState> p_i231870_3_) {
         super(p_i231870_1_, p_i231870_2_, p_i231870_3_);
         AbstractBlock.Properties abstractblock$properties = p_i231870_1_.field_235684_aB_;
         this.lightLevel = abstractblock$properties.field_235803_e_.applyAsInt(this.func_230340_p_());
         this.transparent = p_i231870_1_.isTransparent(this.func_230340_p_());
         this.field_235702_f_ = abstractblock$properties.field_235813_o_;
         this.field_235703_g_ = abstractblock$properties.material;
         this.field_235704_h_ = abstractblock$properties.field_235800_b_.apply(this.func_230340_p_());
         this.field_235705_i_ = abstractblock$properties.hardness;
         this.field_235706_j_ = abstractblock$properties.field_235806_h_;
         this.field_235707_k_ = abstractblock$properties.isSolid;
         this.field_235708_l_ = abstractblock$properties.field_235815_q_;
         this.field_235709_m_ = abstractblock$properties.field_235816_r_;
         this.field_235710_n_ = abstractblock$properties.field_235817_s_;
         this.field_235711_o_ = abstractblock$properties.field_235818_t_;
         this.field_235712_p_ = abstractblock$properties.field_235819_u_;
      }

      public void cacheState() {
         if (!this.getBlock().isVariableOpacity()) {
            this.cache = new AbstractBlock.AbstractBlockState.Cache(this.func_230340_p_());
         }

      }

      public Block getBlock() {
         return this.field_235892_c_;
      }

      public Material getMaterial() {
         return this.field_235703_g_;
      }

      public boolean canEntitySpawn(IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
         return this.getBlock().field_235684_aB_.field_235814_p_.test(this.func_230340_p_(), worldIn, pos, type);
      }

      public boolean propagatesSkylightDown(IBlockReader worldIn, BlockPos pos) {
         return this.cache != null ? this.cache.propagatesSkylightDown : this.getBlock().propagatesSkylightDown(this.func_230340_p_(), worldIn, pos);
      }

      public int getOpacity(IBlockReader worldIn, BlockPos pos) {
         return this.cache != null ? this.cache.opacity : this.getBlock().getOpacity(this.func_230340_p_(), worldIn, pos);
      }

      public VoxelShape getFaceOcclusionShape(IBlockReader worldIn, BlockPos pos, Direction directionIn) {
         return this.cache != null && this.cache.renderShapes != null ? this.cache.renderShapes[directionIn.ordinal()] : VoxelShapes.getFaceShape(this.func_235754_c_(worldIn, pos), directionIn);
      }

      public VoxelShape func_235754_c_(IBlockReader p_235754_1_, BlockPos p_235754_2_) {
         return this.getBlock().getRenderShape(this.func_230340_p_(), p_235754_1_, p_235754_2_);
      }

      public boolean isCollisionShapeLargerThanFullBlock() {
         return this.cache == null || this.cache.isCollisionShapeLargerThanFullBlock;
      }

      public boolean isTransparent() {
         return this.transparent;
      }

      public int getLightValue() {
         return this.lightLevel;
      }

      /** @deprecated use {@link BlockState#isAir(IBlockReader, BlockPos) */
      @Deprecated
      public boolean isAir() {
         return this.field_235702_f_;
      }

      public MaterialColor getMaterialColor(IBlockReader worldIn, BlockPos pos) {
         return this.field_235704_h_;
      }

      /** @deprecated use {@link BlockState#rotate(IWorld, BlockPos, Rotation) */
      /**
       * Returns the blockstate with the given rotation. If inapplicable, returns itself.
       */
      @Deprecated
      public BlockState rotate(Rotation rot) {
         return this.getBlock().rotate(this.func_230340_p_(), rot);
      }

      /**
       * Returns the blockstate mirrored in the given way. If inapplicable, returns itself.
       */
      public BlockState mirror(Mirror mirrorIn) {
         return this.getBlock().mirror(this.func_230340_p_(), mirrorIn);
      }

      public BlockRenderType getRenderType() {
         return this.getBlock().getRenderType(this.func_230340_p_());
      }

      @OnlyIn(Dist.CLIENT)
      public boolean isEmissiveRendering(IBlockReader p_227035_1_, BlockPos p_227035_2_) {
         return this.field_235712_p_.test(this.func_230340_p_(), p_227035_1_, p_227035_2_);
      }

      @OnlyIn(Dist.CLIENT)
      public float getAmbientOcclusionLightValue(IBlockReader reader, BlockPos pos) {
         return this.getBlock().getAmbientOcclusionLightValue(this.func_230340_p_(), reader, pos);
      }

      public boolean isNormalCube(IBlockReader reader, BlockPos pos) {
         return this.field_235708_l_.test(this.func_230340_p_(), reader, pos);
      }

      public boolean canProvidePower() {
         return this.getBlock().canProvidePower(this.func_230340_p_());
      }

      public int getWeakPower(IBlockReader blockAccess, BlockPos pos, Direction side) {
         return this.getBlock().getWeakPower(this.func_230340_p_(), blockAccess, pos, side);
      }

      public boolean hasComparatorInputOverride() {
         return this.getBlock().hasComparatorInputOverride(this.func_230340_p_());
      }

      public int getComparatorInputOverride(World worldIn, BlockPos pos) {
         return this.getBlock().getComparatorInputOverride(this.func_230340_p_(), worldIn, pos);
      }

      public float getBlockHardness(IBlockReader worldIn, BlockPos pos) {
         return this.field_235705_i_;
      }

      public float getPlayerRelativeBlockHardness(PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
         return this.getBlock().getPlayerRelativeBlockHardness(this.func_230340_p_(), player, worldIn, pos);
      }

      public int getStrongPower(IBlockReader blockAccess, BlockPos pos, Direction side) {
         return this.getBlock().getStrongPower(this.func_230340_p_(), blockAccess, pos, side);
      }

      public PushReaction getPushReaction() {
         return this.getBlock().getPushReaction(this.func_230340_p_());
      }

      public boolean isOpaqueCube(IBlockReader worldIn, BlockPos pos) {
         if (this.cache != null) {
            return this.cache.opaqueCube;
         } else {
            BlockState blockstate = this.func_230340_p_();
            return blockstate.isSolid() ? Block.isOpaque(blockstate.func_235754_c_(worldIn, pos)) : false;
         }
      }

      public boolean isSolid() {
         return this.field_235707_k_;
      }

      @OnlyIn(Dist.CLIENT)
      public boolean isSideInvisible(BlockState state, Direction face) {
         return this.getBlock().isSideInvisible(this.func_230340_p_(), state, face);
      }

      public VoxelShape getShape(IBlockReader worldIn, BlockPos pos) {
         return this.getShape(worldIn, pos, ISelectionContext.dummy());
      }

      public VoxelShape getShape(IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
         return this.getBlock().getShape(this.func_230340_p_(), worldIn, pos, context);
      }

      public VoxelShape getCollisionShape(IBlockReader worldIn, BlockPos pos) {
         return this.cache != null ? this.cache.collisionShape : this.getCollisionShape(worldIn, pos, ISelectionContext.dummy());
      }

      public VoxelShape getCollisionShape(IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
         return this.getBlock().getCollisionShape(this.func_230340_p_(), worldIn, pos, context);
      }

      public VoxelShape getRenderShape(IBlockReader worldIn, BlockPos pos) {
         return this.getBlock().func_230335_e_(this.func_230340_p_(), worldIn, pos);
      }

      public VoxelShape getRaytraceShape(IBlockReader worldIn, BlockPos pos, ISelectionContext p_199611_3_) {
         return this.getBlock().func_230322_a_(this.func_230340_p_(), worldIn, pos, p_199611_3_);
      }

      public VoxelShape func_235777_m_(IBlockReader p_235777_1_, BlockPos p_235777_2_) {
         return this.getBlock().getRaytraceShape(this.func_230340_p_(), p_235777_1_, p_235777_2_);
      }

      public final boolean func_235719_a_(IBlockReader p_235719_1_, BlockPos p_235719_2_, Entity p_235719_3_) {
         return this.isTopSolid(p_235719_1_, p_235719_2_, p_235719_3_, Direction.UP);
      }

      /**
       * True if the collision box of this state covers the entire upper face of the blockspace
       */
      public final boolean isTopSolid(IBlockReader reader, BlockPos pos, Entity entityIn, Direction p_215682_4_) {
         return Block.doesSideFillSquare(this.getCollisionShape(reader, pos, ISelectionContext.forEntity(entityIn)), p_215682_4_);
      }

      public Vector3d getOffset(IBlockReader access, BlockPos pos) {
         AbstractBlock.OffsetType abstractblock$offsettype = this.getBlock().getOffsetType();
         if (abstractblock$offsettype == AbstractBlock.OffsetType.NONE) {
            return Vector3d.ZERO;
         } else {
            long i = MathHelper.getCoordinateRandom(pos.getX(), 0, pos.getZ());
            return new Vector3d(((double)((float)(i & 15L) / 15.0F) - 0.5D) * 0.5D, abstractblock$offsettype == AbstractBlock.OffsetType.XYZ ? ((double)((float)(i >> 4 & 15L) / 15.0F) - 1.0D) * 0.2D : 0.0D, ((double)((float)(i >> 8 & 15L) / 15.0F) - 0.5D) * 0.5D);
         }
      }

      public boolean func_235728_a_(World p_235728_1_, BlockPos p_235728_2_, int p_235728_3_, int p_235728_4_) {
         return this.getBlock().eventReceived(this.func_230340_p_(), p_235728_1_, p_235728_2_, p_235728_3_, p_235728_4_);
      }

      public void neighborChanged(World worldIn, BlockPos posIn, Block blockIn, BlockPos fromPosIn, boolean isMoving) {
         this.getBlock().neighborChanged(this.func_230340_p_(), worldIn, posIn, blockIn, fromPosIn, isMoving);
      }

      public final void func_235734_a_(IWorld p_235734_1_, BlockPos p_235734_2_, int p_235734_3_) {
         this.func_241482_a_(p_235734_1_, p_235734_2_, p_235734_3_, 512);
      }

      public final void func_241482_a_(IWorld p_241482_1_, BlockPos p_241482_2_, int p_241482_3_, int p_241482_4_) {
         this.getBlock();
         BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

         for(Direction direction : AbstractBlock.UPDATE_ORDER) {
            blockpos$mutable.func_239622_a_(p_241482_2_, direction);
            BlockState blockstate = p_241482_1_.getBlockState(blockpos$mutable);
            BlockState blockstate1 = blockstate.updatePostPlacement(direction.getOpposite(), this.func_230340_p_(), p_241482_1_, blockpos$mutable, p_241482_2_);
            Block.func_241468_a_(blockstate, blockstate1, p_241482_1_, blockpos$mutable, p_241482_3_, p_241482_4_);
         }

      }

      /**
       * Performs validations on the block state and possibly neighboring blocks to validate whether the incoming state
       * is valid to stay in the world. Currently used only by redstone wire to update itself if neighboring blocks have
       * changed and to possibly break itself.
       */
      public final void updateDiagonalNeighbors(IWorld worldIn, BlockPos pos, int flags) {
         this.func_241483_b_(worldIn, pos, flags, 512);
      }

      public void func_241483_b_(IWorld p_241483_1_, BlockPos p_241483_2_, int p_241483_3_, int p_241483_4_) {
         this.getBlock().updateDiagonalNeighbors(this.func_230340_p_(), p_241483_1_, p_241483_2_, p_241483_3_, p_241483_4_);
      }

      public void onBlockAdded(World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
         this.getBlock().onBlockAdded(this.func_230340_p_(), worldIn, pos, oldState, isMoving);
      }

      public void onReplaced(World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
         this.getBlock().onReplaced(this.func_230340_p_(), worldIn, pos, newState, isMoving);
      }

      public void tick(ServerWorld worldIn, BlockPos posIn, Random randomIn) {
         this.getBlock().tick(this.func_230340_p_(), worldIn, posIn, randomIn);
      }

      public void randomTick(ServerWorld worldIn, BlockPos posIn, Random randomIn) {
         this.getBlock().randomTick(this.func_230340_p_(), worldIn, posIn, randomIn);
      }

      public void onEntityCollision(World worldIn, BlockPos pos, Entity entityIn) {
         this.getBlock().onEntityCollision(this.func_230340_p_(), worldIn, pos, entityIn);
      }

      public void spawnAdditionalDrops(ServerWorld worldIn, BlockPos pos, ItemStack stack) {
         this.getBlock().spawnAdditionalDrops(this.func_230340_p_(), worldIn, pos, stack);
      }

      public List<ItemStack> getDrops(LootContext.Builder builder) {
         return this.getBlock().getDrops(this.func_230340_p_(), builder);
      }

      public ActionResultType onBlockActivated(World worldIn, PlayerEntity player, Hand handIn, BlockRayTraceResult resultIn) {
         return this.getBlock().onBlockActivated(this.func_230340_p_(), worldIn, resultIn.getPos(), player, handIn, resultIn);
      }

      public void onBlockClicked(World worldIn, BlockPos pos, PlayerEntity player) {
         this.getBlock().onBlockClicked(this.func_230340_p_(), worldIn, pos, player);
      }

      public boolean isSuffocating(IBlockReader blockReaderIn, BlockPos blockPosIn) {
         return this.field_235709_m_.test(this.func_230340_p_(), blockReaderIn, blockPosIn);
      }

      @OnlyIn(Dist.CLIENT)
      public boolean causesSuffocation(IBlockReader worldIn, BlockPos pos) {
         return this.field_235710_n_.test(this.func_230340_p_(), worldIn, pos);
      }

      public BlockState updatePostPlacement(Direction face, BlockState queried, IWorld worldIn, BlockPos currentPos, BlockPos offsetPos) {
         return this.getBlock().updatePostPlacement(this.func_230340_p_(), face, queried, worldIn, currentPos, offsetPos);
      }

      public boolean allowsMovement(IBlockReader worldIn, BlockPos pos, PathType type) {
         return this.getBlock().allowsMovement(this.func_230340_p_(), worldIn, pos, type);
      }

      public boolean isReplaceable(BlockItemUseContext useContext) {
         return this.getBlock().isReplaceable(this.func_230340_p_(), useContext);
      }

      public boolean isReplaceable(Fluid fluidIn) {
         return this.getBlock().isReplaceable(this.func_230340_p_(), fluidIn);
      }

      public boolean isValidPosition(IWorldReader worldIn, BlockPos pos) {
         return this.getBlock().isValidPosition(this.func_230340_p_(), worldIn, pos);
      }

      public boolean blockNeedsPostProcessing(IBlockReader worldIn, BlockPos pos) {
         return this.field_235711_o_.test(this.func_230340_p_(), worldIn, pos);
      }

      @Nullable
      public INamedContainerProvider getContainer(World worldIn, BlockPos pos) {
         return this.getBlock().getContainer(this.func_230340_p_(), worldIn, pos);
      }

      public boolean func_235714_a_(ITag<Block> p_235714_1_) {
         return this.getBlock().isIn(p_235714_1_);
      }

      public boolean func_235715_a_(ITag<Block> p_235715_1_, Predicate<AbstractBlock.AbstractBlockState> p_235715_2_) {
         return this.getBlock().isIn(p_235715_1_) && p_235715_2_.test(this);
      }

      public boolean isIn(Block tagIn) {
         return this.getBlock().func_235332_a_(tagIn);
      }

      public FluidState getFluidState() {
         return this.getBlock().getFluidState(this.func_230340_p_());
      }

      public boolean ticksRandomly() {
         return this.getBlock().ticksRandomly(this.func_230340_p_());
      }

      @OnlyIn(Dist.CLIENT)
      public long getPositionRandom(BlockPos pos) {
         return this.getBlock().getPositionRandom(this.func_230340_p_(), pos);
      }

      public SoundType getSoundType() {
         return this.getBlock().getSoundType(this.func_230340_p_());
      }

      public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
         this.getBlock().onProjectileCollision(worldIn, state, hit, projectile);
      }

      public boolean isSolidSide(IBlockReader blockReaderIn, BlockPos blockPosIn, Direction directionIn) {
         return this.func_242698_a(blockReaderIn, blockPosIn, directionIn, BlockVoxelShape.FULL);
      }

      public boolean func_242698_a(IBlockReader p_242698_1_, BlockPos p_242698_2_, Direction p_242698_3_, BlockVoxelShape p_242698_4_) {
         return this.cache != null ? this.cache.func_242700_a(p_242698_3_, p_242698_4_) : p_242698_4_.func_241854_a(this.func_230340_p_(), p_242698_1_, p_242698_2_, p_242698_3_);
      }

      public boolean func_235785_r_(IBlockReader p_235785_1_, BlockPos p_235785_2_) {
         return this.cache != null ? this.cache.opaqueCollisionShape : Block.isOpaque(this.getCollisionShape(p_235785_1_, p_235785_2_));
      }

      protected abstract BlockState func_230340_p_();

      public boolean func_235783_q_() {
         return this.field_235706_j_;
      }

      static final class Cache {
         private static final Direction[] DIRECTIONS = Direction.values();
         private static final int field_242699_f = BlockVoxelShape.values().length;
         protected final boolean opaqueCube;
         private final boolean propagatesSkylightDown;
         private final int opacity;
         @Nullable
         private final VoxelShape[] renderShapes;
         protected final VoxelShape collisionShape;
         protected final boolean isCollisionShapeLargerThanFullBlock;
         private final boolean[] solidSides;
         protected final boolean opaqueCollisionShape;

         private Cache(BlockState stateIn) {
            Block block = stateIn.getBlock();
            this.opaqueCube = stateIn.isOpaqueCube(EmptyBlockReader.INSTANCE, BlockPos.ZERO);
            this.propagatesSkylightDown = block.propagatesSkylightDown(stateIn, EmptyBlockReader.INSTANCE, BlockPos.ZERO);
            this.opacity = block.getOpacity(stateIn, EmptyBlockReader.INSTANCE, BlockPos.ZERO);
            if (!stateIn.isSolid()) {
               this.renderShapes = null;
            } else {
               this.renderShapes = new VoxelShape[DIRECTIONS.length];
               VoxelShape voxelshape = block.getRenderShape(stateIn, EmptyBlockReader.INSTANCE, BlockPos.ZERO);

               for(Direction direction : DIRECTIONS) {
                  this.renderShapes[direction.ordinal()] = VoxelShapes.getFaceShape(voxelshape, direction);
               }
            }

            this.collisionShape = block.getCollisionShape(stateIn, EmptyBlockReader.INSTANCE, BlockPos.ZERO, ISelectionContext.dummy());
            this.isCollisionShapeLargerThanFullBlock = Arrays.stream(Direction.Axis.values()).anyMatch((p_235796_1_) -> {
               return this.collisionShape.getStart(p_235796_1_) < 0.0D || this.collisionShape.getEnd(p_235796_1_) > 1.0D;
            });
            this.solidSides = new boolean[DIRECTIONS.length * field_242699_f];

            for(Direction direction1 : DIRECTIONS) {
               for(BlockVoxelShape blockvoxelshape : BlockVoxelShape.values()) {
                  this.solidSides[func_242701_b(direction1, blockvoxelshape)] = blockvoxelshape.func_241854_a(stateIn, EmptyBlockReader.INSTANCE, BlockPos.ZERO, direction1);
               }
            }

            this.opaqueCollisionShape = Block.isOpaque(stateIn.getCollisionShape(EmptyBlockReader.INSTANCE, BlockPos.ZERO));
         }

         public boolean func_242700_a(Direction p_242700_1_, BlockVoxelShape p_242700_2_) {
            return this.solidSides[func_242701_b(p_242700_1_, p_242700_2_)];
         }

         private static int func_242701_b(Direction p_242701_0_, BlockVoxelShape p_242701_1_) {
            return p_242701_0_.ordinal() * field_242699_f + p_242701_1_.ordinal();
         }
      }
   }

   public interface IExtendedPositionPredicate<A> {
      boolean test(BlockState p_test_1_, IBlockReader p_test_2_, BlockPos p_test_3_, A p_test_4_);
   }

   public interface IPositionPredicate {
      boolean test(BlockState p_test_1_, IBlockReader p_test_2_, BlockPos p_test_3_);
   }

   public static enum OffsetType {
      NONE,
      XZ,
      XYZ;
   }

   public static class Properties {
      private Material material;
      private Function<BlockState, MaterialColor> field_235800_b_;
      private boolean blocksMovement = true;
      private SoundType soundType = SoundType.STONE;
      private ToIntFunction<BlockState> field_235803_e_ = (p_235830_0_) -> {
         return 0;
      };
      private float resistance;
      private float hardness;
      private boolean field_235806_h_;
      private boolean ticksRandomly;
      private float slipperiness = 0.6F;
      private float speedFactor = 1.0F;
      private float jumpFactor = 1.0F;
      /** Sets loot table information */
      private ResourceLocation lootTable;
      private boolean isSolid = true;
      private boolean field_235813_o_;
      private int harvestLevel = -1;
      private net.minecraftforge.common.ToolType harvestTool;
      private java.util.function.Supplier<ResourceLocation> lootTableSupplier;
      private AbstractBlock.IExtendedPositionPredicate<EntityType<?>> field_235814_p_ = (p_235832_0_, p_235832_1_, p_235832_2_, p_235832_3_) -> {
         return p_235832_0_.isSolidSide(p_235832_1_, p_235832_2_, Direction.UP) && p_235832_0_.getLightValue() < 14;
      };
      private AbstractBlock.IPositionPredicate field_235815_q_ = (p_235853_0_, p_235853_1_, p_235853_2_) -> {
         return p_235853_0_.getMaterial().isOpaque() && p_235853_0_.func_235785_r_(p_235853_1_, p_235853_2_);
      };
      private AbstractBlock.IPositionPredicate field_235816_r_ = (p_235848_1_, p_235848_2_, p_235848_3_) -> {
         return this.material.blocksMovement() && p_235848_1_.func_235785_r_(p_235848_2_, p_235848_3_);
      };
      private AbstractBlock.IPositionPredicate field_235817_s_ = this.field_235816_r_;
      private AbstractBlock.IPositionPredicate field_235818_t_ = (p_235843_0_, p_235843_1_, p_235843_2_) -> {
         return false;
      };
      private AbstractBlock.IPositionPredicate field_235819_u_ = (p_235831_0_, p_235831_1_, p_235831_2_) -> {
         return false;
      };
      private boolean variableOpacity;

      private Properties(Material materialIn, MaterialColor mapColorIn) {
         this(materialIn, (p_235837_1_) -> {
            return mapColorIn;
         });
      }

      private Properties(Material p_i241199_1_, Function<BlockState, MaterialColor> p_i241199_2_) {
         this.material = p_i241199_1_;
         this.field_235800_b_ = p_i241199_2_;
      }

      public static AbstractBlock.Properties create(Material materialIn) {
         return create(materialIn, materialIn.getColor());
      }

      public static AbstractBlock.Properties create(Material materialIn, DyeColor color) {
         return create(materialIn, color.getMapColor());
      }

      public static AbstractBlock.Properties create(Material materialIn, MaterialColor mapColorIn) {
         return new AbstractBlock.Properties(materialIn, mapColorIn);
      }

      public static AbstractBlock.Properties func_235836_a_(Material p_235836_0_, Function<BlockState, MaterialColor> p_235836_1_) {
         return new AbstractBlock.Properties(p_235836_0_, p_235836_1_);
      }

      public static AbstractBlock.Properties from(AbstractBlock blockIn) {
         AbstractBlock.Properties abstractblock$properties = new AbstractBlock.Properties(blockIn.material, blockIn.field_235684_aB_.field_235800_b_);
         abstractblock$properties.material = blockIn.field_235684_aB_.material;
         abstractblock$properties.hardness = blockIn.field_235684_aB_.hardness;
         abstractblock$properties.resistance = blockIn.field_235684_aB_.resistance;
         abstractblock$properties.blocksMovement = blockIn.field_235684_aB_.blocksMovement;
         abstractblock$properties.ticksRandomly = blockIn.field_235684_aB_.ticksRandomly;
         abstractblock$properties.field_235803_e_ = blockIn.field_235684_aB_.field_235803_e_;
         abstractblock$properties.field_235800_b_ = blockIn.field_235684_aB_.field_235800_b_;
         abstractblock$properties.soundType = blockIn.field_235684_aB_.soundType;
         abstractblock$properties.slipperiness = blockIn.field_235684_aB_.slipperiness;
         abstractblock$properties.speedFactor = blockIn.field_235684_aB_.speedFactor;
         abstractblock$properties.variableOpacity = blockIn.field_235684_aB_.variableOpacity;
         abstractblock$properties.isSolid = blockIn.field_235684_aB_.isSolid;
         abstractblock$properties.field_235813_o_ = blockIn.field_235684_aB_.field_235813_o_;
         abstractblock$properties.field_235806_h_ = blockIn.field_235684_aB_.field_235806_h_;
         abstractblock$properties.harvestLevel = blockIn.field_235684_aB_.harvestLevel;
         abstractblock$properties.harvestTool = blockIn.field_235684_aB_.harvestTool;
         return abstractblock$properties;
      }

      public AbstractBlock.Properties doesNotBlockMovement() {
         this.blocksMovement = false;
         this.isSolid = false;
         return this;
      }

      public AbstractBlock.Properties notSolid() {
         this.isSolid = false;
         return this;
      }

      public AbstractBlock.Properties harvestLevel(int harvestLevel) {
         this.harvestLevel = harvestLevel;
         return this;
      }

      public AbstractBlock.Properties harvestTool(net.minecraftforge.common.ToolType harvestTool) {
         this.harvestTool = harvestTool;
         return this;
      }

      public int getHarvestLevel() {
         return this.harvestLevel;
      }

      public net.minecraftforge.common.ToolType getHarvestTool() {
         return this.harvestTool;
      }

      public AbstractBlock.Properties slipperiness(float slipperinessIn) {
         this.slipperiness = slipperinessIn;
         return this;
      }

      public AbstractBlock.Properties speedFactor(float factor) {
         this.speedFactor = factor;
         return this;
      }

      public AbstractBlock.Properties jumpFactor(float factor) {
         this.jumpFactor = factor;
         return this;
      }

      public AbstractBlock.Properties sound(SoundType soundTypeIn) {
         this.soundType = soundTypeIn;
         return this;
      }

      public AbstractBlock.Properties func_235838_a_(ToIntFunction<BlockState> p_235838_1_) {
         this.field_235803_e_ = p_235838_1_;
         return this;
      }

      public AbstractBlock.Properties hardnessAndResistance(float hardnessIn, float resistanceIn) {
         this.hardness = hardnessIn;
         this.resistance = Math.max(0.0F, resistanceIn);
         return this;
      }

      public AbstractBlock.Properties zeroHardnessAndResistance() {
         return this.hardnessAndResistance(0.0F);
      }

      public AbstractBlock.Properties hardnessAndResistance(float hardnessAndResistance) {
         this.hardnessAndResistance(hardnessAndResistance, hardnessAndResistance);
         return this;
      }

      public AbstractBlock.Properties tickRandomly() {
         this.ticksRandomly = true;
         return this;
      }

      public AbstractBlock.Properties variableOpacity() {
         this.variableOpacity = true;
         return this;
      }

      public AbstractBlock.Properties noDrops() {
         this.lootTable = LootTables.EMPTY;
         return this;
      }

      public AbstractBlock.Properties lootFrom(Block blockIn) {
         this.lootTableSupplier = () -> blockIn.delegate.get().getLootTable();
         return this;
      }

      public AbstractBlock.Properties func_235859_g_() {
         this.field_235813_o_ = true;
         return this;
      }

      public AbstractBlock.Properties func_235827_a_(AbstractBlock.IExtendedPositionPredicate<EntityType<?>> p_235827_1_) {
         this.field_235814_p_ = p_235827_1_;
         return this;
      }

      public AbstractBlock.Properties func_235828_a_(AbstractBlock.IPositionPredicate p_235828_1_) {
         this.field_235815_q_ = p_235828_1_;
         return this;
      }

      public AbstractBlock.Properties func_235842_b_(AbstractBlock.IPositionPredicate p_235842_1_) {
         this.field_235816_r_ = p_235842_1_;
         return this;
      }

      public AbstractBlock.Properties func_235847_c_(AbstractBlock.IPositionPredicate p_235847_1_) {
         this.field_235817_s_ = p_235847_1_;
         return this;
      }

      public AbstractBlock.Properties func_235852_d_(AbstractBlock.IPositionPredicate p_235852_1_) {
         this.field_235818_t_ = p_235852_1_;
         return this;
      }

      public AbstractBlock.Properties func_235856_e_(AbstractBlock.IPositionPredicate p_235856_1_) {
         this.field_235819_u_ = p_235856_1_;
         return this;
      }

      public AbstractBlock.Properties func_235861_h_() {
         this.field_235806_h_ = true;
         return this;
      }
   }
}