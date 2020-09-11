package net.minecraft.util.text;

import java.util.function.UnaryOperator;

public interface IFormattableTextComponent extends ITextComponent {
   IFormattableTextComponent func_230530_a_(Style p_230530_1_);

   default IFormattableTextComponent func_240702_b_(String p_240702_1_) {
      return this.func_230529_a_(new StringTextComponent(p_240702_1_));
   }

   IFormattableTextComponent func_230529_a_(ITextComponent p_230529_1_);

   default IFormattableTextComponent func_240700_a_(UnaryOperator<Style> p_240700_1_) {
      this.func_230530_a_(p_240700_1_.apply(this.getStyle()));
      return this;
   }

   default IFormattableTextComponent func_240703_c_(Style p_240703_1_) {
      this.func_230530_a_(p_240703_1_.func_240717_a_(this.getStyle()));
      return this;
   }

   default IFormattableTextComponent func_240701_a_(TextFormatting... p_240701_1_) {
      this.func_230530_a_(this.getStyle().func_240720_a_(p_240701_1_));
      return this;
   }

   default IFormattableTextComponent func_240699_a_(TextFormatting p_240699_1_) {
      this.func_230530_a_(this.getStyle().func_240721_b_(p_240699_1_));
      return this;
   }
}