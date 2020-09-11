package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import javax.annotation.Nullable;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LanguageScreen extends SettingsScreen {
   private static final ITextComponent field_243292_c = (new StringTextComponent("(")).func_230529_a_(new TranslationTextComponent("options.languageWarning")).func_240702_b_(")");
   /** The List GuiSlot object reference. */
   private LanguageScreen.List list;
   /** Reference to the LanguageManager object. */
   private final LanguageManager languageManager;
   private OptionButton field_211832_i;
   /** The button to confirm the current settings. */
   private Button confirmSettingsBtn;

   public LanguageScreen(Screen screen, GameSettings gameSettingsObj, LanguageManager manager) {
      super(screen, gameSettingsObj, new TranslationTextComponent("options.language"));
      this.languageManager = manager;
   }

   protected void func_231160_c_() {
      this.list = new LanguageScreen.List(this.field_230706_i_);
      this.field_230705_e_.add(this.list);
      this.field_211832_i = this.func_230480_a_(new OptionButton(this.field_230708_k_ / 2 - 155, this.field_230709_l_ - 38, 150, 20, AbstractOption.FORCE_UNICODE_FONT, AbstractOption.FORCE_UNICODE_FONT.func_238152_c_(this.gameSettings), (p_213037_1_) -> {
         AbstractOption.FORCE_UNICODE_FONT.nextValue(this.gameSettings);
         this.gameSettings.saveOptions();
         p_213037_1_.func_238482_a_(AbstractOption.FORCE_UNICODE_FONT.func_238152_c_(this.gameSettings));
         this.field_230706_i_.updateWindowSize();
      }));
      this.confirmSettingsBtn = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155 + 160, this.field_230709_l_ - 38, 150, 20, DialogTexts.field_240632_c_, (p_213036_1_) -> {
         LanguageScreen.List.LanguageEntry languagescreen$list$languageentry = this.list.func_230958_g_();
         if (languagescreen$list$languageentry != null && !languagescreen$list$languageentry.field_214398_b.getCode().equals(this.languageManager.getCurrentLanguage().getCode())) {
            this.languageManager.setCurrentLanguage(languagescreen$list$languageentry.field_214398_b);
            this.gameSettings.language = languagescreen$list$languageentry.field_214398_b.getCode();
            net.minecraftforge.client.ForgeHooksClient.refreshResources(this.field_230706_i_, net.minecraftforge.resource.VanillaResourceType.LANGUAGES);
            this.confirmSettingsBtn.func_238482_a_(DialogTexts.field_240632_c_);
            this.field_211832_i.func_238482_a_(AbstractOption.FORCE_UNICODE_FONT.func_238152_c_(this.gameSettings));
            this.gameSettings.saveOptions();
         }

         this.field_230706_i_.displayGuiScreen(this.parentScreen);
      }));
      super.func_231160_c_();
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.list.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 16, 16777215);
      func_238472_a_(p_230430_1_, this.field_230712_o_, field_243292_c, this.field_230708_k_ / 2, this.field_230709_l_ - 56, 8421504);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   @OnlyIn(Dist.CLIENT)
   class List extends ExtendedList<LanguageScreen.List.LanguageEntry> {
      public List(Minecraft mcIn) {
         super(mcIn, LanguageScreen.this.field_230708_k_, LanguageScreen.this.field_230709_l_, 32, LanguageScreen.this.field_230709_l_ - 65 + 4, 18);

         for(Language language : LanguageScreen.this.languageManager.getLanguages()) {
            LanguageScreen.List.LanguageEntry languagescreen$list$languageentry = new LanguageScreen.List.LanguageEntry(language);
            this.func_230513_b_(languagescreen$list$languageentry);
            if (LanguageScreen.this.languageManager.getCurrentLanguage().getCode().equals(language.getCode())) {
               this.func_241215_a_(languagescreen$list$languageentry);
            }
         }

         if (this.func_230958_g_() != null) {
            this.func_230951_c_(this.func_230958_g_());
         }

      }

      protected int func_230952_d_() {
         return super.func_230952_d_() + 20;
      }

      public int func_230949_c_() {
         return super.func_230949_c_() + 50;
      }

      public void func_241215_a_(@Nullable LanguageScreen.List.LanguageEntry p_241215_1_) {
         super.func_241215_a_(p_241215_1_);
         if (p_241215_1_ != null) {
            NarratorChatListener.INSTANCE.say((new TranslationTextComponent("narrator.select", p_241215_1_.field_214398_b)).getString());
         }

      }

      protected void func_230433_a_(MatrixStack p_230433_1_) {
         LanguageScreen.this.func_230446_a_(p_230433_1_);
      }

      protected boolean func_230971_aw__() {
         return LanguageScreen.this.func_241217_q_() == this;
      }

      @OnlyIn(Dist.CLIENT)
      public class LanguageEntry extends ExtendedList.AbstractListEntry<LanguageScreen.List.LanguageEntry> {
         private final Language field_214398_b;

         public LanguageEntry(Language p_i50494_2_) {
            this.field_214398_b = p_i50494_2_;
         }

         public void func_230432_a_(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
            String s = this.field_214398_b.toString();
            LanguageScreen.this.field_230712_o_.func_238406_a_(p_230432_1_, s, (float)(List.this.field_230670_d_ / 2 - LanguageScreen.this.field_230712_o_.getStringWidth(s) / 2), (float)(p_230432_3_ + 1), 16777215, true);
         }

         public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
            if (p_231044_5_ == 0) {
               this.func_214395_a();
               return true;
            } else {
               return false;
            }
         }

         private void func_214395_a() {
            List.this.func_241215_a_(this);
         }
      }
   }
}