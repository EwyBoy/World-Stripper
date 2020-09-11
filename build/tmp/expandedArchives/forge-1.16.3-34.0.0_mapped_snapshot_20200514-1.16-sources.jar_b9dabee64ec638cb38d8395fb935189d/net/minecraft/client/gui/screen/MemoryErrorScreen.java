package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MemoryErrorScreen extends Screen {
   public MemoryErrorScreen() {
      super(new StringTextComponent("Out of memory!"));
   }

   protected void func_231160_c_() {
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155, this.field_230709_l_ / 4 + 120 + 12, 150, 20, new TranslationTextComponent("gui.toTitle"), (p_213048_1_) -> {
         this.field_230706_i_.displayGuiScreen(new MainMenuScreen());
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155 + 160, this.field_230709_l_ / 4 + 120 + 12, 150, 20, new TranslationTextComponent("menu.quit"), (p_213047_1_) -> {
         this.field_230706_i_.shutdown();
      }));
   }

   public boolean func_231178_ax__() {
      return false;
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, this.field_230709_l_ / 4 - 60 + 20, 16777215);
      func_238476_c_(p_230430_1_, this.field_230712_o_, "Minecraft has run out of memory.", this.field_230708_k_ / 2 - 140, this.field_230709_l_ / 4 - 60 + 60 + 0, 10526880);
      func_238476_c_(p_230430_1_, this.field_230712_o_, "This could be caused by a bug in the game or by the", this.field_230708_k_ / 2 - 140, this.field_230709_l_ / 4 - 60 + 60 + 18, 10526880);
      func_238476_c_(p_230430_1_, this.field_230712_o_, "Java Virtual Machine not being allocated enough", this.field_230708_k_ / 2 - 140, this.field_230709_l_ / 4 - 60 + 60 + 27, 10526880);
      func_238476_c_(p_230430_1_, this.field_230712_o_, "memory.", this.field_230708_k_ / 2 - 140, this.field_230709_l_ / 4 - 60 + 60 + 36, 10526880);
      func_238476_c_(p_230430_1_, this.field_230712_o_, "To prevent level corruption, the current game has quit.", this.field_230708_k_ / 2 - 140, this.field_230709_l_ / 4 - 60 + 60 + 54, 10526880);
      func_238476_c_(p_230430_1_, this.field_230712_o_, "We've tried to free up enough memory to let you go back to", this.field_230708_k_ / 2 - 140, this.field_230709_l_ / 4 - 60 + 60 + 63, 10526880);
      func_238476_c_(p_230430_1_, this.field_230712_o_, "the main menu and back to playing, but this may not have worked.", this.field_230708_k_ / 2 - 140, this.field_230709_l_ / 4 - 60 + 60 + 72, 10526880);
      func_238476_c_(p_230430_1_, this.field_230712_o_, "Please restart the game if you see this message again.", this.field_230708_k_ / 2 - 140, this.field_230709_l_ / 4 - 60 + 60 + 81, 10526880);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }
}