package net.minecraft.util.math.shapes;

import java.util.function.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class EntitySelectionContext implements ISelectionContext {
   protected static final ISelectionContext DUMMY = new EntitySelectionContext(false, -Double.MAX_VALUE, Items.AIR, (p_237495_0_) -> {
      return false;
   }) {
      public boolean func_216378_a(VoxelShape shape, BlockPos pos, boolean p_216378_3_) {
         return p_216378_3_;
      }
   };
   private final boolean field_227579_b_;
   private final double field_216381_c;
   private final Item item;
   private final Predicate<Fluid> field_237493_e_;

   protected EntitySelectionContext(boolean p_i232177_1_, double p_i232177_2_, Item p_i232177_4_, Predicate<Fluid> p_i232177_5_) {
       this(null, p_i232177_1_, p_i232177_2_, p_i232177_4_, p_i232177_5_);
   }

   protected EntitySelectionContext(@javax.annotation.Nullable Entity entity, boolean p_i232177_1_, double p_i232177_2_, Item p_i232177_4_, Predicate<Fluid> p_i232177_5_) {
      this.entity = entity;
      this.field_227579_b_ = p_i232177_1_;
      this.field_216381_c = p_i232177_2_;
      this.item = p_i232177_4_;
      this.field_237493_e_ = p_i232177_5_;
   }

   @Deprecated
   protected EntitySelectionContext(Entity entityIn) {
      this(entityIn, entityIn.isDescending(), entityIn.getPosY(), entityIn instanceof LivingEntity ? ((LivingEntity)entityIn).getHeldItemMainhand().getItem() : Items.AIR, entityIn instanceof LivingEntity ? ((LivingEntity)entityIn)::func_230285_a_ : (p_237494_0_) -> {
         return false;
      });
   }

   public boolean hasItem(Item itemIn) {
      return this.item == itemIn;
   }

   public boolean func_230426_a_(FluidState p_230426_1_, FlowingFluid p_230426_2_) {
      return this.field_237493_e_.test(p_230426_2_) && !p_230426_1_.getFluid().isEquivalentTo(p_230426_2_);
   }

   public boolean func_225581_b_() {
      return this.field_227579_b_;
   }

   public boolean func_216378_a(VoxelShape shape, BlockPos pos, boolean p_216378_3_) {
      return this.field_216381_c > (double)pos.getY() + shape.getEnd(Direction.Axis.Y) - (double)1.0E-5F;
   }

   private final @javax.annotation.Nullable Entity entity;

   @Override
   public @javax.annotation.Nullable Entity getEntity() {
      return entity;
   }
}