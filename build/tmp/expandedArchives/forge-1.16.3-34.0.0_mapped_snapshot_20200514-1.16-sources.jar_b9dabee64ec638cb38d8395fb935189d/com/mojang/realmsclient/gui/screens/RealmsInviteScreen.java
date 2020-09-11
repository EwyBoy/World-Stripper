package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import javax.annotation.Nullable;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsInviteScreen extends RealmsScreen {
   private static final Logger field_224213_a = LogManager.getLogger();
   private static final ITextComponent field_243118_b = new TranslationTextComponent("mco.configure.world.invite.profile.name");
   private static final ITextComponent field_243119_c = new TranslationTextComponent("mco.configure.world.players.error");
   private TextFieldWidget field_224214_b;
   private final RealmsServer field_224215_c;
   private final RealmsConfigureWorldScreen field_224216_d;
   private final Screen field_224217_e;
   @Nullable
   private ITextComponent field_224222_j;

   public RealmsInviteScreen(RealmsConfigureWorldScreen p_i232207_1_, Screen p_i232207_2_, RealmsServer p_i232207_3_) {
      this.field_224216_d = p_i232207_1_;
      this.field_224217_e = p_i232207_2_;
      this.field_224215_c = p_i232207_3_;
   }

   public void func_231023_e_() {
      this.field_224214_b.tick();
   }

   public void func_231160_c_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      this.field_224214_b = new TextFieldWidget(this.field_230706_i_.fontRenderer, this.field_230708_k_ / 2 - 100, func_239562_k_(2), 200, 20, (TextFieldWidget)null, new TranslationTextComponent("mco.configure.world.invite.profile.name"));
      this.func_230481_d_(this.field_224214_b);
      this.setFocusedDefault(this.field_224214_b);
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, func_239562_k_(10), 200, 20, new TranslationTextComponent("mco.configure.world.buttons.invite"), (p_237844_1_) -> {
         this.func_224211_a();
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, func_239562_k_(12), 200, 20, DialogTexts.field_240633_d_, (p_237843_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.field_224217_e);
      }));
   }

   public void func_231164_f_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(false);
   }

   private void func_224211_a() {
      RealmsClient realmsclient = RealmsClient.func_224911_a();
      if (this.field_224214_b.getText() != null && !this.field_224214_b.getText().isEmpty()) {
         try {
            RealmsServer realmsserver = realmsclient.func_224910_b(this.field_224215_c.field_230582_a_, this.field_224214_b.getText().trim());
            if (realmsserver != null) {
               this.field_224215_c.field_230589_h_ = realmsserver.field_230589_h_;
               this.field_230706_i_.displayGuiScreen(new RealmsPlayerScreen(this.field_224216_d, this.field_224215_c));
            } else {
               this.func_224209_a(field_243119_c);
            }
         } catch (Exception exception) {
            field_224213_a.error("Couldn't invite user");
            this.func_224209_a(field_243119_c);
         }

      } else {
         this.func_224209_a(field_243119_c);
      }
   }

   private void func_224209_a(ITextComponent p_224209_1_) {
      this.field_224222_j = p_224209_1_;
      RealmsNarratorHelper.func_239550_a_(p_224209_1_.getString());
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ == 256) {
         this.field_230706_i_.displayGuiScreen(this.field_224217_e);
         return true;
      } else {
         return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
      }
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      this.field_230712_o_.func_243248_b(p_230430_1_, field_243118_b, (float)(this.field_230708_k_ / 2 - 100), (float)func_239562_k_(1), 10526880);
      if (this.field_224222_j != null) {
         func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_224222_j, this.field_230708_k_ / 2, func_239562_k_(5), 16711680);
      }

      this.field_224214_b.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }
}