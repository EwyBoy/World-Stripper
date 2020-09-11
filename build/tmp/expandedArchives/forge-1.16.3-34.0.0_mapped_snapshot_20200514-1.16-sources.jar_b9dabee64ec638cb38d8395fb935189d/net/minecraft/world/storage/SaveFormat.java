package net.minecraft.world.storage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Nullable;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.server.SessionLockManager;
import net.minecraft.util.FileUtil;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.DefaultTypeReferences;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.codec.DatapackCodec;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveFormat {
   private static final Logger field_215785_a = LogManager.getLogger();
   private static final DateTimeFormatter BACKUP_DATE_FORMAT = (new DateTimeFormatterBuilder()).appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD).appendLiteral('-').appendValue(ChronoField.MONTH_OF_YEAR, 2).appendLiteral('-').appendValue(ChronoField.DAY_OF_MONTH, 2).appendLiteral('_').appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral('-').appendValue(ChronoField.MINUTE_OF_HOUR, 2).appendLiteral('-').appendValue(ChronoField.SECOND_OF_MINUTE, 2).toFormatter();
   private static final ImmutableList<String> field_237257_c_ = ImmutableList.of("RandomSeed", "generatorName", "generatorOptions", "generatorVersion", "legacy_custom_options", "MapFeatures", "BonusChest");
   private final Path savesDir;
   private final Path backupsDir;
   private final DataFixer dataFixer;

   public SaveFormat(Path p_i51277_1_, Path p_i51277_2_, DataFixer p_i51277_3_) {
      this.dataFixer = p_i51277_3_;

      try {
         Files.createDirectories(Files.exists(p_i51277_1_) ? p_i51277_1_.toRealPath() : p_i51277_1_);
      } catch (IOException ioexception) {
         throw new RuntimeException(ioexception);
      }

      this.savesDir = p_i51277_1_;
      this.backupsDir = p_i51277_2_;
   }

   public static SaveFormat func_237269_a_(Path p_237269_0_) {
      return new SaveFormat(p_237269_0_, p_237269_0_.resolve("../backups"), DataFixesManager.getDataFixer());
   }

   private static <T> Pair<DimensionGeneratorSettings, Lifecycle> func_237259_a_(Dynamic<T> p_237259_0_, DataFixer p_237259_1_, int p_237259_2_) {
      Dynamic<T> dynamic = p_237259_0_.get("WorldGenSettings").orElseEmptyMap();

      for(String s : field_237257_c_) {
         Optional<? extends Dynamic<?>> optional = p_237259_0_.get(s).result();
         if (optional.isPresent()) {
            dynamic = dynamic.set(s, optional.get());
         }
      }

      Dynamic<T> dynamic1 = p_237259_1_.update(TypeReferences.field_233375_y_, dynamic, p_237259_2_, SharedConstants.getVersion().getWorldVersion());
      DataResult<DimensionGeneratorSettings> dataresult = DimensionGeneratorSettings.field_236201_a_.parse(dynamic1);
      return Pair.of(dataresult.resultOrPartial(Util.func_240982_a_("WorldGenSettings: ", field_215785_a::error)).orElseGet(() -> {
         Registry<DimensionType> registry = RegistryLookupCodec.func_244331_a(Registry.field_239698_ad_).codec().parse(dynamic1).resultOrPartial(Util.func_240982_a_("Dimension type registry: ", field_215785_a::error)).orElseThrow(() -> {
            return new IllegalStateException("Failed to get dimension registry");
         });
         Registry<Biome> registry1 = RegistryLookupCodec.func_244331_a(Registry.field_239720_u_).codec().parse(dynamic1).resultOrPartial(Util.func_240982_a_("Biome registry: ", field_215785_a::error)).orElseThrow(() -> {
            return new IllegalStateException("Failed to get biome registry");
         });
         Registry<DimensionSettings> registry2 = RegistryLookupCodec.func_244331_a(Registry.field_243549_ar).codec().parse(dynamic1).resultOrPartial(Util.func_240982_a_("Noise settings registry: ", field_215785_a::error)).orElseThrow(() -> {
            return new IllegalStateException("Failed to get noise settings registry");
         });
         return DimensionGeneratorSettings.func_242751_a(registry, registry1, registry2);
      }), dataresult.lifecycle());
   }

   private static DatapackCodec func_237258_a_(Dynamic<?> p_237258_0_) {
      return DatapackCodec.field_234881_b_.parse(p_237258_0_).resultOrPartial(field_215785_a::error).orElse(DatapackCodec.field_234880_a_);
   }

   @OnlyIn(Dist.CLIENT)
   public List<WorldSummary> getSaveList() throws AnvilConverterException {
      if (!Files.isDirectory(this.savesDir)) {
         throw new AnvilConverterException((new TranslationTextComponent("selectWorld.load_folder_access")).getString());
      } else {
         List<WorldSummary> list = Lists.newArrayList();
         File[] afile = this.savesDir.toFile().listFiles();

         for(File file1 : afile) {
            if (file1.isDirectory()) {
               boolean flag;
               try {
                  flag = SessionLockManager.func_232999_b_(file1.toPath());
               } catch (Exception exception) {
                  field_215785_a.warn("Failed to read {} lock", file1, exception);
                  continue;
               }

               WorldSummary worldsummary = this.func_237266_a_(file1, this.func_237267_a_(file1, flag));
               if (worldsummary != null) {
                  list.add(worldsummary);
               }
            }
         }

         return list;
      }
   }

   private int func_215782_e() {
      return 19133;
   }

   @Nullable
   private <T> T func_237266_a_(File p_237266_1_, BiFunction<File, DataFixer, T> p_237266_2_) {
      if (!p_237266_1_.exists()) {
         return (T)null;
      } else {
         File file1 = new File(p_237266_1_, "level.dat");
         if (file1.exists()) {
            T t = p_237266_2_.apply(file1, this.dataFixer);
            if (t != null) {
               return t;
            }
         }

         file1 = new File(p_237266_1_, "level.dat_old");
         return (T)(file1.exists() ? p_237266_2_.apply(file1, this.dataFixer) : null);
      }
   }

   @Nullable
   private static DatapackCodec func_237272_b_(File p_237272_0_, DataFixer p_237272_1_) {
      try {
         CompoundNBT compoundnbt = CompressedStreamTools.func_244263_a(p_237272_0_);
         CompoundNBT compoundnbt1 = compoundnbt.getCompound("Data");
         compoundnbt1.remove("Player");
         int i = compoundnbt1.contains("DataVersion", 99) ? compoundnbt1.getInt("DataVersion") : -1;
         Dynamic<INBT> dynamic = p_237272_1_.update(DefaultTypeReferences.LEVEL.func_219816_a(), new Dynamic<>(NBTDynamicOps.INSTANCE, compoundnbt1), i, SharedConstants.getVersion().getWorldVersion());
         return dynamic.get("DataPacks").result().map(SaveFormat::func_237258_a_).orElse(DatapackCodec.field_234880_a_);
      } catch (Exception exception) {
         field_215785_a.error("Exception reading {}", p_237272_0_, exception);
         return null;
      }
   }

   private static BiFunction<File, DataFixer, ServerWorldInfo> func_237270_b_(DynamicOps<INBT> p_237270_0_, DatapackCodec p_237270_1_) {
       return getReader(p_237270_0_, p_237270_1_, null);
   }

   private static BiFunction<File, DataFixer, ServerWorldInfo> getReader(DynamicOps<INBT> p_237270_0_, DatapackCodec p_237270_1_, @Nullable LevelSave levelSave) {
      return (p_242976_2_, p_242976_3_) -> {
         try {
            CompoundNBT compoundnbt = CompressedStreamTools.func_244263_a(p_242976_2_);
            CompoundNBT compoundnbt1 = compoundnbt.getCompound("Data");
            CompoundNBT compoundnbt2 = compoundnbt1.contains("Player", 10) ? compoundnbt1.getCompound("Player") : null;
            compoundnbt1.remove("Player");
            int i = compoundnbt1.contains("DataVersion", 99) ? compoundnbt1.getInt("DataVersion") : -1;
            Dynamic<INBT> dynamic = p_242976_3_.update(DefaultTypeReferences.LEVEL.func_219816_a(), new Dynamic<>(p_237270_0_, compoundnbt1), i, SharedConstants.getVersion().getWorldVersion());
            Pair<DimensionGeneratorSettings, Lifecycle> pair = func_237259_a_(dynamic, p_242976_3_, i);
            VersionData versiondata = VersionData.func_237324_a_(dynamic);
            WorldSettings worldsettings = WorldSettings.func_234951_a_(dynamic, p_237270_1_);
            ServerWorldInfo info = ServerWorldInfo.func_237369_a_(dynamic, p_242976_3_, i, compoundnbt2, worldsettings, versiondata, pair.getFirst(), pair.getSecond());
            if (levelSave != null)
                net.minecraftforge.fml.WorldPersistenceHooks.handleWorldDataLoad(levelSave, info, compoundnbt);
            return info;
         } catch (Exception exception) {
            field_215785_a.error("Exception reading {}", p_242976_2_, exception);
            return null;
         }
      };
   }

   private BiFunction<File, DataFixer, WorldSummary> func_237267_a_(File p_237267_1_, boolean p_237267_2_) {
      return (p_242977_3_, p_242977_4_) -> {
         try {
            CompoundNBT compoundnbt = CompressedStreamTools.func_244263_a(p_242977_3_);
            CompoundNBT compoundnbt1 = compoundnbt.getCompound("Data");
            compoundnbt1.remove("Player");
            int i = compoundnbt1.contains("DataVersion", 99) ? compoundnbt1.getInt("DataVersion") : -1;
            Dynamic<INBT> dynamic = p_242977_4_.update(DefaultTypeReferences.LEVEL.func_219816_a(), new Dynamic<>(NBTDynamicOps.INSTANCE, compoundnbt1), i, SharedConstants.getVersion().getWorldVersion());
            VersionData versiondata = VersionData.func_237324_a_(dynamic);
            int j = versiondata.func_237323_a_();
            if (j != 19132 && j != 19133) {
               return null;
            } else {
               boolean flag = j != this.func_215782_e();
               File file1 = new File(p_237267_1_, "icon.png");
               DatapackCodec datapackcodec = dynamic.get("DataPacks").result().map(SaveFormat::func_237258_a_).orElse(DatapackCodec.field_234880_a_);
               WorldSettings worldsettings = WorldSettings.func_234951_a_(dynamic, datapackcodec);
               return new WorldSummary(worldsettings, versiondata, p_237267_1_.getName(), flag, p_237267_2_, file1);
            }
         } catch (Exception exception) {
            field_215785_a.error("Exception reading {}", p_242977_3_, exception);
            return null;
         }
      };
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isNewLevelIdAcceptable(String saveName) {
      try {
         Path path = this.savesDir.resolve(saveName);
         Files.createDirectory(path);
         Files.deleteIfExists(path);
         return true;
      } catch (IOException ioexception) {
         return false;
      }
   }

   /**
    * Return whether the given world can be loaded.
    */
   @OnlyIn(Dist.CLIENT)
   public boolean canLoadWorld(String saveName) {
      return Files.isDirectory(this.savesDir.resolve(saveName));
   }

   @OnlyIn(Dist.CLIENT)
   public Path getSavesDir() {
      return this.savesDir;
   }

   /**
    * Gets the folder where backups are stored
    */
   @OnlyIn(Dist.CLIENT)
   public Path getBackupsFolder() {
      return this.backupsDir;
   }

   public SaveFormat.LevelSave func_237274_c_(String p_237274_1_) throws IOException {
      return new SaveFormat.LevelSave(p_237274_1_);
   }

   public class LevelSave implements AutoCloseable {
      private final SessionLockManager field_237278_b_;
      private final Path field_237279_c_;
      private final String field_237280_d_;
      private final Map<FolderName, Path> field_237281_e_ = Maps.newHashMap();

      public LevelSave(String p_i232152_2_) throws IOException {
         this.field_237280_d_ = p_i232152_2_;
         this.field_237279_c_ = SaveFormat.this.savesDir.resolve(p_i232152_2_);
         this.field_237278_b_ = SessionLockManager.func_232998_a_(this.field_237279_c_);
      }

      public String func_237282_a_() {
         return this.field_237280_d_;
      }

      public Path func_237285_a_(FolderName p_237285_1_) {
         return this.field_237281_e_.computeIfAbsent(p_237285_1_, (p_237293_1_) -> {
            return this.field_237279_c_.resolve(p_237293_1_.func_237255_a_());
         });
      }

      public File func_237291_a_(RegistryKey<World> p_237291_1_) {
         return DimensionType.func_236031_a_(p_237291_1_, this.field_237279_c_.toFile());
      }

      private void func_237301_i_() {
         if (!this.field_237278_b_.func_232997_a_()) {
            throw new IllegalStateException("Lock is no longer valid");
         }
      }

      public PlayerData func_237292_b_() {
         this.func_237301_i_();
         return new PlayerData(this, SaveFormat.this.dataFixer);
      }

      public boolean func_237295_c_() {
         WorldSummary worldsummary = this.func_237296_d_();
         return worldsummary != null && worldsummary.func_237314_k_().func_237323_a_() != SaveFormat.this.func_215782_e();
      }

      public boolean func_237283_a_(IProgressUpdate p_237283_1_) {
         this.func_237301_i_();
         return AnvilSaveConverter.func_237330_a_(this, p_237283_1_);
      }

      @Nullable
      public WorldSummary func_237296_d_() {
         this.func_237301_i_();
         return SaveFormat.this.func_237266_a_(this.field_237279_c_.toFile(), SaveFormat.this.func_237267_a_(this.field_237279_c_.toFile(), false));
      }

      @Nullable
      public IServerConfiguration func_237284_a_(DynamicOps<INBT> p_237284_1_, DatapackCodec p_237284_2_) {
         this.func_237301_i_();
         return SaveFormat.this.func_237266_a_(this.field_237279_c_.toFile(), SaveFormat.getReader(p_237284_1_, p_237284_2_, this));
      }

      @Nullable
      public DatapackCodec func_237297_e_() {
         this.func_237301_i_();
         return SaveFormat.this.func_237266_a_(this.field_237279_c_.toFile(), (p_237289_0_, p_237289_1_) -> {
            return SaveFormat.func_237272_b_(p_237289_0_, p_237289_1_);
         });
      }

      public void func_237287_a_(DynamicRegistries p_237287_1_, IServerConfiguration p_237287_2_) {
         this.func_237288_a_(p_237287_1_, p_237287_2_, (CompoundNBT)null);
      }

      public void func_237288_a_(DynamicRegistries p_237288_1_, IServerConfiguration p_237288_2_, @Nullable CompoundNBT p_237288_3_) {
         File file1 = this.field_237279_c_.toFile();
         CompoundNBT compoundnbt = p_237288_2_.func_230411_a_(p_237288_1_, p_237288_3_);
         CompoundNBT compoundnbt1 = new CompoundNBT();
         compoundnbt1.put("Data", compoundnbt);

         net.minecraftforge.fml.WorldPersistenceHooks.handleWorldDataSave(this, p_237288_2_, compoundnbt1);

         try {
            File file2 = File.createTempFile("level", ".dat", file1);
            CompressedStreamTools.func_244264_a(compoundnbt1, file2);
            File file3 = new File(file1, "level.dat_old");
            File file4 = new File(file1, "level.dat");
            Util.func_240977_a_(file4, file2, file3);
         } catch (Exception exception) {
            SaveFormat.field_215785_a.error("Failed to save level {}", file1, exception);
         }

      }

      public File func_237298_f_() {
         this.func_237301_i_();
         return this.field_237279_c_.resolve("icon.png").toFile();
      }

      public Path getWorldDir() {
          return field_237279_c_;
      }

      @OnlyIn(Dist.CLIENT)
      public void func_237299_g_() throws IOException {
         this.func_237301_i_();
         final Path path = this.field_237279_c_.resolve("session.lock");

         for(int i = 1; i <= 5; ++i) {
            SaveFormat.field_215785_a.info("Attempt {}...", (int)i);

            try {
               Files.walkFileTree(this.field_237279_c_, new SimpleFileVisitor<Path>() {
                  public FileVisitResult visitFile(Path p_visitFile_1_, BasicFileAttributes p_visitFile_2_) throws IOException {
                     if (!p_visitFile_1_.equals(path)) {
                        SaveFormat.field_215785_a.debug("Deleting {}", (Object)p_visitFile_1_);
                        Files.delete(p_visitFile_1_);
                     }

                     return FileVisitResult.CONTINUE;
                  }

                  public FileVisitResult postVisitDirectory(Path p_postVisitDirectory_1_, IOException p_postVisitDirectory_2_) throws IOException {
                     if (p_postVisitDirectory_2_ != null) {
                        throw p_postVisitDirectory_2_;
                     } else {
                        if (p_postVisitDirectory_1_.equals(LevelSave.this.field_237279_c_)) {
                           LevelSave.this.field_237278_b_.close();
                           Files.deleteIfExists(path);
                        }

                        Files.delete(p_postVisitDirectory_1_);
                        return FileVisitResult.CONTINUE;
                     }
                  }
               });
               break;
            } catch (IOException ioexception) {
               if (i >= 5) {
                  throw ioexception;
               }

               SaveFormat.field_215785_a.warn("Failed to delete {}", this.field_237279_c_, ioexception);

               try {
                  Thread.sleep(500L);
               } catch (InterruptedException interruptedexception) {
               }
            }
         }

      }

      @OnlyIn(Dist.CLIENT)
      public void func_237290_a_(String p_237290_1_) throws IOException {
         this.func_237301_i_();
         File file1 = new File(SaveFormat.this.savesDir.toFile(), this.field_237280_d_);
         if (file1.exists()) {
            File file2 = new File(file1, "level.dat");
            if (file2.exists()) {
               CompoundNBT compoundnbt = CompressedStreamTools.func_244263_a(file2);
               CompoundNBT compoundnbt1 = compoundnbt.getCompound("Data");
               compoundnbt1.putString("LevelName", p_237290_1_);
               CompressedStreamTools.func_244264_a(compoundnbt, file2);
            }

         }
      }

      @OnlyIn(Dist.CLIENT)
      public long func_237300_h_() throws IOException {
         this.func_237301_i_();
         String s = LocalDateTime.now().format(SaveFormat.BACKUP_DATE_FORMAT) + "_" + this.field_237280_d_;
         Path path = SaveFormat.this.getBackupsFolder();

         try {
            Files.createDirectories(Files.exists(path) ? path.toRealPath() : path);
         } catch (IOException ioexception) {
            throw new RuntimeException(ioexception);
         }

         Path path1 = path.resolve(FileUtil.func_214992_a(path, s, ".zip"));

         try (final ZipOutputStream zipoutputstream = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(path1)))) {
            final Path path2 = Paths.get(this.field_237280_d_);
            Files.walkFileTree(this.field_237279_c_, new SimpleFileVisitor<Path>() {
               public FileVisitResult visitFile(Path p_visitFile_1_, BasicFileAttributes p_visitFile_2_) throws IOException {
                  if (p_visitFile_1_.endsWith("session.lock")) {
                     return FileVisitResult.CONTINUE;
                  } else {
                     String s1 = path2.resolve(LevelSave.this.field_237279_c_.relativize(p_visitFile_1_)).toString().replace('\\', '/');
                     ZipEntry zipentry = new ZipEntry(s1);
                     zipoutputstream.putNextEntry(zipentry);
                     com.google.common.io.Files.asByteSource(p_visitFile_1_.toFile()).copyTo(zipoutputstream);
                     zipoutputstream.closeEntry();
                     return FileVisitResult.CONTINUE;
                  }
               }
            });
         }

         return Files.size(path1);
      }

      public void close() throws IOException {
         this.field_237278_b_.close();
      }
   }
}