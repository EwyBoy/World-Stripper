package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;

public class FeatureSizeType<P extends AbstractFeatureSizeType> {
   public static final FeatureSizeType<TwoLayerFeature> field_236711_a_ = func_236715_a_("two_layers_feature_size", TwoLayerFeature.field_236728_c_);
   public static final FeatureSizeType<ThreeLayerFeature> field_236712_b_ = func_236715_a_("three_layers_feature_size", ThreeLayerFeature.field_236716_c_);
   private final Codec<P> field_236713_c_;

   private static <P extends AbstractFeatureSizeType> FeatureSizeType<P> func_236715_a_(String p_236715_0_, Codec<P> p_236715_1_) {
      return Registry.register(Registry.field_239702_ay_, p_236715_0_, new FeatureSizeType<>(p_236715_1_));
   }

   private FeatureSizeType(Codec<P> p_i232023_1_) {
      this.field_236713_c_ = p_i232023_1_;
   }

   public Codec<P> func_236714_a_() {
      return this.field_236713_c_;
   }
}