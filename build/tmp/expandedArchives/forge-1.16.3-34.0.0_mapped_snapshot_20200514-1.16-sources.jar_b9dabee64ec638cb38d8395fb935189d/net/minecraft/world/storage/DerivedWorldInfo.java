package net.minecraft.world.storage;

import java.util.UUID;
import net.minecraft.command.TimerCallbackManager;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.border.WorldBorder;

public class DerivedWorldInfo implements IServerWorldInfo {
   private final IServerConfiguration field_237244_a_;
   private final IServerWorldInfo delegate;

   public DerivedWorldInfo(IServerConfiguration p_i232150_1_, IServerWorldInfo p_i232150_2_) {
      this.field_237244_a_ = p_i232150_1_;
      this.delegate = p_i232150_2_;
   }

   /**
    * Returns the x spawn position
    */
   public int getSpawnX() {
      return this.delegate.getSpawnX();
   }

   /**
    * Return the Y axis spawning point of the player.
    */
   public int getSpawnY() {
      return this.delegate.getSpawnY();
   }

   /**
    * Returns the z spawn position
    */
   public int getSpawnZ() {
      return this.delegate.getSpawnZ();
   }

   public float func_241860_d() {
      return this.delegate.func_241860_d();
   }

   public long getGameTime() {
      return this.delegate.getGameTime();
   }

   /**
    * Get current world time
    */
   public long getDayTime() {
      return this.delegate.getDayTime();
   }

   /**
    * Get current world name
    */
   public String getWorldName() {
      return this.field_237244_a_.getWorldName();
   }

   public int func_230395_g_() {
      return this.delegate.func_230395_g_();
   }

   public void func_230391_a_(int p_230391_1_) {
   }

   /**
    * Returns true if it is thundering, false otherwise.
    */
   public boolean isThundering() {
      return this.delegate.isThundering();
   }

   /**
    * Returns the number of ticks until next thunderbolt.
    */
   public int getThunderTime() {
      return this.delegate.getThunderTime();
   }

   /**
    * Returns true if it is raining, false otherwise.
    */
   public boolean isRaining() {
      return this.delegate.isRaining();
   }

   /**
    * Return the number of ticks until rain.
    */
   public int getRainTime() {
      return this.delegate.getRainTime();
   }

   /**
    * Gets the GameType.
    */
   public GameType getGameType() {
      return this.field_237244_a_.getGameType();
   }

   /**
    * Set the x spawn position to the passed in value
    */
   public void setSpawnX(int x) {
   }

   /**
    * Sets the y spawn position
    */
   public void setSpawnY(int y) {
   }

   /**
    * Set the z spawn position to the passed in value
    */
   public void setSpawnZ(int z) {
   }

   public void func_241859_a(float p_241859_1_) {
   }

   public void setGameTime(long time) {
   }

   /**
    * Set current world time
    */
   public void setDayTime(long time) {
   }

   public void setSpawn(BlockPos spawnPoint, float p_176143_2_) {
   }

   /**
    * Sets whether it is thundering or not.
    */
   public void setThundering(boolean thunderingIn) {
   }

   /**
    * Defines the number of ticks until next thunderbolt.
    */
   public void setThunderTime(int time) {
   }

   /**
    * Sets whether it is raining or not.
    */
   public void setRaining(boolean isRaining) {
   }

   /**
    * Sets the number of ticks until rain.
    */
   public void setRainTime(int time) {
   }

   public void func_230392_a_(GameType p_230392_1_) {
   }

   /**
    * Returns true if hardcore mode is enabled, otherwise false
    */
   public boolean isHardcore() {
      return this.field_237244_a_.isHardcore();
   }

   /**
    * Returns true if commands are allowed on this World.
    */
   public boolean areCommandsAllowed() {
      return this.field_237244_a_.areCommandsAllowed();
   }

   /**
    * Returns true if the World is initialized.
    */
   public boolean isInitialized() {
      return this.delegate.isInitialized();
   }

   /**
    * Sets the initialization status of the World.
    */
   public void setInitialized(boolean initializedIn) {
   }

   /**
    * Gets the GameRules class Instance.
    */
   public GameRules getGameRulesInstance() {
      return this.field_237244_a_.getGameRulesInstance();
   }

   public WorldBorder.Serializer func_230398_q_() {
      return this.delegate.func_230398_q_();
   }

   public void func_230393_a_(WorldBorder.Serializer p_230393_1_) {
   }

   public Difficulty getDifficulty() {
      return this.field_237244_a_.getDifficulty();
   }

   public boolean isDifficultyLocked() {
      return this.field_237244_a_.isDifficultyLocked();
   }

   public TimerCallbackManager<MinecraftServer> getScheduledEvents() {
      return this.delegate.getScheduledEvents();
   }

   public int func_230399_u_() {
      return 0;
   }

   public void func_230396_g_(int p_230396_1_) {
   }

   public int func_230400_v_() {
      return 0;
   }

   public void func_230397_h_(int p_230397_1_) {
   }

   public void func_230394_a_(UUID p_230394_1_) {
   }

   /**
    * Adds this WorldInfo instance to the crash report.
    */
   public void addToCrashReport(CrashReportCategory category) {
      category.addDetail("Derived", true);
      this.delegate.addToCrashReport(category);
   }
}