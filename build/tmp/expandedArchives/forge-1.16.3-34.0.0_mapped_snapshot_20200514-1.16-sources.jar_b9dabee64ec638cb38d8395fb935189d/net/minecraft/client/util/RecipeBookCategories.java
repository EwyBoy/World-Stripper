package net.minecraft.client.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum RecipeBookCategories {
   CRAFTING_SEARCH(new ItemStack(Items.COMPASS)),
   CRAFTING_BUILDING_BLOCKS(new ItemStack(Blocks.BRICKS)),
   CRAFTING_REDSTONE(new ItemStack(Items.REDSTONE)),
   CRAFTING_EQUIPMENT(new ItemStack(Items.IRON_AXE), new ItemStack(Items.GOLDEN_SWORD)),
   CRAFTING_MISC(new ItemStack(Items.LAVA_BUCKET), new ItemStack(Items.APPLE)),
   FURNACE_SEARCH(new ItemStack(Items.COMPASS)),
   FURNACE_FOOD(new ItemStack(Items.PORKCHOP)),
   FURNACE_BLOCKS(new ItemStack(Blocks.STONE)),
   FURNACE_MISC(new ItemStack(Items.LAVA_BUCKET), new ItemStack(Items.EMERALD)),
   BLAST_FURNACE_SEARCH(new ItemStack(Items.COMPASS)),
   BLAST_FURNACE_BLOCKS(new ItemStack(Blocks.REDSTONE_ORE)),
   BLAST_FURNACE_MISC(new ItemStack(Items.IRON_SHOVEL), new ItemStack(Items.GOLDEN_LEGGINGS)),
   SMOKER_SEARCH(new ItemStack(Items.COMPASS)),
   SMOKER_FOOD(new ItemStack(Items.PORKCHOP)),
   STONECUTTER(new ItemStack(Items.CHISELED_STONE_BRICKS)),
   SMITHING(new ItemStack(Items.field_234764_lt_)),
   CAMPFIRE(new ItemStack(Items.PORKCHOP)),
   UNKNOWN(new ItemStack(Items.BARRIER));

   public static final List<RecipeBookCategories> field_243231_s = ImmutableList.of(SMOKER_SEARCH, SMOKER_FOOD);
   public static final List<RecipeBookCategories> field_243232_t = ImmutableList.of(BLAST_FURNACE_SEARCH, BLAST_FURNACE_BLOCKS, BLAST_FURNACE_MISC);
   public static final List<RecipeBookCategories> field_243233_u = ImmutableList.of(FURNACE_SEARCH, FURNACE_FOOD, FURNACE_BLOCKS, FURNACE_MISC);
   public static final List<RecipeBookCategories> field_243234_v = ImmutableList.of(CRAFTING_SEARCH, CRAFTING_EQUIPMENT, CRAFTING_BUILDING_BLOCKS, CRAFTING_MISC, CRAFTING_REDSTONE);
   public static final Map<RecipeBookCategories, List<RecipeBookCategories>> field_243235_w = ImmutableMap.of(CRAFTING_SEARCH, ImmutableList.of(CRAFTING_EQUIPMENT, CRAFTING_BUILDING_BLOCKS, CRAFTING_MISC, CRAFTING_REDSTONE), FURNACE_SEARCH, ImmutableList.of(FURNACE_FOOD, FURNACE_BLOCKS, FURNACE_MISC), BLAST_FURNACE_SEARCH, ImmutableList.of(BLAST_FURNACE_BLOCKS, BLAST_FURNACE_MISC), SMOKER_SEARCH, ImmutableList.of(SMOKER_FOOD));
   private final List<ItemStack> icons;

   private RecipeBookCategories(ItemStack... p_i48836_3_) {
      this.icons = ImmutableList.copyOf(p_i48836_3_);
   }

   public static List<RecipeBookCategories> func_243236_a(RecipeBookCategory p_243236_0_) {
      switch(p_243236_0_) {
      case CRAFTING:
         return field_243234_v;
      case FURNACE:
         return field_243233_u;
      case BLAST_FURNACE:
         return field_243232_t;
      case SMOKER:
         return field_243231_s;
      default:
         return ImmutableList.of();
      }
   }

   public List<ItemStack> getIcons() {
      return this.icons;
   }
}