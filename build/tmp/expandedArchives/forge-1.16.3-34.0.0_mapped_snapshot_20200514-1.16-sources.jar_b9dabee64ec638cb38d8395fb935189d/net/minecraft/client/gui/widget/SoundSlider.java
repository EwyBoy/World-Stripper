package net.minecraft.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SoundSlider extends GameSettingsSlider {
   private final SoundCategory category;

   public SoundSlider(Minecraft p_i51127_1_, int p_i51127_2_, int p_i51127_3_, SoundCategory category, int p_i51127_5_) {
      super(p_i51127_1_.gameSettings, p_i51127_2_, p_i51127_3_, p_i51127_5_, 20, (double)p_i51127_1_.gameSettings.getSoundLevel(category));
      this.category = category;
      this.func_230979_b_();
   }

   protected void func_230979_b_() {
      ITextComponent itextcomponent = (ITextComponent)((float)this.field_230683_b_ == (float)this.func_230989_a_(false) ? DialogTexts.field_240631_b_ : new StringTextComponent((int)(this.field_230683_b_ * 100.0D) + "%"));
      this.func_238482_a_((new TranslationTextComponent("soundCategory." + this.category.getName())).func_240702_b_(": ").func_230529_a_(itextcomponent));
   }

   protected void func_230972_a_() {
      this.field_238477_a_.setSoundLevel(this.category, (float)this.field_230683_b_);
      this.field_238477_a_.saveOptions();
   }
}