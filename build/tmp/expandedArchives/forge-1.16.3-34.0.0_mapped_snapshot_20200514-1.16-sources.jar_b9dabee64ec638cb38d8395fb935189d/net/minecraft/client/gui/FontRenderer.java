package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.gui.fonts.EmptyGlyph;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.gui.fonts.IGlyph;
import net.minecraft.client.gui.fonts.TexturedGlyph;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ICharacterConsumer;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.CharacterManager;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextProcessing;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FontRenderer {
   private static final Vector3f field_238401_c_ = new Vector3f(0.0F, 0.0F, 0.03F);
   public final int FONT_HEIGHT = 9;
   public final Random random = new Random();
   private final Function<ResourceLocation, Font> font;
   private final CharacterManager field_238402_e_;

   public FontRenderer(Function<ResourceLocation, Font> p_i232249_1_) {
      this.font = p_i232249_1_;
      this.field_238402_e_ = new CharacterManager((p_238404_1_, p_238404_2_) -> {
         return this.func_238419_a_(p_238404_2_.func_240729_k_()).func_238557_a_(p_238404_1_).getAdvance(p_238404_2_.getBold());
      });
   }

   private Font func_238419_a_(ResourceLocation p_238419_1_) {
      return this.font.apply(p_238419_1_);
   }

   public int func_238405_a_(MatrixStack p_238405_1_, String p_238405_2_, float p_238405_3_, float p_238405_4_, int p_238405_5_) {
      return this.renderString(p_238405_2_, p_238405_3_, p_238405_4_, p_238405_5_, p_238405_1_.getLast().getMatrix(), true, this.getBidiFlag());
   }

   public int func_238406_a_(MatrixStack p_238406_1_, String p_238406_2_, float p_238406_3_, float p_238406_4_, int p_238406_5_, boolean p_238406_6_) {
      RenderSystem.enableAlphaTest();
      return this.renderString(p_238406_2_, p_238406_3_, p_238406_4_, p_238406_5_, p_238406_1_.getLast().getMatrix(), true, p_238406_6_);
   }

   public int func_238421_b_(MatrixStack p_238421_1_, String p_238421_2_, float p_238421_3_, float p_238421_4_, int p_238421_5_) {
      RenderSystem.enableAlphaTest();
      return this.renderString(p_238421_2_, p_238421_3_, p_238421_4_, p_238421_5_, p_238421_1_.getLast().getMatrix(), false, this.getBidiFlag());
   }

   public int func_238407_a_(MatrixStack p_238407_1_, IReorderingProcessor p_238407_2_, float p_238407_3_, float p_238407_4_, int p_238407_5_) {
      RenderSystem.enableAlphaTest();
      return this.func_238415_a_(p_238407_2_, p_238407_3_, p_238407_4_, p_238407_5_, p_238407_1_.getLast().getMatrix(), true);
   }

   public int func_243246_a(MatrixStack p_243246_1_, ITextComponent p_243246_2_, float p_243246_3_, float p_243246_4_, int p_243246_5_) {
      RenderSystem.enableAlphaTest();
      return this.func_238415_a_(p_243246_2_.func_241878_f(), p_243246_3_, p_243246_4_, p_243246_5_, p_243246_1_.getLast().getMatrix(), true);
   }

   public int func_238422_b_(MatrixStack p_238422_1_, IReorderingProcessor p_238422_2_, float p_238422_3_, float p_238422_4_, int p_238422_5_) {
      RenderSystem.enableAlphaTest();
      return this.func_238415_a_(p_238422_2_, p_238422_3_, p_238422_4_, p_238422_5_, p_238422_1_.getLast().getMatrix(), false);
   }

   public int func_243248_b(MatrixStack p_243248_1_, ITextComponent p_243248_2_, float p_243248_3_, float p_243248_4_, int p_243248_5_) {
      RenderSystem.enableAlphaTest();
      return this.func_238415_a_(p_243248_2_.func_241878_f(), p_243248_3_, p_243248_4_, p_243248_5_, p_243248_1_.getLast().getMatrix(), false);
   }

   /**
    * Apply Unicode Bidirectional Algorithm to string and return a new possibly reordered string for visual rendering.
    */
   public String bidiReorder(String text) {
      try {
         Bidi bidi = new Bidi((new ArabicShaping(8)).shape(text), 127);
         bidi.setReorderingMode(0);
         return bidi.writeReordered(2);
      } catch (ArabicShapingException arabicshapingexception) {
         return text;
      }
   }

   private int renderString(String text, float x, float y, int color, Matrix4f matrix, boolean dropShadow, boolean p_228078_7_) {
      if (text == null) {
         return 0;
      } else {
         IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
         int i = this.func_238411_a_(text, x, y, color, dropShadow, matrix, irendertypebuffer$impl, false, 0, 15728880, p_228078_7_);
         irendertypebuffer$impl.finish();
         return i;
      }
   }

   private int func_238415_a_(IReorderingProcessor p_238415_1_, float p_238415_2_, float p_238415_3_, int p_238415_4_, Matrix4f p_238415_5_, boolean p_238415_6_) {
      IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
      int i = this.func_238416_a_(p_238415_1_, p_238415_2_, p_238415_3_, p_238415_4_, p_238415_6_, p_238415_5_, irendertypebuffer$impl, false, 0, 15728880);
      irendertypebuffer$impl.finish();
      return i;
   }

   public int renderString(String text, float x, float y, int color, boolean dropShadow, Matrix4f matrix, IRenderTypeBuffer buffer, boolean transparentIn, int colorBackgroundIn, int packedLight) {
      return this.func_238411_a_(text, x, y, color, dropShadow, matrix, buffer, transparentIn, colorBackgroundIn, packedLight, this.getBidiFlag());
   }

   public int func_238411_a_(String p_238411_1_, float p_238411_2_, float p_238411_3_, int p_238411_4_, boolean p_238411_5_, Matrix4f p_238411_6_, IRenderTypeBuffer p_238411_7_, boolean p_238411_8_, int p_238411_9_, int p_238411_10_, boolean p_238411_11_) {
      return this.func_238423_b_(p_238411_1_, p_238411_2_, p_238411_3_, p_238411_4_, p_238411_5_, p_238411_6_, p_238411_7_, p_238411_8_, p_238411_9_, p_238411_10_, p_238411_11_);
   }

   public int func_243247_a(ITextComponent p_243247_1_, float p_243247_2_, float p_243247_3_, int p_243247_4_, boolean p_243247_5_, Matrix4f p_243247_6_, IRenderTypeBuffer p_243247_7_, boolean p_243247_8_, int p_243247_9_, int p_243247_10_) {
      return this.func_238416_a_(p_243247_1_.func_241878_f(), p_243247_2_, p_243247_3_, p_243247_4_, p_243247_5_, p_243247_6_, p_243247_7_, p_243247_8_, p_243247_9_, p_243247_10_);
   }

   public int func_238416_a_(IReorderingProcessor p_238416_1_, float p_238416_2_, float p_238416_3_, int p_238416_4_, boolean p_238416_5_, Matrix4f p_238416_6_, IRenderTypeBuffer p_238416_7_, boolean p_238416_8_, int p_238416_9_, int p_238416_10_) {
      return this.func_238424_b_(p_238416_1_, p_238416_2_, p_238416_3_, p_238416_4_, p_238416_5_, p_238416_6_, p_238416_7_, p_238416_8_, p_238416_9_, p_238416_10_);
   }

   private static int func_238403_a_(int p_238403_0_) {
      return (p_238403_0_ & -67108864) == 0 ? p_238403_0_ | -16777216 : p_238403_0_;
   }

   private int func_238423_b_(String p_238423_1_, float p_238423_2_, float p_238423_3_, int p_238423_4_, boolean p_238423_5_, Matrix4f p_238423_6_, IRenderTypeBuffer p_238423_7_, boolean p_238423_8_, int p_238423_9_, int p_238423_10_, boolean p_238423_11_) {
      if (p_238423_11_) {
         p_238423_1_ = this.bidiReorder(p_238423_1_);
      }

      p_238423_4_ = func_238403_a_(p_238423_4_);
      Matrix4f matrix4f = p_238423_6_.copy();
      if (p_238423_5_) {
         this.renderStringAtPos(p_238423_1_, p_238423_2_, p_238423_3_, p_238423_4_, true, p_238423_6_, p_238423_7_, p_238423_8_, p_238423_9_, p_238423_10_);
         matrix4f.translate(field_238401_c_);
      }

      p_238423_2_ = this.renderStringAtPos(p_238423_1_, p_238423_2_, p_238423_3_, p_238423_4_, false, matrix4f, p_238423_7_, p_238423_8_, p_238423_9_, p_238423_10_);
      return (int)p_238423_2_ + (p_238423_5_ ? 1 : 0);
   }

   private int func_238424_b_(IReorderingProcessor p_238424_1_, float p_238424_2_, float p_238424_3_, int p_238424_4_, boolean p_238424_5_, Matrix4f p_238424_6_, IRenderTypeBuffer p_238424_7_, boolean p_238424_8_, int p_238424_9_, int p_238424_10_) {
      p_238424_4_ = func_238403_a_(p_238424_4_);
      Matrix4f matrix4f = p_238424_6_.copy();
      if (p_238424_5_) {
         this.func_238426_c_(p_238424_1_, p_238424_2_, p_238424_3_, p_238424_4_, true, p_238424_6_, p_238424_7_, p_238424_8_, p_238424_9_, p_238424_10_);
         matrix4f.translate(field_238401_c_);
      }

      p_238424_2_ = this.func_238426_c_(p_238424_1_, p_238424_2_, p_238424_3_, p_238424_4_, false, matrix4f, p_238424_7_, p_238424_8_, p_238424_9_, p_238424_10_);
      return (int)p_238424_2_ + (p_238424_5_ ? 1 : 0);
   }

   private float renderStringAtPos(String text, float x, float y, int color, boolean isShadow, Matrix4f matrix, IRenderTypeBuffer buffer, boolean isTransparent, int colorBackgroundIn, int packedLight) {
      FontRenderer.CharacterRenderer fontrenderer$characterrenderer = new FontRenderer.CharacterRenderer(buffer, x, y, color, isShadow, matrix, isTransparent, packedLight);
      TextProcessing.func_238346_c_(text, Style.field_240709_b_, fontrenderer$characterrenderer);
      return fontrenderer$characterrenderer.func_238441_a_(colorBackgroundIn, x);
   }

   private float func_238426_c_(IReorderingProcessor p_238426_1_, float p_238426_2_, float p_238426_3_, int p_238426_4_, boolean p_238426_5_, Matrix4f p_238426_6_, IRenderTypeBuffer p_238426_7_, boolean p_238426_8_, int p_238426_9_, int p_238426_10_) {
      FontRenderer.CharacterRenderer fontrenderer$characterrenderer = new FontRenderer.CharacterRenderer(p_238426_7_, p_238426_2_, p_238426_3_, p_238426_4_, p_238426_5_, p_238426_6_, p_238426_8_, p_238426_10_);
      p_238426_1_.accept(fontrenderer$characterrenderer);
      return fontrenderer$characterrenderer.func_238441_a_(p_238426_9_, p_238426_2_);
   }

   private void drawGlyph(TexturedGlyph glyphIn, boolean boldIn, boolean italicIn, float boldOffsetIn, float xIn, float yIn, Matrix4f matrix, IVertexBuilder bufferIn, float redIn, float greenIn, float blueIn, float alphaIn, int packedLight) {
      glyphIn.render(italicIn, xIn, yIn, matrix, bufferIn, redIn, greenIn, blueIn, alphaIn, packedLight);
      if (boldIn) {
         glyphIn.render(italicIn, xIn + boldOffsetIn, yIn, matrix, bufferIn, redIn, greenIn, blueIn, alphaIn, packedLight);
      }

   }

   /**
    * Returns the width of this string. Equivalent of FontMetrics.stringWidth(String s).
    */
   public int getStringWidth(String text) {
      return MathHelper.ceil(this.field_238402_e_.func_238350_a_(text));
   }

   public int func_238414_a_(ITextProperties p_238414_1_) {
      return MathHelper.ceil(this.field_238402_e_.func_238356_a_(p_238414_1_));
   }

   public int func_243245_a(IReorderingProcessor p_243245_1_) {
      return MathHelper.ceil(this.field_238402_e_.func_243238_a(p_243245_1_));
   }

   public String func_238413_a_(String p_238413_1_, int p_238413_2_, boolean p_238413_3_) {
      return p_238413_3_ ? this.field_238402_e_.func_238364_c_(p_238413_1_, p_238413_2_, Style.field_240709_b_) : this.field_238402_e_.func_238361_b_(p_238413_1_, p_238413_2_, Style.field_240709_b_);
   }

   public String func_238412_a_(String p_238412_1_, int p_238412_2_) {
      return this.field_238402_e_.func_238361_b_(p_238412_1_, p_238412_2_, Style.field_240709_b_);
   }

   public ITextProperties func_238417_a_(ITextProperties p_238417_1_, int p_238417_2_) {
      return this.field_238402_e_.func_238358_a_(p_238417_1_, p_238417_2_, Style.field_240709_b_);
   }

   public void func_238418_a_(ITextProperties p_238418_1_, int p_238418_2_, int p_238418_3_, int p_238418_4_, int p_238418_5_) {
      Matrix4f matrix4f = TransformationMatrix.identity().getMatrix();

      for(IReorderingProcessor ireorderingprocessor : this.func_238425_b_(p_238418_1_, p_238418_4_)) {
         this.func_238415_a_(ireorderingprocessor, (float)p_238418_2_, (float)p_238418_3_, p_238418_5_, matrix4f, false);
         p_238418_3_ += 9;
      }

   }

   /**
    * Returns the height (in pixels) of the given string if it is wordwrapped to the given max width.
    */
   public int getWordWrappedHeight(String str, int maxLength) {
      return 9 * this.field_238402_e_.func_238365_g_(str, maxLength, Style.field_240709_b_).size();
   }

   public List<IReorderingProcessor> func_238425_b_(ITextProperties p_238425_1_, int p_238425_2_) {
      return LanguageMap.getInstance().func_244260_a(this.field_238402_e_.func_238362_b_(p_238425_1_, p_238425_2_, Style.field_240709_b_));
   }

   /**
    * Get bidiFlag that controls if the Unicode Bidirectional Algorithm should be run before rendering any string
    */
   public boolean getBidiFlag() {
      return LanguageMap.getInstance().func_230505_b_();
   }

   public CharacterManager func_238420_b_() {
      return this.field_238402_e_;
   }

   @OnlyIn(Dist.CLIENT)
   class CharacterRenderer implements ICharacterConsumer {
      final IRenderTypeBuffer field_238427_a_;
      private final boolean field_238429_c_;
      private final float field_238430_d_;
      private final float field_238431_e_;
      private final float field_238432_f_;
      private final float field_238433_g_;
      private final float field_238434_h_;
      private final Matrix4f field_238435_i_;
      private final boolean field_238436_j_;
      private final int field_238437_k_;
      private float field_238438_l_;
      private float field_238439_m_;
      @Nullable
      private List<TexturedGlyph.Effect> field_238440_n_;

      private void func_238442_a_(TexturedGlyph.Effect p_238442_1_) {
         if (this.field_238440_n_ == null) {
            this.field_238440_n_ = Lists.newArrayList();
         }

         this.field_238440_n_.add(p_238442_1_);
      }

      public CharacterRenderer(IRenderTypeBuffer p_i232250_2_, float p_i232250_3_, float p_i232250_4_, int p_i232250_5_, boolean p_i232250_6_, Matrix4f p_i232250_7_, boolean p_i232250_8_, int p_i232250_9_) {
         this.field_238427_a_ = p_i232250_2_;
         this.field_238438_l_ = p_i232250_3_;
         this.field_238439_m_ = p_i232250_4_;
         this.field_238429_c_ = p_i232250_6_;
         this.field_238430_d_ = p_i232250_6_ ? 0.25F : 1.0F;
         this.field_238431_e_ = (float)(p_i232250_5_ >> 16 & 255) / 255.0F * this.field_238430_d_;
         this.field_238432_f_ = (float)(p_i232250_5_ >> 8 & 255) / 255.0F * this.field_238430_d_;
         this.field_238433_g_ = (float)(p_i232250_5_ & 255) / 255.0F * this.field_238430_d_;
         this.field_238434_h_ = (float)(p_i232250_5_ >> 24 & 255) / 255.0F;
         this.field_238435_i_ = p_i232250_7_;
         this.field_238436_j_ = p_i232250_8_;
         this.field_238437_k_ = p_i232250_9_;
      }

      public boolean accept(int p_accept_1_, Style p_accept_2_, int p_accept_3_) {
         Font font = FontRenderer.this.func_238419_a_(p_accept_2_.func_240729_k_());
         IGlyph iglyph = font.func_238557_a_(p_accept_3_);
         TexturedGlyph texturedglyph = p_accept_2_.getObfuscated() && p_accept_3_ != 32 ? font.obfuscate(iglyph) : font.func_238559_b_(p_accept_3_);
         boolean flag = p_accept_2_.getBold();
         float f3 = this.field_238434_h_;
         Color color = p_accept_2_.func_240711_a_();
         float f;
         float f1;
         float f2;
         if (color != null) {
            int i = color.func_240742_a_();
            f = (float)(i >> 16 & 255) / 255.0F * this.field_238430_d_;
            f1 = (float)(i >> 8 & 255) / 255.0F * this.field_238430_d_;
            f2 = (float)(i & 255) / 255.0F * this.field_238430_d_;
         } else {
            f = this.field_238431_e_;
            f1 = this.field_238432_f_;
            f2 = this.field_238433_g_;
         }

         if (!(texturedglyph instanceof EmptyGlyph)) {
            float f5 = flag ? iglyph.getBoldOffset() : 0.0F;
            float f4 = this.field_238429_c_ ? iglyph.getShadowOffset() : 0.0F;
            IVertexBuilder ivertexbuilder = this.field_238427_a_.getBuffer(texturedglyph.getRenderType(this.field_238436_j_));
            FontRenderer.this.drawGlyph(texturedglyph, flag, p_accept_2_.getItalic(), f5, this.field_238438_l_ + f4, this.field_238439_m_ + f4, this.field_238435_i_, ivertexbuilder, f, f1, f2, f3, this.field_238437_k_);
         }

         float f6 = iglyph.getAdvance(flag);
         float f7 = this.field_238429_c_ ? 1.0F : 0.0F;
         if (p_accept_2_.getStrikethrough()) {
            this.func_238442_a_(new TexturedGlyph.Effect(this.field_238438_l_ + f7 - 1.0F, this.field_238439_m_ + f7 + 4.5F, this.field_238438_l_ + f7 + f6, this.field_238439_m_ + f7 + 4.5F - 1.0F, 0.01F, f, f1, f2, f3));
         }

         if (p_accept_2_.getUnderlined()) {
            this.func_238442_a_(new TexturedGlyph.Effect(this.field_238438_l_ + f7 - 1.0F, this.field_238439_m_ + f7 + 9.0F, this.field_238438_l_ + f7 + f6, this.field_238439_m_ + f7 + 9.0F - 1.0F, 0.01F, f, f1, f2, f3));
         }

         this.field_238438_l_ += f6;
         return true;
      }

      public float func_238441_a_(int p_238441_1_, float p_238441_2_) {
         if (p_238441_1_ != 0) {
            float f = (float)(p_238441_1_ >> 24 & 255) / 255.0F;
            float f1 = (float)(p_238441_1_ >> 16 & 255) / 255.0F;
            float f2 = (float)(p_238441_1_ >> 8 & 255) / 255.0F;
            float f3 = (float)(p_238441_1_ & 255) / 255.0F;
            this.func_238442_a_(new TexturedGlyph.Effect(p_238441_2_ - 1.0F, this.field_238439_m_ + 9.0F, this.field_238438_l_ + 1.0F, this.field_238439_m_ - 1.0F, 0.01F, f1, f2, f3, f));
         }

         if (this.field_238440_n_ != null) {
            TexturedGlyph texturedglyph = FontRenderer.this.func_238419_a_(Style.field_240708_a_).getWhiteGlyph();
            IVertexBuilder ivertexbuilder = this.field_238427_a_.getBuffer(texturedglyph.getRenderType(this.field_238436_j_));

            for(TexturedGlyph.Effect texturedglyph$effect : this.field_238440_n_) {
               texturedglyph.renderEffect(texturedglyph$effect, this.field_238435_i_, ivertexbuilder, this.field_238437_k_);
            }
         }

         return this.field_238438_l_;
      }
   }
}