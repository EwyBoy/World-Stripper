package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsConfirmScreen extends RealmsScreen {
   protected BooleanConsumer field_237824_a_;
   private final ITextComponent field_224142_b;
   private final ITextComponent field_224146_f;
   private int field_224147_g;

   public RealmsConfirmScreen(BooleanConsumer p_i232202_1_, ITextComponent p_i232202_2_, ITextComponent p_i232202_3_) {
      this.field_237824_a_ = p_i232202_1_;
      this.field_224142_b = p_i232202_2_;
      this.field_224146_f = p_i232202_3_;
   }

   public void func_231160_c_() {
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 105, func_239562_k_(9), 100, 20, DialogTexts.field_240634_e_, (p_237826_1_) -> {
         this.field_237824_a_.accept(true);
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 5, func_239562_k_(9), 100, 20, DialogTexts.field_240635_f_, (p_237825_1_) -> {
         this.field_237824_a_.accept(false);
      }));
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_224142_b, this.field_230708_k_ / 2, func_239562_k_(3), 16777215);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_224146_f, this.field_230708_k_ / 2, func_239562_k_(5), 16777215);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      if (--this.field_224147_g == 0) {
         for(Widget widget : this.field_230710_m_) {
            widget.field_230693_o_ = true;
         }
      }

   }
}