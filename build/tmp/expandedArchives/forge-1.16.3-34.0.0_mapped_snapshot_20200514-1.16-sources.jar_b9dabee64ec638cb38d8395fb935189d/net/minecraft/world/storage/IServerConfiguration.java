package net.minecraft.world.storage;

import com.mojang.serialization.Lifecycle;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.datafix.codec.DatapackCodec;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IServerConfiguration {
   DatapackCodec func_230403_C_();

   void func_230410_a_(DatapackCodec p_230410_1_);

   boolean func_230405_E_();

   Set<String> func_230406_F_();

   void func_230412_a_(String p_230412_1_, boolean p_230412_2_);

   /**
    * Adds this WorldInfo instance to the crash report.
    */
   default void addToCrashReport(CrashReportCategory category) {
      category.addDetail("Known server brands", () -> {
         return String.join(", ", this.func_230406_F_());
      });
      category.addDetail("Level was modded", () -> {
         return Boolean.toString(this.func_230405_E_());
      });
      category.addDetail("Level storage version", () -> {
         int i = this.func_230417_y_();
         return String.format("0x%05X - %s", i, this.func_237379_i_(i));
      });
   }

   default String func_237379_i_(int p_237379_1_) {
      switch(p_237379_1_) {
      case 19132:
         return "McRegion";
      case 19133:
         return "Anvil";
      default:
         return "Unknown?";
      }
   }

   @Nullable
   CompoundNBT func_230404_D_();

   void func_230414_b_(@Nullable CompoundNBT p_230414_1_);

   IServerWorldInfo func_230407_G_();

   @OnlyIn(Dist.CLIENT)
   WorldSettings func_230408_H_();

   CompoundNBT func_230411_a_(DynamicRegistries p_230411_1_, @Nullable CompoundNBT p_230411_2_);

   /**
    * Returns true if hardcore mode is enabled, otherwise false
    */
   boolean isHardcore();

   int func_230417_y_();

   /**
    * Get current world name
    */
   String getWorldName();

   /**
    * Gets the GameType.
    */
   GameType getGameType();

   void func_230392_a_(GameType p_230392_1_);

   /**
    * Returns true if commands are allowed on this World.
    */
   boolean areCommandsAllowed();

   Difficulty getDifficulty();

   void func_230409_a_(Difficulty p_230409_1_);

   boolean isDifficultyLocked();

   void func_230415_d_(boolean p_230415_1_);

   /**
    * Gets the GameRules class Instance.
    */
   GameRules getGameRulesInstance();

   CompoundNBT func_230416_x_();

   CompoundNBT func_230402_B_();

   void func_230413_a_(CompoundNBT p_230413_1_);

   DimensionGeneratorSettings func_230418_z_();

   @OnlyIn(Dist.CLIENT)
   Lifecycle func_230401_A_();
}