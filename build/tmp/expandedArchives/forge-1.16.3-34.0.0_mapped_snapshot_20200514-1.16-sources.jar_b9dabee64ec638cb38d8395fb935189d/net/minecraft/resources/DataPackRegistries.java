package net.minecraft.resources;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.command.Commands;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.loot.LootPredicateManager;
import net.minecraft.loot.LootTableManager;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.tags.NetworkTagManager;
import net.minecraft.util.Unit;

public class DataPackRegistries implements AutoCloseable {
   private static final CompletableFuture<Unit> field_240951_a_ = CompletableFuture.completedFuture(Unit.INSTANCE);
   private final IReloadableResourceManager field_240952_b_ = new SimpleReloadableResourceManager(ResourcePackType.SERVER_DATA);
   private final Commands field_240953_c_;
   private final RecipeManager field_240954_d_ = new RecipeManager();
   private final NetworkTagManager field_240955_e_ = new NetworkTagManager();
   private final LootPredicateManager field_240956_f_ = new LootPredicateManager();
   private final LootTableManager field_240957_g_ = new LootTableManager(this.field_240956_f_);
   private final AdvancementManager field_240958_h_ = new AdvancementManager(this.field_240956_f_);
   private final FunctionReloader field_240959_i_;

   public DataPackRegistries(Commands.EnvironmentType p_i232598_1_, int p_i232598_2_) {
      this.field_240953_c_ = new Commands(p_i232598_1_);
      this.field_240959_i_ = new FunctionReloader(p_i232598_2_, this.field_240953_c_.getDispatcher());
      this.field_240952_b_.addReloadListener(this.field_240955_e_);
      this.field_240952_b_.addReloadListener(this.field_240956_f_);
      this.field_240952_b_.addReloadListener(this.field_240954_d_);
      this.field_240952_b_.addReloadListener(this.field_240957_g_);
      this.field_240952_b_.addReloadListener(this.field_240959_i_);
      this.field_240952_b_.addReloadListener(this.field_240958_h_);
      net.minecraftforge.event.ForgeEventFactory.onResourceReload(this).forEach(field_240952_b_::addReloadListener);
   }

   public FunctionReloader func_240960_a_() {
      return this.field_240959_i_;
   }

   public LootPredicateManager func_240964_b_() {
      return this.field_240956_f_;
   }

   public LootTableManager func_240965_c_() {
      return this.field_240957_g_;
   }

   public ITagCollectionSupplier func_244358_d() {
      return this.field_240955_e_.func_242231_a();
   }

   public RecipeManager func_240967_e_() {
      return this.field_240954_d_;
   }

   public Commands func_240968_f_() {
      return this.field_240953_c_;
   }

   public AdvancementManager func_240969_g_() {
      return this.field_240958_h_;
   }

   public IResourceManager func_240970_h_() {
      return this.field_240952_b_;
   }

   public static CompletableFuture<DataPackRegistries> func_240961_a_(List<IResourcePack> p_240961_0_, Commands.EnvironmentType p_240961_1_, int p_240961_2_, Executor p_240961_3_, Executor p_240961_4_) {
      DataPackRegistries datapackregistries = new DataPackRegistries(p_240961_1_, p_240961_2_);
      CompletableFuture<Unit> completablefuture = datapackregistries.field_240952_b_.reloadResourcesAndThen(p_240961_3_, p_240961_4_, p_240961_0_, field_240951_a_);
      return completablefuture.whenComplete((p_240963_1_, p_240963_2_) -> {
         if (p_240963_2_ != null) {
            datapackregistries.close();
         }

      }).thenApply((p_240962_1_) -> {
         return datapackregistries;
      });
   }

   public void func_240971_i_() {
      this.field_240955_e_.func_242231_a().func_242212_e();
   }

   public void close() {
      this.field_240952_b_.close();
   }
}