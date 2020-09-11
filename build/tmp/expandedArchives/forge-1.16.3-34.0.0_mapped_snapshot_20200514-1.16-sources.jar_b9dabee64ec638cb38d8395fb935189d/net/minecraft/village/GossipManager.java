package net.minecraft.village;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.util.UUIDCodec;
import net.minecraft.util.Util;

public class GossipManager {
   private final Map<UUID, GossipManager.Gossips> uuid_gossips_mapping = Maps.newHashMap();

   public void tick() {
      Iterator<GossipManager.Gossips> iterator = this.uuid_gossips_mapping.values().iterator();

      while(iterator.hasNext()) {
         GossipManager.Gossips gossipmanager$gossips = iterator.next();
         gossipmanager$gossips.decay();
         if (gossipmanager$gossips.isGossipTypeMapEmpty()) {
            iterator.remove();
         }
      }

   }

   private Stream<GossipManager.GossipEntry> getGossipEntries() {
      return this.uuid_gossips_mapping.entrySet().stream().flatMap((p_220917_0_) -> {
         return p_220917_0_.getValue().unpack(p_220917_0_.getKey());
      });
   }

   private Collection<GossipManager.GossipEntry> selectGossipsForTransfer(Random rand, int p_220920_2_) {
      List<GossipManager.GossipEntry> list = this.getGossipEntries().collect(Collectors.toList());
      if (list.isEmpty()) {
         return Collections.emptyList();
      } else {
         int[] aint = new int[list.size()];
         int i = 0;

         for(int j = 0; j < list.size(); ++j) {
            GossipManager.GossipEntry gossipmanager$gossipentry = list.get(j);
            i += Math.abs(gossipmanager$gossipentry.weightedValue());
            aint[j] = i - 1;
         }

         Set<GossipManager.GossipEntry> set = Sets.newIdentityHashSet();

         for(int i1 = 0; i1 < p_220920_2_; ++i1) {
            int k = rand.nextInt(i);
            int l = Arrays.binarySearch(aint, k);
            set.add(list.get(l < 0 ? -l - 1 : l));
         }

         return set;
      }
   }

   private GossipManager.Gossips getOrCreate(UUID p_220926_1_) {
      return this.uuid_gossips_mapping.computeIfAbsent(p_220926_1_, (p_220922_0_) -> {
         return new GossipManager.Gossips();
      });
   }

   public void transferFrom(GossipManager gossip, Random rand, int p_220912_3_) {
      Collection<GossipManager.GossipEntry> collection = gossip.selectGossipsForTransfer(rand, p_220912_3_);
      collection.forEach((p_220923_1_) -> {
         int i = p_220923_1_.value - p_220923_1_.type.decayPerTransfer;
         if (i >= 2) {
            this.getOrCreate(p_220923_1_.target).gossipTypeMap.mergeInt(p_220923_1_.type, i, GossipManager::getMax);
         }

      });
   }

   public int getReputation(UUID p_220921_1_, Predicate<GossipType> p_220921_2_) {
      GossipManager.Gossips gossipmanager$gossips = this.uuid_gossips_mapping.get(p_220921_1_);
      return gossipmanager$gossips != null ? gossipmanager$gossips.weightedValue(p_220921_2_) : 0;
   }

   public void add(UUID p_220916_1_, GossipType p_220916_2_, int p_220916_3_) {
      GossipManager.Gossips gossipmanager$gossips = this.getOrCreate(p_220916_1_);
      gossipmanager$gossips.gossipTypeMap.mergeInt(p_220916_2_, p_220916_3_, (p_220915_2_, p_220915_3_) -> {
         return this.mergeValuesForAddition(p_220916_2_, p_220915_2_, p_220915_3_);
      });
      gossipmanager$gossips.putGossipType(p_220916_2_);
      if (gossipmanager$gossips.isGossipTypeMapEmpty()) {
         this.uuid_gossips_mapping.remove(p_220916_1_);
      }

   }

   public <T> Dynamic<T> func_234058_a_(DynamicOps<T> p_234058_1_) {
      return new Dynamic<>(p_234058_1_, p_234058_1_.createList(this.getGossipEntries().map((p_234059_1_) -> {
         return p_234059_1_.func_234061_a_(p_234058_1_);
      }).map(Dynamic::getValue)));
   }

   public void func_234057_a_(Dynamic<?> p_234057_1_) {
      p_234057_1_.asStream().map(GossipManager.GossipEntry::func_234060_a_).flatMap((p_234056_0_) -> {
         return Util.streamOptional(p_234056_0_.result());
      }).forEach((p_234055_1_) -> {
         this.getOrCreate(p_234055_1_.target).gossipTypeMap.put(p_234055_1_.type, p_234055_1_.value);
      });
   }

   /**
    * Returns the greater of two int values
    */
   private static int getMax(int value1, int value2) {
      return Math.max(value1, value2);
   }

   private int mergeValuesForAddition(GossipType gossipTypeIn, int p_220925_2_, int p_220925_3_) {
      int i = p_220925_2_ + p_220925_3_;
      return i > gossipTypeIn.max ? Math.max(gossipTypeIn.max, p_220925_2_) : i;
   }

   static class GossipEntry {
      public final UUID target;
      public final GossipType type;
      public final int value;

      public GossipEntry(UUID target, GossipType type, int value) {
         this.target = target;
         this.type = type;
         this.value = value;
      }

      public int weightedValue() {
         return this.value * this.type.weight;
      }

      public String toString() {
         return "GossipEntry{target=" + this.target + ", type=" + this.type + ", value=" + this.value + '}';
      }

      public <T> Dynamic<T> func_234061_a_(DynamicOps<T> p_234061_1_) {
         return new Dynamic<>(p_234061_1_, p_234061_1_.createMap(ImmutableMap.of(p_234061_1_.createString("Target"), UUIDCodec.field_239775_a_.encodeStart(p_234061_1_, this.target).result().orElseThrow(RuntimeException::new), p_234061_1_.createString("Type"), p_234061_1_.createString(this.type.id), p_234061_1_.createString("Value"), p_234061_1_.createInt(this.value))));
      }

      public static DataResult<GossipManager.GossipEntry> func_234060_a_(Dynamic<?> p_234060_0_) {
         return DataResult.unbox(DataResult.instance().group(p_234060_0_.get("Target").read(UUIDCodec.field_239775_a_), p_234060_0_.get("Type").asString().map(GossipType::byId), p_234060_0_.get("Value").asNumber().map(Number::intValue)).apply(DataResult.instance(), GossipManager.GossipEntry::new));
      }
   }

   static class Gossips {
      private final Object2IntMap<GossipType> gossipTypeMap = new Object2IntOpenHashMap<>();

      private Gossips() {
      }

      public int weightedValue(Predicate<GossipType> p_220896_1_) {
         return this.gossipTypeMap.object2IntEntrySet().stream().filter((p_220898_1_) -> {
            return p_220896_1_.test(p_220898_1_.getKey());
         }).mapToInt((p_220894_0_) -> {
            return p_220894_0_.getIntValue() * (p_220894_0_.getKey()).weight;
         }).sum();
      }

      public Stream<GossipManager.GossipEntry> unpack(UUID p_220895_1_) {
         return this.gossipTypeMap.object2IntEntrySet().stream().map((p_220897_1_) -> {
            return new GossipManager.GossipEntry(p_220895_1_, p_220897_1_.getKey(), p_220897_1_.getIntValue());
         });
      }

      public void decay() {
         ObjectIterator<Entry<GossipType>> objectiterator = this.gossipTypeMap.object2IntEntrySet().iterator();

         while(objectiterator.hasNext()) {
            Entry<GossipType> entry = objectiterator.next();
            int i = entry.getIntValue() - (entry.getKey()).decayPerDay;
            if (i < 2) {
               objectiterator.remove();
            } else {
               entry.setValue(i);
            }
         }

      }

      public boolean isGossipTypeMapEmpty() {
         return this.gossipTypeMap.isEmpty();
      }

      public void putGossipType(GossipType p_223531_1_) {
         int i = this.gossipTypeMap.getInt(p_223531_1_);
         if (i > p_223531_1_.max) {
            this.gossipTypeMap.put(p_223531_1_, p_223531_1_.max);
         }

         if (i < 2) {
            this.removeGossipType(p_223531_1_);
         }

      }

      public void removeGossipType(GossipType p_223528_1_) {
         this.gossipTypeMap.removeInt(p_223528_1_);
      }
   }
}