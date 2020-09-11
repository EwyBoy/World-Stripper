package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StructureBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.Rotation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.template.IntegrityProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StructureBlockTileEntity extends TileEntity {
   private ResourceLocation name;
   private String author = "";
   private String metadata = "";
   private BlockPos position = new BlockPos(0, 1, 0);
   private BlockPos size = BlockPos.ZERO;
   private Mirror mirror = Mirror.NONE;
   private Rotation rotation = Rotation.NONE;
   private StructureMode mode = StructureMode.DATA;
   private boolean ignoreEntities = true;
   private boolean powered;
   private boolean showAir;
   private boolean showBoundingBox = true;
   private float integrity = 1.0F;
   private long seed;

   public StructureBlockTileEntity() {
      super(TileEntityType.STRUCTURE_BLOCK);
   }

   @OnlyIn(Dist.CLIENT)
   public double getMaxRenderDistanceSquared() {
      return 96.0D;
   }

   public CompoundNBT write(CompoundNBT compound) {
      super.write(compound);
      compound.putString("name", this.getName());
      compound.putString("author", this.author);
      compound.putString("metadata", this.metadata);
      compound.putInt("posX", this.position.getX());
      compound.putInt("posY", this.position.getY());
      compound.putInt("posZ", this.position.getZ());
      compound.putInt("sizeX", this.size.getX());
      compound.putInt("sizeY", this.size.getY());
      compound.putInt("sizeZ", this.size.getZ());
      compound.putString("rotation", this.rotation.toString());
      compound.putString("mirror", this.mirror.toString());
      compound.putString("mode", this.mode.toString());
      compound.putBoolean("ignoreEntities", this.ignoreEntities);
      compound.putBoolean("powered", this.powered);
      compound.putBoolean("showair", this.showAir);
      compound.putBoolean("showboundingbox", this.showBoundingBox);
      compound.putFloat("integrity", this.integrity);
      compound.putLong("seed", this.seed);
      return compound;
   }

   public void func_230337_a_(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
      super.func_230337_a_(p_230337_1_, p_230337_2_);
      this.setName(p_230337_2_.getString("name"));
      this.author = p_230337_2_.getString("author");
      this.metadata = p_230337_2_.getString("metadata");
      int i = MathHelper.clamp(p_230337_2_.getInt("posX"), -48, 48);
      int j = MathHelper.clamp(p_230337_2_.getInt("posY"), -48, 48);
      int k = MathHelper.clamp(p_230337_2_.getInt("posZ"), -48, 48);
      this.position = new BlockPos(i, j, k);
      int l = MathHelper.clamp(p_230337_2_.getInt("sizeX"), 0, 48);
      int i1 = MathHelper.clamp(p_230337_2_.getInt("sizeY"), 0, 48);
      int j1 = MathHelper.clamp(p_230337_2_.getInt("sizeZ"), 0, 48);
      this.size = new BlockPos(l, i1, j1);

      try {
         this.rotation = Rotation.valueOf(p_230337_2_.getString("rotation"));
      } catch (IllegalArgumentException illegalargumentexception2) {
         this.rotation = Rotation.NONE;
      }

      try {
         this.mirror = Mirror.valueOf(p_230337_2_.getString("mirror"));
      } catch (IllegalArgumentException illegalargumentexception1) {
         this.mirror = Mirror.NONE;
      }

      try {
         this.mode = StructureMode.valueOf(p_230337_2_.getString("mode"));
      } catch (IllegalArgumentException illegalargumentexception) {
         this.mode = StructureMode.DATA;
      }

      this.ignoreEntities = p_230337_2_.getBoolean("ignoreEntities");
      this.powered = p_230337_2_.getBoolean("powered");
      this.showAir = p_230337_2_.getBoolean("showair");
      this.showBoundingBox = p_230337_2_.getBoolean("showboundingbox");
      if (p_230337_2_.contains("integrity")) {
         this.integrity = p_230337_2_.getFloat("integrity");
      } else {
         this.integrity = 1.0F;
      }

      this.seed = p_230337_2_.getLong("seed");
      this.updateBlockState();
   }

   private void updateBlockState() {
      if (this.world != null) {
         BlockPos blockpos = this.getPos();
         BlockState blockstate = this.world.getBlockState(blockpos);
         if (blockstate.isIn(Blocks.STRUCTURE_BLOCK)) {
            this.world.setBlockState(blockpos, blockstate.with(StructureBlock.MODE, this.mode), 2);
         }

      }
   }

   /**
    * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
    * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
    */
   @Nullable
   public SUpdateTileEntityPacket getUpdatePacket() {
      return new SUpdateTileEntityPacket(this.pos, 7, this.getUpdateTag());
   }

   /**
    * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
    * many blocks change at once. This compound comes back to you clientside in {@link handleUpdateTag}
    */
   public CompoundNBT getUpdateTag() {
      return this.write(new CompoundNBT());
   }

   public boolean usedBy(PlayerEntity player) {
      if (!player.canUseCommandBlock()) {
         return false;
      } else {
         if (player.getEntityWorld().isRemote) {
            player.openStructureBlock(this);
         }

         return true;
      }
   }

   public String getName() {
      return this.name == null ? "" : this.name.toString();
   }

   public String func_227014_f_() {
      return this.name == null ? "" : this.name.getPath();
   }

   public boolean hasName() {
      return this.name != null;
   }

   public void setName(@Nullable String nameIn) {
      this.setName(StringUtils.isNullOrEmpty(nameIn) ? null : ResourceLocation.tryCreate(nameIn));
   }

   public void setName(@Nullable ResourceLocation p_210163_1_) {
      this.name = p_210163_1_;
   }

   public void createdBy(LivingEntity p_189720_1_) {
      this.author = p_189720_1_.getName().getString();
   }

   @OnlyIn(Dist.CLIENT)
   public BlockPos getPosition() {
      return this.position;
   }

   public void setPosition(BlockPos posIn) {
      this.position = posIn;
   }

   public BlockPos getStructureSize() {
      return this.size;
   }

   public void setSize(BlockPos sizeIn) {
      this.size = sizeIn;
   }

   @OnlyIn(Dist.CLIENT)
   public Mirror getMirror() {
      return this.mirror;
   }

   public void setMirror(Mirror mirrorIn) {
      this.mirror = mirrorIn;
   }

   public Rotation getRotation() {
      return this.rotation;
   }

   public void setRotation(Rotation rotationIn) {
      this.rotation = rotationIn;
   }

   @OnlyIn(Dist.CLIENT)
   public String getMetadata() {
      return this.metadata;
   }

   public void setMetadata(String metadataIn) {
      this.metadata = metadataIn;
   }

   public StructureMode getMode() {
      return this.mode;
   }

   public void setMode(StructureMode modeIn) {
      this.mode = modeIn;
      BlockState blockstate = this.world.getBlockState(this.getPos());
      if (blockstate.isIn(Blocks.STRUCTURE_BLOCK)) {
         this.world.setBlockState(this.getPos(), blockstate.with(StructureBlock.MODE, modeIn), 2);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public void nextMode() {
      switch(this.getMode()) {
      case SAVE:
         this.setMode(StructureMode.LOAD);
         break;
      case LOAD:
         this.setMode(StructureMode.CORNER);
         break;
      case CORNER:
         this.setMode(StructureMode.DATA);
         break;
      case DATA:
         this.setMode(StructureMode.SAVE);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public boolean ignoresEntities() {
      return this.ignoreEntities;
   }

   public void setIgnoresEntities(boolean ignoreEntitiesIn) {
      this.ignoreEntities = ignoreEntitiesIn;
   }

   @OnlyIn(Dist.CLIENT)
   public float getIntegrity() {
      return this.integrity;
   }

   public void setIntegrity(float integrityIn) {
      this.integrity = integrityIn;
   }

   @OnlyIn(Dist.CLIENT)
   public long getSeed() {
      return this.seed;
   }

   public void setSeed(long seedIn) {
      this.seed = seedIn;
   }

   public boolean detectSize() {
      if (this.mode != StructureMode.SAVE) {
         return false;
      } else {
         BlockPos blockpos = this.getPos();
         int i = 80;
         BlockPos blockpos1 = new BlockPos(blockpos.getX() - 80, 0, blockpos.getZ() - 80);
         BlockPos blockpos2 = new BlockPos(blockpos.getX() + 80, 255, blockpos.getZ() + 80);
         List<StructureBlockTileEntity> list = this.getNearbyCornerBlocks(blockpos1, blockpos2);
         List<StructureBlockTileEntity> list1 = this.filterRelatedCornerBlocks(list);
         if (list1.size() < 1) {
            return false;
         } else {
            MutableBoundingBox mutableboundingbox = this.calculateEnclosingBoundingBox(blockpos, list1);
            if (mutableboundingbox.maxX - mutableboundingbox.minX > 1 && mutableboundingbox.maxY - mutableboundingbox.minY > 1 && mutableboundingbox.maxZ - mutableboundingbox.minZ > 1) {
               this.position = new BlockPos(mutableboundingbox.minX - blockpos.getX() + 1, mutableboundingbox.minY - blockpos.getY() + 1, mutableboundingbox.minZ - blockpos.getZ() + 1);
               this.size = new BlockPos(mutableboundingbox.maxX - mutableboundingbox.minX - 1, mutableboundingbox.maxY - mutableboundingbox.minY - 1, mutableboundingbox.maxZ - mutableboundingbox.minZ - 1);
               this.markDirty();
               BlockState blockstate = this.world.getBlockState(blockpos);
               this.world.notifyBlockUpdate(blockpos, blockstate, blockstate, 3);
               return true;
            } else {
               return false;
            }
         }
      }
   }

   private List<StructureBlockTileEntity> filterRelatedCornerBlocks(List<StructureBlockTileEntity> p_184415_1_) {
      Predicate<StructureBlockTileEntity> predicate = (p_200665_1_) -> {
         return p_200665_1_.mode == StructureMode.CORNER && Objects.equals(this.name, p_200665_1_.name);
      };
      return p_184415_1_.stream().filter(predicate).collect(Collectors.toList());
   }

   private List<StructureBlockTileEntity> getNearbyCornerBlocks(BlockPos p_184418_1_, BlockPos p_184418_2_) {
      List<StructureBlockTileEntity> list = Lists.newArrayList();

      for(BlockPos blockpos : BlockPos.getAllInBoxMutable(p_184418_1_, p_184418_2_)) {
         BlockState blockstate = this.world.getBlockState(blockpos);
         if (blockstate.isIn(Blocks.STRUCTURE_BLOCK)) {
            TileEntity tileentity = this.world.getTileEntity(blockpos);
            if (tileentity != null && tileentity instanceof StructureBlockTileEntity) {
               list.add((StructureBlockTileEntity)tileentity);
            }
         }
      }

      return list;
   }

   private MutableBoundingBox calculateEnclosingBoundingBox(BlockPos p_184416_1_, List<StructureBlockTileEntity> p_184416_2_) {
      MutableBoundingBox mutableboundingbox;
      if (p_184416_2_.size() > 1) {
         BlockPos blockpos = p_184416_2_.get(0).getPos();
         mutableboundingbox = new MutableBoundingBox(blockpos, blockpos);
      } else {
         mutableboundingbox = new MutableBoundingBox(p_184416_1_, p_184416_1_);
      }

      for(StructureBlockTileEntity structureblocktileentity : p_184416_2_) {
         BlockPos blockpos1 = structureblocktileentity.getPos();
         if (blockpos1.getX() < mutableboundingbox.minX) {
            mutableboundingbox.minX = blockpos1.getX();
         } else if (blockpos1.getX() > mutableboundingbox.maxX) {
            mutableboundingbox.maxX = blockpos1.getX();
         }

         if (blockpos1.getY() < mutableboundingbox.minY) {
            mutableboundingbox.minY = blockpos1.getY();
         } else if (blockpos1.getY() > mutableboundingbox.maxY) {
            mutableboundingbox.maxY = blockpos1.getY();
         }

         if (blockpos1.getZ() < mutableboundingbox.minZ) {
            mutableboundingbox.minZ = blockpos1.getZ();
         } else if (blockpos1.getZ() > mutableboundingbox.maxZ) {
            mutableboundingbox.maxZ = blockpos1.getZ();
         }
      }

      return mutableboundingbox;
   }

   /**
    * Saves the template, writing it to disk.
    *  
    * @return true if the template was successfully saved.
    */
   public boolean save() {
      return this.save(true);
   }

   /**
    * Saves the template, either updating the local version or writing it to disk.
    *  
    * @return true if the template was successfully saved.
    */
   public boolean save(boolean writeToDisk) {
      if (this.mode == StructureMode.SAVE && !this.world.isRemote && this.name != null) {
         BlockPos blockpos = this.getPos().add(this.position);
         ServerWorld serverworld = (ServerWorld)this.world;
         TemplateManager templatemanager = serverworld.getStructureTemplateManager();

         Template template;
         try {
            template = templatemanager.getTemplateDefaulted(this.name);
         } catch (ResourceLocationException resourcelocationexception1) {
            return false;
         }

         template.takeBlocksFromWorld(this.world, blockpos, this.size, !this.ignoreEntities, Blocks.STRUCTURE_VOID);
         template.setAuthor(this.author);
         if (writeToDisk) {
            try {
               return templatemanager.writeToFile(this.name);
            } catch (ResourceLocationException resourcelocationexception) {
               return false;
            }
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean func_242687_a(ServerWorld p_242687_1_) {
      return this.func_242688_a(p_242687_1_, true);
   }

   private static Random func_214074_b(long p_214074_0_) {
      return p_214074_0_ == 0L ? new Random(Util.milliTime()) : new Random(p_214074_0_);
   }

   public boolean func_242688_a(ServerWorld p_242688_1_, boolean p_242688_2_) {
      if (this.mode == StructureMode.LOAD && this.name != null) {
         TemplateManager templatemanager = p_242688_1_.getStructureTemplateManager();

         Template template;
         try {
            template = templatemanager.getTemplate(this.name);
         } catch (ResourceLocationException resourcelocationexception) {
            return false;
         }

         return template == null ? false : this.func_242689_a(p_242688_1_, p_242688_2_, template);
      } else {
         return false;
      }
   }

   public boolean func_242689_a(ServerWorld p_242689_1_, boolean p_242689_2_, Template p_242689_3_) {
      BlockPos blockpos = this.getPos();
      if (!StringUtils.isNullOrEmpty(p_242689_3_.getAuthor())) {
         this.author = p_242689_3_.getAuthor();
      }

      BlockPos blockpos1 = p_242689_3_.getSize();
      boolean flag = this.size.equals(blockpos1);
      if (!flag) {
         this.size = blockpos1;
         this.markDirty();
         BlockState blockstate = p_242689_1_.getBlockState(blockpos);
         p_242689_1_.notifyBlockUpdate(blockpos, blockstate, blockstate, 3);
      }

      if (p_242689_2_ && !flag) {
         return false;
      } else {
         PlacementSettings placementsettings = (new PlacementSettings()).setMirror(this.mirror).setRotation(this.rotation).setIgnoreEntities(this.ignoreEntities).setChunk((ChunkPos)null);
         if (this.integrity < 1.0F) {
            placementsettings.clearProcessors().addProcessor(new IntegrityProcessor(MathHelper.clamp(this.integrity, 0.0F, 1.0F))).setRandom(func_214074_b(this.seed));
         }

         BlockPos blockpos2 = blockpos.add(this.position);
         p_242689_3_.func_237144_a_(p_242689_1_, blockpos2, placementsettings, func_214074_b(this.seed));
         return true;
      }
   }

   public void unloadStructure() {
      if (this.name != null) {
         ServerWorld serverworld = (ServerWorld)this.world;
         TemplateManager templatemanager = serverworld.getStructureTemplateManager();
         templatemanager.remove(this.name);
      }
   }

   public boolean isStructureLoadable() {
      if (this.mode == StructureMode.LOAD && !this.world.isRemote && this.name != null) {
         ServerWorld serverworld = (ServerWorld)this.world;
         TemplateManager templatemanager = serverworld.getStructureTemplateManager();

         try {
            return templatemanager.getTemplate(this.name) != null;
         } catch (ResourceLocationException resourcelocationexception) {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean isPowered() {
      return this.powered;
   }

   public void setPowered(boolean poweredIn) {
      this.powered = poweredIn;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean showsAir() {
      return this.showAir;
   }

   public void setShowAir(boolean showAirIn) {
      this.showAir = showAirIn;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean showsBoundingBox() {
      return this.showBoundingBox;
   }

   public void setShowBoundingBox(boolean showBoundingBoxIn) {
      this.showBoundingBox = showBoundingBoxIn;
   }

   public static enum UpdateCommand {
      UPDATE_DATA,
      SAVE_AREA,
      LOAD_AREA,
      SCAN_AREA;
   }
}