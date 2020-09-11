package net.minecraft.world.gen.trunkplacer;

import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;

public class TrunkPlacerType<P extends AbstractTrunkPlacer> {
   public static final TrunkPlacerType<StraightTrunkPlacer> field_236920_a_ = func_236928_a_("straight_trunk_placer", StraightTrunkPlacer.field_236903_a_);
   public static final TrunkPlacerType<ForkyTrunkPlacer> field_236921_b_ = func_236928_a_("forking_trunk_placer", ForkyTrunkPlacer.field_236896_a_);
   public static final TrunkPlacerType<GiantTrunkPlacer> field_236922_c_ = func_236928_a_("giant_trunk_placer", GiantTrunkPlacer.field_236898_a_);
   public static final TrunkPlacerType<MegaJungleTrunkPlacer> field_236923_d_ = func_236928_a_("mega_jungle_trunk_placer", MegaJungleTrunkPlacer.field_236901_b_);
   public static final TrunkPlacerType<DarkOakTrunkPlacer> field_236924_e_ = func_236928_a_("dark_oak_trunk_placer", DarkOakTrunkPlacer.field_236882_a_);
   public static final TrunkPlacerType<FancyTrunkPlacer> field_236925_f_ = func_236928_a_("fancy_trunk_placer", FancyTrunkPlacer.field_236884_a_);
   private final Codec<P> field_236926_g_;

   private static <P extends AbstractTrunkPlacer> TrunkPlacerType<P> func_236928_a_(String p_236928_0_, Codec<P> p_236928_1_) {
      return Registry.register(Registry.field_239701_aw_, p_236928_0_, new TrunkPlacerType<>(p_236928_1_));
   }

   private TrunkPlacerType(Codec<P> p_i232061_1_) {
      this.field_236926_g_ = p_i232061_1_;
   }

   public Codec<P> func_236927_a_() {
      return this.field_236926_g_;
   }
}