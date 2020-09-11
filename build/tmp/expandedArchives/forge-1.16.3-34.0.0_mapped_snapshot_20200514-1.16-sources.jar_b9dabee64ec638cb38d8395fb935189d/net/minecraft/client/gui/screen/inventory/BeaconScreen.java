package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.BeaconContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CCloseWindowPacket;
import net.minecraft.network.play.client.CUpdateBeaconPacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeaconScreen extends ContainerScreen<BeaconContainer> {
   private static final ResourceLocation BEACON_GUI_TEXTURES = new ResourceLocation("textures/gui/container/beacon.png");
   private static final ITextComponent field_243334_B = new TranslationTextComponent("block.minecraft.beacon.primary");
   private static final ITextComponent field_243335_C = new TranslationTextComponent("block.minecraft.beacon.secondary");
   private BeaconScreen.ConfirmButton beaconConfirmButton;
   private boolean buttonsNotDrawn;
   private Effect field_214105_n;
   private Effect field_214106_o;

   public BeaconScreen(final BeaconContainer p_i51102_1_, PlayerInventory p_i51102_2_, ITextComponent p_i51102_3_) {
      super(p_i51102_1_, p_i51102_2_, p_i51102_3_);
      this.xSize = 230;
      this.ySize = 219;
      p_i51102_1_.addListener(new IContainerListener() {
         /**
          * update the crafting window inventory with the items in the list
          */
         public void sendAllContents(Container containerToSend, NonNullList<ItemStack> itemsList) {
         }

         /**
          * Sends the contents of an inventory slot to the client-side Container. This doesn't have to match the actual
          * contents of that slot.
          */
         public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
         }

         /**
          * Sends two ints to the client-side Container. Used for furnace burning time, smelting progress, brewing
          * progress, and enchanting level. Normally the first int identifies which variable to update, and the second
          * contains the new value. Both are truncated to shorts in non-local SMP.
          */
         public void sendWindowProperty(Container containerIn, int varToUpdate, int newValue) {
            BeaconScreen.this.field_214105_n = p_i51102_1_.func_216967_f();
            BeaconScreen.this.field_214106_o = p_i51102_1_.func_216968_g();
            BeaconScreen.this.buttonsNotDrawn = true;
         }
      });
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      this.beaconConfirmButton = this.func_230480_a_(new BeaconScreen.ConfirmButton(this.guiLeft + 164, this.guiTop + 107));
      this.func_230480_a_(new BeaconScreen.CancelButton(this.guiLeft + 190, this.guiTop + 107));
      this.buttonsNotDrawn = true;
      this.beaconConfirmButton.field_230693_o_ = false;
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      int i = this.container.func_216969_e();
      if (this.buttonsNotDrawn && i >= 0) {
         this.buttonsNotDrawn = false;

         for(int j = 0; j <= 2; ++j) {
            int k = BeaconTileEntity.EFFECTS_LIST[j].length;
            int l = k * 22 + (k - 1) * 2;

            for(int i1 = 0; i1 < k; ++i1) {
               Effect effect = BeaconTileEntity.EFFECTS_LIST[j][i1];
               BeaconScreen.PowerButton beaconscreen$powerbutton = new BeaconScreen.PowerButton(this.guiLeft + 76 + i1 * 24 - l / 2, this.guiTop + 22 + j * 25, effect, true);
               this.func_230480_a_(beaconscreen$powerbutton);
               if (j >= i) {
                  beaconscreen$powerbutton.field_230693_o_ = false;
               } else if (effect == this.field_214105_n) {
                  beaconscreen$powerbutton.setSelected(true);
               }
            }
         }

         int j1 = 3;
         int k1 = BeaconTileEntity.EFFECTS_LIST[3].length + 1;
         int l1 = k1 * 22 + (k1 - 1) * 2;

         for(int i2 = 0; i2 < k1 - 1; ++i2) {
            Effect effect1 = BeaconTileEntity.EFFECTS_LIST[3][i2];
            BeaconScreen.PowerButton beaconscreen$powerbutton2 = new BeaconScreen.PowerButton(this.guiLeft + 167 + i2 * 24 - l1 / 2, this.guiTop + 47, effect1, false);
            this.func_230480_a_(beaconscreen$powerbutton2);
            if (3 >= i) {
               beaconscreen$powerbutton2.field_230693_o_ = false;
            } else if (effect1 == this.field_214106_o) {
               beaconscreen$powerbutton2.setSelected(true);
            }
         }

         if (this.field_214105_n != null) {
            BeaconScreen.PowerButton beaconscreen$powerbutton1 = new BeaconScreen.PowerButton(this.guiLeft + 167 + (k1 - 1) * 24 - l1 / 2, this.guiTop + 47, this.field_214105_n, false);
            this.func_230480_a_(beaconscreen$powerbutton1);
            if (3 >= i) {
               beaconscreen$powerbutton1.field_230693_o_ = false;
            } else if (this.field_214105_n == this.field_214106_o) {
               beaconscreen$powerbutton1.setSelected(true);
            }
         }
      }

      this.beaconConfirmButton.field_230693_o_ = this.container.func_216970_h() && this.field_214105_n != null;
   }

   protected void func_230451_b_(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
      func_238472_a_(p_230451_1_, this.field_230712_o_, field_243334_B, 62, 10, 14737632);
      func_238472_a_(p_230451_1_, this.field_230712_o_, field_243335_C, 169, 10, 14737632);

      for(Widget widget : this.field_230710_m_) {
         if (widget.func_230449_g_()) {
            widget.func_230443_a_(p_230451_1_, p_230451_2_ - this.guiLeft, p_230451_3_ - this.guiTop);
            break;
         }
      }

   }

   protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_230706_i_.getTextureManager().bindTexture(BEACON_GUI_TEXTURES);
      int i = (this.field_230708_k_ - this.xSize) / 2;
      int j = (this.field_230709_l_ - this.ySize) / 2;
      this.func_238474_b_(p_230450_1_, i, j, 0, 0, this.xSize, this.ySize);
      this.field_230707_j_.zLevel = 100.0F;
      this.field_230707_j_.renderItemAndEffectIntoGUI(new ItemStack(Items.field_234759_km_), i + 20, j + 109);
      this.field_230707_j_.renderItemAndEffectIntoGUI(new ItemStack(Items.EMERALD), i + 41, j + 109);
      this.field_230707_j_.renderItemAndEffectIntoGUI(new ItemStack(Items.DIAMOND), i + 41 + 22, j + 109);
      this.field_230707_j_.renderItemAndEffectIntoGUI(new ItemStack(Items.GOLD_INGOT), i + 42 + 44, j + 109);
      this.field_230707_j_.renderItemAndEffectIntoGUI(new ItemStack(Items.IRON_INGOT), i + 42 + 66, j + 109);
      this.field_230707_j_.zLevel = 0.0F;
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      this.func_230459_a_(p_230430_1_, p_230430_2_, p_230430_3_);
   }

   @OnlyIn(Dist.CLIENT)
   abstract static class Button extends AbstractButton {
      private boolean selected;

      protected Button(int p_i50826_1_, int p_i50826_2_) {
         super(p_i50826_1_, p_i50826_2_, 22, 22, StringTextComponent.field_240750_d_);
      }

      public void func_230431_b_(MatrixStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
         Minecraft.getInstance().getTextureManager().bindTexture(BeaconScreen.BEACON_GUI_TEXTURES);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         int i = 219;
         int j = 0;
         if (!this.field_230693_o_) {
            j += this.field_230688_j_ * 2;
         } else if (this.selected) {
            j += this.field_230688_j_ * 1;
         } else if (this.func_230449_g_()) {
            j += this.field_230688_j_ * 3;
         }

         this.func_238474_b_(p_230431_1_, this.field_230690_l_, this.field_230691_m_, j, 219, this.field_230688_j_, this.field_230689_k_);
         this.func_230454_a_(p_230431_1_);
      }

      protected abstract void func_230454_a_(MatrixStack p_230454_1_);

      public boolean isSelected() {
         return this.selected;
      }

      public void setSelected(boolean selectedIn) {
         this.selected = selectedIn;
      }
   }

   @OnlyIn(Dist.CLIENT)
   class CancelButton extends BeaconScreen.SpriteButton {
      public CancelButton(int p_i50829_2_, int p_i50829_3_) {
         super(p_i50829_2_, p_i50829_3_, 112, 220);
      }

      public void func_230930_b_() {
         BeaconScreen.this.field_230706_i_.player.connection.sendPacket(new CCloseWindowPacket(BeaconScreen.this.field_230706_i_.player.openContainer.windowId));
         BeaconScreen.this.field_230706_i_.displayGuiScreen((Screen)null);
      }

      public void func_230443_a_(MatrixStack p_230443_1_, int p_230443_2_, int p_230443_3_) {
         BeaconScreen.this.func_238652_a_(p_230443_1_, DialogTexts.field_240633_d_, p_230443_2_, p_230443_3_);
      }
   }

   @OnlyIn(Dist.CLIENT)
   class ConfirmButton extends BeaconScreen.SpriteButton {
      public ConfirmButton(int p_i50828_2_, int p_i50828_3_) {
         super(p_i50828_2_, p_i50828_3_, 90, 220);
      }

      public void func_230930_b_() {
         BeaconScreen.this.field_230706_i_.getConnection().sendPacket(new CUpdateBeaconPacket(Effect.getId(BeaconScreen.this.field_214105_n), Effect.getId(BeaconScreen.this.field_214106_o)));
         BeaconScreen.this.field_230706_i_.player.connection.sendPacket(new CCloseWindowPacket(BeaconScreen.this.field_230706_i_.player.openContainer.windowId));
         BeaconScreen.this.field_230706_i_.displayGuiScreen((Screen)null);
      }

      public void func_230443_a_(MatrixStack p_230443_1_, int p_230443_2_, int p_230443_3_) {
         BeaconScreen.this.func_238652_a_(p_230443_1_, DialogTexts.field_240632_c_, p_230443_2_, p_230443_3_);
      }
   }

   @OnlyIn(Dist.CLIENT)
   class PowerButton extends BeaconScreen.Button {
      private final Effect effect;
      private final TextureAtlasSprite field_212946_c;
      private final boolean field_212947_d;
      private final ITextComponent field_243336_e;

      public PowerButton(int p_i50827_2_, int p_i50827_3_, Effect p_i50827_4_, boolean p_i50827_5_) {
         super(p_i50827_2_, p_i50827_3_);
         this.effect = p_i50827_4_;
         this.field_212946_c = Minecraft.getInstance().getPotionSpriteUploader().getSprite(p_i50827_4_);
         this.field_212947_d = p_i50827_5_;
         this.field_243336_e = this.func_243337_a(p_i50827_4_, p_i50827_5_);
      }

      private ITextComponent func_243337_a(Effect p_243337_1_, boolean p_243337_2_) {
         IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent(p_243337_1_.getName());
         if (!p_243337_2_ && p_243337_1_ != Effects.REGENERATION) {
            iformattabletextcomponent.func_240702_b_(" II");
         }

         return iformattabletextcomponent;
      }

      public void func_230930_b_() {
         if (!this.isSelected()) {
            if (this.field_212947_d) {
               BeaconScreen.this.field_214105_n = this.effect;
            } else {
               BeaconScreen.this.field_214106_o = this.effect;
            }

            BeaconScreen.this.field_230710_m_.clear();
            BeaconScreen.this.field_230705_e_.clear();
            BeaconScreen.this.func_231160_c_();
            BeaconScreen.this.func_231023_e_();
         }
      }

      public void func_230443_a_(MatrixStack p_230443_1_, int p_230443_2_, int p_230443_3_) {
         BeaconScreen.this.func_238652_a_(p_230443_1_, this.field_243336_e, p_230443_2_, p_230443_3_);
      }

      protected void func_230454_a_(MatrixStack p_230454_1_) {
         Minecraft.getInstance().getTextureManager().bindTexture(this.field_212946_c.getAtlasTexture().getTextureLocation());
         func_238470_a_(p_230454_1_, this.field_230690_l_ + 2, this.field_230691_m_ + 2, this.func_230927_p_(), 18, 18, this.field_212946_c);
      }
   }

   @OnlyIn(Dist.CLIENT)
   abstract static class SpriteButton extends BeaconScreen.Button {
      private final int field_212948_a;
      private final int field_212949_b;

      protected SpriteButton(int p_i50825_1_, int p_i50825_2_, int p_i50825_3_, int p_i50825_4_) {
         super(p_i50825_1_, p_i50825_2_);
         this.field_212948_a = p_i50825_3_;
         this.field_212949_b = p_i50825_4_;
      }

      protected void func_230454_a_(MatrixStack p_230454_1_) {
         this.func_238474_b_(p_230454_1_, this.field_230690_l_ + 2, this.field_230691_m_ + 2, this.field_212948_a, this.field_212949_b, 18, 18);
      }
   }
}