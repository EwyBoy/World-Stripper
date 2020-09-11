package net.minecraft.client.gui.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.ToggleWidget;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.util.ClientRecipeBook;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipePlacer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.network.play.client.CUpdateRecipeBookStatusPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RecipeBookGui extends AbstractGui implements IRenderable, IGuiEventListener, IRecipeUpdateListener, IRecipePlacer<Ingredient> {
   protected static final ResourceLocation RECIPE_BOOK = new ResourceLocation("textures/gui/recipe_book.png");
   private static final ITextComponent field_241620_l_ = (new TranslationTextComponent("gui.recipebook.search_hint")).func_240699_a_(TextFormatting.ITALIC).func_240699_a_(TextFormatting.GRAY);
   private static final ITextComponent field_243410_j = new TranslationTextComponent("gui.recipebook.toggleRecipes.craftable");
   private static final ITextComponent field_243411_k = new TranslationTextComponent("gui.recipebook.toggleRecipes.all");
   private int xOffset;
   private int width;
   private int height;
   protected final GhostRecipe ghostRecipe = new GhostRecipe();
   private final List<RecipeTabToggleWidget> recipeTabs = Lists.newArrayList();
   private RecipeTabToggleWidget currentTab;
   protected ToggleWidget toggleRecipesBtn;
   protected RecipeBookContainer<?> field_201522_g;
   protected Minecraft mc;
   private TextFieldWidget searchBar;
   private String lastSearch = "";
   private ClientRecipeBook recipeBook;
   private final RecipeBookPage recipeBookPage = new RecipeBookPage();
   private final RecipeItemHelper stackedContents = new RecipeItemHelper();
   private int timesInventoryChanged;
   private boolean field_199738_u;

   public void init(int widthIn, int heightIn, Minecraft minecraftIn, boolean widthTooNarrowIn, RecipeBookContainer<?> containerIn) {
      this.mc = minecraftIn;
      this.width = widthIn;
      this.height = heightIn;
      this.field_201522_g = containerIn;
      minecraftIn.player.openContainer = containerIn;
      this.recipeBook = minecraftIn.player.getRecipeBook();
      this.timesInventoryChanged = minecraftIn.player.inventory.getTimesChanged();
      if (this.isVisible()) {
         this.initSearchBar(widthTooNarrowIn);
      }

      minecraftIn.keyboardListener.enableRepeatEvents(true);
   }

   public void initSearchBar(boolean widthTooNarrowIn) {
      this.xOffset = widthTooNarrowIn ? 0 : 86;
      int i = (this.width - 147) / 2 - this.xOffset;
      int j = (this.height - 166) / 2;
      this.stackedContents.clear();
      this.mc.player.inventory.accountStacks(this.stackedContents);
      this.field_201522_g.fillStackedContents(this.stackedContents);
      String s = this.searchBar != null ? this.searchBar.getText() : "";
      this.searchBar = new TextFieldWidget(this.mc.fontRenderer, i + 25, j + 14, 80, 9 + 5, new TranslationTextComponent("itemGroup.search"));
      this.searchBar.setMaxStringLength(50);
      this.searchBar.setEnableBackgroundDrawing(false);
      this.searchBar.setVisible(true);
      this.searchBar.setTextColor(16777215);
      this.searchBar.setText(s);
      this.recipeBookPage.init(this.mc, i, j);
      this.recipeBookPage.addListener(this);
      this.toggleRecipesBtn = new ToggleWidget(i + 110, j + 12, 26, 16, this.recipeBook.func_242141_a(this.field_201522_g));
      this.func_205702_a();
      this.recipeTabs.clear();

      for(RecipeBookCategories recipebookcategories : this.field_201522_g.getRecipeBookCategories()) {
         this.recipeTabs.add(new RecipeTabToggleWidget(recipebookcategories));
      }

      if (this.currentTab != null) {
         this.currentTab = this.recipeTabs.stream().filter((p_209505_1_) -> {
            return p_209505_1_.func_201503_d().equals(this.currentTab.func_201503_d());
         }).findFirst().orElse((RecipeTabToggleWidget)null);
      }

      if (this.currentTab == null) {
         this.currentTab = this.recipeTabs.get(0);
      }

      this.currentTab.setStateTriggered(true);
      this.updateCollections(false);
      this.updateTabs();
   }

   public boolean func_231049_c__(boolean p_231049_1_) {
      return false;
   }

   protected void func_205702_a() {
      this.toggleRecipesBtn.initTextureValues(152, 41, 28, 18, RECIPE_BOOK);
   }

   public void removed() {
      this.searchBar = null;
      this.currentTab = null;
      this.mc.keyboardListener.enableRepeatEvents(false);
   }

   public int updateScreenPosition(boolean p_193011_1_, int p_193011_2_, int p_193011_3_) {
      int i;
      if (this.isVisible() && !p_193011_1_) {
         i = 177 + (p_193011_2_ - p_193011_3_ - 200) / 2;
      } else {
         i = (p_193011_2_ - p_193011_3_) / 2;
      }

      return i;
   }

   public void toggleVisibility() {
      this.setVisible(!this.isVisible());
   }

   public boolean isVisible() {
      return this.recipeBook.func_242142_a(this.field_201522_g.func_241850_m());
   }

   protected void setVisible(boolean p_193006_1_) {
      this.recipeBook.func_242143_a(this.field_201522_g.func_241850_m(), p_193006_1_);
      if (!p_193006_1_) {
         this.recipeBookPage.setInvisible();
      }

      this.sendUpdateSettings();
   }

   public void slotClicked(@Nullable Slot slotIn) {
      if (slotIn != null && slotIn.slotNumber < this.field_201522_g.getSize()) {
         this.ghostRecipe.clear();
         if (this.isVisible()) {
            this.updateStackedContents();
         }
      }

   }

   private void updateCollections(boolean p_193003_1_) {
      List<RecipeList> list = this.recipeBook.getRecipes(this.currentTab.func_201503_d());
      list.forEach((p_193944_1_) -> {
         p_193944_1_.canCraft(this.stackedContents, this.field_201522_g.getWidth(), this.field_201522_g.getHeight(), this.recipeBook);
      });
      List<RecipeList> list1 = Lists.newArrayList(list);
      list1.removeIf((p_193952_0_) -> {
         return !p_193952_0_.isNotEmpty();
      });
      list1.removeIf((p_193953_0_) -> {
         return !p_193953_0_.containsValidRecipes();
      });
      String s = this.searchBar.getText();
      if (!s.isEmpty()) {
         ObjectSet<RecipeList> objectset = new ObjectLinkedOpenHashSet<>(this.mc.getSearchTree(SearchTreeManager.RECIPES).search(s.toLowerCase(Locale.ROOT)));
         list1.removeIf((p_193947_1_) -> {
            return !objectset.contains(p_193947_1_);
         });
      }

      if (this.recipeBook.func_242141_a(this.field_201522_g)) {
         list1.removeIf((p_193958_0_) -> {
            return !p_193958_0_.containsCraftableRecipes();
         });
      }

      this.recipeBookPage.updateLists(list1, p_193003_1_);
   }

   private void updateTabs() {
      int i = (this.width - 147) / 2 - this.xOffset - 30;
      int j = (this.height - 166) / 2 + 3;
      int k = 27;
      int l = 0;

      for(RecipeTabToggleWidget recipetabtogglewidget : this.recipeTabs) {
         RecipeBookCategories recipebookcategories = recipetabtogglewidget.func_201503_d();
         if (recipebookcategories != RecipeBookCategories.CRAFTING_SEARCH && recipebookcategories != RecipeBookCategories.FURNACE_SEARCH) {
            if (recipetabtogglewidget.func_199500_a(this.recipeBook)) {
               recipetabtogglewidget.setPosition(i, j + 27 * l++);
               recipetabtogglewidget.startAnimation(this.mc);
            }
         } else {
            recipetabtogglewidget.field_230694_p_ = true;
            recipetabtogglewidget.setPosition(i, j + 27 * l++);
         }
      }

   }

   public void tick() {
      if (this.isVisible()) {
         if (this.timesInventoryChanged != this.mc.player.inventory.getTimesChanged()) {
            this.updateStackedContents();
            this.timesInventoryChanged = this.mc.player.inventory.getTimesChanged();
         }

      }
   }

   private void updateStackedContents() {
      this.stackedContents.clear();
      this.mc.player.inventory.accountStacks(this.stackedContents);
      this.field_201522_g.fillStackedContents(this.stackedContents);
      this.updateCollections(false);
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      if (this.isVisible()) {
         RenderSystem.pushMatrix();
         RenderSystem.translatef(0.0F, 0.0F, 100.0F);
         this.mc.getTextureManager().bindTexture(RECIPE_BOOK);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         int i = (this.width - 147) / 2 - this.xOffset;
         int j = (this.height - 166) / 2;
         this.func_238474_b_(p_230430_1_, i, j, 1, 1, 147, 166);
         if (!this.searchBar.func_230999_j_() && this.searchBar.getText().isEmpty()) {
            func_238475_b_(p_230430_1_, this.mc.fontRenderer, field_241620_l_, i + 25, j + 14, -1);
         } else {
            this.searchBar.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
         }

         for(RecipeTabToggleWidget recipetabtogglewidget : this.recipeTabs) {
            recipetabtogglewidget.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
         }

         this.toggleRecipesBtn.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
         this.recipeBookPage.func_238927_a_(p_230430_1_, i, j, p_230430_2_, p_230430_3_, p_230430_4_);
         RenderSystem.popMatrix();
      }
   }

   public void func_238924_c_(MatrixStack p_238924_1_, int p_238924_2_, int p_238924_3_, int p_238924_4_, int p_238924_5_) {
      if (this.isVisible()) {
         this.recipeBookPage.func_238926_a_(p_238924_1_, p_238924_4_, p_238924_5_);
         if (this.toggleRecipesBtn.func_230449_g_()) {
            ITextComponent itextcomponent = this.func_230478_f_();
            if (this.mc.currentScreen != null) {
               this.mc.currentScreen.func_238652_a_(p_238924_1_, itextcomponent, p_238924_4_, p_238924_5_);
            }
         }

         this.func_238925_d_(p_238924_1_, p_238924_2_, p_238924_3_, p_238924_4_, p_238924_5_);
      }
   }

   private ITextComponent func_230478_f_() {
      return this.toggleRecipesBtn.isStateTriggered() ? this.func_230479_g_() : field_243411_k;
   }

   protected ITextComponent func_230479_g_() {
      return field_243410_j;
   }

   private void func_238925_d_(MatrixStack p_238925_1_, int p_238925_2_, int p_238925_3_, int p_238925_4_, int p_238925_5_) {
      ItemStack itemstack = null;

      for(int i = 0; i < this.ghostRecipe.size(); ++i) {
         GhostRecipe.GhostIngredient ghostrecipe$ghostingredient = this.ghostRecipe.get(i);
         int j = ghostrecipe$ghostingredient.getX() + p_238925_2_;
         int k = ghostrecipe$ghostingredient.getY() + p_238925_3_;
         if (p_238925_4_ >= j && p_238925_5_ >= k && p_238925_4_ < j + 16 && p_238925_5_ < k + 16) {
            itemstack = ghostrecipe$ghostingredient.getItem();
         }
      }

      if (itemstack != null && this.mc.currentScreen != null) {
         this.mc.currentScreen.func_243308_b(p_238925_1_, this.mc.currentScreen.func_231151_a_(itemstack), p_238925_4_, p_238925_5_);
      }

   }

   public void func_230477_a_(MatrixStack p_230477_1_, int p_230477_2_, int p_230477_3_, boolean p_230477_4_, float p_230477_5_) {
      this.ghostRecipe.func_238922_a_(p_230477_1_, this.mc, p_230477_2_, p_230477_3_, p_230477_4_, p_230477_5_);
   }

   public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      if (this.isVisible() && !this.mc.player.isSpectator()) {
         if (this.recipeBookPage.func_198955_a(p_231044_1_, p_231044_3_, p_231044_5_, (this.width - 147) / 2 - this.xOffset, (this.height - 166) / 2, 147, 166)) {
            IRecipe<?> irecipe = this.recipeBookPage.getLastClickedRecipe();
            RecipeList recipelist = this.recipeBookPage.getLastClickedRecipeList();
            if (irecipe != null && recipelist != null) {
               if (!recipelist.isCraftable(irecipe) && this.ghostRecipe.getRecipe() == irecipe) {
                  return false;
               }

               this.ghostRecipe.clear();
               this.mc.playerController.sendPlaceRecipePacket(this.mc.player.openContainer.windowId, irecipe, Screen.func_231173_s_());
               if (!this.isOffsetNextToMainGUI()) {
                  this.setVisible(false);
               }
            }

            return true;
         } else if (this.searchBar.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_)) {
            return true;
         } else if (this.toggleRecipesBtn.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_)) {
            boolean flag = this.toggleCraftableFilter();
            this.toggleRecipesBtn.setStateTriggered(flag);
            this.sendUpdateSettings();
            this.updateCollections(false);
            return true;
         } else {
            for(RecipeTabToggleWidget recipetabtogglewidget : this.recipeTabs) {
               if (recipetabtogglewidget.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_)) {
                  if (this.currentTab != recipetabtogglewidget) {
                     this.currentTab.setStateTriggered(false);
                     this.currentTab = recipetabtogglewidget;
                     this.currentTab.setStateTriggered(true);
                     this.updateCollections(true);
                  }

                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private boolean toggleCraftableFilter() {
      RecipeBookCategory recipebookcategory = this.field_201522_g.func_241850_m();
      boolean flag = !this.recipeBook.func_242145_b(recipebookcategory);
      this.recipeBook.func_242146_b(recipebookcategory, flag);
      return flag;
   }

   public boolean func_195604_a(double mouseX, double mouseY, int guiLeft, int guiTop, int xSize, int ySize, int mouseButton) {
      if (!this.isVisible()) {
         return true;
      } else {
         boolean flag = mouseX < (double)guiLeft || mouseY < (double)guiTop || mouseX >= (double)(guiLeft + xSize) || mouseY >= (double)(guiTop + ySize);
         boolean flag1 = (double)(guiLeft - 147) < mouseX && mouseX < (double)guiLeft && (double)guiTop < mouseY && mouseY < (double)(guiTop + ySize);
         return flag && !flag1 && !this.currentTab.func_230449_g_();
      }
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      this.field_199738_u = false;
      if (this.isVisible() && !this.mc.player.isSpectator()) {
         if (p_231046_1_ == 256 && !this.isOffsetNextToMainGUI()) {
            this.setVisible(false);
            return true;
         } else if (this.searchBar.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_)) {
            this.updateSearch();
            return true;
         } else if (this.searchBar.func_230999_j_() && this.searchBar.getVisible() && p_231046_1_ != 256) {
            return true;
         } else if (this.mc.gameSettings.keyBindChat.matchesKey(p_231046_1_, p_231046_2_) && !this.searchBar.func_230999_j_()) {
            this.field_199738_u = true;
            this.searchBar.setFocused2(true);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
      this.field_199738_u = false;
      return IGuiEventListener.super.keyReleased(keyCode, scanCode, modifiers);
   }

   public boolean func_231042_a_(char p_231042_1_, int p_231042_2_) {
      if (this.field_199738_u) {
         return false;
      } else if (this.isVisible() && !this.mc.player.isSpectator()) {
         if (this.searchBar.func_231042_a_(p_231042_1_, p_231042_2_)) {
            this.updateSearch();
            return true;
         } else {
            return IGuiEventListener.super.func_231042_a_(p_231042_1_, p_231042_2_);
         }
      } else {
         return false;
      }
   }

   public boolean func_231047_b_(double p_231047_1_, double p_231047_3_) {
      return false;
   }

   private void updateSearch() {
      String s = this.searchBar.getText().toLowerCase(Locale.ROOT);
      this.pirateRecipe(s);
      if (!s.equals(this.lastSearch)) {
         this.updateCollections(false);
         this.lastSearch = s;
      }

   }

   /**
    * "Check if we should activate the pirate speak easter egg"
    */
   private void pirateRecipe(String text) {
      if ("excitedze".equals(text)) {
         LanguageManager languagemanager = this.mc.getLanguageManager();
         Language language = languagemanager.getLanguage("en_pt");
         if (languagemanager.getCurrentLanguage().compareTo(language) == 0) {
            return;
         }

         languagemanager.setCurrentLanguage(language);
         this.mc.gameSettings.language = language.getCode();
         net.minecraftforge.client.ForgeHooksClient.refreshResources(this.mc, net.minecraftforge.resource.VanillaResourceType.LANGUAGES);
         this.mc.gameSettings.saveOptions();
      }

   }

   private boolean isOffsetNextToMainGUI() {
      return this.xOffset == 86;
   }

   public void recipesUpdated() {
      this.updateTabs();
      if (this.isVisible()) {
         this.updateCollections(false);
      }

   }

   public void recipesShown(List<IRecipe<?>> recipes) {
      for(IRecipe<?> irecipe : recipes) {
         this.mc.player.removeRecipeHighlight(irecipe);
      }

   }

   public void setupGhostRecipe(IRecipe<?> p_193951_1_, List<Slot> p_193951_2_) {
      ItemStack itemstack = p_193951_1_.getRecipeOutput();
      this.ghostRecipe.setRecipe(p_193951_1_);
      this.ghostRecipe.addIngredient(Ingredient.fromStacks(itemstack), (p_193951_2_.get(0)).xPos, (p_193951_2_.get(0)).yPos);
      this.placeRecipe(this.field_201522_g.getWidth(), this.field_201522_g.getHeight(), this.field_201522_g.getOutputSlot(), p_193951_1_, p_193951_1_.getIngredients().iterator(), 0);
   }

   public void setSlotContents(Iterator<Ingredient> ingredients, int slotIn, int maxAmount, int y, int x) {
      Ingredient ingredient = ingredients.next();
      if (!ingredient.hasNoMatchingItems()) {
         Slot slot = this.field_201522_g.inventorySlots.get(slotIn);
         this.ghostRecipe.addIngredient(ingredient, slot.xPos, slot.yPos);
      }

   }

   protected void sendUpdateSettings() {
      if (this.mc.getConnection() != null) {
         RecipeBookCategory recipebookcategory = this.field_201522_g.func_241850_m();
         boolean flag = this.recipeBook.func_242139_a().func_242151_a(recipebookcategory);
         boolean flag1 = this.recipeBook.func_242139_a().func_242158_b(recipebookcategory);
         this.mc.getConnection().sendPacket(new CUpdateRecipeBookStatusPacket(recipebookcategory, flag, flag1));
      }

   }
}