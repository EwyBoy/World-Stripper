package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.Subscription;
import com.mojang.realmsclient.exception.RealmsServiceException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.Util;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsSubscriptionInfoScreen extends RealmsScreen {
   private static final Logger field_224579_a = LogManager.getLogger();
   private static final ITextComponent field_243173_b = new TranslationTextComponent("mco.configure.world.subscription.title");
   private static final ITextComponent field_243174_c = new TranslationTextComponent("mco.configure.world.subscription.start");
   private static final ITextComponent field_243175_p = new TranslationTextComponent("mco.configure.world.subscription.timeleft");
   private static final ITextComponent field_243176_q = new TranslationTextComponent("mco.configure.world.subscription.recurring.daysleft");
   private static final ITextComponent field_243177_r = new TranslationTextComponent("mco.configure.world.subscription.expired");
   private static final ITextComponent field_243178_s = new TranslationTextComponent("mco.configure.world.subscription.less_than_a_day");
   private static final ITextComponent field_243179_t = new TranslationTextComponent("mco.configure.world.subscription.month");
   private static final ITextComponent field_243180_u = new TranslationTextComponent("mco.configure.world.subscription.months");
   private static final ITextComponent field_243181_v = new TranslationTextComponent("mco.configure.world.subscription.day");
   private static final ITextComponent field_243182_w = new TranslationTextComponent("mco.configure.world.subscription.days");
   private final Screen field_224580_b;
   private final RealmsServer field_224581_c;
   private final Screen field_224582_d;
   private ITextComponent field_224590_l;
   private String field_224591_m;
   private Subscription.Type field_224592_n;

   public RealmsSubscriptionInfoScreen(Screen p_i232223_1_, RealmsServer p_i232223_2_, Screen p_i232223_3_) {
      this.field_224580_b = p_i232223_1_;
      this.field_224581_c = p_i232223_2_;
      this.field_224582_d = p_i232223_3_;
   }

   public void func_231160_c_() {
      this.func_224573_a(this.field_224581_c.field_230582_a_);
      RealmsNarratorHelper.func_239551_a_(field_243173_b.getString(), field_243174_c.getString(), this.field_224591_m, field_243175_p.getString(), this.field_224590_l.getString());
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, func_239562_k_(6), 200, 20, new TranslationTextComponent("mco.configure.world.subscription.extend"), (p_238073_1_) -> {
         String s = "https://aka.ms/ExtendJavaRealms?subscriptionId=" + this.field_224581_c.field_230583_b_ + "&profileId=" + this.field_230706_i_.getSession().getPlayerID();
         this.field_230706_i_.keyboardListener.setClipboardString(s);
         Util.getOSType().openURI(s);
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, func_239562_k_(12), 200, 20, DialogTexts.field_240637_h_, (p_238071_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.field_224580_b);
      }));
      if (this.field_224581_c.field_230591_j_) {
         this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, func_239562_k_(10), 200, 20, new TranslationTextComponent("mco.configure.world.delete.button"), (p_238069_1_) -> {
            ITextComponent itextcomponent = new TranslationTextComponent("mco.configure.world.delete.question.line1");
            ITextComponent itextcomponent1 = new TranslationTextComponent("mco.configure.world.delete.question.line2");
            this.field_230706_i_.displayGuiScreen(new RealmsLongConfirmationScreen(this::func_238074_c_, RealmsLongConfirmationScreen.Type.Warning, itextcomponent, itextcomponent1, true));
         }));
      }

   }

   private void func_238074_c_(boolean p_238074_1_) {
      if (p_238074_1_) {
         (new Thread("Realms-delete-realm") {
            public void run() {
               try {
                  RealmsClient realmsclient = RealmsClient.func_224911_a();
                  realmsclient.func_224916_h(RealmsSubscriptionInfoScreen.this.field_224581_c.field_230582_a_);
               } catch (RealmsServiceException realmsserviceexception) {
                  RealmsSubscriptionInfoScreen.field_224579_a.error("Couldn't delete world");
                  RealmsSubscriptionInfoScreen.field_224579_a.error(realmsserviceexception);
               }

               RealmsSubscriptionInfoScreen.this.field_230706_i_.execute(() -> {
                  RealmsSubscriptionInfoScreen.this.field_230706_i_.displayGuiScreen(RealmsSubscriptionInfoScreen.this.field_224582_d);
               });
            }
         }).start();
      }

      this.field_230706_i_.displayGuiScreen(this);
   }

   private void func_224573_a(long p_224573_1_) {
      RealmsClient realmsclient = RealmsClient.func_224911_a();

      try {
         Subscription subscription = realmsclient.func_224933_g(p_224573_1_);
         this.field_224590_l = this.func_224576_a(subscription.field_230635_b_);
         this.field_224591_m = func_224574_b(subscription.field_230634_a_);
         this.field_224592_n = subscription.field_230636_c_;
      } catch (RealmsServiceException realmsserviceexception) {
         field_224579_a.error("Couldn't get subscription");
         this.field_230706_i_.displayGuiScreen(new RealmsGenericErrorScreen(realmsserviceexception, this.field_224580_b));
      }

   }

   private static String func_224574_b(long p_224574_0_) {
      Calendar calendar = new GregorianCalendar(TimeZone.getDefault());
      calendar.setTimeInMillis(p_224574_0_);
      return DateFormat.getDateTimeInstance().format(calendar.getTime());
   }

   public void func_231164_f_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(false);
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ == 256) {
         this.field_230706_i_.displayGuiScreen(this.field_224580_b);
         return true;
      } else {
         return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
      }
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      int i = this.field_230708_k_ / 2 - 100;
      func_238472_a_(p_230430_1_, this.field_230712_o_, field_243173_b, this.field_230708_k_ / 2, 17, 16777215);
      this.field_230712_o_.func_243248_b(p_230430_1_, field_243174_c, (float)i, (float)func_239562_k_(0), 10526880);
      this.field_230712_o_.func_238421_b_(p_230430_1_, this.field_224591_m, (float)i, (float)func_239562_k_(1), 16777215);
      if (this.field_224592_n == Subscription.Type.NORMAL) {
         this.field_230712_o_.func_243248_b(p_230430_1_, field_243175_p, (float)i, (float)func_239562_k_(3), 10526880);
      } else if (this.field_224592_n == Subscription.Type.RECURRING) {
         this.field_230712_o_.func_243248_b(p_230430_1_, field_243176_q, (float)i, (float)func_239562_k_(3), 10526880);
      }

      this.field_230712_o_.func_243248_b(p_230430_1_, this.field_224590_l, (float)i, (float)func_239562_k_(4), 16777215);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   private ITextComponent func_224576_a(int p_224576_1_) {
      if (p_224576_1_ == -1 && this.field_224581_c.field_230591_j_) {
         return field_243177_r;
      } else if (p_224576_1_ <= 1) {
         return field_243178_s;
      } else {
         int i = p_224576_1_ / 30;
         int j = p_224576_1_ % 30;
         IFormattableTextComponent iformattabletextcomponent = new StringTextComponent("");
         if (i > 0) {
            iformattabletextcomponent.func_240702_b_(Integer.toString(i)).func_240702_b_(" ");
            if (i == 1) {
               iformattabletextcomponent.func_230529_a_(field_243179_t);
            } else {
               iformattabletextcomponent.func_230529_a_(field_243180_u);
            }
         }

         if (j > 0) {
            if (i > 0) {
               iformattabletextcomponent.func_240702_b_(", ");
            }

            iformattabletextcomponent.func_240702_b_(Integer.toString(j)).func_240702_b_(" ");
            if (j == 1) {
               iformattabletextcomponent.func_230529_a_(field_243181_v);
            } else {
               iformattabletextcomponent.func_230529_a_(field_243182_w);
            }
         }

         return iformattabletextcomponent;
      }
   }
}