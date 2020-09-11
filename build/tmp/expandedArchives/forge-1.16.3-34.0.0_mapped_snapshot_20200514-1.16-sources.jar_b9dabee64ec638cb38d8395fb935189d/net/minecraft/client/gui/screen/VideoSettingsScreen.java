package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.FullscreenResolutionOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.OptionsRowList;
import net.minecraft.client.renderer.GPUWarning;
import net.minecraft.client.settings.GraphicsFanciness;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VideoSettingsScreen extends SettingsScreen {
   private static final ITextComponent field_241598_c_ = (new TranslationTextComponent("options.graphics.fabulous")).func_240699_a_(TextFormatting.ITALIC);
   private static final ITextComponent field_241599_p_ = new TranslationTextComponent("options.graphics.warning.message", field_241598_c_, field_241598_c_);
   private static final ITextComponent field_241600_q_ = (new TranslationTextComponent("options.graphics.warning.title")).func_240699_a_(TextFormatting.RED);
   private static final ITextComponent field_241601_r_ = new TranslationTextComponent("options.graphics.warning.accept");
   private static final ITextComponent field_241602_s_ = new TranslationTextComponent("options.graphics.warning.cancel");
   private static final ITextComponent field_241603_t_ = new StringTextComponent("\n");
   private static final AbstractOption[] OPTIONS = new AbstractOption[]{AbstractOption.GRAPHICS, AbstractOption.RENDER_DISTANCE, AbstractOption.AO, AbstractOption.FRAMERATE_LIMIT, AbstractOption.VSYNC, AbstractOption.VIEW_BOBBING, AbstractOption.GUI_SCALE, AbstractOption.ATTACK_INDICATOR, AbstractOption.GAMMA, AbstractOption.RENDER_CLOUDS, AbstractOption.FULLSCREEN, AbstractOption.PARTICLES, AbstractOption.MIPMAP_LEVELS, AbstractOption.ENTITY_SHADOWS, AbstractOption.field_243219_k, AbstractOption.field_238237_p_, AbstractOption.field_243218_j};
   private OptionsRowList optionsRowList;
   private final GPUWarning field_241604_x_;
   private final int mipmapLevels;

   public VideoSettingsScreen(Screen parentScreenIn, GameSettings gameSettingsIn) {
      super(parentScreenIn, gameSettingsIn, new TranslationTextComponent("options.videoTitle"));
      this.field_241604_x_ = parentScreenIn.field_230706_i_.func_241558_U_();
      this.field_241604_x_.func_241702_i_();
      if (gameSettingsIn.field_238330_f_ == GraphicsFanciness.FABULOUS) {
         this.field_241604_x_.func_241698_e_();
      }

      this.mipmapLevels = gameSettingsIn.mipmapLevels;
   }

   protected void func_231160_c_() {
      this.optionsRowList = new OptionsRowList(this.field_230706_i_, this.field_230708_k_, this.field_230709_l_, 32, this.field_230709_l_ - 32, 25);
      this.optionsRowList.addOption(new FullscreenResolutionOption(this.field_230706_i_.getMainWindow()));
      this.optionsRowList.addOption(AbstractOption.BIOME_BLEND_RADIUS);
      this.optionsRowList.addOptions(OPTIONS);
      this.field_230705_e_.add(this.optionsRowList);
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ - 27, 200, 20, DialogTexts.field_240632_c_, (p_213106_1_) -> {
         this.field_230706_i_.gameSettings.saveOptions();
         this.field_230706_i_.getMainWindow().update();
         this.field_230706_i_.displayGuiScreen(this.parentScreen);
      }));
   }

   public void func_231164_f_() {
      if (this.gameSettings.mipmapLevels != this.mipmapLevels) {
         this.field_230706_i_.setMipmapLevels(this.gameSettings.mipmapLevels);
         this.field_230706_i_.scheduleResourcesRefresh();
      }

      super.func_231164_f_();
   }

   public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      int i = this.gameSettings.guiScale;
      if (super.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_)) {
         if (this.gameSettings.guiScale != i) {
            this.field_230706_i_.updateWindowSize();
         }

         if (this.field_241604_x_.func_241700_g_()) {
            List<ITextProperties> list = Lists.newArrayList(field_241599_p_, field_241603_t_);
            String s = this.field_241604_x_.func_241703_j_();
            if (s != null) {
               list.add(field_241603_t_);
               list.add((new TranslationTextComponent("options.graphics.warning.renderer", s)).func_240699_a_(TextFormatting.GRAY));
            }

            String s1 = this.field_241604_x_.func_241705_l_();
            if (s1 != null) {
               list.add(field_241603_t_);
               list.add((new TranslationTextComponent("options.graphics.warning.vendor", s1)).func_240699_a_(TextFormatting.GRAY));
            }

            String s2 = this.field_241604_x_.func_241704_k_();
            if (s2 != null) {
               list.add(field_241603_t_);
               list.add((new TranslationTextComponent("options.graphics.warning.version", s2)).func_240699_a_(TextFormatting.GRAY));
            }

            this.field_230706_i_.displayGuiScreen(new GPUWarningScreen(field_241600_q_, list, ImmutableList.of(new GPUWarningScreen.Option(field_241601_r_, (p_241606_1_) -> {
               this.gameSettings.field_238330_f_ = GraphicsFanciness.FABULOUS;
               Minecraft.getInstance().worldRenderer.loadRenderers();
               this.field_241604_x_.func_241698_e_();
               this.field_230706_i_.displayGuiScreen(this);
            }), new GPUWarningScreen.Option(field_241602_s_, (p_241605_1_) -> {
               this.field_241604_x_.func_241699_f_();
               this.field_230706_i_.displayGuiScreen(this);
            }))));
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean func_231048_c_(double p_231048_1_, double p_231048_3_, int p_231048_5_) {
      int i = this.gameSettings.guiScale;
      if (super.func_231048_c_(p_231048_1_, p_231048_3_, p_231048_5_)) {
         return true;
      } else if (this.optionsRowList.func_231048_c_(p_231048_1_, p_231048_3_, p_231048_5_)) {
         if (this.gameSettings.guiScale != i) {
            this.field_230706_i_.updateWindowSize();
         }

         return true;
      } else {
         return false;
      }
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      this.optionsRowList.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 5, 16777215);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      List<IReorderingProcessor> list = func_243293_a(this.optionsRowList, p_230430_2_, p_230430_3_);
      if (list != null) {
         this.func_238654_b_(p_230430_1_, list, p_230430_2_, p_230430_3_);
      }

   }
}