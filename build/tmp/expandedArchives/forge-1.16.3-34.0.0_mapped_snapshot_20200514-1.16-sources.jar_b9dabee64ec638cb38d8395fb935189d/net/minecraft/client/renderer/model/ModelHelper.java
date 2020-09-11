package net.minecraft.client.renderer.model;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelHelper {
   public static void func_239104_a_(ModelRenderer p_239104_0_, ModelRenderer p_239104_1_, ModelRenderer p_239104_2_, boolean p_239104_3_) {
      ModelRenderer modelrenderer = p_239104_3_ ? p_239104_0_ : p_239104_1_;
      ModelRenderer modelrenderer1 = p_239104_3_ ? p_239104_1_ : p_239104_0_;
      modelrenderer.rotateAngleY = (p_239104_3_ ? -0.3F : 0.3F) + p_239104_2_.rotateAngleY;
      modelrenderer1.rotateAngleY = (p_239104_3_ ? 0.6F : -0.6F) + p_239104_2_.rotateAngleY;
      modelrenderer.rotateAngleX = (-(float)Math.PI / 2F) + p_239104_2_.rotateAngleX + 0.1F;
      modelrenderer1.rotateAngleX = -1.5F + p_239104_2_.rotateAngleX;
   }

   public static void func_239102_a_(ModelRenderer p_239102_0_, ModelRenderer p_239102_1_, LivingEntity p_239102_2_, boolean p_239102_3_) {
      ModelRenderer modelrenderer = p_239102_3_ ? p_239102_0_ : p_239102_1_;
      ModelRenderer modelrenderer1 = p_239102_3_ ? p_239102_1_ : p_239102_0_;
      modelrenderer.rotateAngleY = p_239102_3_ ? -0.8F : 0.8F;
      modelrenderer.rotateAngleX = -0.97079635F;
      modelrenderer1.rotateAngleX = modelrenderer.rotateAngleX;
      float f = (float)CrossbowItem.getChargeTime(p_239102_2_.getActiveItemStack());
      float f1 = MathHelper.clamp((float)p_239102_2_.getItemInUseMaxCount(), 0.0F, f);
      float f2 = f1 / f;
      modelrenderer1.rotateAngleY = MathHelper.lerp(f2, 0.4F, 0.85F) * (float)(p_239102_3_ ? 1 : -1);
      modelrenderer1.rotateAngleX = MathHelper.lerp(f2, modelrenderer1.rotateAngleX, (-(float)Math.PI / 2F));
   }

   public static <T extends MobEntity> void func_239103_a_(ModelRenderer p_239103_0_, ModelRenderer p_239103_1_, T p_239103_2_, float p_239103_3_, float p_239103_4_) {
      float f = MathHelper.sin(p_239103_3_ * (float)Math.PI);
      float f1 = MathHelper.sin((1.0F - (1.0F - p_239103_3_) * (1.0F - p_239103_3_)) * (float)Math.PI);
      p_239103_0_.rotateAngleZ = 0.0F;
      p_239103_1_.rotateAngleZ = 0.0F;
      p_239103_0_.rotateAngleY = 0.15707964F;
      p_239103_1_.rotateAngleY = -0.15707964F;
      if (p_239103_2_.getPrimaryHand() == HandSide.RIGHT) {
         p_239103_0_.rotateAngleX = -1.8849558F + MathHelper.cos(p_239103_4_ * 0.09F) * 0.15F;
         p_239103_1_.rotateAngleX = -0.0F + MathHelper.cos(p_239103_4_ * 0.19F) * 0.5F;
         p_239103_0_.rotateAngleX += f * 2.2F - f1 * 0.4F;
         p_239103_1_.rotateAngleX += f * 1.2F - f1 * 0.4F;
      } else {
         p_239103_0_.rotateAngleX = -0.0F + MathHelper.cos(p_239103_4_ * 0.19F) * 0.5F;
         p_239103_1_.rotateAngleX = -1.8849558F + MathHelper.cos(p_239103_4_ * 0.09F) * 0.15F;
         p_239103_0_.rotateAngleX += f * 1.2F - f1 * 0.4F;
         p_239103_1_.rotateAngleX += f * 2.2F - f1 * 0.4F;
      }

      func_239101_a_(p_239103_0_, p_239103_1_, p_239103_4_);
   }

   public static void func_239101_a_(ModelRenderer p_239101_0_, ModelRenderer p_239101_1_, float p_239101_2_) {
      p_239101_0_.rotateAngleZ += MathHelper.cos(p_239101_2_ * 0.09F) * 0.05F + 0.05F;
      p_239101_1_.rotateAngleZ -= MathHelper.cos(p_239101_2_ * 0.09F) * 0.05F + 0.05F;
      p_239101_0_.rotateAngleX += MathHelper.sin(p_239101_2_ * 0.067F) * 0.05F;
      p_239101_1_.rotateAngleX -= MathHelper.sin(p_239101_2_ * 0.067F) * 0.05F;
   }

   public static void func_239105_a_(ModelRenderer p_239105_0_, ModelRenderer p_239105_1_, boolean p_239105_2_, float p_239105_3_, float p_239105_4_) {
      float f = MathHelper.sin(p_239105_3_ * (float)Math.PI);
      float f1 = MathHelper.sin((1.0F - (1.0F - p_239105_3_) * (1.0F - p_239105_3_)) * (float)Math.PI);
      p_239105_1_.rotateAngleZ = 0.0F;
      p_239105_0_.rotateAngleZ = 0.0F;
      p_239105_1_.rotateAngleY = -(0.1F - f * 0.6F);
      p_239105_0_.rotateAngleY = 0.1F - f * 0.6F;
      float f2 = -(float)Math.PI / (p_239105_2_ ? 1.5F : 2.25F);
      p_239105_1_.rotateAngleX = f2;
      p_239105_0_.rotateAngleX = f2;
      p_239105_1_.rotateAngleX += f * 1.2F - f1 * 0.4F;
      p_239105_0_.rotateAngleX += f * 1.2F - f1 * 0.4F;
      func_239101_a_(p_239105_1_, p_239105_0_, p_239105_4_);
   }
}