package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.dto.RealmsServer;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.realms.action.CreateWorldRealmsAction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsCreateRealmScreen extends RealmsScreen {
   private static final ITextComponent field_243116_a = new TranslationTextComponent("mco.configure.world.name");
   private static final ITextComponent field_243117_b = new TranslationTextComponent("mco.configure.world.description");
   private final RealmsServer field_224135_a;
   private final RealmsMainScreen field_224136_b;
   private TextFieldWidget field_224137_c;
   private TextFieldWidget field_224138_d;
   private Button field_224139_e;
   private RealmsLabel field_224140_f;

   public RealmsCreateRealmScreen(RealmsServer p_i51772_1_, RealmsMainScreen p_i51772_2_) {
      this.field_224135_a = p_i51772_1_;
      this.field_224136_b = p_i51772_2_;
   }

   public void func_231023_e_() {
      if (this.field_224137_c != null) {
         this.field_224137_c.tick();
      }

      if (this.field_224138_d != null) {
         this.field_224138_d.tick();
      }

   }

   public void func_231160_c_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      this.field_224139_e = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 4 + 120 + 17, 97, 20, new TranslationTextComponent("mco.create.world"), (p_237828_1_) -> {
         this.func_224132_a();
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 5, this.field_230709_l_ / 4 + 120 + 17, 95, 20, DialogTexts.field_240633_d_, (p_237827_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.field_224136_b);
      }));
      this.field_224139_e.field_230693_o_ = false;
      this.field_224137_c = new TextFieldWidget(this.field_230706_i_.fontRenderer, this.field_230708_k_ / 2 - 100, 65, 200, 20, (TextFieldWidget)null, new TranslationTextComponent("mco.configure.world.name"));
      this.func_230481_d_(this.field_224137_c);
      this.setFocusedDefault(this.field_224137_c);
      this.field_224138_d = new TextFieldWidget(this.field_230706_i_.fontRenderer, this.field_230708_k_ / 2 - 100, 115, 200, 20, (TextFieldWidget)null, new TranslationTextComponent("mco.configure.world.description"));
      this.func_230481_d_(this.field_224138_d);
      this.field_224140_f = new RealmsLabel(new TranslationTextComponent("mco.selectServer.create"), this.field_230708_k_ / 2, 11, 16777215);
      this.func_230481_d_(this.field_224140_f);
      this.func_231411_u_();
   }

   public void func_231164_f_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(false);
   }

   public boolean func_231042_a_(char p_231042_1_, int p_231042_2_) {
      boolean flag = super.func_231042_a_(p_231042_1_, p_231042_2_);
      this.field_224139_e.field_230693_o_ = this.func_224133_b();
      return flag;
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ == 256) {
         this.field_230706_i_.displayGuiScreen(this.field_224136_b);
         return true;
      } else {
         boolean flag = super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
         this.field_224139_e.field_230693_o_ = this.func_224133_b();
         return flag;
      }
   }

   private void func_224132_a() {
      if (this.func_224133_b()) {
         RealmsResetWorldScreen realmsresetworldscreen = new RealmsResetWorldScreen(this.field_224136_b, this.field_224135_a, new TranslationTextComponent("mco.selectServer.create"), new TranslationTextComponent("mco.create.world.subtitle"), 10526880, new TranslationTextComponent("mco.create.world.skip"), () -> {
            this.field_230706_i_.displayGuiScreen(this.field_224136_b.func_223942_f());
         }, () -> {
            this.field_230706_i_.displayGuiScreen(this.field_224136_b.func_223942_f());
         });
         realmsresetworldscreen.func_224432_a(new TranslationTextComponent("mco.create.world.reset.title"));
         this.field_230706_i_.displayGuiScreen(new RealmsLongRunningMcoTaskScreen(this.field_224136_b, new CreateWorldRealmsAction(this.field_224135_a.field_230582_a_, this.field_224137_c.getText(), this.field_224138_d.getText(), realmsresetworldscreen)));
      }

   }

   private boolean func_224133_b() {
      return !this.field_224137_c.getText().trim().isEmpty();
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      this.field_224140_f.func_239560_a_(this, p_230430_1_);
      this.field_230712_o_.func_243248_b(p_230430_1_, field_243116_a, (float)(this.field_230708_k_ / 2 - 100), 52.0F, 10526880);
      this.field_230712_o_.func_243248_b(p_230430_1_, field_243117_b, (float)(this.field_230708_k_ / 2 - 100), 102.0F, 10526880);
      if (this.field_224137_c != null) {
         this.field_224137_c.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      }

      if (this.field_224138_d != null) {
         this.field_224138_d.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      }

      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }
}