package net.minecraft.data;

import java.nio.file.Path;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class BlockTagsProvider extends TagsProvider<Block> {
   @Deprecated
   public BlockTagsProvider(DataGenerator generatorIn) {
      super(generatorIn, Registry.BLOCK);
   }
   public BlockTagsProvider(DataGenerator generatorIn, String modId, @javax.annotation.Nullable net.minecraftforge.common.data.ExistingFileHelper existingFileHelper) {
      super(generatorIn, Registry.BLOCK, modId, existingFileHelper);
   }

   protected void registerTags() {
      this.func_240522_a_(BlockTags.WOOL).func_240534_a_(Blocks.WHITE_WOOL, Blocks.ORANGE_WOOL, Blocks.MAGENTA_WOOL, Blocks.LIGHT_BLUE_WOOL, Blocks.YELLOW_WOOL, Blocks.LIME_WOOL, Blocks.PINK_WOOL, Blocks.GRAY_WOOL, Blocks.LIGHT_GRAY_WOOL, Blocks.CYAN_WOOL, Blocks.PURPLE_WOOL, Blocks.BLUE_WOOL, Blocks.BROWN_WOOL, Blocks.GREEN_WOOL, Blocks.RED_WOOL, Blocks.BLACK_WOOL);
      this.func_240522_a_(BlockTags.PLANKS).func_240534_a_(Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS, Blocks.JUNGLE_PLANKS, Blocks.ACACIA_PLANKS, Blocks.DARK_OAK_PLANKS, Blocks.field_235344_mC_, Blocks.field_235345_mD_);
      this.func_240522_a_(BlockTags.STONE_BRICKS).func_240534_a_(Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS);
      this.func_240522_a_(BlockTags.WOODEN_BUTTONS).func_240534_a_(Blocks.OAK_BUTTON, Blocks.SPRUCE_BUTTON, Blocks.BIRCH_BUTTON, Blocks.JUNGLE_BUTTON, Blocks.ACACIA_BUTTON, Blocks.DARK_OAK_BUTTON, Blocks.field_235358_mQ_, Blocks.field_235359_mR_);
      this.func_240522_a_(BlockTags.BUTTONS).func_240531_a_(BlockTags.WOODEN_BUTTONS).func_240532_a_(Blocks.STONE_BUTTON).func_240532_a_(Blocks.field_235391_nE_);
      this.func_240522_a_(BlockTags.CARPETS).func_240534_a_(Blocks.WHITE_CARPET, Blocks.ORANGE_CARPET, Blocks.MAGENTA_CARPET, Blocks.LIGHT_BLUE_CARPET, Blocks.YELLOW_CARPET, Blocks.LIME_CARPET, Blocks.PINK_CARPET, Blocks.GRAY_CARPET, Blocks.LIGHT_GRAY_CARPET, Blocks.CYAN_CARPET, Blocks.PURPLE_CARPET, Blocks.BLUE_CARPET, Blocks.BROWN_CARPET, Blocks.GREEN_CARPET, Blocks.RED_CARPET, Blocks.BLACK_CARPET);
      this.func_240522_a_(BlockTags.WOODEN_DOORS).func_240534_a_(Blocks.OAK_DOOR, Blocks.SPRUCE_DOOR, Blocks.BIRCH_DOOR, Blocks.JUNGLE_DOOR, Blocks.ACACIA_DOOR, Blocks.DARK_OAK_DOOR, Blocks.field_235360_mS_, Blocks.field_235361_mT_);
      this.func_240522_a_(BlockTags.WOODEN_STAIRS).func_240534_a_(Blocks.OAK_STAIRS, Blocks.SPRUCE_STAIRS, Blocks.BIRCH_STAIRS, Blocks.JUNGLE_STAIRS, Blocks.ACACIA_STAIRS, Blocks.DARK_OAK_STAIRS, Blocks.field_235356_mO_, Blocks.field_235357_mP_);
      this.func_240522_a_(BlockTags.WOODEN_SLABS).func_240534_a_(Blocks.OAK_SLAB, Blocks.SPRUCE_SLAB, Blocks.BIRCH_SLAB, Blocks.JUNGLE_SLAB, Blocks.ACACIA_SLAB, Blocks.DARK_OAK_SLAB, Blocks.field_235346_mE_, Blocks.field_235347_mF_);
      this.func_240522_a_(BlockTags.WOODEN_FENCES).func_240534_a_(Blocks.OAK_FENCE, Blocks.ACACIA_FENCE, Blocks.DARK_OAK_FENCE, Blocks.SPRUCE_FENCE, Blocks.BIRCH_FENCE, Blocks.JUNGLE_FENCE, Blocks.field_235350_mI_, Blocks.field_235351_mJ_);
      this.func_240522_a_(BlockTags.DOORS).func_240531_a_(BlockTags.WOODEN_DOORS).func_240532_a_(Blocks.IRON_DOOR);
      this.func_240522_a_(BlockTags.SAPLINGS).func_240534_a_(Blocks.OAK_SAPLING, Blocks.SPRUCE_SAPLING, Blocks.BIRCH_SAPLING, Blocks.JUNGLE_SAPLING, Blocks.ACACIA_SAPLING, Blocks.DARK_OAK_SAPLING);
      this.func_240522_a_(BlockTags.DARK_OAK_LOGS).func_240534_a_(Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_WOOD);
      this.func_240522_a_(BlockTags.OAK_LOGS).func_240534_a_(Blocks.OAK_LOG, Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_LOG, Blocks.STRIPPED_OAK_WOOD);
      this.func_240522_a_(BlockTags.ACACIA_LOGS).func_240534_a_(Blocks.ACACIA_LOG, Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_LOG, Blocks.STRIPPED_ACACIA_WOOD);
      this.func_240522_a_(BlockTags.BIRCH_LOGS).func_240534_a_(Blocks.BIRCH_LOG, Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_LOG, Blocks.STRIPPED_BIRCH_WOOD);
      this.func_240522_a_(BlockTags.JUNGLE_LOGS).func_240534_a_(Blocks.JUNGLE_LOG, Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_WOOD);
      this.func_240522_a_(BlockTags.SPRUCE_LOGS).func_240534_a_(Blocks.SPRUCE_LOG, Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_WOOD);
      this.func_240522_a_(BlockTags.field_232888_y_).func_240534_a_(Blocks.field_235377_mq_, Blocks.field_235378_mr_, Blocks.field_235379_ms_, Blocks.field_235380_mt_);
      this.func_240522_a_(BlockTags.field_232889_z_).func_240534_a_(Blocks.field_235368_mh_, Blocks.field_235369_mi_, Blocks.field_235370_mj_, Blocks.field_235371_mk_);
      this.func_240522_a_(BlockTags.field_232887_q_).func_240531_a_(BlockTags.DARK_OAK_LOGS).func_240531_a_(BlockTags.OAK_LOGS).func_240531_a_(BlockTags.ACACIA_LOGS).func_240531_a_(BlockTags.BIRCH_LOGS).func_240531_a_(BlockTags.JUNGLE_LOGS).func_240531_a_(BlockTags.SPRUCE_LOGS);
      this.func_240522_a_(BlockTags.LOGS).func_240531_a_(BlockTags.field_232887_q_).func_240531_a_(BlockTags.field_232888_y_).func_240531_a_(BlockTags.field_232889_z_);
      this.func_240522_a_(BlockTags.ANVIL).func_240534_a_(Blocks.ANVIL, Blocks.CHIPPED_ANVIL, Blocks.DAMAGED_ANVIL);
      this.func_240522_a_(BlockTags.SMALL_FLOWERS).func_240534_a_(Blocks.DANDELION, Blocks.POPPY, Blocks.BLUE_ORCHID, Blocks.ALLIUM, Blocks.AZURE_BLUET, Blocks.RED_TULIP, Blocks.ORANGE_TULIP, Blocks.WHITE_TULIP, Blocks.PINK_TULIP, Blocks.OXEYE_DAISY, Blocks.CORNFLOWER, Blocks.LILY_OF_THE_VALLEY, Blocks.WITHER_ROSE);
      this.func_240522_a_(BlockTags.ENDERMAN_HOLDABLE).func_240531_a_(BlockTags.SMALL_FLOWERS).func_240534_a_(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.SAND, Blocks.RED_SAND, Blocks.GRAVEL, Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM, Blocks.TNT, Blocks.CACTUS, Blocks.CLAY, Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, Blocks.MELON, Blocks.MYCELIUM, Blocks.field_235382_mv_, Blocks.field_235381_mu_, Blocks.field_235343_mB_, Blocks.field_235373_mm_, Blocks.field_235372_ml_, Blocks.field_235375_mo_);
      this.func_240522_a_(BlockTags.FLOWER_POTS).func_240534_a_(Blocks.FLOWER_POT, Blocks.POTTED_POPPY, Blocks.POTTED_BLUE_ORCHID, Blocks.POTTED_ALLIUM, Blocks.POTTED_AZURE_BLUET, Blocks.POTTED_RED_TULIP, Blocks.POTTED_ORANGE_TULIP, Blocks.POTTED_WHITE_TULIP, Blocks.POTTED_PINK_TULIP, Blocks.POTTED_OXEYE_DAISY, Blocks.POTTED_DANDELION, Blocks.POTTED_OAK_SAPLING, Blocks.POTTED_SPRUCE_SAPLING, Blocks.POTTED_BIRCH_SAPLING, Blocks.POTTED_JUNGLE_SAPLING, Blocks.POTTED_ACACIA_SAPLING, Blocks.POTTED_DARK_OAK_SAPLING, Blocks.POTTED_RED_MUSHROOM, Blocks.POTTED_BROWN_MUSHROOM, Blocks.POTTED_DEAD_BUSH, Blocks.POTTED_FERN, Blocks.POTTED_CACTUS, Blocks.POTTED_CORNFLOWER, Blocks.POTTED_LILY_OF_THE_VALLEY, Blocks.POTTED_WITHER_ROSE, Blocks.POTTED_BAMBOO, Blocks.field_235401_nk_, Blocks.field_235402_nl_, Blocks.field_235403_nm_, Blocks.field_235404_nn_);
      this.func_240522_a_(BlockTags.BANNERS).func_240534_a_(Blocks.WHITE_BANNER, Blocks.ORANGE_BANNER, Blocks.MAGENTA_BANNER, Blocks.LIGHT_BLUE_BANNER, Blocks.YELLOW_BANNER, Blocks.LIME_BANNER, Blocks.PINK_BANNER, Blocks.GRAY_BANNER, Blocks.LIGHT_GRAY_BANNER, Blocks.CYAN_BANNER, Blocks.PURPLE_BANNER, Blocks.BLUE_BANNER, Blocks.BROWN_BANNER, Blocks.GREEN_BANNER, Blocks.RED_BANNER, Blocks.BLACK_BANNER, Blocks.WHITE_WALL_BANNER, Blocks.ORANGE_WALL_BANNER, Blocks.MAGENTA_WALL_BANNER, Blocks.LIGHT_BLUE_WALL_BANNER, Blocks.YELLOW_WALL_BANNER, Blocks.LIME_WALL_BANNER, Blocks.PINK_WALL_BANNER, Blocks.GRAY_WALL_BANNER, Blocks.LIGHT_GRAY_WALL_BANNER, Blocks.CYAN_WALL_BANNER, Blocks.PURPLE_WALL_BANNER, Blocks.BLUE_WALL_BANNER, Blocks.BROWN_WALL_BANNER, Blocks.GREEN_WALL_BANNER, Blocks.RED_WALL_BANNER, Blocks.BLACK_WALL_BANNER);
      this.func_240522_a_(BlockTags.WOODEN_PRESSURE_PLATES).func_240534_a_(Blocks.OAK_PRESSURE_PLATE, Blocks.SPRUCE_PRESSURE_PLATE, Blocks.BIRCH_PRESSURE_PLATE, Blocks.JUNGLE_PRESSURE_PLATE, Blocks.ACACIA_PRESSURE_PLATE, Blocks.DARK_OAK_PRESSURE_PLATE, Blocks.field_235348_mG_, Blocks.field_235349_mH_);
      this.func_240522_a_(BlockTags.field_232886_m_).func_240534_a_(Blocks.STONE_PRESSURE_PLATE, Blocks.field_235390_nD_);
      this.func_240522_a_(BlockTags.field_232885_k_).func_240534_a_(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE).func_240531_a_(BlockTags.WOODEN_PRESSURE_PLATES).func_240531_a_(BlockTags.field_232886_m_);
      this.func_240522_a_(BlockTags.STAIRS).func_240531_a_(BlockTags.WOODEN_STAIRS).func_240534_a_(Blocks.COBBLESTONE_STAIRS, Blocks.SANDSTONE_STAIRS, Blocks.NETHER_BRICK_STAIRS, Blocks.STONE_BRICK_STAIRS, Blocks.BRICK_STAIRS, Blocks.PURPUR_STAIRS, Blocks.QUARTZ_STAIRS, Blocks.RED_SANDSTONE_STAIRS, Blocks.PRISMARINE_BRICK_STAIRS, Blocks.PRISMARINE_STAIRS, Blocks.DARK_PRISMARINE_STAIRS, Blocks.POLISHED_GRANITE_STAIRS, Blocks.SMOOTH_RED_SANDSTONE_STAIRS, Blocks.MOSSY_STONE_BRICK_STAIRS, Blocks.POLISHED_DIORITE_STAIRS, Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.END_STONE_BRICK_STAIRS, Blocks.STONE_STAIRS, Blocks.SMOOTH_SANDSTONE_STAIRS, Blocks.SMOOTH_QUARTZ_STAIRS, Blocks.GRANITE_STAIRS, Blocks.ANDESITE_STAIRS, Blocks.RED_NETHER_BRICK_STAIRS, Blocks.POLISHED_ANDESITE_STAIRS, Blocks.DIORITE_STAIRS, Blocks.field_235407_nq_, Blocks.field_235415_ny_, Blocks.field_235388_nB_);
      this.func_240522_a_(BlockTags.SLABS).func_240531_a_(BlockTags.WOODEN_SLABS).func_240534_a_(Blocks.STONE_SLAB, Blocks.SMOOTH_STONE_SLAB, Blocks.STONE_BRICK_SLAB, Blocks.SANDSTONE_SLAB, Blocks.PURPUR_SLAB, Blocks.QUARTZ_SLAB, Blocks.RED_SANDSTONE_SLAB, Blocks.BRICK_SLAB, Blocks.COBBLESTONE_SLAB, Blocks.NETHER_BRICK_SLAB, Blocks.PETRIFIED_OAK_SLAB, Blocks.PRISMARINE_SLAB, Blocks.PRISMARINE_BRICK_SLAB, Blocks.DARK_PRISMARINE_SLAB, Blocks.POLISHED_GRANITE_SLAB, Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.POLISHED_DIORITE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.END_STONE_BRICK_SLAB, Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.GRANITE_SLAB, Blocks.ANDESITE_SLAB, Blocks.RED_NETHER_BRICK_SLAB, Blocks.POLISHED_ANDESITE_SLAB, Blocks.DIORITE_SLAB, Blocks.CUT_SANDSTONE_SLAB, Blocks.CUT_RED_SANDSTONE_SLAB, Blocks.field_235409_ns_, Blocks.field_235414_nx_, Blocks.field_235389_nC_);
      this.func_240522_a_(BlockTags.WALLS).func_240534_a_(Blocks.COBBLESTONE_WALL, Blocks.MOSSY_COBBLESTONE_WALL, Blocks.BRICK_WALL, Blocks.PRISMARINE_WALL, Blocks.RED_SANDSTONE_WALL, Blocks.MOSSY_STONE_BRICK_WALL, Blocks.GRANITE_WALL, Blocks.STONE_BRICK_WALL, Blocks.NETHER_BRICK_WALL, Blocks.ANDESITE_WALL, Blocks.RED_NETHER_BRICK_WALL, Blocks.SANDSTONE_WALL, Blocks.END_STONE_BRICK_WALL, Blocks.DIORITE_WALL, Blocks.field_235408_nr_, Blocks.field_235416_nz_, Blocks.field_235392_nF_);
      this.func_240522_a_(BlockTags.CORAL_PLANTS).func_240534_a_(Blocks.TUBE_CORAL, Blocks.BRAIN_CORAL, Blocks.BUBBLE_CORAL, Blocks.FIRE_CORAL, Blocks.HORN_CORAL);
      this.func_240522_a_(BlockTags.CORALS).func_240531_a_(BlockTags.CORAL_PLANTS).func_240534_a_(Blocks.TUBE_CORAL_FAN, Blocks.BRAIN_CORAL_FAN, Blocks.BUBBLE_CORAL_FAN, Blocks.FIRE_CORAL_FAN, Blocks.HORN_CORAL_FAN);
      this.func_240522_a_(BlockTags.WALL_CORALS).func_240534_a_(Blocks.TUBE_CORAL_WALL_FAN, Blocks.BRAIN_CORAL_WALL_FAN, Blocks.BUBBLE_CORAL_WALL_FAN, Blocks.FIRE_CORAL_WALL_FAN, Blocks.HORN_CORAL_WALL_FAN);
      this.func_240522_a_(BlockTags.SAND).func_240534_a_(Blocks.SAND, Blocks.RED_SAND);
      this.func_240522_a_(BlockTags.RAILS).func_240534_a_(Blocks.RAIL, Blocks.POWERED_RAIL, Blocks.DETECTOR_RAIL, Blocks.ACTIVATOR_RAIL);
      this.func_240522_a_(BlockTags.CORAL_BLOCKS).func_240534_a_(Blocks.TUBE_CORAL_BLOCK, Blocks.BRAIN_CORAL_BLOCK, Blocks.BUBBLE_CORAL_BLOCK, Blocks.FIRE_CORAL_BLOCK, Blocks.HORN_CORAL_BLOCK);
      this.func_240522_a_(BlockTags.ICE).func_240534_a_(Blocks.ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE, Blocks.FROSTED_ICE);
      this.func_240522_a_(BlockTags.VALID_SPAWN).func_240534_a_(Blocks.GRASS_BLOCK, Blocks.PODZOL);
      this.func_240522_a_(BlockTags.LEAVES).func_240534_a_(Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.ACACIA_LEAVES, Blocks.BIRCH_LEAVES);
      this.func_240522_a_(BlockTags.IMPERMEABLE).func_240534_a_(Blocks.GLASS, Blocks.WHITE_STAINED_GLASS, Blocks.ORANGE_STAINED_GLASS, Blocks.MAGENTA_STAINED_GLASS, Blocks.LIGHT_BLUE_STAINED_GLASS, Blocks.YELLOW_STAINED_GLASS, Blocks.LIME_STAINED_GLASS, Blocks.PINK_STAINED_GLASS, Blocks.GRAY_STAINED_GLASS, Blocks.LIGHT_GRAY_STAINED_GLASS, Blocks.CYAN_STAINED_GLASS, Blocks.PURPLE_STAINED_GLASS, Blocks.BLUE_STAINED_GLASS, Blocks.BROWN_STAINED_GLASS, Blocks.GREEN_STAINED_GLASS, Blocks.RED_STAINED_GLASS, Blocks.BLACK_STAINED_GLASS);
      this.func_240522_a_(BlockTags.WOODEN_TRAPDOORS).func_240534_a_(Blocks.ACACIA_TRAPDOOR, Blocks.BIRCH_TRAPDOOR, Blocks.DARK_OAK_TRAPDOOR, Blocks.JUNGLE_TRAPDOOR, Blocks.OAK_TRAPDOOR, Blocks.SPRUCE_TRAPDOOR, Blocks.field_235352_mK_, Blocks.field_235353_mL_);
      this.func_240522_a_(BlockTags.TRAPDOORS).func_240531_a_(BlockTags.WOODEN_TRAPDOORS).func_240532_a_(Blocks.IRON_TRAPDOOR);
      this.func_240522_a_(BlockTags.UNDERWATER_BONEMEALS).func_240532_a_(Blocks.SEAGRASS).func_240531_a_(BlockTags.CORALS).func_240531_a_(BlockTags.WALL_CORALS);
      this.func_240522_a_(BlockTags.BAMBOO_PLANTABLE_ON).func_240531_a_(BlockTags.SAND).func_240534_a_(Blocks.BAMBOO, Blocks.BAMBOO_SAPLING, Blocks.GRAVEL, Blocks.DIRT, Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.MYCELIUM);
      this.func_240522_a_(BlockTags.STANDING_SIGNS).func_240534_a_(Blocks.OAK_SIGN, Blocks.SPRUCE_SIGN, Blocks.BIRCH_SIGN, Blocks.ACACIA_SIGN, Blocks.JUNGLE_SIGN, Blocks.DARK_OAK_SIGN, Blocks.field_235362_mU_, Blocks.field_235363_mV_);
      this.func_240522_a_(BlockTags.WALL_SIGNS).func_240534_a_(Blocks.OAK_WALL_SIGN, Blocks.SPRUCE_WALL_SIGN, Blocks.BIRCH_WALL_SIGN, Blocks.ACACIA_WALL_SIGN, Blocks.JUNGLE_WALL_SIGN, Blocks.DARK_OAK_WALL_SIGN, Blocks.field_235364_mW_, Blocks.field_235365_mX_);
      this.func_240522_a_(BlockTags.SIGNS).func_240531_a_(BlockTags.STANDING_SIGNS).func_240531_a_(BlockTags.WALL_SIGNS);
      this.func_240522_a_(BlockTags.BEDS).func_240534_a_(Blocks.RED_BED, Blocks.BLACK_BED, Blocks.BLUE_BED, Blocks.BROWN_BED, Blocks.CYAN_BED, Blocks.GRAY_BED, Blocks.GREEN_BED, Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_GRAY_BED, Blocks.LIME_BED, Blocks.MAGENTA_BED, Blocks.ORANGE_BED, Blocks.PINK_BED, Blocks.PURPLE_BED, Blocks.WHITE_BED, Blocks.YELLOW_BED);
      this.func_240522_a_(BlockTags.FENCES).func_240531_a_(BlockTags.WOODEN_FENCES).func_240532_a_(Blocks.NETHER_BRICK_FENCE);
      this.func_240522_a_(BlockTags.DRAGON_IMMUNE).func_240534_a_(Blocks.BARRIER, Blocks.BEDROCK, Blocks.END_PORTAL, Blocks.END_PORTAL_FRAME, Blocks.END_GATEWAY, Blocks.COMMAND_BLOCK, Blocks.REPEATING_COMMAND_BLOCK, Blocks.CHAIN_COMMAND_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.JIGSAW, Blocks.MOVING_PISTON, Blocks.OBSIDIAN, Blocks.field_235399_ni_, Blocks.END_STONE, Blocks.IRON_BARS, Blocks.field_235400_nj_);
      this.func_240522_a_(BlockTags.WITHER_IMMUNE).func_240534_a_(Blocks.BARRIER, Blocks.BEDROCK, Blocks.END_PORTAL, Blocks.END_PORTAL_FRAME, Blocks.END_GATEWAY, Blocks.COMMAND_BLOCK, Blocks.REPEATING_COMMAND_BLOCK, Blocks.CHAIN_COMMAND_BLOCK, Blocks.STRUCTURE_BLOCK, Blocks.JIGSAW, Blocks.MOVING_PISTON);
      this.func_240522_a_(BlockTags.field_232871_ah_).func_240534_a_(Blocks.SOUL_SAND, Blocks.field_235336_cN_);
      this.func_240522_a_(BlockTags.TALL_FLOWERS).func_240534_a_(Blocks.SUNFLOWER, Blocks.LILAC, Blocks.PEONY, Blocks.ROSE_BUSH);
      this.func_240522_a_(BlockTags.FLOWERS).func_240531_a_(BlockTags.SMALL_FLOWERS).func_240531_a_(BlockTags.TALL_FLOWERS);
      this.func_240522_a_(BlockTags.BEEHIVES).func_240534_a_(Blocks.BEE_NEST, Blocks.BEEHIVE);
      this.func_240522_a_(BlockTags.CROPS).func_240534_a_(Blocks.BEETROOTS, Blocks.CARROTS, Blocks.POTATOES, Blocks.WHEAT, Blocks.MELON_STEM, Blocks.PUMPKIN_STEM);
      this.func_240522_a_(BlockTags.BEE_GROWABLES).func_240531_a_(BlockTags.CROPS).func_240532_a_(Blocks.SWEET_BERRY_BUSH);
      this.func_240522_a_(BlockTags.SHULKER_BOXES).func_240534_a_(Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX);
      this.func_240522_a_(BlockTags.PORTALS).func_240534_a_(Blocks.NETHER_PORTAL, Blocks.END_PORTAL, Blocks.END_GATEWAY);
      this.func_240522_a_(BlockTags.field_232872_am_).func_240534_a_(Blocks.FIRE, Blocks.field_235335_bO_);
      this.func_240522_a_(BlockTags.field_232873_an_).func_240534_a_(Blocks.field_235381_mu_, Blocks.field_235372_ml_);
      this.func_240522_a_(BlockTags.field_232874_ao_).func_240534_a_(Blocks.NETHER_WART_BLOCK, Blocks.field_235374_mn_);
      this.func_240522_a_(BlockTags.field_232875_ap_).func_240534_a_(Blocks.field_235397_ng_, Blocks.EMERALD_BLOCK, Blocks.DIAMOND_BLOCK, Blocks.GOLD_BLOCK, Blocks.IRON_BLOCK);
      this.func_240522_a_(BlockTags.field_232876_aq_).func_240534_a_(Blocks.SOUL_SAND, Blocks.field_235336_cN_);
      this.func_240522_a_(BlockTags.field_232877_ar_).func_240534_a_(Blocks.TORCH, Blocks.field_235339_cQ_, Blocks.REDSTONE_TORCH, Blocks.TRIPWIRE).func_240531_a_(BlockTags.SIGNS).func_240531_a_(BlockTags.BANNERS).func_240531_a_(BlockTags.field_232885_k_);
      this.func_240522_a_(BlockTags.field_232878_as_).func_240534_a_(Blocks.LADDER, Blocks.VINE, Blocks.SCAFFOLDING, Blocks.field_235384_mx_, Blocks.field_235385_my_, Blocks.field_235386_mz_, Blocks.field_235342_mA_);
      this.func_240522_a_(BlockTags.field_232865_O_).func_240532_a_(Blocks.field_235335_bO_).func_240532_a_(Blocks.field_235339_cQ_).func_240532_a_(Blocks.field_235366_md_).func_240532_a_(Blocks.field_235340_cR_).func_240532_a_(Blocks.field_235367_mf_);
      this.func_240522_a_(BlockTags.field_232879_au_).func_240532_a_(Blocks.field_235373_mm_).func_240532_a_(Blocks.field_235402_nl_).func_240532_a_(Blocks.NETHER_PORTAL).func_240532_a_(Blocks.field_235400_nj_);
      this.func_240522_a_(BlockTags.field_232866_P_).func_240534_a_(Blocks.GOLD_ORE, Blocks.field_235334_I_);
      this.func_240522_a_(BlockTags.field_232880_av_).func_240534_a_(Blocks.SOUL_SAND, Blocks.field_235336_cN_);
      this.func_240522_a_(BlockTags.field_232867_Q_).func_240534_a_(Blocks.field_235368_mh_, Blocks.field_235369_mi_, Blocks.field_235370_mj_, Blocks.field_235371_mk_, Blocks.field_235377_mq_, Blocks.field_235378_mr_, Blocks.field_235379_ms_, Blocks.field_235380_mt_, Blocks.field_235344_mC_, Blocks.field_235345_mD_, Blocks.field_235346_mE_, Blocks.field_235347_mF_, Blocks.field_235348_mG_, Blocks.field_235349_mH_, Blocks.field_235350_mI_, Blocks.field_235351_mJ_, Blocks.field_235352_mK_, Blocks.field_235353_mL_, Blocks.field_235354_mM_, Blocks.field_235355_mN_, Blocks.field_235356_mO_, Blocks.field_235357_mP_, Blocks.field_235358_mQ_, Blocks.field_235359_mR_, Blocks.field_235360_mS_, Blocks.field_235361_mT_, Blocks.field_235362_mU_, Blocks.field_235363_mV_, Blocks.field_235364_mW_, Blocks.field_235365_mX_);
      this.func_240522_a_(BlockTags.field_232881_aw_).func_240532_a_(Blocks.LAVA);
      this.func_240522_a_(BlockTags.field_232882_ax_).func_240534_a_(Blocks.CAMPFIRE, Blocks.field_235367_mf_);
      this.func_240522_a_(BlockTags.field_232883_ay_).func_240534_a_(Blocks.GOLD_BLOCK, Blocks.BARREL, Blocks.CHEST, Blocks.ENDER_CHEST, Blocks.field_235387_nA_, Blocks.TRAPPED_CHEST).func_240531_a_(BlockTags.SHULKER_BOXES).func_240531_a_(BlockTags.field_232866_P_);
      this.func_240522_a_(BlockTags.field_232884_az_).func_240531_a_(BlockTags.RAILS);
      this.func_240522_a_(BlockTags.field_232868_aA_).func_240534_a_(Blocks.ACACIA_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.field_235354_mM_, Blocks.field_235355_mN_);
      this.func_240522_a_(BlockTags.field_232869_aB_).func_240531_a_(BlockTags.field_232868_aA_);
      this.func_240522_a_(BlockTags.field_242171_aD).func_240532_a_(Blocks.MYCELIUM).func_240532_a_(Blocks.PODZOL).func_240532_a_(Blocks.field_235381_mu_).func_240532_a_(Blocks.field_235372_ml_);
      this.func_240522_a_(BlockTags.field_241277_aC_).func_240534_a_(Blocks.NETHERRACK, Blocks.MAGMA_BLOCK);
      this.func_240522_a_(BlockTags.field_241278_aD_).func_240531_a_(BlockTags.field_241277_aC_);
      this.func_240522_a_(BlockTags.field_241279_aE_).func_240531_a_(BlockTags.field_241277_aC_).func_240532_a_(Blocks.BEDROCK);
      this.func_240522_a_(BlockTags.field_242172_aH).func_240532_a_(Blocks.STONE).func_240532_a_(Blocks.GRANITE).func_240532_a_(Blocks.DIORITE).func_240532_a_(Blocks.ANDESITE);
      this.func_240522_a_(BlockTags.field_242173_aI).func_240532_a_(Blocks.NETHERRACK).func_240532_a_(Blocks.field_235337_cO_).func_240532_a_(Blocks.field_235406_np_);
   }

   /**
    * Resolves a Path for the location to save the given tag.
    */
   protected Path makePath(ResourceLocation id) {
      return this.generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/tags/blocks/" + id.getPath() + ".json");
   }

   /**
    * Gets a name for this provider, to use in logging.
    */
   public String getName() {
      return "Block Tags";
   }
}