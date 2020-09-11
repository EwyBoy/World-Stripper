package net.minecraft.data;

import java.nio.file.Path;
import java.util.function.Function;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ItemTagsProvider extends TagsProvider<Item> {
   private final Function<ITag.INamedTag<Block>, ITag.Builder> field_240520_d_;

   @Deprecated
   public ItemTagsProvider(DataGenerator p_i232552_1_, BlockTagsProvider p_i232552_2_) {
      super(p_i232552_1_, Registry.ITEM);
      this.field_240520_d_ = p_i232552_2_::func_240525_b_;
   }
   public ItemTagsProvider(DataGenerator p_i232552_1_, BlockTagsProvider p_i232552_2_, String modId, @javax.annotation.Nullable net.minecraftforge.common.data.ExistingFileHelper existingFileHelper) {
      super(p_i232552_1_, Registry.ITEM, modId, existingFileHelper);
      this.field_240520_d_ = p_i232552_2_::func_240525_b_;
   }

   protected void registerTags() {
      this.func_240521_a_(BlockTags.WOOL, ItemTags.WOOL);
      this.func_240521_a_(BlockTags.PLANKS, ItemTags.PLANKS);
      this.func_240521_a_(BlockTags.STONE_BRICKS, ItemTags.STONE_BRICKS);
      this.func_240521_a_(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
      this.func_240521_a_(BlockTags.BUTTONS, ItemTags.BUTTONS);
      this.func_240521_a_(BlockTags.CARPETS, ItemTags.CARPETS);
      this.func_240521_a_(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
      this.func_240521_a_(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
      this.func_240521_a_(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
      this.func_240521_a_(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
      this.func_240521_a_(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
      this.func_240521_a_(BlockTags.DOORS, ItemTags.DOORS);
      this.func_240521_a_(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
      this.func_240521_a_(BlockTags.OAK_LOGS, ItemTags.OAK_LOGS);
      this.func_240521_a_(BlockTags.DARK_OAK_LOGS, ItemTags.DARK_OAK_LOGS);
      this.func_240521_a_(BlockTags.BIRCH_LOGS, ItemTags.BIRCH_LOGS);
      this.func_240521_a_(BlockTags.ACACIA_LOGS, ItemTags.ACACIA_LOGS);
      this.func_240521_a_(BlockTags.SPRUCE_LOGS, ItemTags.SPRUCE_LOGS);
      this.func_240521_a_(BlockTags.JUNGLE_LOGS, ItemTags.JUNGLE_LOGS);
      this.func_240521_a_(BlockTags.field_232888_y_, ItemTags.field_232913_w_);
      this.func_240521_a_(BlockTags.field_232889_z_, ItemTags.field_232914_x_);
      this.func_240521_a_(BlockTags.field_232887_q_, ItemTags.field_232912_o_);
      this.func_240521_a_(BlockTags.LOGS, ItemTags.LOGS);
      this.func_240521_a_(BlockTags.SAND, ItemTags.SAND);
      this.func_240521_a_(BlockTags.SLABS, ItemTags.SLABS);
      this.func_240521_a_(BlockTags.WALLS, ItemTags.WALLS);
      this.func_240521_a_(BlockTags.STAIRS, ItemTags.STAIRS);
      this.func_240521_a_(BlockTags.ANVIL, ItemTags.ANVIL);
      this.func_240521_a_(BlockTags.RAILS, ItemTags.RAILS);
      this.func_240521_a_(BlockTags.LEAVES, ItemTags.LEAVES);
      this.func_240521_a_(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
      this.func_240521_a_(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
      this.func_240521_a_(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
      this.func_240521_a_(BlockTags.BEDS, ItemTags.BEDS);
      this.func_240521_a_(BlockTags.FENCES, ItemTags.FENCES);
      this.func_240521_a_(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
      this.func_240521_a_(BlockTags.FLOWERS, ItemTags.FLOWERS);
      this.func_240521_a_(BlockTags.field_232866_P_, ItemTags.field_232904_O_);
      this.func_240521_a_(BlockTags.field_232880_av_, ItemTags.field_232906_Q_);
      this.func_240522_a_(ItemTags.BANNERS).func_240534_a_(Items.WHITE_BANNER, Items.ORANGE_BANNER, Items.MAGENTA_BANNER, Items.LIGHT_BLUE_BANNER, Items.YELLOW_BANNER, Items.LIME_BANNER, Items.PINK_BANNER, Items.GRAY_BANNER, Items.LIGHT_GRAY_BANNER, Items.CYAN_BANNER, Items.PURPLE_BANNER, Items.BLUE_BANNER, Items.BROWN_BANNER, Items.GREEN_BANNER, Items.RED_BANNER, Items.BLACK_BANNER);
      this.func_240522_a_(ItemTags.BOATS).func_240534_a_(Items.OAK_BOAT, Items.SPRUCE_BOAT, Items.BIRCH_BOAT, Items.JUNGLE_BOAT, Items.ACACIA_BOAT, Items.DARK_OAK_BOAT);
      this.func_240522_a_(ItemTags.FISHES).func_240534_a_(Items.COD, Items.COOKED_COD, Items.SALMON, Items.COOKED_SALMON, Items.PUFFERFISH, Items.TROPICAL_FISH);
      this.func_240521_a_(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
      this.func_240522_a_(ItemTags.field_232907_V_).func_240534_a_(Items.MUSIC_DISC_13, Items.MUSIC_DISC_CAT, Items.MUSIC_DISC_BLOCKS, Items.MUSIC_DISC_CHIRP, Items.MUSIC_DISC_FAR, Items.MUSIC_DISC_MALL, Items.MUSIC_DISC_MELLOHI, Items.MUSIC_DISC_STAL, Items.MUSIC_DISC_STRAD, Items.MUSIC_DISC_WARD, Items.MUSIC_DISC_11, Items.MUSIC_DISC_WAIT);
      this.func_240522_a_(ItemTags.MUSIC_DISCS).func_240531_a_(ItemTags.field_232907_V_).func_240532_a_(Items.field_234775_qK_);
      this.func_240522_a_(ItemTags.COALS).func_240534_a_(Items.COAL, Items.CHARCOAL);
      this.func_240522_a_(ItemTags.ARROWS).func_240534_a_(Items.ARROW, Items.TIPPED_ARROW, Items.SPECTRAL_ARROW);
      this.func_240522_a_(ItemTags.LECTERN_BOOKS).func_240534_a_(Items.WRITTEN_BOOK, Items.WRITABLE_BOOK);
      this.func_240522_a_(ItemTags.field_232908_Z_).func_240534_a_(Items.field_234759_km_, Items.EMERALD, Items.DIAMOND, Items.GOLD_INGOT, Items.IRON_INGOT);
      this.func_240522_a_(ItemTags.field_232902_M_).func_240532_a_(Items.field_234737_dp_).func_240532_a_(Items.field_234790_rk_).func_240532_a_(Items.field_234791_rn_);
      this.func_240522_a_(ItemTags.field_232903_N_).func_240531_a_(ItemTags.field_232904_O_).func_240534_a_(Items.GOLD_BLOCK, Items.field_234780_rD_, Items.LIGHT_WEIGHTED_PRESSURE_PLATE, Items.GOLD_INGOT, Items.BELL, Items.CLOCK, Items.GOLDEN_CARROT, Items.GLISTERING_MELON_SLICE, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS, Items.GOLDEN_HORSE_ARMOR, Items.GOLDEN_SWORD, Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL, Items.GOLDEN_AXE, Items.GOLDEN_HOE);
      this.func_240522_a_(ItemTags.field_232905_P_).func_240534_a_(Items.field_234710_S_, Items.field_234712_aa_, Items.field_234716_aq_, Items.field_234714_ai_, Items.field_234709_R_, Items.field_234711_Z_, Items.field_234715_ap_, Items.field_234713_ah_, Items.field_234798_v_, Items.field_234799_w_, Items.field_234720_bO_, Items.field_234721_bP_, Items.field_234726_cP_, Items.field_234727_cQ_, Items.field_234732_dg_, Items.field_234733_dh_, Items.field_234738_dy_, Items.field_234739_dz_, Items.field_234730_dY_, Items.field_234731_dZ_, Items.field_234746_ex_, Items.field_234747_ey_, Items.field_234743_eZ_, Items.field_234748_fa_, Items.field_234752_jS_, Items.field_234753_jT_, Items.field_234761_lI_, Items.field_234762_lJ_);
      this.func_240522_a_(ItemTags.field_232909_aa_).func_240534_a_(Items.COBBLESTONE, Items.field_234777_rA_);
      this.func_240522_a_(ItemTags.field_242176_ac).func_240534_a_(Items.COBBLESTONE, Items.field_234777_rA_);
   }

   protected void func_240521_a_(ITag.INamedTag<Block> p_240521_1_, ITag.INamedTag<Item> p_240521_2_) {
      ITag.Builder itag$builder = this.func_240525_b_(p_240521_2_);
      ITag.Builder itag$builder1 = this.field_240520_d_.apply(p_240521_1_);
      itag$builder1.func_232962_b_().forEach(itag$builder::func_232954_a_);
   }

   /**
    * Resolves a Path for the location to save the given tag.
    */
   protected Path makePath(ResourceLocation id) {
      return this.generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/tags/items/" + id.getPath() + ".json");
   }

   /**
    * Gets a name for this provider, to use in logging.
    */
   public String getName() {
      return "Item Tags";
   }
}