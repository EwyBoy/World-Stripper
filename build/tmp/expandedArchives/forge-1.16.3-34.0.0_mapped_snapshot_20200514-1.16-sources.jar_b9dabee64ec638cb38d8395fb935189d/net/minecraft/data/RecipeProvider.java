package net.minecraft.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.criterion.EnterBlockTrigger;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ImpossibleTrigger;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecipeProvider implements IDataProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
   protected final DataGenerator generator;

   public RecipeProvider(DataGenerator generatorIn) {
      this.generator = generatorIn;
   }

   /**
    * Performs this provider's action.
    */
   public void act(DirectoryCache cache) throws IOException {
      Path path = this.generator.getOutputFolder();
      Set<ResourceLocation> set = Sets.newHashSet();
      registerRecipes((p_200410_3_) -> {
         if (!set.add(p_200410_3_.getID())) {
            throw new IllegalStateException("Duplicate recipe " + p_200410_3_.getID());
         } else {
            saveRecipe(cache, p_200410_3_.getRecipeJson(), path.resolve("data/" + p_200410_3_.getID().getNamespace() + "/recipes/" + p_200410_3_.getID().getPath() + ".json"));
            JsonObject jsonobject = p_200410_3_.getAdvancementJson();
            if (jsonobject != null) {
               saveRecipeAdvancement(cache, jsonobject, path.resolve("data/" + p_200410_3_.getID().getNamespace() + "/advancements/" + p_200410_3_.getAdvancementID().getPath() + ".json"));
            }

         }
      });
      if (this.getClass() == RecipeProvider.class) //Forge: Subclasses don't need this.
      saveRecipeAdvancement(cache, Advancement.Builder.builder().withCriterion("impossible", new ImpossibleTrigger.Instance()).serialize(), path.resolve("data/minecraft/advancements/recipes/root.json"));
   }

   /**
    * Saves a recipe to a file.
    */
   private static void saveRecipe(DirectoryCache p_208311_0_, JsonObject cache, Path recipeJson) {
      try {
         String s = GSON.toJson((JsonElement)cache);
         String s1 = HASH_FUNCTION.hashUnencodedChars(s).toString();
         if (!Objects.equals(p_208311_0_.getPreviousHash(recipeJson), s1) || !Files.exists(recipeJson)) {
            Files.createDirectories(recipeJson.getParent());

            try (BufferedWriter bufferedwriter = Files.newBufferedWriter(recipeJson)) {
               bufferedwriter.write(s);
            }
         }

         p_208311_0_.recordHash(recipeJson, s1);
      } catch (IOException ioexception) {
         LOGGER.error("Couldn't save recipe {}", recipeJson, ioexception);
      }

   }

   /**
    * Saves an advancement to a file.
    */
   protected void saveRecipeAdvancement(DirectoryCache p_208310_0_, JsonObject cache, Path advancementJson) {
      try {
         String s = GSON.toJson((JsonElement)cache);
         String s1 = HASH_FUNCTION.hashUnencodedChars(s).toString();
         if (!Objects.equals(p_208310_0_.getPreviousHash(advancementJson), s1) || !Files.exists(advancementJson)) {
            Files.createDirectories(advancementJson.getParent());

            try (BufferedWriter bufferedwriter = Files.newBufferedWriter(advancementJson)) {
               bufferedwriter.write(s);
            }
         }

         p_208310_0_.recordHash(advancementJson, s1);
      } catch (IOException ioexception) {
         LOGGER.error("Couldn't save recipe advancement {}", advancementJson, ioexception);
      }

   }

   /**
    * Registers all recipes to the given consumer.
    */
   protected void registerRecipes(Consumer<IFinishedRecipe> p_200404_0_) {
      func_240470_a_(p_200404_0_, Blocks.ACACIA_PLANKS, ItemTags.ACACIA_LOGS);
      func_240472_b_(p_200404_0_, Blocks.BIRCH_PLANKS, ItemTags.BIRCH_LOGS);
      func_240472_b_(p_200404_0_, Blocks.field_235344_mC_, ItemTags.field_232913_w_);
      func_240470_a_(p_200404_0_, Blocks.DARK_OAK_PLANKS, ItemTags.DARK_OAK_LOGS);
      func_240472_b_(p_200404_0_, Blocks.JUNGLE_PLANKS, ItemTags.JUNGLE_LOGS);
      func_240472_b_(p_200404_0_, Blocks.OAK_PLANKS, ItemTags.OAK_LOGS);
      func_240472_b_(p_200404_0_, Blocks.SPRUCE_PLANKS, ItemTags.SPRUCE_LOGS);
      func_240472_b_(p_200404_0_, Blocks.field_235345_mD_, ItemTags.field_232914_x_);
      func_240471_a_(p_200404_0_, Blocks.ACACIA_WOOD, Blocks.ACACIA_LOG);
      func_240471_a_(p_200404_0_, Blocks.BIRCH_WOOD, Blocks.BIRCH_LOG);
      func_240471_a_(p_200404_0_, Blocks.DARK_OAK_WOOD, Blocks.DARK_OAK_LOG);
      func_240471_a_(p_200404_0_, Blocks.JUNGLE_WOOD, Blocks.JUNGLE_LOG);
      func_240471_a_(p_200404_0_, Blocks.OAK_WOOD, Blocks.OAK_LOG);
      func_240471_a_(p_200404_0_, Blocks.SPRUCE_WOOD, Blocks.SPRUCE_LOG);
      func_240471_a_(p_200404_0_, Blocks.field_235379_ms_, Blocks.field_235377_mq_);
      func_240471_a_(p_200404_0_, Blocks.field_235370_mj_, Blocks.field_235368_mh_);
      func_240471_a_(p_200404_0_, Blocks.STRIPPED_ACACIA_WOOD, Blocks.STRIPPED_ACACIA_LOG);
      func_240471_a_(p_200404_0_, Blocks.STRIPPED_BIRCH_WOOD, Blocks.STRIPPED_BIRCH_LOG);
      func_240471_a_(p_200404_0_, Blocks.STRIPPED_DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_LOG);
      func_240471_a_(p_200404_0_, Blocks.STRIPPED_JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_LOG);
      func_240471_a_(p_200404_0_, Blocks.STRIPPED_OAK_WOOD, Blocks.STRIPPED_OAK_LOG);
      func_240471_a_(p_200404_0_, Blocks.STRIPPED_SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_LOG);
      func_240471_a_(p_200404_0_, Blocks.field_235380_mt_, Blocks.field_235378_mr_);
      func_240471_a_(p_200404_0_, Blocks.field_235371_mk_, Blocks.field_235369_mi_);
      func_240473_b_(p_200404_0_, Items.ACACIA_BOAT, Blocks.ACACIA_PLANKS);
      func_240473_b_(p_200404_0_, Items.BIRCH_BOAT, Blocks.BIRCH_PLANKS);
      func_240473_b_(p_200404_0_, Items.DARK_OAK_BOAT, Blocks.DARK_OAK_PLANKS);
      func_240473_b_(p_200404_0_, Items.JUNGLE_BOAT, Blocks.JUNGLE_PLANKS);
      func_240473_b_(p_200404_0_, Items.OAK_BOAT, Blocks.OAK_PLANKS);
      func_240473_b_(p_200404_0_, Items.SPRUCE_BOAT, Blocks.SPRUCE_PLANKS);
      func_240474_c_(p_200404_0_, Blocks.ACACIA_BUTTON, Blocks.ACACIA_PLANKS);
      func_240475_d_(p_200404_0_, Blocks.ACACIA_DOOR, Blocks.ACACIA_PLANKS);
      func_240476_e_(p_200404_0_, Blocks.ACACIA_FENCE, Blocks.ACACIA_PLANKS);
      func_240477_f_(p_200404_0_, Blocks.ACACIA_FENCE_GATE, Blocks.ACACIA_PLANKS);
      func_240478_g_(p_200404_0_, Blocks.ACACIA_PRESSURE_PLATE, Blocks.ACACIA_PLANKS);
      func_240479_h_(p_200404_0_, Blocks.ACACIA_SLAB, Blocks.ACACIA_PLANKS);
      func_240480_i_(p_200404_0_, Blocks.ACACIA_STAIRS, Blocks.ACACIA_PLANKS);
      func_240481_j_(p_200404_0_, Blocks.ACACIA_TRAPDOOR, Blocks.ACACIA_PLANKS);
      func_240482_k_(p_200404_0_, Blocks.ACACIA_SIGN, Blocks.ACACIA_PLANKS);
      func_240474_c_(p_200404_0_, Blocks.BIRCH_BUTTON, Blocks.BIRCH_PLANKS);
      func_240475_d_(p_200404_0_, Blocks.BIRCH_DOOR, Blocks.BIRCH_PLANKS);
      func_240476_e_(p_200404_0_, Blocks.BIRCH_FENCE, Blocks.BIRCH_PLANKS);
      func_240477_f_(p_200404_0_, Blocks.BIRCH_FENCE_GATE, Blocks.BIRCH_PLANKS);
      func_240478_g_(p_200404_0_, Blocks.BIRCH_PRESSURE_PLATE, Blocks.BIRCH_PLANKS);
      func_240479_h_(p_200404_0_, Blocks.BIRCH_SLAB, Blocks.BIRCH_PLANKS);
      func_240480_i_(p_200404_0_, Blocks.BIRCH_STAIRS, Blocks.BIRCH_PLANKS);
      func_240481_j_(p_200404_0_, Blocks.BIRCH_TRAPDOOR, Blocks.BIRCH_PLANKS);
      func_240482_k_(p_200404_0_, Blocks.BIRCH_SIGN, Blocks.BIRCH_PLANKS);
      func_240474_c_(p_200404_0_, Blocks.field_235358_mQ_, Blocks.field_235344_mC_);
      func_240475_d_(p_200404_0_, Blocks.field_235360_mS_, Blocks.field_235344_mC_);
      func_240476_e_(p_200404_0_, Blocks.field_235350_mI_, Blocks.field_235344_mC_);
      func_240477_f_(p_200404_0_, Blocks.field_235354_mM_, Blocks.field_235344_mC_);
      func_240478_g_(p_200404_0_, Blocks.field_235348_mG_, Blocks.field_235344_mC_);
      func_240479_h_(p_200404_0_, Blocks.field_235346_mE_, Blocks.field_235344_mC_);
      func_240480_i_(p_200404_0_, Blocks.field_235356_mO_, Blocks.field_235344_mC_);
      func_240481_j_(p_200404_0_, Blocks.field_235352_mK_, Blocks.field_235344_mC_);
      func_240482_k_(p_200404_0_, Blocks.field_235362_mU_, Blocks.field_235344_mC_);
      func_240474_c_(p_200404_0_, Blocks.DARK_OAK_BUTTON, Blocks.DARK_OAK_PLANKS);
      func_240475_d_(p_200404_0_, Blocks.DARK_OAK_DOOR, Blocks.DARK_OAK_PLANKS);
      func_240476_e_(p_200404_0_, Blocks.DARK_OAK_FENCE, Blocks.DARK_OAK_PLANKS);
      func_240477_f_(p_200404_0_, Blocks.DARK_OAK_FENCE_GATE, Blocks.DARK_OAK_PLANKS);
      func_240478_g_(p_200404_0_, Blocks.DARK_OAK_PRESSURE_PLATE, Blocks.DARK_OAK_PLANKS);
      func_240479_h_(p_200404_0_, Blocks.DARK_OAK_SLAB, Blocks.DARK_OAK_PLANKS);
      func_240480_i_(p_200404_0_, Blocks.DARK_OAK_STAIRS, Blocks.DARK_OAK_PLANKS);
      func_240481_j_(p_200404_0_, Blocks.DARK_OAK_TRAPDOOR, Blocks.DARK_OAK_PLANKS);
      func_240482_k_(p_200404_0_, Blocks.DARK_OAK_SIGN, Blocks.DARK_OAK_PLANKS);
      func_240474_c_(p_200404_0_, Blocks.JUNGLE_BUTTON, Blocks.JUNGLE_PLANKS);
      func_240475_d_(p_200404_0_, Blocks.JUNGLE_DOOR, Blocks.JUNGLE_PLANKS);
      func_240476_e_(p_200404_0_, Blocks.JUNGLE_FENCE, Blocks.JUNGLE_PLANKS);
      func_240477_f_(p_200404_0_, Blocks.JUNGLE_FENCE_GATE, Blocks.JUNGLE_PLANKS);
      func_240478_g_(p_200404_0_, Blocks.JUNGLE_PRESSURE_PLATE, Blocks.JUNGLE_PLANKS);
      func_240479_h_(p_200404_0_, Blocks.JUNGLE_SLAB, Blocks.JUNGLE_PLANKS);
      func_240480_i_(p_200404_0_, Blocks.JUNGLE_STAIRS, Blocks.JUNGLE_PLANKS);
      func_240481_j_(p_200404_0_, Blocks.JUNGLE_TRAPDOOR, Blocks.JUNGLE_PLANKS);
      func_240482_k_(p_200404_0_, Blocks.JUNGLE_SIGN, Blocks.JUNGLE_PLANKS);
      func_240474_c_(p_200404_0_, Blocks.OAK_BUTTON, Blocks.OAK_PLANKS);
      func_240475_d_(p_200404_0_, Blocks.OAK_DOOR, Blocks.OAK_PLANKS);
      func_240476_e_(p_200404_0_, Blocks.OAK_FENCE, Blocks.OAK_PLANKS);
      func_240477_f_(p_200404_0_, Blocks.OAK_FENCE_GATE, Blocks.OAK_PLANKS);
      func_240478_g_(p_200404_0_, Blocks.OAK_PRESSURE_PLATE, Blocks.OAK_PLANKS);
      func_240479_h_(p_200404_0_, Blocks.OAK_SLAB, Blocks.OAK_PLANKS);
      func_240480_i_(p_200404_0_, Blocks.OAK_STAIRS, Blocks.OAK_PLANKS);
      func_240481_j_(p_200404_0_, Blocks.OAK_TRAPDOOR, Blocks.OAK_PLANKS);
      func_240482_k_(p_200404_0_, Blocks.OAK_SIGN, Blocks.OAK_PLANKS);
      func_240474_c_(p_200404_0_, Blocks.SPRUCE_BUTTON, Blocks.SPRUCE_PLANKS);
      func_240475_d_(p_200404_0_, Blocks.SPRUCE_DOOR, Blocks.SPRUCE_PLANKS);
      func_240476_e_(p_200404_0_, Blocks.SPRUCE_FENCE, Blocks.SPRUCE_PLANKS);
      func_240477_f_(p_200404_0_, Blocks.SPRUCE_FENCE_GATE, Blocks.SPRUCE_PLANKS);
      func_240478_g_(p_200404_0_, Blocks.SPRUCE_PRESSURE_PLATE, Blocks.SPRUCE_PLANKS);
      func_240479_h_(p_200404_0_, Blocks.SPRUCE_SLAB, Blocks.SPRUCE_PLANKS);
      func_240480_i_(p_200404_0_, Blocks.SPRUCE_STAIRS, Blocks.SPRUCE_PLANKS);
      func_240481_j_(p_200404_0_, Blocks.SPRUCE_TRAPDOOR, Blocks.SPRUCE_PLANKS);
      func_240482_k_(p_200404_0_, Blocks.SPRUCE_SIGN, Blocks.SPRUCE_PLANKS);
      func_240474_c_(p_200404_0_, Blocks.field_235359_mR_, Blocks.field_235345_mD_);
      func_240475_d_(p_200404_0_, Blocks.field_235361_mT_, Blocks.field_235345_mD_);
      func_240476_e_(p_200404_0_, Blocks.field_235351_mJ_, Blocks.field_235345_mD_);
      func_240477_f_(p_200404_0_, Blocks.field_235355_mN_, Blocks.field_235345_mD_);
      func_240478_g_(p_200404_0_, Blocks.field_235349_mH_, Blocks.field_235345_mD_);
      func_240479_h_(p_200404_0_, Blocks.field_235347_mF_, Blocks.field_235345_mD_);
      func_240480_i_(p_200404_0_, Blocks.field_235357_mP_, Blocks.field_235345_mD_);
      func_240481_j_(p_200404_0_, Blocks.field_235353_mL_, Blocks.field_235345_mD_);
      func_240482_k_(p_200404_0_, Blocks.field_235363_mV_, Blocks.field_235345_mD_);
      func_240483_l_(p_200404_0_, Blocks.BLACK_WOOL, Items.BLACK_DYE);
      func_240484_m_(p_200404_0_, Blocks.BLACK_CARPET, Blocks.BLACK_WOOL);
      func_240485_n_(p_200404_0_, Blocks.BLACK_CARPET, Items.BLACK_DYE);
      func_240486_o_(p_200404_0_, Items.BLACK_BED, Blocks.BLACK_WOOL);
      func_240487_p_(p_200404_0_, Items.BLACK_BED, Items.BLACK_DYE);
      func_240488_q_(p_200404_0_, Items.BLACK_BANNER, Blocks.BLACK_WOOL);
      func_240483_l_(p_200404_0_, Blocks.BLUE_WOOL, Items.BLUE_DYE);
      func_240484_m_(p_200404_0_, Blocks.BLUE_CARPET, Blocks.BLUE_WOOL);
      func_240485_n_(p_200404_0_, Blocks.BLUE_CARPET, Items.BLUE_DYE);
      func_240486_o_(p_200404_0_, Items.BLUE_BED, Blocks.BLUE_WOOL);
      func_240487_p_(p_200404_0_, Items.BLUE_BED, Items.BLUE_DYE);
      func_240488_q_(p_200404_0_, Items.BLUE_BANNER, Blocks.BLUE_WOOL);
      func_240483_l_(p_200404_0_, Blocks.BROWN_WOOL, Items.BROWN_DYE);
      func_240484_m_(p_200404_0_, Blocks.BROWN_CARPET, Blocks.BROWN_WOOL);
      func_240485_n_(p_200404_0_, Blocks.BROWN_CARPET, Items.BROWN_DYE);
      func_240486_o_(p_200404_0_, Items.BROWN_BED, Blocks.BROWN_WOOL);
      func_240487_p_(p_200404_0_, Items.BROWN_BED, Items.BROWN_DYE);
      func_240488_q_(p_200404_0_, Items.BROWN_BANNER, Blocks.BROWN_WOOL);
      func_240483_l_(p_200404_0_, Blocks.CYAN_WOOL, Items.CYAN_DYE);
      func_240484_m_(p_200404_0_, Blocks.CYAN_CARPET, Blocks.CYAN_WOOL);
      func_240485_n_(p_200404_0_, Blocks.CYAN_CARPET, Items.CYAN_DYE);
      func_240486_o_(p_200404_0_, Items.CYAN_BED, Blocks.CYAN_WOOL);
      func_240487_p_(p_200404_0_, Items.CYAN_BED, Items.CYAN_DYE);
      func_240488_q_(p_200404_0_, Items.CYAN_BANNER, Blocks.CYAN_WOOL);
      func_240483_l_(p_200404_0_, Blocks.GRAY_WOOL, Items.GRAY_DYE);
      func_240484_m_(p_200404_0_, Blocks.GRAY_CARPET, Blocks.GRAY_WOOL);
      func_240485_n_(p_200404_0_, Blocks.GRAY_CARPET, Items.GRAY_DYE);
      func_240486_o_(p_200404_0_, Items.GRAY_BED, Blocks.GRAY_WOOL);
      func_240487_p_(p_200404_0_, Items.GRAY_BED, Items.GRAY_DYE);
      func_240488_q_(p_200404_0_, Items.GRAY_BANNER, Blocks.GRAY_WOOL);
      func_240483_l_(p_200404_0_, Blocks.GREEN_WOOL, Items.GREEN_DYE);
      func_240484_m_(p_200404_0_, Blocks.GREEN_CARPET, Blocks.GREEN_WOOL);
      func_240485_n_(p_200404_0_, Blocks.GREEN_CARPET, Items.GREEN_DYE);
      func_240486_o_(p_200404_0_, Items.GREEN_BED, Blocks.GREEN_WOOL);
      func_240487_p_(p_200404_0_, Items.GREEN_BED, Items.GREEN_DYE);
      func_240488_q_(p_200404_0_, Items.GREEN_BANNER, Blocks.GREEN_WOOL);
      func_240483_l_(p_200404_0_, Blocks.LIGHT_BLUE_WOOL, Items.LIGHT_BLUE_DYE);
      func_240484_m_(p_200404_0_, Blocks.LIGHT_BLUE_CARPET, Blocks.LIGHT_BLUE_WOOL);
      func_240485_n_(p_200404_0_, Blocks.LIGHT_BLUE_CARPET, Items.LIGHT_BLUE_DYE);
      func_240486_o_(p_200404_0_, Items.LIGHT_BLUE_BED, Blocks.LIGHT_BLUE_WOOL);
      func_240487_p_(p_200404_0_, Items.LIGHT_BLUE_BED, Items.LIGHT_BLUE_DYE);
      func_240488_q_(p_200404_0_, Items.LIGHT_BLUE_BANNER, Blocks.LIGHT_BLUE_WOOL);
      func_240483_l_(p_200404_0_, Blocks.LIGHT_GRAY_WOOL, Items.LIGHT_GRAY_DYE);
      func_240484_m_(p_200404_0_, Blocks.LIGHT_GRAY_CARPET, Blocks.LIGHT_GRAY_WOOL);
      func_240485_n_(p_200404_0_, Blocks.LIGHT_GRAY_CARPET, Items.LIGHT_GRAY_DYE);
      func_240486_o_(p_200404_0_, Items.LIGHT_GRAY_BED, Blocks.LIGHT_GRAY_WOOL);
      func_240487_p_(p_200404_0_, Items.LIGHT_GRAY_BED, Items.LIGHT_GRAY_DYE);
      func_240488_q_(p_200404_0_, Items.LIGHT_GRAY_BANNER, Blocks.LIGHT_GRAY_WOOL);
      func_240483_l_(p_200404_0_, Blocks.LIME_WOOL, Items.LIME_DYE);
      func_240484_m_(p_200404_0_, Blocks.LIME_CARPET, Blocks.LIME_WOOL);
      func_240485_n_(p_200404_0_, Blocks.LIME_CARPET, Items.LIME_DYE);
      func_240486_o_(p_200404_0_, Items.LIME_BED, Blocks.LIME_WOOL);
      func_240487_p_(p_200404_0_, Items.LIME_BED, Items.LIME_DYE);
      func_240488_q_(p_200404_0_, Items.LIME_BANNER, Blocks.LIME_WOOL);
      func_240483_l_(p_200404_0_, Blocks.MAGENTA_WOOL, Items.MAGENTA_DYE);
      func_240484_m_(p_200404_0_, Blocks.MAGENTA_CARPET, Blocks.MAGENTA_WOOL);
      func_240485_n_(p_200404_0_, Blocks.MAGENTA_CARPET, Items.MAGENTA_DYE);
      func_240486_o_(p_200404_0_, Items.MAGENTA_BED, Blocks.MAGENTA_WOOL);
      func_240487_p_(p_200404_0_, Items.MAGENTA_BED, Items.MAGENTA_DYE);
      func_240488_q_(p_200404_0_, Items.MAGENTA_BANNER, Blocks.MAGENTA_WOOL);
      func_240483_l_(p_200404_0_, Blocks.ORANGE_WOOL, Items.ORANGE_DYE);
      func_240484_m_(p_200404_0_, Blocks.ORANGE_CARPET, Blocks.ORANGE_WOOL);
      func_240485_n_(p_200404_0_, Blocks.ORANGE_CARPET, Items.ORANGE_DYE);
      func_240486_o_(p_200404_0_, Items.ORANGE_BED, Blocks.ORANGE_WOOL);
      func_240487_p_(p_200404_0_, Items.ORANGE_BED, Items.ORANGE_DYE);
      func_240488_q_(p_200404_0_, Items.ORANGE_BANNER, Blocks.ORANGE_WOOL);
      func_240483_l_(p_200404_0_, Blocks.PINK_WOOL, Items.PINK_DYE);
      func_240484_m_(p_200404_0_, Blocks.PINK_CARPET, Blocks.PINK_WOOL);
      func_240485_n_(p_200404_0_, Blocks.PINK_CARPET, Items.PINK_DYE);
      func_240486_o_(p_200404_0_, Items.PINK_BED, Blocks.PINK_WOOL);
      func_240487_p_(p_200404_0_, Items.PINK_BED, Items.PINK_DYE);
      func_240488_q_(p_200404_0_, Items.PINK_BANNER, Blocks.PINK_WOOL);
      func_240483_l_(p_200404_0_, Blocks.PURPLE_WOOL, Items.PURPLE_DYE);
      func_240484_m_(p_200404_0_, Blocks.PURPLE_CARPET, Blocks.PURPLE_WOOL);
      func_240485_n_(p_200404_0_, Blocks.PURPLE_CARPET, Items.PURPLE_DYE);
      func_240486_o_(p_200404_0_, Items.PURPLE_BED, Blocks.PURPLE_WOOL);
      func_240487_p_(p_200404_0_, Items.PURPLE_BED, Items.PURPLE_DYE);
      func_240488_q_(p_200404_0_, Items.PURPLE_BANNER, Blocks.PURPLE_WOOL);
      func_240483_l_(p_200404_0_, Blocks.RED_WOOL, Items.RED_DYE);
      func_240484_m_(p_200404_0_, Blocks.RED_CARPET, Blocks.RED_WOOL);
      func_240485_n_(p_200404_0_, Blocks.RED_CARPET, Items.RED_DYE);
      func_240486_o_(p_200404_0_, Items.RED_BED, Blocks.RED_WOOL);
      func_240487_p_(p_200404_0_, Items.RED_BED, Items.RED_DYE);
      func_240488_q_(p_200404_0_, Items.RED_BANNER, Blocks.RED_WOOL);
      func_240484_m_(p_200404_0_, Blocks.WHITE_CARPET, Blocks.WHITE_WOOL);
      func_240486_o_(p_200404_0_, Items.WHITE_BED, Blocks.WHITE_WOOL);
      func_240488_q_(p_200404_0_, Items.WHITE_BANNER, Blocks.WHITE_WOOL);
      func_240483_l_(p_200404_0_, Blocks.YELLOW_WOOL, Items.YELLOW_DYE);
      func_240484_m_(p_200404_0_, Blocks.YELLOW_CARPET, Blocks.YELLOW_WOOL);
      func_240485_n_(p_200404_0_, Blocks.YELLOW_CARPET, Items.YELLOW_DYE);
      func_240486_o_(p_200404_0_, Items.YELLOW_BED, Blocks.YELLOW_WOOL);
      func_240487_p_(p_200404_0_, Items.YELLOW_BED, Items.YELLOW_DYE);
      func_240488_q_(p_200404_0_, Items.YELLOW_BANNER, Blocks.YELLOW_WOOL);
      func_240489_r_(p_200404_0_, Blocks.BLACK_STAINED_GLASS, Items.BLACK_DYE);
      func_240490_s_(p_200404_0_, Blocks.BLACK_STAINED_GLASS_PANE, Blocks.BLACK_STAINED_GLASS);
      func_240491_t_(p_200404_0_, Blocks.BLACK_STAINED_GLASS_PANE, Items.BLACK_DYE);
      func_240489_r_(p_200404_0_, Blocks.BLUE_STAINED_GLASS, Items.BLUE_DYE);
      func_240490_s_(p_200404_0_, Blocks.BLUE_STAINED_GLASS_PANE, Blocks.BLUE_STAINED_GLASS);
      func_240491_t_(p_200404_0_, Blocks.BLUE_STAINED_GLASS_PANE, Items.BLUE_DYE);
      func_240489_r_(p_200404_0_, Blocks.BROWN_STAINED_GLASS, Items.BROWN_DYE);
      func_240490_s_(p_200404_0_, Blocks.BROWN_STAINED_GLASS_PANE, Blocks.BROWN_STAINED_GLASS);
      func_240491_t_(p_200404_0_, Blocks.BROWN_STAINED_GLASS_PANE, Items.BROWN_DYE);
      func_240489_r_(p_200404_0_, Blocks.CYAN_STAINED_GLASS, Items.CYAN_DYE);
      func_240490_s_(p_200404_0_, Blocks.CYAN_STAINED_GLASS_PANE, Blocks.CYAN_STAINED_GLASS);
      func_240491_t_(p_200404_0_, Blocks.CYAN_STAINED_GLASS_PANE, Items.CYAN_DYE);
      func_240489_r_(p_200404_0_, Blocks.GRAY_STAINED_GLASS, Items.GRAY_DYE);
      func_240490_s_(p_200404_0_, Blocks.GRAY_STAINED_GLASS_PANE, Blocks.GRAY_STAINED_GLASS);
      func_240491_t_(p_200404_0_, Blocks.GRAY_STAINED_GLASS_PANE, Items.GRAY_DYE);
      func_240489_r_(p_200404_0_, Blocks.GREEN_STAINED_GLASS, Items.GREEN_DYE);
      func_240490_s_(p_200404_0_, Blocks.GREEN_STAINED_GLASS_PANE, Blocks.GREEN_STAINED_GLASS);
      func_240491_t_(p_200404_0_, Blocks.GREEN_STAINED_GLASS_PANE, Items.GREEN_DYE);
      func_240489_r_(p_200404_0_, Blocks.LIGHT_BLUE_STAINED_GLASS, Items.LIGHT_BLUE_DYE);
      func_240490_s_(p_200404_0_, Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, Blocks.LIGHT_BLUE_STAINED_GLASS);
      func_240491_t_(p_200404_0_, Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, Items.LIGHT_BLUE_DYE);
      func_240489_r_(p_200404_0_, Blocks.LIGHT_GRAY_STAINED_GLASS, Items.LIGHT_GRAY_DYE);
      func_240490_s_(p_200404_0_, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, Blocks.LIGHT_GRAY_STAINED_GLASS);
      func_240491_t_(p_200404_0_, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, Items.LIGHT_GRAY_DYE);
      func_240489_r_(p_200404_0_, Blocks.LIME_STAINED_GLASS, Items.LIME_DYE);
      func_240490_s_(p_200404_0_, Blocks.LIME_STAINED_GLASS_PANE, Blocks.LIME_STAINED_GLASS);
      func_240491_t_(p_200404_0_, Blocks.LIME_STAINED_GLASS_PANE, Items.LIME_DYE);
      func_240489_r_(p_200404_0_, Blocks.MAGENTA_STAINED_GLASS, Items.MAGENTA_DYE);
      func_240490_s_(p_200404_0_, Blocks.MAGENTA_STAINED_GLASS_PANE, Blocks.MAGENTA_STAINED_GLASS);
      func_240491_t_(p_200404_0_, Blocks.MAGENTA_STAINED_GLASS_PANE, Items.MAGENTA_DYE);
      func_240489_r_(p_200404_0_, Blocks.ORANGE_STAINED_GLASS, Items.ORANGE_DYE);
      func_240490_s_(p_200404_0_, Blocks.ORANGE_STAINED_GLASS_PANE, Blocks.ORANGE_STAINED_GLASS);
      func_240491_t_(p_200404_0_, Blocks.ORANGE_STAINED_GLASS_PANE, Items.ORANGE_DYE);
      func_240489_r_(p_200404_0_, Blocks.PINK_STAINED_GLASS, Items.PINK_DYE);
      func_240490_s_(p_200404_0_, Blocks.PINK_STAINED_GLASS_PANE, Blocks.PINK_STAINED_GLASS);
      func_240491_t_(p_200404_0_, Blocks.PINK_STAINED_GLASS_PANE, Items.PINK_DYE);
      func_240489_r_(p_200404_0_, Blocks.PURPLE_STAINED_GLASS, Items.PURPLE_DYE);
      func_240490_s_(p_200404_0_, Blocks.PURPLE_STAINED_GLASS_PANE, Blocks.PURPLE_STAINED_GLASS);
      func_240491_t_(p_200404_0_, Blocks.PURPLE_STAINED_GLASS_PANE, Items.PURPLE_DYE);
      func_240489_r_(p_200404_0_, Blocks.RED_STAINED_GLASS, Items.RED_DYE);
      func_240490_s_(p_200404_0_, Blocks.RED_STAINED_GLASS_PANE, Blocks.RED_STAINED_GLASS);
      func_240491_t_(p_200404_0_, Blocks.RED_STAINED_GLASS_PANE, Items.RED_DYE);
      func_240489_r_(p_200404_0_, Blocks.WHITE_STAINED_GLASS, Items.WHITE_DYE);
      func_240490_s_(p_200404_0_, Blocks.WHITE_STAINED_GLASS_PANE, Blocks.WHITE_STAINED_GLASS);
      func_240491_t_(p_200404_0_, Blocks.WHITE_STAINED_GLASS_PANE, Items.WHITE_DYE);
      func_240489_r_(p_200404_0_, Blocks.YELLOW_STAINED_GLASS, Items.YELLOW_DYE);
      func_240490_s_(p_200404_0_, Blocks.YELLOW_STAINED_GLASS_PANE, Blocks.YELLOW_STAINED_GLASS);
      func_240491_t_(p_200404_0_, Blocks.YELLOW_STAINED_GLASS_PANE, Items.YELLOW_DYE);
      func_240492_u_(p_200404_0_, Blocks.BLACK_TERRACOTTA, Items.BLACK_DYE);
      func_240492_u_(p_200404_0_, Blocks.BLUE_TERRACOTTA, Items.BLUE_DYE);
      func_240492_u_(p_200404_0_, Blocks.BROWN_TERRACOTTA, Items.BROWN_DYE);
      func_240492_u_(p_200404_0_, Blocks.CYAN_TERRACOTTA, Items.CYAN_DYE);
      func_240492_u_(p_200404_0_, Blocks.GRAY_TERRACOTTA, Items.GRAY_DYE);
      func_240492_u_(p_200404_0_, Blocks.GREEN_TERRACOTTA, Items.GREEN_DYE);
      func_240492_u_(p_200404_0_, Blocks.LIGHT_BLUE_TERRACOTTA, Items.LIGHT_BLUE_DYE);
      func_240492_u_(p_200404_0_, Blocks.LIGHT_GRAY_TERRACOTTA, Items.LIGHT_GRAY_DYE);
      func_240492_u_(p_200404_0_, Blocks.LIME_TERRACOTTA, Items.LIME_DYE);
      func_240492_u_(p_200404_0_, Blocks.MAGENTA_TERRACOTTA, Items.MAGENTA_DYE);
      func_240492_u_(p_200404_0_, Blocks.ORANGE_TERRACOTTA, Items.ORANGE_DYE);
      func_240492_u_(p_200404_0_, Blocks.PINK_TERRACOTTA, Items.PINK_DYE);
      func_240492_u_(p_200404_0_, Blocks.PURPLE_TERRACOTTA, Items.PURPLE_DYE);
      func_240492_u_(p_200404_0_, Blocks.RED_TERRACOTTA, Items.RED_DYE);
      func_240492_u_(p_200404_0_, Blocks.WHITE_TERRACOTTA, Items.WHITE_DYE);
      func_240492_u_(p_200404_0_, Blocks.YELLOW_TERRACOTTA, Items.YELLOW_DYE);
      func_240493_v_(p_200404_0_, Blocks.BLACK_CONCRETE_POWDER, Items.BLACK_DYE);
      func_240493_v_(p_200404_0_, Blocks.BLUE_CONCRETE_POWDER, Items.BLUE_DYE);
      func_240493_v_(p_200404_0_, Blocks.BROWN_CONCRETE_POWDER, Items.BROWN_DYE);
      func_240493_v_(p_200404_0_, Blocks.CYAN_CONCRETE_POWDER, Items.CYAN_DYE);
      func_240493_v_(p_200404_0_, Blocks.GRAY_CONCRETE_POWDER, Items.GRAY_DYE);
      func_240493_v_(p_200404_0_, Blocks.GREEN_CONCRETE_POWDER, Items.GREEN_DYE);
      func_240493_v_(p_200404_0_, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Items.LIGHT_BLUE_DYE);
      func_240493_v_(p_200404_0_, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Items.LIGHT_GRAY_DYE);
      func_240493_v_(p_200404_0_, Blocks.LIME_CONCRETE_POWDER, Items.LIME_DYE);
      func_240493_v_(p_200404_0_, Blocks.MAGENTA_CONCRETE_POWDER, Items.MAGENTA_DYE);
      func_240493_v_(p_200404_0_, Blocks.ORANGE_CONCRETE_POWDER, Items.ORANGE_DYE);
      func_240493_v_(p_200404_0_, Blocks.PINK_CONCRETE_POWDER, Items.PINK_DYE);
      func_240493_v_(p_200404_0_, Blocks.PURPLE_CONCRETE_POWDER, Items.PURPLE_DYE);
      func_240493_v_(p_200404_0_, Blocks.RED_CONCRETE_POWDER, Items.RED_DYE);
      func_240493_v_(p_200404_0_, Blocks.WHITE_CONCRETE_POWDER, Items.WHITE_DYE);
      func_240493_v_(p_200404_0_, Blocks.YELLOW_CONCRETE_POWDER, Items.YELLOW_DYE);
      ShapedRecipeBuilder.shapedRecipe(Blocks.ACTIVATOR_RAIL, 6).key('#', Blocks.REDSTONE_TORCH).key('S', Items.STICK).key('X', Items.IRON_INGOT).patternLine("XSX").patternLine("X#X").patternLine("XSX").addCriterion("has_rail", hasItem(Blocks.RAIL)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Blocks.ANDESITE, 2).addIngredient(Blocks.DIORITE).addIngredient(Blocks.COBBLESTONE).addCriterion("has_stone", hasItem(Blocks.DIORITE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.ANVIL).key('I', Blocks.IRON_BLOCK).key('i', Items.IRON_INGOT).patternLine("III").patternLine(" i ").patternLine("iii").addCriterion("has_iron_block", hasItem(Blocks.IRON_BLOCK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.ARMOR_STAND).key('/', Items.STICK).key('_', Blocks.SMOOTH_STONE_SLAB).patternLine("///").patternLine(" / ").patternLine("/_/").addCriterion("has_stone_slab", hasItem(Blocks.SMOOTH_STONE_SLAB)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.ARROW, 4).key('#', Items.STICK).key('X', Items.FLINT).key('Y', Items.FEATHER).patternLine("X").patternLine("#").patternLine("Y").addCriterion("has_feather", hasItem(Items.FEATHER)).addCriterion("has_flint", hasItem(Items.FLINT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.BARREL, 1).key('P', ItemTags.PLANKS).key('S', ItemTags.WOODEN_SLABS).patternLine("PSP").patternLine("P P").patternLine("PSP").addCriterion("has_planks", hasItem(ItemTags.PLANKS)).addCriterion("has_wood_slab", hasItem(ItemTags.WOODEN_SLABS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.BEACON).key('S', Items.NETHER_STAR).key('G', Blocks.GLASS).key('O', Blocks.OBSIDIAN).patternLine("GGG").patternLine("GSG").patternLine("OOO").addCriterion("has_nether_star", hasItem(Items.NETHER_STAR)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.BEEHIVE).key('P', ItemTags.PLANKS).key('H', Items.HONEYCOMB).patternLine("PPP").patternLine("HHH").patternLine("PPP").addCriterion("has_honeycomb", hasItem(Items.HONEYCOMB)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.BEETROOT_SOUP).addIngredient(Items.BOWL).addIngredient(Items.BEETROOT, 6).addCriterion("has_beetroot", hasItem(Items.BEETROOT)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.BLACK_DYE).addIngredient(Items.INK_SAC).setGroup("black_dye").addCriterion("has_ink_sac", hasItem(Items.INK_SAC)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.BLACK_DYE).addIngredient(Blocks.WITHER_ROSE).setGroup("black_dye").addCriterion("has_black_flower", hasItem(Blocks.WITHER_ROSE)).build(p_200404_0_, "black_dye_from_wither_rose");
      ShapelessRecipeBuilder.shapelessRecipe(Items.BLAZE_POWDER, 2).addIngredient(Items.BLAZE_ROD).addCriterion("has_blaze_rod", hasItem(Items.BLAZE_ROD)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.BLUE_DYE).addIngredient(Items.LAPIS_LAZULI).setGroup("blue_dye").addCriterion("has_lapis_lazuli", hasItem(Items.LAPIS_LAZULI)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.BLUE_DYE).addIngredient(Blocks.CORNFLOWER).setGroup("blue_dye").addCriterion("has_blue_flower", hasItem(Blocks.CORNFLOWER)).build(p_200404_0_, "blue_dye_from_cornflower");
      ShapedRecipeBuilder.shapedRecipe(Blocks.BLUE_ICE).key('#', Blocks.PACKED_ICE).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_packed_ice", hasItem(Blocks.PACKED_ICE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.BONE_BLOCK).key('X', Items.BONE_MEAL).patternLine("XXX").patternLine("XXX").patternLine("XXX").addCriterion("has_bonemeal", hasItem(Items.BONE_MEAL)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.BONE_MEAL, 3).addIngredient(Items.BONE).setGroup("bonemeal").addCriterion("has_bone", hasItem(Items.BONE)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.BONE_MEAL, 9).addIngredient(Blocks.BONE_BLOCK).setGroup("bonemeal").addCriterion("has_bone_block", hasItem(Blocks.BONE_BLOCK)).build(p_200404_0_, "bone_meal_from_bone_block");
      ShapelessRecipeBuilder.shapelessRecipe(Items.BOOK).addIngredient(Items.PAPER, 3).addIngredient(Items.LEATHER).addCriterion("has_paper", hasItem(Items.PAPER)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.BOOKSHELF).key('#', ItemTags.PLANKS).key('X', Items.BOOK).patternLine("###").patternLine("XXX").patternLine("###").addCriterion("has_book", hasItem(Items.BOOK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.BOW).key('#', Items.STICK).key('X', Items.STRING).patternLine(" #X").patternLine("# X").patternLine(" #X").addCriterion("has_string", hasItem(Items.STRING)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.BOWL, 4).key('#', ItemTags.PLANKS).patternLine("# #").patternLine(" # ").addCriterion("has_brown_mushroom", hasItem(Blocks.BROWN_MUSHROOM)).addCriterion("has_red_mushroom", hasItem(Blocks.RED_MUSHROOM)).addCriterion("has_mushroom_stew", hasItem(Items.MUSHROOM_STEW)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.BREAD).key('#', Items.WHEAT).patternLine("###").addCriterion("has_wheat", hasItem(Items.WHEAT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.BREWING_STAND).key('B', Items.BLAZE_ROD).key('#', ItemTags.field_242176_ac).patternLine(" B ").patternLine("###").addCriterion("has_blaze_rod", hasItem(Items.BLAZE_ROD)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.BRICKS).key('#', Items.BRICK).patternLine("##").patternLine("##").addCriterion("has_brick", hasItem(Items.BRICK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.BRICK_SLAB, 6).key('#', Blocks.BRICKS).patternLine("###").addCriterion("has_brick_block", hasItem(Blocks.BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.BRICK_STAIRS, 4).key('#', Blocks.BRICKS).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_brick_block", hasItem(Blocks.BRICKS)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.BROWN_DYE).addIngredient(Items.COCOA_BEANS).setGroup("brown_dye").addCriterion("has_cocoa_beans", hasItem(Items.COCOA_BEANS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.BUCKET).key('#', Items.IRON_INGOT).patternLine("# #").patternLine(" # ").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.CAKE).key('A', Items.MILK_BUCKET).key('B', Items.SUGAR).key('C', Items.WHEAT).key('E', Items.EGG).patternLine("AAA").patternLine("BEB").patternLine("CCC").addCriterion("has_egg", hasItem(Items.EGG)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.CAMPFIRE).key('L', ItemTags.LOGS).key('S', Items.STICK).key('C', ItemTags.COALS).patternLine(" S ").patternLine("SCS").patternLine("LLL").addCriterion("has_stick", hasItem(Items.STICK)).addCriterion("has_coal", hasItem(ItemTags.COALS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.CARROT_ON_A_STICK).key('#', Items.FISHING_ROD).key('X', Items.CARROT).patternLine("# ").patternLine(" X").addCriterion("has_carrot", hasItem(Items.CARROT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.field_234774_pk_).key('#', Items.FISHING_ROD).key('X', Items.field_234723_bx_).patternLine("# ").patternLine(" X").addCriterion("has_warped_fungus", hasItem(Items.field_234723_bx_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.CAULDRON).key('#', Items.IRON_INGOT).patternLine("# #").patternLine("# #").patternLine("###").addCriterion("has_water_bucket", hasItem(Items.WATER_BUCKET)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.COMPOSTER).key('#', ItemTags.WOODEN_SLABS).patternLine("# #").patternLine("# #").patternLine("###").addCriterion("has_wood_slab", hasItem(ItemTags.WOODEN_SLABS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.CHEST).key('#', ItemTags.PLANKS).patternLine("###").patternLine("# #").patternLine("###").addCriterion("has_lots_of_items", new InventoryChangeTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, MinMaxBounds.IntBound.atLeast(10), MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, new ItemPredicate[0])).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.CHEST_MINECART).key('A', Blocks.CHEST).key('B', Items.MINECART).patternLine("A").patternLine("B").addCriterion("has_minecart", hasItem(Items.MINECART)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235393_nG_).key('#', Blocks.NETHER_BRICK_SLAB).patternLine("#").patternLine("#").addCriterion("has_nether_bricks", hasItem(Blocks.NETHER_BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.CHISELED_QUARTZ_BLOCK).key('#', Blocks.QUARTZ_SLAB).patternLine("#").patternLine("#").addCriterion("has_chiseled_quartz_block", hasItem(Blocks.CHISELED_QUARTZ_BLOCK)).addCriterion("has_quartz_block", hasItem(Blocks.QUARTZ_BLOCK)).addCriterion("has_quartz_pillar", hasItem(Blocks.QUARTZ_PILLAR)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.CHISELED_STONE_BRICKS).key('#', Blocks.STONE_BRICK_SLAB).patternLine("#").patternLine("#").addCriterion("has_stone_bricks", hasItem(ItemTags.STONE_BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.CLAY).key('#', Items.CLAY_BALL).patternLine("##").patternLine("##").addCriterion("has_clay_ball", hasItem(Items.CLAY_BALL)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.CLOCK).key('#', Items.GOLD_INGOT).key('X', Items.REDSTONE).patternLine(" # ").patternLine("#X#").patternLine(" # ").addCriterion("has_redstone", hasItem(Items.REDSTONE)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.COAL, 9).addIngredient(Blocks.COAL_BLOCK).addCriterion("has_coal_block", hasItem(Blocks.COAL_BLOCK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.COAL_BLOCK).key('#', Items.COAL).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_coal", hasItem(Items.COAL)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.COARSE_DIRT, 4).key('D', Blocks.DIRT).key('G', Blocks.GRAVEL).patternLine("DG").patternLine("GD").addCriterion("has_gravel", hasItem(Blocks.GRAVEL)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.COBBLESTONE_SLAB, 6).key('#', Blocks.COBBLESTONE).patternLine("###").addCriterion("has_cobblestone", hasItem(Blocks.COBBLESTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.COBBLESTONE_WALL, 6).key('#', Blocks.COBBLESTONE).patternLine("###").patternLine("###").addCriterion("has_cobblestone", hasItem(Blocks.COBBLESTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.COMPARATOR).key('#', Blocks.REDSTONE_TORCH).key('X', Items.QUARTZ).key('I', Blocks.STONE).patternLine(" # ").patternLine("#X#").patternLine("III").addCriterion("has_quartz", hasItem(Items.QUARTZ)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.COMPASS).key('#', Items.IRON_INGOT).key('X', Items.REDSTONE).patternLine(" # ").patternLine("#X#").patternLine(" # ").addCriterion("has_redstone", hasItem(Items.REDSTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.COOKIE, 8).key('#', Items.WHEAT).key('X', Items.COCOA_BEANS).patternLine("#X#").addCriterion("has_cocoa", hasItem(Items.COCOA_BEANS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.CRAFTING_TABLE).key('#', ItemTags.PLANKS).patternLine("##").patternLine("##").addCriterion("has_planks", hasItem(ItemTags.PLANKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.CROSSBOW).key('~', Items.STRING).key('#', Items.STICK).key('&', Items.IRON_INGOT).key('$', Blocks.TRIPWIRE_HOOK).patternLine("#&#").patternLine("~$~").patternLine(" # ").addCriterion("has_string", hasItem(Items.STRING)).addCriterion("has_stick", hasItem(Items.STICK)).addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).addCriterion("has_tripwire_hook", hasItem(Blocks.TRIPWIRE_HOOK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.LOOM).key('#', ItemTags.PLANKS).key('@', Items.STRING).patternLine("@@").patternLine("##").addCriterion("has_string", hasItem(Items.STRING)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.CHISELED_RED_SANDSTONE).key('#', Blocks.RED_SANDSTONE_SLAB).patternLine("#").patternLine("#").addCriterion("has_red_sandstone", hasItem(Blocks.RED_SANDSTONE)).addCriterion("has_chiseled_red_sandstone", hasItem(Blocks.CHISELED_RED_SANDSTONE)).addCriterion("has_cut_red_sandstone", hasItem(Blocks.CUT_RED_SANDSTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.CHISELED_SANDSTONE).key('#', Blocks.SANDSTONE_SLAB).patternLine("#").patternLine("#").addCriterion("has_stone_slab", hasItem(Blocks.SANDSTONE_SLAB)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.CYAN_DYE, 2).addIngredient(Items.BLUE_DYE).addIngredient(Items.GREEN_DYE).addCriterion("has_green_dye", hasItem(Items.GREEN_DYE)).addCriterion("has_blue_dye", hasItem(Items.BLUE_DYE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.DARK_PRISMARINE).key('S', Items.PRISMARINE_SHARD).key('I', Items.BLACK_DYE).patternLine("SSS").patternLine("SIS").patternLine("SSS").addCriterion("has_prismarine_shard", hasItem(Items.PRISMARINE_SHARD)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE_STAIRS, 4).key('#', Blocks.PRISMARINE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_prismarine", hasItem(Blocks.PRISMARINE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE_BRICK_STAIRS, 4).key('#', Blocks.PRISMARINE_BRICKS).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_prismarine_bricks", hasItem(Blocks.PRISMARINE_BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.DARK_PRISMARINE_STAIRS, 4).key('#', Blocks.DARK_PRISMARINE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_dark_prismarine", hasItem(Blocks.DARK_PRISMARINE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.DAYLIGHT_DETECTOR).key('Q', Items.QUARTZ).key('G', Blocks.GLASS).key('W', Ingredient.fromTag(ItemTags.WOODEN_SLABS)).patternLine("GGG").patternLine("QQQ").patternLine("WWW").addCriterion("has_quartz", hasItem(Items.QUARTZ)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.DETECTOR_RAIL, 6).key('R', Items.REDSTONE).key('#', Blocks.STONE_PRESSURE_PLATE).key('X', Items.IRON_INGOT).patternLine("X X").patternLine("X#X").patternLine("XRX").addCriterion("has_rail", hasItem(Blocks.RAIL)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.DIAMOND, 9).addIngredient(Blocks.DIAMOND_BLOCK).addCriterion("has_diamond_block", hasItem(Blocks.DIAMOND_BLOCK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_AXE).key('#', Items.STICK).key('X', Items.DIAMOND).patternLine("XX").patternLine("X#").patternLine(" #").addCriterion("has_diamond", hasItem(Items.DIAMOND)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.DIAMOND_BLOCK).key('#', Items.DIAMOND).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_diamond", hasItem(Items.DIAMOND)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_BOOTS).key('X', Items.DIAMOND).patternLine("X X").patternLine("X X").addCriterion("has_diamond", hasItem(Items.DIAMOND)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_CHESTPLATE).key('X', Items.DIAMOND).patternLine("X X").patternLine("XXX").patternLine("XXX").addCriterion("has_diamond", hasItem(Items.DIAMOND)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_HELMET).key('X', Items.DIAMOND).patternLine("XXX").patternLine("X X").addCriterion("has_diamond", hasItem(Items.DIAMOND)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_HOE).key('#', Items.STICK).key('X', Items.DIAMOND).patternLine("XX").patternLine(" #").patternLine(" #").addCriterion("has_diamond", hasItem(Items.DIAMOND)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_LEGGINGS).key('X', Items.DIAMOND).patternLine("XXX").patternLine("X X").patternLine("X X").addCriterion("has_diamond", hasItem(Items.DIAMOND)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_PICKAXE).key('#', Items.STICK).key('X', Items.DIAMOND).patternLine("XXX").patternLine(" # ").patternLine(" # ").addCriterion("has_diamond", hasItem(Items.DIAMOND)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_SHOVEL).key('#', Items.STICK).key('X', Items.DIAMOND).patternLine("X").patternLine("#").patternLine("#").addCriterion("has_diamond", hasItem(Items.DIAMOND)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_SWORD).key('#', Items.STICK).key('X', Items.DIAMOND).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_diamond", hasItem(Items.DIAMOND)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.DIORITE, 2).key('Q', Items.QUARTZ).key('C', Blocks.COBBLESTONE).patternLine("CQ").patternLine("QC").addCriterion("has_quartz", hasItem(Items.QUARTZ)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.DISPENSER).key('R', Items.REDSTONE).key('#', Blocks.COBBLESTONE).key('X', Items.BOW).patternLine("###").patternLine("#X#").patternLine("#R#").addCriterion("has_bow", hasItem(Items.BOW)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.DROPPER).key('R', Items.REDSTONE).key('#', Blocks.COBBLESTONE).patternLine("###").patternLine("# #").patternLine("#R#").addCriterion("has_redstone", hasItem(Items.REDSTONE)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.EMERALD, 9).addIngredient(Blocks.EMERALD_BLOCK).addCriterion("has_emerald_block", hasItem(Blocks.EMERALD_BLOCK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.EMERALD_BLOCK).key('#', Items.EMERALD).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_emerald", hasItem(Items.EMERALD)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.ENCHANTING_TABLE).key('B', Items.BOOK).key('#', Blocks.OBSIDIAN).key('D', Items.DIAMOND).patternLine(" B ").patternLine("D#D").patternLine("###").addCriterion("has_obsidian", hasItem(Blocks.OBSIDIAN)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.ENDER_CHEST).key('#', Blocks.OBSIDIAN).key('E', Items.ENDER_EYE).patternLine("###").patternLine("#E#").patternLine("###").addCriterion("has_ender_eye", hasItem(Items.ENDER_EYE)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.ENDER_EYE).addIngredient(Items.ENDER_PEARL).addIngredient(Items.BLAZE_POWDER).addCriterion("has_blaze_powder", hasItem(Items.BLAZE_POWDER)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.END_STONE_BRICKS, 4).key('#', Blocks.END_STONE).patternLine("##").patternLine("##").addCriterion("has_end_stone", hasItem(Blocks.END_STONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.END_CRYSTAL).key('T', Items.GHAST_TEAR).key('E', Items.ENDER_EYE).key('G', Blocks.GLASS).patternLine("GGG").patternLine("GEG").patternLine("GTG").addCriterion("has_ender_eye", hasItem(Items.ENDER_EYE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.END_ROD, 4).key('#', Items.POPPED_CHORUS_FRUIT).key('/', Items.BLAZE_ROD).patternLine("/").patternLine("#").addCriterion("has_chorus_fruit_popped", hasItem(Items.POPPED_CHORUS_FRUIT)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.FERMENTED_SPIDER_EYE).addIngredient(Items.SPIDER_EYE).addIngredient(Blocks.BROWN_MUSHROOM).addIngredient(Items.SUGAR).addCriterion("has_spider_eye", hasItem(Items.SPIDER_EYE)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.FIRE_CHARGE, 3).addIngredient(Items.GUNPOWDER).addIngredient(Items.BLAZE_POWDER).addIngredient(Ingredient.fromItems(Items.COAL, Items.CHARCOAL)).addCriterion("has_blaze_powder", hasItem(Items.BLAZE_POWDER)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.FISHING_ROD).key('#', Items.STICK).key('X', Items.STRING).patternLine("  #").patternLine(" #X").patternLine("# X").addCriterion("has_string", hasItem(Items.STRING)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.FLINT_AND_STEEL).addIngredient(Items.IRON_INGOT).addIngredient(Items.FLINT).addCriterion("has_flint", hasItem(Items.FLINT)).addCriterion("has_obsidian", hasItem(Blocks.OBSIDIAN)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.FLOWER_POT).key('#', Items.BRICK).patternLine("# #").patternLine(" # ").addCriterion("has_brick", hasItem(Items.BRICK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.FURNACE).key('#', ItemTags.field_242176_ac).patternLine("###").patternLine("# #").patternLine("###").addCriterion("has_cobblestone", hasItem(ItemTags.field_242176_ac)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.FURNACE_MINECART).key('A', Blocks.FURNACE).key('B', Items.MINECART).patternLine("A").patternLine("B").addCriterion("has_minecart", hasItem(Items.MINECART)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.GLASS_BOTTLE, 3).key('#', Blocks.GLASS).patternLine("# #").patternLine(" # ").addCriterion("has_glass", hasItem(Blocks.GLASS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.GLASS_PANE, 16).key('#', Blocks.GLASS).patternLine("###").patternLine("###").addCriterion("has_glass", hasItem(Blocks.GLASS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.GLOWSTONE).key('#', Items.GLOWSTONE_DUST).patternLine("##").patternLine("##").addCriterion("has_glowstone_dust", hasItem(Items.GLOWSTONE_DUST)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_APPLE).key('#', Items.GOLD_INGOT).key('X', Items.APPLE).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_gold_ingot", hasItem(Items.GOLD_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_AXE).key('#', Items.STICK).key('X', Items.GOLD_INGOT).patternLine("XX").patternLine("X#").patternLine(" #").addCriterion("has_gold_ingot", hasItem(Items.GOLD_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_BOOTS).key('X', Items.GOLD_INGOT).patternLine("X X").patternLine("X X").addCriterion("has_gold_ingot", hasItem(Items.GOLD_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_CARROT).key('#', Items.GOLD_NUGGET).key('X', Items.CARROT).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_gold_nugget", hasItem(Items.GOLD_NUGGET)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_CHESTPLATE).key('X', Items.GOLD_INGOT).patternLine("X X").patternLine("XXX").patternLine("XXX").addCriterion("has_gold_ingot", hasItem(Items.GOLD_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_HELMET).key('X', Items.GOLD_INGOT).patternLine("XXX").patternLine("X X").addCriterion("has_gold_ingot", hasItem(Items.GOLD_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_HOE).key('#', Items.STICK).key('X', Items.GOLD_INGOT).patternLine("XX").patternLine(" #").patternLine(" #").addCriterion("has_gold_ingot", hasItem(Items.GOLD_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_LEGGINGS).key('X', Items.GOLD_INGOT).patternLine("XXX").patternLine("X X").patternLine("X X").addCriterion("has_gold_ingot", hasItem(Items.GOLD_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_PICKAXE).key('#', Items.STICK).key('X', Items.GOLD_INGOT).patternLine("XXX").patternLine(" # ").patternLine(" # ").addCriterion("has_gold_ingot", hasItem(Items.GOLD_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.POWERED_RAIL, 6).key('R', Items.REDSTONE).key('#', Items.STICK).key('X', Items.GOLD_INGOT).patternLine("X X").patternLine("X#X").patternLine("XRX").addCriterion("has_rail", hasItem(Blocks.RAIL)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_SHOVEL).key('#', Items.STICK).key('X', Items.GOLD_INGOT).patternLine("X").patternLine("#").patternLine("#").addCriterion("has_gold_ingot", hasItem(Items.GOLD_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_SWORD).key('#', Items.STICK).key('X', Items.GOLD_INGOT).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_gold_ingot", hasItem(Items.GOLD_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.GOLD_BLOCK).key('#', Items.GOLD_INGOT).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_gold_ingot", hasItem(Items.GOLD_INGOT)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.GOLD_INGOT, 9).addIngredient(Blocks.GOLD_BLOCK).setGroup("gold_ingot").addCriterion("has_gold_block", hasItem(Blocks.GOLD_BLOCK)).build(p_200404_0_, "gold_ingot_from_gold_block");
      ShapedRecipeBuilder.shapedRecipe(Items.GOLD_INGOT).key('#', Items.GOLD_NUGGET).patternLine("###").patternLine("###").patternLine("###").setGroup("gold_ingot").addCriterion("has_gold_nugget", hasItem(Items.GOLD_NUGGET)).build(p_200404_0_, "gold_ingot_from_nuggets");
      ShapelessRecipeBuilder.shapelessRecipe(Items.GOLD_NUGGET, 9).addIngredient(Items.GOLD_INGOT).addCriterion("has_gold_ingot", hasItem(Items.GOLD_INGOT)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Blocks.GRANITE).addIngredient(Blocks.DIORITE).addIngredient(Items.QUARTZ).addCriterion("has_quartz", hasItem(Items.QUARTZ)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.GRAY_DYE, 2).addIngredient(Items.BLACK_DYE).addIngredient(Items.WHITE_DYE).addCriterion("has_white_dye", hasItem(Items.WHITE_DYE)).addCriterion("has_black_dye", hasItem(Items.BLACK_DYE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.HAY_BLOCK).key('#', Items.WHEAT).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_wheat", hasItem(Items.WHEAT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE).key('#', Items.IRON_INGOT).patternLine("##").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.HONEY_BOTTLE, 4).addIngredient(Items.HONEY_BLOCK).addIngredient(Items.GLASS_BOTTLE, 4).addCriterion("has_honey_block", hasItem(Blocks.HONEY_BLOCK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.HONEY_BLOCK, 1).key('S', Items.HONEY_BOTTLE).patternLine("SS").patternLine("SS").addCriterion("has_honey_bottle", hasItem(Items.HONEY_BOTTLE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.HONEYCOMB_BLOCK).key('H', Items.HONEYCOMB).patternLine("HH").patternLine("HH").addCriterion("has_honeycomb", hasItem(Items.HONEYCOMB)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.HOPPER).key('C', Blocks.CHEST).key('I', Items.IRON_INGOT).patternLine("I I").patternLine("ICI").patternLine(" I ").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.HOPPER_MINECART).key('A', Blocks.HOPPER).key('B', Items.MINECART).patternLine("A").patternLine("B").addCriterion("has_minecart", hasItem(Items.MINECART)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.IRON_AXE).key('#', Items.STICK).key('X', Items.IRON_INGOT).patternLine("XX").patternLine("X#").patternLine(" #").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.IRON_BARS, 16).key('#', Items.IRON_INGOT).patternLine("###").patternLine("###").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.IRON_BLOCK).key('#', Items.IRON_INGOT).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.IRON_BOOTS).key('X', Items.IRON_INGOT).patternLine("X X").patternLine("X X").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.IRON_CHESTPLATE).key('X', Items.IRON_INGOT).patternLine("X X").patternLine("XXX").patternLine("XXX").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.IRON_DOOR, 3).key('#', Items.IRON_INGOT).patternLine("##").patternLine("##").patternLine("##").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.IRON_HELMET).key('X', Items.IRON_INGOT).patternLine("XXX").patternLine("X X").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.IRON_HOE).key('#', Items.STICK).key('X', Items.IRON_INGOT).patternLine("XX").patternLine(" #").patternLine(" #").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.IRON_INGOT, 9).addIngredient(Blocks.IRON_BLOCK).setGroup("iron_ingot").addCriterion("has_iron_block", hasItem(Blocks.IRON_BLOCK)).build(p_200404_0_, "iron_ingot_from_iron_block");
      ShapedRecipeBuilder.shapedRecipe(Items.IRON_INGOT).key('#', Items.IRON_NUGGET).patternLine("###").patternLine("###").patternLine("###").setGroup("iron_ingot").addCriterion("has_iron_nugget", hasItem(Items.IRON_NUGGET)).build(p_200404_0_, "iron_ingot_from_nuggets");
      ShapedRecipeBuilder.shapedRecipe(Items.IRON_LEGGINGS).key('X', Items.IRON_INGOT).patternLine("XXX").patternLine("X X").patternLine("X X").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.IRON_NUGGET, 9).addIngredient(Items.IRON_INGOT).addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.IRON_PICKAXE).key('#', Items.STICK).key('X', Items.IRON_INGOT).patternLine("XXX").patternLine(" # ").patternLine(" # ").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.IRON_SHOVEL).key('#', Items.STICK).key('X', Items.IRON_INGOT).patternLine("X").patternLine("#").patternLine("#").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.IRON_SWORD).key('#', Items.STICK).key('X', Items.IRON_INGOT).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.IRON_TRAPDOOR).key('#', Items.IRON_INGOT).patternLine("##").patternLine("##").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.ITEM_FRAME).key('#', Items.STICK).key('X', Items.LEATHER).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_leather", hasItem(Items.LEATHER)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.JUKEBOX).key('#', ItemTags.PLANKS).key('X', Items.DIAMOND).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_diamond", hasItem(Items.DIAMOND)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.LADDER, 3).key('#', Items.STICK).patternLine("# #").patternLine("###").patternLine("# #").addCriterion("has_stick", hasItem(Items.STICK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.LAPIS_BLOCK).key('#', Items.LAPIS_LAZULI).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_lapis", hasItem(Items.LAPIS_LAZULI)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.LAPIS_LAZULI, 9).addIngredient(Blocks.LAPIS_BLOCK).addCriterion("has_lapis_block", hasItem(Blocks.LAPIS_BLOCK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.LEAD, 2).key('~', Items.STRING).key('O', Items.SLIME_BALL).patternLine("~~ ").patternLine("~O ").patternLine("  ~").addCriterion("has_slime_ball", hasItem(Items.SLIME_BALL)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.LEATHER).key('#', Items.RABBIT_HIDE).patternLine("##").patternLine("##").addCriterion("has_rabbit_hide", hasItem(Items.RABBIT_HIDE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.LEATHER_BOOTS).key('X', Items.LEATHER).patternLine("X X").patternLine("X X").addCriterion("has_leather", hasItem(Items.LEATHER)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.LEATHER_CHESTPLATE).key('X', Items.LEATHER).patternLine("X X").patternLine("XXX").patternLine("XXX").addCriterion("has_leather", hasItem(Items.LEATHER)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.LEATHER_HELMET).key('X', Items.LEATHER).patternLine("XXX").patternLine("X X").addCriterion("has_leather", hasItem(Items.LEATHER)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.LEATHER_LEGGINGS).key('X', Items.LEATHER).patternLine("XXX").patternLine("X X").patternLine("X X").addCriterion("has_leather", hasItem(Items.LEATHER)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.LEATHER_HORSE_ARMOR).key('X', Items.LEATHER).patternLine("X X").patternLine("XXX").patternLine("X X").addCriterion("has_leather", hasItem(Items.LEATHER)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.LECTERN).key('S', ItemTags.WOODEN_SLABS).key('B', Blocks.BOOKSHELF).patternLine("SSS").patternLine(" B ").patternLine(" S ").addCriterion("has_book", hasItem(Items.BOOK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.LEVER).key('#', Blocks.COBBLESTONE).key('X', Items.STICK).patternLine("X").patternLine("#").addCriterion("has_cobblestone", hasItem(Blocks.COBBLESTONE)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_BLUE_DYE).addIngredient(Blocks.BLUE_ORCHID).setGroup("light_blue_dye").addCriterion("has_red_flower", hasItem(Blocks.BLUE_ORCHID)).build(p_200404_0_, "light_blue_dye_from_blue_orchid");
      ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_BLUE_DYE, 2).addIngredient(Items.BLUE_DYE).addIngredient(Items.WHITE_DYE).setGroup("light_blue_dye").addCriterion("has_blue_dye", hasItem(Items.BLUE_DYE)).addCriterion("has_white_dye", hasItem(Items.WHITE_DYE)).build(p_200404_0_, "light_blue_dye_from_blue_white_dye");
      ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_GRAY_DYE).addIngredient(Blocks.AZURE_BLUET).setGroup("light_gray_dye").addCriterion("has_red_flower", hasItem(Blocks.AZURE_BLUET)).build(p_200404_0_, "light_gray_dye_from_azure_bluet");
      ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_GRAY_DYE, 2).addIngredient(Items.GRAY_DYE).addIngredient(Items.WHITE_DYE).setGroup("light_gray_dye").addCriterion("has_gray_dye", hasItem(Items.GRAY_DYE)).addCriterion("has_white_dye", hasItem(Items.WHITE_DYE)).build(p_200404_0_, "light_gray_dye_from_gray_white_dye");
      ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_GRAY_DYE, 3).addIngredient(Items.BLACK_DYE).addIngredient(Items.WHITE_DYE, 2).setGroup("light_gray_dye").addCriterion("has_white_dye", hasItem(Items.WHITE_DYE)).addCriterion("has_black_dye", hasItem(Items.BLACK_DYE)).build(p_200404_0_, "light_gray_dye_from_black_white_dye");
      ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_GRAY_DYE).addIngredient(Blocks.OXEYE_DAISY).setGroup("light_gray_dye").addCriterion("has_red_flower", hasItem(Blocks.OXEYE_DAISY)).build(p_200404_0_, "light_gray_dye_from_oxeye_daisy");
      ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_GRAY_DYE).addIngredient(Blocks.WHITE_TULIP).setGroup("light_gray_dye").addCriterion("has_red_flower", hasItem(Blocks.WHITE_TULIP)).build(p_200404_0_, "light_gray_dye_from_white_tulip");
      ShapedRecipeBuilder.shapedRecipe(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE).key('#', Items.GOLD_INGOT).patternLine("##").addCriterion("has_gold_ingot", hasItem(Items.GOLD_INGOT)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.LIME_DYE, 2).addIngredient(Items.GREEN_DYE).addIngredient(Items.WHITE_DYE).addCriterion("has_green_dye", hasItem(Items.GREEN_DYE)).addCriterion("has_white_dye", hasItem(Items.WHITE_DYE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.JACK_O_LANTERN).key('A', Blocks.CARVED_PUMPKIN).key('B', Blocks.TORCH).patternLine("A").patternLine("B").addCriterion("has_carved_pumpkin", hasItem(Blocks.CARVED_PUMPKIN)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.MAGENTA_DYE).addIngredient(Blocks.ALLIUM).setGroup("magenta_dye").addCriterion("has_red_flower", hasItem(Blocks.ALLIUM)).build(p_200404_0_, "magenta_dye_from_allium");
      ShapelessRecipeBuilder.shapelessRecipe(Items.MAGENTA_DYE, 4).addIngredient(Items.BLUE_DYE).addIngredient(Items.RED_DYE, 2).addIngredient(Items.WHITE_DYE).setGroup("magenta_dye").addCriterion("has_blue_dye", hasItem(Items.BLUE_DYE)).addCriterion("has_rose_red", hasItem(Items.RED_DYE)).addCriterion("has_white_dye", hasItem(Items.WHITE_DYE)).build(p_200404_0_, "magenta_dye_from_blue_red_white_dye");
      ShapelessRecipeBuilder.shapelessRecipe(Items.MAGENTA_DYE, 3).addIngredient(Items.BLUE_DYE).addIngredient(Items.RED_DYE).addIngredient(Items.PINK_DYE).setGroup("magenta_dye").addCriterion("has_pink_dye", hasItem(Items.PINK_DYE)).addCriterion("has_blue_dye", hasItem(Items.BLUE_DYE)).addCriterion("has_red_dye", hasItem(Items.RED_DYE)).build(p_200404_0_, "magenta_dye_from_blue_red_pink");
      ShapelessRecipeBuilder.shapelessRecipe(Items.MAGENTA_DYE, 2).addIngredient(Blocks.LILAC).setGroup("magenta_dye").addCriterion("has_double_plant", hasItem(Blocks.LILAC)).build(p_200404_0_, "magenta_dye_from_lilac");
      ShapelessRecipeBuilder.shapelessRecipe(Items.MAGENTA_DYE, 2).addIngredient(Items.PURPLE_DYE).addIngredient(Items.PINK_DYE).setGroup("magenta_dye").addCriterion("has_pink_dye", hasItem(Items.PINK_DYE)).addCriterion("has_purple_dye", hasItem(Items.PURPLE_DYE)).build(p_200404_0_, "magenta_dye_from_purple_and_pink");
      ShapedRecipeBuilder.shapedRecipe(Blocks.MAGMA_BLOCK).key('#', Items.MAGMA_CREAM).patternLine("##").patternLine("##").addCriterion("has_magma_cream", hasItem(Items.MAGMA_CREAM)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.MAGMA_CREAM).addIngredient(Items.BLAZE_POWDER).addIngredient(Items.SLIME_BALL).addCriterion("has_blaze_powder", hasItem(Items.BLAZE_POWDER)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.MAP).key('#', Items.PAPER).key('X', Items.COMPASS).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_compass", hasItem(Items.COMPASS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.MELON).key('M', Items.MELON_SLICE).patternLine("MMM").patternLine("MMM").patternLine("MMM").addCriterion("has_melon", hasItem(Items.MELON_SLICE)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.MELON_SEEDS).addIngredient(Items.MELON_SLICE).addCriterion("has_melon", hasItem(Items.MELON_SLICE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.MINECART).key('#', Items.IRON_INGOT).patternLine("# #").patternLine("###").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Blocks.MOSSY_COBBLESTONE).addIngredient(Blocks.COBBLESTONE).addIngredient(Blocks.VINE).addCriterion("has_vine", hasItem(Blocks.VINE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.MOSSY_COBBLESTONE_WALL, 6).key('#', Blocks.MOSSY_COBBLESTONE).patternLine("###").patternLine("###").addCriterion("has_mossy_cobblestone", hasItem(Blocks.MOSSY_COBBLESTONE)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Blocks.MOSSY_STONE_BRICKS).addIngredient(Blocks.STONE_BRICKS).addIngredient(Blocks.VINE).addCriterion("has_mossy_cobblestone", hasItem(Blocks.MOSSY_COBBLESTONE)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.MUSHROOM_STEW).addIngredient(Blocks.BROWN_MUSHROOM).addIngredient(Blocks.RED_MUSHROOM).addIngredient(Items.BOWL).addCriterion("has_mushroom_stew", hasItem(Items.MUSHROOM_STEW)).addCriterion("has_bowl", hasItem(Items.BOWL)).addCriterion("has_brown_mushroom", hasItem(Blocks.BROWN_MUSHROOM)).addCriterion("has_red_mushroom", hasItem(Blocks.RED_MUSHROOM)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.NETHER_BRICKS).key('N', Items.NETHER_BRICK).patternLine("NN").patternLine("NN").addCriterion("has_netherbrick", hasItem(Items.NETHER_BRICK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.NETHER_BRICK_FENCE, 6).key('#', Blocks.NETHER_BRICKS).key('-', Items.NETHER_BRICK).patternLine("#-#").patternLine("#-#").addCriterion("has_nether_brick", hasItem(Blocks.NETHER_BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.NETHER_BRICK_SLAB, 6).key('#', Blocks.NETHER_BRICKS).patternLine("###").addCriterion("has_nether_brick", hasItem(Blocks.NETHER_BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.NETHER_BRICK_STAIRS, 4).key('#', Blocks.NETHER_BRICKS).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_nether_brick", hasItem(Blocks.NETHER_BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.NETHER_WART_BLOCK).key('#', Items.NETHER_WART).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_nether_wart", hasItem(Items.NETHER_WART)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.NOTE_BLOCK).key('#', ItemTags.PLANKS).key('X', Items.REDSTONE).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_redstone", hasItem(Items.REDSTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.OBSERVER).key('Q', Items.QUARTZ).key('R', Items.REDSTONE).key('#', Blocks.COBBLESTONE).patternLine("###").patternLine("RRQ").patternLine("###").addCriterion("has_quartz", hasItem(Items.QUARTZ)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.ORANGE_DYE).addIngredient(Blocks.ORANGE_TULIP).setGroup("orange_dye").addCriterion("has_red_flower", hasItem(Blocks.ORANGE_TULIP)).build(p_200404_0_, "orange_dye_from_orange_tulip");
      ShapelessRecipeBuilder.shapelessRecipe(Items.ORANGE_DYE, 2).addIngredient(Items.RED_DYE).addIngredient(Items.YELLOW_DYE).setGroup("orange_dye").addCriterion("has_red_dye", hasItem(Items.RED_DYE)).addCriterion("has_yellow_dye", hasItem(Items.YELLOW_DYE)).build(p_200404_0_, "orange_dye_from_red_yellow");
      ShapedRecipeBuilder.shapedRecipe(Items.PAINTING).key('#', Items.STICK).key('X', Ingredient.fromTag(ItemTags.WOOL)).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_wool", hasItem(ItemTags.WOOL)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.PAPER, 3).key('#', Blocks.SUGAR_CANE).patternLine("###").addCriterion("has_reeds", hasItem(Blocks.SUGAR_CANE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.QUARTZ_PILLAR, 2).key('#', Blocks.QUARTZ_BLOCK).patternLine("#").patternLine("#").addCriterion("has_chiseled_quartz_block", hasItem(Blocks.CHISELED_QUARTZ_BLOCK)).addCriterion("has_quartz_block", hasItem(Blocks.QUARTZ_BLOCK)).addCriterion("has_quartz_pillar", hasItem(Blocks.QUARTZ_PILLAR)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Blocks.PACKED_ICE).addIngredient(Blocks.ICE, 9).addCriterion("has_ice", hasItem(Blocks.ICE)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.PINK_DYE, 2).addIngredient(Blocks.PEONY).setGroup("pink_dye").addCriterion("has_double_plant", hasItem(Blocks.PEONY)).build(p_200404_0_, "pink_dye_from_peony");
      ShapelessRecipeBuilder.shapelessRecipe(Items.PINK_DYE).addIngredient(Blocks.PINK_TULIP).setGroup("pink_dye").addCriterion("has_red_flower", hasItem(Blocks.PINK_TULIP)).build(p_200404_0_, "pink_dye_from_pink_tulip");
      ShapelessRecipeBuilder.shapelessRecipe(Items.PINK_DYE, 2).addIngredient(Items.RED_DYE).addIngredient(Items.WHITE_DYE).setGroup("pink_dye").addCriterion("has_white_dye", hasItem(Items.WHITE_DYE)).addCriterion("has_red_dye", hasItem(Items.RED_DYE)).build(p_200404_0_, "pink_dye_from_red_white_dye");
      ShapedRecipeBuilder.shapedRecipe(Blocks.PISTON).key('R', Items.REDSTONE).key('#', Blocks.COBBLESTONE).key('T', ItemTags.PLANKS).key('X', Items.IRON_INGOT).patternLine("TTT").patternLine("#X#").patternLine("#R#").addCriterion("has_redstone", hasItem(Items.REDSTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235338_cP_, 4).key('S', Blocks.field_235337_cO_).patternLine("SS").patternLine("SS").addCriterion("has_basalt", hasItem(Blocks.field_235337_cO_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_GRANITE, 4).key('S', Blocks.GRANITE).patternLine("SS").patternLine("SS").addCriterion("has_stone", hasItem(Blocks.GRANITE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_DIORITE, 4).key('S', Blocks.DIORITE).patternLine("SS").patternLine("SS").addCriterion("has_stone", hasItem(Blocks.DIORITE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_ANDESITE, 4).key('S', Blocks.ANDESITE).patternLine("SS").patternLine("SS").addCriterion("has_stone", hasItem(Blocks.ANDESITE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE).key('S', Items.PRISMARINE_SHARD).patternLine("SS").patternLine("SS").addCriterion("has_prismarine_shard", hasItem(Items.PRISMARINE_SHARD)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE_BRICKS).key('S', Items.PRISMARINE_SHARD).patternLine("SSS").patternLine("SSS").patternLine("SSS").addCriterion("has_prismarine_shard", hasItem(Items.PRISMARINE_SHARD)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE_SLAB, 6).key('#', Blocks.PRISMARINE).patternLine("###").addCriterion("has_prismarine", hasItem(Blocks.PRISMARINE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE_BRICK_SLAB, 6).key('#', Blocks.PRISMARINE_BRICKS).patternLine("###").addCriterion("has_prismarine_bricks", hasItem(Blocks.PRISMARINE_BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.DARK_PRISMARINE_SLAB, 6).key('#', Blocks.DARK_PRISMARINE).patternLine("###").addCriterion("has_dark_prismarine", hasItem(Blocks.DARK_PRISMARINE)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.PUMPKIN_PIE).addIngredient(Blocks.PUMPKIN).addIngredient(Items.SUGAR).addIngredient(Items.EGG).addCriterion("has_carved_pumpkin", hasItem(Blocks.CARVED_PUMPKIN)).addCriterion("has_pumpkin", hasItem(Blocks.PUMPKIN)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.PUMPKIN_SEEDS, 4).addIngredient(Blocks.PUMPKIN).addCriterion("has_pumpkin", hasItem(Blocks.PUMPKIN)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.PURPLE_DYE, 2).addIngredient(Items.BLUE_DYE).addIngredient(Items.RED_DYE).addCriterion("has_blue_dye", hasItem(Items.BLUE_DYE)).addCriterion("has_red_dye", hasItem(Items.RED_DYE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.SHULKER_BOX).key('#', Blocks.CHEST).key('-', Items.SHULKER_SHELL).patternLine("-").patternLine("#").patternLine("-").addCriterion("has_shulker_shell", hasItem(Items.SHULKER_SHELL)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.PURPUR_BLOCK, 4).key('F', Items.POPPED_CHORUS_FRUIT).patternLine("FF").patternLine("FF").addCriterion("has_chorus_fruit_popped", hasItem(Items.POPPED_CHORUS_FRUIT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.PURPUR_PILLAR).key('#', Blocks.PURPUR_SLAB).patternLine("#").patternLine("#").addCriterion("has_purpur_block", hasItem(Blocks.PURPUR_BLOCK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.PURPUR_SLAB, 6).key('#', Ingredient.fromItems(Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR)).patternLine("###").addCriterion("has_purpur_block", hasItem(Blocks.PURPUR_BLOCK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.PURPUR_STAIRS, 4).key('#', Ingredient.fromItems(Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR)).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_purpur_block", hasItem(Blocks.PURPUR_BLOCK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.QUARTZ_BLOCK).key('#', Items.QUARTZ).patternLine("##").patternLine("##").addCriterion("has_quartz", hasItem(Items.QUARTZ)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235395_nI_, 4).key('#', Blocks.QUARTZ_BLOCK).patternLine("##").patternLine("##").addCriterion("has_quartz_block", hasItem(Blocks.QUARTZ_BLOCK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.QUARTZ_SLAB, 6).key('#', Ingredient.fromItems(Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR)).patternLine("###").addCriterion("has_chiseled_quartz_block", hasItem(Blocks.CHISELED_QUARTZ_BLOCK)).addCriterion("has_quartz_block", hasItem(Blocks.QUARTZ_BLOCK)).addCriterion("has_quartz_pillar", hasItem(Blocks.QUARTZ_PILLAR)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.QUARTZ_STAIRS, 4).key('#', Ingredient.fromItems(Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR)).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_chiseled_quartz_block", hasItem(Blocks.CHISELED_QUARTZ_BLOCK)).addCriterion("has_quartz_block", hasItem(Blocks.QUARTZ_BLOCK)).addCriterion("has_quartz_pillar", hasItem(Blocks.QUARTZ_PILLAR)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.RABBIT_STEW).addIngredient(Items.BAKED_POTATO).addIngredient(Items.COOKED_RABBIT).addIngredient(Items.BOWL).addIngredient(Items.CARROT).addIngredient(Blocks.BROWN_MUSHROOM).setGroup("rabbit_stew").addCriterion("has_cooked_rabbit", hasItem(Items.COOKED_RABBIT)).build(p_200404_0_, "rabbit_stew_from_brown_mushroom");
      ShapelessRecipeBuilder.shapelessRecipe(Items.RABBIT_STEW).addIngredient(Items.BAKED_POTATO).addIngredient(Items.COOKED_RABBIT).addIngredient(Items.BOWL).addIngredient(Items.CARROT).addIngredient(Blocks.RED_MUSHROOM).setGroup("rabbit_stew").addCriterion("has_cooked_rabbit", hasItem(Items.COOKED_RABBIT)).build(p_200404_0_, "rabbit_stew_from_red_mushroom");
      ShapedRecipeBuilder.shapedRecipe(Blocks.RAIL, 16).key('#', Items.STICK).key('X', Items.IRON_INGOT).patternLine("X X").patternLine("X#X").patternLine("X X").addCriterion("has_minecart", hasItem(Items.MINECART)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.REDSTONE, 9).addIngredient(Blocks.REDSTONE_BLOCK).addCriterion("has_redstone_block", hasItem(Blocks.REDSTONE_BLOCK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.REDSTONE_BLOCK).key('#', Items.REDSTONE).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_redstone", hasItem(Items.REDSTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.REDSTONE_LAMP).key('R', Items.REDSTONE).key('G', Blocks.GLOWSTONE).patternLine(" R ").patternLine("RGR").patternLine(" R ").addCriterion("has_glowstone", hasItem(Blocks.GLOWSTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.REDSTONE_TORCH).key('#', Items.STICK).key('X', Items.REDSTONE).patternLine("X").patternLine("#").addCriterion("has_redstone", hasItem(Items.REDSTONE)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.RED_DYE).addIngredient(Items.BEETROOT).setGroup("red_dye").addCriterion("has_beetroot", hasItem(Items.BEETROOT)).build(p_200404_0_, "red_dye_from_beetroot");
      ShapelessRecipeBuilder.shapelessRecipe(Items.RED_DYE).addIngredient(Blocks.POPPY).setGroup("red_dye").addCriterion("has_red_flower", hasItem(Blocks.POPPY)).build(p_200404_0_, "red_dye_from_poppy");
      ShapelessRecipeBuilder.shapelessRecipe(Items.RED_DYE, 2).addIngredient(Blocks.ROSE_BUSH).setGroup("red_dye").addCriterion("has_double_plant", hasItem(Blocks.ROSE_BUSH)).build(p_200404_0_, "red_dye_from_rose_bush");
      ShapelessRecipeBuilder.shapelessRecipe(Items.RED_DYE).addIngredient(Blocks.RED_TULIP).setGroup("red_dye").addCriterion("has_red_flower", hasItem(Blocks.RED_TULIP)).build(p_200404_0_, "red_dye_from_tulip");
      ShapedRecipeBuilder.shapedRecipe(Blocks.RED_NETHER_BRICKS).key('W', Items.NETHER_WART).key('N', Items.NETHER_BRICK).patternLine("NW").patternLine("WN").addCriterion("has_nether_wart", hasItem(Items.NETHER_WART)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.RED_SANDSTONE).key('#', Blocks.RED_SAND).patternLine("##").patternLine("##").addCriterion("has_sand", hasItem(Blocks.RED_SAND)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.RED_SANDSTONE_SLAB, 6).key('#', Ingredient.fromItems(Blocks.RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE)).patternLine("###").addCriterion("has_red_sandstone", hasItem(Blocks.RED_SANDSTONE)).addCriterion("has_chiseled_red_sandstone", hasItem(Blocks.CHISELED_RED_SANDSTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.CUT_RED_SANDSTONE_SLAB, 6).key('#', Blocks.CUT_RED_SANDSTONE).patternLine("###").addCriterion("has_cut_red_sandstone", hasItem(Blocks.CUT_RED_SANDSTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.RED_SANDSTONE_STAIRS, 4).key('#', Ingredient.fromItems(Blocks.RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE)).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_red_sandstone", hasItem(Blocks.RED_SANDSTONE)).addCriterion("has_chiseled_red_sandstone", hasItem(Blocks.CHISELED_RED_SANDSTONE)).addCriterion("has_cut_red_sandstone", hasItem(Blocks.CUT_RED_SANDSTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.REPEATER).key('#', Blocks.REDSTONE_TORCH).key('X', Items.REDSTONE).key('I', Blocks.STONE).patternLine("#X#").patternLine("III").addCriterion("has_redstone_torch", hasItem(Blocks.REDSTONE_TORCH)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.SANDSTONE).key('#', Blocks.SAND).patternLine("##").patternLine("##").addCriterion("has_sand", hasItem(Blocks.SAND)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.SANDSTONE_SLAB, 6).key('#', Ingredient.fromItems(Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE)).patternLine("###").addCriterion("has_sandstone", hasItem(Blocks.SANDSTONE)).addCriterion("has_chiseled_sandstone", hasItem(Blocks.CHISELED_SANDSTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.CUT_SANDSTONE_SLAB, 6).key('#', Blocks.CUT_SANDSTONE).patternLine("###").addCriterion("has_cut_sandstone", hasItem(Blocks.CUT_SANDSTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.SANDSTONE_STAIRS, 4).key('#', Ingredient.fromItems(Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE)).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_sandstone", hasItem(Blocks.SANDSTONE)).addCriterion("has_chiseled_sandstone", hasItem(Blocks.CHISELED_SANDSTONE)).addCriterion("has_cut_sandstone", hasItem(Blocks.CUT_SANDSTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.SEA_LANTERN).key('S', Items.PRISMARINE_SHARD).key('C', Items.PRISMARINE_CRYSTALS).patternLine("SCS").patternLine("CCC").patternLine("SCS").addCriterion("has_prismarine_crystals", hasItem(Items.PRISMARINE_CRYSTALS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.SHEARS).key('#', Items.IRON_INGOT).patternLine(" #").patternLine("# ").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.SHIELD).key('W', ItemTags.PLANKS).key('o', Items.IRON_INGOT).patternLine("WoW").patternLine("WWW").patternLine(" W ").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.SLIME_BLOCK).key('#', Items.SLIME_BALL).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_slime_ball", hasItem(Items.SLIME_BALL)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.SLIME_BALL, 9).addIngredient(Blocks.SLIME_BLOCK).addCriterion("has_slime", hasItem(Blocks.SLIME_BLOCK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.CUT_RED_SANDSTONE, 4).key('#', Blocks.RED_SANDSTONE).patternLine("##").patternLine("##").addCriterion("has_red_sandstone", hasItem(Blocks.RED_SANDSTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.CUT_SANDSTONE, 4).key('#', Blocks.SANDSTONE).patternLine("##").patternLine("##").addCriterion("has_sandstone", hasItem(Blocks.SANDSTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.SNOW_BLOCK).key('#', Items.SNOWBALL).patternLine("##").patternLine("##").addCriterion("has_snowball", hasItem(Items.SNOWBALL)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.SNOW, 6).key('#', Blocks.SNOW_BLOCK).patternLine("###").addCriterion("has_snowball", hasItem(Items.SNOWBALL)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235367_mf_).key('L', ItemTags.LOGS).key('S', Items.STICK).key('#', ItemTags.field_232906_Q_).patternLine(" S ").patternLine("S#S").patternLine("LLL").addCriterion("has_stick", hasItem(Items.STICK)).addCriterion("has_soul_sand", hasItem(ItemTags.field_232906_Q_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.GLISTERING_MELON_SLICE).key('#', Items.GOLD_NUGGET).key('X', Items.MELON_SLICE).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_melon", hasItem(Items.MELON_SLICE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.SPECTRAL_ARROW, 2).key('#', Items.GLOWSTONE_DUST).key('X', Items.ARROW).patternLine(" # ").patternLine("#X#").patternLine(" # ").addCriterion("has_glowstone_dust", hasItem(Items.GLOWSTONE_DUST)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.STICK, 4).key('#', ItemTags.PLANKS).patternLine("#").patternLine("#").setGroup("sticks").addCriterion("has_planks", hasItem(ItemTags.PLANKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.STICK, 1).key('#', Blocks.BAMBOO).patternLine("#").patternLine("#").setGroup("sticks").addCriterion("has_bamboo", hasItem(Blocks.BAMBOO)).build(p_200404_0_, "stick_from_bamboo_item");
      ShapedRecipeBuilder.shapedRecipe(Blocks.STICKY_PISTON).key('P', Blocks.PISTON).key('S', Items.SLIME_BALL).patternLine("S").patternLine("P").addCriterion("has_slime_ball", hasItem(Items.SLIME_BALL)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_BRICKS, 4).key('#', Blocks.STONE).patternLine("##").patternLine("##").addCriterion("has_stone", hasItem(Blocks.STONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.STONE_AXE).key('#', Items.STICK).key('X', ItemTags.field_232909_aa_).patternLine("XX").patternLine("X#").patternLine(" #").addCriterion("has_cobblestone", hasItem(ItemTags.field_232909_aa_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_BRICK_SLAB, 6).key('#', Blocks.STONE_BRICKS).patternLine("###").addCriterion("has_stone_bricks", hasItem(ItemTags.STONE_BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_BRICK_STAIRS, 4).key('#', Blocks.STONE_BRICKS).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_stone_bricks", hasItem(ItemTags.STONE_BRICKS)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Blocks.STONE_BUTTON).addIngredient(Blocks.STONE).addCriterion("has_stone", hasItem(Blocks.STONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.STONE_HOE).key('#', Items.STICK).key('X', ItemTags.field_232909_aa_).patternLine("XX").patternLine(" #").patternLine(" #").addCriterion("has_cobblestone", hasItem(ItemTags.field_232909_aa_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.STONE_PICKAXE).key('#', Items.STICK).key('X', ItemTags.field_232909_aa_).patternLine("XXX").patternLine(" # ").patternLine(" # ").addCriterion("has_cobblestone", hasItem(ItemTags.field_232909_aa_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_PRESSURE_PLATE).key('#', Blocks.STONE).patternLine("##").addCriterion("has_stone", hasItem(Blocks.STONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.STONE_SHOVEL).key('#', Items.STICK).key('X', ItemTags.field_232909_aa_).patternLine("X").patternLine("#").patternLine("#").addCriterion("has_cobblestone", hasItem(ItemTags.field_232909_aa_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_SLAB, 6).key('#', Blocks.STONE).patternLine("###").addCriterion("has_stone", hasItem(Blocks.STONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.SMOOTH_STONE_SLAB, 6).key('#', Blocks.SMOOTH_STONE).patternLine("###").addCriterion("has_smooth_stone", hasItem(Blocks.SMOOTH_STONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.COBBLESTONE_STAIRS, 4).key('#', Blocks.COBBLESTONE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_cobblestone", hasItem(Blocks.COBBLESTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.STONE_SWORD).key('#', Items.STICK).key('X', ItemTags.field_232909_aa_).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_cobblestone", hasItem(ItemTags.field_232909_aa_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.WHITE_WOOL).key('#', Items.STRING).patternLine("##").patternLine("##").addCriterion("has_string", hasItem(Items.STRING)).build(p_200404_0_, "white_wool_from_string");
      ShapelessRecipeBuilder.shapelessRecipe(Items.SUGAR).addIngredient(Blocks.SUGAR_CANE).setGroup("sugar").addCriterion("has_reeds", hasItem(Blocks.SUGAR_CANE)).build(p_200404_0_, "sugar_from_sugar_cane");
      ShapelessRecipeBuilder.shapelessRecipe(Items.SUGAR, 3).addIngredient(Items.HONEY_BOTTLE).setGroup("sugar").addCriterion("has_honey_bottle", hasItem(Items.HONEY_BOTTLE)).build(p_200404_0_, "sugar_from_honey_bottle");
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235396_nb_).key('H', Items.HAY_BLOCK).key('R', Items.REDSTONE).patternLine(" R ").patternLine("RHR").patternLine(" R ").addCriterion("has_redstone", hasItem(Items.REDSTONE)).addCriterion("has_hay_block", hasItem(Blocks.HAY_BLOCK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.TNT).key('#', Ingredient.fromItems(Blocks.SAND, Blocks.RED_SAND)).key('X', Items.GUNPOWDER).patternLine("X#X").patternLine("#X#").patternLine("X#X").addCriterion("has_gunpowder", hasItem(Items.GUNPOWDER)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.TNT_MINECART).key('A', Blocks.TNT).key('B', Items.MINECART).patternLine("A").patternLine("B").addCriterion("has_minecart", hasItem(Items.MINECART)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.TORCH, 4).key('#', Items.STICK).key('X', Ingredient.fromItems(Items.COAL, Items.CHARCOAL)).patternLine("X").patternLine("#").addCriterion("has_stone_pickaxe", hasItem(Items.STONE_PICKAXE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235339_cQ_, 4).key('X', Ingredient.fromItems(Items.COAL, Items.CHARCOAL)).key('#', Items.STICK).key('S', ItemTags.field_232906_Q_).patternLine("X").patternLine("#").patternLine("S").addCriterion("has_soul_sand", hasItem(ItemTags.field_232906_Q_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.LANTERN).key('#', Items.TORCH).key('X', Items.IRON_NUGGET).patternLine("XXX").patternLine("X#X").patternLine("XXX").addCriterion("has_iron_nugget", hasItem(Items.IRON_NUGGET)).addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235366_md_).key('#', Items.field_234737_dp_).key('X', Items.IRON_NUGGET).patternLine("XXX").patternLine("X#X").patternLine("XXX").addCriterion("has_soul_torch", hasItem(Items.field_234737_dp_)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Blocks.TRAPPED_CHEST).addIngredient(Blocks.CHEST).addIngredient(Blocks.TRIPWIRE_HOOK).addCriterion("has_tripwire_hook", hasItem(Blocks.TRIPWIRE_HOOK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.TRIPWIRE_HOOK, 2).key('#', ItemTags.PLANKS).key('S', Items.STICK).key('I', Items.IRON_INGOT).patternLine("I").patternLine("S").patternLine("#").addCriterion("has_string", hasItem(Items.STRING)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.TURTLE_HELMET).key('X', Items.SCUTE).patternLine("XXX").patternLine("X X").addCriterion("has_scute", hasItem(Items.SCUTE)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.WHEAT, 9).addIngredient(Blocks.HAY_BLOCK).addCriterion("has_hay_block", hasItem(Blocks.HAY_BLOCK)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.WHITE_DYE).addIngredient(Items.BONE_MEAL).setGroup("white_dye").addCriterion("has_bone_meal", hasItem(Items.BONE_MEAL)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.WHITE_DYE).addIngredient(Blocks.LILY_OF_THE_VALLEY).setGroup("white_dye").addCriterion("has_white_flower", hasItem(Blocks.LILY_OF_THE_VALLEY)).build(p_200404_0_, "white_dye_from_lily_of_the_valley");
      ShapedRecipeBuilder.shapedRecipe(Items.WOODEN_AXE).key('#', Items.STICK).key('X', ItemTags.PLANKS).patternLine("XX").patternLine("X#").patternLine(" #").addCriterion("has_stick", hasItem(Items.STICK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.WOODEN_HOE).key('#', Items.STICK).key('X', ItemTags.PLANKS).patternLine("XX").patternLine(" #").patternLine(" #").addCriterion("has_stick", hasItem(Items.STICK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.WOODEN_PICKAXE).key('#', Items.STICK).key('X', ItemTags.PLANKS).patternLine("XXX").patternLine(" # ").patternLine(" # ").addCriterion("has_stick", hasItem(Items.STICK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.WOODEN_SHOVEL).key('#', Items.STICK).key('X', ItemTags.PLANKS).patternLine("X").patternLine("#").patternLine("#").addCriterion("has_stick", hasItem(Items.STICK)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Items.WOODEN_SWORD).key('#', Items.STICK).key('X', ItemTags.PLANKS).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_stick", hasItem(Items.STICK)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.WRITABLE_BOOK).addIngredient(Items.BOOK).addIngredient(Items.INK_SAC).addIngredient(Items.FEATHER).addCriterion("has_book", hasItem(Items.BOOK)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.YELLOW_DYE).addIngredient(Blocks.DANDELION).setGroup("yellow_dye").addCriterion("has_yellow_flower", hasItem(Blocks.DANDELION)).build(p_200404_0_, "yellow_dye_from_dandelion");
      ShapelessRecipeBuilder.shapelessRecipe(Items.YELLOW_DYE, 2).addIngredient(Blocks.SUNFLOWER).setGroup("yellow_dye").addCriterion("has_double_plant", hasItem(Blocks.SUNFLOWER)).build(p_200404_0_, "yellow_dye_from_sunflower");
      ShapelessRecipeBuilder.shapelessRecipe(Items.DRIED_KELP, 9).addIngredient(Blocks.DRIED_KELP_BLOCK).addCriterion("has_dried_kelp_block", hasItem(Blocks.DRIED_KELP_BLOCK)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Blocks.DRIED_KELP_BLOCK).addIngredient(Items.DRIED_KELP, 9).addCriterion("has_dried_kelp", hasItem(Items.DRIED_KELP)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.CONDUIT).key('#', Items.NAUTILUS_SHELL).key('X', Items.HEART_OF_THE_SEA).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_nautilus_core", hasItem(Items.HEART_OF_THE_SEA)).addCriterion("has_nautilus_shell", hasItem(Items.NAUTILUS_SHELL)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_GRANITE_STAIRS, 4).key('#', Blocks.POLISHED_GRANITE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_polished_granite", hasItem(Blocks.POLISHED_GRANITE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.SMOOTH_RED_SANDSTONE_STAIRS, 4).key('#', Blocks.SMOOTH_RED_SANDSTONE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_smooth_red_sandstone", hasItem(Blocks.SMOOTH_RED_SANDSTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.MOSSY_STONE_BRICK_STAIRS, 4).key('#', Blocks.MOSSY_STONE_BRICKS).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_mossy_stone_bricks", hasItem(Blocks.MOSSY_STONE_BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_DIORITE_STAIRS, 4).key('#', Blocks.POLISHED_DIORITE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_polished_diorite", hasItem(Blocks.POLISHED_DIORITE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.MOSSY_COBBLESTONE_STAIRS, 4).key('#', Blocks.MOSSY_COBBLESTONE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_mossy_cobblestone", hasItem(Blocks.MOSSY_COBBLESTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.END_STONE_BRICK_STAIRS, 4).key('#', Blocks.END_STONE_BRICKS).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_end_stone_bricks", hasItem(Blocks.END_STONE_BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_STAIRS, 4).key('#', Blocks.STONE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_stone", hasItem(Blocks.STONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.SMOOTH_SANDSTONE_STAIRS, 4).key('#', Blocks.SMOOTH_SANDSTONE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_smooth_sandstone", hasItem(Blocks.SMOOTH_SANDSTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.SMOOTH_QUARTZ_STAIRS, 4).key('#', Blocks.SMOOTH_QUARTZ).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_smooth_quartz", hasItem(Blocks.SMOOTH_QUARTZ)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.GRANITE_STAIRS, 4).key('#', Blocks.GRANITE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_granite", hasItem(Blocks.GRANITE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.ANDESITE_STAIRS, 4).key('#', Blocks.ANDESITE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_andesite", hasItem(Blocks.ANDESITE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.RED_NETHER_BRICK_STAIRS, 4).key('#', Blocks.RED_NETHER_BRICKS).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_red_nether_bricks", hasItem(Blocks.RED_NETHER_BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_ANDESITE_STAIRS, 4).key('#', Blocks.POLISHED_ANDESITE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_polished_andesite", hasItem(Blocks.POLISHED_ANDESITE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.DIORITE_STAIRS, 4).key('#', Blocks.DIORITE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_diorite", hasItem(Blocks.DIORITE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_GRANITE_SLAB, 6).key('#', Blocks.POLISHED_GRANITE).patternLine("###").addCriterion("has_polished_granite", hasItem(Blocks.POLISHED_GRANITE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.SMOOTH_RED_SANDSTONE_SLAB, 6).key('#', Blocks.SMOOTH_RED_SANDSTONE).patternLine("###").addCriterion("has_smooth_red_sandstone", hasItem(Blocks.SMOOTH_RED_SANDSTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.MOSSY_STONE_BRICK_SLAB, 6).key('#', Blocks.MOSSY_STONE_BRICKS).patternLine("###").addCriterion("has_mossy_stone_bricks", hasItem(Blocks.MOSSY_STONE_BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_DIORITE_SLAB, 6).key('#', Blocks.POLISHED_DIORITE).patternLine("###").addCriterion("has_polished_diorite", hasItem(Blocks.POLISHED_DIORITE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.MOSSY_COBBLESTONE_SLAB, 6).key('#', Blocks.MOSSY_COBBLESTONE).patternLine("###").addCriterion("has_mossy_cobblestone", hasItem(Blocks.MOSSY_COBBLESTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.END_STONE_BRICK_SLAB, 6).key('#', Blocks.END_STONE_BRICKS).patternLine("###").addCriterion("has_end_stone_bricks", hasItem(Blocks.END_STONE_BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.SMOOTH_SANDSTONE_SLAB, 6).key('#', Blocks.SMOOTH_SANDSTONE).patternLine("###").addCriterion("has_smooth_sandstone", hasItem(Blocks.SMOOTH_SANDSTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.SMOOTH_QUARTZ_SLAB, 6).key('#', Blocks.SMOOTH_QUARTZ).patternLine("###").addCriterion("has_smooth_quartz", hasItem(Blocks.SMOOTH_QUARTZ)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.GRANITE_SLAB, 6).key('#', Blocks.GRANITE).patternLine("###").addCriterion("has_granite", hasItem(Blocks.GRANITE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.ANDESITE_SLAB, 6).key('#', Blocks.ANDESITE).patternLine("###").addCriterion("has_andesite", hasItem(Blocks.ANDESITE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.RED_NETHER_BRICK_SLAB, 6).key('#', Blocks.RED_NETHER_BRICKS).patternLine("###").addCriterion("has_red_nether_bricks", hasItem(Blocks.RED_NETHER_BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_ANDESITE_SLAB, 6).key('#', Blocks.POLISHED_ANDESITE).patternLine("###").addCriterion("has_polished_andesite", hasItem(Blocks.POLISHED_ANDESITE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.DIORITE_SLAB, 6).key('#', Blocks.DIORITE).patternLine("###").addCriterion("has_diorite", hasItem(Blocks.DIORITE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.BRICK_WALL, 6).key('#', Blocks.BRICKS).patternLine("###").patternLine("###").addCriterion("has_bricks", hasItem(Blocks.BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE_WALL, 6).key('#', Blocks.PRISMARINE).patternLine("###").patternLine("###").addCriterion("has_prismarine", hasItem(Blocks.PRISMARINE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.RED_SANDSTONE_WALL, 6).key('#', Blocks.RED_SANDSTONE).patternLine("###").patternLine("###").addCriterion("has_red_sandstone", hasItem(Blocks.RED_SANDSTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.MOSSY_STONE_BRICK_WALL, 6).key('#', Blocks.MOSSY_STONE_BRICKS).patternLine("###").patternLine("###").addCriterion("has_mossy_stone_bricks", hasItem(Blocks.MOSSY_STONE_BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.GRANITE_WALL, 6).key('#', Blocks.GRANITE).patternLine("###").patternLine("###").addCriterion("has_granite", hasItem(Blocks.GRANITE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_BRICK_WALL, 6).key('#', Blocks.STONE_BRICKS).patternLine("###").patternLine("###").addCriterion("has_stone_bricks", hasItem(Blocks.STONE_BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.NETHER_BRICK_WALL, 6).key('#', Blocks.NETHER_BRICKS).patternLine("###").patternLine("###").addCriterion("has_nether_bricks", hasItem(Blocks.NETHER_BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.ANDESITE_WALL, 6).key('#', Blocks.ANDESITE).patternLine("###").patternLine("###").addCriterion("has_andesite", hasItem(Blocks.ANDESITE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.RED_NETHER_BRICK_WALL, 6).key('#', Blocks.RED_NETHER_BRICKS).patternLine("###").patternLine("###").addCriterion("has_red_nether_bricks", hasItem(Blocks.RED_NETHER_BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.SANDSTONE_WALL, 6).key('#', Blocks.SANDSTONE).patternLine("###").patternLine("###").addCriterion("has_sandstone", hasItem(Blocks.SANDSTONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.END_STONE_BRICK_WALL, 6).key('#', Blocks.END_STONE_BRICKS).patternLine("###").patternLine("###").addCriterion("has_end_stone_bricks", hasItem(Blocks.END_STONE_BRICKS)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.DIORITE_WALL, 6).key('#', Blocks.DIORITE).patternLine("###").patternLine("###").addCriterion("has_diorite", hasItem(Blocks.DIORITE)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.CREEPER_BANNER_PATTERN).addIngredient(Items.PAPER).addIngredient(Items.CREEPER_HEAD).addCriterion("has_creeper_head", hasItem(Items.CREEPER_HEAD)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.SKULL_BANNER_PATTERN).addIngredient(Items.PAPER).addIngredient(Items.WITHER_SKELETON_SKULL).addCriterion("has_wither_skeleton_skull", hasItem(Items.WITHER_SKELETON_SKULL)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.FLOWER_BANNER_PATTERN).addIngredient(Items.PAPER).addIngredient(Blocks.OXEYE_DAISY).addCriterion("has_oxeye_daisy", hasItem(Blocks.OXEYE_DAISY)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.MOJANG_BANNER_PATTERN).addIngredient(Items.PAPER).addIngredient(Items.ENCHANTED_GOLDEN_APPLE).addCriterion("has_enchanted_golden_apple", hasItem(Items.ENCHANTED_GOLDEN_APPLE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.SCAFFOLDING, 6).key('~', Items.STRING).key('I', Blocks.BAMBOO).patternLine("I~I").patternLine("I I").patternLine("I I").addCriterion("has_bamboo", hasItem(Blocks.BAMBOO)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.GRINDSTONE).key('I', Items.STICK).key('-', Blocks.STONE_SLAB).key('#', ItemTags.PLANKS).patternLine("I-I").patternLine("# #").addCriterion("has_stone_slab", hasItem(Blocks.STONE_SLAB)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.BLAST_FURNACE).key('#', Blocks.SMOOTH_STONE).key('X', Blocks.FURNACE).key('I', Items.IRON_INGOT).patternLine("III").patternLine("IXI").patternLine("###").addCriterion("has_smooth_stone", hasItem(Blocks.SMOOTH_STONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.SMOKER).key('#', ItemTags.LOGS).key('X', Blocks.FURNACE).patternLine(" # ").patternLine("#X#").patternLine(" # ").addCriterion("has_furnace", hasItem(Blocks.FURNACE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.CARTOGRAPHY_TABLE).key('#', ItemTags.PLANKS).key('@', Items.PAPER).patternLine("@@").patternLine("##").patternLine("##").addCriterion("has_paper", hasItem(Items.PAPER)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.SMITHING_TABLE).key('#', ItemTags.PLANKS).key('@', Items.IRON_INGOT).patternLine("@@").patternLine("##").patternLine("##").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.FLETCHING_TABLE).key('#', ItemTags.PLANKS).key('@', Items.FLINT).patternLine("@@").patternLine("##").patternLine("##").addCriterion("has_flint", hasItem(Items.FLINT)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.STONECUTTER).key('I', Items.IRON_INGOT).key('#', Blocks.STONE).patternLine(" I ").patternLine("###").addCriterion("has_stone", hasItem(Blocks.STONE)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235405_no_).key('S', Items.CHISELED_STONE_BRICKS).key('#', Items.field_234759_km_).patternLine("SSS").patternLine("S#S").patternLine("SSS").addCriterion("has_netherite_ingot", hasItem(Items.field_234759_km_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235397_ng_).key('#', Items.field_234759_km_).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_netherite_ingot", hasItem(Items.field_234759_km_)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Items.field_234759_km_, 9).addIngredient(Blocks.field_235397_ng_).setGroup("netherite_ingot").addCriterion("has_netherite_block", hasItem(Blocks.field_235397_ng_)).build(p_200404_0_, "netherite_ingot_from_netherite_block");
      ShapelessRecipeBuilder.shapelessRecipe(Items.field_234759_km_).addIngredient(Items.field_234760_kn_, 4).addIngredient(Items.GOLD_INGOT, 4).setGroup("netherite_ingot").addCriterion("has_netherite_scrap", hasItem(Items.field_234760_kn_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235400_nj_).key('O', Blocks.field_235399_ni_).key('G', Blocks.GLOWSTONE).patternLine("OOO").patternLine("GGG").patternLine("OOO").addCriterion("has_obsidian", hasItem(Blocks.field_235399_ni_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235407_nq_, 4).key('#', Blocks.field_235406_np_).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_blackstone", hasItem(Blocks.field_235406_np_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235388_nB_, 4).key('#', Blocks.field_235410_nt_).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_polished_blackstone", hasItem(Blocks.field_235410_nt_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235415_ny_, 4).key('#', Blocks.field_235411_nu_).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_polished_blackstone_bricks", hasItem(Blocks.field_235411_nu_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235409_ns_, 6).key('#', Blocks.field_235406_np_).patternLine("###").addCriterion("has_blackstone", hasItem(Blocks.field_235406_np_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235389_nC_, 6).key('#', Blocks.field_235410_nt_).patternLine("###").addCriterion("has_polished_blackstone", hasItem(Blocks.field_235410_nt_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235414_nx_, 6).key('#', Blocks.field_235411_nu_).patternLine("###").addCriterion("has_polished_blackstone_bricks", hasItem(Blocks.field_235411_nu_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235410_nt_, 4).key('S', Blocks.field_235406_np_).patternLine("SS").patternLine("SS").addCriterion("has_blackstone", hasItem(Blocks.field_235406_np_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235411_nu_, 4).key('#', Blocks.field_235410_nt_).patternLine("##").patternLine("##").addCriterion("has_polished_blackstone", hasItem(Blocks.field_235410_nt_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235413_nw_).key('#', Blocks.field_235389_nC_).patternLine("#").patternLine("#").addCriterion("has_polished_blackstone", hasItem(Blocks.field_235410_nt_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235408_nr_, 6).key('#', Blocks.field_235406_np_).patternLine("###").patternLine("###").addCriterion("has_blackstone", hasItem(Blocks.field_235406_np_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235392_nF_, 6).key('#', Blocks.field_235410_nt_).patternLine("###").patternLine("###").addCriterion("has_polished_blackstone", hasItem(Blocks.field_235410_nt_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235416_nz_, 6).key('#', Blocks.field_235411_nu_).patternLine("###").patternLine("###").addCriterion("has_polished_blackstone_bricks", hasItem(Blocks.field_235411_nu_)).build(p_200404_0_);
      ShapelessRecipeBuilder.shapelessRecipe(Blocks.field_235391_nE_).addIngredient(Blocks.field_235410_nt_).addCriterion("has_polished_blackstone", hasItem(Blocks.field_235410_nt_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235390_nD_).key('#', Blocks.field_235410_nt_).patternLine("##").addCriterion("has_polished_blackstone", hasItem(Blocks.field_235410_nt_)).build(p_200404_0_);
      ShapedRecipeBuilder.shapedRecipe(Blocks.field_235341_dI_).key('I', Items.IRON_INGOT).key('N', Items.IRON_NUGGET).patternLine("N").patternLine("I").patternLine("N").addCriterion("has_iron_nugget", hasItem(Items.IRON_NUGGET)).addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(p_200404_0_);
      CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_ARMORDYE).build(p_200404_0_, "armor_dye");
      CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_BANNERDUPLICATE).build(p_200404_0_, "banner_duplicate");
      CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_BOOKCLONING).build(p_200404_0_, "book_cloning");
      CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_FIREWORK_ROCKET).build(p_200404_0_, "firework_rocket");
      CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_FIREWORK_STAR).build(p_200404_0_, "firework_star");
      CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_FIREWORK_STAR_FADE).build(p_200404_0_, "firework_star_fade");
      CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_MAPCLONING).build(p_200404_0_, "map_cloning");
      CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_MAPEXTENDING).build(p_200404_0_, "map_extending");
      CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_REPAIRITEM).build(p_200404_0_, "repair_item");
      CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_SHIELD).build(p_200404_0_, "shield_decoration");
      CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_SHULKERBOXCOLORING).build(p_200404_0_, "shulker_box_coloring");
      CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_TIPPEDARROW).build(p_200404_0_, "tipped_arrow");
      CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_SUSPICIOUSSTEW).build(p_200404_0_, "suspicious_stew");
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.POTATO), Items.BAKED_POTATO, 0.35F, 200).addCriterion("has_potato", hasItem(Items.POTATO)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.CLAY_BALL), Items.BRICK, 0.3F, 200).addCriterion("has_clay_ball", hasItem(Items.CLAY_BALL)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.field_232912_o_), Items.CHARCOAL, 0.15F, 200).addCriterion("has_log", hasItem(ItemTags.field_232912_o_)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.CHORUS_FRUIT), Items.POPPED_CHORUS_FRUIT, 0.1F, 200).addCriterion("has_chorus_fruit", hasItem(Items.CHORUS_FRUIT)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.COAL_ORE.asItem()), Items.COAL, 0.1F, 200).addCriterion("has_coal_ore", hasItem(Blocks.COAL_ORE)).build(p_200404_0_, "coal_from_smelting");
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.BEEF), Items.COOKED_BEEF, 0.35F, 200).addCriterion("has_beef", hasItem(Items.BEEF)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.CHICKEN), Items.COOKED_CHICKEN, 0.35F, 200).addCriterion("has_chicken", hasItem(Items.CHICKEN)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.COD), Items.COOKED_COD, 0.35F, 200).addCriterion("has_cod", hasItem(Items.COD)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.KELP), Items.DRIED_KELP, 0.1F, 200).addCriterion("has_kelp", hasItem(Blocks.KELP)).build(p_200404_0_, "dried_kelp_from_smelting");
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.SALMON), Items.COOKED_SALMON, 0.35F, 200).addCriterion("has_salmon", hasItem(Items.SALMON)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.MUTTON), Items.COOKED_MUTTON, 0.35F, 200).addCriterion("has_mutton", hasItem(Items.MUTTON)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.PORKCHOP), Items.COOKED_PORKCHOP, 0.35F, 200).addCriterion("has_porkchop", hasItem(Items.PORKCHOP)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.RABBIT), Items.COOKED_RABBIT, 0.35F, 200).addCriterion("has_rabbit", hasItem(Items.RABBIT)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.DIAMOND_ORE.asItem()), Items.DIAMOND, 1.0F, 200).addCriterion("has_diamond_ore", hasItem(Blocks.DIAMOND_ORE)).build(p_200404_0_, "diamond_from_smelting");
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.LAPIS_ORE.asItem()), Items.LAPIS_LAZULI, 0.2F, 200).addCriterion("has_lapis_ore", hasItem(Blocks.LAPIS_ORE)).build(p_200404_0_, "lapis_from_smelting");
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.EMERALD_ORE.asItem()), Items.EMERALD, 1.0F, 200).addCriterion("has_emerald_ore", hasItem(Blocks.EMERALD_ORE)).build(p_200404_0_, "emerald_from_smelting");
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.SAND), Blocks.GLASS.asItem(), 0.1F, 200).addCriterion("has_sand", hasItem(ItemTags.SAND)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.field_232904_O_), Items.GOLD_INGOT, 1.0F, 200).addCriterion("has_gold_ore", hasItem(ItemTags.field_232904_O_)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.SEA_PICKLE.asItem()), Items.LIME_DYE, 0.1F, 200).addCriterion("has_sea_pickle", hasItem(Blocks.SEA_PICKLE)).build(p_200404_0_, "lime_dye_from_smelting");
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.CACTUS.asItem()), Items.GREEN_DYE, 1.0F, 200).addCriterion("has_cactus", hasItem(Blocks.CACTUS)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL, Items.GOLDEN_AXE, Items.GOLDEN_HOE, Items.GOLDEN_SWORD, Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS, Items.GOLDEN_HORSE_ARMOR), Items.GOLD_NUGGET, 0.1F, 200).addCriterion("has_golden_pickaxe", hasItem(Items.GOLDEN_PICKAXE)).addCriterion("has_golden_shovel", hasItem(Items.GOLDEN_SHOVEL)).addCriterion("has_golden_axe", hasItem(Items.GOLDEN_AXE)).addCriterion("has_golden_hoe", hasItem(Items.GOLDEN_HOE)).addCriterion("has_golden_sword", hasItem(Items.GOLDEN_SWORD)).addCriterion("has_golden_helmet", hasItem(Items.GOLDEN_HELMET)).addCriterion("has_golden_chestplate", hasItem(Items.GOLDEN_CHESTPLATE)).addCriterion("has_golden_leggings", hasItem(Items.GOLDEN_LEGGINGS)).addCriterion("has_golden_boots", hasItem(Items.GOLDEN_BOOTS)).addCriterion("has_golden_horse_armor", hasItem(Items.GOLDEN_HORSE_ARMOR)).build(p_200404_0_, "gold_nugget_from_smelting");
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.IRON_PICKAXE, Items.IRON_SHOVEL, Items.IRON_AXE, Items.IRON_HOE, Items.IRON_SWORD, Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS, Items.IRON_HORSE_ARMOR, Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS), Items.IRON_NUGGET, 0.1F, 200).addCriterion("has_iron_pickaxe", hasItem(Items.IRON_PICKAXE)).addCriterion("has_iron_shovel", hasItem(Items.IRON_SHOVEL)).addCriterion("has_iron_axe", hasItem(Items.IRON_AXE)).addCriterion("has_iron_hoe", hasItem(Items.IRON_HOE)).addCriterion("has_iron_sword", hasItem(Items.IRON_SWORD)).addCriterion("has_iron_helmet", hasItem(Items.IRON_HELMET)).addCriterion("has_iron_chestplate", hasItem(Items.IRON_CHESTPLATE)).addCriterion("has_iron_leggings", hasItem(Items.IRON_LEGGINGS)).addCriterion("has_iron_boots", hasItem(Items.IRON_BOOTS)).addCriterion("has_iron_horse_armor", hasItem(Items.IRON_HORSE_ARMOR)).addCriterion("has_chainmail_helmet", hasItem(Items.CHAINMAIL_HELMET)).addCriterion("has_chainmail_chestplate", hasItem(Items.CHAINMAIL_CHESTPLATE)).addCriterion("has_chainmail_leggings", hasItem(Items.CHAINMAIL_LEGGINGS)).addCriterion("has_chainmail_boots", hasItem(Items.CHAINMAIL_BOOTS)).build(p_200404_0_, "iron_nugget_from_smelting");
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.IRON_ORE.asItem()), Items.IRON_INGOT, 0.7F, 200).addCriterion("has_iron_ore", hasItem(Blocks.IRON_ORE.asItem())).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.CLAY), Blocks.TERRACOTTA.asItem(), 0.35F, 200).addCriterion("has_clay_block", hasItem(Blocks.CLAY)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.NETHERRACK), Items.NETHER_BRICK, 0.1F, 200).addCriterion("has_netherrack", hasItem(Blocks.NETHERRACK)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.NETHER_QUARTZ_ORE), Items.QUARTZ, 0.2F, 200).addCriterion("has_nether_quartz_ore", hasItem(Blocks.NETHER_QUARTZ_ORE)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.REDSTONE_ORE), Items.REDSTONE, 0.7F, 200).addCriterion("has_redstone_ore", hasItem(Blocks.REDSTONE_ORE)).build(p_200404_0_, "redstone_from_smelting");
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.WET_SPONGE), Blocks.SPONGE.asItem(), 0.15F, 200).addCriterion("has_wet_sponge", hasItem(Blocks.WET_SPONGE)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.COBBLESTONE), Blocks.STONE.asItem(), 0.1F, 200).addCriterion("has_cobblestone", hasItem(Blocks.COBBLESTONE)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.STONE), Blocks.SMOOTH_STONE.asItem(), 0.1F, 200).addCriterion("has_stone", hasItem(Blocks.STONE)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.SANDSTONE), Blocks.SMOOTH_SANDSTONE.asItem(), 0.1F, 200).addCriterion("has_sandstone", hasItem(Blocks.SANDSTONE)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.RED_SANDSTONE), Blocks.SMOOTH_RED_SANDSTONE.asItem(), 0.1F, 200).addCriterion("has_red_sandstone", hasItem(Blocks.RED_SANDSTONE)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.QUARTZ_BLOCK), Blocks.SMOOTH_QUARTZ.asItem(), 0.1F, 200).addCriterion("has_quartz_block", hasItem(Blocks.QUARTZ_BLOCK)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.STONE_BRICKS), Blocks.CRACKED_STONE_BRICKS.asItem(), 0.1F, 200).addCriterion("has_stone_bricks", hasItem(Blocks.STONE_BRICKS)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.BLACK_TERRACOTTA), Blocks.BLACK_GLAZED_TERRACOTTA.asItem(), 0.1F, 200).addCriterion("has_black_terracotta", hasItem(Blocks.BLACK_TERRACOTTA)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.BLUE_TERRACOTTA), Blocks.BLUE_GLAZED_TERRACOTTA.asItem(), 0.1F, 200).addCriterion("has_blue_terracotta", hasItem(Blocks.BLUE_TERRACOTTA)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.BROWN_TERRACOTTA), Blocks.BROWN_GLAZED_TERRACOTTA.asItem(), 0.1F, 200).addCriterion("has_brown_terracotta", hasItem(Blocks.BROWN_TERRACOTTA)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.CYAN_TERRACOTTA), Blocks.CYAN_GLAZED_TERRACOTTA.asItem(), 0.1F, 200).addCriterion("has_cyan_terracotta", hasItem(Blocks.CYAN_TERRACOTTA)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.GRAY_TERRACOTTA), Blocks.GRAY_GLAZED_TERRACOTTA.asItem(), 0.1F, 200).addCriterion("has_gray_terracotta", hasItem(Blocks.GRAY_TERRACOTTA)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.GREEN_TERRACOTTA), Blocks.GREEN_GLAZED_TERRACOTTA.asItem(), 0.1F, 200).addCriterion("has_green_terracotta", hasItem(Blocks.GREEN_TERRACOTTA)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.LIGHT_BLUE_TERRACOTTA), Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA.asItem(), 0.1F, 200).addCriterion("has_light_blue_terracotta", hasItem(Blocks.LIGHT_BLUE_TERRACOTTA)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.LIGHT_GRAY_TERRACOTTA), Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA.asItem(), 0.1F, 200).addCriterion("has_light_gray_terracotta", hasItem(Blocks.LIGHT_GRAY_TERRACOTTA)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.LIME_TERRACOTTA), Blocks.LIME_GLAZED_TERRACOTTA.asItem(), 0.1F, 200).addCriterion("has_lime_terracotta", hasItem(Blocks.LIME_TERRACOTTA)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.MAGENTA_TERRACOTTA), Blocks.MAGENTA_GLAZED_TERRACOTTA.asItem(), 0.1F, 200).addCriterion("has_magenta_terracotta", hasItem(Blocks.MAGENTA_TERRACOTTA)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.ORANGE_TERRACOTTA), Blocks.ORANGE_GLAZED_TERRACOTTA.asItem(), 0.1F, 200).addCriterion("has_orange_terracotta", hasItem(Blocks.ORANGE_TERRACOTTA)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.PINK_TERRACOTTA), Blocks.PINK_GLAZED_TERRACOTTA.asItem(), 0.1F, 200).addCriterion("has_pink_terracotta", hasItem(Blocks.PINK_TERRACOTTA)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.PURPLE_TERRACOTTA), Blocks.PURPLE_GLAZED_TERRACOTTA.asItem(), 0.1F, 200).addCriterion("has_purple_terracotta", hasItem(Blocks.PURPLE_TERRACOTTA)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.RED_TERRACOTTA), Blocks.RED_GLAZED_TERRACOTTA.asItem(), 0.1F, 200).addCriterion("has_red_terracotta", hasItem(Blocks.RED_TERRACOTTA)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.WHITE_TERRACOTTA), Blocks.WHITE_GLAZED_TERRACOTTA.asItem(), 0.1F, 200).addCriterion("has_white_terracotta", hasItem(Blocks.WHITE_TERRACOTTA)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.YELLOW_TERRACOTTA), Blocks.YELLOW_GLAZED_TERRACOTTA.asItem(), 0.1F, 200).addCriterion("has_yellow_terracotta", hasItem(Blocks.YELLOW_TERRACOTTA)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.field_235398_nh_), Items.field_234760_kn_, 2.0F, 200).addCriterion("has_ancient_debris", hasItem(Blocks.field_235398_nh_)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.field_235411_nu_), Blocks.field_235412_nv_.asItem(), 0.1F, 200).addCriterion("has_blackstone_bricks", hasItem(Blocks.field_235411_nu_)).build(p_200404_0_);
      CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.NETHER_BRICKS), Blocks.field_235394_nH_.asItem(), 0.1F, 200).addCriterion("has_nether_bricks", hasItem(Blocks.NETHER_BRICKS)).build(p_200404_0_);
      CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(Blocks.IRON_ORE.asItem()), Items.IRON_INGOT, 0.7F, 100).addCriterion("has_iron_ore", hasItem(Blocks.IRON_ORE.asItem())).build(p_200404_0_, "iron_ingot_from_blasting");
      CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.field_232904_O_), Items.GOLD_INGOT, 1.0F, 100).addCriterion("has_gold_ore", hasItem(ItemTags.field_232904_O_)).build(p_200404_0_, "gold_ingot_from_blasting");
      CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(Blocks.DIAMOND_ORE.asItem()), Items.DIAMOND, 1.0F, 100).addCriterion("has_diamond_ore", hasItem(Blocks.DIAMOND_ORE)).build(p_200404_0_, "diamond_from_blasting");
      CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(Blocks.LAPIS_ORE.asItem()), Items.LAPIS_LAZULI, 0.2F, 100).addCriterion("has_lapis_ore", hasItem(Blocks.LAPIS_ORE)).build(p_200404_0_, "lapis_from_blasting");
      CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(Blocks.REDSTONE_ORE), Items.REDSTONE, 0.7F, 100).addCriterion("has_redstone_ore", hasItem(Blocks.REDSTONE_ORE)).build(p_200404_0_, "redstone_from_blasting");
      CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(Blocks.COAL_ORE.asItem()), Items.COAL, 0.1F, 100).addCriterion("has_coal_ore", hasItem(Blocks.COAL_ORE)).build(p_200404_0_, "coal_from_blasting");
      CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(Blocks.EMERALD_ORE.asItem()), Items.EMERALD, 1.0F, 100).addCriterion("has_emerald_ore", hasItem(Blocks.EMERALD_ORE)).build(p_200404_0_, "emerald_from_blasting");
      CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(Blocks.NETHER_QUARTZ_ORE), Items.QUARTZ, 0.2F, 100).addCriterion("has_nether_quartz_ore", hasItem(Blocks.NETHER_QUARTZ_ORE)).build(p_200404_0_, "quartz_from_blasting");
      CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL, Items.GOLDEN_AXE, Items.GOLDEN_HOE, Items.GOLDEN_SWORD, Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS, Items.GOLDEN_HORSE_ARMOR), Items.GOLD_NUGGET, 0.1F, 100).addCriterion("has_golden_pickaxe", hasItem(Items.GOLDEN_PICKAXE)).addCriterion("has_golden_shovel", hasItem(Items.GOLDEN_SHOVEL)).addCriterion("has_golden_axe", hasItem(Items.GOLDEN_AXE)).addCriterion("has_golden_hoe", hasItem(Items.GOLDEN_HOE)).addCriterion("has_golden_sword", hasItem(Items.GOLDEN_SWORD)).addCriterion("has_golden_helmet", hasItem(Items.GOLDEN_HELMET)).addCriterion("has_golden_chestplate", hasItem(Items.GOLDEN_CHESTPLATE)).addCriterion("has_golden_leggings", hasItem(Items.GOLDEN_LEGGINGS)).addCriterion("has_golden_boots", hasItem(Items.GOLDEN_BOOTS)).addCriterion("has_golden_horse_armor", hasItem(Items.GOLDEN_HORSE_ARMOR)).build(p_200404_0_, "gold_nugget_from_blasting");
      CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(Items.IRON_PICKAXE, Items.IRON_SHOVEL, Items.IRON_AXE, Items.IRON_HOE, Items.IRON_SWORD, Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS, Items.IRON_HORSE_ARMOR, Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS), Items.IRON_NUGGET, 0.1F, 100).addCriterion("has_iron_pickaxe", hasItem(Items.IRON_PICKAXE)).addCriterion("has_iron_shovel", hasItem(Items.IRON_SHOVEL)).addCriterion("has_iron_axe", hasItem(Items.IRON_AXE)).addCriterion("has_iron_hoe", hasItem(Items.IRON_HOE)).addCriterion("has_iron_sword", hasItem(Items.IRON_SWORD)).addCriterion("has_iron_helmet", hasItem(Items.IRON_HELMET)).addCriterion("has_iron_chestplate", hasItem(Items.IRON_CHESTPLATE)).addCriterion("has_iron_leggings", hasItem(Items.IRON_LEGGINGS)).addCriterion("has_iron_boots", hasItem(Items.IRON_BOOTS)).addCriterion("has_iron_horse_armor", hasItem(Items.IRON_HORSE_ARMOR)).addCriterion("has_chainmail_helmet", hasItem(Items.CHAINMAIL_HELMET)).addCriterion("has_chainmail_chestplate", hasItem(Items.CHAINMAIL_CHESTPLATE)).addCriterion("has_chainmail_leggings", hasItem(Items.CHAINMAIL_LEGGINGS)).addCriterion("has_chainmail_boots", hasItem(Items.CHAINMAIL_BOOTS)).build(p_200404_0_, "iron_nugget_from_blasting");
      CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(Blocks.field_235398_nh_), Items.field_234760_kn_, 2.0F, 100).addCriterion("has_ancient_debris", hasItem(Blocks.field_235398_nh_)).build(p_200404_0_, "netherite_scrap_from_blasting");
      cookingRecipesForMethod(p_200404_0_, "smoking", IRecipeSerializer.SMOKING, 100);
      cookingRecipesForMethod(p_200404_0_, "campfire_cooking", IRecipeSerializer.CAMPFIRE_COOKING, 600);
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE), Blocks.STONE_SLAB, 2).addCriterion("has_stone", hasItem(Blocks.STONE)).build(p_200404_0_, "stone_slab_from_stone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE), Blocks.STONE_STAIRS).addCriterion("has_stone", hasItem(Blocks.STONE)).build(p_200404_0_, "stone_stairs_from_stone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE), Blocks.STONE_BRICKS).addCriterion("has_stone", hasItem(Blocks.STONE)).build(p_200404_0_, "stone_bricks_from_stone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE), Blocks.STONE_BRICK_SLAB, 2).addCriterion("has_stone", hasItem(Blocks.STONE)).build(p_200404_0_, "stone_brick_slab_from_stone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE), Blocks.STONE_BRICK_STAIRS).addCriterion("has_stone", hasItem(Blocks.STONE)).build(p_200404_0_, "stone_brick_stairs_from_stone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE), Blocks.CHISELED_STONE_BRICKS).addCriterion("has_stone", hasItem(Blocks.STONE)).build(p_200404_0_, "chiseled_stone_bricks_stone_from_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE), Blocks.STONE_BRICK_WALL).addCriterion("has_stone", hasItem(Blocks.STONE)).build(p_200404_0_, "stone_brick_walls_from_stone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SANDSTONE), Blocks.CUT_SANDSTONE).addCriterion("has_sandstone", hasItem(Blocks.SANDSTONE)).build(p_200404_0_, "cut_sandstone_from_sandstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SANDSTONE), Blocks.SANDSTONE_SLAB, 2).addCriterion("has_sandstone", hasItem(Blocks.SANDSTONE)).build(p_200404_0_, "sandstone_slab_from_sandstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SANDSTONE), Blocks.CUT_SANDSTONE_SLAB, 2).addCriterion("has_sandstone", hasItem(Blocks.SANDSTONE)).build(p_200404_0_, "cut_sandstone_slab_from_sandstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.CUT_SANDSTONE), Blocks.CUT_SANDSTONE_SLAB, 2).addCriterion("has_cut_sandstone", hasItem(Blocks.SANDSTONE)).build(p_200404_0_, "cut_sandstone_slab_from_cut_sandstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SANDSTONE), Blocks.SANDSTONE_STAIRS).addCriterion("has_sandstone", hasItem(Blocks.SANDSTONE)).build(p_200404_0_, "sandstone_stairs_from_sandstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SANDSTONE), Blocks.SANDSTONE_WALL).addCriterion("has_sandstone", hasItem(Blocks.SANDSTONE)).build(p_200404_0_, "sandstone_wall_from_sandstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SANDSTONE), Blocks.CHISELED_SANDSTONE).addCriterion("has_sandstone", hasItem(Blocks.SANDSTONE)).build(p_200404_0_, "chiseled_sandstone_from_sandstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.RED_SANDSTONE), Blocks.CUT_RED_SANDSTONE).addCriterion("has_red_sandstone", hasItem(Blocks.RED_SANDSTONE)).build(p_200404_0_, "cut_red_sandstone_from_red_sandstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.RED_SANDSTONE), Blocks.RED_SANDSTONE_SLAB, 2).addCriterion("has_red_sandstone", hasItem(Blocks.RED_SANDSTONE)).build(p_200404_0_, "red_sandstone_slab_from_red_sandstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.RED_SANDSTONE), Blocks.CUT_RED_SANDSTONE_SLAB, 2).addCriterion("has_red_sandstone", hasItem(Blocks.RED_SANDSTONE)).build(p_200404_0_, "cut_red_sandstone_slab_from_red_sandstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.CUT_RED_SANDSTONE), Blocks.CUT_RED_SANDSTONE_SLAB, 2).addCriterion("has_cut_red_sandstone", hasItem(Blocks.RED_SANDSTONE)).build(p_200404_0_, "cut_red_sandstone_slab_from_cut_red_sandstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.RED_SANDSTONE), Blocks.RED_SANDSTONE_STAIRS).addCriterion("has_red_sandstone", hasItem(Blocks.RED_SANDSTONE)).build(p_200404_0_, "red_sandstone_stairs_from_red_sandstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.RED_SANDSTONE), Blocks.RED_SANDSTONE_WALL).addCriterion("has_red_sandstone", hasItem(Blocks.RED_SANDSTONE)).build(p_200404_0_, "red_sandstone_wall_from_red_sandstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.RED_SANDSTONE), Blocks.CHISELED_RED_SANDSTONE).addCriterion("has_red_sandstone", hasItem(Blocks.RED_SANDSTONE)).build(p_200404_0_, "chiseled_red_sandstone_from_red_sandstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.QUARTZ_BLOCK), Blocks.QUARTZ_SLAB, 2).addCriterion("has_quartz_block", hasItem(Blocks.QUARTZ_BLOCK)).build(p_200404_0_, "quartz_slab_from_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.QUARTZ_BLOCK), Blocks.QUARTZ_STAIRS).addCriterion("has_quartz_block", hasItem(Blocks.QUARTZ_BLOCK)).build(p_200404_0_, "quartz_stairs_from_quartz_block_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.QUARTZ_BLOCK), Blocks.QUARTZ_PILLAR).addCriterion("has_quartz_block", hasItem(Blocks.QUARTZ_BLOCK)).build(p_200404_0_, "quartz_pillar_from_quartz_block_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.QUARTZ_BLOCK), Blocks.CHISELED_QUARTZ_BLOCK).addCriterion("has_quartz_block", hasItem(Blocks.QUARTZ_BLOCK)).build(p_200404_0_, "chiseled_quartz_block_from_quartz_block_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.QUARTZ_BLOCK), Blocks.field_235395_nI_).addCriterion("has_quartz_block", hasItem(Blocks.QUARTZ_BLOCK)).build(p_200404_0_, "quartz_bricks_from_quartz_block_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.COBBLESTONE), Blocks.COBBLESTONE_STAIRS).addCriterion("has_cobblestone", hasItem(Blocks.COBBLESTONE)).build(p_200404_0_, "cobblestone_stairs_from_cobblestone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.COBBLESTONE), Blocks.COBBLESTONE_SLAB, 2).addCriterion("has_cobblestone", hasItem(Blocks.COBBLESTONE)).build(p_200404_0_, "cobblestone_slab_from_cobblestone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.COBBLESTONE), Blocks.COBBLESTONE_WALL).addCriterion("has_cobblestone", hasItem(Blocks.COBBLESTONE)).build(p_200404_0_, "cobblestone_wall_from_cobblestone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE_BRICKS), Blocks.STONE_BRICK_SLAB, 2).addCriterion("has_stone_bricks", hasItem(Blocks.STONE_BRICKS)).build(p_200404_0_, "stone_brick_slab_from_stone_bricks_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE_BRICKS), Blocks.STONE_BRICK_STAIRS).addCriterion("has_stone_bricks", hasItem(Blocks.STONE_BRICKS)).build(p_200404_0_, "stone_brick_stairs_from_stone_bricks_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE_BRICKS), Blocks.STONE_BRICK_WALL).addCriterion("has_stone_bricks", hasItem(Blocks.STONE_BRICKS)).build(p_200404_0_, "stone_brick_wall_from_stone_bricks_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE_BRICKS), Blocks.CHISELED_STONE_BRICKS).addCriterion("has_stone_bricks", hasItem(Blocks.STONE_BRICKS)).build(p_200404_0_, "chiseled_stone_bricks_from_stone_bricks_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.BRICKS), Blocks.BRICK_SLAB, 2).addCriterion("has_bricks", hasItem(Blocks.BRICKS)).build(p_200404_0_, "brick_slab_from_bricks_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.BRICKS), Blocks.BRICK_STAIRS).addCriterion("has_bricks", hasItem(Blocks.BRICKS)).build(p_200404_0_, "brick_stairs_from_bricks_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.BRICKS), Blocks.BRICK_WALL).addCriterion("has_bricks", hasItem(Blocks.BRICKS)).build(p_200404_0_, "brick_wall_from_bricks_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.NETHER_BRICKS), Blocks.NETHER_BRICK_SLAB, 2).addCriterion("has_nether_bricks", hasItem(Blocks.NETHER_BRICKS)).build(p_200404_0_, "nether_brick_slab_from_nether_bricks_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.NETHER_BRICKS), Blocks.NETHER_BRICK_STAIRS).addCriterion("has_nether_bricks", hasItem(Blocks.NETHER_BRICKS)).build(p_200404_0_, "nether_brick_stairs_from_nether_bricks_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.NETHER_BRICKS), Blocks.NETHER_BRICK_WALL).addCriterion("has_nether_bricks", hasItem(Blocks.NETHER_BRICKS)).build(p_200404_0_, "nether_brick_wall_from_nether_bricks_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.NETHER_BRICKS), Blocks.field_235393_nG_).addCriterion("has_nether_bricks", hasItem(Blocks.NETHER_BRICKS)).build(p_200404_0_, "chiseled_nether_bricks_from_nether_bricks_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.RED_NETHER_BRICKS), Blocks.RED_NETHER_BRICK_SLAB, 2).addCriterion("has_nether_bricks", hasItem(Blocks.RED_NETHER_BRICKS)).build(p_200404_0_, "red_nether_brick_slab_from_red_nether_bricks_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.RED_NETHER_BRICKS), Blocks.RED_NETHER_BRICK_STAIRS).addCriterion("has_nether_bricks", hasItem(Blocks.RED_NETHER_BRICKS)).build(p_200404_0_, "red_nether_brick_stairs_from_red_nether_bricks_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.RED_NETHER_BRICKS), Blocks.RED_NETHER_BRICK_WALL).addCriterion("has_nether_bricks", hasItem(Blocks.RED_NETHER_BRICKS)).build(p_200404_0_, "red_nether_brick_wall_from_red_nether_bricks_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.PURPUR_BLOCK), Blocks.PURPUR_SLAB, 2).addCriterion("has_purpur_block", hasItem(Blocks.PURPUR_BLOCK)).build(p_200404_0_, "purpur_slab_from_purpur_block_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.PURPUR_BLOCK), Blocks.PURPUR_STAIRS).addCriterion("has_purpur_block", hasItem(Blocks.PURPUR_BLOCK)).build(p_200404_0_, "purpur_stairs_from_purpur_block_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.PURPUR_BLOCK), Blocks.PURPUR_PILLAR).addCriterion("has_purpur_block", hasItem(Blocks.PURPUR_BLOCK)).build(p_200404_0_, "purpur_pillar_from_purpur_block_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.PRISMARINE), Blocks.PRISMARINE_SLAB, 2).addCriterion("has_prismarine", hasItem(Blocks.PRISMARINE)).build(p_200404_0_, "prismarine_slab_from_prismarine_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.PRISMARINE), Blocks.PRISMARINE_STAIRS).addCriterion("has_prismarine", hasItem(Blocks.PRISMARINE)).build(p_200404_0_, "prismarine_stairs_from_prismarine_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.PRISMARINE), Blocks.PRISMARINE_WALL).addCriterion("has_prismarine", hasItem(Blocks.PRISMARINE)).build(p_200404_0_, "prismarine_wall_from_prismarine_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.PRISMARINE_BRICKS), Blocks.PRISMARINE_BRICK_SLAB, 2).addCriterion("has_prismarine_brick", hasItem(Blocks.PRISMARINE_BRICKS)).build(p_200404_0_, "prismarine_brick_slab_from_prismarine_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.PRISMARINE_BRICKS), Blocks.PRISMARINE_BRICK_STAIRS).addCriterion("has_prismarine_brick", hasItem(Blocks.PRISMARINE_BRICKS)).build(p_200404_0_, "prismarine_brick_stairs_from_prismarine_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.DARK_PRISMARINE), Blocks.DARK_PRISMARINE_SLAB, 2).addCriterion("has_dark_prismarine", hasItem(Blocks.DARK_PRISMARINE)).build(p_200404_0_, "dark_prismarine_slab_from_dark_prismarine_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.DARK_PRISMARINE), Blocks.DARK_PRISMARINE_STAIRS).addCriterion("has_dark_prismarine", hasItem(Blocks.DARK_PRISMARINE)).build(p_200404_0_, "dark_prismarine_stairs_from_dark_prismarine_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.ANDESITE), Blocks.ANDESITE_SLAB, 2).addCriterion("has_andesite", hasItem(Blocks.ANDESITE)).build(p_200404_0_, "andesite_slab_from_andesite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.ANDESITE), Blocks.ANDESITE_STAIRS).addCriterion("has_andesite", hasItem(Blocks.ANDESITE)).build(p_200404_0_, "andesite_stairs_from_andesite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.ANDESITE), Blocks.ANDESITE_WALL).addCriterion("has_andesite", hasItem(Blocks.ANDESITE)).build(p_200404_0_, "andesite_wall_from_andesite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.ANDESITE), Blocks.POLISHED_ANDESITE).addCriterion("has_andesite", hasItem(Blocks.ANDESITE)).build(p_200404_0_, "polished_andesite_from_andesite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.ANDESITE), Blocks.POLISHED_ANDESITE_SLAB, 2).addCriterion("has_andesite", hasItem(Blocks.ANDESITE)).build(p_200404_0_, "polished_andesite_slab_from_andesite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.ANDESITE), Blocks.POLISHED_ANDESITE_STAIRS).addCriterion("has_andesite", hasItem(Blocks.ANDESITE)).build(p_200404_0_, "polished_andesite_stairs_from_andesite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_ANDESITE), Blocks.POLISHED_ANDESITE_SLAB, 2).addCriterion("has_polished_andesite", hasItem(Blocks.POLISHED_ANDESITE)).build(p_200404_0_, "polished_andesite_slab_from_polished_andesite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_ANDESITE), Blocks.POLISHED_ANDESITE_STAIRS).addCriterion("has_polished_andesite", hasItem(Blocks.POLISHED_ANDESITE)).build(p_200404_0_, "polished_andesite_stairs_from_polished_andesite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235337_cO_), Blocks.field_235338_cP_).addCriterion("has_basalt", hasItem(Blocks.field_235337_cO_)).build(p_200404_0_, "polished_basalt_from_basalt_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.GRANITE), Blocks.GRANITE_SLAB, 2).addCriterion("has_granite", hasItem(Blocks.GRANITE)).build(p_200404_0_, "granite_slab_from_granite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.GRANITE), Blocks.GRANITE_STAIRS).addCriterion("has_granite", hasItem(Blocks.GRANITE)).build(p_200404_0_, "granite_stairs_from_granite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.GRANITE), Blocks.GRANITE_WALL).addCriterion("has_granite", hasItem(Blocks.GRANITE)).build(p_200404_0_, "granite_wall_from_granite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.GRANITE), Blocks.POLISHED_GRANITE).addCriterion("has_granite", hasItem(Blocks.GRANITE)).build(p_200404_0_, "polished_granite_from_granite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.GRANITE), Blocks.POLISHED_GRANITE_SLAB, 2).addCriterion("has_granite", hasItem(Blocks.GRANITE)).build(p_200404_0_, "polished_granite_slab_from_granite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.GRANITE), Blocks.POLISHED_GRANITE_STAIRS).addCriterion("has_granite", hasItem(Blocks.GRANITE)).build(p_200404_0_, "polished_granite_stairs_from_granite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_GRANITE), Blocks.POLISHED_GRANITE_SLAB, 2).addCriterion("has_polished_granite", hasItem(Blocks.POLISHED_GRANITE)).build(p_200404_0_, "polished_granite_slab_from_polished_granite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_GRANITE), Blocks.POLISHED_GRANITE_STAIRS).addCriterion("has_polished_granite", hasItem(Blocks.POLISHED_GRANITE)).build(p_200404_0_, "polished_granite_stairs_from_polished_granite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.DIORITE), Blocks.DIORITE_SLAB, 2).addCriterion("has_diorite", hasItem(Blocks.DIORITE)).build(p_200404_0_, "diorite_slab_from_diorite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.DIORITE), Blocks.DIORITE_STAIRS).addCriterion("has_diorite", hasItem(Blocks.DIORITE)).build(p_200404_0_, "diorite_stairs_from_diorite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.DIORITE), Blocks.DIORITE_WALL).addCriterion("has_diorite", hasItem(Blocks.DIORITE)).build(p_200404_0_, "diorite_wall_from_diorite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.DIORITE), Blocks.POLISHED_DIORITE).addCriterion("has_diorite", hasItem(Blocks.DIORITE)).build(p_200404_0_, "polished_diorite_from_diorite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.DIORITE), Blocks.POLISHED_DIORITE_SLAB, 2).addCriterion("has_diorite", hasItem(Blocks.POLISHED_DIORITE)).build(p_200404_0_, "polished_diorite_slab_from_diorite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.DIORITE), Blocks.POLISHED_DIORITE_STAIRS).addCriterion("has_diorite", hasItem(Blocks.POLISHED_DIORITE)).build(p_200404_0_, "polished_diorite_stairs_from_diorite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_DIORITE), Blocks.POLISHED_DIORITE_SLAB, 2).addCriterion("has_polished_diorite", hasItem(Blocks.POLISHED_DIORITE)).build(p_200404_0_, "polished_diorite_slab_from_polished_diorite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_DIORITE), Blocks.POLISHED_DIORITE_STAIRS).addCriterion("has_polished_diorite", hasItem(Blocks.POLISHED_DIORITE)).build(p_200404_0_, "polished_diorite_stairs_from_polished_diorite_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.MOSSY_STONE_BRICKS), Blocks.MOSSY_STONE_BRICK_SLAB, 2).addCriterion("has_mossy_stone_bricks", hasItem(Blocks.MOSSY_STONE_BRICKS)).build(p_200404_0_, "mossy_stone_brick_slab_from_mossy_stone_brick_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.MOSSY_STONE_BRICKS), Blocks.MOSSY_STONE_BRICK_STAIRS).addCriterion("has_mossy_stone_bricks", hasItem(Blocks.MOSSY_STONE_BRICKS)).build(p_200404_0_, "mossy_stone_brick_stairs_from_mossy_stone_brick_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.MOSSY_STONE_BRICKS), Blocks.MOSSY_STONE_BRICK_WALL).addCriterion("has_mossy_stone_bricks", hasItem(Blocks.MOSSY_STONE_BRICKS)).build(p_200404_0_, "mossy_stone_brick_wall_from_mossy_stone_brick_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.MOSSY_COBBLESTONE), Blocks.MOSSY_COBBLESTONE_SLAB, 2).addCriterion("has_mossy_cobblestone", hasItem(Blocks.MOSSY_COBBLESTONE)).build(p_200404_0_, "mossy_cobblestone_slab_from_mossy_cobblestone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.MOSSY_COBBLESTONE), Blocks.MOSSY_COBBLESTONE_STAIRS).addCriterion("has_mossy_cobblestone", hasItem(Blocks.MOSSY_COBBLESTONE)).build(p_200404_0_, "mossy_cobblestone_stairs_from_mossy_cobblestone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.MOSSY_COBBLESTONE), Blocks.MOSSY_COBBLESTONE_WALL).addCriterion("has_mossy_cobblestone", hasItem(Blocks.MOSSY_COBBLESTONE)).build(p_200404_0_, "mossy_cobblestone_wall_from_mossy_cobblestone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SMOOTH_SANDSTONE), Blocks.SMOOTH_SANDSTONE_SLAB, 2).addCriterion("has_smooth_sandstone", hasItem(Blocks.SMOOTH_SANDSTONE)).build(p_200404_0_, "smooth_sandstone_slab_from_smooth_sandstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SMOOTH_SANDSTONE), Blocks.SMOOTH_SANDSTONE_STAIRS).addCriterion("has_mossy_cobblestone", hasItem(Blocks.SMOOTH_SANDSTONE)).build(p_200404_0_, "smooth_sandstone_stairs_from_smooth_sandstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SMOOTH_RED_SANDSTONE), Blocks.SMOOTH_RED_SANDSTONE_SLAB, 2).addCriterion("has_smooth_red_sandstone", hasItem(Blocks.SMOOTH_RED_SANDSTONE)).build(p_200404_0_, "smooth_red_sandstone_slab_from_smooth_red_sandstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SMOOTH_RED_SANDSTONE), Blocks.SMOOTH_RED_SANDSTONE_STAIRS).addCriterion("has_smooth_red_sandstone", hasItem(Blocks.SMOOTH_RED_SANDSTONE)).build(p_200404_0_, "smooth_red_sandstone_stairs_from_smooth_red_sandstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SMOOTH_QUARTZ), Blocks.SMOOTH_QUARTZ_SLAB, 2).addCriterion("has_smooth_quartz", hasItem(Blocks.SMOOTH_QUARTZ)).build(p_200404_0_, "smooth_quartz_slab_from_smooth_quartz_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SMOOTH_QUARTZ), Blocks.SMOOTH_QUARTZ_STAIRS).addCriterion("has_smooth_quartz", hasItem(Blocks.SMOOTH_QUARTZ)).build(p_200404_0_, "smooth_quartz_stairs_from_smooth_quartz_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.END_STONE_BRICKS), Blocks.END_STONE_BRICK_SLAB, 2).addCriterion("has_end_stone_brick", hasItem(Blocks.END_STONE_BRICKS)).build(p_200404_0_, "end_stone_brick_slab_from_end_stone_brick_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.END_STONE_BRICKS), Blocks.END_STONE_BRICK_STAIRS).addCriterion("has_end_stone_brick", hasItem(Blocks.END_STONE_BRICKS)).build(p_200404_0_, "end_stone_brick_stairs_from_end_stone_brick_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.END_STONE_BRICKS), Blocks.END_STONE_BRICK_WALL).addCriterion("has_end_stone_brick", hasItem(Blocks.END_STONE_BRICKS)).build(p_200404_0_, "end_stone_brick_wall_from_end_stone_brick_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.END_STONE), Blocks.END_STONE_BRICKS).addCriterion("has_end_stone", hasItem(Blocks.END_STONE)).build(p_200404_0_, "end_stone_bricks_from_end_stone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.END_STONE), Blocks.END_STONE_BRICK_SLAB, 2).addCriterion("has_end_stone", hasItem(Blocks.END_STONE)).build(p_200404_0_, "end_stone_brick_slab_from_end_stone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.END_STONE), Blocks.END_STONE_BRICK_STAIRS).addCriterion("has_end_stone", hasItem(Blocks.END_STONE)).build(p_200404_0_, "end_stone_brick_stairs_from_end_stone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.END_STONE), Blocks.END_STONE_BRICK_WALL).addCriterion("has_end_stone", hasItem(Blocks.END_STONE)).build(p_200404_0_, "end_stone_brick_wall_from_end_stone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SMOOTH_STONE), Blocks.SMOOTH_STONE_SLAB, 2).addCriterion("has_smooth_stone", hasItem(Blocks.SMOOTH_STONE)).build(p_200404_0_, "smooth_stone_slab_from_smooth_stone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235406_np_), Blocks.field_235409_ns_, 2).addCriterion("has_blackstone", hasItem(Blocks.field_235406_np_)).build(p_200404_0_, "blackstone_slab_from_blackstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235406_np_), Blocks.field_235407_nq_).addCriterion("has_blackstone", hasItem(Blocks.field_235406_np_)).build(p_200404_0_, "blackstone_stairs_from_blackstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235406_np_), Blocks.field_235408_nr_).addCriterion("has_blackstone", hasItem(Blocks.field_235406_np_)).build(p_200404_0_, "blackstone_wall_from_blackstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235406_np_), Blocks.field_235410_nt_).addCriterion("has_blackstone", hasItem(Blocks.field_235406_np_)).build(p_200404_0_, "polished_blackstone_from_blackstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235406_np_), Blocks.field_235392_nF_).addCriterion("has_blackstone", hasItem(Blocks.field_235406_np_)).build(p_200404_0_, "polished_blackstone_wall_from_blackstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235406_np_), Blocks.field_235389_nC_, 2).addCriterion("has_blackstone", hasItem(Blocks.field_235406_np_)).build(p_200404_0_, "polished_blackstone_slab_from_blackstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235406_np_), Blocks.field_235388_nB_).addCriterion("has_blackstone", hasItem(Blocks.field_235406_np_)).build(p_200404_0_, "polished_blackstone_stairs_from_blackstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235406_np_), Blocks.field_235413_nw_).addCriterion("has_blackstone", hasItem(Blocks.field_235406_np_)).build(p_200404_0_, "chiseled_polished_blackstone_from_blackstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235406_np_), Blocks.field_235411_nu_).addCriterion("has_blackstone", hasItem(Blocks.field_235406_np_)).build(p_200404_0_, "polished_blackstone_bricks_from_blackstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235406_np_), Blocks.field_235414_nx_, 2).addCriterion("has_blackstone", hasItem(Blocks.field_235406_np_)).build(p_200404_0_, "polished_blackstone_brick_slab_from_blackstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235406_np_), Blocks.field_235415_ny_).addCriterion("has_blackstone", hasItem(Blocks.field_235406_np_)).build(p_200404_0_, "polished_blackstone_brick_stairs_from_blackstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235406_np_), Blocks.field_235416_nz_).addCriterion("has_blackstone", hasItem(Blocks.field_235406_np_)).build(p_200404_0_, "polished_blackstone_brick_wall_from_blackstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235410_nt_), Blocks.field_235389_nC_, 2).addCriterion("has_polished_blackstone", hasItem(Blocks.field_235410_nt_)).build(p_200404_0_, "polished_blackstone_slab_from_polished_blackstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235410_nt_), Blocks.field_235388_nB_).addCriterion("has_polished_blackstone", hasItem(Blocks.field_235410_nt_)).build(p_200404_0_, "polished_blackstone_stairs_from_polished_blackstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235410_nt_), Blocks.field_235411_nu_).addCriterion("has_polished_blackstone", hasItem(Blocks.field_235410_nt_)).build(p_200404_0_, "polished_blackstone_bricks_from_polished_blackstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235410_nt_), Blocks.field_235392_nF_).addCriterion("has_polished_blackstone", hasItem(Blocks.field_235410_nt_)).build(p_200404_0_, "polished_blackstone_wall_from_polished_blackstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235410_nt_), Blocks.field_235414_nx_, 2).addCriterion("has_polished_blackstone", hasItem(Blocks.field_235410_nt_)).build(p_200404_0_, "polished_blackstone_brick_slab_from_polished_blackstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235410_nt_), Blocks.field_235415_ny_).addCriterion("has_polished_blackstone", hasItem(Blocks.field_235410_nt_)).build(p_200404_0_, "polished_blackstone_brick_stairs_from_polished_blackstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235410_nt_), Blocks.field_235416_nz_).addCriterion("has_polished_blackstone", hasItem(Blocks.field_235410_nt_)).build(p_200404_0_, "polished_blackstone_brick_wall_from_polished_blackstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235410_nt_), Blocks.field_235413_nw_).addCriterion("has_polished_blackstone", hasItem(Blocks.field_235410_nt_)).build(p_200404_0_, "chiseled_polished_blackstone_from_polished_blackstone_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235411_nu_), Blocks.field_235414_nx_, 2).addCriterion("has_polished_blackstone_bricks", hasItem(Blocks.field_235411_nu_)).build(p_200404_0_, "polished_blackstone_brick_slab_from_polished_blackstone_bricks_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235411_nu_), Blocks.field_235415_ny_).addCriterion("has_polished_blackstone_bricks", hasItem(Blocks.field_235411_nu_)).build(p_200404_0_, "polished_blackstone_brick_stairs_from_polished_blackstone_bricks_stonecutting");
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.field_235411_nu_), Blocks.field_235416_nz_).addCriterion("has_polished_blackstone_bricks", hasItem(Blocks.field_235411_nu_)).build(p_200404_0_, "polished_blackstone_brick_wall_from_polished_blackstone_bricks_stonecutting");
      func_240469_a_(p_200404_0_, Items.DIAMOND_CHESTPLATE, Items.field_234764_lt_);
      func_240469_a_(p_200404_0_, Items.DIAMOND_LEGGINGS, Items.field_234765_lu_);
      func_240469_a_(p_200404_0_, Items.DIAMOND_HELMET, Items.field_234763_ls_);
      func_240469_a_(p_200404_0_, Items.DIAMOND_BOOTS, Items.field_234766_lv_);
      func_240469_a_(p_200404_0_, Items.DIAMOND_SWORD, Items.field_234754_kI_);
      func_240469_a_(p_200404_0_, Items.DIAMOND_AXE, Items.field_234757_kL_);
      func_240469_a_(p_200404_0_, Items.DIAMOND_PICKAXE, Items.field_234756_kK_);
      func_240469_a_(p_200404_0_, Items.DIAMOND_HOE, Items.field_234758_kU_);
      func_240469_a_(p_200404_0_, Items.DIAMOND_SHOVEL, Items.field_234755_kJ_);
   }

   private static void func_240469_a_(Consumer<IFinishedRecipe> p_240469_0_, Item p_240469_1_, Item p_240469_2_) {
      SmithingRecipeBuilder.func_240502_a_(Ingredient.fromItems(p_240469_1_), Ingredient.fromItems(Items.field_234759_km_), p_240469_2_).func_240503_a_("has_netherite_ingot", hasItem(Items.field_234759_km_)).func_240504_a_(p_240469_0_, Registry.ITEM.getKey(p_240469_2_.asItem()).getPath() + "_smithing");
   }

   private static void func_240470_a_(Consumer<IFinishedRecipe> p_240470_0_, IItemProvider p_240470_1_, ITag<Item> p_240470_2_) {
      ShapelessRecipeBuilder.shapelessRecipe(p_240470_1_, 4).addIngredient(p_240470_2_).setGroup("planks").addCriterion("has_log", hasItem(p_240470_2_)).build(p_240470_0_);
   }

   private static void func_240472_b_(Consumer<IFinishedRecipe> p_240472_0_, IItemProvider p_240472_1_, ITag<Item> p_240472_2_) {
      ShapelessRecipeBuilder.shapelessRecipe(p_240472_1_, 4).addIngredient(p_240472_2_).setGroup("planks").addCriterion("has_logs", hasItem(p_240472_2_)).build(p_240472_0_);
   }

   private static void func_240471_a_(Consumer<IFinishedRecipe> p_240471_0_, IItemProvider p_240471_1_, IItemProvider p_240471_2_) {
      ShapedRecipeBuilder.shapedRecipe(p_240471_1_, 3).key('#', p_240471_2_).patternLine("##").patternLine("##").setGroup("bark").addCriterion("has_log", hasItem(p_240471_2_)).build(p_240471_0_);
   }

   private static void func_240473_b_(Consumer<IFinishedRecipe> p_240473_0_, IItemProvider p_240473_1_, IItemProvider p_240473_2_) {
      ShapedRecipeBuilder.shapedRecipe(p_240473_1_).key('#', p_240473_2_).patternLine("# #").patternLine("###").setGroup("boat").addCriterion("in_water", enteredBlock(Blocks.WATER)).build(p_240473_0_);
   }

   private static void func_240474_c_(Consumer<IFinishedRecipe> p_240474_0_, IItemProvider p_240474_1_, IItemProvider p_240474_2_) {
      ShapelessRecipeBuilder.shapelessRecipe(p_240474_1_).addIngredient(p_240474_2_).setGroup("wooden_button").addCriterion("has_planks", hasItem(p_240474_2_)).build(p_240474_0_);
   }

   private static void func_240475_d_(Consumer<IFinishedRecipe> p_240475_0_, IItemProvider p_240475_1_, IItemProvider p_240475_2_) {
      ShapedRecipeBuilder.shapedRecipe(p_240475_1_, 3).key('#', p_240475_2_).patternLine("##").patternLine("##").patternLine("##").setGroup("wooden_door").addCriterion("has_planks", hasItem(p_240475_2_)).build(p_240475_0_);
   }

   private static void func_240476_e_(Consumer<IFinishedRecipe> p_240476_0_, IItemProvider p_240476_1_, IItemProvider p_240476_2_) {
      ShapedRecipeBuilder.shapedRecipe(p_240476_1_, 3).key('#', Items.STICK).key('W', p_240476_2_).patternLine("W#W").patternLine("W#W").setGroup("wooden_fence").addCriterion("has_planks", hasItem(p_240476_2_)).build(p_240476_0_);
   }

   private static void func_240477_f_(Consumer<IFinishedRecipe> p_240477_0_, IItemProvider p_240477_1_, IItemProvider p_240477_2_) {
      ShapedRecipeBuilder.shapedRecipe(p_240477_1_).key('#', Items.STICK).key('W', p_240477_2_).patternLine("#W#").patternLine("#W#").setGroup("wooden_fence_gate").addCriterion("has_planks", hasItem(p_240477_2_)).build(p_240477_0_);
   }

   private static void func_240478_g_(Consumer<IFinishedRecipe> p_240478_0_, IItemProvider p_240478_1_, IItemProvider p_240478_2_) {
      ShapedRecipeBuilder.shapedRecipe(p_240478_1_).key('#', p_240478_2_).patternLine("##").setGroup("wooden_pressure_plate").addCriterion("has_planks", hasItem(p_240478_2_)).build(p_240478_0_);
   }

   private static void func_240479_h_(Consumer<IFinishedRecipe> p_240479_0_, IItemProvider p_240479_1_, IItemProvider p_240479_2_) {
      ShapedRecipeBuilder.shapedRecipe(p_240479_1_, 6).key('#', p_240479_2_).patternLine("###").setGroup("wooden_slab").addCriterion("has_planks", hasItem(p_240479_2_)).build(p_240479_0_);
   }

   private static void func_240480_i_(Consumer<IFinishedRecipe> p_240480_0_, IItemProvider p_240480_1_, IItemProvider p_240480_2_) {
      ShapedRecipeBuilder.shapedRecipe(p_240480_1_, 4).key('#', p_240480_2_).patternLine("#  ").patternLine("## ").patternLine("###").setGroup("wooden_stairs").addCriterion("has_planks", hasItem(p_240480_2_)).build(p_240480_0_);
   }

   private static void func_240481_j_(Consumer<IFinishedRecipe> p_240481_0_, IItemProvider p_240481_1_, IItemProvider p_240481_2_) {
      ShapedRecipeBuilder.shapedRecipe(p_240481_1_, 2).key('#', p_240481_2_).patternLine("###").patternLine("###").setGroup("wooden_trapdoor").addCriterion("has_planks", hasItem(p_240481_2_)).build(p_240481_0_);
   }

   private static void func_240482_k_(Consumer<IFinishedRecipe> p_240482_0_, IItemProvider p_240482_1_, IItemProvider p_240482_2_) {
      String s = Registry.ITEM.getKey(p_240482_2_.asItem()).getPath();
      ShapedRecipeBuilder.shapedRecipe(p_240482_1_, 3).setGroup("sign").key('#', p_240482_2_).key('X', Items.STICK).patternLine("###").patternLine("###").patternLine(" X ").addCriterion("has_" + s, hasItem(p_240482_2_)).build(p_240482_0_);
   }

   private static void func_240483_l_(Consumer<IFinishedRecipe> p_240483_0_, IItemProvider p_240483_1_, IItemProvider p_240483_2_) {
      ShapelessRecipeBuilder.shapelessRecipe(p_240483_1_).addIngredient(p_240483_2_).addIngredient(Blocks.WHITE_WOOL).setGroup("wool").addCriterion("has_white_wool", hasItem(Blocks.WHITE_WOOL)).build(p_240483_0_);
   }

   private static void func_240484_m_(Consumer<IFinishedRecipe> p_240484_0_, IItemProvider p_240484_1_, IItemProvider p_240484_2_) {
      String s = Registry.ITEM.getKey(p_240484_2_.asItem()).getPath();
      ShapedRecipeBuilder.shapedRecipe(p_240484_1_, 3).key('#', p_240484_2_).patternLine("##").setGroup("carpet").addCriterion("has_" + s, hasItem(p_240484_2_)).build(p_240484_0_);
   }

   private static void func_240485_n_(Consumer<IFinishedRecipe> p_240485_0_, IItemProvider p_240485_1_, IItemProvider p_240485_2_) {
      String s = Registry.ITEM.getKey(p_240485_1_.asItem()).getPath();
      String s1 = Registry.ITEM.getKey(p_240485_2_.asItem()).getPath();
      ShapedRecipeBuilder.shapedRecipe(p_240485_1_, 8).key('#', Blocks.WHITE_CARPET).key('$', p_240485_2_).patternLine("###").patternLine("#$#").patternLine("###").setGroup("carpet").addCriterion("has_white_carpet", hasItem(Blocks.WHITE_CARPET)).addCriterion("has_" + s1, hasItem(p_240485_2_)).build(p_240485_0_, s + "_from_white_carpet");
   }

   private static void func_240486_o_(Consumer<IFinishedRecipe> p_240486_0_, IItemProvider p_240486_1_, IItemProvider p_240486_2_) {
      String s = Registry.ITEM.getKey(p_240486_2_.asItem()).getPath();
      ShapedRecipeBuilder.shapedRecipe(p_240486_1_).key('#', p_240486_2_).key('X', ItemTags.PLANKS).patternLine("###").patternLine("XXX").setGroup("bed").addCriterion("has_" + s, hasItem(p_240486_2_)).build(p_240486_0_);
   }

   private static void func_240487_p_(Consumer<IFinishedRecipe> p_240487_0_, IItemProvider p_240487_1_, IItemProvider p_240487_2_) {
      String s = Registry.ITEM.getKey(p_240487_1_.asItem()).getPath();
      ShapelessRecipeBuilder.shapelessRecipe(p_240487_1_).addIngredient(Items.WHITE_BED).addIngredient(p_240487_2_).setGroup("dyed_bed").addCriterion("has_bed", hasItem(Items.WHITE_BED)).build(p_240487_0_, s + "_from_white_bed");
   }

   private static void func_240488_q_(Consumer<IFinishedRecipe> p_240488_0_, IItemProvider p_240488_1_, IItemProvider p_240488_2_) {
      String s = Registry.ITEM.getKey(p_240488_2_.asItem()).getPath();
      ShapedRecipeBuilder.shapedRecipe(p_240488_1_).key('#', p_240488_2_).key('|', Items.STICK).patternLine("###").patternLine("###").patternLine(" | ").setGroup("banner").addCriterion("has_" + s, hasItem(p_240488_2_)).build(p_240488_0_);
   }

   private static void func_240489_r_(Consumer<IFinishedRecipe> p_240489_0_, IItemProvider p_240489_1_, IItemProvider p_240489_2_) {
      ShapedRecipeBuilder.shapedRecipe(p_240489_1_, 8).key('#', Blocks.GLASS).key('X', p_240489_2_).patternLine("###").patternLine("#X#").patternLine("###").setGroup("stained_glass").addCriterion("has_glass", hasItem(Blocks.GLASS)).build(p_240489_0_);
   }

   private static void func_240490_s_(Consumer<IFinishedRecipe> p_240490_0_, IItemProvider p_240490_1_, IItemProvider p_240490_2_) {
      ShapedRecipeBuilder.shapedRecipe(p_240490_1_, 16).key('#', p_240490_2_).patternLine("###").patternLine("###").setGroup("stained_glass_pane").addCriterion("has_glass", hasItem(p_240490_2_)).build(p_240490_0_);
   }

   private static void func_240491_t_(Consumer<IFinishedRecipe> p_240491_0_, IItemProvider p_240491_1_, IItemProvider p_240491_2_) {
      String s = Registry.ITEM.getKey(p_240491_1_.asItem()).getPath();
      String s1 = Registry.ITEM.getKey(p_240491_2_.asItem()).getPath();
      ShapedRecipeBuilder.shapedRecipe(p_240491_1_, 8).key('#', Blocks.GLASS_PANE).key('$', p_240491_2_).patternLine("###").patternLine("#$#").patternLine("###").setGroup("stained_glass_pane").addCriterion("has_glass_pane", hasItem(Blocks.GLASS_PANE)).addCriterion("has_" + s1, hasItem(p_240491_2_)).build(p_240491_0_, s + "_from_glass_pane");
   }

   private static void func_240492_u_(Consumer<IFinishedRecipe> p_240492_0_, IItemProvider p_240492_1_, IItemProvider p_240492_2_) {
      ShapedRecipeBuilder.shapedRecipe(p_240492_1_, 8).key('#', Blocks.TERRACOTTA).key('X', p_240492_2_).patternLine("###").patternLine("#X#").patternLine("###").setGroup("stained_terracotta").addCriterion("has_terracotta", hasItem(Blocks.TERRACOTTA)).build(p_240492_0_);
   }

   private static void func_240493_v_(Consumer<IFinishedRecipe> p_240493_0_, IItemProvider p_240493_1_, IItemProvider p_240493_2_) {
      ShapelessRecipeBuilder.shapelessRecipe(p_240493_1_, 8).addIngredient(p_240493_2_).addIngredient(Blocks.SAND, 4).addIngredient(Blocks.GRAVEL, 4).setGroup("concrete_powder").addCriterion("has_sand", hasItem(Blocks.SAND)).addCriterion("has_gravel", hasItem(Blocks.GRAVEL)).build(p_240493_0_);
   }

   private static void cookingRecipesForMethod(Consumer<IFinishedRecipe> p_218445_0_, String recipeConsumerIn, CookingRecipeSerializer<?> cookingMethod, int serializerIn) {
      CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.BEEF), Items.COOKED_BEEF, 0.35F, serializerIn, cookingMethod).addCriterion("has_beef", hasItem(Items.BEEF)).build(p_218445_0_, "cooked_beef_from_" + recipeConsumerIn);
      CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.CHICKEN), Items.COOKED_CHICKEN, 0.35F, serializerIn, cookingMethod).addCriterion("has_chicken", hasItem(Items.CHICKEN)).build(p_218445_0_, "cooked_chicken_from_" + recipeConsumerIn);
      CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.COD), Items.COOKED_COD, 0.35F, serializerIn, cookingMethod).addCriterion("has_cod", hasItem(Items.COD)).build(p_218445_0_, "cooked_cod_from_" + recipeConsumerIn);
      CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Blocks.KELP), Items.DRIED_KELP, 0.1F, serializerIn, cookingMethod).addCriterion("has_kelp", hasItem(Blocks.KELP)).build(p_218445_0_, "dried_kelp_from_" + recipeConsumerIn);
      CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.SALMON), Items.COOKED_SALMON, 0.35F, serializerIn, cookingMethod).addCriterion("has_salmon", hasItem(Items.SALMON)).build(p_218445_0_, "cooked_salmon_from_" + recipeConsumerIn);
      CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.MUTTON), Items.COOKED_MUTTON, 0.35F, serializerIn, cookingMethod).addCriterion("has_mutton", hasItem(Items.MUTTON)).build(p_218445_0_, "cooked_mutton_from_" + recipeConsumerIn);
      CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.PORKCHOP), Items.COOKED_PORKCHOP, 0.35F, serializerIn, cookingMethod).addCriterion("has_porkchop", hasItem(Items.PORKCHOP)).build(p_218445_0_, "cooked_porkchop_from_" + recipeConsumerIn);
      CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.POTATO), Items.BAKED_POTATO, 0.35F, serializerIn, cookingMethod).addCriterion("has_potato", hasItem(Items.POTATO)).build(p_218445_0_, "baked_potato_from_" + recipeConsumerIn);
      CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.RABBIT), Items.COOKED_RABBIT, 0.35F, serializerIn, cookingMethod).addCriterion("has_rabbit", hasItem(Items.RABBIT)).build(p_218445_0_, "cooked_rabbit_from_" + recipeConsumerIn);
   }

   /**
    * Creates a new {@link EnterBlockTrigger} for use with recipe unlock criteria.
    */
   protected static EnterBlockTrigger.Instance enteredBlock(Block p_200407_0_) {
      return new EnterBlockTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, p_200407_0_, StatePropertiesPredicate.EMPTY);
   }

   /**
    * Creates a new {@link InventoryChangeTrigger} that checks for a player having a certain item.
    */
   protected static InventoryChangeTrigger.Instance hasItem(IItemProvider p_200403_0_) {
      return hasItem(ItemPredicate.Builder.create().item(p_200403_0_).build());
   }

   /**
    * Creates a new {@link InventoryChangeTrigger} that checks for a player having an item within the given tag.
    */
   protected static InventoryChangeTrigger.Instance hasItem(ITag<Item> p_200409_0_) {
      return hasItem(ItemPredicate.Builder.create().tag(p_200409_0_).build());
   }

   /**
    * Creates a new {@link InventoryChangeTrigger} that checks for a player having a certain item.
    */
   protected static InventoryChangeTrigger.Instance hasItem(ItemPredicate... p_200405_0_) {
      return new InventoryChangeTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, p_200405_0_);
   }

   /**
    * Gets a name for this provider, to use in logging.
    */
   public String getName() {
      return "Recipes";
   }
}