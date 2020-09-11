package net.minecraft.realms;

import java.util.Collection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class RealmsObjectSelectionList<E extends ExtendedList.AbstractListEntry<E>> extends ExtendedList<E> {
   protected RealmsObjectSelectionList(int p_i50516_1_, int p_i50516_2_, int p_i50516_3_, int p_i50516_4_, int p_i50516_5_) {
      super(Minecraft.getInstance(), p_i50516_1_, p_i50516_2_, p_i50516_3_, p_i50516_4_, p_i50516_5_);
   }

   public void func_239561_k_(int p_239561_1_) {
      if (p_239561_1_ == -1) {
         this.func_241215_a_((E)null);
      } else if (super.func_230965_k_() != 0) {
         this.func_241215_a_(this.func_230953_d_(p_239561_1_));
      }

   }

   public void func_231400_a_(int p_231400_1_) {
      this.func_239561_k_(p_231400_1_);
   }

   public void func_231401_a_(int p_231401_1_, int p_231401_2_, double p_231401_3_, double p_231401_5_, int p_231401_7_) {
   }

   public int func_230945_b_() {
      return 0;
   }

   public int func_230952_d_() {
      return this.func_230968_n_() + this.func_230949_c_();
   }

   public int func_230949_c_() {
      return (int)((double)this.field_230670_d_ * 0.6D);
   }

   public void func_230942_a_(Collection<E> p_230942_1_) {
      super.func_230942_a_(p_230942_1_);
   }

   public int func_230965_k_() {
      return super.func_230965_k_();
   }

   public int func_230962_i_(int p_230962_1_) {
      return super.func_230962_i_(p_230962_1_);
   }

   public int func_230968_n_() {
      return super.func_230968_n_();
   }

   public int func_230513_b_(E p_230513_1_) {
      return super.func_230513_b_(p_230513_1_);
   }

   public void func_231409_q_() {
      this.func_230963_j_();
   }
}