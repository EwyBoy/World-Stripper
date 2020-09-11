package net.minecraft.client.gui.widget;

import net.minecraft.client.GameSettings;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class GameSettingsSlider extends AbstractSlider {
   protected final GameSettings field_238477_a_;

   protected GameSettingsSlider(GameSettings p_i232252_1_, int p_i232252_2_, int p_i232252_3_, int p_i232252_4_, int p_i232252_5_, double p_i232252_6_) {
      super(p_i232252_2_, p_i232252_3_, p_i232252_4_, p_i232252_5_, StringTextComponent.field_240750_d_, p_i232252_6_);
      this.field_238477_a_ = p_i232252_1_;
   }
}