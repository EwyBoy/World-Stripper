package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DisconnectedScreen extends Screen {
   private final ITextComponent message;
   private IBidiRenderer field_243289_b = IBidiRenderer.field_243257_a;
   private final Screen nextScreen;
   private int textHeight;

   public DisconnectedScreen(Screen p_i242056_1_, ITextComponent p_i242056_2_, ITextComponent p_i242056_3_) {
      super(p_i242056_2_);
      this.nextScreen = p_i242056_1_;
      this.message = p_i242056_3_;
   }

   public boolean func_231178_ax__() {
      return false;
   }

   protected void func_231160_c_() {
      this.field_243289_b = IBidiRenderer.func_243258_a(this.field_230712_o_, this.message, this.field_230708_k_ - 50);
      this.textHeight = this.field_243289_b.func_241862_a() * 9;
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, Math.min(this.field_230709_l_ / 2 + this.textHeight / 2 + 9, this.field_230709_l_ - 30), 200, 20, new TranslationTextComponent("gui.toMenu"), (p_213033_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.nextScreen);
      }));
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, this.field_230709_l_ / 2 - this.textHeight / 2 - 9 * 2, 11184810);
      this.field_243289_b.func_241863_a(p_230430_1_, this.field_230708_k_ / 2, this.field_230709_l_ / 2 - this.textHeight / 2);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }
}