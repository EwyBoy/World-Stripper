package net.minecraft.world.gen;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.DimensionType;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.ITickList;
import net.minecraft.world.WorldGenTickList;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.IWorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldGenRegion implements ISeedReader {
   private static final Logger LOGGER = LogManager.getLogger();
   private final List<IChunk> chunkPrimers;
   private final int mainChunkX;
   private final int mainChunkZ;
   private final int field_217380_e;
   private final ServerWorld world;
   private final long seed;
   private final IWorldInfo worldInfo;
   private final Random random;
   private final DimensionType field_241159_j_;
   private final ITickList<Block> pendingBlockTickList = new WorldGenTickList<>((p_205335_1_) -> {
      return this.getChunk(p_205335_1_).getBlocksToBeTicked();
   });
   private final ITickList<Fluid> pendingFluidTickList = new WorldGenTickList<>((p_205334_1_) -> {
      return this.getChunk(p_205334_1_).getFluidsToBeTicked();
   });
   private final BiomeManager biomeManager;
   private final ChunkPos field_241160_n_;
   private final ChunkPos field_241161_o_;

   public WorldGenRegion(ServerWorld p_i50698_1_, List<IChunk> p_i50698_2_) {
      int i = MathHelper.floor(Math.sqrt((double)p_i50698_2_.size()));
      if (i * i != p_i50698_2_.size()) {
         throw (IllegalStateException)Util.pauseDevMode(new IllegalStateException("Cache size is not a square."));
      } else {
         ChunkPos chunkpos = p_i50698_2_.get(p_i50698_2_.size() / 2).getPos();
         this.chunkPrimers = p_i50698_2_;
         this.mainChunkX = chunkpos.x;
         this.mainChunkZ = chunkpos.z;
         this.field_217380_e = i;
         this.world = p_i50698_1_;
         this.seed = p_i50698_1_.getSeed();
         this.worldInfo = p_i50698_1_.getWorldInfo();
         this.random = p_i50698_1_.getRandom();
         this.field_241159_j_ = p_i50698_1_.func_230315_m_();
         this.biomeManager = new BiomeManager(this, BiomeManager.func_235200_a_(this.seed), p_i50698_1_.func_230315_m_().getMagnifier());
         this.field_241160_n_ = p_i50698_2_.get(0).getPos();
         this.field_241161_o_ = p_i50698_2_.get(p_i50698_2_.size() - 1).getPos();
      }
   }

   public int getMainChunkX() {
      return this.mainChunkX;
   }

   public int getMainChunkZ() {
      return this.mainChunkZ;
   }

   public IChunk getChunk(int chunkX, int chunkZ) {
      return this.getChunk(chunkX, chunkZ, ChunkStatus.EMPTY);
   }

   @Nullable
   public IChunk getChunk(int x, int z, ChunkStatus requiredStatus, boolean nonnull) {
      IChunk ichunk;
      if (this.chunkExists(x, z)) {
         int i = x - this.field_241160_n_.x;
         int j = z - this.field_241160_n_.z;
         ichunk = this.chunkPrimers.get(i + j * this.field_217380_e);
         if (ichunk.getStatus().isAtLeast(requiredStatus)) {
            return ichunk;
         }
      } else {
         ichunk = null;
      }

      if (!nonnull) {
         return null;
      } else {
         LOGGER.error("Requested chunk : {} {}", x, z);
         LOGGER.error("Region bounds : {} {} | {} {}", this.field_241160_n_.x, this.field_241160_n_.z, this.field_241161_o_.x, this.field_241161_o_.z);
         if (ichunk != null) {
            throw (RuntimeException)Util.pauseDevMode(new RuntimeException(String.format("Chunk is not of correct status. Expecting %s, got %s | %s %s", requiredStatus, ichunk.getStatus(), x, z)));
         } else {
            throw (RuntimeException)Util.pauseDevMode(new RuntimeException(String.format("We are asking a region for a chunk out of bound | %s %s", x, z)));
         }
      }
   }

   public boolean chunkExists(int chunkX, int chunkZ) {
      return chunkX >= this.field_241160_n_.x && chunkX <= this.field_241161_o_.x && chunkZ >= this.field_241160_n_.z && chunkZ <= this.field_241161_o_.z;
   }

   public BlockState getBlockState(BlockPos pos) {
      return this.getChunk(pos.getX() >> 4, pos.getZ() >> 4).getBlockState(pos);
   }

   public FluidState getFluidState(BlockPos pos) {
      return this.getChunk(pos).getFluidState(pos);
   }

   @Nullable
   public PlayerEntity getClosestPlayer(double x, double y, double z, double distance, Predicate<Entity> predicate) {
      return null;
   }

   public int getSkylightSubtracted() {
      return 0;
   }

   public BiomeManager getBiomeManager() {
      return this.biomeManager;
   }

   public Biome getNoiseBiomeRaw(int x, int y, int z) {
      return this.world.getNoiseBiomeRaw(x, y, z);
   }

   @OnlyIn(Dist.CLIENT)
   public float func_230487_a_(Direction p_230487_1_, boolean p_230487_2_) {
      return 1.0F;
   }

   public WorldLightManager getLightManager() {
      return this.world.getLightManager();
   }

   public boolean func_241212_a_(BlockPos p_241212_1_, boolean p_241212_2_, @Nullable Entity p_241212_3_, int p_241212_4_) {
      BlockState blockstate = this.getBlockState(p_241212_1_);
      if (blockstate.isAir(this, p_241212_1_)) {
         return false;
      } else {
         if (p_241212_2_) {
            TileEntity tileentity = blockstate.hasTileEntity() ? this.getTileEntity(p_241212_1_) : null;
            Block.spawnDrops(blockstate, this.world, p_241212_1_, tileentity, p_241212_3_, ItemStack.EMPTY);
         }

         return this.func_241211_a_(p_241212_1_, Blocks.AIR.getDefaultState(), 3, p_241212_4_);
      }
   }

   @Nullable
   public TileEntity getTileEntity(BlockPos pos) {
      IChunk ichunk = this.getChunk(pos);
      TileEntity tileentity = ichunk.getTileEntity(pos);
      if (tileentity != null) {
         return tileentity;
      } else {
         CompoundNBT compoundnbt = ichunk.getDeferredTileEntity(pos);
         BlockState blockstate = ichunk.getBlockState(pos);
         if (compoundnbt != null) {
            if ("DUMMY".equals(compoundnbt.getString("id"))) {
               Block block = blockstate.getBlock();
               if (!blockstate.hasTileEntity()) {
                  return null;
               }

               tileentity = blockstate.createTileEntity(this.world);
            } else {
               tileentity = TileEntity.func_235657_b_(blockstate, compoundnbt);
            }

            if (tileentity != null) {
               ichunk.addTileEntity(pos, tileentity);
               return tileentity;
            }
         }

         if (blockstate.hasTileEntity()) {
            LOGGER.warn("Tried to access a block entity before it was created. {}", (Object)pos);
         }

         return null;
      }
   }

   public boolean func_241211_a_(BlockPos p_241211_1_, BlockState p_241211_2_, int p_241211_3_, int p_241211_4_) {
      IChunk ichunk = this.getChunk(p_241211_1_);
      BlockState blockstate = ichunk.setBlockState(p_241211_1_, p_241211_2_, false);
      if (blockstate != null) {
         this.world.onBlockStateChange(p_241211_1_, blockstate, p_241211_2_);
      }

      Block block = p_241211_2_.getBlock();
      if (p_241211_2_.hasTileEntity()) {
         if (ichunk.getStatus().getType() == ChunkStatus.Type.LEVELCHUNK) {
            ichunk.addTileEntity(p_241211_1_, p_241211_2_.createTileEntity(this));
         } else {
            CompoundNBT compoundnbt = new CompoundNBT();
            compoundnbt.putInt("x", p_241211_1_.getX());
            compoundnbt.putInt("y", p_241211_1_.getY());
            compoundnbt.putInt("z", p_241211_1_.getZ());
            compoundnbt.putString("id", "DUMMY");
            ichunk.addTileEntity(compoundnbt);
         }
      } else if (blockstate != null && blockstate.hasTileEntity()) {
         ichunk.removeTileEntity(p_241211_1_);
      }

      if (p_241211_2_.blockNeedsPostProcessing(this, p_241211_1_)) {
         this.markBlockForPostprocessing(p_241211_1_);
      }

      return true;
   }

   private void markBlockForPostprocessing(BlockPos pos) {
      this.getChunk(pos).markBlockForPostprocessing(pos);
   }

   public boolean addEntity(Entity entityIn) {
      int i = MathHelper.floor(entityIn.getPosX() / 16.0D);
      int j = MathHelper.floor(entityIn.getPosZ() / 16.0D);
      this.getChunk(i, j).addEntity(entityIn);
      return true;
   }

   public boolean removeBlock(BlockPos pos, boolean isMoving) {
      return this.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
   }

   public WorldBorder getWorldBorder() {
      return this.world.getWorldBorder();
   }

   public boolean isRemote() {
      return false;
   }

   @Deprecated
   public ServerWorld getWorld() {
      return this.world;
   }

   public DynamicRegistries func_241828_r() {
      return this.world.func_241828_r();
   }

   /**
    * Returns the world's WorldInfo object
    */
   public IWorldInfo getWorldInfo() {
      return this.worldInfo;
   }

   public DifficultyInstance getDifficultyForLocation(BlockPos pos) {
      if (!this.chunkExists(pos.getX() >> 4, pos.getZ() >> 4)) {
         throw new RuntimeException("We are asking a region for a chunk out of bound");
      } else {
         return new DifficultyInstance(this.world.getDifficulty(), this.world.getDayTime(), 0L, this.world.func_242413_ae());
      }
   }

   /**
    * Gets the world's chunk provider
    */
   public AbstractChunkProvider getChunkProvider() {
      return this.world.getChunkProvider();
   }

   /**
    * gets the random world seed
    */
   public long getSeed() {
      return this.seed;
   }

   public ITickList<Block> getPendingBlockTicks() {
      return this.pendingBlockTickList;
   }

   public ITickList<Fluid> getPendingFluidTicks() {
      return this.pendingFluidTickList;
   }

   public int getSeaLevel() {
      return this.world.getSeaLevel();
   }

   public Random getRandom() {
      return this.random;
   }

   public int getHeight(Heightmap.Type heightmapType, int x, int z) {
      return this.getChunk(x >> 4, z >> 4).getTopBlockY(heightmapType, x & 15, z & 15) + 1;
   }

   /**
    * Plays a sound. On the server, the sound is broadcast to all nearby <em>except</em> the given player. On the
    * client, the sound only plays if the given player is the client player. Thus, this method is intended to be called
    * from code running on both sides. The client plays it locally and the server plays it for everyone else.
    */
   public void playSound(@Nullable PlayerEntity player, BlockPos pos, SoundEvent soundIn, SoundCategory category, float volume, float pitch) {
   }

   public void addParticle(IParticleData particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
   }

   public void playEvent(@Nullable PlayerEntity player, int type, BlockPos pos, int data) {
   }

   public DimensionType func_230315_m_() {
      return this.field_241159_j_;
   }

   public boolean hasBlockState(BlockPos p_217375_1_, Predicate<BlockState> p_217375_2_) {
      return p_217375_2_.test(this.getBlockState(p_217375_1_));
   }

   public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> clazz, AxisAlignedBB aabb, @Nullable Predicate<? super T> filter) {
      return Collections.emptyList();
   }

   /**
    * Gets all entities within the specified AABB excluding the one passed into it.
    */
   public List<Entity> getEntitiesInAABBexcluding(@Nullable Entity entityIn, AxisAlignedBB boundingBox, @Nullable Predicate<? super Entity> predicate) {
      return Collections.emptyList();
   }

   public List<PlayerEntity> getPlayers() {
      return Collections.emptyList();
   }

   public Stream<? extends StructureStart<?>> func_241827_a(SectionPos p_241827_1_, Structure<?> p_241827_2_) {
      return this.world.func_241827_a(p_241827_1_, p_241827_2_);
   }
}