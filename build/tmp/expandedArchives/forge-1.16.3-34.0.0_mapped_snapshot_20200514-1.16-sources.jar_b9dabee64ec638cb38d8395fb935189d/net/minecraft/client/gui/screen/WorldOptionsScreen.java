package net.minecraft.client.gui.screen;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.command.Commands;
import net.minecraft.resources.DataPackRegistries;
import net.minecraft.resources.FolderPackFinder;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.resources.ServerPackFinder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.WorldGenSettingsExport;
import net.minecraft.util.registry.WorldSettingsImport;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.PointerBuffer;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

@OnlyIn(Dist.CLIENT)
public class WorldOptionsScreen implements IScreen, IRenderable {
   private static final Logger field_239028_b_ = LogManager.getLogger();
   private static final ITextComponent field_239029_c_ = new TranslationTextComponent("generator.custom");
   private static final ITextComponent field_239030_d_ = new TranslationTextComponent("generator.amplified.info");
   private static final ITextComponent field_243442_e = new TranslationTextComponent("selectWorld.mapFeatures.info");
   private IBidiRenderer field_243443_f = IBidiRenderer.field_243257_a;
   private FontRenderer field_239031_e_;
   private int field_239032_f_;
   private TextFieldWidget field_239033_g_;
   private Button field_239034_h_;
   public Button field_239027_a_;
   private Button field_239035_i_;
   private Button field_239036_j_;
   private Button field_239037_k_;
   private DynamicRegistries.Impl field_239038_l_;
   private DimensionGeneratorSettings field_239039_m_;
   private Optional<BiomeGeneratorTypeScreens> field_239040_n_;
   private OptionalLong field_243444_q;

   public WorldOptionsScreen(DynamicRegistries.Impl p_i242065_1_, DimensionGeneratorSettings p_i242065_2_, Optional<BiomeGeneratorTypeScreens> p_i242065_3_, OptionalLong p_i242065_4_) {
      this.field_239038_l_ = p_i242065_1_;
      this.field_239039_m_ = p_i242065_2_;
      this.field_239040_n_ = p_i242065_3_;
      this.field_243444_q = p_i242065_4_;
   }

   public void func_239048_a_(final CreateWorldScreen p_239048_1_, Minecraft p_239048_2_, FontRenderer p_239048_3_) {
      this.field_239031_e_ = p_239048_3_;
      this.field_239032_f_ = p_239048_1_.field_230708_k_;
      this.field_239033_g_ = new TextFieldWidget(this.field_239031_e_, this.field_239032_f_ / 2 - 100, 60, 200, 20, new TranslationTextComponent("selectWorld.enterSeed"));
      this.field_239033_g_.setText(func_243445_a(this.field_243444_q));
      this.field_239033_g_.setResponder((p_239058_1_) -> {
         this.field_243444_q = this.func_243449_f();
      });
      p_239048_1_.func_230481_d_(this.field_239033_g_);
      int i = this.field_239032_f_ / 2 - 155;
      int j = this.field_239032_f_ / 2 + 5;
      this.field_239034_h_ = p_239048_1_.func_230480_a_(new Button(i, 100, 150, 20, new TranslationTextComponent("selectWorld.mapFeatures"), (p_239056_1_) -> {
         this.field_239039_m_ = this.field_239039_m_.func_236231_l_();
         p_239056_1_.func_230994_c_(250);
      }) {
         public ITextComponent func_230458_i_() {
            return DialogTexts.func_244281_a(super.func_230458_i_(), WorldOptionsScreen.this.field_239039_m_.func_236222_c_());
         }

         protected IFormattableTextComponent func_230442_c_() {
            return super.func_230442_c_().func_240702_b_(". ").func_230529_a_(new TranslationTextComponent("selectWorld.mapFeatures.info"));
         }
      });
      this.field_239034_h_.field_230694_p_ = false;
      this.field_239035_i_ = p_239048_1_.func_230480_a_(new Button(j, 100, 150, 20, new TranslationTextComponent("selectWorld.mapType"), (p_239050_2_) -> {
         while(true) {
            if (this.field_239040_n_.isPresent()) {
               int k = BiomeGeneratorTypeScreens.field_239068_c_.indexOf(this.field_239040_n_.get()) + 1;
               if (k >= BiomeGeneratorTypeScreens.field_239068_c_.size()) {
                  k = 0;
               }

               BiomeGeneratorTypeScreens biomegeneratortypescreens = BiomeGeneratorTypeScreens.field_239068_c_.get(k);
               this.field_239040_n_ = Optional.of(biomegeneratortypescreens);
               this.field_239039_m_ = biomegeneratortypescreens.func_241220_a_(this.field_239038_l_, this.field_239039_m_.func_236221_b_(), this.field_239039_m_.func_236222_c_(), this.field_239039_m_.func_236223_d_());
               if (this.field_239039_m_.func_236227_h_() && !Screen.func_231173_s_()) {
                  continue;
               }
            }

            p_239048_1_.func_238955_g_();
            p_239050_2_.func_230994_c_(250);
            return;
         }
      }) {
         public ITextComponent func_230458_i_() {
            return super.func_230458_i_().func_230532_e_().func_240702_b_(" ").func_230529_a_(WorldOptionsScreen.this.field_239040_n_.map(BiomeGeneratorTypeScreens::func_239077_a_).orElse(WorldOptionsScreen.field_239029_c_));
         }

         protected IFormattableTextComponent func_230442_c_() {
            return Objects.equals(WorldOptionsScreen.this.field_239040_n_, Optional.of(BiomeGeneratorTypeScreens.field_239067_b_)) ? super.func_230442_c_().func_240702_b_(". ").func_230529_a_(WorldOptionsScreen.field_239030_d_) : super.func_230442_c_();
         }
      });
      this.field_239035_i_.field_230694_p_ = false;
      this.field_239035_i_.field_230693_o_ = this.field_239040_n_.isPresent();
      this.field_239036_j_ = p_239048_1_.func_230480_a_(new Button(j, 120, 150, 20, new TranslationTextComponent("selectWorld.customizeType"), (p_239044_3_) -> {
         BiomeGeneratorTypeScreens.IFactory biomegeneratortypescreens$ifactory = BiomeGeneratorTypeScreens.field_239069_d_.get(this.field_239040_n_);
         if (biomegeneratortypescreens$ifactory != null) {
            p_239048_2_.displayGuiScreen(biomegeneratortypescreens$ifactory.createEditScreen(p_239048_1_, this.field_239039_m_));
         }

      }));
      this.field_239036_j_.field_230694_p_ = false;
      this.field_239027_a_ = p_239048_1_.func_230480_a_(new Button(i, 151, 150, 20, new TranslationTextComponent("selectWorld.bonusItems"), (p_239047_1_) -> {
         this.field_239039_m_ = this.field_239039_m_.func_236232_m_();
         p_239047_1_.func_230994_c_(250);
      }) {
         public ITextComponent func_230458_i_() {
            return DialogTexts.func_244281_a(super.func_230458_i_(), WorldOptionsScreen.this.field_239039_m_.func_236223_d_() && !p_239048_1_.hardCoreMode);
         }
      });
      this.field_239027_a_.field_230694_p_ = false;
      this.field_239037_k_ = p_239048_1_.func_230480_a_(new Button(i, 185, 150, 20, new TranslationTextComponent("selectWorld.import_worldgen_settings"), (p_239049_3_) -> {
         TranslationTextComponent translationtextcomponent = new TranslationTextComponent("selectWorld.import_worldgen_settings.select_file");
         String s = TinyFileDialogs.tinyfd_openFileDialog(translationtextcomponent.getString(), (CharSequence)null, (PointerBuffer)null, (CharSequence)null, false);
         if (s != null) {
            DynamicRegistries.Impl dynamicregistries$impl = DynamicRegistries.func_239770_b_();
            ResourcePackList resourcepacklist = new ResourcePackList(new ServerPackFinder(), new FolderPackFinder(p_239048_1_.func_238957_j_().toFile(), IPackNameDecorator.field_232627_c_));

            DataPackRegistries datapackregistries;
            try {
               MinecraftServer.func_240772_a_(resourcepacklist, p_239048_1_.field_238933_b_, false);
               CompletableFuture<DataPackRegistries> completablefuture = DataPackRegistries.func_240961_a_(resourcepacklist.func_232623_f_(), Commands.EnvironmentType.INTEGRATED, 2, Util.getServerExecutor(), p_239048_2_);
               p_239048_2_.driveUntil(completablefuture::isDone);
               datapackregistries = completablefuture.get();
            } catch (ExecutionException | InterruptedException interruptedexception) {
               field_239028_b_.error("Error loading data packs when importing world settings", (Throwable)interruptedexception);
               ITextComponent itextcomponent = new TranslationTextComponent("selectWorld.import_worldgen_settings.failure");
               ITextComponent itextcomponent1 = new StringTextComponent(interruptedexception.getMessage());
               p_239048_2_.getToastGui().add(SystemToast.func_238534_a_(p_239048_2_, SystemToast.Type.WORLD_GEN_SETTINGS_TRANSFER, itextcomponent, itextcomponent1));
               resourcepacklist.close();
               return;
            }

            WorldSettingsImport<JsonElement> worldsettingsimport = WorldSettingsImport.func_244335_a(JsonOps.INSTANCE, datapackregistries.func_240970_h_(), dynamicregistries$impl);
            JsonParser jsonparser = new JsonParser();

            DataResult<DimensionGeneratorSettings> dataresult;
            try (BufferedReader bufferedreader = Files.newBufferedReader(Paths.get(s))) {
               JsonElement jsonelement = jsonparser.parse(bufferedreader);
               dataresult = DimensionGeneratorSettings.field_236201_a_.parse(worldsettingsimport, jsonelement);
            } catch (JsonIOException | JsonSyntaxException | IOException ioexception) {
               dataresult = DataResult.error("Failed to parse file: " + ioexception.getMessage());
            }

            if (dataresult.error().isPresent()) {
               ITextComponent itextcomponent2 = new TranslationTextComponent("selectWorld.import_worldgen_settings.failure");
               String s1 = dataresult.error().get().message();
               field_239028_b_.error("Error parsing world settings: {}", (Object)s1);
               ITextComponent itextcomponent3 = new StringTextComponent(s1);
               p_239048_2_.getToastGui().add(SystemToast.func_238534_a_(p_239048_2_, SystemToast.Type.WORLD_GEN_SETTINGS_TRANSFER, itextcomponent2, itextcomponent3));
            }

            datapackregistries.close();
            Lifecycle lifecycle = dataresult.lifecycle();
            dataresult.resultOrPartial(field_239028_b_::error).ifPresent((p_239046_5_) -> {
               BooleanConsumer booleanconsumer = (p_239045_5_) -> {
                  p_239048_2_.displayGuiScreen(p_239048_1_);
                  if (p_239045_5_) {
                     this.func_239052_a_(dynamicregistries$impl, p_239046_5_);
                  }

               };
               if (lifecycle == Lifecycle.stable()) {
                  this.func_239052_a_(dynamicregistries$impl, p_239046_5_);
               } else if (lifecycle == Lifecycle.experimental()) {
                  p_239048_2_.displayGuiScreen(new ConfirmScreen(booleanconsumer, new TranslationTextComponent("selectWorld.import_worldgen_settings.experimental.title"), new TranslationTextComponent("selectWorld.import_worldgen_settings.experimental.question")));
               } else {
                  p_239048_2_.displayGuiScreen(new ConfirmScreen(booleanconsumer, new TranslationTextComponent("selectWorld.import_worldgen_settings.deprecated.title"), new TranslationTextComponent("selectWorld.import_worldgen_settings.deprecated.question")));
               }

            });
         }
      }));
      this.field_239037_k_.field_230694_p_ = false;
      this.field_243443_f = IBidiRenderer.func_243258_a(p_239048_3_, field_239030_d_, this.field_239035_i_.func_230998_h_());
   }

   private void func_239052_a_(DynamicRegistries.Impl p_239052_1_, DimensionGeneratorSettings p_239052_2_) {
      this.field_239038_l_ = p_239052_1_;
      this.field_239039_m_ = p_239052_2_;
      this.field_239040_n_ = BiomeGeneratorTypeScreens.func_239079_a_(p_239052_2_);
      this.field_243444_q = OptionalLong.of(p_239052_2_.func_236221_b_());
      this.field_239033_g_.setText(func_243445_a(this.field_243444_q));
      this.field_239035_i_.field_230693_o_ = this.field_239040_n_.isPresent();
   }

   public void func_231023_e_() {
      this.field_239033_g_.tick();
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      if (this.field_239034_h_.field_230694_p_) {
         this.field_239031_e_.func_243246_a(p_230430_1_, field_243442_e, (float)(this.field_239032_f_ / 2 - 150), 122.0F, -6250336);
      }

      this.field_239033_g_.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      if (this.field_239040_n_.equals(Optional.of(BiomeGeneratorTypeScreens.field_239067_b_))) {
         this.field_243443_f.func_241865_b(p_230430_1_, this.field_239035_i_.field_230690_l_ + 2, this.field_239035_i_.field_230691_m_ + 22, 9, 10526880);
      }

   }

   protected void func_239043_a_(DimensionGeneratorSettings p_239043_1_) {
      this.field_239039_m_ = p_239043_1_;
   }

   private static String func_243445_a(OptionalLong p_243445_0_) {
      return p_243445_0_.isPresent() ? Long.toString(p_243445_0_.getAsLong()) : "";
   }

   private static OptionalLong func_239053_a_(String p_239053_0_) {
      try {
         return OptionalLong.of(Long.parseLong(p_239053_0_));
      } catch (NumberFormatException numberformatexception) {
         return OptionalLong.empty();
      }
   }

   public DimensionGeneratorSettings func_239054_a_(boolean p_239054_1_) {
      OptionalLong optionallong = this.func_243449_f();
      return this.field_239039_m_.func_236220_a_(p_239054_1_, optionallong);
   }

   private OptionalLong func_243449_f() {
      String s = this.field_239033_g_.getText();
      OptionalLong optionallong;
      if (StringUtils.isEmpty(s)) {
         optionallong = OptionalLong.empty();
      } else {
         OptionalLong optionallong1 = func_239053_a_(s);
         if (optionallong1.isPresent() && optionallong1.getAsLong() != 0L) {
            optionallong = optionallong1;
         } else {
            optionallong = OptionalLong.of((long)s.hashCode());
         }
      }

      return optionallong;
   }

   public boolean func_239042_a_() {
      return this.field_239039_m_.func_236227_h_();
   }

   public void func_239059_b_(boolean p_239059_1_) {
      this.field_239035_i_.field_230694_p_ = p_239059_1_;
      if (this.field_239039_m_.func_236227_h_()) {
         this.field_239034_h_.field_230694_p_ = false;
         this.field_239027_a_.field_230694_p_ = false;
         this.field_239036_j_.field_230694_p_ = false;
         this.field_239037_k_.field_230694_p_ = false;
      } else {
         this.field_239034_h_.field_230694_p_ = p_239059_1_;
         this.field_239027_a_.field_230694_p_ = p_239059_1_;
         this.field_239036_j_.field_230694_p_ = p_239059_1_ && BiomeGeneratorTypeScreens.field_239069_d_.containsKey(this.field_239040_n_);
         this.field_239037_k_.field_230694_p_ = p_239059_1_;
      }

      this.field_239033_g_.setVisible(p_239059_1_);
   }

   public DynamicRegistries.Impl func_239055_b_() {
      return this.field_239038_l_;
   }

   void func_243447_a(DataPackRegistries p_243447_1_) {
      DynamicRegistries.Impl dynamicregistries$impl = DynamicRegistries.func_239770_b_();
      WorldGenSettingsExport<JsonElement> worldgensettingsexport = WorldGenSettingsExport.func_240896_a_(JsonOps.INSTANCE, this.field_239038_l_);
      WorldSettingsImport<JsonElement> worldsettingsimport = WorldSettingsImport.func_244335_a(JsonOps.INSTANCE, p_243447_1_.func_240970_h_(), dynamicregistries$impl);
      DataResult<DimensionGeneratorSettings> dataresult = DimensionGeneratorSettings.field_236201_a_.encodeStart(worldgensettingsexport, this.field_239039_m_).flatMap((p_243446_1_) -> {
         return DimensionGeneratorSettings.field_236201_a_.parse(worldsettingsimport, p_243446_1_);
      });
      dataresult.resultOrPartial(Util.func_240982_a_("Error parsing worldgen settings after loading data packs: ", field_239028_b_::error)).ifPresent((p_243448_2_) -> {
         this.field_239039_m_ = p_243448_2_;
         this.field_239038_l_ = dynamicregistries$impl;
      });
   }
}