package net.minecraft.world.biome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;

public class DefaultBiomeFeatures {
   public static void func_243713_a(BiomeGenerationSettings.Builder p_243713_0_) {
      p_243713_0_.func_242516_a(StructureFeatures.field_244137_c);
      p_243713_0_.func_242516_a(StructureFeatures.field_244145_k);
   }

   public static void func_243733_b(BiomeGenerationSettings.Builder p_243733_0_) {
      p_243733_0_.func_242516_a(StructureFeatures.field_244136_b);
      p_243733_0_.func_242516_a(StructureFeatures.field_244145_k);
   }

   public static void func_243736_c(BiomeGenerationSettings.Builder p_243736_0_) {
      p_243736_0_.func_242516_a(StructureFeatures.field_244136_b);
      p_243736_0_.func_242516_a(StructureFeatures.field_244142_h);
   }

   public static void func_243738_d(BiomeGenerationSettings.Builder p_243738_0_) {
      p_243738_0_.func_242512_a(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243767_a);
      p_243738_0_.func_242512_a(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243768_b);
   }

   public static void func_243740_e(BiomeGenerationSettings.Builder p_243740_0_) {
      p_243740_0_.func_242512_a(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243769_c);
      p_243740_0_.func_242512_a(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243768_b);
      p_243740_0_.func_242512_a(GenerationStage.Carving.LIQUID, ConfiguredCarvers.field_243770_d);
      p_243740_0_.func_242512_a(GenerationStage.Carving.LIQUID, ConfiguredCarvers.field_243771_e);
   }

   public static void func_243742_f(BiomeGenerationSettings.Builder p_243742_0_) {
      p_243742_0_.func_242513_a(GenerationStage.Decoration.LAKES, Features.field_243789_O);
      p_243742_0_.func_242513_a(GenerationStage.Decoration.LAKES, Features.field_243790_P);
   }

   public static void func_243744_g(BiomeGenerationSettings.Builder p_243744_0_) {
      p_243744_0_.func_242513_a(GenerationStage.Decoration.LAKES, Features.field_243790_P);
   }

   public static void func_243746_h(BiomeGenerationSettings.Builder p_243746_0_) {
      p_243746_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Features.field_243797_W);
   }

   public static void func_243748_i(BiomeGenerationSettings.Builder p_243748_0_) {
      p_243748_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_ORES, Features.field_243892_bl);
      p_243748_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_ORES, Features.field_243893_bm);
      p_243748_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_ORES, Features.field_243894_bn);
      p_243748_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_ORES, Features.field_243895_bo);
      p_243748_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_ORES, Features.field_243896_bp);
   }

   public static void func_243750_j(BiomeGenerationSettings.Builder p_243750_0_) {
      p_243750_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_ORES, Features.field_243897_bq);
      p_243750_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_ORES, Features.field_243898_br);
      p_243750_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_ORES, Features.field_243900_bt);
      p_243750_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_ORES, Features.field_243901_bu);
      p_243750_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_ORES, Features.field_243902_bv);
      p_243750_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_ORES, Features.field_243903_bw);
   }

   public static void func_243751_k(BiomeGenerationSettings.Builder p_243751_0_) {
      p_243751_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_ORES, Features.field_243899_bs);
   }

   public static void func_243752_l(BiomeGenerationSettings.Builder p_243752_0_) {
      p_243752_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_ORES, Features.field_243905_by);
   }

   public static void func_243753_m(BiomeGenerationSettings.Builder p_243753_0_) {
      p_243753_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243904_bx);
   }

   public static void func_243754_n(BiomeGenerationSettings.Builder p_243754_0_) {
      p_243754_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_ORES, Features.field_243793_S);
      p_243754_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_ORES, Features.field_243791_Q);
      p_243754_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_ORES, Features.field_243792_R);
   }

   public static void func_243755_o(BiomeGenerationSettings.Builder p_243755_0_) {
      p_243755_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_ORES, Features.field_243791_Q);
   }

   public static void func_243756_p(BiomeGenerationSettings.Builder p_243756_0_) {
      p_243756_0_.func_242513_a(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Features.field_243779_E);
   }

   public static void func_243757_q(BiomeGenerationSettings.Builder p_243757_0_) {
      p_243757_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243813_aL);
   }

   public static void func_243758_r(BiomeGenerationSettings.Builder p_243758_0_) {
      p_243758_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243809_aH);
   }

   public static void func_243759_s(BiomeGenerationSettings.Builder p_243759_0_) {
      p_243759_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243808_aG);
   }

   public static void func_243760_t(BiomeGenerationSettings.Builder p_243760_0_) {
      p_243760_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243786_L);
   }

   public static void func_243761_u(BiomeGenerationSettings.Builder p_243761_0_) {
      p_243761_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243787_M);
      p_243761_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243915_cH);
   }

   public static void func_243762_v(BiomeGenerationSettings.Builder p_243762_0_) {
      p_243762_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243937_ct);
   }

   public static void func_243763_w(BiomeGenerationSettings.Builder p_243763_0_) {
      p_243763_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243908_cA);
   }

   public static void func_243764_x(BiomeGenerationSettings.Builder p_243764_0_) {
      p_243764_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243941_cx);
   }

   public static void func_243765_y(BiomeGenerationSettings.Builder p_243765_0_) {
      p_243765_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243909_cB);
   }

   public static void func_243766_z(BiomeGenerationSettings.Builder p_243766_0_) {
      p_243766_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243940_cw);
   }

   public static void func_243687_A(BiomeGenerationSettings.Builder p_243687_0_) {
      p_243687_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243939_cv);
   }

   public static void func_243688_B(BiomeGenerationSettings.Builder p_243688_0_) {
      p_243688_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243938_cu);
   }

   public static void func_243689_C(BiomeGenerationSettings.Builder p_243689_0_) {
      p_243689_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243943_cz);
   }

   public static void func_243690_D(BiomeGenerationSettings.Builder p_243690_0_) {
      p_243690_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243942_cy);
   }

   public static void func_243691_E(BiomeGenerationSettings.Builder p_243691_0_) {
      p_243691_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243914_cG);
   }

   public static void func_243692_F(BiomeGenerationSettings.Builder p_243692_0_) {
      p_243692_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243911_cD);
   }

   public static void func_243693_G(BiomeGenerationSettings.Builder p_243693_0_) {
      p_243693_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243923_cf);
   }

   public static void func_243694_H(BiomeGenerationSettings.Builder p_243694_0_) {
      p_243694_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243924_cg);
   }

   public static void func_243695_I(BiomeGenerationSettings.Builder p_243695_0_) {
      p_243695_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243803_aB);
   }

   public static void func_243696_J(BiomeGenerationSettings.Builder p_243696_0_) {
      p_243696_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243812_aK);
   }

   public static void func_243697_K(BiomeGenerationSettings.Builder p_243697_0_) {
      p_243697_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243852_ay);
   }

   public static void func_243698_L(BiomeGenerationSettings.Builder p_243698_0_) {
      p_243698_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243851_ax);
   }

   public static void func_243699_M(BiomeGenerationSettings.Builder p_243699_0_) {
      p_243699_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243850_aw);
      p_243699_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243806_aE);
   }

   public static void func_243700_N(BiomeGenerationSettings.Builder p_243700_0_) {
      p_243700_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243932_co);
   }

   public static void func_243701_O(BiomeGenerationSettings.Builder p_243701_0_) {
      p_243701_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243849_av);
   }

   public static void func_243702_P(BiomeGenerationSettings.Builder p_243702_0_) {
      p_243702_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243875_bU);
      p_243702_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243928_ck);
      p_243702_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243852_ay);
      p_243702_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243805_aD);
      p_243702_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243810_aI);
      p_243702_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243882_bb);
      p_243702_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243883_bc);
   }

   public static void func_243703_Q(BiomeGenerationSettings.Builder p_243703_0_) {
      p_243703_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243916_cI);
      p_243703_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243825_aX);
      p_243703_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243826_aY);
   }

   public static void func_243704_R(BiomeGenerationSettings.Builder p_243704_0_) {
      p_243704_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243910_cC);
      p_243704_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243930_cm);
      p_243704_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243848_au);
   }

   public static void func_243705_S(BiomeGenerationSettings.Builder p_243705_0_) {
      p_243705_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243804_aC);
   }

   public static void func_243706_T(BiomeGenerationSettings.Builder p_243706_0_) {
      p_243706_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243802_aA);
      p_243706_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243805_aD);
      p_243706_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243827_aZ);
      p_243706_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243881_ba);
   }

   public static void func_243707_U(BiomeGenerationSettings.Builder p_243707_0_) {
      p_243707_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243926_ci);
   }

   public static void func_243708_V(BiomeGenerationSettings.Builder p_243708_0_) {
      p_243708_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243925_ch);
   }

   public static void func_243709_W(BiomeGenerationSettings.Builder p_243709_0_) {
      p_243709_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243850_aw);
   }

   public static void func_243710_X(BiomeGenerationSettings.Builder p_243710_0_) {
      p_243710_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243853_az);
      p_243710_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243825_aX);
      p_243710_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243826_aY);
   }

   public static void func_243711_Y(BiomeGenerationSettings.Builder p_243711_0_) {
      p_243711_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243811_aJ);
   }

   public static void func_243712_Z(BiomeGenerationSettings.Builder p_243712_0_) {
      p_243712_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243823_aV);
      p_243712_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243824_aW);
   }

   public static void func_243717_aa(BiomeGenerationSettings.Builder p_243717_0_) {
      p_243717_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243820_aS);
      p_243717_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243845_ar);
   }

   public static void func_243718_ab(BiomeGenerationSettings.Builder p_243718_0_) {
      p_243718_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243819_aR);
      p_243718_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243845_ar);
      p_243718_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243816_aO);
   }

   public static void func_243719_ac(BiomeGenerationSettings.Builder p_243719_0_) {
      p_243719_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243807_aF);
      p_243719_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243788_N);
   }

   public static void func_243720_ad(BiomeGenerationSettings.Builder p_243720_0_) {
      p_243720_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243818_aQ);
      p_243720_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243845_ar);
      p_243720_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243815_aN);
   }

   public static void func_243721_ae(BiomeGenerationSettings.Builder p_243721_0_) {
      p_243721_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243817_aP);
      p_243721_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243845_ar);
   }

   public static void func_243722_af(BiomeGenerationSettings.Builder p_243722_0_) {
      p_243722_0_.func_242513_a(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.field_243798_X);
   }

   public static void func_243723_ag(BiomeGenerationSettings.Builder p_243723_0_) {
      p_243723_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Features.field_243799_Y);
   }

   public static void func_243724_ah(BiomeGenerationSettings.Builder p_243724_0_) {
      p_243724_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243783_I);
   }

   public static void func_243725_ai(BiomeGenerationSettings.Builder p_243725_0_) {
      p_243725_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243780_F);
   }

   public static void func_243726_aj(BiomeGenerationSettings.Builder p_243726_0_) {
      p_243726_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243784_J);
   }

   public static void func_243727_ak(BiomeGenerationSettings.Builder p_243727_0_) {
      p_243727_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243833_af);
      p_243727_0_.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Features.field_243828_aa);
   }

   public static void func_243728_al(BiomeGenerationSettings.Builder p_243728_0_) {
      p_243728_0_.func_242513_a(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Features.field_243781_G);
      p_243728_0_.func_242513_a(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Features.field_243782_H);
   }

   public static void func_243729_am(BiomeGenerationSettings.Builder p_243729_0_) {
      p_243729_0_.func_242513_a(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.field_243785_K);
   }

   public static void func_243730_an(BiomeGenerationSettings.Builder p_243730_0_) {
      p_243730_0_.func_242513_a(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Features.field_243794_T);
   }

   public static void func_243731_ao(BiomeGenerationSettings.Builder p_243731_0_) {
      p_243731_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243890_bj);
      p_243731_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243891_bk);
      p_243731_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243888_bh);
      p_243731_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243889_bi);
      func_243732_ap(p_243731_0_);
   }

   public static void func_243732_ap(BiomeGenerationSettings.Builder p_243732_0_) {
      p_243732_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243906_bz);
      p_243732_0_.func_242513_a(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.field_243855_bA);
   }

   public static void func_243714_a(MobSpawnInfo.Builder p_243714_0_) {
      p_243714_0_.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.SHEEP, 12, 4, 4));
      p_243714_0_.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PIG, 10, 4, 4));
      p_243714_0_.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.CHICKEN, 10, 4, 4));
      p_243714_0_.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.COW, 8, 4, 4));
   }

   public static void func_243734_b(MobSpawnInfo.Builder p_243734_0_) {
      p_243734_0_.func_242575_a(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(EntityType.BAT, 10, 8, 8));
   }

   public static void func_243737_c(MobSpawnInfo.Builder p_243737_0_) {
      func_243734_b(p_243737_0_);
      func_243735_b(p_243737_0_, 95, 5, 100);
   }

   public static void func_243716_a(MobSpawnInfo.Builder p_243716_0_, int p_243716_1_, int p_243716_2_, int p_243716_3_) {
      p_243716_0_.func_242575_a(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(EntityType.SQUID, p_243716_1_, 1, p_243716_2_));
      p_243716_0_.func_242575_a(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(EntityType.COD, p_243716_3_, 3, 6));
      func_243737_c(p_243716_0_);
      p_243716_0_.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.DROWNED, 5, 1, 1));
   }

   public static void func_243715_a(MobSpawnInfo.Builder p_243715_0_, int p_243715_1_, int p_243715_2_) {
      p_243715_0_.func_242575_a(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(EntityType.SQUID, p_243715_1_, p_243715_2_, 4));
      p_243715_0_.func_242575_a(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(EntityType.TROPICAL_FISH, 25, 8, 8));
      p_243715_0_.func_242575_a(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(EntityType.DOLPHIN, 2, 1, 2));
      func_243737_c(p_243715_0_);
   }

   public static void func_243739_d(MobSpawnInfo.Builder p_243739_0_) {
      func_243714_a(p_243739_0_);
      p_243739_0_.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.HORSE, 5, 2, 6));
      p_243739_0_.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.DONKEY, 1, 1, 3));
      func_243737_c(p_243739_0_);
   }

   public static void func_243741_e(MobSpawnInfo.Builder p_243741_0_) {
      p_243741_0_.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 10, 2, 3));
      p_243741_0_.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.POLAR_BEAR, 1, 1, 2));
      func_243734_b(p_243741_0_);
      func_243735_b(p_243741_0_, 95, 5, 20);
      p_243741_0_.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.STRAY, 80, 4, 4));
   }

   public static void func_243743_f(MobSpawnInfo.Builder p_243743_0_) {
      p_243743_0_.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 4, 2, 3));
      func_243734_b(p_243743_0_);
      func_243735_b(p_243743_0_, 19, 1, 100);
      p_243743_0_.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.HUSK, 80, 4, 4));
   }

   public static void func_243735_b(MobSpawnInfo.Builder p_243735_0_, int p_243735_1_, int p_243735_2_, int p_243735_3_) {
      p_243735_0_.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SPIDER, 100, 4, 4));
      p_243735_0_.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIE, p_243735_1_, 4, 4));
      p_243735_0_.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIE_VILLAGER, p_243735_2_, 1, 1));
      p_243735_0_.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SKELETON, p_243735_3_, 4, 4));
      p_243735_0_.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.CREEPER, 100, 4, 4));
      p_243735_0_.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SLIME, 100, 4, 4));
      p_243735_0_.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 10, 1, 4));
      p_243735_0_.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.WITCH, 5, 1, 1));
   }

   public static void func_243745_g(MobSpawnInfo.Builder p_243745_0_) {
      p_243745_0_.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.MOOSHROOM, 8, 4, 8));
      func_243734_b(p_243745_0_);
   }

   public static void func_243747_h(MobSpawnInfo.Builder p_243747_0_) {
      func_243714_a(p_243747_0_);
      p_243747_0_.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.CHICKEN, 10, 4, 4));
      func_243737_c(p_243747_0_);
   }

   public static void func_243749_i(MobSpawnInfo.Builder p_243749_0_) {
      p_243749_0_.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 10, 4, 4));
   }
}