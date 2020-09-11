package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsLongConfirmationScreen extends RealmsScreen {
   private final RealmsLongConfirmationScreen.Type field_224254_e;
   private final ITextComponent field_224255_f;
   private final ITextComponent field_224256_g;
   protected final BooleanConsumer field_237845_a_;
   private final boolean field_224258_i;

   public RealmsLongConfirmationScreen(BooleanConsumer p_i232208_1_, RealmsLongConfirmationScreen.Type p_i232208_2_, ITextComponent p_i232208_3_, ITextComponent p_i232208_4_, boolean p_i232208_5_) {
      this.field_237845_a_ = p_i232208_1_;
      this.field_224254_e = p_i232208_2_;
      this.field_224255_f = p_i232208_3_;
      this.field_224256_g = p_i232208_4_;
      this.field_224258_i = p_i232208_5_;
   }

   public void func_231160_c_() {
      RealmsNarratorHelper.func_239551_a_(this.field_224254_e.field_225144_d, this.field_224255_f.getString(), this.field_224256_g.getString());
      if (this.field_224258_i) {
         this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 105, func_239562_k_(8), 100, 20, DialogTexts.field_240634_e_, (p_237848_1_) -> {
            this.field_237845_a_.accept(true);
         }));
         this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 5, func_239562_k_(8), 100, 20, DialogTexts.field_240635_f_, (p_237847_1_) -> {
            this.field_237845_a_.accept(false);
         }));
      } else {
         this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 50, func_239562_k_(8), 100, 20, new TranslationTextComponent("mco.gui.ok"), (p_237846_1_) -> {
            this.field_237845_a_.accept(true);
         }));
      }

   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ == 256) {
         this.field_237845_a_.accept(false);
         return true;
      } else {
         return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
      }
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      func_238471_a_(p_230430_1_, this.field_230712_o_, this.field_224254_e.field_225144_d, this.field_230708_k_ / 2, func_239562_k_(2), this.field_224254_e.field_225143_c);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_224255_f, this.field_230708_k_ / 2, func_239562_k_(4), 16777215);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_224256_g, this.field_230708_k_ / 2, func_239562_k_(6), 16777215);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   @OnlyIn(Dist.CLIENT)
   public static enum Type {
      Warning("Warning!", 16711680),
      Info("Info!", 8226750);

      public final int field_225143_c;
      public final String field_225144_d;

      private Type(String p_i51697_3_, int p_i51697_4_) {
         this.field_225144_d = p_i51697_3_;
         this.field_225143_c = p_i51697_4_;
      }
   }
}