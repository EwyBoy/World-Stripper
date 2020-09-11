package net.minecraft.data;

import javax.annotation.Nullable;

public final class StockTextureAliases {
   public static final StockTextureAliases field_240404_a_ = func_240431_a_("all");
   public static final StockTextureAliases field_240405_b_ = func_240432_a_("texture", field_240404_a_);
   public static final StockTextureAliases field_240406_c_ = func_240432_a_("particle", field_240405_b_);
   public static final StockTextureAliases field_240407_d_ = func_240432_a_("end", field_240404_a_);
   public static final StockTextureAliases field_240408_e_ = func_240432_a_("bottom", field_240407_d_);
   public static final StockTextureAliases field_240409_f_ = func_240432_a_("top", field_240407_d_);
   public static final StockTextureAliases field_240410_g_ = func_240432_a_("front", field_240404_a_);
   public static final StockTextureAliases field_240411_h_ = func_240432_a_("back", field_240404_a_);
   public static final StockTextureAliases field_240412_i_ = func_240432_a_("side", field_240404_a_);
   public static final StockTextureAliases field_240413_j_ = func_240432_a_("north", field_240412_i_);
   public static final StockTextureAliases field_240414_k_ = func_240432_a_("south", field_240412_i_);
   public static final StockTextureAliases field_240415_l_ = func_240432_a_("east", field_240412_i_);
   public static final StockTextureAliases field_240416_m_ = func_240432_a_("west", field_240412_i_);
   public static final StockTextureAliases field_240417_n_ = func_240431_a_("up");
   public static final StockTextureAliases field_240418_o_ = func_240431_a_("down");
   public static final StockTextureAliases field_240419_p_ = func_240431_a_("cross");
   public static final StockTextureAliases field_240420_q_ = func_240431_a_("plant");
   public static final StockTextureAliases field_240421_r_ = func_240432_a_("wall", field_240404_a_);
   public static final StockTextureAliases field_240422_s_ = func_240431_a_("rail");
   public static final StockTextureAliases field_240423_t_ = func_240431_a_("wool");
   public static final StockTextureAliases field_240424_u_ = func_240431_a_("pattern");
   public static final StockTextureAliases field_240425_v_ = func_240431_a_("pane");
   public static final StockTextureAliases field_240426_w_ = func_240431_a_("edge");
   public static final StockTextureAliases field_240427_x_ = func_240431_a_("fan");
   public static final StockTextureAliases field_240428_y_ = func_240431_a_("stem");
   public static final StockTextureAliases field_240429_z_ = func_240431_a_("upperstem");
   public static final StockTextureAliases field_240393_A_ = func_240431_a_("crop");
   public static final StockTextureAliases field_240394_B_ = func_240431_a_("dirt");
   public static final StockTextureAliases field_240395_C_ = func_240431_a_("fire");
   public static final StockTextureAliases field_240396_D_ = func_240431_a_("lantern");
   public static final StockTextureAliases field_240397_E_ = func_240431_a_("platform");
   public static final StockTextureAliases field_240398_F_ = func_240431_a_("unsticky");
   public static final StockTextureAliases field_240399_G_ = func_240431_a_("torch");
   public static final StockTextureAliases field_240400_H_ = func_240431_a_("layer0");
   public static final StockTextureAliases field_240401_I_ = func_240431_a_("lit_log");
   private final String field_240402_J_;
   @Nullable
   private final StockTextureAliases field_240403_K_;

   private static StockTextureAliases func_240431_a_(String p_240431_0_) {
      return new StockTextureAliases(p_240431_0_, (StockTextureAliases)null);
   }

   private static StockTextureAliases func_240432_a_(String p_240432_0_, StockTextureAliases p_240432_1_) {
      return new StockTextureAliases(p_240432_0_, p_240432_1_);
   }

   private StockTextureAliases(String p_i232547_1_, @Nullable StockTextureAliases p_i232547_2_) {
      this.field_240402_J_ = p_i232547_1_;
      this.field_240403_K_ = p_i232547_2_;
   }

   public String func_240430_a_() {
      return this.field_240402_J_;
   }

   @Nullable
   public StockTextureAliases func_240433_b_() {
      return this.field_240403_K_;
   }

   public String toString() {
      return "#" + this.field_240402_J_;
   }
}