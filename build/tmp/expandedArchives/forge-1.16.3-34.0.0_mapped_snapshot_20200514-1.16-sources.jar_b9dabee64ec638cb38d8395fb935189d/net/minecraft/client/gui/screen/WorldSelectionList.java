package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.google.common.hash.Hashing;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.codec.DatapackCodec;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.storage.FolderName;
import net.minecraft.world.storage.SaveFormat;
import net.minecraft.world.storage.WorldSummary;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class WorldSelectionList extends ExtendedList<WorldSelectionList.Entry> {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final DateFormat field_214377_b = new SimpleDateFormat();
   private static final ResourceLocation field_214378_c = new ResourceLocation("textures/misc/unknown_server.png");
   private static final ResourceLocation field_214379_d = new ResourceLocation("textures/gui/world_selection.png");
   private static final ITextComponent field_243462_r = (new TranslationTextComponent("selectWorld.tooltip.fromNewerVersion1")).func_240699_a_(TextFormatting.RED);
   private static final ITextComponent field_243463_s = (new TranslationTextComponent("selectWorld.tooltip.fromNewerVersion2")).func_240699_a_(TextFormatting.RED);
   private static final ITextComponent field_243464_t = (new TranslationTextComponent("selectWorld.tooltip.snapshot1")).func_240699_a_(TextFormatting.GOLD);
   private static final ITextComponent field_243465_u = (new TranslationTextComponent("selectWorld.tooltip.snapshot2")).func_240699_a_(TextFormatting.GOLD);
   private static final ITextComponent field_243466_v = (new TranslationTextComponent("selectWorld.locked")).func_240699_a_(TextFormatting.RED);
   private final WorldSelectionScreen worldSelection;
   @Nullable
   private List<WorldSummary> field_212331_y;

   public WorldSelectionList(WorldSelectionScreen p_i49846_1_, Minecraft p_i49846_2_, int p_i49846_3_, int p_i49846_4_, int p_i49846_5_, int p_i49846_6_, int p_i49846_7_, Supplier<String> p_i49846_8_, @Nullable WorldSelectionList p_i49846_9_) {
      super(p_i49846_2_, p_i49846_3_, p_i49846_4_, p_i49846_5_, p_i49846_6_, p_i49846_7_);
      this.worldSelection = p_i49846_1_;
      if (p_i49846_9_ != null) {
         this.field_212331_y = p_i49846_9_.field_212331_y;
      }

      this.func_212330_a(p_i49846_8_, false);
   }

   public void func_212330_a(Supplier<String> p_212330_1_, boolean p_212330_2_) {
      this.func_230963_j_();
      SaveFormat saveformat = this.field_230668_b_.getSaveLoader();
      if (this.field_212331_y == null || p_212330_2_) {
         try {
            this.field_212331_y = saveformat.getSaveList();
         } catch (AnvilConverterException anvilconverterexception) {
            LOGGER.error("Couldn't load level list", (Throwable)anvilconverterexception);
            this.field_230668_b_.displayGuiScreen(new ErrorScreen(new TranslationTextComponent("selectWorld.unable_to_load"), new StringTextComponent(anvilconverterexception.getMessage())));
            return;
         }

         Collections.sort(this.field_212331_y);
      }

      if (this.field_212331_y.isEmpty()) {
         this.field_230668_b_.displayGuiScreen(CreateWorldScreen.func_243425_a((Screen)null));
      } else {
         String s = p_212330_1_.get().toLowerCase(Locale.ROOT);

         for(WorldSummary worldsummary : this.field_212331_y) {
            if (worldsummary.getDisplayName().toLowerCase(Locale.ROOT).contains(s) || worldsummary.getFileName().toLowerCase(Locale.ROOT).contains(s)) {
               this.func_230513_b_(new WorldSelectionList.Entry(this, worldsummary));
            }
         }

      }
   }

   protected int func_230952_d_() {
      return super.func_230952_d_() + 20;
   }

   public int func_230949_c_() {
      return super.func_230949_c_() + 50;
   }

   protected boolean func_230971_aw__() {
      return this.worldSelection.func_241217_q_() == this;
   }

   public void func_241215_a_(@Nullable WorldSelectionList.Entry p_241215_1_) {
      super.func_241215_a_(p_241215_1_);
      if (p_241215_1_ != null) {
         WorldSummary worldsummary = p_241215_1_.field_214451_d;
         NarratorChatListener.INSTANCE.say((new TranslationTextComponent("narrator.select", new TranslationTextComponent("narrator.select.world", worldsummary.getDisplayName(), new Date(worldsummary.getLastTimePlayed()), worldsummary.isHardcoreModeEnabled() ? new TranslationTextComponent("gameMode.hardcore") : new TranslationTextComponent("gameMode." + worldsummary.getEnumGameType().getName()), worldsummary.getCheatsEnabled() ? new TranslationTextComponent("selectWorld.cheats") : StringTextComponent.field_240750_d_, worldsummary.func_237313_j_()))).getString());
      }

      this.worldSelection.func_214324_a(p_241215_1_ != null && !p_241215_1_.field_214451_d.func_237315_o_());
   }

   protected void func_241219_a_(AbstractList.Ordering p_241219_1_) {
      this.func_241572_a_(p_241219_1_, (p_241652_0_) -> {
         return !p_241652_0_.field_214451_d.func_237315_o_();
      });
   }

   public Optional<WorldSelectionList.Entry> func_214376_a() {
      return Optional.ofNullable(this.func_230958_g_());
   }

   public WorldSelectionScreen getGuiWorldSelection() {
      return this.worldSelection;
   }

   @OnlyIn(Dist.CLIENT)
   public final class Entry extends ExtendedList.AbstractListEntry<WorldSelectionList.Entry> implements AutoCloseable {
      private final Minecraft field_214449_b;
      private final WorldSelectionScreen field_214450_c;
      private final WorldSummary field_214451_d;
      private final ResourceLocation field_214452_e;
      private File field_214453_f;
      @Nullable
      private final DynamicTexture field_214454_g;
      private long field_214455_h;

      public Entry(WorldSelectionList p_i242066_2_, WorldSummary p_i242066_3_) {
         this.field_214450_c = p_i242066_2_.getGuiWorldSelection();
         this.field_214451_d = p_i242066_3_;
         this.field_214449_b = Minecraft.getInstance();
         String s = p_i242066_3_.getFileName();
         this.field_214452_e = new ResourceLocation("minecraft", "worlds/" + Util.func_244361_a(s, ResourceLocation::func_240909_b_) + "/" + Hashing.sha1().hashUnencodedChars(s) + "/icon");
         this.field_214453_f = p_i242066_3_.func_237312_c_();
         if (!this.field_214453_f.isFile()) {
            this.field_214453_f = null;
         }

         this.field_214454_g = this.func_214446_f();
      }

      public void func_230432_a_(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
         String s = this.field_214451_d.getDisplayName();
         String s1 = this.field_214451_d.getFileName() + " (" + WorldSelectionList.field_214377_b.format(new Date(this.field_214451_d.getLastTimePlayed())) + ")";
         if (StringUtils.isEmpty(s)) {
            s = I18n.format("selectWorld.world") + " " + (p_230432_2_ + 1);
         }

         ITextComponent itextcomponent = this.field_214451_d.func_237316_p_();
         this.field_214449_b.fontRenderer.func_238421_b_(p_230432_1_, s, (float)(p_230432_4_ + 32 + 3), (float)(p_230432_3_ + 1), 16777215);
         this.field_214449_b.fontRenderer.func_238421_b_(p_230432_1_, s1, (float)(p_230432_4_ + 32 + 3), (float)(p_230432_3_ + 9 + 3), 8421504);
         this.field_214449_b.fontRenderer.func_243248_b(p_230432_1_, itextcomponent, (float)(p_230432_4_ + 32 + 3), (float)(p_230432_3_ + 9 + 9 + 3), 8421504);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_214449_b.getTextureManager().bindTexture(this.field_214454_g != null ? this.field_214452_e : WorldSelectionList.field_214378_c);
         RenderSystem.enableBlend();
         AbstractGui.func_238463_a_(p_230432_1_, p_230432_4_, p_230432_3_, 0.0F, 0.0F, 32, 32, 32, 32);
         RenderSystem.disableBlend();
         if (this.field_214449_b.gameSettings.touchscreen || p_230432_9_) {
            this.field_214449_b.getTextureManager().bindTexture(WorldSelectionList.field_214379_d);
            AbstractGui.func_238467_a_(p_230432_1_, p_230432_4_, p_230432_3_, p_230432_4_ + 32, p_230432_3_ + 32, -1601138544);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            int i = p_230432_7_ - p_230432_4_;
            boolean flag = i < 32;
            int j = flag ? 32 : 0;
            if (this.field_214451_d.func_237315_o_()) {
               AbstractGui.func_238463_a_(p_230432_1_, p_230432_4_, p_230432_3_, 96.0F, (float)j, 32, 32, 256, 256);
               if (flag) {
                  this.field_214450_c.func_239026_b_(this.field_214449_b.fontRenderer.func_238425_b_(WorldSelectionList.field_243466_v, 175));
               }
            } else if (this.field_214451_d.markVersionInList()) {
               AbstractGui.func_238463_a_(p_230432_1_, p_230432_4_, p_230432_3_, 32.0F, (float)j, 32, 32, 256, 256);
               if (this.field_214451_d.askToOpenWorld()) {
                  AbstractGui.func_238463_a_(p_230432_1_, p_230432_4_, p_230432_3_, 96.0F, (float)j, 32, 32, 256, 256);
                  if (flag) {
                     this.field_214450_c.func_239026_b_(ImmutableList.of(WorldSelectionList.field_243462_r.func_241878_f(), WorldSelectionList.field_243463_s.func_241878_f()));
                  }
               } else if (!SharedConstants.getVersion().isStable()) {
                  AbstractGui.func_238463_a_(p_230432_1_, p_230432_4_, p_230432_3_, 64.0F, (float)j, 32, 32, 256, 256);
                  if (flag) {
                     this.field_214450_c.func_239026_b_(ImmutableList.of(WorldSelectionList.field_243464_t.func_241878_f(), WorldSelectionList.field_243465_u.func_241878_f()));
                  }
               }
            } else {
               AbstractGui.func_238463_a_(p_230432_1_, p_230432_4_, p_230432_3_, 0.0F, (float)j, 32, 32, 256, 256);
            }
         }

      }

      public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
         if (this.field_214451_d.func_237315_o_()) {
            return true;
         } else {
            WorldSelectionList.this.func_241215_a_(this);
            this.field_214450_c.func_214324_a(WorldSelectionList.this.func_214376_a().isPresent());
            if (p_231044_1_ - (double)WorldSelectionList.this.func_230968_n_() <= 32.0D) {
               this.func_214438_a();
               return true;
            } else if (Util.milliTime() - this.field_214455_h < 250L) {
               this.func_214438_a();
               return true;
            } else {
               this.field_214455_h = Util.milliTime();
               return false;
            }
         }
      }

      public void func_214438_a() {
         if (!this.field_214451_d.func_237315_o_()) {
            if (this.field_214451_d.func_197731_n()) {
               ITextComponent itextcomponent = new TranslationTextComponent("selectWorld.backupQuestion");
               ITextComponent itextcomponent1 = new TranslationTextComponent("selectWorld.backupWarning", this.field_214451_d.func_237313_j_(), SharedConstants.getVersion().getName());
               this.field_214449_b.displayGuiScreen(new ConfirmBackupScreen(this.field_214450_c, (p_214436_1_, p_214436_2_) -> {
                  if (p_214436_1_) {
                     String s = this.field_214451_d.getFileName();

                     try (SaveFormat.LevelSave saveformat$levelsave = this.field_214449_b.getSaveLoader().func_237274_c_(s)) {
                        EditWorldScreen.func_239019_a_(saveformat$levelsave);
                     } catch (IOException ioexception) {
                        SystemToast.func_238535_a_(this.field_214449_b, s);
                        WorldSelectionList.LOGGER.error("Failed to backup level {}", s, ioexception);
                     }
                  }

                  this.func_214443_e();
               }, itextcomponent, itextcomponent1, false));
            } else if (this.field_214451_d.askToOpenWorld()) {
               this.field_214449_b.displayGuiScreen(new ConfirmScreen((p_214434_1_) -> {
                  if (p_214434_1_) {
                     try {
                        this.func_214443_e();
                     } catch (Exception exception) {
                        WorldSelectionList.LOGGER.error("Failure to open 'future world'", (Throwable)exception);
                        this.field_214449_b.displayGuiScreen(new AlertScreen(() -> {
                           this.field_214449_b.displayGuiScreen(this.field_214450_c);
                        }, new TranslationTextComponent("selectWorld.futureworld.error.title"), new TranslationTextComponent("selectWorld.futureworld.error.text")));
                     }
                  } else {
                     this.field_214449_b.displayGuiScreen(this.field_214450_c);
                  }

               }, new TranslationTextComponent("selectWorld.versionQuestion"), new TranslationTextComponent("selectWorld.versionWarning", this.field_214451_d.func_237313_j_(), new TranslationTextComponent("selectWorld.versionJoinButton"), DialogTexts.field_240633_d_)));
            } else {
               this.func_214443_e();
            }

         }
      }

      public void func_214442_b() {
         this.field_214449_b.displayGuiScreen(new ConfirmScreen((p_214440_1_) -> {
            if (p_214440_1_) {
               this.field_214449_b.displayGuiScreen(new WorkingScreen());
               SaveFormat saveformat = this.field_214449_b.getSaveLoader();
               String s = this.field_214451_d.getFileName();

               try (SaveFormat.LevelSave saveformat$levelsave = saveformat.func_237274_c_(s)) {
                  saveformat$levelsave.func_237299_g_();
               } catch (IOException ioexception) {
                  SystemToast.func_238538_b_(this.field_214449_b, s);
                  WorldSelectionList.LOGGER.error("Failed to delete world {}", s, ioexception);
               }

               WorldSelectionList.this.func_212330_a(() -> {
                  return this.field_214450_c.field_212352_g.getText();
               }, true);
            }

            this.field_214449_b.displayGuiScreen(this.field_214450_c);
         }, new TranslationTextComponent("selectWorld.deleteQuestion"), new TranslationTextComponent("selectWorld.deleteWarning", this.field_214451_d.getDisplayName()), new TranslationTextComponent("selectWorld.deleteButton"), DialogTexts.field_240633_d_));
      }

      public void func_214444_c() {
         String s = this.field_214451_d.getFileName();

         try {
            SaveFormat.LevelSave saveformat$levelsave = this.field_214449_b.getSaveLoader().func_237274_c_(s);
            this.field_214449_b.displayGuiScreen(new EditWorldScreen((p_239096_3_) -> {
               try {
                  saveformat$levelsave.close();
               } catch (IOException ioexception1) {
                  WorldSelectionList.LOGGER.error("Failed to unlock level {}", s, ioexception1);
               }

               if (p_239096_3_) {
                  WorldSelectionList.this.func_212330_a(() -> {
                     return this.field_214450_c.field_212352_g.getText();
                  }, true);
               }

               this.field_214449_b.displayGuiScreen(this.field_214450_c);
            }, saveformat$levelsave));
         } catch (IOException ioexception) {
            SystemToast.func_238535_a_(this.field_214449_b, s);
            WorldSelectionList.LOGGER.error("Failed to access level {}", s, ioexception);
            WorldSelectionList.this.func_212330_a(() -> {
               return this.field_214450_c.field_212352_g.getText();
            }, true);
         }

      }

      public void func_214445_d() {
         this.func_241653_f_();
         DynamicRegistries.Impl dynamicregistries$impl = DynamicRegistries.func_239770_b_();

         try (
            SaveFormat.LevelSave saveformat$levelsave = this.field_214449_b.getSaveLoader().func_237274_c_(this.field_214451_d.getFileName());
            Minecraft.PackManager minecraft$packmanager = this.field_214449_b.func_238189_a_(dynamicregistries$impl, Minecraft::func_238180_a_, Minecraft::func_238181_a_, false, saveformat$levelsave);
         ) {
            WorldSettings worldsettings = minecraft$packmanager.func_238226_c_().func_230408_H_();
            DatapackCodec datapackcodec = worldsettings.func_234958_g_();
            DimensionGeneratorSettings dimensiongeneratorsettings = minecraft$packmanager.func_238226_c_().func_230418_z_();
            Path path = CreateWorldScreen.func_238943_a_(saveformat$levelsave.func_237285_a_(FolderName.field_237251_g_), this.field_214449_b);
            if (dimensiongeneratorsettings.func_236229_j_()) {
               this.field_214449_b.displayGuiScreen(new ConfirmScreen((p_239095_6_) -> {
                  this.field_214449_b.displayGuiScreen((Screen)(p_239095_6_ ? new CreateWorldScreen(this.field_214450_c, worldsettings, dimensiongeneratorsettings, path, datapackcodec, dynamicregistries$impl) : this.field_214450_c));
               }, new TranslationTextComponent("selectWorld.recreate.customized.title"), new TranslationTextComponent("selectWorld.recreate.customized.text"), DialogTexts.field_240636_g_, DialogTexts.field_240633_d_));
            } else {
               this.field_214449_b.displayGuiScreen(new CreateWorldScreen(this.field_214450_c, worldsettings, dimensiongeneratorsettings, path, datapackcodec, dynamicregistries$impl));
            }
         } catch (Exception exception) {
            WorldSelectionList.LOGGER.error("Unable to recreate world", (Throwable)exception);
            this.field_214449_b.displayGuiScreen(new AlertScreen(() -> {
               this.field_214449_b.displayGuiScreen(this.field_214450_c);
            }, new TranslationTextComponent("selectWorld.recreate.error.title"), new TranslationTextComponent("selectWorld.recreate.error.text")));
         }

      }

      private void func_214443_e() {
         this.field_214449_b.getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         if (this.field_214449_b.getSaveLoader().canLoadWorld(this.field_214451_d.getFileName())) {
            this.func_241653_f_();
            this.field_214449_b.func_238191_a_(this.field_214451_d.getFileName());
         }

      }

      private void func_241653_f_() {
         this.field_214449_b.func_241562_c_(new DirtMessageScreen(new TranslationTextComponent("selectWorld.data_read")));
      }

      @Nullable
      private DynamicTexture func_214446_f() {
         boolean flag = this.field_214453_f != null && this.field_214453_f.isFile();
         if (flag) {
            try (InputStream inputstream = new FileInputStream(this.field_214453_f)) {
               NativeImage nativeimage = NativeImage.read(inputstream);
               Validate.validState(nativeimage.getWidth() == 64, "Must be 64 pixels wide");
               Validate.validState(nativeimage.getHeight() == 64, "Must be 64 pixels high");
               DynamicTexture dynamictexture = new DynamicTexture(nativeimage);
               this.field_214449_b.getTextureManager().loadTexture(this.field_214452_e, dynamictexture);
               return dynamictexture;
            } catch (Throwable throwable) {
               WorldSelectionList.LOGGER.error("Invalid icon for world {}", this.field_214451_d.getFileName(), throwable);
               this.field_214453_f = null;
               return null;
            }
         } else {
            this.field_214449_b.getTextureManager().deleteTexture(this.field_214452_e);
            return null;
         }
      }

      public void close() {
         if (this.field_214454_g != null) {
            this.field_214454_g.close();
         }

      }
   }
}