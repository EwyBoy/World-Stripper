package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsResetNormalWorldScreen extends RealmsScreen {
   private static final ITextComponent field_243144_a = new TranslationTextComponent("mco.reset.world.seed");
   private static final ITextComponent[] field_243145_b = new ITextComponent[]{new TranslationTextComponent("generator.default"), new TranslationTextComponent("generator.flat"), new TranslationTextComponent("generator.large_biomes"), new TranslationTextComponent("generator.amplified")};
   private final RealmsResetWorldScreen field_224354_b;
   private RealmsLabel field_224355_c;
   private TextFieldWidget field_224356_d;
   private Boolean field_224357_e = true;
   private Integer field_224358_f = 0;
   private ITextComponent field_224365_m;

   public RealmsResetNormalWorldScreen(RealmsResetWorldScreen p_i232214_1_, ITextComponent p_i232214_2_) {
      this.field_224354_b = p_i232214_1_;
      this.field_224365_m = p_i232214_2_;
   }

   public void func_231023_e_() {
      this.field_224356_d.tick();
      super.func_231023_e_();
   }

   public void func_231160_c_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      this.field_224355_c = new RealmsLabel(new TranslationTextComponent("mco.reset.world.generate"), this.field_230708_k_ / 2, 17, 16777215);
      this.func_230481_d_(this.field_224355_c);
      this.field_224356_d = new TextFieldWidget(this.field_230706_i_.fontRenderer, this.field_230708_k_ / 2 - 100, func_239562_k_(2), 200, 20, (TextFieldWidget)null, new TranslationTextComponent("mco.reset.world.seed"));
      this.field_224356_d.setMaxStringLength(32);
      this.func_230481_d_(this.field_224356_d);
      this.setFocusedDefault(this.field_224356_d);
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 102, func_239562_k_(4), 205, 20, this.func_237937_g_(), (p_237936_1_) -> {
         this.field_224358_f = (this.field_224358_f + 1) % field_243145_b.length;
         p_237936_1_.func_238482_a_(this.func_237937_g_());
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 102, func_239562_k_(6) - 2, 205, 20, this.func_237938_j_(), (p_237935_1_) -> {
         this.field_224357_e = !this.field_224357_e;
         p_237935_1_.func_238482_a_(this.func_237938_j_());
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 102, func_239562_k_(12), 97, 20, this.field_224365_m, (p_237934_1_) -> {
         this.field_224354_b.func_224438_a(new RealmsResetWorldScreen.ResetWorldInfo(this.field_224356_d.getText(), this.field_224358_f, this.field_224357_e));
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 8, func_239562_k_(12), 97, 20, DialogTexts.field_240637_h_, (p_237933_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.field_224354_b);
      }));
      this.func_231411_u_();
   }

   public void func_231164_f_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(false);
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ == 256) {
         this.field_230706_i_.displayGuiScreen(this.field_224354_b);
         return true;
      } else {
         return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
      }
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      this.field_224355_c.func_239560_a_(this, p_230430_1_);
      this.field_230712_o_.func_243248_b(p_230430_1_, field_243144_a, (float)(this.field_230708_k_ / 2 - 100), (float)func_239562_k_(1), 10526880);
      this.field_224356_d.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   private ITextComponent func_237937_g_() {
      return (new TranslationTextComponent("selectWorld.mapType")).func_240702_b_(" ").func_230529_a_(field_243145_b[this.field_224358_f]);
   }

   private ITextComponent func_237938_j_() {
      return DialogTexts.func_244281_a(new TranslationTextComponent("selectWorld.mapFeatures"), this.field_224357_e);
   }
}