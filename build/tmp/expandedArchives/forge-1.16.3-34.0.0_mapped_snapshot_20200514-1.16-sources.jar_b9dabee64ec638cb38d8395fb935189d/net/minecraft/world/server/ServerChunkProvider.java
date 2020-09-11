package net.minecraft.world.server;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.profiler.IProfiler;
import net.minecraft.util.Util;
import net.minecraft.util.concurrent.ThreadTaskExecutor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.SectionPos;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.chunk.listener.IChunkStatusListener;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.IWorldInfo;
import net.minecraft.world.storage.SaveFormat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ServerChunkProvider extends AbstractChunkProvider {
   private static final List<ChunkStatus> field_217239_c = ChunkStatus.getAll();
   private final TicketManager ticketManager;
   public final ChunkGenerator generator;
   public final ServerWorld world;
   private final Thread mainThread;
   private final ServerWorldLightManager lightManager;
   private final ServerChunkProvider.ChunkExecutor executor;
   public final ChunkManager chunkManager;
   private final DimensionSavedDataManager savedData;
   private long lastGameTime;
   private boolean spawnHostiles = true;
   private boolean spawnPassives = true;
   private final long[] recentPositions = new long[4];
   private final ChunkStatus[] recentStatuses = new ChunkStatus[4];
   private final IChunk[] recentChunks = new IChunk[4];
   @Nullable
   private WorldEntitySpawner.EntityDensityManager field_241097_p_;

   public ServerChunkProvider(ServerWorld p_i232603_1_, SaveFormat.LevelSave p_i232603_2_, DataFixer p_i232603_3_, TemplateManager p_i232603_4_, Executor p_i232603_5_, ChunkGenerator p_i232603_6_, int p_i232603_7_, boolean p_i232603_8_, IChunkStatusListener p_i232603_9_, Supplier<DimensionSavedDataManager> p_i232603_10_) {
      this.world = p_i232603_1_;
      this.executor = new ServerChunkProvider.ChunkExecutor(p_i232603_1_);
      this.generator = p_i232603_6_;
      this.mainThread = Thread.currentThread();
      File file1 = p_i232603_2_.func_237291_a_(p_i232603_1_.func_234923_W_());
      File file2 = new File(file1, "data");
      file2.mkdirs();
      this.savedData = new DimensionSavedDataManager(file2, p_i232603_3_);
      this.chunkManager = new ChunkManager(p_i232603_1_, p_i232603_2_, p_i232603_3_, p_i232603_4_, p_i232603_5_, this.executor, this, this.getChunkGenerator(), p_i232603_9_, p_i232603_10_, p_i232603_7_, p_i232603_8_);
      this.lightManager = this.chunkManager.getLightManager();
      this.ticketManager = this.chunkManager.getTicketManager();
      this.invalidateCaches();
   }

   public ServerWorldLightManager getLightManager() {
      return this.lightManager;
   }

   @Nullable
   private ChunkHolder func_217213_a(long chunkPosIn) {
      return this.chunkManager.func_219219_b(chunkPosIn);
   }

   public int func_217229_b() {
      return this.chunkManager.func_219174_c();
   }

   private void func_225315_a(long p_225315_1_, IChunk p_225315_3_, ChunkStatus p_225315_4_) {
      for(int i = 3; i > 0; --i) {
         this.recentPositions[i] = this.recentPositions[i - 1];
         this.recentStatuses[i] = this.recentStatuses[i - 1];
         this.recentChunks[i] = this.recentChunks[i - 1];
      }

      this.recentPositions[0] = p_225315_1_;
      this.recentStatuses[0] = p_225315_4_;
      this.recentChunks[0] = p_225315_3_;
   }

   @Nullable
   public IChunk getChunk(int chunkX, int chunkZ, ChunkStatus requiredStatus, boolean load) {
      if (Thread.currentThread() != this.mainThread) {
         return CompletableFuture.supplyAsync(() -> {
            return this.getChunk(chunkX, chunkZ, requiredStatus, load);
         }, this.executor).join();
      } else {
         IProfiler iprofiler = this.world.getProfiler();
         iprofiler.func_230035_c_("getChunk");
         long i = ChunkPos.asLong(chunkX, chunkZ);

         for(int j = 0; j < 4; ++j) {
            if (i == this.recentPositions[j] && requiredStatus == this.recentStatuses[j]) {
               IChunk ichunk = this.recentChunks[j];
               if (ichunk != null || !load) {
                  return ichunk;
               }
            }
         }

         iprofiler.func_230035_c_("getChunkCacheMiss");
         CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> completablefuture = this.func_217233_c(chunkX, chunkZ, requiredStatus, load);
         this.executor.driveUntil(completablefuture::isDone);
         IChunk ichunk1 = completablefuture.join().map((p_222874_0_) -> {
            return p_222874_0_;
         }, (p_222870_1_) -> {
            if (load) {
               throw (IllegalStateException)Util.pauseDevMode(new IllegalStateException("Chunk not there when requested: " + p_222870_1_));
            } else {
               return null;
            }
         });
         this.func_225315_a(i, ichunk1, requiredStatus);
         return ichunk1;
      }
   }

   /**
    * Gets a chunk without loading it.
    */
   @Nullable
   public Chunk getChunkWithoutLoading(int chunkX, int chunkZ) {
      if (Thread.currentThread() != this.mainThread) {
         return null;
      } else {
         this.world.getProfiler().func_230035_c_("getChunkNow");
         long i = ChunkPos.asLong(chunkX, chunkZ);

         for(int j = 0; j < 4; ++j) {
            if (i == this.recentPositions[j] && this.recentStatuses[j] == ChunkStatus.FULL) {
               IChunk ichunk = this.recentChunks[j];
               return ichunk instanceof Chunk ? (Chunk)ichunk : null;
            }
         }

         ChunkHolder chunkholder = this.func_217213_a(i);
         if (chunkholder == null) {
            return null;
         } else {
            Either<IChunk, ChunkHolder.IChunkLoadingError> either = chunkholder.func_225410_b(ChunkStatus.FULL).getNow((Either<IChunk, ChunkHolder.IChunkLoadingError>)null);
            if (either == null) {
               return null;
            } else {
               IChunk ichunk1 = either.left().orElse((IChunk)null);
               if (ichunk1 != null) {
                  this.func_225315_a(i, ichunk1, ChunkStatus.FULL);
                  if (ichunk1 instanceof Chunk) {
                     return (Chunk)ichunk1;
                  }
               }

               return null;
            }
         }
      }
   }

   private void invalidateCaches() {
      Arrays.fill(this.recentPositions, ChunkPos.SENTINEL);
      Arrays.fill(this.recentStatuses, (Object)null);
      Arrays.fill(this.recentChunks, (Object)null);
   }

   @OnlyIn(Dist.CLIENT)
   public CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> func_217232_b(int chunkX, int chunkZ, ChunkStatus requiredStatus, boolean load) {
      boolean flag = Thread.currentThread() == this.mainThread;
      CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> completablefuture;
      if (flag) {
         completablefuture = this.func_217233_c(chunkX, chunkZ, requiredStatus, load);
         this.executor.driveUntil(completablefuture::isDone);
      } else {
         completablefuture = CompletableFuture.supplyAsync(() -> {
            return this.func_217233_c(chunkX, chunkZ, requiredStatus, load);
         }, this.executor).thenCompose((p_217211_0_) -> {
            return p_217211_0_;
         });
      }

      return completablefuture;
   }

   private CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> func_217233_c(int chunkX, int chunkZ, ChunkStatus requiredStatus, boolean load) {
      ChunkPos chunkpos = new ChunkPos(chunkX, chunkZ);
      long i = chunkpos.asLong();
      int j = 33 + ChunkStatus.getDistance(requiredStatus);
      ChunkHolder chunkholder = this.func_217213_a(i);
      if (load) {
         this.ticketManager.registerWithLevel(TicketType.UNKNOWN, chunkpos, j, chunkpos);
         if (this.func_217224_a(chunkholder, j)) {
            IProfiler iprofiler = this.world.getProfiler();
            iprofiler.startSection("chunkLoad");
            this.func_217235_l();
            chunkholder = this.func_217213_a(i);
            iprofiler.endSection();
            if (this.func_217224_a(chunkholder, j)) {
               throw (IllegalStateException)Util.pauseDevMode(new IllegalStateException("No chunk holder after ticket has been added"));
            }
         }
      }

      return this.func_217224_a(chunkholder, j) ? ChunkHolder.MISSING_CHUNK_FUTURE : chunkholder.func_219276_a(requiredStatus, this.chunkManager);
   }

   private boolean func_217224_a(@Nullable ChunkHolder chunkHolderIn, int p_217224_2_) {
      return chunkHolderIn == null || chunkHolderIn.getChunkLevel() > p_217224_2_;
   }

   /**
    * Checks to see if a chunk exists at x, z
    */
   public boolean chunkExists(int x, int z) {
      ChunkHolder chunkholder = this.func_217213_a((new ChunkPos(x, z)).asLong());
      int i = 33 + ChunkStatus.getDistance(ChunkStatus.FULL);
      return !this.func_217224_a(chunkholder, i);
   }

   public IBlockReader getChunkForLight(int chunkX, int chunkZ) {
      long i = ChunkPos.asLong(chunkX, chunkZ);
      ChunkHolder chunkholder = this.func_217213_a(i);
      if (chunkholder == null) {
         return null;
      } else {
         int j = field_217239_c.size() - 1;

         while(true) {
            ChunkStatus chunkstatus = field_217239_c.get(j);
            Optional<IChunk> optional = chunkholder.func_219301_a(chunkstatus).getNow(ChunkHolder.MISSING_CHUNK).left();
            if (optional.isPresent()) {
               return optional.get();
            }

            if (chunkstatus == ChunkStatus.LIGHT.getParent()) {
               return null;
            }

            --j;
         }
      }
   }

   public World getWorld() {
      return this.world;
   }

   public boolean driveOneTask() {
      return this.executor.driveOne();
   }

   private boolean func_217235_l() {
      boolean flag = this.ticketManager.processUpdates(this.chunkManager);
      boolean flag1 = this.chunkManager.refreshOffThreadCache();
      if (!flag && !flag1) {
         return false;
      } else {
         this.invalidateCaches();
         return true;
      }
   }

   public boolean isChunkLoaded(Entity entityIn) {
      long i = ChunkPos.asLong(MathHelper.floor(entityIn.getPosX()) >> 4, MathHelper.floor(entityIn.getPosZ()) >> 4);
      return this.isChunkLoaded(i, ChunkHolder::getEntityTickingFuture);
   }

   public boolean isChunkLoaded(ChunkPos pos) {
      return this.isChunkLoaded(pos.asLong(), ChunkHolder::getEntityTickingFuture);
   }

   public boolean canTick(BlockPos pos) {
      long i = ChunkPos.asLong(pos.getX() >> 4, pos.getZ() >> 4);
      return this.isChunkLoaded(i, ChunkHolder::getTickingFuture);
   }

   private boolean isChunkLoaded(long pos, Function<ChunkHolder, CompletableFuture<Either<Chunk, ChunkHolder.IChunkLoadingError>>> p_222872_3_) {
      ChunkHolder chunkholder = this.func_217213_a(pos);
      if (chunkholder == null) {
         return false;
      } else {
         Either<Chunk, ChunkHolder.IChunkLoadingError> either = p_222872_3_.apply(chunkholder).getNow(ChunkHolder.UNLOADED_CHUNK);
         return either.left().isPresent();
      }
   }

   public void save(boolean flush) {
      this.func_217235_l();
      this.chunkManager.save(flush);
   }

   public void close() throws IOException {
      this.save(true);
      this.lightManager.close();
      this.chunkManager.close();
   }

   public void tick(BooleanSupplier hasTimeLeft) {
      this.world.getProfiler().startSection("purge");
      this.ticketManager.tick();
      this.func_217235_l();
      this.world.getProfiler().endStartSection("chunks");
      this.tickChunks();
      this.world.getProfiler().endStartSection("unload");
      this.chunkManager.tick(hasTimeLeft);
      this.world.getProfiler().endSection();
      this.invalidateCaches();
   }

   private void tickChunks() {
      long i = this.world.getGameTime();
      long j = i - this.lastGameTime;
      this.lastGameTime = i;
      IWorldInfo iworldinfo = this.world.getWorldInfo();
      boolean flag = this.world.func_234925_Z_();
      boolean flag1 = this.world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING);
      if (!flag) {
         this.world.getProfiler().startSection("pollingChunks");
         int k = this.world.getGameRules().getInt(GameRules.RANDOM_TICK_SPEED);
         boolean flag2 = iworldinfo.getGameTime() % 400L == 0L;
         this.world.getProfiler().startSection("naturalSpawnCount");
         int l = this.ticketManager.getSpawningChunksCount();
         WorldEntitySpawner.EntityDensityManager worldentityspawner$entitydensitymanager = WorldEntitySpawner.func_234964_a_(l, this.world.func_241136_z_(), this::func_241098_a_);
         this.field_241097_p_ = worldentityspawner$entitydensitymanager;
         this.world.getProfiler().endSection();
         List<ChunkHolder> list = Lists.newArrayList(this.chunkManager.getLoadedChunksIterable());
         Collections.shuffle(list);
         list.forEach((p_241099_7_) -> {
            Optional<Chunk> optional = p_241099_7_.getTickingFuture().getNow(ChunkHolder.UNLOADED_CHUNK).left();
            if (optional.isPresent()) {
               this.world.getProfiler().startSection("broadcast");
               p_241099_7_.sendChanges(optional.get());
               this.world.getProfiler().endSection();
               Optional<Chunk> optional1 = p_241099_7_.getEntityTickingFuture().getNow(ChunkHolder.UNLOADED_CHUNK).left();
               if (optional1.isPresent()) {
                  Chunk chunk = optional1.get();
                  ChunkPos chunkpos = p_241099_7_.getPosition();
                  if (!this.chunkManager.isOutsideSpawningRadius(chunkpos)) {
                     chunk.setInhabitedTime(chunk.getInhabitedTime() + j);
                     if (flag1 && (this.spawnHostiles || this.spawnPassives) && this.world.getWorldBorder().contains(chunk.getPos())) {
                        WorldEntitySpawner.func_234979_a_(this.world, chunk, worldentityspawner$entitydensitymanager, this.spawnPassives, this.spawnHostiles, flag2);
                     }

                     this.world.tickEnvironment(chunk, k);
                  }
               }
            }
         });
         this.world.getProfiler().startSection("customSpawners");
         if (flag1) {
            this.world.func_241123_a_(this.spawnHostiles, this.spawnPassives);
         }

         this.world.getProfiler().endSection();
         this.world.getProfiler().endSection();
      }

      this.chunkManager.tickEntityTracker();
   }

   private void func_241098_a_(long p_241098_1_, Consumer<Chunk> p_241098_3_) {
      ChunkHolder chunkholder = this.func_217213_a(p_241098_1_);
      if (chunkholder != null) {
         chunkholder.getBorderFuture().getNow(ChunkHolder.UNLOADED_CHUNK).left().ifPresent(p_241098_3_);
      }

   }

   /**
    * Converts the instance data to a readable string.
    */
   public String makeString() {
      return "ServerChunkCache: " + this.getLoadedChunkCount();
   }

   @VisibleForTesting
   public int func_225314_f() {
      return this.executor.getQueueSize();
   }

   public ChunkGenerator getChunkGenerator() {
      return this.generator;
   }

   public int getLoadedChunkCount() {
      return this.chunkManager.getLoadedChunkCount();
   }

   public void markBlockChanged(BlockPos pos) {
      int i = pos.getX() >> 4;
      int j = pos.getZ() >> 4;
      ChunkHolder chunkholder = this.func_217213_a(ChunkPos.asLong(i, j));
      if (chunkholder != null) {
         chunkholder.func_244386_a(pos);
      }

   }

   public void markLightChanged(LightType type, SectionPos pos) {
      this.executor.execute(() -> {
         ChunkHolder chunkholder = this.func_217213_a(pos.asChunkPos().asLong());
         if (chunkholder != null) {
            chunkholder.markLightChanged(type, pos.getSectionY());
         }

      });
   }

   public <T> void registerTicket(TicketType<T> type, ChunkPos pos, int distance, T value) {
      this.ticketManager.register(type, pos, distance, value);
   }

   public <T> void releaseTicket(TicketType<T> type, ChunkPos pos, int distance, T value) {
      this.ticketManager.release(type, pos, distance, value);
   }

   public void forceChunk(ChunkPos pos, boolean add) {
      this.ticketManager.forceChunk(pos, add);
   }

   public void updatePlayerPosition(ServerPlayerEntity player) {
      this.chunkManager.updatePlayerPosition(player);
   }

   public void untrack(Entity entityIn) {
      this.chunkManager.untrack(entityIn);
   }

   public void track(Entity entityIn) {
      this.chunkManager.track(entityIn);
   }

   public void sendToTrackingAndSelf(Entity entityIn, IPacket<?> packet) {
      this.chunkManager.sendToTrackingAndSelf(entityIn, packet);
   }

   public void sendToAllTracking(Entity entityIn, IPacket<?> packet) {
      this.chunkManager.sendToAllTracking(entityIn, packet);
   }

   public void setViewDistance(int viewDistance) {
      this.chunkManager.setViewDistance(viewDistance);
   }

   public void setAllowedSpawnTypes(boolean hostile, boolean peaceful) {
      this.spawnHostiles = hostile;
      this.spawnPassives = peaceful;
   }

   @OnlyIn(Dist.CLIENT)
   public String func_217208_a(ChunkPos chunkPosIn) {
      return this.chunkManager.func_219170_a(chunkPosIn);
   }

   public DimensionSavedDataManager getSavedData() {
      return this.savedData;
   }

   public PointOfInterestManager getPointOfInterestManager() {
      return this.chunkManager.getPointOfInterestManager();
   }

   @Nullable
   public WorldEntitySpawner.EntityDensityManager func_241101_k_() {
      return this.field_241097_p_;
   }

   final class ChunkExecutor extends ThreadTaskExecutor<Runnable> {
      private ChunkExecutor(World worldIn) {
         super("Chunk source main thread executor for " + worldIn.func_234923_W_().func_240901_a_());
      }

      protected Runnable wrapTask(Runnable runnable) {
         return runnable;
      }

      protected boolean canRun(Runnable runnable) {
         return true;
      }

      protected boolean shouldDeferTasks() {
         return true;
      }

      protected Thread getExecutionThread() {
         return ServerChunkProvider.this.mainThread;
      }

      protected void run(Runnable taskIn) {
         ServerChunkProvider.this.world.getProfiler().func_230035_c_("runTask");
         super.run(taskIn);
      }

      protected boolean driveOne() {
         if (ServerChunkProvider.this.func_217235_l()) {
            return true;
         } else {
            ServerChunkProvider.this.lightManager.func_215588_z_();
            return super.driveOne();
         }
      }
   }
}