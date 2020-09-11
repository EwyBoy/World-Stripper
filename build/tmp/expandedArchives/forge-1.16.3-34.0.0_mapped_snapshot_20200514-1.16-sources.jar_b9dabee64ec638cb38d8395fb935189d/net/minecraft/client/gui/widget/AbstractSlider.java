package net.minecraft.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractSlider extends Widget {
   protected double field_230683_b_;

   public AbstractSlider(int p_i232253_1_, int p_i232253_2_, int p_i232253_3_, int p_i232253_4_, ITextComponent p_i232253_5_, double p_i232253_6_) {
      super(p_i232253_1_, p_i232253_2_, p_i232253_3_, p_i232253_4_, p_i232253_5_);
      this.field_230683_b_ = p_i232253_6_;
   }

   protected int func_230989_a_(boolean p_230989_1_) {
      return 0;
   }

   protected IFormattableTextComponent func_230442_c_() {
      return new TranslationTextComponent("gui.narrate.slider", this.func_230458_i_());
   }

   protected void func_230441_a_(MatrixStack p_230441_1_, Minecraft p_230441_2_, int p_230441_3_, int p_230441_4_) {
      p_230441_2_.getTextureManager().bindTexture(field_230687_i_);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      int i = (this.func_230449_g_() ? 2 : 1) * 20;
      this.func_238474_b_(p_230441_1_, this.field_230690_l_ + (int)(this.field_230683_b_ * (double)(this.field_230688_j_ - 8)), this.field_230691_m_, 0, 46 + i, 4, 20);
      this.func_238474_b_(p_230441_1_, this.field_230690_l_ + (int)(this.field_230683_b_ * (double)(this.field_230688_j_ - 8)) + 4, this.field_230691_m_, 196, 46 + i, 4, 20);
   }

   public void func_230982_a_(double p_230982_1_, double p_230982_3_) {
      this.func_230973_a_(p_230982_1_);
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      boolean flag = p_231046_1_ == 263;
      if (flag || p_231046_1_ == 262) {
         float f = flag ? -1.0F : 1.0F;
         this.func_230980_b_(this.field_230683_b_ + (double)(f / (float)(this.field_230688_j_ - 8)));
      }

      return false;
   }

   private void func_230973_a_(double p_230973_1_) {
      this.func_230980_b_((p_230973_1_ - (double)(this.field_230690_l_ + 4)) / (double)(this.field_230688_j_ - 8));
   }

   private void func_230980_b_(double p_230980_1_) {
      double d0 = this.field_230683_b_;
      this.field_230683_b_ = MathHelper.clamp(p_230980_1_, 0.0D, 1.0D);
      if (d0 != this.field_230683_b_) {
         this.func_230972_a_();
      }

      this.func_230979_b_();
   }

   protected void func_230983_a_(double p_230983_1_, double p_230983_3_, double p_230983_5_, double p_230983_7_) {
      this.func_230973_a_(p_230983_1_);
      super.func_230983_a_(p_230983_1_, p_230983_3_, p_230983_5_, p_230983_7_);
   }

   public void func_230988_a_(SoundHandler p_230988_1_) {
   }

   public void func_231000_a__(double p_231000_1_, double p_231000_3_) {
      super.func_230988_a_(Minecraft.getInstance().getSoundHandler());
   }

   protected abstract void func_230979_b_();

   protected abstract void func_230972_a_();
}