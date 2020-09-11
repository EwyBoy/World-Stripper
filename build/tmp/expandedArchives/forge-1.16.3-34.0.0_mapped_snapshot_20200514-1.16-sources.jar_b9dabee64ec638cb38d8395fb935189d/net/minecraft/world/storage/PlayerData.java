package net.minecraft.world.storage;

import com.mojang.datafixers.DataFixer;
import java.io.File;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DefaultTypeReferences;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerData {
   private static final Logger field_237332_b_ = LogManager.getLogger();
   private final File field_237333_c_;
   protected final DataFixer field_237331_a_;

   public PlayerData(SaveFormat.LevelSave p_i232157_1_, DataFixer p_i232157_2_) {
      this.field_237331_a_ = p_i232157_2_;
      this.field_237333_c_ = p_i232157_1_.func_237285_a_(FolderName.field_237247_c_).toFile();
      this.field_237333_c_.mkdirs();
   }

   public void func_237335_a_(PlayerEntity p_237335_1_) {
      try {
         CompoundNBT compoundnbt = p_237335_1_.writeWithoutTypeId(new CompoundNBT());
         File file1 = File.createTempFile(p_237335_1_.getCachedUniqueIdString() + "-", ".dat", this.field_237333_c_);
         CompressedStreamTools.func_244264_a(compoundnbt, file1);
         File file2 = new File(this.field_237333_c_, p_237335_1_.getCachedUniqueIdString() + ".dat");
         File file3 = new File(this.field_237333_c_, p_237335_1_.getCachedUniqueIdString() + ".dat_old");
         Util.func_240977_a_(file2, file1, file3);
         net.minecraftforge.event.ForgeEventFactory.firePlayerSavingEvent(p_237335_1_, field_237333_c_, p_237335_1_.getCachedUniqueIdString());
      } catch (Exception exception) {
         field_237332_b_.warn("Failed to save player data for {}", (Object)p_237335_1_.getName().getString());
      }

   }

   @Nullable
   public CompoundNBT func_237336_b_(PlayerEntity p_237336_1_) {
      CompoundNBT compoundnbt = null;

      try {
         File file1 = new File(this.field_237333_c_, p_237336_1_.getCachedUniqueIdString() + ".dat");
         if (file1.exists() && file1.isFile()) {
            compoundnbt = CompressedStreamTools.func_244263_a(file1);
         }
      } catch (Exception exception) {
         field_237332_b_.warn("Failed to load player data for {}", (Object)p_237336_1_.getName().getString());
      }

      if (compoundnbt != null) {
         int i = compoundnbt.contains("DataVersion", 3) ? compoundnbt.getInt("DataVersion") : -1;
         p_237336_1_.read(NBTUtil.update(this.field_237331_a_, DefaultTypeReferences.PLAYER, compoundnbt, i));
      }
      net.minecraftforge.event.ForgeEventFactory.firePlayerLoadingEvent(p_237336_1_, field_237333_c_, p_237336_1_.getCachedUniqueIdString());

      return compoundnbt;
   }

   public String[] func_237334_a_() {
      String[] astring = this.field_237333_c_.list();
      if (astring == null) {
         astring = new String[0];
      }

      for(int i = 0; i < astring.length; ++i) {
         if (astring[i].endsWith(".dat")) {
            astring[i] = astring[i].substring(0, astring[i].length() - 4);
         }
      }

      return astring;
   }

   public File getPlayerDataFolder() {
      return field_237333_c_;
   }
}