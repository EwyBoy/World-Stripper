package net.minecraft.world.biome;

import net.minecraft.client.audio.BackgroundMusicTracks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class BiomeMaker {
   private static int func_244206_a(float p_244206_0_) {
      float lvt_1_1_ = p_244206_0_ / 3.0F;
      lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
      return MathHelper.hsvToRGB(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
   }

   public static Biome func_244210_a(float p_244210_0_, float p_244210_1_, float p_244210_2_, boolean p_244210_3_) {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243714_a(mobspawninfo$builder);
      mobspawninfo$builder.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.WOLF, 8, 4, 4));
      mobspawninfo$builder.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 4, 2, 3));
      mobspawninfo$builder.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.FOX, 8, 2, 4));
      if (p_244210_3_) {
         DefaultBiomeFeatures.func_243737_c(mobspawninfo$builder);
      } else {
         DefaultBiomeFeatures.func_243734_b(mobspawninfo$builder);
         DefaultBiomeFeatures.func_243735_b(mobspawninfo$builder, 100, 25, 100);
      }

      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244177_i);
      DefaultBiomeFeatures.func_243733_b(biomegenerationsettings$builder);
      biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244159_y);
      DefaultBiomeFeatures.func_243738_d(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243742_f(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243746_h(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243756_p(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243757_q(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243748_i(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243750_j(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243754_n(biomegenerationsettings$builder);
      biomegenerationsettings$builder.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, p_244210_3_ ? Features.field_243912_cE : Features.field_243913_cF);
      DefaultBiomeFeatures.func_243707_U(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243706_T(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243717_aa(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243727_ak(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243759_s(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).category(Biome.Category.TAIGA).depth(p_244210_0_).scale(p_244210_1_).temperature(p_244210_2_).downfall(0.8F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(12638463).func_242539_d(func_244206_a(p_244210_2_)).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).func_242458_a(mobspawninfo$builder.func_242577_b()).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   public static Biome func_244217_a(float p_244217_0_, float p_244217_1_, boolean p_244217_2_) {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243714_a(mobspawninfo$builder);
      DefaultBiomeFeatures.func_243737_c(mobspawninfo$builder);
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244178_j);
      DefaultBiomeFeatures.func_243733_b(biomegenerationsettings$builder);
      biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244159_y);
      DefaultBiomeFeatures.func_243738_d(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243742_f(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243746_h(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243700_N(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243748_i(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243750_j(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243754_n(biomegenerationsettings$builder);
      if (p_244217_2_) {
         DefaultBiomeFeatures.func_243766_z(biomegenerationsettings$builder);
      } else {
         DefaultBiomeFeatures.func_243764_x(biomegenerationsettings$builder);
      }

      DefaultBiomeFeatures.func_243707_U(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243701_O(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243717_aa(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243727_ak(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).category(Biome.Category.FOREST).depth(p_244217_0_).scale(p_244217_1_).temperature(0.6F).downfall(0.6F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(12638463).func_242539_d(func_244206_a(0.6F)).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).func_242458_a(mobspawninfo$builder.func_242577_b()).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   public static Biome func_244205_a() {
      return func_244215_a(0.1F, 0.2F, 40, 2, 3);
   }

   public static Biome func_244227_b() {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243747_h(mobspawninfo$builder);
      return func_244213_a(0.1F, 0.2F, 0.8F, false, true, false, mobspawninfo$builder);
   }

   public static Biome func_244231_c() {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243747_h(mobspawninfo$builder);
      return func_244213_a(0.2F, 0.4F, 0.8F, false, true, true, mobspawninfo$builder);
   }

   public static Biome func_244235_d() {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243747_h(mobspawninfo$builder);
      mobspawninfo$builder.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PARROT, 10, 1, 1)).func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.OCELOT, 2, 1, 1));
      return func_244213_a(0.2F, 0.4F, 0.9F, false, false, true, mobspawninfo$builder);
   }

   public static Biome func_244238_e() {
      return func_244215_a(0.45F, 0.3F, 10, 1, 1);
   }

   public static Biome func_244240_f() {
      return func_244214_a(0.1F, 0.2F, 40, 2);
   }

   public static Biome func_244241_g() {
      return func_244214_a(0.45F, 0.3F, 10, 1);
   }

   private static Biome func_244215_a(float p_244215_0_, float p_244215_1_, int p_244215_2_, int p_244215_3_, int p_244215_4_) {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243747_h(mobspawninfo$builder);
      mobspawninfo$builder.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PARROT, p_244215_2_, 1, p_244215_3_)).func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.OCELOT, 2, 1, p_244215_4_)).func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PANDA, 1, 1, 2));
      mobspawninfo$builder.func_242571_a();
      return func_244213_a(p_244215_0_, p_244215_1_, 0.9F, false, false, false, mobspawninfo$builder);
   }

   private static Biome func_244214_a(float p_244214_0_, float p_244214_1_, int p_244214_2_, int p_244214_3_) {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243747_h(mobspawninfo$builder);
      mobspawninfo$builder.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PARROT, p_244214_2_, 1, p_244214_3_)).func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PANDA, 80, 1, 2)).func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.OCELOT, 2, 1, 1));
      return func_244213_a(p_244214_0_, p_244214_1_, 0.9F, true, false, false, mobspawninfo$builder);
   }

   private static Biome func_244213_a(float p_244213_0_, float p_244213_1_, float p_244213_2_, boolean p_244213_3_, boolean p_244213_4_, boolean p_244213_5_, MobSpawnInfo.Builder p_244213_6_) {
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244178_j);
      if (!p_244213_4_ && !p_244213_5_) {
         biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244139_e);
      }

      DefaultBiomeFeatures.func_243733_b(biomegenerationsettings$builder);
      biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244130_A);
      DefaultBiomeFeatures.func_243738_d(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243742_f(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243746_h(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243748_i(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243750_j(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243754_n(biomegenerationsettings$builder);
      if (p_244213_3_) {
         DefaultBiomeFeatures.func_243761_u(biomegenerationsettings$builder);
      } else {
         if (!p_244213_4_ && !p_244213_5_) {
            DefaultBiomeFeatures.func_243760_t(biomegenerationsettings$builder);
         }

         if (p_244213_4_) {
            DefaultBiomeFeatures.func_243692_F(biomegenerationsettings$builder);
         } else {
            DefaultBiomeFeatures.func_243691_E(biomegenerationsettings$builder);
         }
      }

      DefaultBiomeFeatures.func_243708_V(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243695_I(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243717_aa(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243727_ak(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243719_ac(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).category(Biome.Category.JUNGLE).depth(p_244213_0_).scale(p_244213_1_).temperature(0.95F).downfall(p_244213_2_).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(12638463).func_242539_d(func_244206_a(0.95F)).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).func_242458_a(p_244213_6_.func_242577_b()).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   public static Biome func_244216_a(float p_244216_0_, float p_244216_1_, ConfiguredSurfaceBuilder<SurfaceBuilderConfig> p_244216_2_, boolean p_244216_3_) {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243714_a(mobspawninfo$builder);
      mobspawninfo$builder.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.LLAMA, 5, 4, 6));
      DefaultBiomeFeatures.func_243737_c(mobspawninfo$builder);
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(p_244216_2_);
      DefaultBiomeFeatures.func_243733_b(biomegenerationsettings$builder);
      biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244132_C);
      DefaultBiomeFeatures.func_243738_d(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243742_f(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243746_h(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243748_i(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243750_j(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243754_n(biomegenerationsettings$builder);
      if (p_244216_3_) {
         DefaultBiomeFeatures.func_243690_D(biomegenerationsettings$builder);
      } else {
         DefaultBiomeFeatures.func_243689_C(biomegenerationsettings$builder);
      }

      DefaultBiomeFeatures.func_243707_U(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243709_W(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243717_aa(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243727_ak(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243752_l(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243753_m(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).category(Biome.Category.EXTREME_HILLS).depth(p_244216_0_).scale(p_244216_1_).temperature(0.2F).downfall(0.3F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(12638463).func_242539_d(func_244206_a(0.2F)).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).func_242458_a(mobspawninfo$builder.func_242577_b()).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   public static Biome func_244220_a(float p_244220_0_, float p_244220_1_, boolean p_244220_2_, boolean p_244220_3_, boolean p_244220_4_) {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243743_f(mobspawninfo$builder);
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244172_d);
      if (p_244220_2_) {
         biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244155_u);
         biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244135_a);
      }

      if (p_244220_3_) {
         biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244140_f);
      }

      if (p_244220_4_) {
         DefaultBiomeFeatures.func_243723_ag(biomegenerationsettings$builder);
      }

      DefaultBiomeFeatures.func_243733_b(biomegenerationsettings$builder);
      biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244160_z);
      DefaultBiomeFeatures.func_243738_d(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243744_g(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243746_h(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243748_i(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243750_j(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243754_n(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243707_U(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243709_W(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243705_S(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243720_ad(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243727_ak(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243722_af(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(Biome.RainType.NONE).category(Biome.Category.DESERT).depth(p_244220_0_).scale(p_244220_1_).temperature(2.0F).downfall(0.0F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(12638463).func_242539_d(func_244206_a(2.0F)).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).func_242458_a(mobspawninfo$builder.func_242577_b()).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   public static Biome func_244226_a(boolean p_244226_0_) {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243739_d(mobspawninfo$builder);
      if (!p_244226_0_) {
         mobspawninfo$builder.func_242571_a();
      }

      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244178_j);
      if (!p_244226_0_) {
         biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244154_t).func_242516_a(StructureFeatures.field_244135_a);
      }

      DefaultBiomeFeatures.func_243733_b(biomegenerationsettings$builder);
      biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244159_y);
      DefaultBiomeFeatures.func_243738_d(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243742_f(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243746_h(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243711_Y(biomegenerationsettings$builder);
      if (p_244226_0_) {
         biomegenerationsettings$builder.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243844_aq);
      }

      DefaultBiomeFeatures.func_243748_i(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243750_j(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243754_n(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243704_R(biomegenerationsettings$builder);
      if (p_244226_0_) {
         biomegenerationsettings$builder.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243820_aS);
      }

      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      if (p_244226_0_) {
         biomegenerationsettings$builder.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243845_ar);
      } else {
         DefaultBiomeFeatures.func_243717_aa(biomegenerationsettings$builder);
      }

      DefaultBiomeFeatures.func_243727_ak(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).category(Biome.Category.PLAINS).depth(0.125F).scale(0.05F).temperature(0.8F).downfall(0.4F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(12638463).func_242539_d(func_244206_a(0.8F)).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).func_242458_a(mobspawninfo$builder.func_242577_b()).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   private static Biome func_244222_a(BiomeGenerationSettings.Builder p_244222_0_) {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243749_i(mobspawninfo$builder);
      return (new Biome.Builder()).precipitation(Biome.RainType.NONE).category(Biome.Category.THEEND).depth(0.1F).scale(0.2F).temperature(0.5F).downfall(0.5F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(10518688).func_242539_d(0).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).func_242458_a(mobspawninfo$builder.func_242577_b()).func_242457_a(p_244222_0_.func_242508_a()).func_242455_a();
   }

   public static Biome func_244242_h() {
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244173_e);
      return func_244222_a(biomegenerationsettings$builder);
   }

   public static Biome func_244243_i() {
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244173_e).func_242513_a(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.field_243801_a);
      return func_244222_a(biomegenerationsettings$builder);
   }

   public static Biome func_244244_j() {
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244173_e).func_242516_a(StructureFeatures.field_244151_q);
      return func_244222_a(biomegenerationsettings$builder);
   }

   public static Biome func_244245_k() {
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244173_e).func_242516_a(StructureFeatures.field_244151_q).func_242513_a(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.field_243854_b).func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243944_d);
      return func_244222_a(biomegenerationsettings$builder);
   }

   public static Biome func_244246_l() {
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244173_e).func_242513_a(GenerationStage.Decoration.RAW_GENERATION, Features.field_243946_f);
      return func_244222_a(biomegenerationsettings$builder);
   }

   public static Biome func_244207_a(float p_244207_0_, float p_244207_1_) {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243745_g(mobspawninfo$builder);
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244182_n);
      DefaultBiomeFeatures.func_243733_b(biomegenerationsettings$builder);
      biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244159_y);
      DefaultBiomeFeatures.func_243738_d(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243742_f(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243746_h(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243748_i(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243750_j(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243754_n(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243703_Q(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243717_aa(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243727_ak(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).category(Biome.Category.MUSHROOM).depth(p_244207_0_).scale(p_244207_1_).temperature(0.9F).downfall(1.0F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(12638463).func_242539_d(func_244206_a(0.9F)).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).func_242458_a(mobspawninfo$builder.func_242577_b()).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   private static Biome func_244212_a(float p_244212_0_, float p_244212_1_, float p_244212_2_, boolean p_244212_3_, boolean p_244212_4_, MobSpawnInfo.Builder p_244212_5_) {
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(p_244212_4_ ? ConfiguredSurfaceBuilders.field_244186_r : ConfiguredSurfaceBuilders.field_244178_j);
      if (!p_244212_3_ && !p_244212_4_) {
         biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244156_v).func_242516_a(StructureFeatures.field_244135_a);
      }

      DefaultBiomeFeatures.func_243733_b(biomegenerationsettings$builder);
      biomegenerationsettings$builder.func_242516_a(p_244212_3_ ? StructureFeatures.field_244132_C : StructureFeatures.field_244159_y);
      DefaultBiomeFeatures.func_243738_d(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243742_f(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243746_h(biomegenerationsettings$builder);
      if (!p_244212_4_) {
         DefaultBiomeFeatures.func_243696_J(biomegenerationsettings$builder);
      }

      DefaultBiomeFeatures.func_243748_i(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243750_j(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243754_n(biomegenerationsettings$builder);
      if (p_244212_4_) {
         DefaultBiomeFeatures.func_243688_B(biomegenerationsettings$builder);
         DefaultBiomeFeatures.func_243707_U(biomegenerationsettings$builder);
         DefaultBiomeFeatures.func_243697_K(biomegenerationsettings$builder);
      } else {
         DefaultBiomeFeatures.func_243687_A(biomegenerationsettings$builder);
         DefaultBiomeFeatures.func_243708_V(biomegenerationsettings$builder);
         DefaultBiomeFeatures.func_243698_L(biomegenerationsettings$builder);
      }

      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243717_aa(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243727_ak(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(Biome.RainType.NONE).category(Biome.Category.SAVANNA).depth(p_244212_0_).scale(p_244212_1_).temperature(p_244212_2_).downfall(0.0F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(12638463).func_242539_d(func_244206_a(p_244212_2_)).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).func_242458_a(p_244212_5_.func_242577_b()).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   public static Biome func_244211_a(float p_244211_0_, float p_244211_1_, float p_244211_2_, boolean p_244211_3_, boolean p_244211_4_) {
      MobSpawnInfo.Builder mobspawninfo$builder = func_244258_x();
      return func_244212_a(p_244211_0_, p_244211_1_, p_244211_2_, p_244211_3_, p_244211_4_, mobspawninfo$builder);
   }

   private static MobSpawnInfo.Builder func_244258_x() {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243714_a(mobspawninfo$builder);
      mobspawninfo$builder.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.HORSE, 1, 2, 6)).func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.DONKEY, 1, 1, 1));
      DefaultBiomeFeatures.func_243737_c(mobspawninfo$builder);
      return mobspawninfo$builder;
   }

   public static Biome func_244247_m() {
      MobSpawnInfo.Builder mobspawninfo$builder = func_244258_x();
      mobspawninfo$builder.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.LLAMA, 8, 4, 4));
      return func_244212_a(1.5F, 0.025F, 1.0F, true, false, mobspawninfo$builder);
   }

   private static Biome func_244224_a(ConfiguredSurfaceBuilder<SurfaceBuilderConfig> p_244224_0_, float p_244224_1_, float p_244224_2_, boolean p_244224_3_, boolean p_244224_4_) {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243737_c(mobspawninfo$builder);
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(p_244224_0_);
      DefaultBiomeFeatures.func_243713_a(biomegenerationsettings$builder);
      biomegenerationsettings$builder.func_242516_a(p_244224_3_ ? StructureFeatures.field_244132_C : StructureFeatures.field_244159_y);
      DefaultBiomeFeatures.func_243738_d(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243742_f(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243746_h(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243748_i(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243750_j(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243751_k(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243754_n(biomegenerationsettings$builder);
      if (p_244224_4_) {
         DefaultBiomeFeatures.func_243693_G(biomegenerationsettings$builder);
      }

      DefaultBiomeFeatures.func_243699_M(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243718_ab(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243727_ak(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(Biome.RainType.NONE).category(Biome.Category.MESA).depth(p_244224_1_).scale(p_244224_2_).temperature(2.0F).downfall(0.0F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(12638463).func_242539_d(func_244206_a(2.0F)).func_242540_e(10387789).func_242541_f(9470285).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).func_242458_a(mobspawninfo$builder.func_242577_b()).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   public static Biome func_244229_b(float p_244229_0_, float p_244229_1_, boolean p_244229_2_) {
      return func_244224_a(ConfiguredSurfaceBuilders.field_244169_a, p_244229_0_, p_244229_1_, p_244229_2_, false);
   }

   public static Biome func_244228_b(float p_244228_0_, float p_244228_1_) {
      return func_244224_a(ConfiguredSurfaceBuilders.field_244191_w, p_244228_0_, p_244228_1_, true, true);
   }

   public static Biome func_244248_n() {
      return func_244224_a(ConfiguredSurfaceBuilders.field_244174_f, 0.1F, 0.2F, true, false);
   }

   private static Biome func_244223_a(MobSpawnInfo.Builder p_244223_0_, int p_244223_1_, int p_244223_2_, boolean p_244223_3_, BiomeGenerationSettings.Builder p_244223_4_) {
      return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).category(Biome.Category.OCEAN).depth(p_244223_3_ ? -1.8F : -1.0F).scale(0.1F).temperature(0.5F).downfall(0.5F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(p_244223_1_).func_235248_c_(p_244223_2_).func_235239_a_(12638463).func_242539_d(func_244206_a(0.5F)).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).func_242458_a(p_244223_0_.func_242577_b()).func_242457_a(p_244223_4_.func_242508_a()).func_242455_a();
   }

   private static BiomeGenerationSettings.Builder func_244225_a(ConfiguredSurfaceBuilder<SurfaceBuilderConfig> p_244225_0_, boolean p_244225_1_, boolean p_244225_2_, boolean p_244225_3_) {
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(p_244225_0_);
      StructureFeature<?, ?> structurefeature = p_244225_2_ ? StructureFeatures.field_244148_n : StructureFeatures.field_244147_m;
      if (p_244225_3_) {
         if (p_244225_1_) {
            biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244146_l);
         }

         DefaultBiomeFeatures.func_243736_c(biomegenerationsettings$builder);
         biomegenerationsettings$builder.func_242516_a(structurefeature);
      } else {
         biomegenerationsettings$builder.func_242516_a(structurefeature);
         if (p_244225_1_) {
            biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244146_l);
         }

         DefaultBiomeFeatures.func_243736_c(biomegenerationsettings$builder);
      }

      biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244133_D);
      DefaultBiomeFeatures.func_243740_e(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243742_f(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243746_h(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243748_i(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243750_j(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243754_n(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243763_w(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243707_U(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243709_W(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243717_aa(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243727_ak(biomegenerationsettings$builder);
      return biomegenerationsettings$builder;
   }

   public static Biome func_244230_b(boolean p_244230_0_) {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243716_a(mobspawninfo$builder, 3, 4, 15);
      mobspawninfo$builder.func_242575_a(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(EntityType.SALMON, 15, 1, 5));
      boolean flag = !p_244230_0_;
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = func_244225_a(ConfiguredSurfaceBuilders.field_244178_j, p_244230_0_, false, flag);
      biomegenerationsettings$builder.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, p_244230_0_ ? Features.field_243961_u : Features.field_243960_t);
      DefaultBiomeFeatures.func_243725_ai(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243724_ah(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return func_244223_a(mobspawninfo$builder, 4020182, 329011, p_244230_0_, biomegenerationsettings$builder);
   }

   public static Biome func_244234_c(boolean p_244234_0_) {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243716_a(mobspawninfo$builder, 1, 4, 10);
      mobspawninfo$builder.func_242575_a(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(EntityType.DOLPHIN, 1, 1, 2));
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = func_244225_a(ConfiguredSurfaceBuilders.field_244178_j, p_244234_0_, false, true);
      biomegenerationsettings$builder.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, p_244234_0_ ? Features.field_243964_x : Features.field_243962_v);
      DefaultBiomeFeatures.func_243725_ai(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243724_ah(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return func_244223_a(mobspawninfo$builder, 4159204, 329011, p_244234_0_, biomegenerationsettings$builder);
   }

   public static Biome func_244237_d(boolean p_244237_0_) {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      if (p_244237_0_) {
         DefaultBiomeFeatures.func_243716_a(mobspawninfo$builder, 8, 4, 8);
      } else {
         DefaultBiomeFeatures.func_243716_a(mobspawninfo$builder, 10, 2, 15);
      }

      mobspawninfo$builder.func_242575_a(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(EntityType.PUFFERFISH, 5, 1, 3)).func_242575_a(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(EntityType.TROPICAL_FISH, 25, 8, 8)).func_242575_a(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(EntityType.DOLPHIN, 2, 1, 2));
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = func_244225_a(ConfiguredSurfaceBuilders.field_244185_q, p_244237_0_, true, false);
      biomegenerationsettings$builder.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, p_244237_0_ ? Features.field_243775_A : Features.field_243966_z);
      if (p_244237_0_) {
         DefaultBiomeFeatures.func_243725_ai(biomegenerationsettings$builder);
      }

      DefaultBiomeFeatures.func_243726_aj(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return func_244223_a(mobspawninfo$builder, 4566514, 267827, p_244237_0_, biomegenerationsettings$builder);
   }

   public static Biome func_244249_o() {
      MobSpawnInfo.Builder mobspawninfo$builder = (new MobSpawnInfo.Builder()).func_242575_a(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(EntityType.PUFFERFISH, 15, 1, 3));
      DefaultBiomeFeatures.func_243715_a(mobspawninfo$builder, 10, 4);
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = func_244225_a(ConfiguredSurfaceBuilders.field_244176_h, false, true, false).func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243935_cr).func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243966_z).func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243776_B);
      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return func_244223_a(mobspawninfo$builder, 4445678, 270131, false, biomegenerationsettings$builder);
   }

   public static Biome func_244250_p() {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243715_a(mobspawninfo$builder, 5, 1);
      mobspawninfo$builder.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.DROWNED, 5, 1, 1));
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = func_244225_a(ConfiguredSurfaceBuilders.field_244176_h, true, true, false).func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243775_A);
      DefaultBiomeFeatures.func_243725_ai(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return func_244223_a(mobspawninfo$builder, 4445678, 270131, true, biomegenerationsettings$builder);
   }

   public static Biome func_244239_e(boolean p_244239_0_) {
      MobSpawnInfo.Builder mobspawninfo$builder = (new MobSpawnInfo.Builder()).func_242575_a(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(EntityType.SQUID, 1, 1, 4)).func_242575_a(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(EntityType.SALMON, 15, 1, 5)).func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.POLAR_BEAR, 1, 1, 2));
      DefaultBiomeFeatures.func_243737_c(mobspawninfo$builder);
      mobspawninfo$builder.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.DROWNED, 5, 1, 1));
      float f = p_244239_0_ ? 0.5F : 0.0F;
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244175_g);
      biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244147_m);
      if (p_244239_0_) {
         biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244146_l);
      }

      DefaultBiomeFeatures.func_243736_c(biomegenerationsettings$builder);
      biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244133_D);
      DefaultBiomeFeatures.func_243740_e(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243742_f(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243728_al(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243746_h(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243729_am(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243748_i(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243750_j(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243754_n(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243763_w(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243707_U(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243709_W(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243717_aa(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243727_ak(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(p_244239_0_ ? Biome.RainType.RAIN : Biome.RainType.SNOW).category(Biome.Category.OCEAN).depth(p_244239_0_ ? -1.8F : -1.0F).scale(0.1F).temperature(f).func_242456_a(Biome.TemperatureModifier.FROZEN).downfall(0.5F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(3750089).func_235248_c_(329011).func_235239_a_(12638463).func_242539_d(func_244206_a(f)).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).func_242458_a(mobspawninfo$builder.func_242577_b()).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   private static Biome func_244218_a(float p_244218_0_, float p_244218_1_, boolean p_244218_2_, MobSpawnInfo.Builder p_244218_3_) {
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244178_j);
      DefaultBiomeFeatures.func_243733_b(biomegenerationsettings$builder);
      biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244159_y);
      DefaultBiomeFeatures.func_243738_d(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243742_f(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243746_h(biomegenerationsettings$builder);
      if (p_244218_2_) {
         biomegenerationsettings$builder.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243931_cn);
      } else {
         DefaultBiomeFeatures.func_243700_N(biomegenerationsettings$builder);
      }

      DefaultBiomeFeatures.func_243748_i(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243750_j(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243754_n(biomegenerationsettings$builder);
      if (p_244218_2_) {
         biomegenerationsettings$builder.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243936_cs);
         biomegenerationsettings$builder.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243927_cj);
         DefaultBiomeFeatures.func_243709_W(biomegenerationsettings$builder);
      } else {
         DefaultBiomeFeatures.func_243765_y(biomegenerationsettings$builder);
         DefaultBiomeFeatures.func_243707_U(biomegenerationsettings$builder);
         DefaultBiomeFeatures.func_243701_O(biomegenerationsettings$builder);
      }

      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243717_aa(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243727_ak(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).category(Biome.Category.FOREST).depth(p_244218_0_).scale(p_244218_1_).temperature(0.7F).downfall(0.8F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(12638463).func_242539_d(func_244206_a(0.7F)).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).func_242458_a(p_244218_3_.func_242577_b()).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   private static MobSpawnInfo.Builder func_244259_y() {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243714_a(mobspawninfo$builder);
      DefaultBiomeFeatures.func_243737_c(mobspawninfo$builder);
      return mobspawninfo$builder;
   }

   public static Biome func_244232_c(float p_244232_0_, float p_244232_1_) {
      MobSpawnInfo.Builder mobspawninfo$builder = func_244259_y().func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.WOLF, 5, 4, 4)).func_242571_a();
      return func_244218_a(p_244232_0_, p_244232_1_, false, mobspawninfo$builder);
   }

   public static Biome func_244251_q() {
      MobSpawnInfo.Builder mobspawninfo$builder = func_244259_y().func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 4, 2, 3));
      return func_244218_a(0.1F, 0.4F, true, mobspawninfo$builder);
   }

   public static Biome func_244221_a(float p_244221_0_, float p_244221_1_, boolean p_244221_2_, boolean p_244221_3_, boolean p_244221_4_, boolean p_244221_5_) {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243714_a(mobspawninfo$builder);
      mobspawninfo$builder.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.WOLF, 8, 4, 4)).func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 4, 2, 3)).func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.FOX, 8, 2, 4));
      if (!p_244221_2_ && !p_244221_3_) {
         mobspawninfo$builder.func_242571_a();
      }

      DefaultBiomeFeatures.func_243737_c(mobspawninfo$builder);
      float f = p_244221_2_ ? -0.5F : 0.25F;
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244178_j);
      if (p_244221_4_) {
         biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244158_x);
         biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244135_a);
      }

      if (p_244221_5_) {
         biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244141_g);
      }

      DefaultBiomeFeatures.func_243733_b(biomegenerationsettings$builder);
      biomegenerationsettings$builder.func_242516_a(p_244221_3_ ? StructureFeatures.field_244132_C : StructureFeatures.field_244159_y);
      DefaultBiomeFeatures.func_243738_d(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243742_f(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243746_h(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243757_q(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243748_i(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243750_j(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243754_n(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243762_v(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243707_U(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243710_X(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243717_aa(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243727_ak(biomegenerationsettings$builder);
      if (p_244221_2_) {
         DefaultBiomeFeatures.func_243758_r(biomegenerationsettings$builder);
      } else {
         DefaultBiomeFeatures.func_243759_s(biomegenerationsettings$builder);
      }

      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(p_244221_2_ ? Biome.RainType.SNOW : Biome.RainType.RAIN).category(Biome.Category.TAIGA).depth(p_244221_0_).scale(p_244221_1_).temperature(f).downfall(p_244221_2_ ? 0.4F : 0.8F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(p_244221_2_ ? 4020182 : 4159204).func_235248_c_(329011).func_235239_a_(12638463).func_242539_d(func_244206_a(f)).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).func_242458_a(mobspawninfo$builder.func_242577_b()).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   public static Biome func_244233_c(float p_244233_0_, float p_244233_1_, boolean p_244233_2_) {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243714_a(mobspawninfo$builder);
      DefaultBiomeFeatures.func_243737_c(mobspawninfo$builder);
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244178_j);
      biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244138_d);
      DefaultBiomeFeatures.func_243733_b(biomegenerationsettings$builder);
      biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244159_y);
      DefaultBiomeFeatures.func_243738_d(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243742_f(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243746_h(biomegenerationsettings$builder);
      biomegenerationsettings$builder.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, p_244233_2_ ? Features.field_243934_cq : Features.field_243933_cp);
      DefaultBiomeFeatures.func_243700_N(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243748_i(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243750_j(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243754_n(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243707_U(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243701_O(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243717_aa(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243727_ak(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).category(Biome.Category.FOREST).depth(p_244233_0_).scale(p_244233_1_).temperature(0.7F).downfall(0.8F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(12638463).func_242539_d(func_244206_a(0.7F)).func_242537_a(BiomeAmbience.GrassColorModifier.DARK_FOREST).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).func_242458_a(mobspawninfo$builder.func_242577_b()).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   public static Biome func_244236_d(float p_244236_0_, float p_244236_1_, boolean p_244236_2_) {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      DefaultBiomeFeatures.func_243714_a(mobspawninfo$builder);
      DefaultBiomeFeatures.func_243737_c(mobspawninfo$builder);
      mobspawninfo$builder.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SLIME, 1, 1, 1));
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244189_u);
      if (!p_244236_2_) {
         biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244144_j);
      }

      biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244136_b);
      biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244131_B);
      DefaultBiomeFeatures.func_243738_d(biomegenerationsettings$builder);
      if (!p_244236_2_) {
         DefaultBiomeFeatures.func_243723_ag(biomegenerationsettings$builder);
      }

      DefaultBiomeFeatures.func_243742_f(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243746_h(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243748_i(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243750_j(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243755_o(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243702_P(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243721_ae(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243727_ak(biomegenerationsettings$builder);
      if (p_244236_2_) {
         DefaultBiomeFeatures.func_243723_ag(biomegenerationsettings$builder);
      } else {
         biomegenerationsettings$builder.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243965_y);
      }

      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).category(Biome.Category.SWAMP).depth(p_244236_0_).scale(p_244236_1_).temperature(0.8F).downfall(0.9F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(6388580).func_235248_c_(2302743).func_235239_a_(12638463).func_242539_d(func_244206_a(0.8F)).func_242540_e(6975545).func_242537_a(BiomeAmbience.GrassColorModifier.SWAMP).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).func_242458_a(mobspawninfo$builder.func_242577_b()).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   public static Biome func_244219_a(float p_244219_0_, float p_244219_1_, boolean p_244219_2_, boolean p_244219_3_) {
      MobSpawnInfo.Builder mobspawninfo$builder = (new MobSpawnInfo.Builder()).func_242572_a(0.07F);
      DefaultBiomeFeatures.func_243741_e(mobspawninfo$builder);
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(p_244219_2_ ? ConfiguredSurfaceBuilders.field_244180_l : ConfiguredSurfaceBuilders.field_244178_j);
      if (!p_244219_2_ && !p_244219_3_) {
         biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244157_w).func_242516_a(StructureFeatures.field_244141_g);
      }

      DefaultBiomeFeatures.func_243733_b(biomegenerationsettings$builder);
      if (!p_244219_2_ && !p_244219_3_) {
         biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244135_a);
      }

      biomegenerationsettings$builder.func_242516_a(p_244219_3_ ? StructureFeatures.field_244132_C : StructureFeatures.field_244159_y);
      DefaultBiomeFeatures.func_243738_d(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243742_f(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243746_h(biomegenerationsettings$builder);
      if (p_244219_2_) {
         biomegenerationsettings$builder.func_242513_a(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.field_243777_C);
         biomegenerationsettings$builder.func_242513_a(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.field_243778_D);
      }

      DefaultBiomeFeatures.func_243748_i(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243750_j(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243754_n(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243694_H(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243707_U(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243709_W(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243717_aa(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243727_ak(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).category(Biome.Category.ICY).depth(p_244219_0_).scale(p_244219_1_).temperature(0.0F).downfall(0.5F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(12638463).func_242539_d(func_244206_a(0.0F)).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).func_242458_a(mobspawninfo$builder.func_242577_b()).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   public static Biome func_244209_a(float p_244209_0_, float p_244209_1_, float p_244209_2_, int p_244209_3_, boolean p_244209_4_) {
      MobSpawnInfo.Builder mobspawninfo$builder = (new MobSpawnInfo.Builder()).func_242575_a(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(EntityType.SQUID, 2, 1, 4)).func_242575_a(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(EntityType.SALMON, 5, 1, 5));
      DefaultBiomeFeatures.func_243737_c(mobspawninfo$builder);
      mobspawninfo$builder.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.DROWNED, p_244209_4_ ? 1 : 100, 1, 1));
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244178_j);
      biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244136_b);
      biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244159_y);
      DefaultBiomeFeatures.func_243738_d(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243742_f(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243746_h(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243748_i(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243750_j(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243754_n(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243763_w(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243707_U(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243709_W(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243717_aa(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243727_ak(biomegenerationsettings$builder);
      if (!p_244209_4_) {
         biomegenerationsettings$builder.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243963_w);
      }

      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(p_244209_4_ ? Biome.RainType.SNOW : Biome.RainType.RAIN).category(Biome.Category.RIVER).depth(p_244209_0_).scale(p_244209_1_).temperature(p_244209_2_).downfall(0.5F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(p_244209_3_).func_235248_c_(329011).func_235239_a_(12638463).func_242539_d(func_244206_a(p_244209_2_)).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).func_242458_a(mobspawninfo$builder.func_242577_b()).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   public static Biome func_244208_a(float p_244208_0_, float p_244208_1_, float p_244208_2_, float p_244208_3_, int p_244208_4_, boolean p_244208_5_, boolean p_244208_6_) {
      MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
      if (!p_244208_6_ && !p_244208_5_) {
         mobspawninfo$builder.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.TURTLE, 5, 2, 5));
      }

      DefaultBiomeFeatures.func_243737_c(mobspawninfo$builder);
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(p_244208_6_ ? ConfiguredSurfaceBuilders.field_244188_t : ConfiguredSurfaceBuilders.field_244172_d);
      if (p_244208_6_) {
         DefaultBiomeFeatures.func_243733_b(biomegenerationsettings$builder);
      } else {
         biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244136_b);
         biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244152_r);
         biomegenerationsettings$builder.func_242516_a(StructureFeatures.field_244143_i);
      }

      biomegenerationsettings$builder.func_242516_a(p_244208_6_ ? StructureFeatures.field_244132_C : StructureFeatures.field_244159_y);
      DefaultBiomeFeatures.func_243738_d(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243742_f(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243746_h(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243748_i(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243750_j(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243754_n(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243707_U(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243709_W(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243717_aa(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243727_ak(biomegenerationsettings$builder);
      DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(p_244208_5_ ? Biome.RainType.SNOW : Biome.RainType.RAIN).category(p_244208_6_ ? Biome.Category.NONE : Biome.Category.BEACH).depth(p_244208_0_).scale(p_244208_1_).temperature(p_244208_2_).downfall(p_244208_3_).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(p_244208_4_).func_235248_c_(329011).func_235239_a_(12638463).func_242539_d(func_244206_a(p_244208_2_)).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).func_242458_a(mobspawninfo$builder.func_242577_b()).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   public static Biome func_244252_r() {
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244184_p);
      biomegenerationsettings$builder.func_242513_a(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Features.field_243796_V);
      return (new Biome.Builder()).precipitation(Biome.RainType.NONE).category(Biome.Category.NONE).depth(0.1F).scale(0.2F).temperature(0.5F).downfall(0.5F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(12638463).func_242539_d(func_244206_a(0.5F)).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).func_242458_a(MobSpawnInfo.field_242551_b).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   public static Biome func_244253_s() {
      MobSpawnInfo mobspawninfo = (new MobSpawnInfo.Builder()).func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.GHAST, 50, 4, 4)).func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.field_233592_ba_, 100, 4, 4)).func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.MAGMA_CUBE, 2, 4, 4)).func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 1, 4, 4)).func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.field_233591_ai_, 15, 4, 4)).func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.field_233589_aE_, 60, 1, 2)).func_242577_b();
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244183_o).func_242516_a(StructureFeatures.field_244134_E).func_242516_a(StructureFeatures.field_244149_o).func_242516_a(StructureFeatures.field_244153_s).func_242512_a(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243772_f).func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243828_aa);
      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      biomegenerationsettings$builder.func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243832_ae).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243839_al).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243840_am).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243952_l).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243953_m).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243821_aT).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243822_aU).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243884_bd).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243830_ac);
      DefaultBiomeFeatures.func_243731_ao(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(Biome.RainType.NONE).category(Biome.Category.NETHER).depth(0.1F).scale(0.2F).temperature(2.0F).downfall(0.0F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(3344392).func_242539_d(func_244206_a(2.0F)).func_235241_a_(SoundEvents.field_232732_i_).func_235243_a_(new MoodSoundAmbience(SoundEvents.field_232769_j_, 6000, 8, 2.0D)).func_235242_a_(new SoundAdditionsAmbience(SoundEvents.field_232723_h_, 0.0111D)).func_235240_a_(BackgroundMusicTracks.func_232677_a_(SoundEvents.field_232760_ip_)).func_235238_a_()).func_242458_a(mobspawninfo).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   public static Biome func_244254_t() {
      double d0 = 0.7D;
      double d1 = 0.15D;
      MobSpawnInfo mobspawninfo = (new MobSpawnInfo.Builder()).func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SKELETON, 20, 5, 5)).func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.GHAST, 50, 4, 4)).func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 1, 4, 4)).func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.field_233589_aE_, 60, 1, 2)).func_242573_a(EntityType.SKELETON, 0.7D, 0.15D).func_242573_a(EntityType.GHAST, 0.7D, 0.15D).func_242573_a(EntityType.ENDERMAN, 0.7D, 0.15D).func_242573_a(EntityType.field_233589_aE_, 0.7D, 0.15D).func_242577_b();
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244187_s).func_242516_a(StructureFeatures.field_244149_o).func_242516_a(StructureFeatures.field_244150_p).func_242516_a(StructureFeatures.field_244134_E).func_242516_a(StructureFeatures.field_244153_s).func_242512_a(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243772_f).func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243828_aa).func_242513_a(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Features.field_243959_s).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243832_ae).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243952_l).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243953_m).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243843_ap).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243839_al).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243840_am).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243884_bd).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243830_ac).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243885_be);
      DefaultBiomeFeatures.func_243731_ao(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(Biome.RainType.NONE).category(Biome.Category.NETHER).depth(0.1F).scale(0.2F).temperature(2.0F).downfall(0.0F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(1787717).func_242539_d(func_244206_a(2.0F)).func_235244_a_(new ParticleEffectAmbience(ParticleTypes.field_239813_am_, 0.00625F)).func_235241_a_(SoundEvents.field_232798_l_).func_235243_a_(new MoodSoundAmbience(SoundEvents.field_232800_m_, 6000, 8, 2.0D)).func_235242_a_(new SoundAdditionsAmbience(SoundEvents.field_232785_k_, 0.0111D)).func_235240_a_(BackgroundMusicTracks.func_232677_a_(SoundEvents.field_232761_iq_)).func_235238_a_()).func_242458_a(mobspawninfo).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   public static Biome func_244255_u() {
      MobSpawnInfo mobspawninfo = (new MobSpawnInfo.Builder()).func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.GHAST, 40, 1, 1)).func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.MAGMA_CUBE, 100, 2, 5)).func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.field_233589_aE_, 60, 1, 2)).func_242577_b();
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244170_b).func_242516_a(StructureFeatures.field_244134_E).func_242512_a(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243772_f).func_242516_a(StructureFeatures.field_244149_o).func_242513_a(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.field_243947_g).func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243800_Z).func_242513_a(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.field_243948_h).func_242513_a(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.field_243949_i).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243950_j).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243951_k).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243829_ab).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243839_al).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243840_am).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243952_l).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243953_m).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243821_aT).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243822_aU).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243884_bd).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243831_ad).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243886_bf).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243887_bg);
      DefaultBiomeFeatures.func_243732_ap(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(Biome.RainType.NONE).category(Biome.Category.NETHER).depth(0.1F).scale(0.2F).temperature(2.0F).downfall(0.0F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(4341314).func_235239_a_(6840176).func_242539_d(func_244206_a(2.0F)).func_235244_a_(new ParticleEffectAmbience(ParticleTypes.field_239820_at_, 0.118093334F)).func_235241_a_(SoundEvents.field_232698_c_).func_235243_a_(new MoodSoundAmbience(SoundEvents.field_232700_d_, 6000, 8, 2.0D)).func_235242_a_(new SoundAdditionsAmbience(SoundEvents.field_232692_b_, 0.0111D)).func_235240_a_(BackgroundMusicTracks.func_232677_a_(SoundEvents.field_232759_io_)).func_235238_a_()).func_242458_a(mobspawninfo).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   public static Biome func_244256_v() {
      MobSpawnInfo mobspawninfo = (new MobSpawnInfo.Builder()).func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.field_233592_ba_, 1, 2, 4)).func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.field_233588_G_, 9, 3, 4)).func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.field_233591_ai_, 5, 3, 4)).func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.field_233589_aE_, 60, 1, 2)).func_242577_b();
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244171_c).func_242516_a(StructureFeatures.field_244134_E).func_242512_a(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243772_f).func_242516_a(StructureFeatures.field_244149_o).func_242516_a(StructureFeatures.field_244153_s).func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243828_aa);
      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      biomegenerationsettings$builder.func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243832_ae).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243839_al).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243952_l).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243953_m).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243884_bd).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243830_ac).func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243958_r).func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243856_bB).func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243954_n);
      DefaultBiomeFeatures.func_243731_ao(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(Biome.RainType.NONE).category(Biome.Category.NETHER).depth(0.1F).scale(0.2F).temperature(2.0F).downfall(0.0F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(3343107).func_242539_d(func_244206_a(2.0F)).func_235244_a_(new ParticleEffectAmbience(ParticleTypes.field_239814_an_, 0.025F)).func_235241_a_(SoundEvents.field_232711_f_).func_235243_a_(new MoodSoundAmbience(SoundEvents.field_232722_g_, 6000, 8, 2.0D)).func_235242_a_(new SoundAdditionsAmbience(SoundEvents.field_232701_e_, 0.0111D)).func_235240_a_(BackgroundMusicTracks.func_232677_a_(SoundEvents.field_232762_ir_)).func_235238_a_()).func_242458_a(mobspawninfo).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }

   public static Biome func_244257_w() {
      MobSpawnInfo mobspawninfo = (new MobSpawnInfo.Builder()).func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 1, 4, 4)).func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.field_233589_aE_, 60, 1, 2)).func_242573_a(EntityType.ENDERMAN, 1.0D, 0.12D).func_242577_b();
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244190_v).func_242516_a(StructureFeatures.field_244149_o).func_242516_a(StructureFeatures.field_244153_s).func_242516_a(StructureFeatures.field_244134_E).func_242512_a(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243772_f).func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243828_aa);
      DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
      biomegenerationsettings$builder.func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243832_ae).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243839_al).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243840_am).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243952_l).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243953_m).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243884_bd).func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243830_ac).func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243858_bD).func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243955_o).func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243956_p).func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243957_q);
      DefaultBiomeFeatures.func_243731_ao(biomegenerationsettings$builder);
      return (new Biome.Builder()).precipitation(Biome.RainType.NONE).category(Biome.Category.NETHER).depth(0.1F).scale(0.2F).temperature(2.0F).downfall(0.0F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(1705242).func_242539_d(func_244206_a(2.0F)).func_235244_a_(new ParticleEffectAmbience(ParticleTypes.field_239815_ao_, 0.01428F)).func_235241_a_(SoundEvents.field_232839_o_).func_235243_a_(new MoodSoundAmbience(SoundEvents.field_232844_p_, 6000, 8, 2.0D)).func_235242_a_(new SoundAdditionsAmbience(SoundEvents.field_232820_n_, 0.0111D)).func_235240_a_(BackgroundMusicTracks.func_232677_a_(SoundEvents.field_232763_is_)).func_235238_a_()).func_242458_a(mobspawninfo).func_242457_a(biomegenerationsettings$builder.func_242508_a()).func_242455_a();
   }
}