package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.Backup;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.util.RealmsUtil;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.realms.action.PrepareDownloadRealmsAction;
import net.minecraft.realms.action.RestoringBackupRealmsAction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsBackupScreen extends RealmsScreen {
   private static final Logger field_224114_a = LogManager.getLogger();
   private static final ResourceLocation field_237740_b_ = new ResourceLocation("realms", "textures/gui/realms/plus_icon.png");
   private static final ResourceLocation field_237741_c_ = new ResourceLocation("realms", "textures/gui/realms/restore_icon.png");
   private static final ITextComponent field_243094_p = new TranslationTextComponent("mco.backup.button.restore");
   private static final ITextComponent field_243095_q = new TranslationTextComponent("mco.backup.changes.tooltip");
   private static final ITextComponent field_243096_r = new TranslationTextComponent("mco.configure.world.backup");
   private static final ITextComponent field_243097_s = new TranslationTextComponent("mco.backup.nobackups");
   private static int field_224115_b = -1;
   private final RealmsConfigureWorldScreen field_224116_c;
   private List<Backup> field_224117_d = Collections.emptyList();
   @Nullable
   private ITextComponent field_224118_e;
   private RealmsBackupScreen.BackupObjectSelectionList field_224119_f;
   private int field_224120_g = -1;
   private final int field_224121_h;
   private Button field_224122_i;
   private Button field_224123_j;
   private Button field_224124_k;
   private Boolean field_224125_l = false;
   private final RealmsServer field_224126_m;
   private RealmsLabel field_224127_n;

   public RealmsBackupScreen(RealmsConfigureWorldScreen p_i51777_1_, RealmsServer p_i51777_2_, int p_i51777_3_) {
      this.field_224116_c = p_i51777_1_;
      this.field_224126_m = p_i51777_2_;
      this.field_224121_h = p_i51777_3_;
   }

   public void func_231160_c_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      this.field_224119_f = new RealmsBackupScreen.BackupObjectSelectionList();
      if (field_224115_b != -1) {
         this.field_224119_f.func_230932_a_((double)field_224115_b);
      }

      (new Thread("Realms-fetch-backups") {
         public void run() {
            RealmsClient realmsclient = RealmsClient.func_224911_a();

            try {
               List<Backup> list = realmsclient.func_224923_d(RealmsBackupScreen.this.field_224126_m.field_230582_a_).field_230560_a_;
               RealmsBackupScreen.this.field_230706_i_.execute(() -> {
                  RealmsBackupScreen.this.field_224117_d = list;
                  RealmsBackupScreen.this.field_224125_l = RealmsBackupScreen.this.field_224117_d.isEmpty();
                  RealmsBackupScreen.this.field_224119_f.func_231409_q_();

                  for(Backup backup : RealmsBackupScreen.this.field_224117_d) {
                     RealmsBackupScreen.this.field_224119_f.func_223867_a(backup);
                  }

                  RealmsBackupScreen.this.func_224112_b();
               });
            } catch (RealmsServiceException realmsserviceexception) {
               RealmsBackupScreen.field_224114_a.error("Couldn't request backups", (Throwable)realmsserviceexception);
            }

         }
      }).start();
      this.field_224122_i = this.func_230480_a_(new Button(this.field_230708_k_ - 135, func_239562_k_(1), 120, 20, new TranslationTextComponent("mco.backup.button.download"), (p_237758_1_) -> {
         this.func_224088_g();
      }));
      this.field_224123_j = this.func_230480_a_(new Button(this.field_230708_k_ - 135, func_239562_k_(3), 120, 20, new TranslationTextComponent("mco.backup.button.restore"), (p_237754_1_) -> {
         this.func_224104_b(this.field_224120_g);
      }));
      this.field_224124_k = this.func_230480_a_(new Button(this.field_230708_k_ - 135, func_239562_k_(5), 120, 20, new TranslationTextComponent("mco.backup.changes.tooltip"), (p_237752_1_) -> {
         this.field_230706_i_.displayGuiScreen(new RealmsBackupInfoScreen(this, this.field_224117_d.get(this.field_224120_g)));
         this.field_224120_g = -1;
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ - 100, this.field_230709_l_ - 35, 85, 20, DialogTexts.field_240637_h_, (p_237748_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.field_224116_c);
      }));
      this.func_230481_d_(this.field_224119_f);
      this.field_224127_n = this.func_230481_d_(new RealmsLabel(new TranslationTextComponent("mco.configure.world.backup"), this.field_230708_k_ / 2, 12, 16777215));
      this.func_212932_b(this.field_224119_f);
      this.func_224113_d();
      this.func_231411_u_();
   }

   private void func_224112_b() {
      if (this.field_224117_d.size() > 1) {
         for(int i = 0; i < this.field_224117_d.size() - 1; ++i) {
            Backup backup = this.field_224117_d.get(i);
            Backup backup1 = this.field_224117_d.get(i + 1);
            if (!backup.field_230556_d_.isEmpty() && !backup1.field_230556_d_.isEmpty()) {
               for(String s : backup.field_230556_d_.keySet()) {
                  if (!s.contains("Uploaded") && backup1.field_230556_d_.containsKey(s)) {
                     if (!backup.field_230556_d_.get(s).equals(backup1.field_230556_d_.get(s))) {
                        this.func_224103_a(backup, s);
                     }
                  } else {
                     this.func_224103_a(backup, s);
                  }
               }
            }
         }

      }
   }

   private void func_224103_a(Backup p_224103_1_, String p_224103_2_) {
      if (p_224103_2_.contains("Uploaded")) {
         String s = DateFormat.getDateTimeInstance(3, 3).format(p_224103_1_.field_230554_b_);
         p_224103_1_.field_230557_e_.put(p_224103_2_, s);
         p_224103_1_.func_230752_a_(true);
      } else {
         p_224103_1_.field_230557_e_.put(p_224103_2_, p_224103_1_.field_230556_d_.get(p_224103_2_));
      }

   }

   private void func_224113_d() {
      this.field_224123_j.field_230694_p_ = this.func_224111_f();
      this.field_224124_k.field_230694_p_ = this.func_224096_e();
   }

   private boolean func_224096_e() {
      if (this.field_224120_g == -1) {
         return false;
      } else {
         return !(this.field_224117_d.get(this.field_224120_g)).field_230557_e_.isEmpty();
      }
   }

   private boolean func_224111_f() {
      if (this.field_224120_g == -1) {
         return false;
      } else {
         return !this.field_224126_m.field_230591_j_;
      }
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ == 256) {
         this.field_230706_i_.displayGuiScreen(this.field_224116_c);
         return true;
      } else {
         return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
      }
   }

   private void func_224104_b(int p_224104_1_) {
      if (p_224104_1_ >= 0 && p_224104_1_ < this.field_224117_d.size() && !this.field_224126_m.field_230591_j_) {
         this.field_224120_g = p_224104_1_;
         Date date = (this.field_224117_d.get(p_224104_1_)).field_230554_b_;
         String s = DateFormat.getDateTimeInstance(3, 3).format(date);
         String s1 = RealmsUtil.func_238105_a_(date);
         ITextComponent itextcomponent = new TranslationTextComponent("mco.configure.world.restore.question.line1", s, s1);
         ITextComponent itextcomponent1 = new TranslationTextComponent("mco.configure.world.restore.question.line2");
         this.field_230706_i_.displayGuiScreen(new RealmsLongConfirmationScreen((p_237759_1_) -> {
            if (p_237759_1_) {
               this.func_224097_i();
            } else {
               this.field_224120_g = -1;
               this.field_230706_i_.displayGuiScreen(this);
            }

         }, RealmsLongConfirmationScreen.Type.Warning, itextcomponent, itextcomponent1, true));
      }

   }

   private void func_224088_g() {
      ITextComponent itextcomponent = new TranslationTextComponent("mco.configure.world.restore.download.question.line1");
      ITextComponent itextcomponent1 = new TranslationTextComponent("mco.configure.world.restore.download.question.line2");
      this.field_230706_i_.displayGuiScreen(new RealmsLongConfirmationScreen((p_237755_1_) -> {
         if (p_237755_1_) {
            this.func_224100_h();
         } else {
            this.field_230706_i_.displayGuiScreen(this);
         }

      }, RealmsLongConfirmationScreen.Type.Info, itextcomponent, itextcomponent1, true));
   }

   private void func_224100_h() {
      this.field_230706_i_.displayGuiScreen(new RealmsLongRunningMcoTaskScreen(this.field_224116_c.func_224407_b(), new PrepareDownloadRealmsAction(this.field_224126_m.field_230582_a_, this.field_224121_h, this.field_224126_m.field_230584_c_ + " (" + this.field_224126_m.field_230590_i_.get(this.field_224126_m.field_230595_n_).func_230787_a_(this.field_224126_m.field_230595_n_) + ")", this)));
   }

   private void func_224097_i() {
      Backup backup = this.field_224117_d.get(this.field_224120_g);
      this.field_224120_g = -1;
      this.field_230706_i_.displayGuiScreen(new RealmsLongRunningMcoTaskScreen(this.field_224116_c.func_224407_b(), new RestoringBackupRealmsAction(backup, this.field_224126_m.field_230582_a_, this.field_224116_c)));
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.field_224118_e = null;
      this.func_230446_a_(p_230430_1_);
      this.field_224119_f.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      this.field_224127_n.func_239560_a_(this, p_230430_1_);
      this.field_230712_o_.func_243248_b(p_230430_1_, field_243096_r, (float)((this.field_230708_k_ - 150) / 2 - 90), 20.0F, 10526880);
      if (this.field_224125_l) {
         this.field_230712_o_.func_243248_b(p_230430_1_, field_243097_s, 20.0F, (float)(this.field_230709_l_ / 2 - 10), 16777215);
      }

      this.field_224122_i.field_230693_o_ = !this.field_224125_l;
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      if (this.field_224118_e != null) {
         this.func_237744_a_(p_230430_1_, this.field_224118_e, p_230430_2_, p_230430_3_);
      }

   }

   protected void func_237744_a_(MatrixStack p_237744_1_, @Nullable ITextComponent p_237744_2_, int p_237744_3_, int p_237744_4_) {
      if (p_237744_2_ != null) {
         int i = p_237744_3_ + 12;
         int j = p_237744_4_ - 12;
         int k = this.field_230712_o_.func_238414_a_(p_237744_2_);
         this.func_238468_a_(p_237744_1_, i - 3, j - 3, i + k + 3, j + 8 + 3, -1073741824, -1073741824);
         this.field_230712_o_.func_243246_a(p_237744_1_, p_237744_2_, (float)i, (float)j, 16777215);
      }
   }

   @OnlyIn(Dist.CLIENT)
   class BackupObjectSelectionList extends RealmsObjectSelectionList<RealmsBackupScreen.BackupObjectSelectionListEntry> {
      public BackupObjectSelectionList() {
         super(RealmsBackupScreen.this.field_230708_k_ - 150, RealmsBackupScreen.this.field_230709_l_, 32, RealmsBackupScreen.this.field_230709_l_ - 15, 36);
      }

      public void func_223867_a(Backup p_223867_1_) {
         this.func_230513_b_(RealmsBackupScreen.this.new BackupObjectSelectionListEntry(p_223867_1_));
      }

      public int func_230949_c_() {
         return (int)((double)this.field_230670_d_ * 0.93D);
      }

      public boolean func_230971_aw__() {
         return RealmsBackupScreen.this.func_241217_q_() == this;
      }

      public int func_230945_b_() {
         return this.func_230965_k_() * 36;
      }

      public void func_230433_a_(MatrixStack p_230433_1_) {
         RealmsBackupScreen.this.func_230446_a_(p_230433_1_);
      }

      public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
         if (p_231044_5_ != 0) {
            return false;
         } else if (p_231044_1_ < (double)this.func_230952_d_() && p_231044_3_ >= (double)this.field_230672_i_ && p_231044_3_ <= (double)this.field_230673_j_) {
            int i = this.field_230670_d_ / 2 - 92;
            int j = this.field_230670_d_;
            int k = (int)Math.floor(p_231044_3_ - (double)this.field_230672_i_) - this.field_230677_n_ + (int)this.func_230966_l_();
            int l = k / this.field_230669_c_;
            if (p_231044_1_ >= (double)i && p_231044_1_ <= (double)j && l >= 0 && k >= 0 && l < this.func_230965_k_()) {
               this.func_231400_a_(l);
               this.func_231401_a_(k, l, p_231044_1_, p_231044_3_, this.field_230670_d_);
            }

            return true;
         } else {
            return false;
         }
      }

      public int func_230952_d_() {
         return this.field_230670_d_ - 5;
      }

      public void func_231401_a_(int p_231401_1_, int p_231401_2_, double p_231401_3_, double p_231401_5_, int p_231401_7_) {
         int i = this.field_230670_d_ - 35;
         int j = p_231401_2_ * this.field_230669_c_ + 36 - (int)this.func_230966_l_();
         int k = i + 10;
         int l = j - 3;
         if (p_231401_3_ >= (double)i && p_231401_3_ <= (double)(i + 9) && p_231401_5_ >= (double)j && p_231401_5_ <= (double)(j + 9)) {
            if (!(RealmsBackupScreen.this.field_224117_d.get(p_231401_2_)).field_230557_e_.isEmpty()) {
               RealmsBackupScreen.this.field_224120_g = -1;
               RealmsBackupScreen.field_224115_b = (int)this.func_230966_l_();
               this.field_230668_b_.displayGuiScreen(new RealmsBackupInfoScreen(RealmsBackupScreen.this, RealmsBackupScreen.this.field_224117_d.get(p_231401_2_)));
            }
         } else if (p_231401_3_ >= (double)k && p_231401_3_ < (double)(k + 13) && p_231401_5_ >= (double)l && p_231401_5_ < (double)(l + 15)) {
            RealmsBackupScreen.field_224115_b = (int)this.func_230966_l_();
            RealmsBackupScreen.this.func_224104_b(p_231401_2_);
         }

      }

      public void func_231400_a_(int p_231400_1_) {
         this.func_239561_k_(p_231400_1_);
         if (p_231400_1_ != -1) {
            RealmsNarratorHelper.func_239550_a_(I18n.format("narrator.select", (RealmsBackupScreen.this.field_224117_d.get(p_231400_1_)).field_230554_b_.toString()));
         }

         this.func_223866_a(p_231400_1_);
      }

      public void func_223866_a(int p_223866_1_) {
         RealmsBackupScreen.this.field_224120_g = p_223866_1_;
         RealmsBackupScreen.this.func_224113_d();
      }

      public void func_241215_a_(@Nullable RealmsBackupScreen.BackupObjectSelectionListEntry p_241215_1_) {
         super.func_241215_a_(p_241215_1_);
         RealmsBackupScreen.this.field_224120_g = this.func_231039_at__().indexOf(p_241215_1_);
         RealmsBackupScreen.this.func_224113_d();
      }
   }

   @OnlyIn(Dist.CLIENT)
   class BackupObjectSelectionListEntry extends ExtendedList.AbstractListEntry<RealmsBackupScreen.BackupObjectSelectionListEntry> {
      private final Backup field_237765_b_;

      public BackupObjectSelectionListEntry(Backup p_i51657_2_) {
         this.field_237765_b_ = p_i51657_2_;
      }

      public void func_230432_a_(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
         this.func_237767_a_(p_230432_1_, this.field_237765_b_, p_230432_4_ - 40, p_230432_3_, p_230432_7_, p_230432_8_);
      }

      private void func_237767_a_(MatrixStack p_237767_1_, Backup p_237767_2_, int p_237767_3_, int p_237767_4_, int p_237767_5_, int p_237767_6_) {
         int i = p_237767_2_.func_230749_a_() ? -8388737 : 16777215;
         RealmsBackupScreen.this.field_230712_o_.func_238421_b_(p_237767_1_, "Backup (" + RealmsUtil.func_238105_a_(p_237767_2_.field_230554_b_) + ")", (float)(p_237767_3_ + 40), (float)(p_237767_4_ + 1), i);
         RealmsBackupScreen.this.field_230712_o_.func_238421_b_(p_237767_1_, this.func_223738_a(p_237767_2_.field_230554_b_), (float)(p_237767_3_ + 40), (float)(p_237767_4_ + 12), 5000268);
         int j = RealmsBackupScreen.this.field_230708_k_ - 175;
         int k = -3;
         int l = j - 10;
         int i1 = 0;
         if (!RealmsBackupScreen.this.field_224126_m.field_230591_j_) {
            this.func_237766_a_(p_237767_1_, j, p_237767_4_ + -3, p_237767_5_, p_237767_6_);
         }

         if (!p_237767_2_.field_230557_e_.isEmpty()) {
            this.func_237768_b_(p_237767_1_, l, p_237767_4_ + 0, p_237767_5_, p_237767_6_);
         }

      }

      private String func_223738_a(Date p_223738_1_) {
         return DateFormat.getDateTimeInstance(3, 3).format(p_223738_1_);
      }

      private void func_237766_a_(MatrixStack p_237766_1_, int p_237766_2_, int p_237766_3_, int p_237766_4_, int p_237766_5_) {
         boolean flag = p_237766_4_ >= p_237766_2_ && p_237766_4_ <= p_237766_2_ + 12 && p_237766_5_ >= p_237766_3_ && p_237766_5_ <= p_237766_3_ + 14 && p_237766_5_ < RealmsBackupScreen.this.field_230709_l_ - 15 && p_237766_5_ > 32;
         RealmsBackupScreen.this.field_230706_i_.getTextureManager().bindTexture(RealmsBackupScreen.field_237741_c_);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.pushMatrix();
         RenderSystem.scalef(0.5F, 0.5F, 0.5F);
         float f = flag ? 28.0F : 0.0F;
         AbstractGui.func_238463_a_(p_237766_1_, p_237766_2_ * 2, p_237766_3_ * 2, 0.0F, f, 23, 28, 23, 56);
         RenderSystem.popMatrix();
         if (flag) {
            RealmsBackupScreen.this.field_224118_e = RealmsBackupScreen.field_243094_p;
         }

      }

      private void func_237768_b_(MatrixStack p_237768_1_, int p_237768_2_, int p_237768_3_, int p_237768_4_, int p_237768_5_) {
         boolean flag = p_237768_4_ >= p_237768_2_ && p_237768_4_ <= p_237768_2_ + 8 && p_237768_5_ >= p_237768_3_ && p_237768_5_ <= p_237768_3_ + 8 && p_237768_5_ < RealmsBackupScreen.this.field_230709_l_ - 15 && p_237768_5_ > 32;
         RealmsBackupScreen.this.field_230706_i_.getTextureManager().bindTexture(RealmsBackupScreen.field_237740_b_);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.pushMatrix();
         RenderSystem.scalef(0.5F, 0.5F, 0.5F);
         float f = flag ? 15.0F : 0.0F;
         AbstractGui.func_238463_a_(p_237768_1_, p_237768_2_ * 2, p_237768_3_ * 2, 0.0F, f, 15, 15, 15, 30);
         RenderSystem.popMatrix();
         if (flag) {
            RealmsBackupScreen.this.field_224118_e = RealmsBackupScreen.field_243095_q;
         }

      }
   }
}