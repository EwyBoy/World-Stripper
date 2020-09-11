package net.minecraft.client.audio;

import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class TickableSound extends LocatableSound implements ITickableSound {
   private boolean donePlaying;

   protected TickableSound(SoundEvent soundIn, SoundCategory categoryIn) {
      super(soundIn, categoryIn);
   }

   public boolean isDonePlaying() {
      return this.donePlaying;
   }

   protected final void func_239509_o_() {
      this.donePlaying = true;
      this.repeat = false;
   }
}