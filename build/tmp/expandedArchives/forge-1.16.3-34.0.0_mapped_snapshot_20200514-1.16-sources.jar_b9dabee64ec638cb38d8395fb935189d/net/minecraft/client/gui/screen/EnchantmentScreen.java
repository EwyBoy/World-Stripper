package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.List;
import java.util.Random;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.model.BookModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.EnchantmentContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnchantmentScreen extends ContainerScreen<EnchantmentContainer> {
   /** The ResourceLocation containing the Enchantment GUI texture location */
   private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
   /** The ResourceLocation containing the texture for the Book rendered above the enchantment table */
   private static final ResourceLocation ENCHANTMENT_TABLE_BOOK_TEXTURE = new ResourceLocation("textures/entity/enchanting_table_book.png");
   /** The ModelBook instance used for rendering the book on the Enchantment table */
   private static final BookModel MODEL_BOOK = new BookModel();
   /** A Random instance for use with the enchantment gui */
   private final Random random = new Random();
   public int ticks;
   public float flip;
   public float oFlip;
   public float flipT;
   public float flipA;
   public float open;
   public float oOpen;
   private ItemStack last = ItemStack.EMPTY;

   public EnchantmentScreen(EnchantmentContainer p_i51090_1_, PlayerInventory p_i51090_2_, ITextComponent p_i51090_3_) {
      super(p_i51090_1_, p_i51090_2_, p_i51090_3_);
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      this.tickBook();
   }

   public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      int i = (this.field_230708_k_ - this.xSize) / 2;
      int j = (this.field_230709_l_ - this.ySize) / 2;

      for(int k = 0; k < 3; ++k) {
         double d0 = p_231044_1_ - (double)(i + 60);
         double d1 = p_231044_3_ - (double)(j + 14 + 19 * k);
         if (d0 >= 0.0D && d1 >= 0.0D && d0 < 108.0D && d1 < 19.0D && this.container.enchantItem(this.field_230706_i_.player, k)) {
            this.field_230706_i_.playerController.sendEnchantPacket((this.container).windowId, k);
            return true;
         }
      }

      return super.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_);
   }

   protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
      RenderHelper.setupGuiFlatDiffuseLighting();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_230706_i_.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
      int i = (this.field_230708_k_ - this.xSize) / 2;
      int j = (this.field_230709_l_ - this.ySize) / 2;
      this.func_238474_b_(p_230450_1_, i, j, 0, 0, this.xSize, this.ySize);
      RenderSystem.matrixMode(5889);
      RenderSystem.pushMatrix();
      RenderSystem.loadIdentity();
      int k = (int)this.field_230706_i_.getMainWindow().getGuiScaleFactor();
      RenderSystem.viewport((this.field_230708_k_ - 320) / 2 * k, (this.field_230709_l_ - 240) / 2 * k, 320 * k, 240 * k);
      RenderSystem.translatef(-0.34F, 0.23F, 0.0F);
      RenderSystem.multMatrix(Matrix4f.perspective(90.0D, 1.3333334F, 9.0F, 80.0F));
      RenderSystem.matrixMode(5888);
      p_230450_1_.push();
      MatrixStack.Entry matrixstack$entry = p_230450_1_.getLast();
      matrixstack$entry.getMatrix().setIdentity();
      matrixstack$entry.getNormal().setIdentity();
      p_230450_1_.translate(0.0D, (double)3.3F, 1984.0D);
      float f = 5.0F;
      p_230450_1_.scale(5.0F, 5.0F, 5.0F);
      p_230450_1_.rotate(Vector3f.ZP.rotationDegrees(180.0F));
      p_230450_1_.rotate(Vector3f.XP.rotationDegrees(20.0F));
      float f1 = MathHelper.lerp(p_230450_2_, this.oOpen, this.open);
      p_230450_1_.translate((double)((1.0F - f1) * 0.2F), (double)((1.0F - f1) * 0.1F), (double)((1.0F - f1) * 0.25F));
      float f2 = -(1.0F - f1) * 90.0F - 90.0F;
      p_230450_1_.rotate(Vector3f.YP.rotationDegrees(f2));
      p_230450_1_.rotate(Vector3f.XP.rotationDegrees(180.0F));
      float f3 = MathHelper.lerp(p_230450_2_, this.oFlip, this.flip) + 0.25F;
      float f4 = MathHelper.lerp(p_230450_2_, this.oFlip, this.flip) + 0.75F;
      f3 = (f3 - (float)MathHelper.fastFloor((double)f3)) * 1.6F - 0.3F;
      f4 = (f4 - (float)MathHelper.fastFloor((double)f4)) * 1.6F - 0.3F;
      if (f3 < 0.0F) {
         f3 = 0.0F;
      }

      if (f4 < 0.0F) {
         f4 = 0.0F;
      }

      if (f3 > 1.0F) {
         f3 = 1.0F;
      }

      if (f4 > 1.0F) {
         f4 = 1.0F;
      }

      RenderSystem.enableRescaleNormal();
      MODEL_BOOK.func_228247_a_(0.0F, f3, f4, f1);
      IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
      IVertexBuilder ivertexbuilder = irendertypebuffer$impl.getBuffer(MODEL_BOOK.getRenderType(ENCHANTMENT_TABLE_BOOK_TEXTURE));
      MODEL_BOOK.render(p_230450_1_, ivertexbuilder, 15728880, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
      irendertypebuffer$impl.finish();
      p_230450_1_.pop();
      RenderSystem.matrixMode(5889);
      RenderSystem.viewport(0, 0, this.field_230706_i_.getMainWindow().getFramebufferWidth(), this.field_230706_i_.getMainWindow().getFramebufferHeight());
      RenderSystem.popMatrix();
      RenderSystem.matrixMode(5888);
      RenderHelper.setupGui3DDiffuseLighting();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      EnchantmentNameParts.getInstance().reseedRandomGenerator((long)this.container.func_217005_f());
      int l = this.container.getLapisAmount();

      for(int i1 = 0; i1 < 3; ++i1) {
         int j1 = i + 60;
         int k1 = j1 + 20;
         this.func_230926_e_(0);
         this.field_230706_i_.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
         int l1 = (this.container).enchantLevels[i1];
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         if (l1 == 0) {
            this.func_238474_b_(p_230450_1_, j1, j + 14 + 19 * i1, 0, 185, 108, 19);
         } else {
            String s = "" + l1;
            int i2 = 86 - this.field_230712_o_.getStringWidth(s);
            ITextProperties itextproperties = EnchantmentNameParts.getInstance().func_238816_a_(this.field_230712_o_, i2);
            int j2 = 6839882;
            if (((l < i1 + 1 || this.field_230706_i_.player.experienceLevel < l1) && !this.field_230706_i_.player.abilities.isCreativeMode) || this.container.enchantClue[i1] == -1) { // Forge: render buttons as disabled when enchantable but enchantability not met on lower levels
               this.func_238474_b_(p_230450_1_, j1, j + 14 + 19 * i1, 0, 185, 108, 19);
               this.func_238474_b_(p_230450_1_, j1 + 1, j + 15 + 19 * i1, 16 * i1, 239, 16, 16);
               this.field_230712_o_.func_238418_a_(itextproperties, k1, j + 16 + 19 * i1, i2, (j2 & 16711422) >> 1);
               j2 = 4226832;
            } else {
               int k2 = p_230450_3_ - (i + 60);
               int l2 = p_230450_4_ - (j + 14 + 19 * i1);
               if (k2 >= 0 && l2 >= 0 && k2 < 108 && l2 < 19) {
                  this.func_238474_b_(p_230450_1_, j1, j + 14 + 19 * i1, 0, 204, 108, 19);
                  j2 = 16777088;
               } else {
                  this.func_238474_b_(p_230450_1_, j1, j + 14 + 19 * i1, 0, 166, 108, 19);
               }

               this.func_238474_b_(p_230450_1_, j1 + 1, j + 15 + 19 * i1, 16 * i1, 223, 16, 16);
               this.field_230712_o_.func_238418_a_(itextproperties, k1, j + 16 + 19 * i1, i2, j2);
               j2 = 8453920;
            }

            this.field_230712_o_.func_238405_a_(p_230450_1_, s, (float)(k1 + 86 - this.field_230712_o_.getStringWidth(s)), (float)(j + 16 + 19 * i1 + 7), j2);
         }
      }

   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      p_230430_4_ = this.field_230706_i_.getRenderPartialTicks();
      this.func_230446_a_(p_230430_1_);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      this.func_230459_a_(p_230430_1_, p_230430_2_, p_230430_3_);
      boolean flag = this.field_230706_i_.player.abilities.isCreativeMode;
      int i = this.container.getLapisAmount();

      for(int j = 0; j < 3; ++j) {
         int k = (this.container).enchantLevels[j];
         Enchantment enchantment = Enchantment.getEnchantmentByID((this.container).enchantClue[j]);
         int l = (this.container).worldClue[j];
         int i1 = j + 1;
         if (this.isPointInRegion(60, 14 + 19 * j, 108, 17, (double)p_230430_2_, (double)p_230430_3_) && k > 0) {
            List<ITextComponent> list = Lists.newArrayList();
            list.add((new TranslationTextComponent("container.enchant.clue", enchantment == null ? "" : enchantment.getDisplayName(l))).func_240699_a_(TextFormatting.WHITE));
            if(enchantment == null) {
               list.add(new StringTextComponent(""));
               list.add(new TranslationTextComponent("forge.container.enchant.limitedEnchantability").func_240699_a_(TextFormatting.RED));
            } else if (!flag) {
               list.add(StringTextComponent.field_240750_d_);
               if (this.field_230706_i_.player.experienceLevel < k) {
                  list.add((new TranslationTextComponent("container.enchant.level.requirement", (this.container).enchantLevels[j])).func_240699_a_(TextFormatting.RED));
               } else {
                  IFormattableTextComponent iformattabletextcomponent;
                  if (i1 == 1) {
                     iformattabletextcomponent = new TranslationTextComponent("container.enchant.lapis.one");
                  } else {
                     iformattabletextcomponent = new TranslationTextComponent("container.enchant.lapis.many", i1);
                  }

                  list.add(iformattabletextcomponent.func_240699_a_(i >= i1 ? TextFormatting.GRAY : TextFormatting.RED));
                  IFormattableTextComponent iformattabletextcomponent1;
                  if (i1 == 1) {
                     iformattabletextcomponent1 = new TranslationTextComponent("container.enchant.level.one");
                  } else {
                     iformattabletextcomponent1 = new TranslationTextComponent("container.enchant.level.many", i1);
                  }

                  list.add(iformattabletextcomponent1.func_240699_a_(TextFormatting.GRAY));
               }
            }

            this.func_243308_b(p_230430_1_, list, p_230430_2_, p_230430_3_);
            break;
         }
      }

   }

   public void tickBook() {
      ItemStack itemstack = this.container.getSlot(0).getStack();
      if (!ItemStack.areItemStacksEqual(itemstack, this.last)) {
         this.last = itemstack;

         do {
            this.flipT += (float)(this.random.nextInt(4) - this.random.nextInt(4));
         } while(this.flip <= this.flipT + 1.0F && this.flip >= this.flipT - 1.0F);
      }

      ++this.ticks;
      this.oFlip = this.flip;
      this.oOpen = this.open;
      boolean flag = false;

      for(int i = 0; i < 3; ++i) {
         if ((this.container).enchantLevels[i] != 0) {
            flag = true;
         }
      }

      if (flag) {
         this.open += 0.2F;
      } else {
         this.open -= 0.2F;
      }

      this.open = MathHelper.clamp(this.open, 0.0F, 1.0F);
      float f1 = (this.flipT - this.flip) * 0.4F;
      float f = 0.2F;
      f1 = MathHelper.clamp(f1, -0.2F, 0.2F);
      this.flipA += (f1 - this.flipA) * 0.9F;
      this.flip += this.flipA;
   }
}