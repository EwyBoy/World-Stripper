package net.minecraft.data.advancements;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.ChanneledLightningTrigger;
import net.minecraft.advancements.criterion.DamagePredicate;
import net.minecraft.advancements.criterion.DamageSourcePredicate;
import net.minecraft.advancements.criterion.DistancePredicate;
import net.minecraft.advancements.criterion.EntityEquipmentPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.KilledByCrossbowTrigger;
import net.minecraft.advancements.criterion.KilledTrigger;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.PlayerHurtEntityTrigger;
import net.minecraft.advancements.criterion.PositionTrigger;
import net.minecraft.advancements.criterion.ShotCrossbowTrigger;
import net.minecraft.advancements.criterion.SlideDownBlockTrigger;
import net.minecraft.advancements.criterion.SummonedEntityTrigger;
import net.minecraft.advancements.criterion.TargetHitTrigger;
import net.minecraft.advancements.criterion.UsedTotemTrigger;
import net.minecraft.advancements.criterion.VillagerTradeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.raid.Raid;

public class AdventureAdvancements implements Consumer<Consumer<Advancement>> {
   private static final List<RegistryKey<Biome>> EXPLORATION_BIOMES = ImmutableList.of(Biomes.BIRCH_FOREST_HILLS, Biomes.RIVER, Biomes.SWAMP, Biomes.DESERT, Biomes.WOODED_HILLS, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.SNOWY_TAIGA, Biomes.BADLANDS, Biomes.FOREST, Biomes.STONE_SHORE, Biomes.SNOWY_TUNDRA, Biomes.TAIGA_HILLS, Biomes.SNOWY_MOUNTAINS, Biomes.WOODED_BADLANDS_PLATEAU, Biomes.SAVANNA, Biomes.PLAINS, Biomes.FROZEN_RIVER, Biomes.GIANT_TREE_TAIGA, Biomes.SNOWY_BEACH, Biomes.JUNGLE_HILLS, Biomes.JUNGLE_EDGE, Biomes.MUSHROOM_FIELD_SHORE, Biomes.MOUNTAINS, Biomes.DESERT_HILLS, Biomes.JUNGLE, Biomes.BEACH, Biomes.SAVANNA_PLATEAU, Biomes.SNOWY_TAIGA_HILLS, Biomes.BADLANDS_PLATEAU, Biomes.DARK_FOREST, Biomes.TAIGA, Biomes.BIRCH_FOREST, Biomes.MUSHROOM_FIELDS, Biomes.WOODED_MOUNTAINS, Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.COLD_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.BAMBOO_JUNGLE, Biomes.BAMBOO_JUNGLE_HILLS);
   private static final EntityType<?>[] field_218459_b = new EntityType[]{EntityType.BLAZE, EntityType.CAVE_SPIDER, EntityType.CREEPER, EntityType.DROWNED, EntityType.ELDER_GUARDIAN, EntityType.ENDER_DRAGON, EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.EVOKER, EntityType.GHAST, EntityType.GUARDIAN, EntityType.field_233588_G_, EntityType.HUSK, EntityType.MAGMA_CUBE, EntityType.PHANTOM, EntityType.field_233591_ai_, EntityType.field_242287_aj, EntityType.PILLAGER, EntityType.RAVAGER, EntityType.SHULKER, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME, EntityType.SPIDER, EntityType.STRAY, EntityType.VEX, EntityType.VINDICATOR, EntityType.WITCH, EntityType.WITHER_SKELETON, EntityType.WITHER, EntityType.field_233590_aW_, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIE, EntityType.field_233592_ba_};

   public void accept(Consumer<Advancement> p_accept_1_) {
      Advancement advancement = Advancement.Builder.builder().withDisplay(Items.MAP, new TranslationTextComponent("advancements.adventure.root.title"), new TranslationTextComponent("advancements.adventure.root.description"), new ResourceLocation("textures/gui/advancements/backgrounds/adventure.png"), FrameType.TASK, false, false, false).withRequirementsStrategy(IRequirementsStrategy.OR).withCriterion("killed_something", KilledTrigger.Instance.playerKilledEntity()).withCriterion("killed_by_something", KilledTrigger.Instance.entityKilledPlayer()).register(p_accept_1_, "adventure/root");
      Advancement advancement1 = Advancement.Builder.builder().withParent(advancement).withDisplay(Blocks.RED_BED, new TranslationTextComponent("advancements.adventure.sleep_in_bed.title"), new TranslationTextComponent("advancements.adventure.sleep_in_bed.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).withCriterion("slept_in_bed", PositionTrigger.Instance.sleptInBed()).register(p_accept_1_, "adventure/sleep_in_bed");
      func_243675_a(Advancement.Builder.builder(), EXPLORATION_BIOMES).withParent(advancement1).withDisplay(Items.DIAMOND_BOOTS, new TranslationTextComponent("advancements.adventure.adventuring_time.title"), new TranslationTextComponent("advancements.adventure.adventuring_time.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, false).withRewards(AdvancementRewards.Builder.experience(500)).register(p_accept_1_, "adventure/adventuring_time");
      Advancement advancement2 = Advancement.Builder.builder().withParent(advancement).withDisplay(Items.EMERALD, new TranslationTextComponent("advancements.adventure.trade.title"), new TranslationTextComponent("advancements.adventure.trade.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).withCriterion("traded", VillagerTradeTrigger.Instance.any()).register(p_accept_1_, "adventure/trade");
      Advancement advancement3 = this.makeMobAdvancement(Advancement.Builder.builder()).withParent(advancement).withDisplay(Items.IRON_SWORD, new TranslationTextComponent("advancements.adventure.kill_a_mob.title"), new TranslationTextComponent("advancements.adventure.kill_a_mob.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).withRequirementsStrategy(IRequirementsStrategy.OR).register(p_accept_1_, "adventure/kill_a_mob");
      this.makeMobAdvancement(Advancement.Builder.builder()).withParent(advancement3).withDisplay(Items.DIAMOND_SWORD, new TranslationTextComponent("advancements.adventure.kill_all_mobs.title"), new TranslationTextComponent("advancements.adventure.kill_all_mobs.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, false).withRewards(AdvancementRewards.Builder.experience(100)).register(p_accept_1_, "adventure/kill_all_mobs");
      Advancement advancement4 = Advancement.Builder.builder().withParent(advancement3).withDisplay(Items.BOW, new TranslationTextComponent("advancements.adventure.shoot_arrow.title"), new TranslationTextComponent("advancements.adventure.shoot_arrow.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).withCriterion("shot_arrow", PlayerHurtEntityTrigger.Instance.forDamage(DamagePredicate.Builder.create().type(DamageSourcePredicate.Builder.damageType().isProjectile(true).direct(EntityPredicate.Builder.create().type(EntityTypeTags.ARROWS))))).register(p_accept_1_, "adventure/shoot_arrow");
      Advancement advancement5 = Advancement.Builder.builder().withParent(advancement3).withDisplay(Items.TRIDENT, new TranslationTextComponent("advancements.adventure.throw_trident.title"), new TranslationTextComponent("advancements.adventure.throw_trident.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).withCriterion("shot_trident", PlayerHurtEntityTrigger.Instance.forDamage(DamagePredicate.Builder.create().type(DamageSourcePredicate.Builder.damageType().isProjectile(true).direct(EntityPredicate.Builder.create().type(EntityType.TRIDENT))))).register(p_accept_1_, "adventure/throw_trident");
      Advancement.Builder.builder().withParent(advancement5).withDisplay(Items.TRIDENT, new TranslationTextComponent("advancements.adventure.very_very_frightening.title"), new TranslationTextComponent("advancements.adventure.very_very_frightening.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).withCriterion("struck_villager", ChanneledLightningTrigger.Instance.channeledLightning(EntityPredicate.Builder.create().type(EntityType.VILLAGER).build())).register(p_accept_1_, "adventure/very_very_frightening");
      Advancement.Builder.builder().withParent(advancement2).withDisplay(Blocks.CARVED_PUMPKIN, new TranslationTextComponent("advancements.adventure.summon_iron_golem.title"), new TranslationTextComponent("advancements.adventure.summon_iron_golem.description"), (ResourceLocation)null, FrameType.GOAL, true, true, false).withCriterion("summoned_golem", SummonedEntityTrigger.Instance.summonedEntity(EntityPredicate.Builder.create().type(EntityType.IRON_GOLEM))).register(p_accept_1_, "adventure/summon_iron_golem");
      Advancement.Builder.builder().withParent(advancement4).withDisplay(Items.ARROW, new TranslationTextComponent("advancements.adventure.sniper_duel.title"), new TranslationTextComponent("advancements.adventure.sniper_duel.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, false).withRewards(AdvancementRewards.Builder.experience(50)).withCriterion("killed_skeleton", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.create().type(EntityType.SKELETON).distance(DistancePredicate.forHorizontal(MinMaxBounds.FloatBound.atLeast(50.0F))), DamageSourcePredicate.Builder.damageType().isProjectile(true))).register(p_accept_1_, "adventure/sniper_duel");
      Advancement.Builder.builder().withParent(advancement3).withDisplay(Items.TOTEM_OF_UNDYING, new TranslationTextComponent("advancements.adventure.totem_of_undying.title"), new TranslationTextComponent("advancements.adventure.totem_of_undying.description"), (ResourceLocation)null, FrameType.GOAL, true, true, false).withCriterion("used_totem", UsedTotemTrigger.Instance.usedTotem(Items.TOTEM_OF_UNDYING)).register(p_accept_1_, "adventure/totem_of_undying");
      Advancement advancement6 = Advancement.Builder.builder().withParent(advancement).withDisplay(Items.CROSSBOW, new TranslationTextComponent("advancements.adventure.ol_betsy.title"), new TranslationTextComponent("advancements.adventure.ol_betsy.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).withCriterion("shot_crossbow", ShotCrossbowTrigger.Instance.create(Items.CROSSBOW)).register(p_accept_1_, "adventure/ol_betsy");
      Advancement.Builder.builder().withParent(advancement6).withDisplay(Items.CROSSBOW, new TranslationTextComponent("advancements.adventure.whos_the_pillager_now.title"), new TranslationTextComponent("advancements.adventure.whos_the_pillager_now.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).withCriterion("kill_pillager", KilledByCrossbowTrigger.Instance.func_215116_a(EntityPredicate.Builder.create().type(EntityType.PILLAGER))).register(p_accept_1_, "adventure/whos_the_pillager_now");
      Advancement.Builder.builder().withParent(advancement6).withDisplay(Items.CROSSBOW, new TranslationTextComponent("advancements.adventure.two_birds_one_arrow.title"), new TranslationTextComponent("advancements.adventure.two_birds_one_arrow.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, false).withRewards(AdvancementRewards.Builder.experience(65)).withCriterion("two_birds", KilledByCrossbowTrigger.Instance.func_215116_a(EntityPredicate.Builder.create().type(EntityType.PHANTOM), EntityPredicate.Builder.create().type(EntityType.PHANTOM))).register(p_accept_1_, "adventure/two_birds_one_arrow");
      Advancement.Builder.builder().withParent(advancement6).withDisplay(Items.CROSSBOW, new TranslationTextComponent("advancements.adventure.arbalistic.title"), new TranslationTextComponent("advancements.adventure.arbalistic.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true).withRewards(AdvancementRewards.Builder.experience(85)).withCriterion("arbalistic", KilledByCrossbowTrigger.Instance.func_215117_a(MinMaxBounds.IntBound.exactly(5))).register(p_accept_1_, "adventure/arbalistic");
      Advancement advancement7 = Advancement.Builder.builder().withParent(advancement).withDisplay(Raid.createIllagerBanner(), new TranslationTextComponent("advancements.adventure.voluntary_exile.title"), new TranslationTextComponent("advancements.adventure.voluntary_exile.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("voluntary_exile", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.create().type(EntityTypeTags.RAIDERS).equipment(EntityEquipmentPredicate.WEARING_ILLAGER_BANNER))).register(p_accept_1_, "adventure/voluntary_exile");
      Advancement.Builder.builder().withParent(advancement7).withDisplay(Raid.createIllagerBanner(), new TranslationTextComponent("advancements.adventure.hero_of_the_village.title"), new TranslationTextComponent("advancements.adventure.hero_of_the_village.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true).withRewards(AdvancementRewards.Builder.experience(100)).withCriterion("hero_of_the_village", PositionTrigger.Instance.func_215120_d()).register(p_accept_1_, "adventure/hero_of_the_village");
      Advancement.Builder.builder().withParent(advancement).withDisplay(Blocks.HONEY_BLOCK.asItem(), new TranslationTextComponent("advancements.adventure.honey_block_slide.title"), new TranslationTextComponent("advancements.adventure.honey_block_slide.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).withCriterion("honey_block_slide", SlideDownBlockTrigger.Instance.func_227156_a_(Blocks.HONEY_BLOCK)).register(p_accept_1_, "adventure/honey_block_slide");
      Advancement.Builder.builder().withParent(advancement4).withDisplay(Blocks.field_235396_nb_.asItem(), new TranslationTextComponent("advancements.adventure.bullseye.title"), new TranslationTextComponent("advancements.adventure.bullseye.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, false).withRewards(AdvancementRewards.Builder.experience(50)).withCriterion("bullseye", TargetHitTrigger.Instance.func_236354_a_(MinMaxBounds.IntBound.exactly(15), EntityPredicate.AndPredicate.func_234585_a_(EntityPredicate.Builder.create().distance(DistancePredicate.forHorizontal(MinMaxBounds.FloatBound.atLeast(30.0F))).build()))).register(p_accept_1_, "adventure/bullseye");
   }

   /**
    * Adds all the entities in {@link #MOB_ENTITIES} to the given advancement's criteria
    */
   private Advancement.Builder makeMobAdvancement(Advancement.Builder builder) {
      for(EntityType<?> entitytype : field_218459_b) {
         builder.withCriterion(Registry.ENTITY_TYPE.getKey(entitytype).toString(), KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.create().type(entitytype)));
      }

      return builder;
   }

   protected static Advancement.Builder func_243675_a(Advancement.Builder p_243675_0_, List<RegistryKey<Biome>> p_243675_1_) {
      for(RegistryKey<Biome> registrykey : p_243675_1_) {
         p_243675_0_.withCriterion(registrykey.func_240901_a_().toString(), PositionTrigger.Instance.forLocation(LocationPredicate.func_242665_a(registrykey)));
      }

      return p_243675_0_;
   }
}