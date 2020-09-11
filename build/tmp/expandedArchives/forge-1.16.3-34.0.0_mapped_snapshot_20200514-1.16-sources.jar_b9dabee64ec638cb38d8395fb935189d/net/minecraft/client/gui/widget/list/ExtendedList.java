package net.minecraft.client.gui.widget.list;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ExtendedList<E extends AbstractList.AbstractListEntry<E>> extends AbstractList<E> {
   private boolean field_230698_a_;

   public ExtendedList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
      super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
   }

   public boolean func_231049_c__(boolean p_231049_1_) {
      if (!this.field_230698_a_ && this.func_230965_k_() == 0) {
         return false;
      } else {
         this.field_230698_a_ = !this.field_230698_a_;
         if (this.field_230698_a_ && this.func_230958_g_() == null && this.func_230965_k_() > 0) {
            this.func_241219_a_(AbstractList.Ordering.DOWN);
         } else if (this.field_230698_a_ && this.func_230958_g_() != null) {
            this.func_241574_n_();
         }

         return this.field_230698_a_;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public abstract static class AbstractListEntry<E extends ExtendedList.AbstractListEntry<E>> extends AbstractList.AbstractListEntry<E> {
      public boolean func_231049_c__(boolean p_231049_1_) {
         return false;
      }
   }
}