package net.minecraft.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Button extends AbstractButton {
   public static final Button.ITooltip field_238486_s_ = (p_238488_0_, p_238488_1_, p_238488_2_, p_238488_3_) -> {
   };
   protected final Button.IPressable field_230697_t_;
   protected final Button.ITooltip field_238487_u_;

   public Button(int p_i232255_1_, int p_i232255_2_, int p_i232255_3_, int p_i232255_4_, ITextComponent p_i232255_5_, Button.IPressable p_i232255_6_) {
      this(p_i232255_1_, p_i232255_2_, p_i232255_3_, p_i232255_4_, p_i232255_5_, p_i232255_6_, field_238486_s_);
   }

   public Button(int p_i232256_1_, int p_i232256_2_, int p_i232256_3_, int p_i232256_4_, ITextComponent p_i232256_5_, Button.IPressable p_i232256_6_, Button.ITooltip p_i232256_7_) {
      super(p_i232256_1_, p_i232256_2_, p_i232256_3_, p_i232256_4_, p_i232256_5_);
      this.field_230697_t_ = p_i232256_6_;
      this.field_238487_u_ = p_i232256_7_;
   }

   public void func_230930_b_() {
      this.field_230697_t_.onPress(this);
   }

   public void func_230431_b_(MatrixStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
      super.func_230431_b_(p_230431_1_, p_230431_2_, p_230431_3_, p_230431_4_);
      if (this.func_230449_g_()) {
         this.func_230443_a_(p_230431_1_, p_230431_2_, p_230431_3_);
      }

   }

   public void func_230443_a_(MatrixStack p_230443_1_, int p_230443_2_, int p_230443_3_) {
      this.field_238487_u_.onTooltip(this, p_230443_1_, p_230443_2_, p_230443_3_);
   }

   @OnlyIn(Dist.CLIENT)
   public interface IPressable {
      void onPress(Button p_onPress_1_);
   }

   @OnlyIn(Dist.CLIENT)
   public interface ITooltip {
      void onTooltip(Button p_onTooltip_1_, MatrixStack p_onTooltip_2_, int p_onTooltip_3_, int p_onTooltip_4_);
   }
}