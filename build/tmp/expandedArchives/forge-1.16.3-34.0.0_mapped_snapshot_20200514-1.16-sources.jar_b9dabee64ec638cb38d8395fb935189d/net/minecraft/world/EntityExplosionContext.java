package net.minecraft.world;

import java.util.Optional;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;

public class EntityExplosionContext extends ExplosionContext {
   private final Entity field_234889_a_;

   public EntityExplosionContext(Entity p_i231609_1_) {
      this.field_234889_a_ = p_i231609_1_;
   }

   public Optional<Float> func_230312_a_(Explosion p_230312_1_, IBlockReader p_230312_2_, BlockPos p_230312_3_, BlockState p_230312_4_, FluidState p_230312_5_) {
      return super.func_230312_a_(p_230312_1_, p_230312_2_, p_230312_3_, p_230312_4_, p_230312_5_).map((p_234890_6_) -> {
         return this.field_234889_a_.getExplosionResistance(p_230312_1_, p_230312_2_, p_230312_3_, p_230312_4_, p_230312_5_, p_234890_6_);
      });
   }

   public boolean func_230311_a_(Explosion p_230311_1_, IBlockReader p_230311_2_, BlockPos p_230311_3_, BlockState p_230311_4_, float p_230311_5_) {
      return this.field_234889_a_.canExplosionDestroyBlock(p_230311_1_, p_230311_2_, p_230311_3_, p_230311_4_, p_230311_5_);
   }
}