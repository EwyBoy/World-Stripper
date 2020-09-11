package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.ElytraModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElytraLayer<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
   private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");
   private final ElytraModel<T> modelElytra = new ElytraModel<>();

   public ElytraLayer(IEntityRenderer<T, M> rendererIn) {
      super(rendererIn);
   }

   public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      ItemStack itemstack = entitylivingbaseIn.getItemStackFromSlot(EquipmentSlotType.CHEST);
      if (shouldRender(itemstack, entitylivingbaseIn)) {
         ResourceLocation resourcelocation;
         if (entitylivingbaseIn instanceof AbstractClientPlayerEntity) {
            AbstractClientPlayerEntity abstractclientplayerentity = (AbstractClientPlayerEntity)entitylivingbaseIn;
            if (abstractclientplayerentity.isPlayerInfoSet() && abstractclientplayerentity.getLocationElytra() != null) {
               resourcelocation = abstractclientplayerentity.getLocationElytra();
            } else if (abstractclientplayerentity.hasPlayerInfo() && abstractclientplayerentity.getLocationCape() != null && abstractclientplayerentity.isWearing(PlayerModelPart.CAPE)) {
               resourcelocation = abstractclientplayerentity.getLocationCape();
            } else {
               resourcelocation = getElytraTexture(itemstack, entitylivingbaseIn);
            }
         } else {
            resourcelocation = getElytraTexture(itemstack, entitylivingbaseIn);
         }

         matrixStackIn.push();
         matrixStackIn.translate(0.0D, 0.0D, 0.125D);
         this.getEntityModel().copyModelAttributesTo(this.modelElytra);
         this.modelElytra.setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         IVertexBuilder ivertexbuilder = ItemRenderer.func_239386_a_(bufferIn, RenderType.func_239263_a_(resourcelocation), false, itemstack.hasEffect());
         this.modelElytra.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.pop();
      }
   }

   /**
    * Determines if the ElytraLayer should render.
    * ItemStack and Entity are provided for modder convenience,
    * For example, using the same ElytraLayer for multiple custom Elytra.
    *
    * @param stack  The Elytra ItemStack
    * @param entity The entity being rendered.
    * @return If the ElytraLayer should render.
    */
   public boolean shouldRender(ItemStack stack, T entity) {
      return stack.getItem() == Items.ELYTRA;
   }

   /**
    * Gets the texture to use with this ElytraLayer.
    * This assumes the vanilla Elytra model.
    *
    * @param stack  The Elytra ItemStack.
    * @param entity The entity being rendered.
    * @return The texture.
    */
   public ResourceLocation getElytraTexture(ItemStack stack, T entity) {
      return TEXTURE_ELYTRA;
   }
}