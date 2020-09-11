package net.minecraft.world.storage;

import java.util.UUID;
import net.minecraft.command.TimerCallbackManager;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameType;
import net.minecraft.world.border.WorldBorder;

public interface IServerWorldInfo extends ISpawnWorldInfo {
   /**
    * Get current world name
    */
   String getWorldName();

   /**
    * Sets whether it is thundering or not.
    */
   void setThundering(boolean thunderingIn);

   /**
    * Return the number of ticks until rain.
    */
   int getRainTime();

   /**
    * Sets the number of ticks until rain.
    */
   void setRainTime(int time);

   /**
    * Defines the number of ticks until next thunderbolt.
    */
   void setThunderTime(int time);

   /**
    * Returns the number of ticks until next thunderbolt.
    */
   int getThunderTime();

   /**
    * Adds this WorldInfo instance to the crash report.
    */
   default void addToCrashReport(CrashReportCategory category) {
      ISpawnWorldInfo.super.addToCrashReport(category);
      category.addDetail("Level name", this::getWorldName);
      category.addDetail("Level game mode", () -> {
         return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", this.getGameType().getName(), this.getGameType().getID(), this.isHardcore(), this.areCommandsAllowed());
      });
      category.addDetail("Level weather", () -> {
         return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", this.getRainTime(), this.isRaining(), this.getThunderTime(), this.isThundering());
      });
   }

   int func_230395_g_();

   void func_230391_a_(int p_230391_1_);

   int func_230399_u_();

   void func_230396_g_(int p_230396_1_);

   int func_230400_v_();

   void func_230397_h_(int p_230397_1_);

   void func_230394_a_(UUID p_230394_1_);

   /**
    * Gets the GameType.
    */
   GameType getGameType();

   void func_230393_a_(WorldBorder.Serializer p_230393_1_);

   WorldBorder.Serializer func_230398_q_();

   /**
    * Returns true if the World is initialized.
    */
   boolean isInitialized();

   /**
    * Sets the initialization status of the World.
    */
   void setInitialized(boolean initializedIn);

   /**
    * Returns true if commands are allowed on this World.
    */
   boolean areCommandsAllowed();

   void func_230392_a_(GameType p_230392_1_);

   TimerCallbackManager<MinecraftServer> getScheduledEvents();

   void setGameTime(long time);

   /**
    * Set current world time
    */
   void setDayTime(long time);
}