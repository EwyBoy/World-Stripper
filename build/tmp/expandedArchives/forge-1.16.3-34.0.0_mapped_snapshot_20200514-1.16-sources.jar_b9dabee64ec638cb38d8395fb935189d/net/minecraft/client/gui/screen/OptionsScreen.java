package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.AccessibilityScreen;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.LockIconButton;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.network.play.client.CLockDifficultyPacket;
import net.minecraft.network.play.client.CSetDifficultyPacket;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Difficulty;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OptionsScreen extends Screen {
   private static final AbstractOption[] SCREEN_OPTIONS = new AbstractOption[]{AbstractOption.FOV};
   private final Screen lastScreen;
   /** Reference to the GameSettings object. */
   private final GameSettings settings;
   private Button difficultyButton;
   private LockIconButton lockButton;
   private Difficulty field_213062_f;

   public OptionsScreen(Screen p_i1046_1_, GameSettings p_i1046_2_) {
      super(new TranslationTextComponent("options.title"));
      this.lastScreen = p_i1046_1_;
      this.settings = p_i1046_2_;
   }

   protected void func_231160_c_() {
      int i = 0;

      for(AbstractOption abstractoption : SCREEN_OPTIONS) {
         int j = this.field_230708_k_ / 2 - 155 + i % 2 * 160;
         int k = this.field_230709_l_ / 6 - 12 + 24 * (i >> 1);
         this.func_230480_a_(abstractoption.createWidget(this.field_230706_i_.gameSettings, j, k, 150));
         ++i;
      }

      if (this.field_230706_i_.world != null) {
         this.field_213062_f = this.field_230706_i_.world.getDifficulty();
         this.difficultyButton = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155 + i % 2 * 160, this.field_230709_l_ / 6 - 12 + 24 * (i >> 1), 150, 20, this.func_238630_a_(this.field_213062_f), (p_213051_1_) -> {
            this.field_213062_f = Difficulty.byId(this.field_213062_f.getId() + 1);
            this.field_230706_i_.getConnection().sendPacket(new CSetDifficultyPacket(this.field_213062_f));
            this.difficultyButton.func_238482_a_(this.func_238630_a_(this.field_213062_f));
         }));
         if (this.field_230706_i_.isSingleplayer() && !this.field_230706_i_.world.getWorldInfo().isHardcore()) {
            this.difficultyButton.func_230991_b_(this.difficultyButton.func_230998_h_() - 20);
            this.lockButton = this.func_230480_a_(new LockIconButton(this.difficultyButton.field_230690_l_ + this.difficultyButton.func_230998_h_(), this.difficultyButton.field_230691_m_, (p_213054_1_) -> {
               this.field_230706_i_.displayGuiScreen(new ConfirmScreen(this::func_213050_a, new TranslationTextComponent("difficulty.lock.title"), new TranslationTextComponent("difficulty.lock.question", new TranslationTextComponent("options.difficulty." + this.field_230706_i_.world.getWorldInfo().getDifficulty().getTranslationKey()))));
            }));
            this.lockButton.setLocked(this.field_230706_i_.world.getWorldInfo().isDifficultyLocked());
            this.lockButton.field_230693_o_ = !this.lockButton.isLocked();
            this.difficultyButton.field_230693_o_ = !this.lockButton.isLocked();
         } else {
            this.difficultyButton.field_230693_o_ = false;
         }
      } else {
         this.func_230480_a_(new OptionButton(this.field_230708_k_ / 2 - 155 + i % 2 * 160, this.field_230709_l_ / 6 - 12 + 24 * (i >> 1), 150, 20, AbstractOption.REALMS_NOTIFICATIONS, AbstractOption.REALMS_NOTIFICATIONS.func_238152_c_(this.settings), (p_213057_1_) -> {
            AbstractOption.REALMS_NOTIFICATIONS.nextValue(this.settings);
            this.settings.saveOptions();
            p_213057_1_.func_238482_a_(AbstractOption.REALMS_NOTIFICATIONS.func_238152_c_(this.settings));
         }));
      }

      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155, this.field_230709_l_ / 6 + 48 - 6, 150, 20, new TranslationTextComponent("options.skinCustomisation"), (p_213055_1_) -> {
         this.field_230706_i_.displayGuiScreen(new CustomizeSkinScreen(this, this.settings));
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 5, this.field_230709_l_ / 6 + 48 - 6, 150, 20, new TranslationTextComponent("options.sounds"), (p_213061_1_) -> {
         this.field_230706_i_.displayGuiScreen(new OptionsSoundsScreen(this, this.settings));
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155, this.field_230709_l_ / 6 + 72 - 6, 150, 20, new TranslationTextComponent("options.video"), (p_213059_1_) -> {
         this.field_230706_i_.displayGuiScreen(new VideoSettingsScreen(this, this.settings));
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 5, this.field_230709_l_ / 6 + 72 - 6, 150, 20, new TranslationTextComponent("options.controls"), (p_213052_1_) -> {
         this.field_230706_i_.displayGuiScreen(new ControlsScreen(this, this.settings));
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155, this.field_230709_l_ / 6 + 96 - 6, 150, 20, new TranslationTextComponent("options.language"), (p_213053_1_) -> {
         this.field_230706_i_.displayGuiScreen(new LanguageScreen(this, this.settings, this.field_230706_i_.getLanguageManager()));
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 5, this.field_230709_l_ / 6 + 96 - 6, 150, 20, new TranslationTextComponent("options.chat.title"), (p_213049_1_) -> {
         this.field_230706_i_.displayGuiScreen(new ChatOptionsScreen(this, this.settings));
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155, this.field_230709_l_ / 6 + 120 - 6, 150, 20, new TranslationTextComponent("options.resourcepack"), (p_213060_1_) -> {
         this.field_230706_i_.displayGuiScreen(new PackScreen(this, this.field_230706_i_.getResourcePackList(), this::func_241584_a_, this.field_230706_i_.getFileResourcePacks(), new TranslationTextComponent("resourcePack.title")));
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 5, this.field_230709_l_ / 6 + 120 - 6, 150, 20, new TranslationTextComponent("options.accessibility.title"), (p_213058_1_) -> {
         this.field_230706_i_.displayGuiScreen(new AccessibilityScreen(this, this.settings));
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 6 + 168, 200, 20, DialogTexts.field_240632_c_, (p_213056_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.lastScreen);
      }));
   }

   private void func_241584_a_(ResourcePackList p_241584_1_) {
      List<String> list = ImmutableList.copyOf(this.settings.resourcePacks);
      this.settings.resourcePacks.clear();
      this.settings.incompatibleResourcePacks.clear();

      for(ResourcePackInfo resourcepackinfo : p_241584_1_.getEnabledPacks()) {
         if (!resourcepackinfo.isOrderLocked()) {
            this.settings.resourcePacks.add(resourcepackinfo.getName());
            if (!resourcepackinfo.getCompatibility().isCompatible()) {
               this.settings.incompatibleResourcePacks.add(resourcepackinfo.getName());
            }
         }
      }

      this.settings.saveOptions();
      List<String> list1 = ImmutableList.copyOf(this.settings.resourcePacks);
      if (!list1.equals(list)) {
         this.field_230706_i_.reloadResources();
      }

   }

   private ITextComponent func_238630_a_(Difficulty p_238630_1_) {
      return (new TranslationTextComponent("options.difficulty")).func_240702_b_(": ").func_230529_a_(p_238630_1_.getDisplayName());
   }

   private void func_213050_a(boolean p_213050_1_) {
      this.field_230706_i_.displayGuiScreen(this);
      if (p_213050_1_ && this.field_230706_i_.world != null) {
         this.field_230706_i_.getConnection().sendPacket(new CLockDifficultyPacket(true));
         this.lockButton.setLocked(true);
         this.lockButton.field_230693_o_ = false;
         this.difficultyButton.field_230693_o_ = false;
      }

   }

   public void func_231164_f_() {
      this.settings.saveOptions();
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 15, 16777215);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

    @Override
    public void func_231175_as__() {
        // We need to consider 2 potential parent screens here:
        // 1. From the main menu, in which case display the main menu
        // 2. From the pause menu, in which case exit back to game
        this.field_230706_i_.displayGuiScreen(this.lastScreen instanceof IngameMenuScreen ? null : this.lastScreen);
    }
}