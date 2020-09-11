package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DeathScreen extends Screen {
   /** The integer value containing the number of ticks that have passed since the player's death */
   private int enableButtonsTimer;
   private final ITextComponent causeOfDeath;
   private final boolean isHardcoreMode;
   private ITextComponent field_243285_p;

   public DeathScreen(@Nullable ITextComponent p_i51118_1_, boolean p_i51118_2_) {
      super(new TranslationTextComponent(p_i51118_2_ ? "deathScreen.title.hardcore" : "deathScreen.title"));
      this.causeOfDeath = p_i51118_1_;
      this.isHardcoreMode = p_i51118_2_;
   }

   protected void func_231160_c_() {
      this.enableButtonsTimer = 0;
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 4 + 72, 200, 20, this.isHardcoreMode ? new TranslationTextComponent("deathScreen.spectate") : new TranslationTextComponent("deathScreen.respawn"), (p_213021_1_) -> {
         this.field_230706_i_.player.respawnPlayer();
         this.field_230706_i_.displayGuiScreen((Screen)null);
      }));
      Button button = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 4 + 96, 200, 20, new TranslationTextComponent("deathScreen.titleScreen"), (p_213020_1_) -> {
         if (this.isHardcoreMode) {
            confirmCallback(true);
            this.func_228177_a_();
         } else {
            ConfirmScreen confirmscreen = new ConfirmScreen(this::confirmCallback, new TranslationTextComponent("deathScreen.quit.confirm"), StringTextComponent.field_240750_d_, new TranslationTextComponent("deathScreen.titleScreen"), new TranslationTextComponent("deathScreen.respawn"));
            this.field_230706_i_.displayGuiScreen(confirmscreen);
            confirmscreen.setButtonDelay(20);
         }
      }));
      if (!this.isHardcoreMode && this.field_230706_i_.getSession() == null) {
         button.field_230693_o_ = false;
      }

      for(Widget widget : this.field_230710_m_) {
         widget.field_230693_o_ = false;
      }

      this.field_243285_p = (new TranslationTextComponent("deathScreen.score")).func_240702_b_(": ").func_230529_a_((new StringTextComponent(Integer.toString(this.field_230706_i_.player.getScore()))).func_240699_a_(TextFormatting.YELLOW));
   }

   public boolean func_231178_ax__() {
      return false;
   }

   private void confirmCallback(boolean p_213022_1_) {
      if (p_213022_1_) {
         this.func_228177_a_();
      } else {
         this.field_230706_i_.player.respawnPlayer();
         this.field_230706_i_.displayGuiScreen((Screen)null);
      }

   }

   private void func_228177_a_() {
      if (this.field_230706_i_.world != null) {
         this.field_230706_i_.world.sendQuittingDisconnectingPacket();
      }

      this.field_230706_i_.unloadWorld(new DirtMessageScreen(new TranslationTextComponent("menu.savingLevel")));
      this.field_230706_i_.displayGuiScreen(new MainMenuScreen());
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_238468_a_(p_230430_1_, 0, 0, this.field_230708_k_, this.field_230709_l_, 1615855616, -1602211792);
      RenderSystem.pushMatrix();
      RenderSystem.scalef(2.0F, 2.0F, 2.0F);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2 / 2, 30, 16777215);
      RenderSystem.popMatrix();
      if (this.causeOfDeath != null) {
         func_238472_a_(p_230430_1_, this.field_230712_o_, this.causeOfDeath, this.field_230708_k_ / 2, 85, 16777215);
      }

      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_243285_p, this.field_230708_k_ / 2, 100, 16777215);
      if (this.causeOfDeath != null && p_230430_3_ > 85 && p_230430_3_ < 85 + 9) {
         Style style = this.func_238623_a_(p_230430_2_);
         this.func_238653_a_(p_230430_1_, style, p_230430_2_, p_230430_3_);
      }

      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   @Nullable
   private Style func_238623_a_(int p_238623_1_) {
      if (this.causeOfDeath == null) {
         return null;
      } else {
         int i = this.field_230706_i_.fontRenderer.func_238414_a_(this.causeOfDeath);
         int j = this.field_230708_k_ / 2 - i / 2;
         int k = this.field_230708_k_ / 2 + i / 2;
         return p_238623_1_ >= j && p_238623_1_ <= k ? this.field_230706_i_.fontRenderer.func_238420_b_().func_238357_a_(this.causeOfDeath, p_238623_1_ - j) : null;
      }
   }

   public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      if (this.causeOfDeath != null && p_231044_3_ > 85.0D && p_231044_3_ < (double)(85 + 9)) {
         Style style = this.func_238623_a_((int)p_231044_1_);
         if (style != null && style.getClickEvent() != null && style.getClickEvent().getAction() == ClickEvent.Action.OPEN_URL) {
            this.func_230455_a_(style);
            return false;
         }
      }

      return super.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_);
   }

   public boolean func_231177_au__() {
      return false;
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      ++this.enableButtonsTimer;
      if (this.enableButtonsTimer == 20) {
         for(Widget widget : this.field_230710_m_) {
            widget.field_230693_o_ = true;
         }
      }

   }
}