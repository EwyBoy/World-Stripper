package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Arrays;
import java.util.stream.Stream;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.OptionsRowList;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MouseSettingsScreen extends SettingsScreen {
   private OptionsRowList field_213045_b;
   private static final AbstractOption[] OPTIONS = new AbstractOption[]{AbstractOption.SENSITIVITY, AbstractOption.INVERT_MOUSE, AbstractOption.MOUSE_WHEEL_SENSITIVITY, AbstractOption.DISCRETE_MOUSE_SCROLL, AbstractOption.TOUCHSCREEN};

   public MouseSettingsScreen(Screen p_i225929_1_, GameSettings p_i225929_2_) {
      super(p_i225929_1_, p_i225929_2_, new TranslationTextComponent("options.mouse_settings.title"));
   }

   protected void func_231160_c_() {
      this.field_213045_b = new OptionsRowList(this.field_230706_i_, this.field_230708_k_, this.field_230709_l_, 32, this.field_230709_l_ - 32, 25);
      if (InputMappings.func_224790_a()) {
         this.field_213045_b.addOptions(Stream.concat(Arrays.stream(OPTIONS), Stream.of(AbstractOption.RAW_MOUSE_INPUT)).toArray((p_223702_0_) -> {
            return new AbstractOption[p_223702_0_];
         }));
      } else {
         this.field_213045_b.addOptions(OPTIONS);
      }

      this.field_230705_e_.add(this.field_213045_b);
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ - 27, 200, 20, DialogTexts.field_240632_c_, (p_223703_1_) -> {
         this.gameSettings.saveOptions();
         this.field_230706_i_.displayGuiScreen(this.parentScreen);
      }));
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      this.field_213045_b.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 5, 16777215);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }
}