package net.minecraft.realms;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DisconnectedRealmsScreen extends RealmsScreen {
   private final ITextComponent field_230713_a_;
   private final ITextComponent field_230714_b_;
   private IBidiRenderer field_243509_c = IBidiRenderer.field_243257_a;
   private final Screen field_230716_p_;
   private int field_230717_q_;

   public DisconnectedRealmsScreen(Screen p_i242069_1_, ITextComponent p_i242069_2_, ITextComponent p_i242069_3_) {
      this.field_230716_p_ = p_i242069_1_;
      this.field_230713_a_ = p_i242069_2_;
      this.field_230714_b_ = p_i242069_3_;
   }

   public void func_231160_c_() {
      Minecraft minecraft = Minecraft.getInstance();
      minecraft.setConnectedToRealms(false);
      minecraft.getPackFinder().clearResourcePack();
      RealmsNarratorHelper.func_239550_a_(this.field_230713_a_.getString() + ": " + this.field_230714_b_.getString());
      this.field_243509_c = IBidiRenderer.func_243258_a(this.field_230712_o_, this.field_230714_b_, this.field_230708_k_ - 50);
      this.field_230717_q_ = this.field_243509_c.func_241862_a() * 9;
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 2 + this.field_230717_q_ / 2 + 9, 200, 20, DialogTexts.field_240637_h_, (p_239547_2_) -> {
         minecraft.displayGuiScreen(this.field_230716_p_);
      }));
   }

   public void func_231175_as__() {
      Minecraft.getInstance().displayGuiScreen(this.field_230716_p_);
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230713_a_, this.field_230708_k_ / 2, this.field_230709_l_ / 2 - this.field_230717_q_ / 2 - 9 * 2, 11184810);
      this.field_243509_c.func_241863_a(p_230430_1_, this.field_230708_k_ / 2, this.field_230709_l_ / 2 - this.field_230717_q_ / 2);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }
}