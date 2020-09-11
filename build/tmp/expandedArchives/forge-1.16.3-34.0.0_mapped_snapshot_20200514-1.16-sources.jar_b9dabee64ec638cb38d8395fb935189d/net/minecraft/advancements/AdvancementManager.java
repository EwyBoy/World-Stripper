package net.minecraft.advancements;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.LootPredicateManager;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancementManager extends JsonReloadListener {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = (new GsonBuilder()).create();
   private AdvancementList advancementList = new AdvancementList();
   private final LootPredicateManager field_240922_d_;

   public AdvancementManager(LootPredicateManager p_i232595_1_) {
      super(GSON, "advancements");
      this.field_240922_d_ = p_i232595_1_;
   }

   protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
      Map<ResourceLocation, Advancement.Builder> map = Maps.newHashMap();
      objectIn.forEach((p_240923_2_, p_240923_3_) -> {
         try {
            JsonObject jsonobject = JSONUtils.getJsonObject(p_240923_3_, "advancement");
            Advancement.Builder advancement$builder = Advancement.Builder.func_241043_a_(jsonobject, new ConditionArrayParser(p_240923_2_, this.field_240922_d_));
            if (advancement$builder == null) {
                LOGGER.debug("Skipping loading advancement {} as it's conditions were not met", p_240923_2_);
                return;
            }
            map.put(p_240923_2_, advancement$builder);
         } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
            LOGGER.error("Parsing error loading custom advancement {}: {}", p_240923_2_, jsonparseexception.getMessage());
         }

      });
      AdvancementList advancementlist = new AdvancementList();
      advancementlist.loadAdvancements(map);

      for(Advancement advancement : advancementlist.getRoots()) {
         if (advancement.getDisplay() != null) {
            AdvancementTreeNode.layout(advancement);
         }
      }

      this.advancementList = advancementlist;
   }

   @Nullable
   public Advancement getAdvancement(ResourceLocation id) {
      return this.advancementList.getAdvancement(id);
   }

   public Collection<Advancement> getAllAdvancements() {
      return this.advancementList.getAll();
   }
}