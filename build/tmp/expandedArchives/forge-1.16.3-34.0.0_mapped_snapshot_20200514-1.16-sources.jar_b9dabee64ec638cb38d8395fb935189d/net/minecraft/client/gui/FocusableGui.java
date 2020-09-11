package net.minecraft.client.gui;

import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class FocusableGui extends AbstractGui implements INestedGuiEventHandler {
   @Nullable
   private IGuiEventListener field_230699_a_;
   private boolean field_230700_b_;

   public final boolean func_231041_ay__() {
      return this.field_230700_b_;
   }

   public final void func_231037_b__(boolean p_231037_1_) {
      this.field_230700_b_ = p_231037_1_;
   }

   @Nullable
   public IGuiEventListener func_241217_q_() {
      return this.field_230699_a_;
   }

   public void func_231035_a_(@Nullable IGuiEventListener p_231035_1_) {
      this.field_230699_a_ = p_231035_1_;
   }
}