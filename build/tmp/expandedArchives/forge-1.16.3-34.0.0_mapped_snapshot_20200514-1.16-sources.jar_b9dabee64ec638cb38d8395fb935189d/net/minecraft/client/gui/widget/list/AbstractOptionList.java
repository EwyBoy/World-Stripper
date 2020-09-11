package net.minecraft.client.gui.widget.list;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.INestedGuiEventHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractOptionList<E extends AbstractOptionList.Entry<E>> extends AbstractList<E> {
   public AbstractOptionList(Minecraft p_i51139_1_, int p_i51139_2_, int p_i51139_3_, int p_i51139_4_, int p_i51139_5_, int p_i51139_6_) {
      super(p_i51139_1_, p_i51139_2_, p_i51139_3_, p_i51139_4_, p_i51139_5_, p_i51139_6_);
   }

   public boolean func_231049_c__(boolean p_231049_1_) {
      boolean flag = super.func_231049_c__(p_231049_1_);
      if (flag) {
         this.func_230954_d_(this.func_241217_q_());
      }

      return flag;
   }

   protected boolean func_230957_f_(int p_230957_1_) {
      return false;
   }

   @OnlyIn(Dist.CLIENT)
   public abstract static class Entry<E extends AbstractOptionList.Entry<E>> extends AbstractList.AbstractListEntry<E> implements INestedGuiEventHandler {
      @Nullable
      private IGuiEventListener field_214380_a;
      private boolean field_214381_b;

      public boolean func_231041_ay__() {
         return this.field_214381_b;
      }

      public void func_231037_b__(boolean p_231037_1_) {
         this.field_214381_b = p_231037_1_;
      }

      public void func_231035_a_(@Nullable IGuiEventListener p_231035_1_) {
         this.field_214380_a = p_231035_1_;
      }

      @Nullable
      public IGuiEventListener func_241217_q_() {
         return this.field_214380_a;
      }
   }
}