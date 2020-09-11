package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DemoScreen extends Screen {
   private static final ResourceLocation DEMO_BACKGROUND_LOCATION = new ResourceLocation("textures/gui/demo_background.png");
   private IBidiRenderer field_243286_b = IBidiRenderer.field_243257_a;
   private IBidiRenderer field_243287_c = IBidiRenderer.field_243257_a;

   public DemoScreen() {
      super(new TranslationTextComponent("demo.help.title"));
   }

   protected void func_231160_c_() {
      int i = -16;
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 116, this.field_230709_l_ / 2 + 62 + -16, 114, 20, new TranslationTextComponent("demo.help.buy"), (p_213019_0_) -> {
         p_213019_0_.field_230693_o_ = false;
         Util.getOSType().openURI("http://www.minecraft.net/store?source=demo");
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 2, this.field_230709_l_ / 2 + 62 + -16, 114, 20, new TranslationTextComponent("demo.help.later"), (p_213018_1_) -> {
         this.field_230706_i_.displayGuiScreen((Screen)null);
         this.field_230706_i_.mouseHelper.grabMouse();
      }));
      GameSettings gamesettings = this.field_230706_i_.gameSettings;
      this.field_243286_b = IBidiRenderer.func_243260_a(this.field_230712_o_, new TranslationTextComponent("demo.help.movementShort", gamesettings.keyBindForward.func_238171_j_(), gamesettings.keyBindLeft.func_238171_j_(), gamesettings.keyBindBack.func_238171_j_(), gamesettings.keyBindRight.func_238171_j_()), new TranslationTextComponent("demo.help.movementMouse"), new TranslationTextComponent("demo.help.jump", gamesettings.keyBindJump.func_238171_j_()), new TranslationTextComponent("demo.help.inventory", gamesettings.keyBindInventory.func_238171_j_()));
      this.field_243287_c = IBidiRenderer.func_243258_a(this.field_230712_o_, new TranslationTextComponent("demo.help.fullWrapped"), 218);
   }

   public void func_230446_a_(MatrixStack p_230446_1_) {
      super.func_230446_a_(p_230446_1_);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_230706_i_.getTextureManager().bindTexture(DEMO_BACKGROUND_LOCATION);
      int i = (this.field_230708_k_ - 248) / 2;
      int j = (this.field_230709_l_ - 166) / 2;
      this.func_238474_b_(p_230446_1_, i, j, 0, 0, 248, 166);
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      int i = (this.field_230708_k_ - 248) / 2 + 10;
      int j = (this.field_230709_l_ - 166) / 2 + 8;
      this.field_230712_o_.func_243248_b(p_230430_1_, this.field_230704_d_, (float)i, (float)j, 2039583);
      j = this.field_243286_b.func_241866_c(p_230430_1_, i, j + 12, 12, 5197647);
      this.field_243287_c.func_241866_c(p_230430_1_, i, j + 20, 9, 2039583);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }
}