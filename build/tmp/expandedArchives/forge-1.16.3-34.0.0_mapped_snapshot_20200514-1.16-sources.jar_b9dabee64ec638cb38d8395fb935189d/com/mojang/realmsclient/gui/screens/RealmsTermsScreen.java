package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.exception.RealmsServiceException;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.realms.action.ConnectingToRealmsAction;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsTermsScreen extends RealmsScreen {
   private static final Logger field_224722_a = LogManager.getLogger();
   private static final ITextComponent field_243184_b = new TranslationTextComponent("mco.terms.title");
   private static final ITextComponent field_243185_c = new TranslationTextComponent("mco.terms.sentence.1");
   private static final ITextComponent field_243186_p = (new StringTextComponent(" ")).func_230529_a_((new TranslationTextComponent("mco.terms.sentence.2")).func_240703_c_(Style.field_240709_b_.func_244282_c(true)));
   private final Screen field_224723_b;
   private final RealmsMainScreen field_224724_c;
   /**
    * The screen to display when OK is clicked on the disconnect screen.
    *  
    * Seems to be either null (integrated server) or an instance of either {@link MultiplayerScreen} (when connecting to
    * a server) or {@link com.mojang.realmsclient.gui.screens.RealmsTermsScreen} (when connecting to MCO server)
    */
   private final RealmsServer guiScreenServer;
   private boolean field_224727_f;
   private final String field_224728_g = "https://minecraft.net/realms/terms";

   public RealmsTermsScreen(Screen p_i232225_1_, RealmsMainScreen p_i232225_2_, RealmsServer p_i232225_3_) {
      this.field_224723_b = p_i232225_1_;
      this.field_224724_c = p_i232225_2_;
      this.guiScreenServer = p_i232225_3_;
   }

   public void func_231160_c_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      int i = this.field_230708_k_ / 4 - 2;
      this.func_230480_a_(new Button(this.field_230708_k_ / 4, func_239562_k_(12), i, 20, new TranslationTextComponent("mco.terms.buttons.agree"), (p_238078_1_) -> {
         this.func_224721_a();
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 4, func_239562_k_(12), i, 20, new TranslationTextComponent("mco.terms.buttons.disagree"), (p_238077_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.field_224723_b);
      }));
   }

   public void func_231164_f_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(false);
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ == 256) {
         this.field_230706_i_.displayGuiScreen(this.field_224723_b);
         return true;
      } else {
         return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
      }
   }

   private void func_224721_a() {
      RealmsClient realmsclient = RealmsClient.func_224911_a();

      try {
         realmsclient.func_224937_l();
         this.field_230706_i_.displayGuiScreen(new RealmsLongRunningMcoTaskScreen(this.field_224723_b, new ConnectingToRealmsAction(this.field_224724_c, this.field_224723_b, this.guiScreenServer, new ReentrantLock())));
      } catch (RealmsServiceException realmsserviceexception) {
         field_224722_a.error("Couldn't agree to TOS");
      }

   }

   public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      if (this.field_224727_f) {
         this.field_230706_i_.keyboardListener.setClipboardString("https://minecraft.net/realms/terms");
         Util.getOSType().openURI("https://minecraft.net/realms/terms");
         return true;
      } else {
         return super.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_);
      }
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, field_243184_b, this.field_230708_k_ / 2, 17, 16777215);
      this.field_230712_o_.func_243248_b(p_230430_1_, field_243185_c, (float)(this.field_230708_k_ / 2 - 120), (float)func_239562_k_(5), 16777215);
      int i = this.field_230712_o_.func_238414_a_(field_243185_c);
      int j = this.field_230708_k_ / 2 - 121 + i;
      int k = func_239562_k_(5);
      int l = j + this.field_230712_o_.func_238414_a_(field_243186_p) + 1;
      int i1 = k + 1 + 9;
      this.field_224727_f = j <= p_230430_2_ && p_230430_2_ <= l && k <= p_230430_3_ && p_230430_3_ <= i1;
      this.field_230712_o_.func_243248_b(p_230430_1_, field_243186_p, (float)(this.field_230708_k_ / 2 - 120 + i), (float)func_239562_k_(5), this.field_224727_f ? 7107012 : 3368635);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }
}