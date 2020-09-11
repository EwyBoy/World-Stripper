package net.minecraft.resources;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public interface IPackNameDecorator {
   IPackNameDecorator field_232625_a_ = func_232629_a_();
   IPackNameDecorator field_232626_b_ = func_232630_a_("pack.source.builtin");
   IPackNameDecorator field_232627_c_ = func_232630_a_("pack.source.world");
   IPackNameDecorator field_232628_d_ = func_232630_a_("pack.source.server");

   ITextComponent decorate(ITextComponent p_decorate_1_);

   static IPackNameDecorator func_232629_a_() {
      return (p_232631_0_) -> {
         return p_232631_0_;
      };
   }

   static IPackNameDecorator func_232630_a_(String p_232630_0_) {
      ITextComponent itextcomponent = new TranslationTextComponent(p_232630_0_);
      return (p_232632_1_) -> {
         return new TranslationTextComponent("pack.nameAndSource", p_232632_1_, itextcomponent);
      };
   }
}