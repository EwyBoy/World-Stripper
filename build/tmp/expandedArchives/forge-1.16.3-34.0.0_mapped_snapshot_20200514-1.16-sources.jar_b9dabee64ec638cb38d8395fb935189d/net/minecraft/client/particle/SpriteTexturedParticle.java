package net.minecraft.client.particle;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class SpriteTexturedParticle extends TexturedParticle {
   protected TextureAtlasSprite sprite;

   protected SpriteTexturedParticle(ClientWorld p_i232447_1_, double p_i232447_2_, double p_i232447_4_, double p_i232447_6_) {
      super(p_i232447_1_, p_i232447_2_, p_i232447_4_, p_i232447_6_);
   }

   protected SpriteTexturedParticle(ClientWorld p_i232448_1_, double p_i232448_2_, double p_i232448_4_, double p_i232448_6_, double p_i232448_8_, double p_i232448_10_, double p_i232448_12_) {
      super(p_i232448_1_, p_i232448_2_, p_i232448_4_, p_i232448_6_, p_i232448_8_, p_i232448_10_, p_i232448_12_);
   }

   protected void setSprite(TextureAtlasSprite sprite) {
      this.sprite = sprite;
   }

   protected float getMinU() {
      return this.sprite.getMinU();
   }

   protected float getMaxU() {
      return this.sprite.getMaxU();
   }

   protected float getMinV() {
      return this.sprite.getMinV();
   }

   protected float getMaxV() {
      return this.sprite.getMaxV();
   }

   public void selectSpriteRandomly(IAnimatedSprite p_217568_1_) {
      this.setSprite(p_217568_1_.get(this.rand));
   }

   public void selectSpriteWithAge(IAnimatedSprite p_217566_1_) {
      this.setSprite(p_217566_1_.get(this.age, this.maxAge));
   }
}