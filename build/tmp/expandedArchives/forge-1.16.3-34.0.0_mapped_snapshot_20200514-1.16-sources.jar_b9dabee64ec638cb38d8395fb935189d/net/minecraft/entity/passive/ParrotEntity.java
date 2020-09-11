package net.minecraft.entity.passive;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.LandOnOwnersShoulderGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ParrotEntity extends ShoulderRidingEntity implements IFlyingAnimal {
   private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(ParrotEntity.class, DataSerializers.VARINT);
   private static final Predicate<MobEntity> CAN_MIMIC = new Predicate<MobEntity>() {
      public boolean test(@Nullable MobEntity p_test_1_) {
         return p_test_1_ != null && ParrotEntity.IMITATION_SOUND_EVENTS.containsKey(p_test_1_.getType());
      }
   };
   private static final Item DEADLY_ITEM = Items.COOKIE;
   private static final Set<Item> TAME_ITEMS = Sets.newHashSet(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);
   private static final Map<EntityType<?>, SoundEvent> IMITATION_SOUND_EVENTS = Util.make(Maps.newHashMap(), (p_200609_0_) -> {
      p_200609_0_.put(EntityType.BLAZE, SoundEvents.ENTITY_PARROT_IMITATE_BLAZE);
      p_200609_0_.put(EntityType.CAVE_SPIDER, SoundEvents.ENTITY_PARROT_IMITATE_SPIDER);
      p_200609_0_.put(EntityType.CREEPER, SoundEvents.ENTITY_PARROT_IMITATE_CREEPER);
      p_200609_0_.put(EntityType.DROWNED, SoundEvents.ENTITY_PARROT_IMITATE_DROWNED);
      p_200609_0_.put(EntityType.ELDER_GUARDIAN, SoundEvents.ENTITY_PARROT_IMITATE_ELDER_GUARDIAN);
      p_200609_0_.put(EntityType.ENDER_DRAGON, SoundEvents.ENTITY_PARROT_IMITATE_ENDER_DRAGON);
      p_200609_0_.put(EntityType.ENDERMITE, SoundEvents.ENTITY_PARROT_IMITATE_ENDERMITE);
      p_200609_0_.put(EntityType.EVOKER, SoundEvents.ENTITY_PARROT_IMITATE_EVOKER);
      p_200609_0_.put(EntityType.GHAST, SoundEvents.ENTITY_PARROT_IMITATE_GHAST);
      p_200609_0_.put(EntityType.GUARDIAN, SoundEvents.ENTITY_PARROT_IMITATE_GUARDIAN);
      p_200609_0_.put(EntityType.field_233588_G_, SoundEvents.field_232796_kj_);
      p_200609_0_.put(EntityType.HUSK, SoundEvents.ENTITY_PARROT_IMITATE_HUSK);
      p_200609_0_.put(EntityType.ILLUSIONER, SoundEvents.ENTITY_PARROT_IMITATE_ILLUSIONER);
      p_200609_0_.put(EntityType.MAGMA_CUBE, SoundEvents.ENTITY_PARROT_IMITATE_MAGMA_CUBE);
      p_200609_0_.put(EntityType.PHANTOM, SoundEvents.ENTITY_PARROT_IMITATE_PHANTOM);
      p_200609_0_.put(EntityType.field_233591_ai_, SoundEvents.field_232797_ko_);
      p_200609_0_.put(EntityType.field_242287_aj, SoundEvents.field_242131_kp);
      p_200609_0_.put(EntityType.PILLAGER, SoundEvents.ENTITY_PARROT_IMITATE_PILLAGER);
      p_200609_0_.put(EntityType.RAVAGER, SoundEvents.ENTITY_PARROT_IMITATE_RAVAGER);
      p_200609_0_.put(EntityType.SHULKER, SoundEvents.ENTITY_PARROT_IMITATE_SHULKER);
      p_200609_0_.put(EntityType.SILVERFISH, SoundEvents.ENTITY_PARROT_IMITATE_SILVERFISH);
      p_200609_0_.put(EntityType.SKELETON, SoundEvents.ENTITY_PARROT_IMITATE_SKELETON);
      p_200609_0_.put(EntityType.SLIME, SoundEvents.ENTITY_PARROT_IMITATE_SLIME);
      p_200609_0_.put(EntityType.SPIDER, SoundEvents.ENTITY_PARROT_IMITATE_SPIDER);
      p_200609_0_.put(EntityType.STRAY, SoundEvents.ENTITY_PARROT_IMITATE_STRAY);
      p_200609_0_.put(EntityType.VEX, SoundEvents.ENTITY_PARROT_IMITATE_VEX);
      p_200609_0_.put(EntityType.VINDICATOR, SoundEvents.ENTITY_PARROT_IMITATE_VINDICATOR);
      p_200609_0_.put(EntityType.WITCH, SoundEvents.ENTITY_PARROT_IMITATE_WITCH);
      p_200609_0_.put(EntityType.WITHER, SoundEvents.ENTITY_PARROT_IMITATE_WITHER);
      p_200609_0_.put(EntityType.WITHER_SKELETON, SoundEvents.ENTITY_PARROT_IMITATE_WITHER_SKELETON);
      p_200609_0_.put(EntityType.field_233590_aW_, SoundEvents.field_232786_kC_);
      p_200609_0_.put(EntityType.ZOMBIE, SoundEvents.ENTITY_PARROT_IMITATE_ZOMBIE);
      p_200609_0_.put(EntityType.ZOMBIE_VILLAGER, SoundEvents.ENTITY_PARROT_IMITATE_ZOMBIE_VILLAGER);
   });
   public float flap;
   public float flapSpeed;
   public float oFlapSpeed;
   public float oFlap;
   private float flapping = 1.0F;
   private boolean partyParrot;
   private BlockPos jukeboxPosition;

   public ParrotEntity(EntityType<? extends ParrotEntity> type, World worldIn) {
      super(type, worldIn);
      this.moveController = new FlyingMovementController(this, 10, false);
      this.setPathPriority(PathNodeType.DANGER_FIRE, -1.0F);
      this.setPathPriority(PathNodeType.DAMAGE_FIRE, -1.0F);
      this.setPathPriority(PathNodeType.COCOA, -1.0F);
   }

   @Nullable
   public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
      this.setVariant(this.rand.nextInt(5));
      if (spawnDataIn == null) {
         spawnDataIn = new AgeableEntity.AgeableData(false);
      }

      return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
   }

   /**
    * If Animal, checks if the age timer is negative
    */
   public boolean isChild() {
      return false;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
      this.goalSelector.addGoal(0, new SwimGoal(this));
      this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0F));
      this.goalSelector.addGoal(2, new SitGoal(this));
      this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.0D, 5.0F, 1.0F, true));
      this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
      this.goalSelector.addGoal(3, new LandOnOwnersShoulderGoal(this));
      this.goalSelector.addGoal(3, new FollowMobGoal(this, 1.0D, 3.0F, 7.0F));
   }

   public static AttributeModifierMap.MutableAttribute func_234213_eS_() {
      return MobEntity.func_233666_p_().func_233815_a_(Attributes.field_233818_a_, 6.0D).func_233815_a_(Attributes.field_233822_e_, (double)0.4F).func_233815_a_(Attributes.field_233821_d_, (double)0.2F);
   }

   /**
    * Returns new PathNavigateGround instance
    */
   protected PathNavigator createNavigator(World worldIn) {
      FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, worldIn);
      flyingpathnavigator.setCanOpenDoors(false);
      flyingpathnavigator.setCanSwim(true);
      flyingpathnavigator.setCanEnterDoors(true);
      return flyingpathnavigator;
   }

   protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
      return sizeIn.height * 0.6F;
   }

   /**
    * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
    * use this to react to sunlight and start to burn.
    */
   public void livingTick() {
      if (this.jukeboxPosition == null || !this.jukeboxPosition.withinDistance(this.getPositionVec(), 3.46D) || !this.world.getBlockState(this.jukeboxPosition).isIn(Blocks.JUKEBOX)) {
         this.partyParrot = false;
         this.jukeboxPosition = null;
      }

      if (this.world.rand.nextInt(400) == 0) {
         playMimicSound(this.world, this);
      }

      super.livingTick();
      this.calculateFlapping();
   }

   /**
    * Called when a record starts or stops playing. Used to make parrots start or stop partying.
    */
   @OnlyIn(Dist.CLIENT)
   public void setPartying(BlockPos pos, boolean isPartying) {
      this.jukeboxPosition = pos;
      this.partyParrot = isPartying;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isPartying() {
      return this.partyParrot;
   }

   private void calculateFlapping() {
      this.oFlap = this.flap;
      this.oFlapSpeed = this.flapSpeed;
      this.flapSpeed = (float)((double)this.flapSpeed + (double)(!this.onGround && !this.isPassenger() ? 4 : -1) * 0.3D);
      this.flapSpeed = MathHelper.clamp(this.flapSpeed, 0.0F, 1.0F);
      if (!this.onGround && this.flapping < 1.0F) {
         this.flapping = 1.0F;
      }

      this.flapping = (float)((double)this.flapping * 0.9D);
      Vector3d vector3d = this.getMotion();
      if (!this.onGround && vector3d.y < 0.0D) {
         this.setMotion(vector3d.mul(1.0D, 0.6D, 1.0D));
      }

      this.flap += this.flapping * 2.0F;
   }

   public static boolean playMimicSound(World worldIn, Entity parrotIn) {
      if (parrotIn.isAlive() && !parrotIn.isSilent() && worldIn.rand.nextInt(2) == 0) {
         List<MobEntity> list = worldIn.getEntitiesWithinAABB(MobEntity.class, parrotIn.getBoundingBox().grow(20.0D), CAN_MIMIC);
         if (!list.isEmpty()) {
            MobEntity mobentity = list.get(worldIn.rand.nextInt(list.size()));
            if (!mobentity.isSilent()) {
               SoundEvent soundevent = getMimicSound(mobentity.getType());
               worldIn.playSound((PlayerEntity)null, parrotIn.getPosX(), parrotIn.getPosY(), parrotIn.getPosZ(), soundevent, parrotIn.getSoundCategory(), 0.7F, getPitch(worldIn.rand));
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public ActionResultType func_230254_b_(PlayerEntity p_230254_1_, Hand p_230254_2_) {
      ItemStack itemstack = p_230254_1_.getHeldItem(p_230254_2_);
      if (!this.isTamed() && TAME_ITEMS.contains(itemstack.getItem())) {
         if (!p_230254_1_.abilities.isCreativeMode) {
            itemstack.shrink(1);
         }

         if (!this.isSilent()) {
            this.world.playSound((PlayerEntity)null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_PARROT_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
         }

         if (!this.world.isRemote) {
            if (this.rand.nextInt(10) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, p_230254_1_)) {
               this.setTamedBy(p_230254_1_);
               this.world.setEntityState(this, (byte)7);
            } else {
               this.world.setEntityState(this, (byte)6);
            }
         }

         return ActionResultType.func_233537_a_(this.world.isRemote);
      } else if (itemstack.getItem() == DEADLY_ITEM) {
         if (!p_230254_1_.abilities.isCreativeMode) {
            itemstack.shrink(1);
         }

         this.addPotionEffect(new EffectInstance(Effects.POISON, 900));
         if (p_230254_1_.isCreative() || !this.isInvulnerable()) {
            this.attackEntityFrom(DamageSource.causePlayerDamage(p_230254_1_), Float.MAX_VALUE);
         }

         return ActionResultType.func_233537_a_(this.world.isRemote);
      } else if (!this.isFlying() && this.isTamed() && this.isOwner(p_230254_1_)) {
         if (!this.world.isRemote) {
            this.func_233687_w_(!this.func_233685_eM_());
         }

         return ActionResultType.func_233537_a_(this.world.isRemote);
      } else {
         return super.func_230254_b_(p_230254_1_, p_230254_2_);
      }
   }

   /**
    * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
    * the animal type)
    */
   public boolean isBreedingItem(ItemStack stack) {
      return false;
   }

   public static boolean func_223317_c(EntityType<ParrotEntity> parrotIn, IWorld worldIn, SpawnReason reason, BlockPos p_223317_3_, Random random) {
      BlockState blockstate = worldIn.getBlockState(p_223317_3_.down());
      return (blockstate.func_235714_a_(BlockTags.LEAVES) || blockstate.isIn(Blocks.GRASS_BLOCK) || blockstate.func_235714_a_(BlockTags.LOGS) || blockstate.isIn(Blocks.AIR)) && worldIn.getLightSubtracted(p_223317_3_, 0) > 8;
   }

   public boolean onLivingFall(float distance, float damageMultiplier) {
      return false;
   }

   protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
   }

   /**
    * Returns true if the mob is currently able to mate with the specified mob.
    */
   public boolean canMateWith(AnimalEntity otherAnimal) {
      return false;
   }

   @Nullable
   public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
      return null;
   }

   public boolean attackEntityAsMob(Entity entityIn) {
      return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
   }

   @Nullable
   public SoundEvent getAmbientSound() {
      return func_234212_a_(this.world, this.world.rand);
   }

   public static SoundEvent func_234212_a_(World p_234212_0_, Random p_234212_1_) {
      if (p_234212_0_.getDifficulty() != Difficulty.PEACEFUL && p_234212_1_.nextInt(1000) == 0) {
         List<EntityType<?>> list = Lists.newArrayList(IMITATION_SOUND_EVENTS.keySet());
         return getMimicSound(list.get(p_234212_1_.nextInt(list.size())));
      } else {
         return SoundEvents.ENTITY_PARROT_AMBIENT;
      }
   }

   private static SoundEvent getMimicSound(EntityType<?> type) {
      return IMITATION_SOUND_EVENTS.getOrDefault(type, SoundEvents.ENTITY_PARROT_AMBIENT);
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_PARROT_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_PARROT_DEATH;
   }

   protected void playStepSound(BlockPos pos, BlockState blockIn) {
      this.playSound(SoundEvents.ENTITY_PARROT_STEP, 0.15F, 1.0F);
   }

   protected float playFlySound(float volume) {
      this.playSound(SoundEvents.ENTITY_PARROT_FLY, 0.15F, 1.0F);
      return volume + this.flapSpeed / 2.0F;
   }

   protected boolean makeFlySound() {
      return true;
   }

   /**
    * Gets the pitch of living sounds in living entities.
    */
   protected float getSoundPitch() {
      return getPitch(this.rand);
   }

   public static float getPitch(Random random) {
      return (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F;
   }

   public SoundCategory getSoundCategory() {
      return SoundCategory.NEUTRAL;
   }

   /**
    * Returns true if this entity should push and be pushed by other entities when colliding.
    */
   public boolean canBePushed() {
      return true;
   }

   protected void collideWithEntity(Entity entityIn) {
      if (!(entityIn instanceof PlayerEntity)) {
         super.collideWithEntity(entityIn);
      }
   }

   /**
    * Called when the entity is attacked.
    */
   public boolean attackEntityFrom(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else {
         this.func_233687_w_(false);
         return super.attackEntityFrom(source, amount);
      }
   }

   public int getVariant() {
      return MathHelper.clamp(this.dataManager.get(VARIANT), 0, 4);
   }

   public void setVariant(int variantIn) {
      this.dataManager.set(VARIANT, variantIn);
   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(VARIANT, 0);
   }

   public void writeAdditional(CompoundNBT compound) {
      super.writeAdditional(compound);
      compound.putInt("Variant", this.getVariant());
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      this.setVariant(compound.getInt("Variant"));
   }

   public boolean isFlying() {
      return !this.onGround;
   }

   @OnlyIn(Dist.CLIENT)
   public Vector3d func_241205_ce_() {
      return new Vector3d(0.0D, (double)(0.5F * this.getEyeHeight()), (double)(this.getWidth() * 0.4F));
   }
}