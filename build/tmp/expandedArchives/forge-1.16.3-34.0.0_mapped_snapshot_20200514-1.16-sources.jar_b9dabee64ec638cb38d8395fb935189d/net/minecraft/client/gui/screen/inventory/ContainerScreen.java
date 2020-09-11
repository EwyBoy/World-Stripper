package net.minecraft.client.gui.screen.inventory;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ContainerScreen<T extends Container> extends Screen implements IHasContainer<T> {
   /** The location of the inventory background texture */
   public static final ResourceLocation INVENTORY_BACKGROUND = new ResourceLocation("textures/gui/container/inventory.png");
   /** The X size of the inventory window in pixels. */
   protected int xSize = 176;
   /** The Y size of the inventory window in pixels. */
   protected int ySize = 166;
   protected int field_238742_p_;
   protected int field_238743_q_;
   protected int field_238744_r_;
   protected int field_238745_s_;
   /** A list of the players inventory slots */
   protected final T container;
   protected final PlayerInventory playerInventory;
   /** Holds the slot currently hovered */
   @Nullable
   protected Slot hoveredSlot;
   /** Used when touchscreen is enabled */
   @Nullable
   private Slot clickedSlot;
   @Nullable
   private Slot returningStackDestSlot;
   @Nullable
   private Slot currentDragTargetSlot;
   @Nullable
   private Slot lastClickSlot;
   /** Starting X position for the Gui. Inconsistent use for Gui backgrounds. */
   protected int guiLeft;
   /** Starting Y position for the Gui. Inconsistent use for Gui backgrounds. */
   protected int guiTop;
   /** Used when touchscreen is enabled. */
   private boolean isRightMouseClick;
   /** Used when touchscreen is enabled */
   private ItemStack draggedStack = ItemStack.EMPTY;
   private int touchUpX;
   private int touchUpY;
   private long returningStackTime;
   /** Used when touchscreen is enabled */
   private ItemStack returningStack = ItemStack.EMPTY;
   private long dragItemDropDelay;
   protected final Set<Slot> dragSplittingSlots = Sets.newHashSet();
   protected boolean dragSplitting;
   private int dragSplittingLimit;
   private int dragSplittingButton;
   private boolean ignoreMouseUp;
   private int dragSplittingRemnant;
   private long lastClickTime;
   private int lastClickButton;
   private boolean doubleClick;
   private ItemStack shiftClickedSlot = ItemStack.EMPTY;

   public ContainerScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
      super(titleIn);
      this.container = screenContainer;
      this.playerInventory = inv;
      this.ignoreMouseUp = true;
      this.field_238742_p_ = 8;
      this.field_238743_q_ = 6;
      this.field_238744_r_ = 8;
      this.field_238745_s_ = this.ySize - 94;
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      this.guiLeft = (this.field_230708_k_ - this.xSize) / 2;
      this.guiTop = (this.field_230709_l_ - this.ySize) / 2;
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      int i = this.guiLeft;
      int j = this.guiTop;
      this.func_230450_a_(p_230430_1_, p_230430_4_, p_230430_2_, p_230430_3_);
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiContainerEvent.DrawBackground(this, p_230430_1_, p_230430_2_, p_230430_3_));
      RenderSystem.disableRescaleNormal();
      RenderSystem.disableDepthTest();
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      RenderSystem.pushMatrix();
      RenderSystem.translatef((float)i, (float)j, 0.0F);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableRescaleNormal();
      this.hoveredSlot = null;
      int k = 240;
      int l = 240;
      RenderSystem.glMultiTexCoord2f(33986, 240.0F, 240.0F);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

      for(int i1 = 0; i1 < this.container.inventorySlots.size(); ++i1) {
         Slot slot = this.container.inventorySlots.get(i1);
         if (slot.isEnabled()) {
            this.func_238746_a_(p_230430_1_, slot);
         }

         if (this.isSlotSelected(slot, (double)p_230430_2_, (double)p_230430_3_) && slot.isEnabled()) {
            this.hoveredSlot = slot;
            RenderSystem.disableDepthTest();
            int j1 = slot.xPos;
            int k1 = slot.yPos;
            RenderSystem.colorMask(true, true, true, false);
            int slotColor = this.getSlotColor(i1);
            this.func_238468_a_(p_230430_1_, j1, k1, j1 + 16, k1 + 16, slotColor, slotColor);
            RenderSystem.colorMask(true, true, true, true);
            RenderSystem.enableDepthTest();
         }
      }

      this.func_230451_b_(p_230430_1_, p_230430_2_, p_230430_3_);
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiContainerEvent.DrawForeground(this, p_230430_1_, p_230430_2_, p_230430_3_));
      PlayerInventory playerinventory = this.field_230706_i_.player.inventory;
      ItemStack itemstack = this.draggedStack.isEmpty() ? playerinventory.getItemStack() : this.draggedStack;
      if (!itemstack.isEmpty()) {
         int j2 = 8;
         int k2 = this.draggedStack.isEmpty() ? 8 : 16;
         String s = null;
         if (!this.draggedStack.isEmpty() && this.isRightMouseClick) {
            itemstack = itemstack.copy();
            itemstack.setCount(MathHelper.ceil((float)itemstack.getCount() / 2.0F));
         } else if (this.dragSplitting && this.dragSplittingSlots.size() > 1) {
            itemstack = itemstack.copy();
            itemstack.setCount(this.dragSplittingRemnant);
            if (itemstack.isEmpty()) {
               s = "" + TextFormatting.YELLOW + "0";
            }
         }

         this.drawItemStack(itemstack, p_230430_2_ - i - 8, p_230430_3_ - j - k2, s);
      }

      if (!this.returningStack.isEmpty()) {
         float f = (float)(Util.milliTime() - this.returningStackTime) / 100.0F;
         if (f >= 1.0F) {
            f = 1.0F;
            this.returningStack = ItemStack.EMPTY;
         }

         int l2 = this.returningStackDestSlot.xPos - this.touchUpX;
         int i3 = this.returningStackDestSlot.yPos - this.touchUpY;
         int l1 = this.touchUpX + (int)((float)l2 * f);
         int i2 = this.touchUpY + (int)((float)i3 * f);
         this.drawItemStack(this.returningStack, l1, i2, (String)null);
      }

      RenderSystem.popMatrix();
      RenderSystem.enableDepthTest();
   }

   protected void func_230459_a_(MatrixStack p_230459_1_, int p_230459_2_, int p_230459_3_) {
      if (this.field_230706_i_.player.inventory.getItemStack().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.getHasStack()) {
         this.func_230457_a_(p_230459_1_, this.hoveredSlot.getStack(), p_230459_2_, p_230459_3_);
      }

   }

   /**
    * Draws an ItemStack.
    *  
    * The z index is increased by 32 (and not decreased afterwards), and the item is then rendered at z=200.
    */
   private void drawItemStack(ItemStack stack, int x, int y, String altText) {
      RenderSystem.translatef(0.0F, 0.0F, 32.0F);
      this.func_230926_e_(200);
      this.field_230707_j_.zLevel = 200.0F;
      net.minecraft.client.gui.FontRenderer font = stack.getItem().getFontRenderer(stack);
      if (font == null) font = this.field_230712_o_;
      this.field_230707_j_.renderItemAndEffectIntoGUI(stack, x, y);
      this.field_230707_j_.renderItemOverlayIntoGUI(font, stack, x, y - (this.draggedStack.isEmpty() ? 0 : 8), altText);
      this.func_230926_e_(0);
      this.field_230707_j_.zLevel = 0.0F;
   }

   protected void func_230451_b_(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
      this.field_230712_o_.func_243248_b(p_230451_1_, this.field_230704_d_, (float)this.field_238742_p_, (float)this.field_238743_q_, 4210752);
      this.field_230712_o_.func_243248_b(p_230451_1_, this.playerInventory.getDisplayName(), (float)this.field_238744_r_, (float)this.field_238745_s_, 4210752);
   }

   protected abstract void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_);

   private void func_238746_a_(MatrixStack p_238746_1_, Slot p_238746_2_) {
      int i = p_238746_2_.xPos;
      int j = p_238746_2_.yPos;
      ItemStack itemstack = p_238746_2_.getStack();
      boolean flag = false;
      boolean flag1 = p_238746_2_ == this.clickedSlot && !this.draggedStack.isEmpty() && !this.isRightMouseClick;
      ItemStack itemstack1 = this.field_230706_i_.player.inventory.getItemStack();
      String s = null;
      if (p_238746_2_ == this.clickedSlot && !this.draggedStack.isEmpty() && this.isRightMouseClick && !itemstack.isEmpty()) {
         itemstack = itemstack.copy();
         itemstack.setCount(itemstack.getCount() / 2);
      } else if (this.dragSplitting && this.dragSplittingSlots.contains(p_238746_2_) && !itemstack1.isEmpty()) {
         if (this.dragSplittingSlots.size() == 1) {
            return;
         }

         if (Container.canAddItemToSlot(p_238746_2_, itemstack1, true) && this.container.canDragIntoSlot(p_238746_2_)) {
            itemstack = itemstack1.copy();
            flag = true;
            Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack, p_238746_2_.getStack().isEmpty() ? 0 : p_238746_2_.getStack().getCount());
            int k = Math.min(itemstack.getMaxStackSize(), p_238746_2_.getItemStackLimit(itemstack));
            if (itemstack.getCount() > k) {
               s = TextFormatting.YELLOW.toString() + k;
               itemstack.setCount(k);
            }
         } else {
            this.dragSplittingSlots.remove(p_238746_2_);
            this.updateDragSplitting();
         }
      }

      this.func_230926_e_(100);
      this.field_230707_j_.zLevel = 100.0F;
      if (itemstack.isEmpty() && p_238746_2_.isEnabled()) {
         Pair<ResourceLocation, ResourceLocation> pair = p_238746_2_.func_225517_c_();
         if (pair != null) {
            TextureAtlasSprite textureatlassprite = this.field_230706_i_.getAtlasSpriteGetter(pair.getFirst()).apply(pair.getSecond());
            this.field_230706_i_.getTextureManager().bindTexture(textureatlassprite.getAtlasTexture().getTextureLocation());
            func_238470_a_(p_238746_1_, i, j, this.func_230927_p_(), 16, 16, textureatlassprite);
            flag1 = true;
         }
      }

      if (!flag1) {
         if (flag) {
            func_238467_a_(p_238746_1_, i, j, i + 16, j + 16, -2130706433);
         }

         RenderSystem.enableDepthTest();
         this.field_230707_j_.renderItemAndEffectIntoGUI(this.field_230706_i_.player, itemstack, i, j);
         this.field_230707_j_.renderItemOverlayIntoGUI(this.field_230712_o_, itemstack, i, j, s);
      }

      this.field_230707_j_.zLevel = 0.0F;
      this.func_230926_e_(0);
   }

   private void updateDragSplitting() {
      ItemStack itemstack = this.field_230706_i_.player.inventory.getItemStack();
      if (!itemstack.isEmpty() && this.dragSplitting) {
         if (this.dragSplittingLimit == 2) {
            this.dragSplittingRemnant = itemstack.getMaxStackSize();
         } else {
            this.dragSplittingRemnant = itemstack.getCount();

            for(Slot slot : this.dragSplittingSlots) {
               ItemStack itemstack1 = itemstack.copy();
               ItemStack itemstack2 = slot.getStack();
               int i = itemstack2.isEmpty() ? 0 : itemstack2.getCount();
               Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack1, i);
               int j = Math.min(itemstack1.getMaxStackSize(), slot.getItemStackLimit(itemstack1));
               if (itemstack1.getCount() > j) {
                  itemstack1.setCount(j);
               }

               this.dragSplittingRemnant -= itemstack1.getCount() - i;
            }

         }
      }
   }

   @Nullable
   private Slot getSelectedSlot(double mouseX, double mouseY) {
      for(int i = 0; i < this.container.inventorySlots.size(); ++i) {
         Slot slot = this.container.inventorySlots.get(i);
         if (this.isSlotSelected(slot, mouseX, mouseY) && slot.isEnabled()) {
            return slot;
         }
      }

      return null;
   }

   public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      if (super.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_)) {
         return true;
      } else {
         InputMappings.Input mouseKey = InputMappings.Type.MOUSE.getOrMakeInput(p_231044_5_);
         boolean flag = this.field_230706_i_.gameSettings.keyBindPickBlock.isActiveAndMatches(mouseKey);
         Slot slot = this.getSelectedSlot(p_231044_1_, p_231044_3_);
         long i = Util.milliTime();
         this.doubleClick = this.lastClickSlot == slot && i - this.lastClickTime < 250L && this.lastClickButton == p_231044_5_;
         this.ignoreMouseUp = false;
         if (p_231044_5_ != 0 && p_231044_5_ != 1 && !flag) {
            this.func_241609_a_(p_231044_5_);
         } else {
            int j = this.guiLeft;
            int k = this.guiTop;
            boolean flag1 = this.hasClickedOutside(p_231044_1_, p_231044_3_, j, k, p_231044_5_);
            if (slot != null) flag1 = false; // Forge, prevent dropping of items through slots outside of GUI boundaries
            int l = -1;
            if (slot != null) {
               l = slot.slotNumber;
            }

            if (flag1) {
               l = -999;
            }

            if (this.field_230706_i_.gameSettings.touchscreen && flag1 && this.field_230706_i_.player.inventory.getItemStack().isEmpty()) {
               this.field_230706_i_.displayGuiScreen((Screen)null);
               return true;
            }

            if (l != -1) {
               if (this.field_230706_i_.gameSettings.touchscreen) {
                  if (slot != null && slot.getHasStack()) {
                     this.clickedSlot = slot;
                     this.draggedStack = ItemStack.EMPTY;
                     this.isRightMouseClick = p_231044_5_ == 1;
                  } else {
                     this.clickedSlot = null;
                  }
               } else if (!this.dragSplitting) {
                  if (this.field_230706_i_.player.inventory.getItemStack().isEmpty()) {
                     if (this.field_230706_i_.gameSettings.keyBindPickBlock.isActiveAndMatches(mouseKey)) {
                        this.handleMouseClick(slot, l, p_231044_5_, ClickType.CLONE);
                     } else {
                        boolean flag2 = l != -999 && (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 340) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 344));
                        ClickType clicktype = ClickType.PICKUP;
                        if (flag2) {
                           this.shiftClickedSlot = slot != null && slot.getHasStack() ? slot.getStack().copy() : ItemStack.EMPTY;
                           clicktype = ClickType.QUICK_MOVE;
                        } else if (l == -999) {
                           clicktype = ClickType.THROW;
                        }

                        this.handleMouseClick(slot, l, p_231044_5_, clicktype);
                     }

                     this.ignoreMouseUp = true;
                  } else {
                     this.dragSplitting = true;
                     this.dragSplittingButton = p_231044_5_;
                     this.dragSplittingSlots.clear();
                     if (p_231044_5_ == 0) {
                        this.dragSplittingLimit = 0;
                     } else if (p_231044_5_ == 1) {
                        this.dragSplittingLimit = 1;
                     } else if (this.field_230706_i_.gameSettings.keyBindPickBlock.isActiveAndMatches(mouseKey)) {
                        this.dragSplittingLimit = 2;
                     }
                  }
               }
            }
         }

         this.lastClickSlot = slot;
         this.lastClickTime = i;
         this.lastClickButton = p_231044_5_;
         return true;
      }
   }

   private void func_241609_a_(int p_241609_1_) {
      if (this.hoveredSlot != null && this.field_230706_i_.player.inventory.getItemStack().isEmpty()) {
         if (this.field_230706_i_.gameSettings.keyBindSwapHands.matchesMouseKey(p_241609_1_)) {
            this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, 40, ClickType.SWAP);
            return;
         }

         for(int i = 0; i < 9; ++i) {
            if (this.field_230706_i_.gameSettings.keyBindsHotbar[i].matchesMouseKey(p_241609_1_)) {
               this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, i, ClickType.SWAP);
            }
         }
      }

   }

   protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) {
      return mouseX < (double)guiLeftIn || mouseY < (double)guiTopIn || mouseX >= (double)(guiLeftIn + this.xSize) || mouseY >= (double)(guiTopIn + this.ySize);
   }

   public boolean func_231045_a_(double p_231045_1_, double p_231045_3_, int p_231045_5_, double p_231045_6_, double p_231045_8_) {
      Slot slot = this.getSelectedSlot(p_231045_1_, p_231045_3_);
      ItemStack itemstack = this.field_230706_i_.player.inventory.getItemStack();
      if (this.clickedSlot != null && this.field_230706_i_.gameSettings.touchscreen) {
         if (p_231045_5_ == 0 || p_231045_5_ == 1) {
            if (this.draggedStack.isEmpty()) {
               if (slot != this.clickedSlot && !this.clickedSlot.getStack().isEmpty()) {
                  this.draggedStack = this.clickedSlot.getStack().copy();
               }
            } else if (this.draggedStack.getCount() > 1 && slot != null && Container.canAddItemToSlot(slot, this.draggedStack, false)) {
               long i = Util.milliTime();
               if (this.currentDragTargetSlot == slot) {
                  if (i - this.dragItemDropDelay > 500L) {
                     this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, ClickType.PICKUP);
                     this.handleMouseClick(slot, slot.slotNumber, 1, ClickType.PICKUP);
                     this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, ClickType.PICKUP);
                     this.dragItemDropDelay = i + 750L;
                     this.draggedStack.shrink(1);
                  }
               } else {
                  this.currentDragTargetSlot = slot;
                  this.dragItemDropDelay = i;
               }
            }
         }
      } else if (this.dragSplitting && slot != null && !itemstack.isEmpty() && (itemstack.getCount() > this.dragSplittingSlots.size() || this.dragSplittingLimit == 2) && Container.canAddItemToSlot(slot, itemstack, true) && slot.isItemValid(itemstack) && this.container.canDragIntoSlot(slot)) {
         this.dragSplittingSlots.add(slot);
         this.updateDragSplitting();
      }

      return true;
   }

   public boolean func_231048_c_(double p_231048_1_, double p_231048_3_, int p_231048_5_) {
      super.func_231048_c_(p_231048_1_, p_231048_3_, p_231048_5_); //Forge, Call parent to release buttons
      Slot slot = this.getSelectedSlot(p_231048_1_, p_231048_3_);
      int i = this.guiLeft;
      int j = this.guiTop;
      boolean flag = this.hasClickedOutside(p_231048_1_, p_231048_3_, i, j, p_231048_5_);
      if (slot != null) flag = false; // Forge, prevent dropping of items through slots outside of GUI boundaries
      InputMappings.Input mouseKey = InputMappings.Type.MOUSE.getOrMakeInput(p_231048_5_);
      int k = -1;
      if (slot != null) {
         k = slot.slotNumber;
      }

      if (flag) {
         k = -999;
      }

      if (this.doubleClick && slot != null && p_231048_5_ == 0 && this.container.canMergeSlot(ItemStack.EMPTY, slot)) {
         if (func_231173_s_()) {
            if (!this.shiftClickedSlot.isEmpty()) {
               for(Slot slot2 : this.container.inventorySlots) {
                  if (slot2 != null && slot2.canTakeStack(this.field_230706_i_.player) && slot2.getHasStack() && slot2.isSameInventory(slot) && Container.canAddItemToSlot(slot2, this.shiftClickedSlot, true)) {
                     this.handleMouseClick(slot2, slot2.slotNumber, p_231048_5_, ClickType.QUICK_MOVE);
                  }
               }
            }
         } else {
            this.handleMouseClick(slot, k, p_231048_5_, ClickType.PICKUP_ALL);
         }

         this.doubleClick = false;
         this.lastClickTime = 0L;
      } else {
         if (this.dragSplitting && this.dragSplittingButton != p_231048_5_) {
            this.dragSplitting = false;
            this.dragSplittingSlots.clear();
            this.ignoreMouseUp = true;
            return true;
         }

         if (this.ignoreMouseUp) {
            this.ignoreMouseUp = false;
            return true;
         }

         if (this.clickedSlot != null && this.field_230706_i_.gameSettings.touchscreen) {
            if (p_231048_5_ == 0 || p_231048_5_ == 1) {
               if (this.draggedStack.isEmpty() && slot != this.clickedSlot) {
                  this.draggedStack = this.clickedSlot.getStack();
               }

               boolean flag2 = Container.canAddItemToSlot(slot, this.draggedStack, false);
               if (k != -1 && !this.draggedStack.isEmpty() && flag2) {
                  this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, p_231048_5_, ClickType.PICKUP);
                  this.handleMouseClick(slot, k, 0, ClickType.PICKUP);
                  if (this.field_230706_i_.player.inventory.getItemStack().isEmpty()) {
                     this.returningStack = ItemStack.EMPTY;
                  } else {
                     this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, p_231048_5_, ClickType.PICKUP);
                     this.touchUpX = MathHelper.floor(p_231048_1_ - (double)i);
                     this.touchUpY = MathHelper.floor(p_231048_3_ - (double)j);
                     this.returningStackDestSlot = this.clickedSlot;
                     this.returningStack = this.draggedStack;
                     this.returningStackTime = Util.milliTime();
                  }
               } else if (!this.draggedStack.isEmpty()) {
                  this.touchUpX = MathHelper.floor(p_231048_1_ - (double)i);
                  this.touchUpY = MathHelper.floor(p_231048_3_ - (double)j);
                  this.returningStackDestSlot = this.clickedSlot;
                  this.returningStack = this.draggedStack;
                  this.returningStackTime = Util.milliTime();
               }

               this.draggedStack = ItemStack.EMPTY;
               this.clickedSlot = null;
            }
         } else if (this.dragSplitting && !this.dragSplittingSlots.isEmpty()) {
            this.handleMouseClick((Slot)null, -999, Container.getQuickcraftMask(0, this.dragSplittingLimit), ClickType.QUICK_CRAFT);

            for(Slot slot1 : this.dragSplittingSlots) {
               this.handleMouseClick(slot1, slot1.slotNumber, Container.getQuickcraftMask(1, this.dragSplittingLimit), ClickType.QUICK_CRAFT);
            }

            this.handleMouseClick((Slot)null, -999, Container.getQuickcraftMask(2, this.dragSplittingLimit), ClickType.QUICK_CRAFT);
         } else if (!this.field_230706_i_.player.inventory.getItemStack().isEmpty()) {
            if (this.field_230706_i_.gameSettings.keyBindPickBlock.isActiveAndMatches(mouseKey)) {
               this.handleMouseClick(slot, k, p_231048_5_, ClickType.CLONE);
            } else {
               boolean flag1 = k != -999 && (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 340) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 344));
               if (flag1) {
                  this.shiftClickedSlot = slot != null && slot.getHasStack() ? slot.getStack().copy() : ItemStack.EMPTY;
               }

               this.handleMouseClick(slot, k, p_231048_5_, flag1 ? ClickType.QUICK_MOVE : ClickType.PICKUP);
            }
         }
      }

      if (this.field_230706_i_.player.inventory.getItemStack().isEmpty()) {
         this.lastClickTime = 0L;
      }

      this.dragSplitting = false;
      return true;
   }

   private boolean isSlotSelected(Slot slotIn, double mouseX, double mouseY) {
      return this.isPointInRegion(slotIn.xPos, slotIn.yPos, 16, 16, mouseX, mouseY);
   }

   protected boolean isPointInRegion(int x, int y, int width, int height, double mouseX, double mouseY) {
      int i = this.guiLeft;
      int j = this.guiTop;
      mouseX = mouseX - (double)i;
      mouseY = mouseY - (double)j;
      return mouseX >= (double)(x - 1) && mouseX < (double)(x + width + 1) && mouseY >= (double)(y - 1) && mouseY < (double)(y + height + 1);
   }

   /**
    * Called when the mouse is clicked over a slot or outside the gui.
    */
   protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
      if (slotIn != null) {
         slotId = slotIn.slotNumber;
      }

      this.field_230706_i_.playerController.windowClick(this.container.windowId, slotId, mouseButton, type, this.field_230706_i_.player);
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      InputMappings.Input mouseKey = InputMappings.getInputByCode(p_231046_1_, p_231046_2_);
      if (super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_)) {
         return true;
      } else if (this.field_230706_i_.gameSettings.keyBindInventory.isActiveAndMatches(mouseKey)) {
         this.func_231175_as__();
         return true;
      } else {
         boolean handled = this.func_195363_d(p_231046_1_, p_231046_2_);// Forge MC-146650: Needs to return true when the key is handled
         if (this.hoveredSlot != null && this.hoveredSlot.getHasStack()) {
            if (this.field_230706_i_.gameSettings.keyBindPickBlock.isActiveAndMatches(mouseKey)) {
               this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, 0, ClickType.CLONE);
               handled = true;
            } else if (this.field_230706_i_.gameSettings.keyBindDrop.isActiveAndMatches(mouseKey)) {
               this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, func_231172_r_() ? 1 : 0, ClickType.THROW);
               handled = true;
            }
         } else if (this.field_230706_i_.gameSettings.keyBindDrop.isActiveAndMatches(mouseKey)) {
             handled = true; // Forge MC-146650: Emulate MC bug, so we don't drop from hotbar when pressing drop without hovering over a item.
         }

         return handled;
      }
   }

   protected boolean func_195363_d(int keyCode, int scanCode) {
      if (this.field_230706_i_.player.inventory.getItemStack().isEmpty() && this.hoveredSlot != null) {
         if (this.field_230706_i_.gameSettings.keyBindSwapHands.isActiveAndMatches(InputMappings.getInputByCode(keyCode, scanCode))) {
            this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, 40, ClickType.SWAP);
            return true;
         }

         for(int i = 0; i < 9; ++i) {
            if (this.field_230706_i_.gameSettings.keyBindsHotbar[i].isActiveAndMatches(InputMappings.getInputByCode(keyCode, scanCode))) {
               this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, i, ClickType.SWAP);
               return true;
            }
         }
      }

      return false;
   }

   public void func_231164_f_() {
      if (this.field_230706_i_.player != null) {
         this.container.onContainerClosed(this.field_230706_i_.player);
      }
   }

   public boolean func_231177_au__() {
      return false;
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      if (!this.field_230706_i_.player.isAlive() || this.field_230706_i_.player.removed) {
         this.field_230706_i_.player.closeScreen();
      }

   }

   public T getContainer() {
      return this.container;
   }

   @javax.annotation.Nullable
   public Slot getSlotUnderMouse() { return this.hoveredSlot; }
   public int getGuiLeft() { return guiLeft; }
   public int getGuiTop() { return guiTop; }
   public int getXSize() { return xSize; }
   public int getYSize() { return ySize; }

   protected int slotColor = -2130706433;
   public int getSlotColor(int index) {
      return slotColor;
   }

   public void func_231175_as__() {
      this.field_230706_i_.player.closeScreen();
      super.func_231175_as__();
   }
}