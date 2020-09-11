package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.PendingInvite;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.ListButton;
import com.mojang.realmsclient.util.RealmsTextureManager;
import com.mojang.realmsclient.util.RealmsUtil;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsPendingInvitesScreen extends RealmsScreen {
   private static final Logger field_224333_a = LogManager.getLogger();
   private static final ResourceLocation field_237863_b_ = new ResourceLocation("realms", "textures/gui/realms/accept_icon.png");
   private static final ResourceLocation field_237864_c_ = new ResourceLocation("realms", "textures/gui/realms/reject_icon.png");
   private static final ITextComponent field_243124_p = new TranslationTextComponent("mco.invites.nopending");
   private static final ITextComponent field_243125_q = new TranslationTextComponent("mco.invites.button.accept");
   private static final ITextComponent field_243126_r = new TranslationTextComponent("mco.invites.button.reject");
   private final Screen field_224334_b;
   @Nullable
   private ITextComponent field_224335_c;
   private boolean field_224336_d;
   private RealmsPendingInvitesScreen.InvitationList field_224337_e;
   private RealmsLabel field_224338_f;
   private int field_224339_g = -1;
   private Button field_224340_h;
   private Button field_224341_i;

   public RealmsPendingInvitesScreen(Screen p_i232211_1_) {
      this.field_224334_b = p_i232211_1_;
   }

   public void func_231160_c_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      this.field_224337_e = new RealmsPendingInvitesScreen.InvitationList();
      (new Thread("Realms-pending-invitations-fetcher") {
         public void run() {
            RealmsClient realmsclient = RealmsClient.func_224911_a();

            try {
               List<PendingInvite> list = realmsclient.func_224919_k().field_230569_a_;
               List<RealmsPendingInvitesScreen.InvitationEntry> list1 = list.stream().map((p_225146_1_) -> {
                  return RealmsPendingInvitesScreen.this.new InvitationEntry(p_225146_1_);
               }).collect(Collectors.toList());
               RealmsPendingInvitesScreen.this.field_230706_i_.execute(() -> {
                  RealmsPendingInvitesScreen.this.field_224337_e.func_230942_a_(list1);
               });
            } catch (RealmsServiceException realmsserviceexception) {
               RealmsPendingInvitesScreen.field_224333_a.error("Couldn't list invites");
            } finally {
               RealmsPendingInvitesScreen.this.field_224336_d = true;
            }

         }
      }).start();
      this.func_230481_d_(this.field_224337_e);
      this.field_224340_h = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 174, this.field_230709_l_ - 32, 100, 20, new TranslationTextComponent("mco.invites.button.accept"), (p_237878_1_) -> {
         this.func_224329_c(this.field_224339_g);
         this.field_224339_g = -1;
         this.func_224331_b();
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 50, this.field_230709_l_ - 32, 100, 20, DialogTexts.field_240632_c_, (p_237875_1_) -> {
         this.field_230706_i_.displayGuiScreen(new RealmsMainScreen(this.field_224334_b));
      }));
      this.field_224341_i = this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 74, this.field_230709_l_ - 32, 100, 20, new TranslationTextComponent("mco.invites.button.reject"), (p_237871_1_) -> {
         this.func_224321_b(this.field_224339_g);
         this.field_224339_g = -1;
         this.func_224331_b();
      }));
      this.field_224338_f = new RealmsLabel(new TranslationTextComponent("mco.invites.title"), this.field_230708_k_ / 2, 12, 16777215);
      this.func_230481_d_(this.field_224338_f);
      this.func_231411_u_();
      this.func_224331_b();
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ == 256) {
         this.field_230706_i_.displayGuiScreen(new RealmsMainScreen(this.field_224334_b));
         return true;
      } else {
         return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
      }
   }

   private void func_224318_a(int p_224318_1_) {
      this.field_224337_e.func_223872_a(p_224318_1_);
   }

   private void func_224321_b(final int p_224321_1_) {
      if (p_224321_1_ < this.field_224337_e.func_230965_k_()) {
         (new Thread("Realms-reject-invitation") {
            public void run() {
               try {
                  RealmsClient realmsclient = RealmsClient.func_224911_a();
                  realmsclient.func_224913_b((RealmsPendingInvitesScreen.this.field_224337_e.func_231039_at__().get(p_224321_1_)).field_223750_a.field_230563_a_);
                  RealmsPendingInvitesScreen.this.field_230706_i_.execute(() -> {
                     RealmsPendingInvitesScreen.this.func_224318_a(p_224321_1_);
                  });
               } catch (RealmsServiceException realmsserviceexception) {
                  RealmsPendingInvitesScreen.field_224333_a.error("Couldn't reject invite");
               }

            }
         }).start();
      }

   }

   private void func_224329_c(final int p_224329_1_) {
      if (p_224329_1_ < this.field_224337_e.func_230965_k_()) {
         (new Thread("Realms-accept-invitation") {
            public void run() {
               try {
                  RealmsClient realmsclient = RealmsClient.func_224911_a();
                  realmsclient.func_224901_a((RealmsPendingInvitesScreen.this.field_224337_e.func_231039_at__().get(p_224329_1_)).field_223750_a.field_230563_a_);
                  RealmsPendingInvitesScreen.this.field_230706_i_.execute(() -> {
                     RealmsPendingInvitesScreen.this.func_224318_a(p_224329_1_);
                  });
               } catch (RealmsServiceException realmsserviceexception) {
                  RealmsPendingInvitesScreen.field_224333_a.error("Couldn't accept invite");
               }

            }
         }).start();
      }

   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.field_224335_c = null;
      this.func_230446_a_(p_230430_1_);
      this.field_224337_e.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      this.field_224338_f.func_239560_a_(this, p_230430_1_);
      if (this.field_224335_c != null) {
         this.func_237866_a_(p_230430_1_, this.field_224335_c, p_230430_2_, p_230430_3_);
      }

      if (this.field_224337_e.func_230965_k_() == 0 && this.field_224336_d) {
         func_238472_a_(p_230430_1_, this.field_230712_o_, field_243124_p, this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 20, 16777215);
      }

      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   protected void func_237866_a_(MatrixStack p_237866_1_, @Nullable ITextComponent p_237866_2_, int p_237866_3_, int p_237866_4_) {
      if (p_237866_2_ != null) {
         int i = p_237866_3_ + 12;
         int j = p_237866_4_ - 12;
         int k = this.field_230712_o_.func_238414_a_(p_237866_2_);
         this.func_238468_a_(p_237866_1_, i - 3, j - 3, i + k + 3, j + 8 + 3, -1073741824, -1073741824);
         this.field_230712_o_.func_243246_a(p_237866_1_, p_237866_2_, (float)i, (float)j, 16777215);
      }
   }

   private void func_224331_b() {
      this.field_224340_h.field_230694_p_ = this.func_224316_d(this.field_224339_g);
      this.field_224341_i.field_230694_p_ = this.func_224316_d(this.field_224339_g);
   }

   private boolean func_224316_d(int p_224316_1_) {
      return p_224316_1_ != -1;
   }

   @OnlyIn(Dist.CLIENT)
   class InvitationEntry extends ExtendedList.AbstractListEntry<RealmsPendingInvitesScreen.InvitationEntry> {
      private final PendingInvite field_223750_a;
      private final List<ListButton> field_223752_c;

      InvitationEntry(PendingInvite p_i51623_2_) {
         this.field_223750_a = p_i51623_2_;
         this.field_223752_c = Arrays.asList(new RealmsPendingInvitesScreen.InvitationEntry.AcceptButton(), new RealmsPendingInvitesScreen.InvitationEntry.RejectButton());
      }

      public void func_230432_a_(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
         this.func_237893_a_(p_230432_1_, this.field_223750_a, p_230432_4_, p_230432_3_, p_230432_7_, p_230432_8_);
      }

      public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
         ListButton.func_237728_a_(RealmsPendingInvitesScreen.this.field_224337_e, this, this.field_223752_c, p_231044_5_, p_231044_1_, p_231044_3_);
         return true;
      }

      private void func_237893_a_(MatrixStack p_237893_1_, PendingInvite p_237893_2_, int p_237893_3_, int p_237893_4_, int p_237893_5_, int p_237893_6_) {
         RealmsPendingInvitesScreen.this.field_230712_o_.func_238421_b_(p_237893_1_, p_237893_2_.field_230564_b_, (float)(p_237893_3_ + 38), (float)(p_237893_4_ + 1), 16777215);
         RealmsPendingInvitesScreen.this.field_230712_o_.func_238421_b_(p_237893_1_, p_237893_2_.field_230565_c_, (float)(p_237893_3_ + 38), (float)(p_237893_4_ + 12), 7105644);
         RealmsPendingInvitesScreen.this.field_230712_o_.func_238421_b_(p_237893_1_, RealmsUtil.func_238105_a_(p_237893_2_.field_230567_e_), (float)(p_237893_3_ + 38), (float)(p_237893_4_ + 24), 7105644);
         ListButton.func_237727_a_(p_237893_1_, this.field_223752_c, RealmsPendingInvitesScreen.this.field_224337_e, p_237893_3_, p_237893_4_, p_237893_5_, p_237893_6_);
         RealmsTextureManager.func_225205_a(p_237893_2_.field_230566_d_, () -> {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            AbstractGui.func_238466_a_(p_237893_1_, p_237893_3_, p_237893_4_, 32, 32, 8.0F, 8.0F, 8, 8, 64, 64);
            AbstractGui.func_238466_a_(p_237893_1_, p_237893_3_, p_237893_4_, 32, 32, 40.0F, 8.0F, 8, 8, 64, 64);
         });
      }

      @OnlyIn(Dist.CLIENT)
      class AcceptButton extends ListButton {
         AcceptButton() {
            super(15, 15, 215, 5);
         }

         protected void func_230435_a_(MatrixStack p_230435_1_, int p_230435_2_, int p_230435_3_, boolean p_230435_4_) {
            RealmsPendingInvitesScreen.this.field_230706_i_.getTextureManager().bindTexture(RealmsPendingInvitesScreen.field_237863_b_);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            float f = p_230435_4_ ? 19.0F : 0.0F;
            AbstractGui.func_238463_a_(p_230435_1_, p_230435_2_, p_230435_3_, f, 0.0F, 18, 18, 37, 18);
            if (p_230435_4_) {
               RealmsPendingInvitesScreen.this.field_224335_c = RealmsPendingInvitesScreen.field_243125_q;
            }

         }

         public void func_225121_a(int p_225121_1_) {
            RealmsPendingInvitesScreen.this.func_224329_c(p_225121_1_);
         }
      }

      @OnlyIn(Dist.CLIENT)
      class RejectButton extends ListButton {
         RejectButton() {
            super(15, 15, 235, 5);
         }

         protected void func_230435_a_(MatrixStack p_230435_1_, int p_230435_2_, int p_230435_3_, boolean p_230435_4_) {
            RealmsPendingInvitesScreen.this.field_230706_i_.getTextureManager().bindTexture(RealmsPendingInvitesScreen.field_237864_c_);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            float f = p_230435_4_ ? 19.0F : 0.0F;
            AbstractGui.func_238463_a_(p_230435_1_, p_230435_2_, p_230435_3_, f, 0.0F, 18, 18, 37, 18);
            if (p_230435_4_) {
               RealmsPendingInvitesScreen.this.field_224335_c = RealmsPendingInvitesScreen.field_243126_r;
            }

         }

         public void func_225121_a(int p_225121_1_) {
            RealmsPendingInvitesScreen.this.func_224321_b(p_225121_1_);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   class InvitationList extends RealmsObjectSelectionList<RealmsPendingInvitesScreen.InvitationEntry> {
      public InvitationList() {
         super(RealmsPendingInvitesScreen.this.field_230708_k_, RealmsPendingInvitesScreen.this.field_230709_l_, 32, RealmsPendingInvitesScreen.this.field_230709_l_ - 40, 36);
      }

      public void func_223872_a(int p_223872_1_) {
         this.func_230964_j_(p_223872_1_);
      }

      public int func_230945_b_() {
         return this.func_230965_k_() * 36;
      }

      public int func_230949_c_() {
         return 260;
      }

      public boolean func_230971_aw__() {
         return RealmsPendingInvitesScreen.this.func_241217_q_() == this;
      }

      public void func_230433_a_(MatrixStack p_230433_1_) {
         RealmsPendingInvitesScreen.this.func_230446_a_(p_230433_1_);
      }

      public void func_231400_a_(int p_231400_1_) {
         this.func_239561_k_(p_231400_1_);
         if (p_231400_1_ != -1) {
            List<RealmsPendingInvitesScreen.InvitationEntry> list = RealmsPendingInvitesScreen.this.field_224337_e.func_231039_at__();
            PendingInvite pendinginvite = (list.get(p_231400_1_)).field_223750_a;
            String s = I18n.format("narrator.select.list.position", p_231400_1_ + 1, list.size());
            String s1 = RealmsNarratorHelper.func_239552_b_(Arrays.asList(pendinginvite.field_230564_b_, pendinginvite.field_230565_c_, RealmsUtil.func_238105_a_(pendinginvite.field_230567_e_), s));
            RealmsNarratorHelper.func_239550_a_(I18n.format("narrator.select", s1));
         }

         this.func_223873_b(p_231400_1_);
      }

      public void func_223873_b(int p_223873_1_) {
         RealmsPendingInvitesScreen.this.field_224339_g = p_223873_1_;
         RealmsPendingInvitesScreen.this.func_224331_b();
      }

      public void func_241215_a_(@Nullable RealmsPendingInvitesScreen.InvitationEntry p_241215_1_) {
         super.func_241215_a_(p_241215_1_);
         RealmsPendingInvitesScreen.this.field_224339_g = this.func_231039_at__().indexOf(p_241215_1_);
         RealmsPendingInvitesScreen.this.func_224331_b();
      }
   }
}