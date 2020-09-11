package net.minecraft.world.gen.treedecorator;

import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;

public class TreeDecoratorType<P extends TreeDecorator> extends net.minecraftforge.registries.ForgeRegistryEntry<TreeDecoratorType<?>> {
   public static final TreeDecoratorType<TrunkVineTreeDecorator> TRUNK_VINE = func_236877_a_("trunk_vine", TrunkVineTreeDecorator.field_236878_a_);
   public static final TreeDecoratorType<LeaveVineTreeDecorator> LEAVE_VINE = func_236877_a_("leave_vine", LeaveVineTreeDecorator.field_236870_a_);
   public static final TreeDecoratorType<CocoaTreeDecorator> COCOA = func_236877_a_("cocoa", CocoaTreeDecorator.field_236866_a_);
   public static final TreeDecoratorType<BeehiveTreeDecorator> BEEHIVE = func_236877_a_("beehive", BeehiveTreeDecorator.field_236863_a_);
   public static final TreeDecoratorType<AlterGroundTreeDecorator> ALTER_GROUND = func_236877_a_("alter_ground", AlterGroundTreeDecorator.field_236859_a_);
   private final Codec<P> field_236875_f_;

   private static <P extends TreeDecorator> TreeDecoratorType<P> func_236877_a_(String p_236877_0_, Codec<P> p_236877_1_) {
      return Registry.register(Registry.TREE_DECORATOR_TYPE, p_236877_0_, new TreeDecoratorType<>(p_236877_1_));
   }

   private TreeDecoratorType(Codec<P> p_i232052_1_) {
      this.field_236875_f_ = p_i232052_1_;
   }

   public Codec<P> func_236876_a_() {
      return this.field_236875_f_;
   }
}