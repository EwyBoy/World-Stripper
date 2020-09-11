package net.minecraft.client.settings;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IteratableOption extends AbstractOption {
   private final BiConsumer<GameSettings, Integer> setter;
   private final BiFunction<GameSettings, IteratableOption, ITextComponent> getter;

   public IteratableOption(String translationKeyIn, BiConsumer<GameSettings, Integer> setterIn, BiFunction<GameSettings, IteratableOption, ITextComponent> getterIn) {
      super(translationKeyIn);
      this.setter = setterIn;
      this.getter = getterIn;
   }

   public void setValueIndex(GameSettings options, int valueIn) {
      this.setter.accept(options, valueIn);
      options.saveOptions();
   }

   public Widget createWidget(GameSettings options, int xIn, int yIn, int widthIn) {
      return new OptionButton(xIn, yIn, widthIn, 20, this, this.func_238157_c_(options), (p_216721_2_) -> {
         this.setValueIndex(options, 1);
         p_216721_2_.func_238482_a_(this.func_238157_c_(options));
      });
   }

   public ITextComponent func_238157_c_(GameSettings p_238157_1_) {
      return this.getter.apply(p_238157_1_, this);
   }
}