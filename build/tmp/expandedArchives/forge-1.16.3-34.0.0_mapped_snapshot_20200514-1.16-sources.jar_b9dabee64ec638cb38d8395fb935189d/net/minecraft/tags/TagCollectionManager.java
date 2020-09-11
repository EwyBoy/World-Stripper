package net.minecraft.tags;

import java.util.stream.Collectors;

public class TagCollectionManager {
   private static volatile ITagCollectionSupplier field_232918_a_ = net.minecraftforge.common.ForgeTagHandler.populateTagCollectionManager(ITagCollection.func_242202_a(BlockTags.func_242174_b().stream().distinct().collect(Collectors.toMap(ITag.INamedTag::func_230234_a_, (p_242183_0_) -> {
      return p_242183_0_;
   }))), ITagCollection.func_242202_a(ItemTags.func_242177_b().stream().distinct().collect(Collectors.toMap(ITag.INamedTag::func_230234_a_, (p_242182_0_) -> {
      return p_242182_0_;
   }))), ITagCollection.func_242202_a(FluidTags.func_241280_c_().stream().distinct().collect(Collectors.toMap(ITag.INamedTag::func_230234_a_, (p_242181_0_) -> {
      return p_242181_0_;
   }))), ITagCollection.func_242202_a(EntityTypeTags.func_242175_b().stream().distinct().collect(Collectors.toMap(ITag.INamedTag::func_230234_a_, (p_242179_0_) -> {
      return p_242179_0_;
   }))));

   public static ITagCollectionSupplier func_242178_a() {
      return field_232918_a_;
   }

   public static void func_242180_a(ITagCollectionSupplier p_242180_0_) {
      field_232918_a_ = p_242180_0_;
   }
}