package net.minecraft.client.gui.screen;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SleepInMultiplayerScreen extends ChatScreen {
   public SleepInMultiplayerScreen() {
      super("");
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ - 40, 200, 20, new TranslationTextComponent("multiplayer.stopSleeping"), (p_212998_1_) -> {
         this.wakeFromSleep();
      }));
   }

   public void func_231175_as__() {
      this.wakeFromSleep();
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ == 256) {
         this.wakeFromSleep();
      } else if (p_231046_1_ == 257 || p_231046_1_ == 335) {
         String s = this.inputField.getText().trim();
         if (!s.isEmpty()) {
            this.func_231161_c_(s);
         }

         this.inputField.setText("");
         this.field_230706_i_.ingameGUI.getChatGUI().resetScroll();
         return true;
      }

      return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
   }

   private void wakeFromSleep() {
      ClientPlayNetHandler clientplaynethandler = this.field_230706_i_.player.connection;
      clientplaynethandler.sendPacket(new CEntityActionPacket(this.field_230706_i_.player, CEntityActionPacket.Action.STOP_SLEEPING));
   }
}