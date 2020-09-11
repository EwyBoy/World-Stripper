package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.net.IDN;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AddServerScreen extends Screen {
   private static final ITextComponent field_243290_a = new TranslationTextComponent("addServer.enterName");
   private static final ITextComponent field_243291_b = new TranslationTextComponent("addServer.enterIp");
   private Button buttonAddServer;
   private final BooleanConsumer field_213032_b;
   private final ServerData serverData;
   private TextFieldWidget textFieldServerAddress;
   private TextFieldWidget textFieldServerName;
   private Button buttonResourcePack;
   private final Screen field_228179_g_;
   private final Predicate<String> addressFilter = (p_210141_0_) -> {
      if (StringUtils.isNullOrEmpty(p_210141_0_)) {
         return true;
      } else {
         String[] astring = p_210141_0_.split(":");
         if (astring.length == 0) {
            return true;
         } else {
            try {
               String s = IDN.toASCII(astring[0]);
               return true;
            } catch (IllegalArgumentException illegalargumentexception) {
               return false;
            }
         }
      }
   };

   public AddServerScreen(Screen p_i225927_1_, BooleanConsumer p_i225927_2_, ServerData p_i225927_3_) {
      super(new TranslationTextComponent("addServer.title"));
      this.field_228179_g_ = p_i225927_1_;
      this.field_213032_b = p_i225927_2_;
      this.serverData = p_i225927_3_;
   }

   public void func_231023_e_() {
      this.textFieldServerName.tick();
      this.textFieldServerAddress.tick();
   }

   protected void func_231160_c_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      this.textFieldServerName = new TextFieldWidget(this.field_230712_o_, this.field_230708_k_ / 2 - 100, 66, 200, 20, new TranslationTextComponent("addServer.enterName"));
      this.textFieldServerName.setFocused2(true);
      this.textFieldServerName.setText(this.serverData.serverName);
      this.textFieldServerName.setResponder(this::func_213028_a);
      this.field_230705_e_.add(this.textFieldServerName);
      this.textFieldServerAddress = new TextFieldWidget(this.field_230712_o_, this.field_230708_k_ / 2 - 100, 106, 200, 20, new TranslationTextComponent("addServer.enterIp"));
      this.textFieldServerAddress.setMaxStringLength(128);
      this.textFieldServerAddress.setText(this.serverData.serverIP);
      this.textFieldServerAddress.setValidator(this.addressFilter);
      this.textFieldServerAddress.setResponder(this::func_213028_a);
      this.field_230705_e_.add(this.textFieldServerAddress);
      this.buttonResourcePack = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 4 + 72, 200, 20, func_238624_a_(this.serverData.getResourceMode()), (p_213031_1_) -> {
         this.serverData.setResourceMode(ServerData.ServerResourceMode.values()[(this.serverData.getResourceMode().ordinal() + 1) % ServerData.ServerResourceMode.values().length]);
         this.buttonResourcePack.func_238482_a_(func_238624_a_(this.serverData.getResourceMode()));
      }));
      this.buttonAddServer = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 4 + 96 + 18, 200, 20, new TranslationTextComponent("addServer.add"), (p_213030_1_) -> {
         this.onButtonServerAddPressed();
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 4 + 120 + 18, 200, 20, DialogTexts.field_240633_d_, (p_213029_1_) -> {
         this.field_213032_b.accept(false);
      }));
      this.func_228180_b_();
   }

   private static ITextComponent func_238624_a_(ServerData.ServerResourceMode p_238624_0_) {
      return (new TranslationTextComponent("addServer.resourcePack")).func_240702_b_(": ").func_230529_a_(p_238624_0_.getMotd());
   }

   public void func_231152_a_(Minecraft p_231152_1_, int p_231152_2_, int p_231152_3_) {
      String s = this.textFieldServerAddress.getText();
      String s1 = this.textFieldServerName.getText();
      this.func_231158_b_(p_231152_1_, p_231152_2_, p_231152_3_);
      this.textFieldServerAddress.setText(s);
      this.textFieldServerName.setText(s1);
   }

   private void func_213028_a(String p_213028_1_) {
      this.func_228180_b_();
   }

   public void func_231164_f_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(false);
   }

   private void onButtonServerAddPressed() {
      this.serverData.serverName = this.textFieldServerName.getText();
      this.serverData.serverIP = this.textFieldServerAddress.getText();
      this.field_213032_b.accept(true);
   }

   public void func_231175_as__() {
      this.func_228180_b_();
      this.field_230706_i_.displayGuiScreen(this.field_228179_g_);
   }

   private void func_228180_b_() {
      String s = this.textFieldServerAddress.getText();
      boolean flag = !s.isEmpty() && s.split(":").length > 0 && s.indexOf(32) == -1;
      this.buttonAddServer.field_230693_o_ = flag && !this.textFieldServerName.getText().isEmpty();
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 17, 16777215);
      func_238475_b_(p_230430_1_, this.field_230712_o_, field_243290_a, this.field_230708_k_ / 2 - 100, 53, 10526880);
      func_238475_b_(p_230430_1_, this.field_230712_o_, field_243291_b, this.field_230708_k_ / 2 - 100, 94, 10526880);
      this.textFieldServerName.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      this.textFieldServerAddress.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }
}