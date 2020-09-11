package net.minecraft.client.gui.widget.button;

import java.util.List;
import java.util.Optional;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.gui.IBidiTooltip;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OptionButton extends Button implements IBidiTooltip {
   private final AbstractOption enumOptions;

   public OptionButton(int p_i232262_1_, int p_i232262_2_, int p_i232262_3_, int p_i232262_4_, AbstractOption p_i232262_5_, ITextComponent p_i232262_6_, Button.IPressable p_i232262_7_) {
      super(p_i232262_1_, p_i232262_2_, p_i232262_3_, p_i232262_4_, p_i232262_6_, p_i232262_7_);
      this.enumOptions = p_i232262_5_;
   }

   public AbstractOption func_238517_a_() {
      return this.enumOptions;
   }

   public Optional<List<IReorderingProcessor>> func_241867_d() {
      return this.enumOptions.func_238246_b_();
   }
}