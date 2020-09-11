package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MetaParticle extends Particle {
   protected MetaParticle(ClientWorld p_i232407_1_, double p_i232407_2_, double p_i232407_4_, double p_i232407_6_) {
      super(p_i232407_1_, p_i232407_2_, p_i232407_4_, p_i232407_6_);
   }

   protected MetaParticle(ClientWorld p_i232408_1_, double p_i232408_2_, double p_i232408_4_, double p_i232408_6_, double p_i232408_8_, double p_i232408_10_, double p_i232408_12_) {
      super(p_i232408_1_, p_i232408_2_, p_i232408_4_, p_i232408_6_, p_i232408_8_, p_i232408_10_, p_i232408_12_);
   }

   public final void renderParticle(IVertexBuilder buffer, ActiveRenderInfo renderInfo, float partialTicks) {
   }

   public IParticleRenderType getRenderType() {
      return IParticleRenderType.NO_RENDER;
   }
}