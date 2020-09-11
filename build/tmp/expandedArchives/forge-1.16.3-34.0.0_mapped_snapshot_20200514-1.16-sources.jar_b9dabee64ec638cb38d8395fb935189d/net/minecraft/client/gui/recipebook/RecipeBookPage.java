package net.minecraft.client.gui.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ToggleWidget;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBook;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RecipeBookPage {
   private final List<RecipeWidget> buttons = Lists.newArrayListWithCapacity(20);
   private RecipeWidget hoveredButton;
   private final RecipeOverlayGui overlay = new RecipeOverlayGui();
   private Minecraft minecraft;
   private final List<IRecipeUpdateListener> listeners = Lists.newArrayList();
   private List<RecipeList> recipeLists;
   private ToggleWidget forwardButton;
   private ToggleWidget backButton;
   private int totalPages;
   private int currentPage;
   private RecipeBook recipeBook;
   private IRecipe<?> lastClickedRecipe;
   private RecipeList lastClickedRecipeList;

   public RecipeBookPage() {
      for(int i = 0; i < 20; ++i) {
         this.buttons.add(new RecipeWidget());
      }

   }

   public void init(Minecraft p_194194_1_, int p_194194_2_, int p_194194_3_) {
      this.minecraft = p_194194_1_;
      this.recipeBook = p_194194_1_.player.getRecipeBook();

      for(int i = 0; i < this.buttons.size(); ++i) {
         this.buttons.get(i).setPosition(p_194194_2_ + 11 + 25 * (i % 5), p_194194_3_ + 31 + 25 * (i / 5));
      }

      this.forwardButton = new ToggleWidget(p_194194_2_ + 93, p_194194_3_ + 137, 12, 17, false);
      this.forwardButton.initTextureValues(1, 208, 13, 18, RecipeBookGui.RECIPE_BOOK);
      this.backButton = new ToggleWidget(p_194194_2_ + 38, p_194194_3_ + 137, 12, 17, true);
      this.backButton.initTextureValues(1, 208, 13, 18, RecipeBookGui.RECIPE_BOOK);
   }

   public void addListener(RecipeBookGui p_193732_1_) {
      this.listeners.remove(p_193732_1_);
      this.listeners.add(p_193732_1_);
   }

   public void updateLists(List<RecipeList> p_194192_1_, boolean p_194192_2_) {
      this.recipeLists = p_194192_1_;
      this.totalPages = (int)Math.ceil((double)p_194192_1_.size() / 20.0D);
      if (this.totalPages <= this.currentPage || p_194192_2_) {
         this.currentPage = 0;
      }

      this.updateButtonsForPage();
   }

   private void updateButtonsForPage() {
      int i = 20 * this.currentPage;

      for(int j = 0; j < this.buttons.size(); ++j) {
         RecipeWidget recipewidget = this.buttons.get(j);
         if (i + j < this.recipeLists.size()) {
            RecipeList recipelist = this.recipeLists.get(i + j);
            recipewidget.func_203400_a(recipelist, this);
            recipewidget.field_230694_p_ = true;
         } else {
            recipewidget.field_230694_p_ = false;
         }
      }

      this.updateArrowButtons();
   }

   private void updateArrowButtons() {
      this.forwardButton.field_230694_p_ = this.totalPages > 1 && this.currentPage < this.totalPages - 1;
      this.backButton.field_230694_p_ = this.totalPages > 1 && this.currentPage > 0;
   }

   public void func_238927_a_(MatrixStack p_238927_1_, int p_238927_2_, int p_238927_3_, int p_238927_4_, int p_238927_5_, float p_238927_6_) {
      if (this.totalPages > 1) {
         String s = this.currentPage + 1 + "/" + this.totalPages;
         int i = this.minecraft.fontRenderer.getStringWidth(s);
         this.minecraft.fontRenderer.func_238421_b_(p_238927_1_, s, (float)(p_238927_2_ - i / 2 + 73), (float)(p_238927_3_ + 141), -1);
      }

      this.hoveredButton = null;

      for(RecipeWidget recipewidget : this.buttons) {
         recipewidget.func_230430_a_(p_238927_1_, p_238927_4_, p_238927_5_, p_238927_6_);
         if (recipewidget.field_230694_p_ && recipewidget.func_230449_g_()) {
            this.hoveredButton = recipewidget;
         }
      }

      this.backButton.func_230430_a_(p_238927_1_, p_238927_4_, p_238927_5_, p_238927_6_);
      this.forwardButton.func_230430_a_(p_238927_1_, p_238927_4_, p_238927_5_, p_238927_6_);
      this.overlay.func_230430_a_(p_238927_1_, p_238927_4_, p_238927_5_, p_238927_6_);
   }

   public void func_238926_a_(MatrixStack p_238926_1_, int p_238926_2_, int p_238926_3_) {
      if (this.minecraft.currentScreen != null && this.hoveredButton != null && !this.overlay.isVisible()) {
         this.minecraft.currentScreen.func_243308_b(p_238926_1_, this.hoveredButton.getToolTipText(this.minecraft.currentScreen), p_238926_2_, p_238926_3_);
      }

   }

   @Nullable
   public IRecipe<?> getLastClickedRecipe() {
      return this.lastClickedRecipe;
   }

   @Nullable
   public RecipeList getLastClickedRecipeList() {
      return this.lastClickedRecipeList;
   }

   public void setInvisible() {
      this.overlay.setVisible(false);
   }

   public boolean func_198955_a(double p_198955_1_, double p_198955_3_, int p_198955_5_, int p_198955_6_, int p_198955_7_, int p_198955_8_, int p_198955_9_) {
      this.lastClickedRecipe = null;
      this.lastClickedRecipeList = null;
      if (this.overlay.isVisible()) {
         if (this.overlay.func_231044_a_(p_198955_1_, p_198955_3_, p_198955_5_)) {
            this.lastClickedRecipe = this.overlay.getLastRecipeClicked();
            this.lastClickedRecipeList = this.overlay.getRecipeList();
         } else {
            this.overlay.setVisible(false);
         }

         return true;
      } else if (this.forwardButton.func_231044_a_(p_198955_1_, p_198955_3_, p_198955_5_)) {
         ++this.currentPage;
         this.updateButtonsForPage();
         return true;
      } else if (this.backButton.func_231044_a_(p_198955_1_, p_198955_3_, p_198955_5_)) {
         --this.currentPage;
         this.updateButtonsForPage();
         return true;
      } else {
         for(RecipeWidget recipewidget : this.buttons) {
            if (recipewidget.func_231044_a_(p_198955_1_, p_198955_3_, p_198955_5_)) {
               if (p_198955_5_ == 0) {
                  this.lastClickedRecipe = recipewidget.getRecipe();
                  this.lastClickedRecipeList = recipewidget.getList();
               } else if (p_198955_5_ == 1 && !this.overlay.isVisible() && !recipewidget.isOnlyOption()) {
                  this.overlay.func_201703_a(this.minecraft, recipewidget.getList(), recipewidget.field_230690_l_, recipewidget.field_230691_m_, p_198955_6_ + p_198955_8_ / 2, p_198955_7_ + 13 + p_198955_9_ / 2, (float)recipewidget.func_230998_h_());
               }

               return true;
            }
         }

         return false;
      }
   }

   public void recipesShown(List<IRecipe<?>> p_194195_1_) {
      for(IRecipeUpdateListener irecipeupdatelistener : this.listeners) {
         irecipeupdatelistener.recipesShown(p_194195_1_);
      }

   }

   public Minecraft func_203411_d() {
      return this.minecraft;
   }

   public RecipeBook func_203412_e() {
      return this.recipeBook;
   }
}