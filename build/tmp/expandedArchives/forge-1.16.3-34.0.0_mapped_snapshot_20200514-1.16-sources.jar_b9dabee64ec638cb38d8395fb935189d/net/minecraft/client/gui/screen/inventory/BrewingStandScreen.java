package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.BrewingStandContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BrewingStandScreen extends ContainerScreen<BrewingStandContainer> {
   private static final ResourceLocation BREWING_STAND_GUI_TEXTURES = new ResourceLocation("textures/gui/container/brewing_stand.png");
   private static final int[] BUBBLELENGTHS = new int[]{29, 24, 20, 16, 11, 6, 0};

   public BrewingStandScreen(BrewingStandContainer p_i51097_1_, PlayerInventory p_i51097_2_, ITextComponent p_i51097_3_) {
      super(p_i51097_1_, p_i51097_2_, p_i51097_3_);
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      this.field_238742_p_ = (this.xSize - this.field_230712_o_.func_238414_a_(this.field_230704_d_)) / 2;
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      this.func_230459_a_(p_230430_1_, p_230430_2_, p_230430_3_);
   }

   protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_230706_i_.getTextureManager().bindTexture(BREWING_STAND_GUI_TEXTURES);
      int i = (this.field_230708_k_ - this.xSize) / 2;
      int j = (this.field_230709_l_ - this.ySize) / 2;
      this.func_238474_b_(p_230450_1_, i, j, 0, 0, this.xSize, this.ySize);
      int k = this.container.func_216982_e();
      int l = MathHelper.clamp((18 * k + 20 - 1) / 20, 0, 18);
      if (l > 0) {
         this.func_238474_b_(p_230450_1_, i + 60, j + 44, 176, 29, l, 4);
      }

      int i1 = this.container.func_216981_f();
      if (i1 > 0) {
         int j1 = (int)(28.0F * (1.0F - (float)i1 / 400.0F));
         if (j1 > 0) {
            this.func_238474_b_(p_230450_1_, i + 97, j + 16, 176, 0, 9, j1);
         }

         j1 = BUBBLELENGTHS[i1 / 2 % 7];
         if (j1 > 0) {
            this.func_238474_b_(p_230450_1_, i + 63, j + 14 + 29 - j1, 185, 29 - j1, 12, j1);
         }
      }

   }
}