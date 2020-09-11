package net.minecraft.village;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PointOfInterestData {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Short2ObjectMap<PointOfInterest> records = new Short2ObjectOpenHashMap<>();
   private final Map<PointOfInterestType, Set<PointOfInterest>> byType = Maps.newHashMap();
   private final Runnable onChange;
   private boolean valid;

   public static Codec<PointOfInterestData> func_234158_a_(Runnable p_234158_0_) {
      return RecordCodecBuilder.<PointOfInterestData>create((p_234159_1_) -> {
         return p_234159_1_.group(RecordCodecBuilder.point(p_234158_0_), Codec.BOOL.optionalFieldOf("Valid", Boolean.valueOf(false)).forGetter((p_234162_0_) -> {
            return p_234162_0_.valid;
         }), PointOfInterest.func_234150_a_(p_234158_0_).listOf().fieldOf("Records").forGetter((p_234154_0_) -> {
            return ImmutableList.copyOf(p_234154_0_.records.values());
         })).apply(p_234159_1_, PointOfInterestData::new);
      }).orElseGet(Util.func_240982_a_("Failed to read POI section: ", LOGGER::error), () -> {
         return new PointOfInterestData(p_234158_0_, false, ImmutableList.of());
      });
   }

   public PointOfInterestData(Runnable onChangeIn) {
      this(onChangeIn, true, ImmutableList.of());
   }

   private PointOfInterestData(Runnable p_i231555_1_, boolean p_i231555_2_, List<PointOfInterest> p_i231555_3_) {
      this.onChange = p_i231555_1_;
      this.valid = p_i231555_2_;
      p_i231555_3_.forEach(this::add);
   }

   public Stream<PointOfInterest> getRecords(Predicate<PointOfInterestType> p_218247_1_, PointOfInterestManager.Status p_218247_2_) {
      return this.byType.entrySet().stream().filter((p_234161_1_) -> {
         return p_218247_1_.test(p_234161_1_.getKey());
      }).flatMap((p_234160_0_) -> {
         return p_234160_0_.getValue().stream();
      }).filter(p_218247_2_.getTest());
   }

   public void add(BlockPos p_218243_1_, PointOfInterestType p_218243_2_) {
      if (this.add(new PointOfInterest(p_218243_1_, p_218243_2_, this.onChange))) {
         LOGGER.debug("Added POI of type {} @ {}", () -> {
            return p_218243_2_;
         }, () -> {
            return p_218243_1_;
         });
         this.onChange.run();
      }

   }

   private boolean add(PointOfInterest p_218254_1_) {
      BlockPos blockpos = p_218254_1_.getPos();
      PointOfInterestType pointofinteresttype = p_218254_1_.getType();
      short short1 = SectionPos.toRelativeOffset(blockpos);
      PointOfInterest pointofinterest = this.records.get(short1);
      if (pointofinterest != null) {
         if (pointofinteresttype.equals(pointofinterest.getType())) {
            return false;
         } else {
            throw (IllegalStateException)Util.pauseDevMode(new IllegalStateException("POI data mismatch: already registered at " + blockpos));
         }
      } else {
         this.records.put(short1, p_218254_1_);
         this.byType.computeIfAbsent(pointofinteresttype, (p_234155_0_) -> {
            return Sets.newHashSet();
         }).add(p_218254_1_);
         return true;
      }
   }

   public void remove(BlockPos p_218248_1_) {
      PointOfInterest pointofinterest = this.records.remove(SectionPos.toRelativeOffset(p_218248_1_));
      if (pointofinterest == null) {
         LOGGER.error("POI data mismatch: never registered at " + p_218248_1_);
      } else {
         this.byType.get(pointofinterest.getType()).remove(pointofinterest);
         LOGGER.debug("Removed POI of type {} @ {}", pointofinterest::getType, pointofinterest::getPos);
         this.onChange.run();
      }
   }

   public boolean release(BlockPos p_218251_1_) {
      PointOfInterest pointofinterest = this.records.get(SectionPos.toRelativeOffset(p_218251_1_));
      if (pointofinterest == null) {
         throw (IllegalStateException)Util.pauseDevMode(new IllegalStateException("POI never registered at " + p_218251_1_));
      } else {
         boolean flag = pointofinterest.release();
         this.onChange.run();
         return flag;
      }
   }

   public boolean exists(BlockPos p_218245_1_, Predicate<PointOfInterestType> p_218245_2_) {
      short short1 = SectionPos.toRelativeOffset(p_218245_1_);
      PointOfInterest pointofinterest = this.records.get(short1);
      return pointofinterest != null && p_218245_2_.test(pointofinterest.getType());
   }

   public Optional<PointOfInterestType> getType(BlockPos p_218244_1_) {
      short short1 = SectionPos.toRelativeOffset(p_218244_1_);
      PointOfInterest pointofinterest = this.records.get(short1);
      return pointofinterest != null ? Optional.of(pointofinterest.getType()) : Optional.empty();
   }

   public void refresh(Consumer<BiConsumer<BlockPos, PointOfInterestType>> p_218240_1_) {
      if (!this.valid) {
         Short2ObjectMap<PointOfInterest> short2objectmap = new Short2ObjectOpenHashMap<>(this.records);
         this.clear();
         p_218240_1_.accept((p_234157_2_, p_234157_3_) -> {
            short short1 = SectionPos.toRelativeOffset(p_234157_2_);
            PointOfInterest pointofinterest = short2objectmap.computeIfAbsent(short1, (p_234156_3_) -> {
               return new PointOfInterest(p_234157_2_, p_234157_3_, this.onChange);
            });
            this.add(pointofinterest);
         });
         this.valid = true;
         this.onChange.run();
      }

   }

   private void clear() {
      this.records.clear();
      this.byType.clear();
   }

   boolean isValid() {
      return this.valid;
   }
}