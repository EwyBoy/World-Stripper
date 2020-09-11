package net.minecraft.client.gui.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBook;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RecipeWidget extends Widget {
   private static final ResourceLocation RECIPE_BOOK = new ResourceLocation("textures/gui/recipe_book.png");
   private static final ITextComponent field_243412_b = new TranslationTextComponent("gui.recipebook.moreRecipes");
   private RecipeBookContainer<?> field_203401_p;
   private RecipeBook book;
   private RecipeList list;
   private float time;
   private float animationTime;
   private int currentIndex;

   public RecipeWidget() {
      super(0, 0, 25, 25, StringTextComponent.field_240750_d_);
   }

   public void func_203400_a(RecipeList p_203400_1_, RecipeBookPage p_203400_2_) {
      this.list = p_203400_1_;
      this.field_203401_p = (RecipeBookContainer)p_203400_2_.func_203411_d().player.openContainer;
      this.book = p_203400_2_.func_203412_e();
      List<IRecipe<?>> list = p_203400_1_.getRecipes(this.book.func_242141_a(this.field_203401_p));

      for(IRecipe<?> irecipe : list) {
         if (this.book.isNew(irecipe)) {
            p_203400_2_.recipesShown(list);
            this.animationTime = 15.0F;
            break;
         }
      }

   }

   public RecipeList getList() {
      return this.list;
   }

   public void setPosition(int p_191770_1_, int p_191770_2_) {
      this.field_230690_l_ = p_191770_1_;
      this.field_230691_m_ = p_191770_2_;
   }

   public void func_230431_b_(MatrixStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
      if (!Screen.func_231172_r_()) {
         this.time += p_230431_4_;
      }

      Minecraft minecraft = Minecraft.getInstance();
      minecraft.getTextureManager().bindTexture(RECIPE_BOOK);
      int i = 29;
      if (!this.list.containsCraftableRecipes()) {
         i += 25;
      }

      int j = 206;
      if (this.list.getRecipes(this.book.func_242141_a(this.field_203401_p)).size() > 1) {
         j += 25;
      }

      boolean flag = this.animationTime > 0.0F;
      if (flag) {
         float f = 1.0F + 0.1F * (float)Math.sin((double)(this.animationTime / 15.0F * (float)Math.PI));
         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)(this.field_230690_l_ + 8), (float)(this.field_230691_m_ + 12), 0.0F);
         RenderSystem.scalef(f, f, 1.0F);
         RenderSystem.translatef((float)(-(this.field_230690_l_ + 8)), (float)(-(this.field_230691_m_ + 12)), 0.0F);
         this.animationTime -= p_230431_4_;
      }

      this.func_238474_b_(p_230431_1_, this.field_230690_l_, this.field_230691_m_, i, j, this.field_230688_j_, this.field_230689_k_);
      List<IRecipe<?>> list = this.getOrderedRecipes();
      this.currentIndex = MathHelper.floor(this.time / 30.0F) % list.size();
      ItemStack itemstack = list.get(this.currentIndex).getRecipeOutput();
      int k = 4;
      if (this.list.hasSingleResultItem() && this.getOrderedRecipes().size() > 1) {
         minecraft.getItemRenderer().renderItemAndEffectIntoGUI(itemstack, this.field_230690_l_ + k + 1, this.field_230691_m_ + k + 1);
         --k;
      }

      minecraft.getItemRenderer().func_239390_c_(itemstack, this.field_230690_l_ + k, this.field_230691_m_ + k);
      if (flag) {
         RenderSystem.popMatrix();
      }

   }

   private List<IRecipe<?>> getOrderedRecipes() {
      List<IRecipe<?>> list = this.list.getDisplayRecipes(true);
      if (!this.book.func_242141_a(this.field_203401_p)) {
         list.addAll(this.list.getDisplayRecipes(false));
      }

      return list;
   }

   public boolean isOnlyOption() {
      return this.getOrderedRecipes().size() == 1;
   }

   public IRecipe<?> getRecipe() {
      List<IRecipe<?>> list = this.getOrderedRecipes();
      return list.get(this.currentIndex);
   }

   public List<ITextComponent> getToolTipText(Screen p_191772_1_) {
      ItemStack itemstack = this.getOrderedRecipes().get(this.currentIndex).getRecipeOutput();
      List<ITextComponent> list = Lists.newArrayList(p_191772_1_.func_231151_a_(itemstack));
      if (this.list.getRecipes(this.book.func_242141_a(this.field_203401_p)).size() > 1) {
         list.add(field_243412_b);
      }

      return list;
   }

   public int func_230998_h_() {
      return 25;
   }

   protected boolean func_230987_a_(int p_230987_1_) {
      return p_230987_1_ == 0 || p_230987_1_ == 1;
   }
}