package net.minecraft.client.gui.recipebook;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ToggleWidget;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.util.ClientRecipeBook;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RecipeTabToggleWidget extends ToggleWidget {
   private final RecipeBookCategories category;
   private float animationTime;

   public RecipeTabToggleWidget(RecipeBookCategories p_i51075_1_) {
      super(0, 0, 35, 27, false);
      this.category = p_i51075_1_;
      this.initTextureValues(153, 2, 35, 0, RecipeBookGui.RECIPE_BOOK);
   }

   public void startAnimation(Minecraft p_193918_1_) {
      ClientRecipeBook clientrecipebook = p_193918_1_.player.getRecipeBook();
      List<RecipeList> list = clientrecipebook.getRecipes(this.category);
      if (p_193918_1_.player.openContainer instanceof RecipeBookContainer) {
         for(RecipeList recipelist : list) {
            for(IRecipe<?> irecipe : recipelist.getRecipes(clientrecipebook.func_242141_a((RecipeBookContainer)p_193918_1_.player.openContainer))) {
               if (clientrecipebook.isNew(irecipe)) {
                  this.animationTime = 15.0F;
                  return;
               }
            }
         }

      }
   }

   public void func_230431_b_(MatrixStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
      if (this.animationTime > 0.0F) {
         float f = 1.0F + 0.1F * (float)Math.sin((double)(this.animationTime / 15.0F * (float)Math.PI));
         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)(this.field_230690_l_ + 8), (float)(this.field_230691_m_ + 12), 0.0F);
         RenderSystem.scalef(1.0F, f, 1.0F);
         RenderSystem.translatef((float)(-(this.field_230690_l_ + 8)), (float)(-(this.field_230691_m_ + 12)), 0.0F);
      }

      Minecraft minecraft = Minecraft.getInstance();
      minecraft.getTextureManager().bindTexture(this.resourceLocation);
      RenderSystem.disableDepthTest();
      int i = this.xTexStart;
      int j = this.yTexStart;
      if (this.stateTriggered) {
         i += this.xDiffTex;
      }

      if (this.func_230449_g_()) {
         j += this.yDiffTex;
      }

      int k = this.field_230690_l_;
      if (this.stateTriggered) {
         k -= 2;
      }

      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.func_238474_b_(p_230431_1_, k, this.field_230691_m_, i, j, this.field_230688_j_, this.field_230689_k_);
      RenderSystem.enableDepthTest();
      this.renderIcon(minecraft.getItemRenderer());
      if (this.animationTime > 0.0F) {
         RenderSystem.popMatrix();
         this.animationTime -= p_230431_4_;
      }

   }

   private void renderIcon(ItemRenderer p_193920_1_) {
      List<ItemStack> list = this.category.getIcons();
      int i = this.stateTriggered ? -2 : 0;
      if (list.size() == 1) {
         p_193920_1_.func_239390_c_(list.get(0), this.field_230690_l_ + 9 + i, this.field_230691_m_ + 5);
      } else if (list.size() == 2) {
         p_193920_1_.func_239390_c_(list.get(0), this.field_230690_l_ + 3 + i, this.field_230691_m_ + 5);
         p_193920_1_.func_239390_c_(list.get(1), this.field_230690_l_ + 14 + i, this.field_230691_m_ + 5);
      }

   }

   public RecipeBookCategories func_201503_d() {
      return this.category;
   }

   public boolean func_199500_a(ClientRecipeBook p_199500_1_) {
      List<RecipeList> list = p_199500_1_.getRecipes(this.category);
      this.field_230694_p_ = false;
      if (list != null) {
         for(RecipeList recipelist : list) {
            if (recipelist.isNotEmpty() && recipelist.containsValidRecipes()) {
               this.field_230694_p_ = true;
               break;
            }
         }
      }

      return this.field_230694_p_;
   }
}