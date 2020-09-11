package net.minecraft.client;

import com.mojang.blaze3d.platform.GlStateManager;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.GPUWarning;
import net.minecraft.client.settings.AmbientOcclusionStatus;
import net.minecraft.client.settings.AttackIndicatorStatus;
import net.minecraft.client.settings.BooleanOption;
import net.minecraft.client.settings.CloudOption;
import net.minecraft.client.settings.GraphicsFanciness;
import net.minecraft.client.settings.IteratableOption;
import net.minecraft.client.settings.NarratorStatus;
import net.minecraft.client.settings.ParticleStatus;
import net.minecraft.client.settings.SliderMultiplierOption;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.player.ChatVisibility;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractOption {
   public static final SliderPercentageOption BIOME_BLEND_RADIUS = new SliderPercentageOption("options.biomeBlendRadius", 0.0D, 7.0D, 1.0F, (p_216607_0_) -> {
      return (double)p_216607_0_.biomeBlendRadius;
   }, (p_216660_0_, p_216660_1_) -> {
      p_216660_0_.biomeBlendRadius = MathHelper.clamp((int)p_216660_1_.doubleValue(), 0, 7);
      Minecraft.getInstance().worldRenderer.loadRenderers();
   }, (p_216595_0_, p_216595_1_) -> {
      double d0 = p_216595_1_.get(p_216595_0_);
      int i = (int)d0 * 2 + 1;
      return p_216595_1_.func_243222_a(new TranslationTextComponent("options.biomeBlendRadius." + i));
   });
   public static final SliderPercentageOption CHAT_HEIGHT_FOCUSED = new SliderPercentageOption("options.chat.height.focused", 0.0D, 1.0D, 0.0F, (p_216587_0_) -> {
      return p_216587_0_.chatHeightFocused;
   }, (p_216600_0_, p_216600_1_) -> {
      p_216600_0_.chatHeightFocused = p_216600_1_;
      Minecraft.getInstance().ingameGUI.getChatGUI().refreshChat();
   }, (p_216642_0_, p_216642_1_) -> {
      double d0 = p_216642_1_.normalizeValue(p_216642_1_.get(p_216642_0_));
      return p_216642_1_.func_243221_a(NewChatGui.calculateChatboxHeight(d0));
   });
   public static final SliderPercentageOption CHAT_HEIGHT_UNFOCUSED = new SliderPercentageOption("options.chat.height.unfocused", 0.0D, 1.0D, 0.0F, (p_216611_0_) -> {
      return p_216611_0_.chatHeightUnfocused;
   }, (p_216650_0_, p_216650_1_) -> {
      p_216650_0_.chatHeightUnfocused = p_216650_1_;
      Minecraft.getInstance().ingameGUI.getChatGUI().refreshChat();
   }, (p_216604_0_, p_216604_1_) -> {
      double d0 = p_216604_1_.normalizeValue(p_216604_1_.get(p_216604_0_));
      return p_216604_1_.func_243221_a(NewChatGui.calculateChatboxHeight(d0));
   });
   public static final SliderPercentageOption CHAT_OPACITY = new SliderPercentageOption("options.chat.opacity", 0.0D, 1.0D, 0.0F, (p_216649_0_) -> {
      return p_216649_0_.chatOpacity;
   }, (p_216578_0_, p_216578_1_) -> {
      p_216578_0_.chatOpacity = p_216578_1_;
      Minecraft.getInstance().ingameGUI.getChatGUI().refreshChat();
   }, (p_216592_0_, p_216592_1_) -> {
      double d0 = p_216592_1_.normalizeValue(p_216592_1_.get(p_216592_0_));
      return p_216592_1_.func_243224_c(d0 * 0.9D + 0.1D);
   });
   public static final SliderPercentageOption CHAT_SCALE = new SliderPercentageOption("options.chat.scale", 0.0D, 1.0D, 0.0F, (p_216591_0_) -> {
      return p_216591_0_.chatScale;
   }, (p_216624_0_, p_216624_1_) -> {
      p_216624_0_.chatScale = p_216624_1_;
      Minecraft.getInstance().ingameGUI.getChatGUI().refreshChat();
   }, (p_216637_0_, p_216637_1_) -> {
      double d0 = p_216637_1_.normalizeValue(p_216637_1_.get(p_216637_0_));
      return (ITextComponent)(d0 == 0.0D ? DialogTexts.func_244281_a(p_216637_1_.func_243220_a(), false) : p_216637_1_.func_243224_c(d0));
   });
   public static final SliderPercentageOption CHAT_WIDTH = new SliderPercentageOption("options.chat.width", 0.0D, 1.0D, 0.0F, (p_216601_0_) -> {
      return p_216601_0_.chatWidth;
   }, (p_216620_0_, p_216620_1_) -> {
      p_216620_0_.chatWidth = p_216620_1_;
      Minecraft.getInstance().ingameGUI.getChatGUI().refreshChat();
   }, (p_216673_0_, p_216673_1_) -> {
      double d0 = p_216673_1_.normalizeValue(p_216673_1_.get(p_216673_0_));
      return p_216673_1_.func_243221_a(NewChatGui.calculateChatboxWidth(d0));
   });
   public static final SliderPercentageOption field_238235_g_ = new SliderPercentageOption("options.chat.line_spacing", 0.0D, 1.0D, 0.0F, (p_238287_0_) -> {
      return p_238287_0_.field_238331_l_;
   }, (p_238282_0_, p_238282_1_) -> {
      p_238282_0_.field_238331_l_ = p_238282_1_;
   }, (p_238297_0_, p_238297_1_) -> {
      return p_238297_1_.func_243224_c(p_238297_1_.normalizeValue(p_238297_1_.get(p_238297_0_)));
   });
   public static final SliderPercentageOption field_238236_h_ = new SliderPercentageOption("options.chat.delay_instant", 0.0D, 6.0D, 0.1F, (p_238271_0_) -> {
      return p_238271_0_.field_238332_z_;
   }, (p_238273_0_, p_238273_1_) -> {
      p_238273_0_.field_238332_z_ = p_238273_1_;
   }, (p_238292_0_, p_238292_1_) -> {
      double d0 = p_238292_1_.get(p_238292_0_);
      return d0 <= 0.0D ? new TranslationTextComponent("options.chat.delay_none") : new TranslationTextComponent("options.chat.delay", String.format("%.1f", d0));
   });
   public static final SliderPercentageOption FOV = new SliderPercentageOption("options.fov", 30.0D, 110.0D, 1.0F, (p_216655_0_) -> {
      return p_216655_0_.fov;
   }, (p_216612_0_, p_216612_1_) -> {
      p_216612_0_.fov = p_216612_1_;
   }, (p_216590_0_, p_216590_1_) -> {
      double d0 = p_216590_1_.get(p_216590_0_);
      if (d0 == 70.0D) {
         return p_216590_1_.func_243222_a(new TranslationTextComponent("options.fov.min"));
      } else {
         return d0 == p_216590_1_.getMaxValue() ? p_216590_1_.func_243222_a(new TranslationTextComponent("options.fov.max")) : p_216590_1_.func_243225_c((int)d0);
      }
   });
   private static final ITextComponent field_243215_X = new TranslationTextComponent("options.fovEffectScale.tooltip");
   public static final SliderPercentageOption field_243218_j = new SliderPercentageOption("options.fovEffectScale", 0.0D, 1.0D, 0.0F, (p_216672_0_) -> {
      return Math.pow((double)p_216672_0_.field_243227_aN, 2.0D);
   }, (p_216608_0_, p_216608_1_) -> {
      p_216608_0_.field_243227_aN = MathHelper.sqrt(p_216608_1_);
   }, (p_216645_0_, p_216645_1_) -> {
      p_216645_1_.func_241567_a_(Minecraft.getInstance().fontRenderer.func_238425_b_(field_243215_X, 200));
      double d0 = p_216645_1_.normalizeValue(p_216645_1_.get(p_216645_0_));
      return d0 == 0.0D ? p_216645_1_.func_243222_a(new TranslationTextComponent("options.fovEffectScale.off")) : p_216645_1_.func_243224_c(d0);
   });
   private static final ITextComponent field_243216_Y = new TranslationTextComponent("options.screenEffectScale.tooltip");
   public static final SliderPercentageOption field_243219_k = new SliderPercentageOption("options.screenEffectScale", 0.0D, 1.0D, 0.0F, (p_244419_0_) -> {
      return (double)p_244419_0_.field_243226_aM;
   }, (p_244415_0_, p_244415_1_) -> {
      p_244415_0_.field_243226_aM = p_244415_1_.floatValue();
   }, (p_244416_0_, p_244416_1_) -> {
      p_244416_1_.func_241567_a_(Minecraft.getInstance().fontRenderer.func_238425_b_(field_243216_Y, 200));
      double d0 = p_244416_1_.normalizeValue(p_244416_1_.get(p_244416_0_));
      return d0 == 0.0D ? p_244416_1_.func_243222_a(new TranslationTextComponent("options.screenEffectScale.off")) : p_244416_1_.func_243224_c(d0);
   });
   public static final SliderPercentageOption FRAMERATE_LIMIT = new SliderPercentageOption("options.framerateLimit", 10.0D, 260.0D, 10.0F, (p_244406_0_) -> {
      return (double)p_244406_0_.framerateLimit;
   }, (p_244411_0_, p_244411_1_) -> {
      p_244411_0_.framerateLimit = (int)p_244411_1_.doubleValue();
      Minecraft.getInstance().getMainWindow().setFramerateLimit(p_244411_0_.framerateLimit);
   }, (p_244413_0_, p_244413_1_) -> {
      double d0 = p_244413_1_.get(p_244413_0_);
      return d0 == p_244413_1_.getMaxValue() ? p_244413_1_.func_243222_a(new TranslationTextComponent("options.framerateLimit.max")) : p_244413_1_.func_243222_a(new TranslationTextComponent("options.framerate", (int)d0));
   });
   public static final SliderPercentageOption GAMMA = new SliderPercentageOption("options.gamma", 0.0D, 1.0D, 0.0F, (p_216636_0_) -> {
      return p_216636_0_.gamma;
   }, (p_216651_0_, p_216651_1_) -> {
      p_216651_0_.gamma = p_216651_1_;
   }, (p_216594_0_, p_216594_1_) -> {
      double d0 = p_216594_1_.normalizeValue(p_216594_1_.get(p_216594_0_));
      if (d0 == 0.0D) {
         return p_216594_1_.func_243222_a(new TranslationTextComponent("options.gamma.min"));
      } else {
         return d0 == 1.0D ? p_216594_1_.func_243222_a(new TranslationTextComponent("options.gamma.max")) : p_216594_1_.func_243223_b((int)(d0 * 100.0D));
      }
   });
   public static final SliderPercentageOption MIPMAP_LEVELS = new SliderPercentageOption("options.mipmapLevels", 0.0D, 4.0D, 1.0F, (p_216667_0_) -> {
      return (double)p_216667_0_.mipmapLevels;
   }, (p_216585_0_, p_216585_1_) -> {
      p_216585_0_.mipmapLevels = (int)p_216585_1_.doubleValue();
   }, (p_216629_0_, p_216629_1_) -> {
      double d0 = p_216629_1_.get(p_216629_0_);
      return (ITextComponent)(d0 == 0.0D ? DialogTexts.func_244281_a(p_216629_1_.func_243220_a(), false) : p_216629_1_.func_243225_c((int)d0));
   });
   public static final SliderPercentageOption MOUSE_WHEEL_SENSITIVITY = new SliderMultiplierOption("options.mouseWheelSensitivity", 0.01D, 10.0D, 0.01F, (p_216581_0_) -> {
      return p_216581_0_.mouseWheelSensitivity;
   }, (p_216628_0_, p_216628_1_) -> {
      p_216628_0_.mouseWheelSensitivity = p_216628_1_;
   }, (p_216675_0_, p_216675_1_) -> {
      double d0 = p_216675_1_.normalizeValue(p_216675_1_.get(p_216675_0_));
      return p_216675_1_.func_243222_a(new StringTextComponent(String.format("%.2f", p_216675_1_.denormalizeValue(d0))));
   });
   public static final BooleanOption RAW_MOUSE_INPUT = new BooleanOption("options.rawMouseInput", (p_225287_0_) -> {
      return p_225287_0_.rawMouseInput;
   }, (p_225259_0_, p_225259_1_) -> {
      p_225259_0_.rawMouseInput = p_225259_1_;
      MainWindow mainwindow = Minecraft.getInstance().getMainWindow();
      if (mainwindow != null) {
         mainwindow.setRawMouseInput(p_225259_1_);
      }

   });
   public static final SliderPercentageOption RENDER_DISTANCE = new SliderPercentageOption("options.renderDistance", 2.0D, 16.0D, 1.0F, (p_216658_0_) -> {
      return (double)p_216658_0_.renderDistanceChunks;
   }, (p_216579_0_, p_216579_1_) -> {
      p_216579_0_.renderDistanceChunks = (int)p_216579_1_.doubleValue();
      Minecraft.getInstance().worldRenderer.setDisplayListEntitiesDirty();
   }, (p_216664_0_, p_216664_1_) -> {
      double d0 = p_216664_1_.get(p_216664_0_);
      return p_216664_1_.func_243222_a(new TranslationTextComponent("options.chunks", (int)d0));
   });
   public static final SliderPercentageOption field_238237_p_ = new SliderPercentageOption("options.entityDistanceScaling", 0.5D, 5.0D, 0.25F, (p_238324_0_) -> {
      return (double)p_238324_0_.field_238329_c_;
   }, (p_238256_0_, p_238256_1_) -> {
      p_238256_0_.field_238329_c_ = (float)p_238256_1_.doubleValue();
   }, (p_238254_0_, p_238254_1_) -> {
      double d0 = p_238254_1_.get(p_238254_0_);
      return p_238254_1_.func_243224_c(d0);
   });
   public static final SliderPercentageOption SENSITIVITY = new SliderPercentageOption("options.sensitivity", 0.0D, 1.0D, 0.0F, (p_216654_0_) -> {
      return p_216654_0_.mouseSensitivity;
   }, (p_216644_0_, p_216644_1_) -> {
      p_216644_0_.mouseSensitivity = p_216644_1_;
   }, (p_216641_0_, p_216641_1_) -> {
      double d0 = p_216641_1_.normalizeValue(p_216641_1_.get(p_216641_0_));
      if (d0 == 0.0D) {
         return p_216641_1_.func_243222_a(new TranslationTextComponent("options.sensitivity.min"));
      } else {
         return d0 == 1.0D ? p_216641_1_.func_243222_a(new TranslationTextComponent("options.sensitivity.max")) : p_216641_1_.func_243224_c(2.0D * d0);
      }
   });
   public static final SliderPercentageOption ACCESSIBILITY_TEXT_BACKGROUND_OPACITY = new SliderPercentageOption("options.accessibility.text_background_opacity", 0.0D, 1.0D, 0.0F, (p_216597_0_) -> {
      return p_216597_0_.accessibilityTextBackgroundOpacity;
   }, (p_216593_0_, p_216593_1_) -> {
      p_216593_0_.accessibilityTextBackgroundOpacity = p_216593_1_;
      Minecraft.getInstance().ingameGUI.getChatGUI().refreshChat();
   }, (p_216626_0_, p_216626_1_) -> {
      return p_216626_1_.func_243224_c(p_216626_1_.normalizeValue(p_216626_1_.get(p_216626_0_)));
   });
   public static final IteratableOption AO = new IteratableOption("options.ao", (p_216653_0_, p_216653_1_) -> {
      p_216653_0_.ambientOcclusionStatus = AmbientOcclusionStatus.getValue(p_216653_0_.ambientOcclusionStatus.getId() + p_216653_1_);
      Minecraft.getInstance().worldRenderer.loadRenderers();
   }, (p_216630_0_, p_216630_1_) -> {
      return p_216630_1_.func_243222_a(new TranslationTextComponent(p_216630_0_.ambientOcclusionStatus.getResourceKey()));
   });
   public static final IteratableOption ATTACK_INDICATOR = new IteratableOption("options.attackIndicator", (p_216615_0_, p_216615_1_) -> {
      p_216615_0_.attackIndicator = AttackIndicatorStatus.byId(p_216615_0_.attackIndicator.getId() + p_216615_1_);
   }, (p_216609_0_, p_216609_1_) -> {
      return p_216609_1_.func_243222_a(new TranslationTextComponent(p_216609_0_.attackIndicator.getResourceKey()));
   });
   public static final IteratableOption CHAT_VISIBILITY = new IteratableOption("options.chat.visibility", (p_216640_0_, p_216640_1_) -> {
      p_216640_0_.chatVisibility = ChatVisibility.getValue((p_216640_0_.chatVisibility.getId() + p_216640_1_) % 3);
   }, (p_216598_0_, p_216598_1_) -> {
      return p_216598_1_.func_243222_a(new TranslationTextComponent(p_216598_0_.chatVisibility.getResourceKey()));
   });
   private static final ITextComponent field_241564_V_ = new TranslationTextComponent("options.graphics.fast.tooltip");
   private static final ITextComponent field_241565_W_ = new TranslationTextComponent("options.graphics.fabulous.tooltip", (new TranslationTextComponent("options.graphics.fabulous")).func_240699_a_(TextFormatting.ITALIC));
   private static final ITextComponent field_241566_X_ = new TranslationTextComponent("options.graphics.fancy.tooltip");
   public static final IteratableOption GRAPHICS = new IteratableOption("options.graphics", (p_216577_0_, p_216577_1_) -> {
      Minecraft minecraft = Minecraft.getInstance();
      GPUWarning gpuwarning = minecraft.func_241558_U_();
      if (p_216577_0_.field_238330_f_ == GraphicsFanciness.FANCY && gpuwarning.func_241695_b_()) {
         gpuwarning.func_241697_d_();
      } else {
         p_216577_0_.field_238330_f_ = p_216577_0_.field_238330_f_.func_238166_c_();
         if (p_216577_0_.field_238330_f_ == GraphicsFanciness.FABULOUS && (!GlStateManager.func_237508_S_() || gpuwarning.func_241701_h_())) {
            p_216577_0_.field_238330_f_ = GraphicsFanciness.FAST;
         }

         minecraft.worldRenderer.loadRenderers();
      }
   }, (p_216633_0_, p_216633_1_) -> {
      switch(p_216633_0_.field_238330_f_) {
      case FAST:
         p_216633_1_.func_241567_a_(Minecraft.getInstance().fontRenderer.func_238425_b_(field_241564_V_, 200));
         break;
      case FANCY:
         p_216633_1_.func_241567_a_(Minecraft.getInstance().fontRenderer.func_238425_b_(field_241566_X_, 200));
         break;
      case FABULOUS:
         p_216633_1_.func_241567_a_(Minecraft.getInstance().fontRenderer.func_238425_b_(field_241565_W_, 200));
      }

      IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent(p_216633_0_.field_238330_f_.func_238164_b_());
      return p_216633_0_.field_238330_f_ == GraphicsFanciness.FABULOUS ? p_216633_1_.func_243222_a(iformattabletextcomponent.func_240699_a_(TextFormatting.ITALIC)) : p_216633_1_.func_243222_a(iformattabletextcomponent);
   });
   public static final IteratableOption GUI_SCALE = new IteratableOption("options.guiScale", (p_216674_0_, p_216674_1_) -> {
      p_216674_0_.guiScale = Integer.remainderUnsigned(p_216674_0_.guiScale + p_216674_1_, Minecraft.getInstance().getMainWindow().calcGuiScale(0, Minecraft.getInstance().getForceUnicodeFont()) + 1);
   }, (p_216668_0_, p_216668_1_) -> {
      return p_216668_0_.guiScale == 0 ? p_216668_1_.func_243222_a(new TranslationTextComponent("options.guiScale.auto")) : p_216668_1_.func_243225_c(p_216668_0_.guiScale);
   });
   public static final IteratableOption MAIN_HAND = new IteratableOption("options.mainHand", (p_216584_0_, p_216584_1_) -> {
      p_216584_0_.mainHand = p_216584_0_.mainHand.opposite();
   }, (p_216596_0_, p_216596_1_) -> {
      return p_216596_1_.func_243222_a(p_216596_0_.mainHand.func_233609_b_());
   });
   public static final IteratableOption NARRATOR = new IteratableOption("options.narrator", (p_216648_0_, p_216648_1_) -> {
      if (NarratorChatListener.INSTANCE.isActive()) {
         p_216648_0_.narrator = NarratorStatus.byId(p_216648_0_.narrator.getId() + p_216648_1_);
      } else {
         p_216648_0_.narrator = NarratorStatus.OFF;
      }

      NarratorChatListener.INSTANCE.announceMode(p_216648_0_.narrator);
   }, (p_216632_0_, p_216632_1_) -> {
      return NarratorChatListener.INSTANCE.isActive() ? p_216632_1_.func_243222_a(p_216632_0_.narrator.func_238233_b_()) : p_216632_1_.func_243222_a(new TranslationTextComponent("options.narrator.notavailable"));
   });
   public static final IteratableOption PARTICLES = new IteratableOption("options.particles", (p_216622_0_, p_216622_1_) -> {
      p_216622_0_.particles = ParticleStatus.byId(p_216622_0_.particles.getId() + p_216622_1_);
   }, (p_216616_0_, p_216616_1_) -> {
      return p_216616_1_.func_243222_a(new TranslationTextComponent(p_216616_0_.particles.getResourceKey()));
   });
   public static final IteratableOption RENDER_CLOUDS = new IteratableOption("options.renderClouds", (p_216605_0_, p_216605_1_) -> {
      p_216605_0_.cloudOption = CloudOption.byId(p_216605_0_.cloudOption.getId() + p_216605_1_);
      if (Minecraft.func_238218_y_()) {
         Framebuffer framebuffer = Minecraft.getInstance().worldRenderer.func_239232_u_();
         if (framebuffer != null) {
            framebuffer.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
         }
      }

   }, (p_216602_0_, p_216602_1_) -> {
      return p_216602_1_.func_243222_a(new TranslationTextComponent(p_216602_0_.cloudOption.getKey()));
   });
   public static final IteratableOption ACCESSIBILITY_TEXT_BACKGROUND = new IteratableOption("options.accessibility.text_background", (p_216665_0_, p_216665_1_) -> {
      p_216665_0_.accessibilityTextBackground = !p_216665_0_.accessibilityTextBackground;
   }, (p_216639_0_, p_216639_1_) -> {
      return p_216639_1_.func_243222_a(new TranslationTextComponent(p_216639_0_.accessibilityTextBackground ? "options.accessibility.text_background.chat" : "options.accessibility.text_background.everywhere"));
   });
   public static final BooleanOption AUTO_JUMP = new BooleanOption("options.autoJump", (p_216619_0_) -> {
      return p_216619_0_.autoJump;
   }, (p_216621_0_, p_216621_1_) -> {
      p_216621_0_.autoJump = p_216621_1_;
   });
   public static final BooleanOption AUTO_SUGGEST_COMMANDS = new BooleanOption("options.autoSuggestCommands", (p_216643_0_) -> {
      return p_216643_0_.autoSuggestCommands;
   }, (p_216656_0_, p_216656_1_) -> {
      p_216656_0_.autoSuggestCommands = p_216656_1_;
   });
   public static final BooleanOption CHAT_COLOR = new BooleanOption("options.chat.color", (p_216669_0_) -> {
      return p_216669_0_.chatColor;
   }, (p_216659_0_, p_216659_1_) -> {
      p_216659_0_.chatColor = p_216659_1_;
   });
   public static final BooleanOption CHAT_LINKS = new BooleanOption("options.chat.links", (p_216583_0_) -> {
      return p_216583_0_.chatLinks;
   }, (p_216670_0_, p_216670_1_) -> {
      p_216670_0_.chatLinks = p_216670_1_;
   });
   public static final BooleanOption CHAT_LINKS_PROMPT = new BooleanOption("options.chat.links.prompt", (p_216610_0_) -> {
      return p_216610_0_.chatLinksPrompt;
   }, (p_216652_0_, p_216652_1_) -> {
      p_216652_0_.chatLinksPrompt = p_216652_1_;
   });
   public static final BooleanOption DISCRETE_MOUSE_SCROLL = new BooleanOption("options.discrete_mouse_scroll", (p_216634_0_) -> {
      return p_216634_0_.discreteMouseScroll;
   }, (p_216625_0_, p_216625_1_) -> {
      p_216625_0_.discreteMouseScroll = p_216625_1_;
   });
   public static final BooleanOption VSYNC = new BooleanOption("options.vsync", (p_216661_0_) -> {
      return p_216661_0_.vsync;
   }, (p_216635_0_, p_216635_1_) -> {
      p_216635_0_.vsync = p_216635_1_;
      if (Minecraft.getInstance().getMainWindow() != null) {
         Minecraft.getInstance().getMainWindow().setVsync(p_216635_0_.vsync);
      }

   });
   public static final BooleanOption ENTITY_SHADOWS = new BooleanOption("options.entityShadows", (p_216576_0_) -> {
      return p_216576_0_.entityShadows;
   }, (p_216588_0_, p_216588_1_) -> {
      p_216588_0_.entityShadows = p_216588_1_;
   });
   public static final BooleanOption FORCE_UNICODE_FONT = new BooleanOption("options.forceUnicodeFont", (p_216657_0_) -> {
      return p_216657_0_.forceUnicodeFont;
   }, (p_216631_0_, p_216631_1_) -> {
      p_216631_0_.forceUnicodeFont = p_216631_1_;
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.getMainWindow() != null) {
         minecraft.func_238209_b_(p_216631_1_);
      }

   });
   public static final BooleanOption INVERT_MOUSE = new BooleanOption("options.invertMouse", (p_216627_0_) -> {
      return p_216627_0_.invertMouse;
   }, (p_216603_0_, p_216603_1_) -> {
      p_216603_0_.invertMouse = p_216603_1_;
   });
   public static final BooleanOption REALMS_NOTIFICATIONS = new BooleanOption("options.realmsNotifications", (p_216606_0_) -> {
      return p_216606_0_.realmsNotifications;
   }, (p_216618_0_, p_216618_1_) -> {
      p_216618_0_.realmsNotifications = p_216618_1_;
   });
   public static final BooleanOption REDUCED_DEBUG_INFO = new BooleanOption("options.reducedDebugInfo", (p_216582_0_) -> {
      return p_216582_0_.reducedDebugInfo;
   }, (p_216613_0_, p_216613_1_) -> {
      p_216613_0_.reducedDebugInfo = p_216613_1_;
   });
   public static final BooleanOption SHOW_SUBTITLES = new BooleanOption("options.showSubtitles", (p_216663_0_) -> {
      return p_216663_0_.showSubtitles;
   }, (p_216662_0_, p_216662_1_) -> {
      p_216662_0_.showSubtitles = p_216662_1_;
   });
   public static final BooleanOption SNOOPER = new BooleanOption("options.snooper", (p_216638_0_) -> {
      if (p_216638_0_.snooper) {
      }

      return false;
   }, (p_216676_0_, p_216676_1_) -> {
      p_216676_0_.snooper = p_216676_1_;
   });
   public static final IteratableOption SNEAK = new IteratableOption("key.sneak", (p_228043_0_, p_228043_1_) -> {
      p_228043_0_.toggleCrouch = !p_228043_0_.toggleCrouch;
   }, (p_228041_0_, p_228041_1_) -> {
      return p_228041_1_.func_243222_a(new TranslationTextComponent(p_228041_0_.toggleCrouch ? "options.key.toggle" : "options.key.hold"));
   });
   public static final IteratableOption SPRINT = new IteratableOption("key.sprint", (p_228039_0_, p_228039_1_) -> {
      p_228039_0_.toggleSprint = !p_228039_0_.toggleSprint;
   }, (p_228037_0_, p_228037_1_) -> {
      return p_228037_1_.func_243222_a(new TranslationTextComponent(p_228037_0_.toggleSprint ? "options.key.toggle" : "options.key.hold"));
   });
   public static final BooleanOption TOUCHSCREEN = new BooleanOption("options.touchscreen", (p_216647_0_) -> {
      return p_216647_0_.touchscreen;
   }, (p_216580_0_, p_216580_1_) -> {
      p_216580_0_.touchscreen = p_216580_1_;
   });
   public static final BooleanOption FULLSCREEN = new BooleanOption("options.fullscreen", (p_228040_0_) -> {
      return p_228040_0_.fullscreen;
   }, (p_228042_0_, p_228042_1_) -> {
      p_228042_0_.fullscreen = p_228042_1_;
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.getMainWindow() != null && minecraft.getMainWindow().isFullscreen() != p_228042_0_.fullscreen) {
         minecraft.getMainWindow().toggleFullscreen();
         p_228042_0_.fullscreen = minecraft.getMainWindow().isFullscreen();
      }

   });
   public static final BooleanOption VIEW_BOBBING = new BooleanOption("options.viewBobbing", (p_228036_0_) -> {
      return p_228036_0_.viewBobbing;
   }, (p_228038_0_, p_228038_1_) -> {
      p_228038_0_.viewBobbing = p_228038_1_;
   });
   private final ITextComponent field_243217_ac;
   private Optional<List<IReorderingProcessor>> field_238234_W_ = Optional.empty();

   public AbstractOption(String translationKeyIn) {
      this.field_243217_ac = new TranslationTextComponent(translationKeyIn);
   }

   public abstract Widget createWidget(GameSettings options, int xIn, int yIn, int widthIn);

   protected ITextComponent func_243220_a() {
      return this.field_243217_ac;
   }

   public void func_241567_a_(List<IReorderingProcessor> p_241567_1_) {
      this.field_238234_W_ = Optional.of(p_241567_1_);
   }

   public Optional<List<IReorderingProcessor>> func_238246_b_() {
      return this.field_238234_W_;
   }

   protected ITextComponent func_243221_a(int p_243221_1_) {
      return new TranslationTextComponent("options.pixel_value", this.func_243220_a(), p_243221_1_);
   }

   protected ITextComponent func_243224_c(double p_243224_1_) {
      return new TranslationTextComponent("options.percent_value", this.func_243220_a(), (int)(p_243224_1_ * 100.0D));
   }

   protected ITextComponent func_243223_b(int p_243223_1_) {
      return new TranslationTextComponent("options.percent_add_value", this.func_243220_a(), p_243223_1_);
   }

   protected ITextComponent func_243222_a(ITextComponent p_243222_1_) {
      return new TranslationTextComponent("options.generic_value", this.func_243220_a(), p_243222_1_);
   }

   protected ITextComponent func_243225_c(int p_243225_1_) {
      return this.func_243222_a(new StringTextComponent(Integer.toString(p_243225_1_)));
   }
}