package net.minecraft.loot;

public class LootType<T> {
   private final ILootSerializer<? extends T> field_237407_a_;

   public LootType(ILootSerializer<? extends T> p_i232166_1_) {
      this.field_237407_a_ = p_i232166_1_;
   }

   public ILootSerializer<? extends T> func_237408_a_() {
      return this.field_237407_a_;
   }
}