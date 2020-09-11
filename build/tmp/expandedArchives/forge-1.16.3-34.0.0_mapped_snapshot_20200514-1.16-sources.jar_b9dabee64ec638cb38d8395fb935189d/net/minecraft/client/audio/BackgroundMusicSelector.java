package net.minecraft.client.audio;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BackgroundMusicSelector {
   public static final Codec<BackgroundMusicSelector> field_232656_a_ = RecordCodecBuilder.create((p_232663_0_) -> {
      return p_232663_0_.group(SoundEvent.field_232678_a_.fieldOf("sound").forGetter((p_232669_0_) -> {
         return p_232669_0_.field_232657_b_;
      }), Codec.INT.fieldOf("min_delay").forGetter((p_232667_0_) -> {
         return p_232667_0_.field_232658_c_;
      }), Codec.INT.fieldOf("max_delay").forGetter((p_232665_0_) -> {
         return p_232665_0_.field_232659_d_;
      }), Codec.BOOL.fieldOf("replace_current_music").forGetter((p_232662_0_) -> {
         return p_232662_0_.field_232660_e_;
      })).apply(p_232663_0_, BackgroundMusicSelector::new);
   });
   private final SoundEvent field_232657_b_;
   private final int field_232658_c_;
   private final int field_232659_d_;
   private final boolean field_232660_e_;

   public BackgroundMusicSelector(SoundEvent p_i231428_1_, int p_i231428_2_, int p_i231428_3_, boolean p_i231428_4_) {
      this.field_232657_b_ = p_i231428_1_;
      this.field_232658_c_ = p_i231428_2_;
      this.field_232659_d_ = p_i231428_3_;
      this.field_232660_e_ = p_i231428_4_;
   }

   @OnlyIn(Dist.CLIENT)
   public SoundEvent func_232661_a_() {
      return this.field_232657_b_;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_232664_b_() {
      return this.field_232658_c_;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_232666_c_() {
      return this.field_232659_d_;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_232668_d_() {
      return this.field_232660_e_;
   }
}