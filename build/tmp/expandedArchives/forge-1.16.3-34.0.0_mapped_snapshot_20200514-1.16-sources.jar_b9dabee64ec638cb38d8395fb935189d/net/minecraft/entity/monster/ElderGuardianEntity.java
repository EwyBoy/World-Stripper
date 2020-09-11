package net.minecraft.entity.monster;

import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ElderGuardianEntity extends GuardianEntity {
   public static final float field_213629_b = EntityType.ELDER_GUARDIAN.getWidth() / EntityType.GUARDIAN.getWidth();

   public ElderGuardianEntity(EntityType<? extends ElderGuardianEntity> type, World worldIn) {
      super(type, worldIn);
      this.enablePersistence();
      if (this.wander != null) {
         this.wander.setExecutionChance(400);
      }

   }

   public static AttributeModifierMap.MutableAttribute func_234283_m_() {
      return GuardianEntity.func_234292_eK_().func_233815_a_(Attributes.field_233821_d_, (double)0.3F).func_233815_a_(Attributes.field_233823_f_, 8.0D).func_233815_a_(Attributes.field_233818_a_, 80.0D);
   }

   public int getAttackDuration() {
      return 60;
   }

   protected SoundEvent getAmbientSound() {
      return this.isInWaterOrBubbleColumn() ? SoundEvents.ENTITY_ELDER_GUARDIAN_AMBIENT : SoundEvents.ENTITY_ELDER_GUARDIAN_AMBIENT_LAND;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return this.isInWaterOrBubbleColumn() ? SoundEvents.ENTITY_ELDER_GUARDIAN_HURT : SoundEvents.ENTITY_ELDER_GUARDIAN_HURT_LAND;
   }

   protected SoundEvent getDeathSound() {
      return this.isInWaterOrBubbleColumn() ? SoundEvents.ENTITY_ELDER_GUARDIAN_DEATH : SoundEvents.ENTITY_ELDER_GUARDIAN_DEATH_LAND;
   }

   protected SoundEvent getFlopSound() {
      return SoundEvents.ENTITY_ELDER_GUARDIAN_FLOP;
   }

   protected void updateAITasks() {
      super.updateAITasks();
      int i = 1200;
      if ((this.ticksExisted + this.getEntityId()) % 1200 == 0) {
         Effect effect = Effects.MINING_FATIGUE;
         List<ServerPlayerEntity> list = ((ServerWorld)this.world).getPlayers((p_210138_1_) -> {
            return this.getDistanceSq(p_210138_1_) < 2500.0D && p_210138_1_.interactionManager.survivalOrAdventure();
         });
         int j = 2;
         int k = 6000;
         int l = 1200;

         for(ServerPlayerEntity serverplayerentity : list) {
            if (!serverplayerentity.isPotionActive(effect) || serverplayerentity.getActivePotionEffect(effect).getAmplifier() < 2 || serverplayerentity.getActivePotionEffect(effect).getDuration() < 1200) {
               serverplayerentity.connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241774_k_, this.isSilent() ? 0.0F : 1.0F));
               serverplayerentity.addPotionEffect(new EffectInstance(effect, 6000, 2));
            }
         }
      }

      if (!this.detachHome()) {
         this.setHomePosAndDistance(this.func_233580_cy_(), 16);
      }

   }
}