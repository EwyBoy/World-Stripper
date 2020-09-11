package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.RealmsDataFetcher;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsNotificationsScreen extends RealmsScreen {
   private static final ResourceLocation field_237853_a_ = new ResourceLocation("realms", "textures/gui/realms/invite_icon.png");
   private static final ResourceLocation field_237854_b_ = new ResourceLocation("realms", "textures/gui/realms/trial_icon.png");
   private static final ResourceLocation field_237855_c_ = new ResourceLocation("realms", "textures/gui/realms/news_notification_mainscreen.png");
   private static final RealmsDataFetcher field_237856_p_ = new RealmsDataFetcher();
   private volatile int field_224266_b;
   private static boolean field_224267_c;
   private static boolean field_224268_d;
   private static boolean field_224269_e;
   private static boolean field_224270_f;

   public void func_231160_c_() {
      this.func_224261_a();
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
   }

   public void func_231023_e_() {
      if ((!this.func_237858_g_() || !this.func_237859_j_() || !field_224269_e) && !field_237856_p_.func_225065_a()) {
         field_237856_p_.func_225070_k();
      } else if (field_224269_e && this.func_237858_g_()) {
         field_237856_p_.func_237710_c_();
         if (field_237856_p_.func_225083_a(RealmsDataFetcher.Task.PENDING_INVITE)) {
            this.field_224266_b = field_237856_p_.func_225081_f();
         }

         if (field_237856_p_.func_225083_a(RealmsDataFetcher.Task.TRIAL_AVAILABLE)) {
            field_224268_d = field_237856_p_.func_225071_g();
         }

         if (field_237856_p_.func_225083_a(RealmsDataFetcher.Task.UNREAD_NEWS)) {
            field_224270_f = field_237856_p_.func_225059_i();
         }

         field_237856_p_.func_225072_c();
      }
   }

   private boolean func_237858_g_() {
      return this.field_230706_i_.gameSettings.realmsNotifications;
   }

   private boolean func_237859_j_() {
      return this.field_230706_i_.currentScreen instanceof MainMenuScreen;
   }

   private void func_224261_a() {
      if (!field_224267_c) {
         field_224267_c = true;
         (new Thread("Realms Notification Availability checker #1") {
            public void run() {
               RealmsClient realmsclient = RealmsClient.func_224911_a();

               try {
                  RealmsClient.CompatibleVersionResponse realmsclient$compatibleversionresponse = realmsclient.func_224939_i();
                  if (realmsclient$compatibleversionresponse != RealmsClient.CompatibleVersionResponse.COMPATIBLE) {
                     return;
                  }
               } catch (RealmsServiceException realmsserviceexception) {
                  if (realmsserviceexception.field_224981_a != 401) {
                     RealmsNotificationsScreen.field_224267_c = false;
                  }

                  return;
               }

               RealmsNotificationsScreen.field_224269_e = true;
            }
         }).start();
      }

   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      if (field_224269_e) {
         this.func_237857_a_(p_230430_1_, p_230430_2_, p_230430_3_);
      }

      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   private void func_237857_a_(MatrixStack p_237857_1_, int p_237857_2_, int p_237857_3_) {
      int i = this.field_224266_b;
      int j = 24;
      int k = this.field_230709_l_ / 4 + 48;
      int l = this.field_230708_k_ / 2 + 80;
      int i1 = k + 48 + 2;
      int j1 = 0;
      if (field_224270_f) {
         this.field_230706_i_.getTextureManager().bindTexture(field_237855_c_);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.pushMatrix();
         RenderSystem.scalef(0.4F, 0.4F, 0.4F);
         AbstractGui.func_238463_a_(p_237857_1_, (int)((double)(l + 2 - j1) * 2.5D), (int)((double)i1 * 2.5D), 0.0F, 0.0F, 40, 40, 40, 40);
         RenderSystem.popMatrix();
         j1 += 14;
      }

      if (i != 0) {
         this.field_230706_i_.getTextureManager().bindTexture(field_237853_a_);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         AbstractGui.func_238463_a_(p_237857_1_, l - j1, i1 - 6, 0.0F, 0.0F, 15, 25, 31, 25);
         j1 += 16;
      }

      if (field_224268_d) {
         this.field_230706_i_.getTextureManager().bindTexture(field_237854_b_);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         int k1 = 0;
         if ((Util.milliTime() / 800L & 1L) == 1L) {
            k1 = 8;
         }

         AbstractGui.func_238463_a_(p_237857_1_, l + 4 - j1, i1 + 4, 0.0F, (float)k1, 8, 8, 8, 16);
      }

   }

   public void func_231164_f_() {
      field_237856_p_.func_225070_k();
   }
}