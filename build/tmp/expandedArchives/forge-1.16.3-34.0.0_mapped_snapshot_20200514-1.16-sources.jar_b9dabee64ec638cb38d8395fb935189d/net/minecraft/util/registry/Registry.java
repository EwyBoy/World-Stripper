package net.minecraft.util.registry;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;
import com.mojang.serialization.Lifecycle;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootEntryManager;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootPoolEntryType;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IObjectIntIterable;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import net.minecraft.world.gen.blockstateprovider.BlockStateProviderType;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSizeType;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.jigsaw.IJigsawDeserializer;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.IPosRuleTests;
import net.minecraft.world.gen.feature.template.IRuleTestType;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.StructureProcessorList;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Attention Modders: This SHOULD NOT be used, you should use ForgeRegistries instead. As it has a cleaner modder facing API.
 * We will be wrapping all of these in our API as necessary for syncing and management.
 */
public abstract class Registry<T> implements Codec<T>, Keyable, IObjectIntIterable<T> {
   protected static final Logger LOGGER = LogManager.getLogger();
   private static final Map<ResourceLocation, Supplier<?>> LOCATION_TO_SUPPLIER = Maps.newLinkedHashMap();
   public static final ResourceLocation field_239706_f_ = new ResourceLocation("root");
   protected static final MutableRegistry<MutableRegistry<?>> field_239707_g_ = new SimpleRegistry<>(func_239741_a_("root"), Lifecycle.experimental());
   public static final Registry<? extends Registry<?>> REGISTRY = field_239707_g_;
   public static final RegistryKey<Registry<SoundEvent>> field_239708_i_ = func_239741_a_("sound_event");
   public static final RegistryKey<Registry<Fluid>> field_239709_j_ = func_239741_a_("fluid");
   public static final RegistryKey<Registry<Effect>> field_239710_k_ = func_239741_a_("mob_effect");
   public static final RegistryKey<Registry<Block>> field_239711_l_ = func_239741_a_("block");
   public static final RegistryKey<Registry<Enchantment>> field_239712_m_ = func_239741_a_("enchantment");
   public static final RegistryKey<Registry<EntityType<?>>> field_239713_n_ = func_239741_a_("entity_type");
   public static final RegistryKey<Registry<Item>> field_239714_o_ = func_239741_a_("item");
   public static final RegistryKey<Registry<Potion>> field_239715_p_ = func_239741_a_("potion");
   public static final RegistryKey<Registry<ParticleType<?>>> field_239664_B_ = func_239741_a_("particle_type");
   public static final RegistryKey<Registry<TileEntityType<?>>> field_239667_E_ = func_239741_a_("block_entity_type");
   public static final RegistryKey<Registry<PaintingType>> field_239668_F_ = func_239741_a_("motive");
   public static final RegistryKey<Registry<ResourceLocation>> field_239669_G_ = func_239741_a_("custom_stat");
   public static final RegistryKey<Registry<ChunkStatus>> field_239670_H_ = func_239741_a_("chunk_status");
   public static final RegistryKey<Registry<IRuleTestType<?>>> field_239673_K_ = func_239741_a_("rule_test");
   public static final RegistryKey<Registry<IPosRuleTests<?>>> field_239674_L_ = func_239741_a_("pos_rule_test");
   public static final RegistryKey<Registry<ContainerType<?>>> field_239677_O_ = func_239741_a_("menu");
   public static final RegistryKey<Registry<IRecipeType<?>>> field_239678_P_ = func_239741_a_("recipe_type");
   public static final RegistryKey<Registry<IRecipeSerializer<?>>> field_239679_Q_ = func_239741_a_("recipe_serializer");
   public static final RegistryKey<Registry<Attribute>> field_239680_R_ = func_239741_a_("attribute");
   public static final RegistryKey<Registry<StatType<?>>> field_239681_S_ = func_239741_a_("stat_type");
   public static final RegistryKey<Registry<VillagerType>> field_239682_T_ = func_239741_a_("villager_type");
   public static final RegistryKey<Registry<VillagerProfession>> field_239683_U_ = func_239741_a_("villager_profession");
   public static final RegistryKey<Registry<PointOfInterestType>> field_239684_V_ = func_239741_a_("point_of_interest_type");
   public static final RegistryKey<Registry<MemoryModuleType<?>>> field_239685_W_ = func_239741_a_("memory_module_type");
   public static final RegistryKey<Registry<SensorType<?>>> field_239686_X_ = func_239741_a_("sensor_type");
   public static final RegistryKey<Registry<Schedule>> field_239687_Y_ = func_239741_a_("schedule");
   public static final RegistryKey<Registry<Activity>> field_239688_Z_ = func_239741_a_("activity");
   public static final RegistryKey<Registry<LootPoolEntryType>> field_239695_aa_ = func_239741_a_("loot_pool_entry_type");
   public static final RegistryKey<Registry<LootFunctionType>> field_239696_ab_ = func_239741_a_("loot_function_type");
   public static final RegistryKey<Registry<LootConditionType>> field_239697_ac_ = func_239741_a_("loot_condition_type");
   public static final RegistryKey<Registry<DimensionType>> field_239698_ad_ = func_239741_a_("dimension_type");
   public static final RegistryKey<Registry<World>> field_239699_ae_ = func_239741_a_("dimension");
   public static final RegistryKey<Registry<Dimension>> field_239700_af_ = func_239741_a_("dimension");
   @Deprecated public static final Registry<SoundEvent> SOUND_EVENT = forge(field_239708_i_, () -> {
      return SoundEvents.ENTITY_ITEM_PICKUP;
   });
   @Deprecated public static final DefaultedRegistry<Fluid> FLUID = forge(field_239709_j_, "empty", () -> {
      return Fluids.EMPTY;
   });
   @Deprecated public static final Registry<Effect> EFFECTS = forge(field_239710_k_, () -> {
      return Effects.LUCK;
   });
   @Deprecated public static final DefaultedRegistry<Block> BLOCK = forge(field_239711_l_, "air", () -> {
      return Blocks.AIR;
   });
   @Deprecated public static final Registry<Enchantment> ENCHANTMENT = forge(field_239712_m_, () -> {
      return Enchantments.FORTUNE;
   });
   @Deprecated public static final DefaultedRegistry<EntityType<?>> ENTITY_TYPE = forge(field_239713_n_, "pig", () -> {
      return EntityType.PIG;
   });
   @Deprecated public static final DefaultedRegistry<Item> ITEM = forge(field_239714_o_, "air", () -> {
      return Items.AIR;
   });
   @Deprecated public static final DefaultedRegistry<Potion> POTION = forge(field_239715_p_, "empty", () -> {
      return Potions.EMPTY;
   });
   @Deprecated public static final Registry<ParticleType<?>> PARTICLE_TYPE = forge(field_239664_B_, () -> {
      return ParticleTypes.BLOCK;
   });
   @Deprecated public static final Registry<TileEntityType<?>> BLOCK_ENTITY_TYPE = forge(field_239667_E_, () -> {
      return TileEntityType.FURNACE;
   });
   @Deprecated public static final DefaultedRegistry<PaintingType> MOTIVE = forge(field_239668_F_, "kebab", () -> {
      return PaintingType.KEBAB;
   });
   public static final Registry<ResourceLocation> CUSTOM_STAT = func_239746_a_(field_239669_G_, () -> {
      return Stats.JUMP;
   });
   @Deprecated public static final DefaultedRegistry<ChunkStatus> CHUNK_STATUS = forge(field_239670_H_, "empty", () -> {
      return ChunkStatus.EMPTY;
   });
   public static final Registry<IRuleTestType<?>> RULE_TEST = func_239746_a_(field_239673_K_, () -> {
      return IRuleTestType.ALWAYS_TRUE;
   });
   public static final Registry<IPosRuleTests<?>> field_239691_aJ_ = func_239746_a_(field_239674_L_, () -> {
      return IPosRuleTests.field_237103_a_;
   });
   @Deprecated public static final Registry<ContainerType<?>> MENU = forge(field_239677_O_, () -> {
      return ContainerType.ANVIL;
   });
   public static final Registry<IRecipeType<?>> RECIPE_TYPE = func_239746_a_(field_239678_P_, () -> {
      return IRecipeType.CRAFTING;
   });
   @Deprecated public static final Registry<IRecipeSerializer<?>> RECIPE_SERIALIZER = forge(field_239679_Q_, () -> {
      return IRecipeSerializer.CRAFTING_SHAPELESS;
   });
   @Deprecated public static final Registry<Attribute> field_239692_aP_ = forge(field_239680_R_, () -> {
      return Attributes.field_233828_k_;
   });
   @Deprecated public static final Registry<StatType<?>> STATS = forge(field_239681_S_, () -> {
      return Stats.ITEM_USED;
   });
   public static final DefaultedRegistry<VillagerType> VILLAGER_TYPE = func_239745_a_(field_239682_T_, "plains", () -> {
      return VillagerType.PLAINS;
   });
   @Deprecated public static final DefaultedRegistry<VillagerProfession> VILLAGER_PROFESSION = forge(field_239683_U_, "none", () -> {
      return VillagerProfession.NONE;
   });
   @Deprecated public static final DefaultedRegistry<PointOfInterestType> POINT_OF_INTEREST_TYPE = forge(field_239684_V_, "unemployed", () -> {
      return PointOfInterestType.UNEMPLOYED;
   });
   @Deprecated public static final DefaultedRegistry<MemoryModuleType<?>> MEMORY_MODULE_TYPE = forge(field_239685_W_, "dummy", () -> {
      return MemoryModuleType.DUMMY;
   });
   @Deprecated public static final DefaultedRegistry<SensorType<?>> SENSOR_TYPE = forge(field_239686_X_, "dummy", () -> {
      return SensorType.DUMMY;
   });
   @Deprecated public static final Registry<Schedule> SCHEDULE = forge(field_239687_Y_, () -> {
      return Schedule.EMPTY;
   });
   @Deprecated public static final Registry<Activity> ACTIVITY = forge(field_239688_Z_, () -> {
      return Activity.IDLE;
   });
   public static final Registry<LootPoolEntryType> field_239693_aY_ = func_239746_a_(field_239695_aa_, () -> {
      return LootEntryManager.field_237410_a_;
   });
   public static final Registry<LootFunctionType> field_239694_aZ_ = func_239746_a_(field_239696_ab_, () -> {
      return LootFunctionManager.field_237429_b_;
   });
   public static final Registry<LootConditionType> field_239704_ba_ = func_239746_a_(field_239697_ac_, () -> {
      return LootConditionManager.field_237458_a_;
   });
   public static final RegistryKey<Registry<DimensionSettings>> field_243549_ar = func_239741_a_("worldgen/noise_settings");
   public static final RegistryKey<Registry<ConfiguredSurfaceBuilder<?>>> field_243550_as = func_239741_a_("worldgen/configured_surface_builder");
   public static final RegistryKey<Registry<ConfiguredCarver<?>>> field_243551_at = func_239741_a_("worldgen/configured_carver");
   public static final RegistryKey<Registry<ConfiguredFeature<?, ?>>> field_243552_au = func_239741_a_("worldgen/configured_feature");
   public static final RegistryKey<Registry<StructureFeature<?, ?>>> field_243553_av = func_239741_a_("worldgen/configured_structure_feature");
   public static final RegistryKey<Registry<StructureProcessorList>> field_243554_aw = func_239741_a_("worldgen/processor_list");
   public static final RegistryKey<Registry<JigsawPattern>> field_243555_ax = func_239741_a_("worldgen/template_pool");
   public static final RegistryKey<Registry<Biome>> field_239720_u_ = func_239741_a_("worldgen/biome");
   public static final RegistryKey<Registry<SurfaceBuilder<?>>> field_239717_r_ = func_239741_a_("worldgen/surface_builder");
   @Deprecated public static final Registry<SurfaceBuilder<?>> SURFACE_BUILDER = forge(field_239717_r_, () -> {
      return SurfaceBuilder.DEFAULT;
   });
   public static final RegistryKey<Registry<WorldCarver<?>>> field_239716_q_ = func_239741_a_("worldgen/carver");
   @Deprecated public static final Registry<WorldCarver<?>> CARVER = forge(field_239716_q_, () -> {
      return WorldCarver.CAVE;
   });
   public static final RegistryKey<Registry<Feature<?>>> field_239718_s_ = func_239741_a_("worldgen/feature");
   @Deprecated public static final Registry<Feature<?>> FEATURE = forge(field_239718_s_, () -> {
      return Feature.ORE;
   });
   public static final RegistryKey<Registry<Structure<?>>> field_239671_I_ = func_239741_a_("worldgen/structure_feature");
   @Deprecated public static final Registry<Structure<?>> STRUCTURE_FEATURE = forge(field_239671_I_, () -> {
      return Structure.field_236367_c_;
   });
   public static final RegistryKey<Registry<IStructurePieceType>> field_239672_J_ = func_239741_a_("worldgen/structure_piece");
   public static final Registry<IStructurePieceType> STRUCTURE_PIECE = func_239746_a_(field_239672_J_, () -> {
      return IStructurePieceType.MSROOM;
   });
   public static final RegistryKey<Registry<Placement<?>>> field_239719_t_ = func_239741_a_("worldgen/decorator");
   @Deprecated public static final Registry<Placement<?>> DECORATOR = forge(field_239719_t_, () -> {
      return Placement.NOPE;
   });
   public static final RegistryKey<Registry<BlockStateProviderType<?>>> field_239721_v_ = func_239741_a_("worldgen/block_state_provider_type");
   public static final RegistryKey<Registry<BlockPlacerType<?>>> field_239722_w_ = func_239741_a_("worldgen/block_placer_type");
   public static final RegistryKey<Registry<FoliagePlacerType<?>>> field_239723_x_ = func_239741_a_("worldgen/foliage_placer_type");
   public static final RegistryKey<Registry<TrunkPlacerType<?>>> field_239724_y_ = func_239741_a_("worldgen/trunk_placer_type");
   public static final RegistryKey<Registry<TreeDecoratorType<?>>> field_239725_z_ = func_239741_a_("worldgen/tree_decorator_type");
   public static final RegistryKey<Registry<FeatureSizeType<?>>> field_239663_A_ = func_239741_a_("worldgen/feature_size_type");
   public static final RegistryKey<Registry<Codec<? extends BiomeProvider>>> field_239665_C_ = func_239741_a_("worldgen/biome_source");
   public static final RegistryKey<Registry<Codec<? extends ChunkGenerator>>> field_239666_D_ = func_239741_a_("worldgen/chunk_generator");
   public static final RegistryKey<Registry<IStructureProcessorType<?>>> field_239675_M_ = func_239741_a_("worldgen/structure_processor");
   public static final RegistryKey<Registry<IJigsawDeserializer<?>>> field_239676_N_ = func_239741_a_("worldgen/structure_pool_element");
   @Deprecated public static final Registry<BlockStateProviderType<?>> BLOCK_STATE_PROVIDER_TYPE = forge(field_239721_v_, () -> {
      return BlockStateProviderType.SIMPLE_STATE_PROVIDER;
   });
   @Deprecated public static final Registry<BlockPlacerType<?>> BLOCK_PLACER_TYPE = forge(field_239722_w_, () -> {
      return BlockPlacerType.SIMPLE_BLOCK;
   });
   @Deprecated public static final Registry<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPE = forge(field_239723_x_, () -> {
      return FoliagePlacerType.BLOB;
   });
   public static final Registry<TrunkPlacerType<?>> field_239701_aw_ = func_239746_a_(field_239724_y_, () -> {
      return TrunkPlacerType.field_236920_a_;
   });
   @Deprecated public static final Registry<TreeDecoratorType<?>> TREE_DECORATOR_TYPE = forge(field_239725_z_, () -> {
      return TreeDecoratorType.LEAVE_VINE;
   });
   public static final Registry<FeatureSizeType<?>> field_239702_ay_ = func_239746_a_(field_239663_A_, () -> {
      return FeatureSizeType.field_236711_a_;
   });
   public static final Registry<Codec<? extends BiomeProvider>> field_239689_aA_ = func_239742_a_(field_239665_C_, Lifecycle.stable(), () -> {
      return BiomeProvider.field_235202_a_;
   });
   public static final Registry<Codec<? extends ChunkGenerator>> field_239690_aB_ = func_239742_a_(field_239666_D_, Lifecycle.stable(), () -> {
      return ChunkGenerator.field_235948_a_;
   });
   public static final Registry<IStructureProcessorType<?>> STRUCTURE_PROCESSOR = func_239746_a_(field_239675_M_, () -> {
      return IStructureProcessorType.BLOCK_IGNORE;
   });
   public static final Registry<IJigsawDeserializer<?>> STRUCTURE_POOL_ELEMENT = func_239746_a_(field_239676_N_, () -> {
      return IJigsawDeserializer.EMPTY_POOL_ELEMENT;
   });
   private final RegistryKey<? extends Registry<T>> field_239703_b_;
   private final Lifecycle field_239705_c_;

   private static <T> RegistryKey<Registry<T>> func_239741_a_(String p_239741_0_) {
      return RegistryKey.func_240904_a_(new ResourceLocation(p_239741_0_));
   }

   public static <T extends MutableRegistry<?>> void func_239738_a_(MutableRegistry<T> p_239738_0_) {
      p_239738_0_.forEach((p_239739_1_) -> {
         if (p_239739_1_.keySet().isEmpty()) {
            LOGGER.error("Registry '{}' was empty after loading", (Object)p_239738_0_.getKey(p_239739_1_));
            if (SharedConstants.developmentMode) {
               throw new IllegalStateException("Registry: '" + p_239738_0_.getKey(p_239739_1_) + "' is empty, not allowed, fix me!");
            }
         }

         if (p_239739_1_ instanceof DefaultedRegistry) {
            ResourceLocation resourcelocation = ((DefaultedRegistry)p_239739_1_).getDefaultKey();
            Validate.notNull(p_239739_1_.getOrDefault(resourcelocation), "Missing default of DefaultedMappedRegistry: " + resourcelocation);
         }

      });
   }

   private static <T> Registry<T> func_239746_a_(RegistryKey<? extends Registry<T>> p_239746_0_, Supplier<T> p_239746_1_) {
      return func_239742_a_(p_239746_0_, Lifecycle.experimental(), p_239746_1_);
   }

   private static <T extends net.minecraftforge.registries.IForgeRegistryEntry<T>> Registry<T> forge(RegistryKey<? extends Registry<T>> key, Supplier<T> def) {
      return forge(key, Lifecycle.experimental(), def);
   }

   private static <T> DefaultedRegistry<T> func_239745_a_(RegistryKey<? extends Registry<T>> p_239745_0_, String p_239745_1_, Supplier<T> p_239745_2_) {
      return func_239744_a_(p_239745_0_, p_239745_1_, Lifecycle.experimental(), p_239745_2_);
   }

   private static <T extends net.minecraftforge.registries.IForgeRegistryEntry<T>> DefaultedRegistry<T> forge(RegistryKey<? extends Registry<T>> key, String defKey, Supplier<T> def) {
      return forge(key, defKey, Lifecycle.experimental(), def);
   }

   private static <T> Registry<T> func_239742_a_(RegistryKey<? extends Registry<T>> p_239742_0_, Lifecycle p_239742_1_, Supplier<T> p_239742_2_) {
      return func_239743_a_(p_239742_0_, new SimpleRegistry<>(p_239742_0_, p_239742_1_), p_239742_2_, p_239742_1_);
   }

   private static <T extends net.minecraftforge.registries.IForgeRegistryEntry<T>> Registry<T> forge(RegistryKey<? extends Registry<T>> key, Lifecycle cycle, Supplier<T> def) {
      return func_239743_a_(key, net.minecraftforge.registries.GameData.getWrapper(key, cycle), def, cycle);
   }

   private static <T> DefaultedRegistry<T> func_239744_a_(RegistryKey<? extends Registry<T>> p_239744_0_, String p_239744_1_, Lifecycle p_239744_2_, Supplier<T> p_239744_3_) {
      return func_239743_a_(p_239744_0_, new DefaultedRegistry<>(p_239744_1_, p_239744_0_, p_239744_2_), p_239744_3_, p_239744_2_);
   }

   private static <T extends net.minecraftforge.registries.IForgeRegistryEntry<T>> DefaultedRegistry<T> forge(RegistryKey<? extends Registry<T>> key, String defKey, Lifecycle cycle, Supplier<T> def) {
      return func_239743_a_(key, net.minecraftforge.registries.GameData.getWrapper(key, cycle, defKey), def, cycle);
   }

   private static <T, R extends MutableRegistry<T>> R func_239743_a_(RegistryKey<? extends Registry<T>> p_239743_0_, R p_239743_1_, Supplier<T> p_239743_2_, Lifecycle p_239743_3_) {
      ResourceLocation resourcelocation = p_239743_0_.func_240901_a_();
      LOCATION_TO_SUPPLIER.put(resourcelocation, p_239743_2_);
      MutableRegistry<R> mutableregistry = (MutableRegistry<R>)field_239707_g_;
      return (R)mutableregistry.register((RegistryKey)p_239743_0_, p_239743_1_, p_239743_3_);
   }

   protected Registry(RegistryKey<? extends Registry<T>> p_i232510_1_, Lifecycle p_i232510_2_) {
      this.field_239703_b_ = p_i232510_1_;
      this.field_239705_c_ = p_i232510_2_;
   }

   public RegistryKey<? extends Registry<T>> func_243578_f() {
      return this.field_239703_b_;
   }

   public String toString() {
      return "Registry[" + this.field_239703_b_ + " (" + this.field_239705_c_ + ")]";
   }

   public <U> DataResult<Pair<T, U>> decode(DynamicOps<U> p_decode_1_, U p_decode_2_) {
      return p_decode_1_.compressMaps() ? p_decode_1_.getNumberValue(p_decode_2_).flatMap((p_239740_1_) -> {
         T t = this.getByValue(p_239740_1_.intValue());
         return t == null ? DataResult.error("Unknown registry id: " + p_239740_1_) : DataResult.success(t, this.func_241876_d(t));
      }).map((p_239736_1_) -> {
         return Pair.of((T)p_239736_1_, p_decode_1_.empty());
      }) : ResourceLocation.field_240908_a_.decode(p_decode_1_, p_decode_2_).flatMap((p_239735_1_) -> {
         T t = this.getOrDefault(p_239735_1_.getFirst());
         return t == null ? DataResult.error("Unknown registry key: " + p_239735_1_.getFirst()) : DataResult.success(Pair.of(t, p_239735_1_.getSecond()), this.func_241876_d(t));
      });
   }

   public <U> DataResult<U> encode(T p_encode_1_, DynamicOps<U> p_encode_2_, U p_encode_3_) {
      ResourceLocation resourcelocation = this.getKey(p_encode_1_);
      if (resourcelocation == null) {
         return DataResult.error("Unknown registry element " + p_encode_1_);
      } else {
         return p_encode_2_.compressMaps() ? p_encode_2_.mergeToPrimitive(p_encode_3_, p_encode_2_.createInt(this.getId(p_encode_1_))).setLifecycle(this.field_239705_c_) : p_encode_2_.mergeToPrimitive(p_encode_3_, p_encode_2_.createString(resourcelocation.toString())).setLifecycle(this.field_239705_c_);
      }
   }

   public <U> Stream<U> keys(DynamicOps<U> p_keys_1_) {
      return this.keySet().stream().map((p_243574_1_) -> {
         return p_keys_1_.createString(p_243574_1_.toString());
      });
   }

   /**
    * Gets the name we use to identify the given object.
    */
   @Nullable
   public abstract ResourceLocation getKey(T value);

   public abstract Optional<RegistryKey<T>> func_230519_c_(T p_230519_1_);

   /**
    * Gets the integer ID we use to identify the given object.
    */
   public abstract int getId(@Nullable T value);

   @Nullable
   public abstract T func_230516_a_(@Nullable RegistryKey<T> p_230516_1_);

   @Nullable
   public abstract T getOrDefault(@Nullable ResourceLocation name);

   protected abstract Lifecycle func_241876_d(T p_241876_1_);

   public abstract Lifecycle func_241875_b();

   public Optional<T> func_241873_b(@Nullable ResourceLocation p_241873_1_) {
      return Optional.ofNullable(this.getOrDefault(p_241873_1_));
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<T> func_243575_c(@Nullable RegistryKey<T> p_243575_1_) {
      return Optional.ofNullable(this.func_230516_a_(p_243575_1_));
   }

   public T func_243576_d(RegistryKey<T> p_243576_1_) {
      T t = this.func_230516_a_(p_243576_1_);
      if (t == null) {
         throw new IllegalStateException("Missing: " + p_243576_1_);
      } else {
         return t;
      }
   }

   /**
    * Gets all the keys recognized by this registry.
    */
   public abstract Set<ResourceLocation> keySet();

   public abstract Set<Entry<RegistryKey<T>, T>> func_239659_c_();

   public Stream<T> stream() {
      return StreamSupport.stream(this.spliterator(), false);
   }

   @OnlyIn(Dist.CLIENT)
   public abstract boolean containsKey(ResourceLocation name);

   public static <T> T register(Registry<? super T> p_218325_0_, String p_218325_1_, T p_218325_2_) {
      return register(p_218325_0_, new ResourceLocation(p_218325_1_), p_218325_2_);
   }

   public static <V, T extends V> T register(Registry<V> p_218322_0_, ResourceLocation p_218322_1_, T p_218322_2_) {
      return ((MutableRegistry<V>)p_218322_0_).register(RegistryKey.func_240903_a_(p_218322_0_.field_239703_b_, p_218322_1_), p_218322_2_, Lifecycle.stable());
   }

   public static <V, T extends V> T register(Registry<V> p_218343_0_, int p_218343_1_, String p_218343_2_, T p_218343_3_) {
      return ((MutableRegistry<V>)p_218343_0_).register(p_218343_1_, RegistryKey.func_240903_a_(p_218343_0_.field_239703_b_, new ResourceLocation(p_218343_2_)), p_218343_3_, Lifecycle.stable());
   }

   static {
      WorldGenRegistries.func_243661_a();
      LOCATION_TO_SUPPLIER.forEach((p_239747_0_, p_239747_1_) -> {
         if (p_239747_1_.get() == null) {
            LOGGER.error("Unable to bootstrap registry '{}'", (Object)p_239747_0_);
         }

      });
      func_239738_a_(field_239707_g_);
   }
}