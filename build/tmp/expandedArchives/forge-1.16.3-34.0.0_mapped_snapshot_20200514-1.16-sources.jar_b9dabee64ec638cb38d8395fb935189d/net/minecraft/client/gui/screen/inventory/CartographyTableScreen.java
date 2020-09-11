package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.CartographyContainer;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CartographyTableScreen extends ContainerScreen<CartographyContainer> {
   private static final ResourceLocation CONTAINER_TEXTURE = new ResourceLocation("textures/gui/container/cartography_table.png");

   public CartographyTableScreen(CartographyContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
      super(screenContainer, inv, titleIn);
      this.field_238743_q_ -= 2;
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      this.func_230459_a_(p_230430_1_, p_230430_2_, p_230430_3_);
   }

   protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
      this.func_230446_a_(p_230450_1_);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_230706_i_.getTextureManager().bindTexture(CONTAINER_TEXTURE);
      int i = this.guiLeft;
      int j = this.guiTop;
      this.func_238474_b_(p_230450_1_, i, j, 0, 0, this.xSize, this.ySize);
      Item item = this.container.getSlot(1).getStack().getItem();
      boolean flag = item == Items.MAP;
      boolean flag1 = item == Items.PAPER;
      boolean flag2 = item == Items.GLASS_PANE;
      ItemStack itemstack = this.container.getSlot(0).getStack();
      boolean flag3 = false;
      MapData mapdata;
      if (itemstack.getItem() == Items.FILLED_MAP) {
         mapdata = FilledMapItem.getData(itemstack, this.field_230706_i_.world);
         if (mapdata != null) {
            if (mapdata.locked) {
               flag3 = true;
               if (flag1 || flag2) {
                  this.func_238474_b_(p_230450_1_, i + 35, j + 31, this.xSize + 50, 132, 28, 21);
               }
            }

            if (flag1 && mapdata.scale >= 4) {
               flag3 = true;
               this.func_238474_b_(p_230450_1_, i + 35, j + 31, this.xSize + 50, 132, 28, 21);
            }
         }
      } else {
         mapdata = null;
      }

      this.func_238807_a_(p_230450_1_, mapdata, flag, flag1, flag2, flag3);
   }

   private void func_238807_a_(MatrixStack p_238807_1_, @Nullable MapData p_238807_2_, boolean p_238807_3_, boolean p_238807_4_, boolean p_238807_5_, boolean p_238807_6_) {
      int i = this.guiLeft;
      int j = this.guiTop;
      if (p_238807_4_ && !p_238807_6_) {
         this.func_238474_b_(p_238807_1_, i + 67, j + 13, this.xSize, 66, 66, 66);
         this.drawMapItem(p_238807_2_, i + 85, j + 31, 0.226F);
      } else if (p_238807_3_) {
         this.func_238474_b_(p_238807_1_, i + 67 + 16, j + 13, this.xSize, 132, 50, 66);
         this.drawMapItem(p_238807_2_, i + 86, j + 16, 0.34F);
         this.field_230706_i_.getTextureManager().bindTexture(CONTAINER_TEXTURE);
         RenderSystem.pushMatrix();
         RenderSystem.translatef(0.0F, 0.0F, 1.0F);
         this.func_238474_b_(p_238807_1_, i + 67, j + 13 + 16, this.xSize, 132, 50, 66);
         this.drawMapItem(p_238807_2_, i + 70, j + 32, 0.34F);
         RenderSystem.popMatrix();
      } else if (p_238807_5_) {
         this.func_238474_b_(p_238807_1_, i + 67, j + 13, this.xSize, 0, 66, 66);
         this.drawMapItem(p_238807_2_, i + 71, j + 17, 0.45F);
         this.field_230706_i_.getTextureManager().bindTexture(CONTAINER_TEXTURE);
         RenderSystem.pushMatrix();
         RenderSystem.translatef(0.0F, 0.0F, 1.0F);
         this.func_238474_b_(p_238807_1_, i + 66, j + 12, 0, this.ySize, 66, 66);
         RenderSystem.popMatrix();
      } else {
         this.func_238474_b_(p_238807_1_, i + 67, j + 13, this.xSize, 0, 66, 66);
         this.drawMapItem(p_238807_2_, i + 71, j + 17, 0.45F);
      }

   }

   private void drawMapItem(@Nullable MapData mapDataIn, int x, int y, float scale) {
      if (mapDataIn != null) {
         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)x, (float)y, 1.0F);
         RenderSystem.scalef(scale, scale, 1.0F);
         IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
         this.field_230706_i_.gameRenderer.getMapItemRenderer().renderMap(new MatrixStack(), irendertypebuffer$impl, mapDataIn, true, 15728880);
         irendertypebuffer$impl.finish();
         RenderSystem.popMatrix();
      }

   }
}