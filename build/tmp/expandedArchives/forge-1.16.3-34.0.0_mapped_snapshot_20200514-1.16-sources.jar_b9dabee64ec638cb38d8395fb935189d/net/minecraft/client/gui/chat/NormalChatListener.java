package net.minecraft.client.gui.chat;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NormalChatListener implements IChatListener {
   private final Minecraft mc;

   public NormalChatListener(Minecraft p_i47393_1_) {
      this.mc = p_i47393_1_;
   }

   /**
    * Called whenever this listener receives a chat message, if this listener is registered to the given type in {@link
    * net.minecraft.client.gui.GuiIngame#chatListeners chatListeners}
    */
   public void say(ChatType chatTypeIn, ITextComponent message, UUID p_192576_3_) {
      if (!this.mc.func_238198_a_(p_192576_3_)) {
         if (chatTypeIn != ChatType.CHAT) {
            this.mc.ingameGUI.getChatGUI().printChatMessage(message);
         } else {
            this.mc.ingameGUI.getChatGUI().func_238495_b_(message);
         }

      }
   }
}