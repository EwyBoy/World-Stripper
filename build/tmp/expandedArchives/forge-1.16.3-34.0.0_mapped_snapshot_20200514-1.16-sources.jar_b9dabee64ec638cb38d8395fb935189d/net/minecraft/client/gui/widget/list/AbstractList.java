package net.minecraft.client.gui.widget.list;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FocusableGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractList<E extends AbstractList.AbstractListEntry<E>> extends FocusableGui implements IRenderable {
   protected final Minecraft field_230668_b_;
   protected final int field_230669_c_;
   private final List<E> field_230667_a_ = new AbstractList.SimpleArrayList();
   protected int field_230670_d_;
   protected int field_230671_e_;
   protected int field_230672_i_;
   protected int field_230673_j_;
   protected int field_230674_k_;
   protected int field_230675_l_;
   protected boolean field_230676_m_ = true;
   private double field_230678_o_;
   private boolean field_230679_p_ = true;
   private boolean field_230680_q_;
   protected int field_230677_n_;
   private boolean field_230681_r_;
   private E field_230682_s_;

   public AbstractList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int itemHeightIn) {
      this.field_230668_b_ = mcIn;
      this.field_230670_d_ = widthIn;
      this.field_230671_e_ = heightIn;
      this.field_230672_i_ = topIn;
      this.field_230673_j_ = bottomIn;
      this.field_230669_c_ = itemHeightIn;
      this.field_230675_l_ = 0;
      this.field_230674_k_ = widthIn;
   }

   public void func_230943_a_(boolean p_230943_1_) {
      this.field_230679_p_ = p_230943_1_;
   }

   protected void func_230944_a_(boolean p_230944_1_, int p_230944_2_) {
      this.field_230680_q_ = p_230944_1_;
      this.field_230677_n_ = p_230944_2_;
      if (!p_230944_1_) {
         this.field_230677_n_ = 0;
      }

   }

   public int func_230949_c_() {
      return 220;
   }

   @Nullable
   public E func_230958_g_() {
      return this.field_230682_s_;
   }

   public void func_241215_a_(@Nullable E p_241215_1_) {
      this.field_230682_s_ = p_241215_1_;
   }

   @Nullable
   public E func_241217_q_() {
      return (E)(super.func_241217_q_());
   }

   public final List<E> func_231039_at__() {
      return this.field_230667_a_;
   }

   protected final void func_230963_j_() {
      this.field_230667_a_.clear();
   }

   protected void func_230942_a_(Collection<E> p_230942_1_) {
      this.field_230667_a_.clear();
      this.field_230667_a_.addAll(p_230942_1_);
   }

   protected E func_230953_d_(int p_230953_1_) {
      return this.func_231039_at__().get(p_230953_1_);
   }

   protected int func_230513_b_(E p_230513_1_) {
      this.field_230667_a_.add(p_230513_1_);
      return this.field_230667_a_.size() - 1;
   }

   protected int func_230965_k_() {
      return this.func_231039_at__().size();
   }

   protected boolean func_230957_f_(int p_230957_1_) {
      return Objects.equals(this.func_230958_g_(), this.func_231039_at__().get(p_230957_1_));
   }

   @Nullable
   protected final E func_230933_a_(double p_230933_1_, double p_230933_3_) {
      int i = this.func_230949_c_() / 2;
      int j = this.field_230675_l_ + this.field_230670_d_ / 2;
      int k = j - i;
      int l = j + i;
      int i1 = MathHelper.floor(p_230933_3_ - (double)this.field_230672_i_) - this.field_230677_n_ + (int)this.func_230966_l_() - 4;
      int j1 = i1 / this.field_230669_c_;
      return (E)(p_230933_1_ < (double)this.func_230952_d_() && p_230933_1_ >= (double)k && p_230933_1_ <= (double)l && j1 >= 0 && i1 >= 0 && j1 < this.func_230965_k_() ? this.func_231039_at__().get(j1) : null);
   }

   public void func_230940_a_(int p_230940_1_, int p_230940_2_, int p_230940_3_, int p_230940_4_) {
      this.field_230670_d_ = p_230940_1_;
      this.field_230671_e_ = p_230940_2_;
      this.field_230672_i_ = p_230940_3_;
      this.field_230673_j_ = p_230940_4_;
      this.field_230675_l_ = 0;
      this.field_230674_k_ = p_230940_1_;
   }

   public void func_230959_g_(int p_230959_1_) {
      this.field_230675_l_ = p_230959_1_;
      this.field_230674_k_ = p_230959_1_ + this.field_230670_d_;
   }

   protected int func_230945_b_() {
      return this.func_230965_k_() * this.field_230669_c_ + this.field_230677_n_;
   }

   protected void func_230938_a_(int p_230938_1_, int p_230938_2_) {
   }

   protected void func_230448_a_(MatrixStack p_230448_1_, int p_230448_2_, int p_230448_3_, Tessellator p_230448_4_) {
   }

   protected void func_230433_a_(MatrixStack p_230433_1_) {
   }

   protected void func_230447_a_(MatrixStack p_230447_1_, int p_230447_2_, int p_230447_3_) {
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230433_a_(p_230430_1_);
      int i = this.func_230952_d_();
      int j = i + 6;
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      this.field_230668_b_.getTextureManager().bindTexture(AbstractGui.field_230663_f_);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float f = 32.0F;
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      bufferbuilder.pos((double)this.field_230675_l_, (double)this.field_230673_j_, 0.0D).tex((float)this.field_230675_l_ / 32.0F, (float)(this.field_230673_j_ + (int)this.func_230966_l_()) / 32.0F).color(32, 32, 32, 255).endVertex();
      bufferbuilder.pos((double)this.field_230674_k_, (double)this.field_230673_j_, 0.0D).tex((float)this.field_230674_k_ / 32.0F, (float)(this.field_230673_j_ + (int)this.func_230966_l_()) / 32.0F).color(32, 32, 32, 255).endVertex();
      bufferbuilder.pos((double)this.field_230674_k_, (double)this.field_230672_i_, 0.0D).tex((float)this.field_230674_k_ / 32.0F, (float)(this.field_230672_i_ + (int)this.func_230966_l_()) / 32.0F).color(32, 32, 32, 255).endVertex();
      bufferbuilder.pos((double)this.field_230675_l_, (double)this.field_230672_i_, 0.0D).tex((float)this.field_230675_l_ / 32.0F, (float)(this.field_230672_i_ + (int)this.func_230966_l_()) / 32.0F).color(32, 32, 32, 255).endVertex();
      tessellator.draw();
      int k = this.func_230968_n_();
      int l = this.field_230672_i_ + 4 - (int)this.func_230966_l_();
      if (this.field_230680_q_) {
         this.func_230448_a_(p_230430_1_, k, l, tessellator);
      }

      this.func_238478_a_(p_230430_1_, k, l, p_230430_2_, p_230430_3_, p_230430_4_);
      this.field_230668_b_.getTextureManager().bindTexture(AbstractGui.field_230663_f_);
      RenderSystem.enableDepthTest();
      RenderSystem.depthFunc(519);
      float f1 = 32.0F;
      int i1 = -100;
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      bufferbuilder.pos((double)this.field_230675_l_, (double)this.field_230672_i_, -100.0D).tex(0.0F, (float)this.field_230672_i_ / 32.0F).color(64, 64, 64, 255).endVertex();
      bufferbuilder.pos((double)(this.field_230675_l_ + this.field_230670_d_), (double)this.field_230672_i_, -100.0D).tex((float)this.field_230670_d_ / 32.0F, (float)this.field_230672_i_ / 32.0F).color(64, 64, 64, 255).endVertex();
      bufferbuilder.pos((double)(this.field_230675_l_ + this.field_230670_d_), 0.0D, -100.0D).tex((float)this.field_230670_d_ / 32.0F, 0.0F).color(64, 64, 64, 255).endVertex();
      bufferbuilder.pos((double)this.field_230675_l_, 0.0D, -100.0D).tex(0.0F, 0.0F).color(64, 64, 64, 255).endVertex();
      bufferbuilder.pos((double)this.field_230675_l_, (double)this.field_230671_e_, -100.0D).tex(0.0F, (float)this.field_230671_e_ / 32.0F).color(64, 64, 64, 255).endVertex();
      bufferbuilder.pos((double)(this.field_230675_l_ + this.field_230670_d_), (double)this.field_230671_e_, -100.0D).tex((float)this.field_230670_d_ / 32.0F, (float)this.field_230671_e_ / 32.0F).color(64, 64, 64, 255).endVertex();
      bufferbuilder.pos((double)(this.field_230675_l_ + this.field_230670_d_), (double)this.field_230673_j_, -100.0D).tex((float)this.field_230670_d_ / 32.0F, (float)this.field_230673_j_ / 32.0F).color(64, 64, 64, 255).endVertex();
      bufferbuilder.pos((double)this.field_230675_l_, (double)this.field_230673_j_, -100.0D).tex(0.0F, (float)this.field_230673_j_ / 32.0F).color(64, 64, 64, 255).endVertex();
      tessellator.draw();
      RenderSystem.depthFunc(515);
      RenderSystem.disableDepthTest();
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
      RenderSystem.disableAlphaTest();
      RenderSystem.shadeModel(7425);
      RenderSystem.disableTexture();
      int j1 = 4;
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      bufferbuilder.pos((double)this.field_230675_l_, (double)(this.field_230672_i_ + 4), 0.0D).tex(0.0F, 1.0F).color(0, 0, 0, 0).endVertex();
      bufferbuilder.pos((double)this.field_230674_k_, (double)(this.field_230672_i_ + 4), 0.0D).tex(1.0F, 1.0F).color(0, 0, 0, 0).endVertex();
      bufferbuilder.pos((double)this.field_230674_k_, (double)this.field_230672_i_, 0.0D).tex(1.0F, 0.0F).color(0, 0, 0, 255).endVertex();
      bufferbuilder.pos((double)this.field_230675_l_, (double)this.field_230672_i_, 0.0D).tex(0.0F, 0.0F).color(0, 0, 0, 255).endVertex();
      bufferbuilder.pos((double)this.field_230675_l_, (double)this.field_230673_j_, 0.0D).tex(0.0F, 1.0F).color(0, 0, 0, 255).endVertex();
      bufferbuilder.pos((double)this.field_230674_k_, (double)this.field_230673_j_, 0.0D).tex(1.0F, 1.0F).color(0, 0, 0, 255).endVertex();
      bufferbuilder.pos((double)this.field_230674_k_, (double)(this.field_230673_j_ - 4), 0.0D).tex(1.0F, 0.0F).color(0, 0, 0, 0).endVertex();
      bufferbuilder.pos((double)this.field_230675_l_, (double)(this.field_230673_j_ - 4), 0.0D).tex(0.0F, 0.0F).color(0, 0, 0, 0).endVertex();
      tessellator.draw();
      int k1 = this.func_230955_e_();
      if (k1 > 0) {
         int l1 = (int)((float)((this.field_230673_j_ - this.field_230672_i_) * (this.field_230673_j_ - this.field_230672_i_)) / (float)this.func_230945_b_());
         l1 = MathHelper.clamp(l1, 32, this.field_230673_j_ - this.field_230672_i_ - 8);
         int i2 = (int)this.func_230966_l_() * (this.field_230673_j_ - this.field_230672_i_ - l1) / k1 + this.field_230672_i_;
         if (i2 < this.field_230672_i_) {
            i2 = this.field_230672_i_;
         }

         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         bufferbuilder.pos((double)i, (double)this.field_230673_j_, 0.0D).tex(0.0F, 1.0F).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos((double)j, (double)this.field_230673_j_, 0.0D).tex(1.0F, 1.0F).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos((double)j, (double)this.field_230672_i_, 0.0D).tex(1.0F, 0.0F).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos((double)i, (double)this.field_230672_i_, 0.0D).tex(0.0F, 0.0F).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos((double)i, (double)(i2 + l1), 0.0D).tex(0.0F, 1.0F).color(128, 128, 128, 255).endVertex();
         bufferbuilder.pos((double)j, (double)(i2 + l1), 0.0D).tex(1.0F, 1.0F).color(128, 128, 128, 255).endVertex();
         bufferbuilder.pos((double)j, (double)i2, 0.0D).tex(1.0F, 0.0F).color(128, 128, 128, 255).endVertex();
         bufferbuilder.pos((double)i, (double)i2, 0.0D).tex(0.0F, 0.0F).color(128, 128, 128, 255).endVertex();
         bufferbuilder.pos((double)i, (double)(i2 + l1 - 1), 0.0D).tex(0.0F, 1.0F).color(192, 192, 192, 255).endVertex();
         bufferbuilder.pos((double)(j - 1), (double)(i2 + l1 - 1), 0.0D).tex(1.0F, 1.0F).color(192, 192, 192, 255).endVertex();
         bufferbuilder.pos((double)(j - 1), (double)i2, 0.0D).tex(1.0F, 0.0F).color(192, 192, 192, 255).endVertex();
         bufferbuilder.pos((double)i, (double)i2, 0.0D).tex(0.0F, 0.0F).color(192, 192, 192, 255).endVertex();
         tessellator.draw();
      }

      this.func_230447_a_(p_230430_1_, p_230430_2_, p_230430_3_);
      RenderSystem.enableTexture();
      RenderSystem.shadeModel(7424);
      RenderSystem.enableAlphaTest();
      RenderSystem.disableBlend();
   }

   protected void func_230951_c_(E p_230951_1_) {
      this.func_230932_a_((double)(this.func_231039_at__().indexOf(p_230951_1_) * this.field_230669_c_ + this.field_230669_c_ / 2 - (this.field_230673_j_ - this.field_230672_i_) / 2));
   }

   protected void func_230954_d_(E p_230954_1_) {
      int i = this.func_230962_i_(this.func_231039_at__().indexOf(p_230954_1_));
      int j = i - this.field_230672_i_ - 4 - this.field_230669_c_;
      if (j < 0) {
         this.func_230937_a_(j);
      }

      int k = this.field_230673_j_ - i - this.field_230669_c_ - this.field_230669_c_;
      if (k < 0) {
         this.func_230937_a_(-k);
      }

   }

   private void func_230937_a_(int p_230937_1_) {
      this.func_230932_a_(this.func_230966_l_() + (double)p_230937_1_);
   }

   public double func_230966_l_() {
      return this.field_230678_o_;
   }

   public void func_230932_a_(double p_230932_1_) {
      this.field_230678_o_ = MathHelper.clamp(p_230932_1_, 0.0D, (double)this.func_230955_e_());
   }

   private int func_230955_e_() {
      return Math.max(0, this.func_230945_b_() - (this.field_230673_j_ - this.field_230672_i_ - 4));
   }

   protected void func_230947_b_(double p_230947_1_, double p_230947_3_, int p_230947_5_) {
      this.field_230681_r_ = p_230947_5_ == 0 && p_230947_1_ >= (double)this.func_230952_d_() && p_230947_1_ < (double)(this.func_230952_d_() + 6);
   }

   protected int func_230952_d_() {
      return this.field_230670_d_ / 2 + 124;
   }

   public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      this.func_230947_b_(p_231044_1_, p_231044_3_, p_231044_5_);
      if (!this.func_231047_b_(p_231044_1_, p_231044_3_)) {
         return false;
      } else {
         E e = this.func_230933_a_(p_231044_1_, p_231044_3_);
         if (e != null) {
            if (e.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_)) {
               this.func_231035_a_(e);
               this.func_231037_b__(true);
               return true;
            }
         } else if (p_231044_5_ == 0) {
            this.func_230938_a_((int)(p_231044_1_ - (double)(this.field_230675_l_ + this.field_230670_d_ / 2 - this.func_230949_c_() / 2)), (int)(p_231044_3_ - (double)this.field_230672_i_) + (int)this.func_230966_l_() - 4);
            return true;
         }

         return this.field_230681_r_;
      }
   }

   public boolean func_231048_c_(double p_231048_1_, double p_231048_3_, int p_231048_5_) {
      if (this.func_241217_q_() != null) {
         this.func_241217_q_().func_231048_c_(p_231048_1_, p_231048_3_, p_231048_5_);
      }

      return false;
   }

   public boolean func_231045_a_(double p_231045_1_, double p_231045_3_, int p_231045_5_, double p_231045_6_, double p_231045_8_) {
      if (super.func_231045_a_(p_231045_1_, p_231045_3_, p_231045_5_, p_231045_6_, p_231045_8_)) {
         return true;
      } else if (p_231045_5_ == 0 && this.field_230681_r_) {
         if (p_231045_3_ < (double)this.field_230672_i_) {
            this.func_230932_a_(0.0D);
         } else if (p_231045_3_ > (double)this.field_230673_j_) {
            this.func_230932_a_((double)this.func_230955_e_());
         } else {
            double d0 = (double)Math.max(1, this.func_230955_e_());
            int i = this.field_230673_j_ - this.field_230672_i_;
            int j = MathHelper.clamp((int)((float)(i * i) / (float)this.func_230945_b_()), 32, i - 8);
            double d1 = Math.max(1.0D, d0 / (double)(i - j));
            this.func_230932_a_(this.func_230966_l_() + p_231045_8_ * d1);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean func_231043_a_(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
      this.func_230932_a_(this.func_230966_l_() - p_231043_5_ * (double)this.field_230669_c_ / 2.0D);
      return true;
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_)) {
         return true;
      } else if (p_231046_1_ == 264) {
         this.func_241219_a_(AbstractList.Ordering.DOWN);
         return true;
      } else if (p_231046_1_ == 265) {
         this.func_241219_a_(AbstractList.Ordering.UP);
         return true;
      } else {
         return false;
      }
   }

   protected void func_241219_a_(AbstractList.Ordering p_241219_1_) {
      this.func_241572_a_(p_241219_1_, (p_241573_0_) -> {
         return true;
      });
   }

   protected void func_241574_n_() {
      E e = this.func_230958_g_();
      if (e != null) {
         this.func_241215_a_(e);
         this.func_230954_d_(e);
      }

   }

   protected void func_241572_a_(AbstractList.Ordering p_241572_1_, Predicate<E> p_241572_2_) {
      int i = p_241572_1_ == AbstractList.Ordering.UP ? -1 : 1;
      if (!this.func_231039_at__().isEmpty()) {
         int j = this.func_231039_at__().indexOf(this.func_230958_g_());

         while(true) {
            int k = MathHelper.clamp(j + i, 0, this.func_230965_k_() - 1);
            if (j == k) {
               break;
            }

            E e = this.func_231039_at__().get(k);
            if (p_241572_2_.test(e)) {
               this.func_241215_a_(e);
               this.func_230954_d_(e);
               break;
            }

            j = k;
         }
      }

   }

   public boolean func_231047_b_(double p_231047_1_, double p_231047_3_) {
      return p_231047_3_ >= (double)this.field_230672_i_ && p_231047_3_ <= (double)this.field_230673_j_ && p_231047_1_ >= (double)this.field_230675_l_ && p_231047_1_ <= (double)this.field_230674_k_;
   }

   protected void func_238478_a_(MatrixStack p_238478_1_, int p_238478_2_, int p_238478_3_, int p_238478_4_, int p_238478_5_, float p_238478_6_) {
      int i = this.func_230965_k_();
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();

      for(int j = 0; j < i; ++j) {
         int k = this.func_230962_i_(j);
         int l = this.func_230948_b_(j);
         if (l >= this.field_230672_i_ && k <= this.field_230673_j_) {
            int i1 = p_238478_3_ + j * this.field_230669_c_ + this.field_230677_n_;
            int j1 = this.field_230669_c_ - 4;
            E e = this.func_230953_d_(j);
            int k1 = this.func_230949_c_();
            if (this.field_230679_p_ && this.func_230957_f_(j)) {
               int l1 = this.field_230675_l_ + this.field_230670_d_ / 2 - k1 / 2;
               int i2 = this.field_230675_l_ + this.field_230670_d_ / 2 + k1 / 2;
               RenderSystem.disableTexture();
               float f = this.func_230971_aw__() ? 1.0F : 0.5F;
               RenderSystem.color4f(f, f, f, 1.0F);
               bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
               bufferbuilder.pos((double)l1, (double)(i1 + j1 + 2), 0.0D).endVertex();
               bufferbuilder.pos((double)i2, (double)(i1 + j1 + 2), 0.0D).endVertex();
               bufferbuilder.pos((double)i2, (double)(i1 - 2), 0.0D).endVertex();
               bufferbuilder.pos((double)l1, (double)(i1 - 2), 0.0D).endVertex();
               tessellator.draw();
               RenderSystem.color4f(0.0F, 0.0F, 0.0F, 1.0F);
               bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
               bufferbuilder.pos((double)(l1 + 1), (double)(i1 + j1 + 1), 0.0D).endVertex();
               bufferbuilder.pos((double)(i2 - 1), (double)(i1 + j1 + 1), 0.0D).endVertex();
               bufferbuilder.pos((double)(i2 - 1), (double)(i1 - 1), 0.0D).endVertex();
               bufferbuilder.pos((double)(l1 + 1), (double)(i1 - 1), 0.0D).endVertex();
               tessellator.draw();
               RenderSystem.enableTexture();
            }

            int j2 = this.func_230968_n_();
            e.func_230432_a_(p_238478_1_, j, k, j2, k1, j1, p_238478_4_, p_238478_5_, this.func_231047_b_((double)p_238478_4_, (double)p_238478_5_) && Objects.equals(this.func_230933_a_((double)p_238478_4_, (double)p_238478_5_), e), p_238478_6_);
         }
      }

   }

   protected int func_230968_n_() {
      return this.field_230675_l_ + this.field_230670_d_ / 2 - this.func_230949_c_() / 2 + 2;
   }

   protected int func_230962_i_(int p_230962_1_) {
      return this.field_230672_i_ + 4 - (int)this.func_230966_l_() + p_230962_1_ * this.field_230669_c_ + this.field_230677_n_;
   }

   private int func_230948_b_(int p_230948_1_) {
      return this.func_230962_i_(p_230948_1_) + this.field_230669_c_;
   }

   protected boolean func_230971_aw__() {
      return false;
   }

   protected E func_230964_j_(int p_230964_1_) {
      E e = this.field_230667_a_.get(p_230964_1_);
      return (E)(this.func_230956_e_(this.field_230667_a_.get(p_230964_1_)) ? e : null);
   }

   protected boolean func_230956_e_(E p_230956_1_) {
      boolean flag = this.field_230667_a_.remove(p_230956_1_);
      if (flag && p_230956_1_ == this.func_230958_g_()) {
         this.func_241215_a_((E)null);
      }

      return flag;
   }

   private void func_238480_f_(AbstractList.AbstractListEntry<E> p_238480_1_) {
      p_238480_1_.field_230666_a_ = this;
   }

   public int getWidth() { return this.field_230670_d_; }
   public int getHeight() { return this.field_230671_e_; }
   public int getTop() { return this.field_230672_i_; }
   public int getBottom() { return this.field_230673_j_; }
   public int getLeft() { return this.field_230675_l_; }
   public int getRight() { return this.field_230674_k_; }

   @OnlyIn(Dist.CLIENT)
   public abstract static class AbstractListEntry<E extends AbstractList.AbstractListEntry<E>> implements IGuiEventListener {
      @Deprecated
      protected AbstractList<E> field_230666_a_;

      public abstract void func_230432_a_(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_);

      public boolean func_231047_b_(double p_231047_1_, double p_231047_3_) {
         return Objects.equals(this.field_230666_a_.func_230933_a_(p_231047_1_, p_231047_3_), this);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static enum Ordering {
      UP,
      DOWN;
   }

   @OnlyIn(Dist.CLIENT)
   class SimpleArrayList extends java.util.AbstractList<E> {
      private final List<E> field_216871_b = Lists.newArrayList();

      private SimpleArrayList() {
      }

      public E get(int p_get_1_) {
         return this.field_216871_b.get(p_get_1_);
      }

      public int size() {
         return this.field_216871_b.size();
      }

      public E set(int p_set_1_, E p_set_2_) {
         E e = this.field_216871_b.set(p_set_1_, p_set_2_);
         AbstractList.this.func_238480_f_(p_set_2_);
         return e;
      }

      public void add(int p_add_1_, E p_add_2_) {
         this.field_216871_b.add(p_add_1_, p_add_2_);
         AbstractList.this.func_238480_f_(p_add_2_);
      }

      public E remove(int p_remove_1_) {
         return this.field_216871_b.remove(p_remove_1_);
      }
   }
}