package net.minecraft.client.audio;

import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChannelManager {
   private final Set<ChannelManager.Entry> channels = Sets.newIdentityHashSet();
   private final SoundSystem sndSystem;
   private final Executor soundExecutor;

   public ChannelManager(SoundSystem p_i50894_1_, Executor p_i50894_2_) {
      this.sndSystem = p_i50894_1_;
      this.soundExecutor = p_i50894_2_;
   }

   public CompletableFuture<ChannelManager.Entry> func_239534_a_(SoundSystem.Mode p_239534_1_) {
      CompletableFuture<ChannelManager.Entry> completablefuture = new CompletableFuture<>();
      this.soundExecutor.execute(() -> {
         SoundSource soundsource = this.sndSystem.getSource(p_239534_1_);
         if (soundsource != null) {
            ChannelManager.Entry channelmanager$entry = new ChannelManager.Entry(soundsource);
            this.channels.add(channelmanager$entry);
            completablefuture.complete(channelmanager$entry);
         } else {
            completablefuture.complete((ChannelManager.Entry)null);
         }

      });
      return completablefuture;
   }

   public void func_217897_a(Consumer<Stream<SoundSource>> p_217897_1_) {
      this.soundExecutor.execute(() -> {
         p_217897_1_.accept(this.channels.stream().map((p_217896_0_) -> {
            return p_217896_0_.source;
         }).filter(Objects::nonNull));
      });
   }

   public void tick() {
      this.soundExecutor.execute(() -> {
         Iterator<ChannelManager.Entry> iterator = this.channels.iterator();

         while(iterator.hasNext()) {
            ChannelManager.Entry channelmanager$entry = iterator.next();
            channelmanager$entry.source.func_216434_i();
            if (channelmanager$entry.source.isStopped()) {
               channelmanager$entry.release();
               iterator.remove();
            }
         }

      });
   }

   public void releaseAll() {
      this.channels.forEach(ChannelManager.Entry::release);
      this.channels.clear();
   }

   @OnlyIn(Dist.CLIENT)
   public class Entry {
      @Nullable
      private SoundSource source;
      private boolean released;

      public boolean isReleased() {
         return this.released;
      }

      public Entry(SoundSource p_i232495_2_) {
         this.source = p_i232495_2_;
      }

      public void runOnSoundExecutor(Consumer<SoundSource> p_217888_1_) {
         ChannelManager.this.soundExecutor.execute(() -> {
            if (this.source != null) {
               p_217888_1_.accept(this.source);
            }

         });
      }

      public void release() {
         this.released = true;
         ChannelManager.this.sndSystem.release(this.source);
         this.source = null;
      }
   }
}