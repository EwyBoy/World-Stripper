package net.minecraft.world.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntMaps;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.SortedArraySet;
import net.minecraft.util.concurrent.ITaskExecutor;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkDistanceGraph;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.ChunkTaskPriorityQueueSorter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TicketManager {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int PLAYER_TICKET_LEVEL = 33 + ChunkStatus.getDistance(ChunkStatus.FULL) - 2;
   private final Long2ObjectMap<ObjectSet<ServerPlayerEntity>> playersByChunkPos = new Long2ObjectOpenHashMap<>();
   private final Long2ObjectOpenHashMap<SortedArraySet<Ticket<?>>> tickets = new Long2ObjectOpenHashMap<>();
   private final TicketManager.ChunkTicketTracker ticketTracker = new TicketManager.ChunkTicketTracker();
   private final TicketManager.PlayerChunkTracker playerChunkTracker = new TicketManager.PlayerChunkTracker(8);
   private final TicketManager.PlayerTicketTracker playerTicketTracker = new TicketManager.PlayerTicketTracker(33);
   private final Set<ChunkHolder> chunkHolders = Sets.newHashSet();
   private final ChunkTaskPriorityQueueSorter field_219384_l;
   private final ITaskExecutor<ChunkTaskPriorityQueueSorter.FunctionEntry<Runnable>> field_219385_m;
   private final ITaskExecutor<ChunkTaskPriorityQueueSorter.RunnableEntry> field_219386_n;
   private final LongSet field_219387_o = new LongOpenHashSet();
   private final Executor field_219388_p;
   private long currentTime;

   protected TicketManager(Executor p_i50707_1_, Executor p_i50707_2_) {
      ITaskExecutor<Runnable> itaskexecutor = ITaskExecutor.inline("player ticket throttler", p_i50707_2_::execute);
      ChunkTaskPriorityQueueSorter chunktaskpriorityqueuesorter = new ChunkTaskPriorityQueueSorter(ImmutableList.of(itaskexecutor), p_i50707_1_, 4);
      this.field_219384_l = chunktaskpriorityqueuesorter;
      this.field_219385_m = chunktaskpriorityqueuesorter.func_219087_a(itaskexecutor, true);
      this.field_219386_n = chunktaskpriorityqueuesorter.func_219091_a(itaskexecutor);
      this.field_219388_p = p_i50707_2_;
   }

   protected void tick() {
      ++this.currentTime;
      ObjectIterator<Entry<SortedArraySet<Ticket<?>>>> objectiterator = this.tickets.long2ObjectEntrySet().fastIterator();

      while(objectiterator.hasNext()) {
         Entry<SortedArraySet<Ticket<?>>> entry = objectiterator.next();
         if (entry.getValue().removeIf((p_219370_1_) -> {
            return p_219370_1_.isExpired(this.currentTime);
         })) {
            this.ticketTracker.updateSourceLevel(entry.getLongKey(), func_229844_a_(entry.getValue()), false);
         }

         if (entry.getValue().isEmpty()) {
            objectiterator.remove();
         }
      }

   }

   private static int func_229844_a_(SortedArraySet<Ticket<?>> p_229844_0_) {
      return !p_229844_0_.isEmpty() ? p_229844_0_.getSmallest().getLevel() : ChunkManager.MAX_LOADED_LEVEL + 1;
   }

   protected abstract boolean contains(long p_219371_1_);

   @Nullable
   protected abstract ChunkHolder func_219335_b(long chunkPosIn);

   @Nullable
   protected abstract ChunkHolder setChunkLevel(long chunkPosIn, int newLevel, @Nullable ChunkHolder holder, int oldLevel);

   public boolean processUpdates(ChunkManager p_219353_1_) {
      this.playerChunkTracker.processAllUpdates();
      this.playerTicketTracker.processAllUpdates();
      int i = Integer.MAX_VALUE - this.ticketTracker.func_215493_a(Integer.MAX_VALUE);
      boolean flag = i != 0;
      if (flag) {
      }

      if (!this.chunkHolders.isEmpty()) {
         this.chunkHolders.forEach((p_219343_1_) -> {
            p_219343_1_.processUpdates(p_219353_1_);
         });
         this.chunkHolders.clear();
         return true;
      } else {
         if (!this.field_219387_o.isEmpty()) {
            LongIterator longiterator = this.field_219387_o.iterator();

            while(longiterator.hasNext()) {
               long j = longiterator.nextLong();
               if (this.getTicketSet(j).stream().anyMatch((p_219369_0_) -> {
                  return p_219369_0_.getType() == TicketType.PLAYER;
               })) {
                  ChunkHolder chunkholder = p_219353_1_.func_219220_a(j);
                  if (chunkholder == null) {
                     throw new IllegalStateException();
                  }

                  CompletableFuture<Either<Chunk, ChunkHolder.IChunkLoadingError>> completablefuture = chunkholder.getEntityTickingFuture();
                  completablefuture.thenAccept((p_219363_3_) -> {
                     this.field_219388_p.execute(() -> {
                        this.field_219386_n.enqueue(ChunkTaskPriorityQueueSorter.func_219073_a(() -> {
                        }, j, false));
                     });
                  });
               }
            }

            this.field_219387_o.clear();
         }

         return flag;
      }
   }

   private void register(long chunkPosIn, Ticket<?> ticketIn) {
      SortedArraySet<Ticket<?>> sortedarrayset = this.getTicketSet(chunkPosIn);
      int i = func_229844_a_(sortedarrayset);
      Ticket<?> ticket = sortedarrayset.func_226175_a_(ticketIn);
      ticket.setTimestamp(this.currentTime);
      if (ticketIn.getLevel() < i) {
         this.ticketTracker.updateSourceLevel(chunkPosIn, ticketIn.getLevel(), true);
      }

   }

   private void release(long chunkPosIn, Ticket<?> ticketIn) {
      SortedArraySet<Ticket<?>> sortedarrayset = this.getTicketSet(chunkPosIn);
      if (sortedarrayset.remove(ticketIn)) {
      }

      if (sortedarrayset.isEmpty()) {
         this.tickets.remove(chunkPosIn);
      }

      this.ticketTracker.updateSourceLevel(chunkPosIn, func_229844_a_(sortedarrayset), false);
   }

   public <T> void registerWithLevel(TicketType<T> type, ChunkPos pos, int level, T value) {
      this.register(pos.asLong(), new Ticket<>(type, level, value));
   }

   public <T> void releaseWithLevel(TicketType<T> type, ChunkPos pos, int level, T value) {
      Ticket<T> ticket = new Ticket<>(type, level, value);
      this.release(pos.asLong(), ticket);
   }

   public <T> void register(TicketType<T> type, ChunkPos pos, int distance, T value) {
      this.register(pos.asLong(), new Ticket<>(type, 33 - distance, value));
   }

   public <T> void release(TicketType<T> type, ChunkPos pos, int distance, T value) {
      Ticket<T> ticket = new Ticket<>(type, 33 - distance, value);
      this.release(pos.asLong(), ticket);
   }

   private SortedArraySet<Ticket<?>> getTicketSet(long p_229848_1_) {
      return this.tickets.computeIfAbsent(p_229848_1_, (p_229851_0_) -> {
         return SortedArraySet.newSet(4);
      });
   }

   protected void forceChunk(ChunkPos pos, boolean add) {
      Ticket<ChunkPos> ticket = new Ticket<>(TicketType.FORCED, 31, pos);
      if (add) {
         this.register(pos.asLong(), ticket);
      } else {
         this.release(pos.asLong(), ticket);
      }

   }

   public void updatePlayerPosition(SectionPos sectionPosIn, ServerPlayerEntity player) {
      long i = sectionPosIn.asChunkPos().asLong();
      this.playersByChunkPos.computeIfAbsent(i, (p_219361_0_) -> {
         return new ObjectOpenHashSet();
      }).add(player);
      this.playerChunkTracker.updateSourceLevel(i, 0, true);
      this.playerTicketTracker.updateSourceLevel(i, 0, true);
   }

   public void removePlayer(SectionPos sectionPosIn, ServerPlayerEntity player) {
      long i = sectionPosIn.asChunkPos().asLong();
      ObjectSet<ServerPlayerEntity> objectset = this.playersByChunkPos.get(i);
      objectset.remove(player);
      if (objectset.isEmpty()) {
         this.playersByChunkPos.remove(i);
         this.playerChunkTracker.updateSourceLevel(i, Integer.MAX_VALUE, false);
         this.playerTicketTracker.updateSourceLevel(i, Integer.MAX_VALUE, false);
      }

   }

   protected String func_225413_c(long p_225413_1_) {
      SortedArraySet<Ticket<?>> sortedarrayset = this.tickets.get(p_225413_1_);
      String s;
      if (sortedarrayset != null && !sortedarrayset.isEmpty()) {
         s = sortedarrayset.getSmallest().toString();
      } else {
         s = "no_ticket";
      }

      return s;
   }

   protected void setViewDistance(int viewDistance) {
      this.playerTicketTracker.setViewDistance(viewDistance);
   }

   /**
    * Returns the number of chunks taken into account when calculating the mob cap
    */
   public int getSpawningChunksCount() {
      this.playerChunkTracker.processAllUpdates();
      return this.playerChunkTracker.chunksInRange.size();
   }

   public boolean isOutsideSpawningRadius(long chunkPosIn) {
      this.playerChunkTracker.processAllUpdates();
      return this.playerChunkTracker.chunksInRange.containsKey(chunkPosIn);
   }

   public String func_225412_c() {
      return this.field_219384_l.func_225396_a();
   }

   class ChunkTicketTracker extends ChunkDistanceGraph {
      public ChunkTicketTracker() {
         super(ChunkManager.MAX_LOADED_LEVEL + 2, 16, 256);
      }

      protected int getSourceLevel(long pos) {
         SortedArraySet<Ticket<?>> sortedarrayset = TicketManager.this.tickets.get(pos);
         if (sortedarrayset == null) {
            return Integer.MAX_VALUE;
         } else {
            return sortedarrayset.isEmpty() ? Integer.MAX_VALUE : sortedarrayset.getSmallest().getLevel();
         }
      }

      protected int getLevel(long sectionPosIn) {
         if (!TicketManager.this.contains(sectionPosIn)) {
            ChunkHolder chunkholder = TicketManager.this.func_219335_b(sectionPosIn);
            if (chunkholder != null) {
               return chunkholder.getChunkLevel();
            }
         }

         return ChunkManager.MAX_LOADED_LEVEL + 1;
      }

      protected void setLevel(long sectionPosIn, int level) {
         ChunkHolder chunkholder = TicketManager.this.func_219335_b(sectionPosIn);
         int i = chunkholder == null ? ChunkManager.MAX_LOADED_LEVEL + 1 : chunkholder.getChunkLevel();
         if (i != level) {
            chunkholder = TicketManager.this.setChunkLevel(sectionPosIn, level, chunkholder, i);
            if (chunkholder != null) {
               TicketManager.this.chunkHolders.add(chunkholder);
            }

         }
      }

      public int func_215493_a(int p_215493_1_) {
         return this.processUpdates(p_215493_1_);
      }
   }

   class PlayerChunkTracker extends ChunkDistanceGraph {
      /** Chunks that are at most {@link #range} chunks away from the closest player. */
      protected final Long2ByteMap chunksInRange = new Long2ByteOpenHashMap();
      protected final int range;

      protected PlayerChunkTracker(int p_i50684_2_) {
         super(p_i50684_2_ + 2, 16, 256);
         this.range = p_i50684_2_;
         this.chunksInRange.defaultReturnValue((byte)(p_i50684_2_ + 2));
      }

      protected int getLevel(long sectionPosIn) {
         return this.chunksInRange.get(sectionPosIn);
      }

      protected void setLevel(long sectionPosIn, int level) {
         byte b0;
         if (level > this.range) {
            b0 = this.chunksInRange.remove(sectionPosIn);
         } else {
            b0 = this.chunksInRange.put(sectionPosIn, (byte)level);
         }

         this.chunkLevelChanged(sectionPosIn, b0, level);
      }

      /**
       * Called after {@link PlayerChunkTracker#setLevel(long, int)} puts/removes chunk into/from {@link
       * #chunksInRange}.
       *  
       * @param oldLevel Previous level of the chunk if it was smaller than {@link #range}, {@code range + 2} otherwise.
       */
      protected void chunkLevelChanged(long chunkPosIn, int oldLevel, int newLevel) {
      }

      protected int getSourceLevel(long pos) {
         return this.hasPlayerInChunk(pos) ? 0 : Integer.MAX_VALUE;
      }

      private boolean hasPlayerInChunk(long chunkPosIn) {
         ObjectSet<ServerPlayerEntity> objectset = TicketManager.this.playersByChunkPos.get(chunkPosIn);
         return objectset != null && !objectset.isEmpty();
      }

      public void processAllUpdates() {
         this.processUpdates(Integer.MAX_VALUE);
      }
   }

   class PlayerTicketTracker extends TicketManager.PlayerChunkTracker {
      private int viewDistance;
      private final Long2IntMap field_215513_f = Long2IntMaps.synchronize(new Long2IntOpenHashMap());
      private final LongSet field_215514_g = new LongOpenHashSet();

      protected PlayerTicketTracker(int p_i50682_2_) {
         super(p_i50682_2_);
         this.viewDistance = 0;
         this.field_215513_f.defaultReturnValue(p_i50682_2_ + 2);
      }

      /**
       * Called after {@link PlayerChunkTracker#setLevel(long, int)} puts/removes chunk into/from {@link
       * #chunksInRange}.
       *  
       * @param oldLevel Previous level of the chunk if it was smaller than {@link #range}, {@code range + 2} otherwise.
       */
      protected void chunkLevelChanged(long chunkPosIn, int oldLevel, int newLevel) {
         this.field_215514_g.add(chunkPosIn);
      }

      public void setViewDistance(int viewDistanceIn) {
         for(it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry entry : this.chunksInRange.long2ByteEntrySet()) {
            byte b0 = entry.getByteValue();
            long i = entry.getLongKey();
            this.func_215504_a(i, b0, this.func_215505_c(b0), b0 <= viewDistanceIn - 2);
         }

         this.viewDistance = viewDistanceIn;
      }

      private void func_215504_a(long chunkPosIn, int p_215504_3_, boolean p_215504_4_, boolean p_215504_5_) {
         if (p_215504_4_ != p_215504_5_) {
            Ticket<?> ticket = new Ticket<>(TicketType.PLAYER, TicketManager.PLAYER_TICKET_LEVEL, new ChunkPos(chunkPosIn));
            if (p_215504_5_) {
               TicketManager.this.field_219385_m.enqueue(ChunkTaskPriorityQueueSorter.func_219069_a(() -> {
                  TicketManager.this.field_219388_p.execute(() -> {
                     if (this.func_215505_c(this.getLevel(chunkPosIn))) {
                        TicketManager.this.register(chunkPosIn, ticket);
                        TicketManager.this.field_219387_o.add(chunkPosIn);
                     } else {
                        TicketManager.this.field_219386_n.enqueue(ChunkTaskPriorityQueueSorter.func_219073_a(() -> {
                        }, chunkPosIn, false));
                     }

                  });
               }, chunkPosIn, () -> {
                  return p_215504_3_;
               }));
            } else {
               TicketManager.this.field_219386_n.enqueue(ChunkTaskPriorityQueueSorter.func_219073_a(() -> {
                  TicketManager.this.field_219388_p.execute(() -> {
                     TicketManager.this.release(chunkPosIn, ticket);
                  });
               }, chunkPosIn, true));
            }
         }

      }

      public void processAllUpdates() {
         super.processAllUpdates();
         if (!this.field_215514_g.isEmpty()) {
            LongIterator longiterator = this.field_215514_g.iterator();

            while(longiterator.hasNext()) {
               long i = longiterator.nextLong();
               int j = this.field_215513_f.get(i);
               int k = this.getLevel(i);
               if (j != k) {
                  TicketManager.this.field_219384_l.func_219066_a(new ChunkPos(i), () -> {
                     return this.field_215513_f.get(i);
                  }, k, (p_215506_3_) -> {
                     if (p_215506_3_ >= this.field_215513_f.defaultReturnValue()) {
                        this.field_215513_f.remove(i);
                     } else {
                        this.field_215513_f.put(i, p_215506_3_);
                     }

                  });
                  this.func_215504_a(i, k, this.func_215505_c(j), this.func_215505_c(k));
               }
            }

            this.field_215514_g.clear();
         }

      }

      private boolean func_215505_c(int p_215505_1_) {
         return p_215505_1_ <= this.viewDistance - 2;
      }
   }
}