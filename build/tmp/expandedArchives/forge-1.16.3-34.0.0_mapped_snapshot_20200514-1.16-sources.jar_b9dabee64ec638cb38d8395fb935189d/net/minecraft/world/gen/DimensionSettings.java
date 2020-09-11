package net.minecraft.world.gen;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKeyCodec;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.NoiseSettings;
import net.minecraft.world.gen.settings.ScalingSettings;
import net.minecraft.world.gen.settings.SlideSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

public final class DimensionSettings {
   public static final Codec<DimensionSettings> field_236097_a_ = RecordCodecBuilder.create((p_236112_0_) -> {
      return p_236112_0_.group(DimensionStructuresSettings.field_236190_a_.fieldOf("structures").forGetter(DimensionSettings::func_236108_a_), NoiseSettings.field_236156_a_.fieldOf("noise").forGetter(DimensionSettings::func_236113_b_), BlockState.field_235877_b_.fieldOf("default_block").forGetter(DimensionSettings::func_236115_c_), BlockState.field_235877_b_.fieldOf("default_fluid").forGetter(DimensionSettings::func_236116_d_), Codec.intRange(-20, 276).fieldOf("bedrock_roof_position").forGetter(DimensionSettings::func_236117_e_), Codec.intRange(-20, 276).fieldOf("bedrock_floor_position").forGetter(DimensionSettings::func_236118_f_), Codec.intRange(0, 255).fieldOf("sea_level").forGetter(DimensionSettings::func_236119_g_), Codec.BOOL.fieldOf("disable_mob_generation").forGetter(DimensionSettings::func_236120_h_)).apply(p_236112_0_, DimensionSettings::new);
   });
   public static final Codec<Supplier<DimensionSettings>> field_236098_b_ = RegistryKeyCodec.func_241794_a_(Registry.field_243549_ar, field_236097_a_);
   private final DimensionStructuresSettings field_236099_c_;
   private final NoiseSettings field_236100_d_;
   private final BlockState field_236101_e_;
   private final BlockState field_236102_f_;
   private final int field_236103_g_;
   private final int field_236104_h_;
   private final int field_236105_i_;
   private final boolean field_236106_j_;
   public static final RegistryKey<DimensionSettings> field_242734_c = RegistryKey.func_240903_a_(Registry.field_243549_ar, new ResourceLocation("overworld"));
   public static final RegistryKey<DimensionSettings> field_242735_d = RegistryKey.func_240903_a_(Registry.field_243549_ar, new ResourceLocation("amplified"));
   public static final RegistryKey<DimensionSettings> field_242736_e = RegistryKey.func_240903_a_(Registry.field_243549_ar, new ResourceLocation("nether"));
   public static final RegistryKey<DimensionSettings> field_242737_f = RegistryKey.func_240903_a_(Registry.field_243549_ar, new ResourceLocation("end"));
   public static final RegistryKey<DimensionSettings> field_242738_g = RegistryKey.func_240903_a_(Registry.field_243549_ar, new ResourceLocation("caves"));
   public static final RegistryKey<DimensionSettings> field_242739_h = RegistryKey.func_240903_a_(Registry.field_243549_ar, new ResourceLocation("floating_islands"));
   private static final DimensionSettings field_242740_q = func_242745_a(field_242734_c, func_242743_a(new DimensionStructuresSettings(true), false, field_242734_c.func_240901_a_()));

   private DimensionSettings(DimensionStructuresSettings p_i231905_1_, NoiseSettings p_i231905_2_, BlockState p_i231905_3_, BlockState p_i231905_4_, int p_i231905_5_, int p_i231905_6_, int p_i231905_7_, boolean p_i231905_8_) {
      this.field_236099_c_ = p_i231905_1_;
      this.field_236100_d_ = p_i231905_2_;
      this.field_236101_e_ = p_i231905_3_;
      this.field_236102_f_ = p_i231905_4_;
      this.field_236103_g_ = p_i231905_5_;
      this.field_236104_h_ = p_i231905_6_;
      this.field_236105_i_ = p_i231905_7_;
      this.field_236106_j_ = p_i231905_8_;
   }

   public DimensionStructuresSettings func_236108_a_() {
      return this.field_236099_c_;
   }

   public NoiseSettings func_236113_b_() {
      return this.field_236100_d_;
   }

   public BlockState func_236115_c_() {
      return this.field_236101_e_;
   }

   public BlockState func_236116_d_() {
      return this.field_236102_f_;
   }

   public int func_236117_e_() {
      return this.field_236103_g_;
   }

   public int func_236118_f_() {
      return this.field_236104_h_;
   }

   public int func_236119_g_() {
      return this.field_236105_i_;
   }

   @Deprecated
   protected boolean func_236120_h_() {
      return this.field_236106_j_;
   }

   public boolean func_242744_a(RegistryKey<DimensionSettings> p_242744_1_) {
      return Objects.equals(this, WorldGenRegistries.field_243658_j.func_230516_a_(p_242744_1_));
   }

   private static DimensionSettings func_242745_a(RegistryKey<DimensionSettings> p_242745_0_, DimensionSettings p_242745_1_) {
      WorldGenRegistries.func_243664_a(WorldGenRegistries.field_243658_j, p_242745_0_.func_240901_a_(), p_242745_1_);
      return p_242745_1_;
   }

   public static DimensionSettings func_242746_i() {
      return field_242740_q;
   }

   private static DimensionSettings func_242742_a(DimensionStructuresSettings p_242742_0_, BlockState p_242742_1_, BlockState p_242742_2_, ResourceLocation p_242742_3_, boolean p_242742_4_, boolean p_242742_5_) {
      return new DimensionSettings(p_242742_0_, new NoiseSettings(128, new ScalingSettings(2.0D, 1.0D, 80.0D, 160.0D), new SlideSettings(-3000, 64, -46), new SlideSettings(-30, 7, 1), 2, 1, 0.0D, 0.0D, true, false, p_242742_5_, false), p_242742_1_, p_242742_2_, -10, -10, 0, p_242742_4_);
   }

   private static DimensionSettings func_242741_a(DimensionStructuresSettings p_242741_0_, BlockState p_242741_1_, BlockState p_242741_2_, ResourceLocation p_242741_3_) {
      Map<Structure<?>, StructureSeparationSettings> map = Maps.newHashMap(DimensionStructuresSettings.field_236191_b_);
      map.put(Structure.field_236372_h_, new StructureSeparationSettings(25, 10, 34222645));
      return new DimensionSettings(new DimensionStructuresSettings(Optional.ofNullable(p_242741_0_.func_236199_b_()), map), new NoiseSettings(128, new ScalingSettings(1.0D, 3.0D, 80.0D, 60.0D), new SlideSettings(120, 3, 0), new SlideSettings(320, 4, -1), 1, 2, 0.0D, 0.019921875D, false, false, false, false), p_242741_1_, p_242741_2_, 0, 0, 32, false);
   }

   private static DimensionSettings func_242743_a(DimensionStructuresSettings p_242743_0_, boolean p_242743_1_, ResourceLocation p_242743_2_) {
      double d0 = 0.9999999814507745D;
      return new DimensionSettings(p_242743_0_, new NoiseSettings(256, new ScalingSettings(0.9999999814507745D, 0.9999999814507745D, 80.0D, 160.0D), new SlideSettings(-10, 3, 0), new SlideSettings(-30, 0, 0), 1, 2, 1.0D, -0.46875D, true, true, false, p_242743_1_), Blocks.STONE.getDefaultState(), Blocks.WATER.getDefaultState(), -10, 0, 63, false);
   }

   static {
      func_242745_a(field_242735_d, func_242743_a(new DimensionStructuresSettings(true), true, field_242735_d.func_240901_a_()));
      func_242745_a(field_242736_e, func_242741_a(new DimensionStructuresSettings(false), Blocks.NETHERRACK.getDefaultState(), Blocks.LAVA.getDefaultState(), field_242736_e.func_240901_a_()));
      func_242745_a(field_242737_f, func_242742_a(new DimensionStructuresSettings(false), Blocks.END_STONE.getDefaultState(), Blocks.AIR.getDefaultState(), field_242737_f.func_240901_a_(), true, true));
      func_242745_a(field_242738_g, func_242741_a(new DimensionStructuresSettings(true), Blocks.STONE.getDefaultState(), Blocks.WATER.getDefaultState(), field_242738_g.func_240901_a_()));
      func_242745_a(field_242739_h, func_242742_a(new DimensionStructuresSettings(true), Blocks.STONE.getDefaultState(), Blocks.WATER.getDefaultState(), field_242739_h.func_240901_a_(), false, false));
   }
}