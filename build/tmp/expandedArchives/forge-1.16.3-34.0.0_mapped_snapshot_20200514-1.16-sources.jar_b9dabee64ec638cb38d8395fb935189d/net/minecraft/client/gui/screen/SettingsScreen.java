package net.minecraft.client.gui.screen;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.IBidiTooltip;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.list.OptionsRowList;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SettingsScreen extends Screen {
   protected final Screen parentScreen;
   protected final GameSettings gameSettings;

   public SettingsScreen(Screen p_i225930_1_, GameSettings p_i225930_2_, ITextComponent p_i225930_3_) {
      super(p_i225930_3_);
      this.parentScreen = p_i225930_1_;
      this.gameSettings = p_i225930_2_;
   }

   public void func_231164_f_() {
      this.field_230706_i_.gameSettings.saveOptions();
   }

   public void func_231175_as__() {
      this.field_230706_i_.displayGuiScreen(this.parentScreen);
   }

   @Nullable
   public static List<IReorderingProcessor> func_243293_a(OptionsRowList p_243293_0_, int p_243293_1_, int p_243293_2_) {
      Optional<Widget> optional = p_243293_0_.func_238518_c_((double)p_243293_1_, (double)p_243293_2_);
      if (optional.isPresent() && optional.get() instanceof IBidiTooltip) {
         Optional<List<IReorderingProcessor>> optional1 = ((IBidiTooltip)optional.get()).func_241867_d();
         return optional1.orElse((List<IReorderingProcessor>)null);
      } else {
         return null;
      }
   }
}