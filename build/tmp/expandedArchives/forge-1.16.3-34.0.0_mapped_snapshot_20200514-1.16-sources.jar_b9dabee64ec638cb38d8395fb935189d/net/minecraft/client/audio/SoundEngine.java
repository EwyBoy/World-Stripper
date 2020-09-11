package net.minecraft.client.audio;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.client.GameSettings;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@OnlyIn(Dist.CLIENT)
public class SoundEngine {
   /** The marker used for logging */
   private static final Marker LOG_MARKER = MarkerManager.getMarker("SOUNDS");
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Set<ResourceLocation> UNABLE_TO_PLAY = Sets.newHashSet();
   /** A reference to the sound handler. */
   public final SoundHandler sndHandler;
   /** Reference to the GameSettings object. */
   private final GameSettings options;
   /** Set to true when the SoundManager has been initialised. */
   private boolean loaded;
   private final SoundSystem sndSystem = new SoundSystem();
   private final Listener listener = this.sndSystem.getListener();
   private final AudioStreamManager audioStreamManager;
   private final SoundEngineExecutor executor = new SoundEngineExecutor();
   private final ChannelManager channelManager = new ChannelManager(this.sndSystem, this.executor);
   /** A counter for how long the sound manager has been running */
   private int ticks;
   private final Map<ISound, ChannelManager.Entry> playingSoundsChannel = Maps.newHashMap();
   private final Multimap<SoundCategory, ISound> field_217943_n = HashMultimap.create();
   /** A subset of playingSounds, this contains only ITickableSounds */
   private final List<ITickableSound> tickableSounds = Lists.newArrayList();
   /** Contains sounds to play in n ticks. Type: HashMap<ISound, Integer> */
   private final Map<ISound, Integer> delayedSounds = Maps.newHashMap();
   /** The future time in which to stop this sound. Type: HashMap<String, Integer> */
   private final Map<ISound, Integer> playingSoundsStopTime = Maps.newHashMap();
   private final List<ISoundEventListener> listeners = Lists.newArrayList();
   private final List<ITickableSound> field_229361_s_ = Lists.newArrayList();
   private final List<Sound> soundsToPreload = Lists.newArrayList();

   public SoundEngine(SoundHandler sndHandlerIn, GameSettings optionsIn, IResourceManager resourceManagerIn) {
      this.sndHandler = sndHandlerIn;
      this.options = optionsIn;
      this.audioStreamManager = new AudioStreamManager(resourceManagerIn);
      net.minecraftforge.fml.ModLoader.get().postEvent(new net.minecraftforge.client.event.sound.SoundLoadEvent(this));
   }

   public void reload() {
      UNABLE_TO_PLAY.clear();

      for(SoundEvent soundevent : Registry.SOUND_EVENT) {
         ResourceLocation resourcelocation = soundevent.getName();
         if (this.sndHandler.getAccessor(resourcelocation) == null) {
            LOGGER.warn("Missing sound for event: {}", (Object)Registry.SOUND_EVENT.getKey(soundevent));
            UNABLE_TO_PLAY.add(resourcelocation);
         }
      }

      this.unload();
      this.load();
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.sound.SoundLoadEvent(this));
   }

   /**
    * Tries to add the paulscode library and the relevant codecs. If it fails, the master volume  will be set to zero.
    */
   private synchronized void load() {
      if (!this.loaded) {
         try {
            this.sndSystem.func_216404_a();
            this.listener.init();
            this.listener.setGain(this.options.getSoundLevel(SoundCategory.MASTER));
            this.audioStreamManager.func_217908_a(this.soundsToPreload).thenRun(this.soundsToPreload::clear);
            this.loaded = true;
            LOGGER.info(LOG_MARKER, "Sound engine started");
         } catch (RuntimeException runtimeexception) {
            LOGGER.error(LOG_MARKER, "Error starting SoundSystem. Turning off sounds & music", (Throwable)runtimeexception);
         }

      }
   }

   private float getVolume(@Nullable SoundCategory category) {
      return category != null && category != SoundCategory.MASTER ? this.options.getSoundLevel(category) : 1.0F;
   }

   public void setVolume(SoundCategory category, float volume) {
      if (this.loaded) {
         if (category == SoundCategory.MASTER) {
            this.listener.setGain(volume);
         } else {
            this.playingSoundsChannel.forEach((p_217926_1_, p_217926_2_) -> {
               float f = this.getClampedVolume(p_217926_1_);
               p_217926_2_.runOnSoundExecutor((p_217923_1_) -> {
                  if (f <= 0.0F) {
                     p_217923_1_.stop();
                  } else {
                     p_217923_1_.setGain(f);
                  }

               });
            });
         }
      }
   }

   /**
    * Cleans up the Sound System
    */
   public void unload() {
      if (this.loaded) {
         this.stopAllSounds();
         this.audioStreamManager.func_217912_a();
         this.sndSystem.func_216409_b();
         this.loaded = false;
      }

   }

   public void stop(ISound sound) {
      if (this.loaded) {
         ChannelManager.Entry channelmanager$entry = this.playingSoundsChannel.get(sound);
         if (channelmanager$entry != null) {
            channelmanager$entry.runOnSoundExecutor(SoundSource::stop);
         }
      }

   }

   /**
    * Stops all currently playing sounds
    */
   public void stopAllSounds() {
      if (this.loaded) {
         this.executor.restart();
         this.playingSoundsChannel.values().forEach((p_217922_0_) -> {
            p_217922_0_.runOnSoundExecutor(SoundSource::stop);
         });
         this.playingSoundsChannel.clear();
         this.channelManager.releaseAll();
         this.delayedSounds.clear();
         this.tickableSounds.clear();
         this.field_217943_n.clear();
         this.playingSoundsStopTime.clear();
         this.field_229361_s_.clear();
      }

   }

   public void addListener(ISoundEventListener listener) {
      this.listeners.add(listener);
   }

   public void removeListener(ISoundEventListener listener) {
      this.listeners.remove(listener);
   }

   public void tick(boolean isGamePaused) {
      if (!isGamePaused) {
         this.tickNonPaused();
      }

      this.channelManager.tick();
   }

   private void tickNonPaused() {
      ++this.ticks;
      this.field_229361_s_.stream().filter(ISound::func_230510_t_).forEach(this::play);
      this.field_229361_s_.clear();

      for(ITickableSound itickablesound : this.tickableSounds) {
         if (!itickablesound.func_230510_t_()) {
            this.stop(itickablesound);
         }

         itickablesound.tick();
         if (itickablesound.isDonePlaying()) {
            this.stop(itickablesound);
         } else {
            float f = this.getClampedVolume(itickablesound);
            float f1 = this.getClampedPitch(itickablesound);
            Vector3d vector3d = new Vector3d(itickablesound.getX(), itickablesound.getY(), itickablesound.getZ());
            ChannelManager.Entry channelmanager$entry = this.playingSoundsChannel.get(itickablesound);
            if (channelmanager$entry != null) {
               channelmanager$entry.runOnSoundExecutor((p_217924_3_) -> {
                  p_217924_3_.setGain(f);
                  p_217924_3_.setPitch(f1);
                  p_217924_3_.updateSource(vector3d);
               });
            }
         }
      }

      Iterator<Entry<ISound, ChannelManager.Entry>> iterator = this.playingSoundsChannel.entrySet().iterator();

      while(iterator.hasNext()) {
         Entry<ISound, ChannelManager.Entry> entry = iterator.next();
         ChannelManager.Entry channelmanager$entry1 = entry.getValue();
         ISound isound = entry.getKey();
         float f2 = this.options.getSoundLevel(isound.getCategory());
         if (f2 <= 0.0F) {
            channelmanager$entry1.runOnSoundExecutor(SoundSource::stop);
            iterator.remove();
         } else if (channelmanager$entry1.isReleased()) {
            int i = this.playingSoundsStopTime.get(isound);
            if (i <= this.ticks) {
               if (func_239545_e_(isound)) {
                  this.delayedSounds.put(isound, this.ticks + isound.getRepeatDelay());
               }

               iterator.remove();
               LOGGER.debug(LOG_MARKER, "Removed channel {} because it's not playing anymore", (Object)channelmanager$entry1);
               this.playingSoundsStopTime.remove(isound);

               try {
                  this.field_217943_n.remove(isound.getCategory(), isound);
               } catch (RuntimeException runtimeexception) {
               }

               if (isound instanceof ITickableSound) {
                  this.tickableSounds.remove(isound);
               }
            }
         }
      }

      Iterator<Entry<ISound, Integer>> iterator1 = this.delayedSounds.entrySet().iterator();

      while(iterator1.hasNext()) {
         Entry<ISound, Integer> entry1 = iterator1.next();
         if (this.ticks >= entry1.getValue()) {
            ISound isound1 = entry1.getKey();
            if (isound1 instanceof ITickableSound) {
               ((ITickableSound)isound1).tick();
            }

            this.play(isound1);
            iterator1.remove();
         }
      }

   }

   private static boolean func_239544_d_(ISound p_239544_0_) {
      return p_239544_0_.getRepeatDelay() > 0;
   }

   private static boolean func_239545_e_(ISound p_239545_0_) {
      return p_239545_0_.canRepeat() && func_239544_d_(p_239545_0_);
   }

   private static boolean func_239546_f_(ISound p_239546_0_) {
      return p_239546_0_.canRepeat() && !func_239544_d_(p_239546_0_);
   }

   public boolean isPlaying(ISound soundIn) {
      if (!this.loaded) {
         return false;
      } else {
         return this.playingSoundsStopTime.containsKey(soundIn) && this.playingSoundsStopTime.get(soundIn) <= this.ticks ? true : this.playingSoundsChannel.containsKey(soundIn);
      }
   }

   public void play(ISound p_sound) {
      if (this.loaded) {
         p_sound = net.minecraftforge.client.ForgeHooksClient.playSound(this, p_sound);
         if (p_sound != null && p_sound.func_230510_t_()) {
            SoundEventAccessor soundeventaccessor = p_sound.createAccessor(this.sndHandler);
            ResourceLocation resourcelocation = p_sound.getSoundLocation();
            if (soundeventaccessor == null) {
               if (UNABLE_TO_PLAY.add(resourcelocation)) {
                  LOGGER.warn(LOG_MARKER, "Unable to play unknown soundEvent: {}", (Object)resourcelocation);
               }

            } else {
               Sound sound = p_sound.getSound();
               if (sound == SoundHandler.MISSING_SOUND) {
                  if (UNABLE_TO_PLAY.add(resourcelocation)) {
                     LOGGER.warn(LOG_MARKER, "Unable to play empty soundEvent: {}", (Object)resourcelocation);
                  }

               } else {
                  float f = p_sound.getVolume();
                  float f1 = Math.max(f, 1.0F) * (float)sound.getAttenuationDistance();
                  SoundCategory soundcategory = p_sound.getCategory();
                  float f2 = this.getClampedVolume(p_sound);
                  float f3 = this.getClampedPitch(p_sound);
                  ISound.AttenuationType isound$attenuationtype = p_sound.getAttenuationType();
                  boolean flag = p_sound.isGlobal();
                  if (f2 == 0.0F && !p_sound.canBeSilent()) {
                     LOGGER.debug(LOG_MARKER, "Skipped playing sound {}, volume was zero.", (Object)sound.getSoundLocation());
                  } else {
                     Vector3d vector3d = new Vector3d(p_sound.getX(), p_sound.getY(), p_sound.getZ());
                     if (!this.listeners.isEmpty()) {
                        boolean flag1 = flag || isound$attenuationtype == ISound.AttenuationType.NONE || this.listener.func_237504_a_().squareDistanceTo(vector3d) < (double)(f1 * f1);
                        if (flag1) {
                           for(ISoundEventListener isoundeventlistener : this.listeners) {
                              isoundeventlistener.onPlaySound(p_sound, soundeventaccessor);
                           }
                        } else {
                           LOGGER.debug(LOG_MARKER, "Did not notify listeners of soundEvent: {}, it is too far away to hear", (Object)resourcelocation);
                        }
                     }

                     if (this.listener.getGain() <= 0.0F) {
                        LOGGER.debug(LOG_MARKER, "Skipped playing soundEvent: {}, master volume was zero", (Object)resourcelocation);
                     } else {
                        boolean flag2 = func_239546_f_(p_sound);
                        boolean flag3 = sound.isStreaming();
                        CompletableFuture<ChannelManager.Entry> completablefuture = this.channelManager.func_239534_a_(sound.isStreaming() ? SoundSystem.Mode.STREAMING : SoundSystem.Mode.STATIC);
                        ChannelManager.Entry channelmanager$entry = completablefuture.join();
                        if (channelmanager$entry == null) {
                           LOGGER.warn("Failed to create new sound handle");
                        } else {
                           LOGGER.debug(LOG_MARKER, "Playing sound {} for event {}", sound.getSoundLocation(), resourcelocation);
                           this.playingSoundsStopTime.put(p_sound, this.ticks + 20);
                           this.playingSoundsChannel.put(p_sound, channelmanager$entry);
                           this.field_217943_n.put(soundcategory, p_sound);
                           channelmanager$entry.runOnSoundExecutor((p_239543_8_) -> {
                              p_239543_8_.setPitch(f3);
                              p_239543_8_.setGain(f2);
                              if (isound$attenuationtype == ISound.AttenuationType.LINEAR) {
                                 p_239543_8_.func_216423_c(f1);
                              } else {
                                 p_239543_8_.func_216419_h();
                              }

                              p_239543_8_.setLooping(flag2 && !flag3);
                              p_239543_8_.updateSource(vector3d);
                              p_239543_8_.func_216432_b(flag);
                           });
                           final ISound isound = p_sound;
                           if (!flag3) {
                              this.audioStreamManager.func_217909_a(sound.getSoundAsOggLocation()).thenAccept((p_217934_1_) -> {
                                 channelmanager$entry.runOnSoundExecutor((p_217925_1_) -> {
                                    p_217925_1_.func_216429_a(p_217934_1_);
                                    p_217925_1_.play();
                                    net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.sound.PlaySoundSourceEvent(this, isound, p_217925_1_));
                                 });
                              });
                           } else {
                              this.audioStreamManager.func_217917_b(sound.getSoundAsOggLocation(), flag2).thenAccept((p_217928_1_) -> {
                                 channelmanager$entry.runOnSoundExecutor((p_217935_1_) -> {
                                    p_217935_1_.func_216433_a(p_217928_1_);
                                    p_217935_1_.play();
                                    net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.sound.PlayStreamingSourceEvent(this, isound, p_217935_1_));
                                 });
                              });
                           }

                           if (p_sound instanceof ITickableSound) {
                              this.tickableSounds.add((ITickableSound)p_sound);
                           }

                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void func_229363_a_(ITickableSound p_229363_1_) {
      this.field_229361_s_.add(p_229363_1_);
   }

   public void enqueuePreload(Sound soundIn) {
      this.soundsToPreload.add(soundIn);
   }

   private float getClampedPitch(ISound soundIn) {
      return MathHelper.clamp(soundIn.getPitch(), 0.5F, 2.0F);
   }

   private float getClampedVolume(ISound soundIn) {
      return MathHelper.clamp(soundIn.getVolume() * this.getVolume(soundIn.getCategory()), 0.0F, 1.0F);
   }

   /**
    * Pauses all currently playing sounds
    */
   public void pause() {
      if (this.loaded) {
         this.channelManager.func_217897_a((p_217929_0_) -> {
            p_217929_0_.forEach(SoundSource::pause);
         });
      }

   }

   /**
    * Resumes playing all currently playing sounds (after pauseAllSounds)
    */
   public void resume() {
      if (this.loaded) {
         this.channelManager.func_217897_a((p_217936_0_) -> {
            p_217936_0_.forEach(SoundSource::resume);
         });
      }

   }

   /**
    * Adds a sound to play in n tick
    */
   public void playDelayed(ISound sound, int delay) {
      this.delayedSounds.put(sound, this.ticks + delay);
   }

   public void updateListener(ActiveRenderInfo p_217920_1_) {
      if (this.loaded && p_217920_1_.isValid()) {
         Vector3d vector3d = p_217920_1_.getProjectedView();
         Vector3f vector3f = p_217920_1_.getViewVector();
         Vector3f vector3f1 = p_217920_1_.getUpVector();
         this.executor.execute(() -> {
            this.listener.setPosition(vector3d);
            this.listener.func_227580_a_(vector3f, vector3f1);
         });
      }
   }

   public void stop(@Nullable ResourceLocation soundName, @Nullable SoundCategory category) {
      if (category != null) {
         for(ISound isound : this.field_217943_n.get(category)) {
            if (soundName == null || isound.getSoundLocation().equals(soundName)) {
               this.stop(isound);
            }
         }
      } else if (soundName == null) {
         this.stopAllSounds();
      } else {
         for(ISound isound1 : this.playingSoundsChannel.keySet()) {
            if (isound1.getSoundLocation().equals(soundName)) {
               this.stop(isound1);
            }
         }
      }

   }

   public String getDebugString() {
      return this.sndSystem.getDebugString();
   }
}