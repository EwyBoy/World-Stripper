package net.minecraft.world;

import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.codec.DatapackCodec;

public final class WorldSettings {
   private final String field_234943_a_;
   private final GameType gameType;
   private final boolean hardcoreEnabled;
   private final Difficulty field_234944_d_;
   private final boolean commandsAllowed;
   private final GameRules field_234945_f_;
   private final DatapackCodec field_234946_g_;

   public WorldSettings(String p_i231620_1_, GameType p_i231620_2_, boolean p_i231620_3_, Difficulty p_i231620_4_, boolean p_i231620_5_, GameRules p_i231620_6_, DatapackCodec p_i231620_7_) {
      this.field_234943_a_ = p_i231620_1_;
      this.gameType = p_i231620_2_;
      this.hardcoreEnabled = p_i231620_3_;
      this.field_234944_d_ = p_i231620_4_;
      this.commandsAllowed = p_i231620_5_;
      this.field_234945_f_ = p_i231620_6_;
      this.field_234946_g_ = p_i231620_7_;
   }

   public static WorldSettings func_234951_a_(Dynamic<?> p_234951_0_, DatapackCodec p_234951_1_) {
      GameType gametype = GameType.getByID(p_234951_0_.get("GameType").asInt(0));
      return new WorldSettings(p_234951_0_.get("LevelName").asString(""), gametype, p_234951_0_.get("hardcore").asBoolean(false), p_234951_0_.get("Difficulty").asNumber().map((p_234952_0_) -> {
         return Difficulty.byId(p_234952_0_.byteValue());
      }).result().orElse(Difficulty.NORMAL), p_234951_0_.get("allowCommands").asBoolean(gametype == GameType.CREATIVE), new GameRules(p_234951_0_.get("GameRules")), p_234951_1_);
   }

   public String func_234947_a_() {
      return this.field_234943_a_;
   }

   public GameType func_234953_b_() {
      return this.gameType;
   }

   public boolean func_234954_c_() {
      return this.hardcoreEnabled;
   }

   public Difficulty func_234955_d_() {
      return this.field_234944_d_;
   }

   public boolean func_234956_e_() {
      return this.commandsAllowed;
   }

   public GameRules func_234957_f_() {
      return this.field_234945_f_;
   }

   public DatapackCodec func_234958_g_() {
      return this.field_234946_g_;
   }

   public WorldSettings func_234950_a_(GameType p_234950_1_) {
      return new WorldSettings(this.field_234943_a_, p_234950_1_, this.hardcoreEnabled, this.field_234944_d_, this.commandsAllowed, this.field_234945_f_, this.field_234946_g_);
   }

   public WorldSettings func_234948_a_(Difficulty p_234948_1_) {
      net.minecraftforge.common.ForgeHooks.onDifficultyChange(p_234948_1_, this.field_234944_d_);
      return new WorldSettings(this.field_234943_a_, this.gameType, this.hardcoreEnabled, p_234948_1_, this.commandsAllowed, this.field_234945_f_, this.field_234946_g_);
   }

   public WorldSettings func_234949_a_(DatapackCodec p_234949_1_) {
      return new WorldSettings(this.field_234943_a_, this.gameType, this.hardcoreEnabled, this.field_234944_d_, this.commandsAllowed, this.field_234945_f_, p_234949_1_);
   }

   public WorldSettings func_234959_h_() {
      return new WorldSettings(this.field_234943_a_, this.gameType, this.hardcoreEnabled, this.field_234944_d_, this.commandsAllowed, this.field_234945_f_.func_234905_b_(), this.field_234946_g_);
   }
}