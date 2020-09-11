package net.minecraft.world.gen.blockplacer;

import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;

public class BlockPlacerType<P extends BlockPlacer> extends net.minecraftforge.registries.ForgeRegistryEntry<BlockPlacerType<?>> {
   public static final BlockPlacerType<SimpleBlockPlacer> SIMPLE_BLOCK = func_236438_a_("simple_block_placer", SimpleBlockPlacer.field_236446_b_);
   public static final BlockPlacerType<DoublePlantBlockPlacer> DOUBLE_PLANT = func_236438_a_("double_plant_placer", DoublePlantBlockPlacer.field_236443_b_);
   public static final BlockPlacerType<ColumnBlockPlacer> COLUMN = func_236438_a_("column_placer", ColumnBlockPlacer.field_236439_b_);
   private final Codec<P> field_236436_d_;

   private static <P extends BlockPlacer> BlockPlacerType<P> func_236438_a_(String p_236438_0_, Codec<P> p_236438_1_) {
      return Registry.register(Registry.BLOCK_PLACER_TYPE, p_236438_0_, new BlockPlacerType<>(p_236438_1_));
   }

   private BlockPlacerType(Codec<P> p_i232006_1_) {
      this.field_236436_d_ = p_i232006_1_;
   }

   public Codec<P> func_236437_a_() {
      return this.field_236436_d_;
   }
}