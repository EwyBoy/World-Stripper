package net.minecraft.data;

import com.google.gson.JsonElement;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

public class TexturedModel {
   public static final TexturedModel.ISupplier field_240434_a_ = func_240461_a_(ModelTextures::func_240345_a_, StockModelShapes.field_240307_c_);
   public static final TexturedModel.ISupplier field_240435_b_ = func_240461_a_(ModelTextures::func_240345_a_, StockModelShapes.field_240308_d_);
   public static final TexturedModel.ISupplier field_240436_c_ = func_240461_a_(ModelTextures::func_240375_j_, StockModelShapes.field_240309_e_);
   public static final TexturedModel.ISupplier field_240437_d_ = func_240461_a_(ModelTextures::func_240375_j_, StockModelShapes.field_240310_f_);
   public static final TexturedModel.ISupplier field_240438_e_ = func_240461_a_(ModelTextures::func_240379_m_, StockModelShapes.field_240312_h_);
   public static final TexturedModel.ISupplier field_240439_f_ = func_240461_a_(ModelTextures::func_240377_k_, StockModelShapes.field_240311_g_);
   public static final TexturedModel.ISupplier field_240440_g_ = func_240461_a_(ModelTextures::func_240390_x_, StockModelShapes.field_240313_i_);
   public static final TexturedModel.ISupplier field_240441_h_ = func_240461_a_(ModelTextures::func_240389_w_, StockModelShapes.field_240314_j_);
   public static final TexturedModel.ISupplier field_240442_i_ = func_240461_a_(ModelTextures::func_240368_f_, StockModelShapes.field_240280_aa_);
   public static final TexturedModel.ISupplier field_240443_j_ = func_240461_a_(ModelTextures::func_240371_h_, StockModelShapes.field_240283_ad_);
   public static final TexturedModel.ISupplier field_240444_k_ = func_240461_a_(ModelTextures::func_240373_i_, StockModelShapes.field_240281_ab_);
   public static final TexturedModel.ISupplier field_240445_l_ = func_240461_a_(ModelTextures::func_240383_q_, StockModelShapes.field_240241_F_);
   public static final TexturedModel.ISupplier field_240446_m_ = func_240461_a_(ModelTextures::func_240392_z_, StockModelShapes.field_240292_am_);
   public static final TexturedModel.ISupplier field_240447_n_ = func_240461_a_(ModelTextures::func_240345_a_, StockModelShapes.field_240244_I_);
   public static final TexturedModel.ISupplier field_240448_o_ = func_240461_a_(ModelTextures::func_240386_t_, StockModelShapes.field_240303_ax_);
   public static final TexturedModel.ISupplier field_240449_p_ = func_240461_a_(ModelTextures::func_240386_t_, StockModelShapes.field_240304_ay_);
   public static final TexturedModel.ISupplier field_240450_q_ = func_240461_a_(ModelTextures::func_240353_b_, StockModelShapes.field_240267_aE_);
   public static final TexturedModel.ISupplier field_240451_r_ = func_240461_a_(ModelTextures::func_240378_l_, StockModelShapes.field_240309_e_);
   public static final TexturedModel.ISupplier field_240452_s_ = func_240461_a_(ModelTextures::func_240378_l_, StockModelShapes.field_240310_f_);
   public static final TexturedModel.ISupplier field_240453_t_ = func_240461_a_(ModelTextures::func_240380_n_, StockModelShapes.field_240312_h_);
   public static final TexturedModel.ISupplier field_240454_u_ = func_240461_a_(ModelTextures::func_240381_o_, StockModelShapes.field_240309_e_);
   private final ModelTextures field_240455_v_;
   private final ModelsUtil field_240456_w_;

   private TexturedModel(ModelTextures p_i232548_1_, ModelsUtil p_i232548_2_) {
      this.field_240455_v_ = p_i232548_1_;
      this.field_240456_w_ = p_i232548_2_;
   }

   public ModelsUtil func_240457_a_() {
      return this.field_240456_w_;
   }

   public ModelTextures func_240464_b_() {
      return this.field_240455_v_;
   }

   public TexturedModel func_240460_a_(Consumer<ModelTextures> p_240460_1_) {
      p_240460_1_.accept(this.field_240455_v_);
      return this;
   }

   public ResourceLocation func_240459_a_(Block p_240459_1_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_240459_2_) {
      return this.field_240456_w_.func_240228_a_(p_240459_1_, this.field_240455_v_, p_240459_2_);
   }

   public ResourceLocation func_240458_a_(Block p_240458_1_, String p_240458_2_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_240458_3_) {
      return this.field_240456_w_.func_240229_a_(p_240458_1_, p_240458_2_, this.field_240455_v_, p_240458_3_);
   }

   private static TexturedModel.ISupplier func_240461_a_(Function<Block, ModelTextures> p_240461_0_, ModelsUtil p_240461_1_) {
      return (p_240462_2_) -> {
         return new TexturedModel(p_240461_0_.apply(p_240462_2_), p_240461_1_);
      };
   }

   public static TexturedModel func_240463_a_(ResourceLocation p_240463_0_) {
      return new TexturedModel(ModelTextures.func_240356_b_(p_240463_0_), StockModelShapes.field_240307_c_);
   }

   @FunctionalInterface
   public interface ISupplier {
      TexturedModel get(Block p_get_1_);

      default ResourceLocation func_240466_a_(Block p_240466_1_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_240466_2_) {
         return this.get(p_240466_1_).func_240459_a_(p_240466_1_, p_240466_2_);
      }

      default ResourceLocation func_240465_a_(Block p_240465_1_, String p_240465_2_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_240465_3_) {
         return this.get(p_240465_1_).func_240458_a_(p_240465_1_, p_240465_2_, p_240465_3_);
      }

      default TexturedModel.ISupplier func_240467_a_(Consumer<ModelTextures> p_240467_1_) {
         return (p_240468_2_) -> {
            return this.get(p_240468_2_).func_240460_a_(p_240467_1_);
         };
      }
   }
}