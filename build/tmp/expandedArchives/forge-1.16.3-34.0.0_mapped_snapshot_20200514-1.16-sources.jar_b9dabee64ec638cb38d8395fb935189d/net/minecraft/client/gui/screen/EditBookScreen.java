package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.Nullable;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.fonts.TextInputUtil;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ChangePageButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.play.client.CEditBookPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.text.CharacterManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

@OnlyIn(Dist.CLIENT)
public class EditBookScreen extends Screen {
   private static final ITextComponent field_243340_a = new TranslationTextComponent("book.editTitle");
   private static final ITextComponent field_243341_b = new TranslationTextComponent("book.finalizeWarning");
   private static final IReorderingProcessor field_243342_c = IReorderingProcessor.func_242239_a("_", Style.field_240709_b_.func_240712_a_(TextFormatting.BLACK));
   private static final IReorderingProcessor field_243343_p = IReorderingProcessor.func_242239_a("_", Style.field_240709_b_.func_240712_a_(TextFormatting.GRAY));
   private final PlayerEntity editingPlayer;
   private final ItemStack book;
   /** Whether the book's title or contents has been modified since being opened */
   private boolean bookIsModified;
   /** Determines if the signing screen is open */
   private boolean bookGettingSigned;
   /** Update ticks since the gui was opened */
   private int updateCount;
   private int currPage;
   private final List<String> bookPages = Lists.newArrayList();
   private String bookTitle = "";
   private final TextInputUtil field_238748_u_ = new TextInputUtil(this::getCurrPageText, this::func_214217_j, this::func_238773_g_, this::func_238760_a_, (p_238774_1_) -> {
      return p_238774_1_.length() < 1024 && this.field_230712_o_.getWordWrappedHeight(p_238774_1_, 114) <= 128;
   });
   private final TextInputUtil field_238749_v_ = new TextInputUtil(() -> {
      return this.bookTitle;
   }, (p_238772_1_) -> {
      this.bookTitle = p_238772_1_;
   }, this::func_238773_g_, this::func_238760_a_, (p_238771_0_) -> {
      return p_238771_0_.length() < 16;
   });
   /** In milliseconds */
   private long lastClickTime;
   private int cachedPage = -1;
   private ChangePageButton buttonNextPage;
   private ChangePageButton buttonPreviousPage;
   private Button buttonDone;
   private Button buttonSign;
   private Button buttonFinalize;
   private Button buttonCancel;
   private final Hand hand;
   @Nullable
   private EditBookScreen.BookPage field_238747_F_ = EditBookScreen.BookPage.field_238779_a_;
   private ITextComponent field_243338_K = StringTextComponent.field_240750_d_;
   private final ITextComponent field_243339_L;

   public EditBookScreen(PlayerEntity player, ItemStack bookIn, Hand handIn) {
      super(NarratorChatListener.EMPTY);
      this.editingPlayer = player;
      this.book = bookIn;
      this.hand = handIn;
      CompoundNBT compoundnbt = bookIn.getTag();
      if (compoundnbt != null) {
         ListNBT listnbt = compoundnbt.getList("pages", 8).copy();

         for(int i = 0; i < listnbt.size(); ++i) {
            this.bookPages.add(listnbt.getString(i));
         }
      }

      if (this.bookPages.isEmpty()) {
         this.bookPages.add("");
      }

      this.field_243339_L = (new TranslationTextComponent("book.byAuthor", player.getName())).func_240699_a_(TextFormatting.DARK_GRAY);
   }

   private void func_238760_a_(String p_238760_1_) {
      if (this.field_230706_i_ != null) {
         TextInputUtil.func_238571_a_(this.field_230706_i_, p_238760_1_);
      }

   }

   private String func_238773_g_() {
      return this.field_230706_i_ != null ? TextInputUtil.func_238576_b_(this.field_230706_i_) : "";
   }

   /**
    * Returns the number of pages in the book
    */
   private int getPageCount() {
      return this.bookPages.size();
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      ++this.updateCount;
   }

   protected void func_231160_c_() {
      this.func_238751_C_();
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      this.buttonSign = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, 196, 98, 20, new TranslationTextComponent("book.signButton"), (p_214201_1_) -> {
         this.bookGettingSigned = true;
         this.updateButtons();
      }));
      this.buttonDone = this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 2, 196, 98, 20, DialogTexts.field_240632_c_, (p_214204_1_) -> {
         this.field_230706_i_.displayGuiScreen((Screen)null);
         this.sendBookToServer(false);
      }));
      this.buttonFinalize = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, 196, 98, 20, new TranslationTextComponent("book.finalizeButton"), (p_214195_1_) -> {
         if (this.bookGettingSigned) {
            this.sendBookToServer(true);
            this.field_230706_i_.displayGuiScreen((Screen)null);
         }

      }));
      this.buttonCancel = this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 2, 196, 98, 20, DialogTexts.field_240633_d_, (p_214212_1_) -> {
         if (this.bookGettingSigned) {
            this.bookGettingSigned = false;
         }

         this.updateButtons();
      }));
      int i = (this.field_230708_k_ - 192) / 2;
      int j = 2;
      this.buttonNextPage = this.func_230480_a_(new ChangePageButton(i + 116, 159, true, (p_214208_1_) -> {
         this.nextPage();
      }, true));
      this.buttonPreviousPage = this.func_230480_a_(new ChangePageButton(i + 43, 159, false, (p_214205_1_) -> {
         this.previousPage();
      }, true));
      this.updateButtons();
   }

   /**
    * Displays the previous page
    */
   private void previousPage() {
      if (this.currPage > 0) {
         --this.currPage;
      }

      this.updateButtons();
      this.func_238752_D_();
   }

   /**
    * Displays the next page (creating it if necessary)
    */
   private void nextPage() {
      if (this.currPage < this.getPageCount() - 1) {
         ++this.currPage;
      } else {
         this.addNewPage();
         if (this.currPage < this.getPageCount() - 1) {
            ++this.currPage;
         }
      }

      this.updateButtons();
      this.func_238752_D_();
   }

   public void func_231164_f_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(false);
   }

   /**
    * Sets visibility for book buttons
    */
   private void updateButtons() {
      this.buttonPreviousPage.field_230694_p_ = !this.bookGettingSigned && this.currPage > 0;
      this.buttonNextPage.field_230694_p_ = !this.bookGettingSigned;
      this.buttonDone.field_230694_p_ = !this.bookGettingSigned;
      this.buttonSign.field_230694_p_ = !this.bookGettingSigned;
      this.buttonCancel.field_230694_p_ = this.bookGettingSigned;
      this.buttonFinalize.field_230694_p_ = this.bookGettingSigned;
      this.buttonFinalize.field_230693_o_ = !this.bookTitle.trim().isEmpty();
   }

   private void trimEmptyPages() {
      ListIterator<String> listiterator = this.bookPages.listIterator(this.bookPages.size());

      while(listiterator.hasPrevious() && listiterator.previous().isEmpty()) {
         listiterator.remove();
      }

   }

   private void sendBookToServer(boolean publish) {
      if (this.bookIsModified) {
         this.trimEmptyPages();
         ListNBT listnbt = new ListNBT();
         this.bookPages.stream().map(StringNBT::valueOf).forEach(listnbt::add);
         if (!this.bookPages.isEmpty()) {
            this.book.setTagInfo("pages", listnbt);
         }

         if (publish) {
            this.book.setTagInfo("author", StringNBT.valueOf(this.editingPlayer.getGameProfile().getName()));
            this.book.setTagInfo("title", StringNBT.valueOf(this.bookTitle.trim()));
         }

         this.field_230706_i_.getConnection().sendPacket(new CEditBookPacket(this.book, publish, this.hand));
      }
   }

   /**
    * Adds a new page to the book (capped at 100 pages)
    */
   private void addNewPage() {
      if (this.getPageCount() < 100) {
         this.bookPages.add("");
         this.bookIsModified = true;
      }
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_)) {
         return true;
      } else if (this.bookGettingSigned) {
         return this.keyPressedInTitle(p_231046_1_, p_231046_2_, p_231046_3_);
      } else {
         boolean flag = this.keyPressedInBook(p_231046_1_, p_231046_2_, p_231046_3_);
         if (flag) {
            this.func_238751_C_();
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean func_231042_a_(char p_231042_1_, int p_231042_2_) {
      if (super.func_231042_a_(p_231042_1_, p_231042_2_)) {
         return true;
      } else if (this.bookGettingSigned) {
         boolean flag = this.field_238749_v_.func_216894_a(p_231042_1_);
         if (flag) {
            this.updateButtons();
            this.bookIsModified = true;
            return true;
         } else {
            return false;
         }
      } else if (SharedConstants.isAllowedCharacter(p_231042_1_)) {
         this.field_238748_u_.func_216892_a(Character.toString(p_231042_1_));
         this.func_238751_C_();
         return true;
      } else {
         return false;
      }
   }

   /**
    * Handles keypresses, clipboard functions, and page turning
    */
   private boolean keyPressedInBook(int keyCode, int scanCode, int modifiers) {
      if (Screen.func_231170_j_(keyCode)) {
         this.field_238748_u_.func_238585_d_();
         return true;
      } else if (Screen.func_231169_i_(keyCode)) {
         this.field_238748_u_.func_238580_c_();
         return true;
      } else if (Screen.func_231168_h_(keyCode)) {
         this.field_238748_u_.func_238574_b_();
         return true;
      } else if (Screen.func_231166_g_(keyCode)) {
         this.field_238748_u_.func_238567_a_();
         return true;
      } else {
         switch(keyCode) {
         case 257:
         case 335:
            this.field_238748_u_.func_216892_a("\n");
            return true;
         case 259:
            this.field_238748_u_.func_238586_d_(-1);
            return true;
         case 261:
            this.field_238748_u_.func_238586_d_(1);
            return true;
         case 262:
            this.field_238748_u_.func_238569_a_(1, Screen.func_231173_s_());
            return true;
         case 263:
            this.field_238748_u_.func_238569_a_(-1, Screen.func_231173_s_());
            return true;
         case 264:
            this.func_238776_x_();
            return true;
         case 265:
            this.func_238775_w_();
            return true;
         case 266:
            this.buttonPreviousPage.func_230930_b_();
            return true;
         case 267:
            this.buttonNextPage.func_230930_b_();
            return true;
         case 268:
            this.func_238777_y_();
            return true;
         case 269:
            this.func_238778_z_();
            return true;
         default:
            return false;
         }
      }
   }

   private void func_238775_w_() {
      this.func_238755_a_(-1);
   }

   private void func_238776_x_() {
      this.func_238755_a_(1);
   }

   private void func_238755_a_(int p_238755_1_) {
      int i = this.field_238748_u_.func_216896_c();
      int j = this.func_238750_B_().func_238788_a_(i, p_238755_1_);
      this.field_238748_u_.func_238581_c_(j, Screen.func_231173_s_());
   }

   private void func_238777_y_() {
      int i = this.field_238748_u_.func_216896_c();
      int j = this.func_238750_B_().func_238787_a_(i);
      this.field_238748_u_.func_238581_c_(j, Screen.func_231173_s_());
   }

   private void func_238778_z_() {
      EditBookScreen.BookPage editbookscreen$bookpage = this.func_238750_B_();
      int i = this.field_238748_u_.func_216896_c();
      int j = editbookscreen$bookpage.func_238791_b_(i);
      this.field_238748_u_.func_238581_c_(j, Screen.func_231173_s_());
   }

   /**
    * Handles special keys pressed while editing the book's title
    */
   private boolean keyPressedInTitle(int keyCode, int scanCode, int modifiers) {
      switch(keyCode) {
      case 257:
      case 335:
         if (!this.bookTitle.isEmpty()) {
            this.sendBookToServer(true);
            this.field_230706_i_.displayGuiScreen((Screen)null);
         }

         return true;
      case 259:
         this.field_238749_v_.func_238586_d_(-1);
         this.updateButtons();
         this.bookIsModified = true;
         return true;
      default:
         return false;
      }
   }

   /**
    * Returns the contents of the current page as a string (or an empty string if the currPage isn't a valid page index)
    */
   private String getCurrPageText() {
      return this.currPage >= 0 && this.currPage < this.bookPages.size() ? this.bookPages.get(this.currPage) : "";
   }

   private void func_214217_j(String p_214217_1_) {
      if (this.currPage >= 0 && this.currPage < this.bookPages.size()) {
         this.bookPages.set(this.currPage, p_214217_1_);
         this.bookIsModified = true;
         this.func_238751_C_();
      }

   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      this.func_231035_a_((IGuiEventListener)null);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_230706_i_.getTextureManager().bindTexture(ReadBookScreen.BOOK_TEXTURES);
      int i = (this.field_230708_k_ - 192) / 2;
      int j = 2;
      this.func_238474_b_(p_230430_1_, i, 2, 0, 0, 192, 192);
      if (this.bookGettingSigned) {
         boolean flag = this.updateCount / 6 % 2 == 0;
         IReorderingProcessor ireorderingprocessor = IReorderingProcessor.func_242234_a(IReorderingProcessor.func_242239_a(this.bookTitle, Style.field_240709_b_), flag ? field_243342_c : field_243343_p);
         int k = this.field_230712_o_.func_238414_a_(field_243340_a);
         this.field_230712_o_.func_243248_b(p_230430_1_, field_243340_a, (float)(i + 36 + (114 - k) / 2), 34.0F, 0);
         int l = this.field_230712_o_.func_243245_a(ireorderingprocessor);
         this.field_230712_o_.func_238422_b_(p_230430_1_, ireorderingprocessor, (float)(i + 36 + (114 - l) / 2), 50.0F, 0);
         int i1 = this.field_230712_o_.func_238414_a_(this.field_243339_L);
         this.field_230712_o_.func_243248_b(p_230430_1_, this.field_243339_L, (float)(i + 36 + (114 - i1) / 2), 60.0F, 0);
         this.field_230712_o_.func_238418_a_(field_243341_b, i + 36, 82, 114, 0);
      } else {
         int j1 = this.field_230712_o_.func_238414_a_(this.field_243338_K);
         this.field_230712_o_.func_243248_b(p_230430_1_, this.field_243338_K, (float)(i - j1 + 192 - 44), 18.0F, 0);
         EditBookScreen.BookPage editbookscreen$bookpage = this.func_238750_B_();

         for(EditBookScreen.BookLine editbookscreen$bookline : editbookscreen$bookpage.field_238784_f_) {
            this.field_230712_o_.func_243248_b(p_230430_1_, editbookscreen$bookline.field_238797_c_, (float)editbookscreen$bookline.field_238798_d_, (float)editbookscreen$bookline.field_238799_e_, -16777216);
         }

         this.func_238764_a_(editbookscreen$bookpage.field_238785_g_);
         this.func_238756_a_(p_230430_1_, editbookscreen$bookpage.field_238781_c_, editbookscreen$bookpage.field_238782_d_);
      }

      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   private void func_238756_a_(MatrixStack p_238756_1_, EditBookScreen.Point p_238756_2_, boolean p_238756_3_) {
      if (this.updateCount / 6 % 2 == 0) {
         p_238756_2_ = this.func_238767_b_(p_238756_2_);
         if (!p_238756_3_) {
            AbstractGui.func_238467_a_(p_238756_1_, p_238756_2_.x, p_238756_2_.y - 1, p_238756_2_.x + 1, p_238756_2_.y + 9, -16777216);
         } else {
            this.field_230712_o_.func_238421_b_(p_238756_1_, "_", (float)p_238756_2_.x, (float)p_238756_2_.y, 0);
         }
      }

   }

   private void func_238764_a_(Rectangle2d[] p_238764_1_) {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      RenderSystem.color4f(0.0F, 0.0F, 255.0F, 255.0F);
      RenderSystem.disableTexture();
      RenderSystem.enableColorLogicOp();
      RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION);

      for(Rectangle2d rectangle2d : p_238764_1_) {
         int i = rectangle2d.getX();
         int j = rectangle2d.getY();
         int k = i + rectangle2d.getWidth();
         int l = j + rectangle2d.getHeight();
         bufferbuilder.pos((double)i, (double)l, 0.0D).endVertex();
         bufferbuilder.pos((double)k, (double)l, 0.0D).endVertex();
         bufferbuilder.pos((double)k, (double)j, 0.0D).endVertex();
         bufferbuilder.pos((double)i, (double)j, 0.0D).endVertex();
      }

      tessellator.draw();
      RenderSystem.disableColorLogicOp();
      RenderSystem.enableTexture();
   }

   private EditBookScreen.Point func_238758_a_(EditBookScreen.Point p_238758_1_) {
      return new EditBookScreen.Point(p_238758_1_.x - (this.field_230708_k_ - 192) / 2 - 36, p_238758_1_.y - 32);
   }

   private EditBookScreen.Point func_238767_b_(EditBookScreen.Point p_238767_1_) {
      return new EditBookScreen.Point(p_238767_1_.x + (this.field_230708_k_ - 192) / 2 + 36, p_238767_1_.y + 32);
   }

   public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      if (super.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_)) {
         return true;
      } else {
         if (p_231044_5_ == 0) {
            long i = Util.milliTime();
            EditBookScreen.BookPage editbookscreen$bookpage = this.func_238750_B_();
            int j = editbookscreen$bookpage.func_238789_a_(this.field_230712_o_, this.func_238758_a_(new EditBookScreen.Point((int)p_231044_1_, (int)p_231044_3_)));
            if (j >= 0) {
               if (j == this.cachedPage && i - this.lastClickTime < 250L) {
                  if (!this.field_238748_u_.func_238590_i_()) {
                     this.func_238765_b_(j);
                  } else {
                     this.field_238748_u_.func_238585_d_();
                  }
               } else {
                  this.field_238748_u_.func_238581_c_(j, Screen.func_231173_s_());
               }

               this.func_238751_C_();
            }

            this.cachedPage = j;
            this.lastClickTime = i;
         }

         return true;
      }
   }

   private void func_238765_b_(int p_238765_1_) {
      String s = this.getCurrPageText();
      this.field_238748_u_.func_238568_a_(CharacterManager.func_238351_a_(s, -1, p_238765_1_, false), CharacterManager.func_238351_a_(s, 1, p_238765_1_, false));
   }

   public boolean func_231045_a_(double p_231045_1_, double p_231045_3_, int p_231045_5_, double p_231045_6_, double p_231045_8_) {
      if (super.func_231045_a_(p_231045_1_, p_231045_3_, p_231045_5_, p_231045_6_, p_231045_8_)) {
         return true;
      } else {
         if (p_231045_5_ == 0) {
            EditBookScreen.BookPage editbookscreen$bookpage = this.func_238750_B_();
            int i = editbookscreen$bookpage.func_238789_a_(this.field_230712_o_, this.func_238758_a_(new EditBookScreen.Point((int)p_231045_1_, (int)p_231045_3_)));
            this.field_238748_u_.func_238581_c_(i, true);
            this.func_238751_C_();
         }

         return true;
      }
   }

   private EditBookScreen.BookPage func_238750_B_() {
      if (this.field_238747_F_ == null) {
         this.field_238747_F_ = this.func_238753_E_();
         this.field_243338_K = new TranslationTextComponent("book.pageIndicator", this.currPage + 1, this.getPageCount());
      }

      return this.field_238747_F_;
   }

   private void func_238751_C_() {
      this.field_238747_F_ = null;
   }

   private void func_238752_D_() {
      this.field_238748_u_.func_238588_f_();
      this.func_238751_C_();
   }

   private EditBookScreen.BookPage func_238753_E_() {
      String s = this.getCurrPageText();
      if (s.isEmpty()) {
         return EditBookScreen.BookPage.field_238779_a_;
      } else {
         int i = this.field_238748_u_.func_216896_c();
         int j = this.field_238748_u_.func_216898_d();
         IntList intlist = new IntArrayList();
         List<EditBookScreen.BookLine> list = Lists.newArrayList();
         MutableInt mutableint = new MutableInt();
         MutableBoolean mutableboolean = new MutableBoolean();
         CharacterManager charactermanager = this.field_230712_o_.func_238420_b_();
         charactermanager.func_238353_a_(s, 114, Style.field_240709_b_, true, (p_238762_6_, p_238762_7_, p_238762_8_) -> {
            int k3 = mutableint.getAndIncrement();
            String s2 = s.substring(p_238762_7_, p_238762_8_);
            mutableboolean.setValue(s2.endsWith("\n"));
            String s3 = StringUtils.stripEnd(s2, " \n");
            int l3 = k3 * 9;
            EditBookScreen.Point editbookscreen$point1 = this.func_238767_b_(new EditBookScreen.Point(0, l3));
            intlist.add(p_238762_7_);
            list.add(new EditBookScreen.BookLine(p_238762_6_, s3, editbookscreen$point1.x, editbookscreen$point1.y));
         });
         int[] aint = intlist.toIntArray();
         boolean flag = i == s.length();
         EditBookScreen.Point editbookscreen$point;
         if (flag && mutableboolean.isTrue()) {
            editbookscreen$point = new EditBookScreen.Point(0, list.size() * 9);
         } else {
            int k = func_238768_b_(aint, i);
            int l = this.field_230712_o_.getStringWidth(s.substring(aint[k], i));
            editbookscreen$point = new EditBookScreen.Point(l, k * 9);
         }

         List<Rectangle2d> list1 = Lists.newArrayList();
         if (i != j) {
            int l2 = Math.min(i, j);
            int i1 = Math.max(i, j);
            int j1 = func_238768_b_(aint, l2);
            int k1 = func_238768_b_(aint, i1);
            if (j1 == k1) {
               int l1 = j1 * 9;
               int i2 = aint[j1];
               list1.add(this.func_238761_a_(s, charactermanager, l2, i1, l1, i2));
            } else {
               int i3 = j1 + 1 > aint.length ? s.length() : aint[j1 + 1];
               list1.add(this.func_238761_a_(s, charactermanager, l2, i3, j1 * 9, aint[j1]));

               for(int j3 = j1 + 1; j3 < k1; ++j3) {
                  int j2 = j3 * 9;
                  String s1 = s.substring(aint[j3], aint[j3 + 1]);
                  int k2 = (int)charactermanager.func_238350_a_(s1);
                  list1.add(this.func_238759_a_(new EditBookScreen.Point(0, j2), new EditBookScreen.Point(k2, j2 + 9)));
               }

               list1.add(this.func_238761_a_(s, charactermanager, aint[k1], i1, k1 * 9, aint[k1]));
            }
         }

         return new EditBookScreen.BookPage(s, editbookscreen$point, flag, aint, list.toArray(new EditBookScreen.BookLine[0]), list1.toArray(new Rectangle2d[0]));
      }
   }

   private static int func_238768_b_(int[] p_238768_0_, int p_238768_1_) {
      int i = Arrays.binarySearch(p_238768_0_, p_238768_1_);
      return i < 0 ? -(i + 2) : i;
   }

   private Rectangle2d func_238761_a_(String p_238761_1_, CharacterManager p_238761_2_, int p_238761_3_, int p_238761_4_, int p_238761_5_, int p_238761_6_) {
      String s = p_238761_1_.substring(p_238761_6_, p_238761_3_);
      String s1 = p_238761_1_.substring(p_238761_6_, p_238761_4_);
      EditBookScreen.Point editbookscreen$point = new EditBookScreen.Point((int)p_238761_2_.func_238350_a_(s), p_238761_5_);
      EditBookScreen.Point editbookscreen$point1 = new EditBookScreen.Point((int)p_238761_2_.func_238350_a_(s1), p_238761_5_ + 9);
      return this.func_238759_a_(editbookscreen$point, editbookscreen$point1);
   }

   private Rectangle2d func_238759_a_(EditBookScreen.Point p_238759_1_, EditBookScreen.Point p_238759_2_) {
      EditBookScreen.Point editbookscreen$point = this.func_238767_b_(p_238759_1_);
      EditBookScreen.Point editbookscreen$point1 = this.func_238767_b_(p_238759_2_);
      int i = Math.min(editbookscreen$point.x, editbookscreen$point1.x);
      int j = Math.max(editbookscreen$point.x, editbookscreen$point1.x);
      int k = Math.min(editbookscreen$point.y, editbookscreen$point1.y);
      int l = Math.max(editbookscreen$point.y, editbookscreen$point1.y);
      return new Rectangle2d(i, k, j - i, l - k);
   }

   @OnlyIn(Dist.CLIENT)
   static class BookLine {
      private final Style field_238795_a_;
      private final String field_238796_b_;
      private final ITextComponent field_238797_c_;
      private final int field_238798_d_;
      private final int field_238799_e_;

      public BookLine(Style p_i232289_1_, String p_i232289_2_, int p_i232289_3_, int p_i232289_4_) {
         this.field_238795_a_ = p_i232289_1_;
         this.field_238796_b_ = p_i232289_2_;
         this.field_238798_d_ = p_i232289_3_;
         this.field_238799_e_ = p_i232289_4_;
         this.field_238797_c_ = (new StringTextComponent(p_i232289_2_)).func_230530_a_(p_i232289_1_);
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class BookPage {
      private static final EditBookScreen.BookPage field_238779_a_ = new EditBookScreen.BookPage("", new EditBookScreen.Point(0, 0), true, new int[]{0}, new EditBookScreen.BookLine[]{new EditBookScreen.BookLine(Style.field_240709_b_, "", 0, 0)}, new Rectangle2d[0]);
      private final String field_238780_b_;
      private final EditBookScreen.Point field_238781_c_;
      private final boolean field_238782_d_;
      private final int[] field_238783_e_;
      private final EditBookScreen.BookLine[] field_238784_f_;
      private final Rectangle2d[] field_238785_g_;

      public BookPage(String p_i232288_1_, EditBookScreen.Point p_i232288_2_, boolean p_i232288_3_, int[] p_i232288_4_, EditBookScreen.BookLine[] p_i232288_5_, Rectangle2d[] p_i232288_6_) {
         this.field_238780_b_ = p_i232288_1_;
         this.field_238781_c_ = p_i232288_2_;
         this.field_238782_d_ = p_i232288_3_;
         this.field_238783_e_ = p_i232288_4_;
         this.field_238784_f_ = p_i232288_5_;
         this.field_238785_g_ = p_i232288_6_;
      }

      public int func_238789_a_(FontRenderer p_238789_1_, EditBookScreen.Point p_238789_2_) {
         int i = p_238789_2_.y / 9;
         if (i < 0) {
            return 0;
         } else if (i >= this.field_238784_f_.length) {
            return this.field_238780_b_.length();
         } else {
            EditBookScreen.BookLine editbookscreen$bookline = this.field_238784_f_[i];
            return this.field_238783_e_[i] + p_238789_1_.func_238420_b_().func_238352_a_(editbookscreen$bookline.field_238796_b_, p_238789_2_.x, editbookscreen$bookline.field_238795_a_);
         }
      }

      public int func_238788_a_(int p_238788_1_, int p_238788_2_) {
         int i = EditBookScreen.func_238768_b_(this.field_238783_e_, p_238788_1_);
         int j = i + p_238788_2_;
         int k;
         if (0 <= j && j < this.field_238783_e_.length) {
            int l = p_238788_1_ - this.field_238783_e_[i];
            int i1 = this.field_238784_f_[j].field_238796_b_.length();
            k = this.field_238783_e_[j] + Math.min(l, i1);
         } else {
            k = p_238788_1_;
         }

         return k;
      }

      public int func_238787_a_(int p_238787_1_) {
         int i = EditBookScreen.func_238768_b_(this.field_238783_e_, p_238787_1_);
         return this.field_238783_e_[i];
      }

      public int func_238791_b_(int p_238791_1_) {
         int i = EditBookScreen.func_238768_b_(this.field_238783_e_, p_238791_1_);
         return this.field_238783_e_[i] + this.field_238784_f_[i].field_238796_b_.length();
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class Point {
      public final int x;
      public final int y;

      Point(int p_i232290_1_, int p_i232290_2_) {
         this.x = p_i232290_1_;
         this.y = p_i232290_2_;
      }
   }
}