package net.minecraft.tileentity;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TileEntity extends net.minecraftforge.common.capabilities.CapabilityProvider<TileEntity> implements net.minecraftforge.common.extensions.IForgeTileEntity {
   private static final Logger LOGGER = LogManager.getLogger();
   private final TileEntityType<?> type;
   /** the instance of the world the tile entity is in. */
   @Nullable
   protected World world;
   protected BlockPos pos = BlockPos.ZERO;
   protected boolean removed;
   @Nullable
   private BlockState cachedBlockState;
   private boolean warnedInvalidBlock;
   private CompoundNBT customTileData;

   public TileEntity(TileEntityType<?> tileEntityTypeIn) {
      super(TileEntity.class);
      this.type = tileEntityTypeIn;
      this.gatherCapabilities();
   }

   /**
    * Returns the worldObj for this tileEntity.
    */
   @Nullable
   public World getWorld() {
      return this.world;
   }

   public void setWorldAndPos(World p_226984_1_, BlockPos p_226984_2_) {
      this.world = p_226984_1_;
      this.pos = p_226984_2_.toImmutable();
   }

   /**
    * Returns true if the worldObj isn't null.
    */
   public boolean hasWorld() {
      return this.world != null;
   }

   public void func_230337_a_(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
      this.pos = new BlockPos(p_230337_2_.getInt("x"), p_230337_2_.getInt("y"), p_230337_2_.getInt("z"));
      if (p_230337_2_.contains("ForgeData")) this.customTileData = p_230337_2_.getCompound("ForgeData");
      if (getCapabilities() != null && p_230337_2_.contains("ForgeCaps")) deserializeCaps(p_230337_2_.getCompound("ForgeCaps"));
   }

   public CompoundNBT write(CompoundNBT compound) {
      return this.writeInternal(compound);
   }

   private CompoundNBT writeInternal(CompoundNBT compound) {
      ResourceLocation resourcelocation = TileEntityType.getId(this.getType());
      if (resourcelocation == null) {
         throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
      } else {
         compound.putString("id", resourcelocation.toString());
         compound.putInt("x", this.pos.getX());
         compound.putInt("y", this.pos.getY());
         compound.putInt("z", this.pos.getZ());
         if (this.customTileData != null) compound.put("ForgeData", this.customTileData);
         if (getCapabilities() != null) compound.put("ForgeCaps", serializeCaps());
         return compound;
      }
   }

   @Nullable
   public static TileEntity func_235657_b_(BlockState p_235657_0_, CompoundNBT p_235657_1_) {
      String s = p_235657_1_.getString("id");
      return Registry.BLOCK_ENTITY_TYPE.func_241873_b(new ResourceLocation(s)).map((p_213134_1_) -> {
         try {
            return p_213134_1_.create();
         } catch (Throwable throwable) {
            LOGGER.error("Failed to create block entity {}", s, throwable);
            return null;
         }
      }).map((p_235656_3_) -> {
         try {
            p_235656_3_.func_230337_a_(p_235657_0_, p_235657_1_);
            return p_235656_3_;
         } catch (Throwable throwable) {
            LOGGER.error("Failed to load data for block entity {}", s, throwable);
            return null;
         }
      }).orElseGet(() -> {
         LOGGER.warn("Skipping BlockEntity with id {}", (Object)s);
         return null;
      });
   }

   /**
    * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
    * hasn't changed and skip it.
    */
   public void markDirty() {
      if (this.world != null) {
         this.cachedBlockState = this.world.getBlockState(this.pos);
         this.world.markChunkDirty(this.pos, this);
         if (!this.cachedBlockState.isAir(this.world, this.pos)) {
            this.world.updateComparatorOutputLevel(this.pos, this.cachedBlockState.getBlock());
         }
      }

   }

   @OnlyIn(Dist.CLIENT)
   public double getMaxRenderDistanceSquared() {
      return 64.0D;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public BlockState getBlockState() {
      if (this.cachedBlockState == null) {
         this.cachedBlockState = this.world.getBlockState(this.pos);
      }

      return this.cachedBlockState;
   }

   /**
    * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
    * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
    */
   @Nullable
   public SUpdateTileEntityPacket getUpdatePacket() {
      return null;
   }

   /**
    * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
    * many blocks change at once. This compound comes back to you clientside in {@link handleUpdateTag}
    */
   public CompoundNBT getUpdateTag() {
      return this.writeInternal(new CompoundNBT());
   }

   public boolean isRemoved() {
      return this.removed;
   }

   /**
    * invalidates a tile entity
    */
   public void remove() {
      this.removed = true;
      this.invalidateCaps();
      requestModelDataUpdate();
   }

   /**
    * validates a tile entity
    */
   public void validate() {
      this.removed = false;
   }

   /**
    * See {@link Block#eventReceived} for more information. This must return true serverside before it is called
    * clientside.
    */
   public boolean receiveClientEvent(int id, int type) {
      return false;
   }

   public void updateContainingBlockInfo() {
      this.cachedBlockState = null;
   }

   public void addInfoToCrashReport(CrashReportCategory reportCategory) {
      reportCategory.addDetail("Name", () -> {
         return Registry.BLOCK_ENTITY_TYPE.getKey(this.getType()) + " // " + this.getClass().getCanonicalName();
      });
      if (this.world != null) {
         CrashReportCategory.addBlockInfo(reportCategory, this.pos, this.getBlockState());
         CrashReportCategory.addBlockInfo(reportCategory, this.pos, this.world.getBlockState(this.pos));
      }
   }

   public void setPos(BlockPos posIn) {
      this.pos = posIn.toImmutable();
   }

   /**
    * Checks if players can use this tile entity to access operator (permission level 2) commands either directly or
    * indirectly, such as give or setblock. A similar method exists for entities at {@link
    * net.minecraft.entity.Entity#ignoreItemEntityData()}.<p>For example, {@link
    * net.minecraft.tileentity.TileEntitySign#onlyOpsCanSetNbt() signs} (player right-clicking) and {@link
    * net.minecraft.tileentity.TileEntityCommandBlock#onlyOpsCanSetNbt() command blocks} are considered
    * accessible.</p>@return true if this block entity offers ways for unauthorized players to use restricted commands
    */
   public boolean onlyOpsCanSetNbt() {
      return false;
   }

   public void rotate(Rotation rotationIn) {
   }

   public void mirror(Mirror mirrorIn) {
   }

   public TileEntityType<?> getType() {
      return this.type;
   }

   @Override
   public CompoundNBT getTileData() {
      if (this.customTileData == null)
         this.customTileData = new CompoundNBT();
      return this.customTileData;
   }

   public void warnInvalidBlock() {
      if (!this.warnedInvalidBlock) {
         this.warnedInvalidBlock = true;
         LOGGER.warn("Block entity invalid: {} @ {}", () -> {
            return Registry.BLOCK_ENTITY_TYPE.getKey(this.getType());
         }, this::getPos);
      }
   }
}