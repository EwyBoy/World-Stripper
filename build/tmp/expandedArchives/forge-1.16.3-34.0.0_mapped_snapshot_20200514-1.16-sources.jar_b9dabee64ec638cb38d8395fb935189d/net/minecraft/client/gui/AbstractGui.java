package net.minecraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.function.BiConsumer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractGui {
   public static final ResourceLocation field_230663_f_ = new ResourceLocation("textures/gui/options_background.png");
   public static final ResourceLocation field_230664_g_ = new ResourceLocation("textures/gui/container/stats_icons.png");
   public static final ResourceLocation field_230665_h_ = new ResourceLocation("textures/gui/icons.png");
   private int field_230662_a_;

   protected void func_238465_a_(MatrixStack p_238465_1_, int p_238465_2_, int p_238465_3_, int p_238465_4_, int p_238465_5_) {
      if (p_238465_3_ < p_238465_2_) {
         int i = p_238465_2_;
         p_238465_2_ = p_238465_3_;
         p_238465_3_ = i;
      }

      func_238467_a_(p_238465_1_, p_238465_2_, p_238465_4_, p_238465_3_ + 1, p_238465_4_ + 1, p_238465_5_);
   }

   protected void func_238473_b_(MatrixStack p_238473_1_, int p_238473_2_, int p_238473_3_, int p_238473_4_, int p_238473_5_) {
      if (p_238473_4_ < p_238473_3_) {
         int i = p_238473_3_;
         p_238473_3_ = p_238473_4_;
         p_238473_4_ = i;
      }

      func_238467_a_(p_238473_1_, p_238473_2_, p_238473_3_ + 1, p_238473_2_ + 1, p_238473_4_, p_238473_5_);
   }

   public static void func_238467_a_(MatrixStack p_238467_0_, int p_238467_1_, int p_238467_2_, int p_238467_3_, int p_238467_4_, int p_238467_5_) {
      func_238460_a_(p_238467_0_.getLast().getMatrix(), p_238467_1_, p_238467_2_, p_238467_3_, p_238467_4_, p_238467_5_);
   }

   private static void func_238460_a_(Matrix4f p_238460_0_, int p_238460_1_, int p_238460_2_, int p_238460_3_, int p_238460_4_, int p_238460_5_) {
      if (p_238460_1_ < p_238460_3_) {
         int i = p_238460_1_;
         p_238460_1_ = p_238460_3_;
         p_238460_3_ = i;
      }

      if (p_238460_2_ < p_238460_4_) {
         int j = p_238460_2_;
         p_238460_2_ = p_238460_4_;
         p_238460_4_ = j;
      }

      float f3 = (float)(p_238460_5_ >> 24 & 255) / 255.0F;
      float f = (float)(p_238460_5_ >> 16 & 255) / 255.0F;
      float f1 = (float)(p_238460_5_ >> 8 & 255) / 255.0F;
      float f2 = (float)(p_238460_5_ & 255) / 255.0F;
      BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
      RenderSystem.enableBlend();
      RenderSystem.disableTexture();
      RenderSystem.defaultBlendFunc();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
      bufferbuilder.pos(p_238460_0_, (float)p_238460_1_, (float)p_238460_4_, 0.0F).color(f, f1, f2, f3).endVertex();
      bufferbuilder.pos(p_238460_0_, (float)p_238460_3_, (float)p_238460_4_, 0.0F).color(f, f1, f2, f3).endVertex();
      bufferbuilder.pos(p_238460_0_, (float)p_238460_3_, (float)p_238460_2_, 0.0F).color(f, f1, f2, f3).endVertex();
      bufferbuilder.pos(p_238460_0_, (float)p_238460_1_, (float)p_238460_2_, 0.0F).color(f, f1, f2, f3).endVertex();
      bufferbuilder.finishDrawing();
      WorldVertexBufferUploader.draw(bufferbuilder);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
   }

   protected void func_238468_a_(MatrixStack p_238468_1_, int p_238468_2_, int p_238468_3_, int p_238468_4_, int p_238468_5_, int p_238468_6_, int p_238468_7_) {
      RenderSystem.disableTexture();
      RenderSystem.enableBlend();
      RenderSystem.disableAlphaTest();
      RenderSystem.defaultBlendFunc();
      RenderSystem.shadeModel(7425);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
      func_238462_a_(p_238468_1_.getLast().getMatrix(), bufferbuilder, p_238468_2_, p_238468_3_, p_238468_4_, p_238468_5_, this.field_230662_a_, p_238468_6_, p_238468_7_);
      tessellator.draw();
      RenderSystem.shadeModel(7424);
      RenderSystem.disableBlend();
      RenderSystem.enableAlphaTest();
      RenderSystem.enableTexture();
   }

   protected static void func_238462_a_(Matrix4f p_238462_0_, BufferBuilder p_238462_1_, int p_238462_2_, int p_238462_3_, int p_238462_4_, int p_238462_5_, int p_238462_6_, int p_238462_7_, int p_238462_8_) {
      float f = (float)(p_238462_7_ >> 24 & 255) / 255.0F;
      float f1 = (float)(p_238462_7_ >> 16 & 255) / 255.0F;
      float f2 = (float)(p_238462_7_ >> 8 & 255) / 255.0F;
      float f3 = (float)(p_238462_7_ & 255) / 255.0F;
      float f4 = (float)(p_238462_8_ >> 24 & 255) / 255.0F;
      float f5 = (float)(p_238462_8_ >> 16 & 255) / 255.0F;
      float f6 = (float)(p_238462_8_ >> 8 & 255) / 255.0F;
      float f7 = (float)(p_238462_8_ & 255) / 255.0F;
      p_238462_1_.pos(p_238462_0_, (float)p_238462_4_, (float)p_238462_3_, (float)p_238462_6_).color(f1, f2, f3, f).endVertex();
      p_238462_1_.pos(p_238462_0_, (float)p_238462_2_, (float)p_238462_3_, (float)p_238462_6_).color(f1, f2, f3, f).endVertex();
      p_238462_1_.pos(p_238462_0_, (float)p_238462_2_, (float)p_238462_5_, (float)p_238462_6_).color(f5, f6, f7, f4).endVertex();
      p_238462_1_.pos(p_238462_0_, (float)p_238462_4_, (float)p_238462_5_, (float)p_238462_6_).color(f5, f6, f7, f4).endVertex();
   }

   public static void func_238471_a_(MatrixStack p_238471_0_, FontRenderer p_238471_1_, String p_238471_2_, int p_238471_3_, int p_238471_4_, int p_238471_5_) {
      p_238471_1_.func_238405_a_(p_238471_0_, p_238471_2_, (float)(p_238471_3_ - p_238471_1_.getStringWidth(p_238471_2_) / 2), (float)p_238471_4_, p_238471_5_);
   }

   public static void func_238472_a_(MatrixStack p_238472_0_, FontRenderer p_238472_1_, ITextComponent p_238472_2_, int p_238472_3_, int p_238472_4_, int p_238472_5_) {
      IReorderingProcessor ireorderingprocessor = p_238472_2_.func_241878_f();
      p_238472_1_.func_238407_a_(p_238472_0_, ireorderingprocessor, (float)(p_238472_3_ - p_238472_1_.func_243245_a(ireorderingprocessor) / 2), (float)p_238472_4_, p_238472_5_);
   }

   public static void func_238476_c_(MatrixStack p_238476_0_, FontRenderer p_238476_1_, String p_238476_2_, int p_238476_3_, int p_238476_4_, int p_238476_5_) {
      p_238476_1_.func_238405_a_(p_238476_0_, p_238476_2_, (float)p_238476_3_, (float)p_238476_4_, p_238476_5_);
   }

   public static void func_238475_b_(MatrixStack p_238475_0_, FontRenderer p_238475_1_, ITextComponent p_238475_2_, int p_238475_3_, int p_238475_4_, int p_238475_5_) {
      p_238475_1_.func_243246_a(p_238475_0_, p_238475_2_, (float)p_238475_3_, (float)p_238475_4_, p_238475_5_);
   }

   public void func_238459_a_(int p_238459_1_, int p_238459_2_, BiConsumer<Integer, Integer> p_238459_3_) {
      RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      p_238459_3_.accept(p_238459_1_ + 1, p_238459_2_);
      p_238459_3_.accept(p_238459_1_ - 1, p_238459_2_);
      p_238459_3_.accept(p_238459_1_, p_238459_2_ + 1);
      p_238459_3_.accept(p_238459_1_, p_238459_2_ - 1);
      RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      p_238459_3_.accept(p_238459_1_, p_238459_2_);
   }

   public static void func_238470_a_(MatrixStack p_238470_0_, int p_238470_1_, int p_238470_2_, int p_238470_3_, int p_238470_4_, int p_238470_5_, TextureAtlasSprite p_238470_6_) {
      func_238461_a_(p_238470_0_.getLast().getMatrix(), p_238470_1_, p_238470_1_ + p_238470_4_, p_238470_2_, p_238470_2_ + p_238470_5_, p_238470_3_, p_238470_6_.getMinU(), p_238470_6_.getMaxU(), p_238470_6_.getMinV(), p_238470_6_.getMaxV());
   }

   public void func_238474_b_(MatrixStack p_238474_1_, int p_238474_2_, int p_238474_3_, int p_238474_4_, int p_238474_5_, int p_238474_6_, int p_238474_7_) {
      func_238464_a_(p_238474_1_, p_238474_2_, p_238474_3_, this.field_230662_a_, (float)p_238474_4_, (float)p_238474_5_, p_238474_6_, p_238474_7_, 256, 256);
   }

   public static void func_238464_a_(MatrixStack p_238464_0_, int p_238464_1_, int p_238464_2_, int p_238464_3_, float p_238464_4_, float p_238464_5_, int p_238464_6_, int p_238464_7_, int p_238464_8_, int p_238464_9_) {
      func_238469_a_(p_238464_0_, p_238464_1_, p_238464_1_ + p_238464_6_, p_238464_2_, p_238464_2_ + p_238464_7_, p_238464_3_, p_238464_6_, p_238464_7_, p_238464_4_, p_238464_5_, p_238464_9_, p_238464_8_);
   }

   public static void func_238466_a_(MatrixStack p_238466_0_, int p_238466_1_, int p_238466_2_, int p_238466_3_, int p_238466_4_, float p_238466_5_, float p_238466_6_, int p_238466_7_, int p_238466_8_, int p_238466_9_, int p_238466_10_) {
      func_238469_a_(p_238466_0_, p_238466_1_, p_238466_1_ + p_238466_3_, p_238466_2_, p_238466_2_ + p_238466_4_, 0, p_238466_7_, p_238466_8_, p_238466_5_, p_238466_6_, p_238466_9_, p_238466_10_);
   }

   public static void func_238463_a_(MatrixStack p_238463_0_, int p_238463_1_, int p_238463_2_, float p_238463_3_, float p_238463_4_, int p_238463_5_, int p_238463_6_, int p_238463_7_, int p_238463_8_) {
      func_238466_a_(p_238463_0_, p_238463_1_, p_238463_2_, p_238463_5_, p_238463_6_, p_238463_3_, p_238463_4_, p_238463_5_, p_238463_6_, p_238463_7_, p_238463_8_);
   }

   private static void func_238469_a_(MatrixStack p_238469_0_, int p_238469_1_, int p_238469_2_, int p_238469_3_, int p_238469_4_, int p_238469_5_, int p_238469_6_, int p_238469_7_, float p_238469_8_, float p_238469_9_, int p_238469_10_, int p_238469_11_) {
      func_238461_a_(p_238469_0_.getLast().getMatrix(), p_238469_1_, p_238469_2_, p_238469_3_, p_238469_4_, p_238469_5_, (p_238469_8_ + 0.0F) / (float)p_238469_10_, (p_238469_8_ + (float)p_238469_6_) / (float)p_238469_10_, (p_238469_9_ + 0.0F) / (float)p_238469_11_, (p_238469_9_ + (float)p_238469_7_) / (float)p_238469_11_);
   }

   private static void func_238461_a_(Matrix4f p_238461_0_, int p_238461_1_, int p_238461_2_, int p_238461_3_, int p_238461_4_, int p_238461_5_, float p_238461_6_, float p_238461_7_, float p_238461_8_, float p_238461_9_) {
      BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(p_238461_0_, (float)p_238461_1_, (float)p_238461_4_, (float)p_238461_5_).tex(p_238461_6_, p_238461_9_).endVertex();
      bufferbuilder.pos(p_238461_0_, (float)p_238461_2_, (float)p_238461_4_, (float)p_238461_5_).tex(p_238461_7_, p_238461_9_).endVertex();
      bufferbuilder.pos(p_238461_0_, (float)p_238461_2_, (float)p_238461_3_, (float)p_238461_5_).tex(p_238461_7_, p_238461_8_).endVertex();
      bufferbuilder.pos(p_238461_0_, (float)p_238461_1_, (float)p_238461_3_, (float)p_238461_5_).tex(p_238461_6_, p_238461_8_).endVertex();
      bufferbuilder.finishDrawing();
      RenderSystem.enableAlphaTest();
      WorldVertexBufferUploader.draw(bufferbuilder);
   }

   public int func_230927_p_() {
      return this.field_230662_a_;
   }

   public void func_230926_e_(int p_230926_1_) {
      this.field_230662_a_ = p_230926_1_;
   }
}