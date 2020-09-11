package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.HorseInventoryContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HorseInventoryScreen extends ContainerScreen<HorseInventoryContainer> {
   private static final ResourceLocation HORSE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/horse.png");
   /** The EntityHorse whose inventory is currently being accessed. */
   private final AbstractHorseEntity horseEntity;
   /** The mouse x-position recorded during the last rendered frame. */
   private float mousePosx;
   /** The mouse y-position recorded during the last renderered frame. */
   private float mousePosY;

   public HorseInventoryScreen(HorseInventoryContainer p_i51084_1_, PlayerInventory p_i51084_2_, AbstractHorseEntity p_i51084_3_) {
      super(p_i51084_1_, p_i51084_2_, p_i51084_3_.getDisplayName());
      this.horseEntity = p_i51084_3_;
      this.field_230711_n_ = false;
   }

   protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_230706_i_.getTextureManager().bindTexture(HORSE_GUI_TEXTURES);
      int i = (this.field_230708_k_ - this.xSize) / 2;
      int j = (this.field_230709_l_ - this.ySize) / 2;
      this.func_238474_b_(p_230450_1_, i, j, 0, 0, this.xSize, this.ySize);
      if (this.horseEntity instanceof AbstractChestedHorseEntity) {
         AbstractChestedHorseEntity abstractchestedhorseentity = (AbstractChestedHorseEntity)this.horseEntity;
         if (abstractchestedhorseentity.hasChest()) {
            this.func_238474_b_(p_230450_1_, i + 79, j + 17, 0, this.ySize, abstractchestedhorseentity.getInventoryColumns() * 18, 54);
         }
      }

      if (this.horseEntity.func_230264_L__()) {
         this.func_238474_b_(p_230450_1_, i + 7, j + 35 - 18, 18, this.ySize + 54, 18, 18);
      }

      if (this.horseEntity.func_230276_fq_()) {
         if (this.horseEntity instanceof LlamaEntity) {
            this.func_238474_b_(p_230450_1_, i + 7, j + 35, 36, this.ySize + 54, 18, 18);
         } else {
            this.func_238474_b_(p_230450_1_, i + 7, j + 35, 0, this.ySize + 54, 18, 18);
         }
      }

      InventoryScreen.drawEntityOnScreen(i + 51, j + 60, 17, (float)(i + 51) - this.mousePosx, (float)(j + 75 - 50) - this.mousePosY, this.horseEntity);
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      this.mousePosx = (float)p_230430_2_;
      this.mousePosY = (float)p_230430_3_;
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      this.func_230459_a_(p_230430_1_, p_230430_2_, p_230430_3_);
   }
}