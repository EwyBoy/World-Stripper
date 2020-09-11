package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.OptionsRowList;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class WithNarratorSettingsScreen extends SettingsScreen {
   private final AbstractOption[] field_243313_c;
   @Nullable
   private Widget field_243314_p;
   private OptionsRowList field_243315_q;

   public WithNarratorSettingsScreen(Screen p_i242058_1_, GameSettings p_i242058_2_, ITextComponent p_i242058_3_, AbstractOption[] p_i242058_4_) {
      super(p_i242058_1_, p_i242058_2_, p_i242058_3_);
      this.field_243313_c = p_i242058_4_;
   }

   protected void func_231160_c_() {
      this.field_243315_q = new OptionsRowList(this.field_230706_i_, this.field_230708_k_, this.field_230709_l_, 32, this.field_230709_l_ - 32, 25);
      this.field_243315_q.addOptions(this.field_243313_c);
      this.field_230705_e_.add(this.field_243315_q);
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ - 27, 200, 20, DialogTexts.field_240632_c_, (p_243316_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.parentScreen);
      }));
      this.field_243314_p = this.field_243315_q.func_243271_b(AbstractOption.NARRATOR);
      if (this.field_243314_p != null) {
         this.field_243314_p.field_230693_o_ = NarratorChatListener.INSTANCE.isActive();
      }

   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      this.field_243315_q.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 20, 16777215);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      List<IReorderingProcessor> list = func_243293_a(this.field_243315_q, p_230430_2_, p_230430_3_);
      if (list != null) {
         this.func_238654_b_(p_230430_1_, list, p_230430_2_, p_230430_3_);
      }

   }

   public void func_243317_i() {
      if (this.field_243314_p != null) {
         this.field_243314_p.func_238482_a_(AbstractOption.NARRATOR.func_238157_c_(this.gameSettings));
      }

   }
}