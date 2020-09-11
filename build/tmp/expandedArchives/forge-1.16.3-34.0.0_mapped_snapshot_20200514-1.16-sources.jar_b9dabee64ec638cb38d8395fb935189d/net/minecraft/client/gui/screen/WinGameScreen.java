package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.resources.IResource;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class WinGameScreen extends Screen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ResourceLocation MINECRAFT_LOGO = new ResourceLocation("textures/gui/title/minecraft.png");
   private static final ResourceLocation field_194401_g = new ResourceLocation("textures/gui/title/edition.png");
   private static final ResourceLocation VIGNETTE_TEXTURE = new ResourceLocation("textures/misc/vignette.png");
   private static final String field_238663_q_ = "" + TextFormatting.WHITE + TextFormatting.OBFUSCATED + TextFormatting.GREEN + TextFormatting.AQUA;
   private final boolean poem;
   private final Runnable onFinished;
   private float time;
   private List<IReorderingProcessor> lines;
   private IntSet field_238664_v_;
   private int totalScrollLength;
   private float scrollSpeed = 0.5F;

   public WinGameScreen(boolean poemIn, Runnable onFinishedIn) {
      super(NarratorChatListener.EMPTY);
      this.poem = poemIn;
      this.onFinished = onFinishedIn;
      if (!poemIn) {
         this.scrollSpeed = 0.75F;
      }

   }

   public void func_231023_e_() {
      this.field_230706_i_.getMusicTicker().tick();
      this.field_230706_i_.getSoundHandler().tick(false);
      float f = (float)(this.totalScrollLength + this.field_230709_l_ + this.field_230709_l_ + 24) / this.scrollSpeed;
      if (this.time > f) {
         this.sendRespawnPacket();
      }

   }

   public void func_231175_as__() {
      this.sendRespawnPacket();
   }

   private void sendRespawnPacket() {
      this.onFinished.run();
      this.field_230706_i_.displayGuiScreen((Screen)null);
   }

   protected void func_231160_c_() {
      if (this.lines == null) {
         this.lines = Lists.newArrayList();
         this.field_238664_v_ = new IntOpenHashSet();
         IResource iresource = null;

         try {
            int i = 274;
            if (this.poem) {
               iresource = this.field_230706_i_.getResourceManager().getResource(new ResourceLocation("texts/end.txt"));
               InputStream inputstream = iresource.getInputStream();
               BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
               Random random = new Random(8124371L);

               String s;
               while((s = bufferedreader.readLine()) != null) {
                  int j;
                  String s1;
                  String s2;
                  for(s = s.replaceAll("PLAYERNAME", this.field_230706_i_.getSession().getUsername()); (j = s.indexOf(field_238663_q_)) != -1; s = s1 + TextFormatting.WHITE + TextFormatting.OBFUSCATED + "XXXXXXXX".substring(0, random.nextInt(4) + 3) + s2) {
                     s1 = s.substring(0, j);
                     s2 = s.substring(j + field_238663_q_.length());
                  }

                  this.lines.addAll(this.field_230706_i_.fontRenderer.func_238425_b_(new StringTextComponent(s), 274));
                  this.lines.add(IReorderingProcessor.field_242232_a);
               }

               inputstream.close();

               for(int k = 0; k < 8; ++k) {
                  this.lines.add(IReorderingProcessor.field_242232_a);
               }
            }

            InputStream inputstream1 = this.field_230706_i_.getResourceManager().getResource(new ResourceLocation("texts/credits.txt")).getInputStream();
            BufferedReader bufferedreader1 = new BufferedReader(new InputStreamReader(inputstream1, StandardCharsets.UTF_8));

            String s3;
            while((s3 = bufferedreader1.readLine()) != null) {
               s3 = s3.replaceAll("PLAYERNAME", this.field_230706_i_.getSession().getUsername());
               s3 = s3.replaceAll("\t", "    ");
               boolean flag;
               if (s3.startsWith("[C]")) {
                  s3 = s3.substring(3);
                  flag = true;
               } else {
                  flag = false;
               }

               for(IReorderingProcessor ireorderingprocessor : this.field_230706_i_.fontRenderer.func_238425_b_(new StringTextComponent(s3), 274)) {
                  if (flag) {
                     this.field_238664_v_.add(this.lines.size());
                  }

                  this.lines.add(ireorderingprocessor);
               }

               this.lines.add(IReorderingProcessor.field_242232_a);
            }

            inputstream1.close();
            this.totalScrollLength = this.lines.size() * 12;
         } catch (Exception exception) {
            LOGGER.error("Couldn't load credits", (Throwable)exception);
         } finally {
            IOUtils.closeQuietly((Closeable)iresource);
         }

      }
   }

   private void drawWinGameScreen(int p_146575_1_, int p_146575_2_, float p_146575_3_) {
      this.field_230706_i_.getTextureManager().bindTexture(AbstractGui.field_230663_f_);
      int i = this.field_230708_k_;
      float f = -this.time * 0.5F * this.scrollSpeed;
      float f1 = (float)this.field_230709_l_ - this.time * 0.5F * this.scrollSpeed;
      float f2 = 0.015625F;
      float f3 = this.time * 0.02F;
      float f4 = (float)(this.totalScrollLength + this.field_230709_l_ + this.field_230709_l_ + 24) / this.scrollSpeed;
      float f5 = (f4 - 20.0F - this.time) * 0.005F;
      if (f5 < f3) {
         f3 = f5;
      }

      if (f3 > 1.0F) {
         f3 = 1.0F;
      }

      f3 = f3 * f3;
      f3 = f3 * 96.0F / 255.0F;
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      bufferbuilder.pos(0.0D, (double)this.field_230709_l_, (double)this.func_230927_p_()).tex(0.0F, f * 0.015625F).color(f3, f3, f3, 1.0F).endVertex();
      bufferbuilder.pos((double)i, (double)this.field_230709_l_, (double)this.func_230927_p_()).tex((float)i * 0.015625F, f * 0.015625F).color(f3, f3, f3, 1.0F).endVertex();
      bufferbuilder.pos((double)i, 0.0D, (double)this.func_230927_p_()).tex((float)i * 0.015625F, f1 * 0.015625F).color(f3, f3, f3, 1.0F).endVertex();
      bufferbuilder.pos(0.0D, 0.0D, (double)this.func_230927_p_()).tex(0.0F, f1 * 0.015625F).color(f3, f3, f3, 1.0F).endVertex();
      tessellator.draw();
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.drawWinGameScreen(p_230430_2_, p_230430_3_, p_230430_4_);
      int i = 274;
      int j = this.field_230708_k_ / 2 - 137;
      int k = this.field_230709_l_ + 50;
      this.time += p_230430_4_;
      float f = -this.time * this.scrollSpeed;
      RenderSystem.pushMatrix();
      RenderSystem.translatef(0.0F, f, 0.0F);
      this.field_230706_i_.getTextureManager().bindTexture(MINECRAFT_LOGO);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableAlphaTest();
      RenderSystem.enableBlend();
      this.func_238459_a_(j, k, (p_238665_2_, p_238665_3_) -> {
         this.func_238474_b_(p_230430_1_, p_238665_2_ + 0, p_238665_3_, 0, 0, 155, 44);
         this.func_238474_b_(p_230430_1_, p_238665_2_ + 155, p_238665_3_, 0, 45, 155, 44);
      });
      RenderSystem.disableBlend();
      this.field_230706_i_.getTextureManager().bindTexture(field_194401_g);
      func_238463_a_(p_230430_1_, j + 88, k + 37, 0.0F, 0.0F, 98, 14, 128, 16);
      RenderSystem.disableAlphaTest();
      int l = k + 100;

      for(int i1 = 0; i1 < this.lines.size(); ++i1) {
         if (i1 == this.lines.size() - 1) {
            float f1 = (float)l + f - (float)(this.field_230709_l_ / 2 - 6);
            if (f1 < 0.0F) {
               RenderSystem.translatef(0.0F, -f1, 0.0F);
            }
         }

         if ((float)l + f + 12.0F + 8.0F > 0.0F && (float)l + f < (float)this.field_230709_l_) {
            IReorderingProcessor ireorderingprocessor = this.lines.get(i1);
            if (this.field_238664_v_.contains(i1)) {
               this.field_230712_o_.func_238407_a_(p_230430_1_, ireorderingprocessor, (float)(j + (274 - this.field_230712_o_.func_243245_a(ireorderingprocessor)) / 2), (float)l, 16777215);
            } else {
               this.field_230712_o_.random.setSeed((long)((float)((long)i1 * 4238972211L) + this.time / 4.0F));
               this.field_230712_o_.func_238407_a_(p_230430_1_, ireorderingprocessor, (float)j, (float)l, 16777215);
            }
         }

         l += 12;
      }

      RenderSystem.popMatrix();
      this.field_230706_i_.getTextureManager().bindTexture(VIGNETTE_TEXTURE);
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR);
      int j1 = this.field_230708_k_;
      int k1 = this.field_230709_l_;
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      bufferbuilder.pos(0.0D, (double)k1, (double)this.func_230927_p_()).tex(0.0F, 1.0F).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      bufferbuilder.pos((double)j1, (double)k1, (double)this.func_230927_p_()).tex(1.0F, 1.0F).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      bufferbuilder.pos((double)j1, 0.0D, (double)this.func_230927_p_()).tex(1.0F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      bufferbuilder.pos(0.0D, 0.0D, (double)this.func_230927_p_()).tex(0.0F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      tessellator.draw();
      RenderSystem.disableBlend();
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }
}