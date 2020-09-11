package net.minecraft.realms;

import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.IScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class RealmsScreen extends Screen {
   public RealmsScreen() {
      super(NarratorChatListener.EMPTY);
   }

   protected static int func_239562_k_(int p_239562_0_) {
      return 40 + p_239562_0_ * 13;
   }

   public void func_231023_e_() {
      for(Widget widget : this.field_230710_m_) {
         if (widget instanceof IScreen) {
            ((IScreen)widget).func_231023_e_();
         }
      }

   }

   public void func_231411_u_() {
      List<String> list = this.field_230705_e_.stream().filter(RealmsLabel.class::isInstance).map(RealmsLabel.class::cast).map(RealmsLabel::func_231399_a_).collect(Collectors.toList());
      RealmsNarratorHelper.func_239549_a_(list);
   }
}