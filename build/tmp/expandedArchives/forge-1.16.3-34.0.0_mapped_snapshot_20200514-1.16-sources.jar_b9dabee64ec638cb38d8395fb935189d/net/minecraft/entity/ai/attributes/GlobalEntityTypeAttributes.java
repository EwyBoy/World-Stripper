package net.minecraft.entity.ai.attributes;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.monster.CaveSpiderEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.ElderGuardianEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.entity.monster.EvokerEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.GiantEntity;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.IllusionerEntity;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.monster.VindicatorEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.ZoglinEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GlobalEntityTypeAttributes {
   private static final Logger field_233832_a_ = LogManager.getLogger();
   private static final Map<EntityType<? extends LivingEntity>, AttributeModifierMap> field_233833_b_ = ImmutableMap.<EntityType<? extends LivingEntity>, AttributeModifierMap>builder().put(EntityType.ARMOR_STAND, LivingEntity.func_233639_cI_().func_233813_a_()).put(EntityType.BAT, BatEntity.func_234175_m_().func_233813_a_()).put(EntityType.BEE, BeeEntity.func_234182_eX_().func_233813_a_()).put(EntityType.BLAZE, BlazeEntity.func_234276_m_().func_233813_a_()).put(EntityType.CAT, CatEntity.func_234184_eY_().func_233813_a_()).put(EntityType.CAVE_SPIDER, CaveSpiderEntity.func_234277_m_().func_233813_a_()).put(EntityType.CHICKEN, ChickenEntity.func_234187_eI_().func_233813_a_()).put(EntityType.COD, AbstractFishEntity.func_234176_m_().func_233813_a_()).put(EntityType.COW, CowEntity.func_234188_eI_().func_233813_a_()).put(EntityType.CREEPER, CreeperEntity.func_234278_m_().func_233813_a_()).put(EntityType.DOLPHIN, DolphinEntity.func_234190_eK_().func_233813_a_()).put(EntityType.DONKEY, AbstractChestedHorseEntity.func_234234_eJ_().func_233813_a_()).put(EntityType.DROWNED, ZombieEntity.func_234342_eQ_().func_233813_a_()).put(EntityType.ELDER_GUARDIAN, ElderGuardianEntity.func_234283_m_().func_233813_a_()).put(EntityType.ENDERMAN, EndermanEntity.func_234287_m_().func_233813_a_()).put(EntityType.ENDERMITE, EndermiteEntity.func_234288_m_().func_233813_a_()).put(EntityType.ENDER_DRAGON, EnderDragonEntity.func_234257_m_().func_233813_a_()).put(EntityType.EVOKER, EvokerEntity.func_234289_eI_().func_233813_a_()).put(EntityType.FOX, FoxEntity.func_234192_eI_().func_233813_a_()).put(EntityType.GHAST, GhastEntity.func_234290_eH_().func_233813_a_()).put(EntityType.GIANT, GiantEntity.func_234291_m_().func_233813_a_()).put(EntityType.GUARDIAN, GuardianEntity.func_234292_eK_().func_233813_a_()).put(EntityType.field_233588_G_, HoglinEntity.func_234362_eI_().func_233813_a_()).put(EntityType.HORSE, AbstractHorseEntity.func_234237_fg_().func_233813_a_()).put(EntityType.HUSK, ZombieEntity.func_234342_eQ_().func_233813_a_()).put(EntityType.ILLUSIONER, IllusionerEntity.func_234293_eI_().func_233813_a_()).put(EntityType.IRON_GOLEM, IronGolemEntity.func_234200_m_().func_233813_a_()).put(EntityType.LLAMA, LlamaEntity.func_234244_fu_().func_233813_a_()).put(EntityType.MAGMA_CUBE, MagmaCubeEntity.func_234294_m_().func_233813_a_()).put(EntityType.MOOSHROOM, CowEntity.func_234188_eI_().func_233813_a_()).put(EntityType.MULE, AbstractChestedHorseEntity.func_234234_eJ_().func_233813_a_()).put(EntityType.OCELOT, OcelotEntity.func_234201_eI_().func_233813_a_()).put(EntityType.PANDA, PandaEntity.func_234204_eW_().func_233813_a_()).put(EntityType.PARROT, ParrotEntity.func_234213_eS_().func_233813_a_()).put(EntityType.PHANTOM, MonsterEntity.func_234295_eP_().func_233813_a_()).put(EntityType.PIG, PigEntity.func_234215_eI_().func_233813_a_()).put(EntityType.field_233591_ai_, PiglinEntity.func_234420_eI_().func_233813_a_()).put(EntityType.field_242287_aj, PiglinBruteEntity.func_242344_eS().func_233813_a_()).put(EntityType.PILLAGER, PillagerEntity.func_234296_eI_().func_233813_a_()).put(EntityType.PLAYER, PlayerEntity.func_234570_el_().func_233813_a_()).put(EntityType.POLAR_BEAR, PolarBearEntity.func_234219_eI_().func_233813_a_()).put(EntityType.PUFFERFISH, AbstractFishEntity.func_234176_m_().func_233813_a_()).put(EntityType.RABBIT, RabbitEntity.func_234224_eJ_().func_233813_a_()).put(EntityType.RAVAGER, RavagerEntity.func_234297_m_().func_233813_a_()).put(EntityType.SALMON, AbstractFishEntity.func_234176_m_().func_233813_a_()).put(EntityType.SHEEP, SheepEntity.func_234225_eI_().func_233813_a_()).put(EntityType.SHULKER, ShulkerEntity.func_234300_m_().func_233813_a_()).put(EntityType.SILVERFISH, SilverfishEntity.func_234301_m_().func_233813_a_()).put(EntityType.SKELETON, AbstractSkeletonEntity.func_234275_m_().func_233813_a_()).put(EntityType.SKELETON_HORSE, SkeletonHorseEntity.func_234250_eJ_().func_233813_a_()).put(EntityType.SLIME, MonsterEntity.func_234295_eP_().func_233813_a_()).put(EntityType.SNOW_GOLEM, SnowGolemEntity.func_234226_m_().func_233813_a_()).put(EntityType.SPIDER, SpiderEntity.func_234305_eI_().func_233813_a_()).put(EntityType.SQUID, SquidEntity.func_234227_m_().func_233813_a_()).put(EntityType.STRAY, AbstractSkeletonEntity.func_234275_m_().func_233813_a_()).put(EntityType.field_233589_aE_, StriderEntity.func_234317_eK_().func_233813_a_()).put(EntityType.TRADER_LLAMA, LlamaEntity.func_234244_fu_().func_233813_a_()).put(EntityType.TROPICAL_FISH, AbstractFishEntity.func_234176_m_().func_233813_a_()).put(EntityType.TURTLE, TurtleEntity.func_234228_eK_().func_233813_a_()).put(EntityType.VEX, VexEntity.func_234321_m_().func_233813_a_()).put(EntityType.VILLAGER, VillagerEntity.func_234551_eU_().func_233813_a_()).put(EntityType.VINDICATOR, VindicatorEntity.func_234322_eI_().func_233813_a_()).put(EntityType.WANDERING_TRADER, MobEntity.func_233666_p_().func_233813_a_()).put(EntityType.WITCH, WitchEntity.func_234323_eI_().func_233813_a_()).put(EntityType.WITHER, WitherEntity.func_234258_eI_().func_233813_a_()).put(EntityType.WITHER_SKELETON, AbstractSkeletonEntity.func_234275_m_().func_233813_a_()).put(EntityType.WOLF, WolfEntity.func_234233_eS_().func_233813_a_()).put(EntityType.field_233590_aW_, ZoglinEntity.func_234339_m_().func_233813_a_()).put(EntityType.ZOMBIE, ZombieEntity.func_234342_eQ_().func_233813_a_()).put(EntityType.ZOMBIE_HORSE, ZombieHorseEntity.func_234256_eJ_().func_233813_a_()).put(EntityType.ZOMBIE_VILLAGER, ZombieEntity.func_234342_eQ_().func_233813_a_()).put(EntityType.field_233592_ba_, ZombifiedPiglinEntity.func_234352_eU_().func_233813_a_()).build();
   private static final Map<EntityType<? extends LivingEntity>, AttributeModifierMap> FORGE_ATTRIBUTES = new java.util.HashMap<EntityType<? extends LivingEntity>, AttributeModifierMap>();
   
   public static AttributeModifierMap put(EntityType<? extends LivingEntity> type, AttributeModifierMap map) {
       return FORGE_ATTRIBUTES.put(type, map);
   }

   public static AttributeModifierMap func_233835_a_(EntityType<? extends LivingEntity> p_233835_0_) {
       AttributeModifierMap map = FORGE_ATTRIBUTES.get(p_233835_0_);
       return map != null ? map : field_233833_b_.get(p_233835_0_);
   }

   public static boolean func_233837_b_(EntityType<?> p_233837_0_) {
      return field_233833_b_.containsKey(p_233837_0_) || FORGE_ATTRIBUTES.containsKey(p_233837_0_);
   }

   public static void func_233834_a_() {
      Registry.ENTITY_TYPE.stream().filter((p_233839_0_) -> {
         return p_233839_0_.getClassification() != EntityClassification.MISC;
      }).filter((p_233838_0_) -> {
         return !func_233837_b_(p_233838_0_);
      }).map(Registry.ENTITY_TYPE::getKey).forEach((p_233836_0_) -> {
         if (SharedConstants.developmentMode) {
            throw new IllegalStateException("Entity " + p_233836_0_ + " has no attributes");
         } else {
            field_233832_a_.error("Entity {} has no attributes", (Object)p_233836_0_);
         }
      });
   }
}