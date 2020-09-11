package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.MerchantContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.network.play.client.CSelectTradePacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MerchantScreen extends ContainerScreen<MerchantContainer> {
   /** The GUI texture for the villager merchant GUI. */
   private static final ResourceLocation MERCHANT_GUI_TEXTURE = new ResourceLocation("textures/gui/container/villager2.png");
   private static final ITextComponent field_243351_B = new TranslationTextComponent("merchant.trades");
   private static final ITextComponent field_243352_C = new StringTextComponent(" - ");
   private static final ITextComponent field_243353_D = new TranslationTextComponent("merchant.deprecated");
   /** The integer value corresponding to the currently selected merchant recipe. */
   private int selectedMerchantRecipe;
   private final MerchantScreen.TradeButton[] field_214138_m = new MerchantScreen.TradeButton[7];
   private int field_214139_n;
   private boolean field_214140_o;

   public MerchantScreen(MerchantContainer p_i51080_1_, PlayerInventory p_i51080_2_, ITextComponent p_i51080_3_) {
      super(p_i51080_1_, p_i51080_2_, p_i51080_3_);
      this.xSize = 276;
      this.field_238744_r_ = 107;
   }

   private void func_195391_j() {
      this.container.setCurrentRecipeIndex(this.selectedMerchantRecipe);
      this.container.func_217046_g(this.selectedMerchantRecipe);
      this.field_230706_i_.getConnection().sendPacket(new CSelectTradePacket(this.selectedMerchantRecipe));
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      int i = (this.field_230708_k_ - this.xSize) / 2;
      int j = (this.field_230709_l_ - this.ySize) / 2;
      int k = j + 16 + 2;

      for(int l = 0; l < 7; ++l) {
         this.field_214138_m[l] = this.func_230480_a_(new MerchantScreen.TradeButton(i + 5, k, l, (p_214132_1_) -> {
            if (p_214132_1_ instanceof MerchantScreen.TradeButton) {
               this.selectedMerchantRecipe = ((MerchantScreen.TradeButton)p_214132_1_).func_212937_a() + this.field_214139_n;
               this.func_195391_j();
            }

         }));
         k += 20;
      }

   }

   protected void func_230451_b_(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
      int i = this.container.getMerchantLevel();
      if (i > 0 && i <= 5 && this.container.func_217042_i()) {
         ITextComponent itextcomponent = this.field_230704_d_.func_230532_e_().func_230529_a_(field_243352_C).func_230529_a_(new TranslationTextComponent("merchant.level." + i));
         int j = this.field_230712_o_.func_238414_a_(itextcomponent);
         int k = 49 + this.xSize / 2 - j / 2;
         this.field_230712_o_.func_243248_b(p_230451_1_, itextcomponent, (float)k, 6.0F, 4210752);
      } else {
         this.field_230712_o_.func_243248_b(p_230451_1_, this.field_230704_d_, (float)(49 + this.xSize / 2 - this.field_230712_o_.func_238414_a_(this.field_230704_d_) / 2), 6.0F, 4210752);
      }

      this.field_230712_o_.func_243248_b(p_230451_1_, this.playerInventory.getDisplayName(), (float)this.field_238744_r_, (float)this.field_238745_s_, 4210752);
      int l = this.field_230712_o_.func_238414_a_(field_243351_B);
      this.field_230712_o_.func_243248_b(p_230451_1_, field_243351_B, (float)(5 - l / 2 + 48), 6.0F, 4210752);
   }

   protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_230706_i_.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
      int i = (this.field_230708_k_ - this.xSize) / 2;
      int j = (this.field_230709_l_ - this.ySize) / 2;
      func_238464_a_(p_230450_1_, i, j, this.func_230927_p_(), 0.0F, 0.0F, this.xSize, this.ySize, 256, 512);
      MerchantOffers merchantoffers = this.container.getOffers();
      if (!merchantoffers.isEmpty()) {
         int k = this.selectedMerchantRecipe;
         if (k < 0 || k >= merchantoffers.size()) {
            return;
         }

         MerchantOffer merchantoffer = merchantoffers.get(k);
         if (merchantoffer.hasNoUsesLeft()) {
            this.field_230706_i_.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            func_238464_a_(p_230450_1_, this.guiLeft + 83 + 99, this.guiTop + 35, this.func_230927_p_(), 311.0F, 0.0F, 28, 21, 256, 512);
         }
      }

   }

   private void func_238839_a_(MatrixStack p_238839_1_, int p_238839_2_, int p_238839_3_, MerchantOffer p_238839_4_) {
      this.field_230706_i_.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
      int i = this.container.getMerchantLevel();
      int j = this.container.getXp();
      if (i < 5) {
         func_238464_a_(p_238839_1_, p_238839_2_ + 136, p_238839_3_ + 16, this.func_230927_p_(), 0.0F, 186.0F, 102, 5, 256, 512);
         int k = VillagerData.func_221133_b(i);
         if (j >= k && VillagerData.func_221128_d(i)) {
            int l = 100;
            float f = 100.0F / (float)(VillagerData.func_221127_c(i) - k);
            int i1 = Math.min(MathHelper.floor(f * (float)(j - k)), 100);
            func_238464_a_(p_238839_1_, p_238839_2_ + 136, p_238839_3_ + 16, this.func_230927_p_(), 0.0F, 191.0F, i1 + 1, 5, 256, 512);
            int j1 = this.container.getPendingExp();
            if (j1 > 0) {
               int k1 = Math.min(MathHelper.floor((float)j1 * f), 100 - i1);
               func_238464_a_(p_238839_1_, p_238839_2_ + 136 + i1 + 1, p_238839_3_ + 16 + 1, this.func_230927_p_(), 2.0F, 182.0F, k1, 3, 256, 512);
            }

         }
      }
   }

   private void func_238840_a_(MatrixStack p_238840_1_, int p_238840_2_, int p_238840_3_, MerchantOffers p_238840_4_) {
      int i = p_238840_4_.size() + 1 - 7;
      if (i > 1) {
         int j = 139 - (27 + (i - 1) * 139 / i);
         int k = 1 + j / i + 139 / i;
         int l = 113;
         int i1 = Math.min(113, this.field_214139_n * k);
         if (this.field_214139_n == i - 1) {
            i1 = 113;
         }

         func_238464_a_(p_238840_1_, p_238840_2_ + 94, p_238840_3_ + 18 + i1, this.func_230927_p_(), 0.0F, 199.0F, 6, 27, 256, 512);
      } else {
         func_238464_a_(p_238840_1_, p_238840_2_ + 94, p_238840_3_ + 18, this.func_230927_p_(), 6.0F, 199.0F, 6, 27, 256, 512);
      }

   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      MerchantOffers merchantoffers = this.container.getOffers();
      if (!merchantoffers.isEmpty()) {
         int i = (this.field_230708_k_ - this.xSize) / 2;
         int j = (this.field_230709_l_ - this.ySize) / 2;
         int k = j + 16 + 1;
         int l = i + 5 + 5;
         RenderSystem.pushMatrix();
         RenderSystem.enableRescaleNormal();
         this.field_230706_i_.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
         this.func_238840_a_(p_230430_1_, i, j, merchantoffers);
         int i1 = 0;

         for(MerchantOffer merchantoffer : merchantoffers) {
            if (this.func_214135_a(merchantoffers.size()) && (i1 < this.field_214139_n || i1 >= 7 + this.field_214139_n)) {
               ++i1;
            } else {
               ItemStack itemstack = merchantoffer.getBuyingStackFirst();
               ItemStack itemstack1 = merchantoffer.func_222205_b();
               ItemStack itemstack2 = merchantoffer.getBuyingStackSecond();
               ItemStack itemstack3 = merchantoffer.getSellingStack();
               this.field_230707_j_.zLevel = 100.0F;
               int j1 = k + 2;
               this.func_238841_a_(p_230430_1_, itemstack1, itemstack, l, j1);
               if (!itemstack2.isEmpty()) {
                  this.field_230707_j_.func_239390_c_(itemstack2, i + 5 + 35, j1);
                  this.field_230707_j_.renderItemOverlays(this.field_230712_o_, itemstack2, i + 5 + 35, j1);
               }

               this.func_238842_a_(p_230430_1_, merchantoffer, i, j1);
               this.field_230707_j_.func_239390_c_(itemstack3, i + 5 + 68, j1);
               this.field_230707_j_.renderItemOverlays(this.field_230712_o_, itemstack3, i + 5 + 68, j1);
               this.field_230707_j_.zLevel = 0.0F;
               k += 20;
               ++i1;
            }
         }

         int k1 = this.selectedMerchantRecipe;
         MerchantOffer merchantoffer1 = merchantoffers.get(k1);
         if (this.container.func_217042_i()) {
            this.func_238839_a_(p_230430_1_, i, j, merchantoffer1);
         }

         if (merchantoffer1.hasNoUsesLeft() && this.isPointInRegion(186, 35, 22, 21, (double)p_230430_2_, (double)p_230430_3_) && this.container.func_223432_h()) {
            this.func_238652_a_(p_230430_1_, field_243353_D, p_230430_2_, p_230430_3_);
         }

         for(MerchantScreen.TradeButton merchantscreen$tradebutton : this.field_214138_m) {
            if (merchantscreen$tradebutton.func_230449_g_()) {
               merchantscreen$tradebutton.func_230443_a_(p_230430_1_, p_230430_2_, p_230430_3_);
            }

            merchantscreen$tradebutton.field_230694_p_ = merchantscreen$tradebutton.field_212938_a < this.container.getOffers().size();
         }

         RenderSystem.popMatrix();
         RenderSystem.enableDepthTest();
      }

      this.func_230459_a_(p_230430_1_, p_230430_2_, p_230430_3_);
   }

   private void func_238842_a_(MatrixStack p_238842_1_, MerchantOffer p_238842_2_, int p_238842_3_, int p_238842_4_) {
      RenderSystem.enableBlend();
      this.field_230706_i_.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
      if (p_238842_2_.hasNoUsesLeft()) {
         func_238464_a_(p_238842_1_, p_238842_3_ + 5 + 35 + 20, p_238842_4_ + 3, this.func_230927_p_(), 25.0F, 171.0F, 10, 9, 256, 512);
      } else {
         func_238464_a_(p_238842_1_, p_238842_3_ + 5 + 35 + 20, p_238842_4_ + 3, this.func_230927_p_(), 15.0F, 171.0F, 10, 9, 256, 512);
      }

   }

   private void func_238841_a_(MatrixStack p_238841_1_, ItemStack p_238841_2_, ItemStack p_238841_3_, int p_238841_4_, int p_238841_5_) {
      this.field_230707_j_.func_239390_c_(p_238841_2_, p_238841_4_, p_238841_5_);
      if (p_238841_3_.getCount() == p_238841_2_.getCount()) {
         this.field_230707_j_.renderItemOverlays(this.field_230712_o_, p_238841_2_, p_238841_4_, p_238841_5_);
      } else {
         this.field_230707_j_.renderItemOverlayIntoGUI(this.field_230712_o_, p_238841_3_, p_238841_4_, p_238841_5_, p_238841_3_.getCount() == 1 ? "1" : null);
         this.field_230707_j_.renderItemOverlayIntoGUI(this.field_230712_o_, p_238841_2_, p_238841_4_ + 14, p_238841_5_, p_238841_2_.getCount() == 1 ? "1" : null);
         this.field_230706_i_.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
         this.func_230926_e_(this.func_230927_p_() + 300);
         func_238464_a_(p_238841_1_, p_238841_4_ + 7, p_238841_5_ + 12, this.func_230927_p_(), 0.0F, 176.0F, 9, 2, 256, 512);
         this.func_230926_e_(this.func_230927_p_() - 300);
      }

   }

   private boolean func_214135_a(int p_214135_1_) {
      return p_214135_1_ > 7;
   }

   public boolean func_231043_a_(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
      int i = this.container.getOffers().size();
      if (this.func_214135_a(i)) {
         int j = i - 7;
         this.field_214139_n = (int)((double)this.field_214139_n - p_231043_5_);
         this.field_214139_n = MathHelper.clamp(this.field_214139_n, 0, j);
      }

      return true;
   }

   public boolean func_231045_a_(double p_231045_1_, double p_231045_3_, int p_231045_5_, double p_231045_6_, double p_231045_8_) {
      int i = this.container.getOffers().size();
      if (this.field_214140_o) {
         int j = this.guiTop + 18;
         int k = j + 139;
         int l = i - 7;
         float f = ((float)p_231045_3_ - (float)j - 13.5F) / ((float)(k - j) - 27.0F);
         f = f * (float)l + 0.5F;
         this.field_214139_n = MathHelper.clamp((int)f, 0, l);
         return true;
      } else {
         return super.func_231045_a_(p_231045_1_, p_231045_3_, p_231045_5_, p_231045_6_, p_231045_8_);
      }
   }

   public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      this.field_214140_o = false;
      int i = (this.field_230708_k_ - this.xSize) / 2;
      int j = (this.field_230709_l_ - this.ySize) / 2;
      if (this.func_214135_a(this.container.getOffers().size()) && p_231044_1_ > (double)(i + 94) && p_231044_1_ < (double)(i + 94 + 6) && p_231044_3_ > (double)(j + 18) && p_231044_3_ <= (double)(j + 18 + 139 + 1)) {
         this.field_214140_o = true;
      }

      return super.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_);
   }

   @OnlyIn(Dist.CLIENT)
   class TradeButton extends Button {
      final int field_212938_a;

      public TradeButton(int p_i50601_2_, int p_i50601_3_, int p_i50601_4_, Button.IPressable p_i50601_5_) {
         super(p_i50601_2_, p_i50601_3_, 89, 20, StringTextComponent.field_240750_d_, p_i50601_5_);
         this.field_212938_a = p_i50601_4_;
         this.field_230694_p_ = false;
      }

      public int func_212937_a() {
         return this.field_212938_a;
      }

      public void func_230443_a_(MatrixStack p_230443_1_, int p_230443_2_, int p_230443_3_) {
         if (this.field_230692_n_ && MerchantScreen.this.container.getOffers().size() > this.field_212938_a + MerchantScreen.this.field_214139_n) {
            if (p_230443_2_ < this.field_230690_l_ + 20) {
               ItemStack itemstack = MerchantScreen.this.container.getOffers().get(this.field_212938_a + MerchantScreen.this.field_214139_n).func_222205_b();
               MerchantScreen.this.func_230457_a_(p_230443_1_, itemstack, p_230443_2_, p_230443_3_);
            } else if (p_230443_2_ < this.field_230690_l_ + 50 && p_230443_2_ > this.field_230690_l_ + 30) {
               ItemStack itemstack2 = MerchantScreen.this.container.getOffers().get(this.field_212938_a + MerchantScreen.this.field_214139_n).getBuyingStackSecond();
               if (!itemstack2.isEmpty()) {
                  MerchantScreen.this.func_230457_a_(p_230443_1_, itemstack2, p_230443_2_, p_230443_3_);
               }
            } else if (p_230443_2_ > this.field_230690_l_ + 65) {
               ItemStack itemstack1 = MerchantScreen.this.container.getOffers().get(this.field_212938_a + MerchantScreen.this.field_214139_n).getSellingStack();
               MerchantScreen.this.func_230457_a_(p_230443_1_, itemstack1, p_230443_2_, p_230443_3_);
            }
         }

      }
   }
}