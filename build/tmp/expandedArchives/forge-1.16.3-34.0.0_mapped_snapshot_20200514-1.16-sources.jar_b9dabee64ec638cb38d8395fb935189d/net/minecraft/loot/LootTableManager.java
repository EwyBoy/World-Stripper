package net.minecraft.loot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTableManager extends JsonReloadListener {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON_INSTANCE = LootSerializers.func_237388_c_().create();
   private Map<ResourceLocation, LootTable> registeredLootTables = ImmutableMap.of();
   private final LootPredicateManager field_227507_d_;

   public LootTableManager(LootPredicateManager p_i225887_1_) {
      super(GSON_INSTANCE, "loot_tables");
      this.field_227507_d_ = p_i225887_1_;
   }

   public LootTable getLootTableFromLocation(ResourceLocation ressources) {
      return this.registeredLootTables.getOrDefault(ressources, LootTable.EMPTY_LOOT_TABLE);
   }

   protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
      Builder<ResourceLocation, LootTable> builder = ImmutableMap.builder();
      JsonElement jsonelement = objectIn.remove(LootTables.EMPTY);
      if (jsonelement != null) {
         LOGGER.warn("Datapack tried to redefine {} loot table, ignoring", (Object)LootTables.EMPTY);
      }

      objectIn.forEach((p_237403_1_, p_237403_2_) -> {
         try (net.minecraft.resources.IResource res = resourceManagerIn.getResource(getPreparedPath(p_237403_1_));){
            LootTable loottable = net.minecraftforge.common.ForgeHooks.loadLootTable(GSON_INSTANCE, p_237403_1_, p_237403_2_, res == null || !res.getPackName().equals("Default"), this);
            builder.put(p_237403_1_, loottable);
         } catch (Exception exception) {
            LOGGER.error("Couldn't parse loot table {}", p_237403_1_, exception);
         }

      });
      builder.put(LootTables.EMPTY, LootTable.EMPTY_LOOT_TABLE);
      ImmutableMap<ResourceLocation, LootTable> immutablemap = builder.build();
      ValidationTracker validationtracker = new ValidationTracker(LootParameterSets.GENERIC, this.field_227507_d_::func_227517_a_, immutablemap::get);
      immutablemap.forEach((p_227509_1_, p_227509_2_) -> {
         func_227508_a_(validationtracker, p_227509_1_, p_227509_2_);
      });
      validationtracker.getProblems().forEach((p_215303_0_, p_215303_1_) -> {
         LOGGER.warn("Found validation problem in " + p_215303_0_ + ": " + p_215303_1_);
      });
      this.registeredLootTables = immutablemap;
   }

   public static void func_227508_a_(ValidationTracker p_227508_0_, ResourceLocation p_227508_1_, LootTable p_227508_2_) {
      p_227508_2_.func_227506_a_(p_227508_0_.func_227529_a_(p_227508_2_.getParameterSet()).func_227531_a_("{" + p_227508_1_ + "}", p_227508_1_));
   }

   public static JsonElement toJson(LootTable lootTableIn) {
      return GSON_INSTANCE.toJsonTree(lootTableIn);
   }

   public Set<ResourceLocation> getLootTableKeys() {
      return this.registeredLootTables.keySet();
   }
}