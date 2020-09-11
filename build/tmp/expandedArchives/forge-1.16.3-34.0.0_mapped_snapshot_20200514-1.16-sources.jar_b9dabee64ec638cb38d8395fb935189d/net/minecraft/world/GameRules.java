package net.minecraft.world;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.serialization.DynamicLike;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SEntityStatusPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameRules {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Map<GameRules.RuleKey<?>, GameRules.RuleType<?>> GAME_RULES = Maps.newTreeMap(Comparator.comparing((p_223597_0_) -> {
      return p_223597_0_.gameRuleName;
   }));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_FIRE_TICK = func_234903_a_("doFireTick", GameRules.Category.UPDATES, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> MOB_GRIEFING = func_234903_a_("mobGriefing", GameRules.Category.MOBS, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> KEEP_INVENTORY = func_234903_a_("keepInventory", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_MOB_SPAWNING = func_234903_a_("doMobSpawning", GameRules.Category.SPAWNING, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_MOB_LOOT = func_234903_a_("doMobLoot", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_TILE_DROPS = func_234903_a_("doTileDrops", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_ENTITY_DROPS = func_234903_a_("doEntityDrops", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> COMMAND_BLOCK_OUTPUT = func_234903_a_("commandBlockOutput", GameRules.Category.CHAT, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> NATURAL_REGENERATION = func_234903_a_("naturalRegeneration", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_DAYLIGHT_CYCLE = func_234903_a_("doDaylightCycle", GameRules.Category.UPDATES, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> LOG_ADMIN_COMMANDS = func_234903_a_("logAdminCommands", GameRules.Category.CHAT, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> SHOW_DEATH_MESSAGES = func_234903_a_("showDeathMessages", GameRules.Category.CHAT, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.IntegerValue> RANDOM_TICK_SPEED = func_234903_a_("randomTickSpeed", GameRules.Category.UPDATES, GameRules.IntegerValue.create(3));
   public static final GameRules.RuleKey<GameRules.BooleanValue> SEND_COMMAND_FEEDBACK = func_234903_a_("sendCommandFeedback", GameRules.Category.CHAT, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> REDUCED_DEBUG_INFO = func_234903_a_("reducedDebugInfo", GameRules.Category.MISC, GameRules.BooleanValue.create(false, (p_223589_0_, p_223589_1_) -> {
      byte b0 = (byte)(p_223589_1_.get() ? 22 : 23);

      for(ServerPlayerEntity serverplayerentity : p_223589_0_.getPlayerList().getPlayers()) {
         serverplayerentity.connection.sendPacket(new SEntityStatusPacket(serverplayerentity, b0));
      }

   }));
   public static final GameRules.RuleKey<GameRules.BooleanValue> SPECTATORS_GENERATE_CHUNKS = func_234903_a_("spectatorsGenerateChunks", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.IntegerValue> SPAWN_RADIUS = func_234903_a_("spawnRadius", GameRules.Category.PLAYER, GameRules.IntegerValue.create(10));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DISABLE_ELYTRA_MOVEMENT_CHECK = func_234903_a_("disableElytraMovementCheck", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
   public static final GameRules.RuleKey<GameRules.IntegerValue> MAX_ENTITY_CRAMMING = func_234903_a_("maxEntityCramming", GameRules.Category.MOBS, GameRules.IntegerValue.create(24));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_WEATHER_CYCLE = func_234903_a_("doWeatherCycle", GameRules.Category.UPDATES, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_LIMITED_CRAFTING = func_234903_a_("doLimitedCrafting", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
   public static final GameRules.RuleKey<GameRules.IntegerValue> MAX_COMMAND_CHAIN_LENGTH = func_234903_a_("maxCommandChainLength", GameRules.Category.MISC, GameRules.IntegerValue.create(65536));
   public static final GameRules.RuleKey<GameRules.BooleanValue> ANNOUNCE_ADVANCEMENTS = func_234903_a_("announceAdvancements", GameRules.Category.CHAT, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DISABLE_RAIDS = func_234903_a_("disableRaids", GameRules.Category.MOBS, GameRules.BooleanValue.create(false));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_INSOMNIA = func_234903_a_("doInsomnia", GameRules.Category.SPAWNING, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_IMMEDIATE_RESPAWN = func_234903_a_("doImmediateRespawn", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false, (p_226686_0_, p_226686_1_) -> {
      for(ServerPlayerEntity serverplayerentity : p_226686_0_.getPlayerList().getPlayers()) {
         serverplayerentity.connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241775_l_, p_226686_1_.get() ? 1.0F : 0.0F));
      }

   }));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DROWNING_DAMAGE = func_234903_a_("drowningDamage", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> FALL_DAMAGE = func_234903_a_("fallDamage", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> FIRE_DAMAGE = func_234903_a_("fireDamage", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> field_230127_D_ = func_234903_a_("doPatrolSpawning", GameRules.Category.SPAWNING, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> field_230128_E_ = func_234903_a_("doTraderSpawning", GameRules.Category.SPAWNING, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> field_234895_F_ = func_234903_a_("forgiveDeadPlayers", GameRules.Category.MOBS, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> field_234896_G_ = func_234903_a_("universalAnger", GameRules.Category.MOBS, GameRules.BooleanValue.create(false));
   private final Map<GameRules.RuleKey<?>, GameRules.RuleValue<?>> rules;

   public static <T extends GameRules.RuleValue<T>> GameRules.RuleKey<T> func_234903_a_(String p_234903_0_, GameRules.Category p_234903_1_, GameRules.RuleType<T> p_234903_2_) {
      GameRules.RuleKey<T> rulekey = new GameRules.RuleKey<>(p_234903_0_, p_234903_1_);
      GameRules.RuleType<?> ruletype = GAME_RULES.put(rulekey, p_234903_2_);
      if (ruletype != null) {
         throw new IllegalStateException("Duplicate game rule registration for " + p_234903_0_);
      } else {
         return rulekey;
      }
   }

   public GameRules(DynamicLike<?> p_i231611_1_) {
      this();
      this.func_234901_a_(p_i231611_1_);
   }

   public GameRules() {
      this.rules = GAME_RULES.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, (p_226684_0_) -> {
         return p_226684_0_.getValue().createValue();
      }));
   }

   private GameRules(Map<GameRules.RuleKey<?>, GameRules.RuleValue<?>> p_i231612_1_) {
      this.rules = p_i231612_1_;
   }

   public <T extends GameRules.RuleValue<T>> T get(GameRules.RuleKey<T> key) {
      return (T)(this.rules.get(key));
   }

   /**
    * Return the defined game rules as NBT.
    */
   public CompoundNBT write() {
      CompoundNBT compoundnbt = new CompoundNBT();
      this.rules.forEach((p_226688_1_, p_226688_2_) -> {
         compoundnbt.putString(p_226688_1_.gameRuleName, p_226688_2_.stringValue());
      });
      return compoundnbt;
   }

   private void func_234901_a_(DynamicLike<?> p_234901_1_) {
      this.rules.forEach((p_234902_1_, p_234902_2_) -> {
         p_234901_1_.get(p_234902_1_.gameRuleName).asString().result().ifPresent(p_234902_2_::setStringValue);
      });
   }

   public GameRules func_234905_b_() {
      return new GameRules(this.rules.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, (p_234904_0_) -> {
         return p_234904_0_.getValue().func_230314_f_();
      })));
   }

   public static void visitAll(GameRules.IRuleEntryVisitor visitor) {
      GAME_RULES.forEach((p_234906_1_, p_234906_2_) -> {
         func_234897_a_(visitor, p_234906_1_, p_234906_2_);
      });
   }

   private static <T extends GameRules.RuleValue<T>> void func_234897_a_(GameRules.IRuleEntryVisitor p_234897_0_, GameRules.RuleKey<?> p_234897_1_, GameRules.RuleType<?> p_234897_2_) {
      p_234897_0_.visit((GameRules.RuleKey)p_234897_1_, p_234897_2_);
      p_234897_2_.func_234914_a_(p_234897_0_,(GameRules.RuleKey) p_234897_1_);
   }

   @OnlyIn(Dist.CLIENT)
   public void func_234899_a_(GameRules p_234899_1_, @Nullable MinecraftServer p_234899_2_) {
      p_234899_1_.rules.keySet().forEach((p_234900_3_) -> {
         this.func_234898_a_(p_234900_3_, p_234899_1_, p_234899_2_);
      });
   }

   @OnlyIn(Dist.CLIENT)
   private <T extends GameRules.RuleValue<T>> void func_234898_a_(GameRules.RuleKey<T> p_234898_1_, GameRules p_234898_2_, @Nullable MinecraftServer p_234898_3_) {
      T t = p_234898_2_.get(p_234898_1_);
      this.<T>get(p_234898_1_).func_230313_a_(t, p_234898_3_);
   }

   public boolean getBoolean(GameRules.RuleKey<GameRules.BooleanValue> key) {
      return this.get(key).get();
   }

   public int getInt(GameRules.RuleKey<GameRules.IntegerValue> key) {
      return this.get(key).get();
   }

   public static class BooleanValue extends GameRules.RuleValue<GameRules.BooleanValue> {
      private boolean value;

      private static GameRules.RuleType<GameRules.BooleanValue> create(boolean defaultValue, BiConsumer<MinecraftServer, GameRules.BooleanValue> changeListener) {
         return new GameRules.RuleType<>(BoolArgumentType::bool, (p_223574_1_) -> {
            return new GameRules.BooleanValue(p_223574_1_, defaultValue);
         }, changeListener, GameRules.IRuleEntryVisitor::func_230482_b_);
      }

      private static GameRules.RuleType<GameRules.BooleanValue> create(boolean defaultValue) {
         return create(defaultValue, (p_223569_0_, p_223569_1_) -> {
         });
      }

      public BooleanValue(GameRules.RuleType<GameRules.BooleanValue> type, boolean defaultValue) {
         super(type);
         this.value = defaultValue;
      }

      protected void updateValue0(CommandContext<CommandSource> context, String paramName) {
         this.value = BoolArgumentType.getBool(context, paramName);
      }

      public boolean get() {
         return this.value;
      }

      public void set(boolean valueIn, @Nullable MinecraftServer server) {
         this.value = valueIn;
         this.notifyChange(server);
      }

      public String stringValue() {
         return Boolean.toString(this.value);
      }

      protected void setStringValue(String valueIn) {
         this.value = Boolean.parseBoolean(valueIn);
      }

      public int intValue() {
         return this.value ? 1 : 0;
      }

      protected GameRules.BooleanValue getValue() {
         return this;
      }

      protected GameRules.BooleanValue func_230314_f_() {
         return new GameRules.BooleanValue(this.type, this.value);
      }

      @OnlyIn(Dist.CLIENT)
      public void func_230313_a_(GameRules.BooleanValue p_230313_1_, @Nullable MinecraftServer p_230313_2_) {
         this.value = p_230313_1_.value;
         this.notifyChange(p_230313_2_);
      }
   }

   public static enum Category {
      PLAYER("gamerule.category.player"),
      MOBS("gamerule.category.mobs"),
      SPAWNING("gamerule.category.spawning"),
      DROPS("gamerule.category.drops"),
      UPDATES("gamerule.category.updates"),
      CHAT("gamerule.category.chat"),
      MISC("gamerule.category.misc");

      private final String field_234907_h_;

      private Category(String p_i231613_3_) {
         this.field_234907_h_ = p_i231613_3_;
      }

      @OnlyIn(Dist.CLIENT)
      public String func_234908_a_() {
         return this.field_234907_h_;
      }
   }

   interface IRule<T extends GameRules.RuleValue<T>> {
      void call(GameRules.IRuleEntryVisitor p_call_1_, GameRules.RuleKey<T> p_call_2_, GameRules.RuleType<T> p_call_3_);
   }

   public interface IRuleEntryVisitor {
      default <T extends GameRules.RuleValue<T>> void visit(GameRules.RuleKey<T> key, GameRules.RuleType<T> type) {
      }

      default void func_230482_b_(GameRules.RuleKey<GameRules.BooleanValue> p_230482_1_, GameRules.RuleType<GameRules.BooleanValue> p_230482_2_) {
      }

      default void func_230483_c_(GameRules.RuleKey<GameRules.IntegerValue> p_230483_1_, GameRules.RuleType<GameRules.IntegerValue> p_230483_2_) {
      }
   }

   public static class IntegerValue extends GameRules.RuleValue<GameRules.IntegerValue> {
      private int value;

      private static GameRules.RuleType<GameRules.IntegerValue> create(int defaultValue, BiConsumer<MinecraftServer, GameRules.IntegerValue> changeListener) {
         return new GameRules.RuleType<>(IntegerArgumentType::integer, (p_223565_1_) -> {
            return new GameRules.IntegerValue(p_223565_1_, defaultValue);
         }, changeListener, GameRules.IRuleEntryVisitor::func_230483_c_);
      }

      private static GameRules.RuleType<GameRules.IntegerValue> create(int defaultValue) {
         return create(defaultValue, (p_223561_0_, p_223561_1_) -> {
         });
      }

      public IntegerValue(GameRules.RuleType<GameRules.IntegerValue> type, int defaultValue) {
         super(type);
         this.value = defaultValue;
      }

      protected void updateValue0(CommandContext<CommandSource> context, String paramName) {
         this.value = IntegerArgumentType.getInteger(context, paramName);
      }

      public int get() {
         return this.value;
      }

      public String stringValue() {
         return Integer.toString(this.value);
      }

      protected void setStringValue(String valueIn) {
         this.value = parseInt(valueIn);
      }

      @OnlyIn(Dist.CLIENT)
      public boolean func_234909_b_(String p_234909_1_) {
         try {
            this.value = Integer.parseInt(p_234909_1_);
            return true;
         } catch (NumberFormatException numberformatexception) {
            return false;
         }
      }

      private static int parseInt(String strValue) {
         if (!strValue.isEmpty()) {
            try {
               return Integer.parseInt(strValue);
            } catch (NumberFormatException numberformatexception) {
               GameRules.LOGGER.warn("Failed to parse integer {}", (Object)strValue);
            }
         }

         return 0;
      }

      public int intValue() {
         return this.value;
      }

      protected GameRules.IntegerValue getValue() {
         return this;
      }

      protected GameRules.IntegerValue func_230314_f_() {
         return new GameRules.IntegerValue(this.type, this.value);
      }

      @OnlyIn(Dist.CLIENT)
      public void func_230313_a_(GameRules.IntegerValue p_230313_1_, @Nullable MinecraftServer p_230313_2_) {
         this.value = p_230313_1_.value;
         this.notifyChange(p_230313_2_);
      }
   }

   public static final class RuleKey<T extends GameRules.RuleValue<T>> {
      private final String gameRuleName;
      private final GameRules.Category field_234910_b_;

      public RuleKey(String p_i231614_1_, GameRules.Category p_i231614_2_) {
         this.gameRuleName = p_i231614_1_;
         this.field_234910_b_ = p_i231614_2_;
      }

      public String toString() {
         return this.gameRuleName;
      }

      public boolean equals(Object p_equals_1_) {
         if (this == p_equals_1_) {
            return true;
         } else {
            return p_equals_1_ instanceof GameRules.RuleKey && ((GameRules.RuleKey)p_equals_1_).gameRuleName.equals(this.gameRuleName);
         }
      }

      public int hashCode() {
         return this.gameRuleName.hashCode();
      }

      public String getName() {
         return this.gameRuleName;
      }

      public String func_234911_b_() {
         return "gamerule." + this.gameRuleName;
      }

      @OnlyIn(Dist.CLIENT)
      public GameRules.Category func_234912_c_() {
         return this.field_234910_b_;
      }
   }

   public static class RuleType<T extends GameRules.RuleValue<T>> {
      private final Supplier<ArgumentType<?>> argTypeSupplier;
      private final Function<GameRules.RuleType<T>, T> valueCreator;
      private final BiConsumer<MinecraftServer, T> changeListener;
      private final GameRules.IRule<T> field_234913_d_;

      private RuleType(Supplier<ArgumentType<?>> p_i231615_1_, Function<GameRules.RuleType<T>, T> p_i231615_2_, BiConsumer<MinecraftServer, T> p_i231615_3_, GameRules.IRule<T> p_i231615_4_) {
         this.argTypeSupplier = p_i231615_1_;
         this.valueCreator = p_i231615_2_;
         this.changeListener = p_i231615_3_;
         this.field_234913_d_ = p_i231615_4_;
      }

      public RequiredArgumentBuilder<CommandSource, ?> createArgument(String name) {
         return Commands.argument(name, this.argTypeSupplier.get());
      }

      public T createValue() {
         return this.valueCreator.apply(this);
      }

      public void func_234914_a_(GameRules.IRuleEntryVisitor p_234914_1_, GameRules.RuleKey<T> p_234914_2_) {
         this.field_234913_d_.call(p_234914_1_, p_234914_2_, this);
      }
   }

   public abstract static class RuleValue<T extends GameRules.RuleValue<T>> {
      protected final GameRules.RuleType<T> type;

      public RuleValue(GameRules.RuleType<T> type) {
         this.type = type;
      }

      protected abstract void updateValue0(CommandContext<CommandSource> context, String paramName);

      public void updateValue(CommandContext<CommandSource> context, String paramName) {
         this.updateValue0(context, paramName);
         this.notifyChange(context.getSource().getServer());
      }

      protected void notifyChange(@Nullable MinecraftServer server) {
         if (server != null) {
            this.type.changeListener.accept(server, this.getValue());
         }

      }

      protected abstract void setStringValue(String valueIn);

      public abstract String stringValue();

      public String toString() {
         return this.stringValue();
      }

      public abstract int intValue();

      protected abstract T getValue();

      protected abstract T func_230314_f_();

      @OnlyIn(Dist.CLIENT)
      public abstract void func_230313_a_(T p_230313_1_, @Nullable MinecraftServer p_230313_2_);
   }
}