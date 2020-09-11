package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class DynamicTexture extends Texture {
   private static final Logger field_243504_d = LogManager.getLogger();
   @Nullable
   private NativeImage dynamicTextureData;

   public DynamicTexture(NativeImage nativeImageIn) {
      this.dynamicTextureData = nativeImageIn;
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> {
            TextureUtil.prepareImage(this.getGlTextureId(), this.dynamicTextureData.getWidth(), this.dynamicTextureData.getHeight());
            this.updateDynamicTexture();
         });
      } else {
         TextureUtil.prepareImage(this.getGlTextureId(), this.dynamicTextureData.getWidth(), this.dynamicTextureData.getHeight());
         this.updateDynamicTexture();
      }

   }

   public DynamicTexture(int widthIn, int heightIn, boolean clearIn) {
      RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
      this.dynamicTextureData = new NativeImage(widthIn, heightIn, clearIn);
      TextureUtil.prepareImage(this.getGlTextureId(), this.dynamicTextureData.getWidth(), this.dynamicTextureData.getHeight());
   }

   public void loadTexture(IResourceManager manager) {
   }

   public void updateDynamicTexture() {
      if (this.dynamicTextureData != null) {
         this.bindTexture();
         this.dynamicTextureData.uploadTextureSub(0, 0, 0, false);
      } else {
         field_243504_d.warn("Trying to upload disposed texture {}", (int)this.getGlTextureId());
      }

   }

   @Nullable
   public NativeImage getTextureData() {
      return this.dynamicTextureData;
   }

   public void setTextureData(NativeImage nativeImageIn) {
      if (this.dynamicTextureData != null) {
         this.dynamicTextureData.close();
      }

      this.dynamicTextureData = nativeImageIn;
   }

   public void close() {
      if (this.dynamicTextureData != null) {
         this.dynamicTextureData.close();
         this.deleteGlTexture();
         this.dynamicTextureData = null;
      }

   }
}