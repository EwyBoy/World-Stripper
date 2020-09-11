package net.minecraft.client.audio;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MusicTicker {
   private final Random random = new Random();
   private final Minecraft client;
   @Nullable
   private ISound currentMusic;
   private int timeUntilNextMusic = 100;

   public MusicTicker(Minecraft client) {
      this.client = client;
   }

   public void tick() {
      BackgroundMusicSelector backgroundmusicselector = this.client.func_238178_U_();
      if (this.currentMusic != null) {
         if (!backgroundmusicselector.func_232661_a_().getName().equals(this.currentMusic.getSoundLocation()) && backgroundmusicselector.func_232668_d_()) {
            this.client.getSoundHandler().stop(this.currentMusic);
            this.timeUntilNextMusic = MathHelper.nextInt(this.random, 0, backgroundmusicselector.func_232664_b_() / 2);
         }

         if (!this.client.getSoundHandler().isPlaying(this.currentMusic)) {
            this.currentMusic = null;
            this.timeUntilNextMusic = Math.min(this.timeUntilNextMusic, MathHelper.nextInt(this.random, backgroundmusicselector.func_232664_b_(), backgroundmusicselector.func_232666_c_()));
         }
      }

      this.timeUntilNextMusic = Math.min(this.timeUntilNextMusic, backgroundmusicselector.func_232666_c_());
      if (this.currentMusic == null && this.timeUntilNextMusic-- <= 0) {
         this.func_239539_a_(backgroundmusicselector);
      }

   }

   public void func_239539_a_(BackgroundMusicSelector p_239539_1_) {
      this.currentMusic = SimpleSound.music(p_239539_1_.func_232661_a_());
      if (this.currentMusic.getSound() != SoundHandler.MISSING_SOUND) {
         this.client.getSoundHandler().play(this.currentMusic);
      }

      this.timeUntilNextMusic = Integer.MAX_VALUE;
   }

   public void stop() {
      if (this.currentMusic != null) {
         this.client.getSoundHandler().stop(this.currentMusic);
         this.currentMusic = null;
      }

      this.timeUntilNextMusic += 100;
   }

   public boolean func_239540_b_(BackgroundMusicSelector p_239540_1_) {
      return this.currentMusic == null ? false : p_239540_1_.func_232661_a_().getName().equals(this.currentMusic.getSoundLocation());
   }
}