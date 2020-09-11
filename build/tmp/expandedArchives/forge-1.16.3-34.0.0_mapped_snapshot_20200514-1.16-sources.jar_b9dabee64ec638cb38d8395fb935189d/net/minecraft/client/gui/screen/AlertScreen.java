package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AlertScreen extends Screen {
   private final Runnable field_201552_h;
   protected final ITextComponent field_201550_f;
   private IBidiRenderer field_243274_p = IBidiRenderer.field_243257_a;
   protected final ITextComponent field_201551_g;
   private int field_201549_s;

   public AlertScreen(Runnable p_i48623_1_, ITextComponent p_i48623_2_, ITextComponent p_i48623_3_) {
      this(p_i48623_1_, p_i48623_2_, p_i48623_3_, DialogTexts.field_240637_h_);
   }

   public AlertScreen(Runnable p_i232268_1_, ITextComponent p_i232268_2_, ITextComponent p_i232268_3_, ITextComponent p_i232268_4_) {
      super(p_i232268_2_);
      this.field_201552_h = p_i232268_1_;
      this.field_201550_f = p_i232268_3_;
      this.field_201551_g = p_i232268_4_;
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 6 + 168, 200, 20, this.field_201551_g, (p_212983_1_) -> {
         this.field_201552_h.run();
      }));
      this.field_243274_p = IBidiRenderer.func_243258_a(this.field_230712_o_, this.field_201550_f, this.field_230708_k_ - 50);
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 70, 16777215);
      this.field_243274_p.func_241863_a(p_230430_1_, this.field_230708_k_ / 2, 90);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      if (--this.field_201549_s == 0) {
         for(Widget widget : this.field_230710_m_) {
            widget.field_230693_o_ = true;
         }
      }

   }
}