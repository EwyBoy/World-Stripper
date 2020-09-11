package net.minecraft.entity;

import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.EntityPredicates;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public interface IAngerable {
   int func_230256_F__();

   void func_230260_a__(int p_230260_1_);

   @Nullable
   UUID func_230257_G__();

   void func_230259_a_(@Nullable UUID p_230259_1_);

   void func_230258_H__();

   default void func_233682_c_(CompoundNBT p_233682_1_) {
      p_233682_1_.putInt("AngerTime", this.func_230256_F__());
      if (this.func_230257_G__() != null) {
         p_233682_1_.putUniqueId("AngryAt", this.func_230257_G__());
      }

   }

   default void func_241358_a_(ServerWorld p_241358_1_, CompoundNBT p_241358_2_) {
      this.func_230260_a__(p_241358_2_.getInt("AngerTime"));
      if (!p_241358_2_.hasUniqueId("AngryAt")) {
         this.func_230259_a_((UUID)null);
      } else {
         UUID uuid = p_241358_2_.getUniqueId("AngryAt");
         this.func_230259_a_(uuid);
         Entity entity = p_241358_1_.getEntityByUuid(uuid);
         if (entity != null) {
            if (entity instanceof MobEntity) {
               this.setRevengeTarget((MobEntity)entity);
            }

            if (entity.getType() == EntityType.PLAYER) {
               this.func_230246_e_((PlayerEntity)entity);
            }

         }
      }
   }

   default void func_241359_a_(ServerWorld p_241359_1_, boolean p_241359_2_) {
      LivingEntity livingentity = this.getAttackTarget();
      UUID uuid = this.func_230257_G__();
      if ((livingentity == null || livingentity.func_233643_dh_()) && uuid != null && p_241359_1_.getEntityByUuid(uuid) instanceof MobEntity) {
         this.func_241356_K__();
      } else {
         if (livingentity != null && !Objects.equals(uuid, livingentity.getUniqueID())) {
            this.func_230259_a_(livingentity.getUniqueID());
            this.func_230258_H__();
         }

         if (this.func_230256_F__() > 0 && (livingentity == null || livingentity.getType() != EntityType.PLAYER || !p_241359_2_)) {
            this.func_230260_a__(this.func_230256_F__() - 1);
            if (this.func_230256_F__() == 0) {
               this.func_241356_K__();
            }
         }

      }
   }

   default boolean func_233680_b_(LivingEntity p_233680_1_) {
      if (!EntityPredicates.field_233583_f_.test(p_233680_1_)) {
         return false;
      } else {
         return p_233680_1_.getType() == EntityType.PLAYER && this.func_241357_a_(p_233680_1_.world) ? true : p_233680_1_.getUniqueID().equals(this.func_230257_G__());
      }
   }

   default boolean func_241357_a_(World p_241357_1_) {
      return p_241357_1_.getGameRules().getBoolean(GameRules.field_234896_G_) && this.func_233678_J__() && this.func_230257_G__() == null;
   }

   default boolean func_233678_J__() {
      return this.func_230256_F__() > 0;
   }

   default void func_233681_b_(PlayerEntity p_233681_1_) {
      if (p_233681_1_.world.getGameRules().getBoolean(GameRules.field_234895_F_)) {
         if (p_233681_1_.getUniqueID().equals(this.func_230257_G__())) {
            this.func_241356_K__();
         }
      }
   }

   default void func_241355_J__() {
      this.func_241356_K__();
      this.func_230258_H__();
   }

   default void func_241356_K__() {
      this.setRevengeTarget((LivingEntity)null);
      this.func_230259_a_((UUID)null);
      this.setAttackTarget((LivingEntity)null);
      this.func_230260_a__(0);
   }

   /**
    * Hint to AI tasks that we were attacked by the passed EntityLivingBase and should retaliate. Is not guaranteed to
    * change our actual active target (for example if we are currently busy attacking someone else)
    */
   void setRevengeTarget(@Nullable LivingEntity livingBase);

   void func_230246_e_(@Nullable PlayerEntity p_230246_1_);

   /**
    * Sets the active target the Task system uses for tracking
    */
   void setAttackTarget(@Nullable LivingEntity entitylivingbaseIn);

   /**
    * Gets the active target the Task system uses for tracking
    */
   @Nullable
   LivingEntity getAttackTarget();
}