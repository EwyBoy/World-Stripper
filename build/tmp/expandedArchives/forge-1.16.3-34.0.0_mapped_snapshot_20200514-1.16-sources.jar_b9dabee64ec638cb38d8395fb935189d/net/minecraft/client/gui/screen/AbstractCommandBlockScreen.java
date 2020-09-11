package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.CommandSuggestionHelper;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.tileentity.CommandBlockLogic;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractCommandBlockScreen extends Screen {
   private static final ITextComponent field_243330_s = new TranslationTextComponent("advMode.setCommand");
   private static final ITextComponent field_243331_t = new TranslationTextComponent("advMode.command");
   private static final ITextComponent field_243332_u = new TranslationTextComponent("advMode.previousOutput");
   protected TextFieldWidget commandTextField;
   protected TextFieldWidget resultTextField;
   protected Button doneButton;
   protected Button cancelButton;
   protected Button trackOutputButton;
   protected boolean field_195238_s;
   private CommandSuggestionHelper field_228184_g_;

   public AbstractCommandBlockScreen() {
      super(NarratorChatListener.EMPTY);
   }

   public void func_231023_e_() {
      this.commandTextField.tick();
   }

   abstract CommandBlockLogic getLogic();

   abstract int func_195236_i();

   protected void func_231160_c_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      this.doneButton = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 4 - 150, this.field_230709_l_ / 4 + 120 + 12, 150, 20, DialogTexts.field_240632_c_, (p_214187_1_) -> {
         this.func_195234_k();
      }));
      this.cancelButton = this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 4, this.field_230709_l_ / 4 + 120 + 12, 150, 20, DialogTexts.field_240633_d_, (p_214186_1_) -> {
         this.func_231175_as__();
      }));
      this.trackOutputButton = this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 150 - 20, this.func_195236_i(), 20, 20, new StringTextComponent("O"), (p_214184_1_) -> {
         CommandBlockLogic commandblocklogic = this.getLogic();
         commandblocklogic.setTrackOutput(!commandblocklogic.shouldTrackOutput());
         this.updateTrackOutput();
      }));
      this.commandTextField = new TextFieldWidget(this.field_230712_o_, this.field_230708_k_ / 2 - 150, 50, 300, 20, new TranslationTextComponent("advMode.command")) {
         protected IFormattableTextComponent func_230442_c_() {
            return super.func_230442_c_().func_240702_b_(AbstractCommandBlockScreen.this.field_228184_g_.func_228129_c_());
         }
      };
      this.commandTextField.setMaxStringLength(32500);
      this.commandTextField.setResponder(this::func_214185_b);
      this.field_230705_e_.add(this.commandTextField);
      this.resultTextField = new TextFieldWidget(this.field_230712_o_, this.field_230708_k_ / 2 - 150, this.func_195236_i(), 276, 20, new TranslationTextComponent("advMode.previousOutput"));
      this.resultTextField.setMaxStringLength(32500);
      this.resultTextField.setEnabled(false);
      this.resultTextField.setText("-");
      this.field_230705_e_.add(this.resultTextField);
      this.setFocusedDefault(this.commandTextField);
      this.commandTextField.setFocused2(true);
      this.field_228184_g_ = new CommandSuggestionHelper(this.field_230706_i_, this, this.commandTextField, this.field_230712_o_, true, true, 0, 7, false, Integer.MIN_VALUE);
      this.field_228184_g_.func_228124_a_(true);
      this.field_228184_g_.init();
   }

   public void func_231152_a_(Minecraft p_231152_1_, int p_231152_2_, int p_231152_3_) {
      String s = this.commandTextField.getText();
      this.func_231158_b_(p_231152_1_, p_231152_2_, p_231152_3_);
      this.commandTextField.setText(s);
      this.field_228184_g_.init();
   }

   protected void updateTrackOutput() {
      if (this.getLogic().shouldTrackOutput()) {
         this.trackOutputButton.func_238482_a_(new StringTextComponent("O"));
         this.resultTextField.setText(this.getLogic().getLastOutput().getString());
      } else {
         this.trackOutputButton.func_238482_a_(new StringTextComponent("X"));
         this.resultTextField.setText("-");
      }

   }

   protected void func_195234_k() {
      CommandBlockLogic commandblocklogic = this.getLogic();
      this.func_195235_a(commandblocklogic);
      if (!commandblocklogic.shouldTrackOutput()) {
         commandblocklogic.setLastOutput((ITextComponent)null);
      }

      this.field_230706_i_.displayGuiScreen((Screen)null);
   }

   public void func_231164_f_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(false);
   }

   protected abstract void func_195235_a(CommandBlockLogic commandBlockLogicIn);

   public void func_231175_as__() {
      this.getLogic().setTrackOutput(this.field_195238_s);
      this.field_230706_i_.displayGuiScreen((Screen)null);
   }

   private void func_214185_b(String p_214185_1_) {
      this.field_228184_g_.init();
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (this.field_228184_g_.onKeyPressed(p_231046_1_, p_231046_2_, p_231046_3_)) {
         return true;
      } else if (super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_)) {
         return true;
      } else if (p_231046_1_ != 257 && p_231046_1_ != 335) {
         return false;
      } else {
         this.func_195234_k();
         return true;
      }
   }

   public boolean func_231043_a_(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
      return this.field_228184_g_.onScroll(p_231043_5_) ? true : super.func_231043_a_(p_231043_1_, p_231043_3_, p_231043_5_);
   }

   public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      return this.field_228184_g_.onClick(p_231044_1_, p_231044_3_, p_231044_5_) ? true : super.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_);
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, field_243330_s, this.field_230708_k_ / 2, 20, 16777215);
      func_238475_b_(p_230430_1_, this.field_230712_o_, field_243331_t, this.field_230708_k_ / 2 - 150, 40, 10526880);
      this.commandTextField.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      int i = 75;
      if (!this.resultTextField.getText().isEmpty()) {
         i = i + (5 * 9 + 1 + this.func_195236_i() - 135);
         func_238475_b_(p_230430_1_, this.field_230712_o_, field_243332_u, this.field_230708_k_ / 2 - 150, i + 4, 10526880);
         this.resultTextField.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      }

      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      this.field_228184_g_.func_238500_a_(p_230430_1_, p_230430_2_, p_230430_3_);
   }
}