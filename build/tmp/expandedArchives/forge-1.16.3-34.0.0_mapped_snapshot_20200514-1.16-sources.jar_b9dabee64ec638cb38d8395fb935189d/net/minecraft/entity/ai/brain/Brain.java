package net.minecraft.entity.ai.brain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Brain<E extends LivingEntity> {
   private static final Logger field_233688_a_ = LogManager.getLogger();
   private final Supplier<Codec<Brain<E>>> field_233689_b_;
   private final Map<MemoryModuleType<?>, Optional<? extends Memory<?>>> memories = Maps.newHashMap();
   private final Map<SensorType<? extends Sensor<? super E>>, Sensor<? super E>> sensors = Maps.newLinkedHashMap();
   private final Map<Integer, Map<Activity, Set<Task<? super E>>>> field_218232_c = Maps.newTreeMap();
   private Schedule schedule = Schedule.EMPTY;
   private final Map<Activity, Set<Pair<MemoryModuleType<?>, MemoryModuleStatus>>> requiredMemoryStates = Maps.newHashMap();
   private final Map<Activity, Set<MemoryModuleType<?>>> field_233691_h_ = Maps.newHashMap();
   private Set<Activity> defaultActivities = Sets.newHashSet();
   private final Set<Activity> activities = Sets.newHashSet();
   private Activity fallbackActivity = Activity.IDLE;
   private long lastGameTime = -9999L;

   public static <E extends LivingEntity> Brain.BrainCodec<E> func_233705_a_(Collection<? extends MemoryModuleType<?>> p_233705_0_, Collection<? extends SensorType<? extends Sensor<? super E>>> p_233705_1_) {
      return new Brain.BrainCodec<>(p_233705_0_, p_233705_1_);
   }

   public static <E extends LivingEntity> Codec<Brain<E>> func_233710_b_(final Collection<? extends MemoryModuleType<?>> p_233710_0_, final Collection<? extends SensorType<? extends Sensor<? super E>>> p_233710_1_) {
      final MutableObject<Codec<Brain<E>>> mutableobject = new MutableObject<>();
      mutableobject.setValue((new MapCodec<Brain<E>>() {
         public <T> Stream<T> keys(DynamicOps<T> p_keys_1_) {
            return p_233710_0_.stream().flatMap((p_233734_0_) -> {
               return Util.streamOptional(p_233734_0_.func_234107_a_().map((p_233727_1_) -> {
                  return Registry.MEMORY_MODULE_TYPE.getKey(p_233734_0_);
               }));
            }).map((p_233733_1_) -> {
               return p_keys_1_.createString(p_233733_1_.toString());
            });
         }

         public <T> DataResult<Brain<E>> decode(DynamicOps<T> p_decode_1_, MapLike<T> p_decode_2_) {
            MutableObject<DataResult<Builder<Brain.MemoryCodec<?>>>> mutableobject1 = new MutableObject<>(DataResult.success(ImmutableList.builder()));
            p_decode_2_.entries().forEach((p_233732_3_) -> {
               DataResult<MemoryModuleType<?>> dataresult = Registry.MEMORY_MODULE_TYPE.parse(p_decode_1_, p_233732_3_.getFirst());
               DataResult<? extends Brain.MemoryCodec<?>> dataresult1 = dataresult.flatMap((p_233729_3_) -> {
                  return this.func_233728_a_(p_233729_3_, p_decode_1_, (T)p_233732_3_.getSecond());
               });
               mutableobject1.setValue(mutableobject1.getValue().apply2(Builder::add, dataresult1));
            });
            ImmutableList<Brain.MemoryCodec<?>> immutablelist = mutableobject1.getValue().resultOrPartial(Brain.field_233688_a_::error).map(Builder::build).orElseGet(ImmutableList::of);
            return DataResult.success(new Brain<>(p_233710_0_, p_233710_1_, immutablelist, mutableobject::getValue));
         }

         private <T, U> DataResult<Brain.MemoryCodec<U>> func_233728_a_(MemoryModuleType<U> p_233728_1_, DynamicOps<T> p_233728_2_, T p_233728_3_) {
            return p_233728_1_.func_234107_a_().map(DataResult::success).orElseGet(() -> {
               return DataResult.error("No codec for memory: " + p_233728_1_);
            }).flatMap((p_233731_2_) -> {
               return p_233731_2_.parse(p_233728_2_, p_233728_3_);
            }).map((p_233726_1_) -> {
               return new Brain.MemoryCodec<>(p_233728_1_, Optional.of(p_233726_1_));
            });
         }

         public <T> RecordBuilder<T> encode(Brain<E> p_encode_1_, DynamicOps<T> p_encode_2_, RecordBuilder<T> p_encode_3_) {
            p_encode_1_.func_233720_j_().forEach((p_233730_2_) -> {
               p_233730_2_.func_233740_a_(p_encode_2_, p_encode_3_);
            });
            return p_encode_3_;
         }
      }).fieldOf("memories").codec());
      return mutableobject.getValue();
   }

   public Brain(Collection<? extends MemoryModuleType<?>> p_i231494_1_, Collection<? extends SensorType<? extends Sensor<? super E>>> p_i231494_2_, ImmutableList<Brain.MemoryCodec<?>> p_i231494_3_, Supplier<Codec<Brain<E>>> p_i231494_4_) {
      this.field_233689_b_ = p_i231494_4_;

      for(MemoryModuleType<?> memorymoduletype : p_i231494_1_) {
         this.memories.put(memorymoduletype, Optional.empty());
      }

      for(SensorType<? extends Sensor<? super E>> sensortype : p_i231494_2_) {
         this.sensors.put(sensortype, sensortype.func_220995_a());
      }

      for(Sensor<? super E> sensor : this.sensors.values()) {
         for(MemoryModuleType<?> memorymoduletype1 : sensor.getUsedMemories()) {
            this.memories.put(memorymoduletype1, Optional.empty());
         }
      }

      for(Brain.MemoryCodec<?> memorycodec : p_i231494_3_) {
         memorycodec.func_233738_a_(this);
      }

   }

   public <T> DataResult<T> func_233702_a_(DynamicOps<T> p_233702_1_) {
      return this.field_233689_b_.get().encodeStart(p_233702_1_, this);
   }

   private Stream<Brain.MemoryCodec<?>> func_233720_j_() {
      return this.memories.entrySet().stream().map((p_233707_0_) -> {
         return Brain.MemoryCodec.func_233743_b_(p_233707_0_.getKey(), p_233707_0_.getValue());
      });
   }

   public boolean hasMemory(MemoryModuleType<?> typeIn) {
      return this.hasMemory(typeIn, MemoryModuleStatus.VALUE_PRESENT);
   }

   public <U> void removeMemory(MemoryModuleType<U> type) {
      this.setMemory(type, Optional.empty());
   }

   public <U> void setMemory(MemoryModuleType<U> memoryType, @Nullable U p_218205_2_) {
      this.setMemory(memoryType, Optional.ofNullable(p_218205_2_));
   }

   public <U> void func_233696_a_(MemoryModuleType<U> p_233696_1_, U p_233696_2_, long p_233696_3_) {
      this.func_233709_b_(p_233696_1_, Optional.of(Memory.func_234069_a_(p_233696_2_, p_233696_3_)));
   }

   public <U> void setMemory(MemoryModuleType<U> memoryType, Optional<? extends U> p_218226_2_) {
      this.func_233709_b_(memoryType, p_218226_2_.map(Memory::func_234068_a_));
   }

   private <U> void func_233709_b_(MemoryModuleType<U> p_233709_1_, Optional<? extends Memory<?>> p_233709_2_) {
      if (this.memories.containsKey(p_233709_1_)) {
         if (p_233709_2_.isPresent() && this.isEmptyCollection(p_233709_2_.get().func_234072_c_())) {
            this.removeMemory(p_233709_1_);
         } else {
            this.memories.put(p_233709_1_, p_233709_2_);
         }
      }

   }

   public <U> Optional<U> getMemory(MemoryModuleType<U> type) {
      return (Optional<U>) this.memories.get(type).map(Memory::func_234072_c_);
   }

   public <U> boolean func_233708_b_(MemoryModuleType<U> p_233708_1_, U p_233708_2_) {
      return !this.hasMemory(p_233708_1_) ? false : this.getMemory(p_233708_1_).filter((p_233704_1_) -> {
         return p_233704_1_.equals(p_233708_2_);
      }).isPresent();
   }

   public boolean hasMemory(MemoryModuleType<?> memoryTypeIn, MemoryModuleStatus memoryStatusIn) {
      Optional<? extends Memory<?>> optional = this.memories.get(memoryTypeIn);
      if (optional == null) {
         return false;
      } else {
         return memoryStatusIn == MemoryModuleStatus.REGISTERED || memoryStatusIn == MemoryModuleStatus.VALUE_PRESENT && optional.isPresent() || memoryStatusIn == MemoryModuleStatus.VALUE_ABSENT && !optional.isPresent();
      }
   }

   public Schedule getSchedule() {
      return this.schedule;
   }

   public void setSchedule(Schedule newSchedule) {
      this.schedule = newSchedule;
   }

   public void setDefaultActivities(Set<Activity> newActivities) {
      this.defaultActivities = newActivities;
   }

   @Deprecated
   public List<Task<? super E>> func_233712_d_() {
      List<Task<? super E>> list = new ObjectArrayList<>();

      for(Map<Activity, Set<Task<? super E>>> map : this.field_218232_c.values()) {
         for(Set<Task<? super E>> set : map.values()) {
            for(Task<? super E> task : set) {
               if (task.getStatus() == Task.Status.RUNNING) {
                  list.add(task);
               }
            }
         }
      }

      return list;
   }

   public void func_233714_e_() {
      this.func_233713_d_(this.fallbackActivity);
   }

   public Optional<Activity> func_233716_f_() {
      for(Activity activity : this.activities) {
         if (!this.defaultActivities.contains(activity)) {
            return Optional.of(activity);
         }
      }

      return Optional.empty();
   }

   public void switchTo(Activity activityIn) {
      if (this.hasRequiredMemories(activityIn)) {
         this.func_233713_d_(activityIn);
      } else {
         this.func_233714_e_();
      }

   }

   private void func_233713_d_(Activity p_233713_1_) {
      if (!this.hasActivity(p_233713_1_)) {
         this.func_233715_e_(p_233713_1_);
         this.activities.clear();
         this.activities.addAll(this.defaultActivities);
         this.activities.add(p_233713_1_);
      }
   }

   private void func_233715_e_(Activity p_233715_1_) {
      for(Activity activity : this.activities) {
         if (activity != p_233715_1_) {
            Set<MemoryModuleType<?>> set = this.field_233691_h_.get(activity);
            if (set != null) {
               for(MemoryModuleType<?> memorymoduletype : set) {
                  this.removeMemory(memorymoduletype);
               }
            }
         }
      }

   }

   public void updateActivity(long dayTime, long gameTime) {
      if (gameTime - this.lastGameTime > 20L) {
         this.lastGameTime = gameTime;
         Activity activity = this.getSchedule().getScheduledActivity((int)(dayTime % 24000L));
         if (!this.activities.contains(activity)) {
            this.switchTo(activity);
         }
      }

   }

   public void func_233706_a_(List<Activity> p_233706_1_) {
      for(Activity activity : p_233706_1_) {
         if (this.hasRequiredMemories(activity)) {
            this.func_233713_d_(activity);
            break;
         }
      }

   }

   public void setFallbackActivity(Activity newFallbackActivity) {
      this.fallbackActivity = newFallbackActivity;
   }

   public void func_233698_a_(Activity p_233698_1_, int p_233698_2_, ImmutableList<? extends Task<? super E>> p_233698_3_) {
      this.registerActivity(p_233698_1_, this.func_233692_a_(p_233698_2_, p_233698_3_));
   }

   public void func_233699_a_(Activity p_233699_1_, int p_233699_2_, ImmutableList<? extends Task<? super E>> p_233699_3_, MemoryModuleType<?> p_233699_4_) {
      Set<Pair<MemoryModuleType<?>, MemoryModuleStatus>> set = ImmutableSet.of(Pair.of(p_233699_4_, MemoryModuleStatus.VALUE_PRESENT));
      Set<MemoryModuleType<?>> set1 = ImmutableSet.of(p_233699_4_);
      this.func_233701_a_(p_233699_1_, this.func_233692_a_(p_233699_2_, p_233699_3_), set, set1);
   }

   public void registerActivity(Activity activityIn, ImmutableList<? extends Pair<Integer, ? extends Task<? super E>>> p_218208_2_) {
      this.func_233701_a_(activityIn, p_218208_2_, ImmutableSet.of(), Sets.newHashSet());
   }

   public void func_233700_a_(Activity p_233700_1_, ImmutableList<? extends Pair<Integer, ? extends Task<? super E>>> p_233700_2_, Set<Pair<MemoryModuleType<?>, MemoryModuleStatus>> p_233700_3_) {
      this.func_233701_a_(p_233700_1_, p_233700_2_, p_233700_3_, Sets.newHashSet());
   }

   private void func_233701_a_(Activity p_233701_1_, ImmutableList<? extends Pair<Integer, ? extends Task<? super E>>> p_233701_2_, Set<Pair<MemoryModuleType<?>, MemoryModuleStatus>> p_233701_3_, Set<MemoryModuleType<?>> p_233701_4_) {
      this.requiredMemoryStates.put(p_233701_1_, p_233701_3_);
      if (!p_233701_4_.isEmpty()) {
         this.field_233691_h_.put(p_233701_1_, p_233701_4_);
      }

      for(Pair<Integer, ? extends Task<? super E>> pair : p_233701_2_) {
         this.field_218232_c.computeIfAbsent(pair.getFirst(), (p_233703_0_) -> {
            return Maps.newHashMap();
         }).computeIfAbsent(p_233701_1_, (p_233717_0_) -> {
            return Sets.newLinkedHashSet();
         }).add(pair.getSecond());
      }

   }

   public boolean hasActivity(Activity activityIn) {
      return this.activities.contains(activityIn);
   }

   public Brain<E> copy() {
      Brain<E> brain = new Brain<>(this.memories.keySet(), this.sensors.keySet(), ImmutableList.of(), this.field_233689_b_);

      for(Entry<MemoryModuleType<?>, Optional<? extends Memory<?>>> entry : this.memories.entrySet()) {
         MemoryModuleType<?> memorymoduletype = entry.getKey();
         if (entry.getValue().isPresent()) {
            brain.memories.put(memorymoduletype, entry.getValue());
         }
      }

      return brain;
   }

   public void tick(ServerWorld worldIn, E entityIn) {
      this.func_233721_k_();
      this.func_233711_c_(worldIn, entityIn);
      this.startTasks(worldIn, entityIn);
      this.tickTasks(worldIn, entityIn);
   }

   private void func_233711_c_(ServerWorld p_233711_1_, E p_233711_2_) {
      for(Sensor<? super E> sensor : this.sensors.values()) {
         sensor.tick(p_233711_1_, p_233711_2_);
      }

   }

   private void func_233721_k_() {
      for(Entry<MemoryModuleType<?>, Optional<? extends Memory<?>>> entry : this.memories.entrySet()) {
         if (entry.getValue().isPresent()) {
            Memory<?> memory = entry.getValue().get();
            memory.func_234064_a_();
            if (memory.func_234073_d_()) {
               this.removeMemory(entry.getKey());
            }
         }
      }

   }

   public void stopAllTasks(ServerWorld worldIn, E owner) {
      long i = owner.world.getGameTime();

      for(Task<? super E> task : this.func_233712_d_()) {
         task.stop(worldIn, owner, i);
      }

   }

   private void startTasks(ServerWorld worldIn, E entityIn) {
      long i = worldIn.getGameTime();

      for(Map<Activity, Set<Task<? super E>>> map : this.field_218232_c.values()) {
         for(Entry<Activity, Set<Task<? super E>>> entry : map.entrySet()) {
            Activity activity = entry.getKey();
            if (this.activities.contains(activity)) {
               for(Task<? super E> task : entry.getValue()) {
                  if (task.getStatus() == Task.Status.STOPPED) {
                     task.start(worldIn, entityIn, i);
                  }
               }
            }
         }
      }

   }

   private void tickTasks(ServerWorld worldIn, E entityIn) {
      long i = worldIn.getGameTime();

      for(Task<? super E> task : this.func_233712_d_()) {
         task.tick(worldIn, entityIn, i);
      }

   }

   private boolean hasRequiredMemories(Activity activityIn) {
      if (!this.requiredMemoryStates.containsKey(activityIn)) {
         return false;
      } else {
         for(Pair<MemoryModuleType<?>, MemoryModuleStatus> pair : this.requiredMemoryStates.get(activityIn)) {
            MemoryModuleType<?> memorymoduletype = pair.getFirst();
            MemoryModuleStatus memorymodulestatus = pair.getSecond();
            if (!this.hasMemory(memorymoduletype, memorymodulestatus)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean isEmptyCollection(Object p_218213_1_) {
      return p_218213_1_ instanceof Collection && ((Collection)p_218213_1_).isEmpty();
   }

   ImmutableList<? extends Pair<Integer, ? extends Task<? super E>>> func_233692_a_(int p_233692_1_, ImmutableList<? extends Task<? super E>> p_233692_2_) {
      int i = p_233692_1_;
      Builder<Pair<Integer, ? extends Task<? super E>>> builder = ImmutableList.builder();

      for(Task<? super E> task : p_233692_2_) {
         builder.add(Pair.of(i++, task));
      }

      return builder.build();
   }

   public static final class BrainCodec<E extends LivingEntity> {
      private final Collection<? extends MemoryModuleType<?>> field_233744_a_;
      private final Collection<? extends SensorType<? extends Sensor<? super E>>> field_233745_b_;
      private final Codec<Brain<E>> field_233746_c_;

      private BrainCodec(Collection<? extends MemoryModuleType<?>> p_i231498_1_, Collection<? extends SensorType<? extends Sensor<? super E>>> p_i231498_2_) {
         this.field_233744_a_ = p_i231498_1_;
         this.field_233745_b_ = p_i231498_2_;
         this.field_233746_c_ = Brain.func_233710_b_(p_i231498_1_, p_i231498_2_);
      }

      public Brain<E> func_233748_a_(Dynamic<?> p_233748_1_) {
         return this.field_233746_c_.parse(p_233748_1_).resultOrPartial(Brain.field_233688_a_::error).orElseGet(() -> {
            return new Brain<>(this.field_233744_a_, this.field_233745_b_, ImmutableList.of(), () -> {
               return this.field_233746_c_;
            });
         });
      }
   }

   static final class MemoryCodec<U> {
      private final MemoryModuleType<U> field_233735_a_;
      private final Optional<? extends Memory<U>> field_233736_b_;

      private static <U> Brain.MemoryCodec<U> func_233743_b_(MemoryModuleType<U> p_233743_0_, Optional<? extends Memory<?>> p_233743_1_) {
         return new Brain.MemoryCodec<>(p_233743_0_, (Optional<? extends Memory<U>>) p_233743_1_);
      }

      private MemoryCodec(MemoryModuleType<U> p_i231496_1_, Optional<? extends Memory<U>> p_i231496_2_) {
         this.field_233735_a_ = p_i231496_1_;
         this.field_233736_b_ = p_i231496_2_;
      }

      private void func_233738_a_(Brain<?> p_233738_1_) {
         p_233738_1_.func_233709_b_(this.field_233735_a_, this.field_233736_b_);
      }

      public <T> void func_233740_a_(DynamicOps<T> p_233740_1_, RecordBuilder<T> p_233740_2_) {
         this.field_233735_a_.func_234107_a_().ifPresent((p_233741_3_) -> {
            this.field_233736_b_.ifPresent((p_233742_4_) -> {
               p_233740_2_.add(Registry.MEMORY_MODULE_TYPE.encodeStart(p_233740_1_, this.field_233735_a_), p_233741_3_.encodeStart(p_233740_1_, p_233742_4_));
            });
         });
      }
   }
}