package net.minecraft.tags;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

public final class BlockTags {
   protected static final TagRegistry<Block> collection = TagRegistryManager.func_242196_a(new ResourceLocation("block"), ITagCollectionSupplier::func_241835_a);
   public static final ITag.INamedTag<Block> WOOL = makeWrapperTag("wool");
   public static final ITag.INamedTag<Block> PLANKS = makeWrapperTag("planks");
   public static final ITag.INamedTag<Block> STONE_BRICKS = makeWrapperTag("stone_bricks");
   public static final ITag.INamedTag<Block> WOODEN_BUTTONS = makeWrapperTag("wooden_buttons");
   public static final ITag.INamedTag<Block> BUTTONS = makeWrapperTag("buttons");
   public static final ITag.INamedTag<Block> CARPETS = makeWrapperTag("carpets");
   public static final ITag.INamedTag<Block> WOODEN_DOORS = makeWrapperTag("wooden_doors");
   public static final ITag.INamedTag<Block> WOODEN_STAIRS = makeWrapperTag("wooden_stairs");
   public static final ITag.INamedTag<Block> WOODEN_SLABS = makeWrapperTag("wooden_slabs");
   public static final ITag.INamedTag<Block> WOODEN_FENCES = makeWrapperTag("wooden_fences");
   public static final ITag.INamedTag<Block> field_232885_k_ = makeWrapperTag("pressure_plates");
   public static final ITag.INamedTag<Block> WOODEN_PRESSURE_PLATES = makeWrapperTag("wooden_pressure_plates");
   public static final ITag.INamedTag<Block> field_232886_m_ = makeWrapperTag("stone_pressure_plates");
   public static final ITag.INamedTag<Block> WOODEN_TRAPDOORS = makeWrapperTag("wooden_trapdoors");
   public static final ITag.INamedTag<Block> DOORS = makeWrapperTag("doors");
   public static final ITag.INamedTag<Block> SAPLINGS = makeWrapperTag("saplings");
   public static final ITag.INamedTag<Block> field_232887_q_ = makeWrapperTag("logs_that_burn");
   public static final ITag.INamedTag<Block> LOGS = makeWrapperTag("logs");
   public static final ITag.INamedTag<Block> DARK_OAK_LOGS = makeWrapperTag("dark_oak_logs");
   public static final ITag.INamedTag<Block> OAK_LOGS = makeWrapperTag("oak_logs");
   public static final ITag.INamedTag<Block> BIRCH_LOGS = makeWrapperTag("birch_logs");
   public static final ITag.INamedTag<Block> ACACIA_LOGS = makeWrapperTag("acacia_logs");
   public static final ITag.INamedTag<Block> JUNGLE_LOGS = makeWrapperTag("jungle_logs");
   public static final ITag.INamedTag<Block> SPRUCE_LOGS = makeWrapperTag("spruce_logs");
   public static final ITag.INamedTag<Block> field_232888_y_ = makeWrapperTag("crimson_stems");
   public static final ITag.INamedTag<Block> field_232889_z_ = makeWrapperTag("warped_stems");
   public static final ITag.INamedTag<Block> BANNERS = makeWrapperTag("banners");
   public static final ITag.INamedTag<Block> SAND = makeWrapperTag("sand");
   public static final ITag.INamedTag<Block> STAIRS = makeWrapperTag("stairs");
   public static final ITag.INamedTag<Block> SLABS = makeWrapperTag("slabs");
   public static final ITag.INamedTag<Block> WALLS = makeWrapperTag("walls");
   public static final ITag.INamedTag<Block> ANVIL = makeWrapperTag("anvil");
   public static final ITag.INamedTag<Block> RAILS = makeWrapperTag("rails");
   public static final ITag.INamedTag<Block> LEAVES = makeWrapperTag("leaves");
   public static final ITag.INamedTag<Block> TRAPDOORS = makeWrapperTag("trapdoors");
   public static final ITag.INamedTag<Block> SMALL_FLOWERS = makeWrapperTag("small_flowers");
   public static final ITag.INamedTag<Block> BEDS = makeWrapperTag("beds");
   public static final ITag.INamedTag<Block> FENCES = makeWrapperTag("fences");
   public static final ITag.INamedTag<Block> TALL_FLOWERS = makeWrapperTag("tall_flowers");
   public static final ITag.INamedTag<Block> FLOWERS = makeWrapperTag("flowers");
   public static final ITag.INamedTag<Block> field_232865_O_ = makeWrapperTag("piglin_repellents");
   public static final ITag.INamedTag<Block> field_232866_P_ = makeWrapperTag("gold_ores");
   public static final ITag.INamedTag<Block> field_232867_Q_ = makeWrapperTag("non_flammable_wood");
   public static final ITag.INamedTag<Block> FLOWER_POTS = makeWrapperTag("flower_pots");
   public static final ITag.INamedTag<Block> ENDERMAN_HOLDABLE = makeWrapperTag("enderman_holdable");
   public static final ITag.INamedTag<Block> ICE = makeWrapperTag("ice");
   public static final ITag.INamedTag<Block> VALID_SPAWN = makeWrapperTag("valid_spawn");
   public static final ITag.INamedTag<Block> IMPERMEABLE = makeWrapperTag("impermeable");
   public static final ITag.INamedTag<Block> UNDERWATER_BONEMEALS = makeWrapperTag("underwater_bonemeals");
   public static final ITag.INamedTag<Block> CORAL_BLOCKS = makeWrapperTag("coral_blocks");
   public static final ITag.INamedTag<Block> WALL_CORALS = makeWrapperTag("wall_corals");
   public static final ITag.INamedTag<Block> CORAL_PLANTS = makeWrapperTag("coral_plants");
   public static final ITag.INamedTag<Block> CORALS = makeWrapperTag("corals");
   public static final ITag.INamedTag<Block> BAMBOO_PLANTABLE_ON = makeWrapperTag("bamboo_plantable_on");
   public static final ITag.INamedTag<Block> STANDING_SIGNS = makeWrapperTag("standing_signs");
   public static final ITag.INamedTag<Block> WALL_SIGNS = makeWrapperTag("wall_signs");
   public static final ITag.INamedTag<Block> SIGNS = makeWrapperTag("signs");
   public static final ITag.INamedTag<Block> DRAGON_IMMUNE = makeWrapperTag("dragon_immune");
   public static final ITag.INamedTag<Block> WITHER_IMMUNE = makeWrapperTag("wither_immune");
   public static final ITag.INamedTag<Block> field_232871_ah_ = makeWrapperTag("wither_summon_base_blocks");
   public static final ITag.INamedTag<Block> BEEHIVES = makeWrapperTag("beehives");
   public static final ITag.INamedTag<Block> CROPS = makeWrapperTag("crops");
   public static final ITag.INamedTag<Block> BEE_GROWABLES = makeWrapperTag("bee_growables");
   public static final ITag.INamedTag<Block> PORTALS = makeWrapperTag("portals");
   public static final ITag.INamedTag<Block> field_232872_am_ = makeWrapperTag("fire");
   public static final ITag.INamedTag<Block> field_232873_an_ = makeWrapperTag("nylium");
   public static final ITag.INamedTag<Block> field_232874_ao_ = makeWrapperTag("wart_blocks");
   public static final ITag.INamedTag<Block> field_232875_ap_ = makeWrapperTag("beacon_base_blocks");
   public static final ITag.INamedTag<Block> field_232876_aq_ = makeWrapperTag("soul_speed_blocks");
   public static final ITag.INamedTag<Block> field_232877_ar_ = makeWrapperTag("wall_post_override");
   public static final ITag.INamedTag<Block> field_232878_as_ = makeWrapperTag("climbable");
   public static final ITag.INamedTag<Block> SHULKER_BOXES = makeWrapperTag("shulker_boxes");
   public static final ITag.INamedTag<Block> field_232879_au_ = makeWrapperTag("hoglin_repellents");
   public static final ITag.INamedTag<Block> field_232880_av_ = makeWrapperTag("soul_fire_base_blocks");
   public static final ITag.INamedTag<Block> field_232881_aw_ = makeWrapperTag("strider_warm_blocks");
   public static final ITag.INamedTag<Block> field_232882_ax_ = makeWrapperTag("campfires");
   public static final ITag.INamedTag<Block> field_232883_ay_ = makeWrapperTag("guarded_by_piglins");
   public static final ITag.INamedTag<Block> field_232884_az_ = makeWrapperTag("prevent_mob_spawning_inside");
   public static final ITag.INamedTag<Block> field_232868_aA_ = makeWrapperTag("fence_gates");
   public static final ITag.INamedTag<Block> field_232869_aB_ = makeWrapperTag("unstable_bottom_center");
   public static final ITag.INamedTag<Block> field_242171_aD = makeWrapperTag("mushroom_grow_block");
   public static final ITag.INamedTag<Block> field_241277_aC_ = makeWrapperTag("infiniburn_overworld");
   public static final ITag.INamedTag<Block> field_241278_aD_ = makeWrapperTag("infiniburn_nether");
   public static final ITag.INamedTag<Block> field_241279_aE_ = makeWrapperTag("infiniburn_end");
   public static final ITag.INamedTag<Block> field_242172_aH = makeWrapperTag("base_stone_overworld");
   public static final ITag.INamedTag<Block> field_242173_aI = makeWrapperTag("base_stone_nether");

   public static ITag.INamedTag<Block> makeWrapperTag(String id) {
      return collection.func_232937_a_(id);
   }

   public static net.minecraftforge.common.Tags.IOptionalNamedTag<Block> createOptional(ResourceLocation name) {
       return collection.createOptional(name, () -> null);
   }

   public static ITagCollection<Block> getCollection() {
      return collection.func_232939_b_();
   }

   public static List<? extends ITag.INamedTag<Block>> func_242174_b() {
      return collection.func_241288_c_();
   }
}