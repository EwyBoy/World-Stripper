package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.dto.RealmsServer;
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
public class RealmsSettingsScreen extends RealmsScreen {
   private static final ITextComponent field_243169_a = new TranslationTextComponent("mco.configure.world.name");
   private static final ITextComponent field_243170_b = new TranslationTextComponent("mco.configure.world.description");
   private final RealmsConfigureWorldScreen field_224565_a;
   private final RealmsServer field_224566_b;
   private Button field_224568_d;
   private TextFieldWidget field_224569_e;
   private TextFieldWidget field_224570_f;
   private RealmsLabel field_224571_g;

   public RealmsSettingsScreen(RealmsConfigureWorldScreen p_i51751_1_, RealmsServer p_i51751_2_) {
      this.field_224565_a = p_i51751_1_;
      this.field_224566_b = p_i51751_2_;
   }

   public void func_231023_e_() {
      this.field_224570_f.tick();
      this.field_224569_e.tick();
      this.field_224568_d.field_230693_o_ = !this.field_224570_f.getText().trim().isEmpty();
   }

   public void func_231160_c_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      int i = this.field_230708_k_ / 2 - 106;
      this.field_224568_d = this.func_230480_a_(new Button(i - 2, func_239562_k_(12), 106, 20, new TranslationTextComponent("mco.configure.world.buttons.done"), (p_238033_1_) -> {
         this.func_224563_a();
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 2, func_239562_k_(12), 106, 20, DialogTexts.field_240633_d_, (p_238032_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.field_224565_a);
      }));
      String s = this.field_224566_b.field_230586_e_ == RealmsServer.Status.OPEN ? "mco.configure.world.buttons.close" : "mco.configure.world.buttons.open";
      Button button = new Button(this.field_230708_k_ / 2 - 53, func_239562_k_(0), 106, 20, new TranslationTextComponent(s), (p_238031_1_) -> {
         if (this.field_224566_b.field_230586_e_ == RealmsServer.Status.OPEN) {
            ITextComponent itextcomponent = new TranslationTextComponent("mco.configure.world.close.question.line1");
            ITextComponent itextcomponent1 = new TranslationTextComponent("mco.configure.world.close.question.line2");
            this.field_230706_i_.displayGuiScreen(new RealmsLongConfirmationScreen((p_238034_1_) -> {
               if (p_238034_1_) {
                  this.field_224565_a.func_237800_a_(this);
               } else {
                  this.field_230706_i_.displayGuiScreen(this);
               }

            }, RealmsLongConfirmationScreen.Type.Info, itextcomponent, itextcomponent1, true));
         } else {
            this.field_224565_a.func_237802_a_(false, this);
         }

      });
      this.func_230480_a_(button);
      this.field_224570_f = new TextFieldWidget(this.field_230706_i_.fontRenderer, i, func_239562_k_(4), 212, 20, (TextFieldWidget)null, new TranslationTextComponent("mco.configure.world.name"));
      this.field_224570_f.setMaxStringLength(32);
      this.field_224570_f.setText(this.field_224566_b.func_230775_b_());
      this.func_230481_d_(this.field_224570_f);
      this.func_212932_b(this.field_224570_f);
      this.field_224569_e = new TextFieldWidget(this.field_230706_i_.fontRenderer, i, func_239562_k_(8), 212, 20, (TextFieldWidget)null, new TranslationTextComponent("mco.configure.world.description"));
      this.field_224569_e.setMaxStringLength(32);
      this.field_224569_e.setText(this.field_224566_b.func_230768_a_());
      this.func_230481_d_(this.field_224569_e);
      this.field_224571_g = this.func_230481_d_(new RealmsLabel(new TranslationTextComponent("mco.configure.world.settings.title"), this.field_230708_k_ / 2, 17, 16777215));
      this.func_231411_u_();
   }

   public void func_231164_f_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(false);
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ == 256) {
         this.field_230706_i_.displayGuiScreen(this.field_224565_a);
         return true;
      } else {
         return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
      }
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      this.field_224571_g.func_239560_a_(this, p_230430_1_);
      this.field_230712_o_.func_243248_b(p_230430_1_, field_243169_a, (float)(this.field_230708_k_ / 2 - 106), (float)func_239562_k_(3), 10526880);
      this.field_230712_o_.func_243248_b(p_230430_1_, field_243170_b, (float)(this.field_230708_k_ / 2 - 106), (float)func_239562_k_(7), 10526880);
      this.field_224570_f.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      this.field_224569_e.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   public void func_224563_a() {
      this.field_224565_a.func_224410_a(this.field_224570_f.getText(), this.field_224569_e.getText());
   }
}