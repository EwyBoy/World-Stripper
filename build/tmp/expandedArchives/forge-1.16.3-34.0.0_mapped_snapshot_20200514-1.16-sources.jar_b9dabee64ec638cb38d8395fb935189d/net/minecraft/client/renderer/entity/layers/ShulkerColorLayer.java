package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.ShulkerRenderer;
import net.minecraft.client.renderer.entity.model.ShulkerModel;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShulkerColorLayer extends LayerRenderer<ShulkerEntity, ShulkerModel<ShulkerEntity>> {
   public ShulkerColorLayer(IEntityRenderer<ShulkerEntity, ShulkerModel<ShulkerEntity>> p_i50924_1_) {
      super(p_i50924_1_);
   }

   public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, ShulkerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      matrixStackIn.push();
      matrixStackIn.translate(0.0D, 1.0D, 0.0D);
      matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
      Quaternion quaternion = entitylivingbaseIn.getAttachmentFacing().getOpposite().getRotation();
      quaternion.conjugate();
      matrixStackIn.rotate(quaternion);
      matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
      matrixStackIn.translate(0.0D, -1.0D, 0.0D);
      DyeColor dyecolor = entitylivingbaseIn.getColor();
      ResourceLocation resourcelocation = dyecolor == null ? ShulkerRenderer.field_204402_a : ShulkerRenderer.SHULKER_ENDERGOLEM_TEXTURE[dyecolor.getId()];
      IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntitySolid(resourcelocation));
      this.getEntityModel().getHead().render(matrixStackIn, ivertexbuilder, packedLightIn, LivingRenderer.getPackedOverlay(entitylivingbaseIn, 0.0F));
      matrixStackIn.pop();
   }
}