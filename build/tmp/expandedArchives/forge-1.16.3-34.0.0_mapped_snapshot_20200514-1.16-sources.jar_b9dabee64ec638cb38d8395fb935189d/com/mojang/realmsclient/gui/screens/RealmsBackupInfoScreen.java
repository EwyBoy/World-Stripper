package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.dto.Backup;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsBackupInfoScreen extends RealmsScreen {
   private final Screen field_224047_c;
   private final Backup field_224049_e;
   private RealmsBackupInfoScreen.BackupInfoList field_224051_g;

   public RealmsBackupInfoScreen(Screen p_i232197_1_, Backup p_i232197_2_) {
      this.field_224047_c = p_i232197_1_;
      this.field_224049_e = p_i232197_2_;
   }

   public void func_231023_e_() {
   }

   public void func_231160_c_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 4 + 120 + 24, 200, 20, DialogTexts.field_240637_h_, (p_237731_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.field_224047_c);
      }));
      this.field_224051_g = new RealmsBackupInfoScreen.BackupInfoList(this.field_230706_i_);
      this.func_230481_d_(this.field_224051_g);
      this.func_212932_b(this.field_224051_g);
   }

   public void func_231164_f_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(false);
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ == 256) {
         this.field_230706_i_.displayGuiScreen(this.field_224047_c);
         return true;
      } else {
         return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
      }
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      func_238471_a_(p_230430_1_, this.field_230712_o_, "Changes from last backup", this.field_230708_k_ / 2, 10, 16777215);
      this.field_224051_g.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   private ITextComponent func_237733_a_(String p_237733_1_, String p_237733_2_) {
      String s = p_237733_1_.toLowerCase(Locale.ROOT);
      if (s.contains("game") && s.contains("mode")) {
         return this.func_237735_b_(p_237733_2_);
      } else {
         return (ITextComponent)(s.contains("game") && s.contains("difficulty") ? this.func_237732_a_(p_237733_2_) : new StringTextComponent(p_237733_2_));
      }
   }

   private ITextComponent func_237732_a_(String p_237732_1_) {
      try {
         return RealmsSlotOptionsScreen.field_238035_a_[Integer.parseInt(p_237732_1_)];
      } catch (Exception exception) {
         return new StringTextComponent("UNKNOWN");
      }
   }

   private ITextComponent func_237735_b_(String p_237735_1_) {
      try {
         return RealmsSlotOptionsScreen.field_238036_b_[Integer.parseInt(p_237735_1_)];
      } catch (Exception exception) {
         return new StringTextComponent("UNKNOWN");
      }
   }

   @OnlyIn(Dist.CLIENT)
   class BackupInfoEntry extends ExtendedList.AbstractListEntry<RealmsBackupInfoScreen.BackupInfoEntry> {
      private final String field_237738_b_;
      private final String field_237739_c_;

      public BackupInfoEntry(String p_i232199_2_, String p_i232199_3_) {
         this.field_237738_b_ = p_i232199_2_;
         this.field_237739_c_ = p_i232199_3_;
      }

      public void func_230432_a_(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
         FontRenderer fontrenderer = RealmsBackupInfoScreen.this.field_230706_i_.fontRenderer;
         AbstractGui.func_238476_c_(p_230432_1_, fontrenderer, this.field_237738_b_, p_230432_4_, p_230432_3_, 10526880);
         AbstractGui.func_238475_b_(p_230432_1_, fontrenderer, RealmsBackupInfoScreen.this.func_237733_a_(this.field_237738_b_, this.field_237739_c_), p_230432_4_, p_230432_3_ + 12, 16777215);
      }
   }

   @OnlyIn(Dist.CLIENT)
   class BackupInfoList extends ExtendedList<RealmsBackupInfoScreen.BackupInfoEntry> {
      public BackupInfoList(Minecraft p_i232198_2_) {
         super(p_i232198_2_, RealmsBackupInfoScreen.this.field_230708_k_, RealmsBackupInfoScreen.this.field_230709_l_, 32, RealmsBackupInfoScreen.this.field_230709_l_ - 64, 36);
         this.func_230943_a_(false);
         if (RealmsBackupInfoScreen.this.field_224049_e.field_230557_e_ != null) {
            RealmsBackupInfoScreen.this.field_224049_e.field_230557_e_.forEach((p_237736_1_, p_237736_2_) -> {
               this.func_230513_b_(RealmsBackupInfoScreen.this.new BackupInfoEntry(p_237736_1_, p_237736_2_));
            });
         }

      }
   }
}