package net.minecraft.world.border;

import com.google.common.collect.Lists;
import com.mojang.serialization.DynamicLike;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WorldBorder {
   private final List<IBorderListener> listeners = Lists.newArrayList();
   private double damagePerBlock = 0.2D;
   private double damageBuffer = 5.0D;
   private int warningTime = 15;
   private int warningDistance = 5;
   private double centerX;
   private double centerZ;
   private int worldSize = 29999984;
   private WorldBorder.IBorderInfo state = new WorldBorder.StationaryBorderInfo(6.0E7D);
   public static final WorldBorder.Serializer field_235925_b_ = new WorldBorder.Serializer(0.0D, 0.0D, 0.2D, 5.0D, 5, 15, 6.0E7D, 0L, 0.0D);

   public boolean contains(BlockPos pos) {
      return (double)(pos.getX() + 1) > this.minX() && (double)pos.getX() < this.maxX() && (double)(pos.getZ() + 1) > this.minZ() && (double)pos.getZ() < this.maxZ();
   }

   public boolean contains(ChunkPos range) {
      return (double)range.getXEnd() > this.minX() && (double)range.getXStart() < this.maxX() && (double)range.getZEnd() > this.minZ() && (double)range.getZStart() < this.maxZ();
   }

   public boolean contains(AxisAlignedBB bb) {
      return bb.maxX > this.minX() && bb.minX < this.maxX() && bb.maxZ > this.minZ() && bb.minZ < this.maxZ();
   }

   public double getClosestDistance(Entity entityIn) {
      return this.getClosestDistance(entityIn.getPosX(), entityIn.getPosZ());
   }

   public VoxelShape getShape() {
      return this.state.getShape();
   }

   public double getClosestDistance(double x, double z) {
      double d0 = z - this.minZ();
      double d1 = this.maxZ() - z;
      double d2 = x - this.minX();
      double d3 = this.maxX() - x;
      double d4 = Math.min(d2, d3);
      d4 = Math.min(d4, d0);
      return Math.min(d4, d1);
   }

   @OnlyIn(Dist.CLIENT)
   public BorderStatus getStatus() {
      return this.state.getStatus();
   }

   public double minX() {
      return this.state.getMinX();
   }

   public double minZ() {
      return this.state.getMinZ();
   }

   public double maxX() {
      return this.state.getMaxX();
   }

   public double maxZ() {
      return this.state.getMaxZ();
   }

   public double func_230316_a_() {
      return this.centerX;
   }

   public double func_230317_b_() {
      return this.centerZ;
   }

   public void setCenter(double x, double z) {
      this.centerX = x;
      this.centerZ = z;
      this.state.onCenterChanged();

      for(IBorderListener iborderlistener : this.getListeners()) {
         iborderlistener.onCenterChanged(this, x, z);
      }

   }

   public double getDiameter() {
      return this.state.getSize();
   }

   public long getTimeUntilTarget() {
      return this.state.getTimeUntilTarget();
   }

   public double getTargetSize() {
      return this.state.getTargetSize();
   }

   public void setTransition(double newSize) {
      this.state = new WorldBorder.StationaryBorderInfo(newSize);

      for(IBorderListener iborderlistener : this.getListeners()) {
         iborderlistener.onSizeChanged(this, newSize);
      }

   }

   public void setTransition(double oldSize, double newSize, long time) {
      this.state = (WorldBorder.IBorderInfo)(oldSize == newSize ? new WorldBorder.StationaryBorderInfo(newSize) : new WorldBorder.MovingBorderInfo(oldSize, newSize, time));

      for(IBorderListener iborderlistener : this.getListeners()) {
         iborderlistener.onTransitionStarted(this, oldSize, newSize, time);
      }

   }

   protected List<IBorderListener> getListeners() {
      return Lists.newArrayList(this.listeners);
   }

   public void addListener(IBorderListener listener) {
      this.listeners.add(listener);
   }

   public void removeListener(IBorderListener listener) {
      this.listeners.remove(listener);
   }

   public void setSize(int size) {
      this.worldSize = size;
      this.state.onSizeChanged();
   }

   public int getSize() {
      return this.worldSize;
   }

   public double getDamageBuffer() {
      return this.damageBuffer;
   }

   public void setDamageBuffer(double bufferSize) {
      this.damageBuffer = bufferSize;

      for(IBorderListener iborderlistener : this.getListeners()) {
         iborderlistener.onDamageBufferChanged(this, bufferSize);
      }

   }

   public double getDamagePerBlock() {
      return this.damagePerBlock;
   }

   public void setDamagePerBlock(double newAmount) {
      this.damagePerBlock = newAmount;

      for(IBorderListener iborderlistener : this.getListeners()) {
         iborderlistener.onDamageAmountChanged(this, newAmount);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public double getResizeSpeed() {
      return this.state.getResizeSpeed();
   }

   public int getWarningTime() {
      return this.warningTime;
   }

   public void setWarningTime(int warningTime) {
      this.warningTime = warningTime;

      for(IBorderListener iborderlistener : this.getListeners()) {
         iborderlistener.onWarningTimeChanged(this, warningTime);
      }

   }

   public int getWarningDistance() {
      return this.warningDistance;
   }

   public void setWarningDistance(int warningDistance) {
      this.warningDistance = warningDistance;

      for(IBorderListener iborderlistener : this.getListeners()) {
         iborderlistener.onWarningDistanceChanged(this, warningDistance);
      }

   }

   public void tick() {
      this.state = this.state.tick();
   }

   public WorldBorder.Serializer func_235927_t_() {
      return new WorldBorder.Serializer(this);
   }

   public void func_235926_a_(WorldBorder.Serializer p_235926_1_) {
      this.setCenter(p_235926_1_.func_235937_a_(), p_235926_1_.func_235940_b_());
      this.setDamagePerBlock(p_235926_1_.func_235941_c_());
      this.setDamageBuffer(p_235926_1_.func_235942_d_());
      this.setWarningDistance(p_235926_1_.func_235943_e_());
      this.setWarningTime(p_235926_1_.func_235944_f_());
      if (p_235926_1_.func_235946_h_() > 0L) {
         this.setTransition(p_235926_1_.func_235945_g_(), p_235926_1_.func_235947_i_(), p_235926_1_.func_235946_h_());
      } else {
         this.setTransition(p_235926_1_.func_235945_g_());
      }

   }

   interface IBorderInfo {
      double getMinX();

      double getMaxX();

      double getMinZ();

      double getMaxZ();

      double getSize();

      @OnlyIn(Dist.CLIENT)
      double getResizeSpeed();

      long getTimeUntilTarget();

      double getTargetSize();

      @OnlyIn(Dist.CLIENT)
      BorderStatus getStatus();

      void onSizeChanged();

      void onCenterChanged();

      WorldBorder.IBorderInfo tick();

      VoxelShape getShape();
   }

   class MovingBorderInfo implements WorldBorder.IBorderInfo {
      private final double oldSize;
      private final double newSize;
      private final long endTime;
      private final long startTime;
      private final double transitionTime;

      private MovingBorderInfo(double p_i49838_2_, double p_i49838_4_, long p_i49838_6_) {
         this.oldSize = p_i49838_2_;
         this.newSize = p_i49838_4_;
         this.transitionTime = (double)p_i49838_6_;
         this.startTime = Util.milliTime();
         this.endTime = this.startTime + p_i49838_6_;
      }

      public double getMinX() {
         return Math.max(WorldBorder.this.func_230316_a_() - this.getSize() / 2.0D, (double)(-WorldBorder.this.worldSize));
      }

      public double getMinZ() {
         return Math.max(WorldBorder.this.func_230317_b_() - this.getSize() / 2.0D, (double)(-WorldBorder.this.worldSize));
      }

      public double getMaxX() {
         return Math.min(WorldBorder.this.func_230316_a_() + this.getSize() / 2.0D, (double)WorldBorder.this.worldSize);
      }

      public double getMaxZ() {
         return Math.min(WorldBorder.this.func_230317_b_() + this.getSize() / 2.0D, (double)WorldBorder.this.worldSize);
      }

      public double getSize() {
         double d0 = (double)(Util.milliTime() - this.startTime) / this.transitionTime;
         return d0 < 1.0D ? MathHelper.lerp(d0, this.oldSize, this.newSize) : this.newSize;
      }

      @OnlyIn(Dist.CLIENT)
      public double getResizeSpeed() {
         return Math.abs(this.oldSize - this.newSize) / (double)(this.endTime - this.startTime);
      }

      public long getTimeUntilTarget() {
         return this.endTime - Util.milliTime();
      }

      public double getTargetSize() {
         return this.newSize;
      }

      @OnlyIn(Dist.CLIENT)
      public BorderStatus getStatus() {
         return this.newSize < this.oldSize ? BorderStatus.SHRINKING : BorderStatus.GROWING;
      }

      public void onCenterChanged() {
      }

      public void onSizeChanged() {
      }

      public WorldBorder.IBorderInfo tick() {
         return (WorldBorder.IBorderInfo)(this.getTimeUntilTarget() <= 0L ? WorldBorder.this.new StationaryBorderInfo(this.newSize) : this);
      }

      public VoxelShape getShape() {
         return VoxelShapes.combineAndSimplify(VoxelShapes.INFINITY, VoxelShapes.create(Math.floor(this.getMinX()), Double.NEGATIVE_INFINITY, Math.floor(this.getMinZ()), Math.ceil(this.getMaxX()), Double.POSITIVE_INFINITY, Math.ceil(this.getMaxZ())), IBooleanFunction.ONLY_FIRST);
      }
   }

   public static class Serializer {
      private final double field_235928_a_;
      private final double field_235929_b_;
      private final double field_235930_c_;
      private final double field_235931_d_;
      private final int field_235932_e_;
      private final int field_235933_f_;
      private final double field_235934_g_;
      private final long field_235935_h_;
      private final double field_235936_i_;

      private Serializer(double p_i231883_1_, double p_i231883_3_, double p_i231883_5_, double p_i231883_7_, int p_i231883_9_, int p_i231883_10_, double p_i231883_11_, long p_i231883_13_, double p_i231883_15_) {
         this.field_235928_a_ = p_i231883_1_;
         this.field_235929_b_ = p_i231883_3_;
         this.field_235930_c_ = p_i231883_5_;
         this.field_235931_d_ = p_i231883_7_;
         this.field_235932_e_ = p_i231883_9_;
         this.field_235933_f_ = p_i231883_10_;
         this.field_235934_g_ = p_i231883_11_;
         this.field_235935_h_ = p_i231883_13_;
         this.field_235936_i_ = p_i231883_15_;
      }

      private Serializer(WorldBorder p_i231885_1_) {
         this.field_235928_a_ = p_i231885_1_.func_230316_a_();
         this.field_235929_b_ = p_i231885_1_.func_230317_b_();
         this.field_235930_c_ = p_i231885_1_.getDamagePerBlock();
         this.field_235931_d_ = p_i231885_1_.getDamageBuffer();
         this.field_235932_e_ = p_i231885_1_.getWarningDistance();
         this.field_235933_f_ = p_i231885_1_.getWarningTime();
         this.field_235934_g_ = p_i231885_1_.getDiameter();
         this.field_235935_h_ = p_i231885_1_.getTimeUntilTarget();
         this.field_235936_i_ = p_i231885_1_.getTargetSize();
      }

      public double func_235937_a_() {
         return this.field_235928_a_;
      }

      public double func_235940_b_() {
         return this.field_235929_b_;
      }

      public double func_235941_c_() {
         return this.field_235930_c_;
      }

      public double func_235942_d_() {
         return this.field_235931_d_;
      }

      public int func_235943_e_() {
         return this.field_235932_e_;
      }

      public int func_235944_f_() {
         return this.field_235933_f_;
      }

      public double func_235945_g_() {
         return this.field_235934_g_;
      }

      public long func_235946_h_() {
         return this.field_235935_h_;
      }

      public double func_235947_i_() {
         return this.field_235936_i_;
      }

      public static WorldBorder.Serializer func_235938_a_(DynamicLike<?> p_235938_0_, WorldBorder.Serializer p_235938_1_) {
         double d0 = p_235938_0_.get("BorderCenterX").asDouble(p_235938_1_.field_235928_a_);
         double d1 = p_235938_0_.get("BorderCenterZ").asDouble(p_235938_1_.field_235929_b_);
         double d2 = p_235938_0_.get("BorderSize").asDouble(p_235938_1_.field_235934_g_);
         long i = p_235938_0_.get("BorderSizeLerpTime").asLong(p_235938_1_.field_235935_h_);
         double d3 = p_235938_0_.get("BorderSizeLerpTarget").asDouble(p_235938_1_.field_235936_i_);
         double d4 = p_235938_0_.get("BorderSafeZone").asDouble(p_235938_1_.field_235931_d_);
         double d5 = p_235938_0_.get("BorderDamagePerBlock").asDouble(p_235938_1_.field_235930_c_);
         int j = p_235938_0_.get("BorderWarningBlocks").asInt(p_235938_1_.field_235932_e_);
         int k = p_235938_0_.get("BorderWarningTime").asInt(p_235938_1_.field_235933_f_);
         return new WorldBorder.Serializer(d0, d1, d5, d4, j, k, d2, i, d3);
      }

      public void func_235939_a_(CompoundNBT p_235939_1_) {
         p_235939_1_.putDouble("BorderCenterX", this.field_235928_a_);
         p_235939_1_.putDouble("BorderCenterZ", this.field_235929_b_);
         p_235939_1_.putDouble("BorderSize", this.field_235934_g_);
         p_235939_1_.putLong("BorderSizeLerpTime", this.field_235935_h_);
         p_235939_1_.putDouble("BorderSafeZone", this.field_235931_d_);
         p_235939_1_.putDouble("BorderDamagePerBlock", this.field_235930_c_);
         p_235939_1_.putDouble("BorderSizeLerpTarget", this.field_235936_i_);
         p_235939_1_.putDouble("BorderWarningBlocks", (double)this.field_235932_e_);
         p_235939_1_.putDouble("BorderWarningTime", (double)this.field_235933_f_);
      }
   }

   class StationaryBorderInfo implements WorldBorder.IBorderInfo {
      private final double size;
      private double minX;
      private double minZ;
      private double maxX;
      private double maxZ;
      private VoxelShape shape;

      public StationaryBorderInfo(double p_i49837_2_) {
         this.size = p_i49837_2_;
         this.updateBox();
      }

      public double getMinX() {
         return this.minX;
      }

      public double getMaxX() {
         return this.maxX;
      }

      public double getMinZ() {
         return this.minZ;
      }

      public double getMaxZ() {
         return this.maxZ;
      }

      public double getSize() {
         return this.size;
      }

      @OnlyIn(Dist.CLIENT)
      public BorderStatus getStatus() {
         return BorderStatus.STATIONARY;
      }

      @OnlyIn(Dist.CLIENT)
      public double getResizeSpeed() {
         return 0.0D;
      }

      public long getTimeUntilTarget() {
         return 0L;
      }

      public double getTargetSize() {
         return this.size;
      }

      private void updateBox() {
         this.minX = Math.max(WorldBorder.this.func_230316_a_() - this.size / 2.0D, (double)(-WorldBorder.this.worldSize));
         this.minZ = Math.max(WorldBorder.this.func_230317_b_() - this.size / 2.0D, (double)(-WorldBorder.this.worldSize));
         this.maxX = Math.min(WorldBorder.this.func_230316_a_() + this.size / 2.0D, (double)WorldBorder.this.worldSize);
         this.maxZ = Math.min(WorldBorder.this.func_230317_b_() + this.size / 2.0D, (double)WorldBorder.this.worldSize);
         this.shape = VoxelShapes.combineAndSimplify(VoxelShapes.INFINITY, VoxelShapes.create(Math.floor(this.getMinX()), Double.NEGATIVE_INFINITY, Math.floor(this.getMinZ()), Math.ceil(this.getMaxX()), Double.POSITIVE_INFINITY, Math.ceil(this.getMaxZ())), IBooleanFunction.ONLY_FIRST);
      }

      public void onSizeChanged() {
         this.updateBox();
      }

      public void onCenterChanged() {
         this.updateBox();
      }

      public WorldBorder.IBorderInfo tick() {
         return this;
      }

      public VoxelShape getShape() {
         return this.shape;
      }
   }
}