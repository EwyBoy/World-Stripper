package net.minecraft.world.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Util;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobSpawnInfo {
   public static final Logger field_242550_a = LogManager.getLogger();
   public static final MobSpawnInfo field_242551_b = new MobSpawnInfo(0.1F, Stream.of(EntityClassification.values()).collect(ImmutableMap.toImmutableMap((p_242565_0_) -> {
      return p_242565_0_;
   }, (p_242563_0_) -> {
      return ImmutableList.of();
   })), ImmutableMap.of(), false);
   public static final MapCodec<MobSpawnInfo> field_242552_c = RecordCodecBuilder.mapCodec((p_242561_0_) -> {
      return p_242561_0_.group(Codec.FLOAT.optionalFieldOf("creature_spawn_probability", Float.valueOf(0.1F)).forGetter((p_242566_0_) -> {
         return p_242566_0_.field_242553_d;
      }), Codec.simpleMap(EntityClassification.field_233667_g_, MobSpawnInfo.Spawners.field_242587_b.listOf().promotePartial(Util.func_240982_a_("Spawn data: ", field_242550_a::error)), IStringSerializable.func_233025_a_(EntityClassification.values())).fieldOf("spawners").forGetter((p_242564_0_) -> {
         return p_242564_0_.field_242554_e;
      }), Codec.simpleMap(Registry.ENTITY_TYPE, MobSpawnInfo.SpawnCosts.field_242579_a, Registry.ENTITY_TYPE).fieldOf("spawn_costs").forGetter((p_242560_0_) -> {
         return p_242560_0_.field_242555_f;
      }), Codec.BOOL.fieldOf("player_spawn_friendly").orElse(false).forGetter(MobSpawnInfo::func_242562_b)).apply(p_242561_0_, MobSpawnInfo::new);
   });
   private final float field_242553_d;
   private final Map<EntityClassification, List<MobSpawnInfo.Spawners>> field_242554_e;
   private final Map<EntityType<?>, MobSpawnInfo.SpawnCosts> field_242555_f;
   private final boolean field_242556_g;

   private MobSpawnInfo(float p_i241946_1_, Map<EntityClassification, List<MobSpawnInfo.Spawners>> p_i241946_2_, Map<EntityType<?>, MobSpawnInfo.SpawnCosts> p_i241946_3_, boolean p_i241946_4_) {
      this.field_242553_d = p_i241946_1_;
      this.field_242554_e = p_i241946_2_;
      this.field_242555_f = p_i241946_3_;
      this.field_242556_g = p_i241946_4_;
   }

   public List<MobSpawnInfo.Spawners> func_242559_a(EntityClassification p_242559_1_) {
      return this.field_242554_e.getOrDefault(p_242559_1_, ImmutableList.of());
   }

   @Nullable
   public MobSpawnInfo.SpawnCosts func_242558_a(EntityType<?> p_242558_1_) {
      return this.field_242555_f.get(p_242558_1_);
   }

   public float func_242557_a() {
      return this.field_242553_d;
   }

   public boolean func_242562_b() {
      return this.field_242556_g;
   }

   public static class Builder {
      private final Map<EntityClassification, List<MobSpawnInfo.Spawners>> field_242567_a = Stream.of(EntityClassification.values()).collect(ImmutableMap.toImmutableMap((p_242578_0_) -> {
         return p_242578_0_;
      }, (p_242574_0_) -> {
         return Lists.newArrayList();
      }));
      private final Map<EntityType<?>, MobSpawnInfo.SpawnCosts> field_242568_b = Maps.newLinkedHashMap();
      private float field_242569_c = 0.1F;
      private boolean field_242570_d;

      public MobSpawnInfo.Builder func_242575_a(EntityClassification p_242575_1_, MobSpawnInfo.Spawners p_242575_2_) {
         this.field_242567_a.get(p_242575_1_).add(p_242575_2_);
         return this;
      }

      public MobSpawnInfo.Builder func_242573_a(EntityType<?> p_242573_1_, double p_242573_2_, double p_242573_4_) {
         this.field_242568_b.put(p_242573_1_, new MobSpawnInfo.SpawnCosts(p_242573_4_, p_242573_2_));
         return this;
      }

      public MobSpawnInfo.Builder func_242572_a(float p_242572_1_) {
         this.field_242569_c = p_242572_1_;
         return this;
      }

      public MobSpawnInfo.Builder func_242571_a() {
         this.field_242570_d = true;
         return this;
      }

      public MobSpawnInfo func_242577_b() {
         return new MobSpawnInfo(this.field_242569_c, this.field_242567_a.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, (p_242576_0_) -> {
            return ImmutableList.copyOf((Collection)p_242576_0_.getValue());
         })), ImmutableMap.copyOf(this.field_242568_b), this.field_242570_d);
      }
   }

   public static class SpawnCosts {
      public static final Codec<MobSpawnInfo.SpawnCosts> field_242579_a = RecordCodecBuilder.create((p_242584_0_) -> {
         return p_242584_0_.group(Codec.DOUBLE.fieldOf("energy_budget").forGetter((p_242586_0_) -> {
            return p_242586_0_.field_242580_b;
         }), Codec.DOUBLE.fieldOf("charge").forGetter((p_242583_0_) -> {
            return p_242583_0_.field_242581_c;
         })).apply(p_242584_0_, MobSpawnInfo.SpawnCosts::new);
      });
      private final double field_242580_b;
      private final double field_242581_c;

      private SpawnCosts(double p_i241948_1_, double p_i241948_3_) {
         this.field_242580_b = p_i241948_1_;
         this.field_242581_c = p_i241948_3_;
      }

      public double func_242582_a() {
         return this.field_242580_b;
      }

      public double func_242585_b() {
         return this.field_242581_c;
      }
   }

   public static class Spawners extends WeightedRandom.Item {
      public static final Codec<MobSpawnInfo.Spawners> field_242587_b = RecordCodecBuilder.create((p_242592_0_) -> {
         return p_242592_0_.group(Registry.ENTITY_TYPE.fieldOf("type").forGetter((p_242595_0_) -> {
            return p_242595_0_.field_242588_c;
         }), Codec.INT.fieldOf("weight").forGetter((p_242594_0_) -> {
            return p_242594_0_.itemWeight;
         }), Codec.INT.fieldOf("minCount").forGetter((p_242593_0_) -> {
            return p_242593_0_.field_242589_d;
         }), Codec.INT.fieldOf("maxCount").forGetter((p_242591_0_) -> {
            return p_242591_0_.field_242590_e;
         })).apply(p_242592_0_, MobSpawnInfo.Spawners::new);
      });
      public final EntityType<?> field_242588_c;
      public final int field_242589_d;
      public final int field_242590_e;

      public Spawners(EntityType<?> p_i241950_1_, int p_i241950_2_, int p_i241950_3_, int p_i241950_4_) {
         super(p_i241950_2_);
         this.field_242588_c = p_i241950_1_.getClassification() == EntityClassification.MISC ? EntityType.PIG : p_i241950_1_;
         this.field_242589_d = p_i241950_3_;
         this.field_242590_e = p_i241950_4_;
      }

      public String toString() {
         return EntityType.getKey(this.field_242588_c) + "*(" + this.field_242589_d + "-" + this.field_242590_e + "):" + this.itemWeight;
      }
   }
}