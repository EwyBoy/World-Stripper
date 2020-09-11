package net.minecraft.client.renderer.model;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BakedQuad implements net.minecraftforge.client.model.pipeline.IVertexProducer {
   /**
    * Joined 4 vertex records, each stores packed data according to the VertexFormat of the quad. Vanilla minecraft uses
    * DefaultVertexFormats.BLOCK, Forge uses (usually) ITEM, use BakedQuad.getFormat() to get the correct format.
    */
   protected final int[] vertexData;
   protected final int tintIndex;
   protected final Direction face;
   protected final TextureAtlasSprite sprite;
   private final boolean field_239286_e_;

   public BakedQuad(int[] p_i232466_1_, int p_i232466_2_, Direction p_i232466_3_, TextureAtlasSprite p_i232466_4_, boolean p_i232466_5_) {
      this.vertexData = p_i232466_1_;
      this.tintIndex = p_i232466_2_;
      this.face = p_i232466_3_;
      this.sprite = p_i232466_4_;
      this.field_239286_e_ = p_i232466_5_;
   }

   public int[] getVertexData() {
      return this.vertexData;
   }

   public boolean hasTintIndex() {
      return this.tintIndex != -1;
   }

   public int getTintIndex() {
      return this.tintIndex;
   }

   public Direction getFace() {
      return this.face;
   }

   @Override
   public void pipe(net.minecraftforge.client.model.pipeline.IVertexConsumer consumer) {
      net.minecraftforge.client.model.pipeline.LightUtil.putBakedQuad(consumer, this);
   }

   public TextureAtlasSprite func_187508_a() {
      return sprite;
   }

   public boolean func_239287_f_() {
      return this.field_239286_e_;
   }
}