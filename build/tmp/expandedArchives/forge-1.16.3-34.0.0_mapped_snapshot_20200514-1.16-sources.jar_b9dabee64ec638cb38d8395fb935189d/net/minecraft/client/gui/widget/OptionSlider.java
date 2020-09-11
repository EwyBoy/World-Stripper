package net.minecraft.client.gui.widget;

import java.util.List;
import java.util.Optional;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.IBidiTooltip;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.IReorderingProcessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OptionSlider extends GameSettingsSlider implements IBidiTooltip {
   private final SliderPercentageOption option;

   public OptionSlider(GameSettings settings, int xIn, int yIn, int widthIn, int heightIn, SliderPercentageOption optionIn) {
      super(settings, xIn, yIn, widthIn, heightIn, (double)((float)optionIn.normalizeValue(optionIn.get(settings))));
      this.option = optionIn;
      this.func_230979_b_();
   }

   protected void func_230972_a_() {
      this.option.set(this.field_238477_a_, this.option.denormalizeValue(this.field_230683_b_));
      this.field_238477_a_.saveOptions();
   }

   protected void func_230979_b_() {
      this.func_238482_a_(this.option.func_238334_c_(this.field_238477_a_));
   }

   public Optional<List<IReorderingProcessor>> func_241867_d() {
      return this.option.func_238246_b_();
   }
}