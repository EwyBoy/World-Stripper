package net.minecraft.world.storage;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.command.TimerCallbackManager;
import net.minecraft.command.TimerCallbackSerializers;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.nbt.StringNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.UUIDCodec;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DefaultTypeReferences;
import net.minecraft.util.datafix.codec.DatapackCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.WorldGenSettingsExport;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerWorldInfo implements IServerWorldInfo, IServerConfiguration {
   private static final Logger field_237341_a_ = LogManager.getLogger();
   private WorldSettings field_237342_b_;
   private final DimensionGeneratorSettings field_237343_c_;
   private final Lifecycle field_237344_d_;
   private int field_237345_e_;
   private int field_237346_f_;
   private int field_237347_g_;
   private float field_242984_h;
   private long field_237348_h_;
   private long field_237349_i_;
   @Nullable
   private final DataFixer field_237350_j_;
   private final int field_237351_k_;
   private boolean field_237352_l_;
   @Nullable
   private CompoundNBT field_237353_m_;
   private final int field_237354_n_;
   private int field_237355_o_;
   private boolean field_237356_p_;
   private int field_237357_q_;
   private boolean field_237358_r_;
   private int field_237359_s_;
   private boolean field_237360_t_;
   private boolean field_237361_u_;
   private WorldBorder.Serializer field_237362_v_;
   private CompoundNBT field_237363_w_;
   @Nullable
   private CompoundNBT field_237364_x_;
   private int field_237365_y_;
   private int field_237366_z_;
   @Nullable
   private UUID field_237337_A_;
   private final Set<String> field_237338_B_;
   private boolean field_237339_C_;
   private final TimerCallbackManager<MinecraftServer> field_237340_D_;

   private ServerWorldInfo(@Nullable DataFixer p_i242043_1_, int p_i242043_2_, @Nullable CompoundNBT p_i242043_3_, boolean p_i242043_4_, int p_i242043_5_, int p_i242043_6_, int p_i242043_7_, float p_i242043_8_, long p_i242043_9_, long p_i242043_11_, int p_i242043_13_, int p_i242043_14_, int p_i242043_15_, boolean p_i242043_16_, int p_i242043_17_, boolean p_i242043_18_, boolean p_i242043_19_, boolean p_i242043_20_, WorldBorder.Serializer p_i242043_21_, int p_i242043_22_, int p_i242043_23_, @Nullable UUID p_i242043_24_, LinkedHashSet<String> p_i242043_25_, TimerCallbackManager<MinecraftServer> p_i242043_26_, @Nullable CompoundNBT p_i242043_27_, CompoundNBT p_i242043_28_, WorldSettings p_i242043_29_, DimensionGeneratorSettings p_i242043_30_, Lifecycle p_i242043_31_) {
      this.field_237350_j_ = p_i242043_1_;
      this.field_237339_C_ = p_i242043_4_;
      this.field_237345_e_ = p_i242043_5_;
      this.field_237346_f_ = p_i242043_6_;
      this.field_237347_g_ = p_i242043_7_;
      this.field_242984_h = p_i242043_8_;
      this.field_237348_h_ = p_i242043_9_;
      this.field_237349_i_ = p_i242043_11_;
      this.field_237354_n_ = p_i242043_13_;
      this.field_237355_o_ = p_i242043_14_;
      this.field_237357_q_ = p_i242043_15_;
      this.field_237356_p_ = p_i242043_16_;
      this.field_237359_s_ = p_i242043_17_;
      this.field_237358_r_ = p_i242043_18_;
      this.field_237360_t_ = p_i242043_19_;
      this.field_237361_u_ = p_i242043_20_;
      this.field_237362_v_ = p_i242043_21_;
      this.field_237365_y_ = p_i242043_22_;
      this.field_237366_z_ = p_i242043_23_;
      this.field_237337_A_ = p_i242043_24_;
      this.field_237338_B_ = p_i242043_25_;
      this.field_237353_m_ = p_i242043_3_;
      this.field_237351_k_ = p_i242043_2_;
      this.field_237340_D_ = p_i242043_26_;
      this.field_237364_x_ = p_i242043_27_;
      this.field_237363_w_ = p_i242043_28_;
      this.field_237342_b_ = p_i242043_29_;
      this.field_237343_c_ = p_i242043_30_;
      this.field_237344_d_ = p_i242043_31_;
   }

   public ServerWorldInfo(WorldSettings p_i232158_1_, DimensionGeneratorSettings p_i232158_2_, Lifecycle p_i232158_3_) {
      this((DataFixer)null, SharedConstants.getVersion().getWorldVersion(), (CompoundNBT)null, false, 0, 0, 0, 0.0F, 0L, 0L, 19133, 0, 0, false, 0, false, false, false, WorldBorder.field_235925_b_, 0, 0, (UUID)null, Sets.newLinkedHashSet(), new TimerCallbackManager<>(TimerCallbackSerializers.field_216342_a), (CompoundNBT)null, new CompoundNBT(), p_i232158_1_.func_234959_h_(), p_i232158_2_, p_i232158_3_);
   }

   public static ServerWorldInfo func_237369_a_(Dynamic<INBT> p_237369_0_, DataFixer p_237369_1_, int p_237369_2_, @Nullable CompoundNBT p_237369_3_, WorldSettings p_237369_4_, VersionData p_237369_5_, DimensionGeneratorSettings p_237369_6_, Lifecycle p_237369_7_) {
      long i = p_237369_0_.get("Time").asLong(0L);
      CompoundNBT compoundnbt = (CompoundNBT)p_237369_0_.get("DragonFight").result().map(Dynamic::getValue).orElseGet(() -> {
         return p_237369_0_.get("DimensionData").get("1").get("DragonFight").orElseEmptyMap().getValue();
      });
      return new ServerWorldInfo(p_237369_1_, p_237369_2_, p_237369_3_, p_237369_0_.get("WasModded").asBoolean(false), p_237369_0_.get("SpawnX").asInt(0), p_237369_0_.get("SpawnY").asInt(0), p_237369_0_.get("SpawnZ").asInt(0), p_237369_0_.get("SpawnAngle").asFloat(0.0F), i, p_237369_0_.get("DayTime").asLong(i), p_237369_5_.func_237323_a_(), p_237369_0_.get("clearWeatherTime").asInt(0), p_237369_0_.get("rainTime").asInt(0), p_237369_0_.get("raining").asBoolean(false), p_237369_0_.get("thunderTime").asInt(0), p_237369_0_.get("thundering").asBoolean(false), p_237369_0_.get("initialized").asBoolean(true), p_237369_0_.get("DifficultyLocked").asBoolean(false), WorldBorder.Serializer.func_235938_a_(p_237369_0_, WorldBorder.field_235925_b_), p_237369_0_.get("WanderingTraderSpawnDelay").asInt(0), p_237369_0_.get("WanderingTraderSpawnChance").asInt(0), p_237369_0_.get("WanderingTraderId").read(UUIDCodec.field_239775_a_).result().orElse((UUID)null), p_237369_0_.get("ServerBrands").asStream().flatMap((p_237368_0_) -> {
         return Util.streamOptional(p_237368_0_.asString().result());
      }).collect(Collectors.toCollection(Sets::newLinkedHashSet)), new TimerCallbackManager<>(TimerCallbackSerializers.field_216342_a, p_237369_0_.get("ScheduledEvents").asStream()), (CompoundNBT)p_237369_0_.get("CustomBossEvents").orElseEmptyMap().getValue(), compoundnbt, p_237369_4_, p_237369_6_, p_237369_7_);
   }

   public CompoundNBT func_230411_a_(DynamicRegistries p_230411_1_, @Nullable CompoundNBT p_230411_2_) {
      this.func_237367_I_();
      if (p_230411_2_ == null) {
         p_230411_2_ = this.field_237353_m_;
      }

      CompoundNBT compoundnbt = new CompoundNBT();
      this.func_237370_a_(p_230411_1_, compoundnbt, p_230411_2_);
      return compoundnbt;
   }

   private void func_237370_a_(DynamicRegistries p_237370_1_, CompoundNBT p_237370_2_, @Nullable CompoundNBT p_237370_3_) {
      ListNBT listnbt = new ListNBT();
      this.field_237338_B_.stream().map(StringNBT::valueOf).forEach(listnbt::add);
      p_237370_2_.put("ServerBrands", listnbt);
      p_237370_2_.putBoolean("WasModded", this.field_237339_C_);
      CompoundNBT compoundnbt = new CompoundNBT();
      compoundnbt.putString("Name", SharedConstants.getVersion().getName());
      compoundnbt.putInt("Id", SharedConstants.getVersion().getWorldVersion());
      compoundnbt.putBoolean("Snapshot", !SharedConstants.getVersion().isStable());
      p_237370_2_.put("Version", compoundnbt);
      p_237370_2_.putInt("DataVersion", SharedConstants.getVersion().getWorldVersion());
      WorldGenSettingsExport<INBT> worldgensettingsexport = WorldGenSettingsExport.func_240896_a_(NBTDynamicOps.INSTANCE, p_237370_1_);
      DimensionGeneratorSettings.field_236201_a_.encodeStart(worldgensettingsexport, this.field_237343_c_).resultOrPartial(Util.func_240982_a_("WorldGenSettings: ", field_237341_a_::error)).ifPresent((p_237373_1_) -> {
         p_237370_2_.put("WorldGenSettings", p_237373_1_);
      });
      p_237370_2_.putInt("GameType", this.field_237342_b_.func_234953_b_().getID());
      p_237370_2_.putInt("SpawnX", this.field_237345_e_);
      p_237370_2_.putInt("SpawnY", this.field_237346_f_);
      p_237370_2_.putInt("SpawnZ", this.field_237347_g_);
      p_237370_2_.putFloat("SpawnAngle", this.field_242984_h);
      p_237370_2_.putLong("Time", this.field_237348_h_);
      p_237370_2_.putLong("DayTime", this.field_237349_i_);
      p_237370_2_.putLong("LastPlayed", Util.millisecondsSinceEpoch());
      p_237370_2_.putString("LevelName", this.field_237342_b_.func_234947_a_());
      p_237370_2_.putInt("version", 19133);
      p_237370_2_.putInt("clearWeatherTime", this.field_237355_o_);
      p_237370_2_.putInt("rainTime", this.field_237357_q_);
      p_237370_2_.putBoolean("raining", this.field_237356_p_);
      p_237370_2_.putInt("thunderTime", this.field_237359_s_);
      p_237370_2_.putBoolean("thundering", this.field_237358_r_);
      p_237370_2_.putBoolean("hardcore", this.field_237342_b_.func_234954_c_());
      p_237370_2_.putBoolean("allowCommands", this.field_237342_b_.func_234956_e_());
      p_237370_2_.putBoolean("initialized", this.field_237360_t_);
      this.field_237362_v_.func_235939_a_(p_237370_2_);
      p_237370_2_.putByte("Difficulty", (byte)this.field_237342_b_.func_234955_d_().getId());
      p_237370_2_.putBoolean("DifficultyLocked", this.field_237361_u_);
      p_237370_2_.put("GameRules", this.field_237342_b_.func_234957_f_().write());
      p_237370_2_.put("DragonFight", this.field_237363_w_);
      if (p_237370_3_ != null) {
         p_237370_2_.put("Player", p_237370_3_);
      }

      DatapackCodec.field_234881_b_.encodeStart(NBTDynamicOps.INSTANCE, this.field_237342_b_.func_234958_g_()).result().ifPresent((p_237371_1_) -> {
         p_237370_2_.put("DataPacks", p_237371_1_);
      });
      if (this.field_237364_x_ != null) {
         p_237370_2_.put("CustomBossEvents", this.field_237364_x_);
      }

      p_237370_2_.put("ScheduledEvents", this.field_237340_D_.write());
      p_237370_2_.putInt("WanderingTraderSpawnDelay", this.field_237365_y_);
      p_237370_2_.putInt("WanderingTraderSpawnChance", this.field_237366_z_);
      if (this.field_237337_A_ != null) {
         p_237370_2_.putUniqueId("WanderingTraderId", this.field_237337_A_);
      }

   }

   /**
    * Returns the x spawn position
    */
   public int getSpawnX() {
      return this.field_237345_e_;
   }

   /**
    * Return the Y axis spawning point of the player.
    */
   public int getSpawnY() {
      return this.field_237346_f_;
   }

   /**
    * Returns the z spawn position
    */
   public int getSpawnZ() {
      return this.field_237347_g_;
   }

   public float func_241860_d() {
      return this.field_242984_h;
   }

   public long getGameTime() {
      return this.field_237348_h_;
   }

   /**
    * Get current world time
    */
   public long getDayTime() {
      return this.field_237349_i_;
   }

   private void func_237367_I_() {
      if (!this.field_237352_l_ && this.field_237353_m_ != null) {
         if (this.field_237351_k_ < SharedConstants.getVersion().getWorldVersion()) {
            if (this.field_237350_j_ == null) {
               throw (NullPointerException)Util.pauseDevMode(new NullPointerException("Fixer Upper not set inside LevelData, and the player tag is not upgraded."));
            }

            this.field_237353_m_ = NBTUtil.update(this.field_237350_j_, DefaultTypeReferences.PLAYER, this.field_237353_m_, this.field_237351_k_);
         }

         this.field_237352_l_ = true;
      }
   }

   public CompoundNBT func_230416_x_() {
      this.func_237367_I_();
      return this.field_237353_m_;
   }

   /**
    * Set the x spawn position to the passed in value
    */
   public void setSpawnX(int x) {
      this.field_237345_e_ = x;
   }

   /**
    * Sets the y spawn position
    */
   public void setSpawnY(int y) {
      this.field_237346_f_ = y;
   }

   /**
    * Set the z spawn position to the passed in value
    */
   public void setSpawnZ(int z) {
      this.field_237347_g_ = z;
   }

   public void func_241859_a(float p_241859_1_) {
      this.field_242984_h = p_241859_1_;
   }

   public void setGameTime(long time) {
      this.field_237348_h_ = time;
   }

   /**
    * Set current world time
    */
   public void setDayTime(long time) {
      this.field_237349_i_ = time;
   }

   public void setSpawn(BlockPos spawnPoint, float p_176143_2_) {
      this.field_237345_e_ = spawnPoint.getX();
      this.field_237346_f_ = spawnPoint.getY();
      this.field_237347_g_ = spawnPoint.getZ();
      this.field_242984_h = p_176143_2_;
   }

   /**
    * Get current world name
    */
   public String getWorldName() {
      return this.field_237342_b_.func_234947_a_();
   }

   public int func_230417_y_() {
      return this.field_237354_n_;
   }

   public int func_230395_g_() {
      return this.field_237355_o_;
   }

   public void func_230391_a_(int p_230391_1_) {
      this.field_237355_o_ = p_230391_1_;
   }

   /**
    * Returns true if it is thundering, false otherwise.
    */
   public boolean isThundering() {
      return this.field_237358_r_;
   }

   /**
    * Sets whether it is thundering or not.
    */
   public void setThundering(boolean thunderingIn) {
      this.field_237358_r_ = thunderingIn;
   }

   /**
    * Returns the number of ticks until next thunderbolt.
    */
   public int getThunderTime() {
      return this.field_237359_s_;
   }

   /**
    * Defines the number of ticks until next thunderbolt.
    */
   public void setThunderTime(int time) {
      this.field_237359_s_ = time;
   }

   /**
    * Returns true if it is raining, false otherwise.
    */
   public boolean isRaining() {
      return this.field_237356_p_;
   }

   /**
    * Sets whether it is raining or not.
    */
   public void setRaining(boolean isRaining) {
      this.field_237356_p_ = isRaining;
   }

   /**
    * Return the number of ticks until rain.
    */
   public int getRainTime() {
      return this.field_237357_q_;
   }

   /**
    * Sets the number of ticks until rain.
    */
   public void setRainTime(int time) {
      this.field_237357_q_ = time;
   }

   /**
    * Gets the GameType.
    */
   public GameType getGameType() {
      return this.field_237342_b_.func_234953_b_();
   }

   public void func_230392_a_(GameType p_230392_1_) {
      this.field_237342_b_ = this.field_237342_b_.func_234950_a_(p_230392_1_);
   }

   /**
    * Returns true if hardcore mode is enabled, otherwise false
    */
   public boolean isHardcore() {
      return this.field_237342_b_.func_234954_c_();
   }

   /**
    * Returns true if commands are allowed on this World.
    */
   public boolean areCommandsAllowed() {
      return this.field_237342_b_.func_234956_e_();
   }

   /**
    * Returns true if the World is initialized.
    */
   public boolean isInitialized() {
      return this.field_237360_t_;
   }

   /**
    * Sets the initialization status of the World.
    */
   public void setInitialized(boolean initializedIn) {
      this.field_237360_t_ = initializedIn;
   }

   /**
    * Gets the GameRules class Instance.
    */
   public GameRules getGameRulesInstance() {
      return this.field_237342_b_.func_234957_f_();
   }

   public WorldBorder.Serializer func_230398_q_() {
      return this.field_237362_v_;
   }

   public void func_230393_a_(WorldBorder.Serializer p_230393_1_) {
      this.field_237362_v_ = p_230393_1_;
   }

   public Difficulty getDifficulty() {
      return this.field_237342_b_.func_234955_d_();
   }

   public void func_230409_a_(Difficulty p_230409_1_) {
      this.field_237342_b_ = this.field_237342_b_.func_234948_a_(p_230409_1_);
   }

   public boolean isDifficultyLocked() {
      return this.field_237361_u_;
   }

   public void func_230415_d_(boolean p_230415_1_) {
      this.field_237361_u_ = p_230415_1_;
   }

   public TimerCallbackManager<MinecraftServer> getScheduledEvents() {
      return this.field_237340_D_;
   }

   /**
    * Adds this WorldInfo instance to the crash report.
    */
   public void addToCrashReport(CrashReportCategory category) {
      IServerWorldInfo.super.addToCrashReport(category);
      IServerConfiguration.super.addToCrashReport(category);
   }

   public DimensionGeneratorSettings func_230418_z_() {
      return this.field_237343_c_;
   }

   @OnlyIn(Dist.CLIENT)
   public Lifecycle func_230401_A_() {
      return this.field_237344_d_;
   }

   public CompoundNBT func_230402_B_() {
      return this.field_237363_w_;
   }

   public void func_230413_a_(CompoundNBT p_230413_1_) {
      this.field_237363_w_ = p_230413_1_;
   }

   public DatapackCodec func_230403_C_() {
      return this.field_237342_b_.func_234958_g_();
   }

   public void func_230410_a_(DatapackCodec p_230410_1_) {
      this.field_237342_b_ = this.field_237342_b_.func_234949_a_(p_230410_1_);
   }

   @Nullable
   public CompoundNBT func_230404_D_() {
      return this.field_237364_x_;
   }

   public void func_230414_b_(@Nullable CompoundNBT p_230414_1_) {
      this.field_237364_x_ = p_230414_1_;
   }

   public int func_230399_u_() {
      return this.field_237365_y_;
   }

   public void func_230396_g_(int p_230396_1_) {
      this.field_237365_y_ = p_230396_1_;
   }

   public int func_230400_v_() {
      return this.field_237366_z_;
   }

   public void func_230397_h_(int p_230397_1_) {
      this.field_237366_z_ = p_230397_1_;
   }

   public void func_230394_a_(UUID p_230394_1_) {
      this.field_237337_A_ = p_230394_1_;
   }

   public void func_230412_a_(String p_230412_1_, boolean p_230412_2_) {
      this.field_237338_B_.add(p_230412_1_);
      this.field_237339_C_ |= p_230412_2_;
   }

   public boolean func_230405_E_() {
      return this.field_237339_C_;
   }

   public Set<String> func_230406_F_() {
      return ImmutableSet.copyOf(this.field_237338_B_);
   }

   public IServerWorldInfo func_230407_G_() {
      return this;
   }

   @OnlyIn(Dist.CLIENT)
   public WorldSettings func_230408_H_() {
      return this.field_237342_b_.func_234959_h_();
   }
}