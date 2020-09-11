package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.AbstractRepairContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AbstractRepairScreen<T extends AbstractRepairContainer> extends ContainerScreen<T> implements IContainerListener {
   private ResourceLocation field_238817_A_;

   public AbstractRepairScreen(T p_i232291_1_, PlayerInventory p_i232291_2_, ITextComponent p_i232291_3_, ResourceLocation p_i232291_4_) {
      super(p_i232291_1_, p_i232291_2_, p_i232291_3_);
      this.field_238817_A_ = p_i232291_4_;
   }

   protected void func_230453_j_() {
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      this.func_230453_j_();
      this.container.addListener(this);
   }

   public void func_231164_f_() {
      super.func_231164_f_();
      this.container.removeListener(this);
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      RenderSystem.disableBlend();
      this.func_230452_b_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      this.func_230459_a_(p_230430_1_, p_230430_2_, p_230430_3_);
   }

   protected void func_230452_b_(MatrixStack p_230452_1_, int p_230452_2_, int p_230452_3_, float p_230452_4_) {
   }

   protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_230706_i_.getTextureManager().bindTexture(this.field_238817_A_);
      int i = (this.field_230708_k_ - this.xSize) / 2;
      int j = (this.field_230709_l_ - this.ySize) / 2;
      this.func_238474_b_(p_230450_1_, i, j, 0, 0, this.xSize, this.ySize);
      this.func_238474_b_(p_230450_1_, i + 59, j + 20, 0, this.ySize + (this.container.getSlot(0).getHasStack() ? 0 : 16), 110, 16);
      if ((this.container.getSlot(0).getHasStack() || this.container.getSlot(1).getHasStack()) && !this.container.getSlot(2).getHasStack()) {
         this.func_238474_b_(p_230450_1_, i + 99, j + 45, this.xSize, 0, 28, 21);
      }

   }

   /**
    * update the crafting window inventory with the items in the list
    */
   public void sendAllContents(Container containerToSend, NonNullList<ItemStack> itemsList) {
      this.sendSlotContents(containerToSend, 0, containerToSend.getSlot(0).getStack());
   }

   /**
    * Sends two ints to the client-side Container. Used for furnace burning time, smelting progress, brewing progress,
    * and enchanting level. Normally the first int identifies which variable to update, and the second contains the new
    * value. Both are truncated to shorts in non-local SMP.
    */
   public void sendWindowProperty(Container containerIn, int varToUpdate, int newValue) {
   }

   /**
    * Sends the contents of an inventory slot to the client-side Container. This doesn't have to match the actual
    * contents of that slot.
    */
   public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
   }
}