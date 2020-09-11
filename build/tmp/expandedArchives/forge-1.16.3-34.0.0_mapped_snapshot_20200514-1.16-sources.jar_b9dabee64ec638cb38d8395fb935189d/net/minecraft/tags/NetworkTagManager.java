package net.minecraft.tags;

import com.google.common.collect.Multimap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class NetworkTagManager implements IFutureReloadListener {
   protected TagCollectionReader<Block> blocks = new TagCollectionReader<>(Registry.BLOCK::func_241873_b, "tags/blocks", "block");
   protected TagCollectionReader<Item> items = new TagCollectionReader<>(Registry.ITEM::func_241873_b, "tags/items", "item");
   protected TagCollectionReader<Fluid> fluids = new TagCollectionReader<>(Registry.FLUID::func_241873_b, "tags/fluids", "fluid");
   protected TagCollectionReader<EntityType<?>> entityTypes = new TagCollectionReader<>(Registry.ENTITY_TYPE::func_241873_b, "tags/entity_types", "entity_type");
   protected Map<ResourceLocation, TagCollectionReader<?>> customTagTypeReaders = net.minecraftforge.common.ForgeTagHandler.createCustomTagTypeReaders();
   private ITagCollectionSupplier field_242230_e = ITagCollectionSupplier.field_242208_a;

   public ITagCollectionSupplier func_242231_a() {
      return this.field_242230_e;
   }

   public CompletableFuture<Void> reload(IFutureReloadListener.IStage stage, IResourceManager resourceManager, IProfiler preparationsProfiler, IProfiler reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
      CompletableFuture<Map<ResourceLocation, ITag.Builder>> completablefuture = this.blocks.func_242224_a(resourceManager, backgroundExecutor);
      CompletableFuture<Map<ResourceLocation, ITag.Builder>> completablefuture1 = this.items.func_242224_a(resourceManager, backgroundExecutor);
      CompletableFuture<Map<ResourceLocation, ITag.Builder>> completablefuture2 = this.fluids.func_242224_a(resourceManager, backgroundExecutor);
      CompletableFuture<Map<ResourceLocation, ITag.Builder>> completablefuture3 = this.entityTypes.func_242224_a(resourceManager, backgroundExecutor);
      CompletableFuture<java.util.List<net.minecraftforge.common.ForgeTagHandler.TagCollectionReaderInfo>> customTagTypeResults = net.minecraftforge.common.ForgeTagHandler.getCustomTagTypeReloadResults(resourceManager, backgroundExecutor, customTagTypeReaders);
      return CompletableFuture.allOf(completablefuture, completablefuture1, completablefuture2, completablefuture3, customTagTypeResults).thenCompose(stage::markCompleteAwaitingOthers).thenAcceptAsync((p_232979_5_) -> {
         ITagCollection<Block> itagcollection = this.blocks.func_242226_a(completablefuture.join());
         ITagCollection<Item> itagcollection1 = this.items.func_242226_a(completablefuture1.join());
         ITagCollection<Fluid> itagcollection2 = this.fluids.func_242226_a(completablefuture2.join());
         ITagCollection<EntityType<?>> itagcollection3 = this.entityTypes.func_242226_a(completablefuture3.join());
         net.minecraftforge.common.ForgeTagHandler.updateCustomTagTypes(customTagTypeResults.join());
         ITagCollectionSupplier itagcollectionsupplier = ITagCollectionSupplier.func_242209_a(itagcollection, itagcollection1, itagcollection2, itagcollection3);
         Multimap<ResourceLocation, ResourceLocation> multimap = TagRegistryManager.func_242198_b(itagcollectionsupplier);
         if (!multimap.isEmpty()) {
            throw new IllegalStateException("Missing required tags: " + (String)multimap.entries().stream().map((p_232978_0_) -> {
               return p_232978_0_.getKey() + ":" + p_232978_0_.getValue();
            }).sorted().collect(Collectors.joining(",")));
         } else {
            TagCollectionManager.func_242180_a(itagcollectionsupplier);
            this.field_242230_e = itagcollectionsupplier;
         }
      }, gameExecutor);
   }
}