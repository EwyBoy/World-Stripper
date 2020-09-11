package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.StonecutterContainer;
import net.minecraft.item.crafting.StonecuttingRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StonecutterScreen extends ContainerScreen<StonecutterContainer> {
   private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("textures/gui/container/stonecutter.png");
   private float sliderProgress;
   /** Is {@code true} if the player clicked on the scroll wheel in the GUI. */
   private boolean clickedOnSroll;
   /**
    * The index of the first recipe to display.
    * The number of recipes displayed at any time is 12 (4 recipes per row, and 3 rows). If the player scrolled down one
    * row, this value would be 4 (representing the index of the first slot on the second row).
    */
   private int recipeIndexOffset;
   private boolean hasItemsInInputSlot;

   public StonecutterScreen(StonecutterContainer containerIn, PlayerInventory playerInv, ITextComponent titleIn) {
      super(containerIn, playerInv, titleIn);
      containerIn.setInventoryUpdateListener(this::onInventoryUpdate);
      --this.field_238743_q_;
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      this.func_230459_a_(p_230430_1_, p_230430_2_, p_230430_3_);
   }

   protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
      this.func_230446_a_(p_230450_1_);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_230706_i_.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
      int i = this.guiLeft;
      int j = this.guiTop;
      this.func_238474_b_(p_230450_1_, i, j, 0, 0, this.xSize, this.ySize);
      int k = (int)(41.0F * this.sliderProgress);
      this.func_238474_b_(p_230450_1_, i + 119, j + 15 + k, 176 + (this.canScroll() ? 0 : 12), 0, 12, 15);
      int l = this.guiLeft + 52;
      int i1 = this.guiTop + 14;
      int j1 = this.recipeIndexOffset + 12;
      this.func_238853_b_(p_230450_1_, p_230450_3_, p_230450_4_, l, i1, j1);
      this.drawRecipesItems(l, i1, j1);
   }

   protected void func_230459_a_(MatrixStack p_230459_1_, int p_230459_2_, int p_230459_3_) {
      super.func_230459_a_(p_230459_1_, p_230459_2_, p_230459_3_);
      if (this.hasItemsInInputSlot) {
         int i = this.guiLeft + 52;
         int j = this.guiTop + 14;
         int k = this.recipeIndexOffset + 12;
         List<StonecuttingRecipe> list = this.container.getRecipeList();

         for(int l = this.recipeIndexOffset; l < k && l < this.container.getRecipeListSize(); ++l) {
            int i1 = l - this.recipeIndexOffset;
            int j1 = i + i1 % 4 * 16;
            int k1 = j + i1 / 4 * 18 + 2;
            if (p_230459_2_ >= j1 && p_230459_2_ < j1 + 16 && p_230459_3_ >= k1 && p_230459_3_ < k1 + 18) {
               this.func_230457_a_(p_230459_1_, list.get(l).getRecipeOutput(), p_230459_2_, p_230459_3_);
            }
         }
      }

   }

   private void func_238853_b_(MatrixStack p_238853_1_, int p_238853_2_, int p_238853_3_, int p_238853_4_, int p_238853_5_, int p_238853_6_) {
      for(int i = this.recipeIndexOffset; i < p_238853_6_ && i < this.container.getRecipeListSize(); ++i) {
         int j = i - this.recipeIndexOffset;
         int k = p_238853_4_ + j % 4 * 16;
         int l = j / 4;
         int i1 = p_238853_5_ + l * 18 + 2;
         int j1 = this.ySize;
         if (i == this.container.getSelectedRecipe()) {
            j1 += 18;
         } else if (p_238853_2_ >= k && p_238853_3_ >= i1 && p_238853_2_ < k + 16 && p_238853_3_ < i1 + 18) {
            j1 += 36;
         }

         this.func_238474_b_(p_238853_1_, k, i1 - 1, 0, j1, 16, 18);
      }

   }

   private void drawRecipesItems(int left, int top, int recipeIndexOffsetMax) {
      List<StonecuttingRecipe> list = this.container.getRecipeList();

      for(int i = this.recipeIndexOffset; i < recipeIndexOffsetMax && i < this.container.getRecipeListSize(); ++i) {
         int j = i - this.recipeIndexOffset;
         int k = left + j % 4 * 16;
         int l = j / 4;
         int i1 = top + l * 18 + 2;
         this.field_230706_i_.getItemRenderer().renderItemAndEffectIntoGUI(list.get(i).getRecipeOutput(), k, i1);
      }

   }

   public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      this.clickedOnSroll = false;
      if (this.hasItemsInInputSlot) {
         int i = this.guiLeft + 52;
         int j = this.guiTop + 14;
         int k = this.recipeIndexOffset + 12;

         for(int l = this.recipeIndexOffset; l < k; ++l) {
            int i1 = l - this.recipeIndexOffset;
            double d0 = p_231044_1_ - (double)(i + i1 % 4 * 16);
            double d1 = p_231044_3_ - (double)(j + i1 / 4 * 18);
            if (d0 >= 0.0D && d1 >= 0.0D && d0 < 16.0D && d1 < 18.0D && this.container.enchantItem(this.field_230706_i_.player, l)) {
               Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
               this.field_230706_i_.playerController.sendEnchantPacket((this.container).windowId, l);
               return true;
            }
         }

         i = this.guiLeft + 119;
         j = this.guiTop + 9;
         if (p_231044_1_ >= (double)i && p_231044_1_ < (double)(i + 12) && p_231044_3_ >= (double)j && p_231044_3_ < (double)(j + 54)) {
            this.clickedOnSroll = true;
         }
      }

      return super.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_);
   }

   public boolean func_231045_a_(double p_231045_1_, double p_231045_3_, int p_231045_5_, double p_231045_6_, double p_231045_8_) {
      if (this.clickedOnSroll && this.canScroll()) {
         int i = this.guiTop + 14;
         int j = i + 54;
         this.sliderProgress = ((float)p_231045_3_ - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
         this.sliderProgress = MathHelper.clamp(this.sliderProgress, 0.0F, 1.0F);
         this.recipeIndexOffset = (int)((double)(this.sliderProgress * (float)this.getHiddenRows()) + 0.5D) * 4;
         return true;
      } else {
         return super.func_231045_a_(p_231045_1_, p_231045_3_, p_231045_5_, p_231045_6_, p_231045_8_);
      }
   }

   public boolean func_231043_a_(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
      if (this.canScroll()) {
         int i = this.getHiddenRows();
         this.sliderProgress = (float)((double)this.sliderProgress - p_231043_5_ / (double)i);
         this.sliderProgress = MathHelper.clamp(this.sliderProgress, 0.0F, 1.0F);
         this.recipeIndexOffset = (int)((double)(this.sliderProgress * (float)i) + 0.5D) * 4;
      }

      return true;
   }

   private boolean canScroll() {
      return this.hasItemsInInputSlot && this.container.getRecipeListSize() > 12;
   }

   protected int getHiddenRows() {
      return (this.container.getRecipeListSize() + 4 - 1) / 4 - 3;
   }

   /**
    * Called every time this screen's container is changed (is marked as dirty).
    */
   private void onInventoryUpdate() {
      this.hasItemsInInputSlot = this.container.hasItemsinInputSlot();
      if (!this.hasItemsInInputSlot) {
         this.sliderProgress = 0.0F;
         this.recipeIndexOffset = 0;
      }

   }
}