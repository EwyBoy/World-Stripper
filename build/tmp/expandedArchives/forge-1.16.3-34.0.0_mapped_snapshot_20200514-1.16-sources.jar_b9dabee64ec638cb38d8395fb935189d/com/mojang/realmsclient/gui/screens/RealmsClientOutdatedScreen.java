package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsClientOutdatedScreen extends RealmsScreen {
   private static final ITextComponent field_243104_a = new TranslationTextComponent("mco.client.outdated.title");
   private static final ITextComponent[] field_243105_b = new ITextComponent[]{new TranslationTextComponent("mco.client.outdated.msg.line1"), new TranslationTextComponent("mco.client.outdated.msg.line2")};
   private static final ITextComponent field_243106_c = new TranslationTextComponent("mco.client.incompatible.title");
   private static final ITextComponent[] field_243107_p = new ITextComponent[]{new TranslationTextComponent("mco.client.incompatible.msg.line1"), new TranslationTextComponent("mco.client.incompatible.msg.line2"), new TranslationTextComponent("mco.client.incompatible.msg.line3")};
   private final Screen field_224129_a;
   private final boolean field_224130_b;

   public RealmsClientOutdatedScreen(Screen p_i232201_1_, boolean p_i232201_2_) {
      this.field_224129_a = p_i232201_1_;
      this.field_224130_b = p_i232201_2_;
   }

   public void func_231160_c_() {
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, func_239562_k_(12), 200, 20, DialogTexts.field_240637_h_, (p_237786_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.field_224129_a);
      }));
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      ITextComponent itextcomponent;
      ITextComponent[] aitextcomponent;
      if (this.field_224130_b) {
         itextcomponent = field_243106_c;
         aitextcomponent = field_243107_p;
      } else {
         itextcomponent = field_243104_a;
         aitextcomponent = field_243105_b;
      }

      func_238472_a_(p_230430_1_, this.field_230712_o_, itextcomponent, this.field_230708_k_ / 2, func_239562_k_(3), 16711680);

      for(int i = 0; i < aitextcomponent.length; ++i) {
         func_238472_a_(p_230430_1_, this.field_230712_o_, aitextcomponent[i], this.field_230708_k_ / 2, func_239562_k_(5) + i * 12, 16777215);
      }

      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ != 257 && p_231046_1_ != 335 && p_231046_1_ != 256) {
         return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
      } else {
         this.field_230706_i_.displayGuiScreen(this.field_224129_a);
         return true;
      }
   }
}