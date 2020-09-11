package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.advancements.AdvancementsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsBridgeScreen;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IngameMenuScreen extends Screen {
   private final boolean isFullMenu;

   public IngameMenuScreen(boolean p_i51519_1_) {
      super(p_i51519_1_ ? new TranslationTextComponent("menu.game") : new TranslationTextComponent("menu.paused"));
      this.isFullMenu = p_i51519_1_;
   }

   protected void func_231160_c_() {
      if (this.isFullMenu) {
         this.addButtons();
      }

   }

   private void addButtons() {
      int i = -16;
      int j = 98;
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 102, this.field_230709_l_ / 4 + 24 + -16, 204, 20, new TranslationTextComponent("menu.returnToGame"), (p_213070_1_) -> {
         this.field_230706_i_.displayGuiScreen((Screen)null);
         this.field_230706_i_.mouseHelper.grabMouse();
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 102, this.field_230709_l_ / 4 + 48 + -16, 98, 20, new TranslationTextComponent("gui.advancements"), (p_213065_1_) -> {
         this.field_230706_i_.displayGuiScreen(new AdvancementsScreen(this.field_230706_i_.player.connection.getAdvancementManager()));
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 4, this.field_230709_l_ / 4 + 48 + -16, 98, 20, new TranslationTextComponent("gui.stats"), (p_213066_1_) -> {
         this.field_230706_i_.displayGuiScreen(new StatsScreen(this, this.field_230706_i_.player.getStats()));
      }));
      String s = SharedConstants.getVersion().isStable() ? "https://aka.ms/javafeedback?ref=game" : "https://aka.ms/snapshotfeedback?ref=game";
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 102, this.field_230709_l_ / 4 + 72 + -16, 98, 20, new TranslationTextComponent("menu.sendFeedback"), (p_213072_2_) -> {
         this.field_230706_i_.displayGuiScreen(new ConfirmOpenLinkScreen((p_213069_2_) -> {
            if (p_213069_2_) {
               Util.getOSType().openURI(s);
            }

            this.field_230706_i_.displayGuiScreen(this);
         }, s, true));
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 4, this.field_230709_l_ / 4 + 72 + -16, 98, 20, new TranslationTextComponent("menu.reportBugs"), (p_213063_1_) -> {
         this.field_230706_i_.displayGuiScreen(new ConfirmOpenLinkScreen((p_213064_1_) -> {
            if (p_213064_1_) {
               Util.getOSType().openURI("https://aka.ms/snapshotbugs?ref=game");
            }

            this.field_230706_i_.displayGuiScreen(this);
         }, "https://aka.ms/snapshotbugs?ref=game", true));
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 102, this.field_230709_l_ / 4 + 96 + -16, 98, 20, new TranslationTextComponent("menu.options"), (p_213071_1_) -> {
         this.field_230706_i_.displayGuiScreen(new OptionsScreen(this, this.field_230706_i_.gameSettings));
      }));
      Button button = this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 4, this.field_230709_l_ / 4 + 96 + -16, 98, 20, new TranslationTextComponent("menu.shareToLan"), (p_213068_1_) -> {
         this.field_230706_i_.displayGuiScreen(new ShareToLanScreen(this));
      }));
      button.field_230693_o_ = this.field_230706_i_.isSingleplayer() && !this.field_230706_i_.getIntegratedServer().getPublic();
      Button button1 = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 102, this.field_230709_l_ / 4 + 120 + -16, 204, 20, new TranslationTextComponent("menu.returnToMenu"), (p_213067_1_) -> {
         boolean flag = this.field_230706_i_.isIntegratedServerRunning();
         boolean flag1 = this.field_230706_i_.isConnectedToRealms();
         p_213067_1_.field_230693_o_ = false;
         this.field_230706_i_.world.sendQuittingDisconnectingPacket();
         if (flag) {
            this.field_230706_i_.unloadWorld(new DirtMessageScreen(new TranslationTextComponent("menu.savingLevel")));
         } else {
            this.field_230706_i_.unloadWorld();
         }

         if (flag) {
            this.field_230706_i_.displayGuiScreen(new MainMenuScreen());
         } else if (flag1) {
            RealmsBridgeScreen realmsbridgescreen = new RealmsBridgeScreen();
            realmsbridgescreen.func_231394_a_(new MainMenuScreen());
         } else {
            this.field_230706_i_.displayGuiScreen(new MultiplayerScreen(new MainMenuScreen()));
         }

      }));
      if (!this.field_230706_i_.isIntegratedServerRunning()) {
         button1.func_238482_a_(new TranslationTextComponent("menu.disconnect"));
      }

   }

   public void func_231023_e_() {
      super.func_231023_e_();
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      if (this.isFullMenu) {
         this.func_230446_a_(p_230430_1_);
         func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 40, 16777215);
      } else {
         func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 10, 16777215);
      }

      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }
}