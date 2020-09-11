package net.minecraft.world.chunk;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.concurrent.DelegatedTaskExecutor;
import net.minecraft.util.concurrent.ITaskExecutor;
import net.minecraft.util.concurrent.ITaskQueue;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.server.ChunkHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkTaskPriorityQueueSorter implements AutoCloseable, ChunkHolder.IListener {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Map<ITaskExecutor<?>, ChunkTaskPriorityQueue<? extends Function<ITaskExecutor<Unit>, ?>>> queues;
   private final Set<ITaskExecutor<?>> field_219095_c;
   private final DelegatedTaskExecutor<ITaskQueue.RunnableWithPriority> sorter;

   public ChunkTaskPriorityQueueSorter(List<ITaskExecutor<?>> p_i50713_1_, Executor p_i50713_2_, int p_i50713_3_) {
      this.queues = p_i50713_1_.stream().collect(Collectors.toMap(Function.identity(), (p_219084_1_) -> {
         return new ChunkTaskPriorityQueue<>(p_219084_1_.getName() + "_queue", p_i50713_3_);
      }));
      this.field_219095_c = Sets.newHashSet(p_i50713_1_);
      this.sorter = new DelegatedTaskExecutor<>(new ITaskQueue.Priority(4), p_i50713_2_, "sorter");
   }

   public static ChunkTaskPriorityQueueSorter.FunctionEntry<Runnable> func_219069_a(Runnable p_219069_0_, long pos, IntSupplier p_219069_3_) {
      return new ChunkTaskPriorityQueueSorter.FunctionEntry<>((p_219072_1_) -> {
         return () -> {
            p_219069_0_.run();
            p_219072_1_.enqueue(Unit.INSTANCE);
         };
      }, pos, p_219069_3_);
   }

   public static ChunkTaskPriorityQueueSorter.FunctionEntry<Runnable> func_219081_a(ChunkHolder p_219081_0_, Runnable p_219081_1_) {
      return func_219069_a(p_219081_1_, p_219081_0_.getPosition().asLong(), p_219081_0_::func_219281_j);
   }

   public static ChunkTaskPriorityQueueSorter.RunnableEntry func_219073_a(Runnable p_219073_0_, long p_219073_1_, boolean p_219073_3_) {
      return new ChunkTaskPriorityQueueSorter.RunnableEntry(p_219073_0_, p_219073_1_, p_219073_3_);
   }

   public <T> ITaskExecutor<ChunkTaskPriorityQueueSorter.FunctionEntry<T>> func_219087_a(ITaskExecutor<T> p_219087_1_, boolean p_219087_2_) {
      return this.sorter.<ITaskExecutor<FunctionEntry<T>>>func_213141_a((p_219086_3_) -> {
         return new ITaskQueue.RunnableWithPriority(0, () -> {
            this.getQueue(p_219087_1_);
            p_219086_3_.enqueue(ITaskExecutor.inline("chunk priority sorter around " + p_219087_1_.getName(), (p_219071_3_) -> {
               this.func_219067_a(p_219087_1_, p_219071_3_.task, p_219071_3_.chunkPos, p_219071_3_.field_219430_c, p_219087_2_);
            }));
         });
      }).join();
   }

   public ITaskExecutor<ChunkTaskPriorityQueueSorter.RunnableEntry> func_219091_a(ITaskExecutor<Runnable> p_219091_1_) {
      return this.sorter.<ITaskExecutor<RunnableEntry>>func_213141_a((p_219080_2_) -> {
         return new ITaskQueue.RunnableWithPriority(0, () -> {
            p_219080_2_.enqueue(ITaskExecutor.inline("chunk priority sorter around " + p_219091_1_.getName(), (p_219075_2_) -> {
               this.func_219074_a(p_219091_1_, p_219075_2_.field_219435_b, p_219075_2_.field_219434_a, p_219075_2_.field_219436_c);
            }));
         });
      }).join();
   }

   public void func_219066_a(ChunkPos pos, IntSupplier p_219066_2_, int p_219066_3_, IntConsumer p_219066_4_) {
      this.sorter.enqueue(new ITaskQueue.RunnableWithPriority(0, () -> {
         int i = p_219066_2_.getAsInt();
         this.queues.values().forEach((p_219076_3_) -> {
            p_219076_3_.func_219407_a(i, pos, p_219066_3_);
         });
         p_219066_4_.accept(p_219066_3_);
      }));
   }

   private <T> void func_219074_a(ITaskExecutor<T> p_219074_1_, long p_219074_2_, Runnable p_219074_4_, boolean p_219074_5_) {
      this.sorter.enqueue(new ITaskQueue.RunnableWithPriority(1, () -> {
         ChunkTaskPriorityQueue<Function<ITaskExecutor<Unit>, T>> chunktaskpriorityqueue = this.getQueue(p_219074_1_);
         chunktaskpriorityqueue.func_219416_a(p_219074_2_, p_219074_5_);
         if (this.field_219095_c.remove(p_219074_1_)) {
            this.func_219078_a(chunktaskpriorityqueue, p_219074_1_);
         }

         p_219074_4_.run();
      }));
   }

   private <T> void func_219067_a(ITaskExecutor<T> p_219067_1_, Function<ITaskExecutor<Unit>, T> p_219067_2_, long p_219067_3_, IntSupplier p_219067_5_, boolean p_219067_6_) {
      this.sorter.enqueue(new ITaskQueue.RunnableWithPriority(2, () -> {
         ChunkTaskPriorityQueue<Function<ITaskExecutor<Unit>, T>> chunktaskpriorityqueue = this.getQueue(p_219067_1_);
         int i = p_219067_5_.getAsInt();
         chunktaskpriorityqueue.func_219412_a(Optional.of(p_219067_2_), p_219067_3_, i);
         if (p_219067_6_) {
            chunktaskpriorityqueue.func_219412_a(Optional.empty(), p_219067_3_, i);
         }

         if (this.field_219095_c.remove(p_219067_1_)) {
            this.func_219078_a(chunktaskpriorityqueue, p_219067_1_);
         }

      }));
   }

   private <T> void func_219078_a(ChunkTaskPriorityQueue<Function<ITaskExecutor<Unit>, T>> p_219078_1_, ITaskExecutor<T> p_219078_2_) {
      this.sorter.enqueue(new ITaskQueue.RunnableWithPriority(3, () -> {
         Stream<Either<Function<ITaskExecutor<Unit>, T>, Runnable>> stream = p_219078_1_.func_219417_a();
         if (stream == null) {
            this.field_219095_c.add(p_219078_2_);
         } else {
            Util.gather(stream.map((p_219092_1_) -> {
               return p_219092_1_.map(p_219078_2_::func_213141_a, (p_219077_0_) -> {
                  p_219077_0_.run();
                  return CompletableFuture.completedFuture(Unit.INSTANCE);
               });
            }).collect(Collectors.toList())).thenAccept((p_219088_3_) -> {
               this.func_219078_a(p_219078_1_, p_219078_2_);
            });
         }

      }));
   }

   private <T> ChunkTaskPriorityQueue<Function<ITaskExecutor<Unit>, T>> getQueue(ITaskExecutor<T> p_219068_1_) {
      ChunkTaskPriorityQueue<? extends Function<ITaskExecutor<Unit>, ?>> chunktaskpriorityqueue = this.queues.get(p_219068_1_);
      if (chunktaskpriorityqueue == null) {
         throw (IllegalArgumentException)Util.pauseDevMode(new IllegalArgumentException("No queue for: " + p_219068_1_));
      } else {
         return (ChunkTaskPriorityQueue<Function<ITaskExecutor<Unit>, T>>) chunktaskpriorityqueue;
      }
   }

   @VisibleForTesting
   public String func_225396_a() {
      return (String)this.queues.entrySet().stream().map((p_225397_0_) -> {
         return p_225397_0_.getKey().getName() + "=[" + (String)p_225397_0_.getValue().func_225414_b().stream().map((p_225398_0_) -> {
            return p_225398_0_ + ":" + new ChunkPos(p_225398_0_);
         }).collect(Collectors.joining(",")) + "]";
      }).collect(Collectors.joining(",")) + ", s=" + this.field_219095_c.size();
   }

   public void close() {
      this.queues.keySet().forEach(ITaskExecutor::close);
   }

   public static final class FunctionEntry<T> {
      private final Function<ITaskExecutor<Unit>, T> task;
      private final long chunkPos;
      private final IntSupplier field_219430_c;

      private FunctionEntry(Function<ITaskExecutor<Unit>, T> p_i50028_1_, long p_i50028_2_, IntSupplier p_i50028_4_) {
         this.task = p_i50028_1_;
         this.chunkPos = p_i50028_2_;
         this.field_219430_c = p_i50028_4_;
      }
   }

   public static final class RunnableEntry {
      private final Runnable field_219434_a;
      private final long field_219435_b;
      private final boolean field_219436_c;

      private RunnableEntry(Runnable p_i50026_1_, long p_i50026_2_, boolean p_i50026_4_) {
         this.field_219434_a = p_i50026_1_;
         this.field_219435_b = p_i50026_2_;
         this.field_219436_c = p_i50026_4_;
      }
   }
}