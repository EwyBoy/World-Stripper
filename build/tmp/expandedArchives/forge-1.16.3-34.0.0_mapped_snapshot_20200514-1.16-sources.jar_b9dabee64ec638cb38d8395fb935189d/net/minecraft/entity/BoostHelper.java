package net.minecraft.entity;

import java.util.Random;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;

public class BoostHelper {
   private final EntityDataManager field_233613_d_;
   private final DataParameter<Integer> field_233614_e_;
   private final DataParameter<Boolean> field_233615_f_;
   public boolean field_233610_a_;
   public int field_233611_b_;
   public int field_233612_c_;

   public BoostHelper(EntityDataManager p_i231490_1_, DataParameter<Integer> p_i231490_2_, DataParameter<Boolean> p_i231490_3_) {
      this.field_233613_d_ = p_i231490_1_;
      this.field_233614_e_ = p_i231490_2_;
      this.field_233615_f_ = p_i231490_3_;
   }

   public void func_233616_a_() {
      this.field_233610_a_ = true;
      this.field_233611_b_ = 0;
      this.field_233612_c_ = this.field_233613_d_.get(this.field_233614_e_);
   }

   public boolean func_233617_a_(Random p_233617_1_) {
      if (this.field_233610_a_) {
         return false;
      } else {
         this.field_233610_a_ = true;
         this.field_233611_b_ = 0;
         this.field_233612_c_ = p_233617_1_.nextInt(841) + 140;
         this.field_233613_d_.set(this.field_233614_e_, this.field_233612_c_);
         return true;
      }
   }

   public void func_233618_a_(CompoundNBT p_233618_1_) {
      p_233618_1_.putBoolean("Saddle", this.func_233620_b_());
   }

   public void func_233621_b_(CompoundNBT p_233621_1_) {
      this.func_233619_a_(p_233621_1_.getBoolean("Saddle"));
   }

   public void func_233619_a_(boolean p_233619_1_) {
      this.field_233613_d_.set(this.field_233615_f_, p_233619_1_);
   }

   public boolean func_233620_b_() {
      return this.field_233613_d_.get(this.field_233615_f_);
   }
}