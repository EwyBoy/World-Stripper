package net.minecraft.data;

import com.google.gson.JsonElement;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public class ItemModelProvider {
   private final BiConsumer<ResourceLocation, Supplier<JsonElement>> field_240073_a_;

   public ItemModelProvider(BiConsumer<ResourceLocation, Supplier<JsonElement>> p_i232519_1_) {
      this.field_240073_a_ = p_i232519_1_;
   }

   private void func_240076_a_(Item p_240076_1_, ModelsUtil p_240076_2_) {
      p_240076_2_.func_240234_a_(ModelsResourceUtil.func_240219_a_(p_240076_1_), ModelTextures.func_240352_b_(p_240076_1_), this.field_240073_a_);
   }

   private void func_240077_a_(Item p_240077_1_, String p_240077_2_, ModelsUtil p_240077_3_) {
      p_240077_3_.func_240234_a_(ModelsResourceUtil.func_240220_a_(p_240077_1_, p_240077_2_), ModelTextures.func_240376_j_(ModelTextures.func_240344_a_(p_240077_1_, p_240077_2_)), this.field_240073_a_);
   }

   private void func_240075_a_(Item p_240075_1_, Item p_240075_2_, ModelsUtil p_240075_3_) {
      p_240075_3_.func_240234_a_(ModelsResourceUtil.func_240219_a_(p_240075_1_), ModelTextures.func_240352_b_(p_240075_2_), this.field_240073_a_);
   }

   public void func_240074_a_() {
      this.func_240076_a_(Items.ACACIA_BOAT, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.APPLE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.ARMOR_STAND, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.ARROW, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.BAKED_POTATO, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.BAMBOO, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.BEEF, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.BEETROOT, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.BEETROOT_SOUP, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.BIRCH_BOAT, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.BLACK_DYE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.BLAZE_POWDER, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.BLAZE_ROD, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.BLUE_DYE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.BONE_MEAL, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.BOOK, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.BOWL, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.BREAD, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.BRICK, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.BROWN_DYE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.BUCKET, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.CARROT_ON_A_STICK, StockModelShapes.field_240275_aM_);
      this.func_240076_a_(Items.field_234774_pk_, StockModelShapes.field_240275_aM_);
      this.func_240076_a_(Items.CHAINMAIL_BOOTS, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.CHAINMAIL_CHESTPLATE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.CHAINMAIL_HELMET, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.CHAINMAIL_LEGGINGS, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.CHARCOAL, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.CHEST_MINECART, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.CHICKEN, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.CHORUS_FRUIT, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.CLAY_BALL, StockModelShapes.field_240273_aK_);

      for(int i = 1; i < 64; ++i) {
         this.func_240077_a_(Items.CLOCK, String.format("_%02d", i), StockModelShapes.field_240273_aK_);
      }

      this.func_240076_a_(Items.COAL, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.COD_BUCKET, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.COMMAND_BLOCK_MINECART, StockModelShapes.field_240273_aK_);

      for(int j = 0; j < 32; ++j) {
         if (j != 16) {
            this.func_240077_a_(Items.COMPASS, String.format("_%02d", j), StockModelShapes.field_240273_aK_);
         }
      }

      this.func_240076_a_(Items.COOKED_BEEF, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.COOKED_CHICKEN, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.COOKED_COD, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.COOKED_MUTTON, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.COOKED_PORKCHOP, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.COOKED_RABBIT, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.COOKED_SALMON, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.COOKIE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.CREEPER_BANNER_PATTERN, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.CYAN_DYE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.DARK_OAK_BOAT, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.DIAMOND, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.DIAMOND_AXE, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.DIAMOND_BOOTS, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.DIAMOND_CHESTPLATE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.DIAMOND_HELMET, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.DIAMOND_HOE, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.DIAMOND_HORSE_ARMOR, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.DIAMOND_LEGGINGS, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.DIAMOND_PICKAXE, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.DIAMOND_SHOVEL, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.DIAMOND_SWORD, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.DRAGON_BREATH, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.DRIED_KELP, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.EGG, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.EMERALD, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.ENCHANTED_BOOK, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.ENDER_EYE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.ENDER_PEARL, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.END_CRYSTAL, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.EXPERIENCE_BOTTLE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.FERMENTED_SPIDER_EYE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.FIREWORK_ROCKET, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.FIRE_CHARGE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.FLINT, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.FLINT_AND_STEEL, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.FLOWER_BANNER_PATTERN, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.FURNACE_MINECART, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.GHAST_TEAR, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.GLASS_BOTTLE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.GLISTERING_MELON_SLICE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.GLOBE_BANNER_PATTERN, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.GLOWSTONE_DUST, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.GOLDEN_APPLE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.GOLDEN_AXE, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.GOLDEN_BOOTS, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.GOLDEN_CARROT, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.GOLDEN_CHESTPLATE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.GOLDEN_HELMET, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.GOLDEN_HOE, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.GOLDEN_HORSE_ARMOR, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.GOLDEN_LEGGINGS, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.GOLDEN_PICKAXE, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.GOLDEN_SHOVEL, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.GOLDEN_SWORD, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.GOLD_INGOT, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.GOLD_NUGGET, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.GRAY_DYE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.GREEN_DYE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.GUNPOWDER, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.HEART_OF_THE_SEA, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.HONEYCOMB, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.HONEY_BOTTLE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.HOPPER_MINECART, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.INK_SAC, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.IRON_AXE, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.IRON_BOOTS, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.IRON_CHESTPLATE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.IRON_HELMET, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.IRON_HOE, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.IRON_HORSE_ARMOR, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.IRON_INGOT, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.IRON_LEGGINGS, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.IRON_NUGGET, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.IRON_PICKAXE, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.IRON_SHOVEL, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.IRON_SWORD, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.ITEM_FRAME, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.JUNGLE_BOAT, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.KNOWLEDGE_BOOK, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.LAPIS_LAZULI, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.LAVA_BUCKET, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.LEATHER, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.LEATHER_HORSE_ARMOR, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.LIGHT_BLUE_DYE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.LIGHT_GRAY_DYE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.LIME_DYE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MAGENTA_DYE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MAGMA_CREAM, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MAP, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MELON_SLICE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MILK_BUCKET, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MINECART, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MOJANG_BANNER_PATTERN, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MUSHROOM_STEW, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MUSIC_DISC_11, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MUSIC_DISC_13, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MUSIC_DISC_BLOCKS, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MUSIC_DISC_CAT, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MUSIC_DISC_CHIRP, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MUSIC_DISC_FAR, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MUSIC_DISC_MALL, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MUSIC_DISC_MELLOHI, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.field_234775_qK_, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MUSIC_DISC_STAL, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MUSIC_DISC_STRAD, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MUSIC_DISC_WAIT, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MUSIC_DISC_WARD, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.MUTTON, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.NAME_TAG, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.NAUTILUS_SHELL, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.field_234757_kL_, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.field_234766_lv_, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.field_234764_lt_, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.field_234763_ls_, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.field_234758_kU_, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.field_234759_km_, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.field_234765_lu_, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.field_234756_kK_, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.field_234760_kn_, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.field_234755_kJ_, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.field_234754_kI_, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.NETHER_BRICK, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.NETHER_STAR, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.OAK_BOAT, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.ORANGE_DYE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.PAINTING, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.PAPER, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.PHANTOM_MEMBRANE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.field_234776_qX_, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.PINK_DYE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.POISONOUS_POTATO, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.POPPED_CHORUS_FRUIT, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.PORKCHOP, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.PRISMARINE_CRYSTALS, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.PRISMARINE_SHARD, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.PUFFERFISH, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.PUFFERFISH_BUCKET, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.PUMPKIN_PIE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.PURPLE_DYE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.QUARTZ, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.RABBIT, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.RABBIT_FOOT, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.RABBIT_HIDE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.RABBIT_STEW, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.RED_DYE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.ROTTEN_FLESH, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.SADDLE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.SALMON, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.SALMON_BUCKET, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.SCUTE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.SHEARS, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.SHULKER_SHELL, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.SKULL_BANNER_PATTERN, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.SLIME_BALL, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.SNOWBALL, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.SPECTRAL_ARROW, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.SPIDER_EYE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.SPRUCE_BOAT, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.STICK, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.STONE_AXE, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.STONE_HOE, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.STONE_PICKAXE, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.STONE_SHOVEL, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.STONE_SWORD, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.SUGAR, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.SUSPICIOUS_STEW, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.TNT_MINECART, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.TOTEM_OF_UNDYING, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.TRIDENT, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.TROPICAL_FISH, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.TROPICAL_FISH_BUCKET, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.TURTLE_HELMET, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.WATER_BUCKET, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.WHEAT, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.WHITE_DYE, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.WOODEN_AXE, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.WOODEN_HOE, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.WOODEN_PICKAXE, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.WOODEN_SHOVEL, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.WOODEN_SWORD, StockModelShapes.field_240274_aL_);
      this.func_240076_a_(Items.WRITABLE_BOOK, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.WRITTEN_BOOK, StockModelShapes.field_240273_aK_);
      this.func_240076_a_(Items.YELLOW_DYE, StockModelShapes.field_240273_aK_);
      this.func_240075_a_(Items.DEBUG_STICK, Items.STICK, StockModelShapes.field_240274_aL_);
      this.func_240075_a_(Items.ENCHANTED_GOLDEN_APPLE, Items.GOLDEN_APPLE, StockModelShapes.field_240273_aK_);
   }
}