package net.minecraft.data;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.Util;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SNBTToNBTConverter implements IDataProvider {
   @Nullable
   private static final Path field_240514_b_ = null;
   private static final Logger LOGGER = LogManager.getLogger();
   private final DataGenerator generator;
   private final List<SNBTToNBTConverter.ITransformer> field_225370_d = Lists.newArrayList();

   public SNBTToNBTConverter(DataGenerator generatorIn) {
      this.generator = generatorIn;
   }

   public SNBTToNBTConverter func_225369_a(SNBTToNBTConverter.ITransformer p_225369_1_) {
      this.field_225370_d.add(p_225369_1_);
      return this;
   }

   private CompoundNBT func_225368_a(String p_225368_1_, CompoundNBT p_225368_2_) {
      CompoundNBT compoundnbt = p_225368_2_;

      for(SNBTToNBTConverter.ITransformer snbttonbtconverter$itransformer : this.field_225370_d) {
         compoundnbt = snbttonbtconverter$itransformer.func_225371_a(p_225368_1_, compoundnbt);
      }

      return compoundnbt;
   }

   /**
    * Performs this provider's action.
    */
   public void act(DirectoryCache cache) throws IOException {
      Path path = this.generator.getOutputFolder();
      List<CompletableFuture<SNBTToNBTConverter.TaskResult>> list = Lists.newArrayList();

      for(Path path1 : this.generator.getInputFolders()) {
         Files.walk(path1).filter((p_200422_0_) -> {
            return p_200422_0_.toString().endsWith(".snbt");
         }).forEach((p_229447_3_) -> {
            list.add(CompletableFuture.supplyAsync(() -> {
               return this.func_229446_a_(p_229447_3_, this.getFileName(path1, p_229447_3_));
            }, Util.getServerExecutor()));
         });
      }

      Util.gather(list).join().stream().filter(Objects::nonNull).forEach((p_229445_3_) -> {
         this.func_229444_a_(cache, p_229445_3_, path);
      });
   }

   /**
    * Gets a name for this provider, to use in logging.
    */
   public String getName() {
      return "SNBT -> NBT";
   }

   /**
    * Gets the name of the given SNBT file, based on its path and the input directory. The result does not have the
    * ".snbt" extension.
    */
   private String getFileName(Path inputFolder, Path fileIn) {
      String s = inputFolder.relativize(fileIn).toString().replaceAll("\\\\", "/");
      return s.substring(0, s.length() - ".snbt".length());
   }

   @Nullable
   private SNBTToNBTConverter.TaskResult func_229446_a_(Path p_229446_1_, String p_229446_2_) {
      try (BufferedReader bufferedreader = Files.newBufferedReader(p_229446_1_)) {
         String s = IOUtils.toString((Reader)bufferedreader);
         CompoundNBT compoundnbt = this.func_225368_a(p_229446_2_, JsonToNBT.getTagFromJson(s));
         ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
         CompressedStreamTools.writeCompressed(compoundnbt, bytearrayoutputstream);
         byte[] abyte = bytearrayoutputstream.toByteArray();
         String s1 = HASH_FUNCTION.hashBytes(abyte).toString();
         String s2;
         if (field_240514_b_ != null) {
            s2 = compoundnbt.toFormattedComponent("    ", 0).getString() + "\n";
         } else {
            s2 = null;
         }

         return new SNBTToNBTConverter.TaskResult(p_229446_2_, abyte, s2, s1);
      } catch (CommandSyntaxException commandsyntaxexception) {
         LOGGER.error("Couldn't convert {} from SNBT to NBT at {} as it's invalid SNBT", p_229446_2_, p_229446_1_, commandsyntaxexception);
      } catch (IOException ioexception) {
         LOGGER.error("Couldn't convert {} from SNBT to NBT at {}", p_229446_2_, p_229446_1_, ioexception);
      }

      return null;
   }

   private void func_229444_a_(DirectoryCache p_229444_1_, SNBTToNBTConverter.TaskResult p_229444_2_, Path p_229444_3_) {
      if (p_229444_2_.field_240515_c_ != null) {
         Path path = field_240514_b_.resolve(p_229444_2_.field_229449_a_ + ".snbt");

         try {
            FileUtils.write(path.toFile(), p_229444_2_.field_240515_c_, StandardCharsets.UTF_8);
         } catch (IOException ioexception) {
            LOGGER.error("Couldn't write structure SNBT {} at {}", p_229444_2_.field_229449_a_, path, ioexception);
         }
      }

      Path path1 = p_229444_3_.resolve(p_229444_2_.field_229449_a_ + ".nbt");

      try {
         if (!Objects.equals(p_229444_1_.getPreviousHash(path1), p_229444_2_.field_229451_c_) || !Files.exists(path1)) {
            Files.createDirectories(path1.getParent());

            try (OutputStream outputstream = Files.newOutputStream(path1)) {
               outputstream.write(p_229444_2_.field_229450_b_);
            }
         }

         p_229444_1_.recordHash(path1, p_229444_2_.field_229451_c_);
      } catch (IOException ioexception1) {
         LOGGER.error("Couldn't write structure {} at {}", p_229444_2_.field_229449_a_, path1, ioexception1);
      }

   }

   @FunctionalInterface
   public interface ITransformer {
      CompoundNBT func_225371_a(String p_225371_1_, CompoundNBT p_225371_2_);
   }

   static class TaskResult {
      private final String field_229449_a_;
      private final byte[] field_229450_b_;
      @Nullable
      private final String field_240515_c_;
      private final String field_229451_c_;

      public TaskResult(String p_i232551_1_, byte[] p_i232551_2_, @Nullable String p_i232551_3_, String p_i232551_4_) {
         this.field_229449_a_ = p_i232551_1_;
         this.field_229450_b_ = p_i232551_2_;
         this.field_240515_c_ = p_i232551_3_;
         this.field_229451_c_ = p_i232551_4_;
      }
   }
}