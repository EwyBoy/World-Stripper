package net.minecraft.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class Widget extends AbstractGui implements IRenderable, IGuiEventListener {
   public static final ResourceLocation field_230687_i_ = new ResourceLocation("textures/gui/widgets.png");
   protected int field_230688_j_;
   protected int field_230689_k_;
   public int field_230690_l_;
   public int field_230691_m_;
   private ITextComponent field_230684_a_;
   private boolean field_230685_b_;
   protected boolean field_230692_n_;
   public boolean field_230693_o_ = true;
   public boolean field_230694_p_ = true;
   protected float field_230695_q_ = 1.0F;
   protected long field_230696_r_ = Long.MAX_VALUE;
   private boolean field_230686_c_;

   public Widget(int p_i232254_1_, int p_i232254_2_, int p_i232254_3_, int p_i232254_4_, ITextComponent p_i232254_5_) {
      this.field_230690_l_ = p_i232254_1_;
      this.field_230691_m_ = p_i232254_2_;
      this.field_230688_j_ = p_i232254_3_;
      this.field_230689_k_ = p_i232254_4_;
      this.field_230684_a_ = p_i232254_5_;
   }

   public int func_238483_d_() {
      return this.field_230689_k_;
   }

   protected int func_230989_a_(boolean p_230989_1_) {
      int i = 1;
      if (!this.field_230693_o_) {
         i = 0;
      } else if (p_230989_1_) {
         i = 2;
      }

      return i;
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      if (this.field_230694_p_) {
         this.field_230692_n_ = p_230430_2_ >= this.field_230690_l_ && p_230430_3_ >= this.field_230691_m_ && p_230430_2_ < this.field_230690_l_ + this.field_230688_j_ && p_230430_3_ < this.field_230691_m_ + this.field_230689_k_;
         if (this.field_230685_b_ != this.func_230449_g_()) {
            if (this.func_230449_g_()) {
               if (this.field_230686_c_) {
                  this.func_230994_c_(200);
               } else {
                  this.func_230994_c_(750);
               }
            } else {
               this.field_230696_r_ = Long.MAX_VALUE;
            }
         }

         if (this.field_230694_p_) {
            this.func_230431_b_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
         }

         this.func_230997_f_();
         this.field_230685_b_ = this.func_230449_g_();
      }
   }

   protected void func_230997_f_() {
      if (this.field_230693_o_ && this.func_230449_g_() && Util.milliTime() > this.field_230696_r_) {
         String s = this.func_230442_c_().getString();
         if (!s.isEmpty()) {
            NarratorChatListener.INSTANCE.say(s);
            this.field_230696_r_ = Long.MAX_VALUE;
         }
      }

   }

   protected IFormattableTextComponent func_230442_c_() {
      return new TranslationTextComponent("gui.narrate.button", this.func_230458_i_());
   }

   public void func_230431_b_(MatrixStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
      Minecraft minecraft = Minecraft.getInstance();
      FontRenderer fontrenderer = minecraft.fontRenderer;
      minecraft.getTextureManager().bindTexture(field_230687_i_);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.field_230695_q_);
      int i = this.func_230989_a_(this.func_230449_g_());
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      this.func_238474_b_(p_230431_1_, this.field_230690_l_, this.field_230691_m_, 0, 46 + i * 20, this.field_230688_j_ / 2, this.field_230689_k_);
      this.func_238474_b_(p_230431_1_, this.field_230690_l_ + this.field_230688_j_ / 2, this.field_230691_m_, 200 - this.field_230688_j_ / 2, 46 + i * 20, this.field_230688_j_ / 2, this.field_230689_k_);
      this.func_230441_a_(p_230431_1_, minecraft, p_230431_2_, p_230431_3_);
      int j = getFGColor();
      func_238472_a_(p_230431_1_, fontrenderer, this.func_230458_i_(), this.field_230690_l_ + this.field_230688_j_ / 2, this.field_230691_m_ + (this.field_230689_k_ - 8) / 2, j | MathHelper.ceil(this.field_230695_q_ * 255.0F) << 24);
   }

   protected void func_230441_a_(MatrixStack p_230441_1_, Minecraft p_230441_2_, int p_230441_3_, int p_230441_4_) {
   }

   public void func_230982_a_(double p_230982_1_, double p_230982_3_) {
   }

   public void func_231000_a__(double p_231000_1_, double p_231000_3_) {
   }

   protected void func_230983_a_(double p_230983_1_, double p_230983_3_, double p_230983_5_, double p_230983_7_) {
   }

   public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      if (this.field_230693_o_ && this.field_230694_p_) {
         if (this.func_230987_a_(p_231044_5_)) {
            boolean flag = this.func_230992_c_(p_231044_1_, p_231044_3_);
            if (flag) {
               this.func_230988_a_(Minecraft.getInstance().getSoundHandler());
               this.func_230982_a_(p_231044_1_, p_231044_3_);
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public boolean func_231048_c_(double p_231048_1_, double p_231048_3_, int p_231048_5_) {
      if (this.func_230987_a_(p_231048_5_)) {
         this.func_231000_a__(p_231048_1_, p_231048_3_);
         return true;
      } else {
         return false;
      }
   }

   protected boolean func_230987_a_(int p_230987_1_) {
      return p_230987_1_ == 0;
   }

   public boolean func_231045_a_(double p_231045_1_, double p_231045_3_, int p_231045_5_, double p_231045_6_, double p_231045_8_) {
      if (this.func_230987_a_(p_231045_5_)) {
         this.func_230983_a_(p_231045_1_, p_231045_3_, p_231045_6_, p_231045_8_);
         return true;
      } else {
         return false;
      }
   }

   protected boolean func_230992_c_(double p_230992_1_, double p_230992_3_) {
      return this.field_230693_o_ && this.field_230694_p_ && p_230992_1_ >= (double)this.field_230690_l_ && p_230992_3_ >= (double)this.field_230691_m_ && p_230992_1_ < (double)(this.field_230690_l_ + this.field_230688_j_) && p_230992_3_ < (double)(this.field_230691_m_ + this.field_230689_k_);
   }

   public boolean func_230449_g_() {
      return this.field_230692_n_ || this.field_230686_c_;
   }

   public boolean func_231049_c__(boolean p_231049_1_) {
      if (this.field_230693_o_ && this.field_230694_p_) {
         this.field_230686_c_ = !this.field_230686_c_;
         this.func_230995_c_(this.field_230686_c_);
         return this.field_230686_c_;
      } else {
         return false;
      }
   }

   protected void func_230995_c_(boolean p_230995_1_) {
   }

   public boolean func_231047_b_(double p_231047_1_, double p_231047_3_) {
      return this.field_230693_o_ && this.field_230694_p_ && p_231047_1_ >= (double)this.field_230690_l_ && p_231047_3_ >= (double)this.field_230691_m_ && p_231047_1_ < (double)(this.field_230690_l_ + this.field_230688_j_) && p_231047_3_ < (double)(this.field_230691_m_ + this.field_230689_k_);
   }

   public void func_230443_a_(MatrixStack p_230443_1_, int p_230443_2_, int p_230443_3_) {
   }

   public void func_230988_a_(SoundHandler p_230988_1_) {
      p_230988_1_.play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
   }

   public int func_230998_h_() {
      return this.field_230688_j_;
   }

   public void func_230991_b_(int p_230991_1_) {
      this.field_230688_j_ = p_230991_1_;
   }

   public void setHeight(int value) {
      this.field_230689_k_ = value;
   }

   public void func_230986_a_(float p_230986_1_) {
      this.field_230695_q_ = p_230986_1_;
   }

   public void func_238482_a_(ITextComponent p_238482_1_) {
      if (!Objects.equals(p_238482_1_, this.field_230684_a_)) {
         this.func_230994_c_(250);
      }

      this.field_230684_a_ = p_238482_1_;
   }

   public void func_230994_c_(int p_230994_1_) {
      this.field_230696_r_ = Util.milliTime() + (long)p_230994_1_;
   }

   public ITextComponent func_230458_i_() {
      return this.field_230684_a_;
   }

   public boolean func_230999_j_() {
      return this.field_230686_c_;
   }

   protected void func_230996_d_(boolean p_230996_1_) {
      this.field_230686_c_ = p_230996_1_;
   }

   public static final int UNSET_FG_COLOR = -1;
   protected int packedFGColor = UNSET_FG_COLOR;
   public int getFGColor() {
      if (packedFGColor != UNSET_FG_COLOR) return packedFGColor;
      return this.field_230693_o_ ? 16777215 : 10526880; // White : Light Grey
   }
   public void setFGColor(int color) {
      this.packedFGColor = color;
   }
   public void clearFGColor() {
      this.packedFGColor = UNSET_FG_COLOR;
   }
}