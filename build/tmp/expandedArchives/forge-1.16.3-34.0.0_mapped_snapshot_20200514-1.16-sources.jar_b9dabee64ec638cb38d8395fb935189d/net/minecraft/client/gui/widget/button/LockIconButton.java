package net.minecraft.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LockIconButton extends Button {
   private boolean locked;

   public LockIconButton(int p_i51133_1_, int p_i51133_2_, Button.IPressable p_i51133_3_) {
      super(p_i51133_1_, p_i51133_2_, 20, 20, new TranslationTextComponent("narrator.button.difficulty_lock"), p_i51133_3_);
   }

   protected IFormattableTextComponent func_230442_c_() {
      return super.func_230442_c_().func_240702_b_(". ").func_230529_a_(this.isLocked() ? new TranslationTextComponent("narrator.button.difficulty_lock.locked") : new TranslationTextComponent("narrator.button.difficulty_lock.unlocked"));
   }

   public boolean isLocked() {
      return this.locked;
   }

   public void setLocked(boolean lockedIn) {
      this.locked = lockedIn;
   }

   public void func_230431_b_(MatrixStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
      Minecraft.getInstance().getTextureManager().bindTexture(Button.field_230687_i_);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      LockIconButton.Icon lockiconbutton$icon;
      if (!this.field_230693_o_) {
         lockiconbutton$icon = this.locked ? LockIconButton.Icon.LOCKED_DISABLED : LockIconButton.Icon.UNLOCKED_DISABLED;
      } else if (this.func_230449_g_()) {
         lockiconbutton$icon = this.locked ? LockIconButton.Icon.LOCKED_HOVER : LockIconButton.Icon.UNLOCKED_HOVER;
      } else {
         lockiconbutton$icon = this.locked ? LockIconButton.Icon.LOCKED : LockIconButton.Icon.UNLOCKED;
      }

      this.func_238474_b_(p_230431_1_, this.field_230690_l_, this.field_230691_m_, lockiconbutton$icon.getX(), lockiconbutton$icon.getY(), this.field_230688_j_, this.field_230689_k_);
   }

   @OnlyIn(Dist.CLIENT)
   static enum Icon {
      LOCKED(0, 146),
      LOCKED_HOVER(0, 166),
      LOCKED_DISABLED(0, 186),
      UNLOCKED(20, 146),
      UNLOCKED_HOVER(20, 166),
      UNLOCKED_DISABLED(20, 186);

      private final int x;
      private final int y;

      private Icon(int xIn, int yIn) {
         this.x = xIn;
         this.y = yIn;
      }

      public int getX() {
         return this.x;
      }

      public int getY() {
         return this.y;
      }
   }
}