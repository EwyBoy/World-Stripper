package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.SoundSlider;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OptionsSoundsScreen extends SettingsScreen {
   public OptionsSoundsScreen(Screen parentIn, GameSettings settingsIn) {
      super(parentIn, settingsIn, new TranslationTextComponent("options.sounds.title"));
   }

   protected void func_231160_c_() {
      int i = 0;
      this.func_230480_a_(new SoundSlider(this.field_230706_i_, this.field_230708_k_ / 2 - 155 + i % 2 * 160, this.field_230709_l_ / 6 - 12 + 24 * (i >> 1), SoundCategory.MASTER, 310));
      i = i + 2;

      for(SoundCategory soundcategory : SoundCategory.values()) {
         if (soundcategory != SoundCategory.MASTER) {
            this.func_230480_a_(new SoundSlider(this.field_230706_i_, this.field_230708_k_ / 2 - 155 + i % 2 * 160, this.field_230709_l_ / 6 - 12 + 24 * (i >> 1), soundcategory, 150));
            ++i;
         }
      }

      int j = this.field_230708_k_ / 2 - 75;
      int k = this.field_230709_l_ / 6 - 12;
      ++i;
      this.func_230480_a_(new OptionButton(j, k + 24 * (i >> 1), 150, 20, AbstractOption.SHOW_SUBTITLES, AbstractOption.SHOW_SUBTITLES.func_238152_c_(this.gameSettings), (p_213105_1_) -> {
         AbstractOption.SHOW_SUBTITLES.nextValue(this.field_230706_i_.gameSettings);
         p_213105_1_.func_238482_a_(AbstractOption.SHOW_SUBTITLES.func_238152_c_(this.field_230706_i_.gameSettings));
         this.field_230706_i_.gameSettings.saveOptions();
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 6 + 168, 200, 20, DialogTexts.field_240632_c_, (p_213104_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.parentScreen);
      }));
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 15, 16777215);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }
}