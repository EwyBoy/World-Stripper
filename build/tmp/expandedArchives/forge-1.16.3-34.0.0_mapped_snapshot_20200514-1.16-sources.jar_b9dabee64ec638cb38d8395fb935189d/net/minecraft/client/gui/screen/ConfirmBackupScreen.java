package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import javax.annotation.Nullable;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConfirmBackupScreen extends Screen {
   @Nullable
   private final Screen parentScreen;
   protected final ConfirmBackupScreen.ICallback callback;
   private final ITextComponent message;
   private final boolean field_212994_d;
   private IBidiRenderer field_243275_q = IBidiRenderer.field_243257_a;
   private CheckboxButton field_212996_j;

   public ConfirmBackupScreen(@Nullable Screen p_i51122_1_, ConfirmBackupScreen.ICallback p_i51122_2_, ITextComponent p_i51122_3_, ITextComponent p_i51122_4_, boolean p_i51122_5_) {
      super(p_i51122_3_);
      this.parentScreen = p_i51122_1_;
      this.callback = p_i51122_2_;
      this.message = p_i51122_4_;
      this.field_212994_d = p_i51122_5_;
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      this.field_243275_q = IBidiRenderer.func_243258_a(this.field_230712_o_, this.message, this.field_230708_k_ - 50);
      int i = (this.field_243275_q.func_241862_a() + 1) * 9;
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155, 100 + i, 150, 20, new TranslationTextComponent("selectWorld.backupJoinConfirmButton"), (p_212993_1_) -> {
         this.callback.proceed(true, this.field_212996_j.isChecked());
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155 + 160, 100 + i, 150, 20, new TranslationTextComponent("selectWorld.backupJoinSkipButton"), (p_212992_1_) -> {
         this.callback.proceed(false, this.field_212996_j.isChecked());
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155 + 80, 124 + i, 150, 20, DialogTexts.field_240633_d_, (p_212991_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.parentScreen);
      }));
      this.field_212996_j = new CheckboxButton(this.field_230708_k_ / 2 - 155 + 80, 76 + i, 150, 20, new TranslationTextComponent("selectWorld.backupEraseCache"), false);
      if (this.field_212994_d) {
         this.func_230480_a_(this.field_212996_j);
      }

   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 50, 16777215);
      this.field_243275_q.func_241863_a(p_230430_1_, this.field_230708_k_ / 2, 70);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   public boolean func_231178_ax__() {
      return false;
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ == 256) {
         this.field_230706_i_.displayGuiScreen(this.parentScreen);
         return true;
      } else {
         return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public interface ICallback {
      void proceed(boolean p_proceed_1_, boolean p_proceed_2_);
   }
}