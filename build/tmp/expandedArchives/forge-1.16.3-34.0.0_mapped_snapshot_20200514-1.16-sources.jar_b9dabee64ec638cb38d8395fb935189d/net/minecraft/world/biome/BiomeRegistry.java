package net.minecraft.world.biome;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;

public abstract class BiomeRegistry {
   private static final Int2ObjectMap<RegistryKey<Biome>> field_244202_c = new Int2ObjectArrayMap<>();
   public static final Biome field_244200_a = func_244204_a(1, Biomes.PLAINS, BiomeMaker.func_244226_a(false));
   public static final Biome field_244201_b = func_244204_a(127, Biomes.THE_VOID, BiomeMaker.func_244252_r());

   private static Biome func_244204_a(int p_244204_0_, RegistryKey<Biome> p_244204_1_, Biome p_244204_2_) {
      field_244202_c.put(p_244204_0_, p_244204_1_);
      return WorldGenRegistries.func_243662_a(WorldGenRegistries.field_243657_i, p_244204_0_, p_244204_1_, p_244204_2_);
   }

   public static RegistryKey<Biome> func_244203_a(int p_244203_0_) {
      return ((net.minecraftforge.registries.ForgeRegistry<Biome>)net.minecraftforge.registries.ForgeRegistries.BIOMES).getKey(p_244203_0_);
   }

   static {
      func_244204_a(0, Biomes.OCEAN, BiomeMaker.func_244234_c(false));
      func_244204_a(2, Biomes.DESERT, BiomeMaker.func_244220_a(0.125F, 0.05F, true, true, true));
      func_244204_a(3, Biomes.MOUNTAINS, BiomeMaker.func_244216_a(1.0F, 0.5F, ConfiguredSurfaceBuilders.field_244181_m, false));
      func_244204_a(4, Biomes.FOREST, BiomeMaker.func_244232_c(0.1F, 0.2F));
      func_244204_a(5, Biomes.TAIGA, BiomeMaker.func_244221_a(0.2F, 0.2F, false, false, true, false));
      func_244204_a(6, Biomes.SWAMP, BiomeMaker.func_244236_d(-0.2F, 0.1F, false));
      func_244204_a(7, Biomes.RIVER, BiomeMaker.func_244209_a(-0.5F, 0.0F, 0.5F, 4159204, false));
      func_244204_a(8, Biomes.field_235254_j_, BiomeMaker.func_244253_s());
      func_244204_a(9, Biomes.THE_END, BiomeMaker.func_244243_i());
      func_244204_a(10, Biomes.FROZEN_OCEAN, BiomeMaker.func_244239_e(false));
      func_244204_a(11, Biomes.FROZEN_RIVER, BiomeMaker.func_244209_a(-0.5F, 0.0F, 0.0F, 3750089, true));
      func_244204_a(12, Biomes.SNOWY_TUNDRA, BiomeMaker.func_244219_a(0.125F, 0.05F, false, false));
      func_244204_a(13, Biomes.SNOWY_MOUNTAINS, BiomeMaker.func_244219_a(0.45F, 0.3F, false, true));
      func_244204_a(14, Biomes.MUSHROOM_FIELDS, BiomeMaker.func_244207_a(0.2F, 0.3F));
      func_244204_a(15, Biomes.MUSHROOM_FIELD_SHORE, BiomeMaker.func_244207_a(0.0F, 0.025F));
      func_244204_a(16, Biomes.BEACH, BiomeMaker.func_244208_a(0.0F, 0.025F, 0.8F, 0.4F, 4159204, false, false));
      func_244204_a(17, Biomes.DESERT_HILLS, BiomeMaker.func_244220_a(0.45F, 0.3F, false, true, false));
      func_244204_a(18, Biomes.WOODED_HILLS, BiomeMaker.func_244232_c(0.45F, 0.3F));
      func_244204_a(19, Biomes.TAIGA_HILLS, BiomeMaker.func_244221_a(0.45F, 0.3F, false, false, false, false));
      func_244204_a(20, Biomes.MOUNTAIN_EDGE, BiomeMaker.func_244216_a(0.8F, 0.3F, ConfiguredSurfaceBuilders.field_244178_j, true));
      func_244204_a(21, Biomes.JUNGLE, BiomeMaker.func_244205_a());
      func_244204_a(22, Biomes.JUNGLE_HILLS, BiomeMaker.func_244238_e());
      func_244204_a(23, Biomes.JUNGLE_EDGE, BiomeMaker.func_244227_b());
      func_244204_a(24, Biomes.DEEP_OCEAN, BiomeMaker.func_244234_c(true));
      func_244204_a(25, Biomes.STONE_SHORE, BiomeMaker.func_244208_a(0.1F, 0.8F, 0.2F, 0.3F, 4159204, false, true));
      func_244204_a(26, Biomes.SNOWY_BEACH, BiomeMaker.func_244208_a(0.0F, 0.025F, 0.05F, 0.3F, 4020182, true, false));
      func_244204_a(27, Biomes.BIRCH_FOREST, BiomeMaker.func_244217_a(0.1F, 0.2F, false));
      func_244204_a(28, Biomes.BIRCH_FOREST_HILLS, BiomeMaker.func_244217_a(0.45F, 0.3F, false));
      func_244204_a(29, Biomes.DARK_FOREST, BiomeMaker.func_244233_c(0.1F, 0.2F, false));
      func_244204_a(30, Biomes.SNOWY_TAIGA, BiomeMaker.func_244221_a(0.2F, 0.2F, true, false, false, true));
      func_244204_a(31, Biomes.SNOWY_TAIGA_HILLS, BiomeMaker.func_244221_a(0.45F, 0.3F, true, false, false, false));
      func_244204_a(32, Biomes.GIANT_TREE_TAIGA, BiomeMaker.func_244210_a(0.2F, 0.2F, 0.3F, false));
      func_244204_a(33, Biomes.GIANT_TREE_TAIGA_HILLS, BiomeMaker.func_244210_a(0.45F, 0.3F, 0.3F, false));
      func_244204_a(34, Biomes.WOODED_MOUNTAINS, BiomeMaker.func_244216_a(1.0F, 0.5F, ConfiguredSurfaceBuilders.field_244178_j, true));
      func_244204_a(35, Biomes.SAVANNA, BiomeMaker.func_244211_a(0.125F, 0.05F, 1.2F, false, false));
      func_244204_a(36, Biomes.SAVANNA_PLATEAU, BiomeMaker.func_244247_m());
      func_244204_a(37, Biomes.BADLANDS, BiomeMaker.func_244229_b(0.1F, 0.2F, false));
      func_244204_a(38, Biomes.WOODED_BADLANDS_PLATEAU, BiomeMaker.func_244228_b(1.5F, 0.025F));
      func_244204_a(39, Biomes.BADLANDS_PLATEAU, BiomeMaker.func_244229_b(1.5F, 0.025F, true));
      func_244204_a(40, Biomes.SMALL_END_ISLANDS, BiomeMaker.func_244246_l());
      func_244204_a(41, Biomes.END_MIDLANDS, BiomeMaker.func_244244_j());
      func_244204_a(42, Biomes.END_HIGHLANDS, BiomeMaker.func_244245_k());
      func_244204_a(43, Biomes.END_BARRENS, BiomeMaker.func_244242_h());
      func_244204_a(44, Biomes.WARM_OCEAN, BiomeMaker.func_244249_o());
      func_244204_a(45, Biomes.LUKEWARM_OCEAN, BiomeMaker.func_244237_d(false));
      func_244204_a(46, Biomes.COLD_OCEAN, BiomeMaker.func_244230_b(false));
      func_244204_a(47, Biomes.DEEP_WARM_OCEAN, BiomeMaker.func_244250_p());
      func_244204_a(48, Biomes.DEEP_LUKEWARM_OCEAN, BiomeMaker.func_244237_d(true));
      func_244204_a(49, Biomes.DEEP_COLD_OCEAN, BiomeMaker.func_244230_b(true));
      func_244204_a(50, Biomes.DEEP_FROZEN_OCEAN, BiomeMaker.func_244239_e(true));
      func_244204_a(129, Biomes.SUNFLOWER_PLAINS, BiomeMaker.func_244226_a(true));
      func_244204_a(130, Biomes.DESERT_LAKES, BiomeMaker.func_244220_a(0.225F, 0.25F, false, false, false));
      func_244204_a(131, Biomes.GRAVELLY_MOUNTAINS, BiomeMaker.func_244216_a(1.0F, 0.5F, ConfiguredSurfaceBuilders.field_244179_k, false));
      func_244204_a(132, Biomes.FLOWER_FOREST, BiomeMaker.func_244251_q());
      func_244204_a(133, Biomes.TAIGA_MOUNTAINS, BiomeMaker.func_244221_a(0.3F, 0.4F, false, true, false, false));
      func_244204_a(134, Biomes.SWAMP_HILLS, BiomeMaker.func_244236_d(-0.1F, 0.3F, true));
      func_244204_a(140, Biomes.ICE_SPIKES, BiomeMaker.func_244219_a(0.425F, 0.45000002F, true, false));
      func_244204_a(149, Biomes.MODIFIED_JUNGLE, BiomeMaker.func_244235_d());
      func_244204_a(151, Biomes.MODIFIED_JUNGLE_EDGE, BiomeMaker.func_244231_c());
      func_244204_a(155, Biomes.TALL_BIRCH_FOREST, BiomeMaker.func_244217_a(0.2F, 0.4F, true));
      func_244204_a(156, Biomes.TALL_BIRCH_HILLS, BiomeMaker.func_244217_a(0.55F, 0.5F, true));
      func_244204_a(157, Biomes.DARK_FOREST_HILLS, BiomeMaker.func_244233_c(0.2F, 0.4F, true));
      func_244204_a(158, Biomes.SNOWY_TAIGA_MOUNTAINS, BiomeMaker.func_244221_a(0.3F, 0.4F, true, true, false, false));
      func_244204_a(160, Biomes.GIANT_SPRUCE_TAIGA, BiomeMaker.func_244210_a(0.2F, 0.2F, 0.25F, true));
      func_244204_a(161, Biomes.GIANT_SPRUCE_TAIGA_HILLS, BiomeMaker.func_244210_a(0.2F, 0.2F, 0.25F, true));
      func_244204_a(162, Biomes.MODIFIED_GRAVELLY_MOUNTAINS, BiomeMaker.func_244216_a(1.0F, 0.5F, ConfiguredSurfaceBuilders.field_244179_k, false));
      func_244204_a(163, Biomes.SHATTERED_SAVANNA, BiomeMaker.func_244211_a(0.3625F, 1.225F, 1.1F, true, true));
      func_244204_a(164, Biomes.SHATTERED_SAVANNA_PLATEAU, BiomeMaker.func_244211_a(1.05F, 1.2125001F, 1.0F, true, true));
      func_244204_a(165, Biomes.ERODED_BADLANDS, BiomeMaker.func_244248_n());
      func_244204_a(166, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, BiomeMaker.func_244228_b(0.45F, 0.3F));
      func_244204_a(167, Biomes.MODIFIED_BADLANDS_PLATEAU, BiomeMaker.func_244229_b(0.45F, 0.3F, true));
      func_244204_a(168, Biomes.BAMBOO_JUNGLE, BiomeMaker.func_244240_f());
      func_244204_a(169, Biomes.BAMBOO_JUNGLE_HILLS, BiomeMaker.func_244241_g());
      func_244204_a(170, Biomes.field_235252_ay_, BiomeMaker.func_244254_t());
      func_244204_a(171, Biomes.field_235253_az_, BiomeMaker.func_244256_v());
      func_244204_a(172, Biomes.field_235250_aA_, BiomeMaker.func_244257_w());
      func_244204_a(173, Biomes.field_235251_aB_, BiomeMaker.func_244255_u());
   }
}