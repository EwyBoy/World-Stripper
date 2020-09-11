package net.minecraft.client.gui.widget.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractButton extends Widget {
   public AbstractButton(int p_i232251_1_, int p_i232251_2_, int p_i232251_3_, int p_i232251_4_, ITextComponent p_i232251_5_) {
      super(p_i232251_1_, p_i232251_2_, p_i232251_3_, p_i232251_4_, p_i232251_5_);
   }

   public abstract void func_230930_b_();

   public void func_230982_a_(double p_230982_1_, double p_230982_3_) {
      this.func_230930_b_();
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (this.field_230693_o_ && this.field_230694_p_) {
         if (p_231046_1_ != 257 && p_231046_1_ != 32 && p_231046_1_ != 335) {
            return false;
         } else {
            this.func_230988_a_(Minecraft.getInstance().getSoundHandler());
            this.func_230930_b_();
            return true;
         }
      } else {
         return false;
      }
   }
}