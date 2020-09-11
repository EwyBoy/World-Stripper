package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ChangePageButton;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ReadBookScreen extends Screen {
   public static final ReadBookScreen.IBookInfo EMPTY_BOOK = new ReadBookScreen.IBookInfo() {
      /**
       * Returns the size of the book
       */
      public int getPageCount() {
         return 0;
      }

      public ITextProperties func_230456_a_(int p_230456_1_) {
         return ITextProperties.field_240651_c_;
      }
   };
   public static final ResourceLocation BOOK_TEXTURES = new ResourceLocation("textures/gui/book.png");
   private ReadBookScreen.IBookInfo bookInfo;
   private int currPage;
   /** Holds a copy of the page text, split into page width lines */
   private List<IReorderingProcessor> cachedPageLines = Collections.emptyList();
   private int cachedPage = -1;
   private ITextComponent field_243344_s = StringTextComponent.field_240750_d_;
   private ChangePageButton buttonNextPage;
   private ChangePageButton buttonPreviousPage;
   /** Determines if a sound is played when the page is turned */
   private final boolean pageTurnSounds;

   public ReadBookScreen(ReadBookScreen.IBookInfo bookInfoIn) {
      this(bookInfoIn, true);
   }

   public ReadBookScreen() {
      this(EMPTY_BOOK, false);
   }

   private ReadBookScreen(ReadBookScreen.IBookInfo bookInfoIn, boolean pageTurnSoundsIn) {
      super(NarratorChatListener.EMPTY);
      this.bookInfo = bookInfoIn;
      this.pageTurnSounds = pageTurnSoundsIn;
   }

   public void func_214155_a(ReadBookScreen.IBookInfo p_214155_1_) {
      this.bookInfo = p_214155_1_;
      this.currPage = MathHelper.clamp(this.currPage, 0, p_214155_1_.getPageCount());
      this.updateButtons();
      this.cachedPage = -1;
   }

   /**
    * Moves the book to the specified page and returns true if it exists, false otherwise
    */
   public boolean showPage(int pageNum) {
      int i = MathHelper.clamp(pageNum, 0, this.bookInfo.getPageCount() - 1);
      if (i != this.currPage) {
         this.currPage = i;
         this.updateButtons();
         this.cachedPage = -1;
         return true;
      } else {
         return false;
      }
   }

   /**
    * I'm not sure why this exists. The function it calls is public and does all of the work
    */
   protected boolean showPage2(int pageNum) {
      return this.showPage(pageNum);
   }

   protected void func_231160_c_() {
      this.addDoneButton();
      this.addChangePageButtons();
   }

   protected void addDoneButton() {
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, 196, 200, 20, DialogTexts.field_240632_c_, (p_214161_1_) -> {
         this.field_230706_i_.displayGuiScreen((Screen)null);
      }));
   }

   protected void addChangePageButtons() {
      int i = (this.field_230708_k_ - 192) / 2;
      int j = 2;
      this.buttonNextPage = this.func_230480_a_(new ChangePageButton(i + 116, 159, true, (p_214159_1_) -> {
         this.nextPage();
      }, this.pageTurnSounds));
      this.buttonPreviousPage = this.func_230480_a_(new ChangePageButton(i + 43, 159, false, (p_214158_1_) -> {
         this.previousPage();
      }, this.pageTurnSounds));
      this.updateButtons();
   }

   private int getPageCount() {
      return this.bookInfo.getPageCount();
   }

   /**
    * Moves the display back one page
    */
   protected void previousPage() {
      if (this.currPage > 0) {
         --this.currPage;
      }

      this.updateButtons();
   }

   /**
    * Moves the display forward one page
    */
   protected void nextPage() {
      if (this.currPage < this.getPageCount() - 1) {
         ++this.currPage;
      }

      this.updateButtons();
   }

   private void updateButtons() {
      this.buttonNextPage.field_230694_p_ = this.currPage < this.getPageCount() - 1;
      this.buttonPreviousPage.field_230694_p_ = this.currPage > 0;
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_)) {
         return true;
      } else {
         switch(p_231046_1_) {
         case 266:
            this.buttonPreviousPage.func_230930_b_();
            return true;
         case 267:
            this.buttonNextPage.func_230930_b_();
            return true;
         default:
            return false;
         }
      }
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_230706_i_.getTextureManager().bindTexture(BOOK_TEXTURES);
      int i = (this.field_230708_k_ - 192) / 2;
      int j = 2;
      this.func_238474_b_(p_230430_1_, i, 2, 0, 0, 192, 192);
      if (this.cachedPage != this.currPage) {
         ITextProperties itextproperties = this.bookInfo.func_238806_b_(this.currPage);
         this.cachedPageLines = this.field_230712_o_.func_238425_b_(itextproperties, 114);
         this.field_243344_s = new TranslationTextComponent("book.pageIndicator", this.currPage + 1, Math.max(this.getPageCount(), 1));
      }

      this.cachedPage = this.currPage;
      int i1 = this.field_230712_o_.func_238414_a_(this.field_243344_s);
      this.field_230712_o_.func_243248_b(p_230430_1_, this.field_243344_s, (float)(i - i1 + 192 - 44), 18.0F, 0);
      int k = Math.min(128 / 9, this.cachedPageLines.size());

      for(int l = 0; l < k; ++l) {
         IReorderingProcessor ireorderingprocessor = this.cachedPageLines.get(l);
         this.field_230712_o_.func_238422_b_(p_230430_1_, ireorderingprocessor, (float)(i + 36), (float)(32 + l * 9), 0);
      }

      Style style = this.func_238805_a_((double)p_230430_2_, (double)p_230430_3_);
      if (style != null) {
         this.func_238653_a_(p_230430_1_, style, p_230430_2_, p_230430_3_);
      }

      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      if (p_231044_5_ == 0) {
         Style style = this.func_238805_a_(p_231044_1_, p_231044_3_);
         if (style != null && this.func_230455_a_(style)) {
            return true;
         }
      }

      return super.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_);
   }

   public boolean func_230455_a_(Style p_230455_1_) {
      ClickEvent clickevent = p_230455_1_.getClickEvent();
      if (clickevent == null) {
         return false;
      } else if (clickevent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
         String s = clickevent.getValue();

         try {
            int i = Integer.parseInt(s) - 1;
            return this.showPage2(i);
         } catch (Exception exception) {
            return false;
         }
      } else {
         boolean flag = super.func_230455_a_(p_230455_1_);
         if (flag && clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
            this.field_230706_i_.displayGuiScreen((Screen)null);
         }

         return flag;
      }
   }

   @Nullable
   public Style func_238805_a_(double p_238805_1_, double p_238805_3_) {
      if (this.cachedPageLines.isEmpty()) {
         return null;
      } else {
         int i = MathHelper.floor(p_238805_1_ - (double)((this.field_230708_k_ - 192) / 2) - 36.0D);
         int j = MathHelper.floor(p_238805_3_ - 2.0D - 30.0D);
         if (i >= 0 && j >= 0) {
            int k = Math.min(128 / 9, this.cachedPageLines.size());
            if (i <= 114 && j < 9 * k + k) {
               int l = j / 9;
               if (l >= 0 && l < this.cachedPageLines.size()) {
                  IReorderingProcessor ireorderingprocessor = this.cachedPageLines.get(l);
                  return this.field_230706_i_.fontRenderer.func_238420_b_().func_243239_a(ireorderingprocessor, i);
               } else {
                  return null;
               }
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   public static List<String> nbtPagesToStrings(CompoundNBT p_214157_0_) {
      ListNBT listnbt = p_214157_0_.getList("pages", 8).copy();
      Builder<String> builder = ImmutableList.builder();

      for(int i = 0; i < listnbt.size(); ++i) {
         builder.add(listnbt.getString(i));
      }

      return builder.build();
   }

   @OnlyIn(Dist.CLIENT)
   public interface IBookInfo {
      /**
       * Returns the size of the book
       */
      int getPageCount();

      ITextProperties func_230456_a_(int p_230456_1_);

      default ITextProperties func_238806_b_(int p_238806_1_) {
         return p_238806_1_ >= 0 && p_238806_1_ < this.getPageCount() ? this.func_230456_a_(p_238806_1_) : ITextProperties.field_240651_c_;
      }

      static ReadBookScreen.IBookInfo func_216917_a(ItemStack p_216917_0_) {
         Item item = p_216917_0_.getItem();
         if (item == Items.WRITTEN_BOOK) {
            return new ReadBookScreen.WrittenBookInfo(p_216917_0_);
         } else {
            return (ReadBookScreen.IBookInfo)(item == Items.WRITABLE_BOOK ? new ReadBookScreen.UnwrittenBookInfo(p_216917_0_) : ReadBookScreen.EMPTY_BOOK);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class UnwrittenBookInfo implements ReadBookScreen.IBookInfo {
      private final List<String> pages;

      public UnwrittenBookInfo(ItemStack p_i50617_1_) {
         this.pages = func_216919_b(p_i50617_1_);
      }

      private static List<String> func_216919_b(ItemStack p_216919_0_) {
         CompoundNBT compoundnbt = p_216919_0_.getTag();
         return (List<String>)(compoundnbt != null ? ReadBookScreen.nbtPagesToStrings(compoundnbt) : ImmutableList.of());
      }

      /**
       * Returns the size of the book
       */
      public int getPageCount() {
         return this.pages.size();
      }

      public ITextProperties func_230456_a_(int p_230456_1_) {
         return ITextProperties.func_240652_a_(this.pages.get(p_230456_1_));
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class WrittenBookInfo implements ReadBookScreen.IBookInfo {
      private final List<String> pages;

      public WrittenBookInfo(ItemStack p_i50616_1_) {
         this.pages = func_216921_b(p_i50616_1_);
      }

      private static List<String> func_216921_b(ItemStack stack) {
         CompoundNBT compoundnbt = stack.getTag();
         return (List<String>)(compoundnbt != null && WrittenBookItem.validBookTagContents(compoundnbt) ? ReadBookScreen.nbtPagesToStrings(compoundnbt) : ImmutableList.of(ITextComponent.Serializer.toJson((new TranslationTextComponent("book.invalid.tag")).func_240699_a_(TextFormatting.DARK_RED))));
      }

      /**
       * Returns the size of the book
       */
      public int getPageCount() {
         return this.pages.size();
      }

      public ITextProperties func_230456_a_(int p_230456_1_) {
         String s = this.pages.get(p_230456_1_);

         try {
            ITextProperties itextproperties = ITextComponent.Serializer.func_240643_a_(s);
            if (itextproperties != null) {
               return itextproperties;
            }
         } catch (Exception exception) {
         }

         return ITextProperties.func_240652_a_(s);
      }
   }
}