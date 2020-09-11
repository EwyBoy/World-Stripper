package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CraftingScreen extends ContainerScreen<WorkbenchContainer> implements IRecipeShownListener {
   private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/crafting_table.png");
   private static final ResourceLocation RECIPE_BUTTON_TEXTURE = new ResourceLocation("textures/gui/recipe_button.png");
   private final RecipeBookGui recipeBookGui = new RecipeBookGui();
   private boolean widthTooNarrow;

   public CraftingScreen(WorkbenchContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
      super(screenContainer, inv, titleIn);
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      this.widthTooNarrow = this.field_230708_k_ < 379;
      this.recipeBookGui.init(this.field_230708_k_, this.field_230709_l_, this.field_230706_i_, this.widthTooNarrow, this.container);
      this.guiLeft = this.recipeBookGui.updateScreenPosition(this.widthTooNarrow, this.field_230708_k_, this.xSize);
      this.field_230705_e_.add(this.recipeBookGui);
      this.setFocusedDefault(this.recipeBookGui);
      this.func_230480_a_(new ImageButton(this.guiLeft + 5, this.field_230709_l_ / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, (p_214076_1_) -> {
         this.recipeBookGui.initSearchBar(this.widthTooNarrow);
         this.recipeBookGui.toggleVisibility();
         this.guiLeft = this.recipeBookGui.updateScreenPosition(this.widthTooNarrow, this.field_230708_k_, this.xSize);
         ((ImageButton)p_214076_1_).setPosition(this.guiLeft + 5, this.field_230709_l_ / 2 - 49);
      }));
      this.field_238742_p_ = 29;
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      this.recipeBookGui.tick();
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      if (this.recipeBookGui.isVisible() && this.widthTooNarrow) {
         this.func_230450_a_(p_230430_1_, p_230430_4_, p_230430_2_, p_230430_3_);
         this.recipeBookGui.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      } else {
         this.recipeBookGui.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
         super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
         this.recipeBookGui.func_230477_a_(p_230430_1_, this.guiLeft, this.guiTop, true, p_230430_4_);
      }

      this.func_230459_a_(p_230430_1_, p_230430_2_, p_230430_3_);
      this.recipeBookGui.func_238924_c_(p_230430_1_, this.guiLeft, this.guiTop, p_230430_2_, p_230430_3_);
   }

   protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_230706_i_.getTextureManager().bindTexture(CRAFTING_TABLE_GUI_TEXTURES);
      int i = this.guiLeft;
      int j = (this.field_230709_l_ - this.ySize) / 2;
      this.func_238474_b_(p_230450_1_, i, j, 0, 0, this.xSize, this.ySize);
   }

   protected boolean isPointInRegion(int x, int y, int width, int height, double mouseX, double mouseY) {
      return (!this.widthTooNarrow || !this.recipeBookGui.isVisible()) && super.isPointInRegion(x, y, width, height, mouseX, mouseY);
   }

   public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      if (this.recipeBookGui.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_)) {
         this.func_231035_a_(this.recipeBookGui);
         return true;
      } else {
         return this.widthTooNarrow && this.recipeBookGui.isVisible() ? true : super.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_);
      }
   }

   protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) {
      boolean flag = mouseX < (double)guiLeftIn || mouseY < (double)guiTopIn || mouseX >= (double)(guiLeftIn + this.xSize) || mouseY >= (double)(guiTopIn + this.ySize);
      return this.recipeBookGui.func_195604_a(mouseX, mouseY, this.guiLeft, this.guiTop, this.xSize, this.ySize, mouseButton) && flag;
   }

   /**
    * Called when the mouse is clicked over a slot or outside the gui.
    */
   protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
      super.handleMouseClick(slotIn, slotId, mouseButton, type);
      this.recipeBookGui.slotClicked(slotIn);
   }

   public void recipesUpdated() {
      this.recipeBookGui.recipesUpdated();
   }

   public void func_231164_f_() {
      this.recipeBookGui.removed();
      super.func_231164_f_();
   }

   public RecipeBookGui getRecipeGui() {
      return this.recipeBookGui;
   }
}