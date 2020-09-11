package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.JigsawBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class JigsawTileEntity extends TileEntity {
   private ResourceLocation field_235658_a_ = new ResourceLocation("empty");
   private ResourceLocation field_235659_b_ = new ResourceLocation("empty");
   private ResourceLocation field_235660_c_ = new ResourceLocation("empty");
   private JigsawTileEntity.OrientationType field_235661_g_ = JigsawTileEntity.OrientationType.ROLLABLE;
   private String finalState = "minecraft:air";

   public JigsawTileEntity(TileEntityType<?> p_i49960_1_) {
      super(p_i49960_1_);
   }

   public JigsawTileEntity() {
      this(TileEntityType.JIGSAW);
   }

   @OnlyIn(Dist.CLIENT)
   public ResourceLocation func_235668_d_() {
      return this.field_235658_a_;
   }

   @OnlyIn(Dist.CLIENT)
   public ResourceLocation func_235669_f_() {
      return this.field_235659_b_;
   }

   @OnlyIn(Dist.CLIENT)
   public ResourceLocation func_235670_g_() {
      return this.field_235660_c_;
   }

   @OnlyIn(Dist.CLIENT)
   public String getFinalState() {
      return this.finalState;
   }

   @OnlyIn(Dist.CLIENT)
   public JigsawTileEntity.OrientationType func_235671_j_() {
      return this.field_235661_g_;
   }

   public void func_235664_a_(ResourceLocation p_235664_1_) {
      this.field_235658_a_ = p_235664_1_;
   }

   public void func_235666_b_(ResourceLocation p_235666_1_) {
      this.field_235659_b_ = p_235666_1_;
   }

   public void func_235667_c_(ResourceLocation p_235667_1_) {
      this.field_235660_c_ = p_235667_1_;
   }

   public void setFinalState(String p_214055_1_) {
      this.finalState = p_214055_1_;
   }

   public void func_235662_a_(JigsawTileEntity.OrientationType p_235662_1_) {
      this.field_235661_g_ = p_235662_1_;
   }

   public CompoundNBT write(CompoundNBT compound) {
      super.write(compound);
      compound.putString("name", this.field_235658_a_.toString());
      compound.putString("target", this.field_235659_b_.toString());
      compound.putString("pool", this.field_235660_c_.toString());
      compound.putString("final_state", this.finalState);
      compound.putString("joint", this.field_235661_g_.func_176610_l());
      return compound;
   }

   public void func_230337_a_(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
      super.func_230337_a_(p_230337_1_, p_230337_2_);
      this.field_235658_a_ = new ResourceLocation(p_230337_2_.getString("name"));
      this.field_235659_b_ = new ResourceLocation(p_230337_2_.getString("target"));
      this.field_235660_c_ = new ResourceLocation(p_230337_2_.getString("pool"));
      this.finalState = p_230337_2_.getString("final_state");
      this.field_235661_g_ = JigsawTileEntity.OrientationType.func_235673_a_(p_230337_2_.getString("joint")).orElseGet(() -> {
         return JigsawBlock.func_235508_h_(p_230337_1_).getAxis().isHorizontal() ? JigsawTileEntity.OrientationType.ALIGNED : JigsawTileEntity.OrientationType.ROLLABLE;
      });
   }

   /**
    * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
    * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
    */
   @Nullable
   public SUpdateTileEntityPacket getUpdatePacket() {
      return new SUpdateTileEntityPacket(this.pos, 12, this.getUpdateTag());
   }

   /**
    * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
    * many blocks change at once. This compound comes back to you clientside in {@link handleUpdateTag}
    */
   public CompoundNBT getUpdateTag() {
      return this.write(new CompoundNBT());
   }

   public void func_235665_a_(ServerWorld p_235665_1_, int p_235665_2_, boolean p_235665_3_) {
      ChunkGenerator chunkgenerator = p_235665_1_.getChunkProvider().getChunkGenerator();
      TemplateManager templatemanager = p_235665_1_.getStructureTemplateManager();
      StructureManager structuremanager = p_235665_1_.func_241112_a_();
      Random random = p_235665_1_.getRandom();
      BlockPos blockpos = this.getPos();
      List<AbstractVillagePiece> list = Lists.newArrayList();
      Template template = new Template();
      template.takeBlocksFromWorld(p_235665_1_, blockpos, new BlockPos(1, 1, 1), false, (Block)null);
      JigsawPiece jigsawpiece = new SingleJigsawPiece(template);
      AbstractVillagePiece abstractvillagepiece = new AbstractVillagePiece(templatemanager, jigsawpiece, blockpos, 1, Rotation.NONE, new MutableBoundingBox(blockpos, blockpos));
      JigsawManager.func_242838_a(p_235665_1_.func_241828_r(), abstractvillagepiece, p_235665_2_, AbstractVillagePiece::new, chunkgenerator, templatemanager, list, random);

      for(AbstractVillagePiece abstractvillagepiece1 : list) {
         abstractvillagepiece1.func_237001_a_(p_235665_1_, structuremanager, chunkgenerator, random, MutableBoundingBox.func_236990_b_(), blockpos, p_235665_3_);
      }

   }

   public static enum OrientationType implements IStringSerializable {
      ROLLABLE("rollable"),
      ALIGNED("aligned");

      private final String field_235672_c_;

      private OrientationType(String p_i231862_3_) {
         this.field_235672_c_ = p_i231862_3_;
      }

      public String func_176610_l() {
         return this.field_235672_c_;
      }

      public static Optional<JigsawTileEntity.OrientationType> func_235673_a_(String p_235673_0_) {
         return Arrays.stream(values()).filter((p_235674_1_) -> {
            return p_235674_1_.func_176610_l().equals(p_235673_0_);
         }).findFirst();
      }
   }
}