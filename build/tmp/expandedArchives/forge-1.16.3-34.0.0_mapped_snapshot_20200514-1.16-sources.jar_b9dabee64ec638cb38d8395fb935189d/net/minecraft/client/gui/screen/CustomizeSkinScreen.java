package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CustomizeSkinScreen extends SettingsScreen {
   public CustomizeSkinScreen(Screen parentScreenIn, GameSettings gameSettingsIn) {
      super(parentScreenIn, gameSettingsIn, new TranslationTextComponent("options.skinCustomisation.title"));
   }

   protected void func_231160_c_() {
      int i = 0;

      for(PlayerModelPart playermodelpart : PlayerModelPart.values()) {
         this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155 + i % 2 * 160, this.field_230709_l_ / 6 + 24 * (i >> 1), 150, 20, this.func_238655_a_(playermodelpart), (p_213080_2_) -> {
            this.gameSettings.switchModelPartEnabled(playermodelpart);
            p_213080_2_.func_238482_a_(this.func_238655_a_(playermodelpart));
         }));
         ++i;
      }

      this.func_230480_a_(new OptionButton(this.field_230708_k_ / 2 - 155 + i % 2 * 160, this.field_230709_l_ / 6 + 24 * (i >> 1), 150, 20, AbstractOption.MAIN_HAND, AbstractOption.MAIN_HAND.func_238157_c_(this.gameSettings), (p_213081_1_) -> {
         AbstractOption.MAIN_HAND.setValueIndex(this.gameSettings, 1);
         this.gameSettings.saveOptions();
         p_213081_1_.func_238482_a_(AbstractOption.MAIN_HAND.func_238157_c_(this.gameSettings));
         this.gameSettings.sendSettingsToServer();
      }));
      ++i;
      if (i % 2 == 1) {
         ++i;
      }

      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 6 + 24 * (i >> 1), 200, 20, DialogTexts.field_240632_c_, (p_213079_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.parentScreen);
      }));
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 20, 16777215);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   private ITextComponent func_238655_a_(PlayerModelPart p_238655_1_) {
      return DialogTexts.func_244281_a(p_238655_1_.getName(), this.gameSettings.getModelParts().contains(p_238655_1_));
   }
}