package net.minecraft.client.gui;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DialogTexts {
   public static final ITextComponent field_240630_a_ = new TranslationTextComponent("options.on");
   public static final ITextComponent field_240631_b_ = new TranslationTextComponent("options.off");
   public static final ITextComponent field_240632_c_ = new TranslationTextComponent("gui.done");
   public static final ITextComponent field_240633_d_ = new TranslationTextComponent("gui.cancel");
   public static final ITextComponent field_240634_e_ = new TranslationTextComponent("gui.yes");
   public static final ITextComponent field_240635_f_ = new TranslationTextComponent("gui.no");
   public static final ITextComponent field_240636_g_ = new TranslationTextComponent("gui.proceed");
   public static final ITextComponent field_240637_h_ = new TranslationTextComponent("gui.back");
   public static final ITextComponent field_244280_i = new TranslationTextComponent("connect.failed");

   public static ITextComponent func_240638_a_(boolean p_240638_0_) {
      return p_240638_0_ ? field_240630_a_ : field_240631_b_;
   }

   public static IFormattableTextComponent func_244281_a(ITextComponent p_244281_0_, boolean p_244281_1_) {
      return new TranslationTextComponent(p_244281_1_ ? "options.on.composed" : "options.off.composed", p_244281_0_);
   }
}