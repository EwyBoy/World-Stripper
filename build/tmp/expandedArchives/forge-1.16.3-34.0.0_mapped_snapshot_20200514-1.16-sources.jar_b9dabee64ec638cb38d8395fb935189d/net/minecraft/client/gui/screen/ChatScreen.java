package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.CommandSuggestionHelper;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChatScreen extends Screen {
   private String historyBuffer = "";
   /**
    * keeps position of which chat message you will select when you press up, (does not increase for duplicated messages
    * sent immediately after each other)
    */
   private int sentHistoryCursor = -1;
   /** Chat entry field */
   protected TextFieldWidget inputField;
   /** is the text that appears when you press the chat key and the input box appears pre-filled */
   private String defaultInputFieldText = "";
   private CommandSuggestionHelper commandSuggestionHelper;

   public ChatScreen(String defaultText) {
      super(NarratorChatListener.EMPTY);
      this.defaultInputFieldText = defaultText;
   }

   protected void func_231160_c_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      this.sentHistoryCursor = this.field_230706_i_.ingameGUI.getChatGUI().getSentMessages().size();
      this.inputField = new TextFieldWidget(this.field_230712_o_, 4, this.field_230709_l_ - 12, this.field_230708_k_ - 4, 12, new TranslationTextComponent("chat.editBox")) {
         protected IFormattableTextComponent func_230442_c_() {
            return super.func_230442_c_().func_240702_b_(ChatScreen.this.commandSuggestionHelper.func_228129_c_());
         }
      };
      this.inputField.setMaxStringLength(256);
      this.inputField.setEnableBackgroundDrawing(false);
      this.inputField.setText(this.defaultInputFieldText);
      this.inputField.setResponder(this::func_212997_a);
      this.field_230705_e_.add(this.inputField);
      this.commandSuggestionHelper = new CommandSuggestionHelper(this.field_230706_i_, this, this.inputField, this.field_230712_o_, false, false, 1, 10, true, -805306368);
      this.commandSuggestionHelper.init();
      this.setFocusedDefault(this.inputField);
   }

   public void func_231152_a_(Minecraft p_231152_1_, int p_231152_2_, int p_231152_3_) {
      String s = this.inputField.getText();
      this.func_231158_b_(p_231152_1_, p_231152_2_, p_231152_3_);
      this.setChatLine(s);
      this.commandSuggestionHelper.init();
   }

   public void func_231164_f_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(false);
      this.field_230706_i_.ingameGUI.getChatGUI().resetScroll();
   }

   public void func_231023_e_() {
      this.inputField.tick();
   }

   private void func_212997_a(String p_212997_1_) {
      String s = this.inputField.getText();
      this.commandSuggestionHelper.func_228124_a_(!s.equals(this.defaultInputFieldText));
      this.commandSuggestionHelper.init();
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (this.commandSuggestionHelper.onKeyPressed(p_231046_1_, p_231046_2_, p_231046_3_)) {
         return true;
      } else if (super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_)) {
         return true;
      } else if (p_231046_1_ == 256) {
         this.field_230706_i_.displayGuiScreen((Screen)null);
         return true;
      } else if (p_231046_1_ != 257 && p_231046_1_ != 335) {
         if (p_231046_1_ == 265) {
            this.getSentHistory(-1);
            return true;
         } else if (p_231046_1_ == 264) {
            this.getSentHistory(1);
            return true;
         } else if (p_231046_1_ == 266) {
            this.field_230706_i_.ingameGUI.getChatGUI().addScrollPos((double)(this.field_230706_i_.ingameGUI.getChatGUI().getLineCount() - 1));
            return true;
         } else if (p_231046_1_ == 267) {
            this.field_230706_i_.ingameGUI.getChatGUI().addScrollPos((double)(-this.field_230706_i_.ingameGUI.getChatGUI().getLineCount() + 1));
            return true;
         } else {
            return false;
         }
      } else {
         String s = this.inputField.getText().trim();
         if (!s.isEmpty()) {
            this.func_231161_c_(s);
         }

         this.field_230706_i_.displayGuiScreen((Screen)null);
         return true;
      }
   }

   public boolean func_231043_a_(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
      if (p_231043_5_ > 1.0D) {
         p_231043_5_ = 1.0D;
      }

      if (p_231043_5_ < -1.0D) {
         p_231043_5_ = -1.0D;
      }

      if (this.commandSuggestionHelper.onScroll(p_231043_5_)) {
         return true;
      } else {
         if (!func_231173_s_()) {
            p_231043_5_ *= 7.0D;
         }

         this.field_230706_i_.ingameGUI.getChatGUI().addScrollPos(p_231043_5_);
         return true;
      }
   }

   public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      if (this.commandSuggestionHelper.onClick((double)((int)p_231044_1_), (double)((int)p_231044_3_), p_231044_5_)) {
         return true;
      } else {
         if (p_231044_5_ == 0) {
            NewChatGui newchatgui = this.field_230706_i_.ingameGUI.getChatGUI();
            if (newchatgui.func_238491_a_(p_231044_1_, p_231044_3_)) {
               return true;
            }

            Style style = newchatgui.func_238494_b_(p_231044_1_, p_231044_3_);
            if (style != null && this.func_230455_a_(style)) {
               return true;
            }
         }

         return this.inputField.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_) ? true : super.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_);
      }
   }

   protected void func_231155_a_(String p_231155_1_, boolean p_231155_2_) {
      if (p_231155_2_) {
         this.inputField.setText(p_231155_1_);
      } else {
         this.inputField.writeText(p_231155_1_);
      }

   }

   /**
    * input is relative and is applied directly to the sentHistoryCursor so -1 is the previous message, 1 is the next
    * message from the current cursor position
    */
   public void getSentHistory(int msgPos) {
      int i = this.sentHistoryCursor + msgPos;
      int j = this.field_230706_i_.ingameGUI.getChatGUI().getSentMessages().size();
      i = MathHelper.clamp(i, 0, j);
      if (i != this.sentHistoryCursor) {
         if (i == j) {
            this.sentHistoryCursor = j;
            this.inputField.setText(this.historyBuffer);
         } else {
            if (this.sentHistoryCursor == j) {
               this.historyBuffer = this.inputField.getText();
            }

            this.inputField.setText(this.field_230706_i_.ingameGUI.getChatGUI().getSentMessages().get(i));
            this.commandSuggestionHelper.func_228124_a_(false);
            this.sentHistoryCursor = i;
         }
      }
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_231035_a_(this.inputField);
      this.inputField.setFocused2(true);
      func_238467_a_(p_230430_1_, 2, this.field_230709_l_ - 14, this.field_230708_k_ - 2, this.field_230709_l_ - 2, this.field_230706_i_.gameSettings.getChatBackgroundColor(Integer.MIN_VALUE));
      this.inputField.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      this.commandSuggestionHelper.func_238500_a_(p_230430_1_, p_230430_2_, p_230430_3_);
      Style style = this.field_230706_i_.ingameGUI.getChatGUI().func_238494_b_((double)p_230430_2_, (double)p_230430_3_);
      if (style != null && style.getHoverEvent() != null) {
         this.func_238653_a_(p_230430_1_, style, p_230430_2_, p_230430_3_);
      }

      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   public boolean func_231177_au__() {
      return false;
   }

   private void setChatLine(String p_208604_1_) {
      this.inputField.setText(p_208604_1_);
   }
}