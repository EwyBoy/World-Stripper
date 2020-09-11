package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ErrorScreen extends Screen {
   private final ITextComponent message;

   public ErrorScreen(ITextComponent p_i232277_1_, ITextComponent p_i232277_2_) {
      super(p_i232277_1_);
      this.message = p_i232277_2_;
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, 140, 200, 20, DialogTexts.field_240633_d_, (p_213034_1_) -> {
         this.field_230706_i_.displayGuiScreen((Screen)null);
      }));
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_238468_a_(p_230430_1_, 0, 0, this.field_230708_k_, this.field_230709_l_, -12574688, -11530224);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 90, 16777215);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.message, this.field_230708_k_ / 2, 110, 16777215);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   public boolean func_231178_ax__() {
      return false;
   }
}