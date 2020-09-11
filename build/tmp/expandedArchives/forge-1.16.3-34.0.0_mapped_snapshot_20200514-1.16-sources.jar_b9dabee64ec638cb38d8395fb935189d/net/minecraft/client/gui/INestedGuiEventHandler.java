package net.minecraft.client.gui;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface INestedGuiEventHandler extends IGuiEventListener {
   List<? extends IGuiEventListener> func_231039_at__();

   /**
    * Returns the first event listener that intersects with the mouse coordinates.
    */
   default Optional<IGuiEventListener> getEventListenerForPos(double mouseX, double mouseY) {
      for(IGuiEventListener iguieventlistener : this.func_231039_at__()) {
         if (iguieventlistener.func_231047_b_(mouseX, mouseY)) {
            return Optional.of(iguieventlistener);
         }
      }

      return Optional.empty();
   }

   default boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      for(IGuiEventListener iguieventlistener : this.func_231039_at__()) {
         if (iguieventlistener.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_)) {
            this.func_231035_a_(iguieventlistener);
            if (p_231044_5_ == 0) {
               this.func_231037_b__(true);
            }

            return true;
         }
      }

      return false;
   }

   default boolean func_231048_c_(double p_231048_1_, double p_231048_3_, int p_231048_5_) {
      this.func_231037_b__(false);
      return this.getEventListenerForPos(p_231048_1_, p_231048_3_).filter((p_212931_5_) -> {
         return p_212931_5_.func_231048_c_(p_231048_1_, p_231048_3_, p_231048_5_);
      }).isPresent();
   }

   default boolean func_231045_a_(double p_231045_1_, double p_231045_3_, int p_231045_5_, double p_231045_6_, double p_231045_8_) {
      return this.func_241217_q_() != null && this.func_231041_ay__() && p_231045_5_ == 0 ? this.func_241217_q_().func_231045_a_(p_231045_1_, p_231045_3_, p_231045_5_, p_231045_6_, p_231045_8_) : false;
   }

   boolean func_231041_ay__();

   void func_231037_b__(boolean p_231037_1_);

   default boolean func_231043_a_(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
      return this.getEventListenerForPos(p_231043_1_, p_231043_3_).filter((p_212929_6_) -> {
         return p_212929_6_.func_231043_a_(p_231043_1_, p_231043_3_, p_231043_5_);
      }).isPresent();
   }

   default boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      return this.func_241217_q_() != null && this.func_241217_q_().func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
   }

   default boolean keyReleased(int keyCode, int scanCode, int modifiers) {
      return this.func_241217_q_() != null && this.func_241217_q_().keyReleased(keyCode, scanCode, modifiers);
   }

   default boolean func_231042_a_(char p_231042_1_, int p_231042_2_) {
      return this.func_241217_q_() != null && this.func_241217_q_().func_231042_a_(p_231042_1_, p_231042_2_);
   }

   @Nullable
   IGuiEventListener func_241217_q_();

   void func_231035_a_(@Nullable IGuiEventListener p_231035_1_);

   default void setFocusedDefault(@Nullable IGuiEventListener eventListener) {
      this.func_231035_a_(eventListener);
      eventListener.func_231049_c__(true);
   }

   default void func_212932_b(@Nullable IGuiEventListener eventListener) {
      this.func_231035_a_(eventListener);
   }

   default boolean func_231049_c__(boolean p_231049_1_) {
      IGuiEventListener iguieventlistener = this.func_241217_q_();
      boolean flag = iguieventlistener != null;
      if (flag && iguieventlistener.func_231049_c__(p_231049_1_)) {
         return true;
      } else {
         List<? extends IGuiEventListener> list = this.func_231039_at__();
         int j = list.indexOf(iguieventlistener);
         int i;
         if (flag && j >= 0) {
            i = j + (p_231049_1_ ? 1 : 0);
         } else if (p_231049_1_) {
            i = 0;
         } else {
            i = list.size();
         }

         ListIterator<? extends IGuiEventListener> listiterator = list.listIterator(i);
         BooleanSupplier booleansupplier = p_231049_1_ ? listiterator::hasNext : listiterator::hasPrevious;
         Supplier<? extends IGuiEventListener> supplier = p_231049_1_ ? listiterator::next : listiterator::previous;

         while(booleansupplier.getAsBoolean()) {
            IGuiEventListener iguieventlistener1 = supplier.get();
            if (iguieventlistener1.func_231049_c__(p_231049_1_)) {
               this.func_231035_a_(iguieventlistener1);
               return true;
            }
         }

         this.func_231035_a_((IGuiEventListener)null);
         return false;
      }
   }
}