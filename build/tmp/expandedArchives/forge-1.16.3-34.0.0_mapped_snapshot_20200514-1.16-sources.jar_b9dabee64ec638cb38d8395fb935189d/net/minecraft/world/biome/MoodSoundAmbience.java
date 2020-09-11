package net.minecraft.world.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MoodSoundAmbience {
   public static final Codec<MoodSoundAmbience> field_235026_a_ = RecordCodecBuilder.create((p_235034_0_) -> {
      return p_235034_0_.group(SoundEvent.field_232678_a_.fieldOf("sound").forGetter((p_235040_0_) -> {
         return p_235040_0_.field_235028_c_;
      }), Codec.INT.fieldOf("tick_delay").forGetter((p_235038_0_) -> {
         return p_235038_0_.field_235029_d_;
      }), Codec.INT.fieldOf("block_search_extent").forGetter((p_235036_0_) -> {
         return p_235036_0_.field_235030_e_;
      }), Codec.DOUBLE.fieldOf("offset").forGetter((p_235033_0_) -> {
         return p_235033_0_.field_235031_f_;
      })).apply(p_235034_0_, MoodSoundAmbience::new);
   });
   public static final MoodSoundAmbience field_235027_b_ = new MoodSoundAmbience(SoundEvents.AMBIENT_CAVE, 6000, 8, 2.0D);
   private SoundEvent field_235028_c_;
   private int field_235029_d_;
   private int field_235030_e_;
   private double field_235031_f_;

   public MoodSoundAmbience(SoundEvent p_i231628_1_, int p_i231628_2_, int p_i231628_3_, double p_i231628_4_) {
      this.field_235028_c_ = p_i231628_1_;
      this.field_235029_d_ = p_i231628_2_;
      this.field_235030_e_ = p_i231628_3_;
      this.field_235031_f_ = p_i231628_4_;
   }

   @OnlyIn(Dist.CLIENT)
   public SoundEvent func_235032_a_() {
      return this.field_235028_c_;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_235035_b_() {
      return this.field_235029_d_;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_235037_c_() {
      return this.field_235030_e_;
   }

   @OnlyIn(Dist.CLIENT)
   public double func_235039_d_() {
      return this.field_235031_f_;
   }
}