package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.advancements.Advancement;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.loot.LootPredicateManager;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;

public class ResourceLocationArgument implements ArgumentType<ResourceLocation> {
   private static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo:bar", "012");
   private static final DynamicCommandExceptionType ADVANCEMENT_NOT_FOUND = new DynamicCommandExceptionType((p_208676_0_) -> {
      return new TranslationTextComponent("advancement.advancementNotFound", p_208676_0_);
   });
   private static final DynamicCommandExceptionType RECIPE_NOT_FOUND = new DynamicCommandExceptionType((p_208677_0_) -> {
      return new TranslationTextComponent("recipe.notFound", p_208677_0_);
   });
   private static final DynamicCommandExceptionType field_228258_d_ = new DynamicCommandExceptionType((p_208674_0_) -> {
      return new TranslationTextComponent("predicate.unknown", p_208674_0_);
   });
   private static final DynamicCommandExceptionType field_239090_e_ = new DynamicCommandExceptionType((p_239091_0_) -> {
      return new TranslationTextComponent("attribute.unknown", p_239091_0_);
   });

   public static ResourceLocationArgument resourceLocation() {
      return new ResourceLocationArgument();
   }

   public static Advancement getAdvancement(CommandContext<CommandSource> context, String name) throws CommandSyntaxException {
      ResourceLocation resourcelocation = context.getArgument(name, ResourceLocation.class);
      Advancement advancement = context.getSource().getServer().getAdvancementManager().getAdvancement(resourcelocation);
      if (advancement == null) {
         throw ADVANCEMENT_NOT_FOUND.create(resourcelocation);
      } else {
         return advancement;
      }
   }

   public static IRecipe<?> getRecipe(CommandContext<CommandSource> context, String name) throws CommandSyntaxException {
      RecipeManager recipemanager = context.getSource().getServer().getRecipeManager();
      ResourceLocation resourcelocation = context.getArgument(name, ResourceLocation.class);
      return recipemanager.getRecipe(resourcelocation).orElseThrow(() -> {
         return RECIPE_NOT_FOUND.create(resourcelocation);
      });
   }

   public static ILootCondition func_228259_c_(CommandContext<CommandSource> p_228259_0_, String p_228259_1_) throws CommandSyntaxException {
      ResourceLocation resourcelocation = p_228259_0_.getArgument(p_228259_1_, ResourceLocation.class);
      LootPredicateManager lootpredicatemanager = p_228259_0_.getSource().getServer().func_229736_aP_();
      ILootCondition ilootcondition = lootpredicatemanager.func_227517_a_(resourcelocation);
      if (ilootcondition == null) {
         throw field_228258_d_.create(resourcelocation);
      } else {
         return ilootcondition;
      }
   }

   public static Attribute func_239094_d_(CommandContext<CommandSource> p_239094_0_, String p_239094_1_) throws CommandSyntaxException {
      ResourceLocation resourcelocation = p_239094_0_.getArgument(p_239094_1_, ResourceLocation.class);
      return Registry.field_239692_aP_.func_241873_b(resourcelocation).orElseThrow(() -> {
         return field_239090_e_.create(resourcelocation);
      });
   }

   public static ResourceLocation getResourceLocation(CommandContext<CommandSource> context, String name) {
      return context.getArgument(name, ResourceLocation.class);
   }

   public ResourceLocation parse(StringReader p_parse_1_) throws CommandSyntaxException {
      return ResourceLocation.read(p_parse_1_);
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}