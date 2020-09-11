package net.minecraft.client.audio;

import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class BackgroundMusicTracks {
   public static final BackgroundMusicSelector field_232670_a_ = new BackgroundMusicSelector(SoundEvents.MUSIC_MENU, 20, 600, true);
   public static final BackgroundMusicSelector field_232671_b_ = new BackgroundMusicSelector(SoundEvents.MUSIC_CREATIVE, 12000, 24000, false);
   public static final BackgroundMusicSelector field_232672_c_ = new BackgroundMusicSelector(SoundEvents.MUSIC_CREDITS, 0, 0, true);
   public static final BackgroundMusicSelector field_232673_d_ = new BackgroundMusicSelector(SoundEvents.MUSIC_DRAGON, 0, 0, true);
   public static final BackgroundMusicSelector field_232674_e_ = new BackgroundMusicSelector(SoundEvents.MUSIC_END, 6000, 24000, true);
   public static final BackgroundMusicSelector field_232675_f_ = func_232677_a_(SoundEvents.MUSIC_UNDER_WATER);
   public static final BackgroundMusicSelector field_232676_g_ = func_232677_a_(SoundEvents.MUSIC_GAME);

   public static BackgroundMusicSelector func_232677_a_(SoundEvent p_232677_0_) {
      return new BackgroundMusicSelector(p_232677_0_, 12000, 24000, false);
   }
}