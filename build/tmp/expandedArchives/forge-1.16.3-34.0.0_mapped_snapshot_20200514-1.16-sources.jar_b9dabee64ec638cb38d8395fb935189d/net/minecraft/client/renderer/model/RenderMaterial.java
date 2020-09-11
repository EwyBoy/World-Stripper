package net.minecraft.client.renderer.model;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderMaterial {
   private final ResourceLocation atlasLocation;
   private final ResourceLocation textureLocation;
   @Nullable
   private RenderType renderType;

   public RenderMaterial(ResourceLocation atlasLocationIn, ResourceLocation textureLocationIn) {
      this.atlasLocation = atlasLocationIn;
      this.textureLocation = textureLocationIn;
   }

   public ResourceLocation getAtlasLocation() {
      return this.atlasLocation;
   }

   public ResourceLocation getTextureLocation() {
      return this.textureLocation;
   }

   public TextureAtlasSprite getSprite() {
      return Minecraft.getInstance().getAtlasSpriteGetter(this.getAtlasLocation()).apply(this.getTextureLocation());
   }

   public RenderType getRenderType(Function<ResourceLocation, RenderType> renderTypeGetter) {
      if (this.renderType == null) {
         this.renderType = renderTypeGetter.apply(this.atlasLocation);
      }

      return this.renderType;
   }

   public IVertexBuilder getBuffer(IRenderTypeBuffer bufferIn, Function<ResourceLocation, RenderType> renderTypeGetter) {
      return this.getSprite().wrapBuffer(bufferIn.getBuffer(this.getRenderType(renderTypeGetter)));
   }

   public IVertexBuilder func_241742_a_(IRenderTypeBuffer p_241742_1_, Function<ResourceLocation, RenderType> p_241742_2_, boolean p_241742_3_) {
      return this.getSprite().wrapBuffer(ItemRenderer.func_239391_c_(p_241742_1_, this.getRenderType(p_241742_2_), true, p_241742_3_));
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         RenderMaterial rendermaterial = (RenderMaterial)p_equals_1_;
         return this.atlasLocation.equals(rendermaterial.atlasLocation) && this.textureLocation.equals(rendermaterial.textureLocation);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(this.atlasLocation, this.textureLocation);
   }

   public String toString() {
      return "Material{atlasLocation=" + this.atlasLocation + ", texture=" + this.textureLocation + '}';
   }
}