package net.minecraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.resources.IAsyncReloader;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.VanillaPack;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ResourceLoadProgressGui extends LoadingGui {
   private static final ResourceLocation MOJANG_LOGO_TEXTURE = new ResourceLocation("textures/gui/title/mojangstudios.png");
   private static final int field_238627_b_ = ColorHelper.PackedColor.func_233006_a_(255, 239, 50, 61);
   private static final int field_238628_c_ = field_238627_b_ & 16777215;
   private final Minecraft mc;
   private final IAsyncReloader asyncReloader;
   private final Consumer<Optional<Throwable>> completedCallback;
   private final boolean reloading;
   private float progress;
   private long fadeOutStart = -1L;
   private long fadeInStart = -1L;

   public ResourceLoadProgressGui(Minecraft p_i225928_1_, IAsyncReloader p_i225928_2_, Consumer<Optional<Throwable>> p_i225928_3_, boolean p_i225928_4_) {
      this.mc = p_i225928_1_;
      this.asyncReloader = p_i225928_2_;
      this.completedCallback = p_i225928_3_;
      this.reloading = p_i225928_4_;
   }

   public static void loadLogoTexture(Minecraft mc) {
      mc.getTextureManager().loadTexture(MOJANG_LOGO_TEXTURE, new ResourceLoadProgressGui.MojangLogoTexture());
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      int i = this.mc.getMainWindow().getScaledWidth();
      int j = this.mc.getMainWindow().getScaledHeight();
      long k = Util.milliTime();
      if (this.reloading && (this.asyncReloader.asyncPartDone() || this.mc.currentScreen != null) && this.fadeInStart == -1L) {
         this.fadeInStart = k;
      }

      float f = this.fadeOutStart > -1L ? (float)(k - this.fadeOutStart) / 1000.0F : -1.0F;
      float f1 = this.fadeInStart > -1L ? (float)(k - this.fadeInStart) / 500.0F : -1.0F;
      float f2;
      if (f >= 1.0F) {
         if (this.mc.currentScreen != null) {
            this.mc.currentScreen.func_230430_a_(p_230430_1_, 0, 0, p_230430_4_);
         }

         int l = MathHelper.ceil((1.0F - MathHelper.clamp(f - 1.0F, 0.0F, 1.0F)) * 255.0F);
         func_238467_a_(p_230430_1_, 0, 0, i, j, field_238628_c_ | l << 24);
         f2 = 1.0F - MathHelper.clamp(f - 1.0F, 0.0F, 1.0F);
      } else if (this.reloading) {
         if (this.mc.currentScreen != null && f1 < 1.0F) {
            this.mc.currentScreen.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
         }

         int i2 = MathHelper.ceil(MathHelper.clamp((double)f1, 0.15D, 1.0D) * 255.0D);
         func_238467_a_(p_230430_1_, 0, 0, i, j, field_238628_c_ | i2 << 24);
         f2 = MathHelper.clamp(f1, 0.0F, 1.0F);
      } else {
         func_238467_a_(p_230430_1_, 0, 0, i, j, field_238627_b_);
         f2 = 1.0F;
      }

      int j2 = (int)((double)this.mc.getMainWindow().getScaledWidth() * 0.5D);
      int i1 = (int)((double)this.mc.getMainWindow().getScaledHeight() * 0.5D);
      double d0 = Math.min((double)this.mc.getMainWindow().getScaledWidth() * 0.75D, (double)this.mc.getMainWindow().getScaledHeight()) * 0.25D;
      int j1 = (int)(d0 * 0.5D);
      double d1 = d0 * 4.0D;
      int k1 = (int)(d1 * 0.5D);
      this.mc.getTextureManager().bindTexture(MOJANG_LOGO_TEXTURE);
      RenderSystem.enableBlend();
      RenderSystem.blendEquation(32774);
      RenderSystem.blendFunc(770, 1);
      RenderSystem.alphaFunc(516, 0.0F);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, f2);
      func_238466_a_(p_230430_1_, j2 - k1, i1 - j1, k1, (int)d0, -0.0625F, 0.0F, 120, 60, 120, 120);
      func_238466_a_(p_230430_1_, j2, i1 - j1, k1, (int)d0, 0.0625F, 60.0F, 120, 60, 120, 120);
      RenderSystem.defaultBlendFunc();
      RenderSystem.defaultAlphaFunc();
      RenderSystem.disableBlend();
      int l1 = (int)((double)this.mc.getMainWindow().getScaledHeight() * 0.8325D);
      float f3 = this.asyncReloader.estimateExecutionSpeed();
      this.progress = MathHelper.clamp(this.progress * 0.95F + f3 * 0.050000012F, 0.0F, 1.0F);
      net.minecraftforge.fml.client.ClientModLoader.renderProgressText();
      if (f < 1.0F) {
         this.func_238629_a_(p_230430_1_, i / 2 - k1, l1 - 5, i / 2 + k1, l1 + 5, 1.0F - MathHelper.clamp(f, 0.0F, 1.0F));
      }

      if (f >= 2.0F) {
         this.mc.setLoadingGui((LoadingGui)null);
      }

      if (this.fadeOutStart == -1L && this.asyncReloader.fullyDone() && (!this.reloading || f1 >= 2.0F)) {
         this.fadeOutStart = Util.milliTime(); // Moved up to guard against inf loops caused by callback
         try {
            this.asyncReloader.join();
            this.completedCallback.accept(Optional.empty());
         } catch (Throwable throwable) {
            this.completedCallback.accept(Optional.of(throwable));
         }

         if (this.mc.currentScreen != null) {
            this.mc.currentScreen.func_231158_b_(this.mc, this.mc.getMainWindow().getScaledWidth(), this.mc.getMainWindow().getScaledHeight());
         }
      }

   }

   private void func_238629_a_(MatrixStack p_238629_1_, int p_238629_2_, int p_238629_3_, int p_238629_4_, int p_238629_5_, float p_238629_6_) {
      int i = MathHelper.ceil((float)(p_238629_4_ - p_238629_2_ - 2) * this.progress);
      int j = Math.round(p_238629_6_ * 255.0F);
      int k = ColorHelper.PackedColor.func_233006_a_(j, 255, 255, 255);
      func_238467_a_(p_238629_1_, p_238629_2_ + 1, p_238629_3_, p_238629_4_ - 1, p_238629_3_ + 1, k);
      func_238467_a_(p_238629_1_, p_238629_2_ + 1, p_238629_5_, p_238629_4_ - 1, p_238629_5_ - 1, k);
      func_238467_a_(p_238629_1_, p_238629_2_, p_238629_3_, p_238629_2_ + 1, p_238629_5_, k);
      func_238467_a_(p_238629_1_, p_238629_4_, p_238629_3_, p_238629_4_ - 1, p_238629_5_, k);
      func_238467_a_(p_238629_1_, p_238629_2_ + 2, p_238629_3_ + 2, p_238629_2_ + i, p_238629_5_ - 2, k);
   }

   public boolean isPauseScreen() {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   static class MojangLogoTexture extends SimpleTexture {
      public MojangLogoTexture() {
         super(ResourceLoadProgressGui.MOJANG_LOGO_TEXTURE);
      }

      protected SimpleTexture.TextureData getTextureData(IResourceManager resourceManager) {
         Minecraft minecraft = Minecraft.getInstance();
         VanillaPack vanillapack = minecraft.getPackFinder().getVanillaPack();

         try (InputStream inputstream = vanillapack.getResourceStream(ResourcePackType.CLIENT_RESOURCES, ResourceLoadProgressGui.MOJANG_LOGO_TEXTURE)) {
            return new SimpleTexture.TextureData(new TextureMetadataSection(true, true), NativeImage.read(inputstream));
         } catch (IOException ioexception) {
            return new SimpleTexture.TextureData(ioexception);
         }
      }
   }
}