package net.minecraft.tags;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.registry.Registry;

public interface ITagCollectionSupplier extends net.minecraftforge.common.extensions.IForgeTagCollectionSupplier {
   ITagCollectionSupplier field_242208_a = func_242209_a(ITagCollection.func_242205_c(), ITagCollection.func_242205_c(), ITagCollection.func_242205_c(), ITagCollection.func_242205_c());

   ITagCollection<Block> func_241835_a();

   ITagCollection<Item> func_241836_b();

   ITagCollection<Fluid> func_241837_c();

   ITagCollection<EntityType<?>> func_241838_d();

   default void func_242212_e() {
      TagRegistryManager.func_242193_a(this);
      Blocks.func_235419_a_();
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.TagsUpdatedEvent.VanillaTagTypes(this));
   }

   default void func_242210_a(PacketBuffer p_242210_1_) {
      this.func_241835_a().func_242203_a(p_242210_1_, Registry.BLOCK);
      this.func_241836_b().func_242203_a(p_242210_1_, Registry.ITEM);
      this.func_241837_c().func_242203_a(p_242210_1_, Registry.FLUID);
      this.func_241838_d().func_242203_a(p_242210_1_, Registry.ENTITY_TYPE);
   }

   static ITagCollectionSupplier func_242211_b(PacketBuffer p_242211_0_) {
      ITagCollection<Block> itagcollection = ITagCollection.func_242204_a(p_242211_0_, Registry.BLOCK);
      ITagCollection<Item> itagcollection1 = ITagCollection.func_242204_a(p_242211_0_, Registry.ITEM);
      ITagCollection<Fluid> itagcollection2 = ITagCollection.func_242204_a(p_242211_0_, Registry.FLUID);
      ITagCollection<EntityType<?>> itagcollection3 = ITagCollection.func_242204_a(p_242211_0_, Registry.ENTITY_TYPE);
      return func_242209_a(itagcollection, itagcollection1, itagcollection2, itagcollection3);
   }

   static ITagCollectionSupplier func_242209_a(final ITagCollection<Block> p_242209_0_, final ITagCollection<Item> p_242209_1_, final ITagCollection<Fluid> p_242209_2_, final ITagCollection<EntityType<?>> p_242209_3_) {
      return new ITagCollectionSupplier() {
         public ITagCollection<Block> func_241835_a() {
            return p_242209_0_;
         }

         public ITagCollection<Item> func_241836_b() {
            return p_242209_1_;
         }

         public ITagCollection<Fluid> func_241837_c() {
            return p_242209_2_;
         }

         public ITagCollection<EntityType<?>> func_241838_d() {
            return p_242209_3_;
         }
      };
   }
}