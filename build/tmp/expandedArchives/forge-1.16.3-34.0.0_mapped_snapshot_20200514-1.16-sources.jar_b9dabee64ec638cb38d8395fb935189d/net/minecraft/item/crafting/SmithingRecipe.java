package net.minecraft.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SmithingRecipe implements IRecipe<IInventory> {
   private final Ingredient field_234837_a_;
   private final Ingredient field_234838_b_;
   private final ItemStack field_234839_c_;
   private final ResourceLocation field_234840_d_;

   public SmithingRecipe(ResourceLocation p_i231600_1_, Ingredient p_i231600_2_, Ingredient p_i231600_3_, ItemStack p_i231600_4_) {
      this.field_234840_d_ = p_i231600_1_;
      this.field_234837_a_ = p_i231600_2_;
      this.field_234838_b_ = p_i231600_3_;
      this.field_234839_c_ = p_i231600_4_;
   }

   /**
    * Used to check if a recipe matches current crafting inventory
    */
   public boolean matches(IInventory inv, World worldIn) {
      return this.field_234837_a_.test(inv.getStackInSlot(0)) && this.field_234838_b_.test(inv.getStackInSlot(1));
   }

   /**
    * Returns an Item that is the result of this recipe
    */
   public ItemStack getCraftingResult(IInventory inv) {
      ItemStack itemstack = this.field_234839_c_.copy();
      CompoundNBT compoundnbt = inv.getStackInSlot(0).getTag();
      if (compoundnbt != null) {
         itemstack.setTag(compoundnbt.copy());
      }

      return itemstack;
   }

   /**
    * Used to determine if this recipe can fit in a grid of the given width/height
    */
   public boolean canFit(int width, int height) {
      return width * height >= 2;
   }

   /**
    * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
    * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
    */
   public ItemStack getRecipeOutput() {
      return this.field_234839_c_;
   }

   public boolean func_241456_a_(ItemStack p_241456_1_) {
      return this.field_234838_b_.test(p_241456_1_);
   }

   public ItemStack getIcon() {
      return new ItemStack(Blocks.SMITHING_TABLE);
   }

   public ResourceLocation getId() {
      return this.field_234840_d_;
   }

   public IRecipeSerializer<?> getSerializer() {
      return IRecipeSerializer.field_234826_u_;
   }

   public IRecipeType<?> getType() {
      return IRecipeType.field_234827_g_;
   }

   public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<SmithingRecipe> {
      public SmithingRecipe read(ResourceLocation recipeId, JsonObject json) {
         Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "base"));
         Ingredient ingredient1 = Ingredient.deserialize(JSONUtils.getJsonObject(json, "addition"));
         ItemStack itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
         return new SmithingRecipe(recipeId, ingredient, ingredient1, itemstack);
      }

      public SmithingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
         Ingredient ingredient = Ingredient.read(buffer);
         Ingredient ingredient1 = Ingredient.read(buffer);
         ItemStack itemstack = buffer.readItemStack();
         return new SmithingRecipe(recipeId, ingredient, ingredient1, itemstack);
      }

      public void write(PacketBuffer buffer, SmithingRecipe recipe) {
         recipe.field_234837_a_.write(buffer);
         recipe.field_234838_b_.write(buffer);
         buffer.writeItemStack(recipe.field_234839_c_);
      }
   }
}