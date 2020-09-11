package net.minecraft.entity.ai.brain.memory;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.Memory;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.UUIDCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.IPosWrapper;
import net.minecraft.util.registry.Registry;

public class MemoryModuleType<U> extends net.minecraftforge.registries.ForgeRegistryEntry<MemoryModuleType<?>> {
   public static final MemoryModuleType<Void> DUMMY = register("dummy");
   public static final MemoryModuleType<GlobalPos> HOME = func_234108_a_("home", GlobalPos.field_239645_a_);
   public static final MemoryModuleType<GlobalPos> JOB_SITE = func_234108_a_("job_site", GlobalPos.field_239645_a_);
   public static final MemoryModuleType<GlobalPos> field_234101_d_ = func_234108_a_("potential_job_site", GlobalPos.field_239645_a_);
   public static final MemoryModuleType<GlobalPos> MEETING_POINT = func_234108_a_("meeting_point", GlobalPos.field_239645_a_);
   public static final MemoryModuleType<List<GlobalPos>> SECONDARY_JOB_SITE = register("secondary_job_site");
   public static final MemoryModuleType<List<LivingEntity>> MOBS = register("mobs");
   public static final MemoryModuleType<List<LivingEntity>> VISIBLE_MOBS = register("visible_mobs");
   public static final MemoryModuleType<List<LivingEntity>> VISIBLE_VILLAGER_BABIES = register("visible_villager_babies");
   public static final MemoryModuleType<List<PlayerEntity>> NEAREST_PLAYERS = register("nearest_players");
   public static final MemoryModuleType<PlayerEntity> NEAREST_VISIBLE_PLAYER = register("nearest_visible_player");
   public static final MemoryModuleType<PlayerEntity> field_234102_l_ = register("nearest_visible_targetable_player");
   public static final MemoryModuleType<WalkTarget> WALK_TARGET = register("walk_target");
   public static final MemoryModuleType<IPosWrapper> LOOK_TARGET = register("look_target");
   public static final MemoryModuleType<LivingEntity> field_234103_o_ = register("attack_target");
   public static final MemoryModuleType<Boolean> field_234104_p_ = register("attack_cooling_down");
   public static final MemoryModuleType<LivingEntity> INTERACTION_TARGET = register("interaction_target");
   public static final MemoryModuleType<AgeableEntity> BREED_TARGET = register("breed_target");
   public static final MemoryModuleType<Entity> field_234105_s_ = register("ride_target");
   public static final MemoryModuleType<Path> PATH = register("path");
   public static final MemoryModuleType<List<GlobalPos>> INTERACTABLE_DOORS = register("interactable_doors");
   public static final MemoryModuleType<Set<GlobalPos>> field_225462_q = register("doors_to_close");
   public static final MemoryModuleType<BlockPos> NEAREST_BED = register("nearest_bed");
   public static final MemoryModuleType<DamageSource> HURT_BY = register("hurt_by");
   public static final MemoryModuleType<LivingEntity> HURT_BY_ENTITY = register("hurt_by_entity");
   public static final MemoryModuleType<LivingEntity> field_234106_z_ = register("avoid_target");
   public static final MemoryModuleType<LivingEntity> NEAREST_HOSTILE = register("nearest_hostile");
   public static final MemoryModuleType<GlobalPos> HIDING_PLACE = register("hiding_place");
   public static final MemoryModuleType<Long> HEARD_BELL_TIME = register("heard_bell_time");
   public static final MemoryModuleType<Long> CANT_REACH_WALK_TARGET_SINCE = register("cant_reach_walk_target_since");
   public static final MemoryModuleType<Boolean> field_242309_E = func_234108_a_("golem_detected_recently", Codec.BOOL);
   public static final MemoryModuleType<Long> LAST_SLEPT = func_234108_a_("last_slept", Codec.LONG);
   public static final MemoryModuleType<Long> field_226332_A_ = func_234108_a_("last_woken", Codec.LONG);
   public static final MemoryModuleType<Long> LAST_WORKED_AT_POI = func_234108_a_("last_worked_at_poi", Codec.LONG);
   public static final MemoryModuleType<AgeableEntity> field_234075_I_ = register("nearest_visible_adult");
   public static final MemoryModuleType<ItemEntity> field_234076_J_ = register("nearest_visible_wanted_item");
   public static final MemoryModuleType<MobEntity> field_234077_K_ = register("nearest_visible_nemesis");
   public static final MemoryModuleType<UUID> field_234078_L_ = func_234108_a_("angry_at", UUIDCodec.field_239775_a_);
   public static final MemoryModuleType<Boolean> field_234079_M_ = func_234108_a_("universal_anger", Codec.BOOL);
   public static final MemoryModuleType<Boolean> field_234080_N_ = func_234108_a_("admiring_item", Codec.BOOL);
   public static final MemoryModuleType<Integer> field_242310_O = register("time_trying_to_reach_admire_item");
   public static final MemoryModuleType<Boolean> field_242311_P = register("disable_walk_to_admire_item");
   public static final MemoryModuleType<Boolean> field_234081_O_ = func_234108_a_("admiring_disabled", Codec.BOOL);
   public static final MemoryModuleType<Boolean> field_234082_P_ = func_234108_a_("hunted_recently", Codec.BOOL);
   public static final MemoryModuleType<BlockPos> field_234083_Q_ = register("celebrate_location");
   public static final MemoryModuleType<Boolean> field_234084_R_ = register("dancing");
   public static final MemoryModuleType<HoglinEntity> field_234085_S_ = register("nearest_visible_huntable_hoglin");
   public static final MemoryModuleType<HoglinEntity> field_234086_T_ = register("nearest_visible_baby_hoglin");
   public static final MemoryModuleType<PlayerEntity> field_234088_V_ = register("nearest_targetable_player_not_wearing_gold");
   public static final MemoryModuleType<List<AbstractPiglinEntity>> field_234089_W_ = register("nearby_adult_piglins");
   public static final MemoryModuleType<List<AbstractPiglinEntity>> field_234090_X_ = register("nearest_visible_adult_piglins");
   public static final MemoryModuleType<List<HoglinEntity>> field_234091_Y_ = register("nearest_visible_adult_hoglins");
   public static final MemoryModuleType<AbstractPiglinEntity> field_234092_Z_ = register("nearest_visible_adult_piglin");
   public static final MemoryModuleType<LivingEntity> field_234093_aa_ = register("nearest_visible_zombified");
   public static final MemoryModuleType<Integer> field_234094_ab_ = register("visible_adult_piglin_count");
   public static final MemoryModuleType<Integer> field_234095_ac_ = register("visible_adult_hoglin_count");
   public static final MemoryModuleType<PlayerEntity> field_234096_ad_ = register("nearest_player_holding_wanted_item");
   public static final MemoryModuleType<Boolean> field_234097_ae_ = register("ate_recently");
   public static final MemoryModuleType<BlockPos> field_234098_af_ = register("nearest_repellent");
   public static final MemoryModuleType<Boolean> field_234099_ag_ = register("pacified");
   private final Optional<Codec<Memory<U>>> field_234100_ah_;

   public MemoryModuleType(Optional<Codec<U>> p_i50306_1_) {
      this.field_234100_ah_ = p_i50306_1_.map(Memory::func_234066_a_);
   }

   public String toString() {
      return Registry.MEMORY_MODULE_TYPE.getKey(this).toString();
   }

   public Optional<Codec<Memory<U>>> func_234107_a_() {
      return this.field_234100_ah_;
   }

   private static <U> MemoryModuleType<U> func_234108_a_(String p_234108_0_, Codec<U> p_234108_1_) {
      return Registry.register(Registry.MEMORY_MODULE_TYPE, new ResourceLocation(p_234108_0_), new MemoryModuleType<>(Optional.of(p_234108_1_)));
   }

   private static <U> MemoryModuleType<U> register(String p_223541_0_) {
      return Registry.register(Registry.MEMORY_MODULE_TYPE, new ResourceLocation(p_223541_0_), new MemoryModuleType<>(Optional.empty()));
   }
}