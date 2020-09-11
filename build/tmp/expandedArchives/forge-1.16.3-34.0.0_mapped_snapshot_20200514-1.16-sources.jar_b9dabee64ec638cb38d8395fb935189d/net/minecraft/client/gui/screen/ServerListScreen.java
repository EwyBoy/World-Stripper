package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ServerListScreen extends Screen {
   private static final ITextComponent field_243288_a = new TranslationTextComponent("addServer.enterIp");
   private Button field_195170_a;
   private final ServerData serverData;
   private TextFieldWidget ipEdit;
   private final BooleanConsumer field_213027_d;
   private final Screen field_228178_e_;

   public ServerListScreen(Screen p_i225926_1_, BooleanConsumer p_i225926_2_, ServerData p_i225926_3_) {
      super(new TranslationTextComponent("selectServer.direct"));
      this.field_228178_e_ = p_i225926_1_;
      this.serverData = p_i225926_3_;
      this.field_213027_d = p_i225926_2_;
   }

   public void func_231023_e_() {
      this.ipEdit.tick();
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (this.func_241217_q_() != this.ipEdit || p_231046_1_ != 257 && p_231046_1_ != 335) {
         return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
      } else {
         this.func_195167_h();
         return true;
      }
   }

   protected void func_231160_c_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      this.field_195170_a = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 4 + 96 + 12, 200, 20, new TranslationTextComponent("selectServer.select"), (p_213026_1_) -> {
         this.func_195167_h();
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 4 + 120 + 12, 200, 20, DialogTexts.field_240633_d_, (p_213025_1_) -> {
         this.field_213027_d.accept(false);
      }));
      this.ipEdit = new TextFieldWidget(this.field_230712_o_, this.field_230708_k_ / 2 - 100, 116, 200, 20, new TranslationTextComponent("addServer.enterIp"));
      this.ipEdit.setMaxStringLength(128);
      this.ipEdit.setFocused2(true);
      this.ipEdit.setText(this.field_230706_i_.gameSettings.lastServer);
      this.ipEdit.setResponder((p_213024_1_) -> {
         this.func_195168_i();
      });
      this.field_230705_e_.add(this.ipEdit);
      this.setFocusedDefault(this.ipEdit);
      this.func_195168_i();
   }

   public void func_231152_a_(Minecraft p_231152_1_, int p_231152_2_, int p_231152_3_) {
      String s = this.ipEdit.getText();
      this.func_231158_b_(p_231152_1_, p_231152_2_, p_231152_3_);
      this.ipEdit.setText(s);
   }

   private void func_195167_h() {
      this.serverData.serverIP = this.ipEdit.getText();
      this.field_213027_d.accept(true);
   }

   public void func_231175_as__() {
      this.field_230706_i_.displayGuiScreen(this.field_228178_e_);
   }

   public void func_231164_f_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(false);
      this.field_230706_i_.gameSettings.lastServer = this.ipEdit.getText();
      this.field_230706_i_.gameSettings.saveOptions();
   }

   private void func_195168_i() {
      String s = this.ipEdit.getText();
      this.field_195170_a.field_230693_o_ = !s.isEmpty() && s.split(":").length > 0 && s.indexOf(32) == -1;
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 20, 16777215);
      func_238475_b_(p_230430_1_, this.field_230712_o_, field_243288_a, this.field_230708_k_ / 2 - 100, 100, 10526880);
      this.ipEdit.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }
}