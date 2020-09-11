package net.minecraft.entity.ai.brain;

import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPosWrapper;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class BrainUtil {
   public static void lookApproachEachOther(LivingEntity firstEntity, LivingEntity secondEntity, float p_220618_2_) {
      lookAtEachOther(firstEntity, secondEntity);
      approachEachOther(firstEntity, secondEntity, p_220618_2_);
   }

   public static boolean canSee(Brain<?> brainIn, LivingEntity target) {
      return brainIn.getMemory(MemoryModuleType.VISIBLE_MOBS).filter((p_220614_1_) -> {
         return p_220614_1_.contains(target);
      }).isPresent();
   }

   public static boolean isCorrectVisibleType(Brain<?> brains, MemoryModuleType<? extends LivingEntity> memorymodule, EntityType<?> entityTypeIn) {
      return func_233870_a_(brains, memorymodule, (p_220622_1_) -> {
         return p_220622_1_.getType() == entityTypeIn;
      });
   }

   private static boolean func_233870_a_(Brain<?> p_233870_0_, MemoryModuleType<? extends LivingEntity> p_233870_1_, Predicate<LivingEntity> p_233870_2_) {
      return p_233870_0_.getMemory(p_233870_1_).filter(p_233870_2_).filter(LivingEntity::isAlive).filter((p_220615_1_) -> {
         return canSee(p_233870_0_, p_220615_1_);
      }).isPresent();
   }

   private static void lookAtEachOther(LivingEntity firstEntity, LivingEntity secondEntity) {
      lookAt(firstEntity, secondEntity);
      lookAt(secondEntity, firstEntity);
   }

   public static void lookAt(LivingEntity entityIn, LivingEntity targetIn) {
      entityIn.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(targetIn, true));
   }

   private static void approachEachOther(LivingEntity firstEntity, LivingEntity secondEntity, float p_220626_2_) {
      int i = 2;
      func_233860_a_(firstEntity, secondEntity, p_220626_2_, 2);
      func_233860_a_(secondEntity, firstEntity, p_220626_2_, 2);
   }

   public static void func_233860_a_(LivingEntity p_233860_0_, Entity p_233860_1_, float p_233860_2_, int p_233860_3_) {
      WalkTarget walktarget = new WalkTarget(new EntityPosWrapper(p_233860_1_, false), p_233860_2_, p_233860_3_);
      p_233860_0_.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(p_233860_1_, true));
      p_233860_0_.getBrain().setMemory(MemoryModuleType.WALK_TARGET, walktarget);
   }

   public static void func_233866_a_(LivingEntity p_233866_0_, BlockPos p_233866_1_, float p_233866_2_, int p_233866_3_) {
      WalkTarget walktarget = new WalkTarget(new BlockPosWrapper(p_233866_1_), p_233866_2_, p_233866_3_);
      p_233866_0_.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosWrapper(p_233866_1_));
      p_233866_0_.getBrain().setMemory(MemoryModuleType.WALK_TARGET, walktarget);
   }

   public static void func_233865_a_(LivingEntity p_233865_0_, ItemStack p_233865_1_, Vector3d p_233865_2_) {
      double d0 = p_233865_0_.getPosYEye() - (double)0.3F;
      ItemEntity itementity = new ItemEntity(p_233865_0_.world, p_233865_0_.getPosX(), d0, p_233865_0_.getPosZ(), p_233865_1_);
      float f = 0.3F;
      Vector3d vector3d = p_233865_2_.subtract(p_233865_0_.getPositionVec());
      vector3d = vector3d.normalize().scale((double)0.3F);
      itementity.setMotion(vector3d);
      itementity.setDefaultPickupDelay();
      p_233865_0_.world.addEntity(itementity);
   }

   public static SectionPos func_220617_a(ServerWorld serverWorldIn, SectionPos sectionPosIn, int radius) {
      int i = serverWorldIn.sectionsToVillage(sectionPosIn);
      return SectionPos.getAllInBox(sectionPosIn, radius).filter((p_220620_2_) -> {
         return serverWorldIn.sectionsToVillage(p_220620_2_) < i;
      }).min(Comparator.comparingInt(serverWorldIn::sectionsToVillage)).orElse(sectionPosIn);
   }

   public static boolean func_233869_a_(MobEntity p_233869_0_, LivingEntity p_233869_1_, int p_233869_2_) {
      Item item = p_233869_0_.getHeldItemMainhand().getItem();
      if (item instanceof ShootableItem && p_233869_0_.func_230280_a_((ShootableItem)item)) {
         int i = ((ShootableItem)item).func_230305_d_() - p_233869_2_;
         return p_233869_0_.func_233562_a_(p_233869_1_, (double)i);
      } else {
         return func_233874_b_(p_233869_0_, p_233869_1_);
      }
   }

   public static boolean func_233874_b_(LivingEntity p_233874_0_, LivingEntity p_233874_1_) {
      double d0 = p_233874_0_.getDistanceSq(p_233874_1_.getPosX(), p_233874_1_.getPosY(), p_233874_1_.getPosZ());
      double d1 = (double)(p_233874_0_.getWidth() * 2.0F * p_233874_0_.getWidth() * 2.0F + p_233874_1_.getWidth());
      return d0 <= d1;
   }

   public static boolean func_233861_a_(LivingEntity p_233861_0_, LivingEntity p_233861_1_, double p_233861_2_) {
      Optional<LivingEntity> optional = p_233861_0_.getBrain().getMemory(MemoryModuleType.field_234103_o_);
      if (!optional.isPresent()) {
         return false;
      } else {
         double d0 = p_233861_0_.getDistanceSq(optional.get().getPositionVec());
         double d1 = p_233861_0_.getDistanceSq(p_233861_1_.getPositionVec());
         return d1 > d0 + p_233861_2_ * p_233861_2_;
      }
   }

   public static boolean func_233876_c_(LivingEntity p_233876_0_, LivingEntity p_233876_1_) {
      Brain<?> brain = p_233876_0_.getBrain();
      return !brain.hasMemory(MemoryModuleType.VISIBLE_MOBS) ? false : brain.getMemory(MemoryModuleType.VISIBLE_MOBS).get().contains(p_233876_1_);
   }

   public static LivingEntity func_233867_a_(LivingEntity p_233867_0_, Optional<LivingEntity> p_233867_1_, LivingEntity p_233867_2_) {
      return !p_233867_1_.isPresent() ? p_233867_2_ : func_233863_a_(p_233867_0_, p_233867_1_.get(), p_233867_2_);
   }

   public static LivingEntity func_233863_a_(LivingEntity p_233863_0_, LivingEntity p_233863_1_, LivingEntity p_233863_2_) {
      Vector3d vector3d = p_233863_1_.getPositionVec();
      Vector3d vector3d1 = p_233863_2_.getPositionVec();
      return p_233863_0_.getDistanceSq(vector3d) < p_233863_0_.getDistanceSq(vector3d1) ? p_233863_1_ : p_233863_2_;
   }

   public static Optional<LivingEntity> func_233864_a_(LivingEntity p_233864_0_, MemoryModuleType<UUID> p_233864_1_) {
      Optional<UUID> optional = p_233864_0_.getBrain().getMemory(p_233864_1_);
      return optional.map((p_233868_1_) -> {
         return (LivingEntity)((ServerWorld)p_233864_0_.world).getEntityByUuid(p_233868_1_);
      });
   }

   public static Stream<VillagerEntity> func_233872_a_(VillagerEntity p_233872_0_, Predicate<VillagerEntity> p_233872_1_) {
      return p_233872_0_.getBrain().getMemory(MemoryModuleType.MOBS).map((p_233873_2_) -> {
         return p_233873_2_.stream().filter((p_233871_1_) -> {
            return p_233871_1_ instanceof VillagerEntity && p_233871_1_ != p_233872_0_;
         }).map((p_233859_0_) -> {
            return (VillagerEntity)p_233859_0_;
         }).filter(LivingEntity::isAlive).filter(p_233872_1_);
      }).orElseGet(Stream::empty);
   }
}