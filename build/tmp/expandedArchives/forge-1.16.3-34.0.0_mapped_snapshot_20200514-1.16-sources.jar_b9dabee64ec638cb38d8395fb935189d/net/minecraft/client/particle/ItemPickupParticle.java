package net.minecraft.client.particle;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemPickupParticle extends Particle {
   private final RenderTypeBuffers renderTypeBuffers;
   private final Entity item;
   private final Entity target;
   private int age;
   private final EntityRendererManager renderManager;

   public ItemPickupParticle(EntityRendererManager p_i232400_1_, RenderTypeBuffers p_i232400_2_, ClientWorld p_i232400_3_, Entity p_i232400_4_, Entity p_i232400_5_) {
      this(p_i232400_1_, p_i232400_2_, p_i232400_3_, p_i232400_4_, p_i232400_5_, p_i232400_4_.getMotion());
   }

   private ItemPickupParticle(EntityRendererManager p_i232401_1_, RenderTypeBuffers p_i232401_2_, ClientWorld p_i232401_3_, Entity p_i232401_4_, Entity p_i232401_5_, Vector3d p_i232401_6_) {
      super(p_i232401_3_, p_i232401_4_.getPosX(), p_i232401_4_.getPosY(), p_i232401_4_.getPosZ(), p_i232401_6_.x, p_i232401_6_.y, p_i232401_6_.z);
      this.renderTypeBuffers = p_i232401_2_;
      this.item = this.func_239181_a_(p_i232401_4_);
      this.target = p_i232401_5_;
      this.renderManager = p_i232401_1_;
   }

   private Entity func_239181_a_(Entity p_239181_1_) {
      return (Entity)(!(p_239181_1_ instanceof ItemEntity) ? p_239181_1_ : ((ItemEntity)p_239181_1_).func_234273_t_());
   }

   public IParticleRenderType getRenderType() {
      return IParticleRenderType.CUSTOM;
   }

   public void renderParticle(IVertexBuilder buffer, ActiveRenderInfo renderInfo, float partialTicks) {
      float f = ((float)this.age + partialTicks) / 3.0F;
      f = f * f;
      double d0 = MathHelper.lerp((double)partialTicks, this.target.lastTickPosX, this.target.getPosX());
      double d1 = MathHelper.lerp((double)partialTicks, this.target.lastTickPosY, this.target.getPosY()) + 0.5D;
      double d2 = MathHelper.lerp((double)partialTicks, this.target.lastTickPosZ, this.target.getPosZ());
      double d3 = MathHelper.lerp((double)f, this.item.getPosX(), d0);
      double d4 = MathHelper.lerp((double)f, this.item.getPosY(), d1);
      double d5 = MathHelper.lerp((double)f, this.item.getPosZ(), d2);
      IRenderTypeBuffer.Impl irendertypebuffer$impl = this.renderTypeBuffers.getBufferSource();
      Vector3d vector3d = renderInfo.getProjectedView();
      this.renderManager.renderEntityStatic(this.item, d3 - vector3d.getX(), d4 - vector3d.getY(), d5 - vector3d.getZ(), this.item.rotationYaw, partialTicks, new MatrixStack(), irendertypebuffer$impl, this.renderManager.getPackedLight(this.item, partialTicks));
      irendertypebuffer$impl.finish();
   }

   public void tick() {
      ++this.age;
      if (this.age == 3) {
         this.setExpired();
      }

   }
}