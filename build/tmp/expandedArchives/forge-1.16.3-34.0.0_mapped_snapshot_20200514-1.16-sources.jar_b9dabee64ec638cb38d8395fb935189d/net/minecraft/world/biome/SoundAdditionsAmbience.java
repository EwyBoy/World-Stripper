package net.minecraft.world.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SoundAdditionsAmbience {
   public static final Codec<SoundAdditionsAmbience> field_235018_a_ = RecordCodecBuilder.create((p_235023_0_) -> {
      return p_235023_0_.group(SoundEvent.field_232678_a_.fieldOf("sound").forGetter((p_235025_0_) -> {
         return p_235025_0_.field_235019_b_;
      }), Codec.DOUBLE.fieldOf("tick_chance").forGetter((p_235022_0_) -> {
         return p_235022_0_.field_235020_c_;
      })).apply(p_235023_0_, SoundAdditionsAmbience::new);
   });
   private SoundEvent field_235019_b_;
   private double field_235020_c_;

   public SoundAdditionsAmbience(SoundEvent p_i231627_1_, double p_i231627_2_) {
      this.field_235019_b_ = p_i231627_1_;
      this.field_235020_c_ = p_i231627_2_;
   }

   @OnlyIn(Dist.CLIENT)
   public SoundEvent func_235021_a_() {
      return this.field_235019_b_;
   }

   @OnlyIn(Dist.CLIENT)
   public double func_235024_b_() {
      return this.field_235020_c_;
   }
}