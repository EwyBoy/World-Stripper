package net.minecraft.entity.monster;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HoglinEntity extends AnimalEntity implements IMob, IFlinging {
   private static final DataParameter<Boolean> field_234356_bw_ = EntityDataManager.createKey(HoglinEntity.class, DataSerializers.BOOLEAN);
   private int field_234357_bx_;
   private int field_234358_by_ = 0;
   private boolean field_234359_bz_ = false;
   protected static final ImmutableList<? extends SensorType<? extends Sensor<? super HoglinEntity>>> field_234354_bu_ = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.field_234132_n_, SensorType.field_234131_m_);
   protected static final ImmutableList<? extends MemoryModuleType<?>> field_234355_bv_ = ImmutableList.of(MemoryModuleType.BREED_TARGET, MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.field_234102_l_, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.field_234103_o_, MemoryModuleType.field_234104_p_, MemoryModuleType.field_234092_Z_, MemoryModuleType.field_234106_z_, MemoryModuleType.field_234094_ab_, MemoryModuleType.field_234095_ac_, MemoryModuleType.field_234091_Y_, MemoryModuleType.field_234075_I_, MemoryModuleType.field_234098_af_, MemoryModuleType.field_234099_ag_);

   public HoglinEntity(EntityType<? extends HoglinEntity> p_i231569_1_, World p_i231569_2_) {
      super(p_i231569_1_, p_i231569_2_);
      this.experienceValue = 5;
   }

   public boolean canBeLeashedTo(PlayerEntity player) {
      return !this.getLeashed();
   }

   public static AttributeModifierMap.MutableAttribute func_234362_eI_() {
      return MonsterEntity.func_234295_eP_().func_233815_a_(Attributes.field_233818_a_, 40.0D).func_233815_a_(Attributes.field_233821_d_, (double)0.3F).func_233815_a_(Attributes.field_233820_c_, (double)0.6F).func_233815_a_(Attributes.field_233824_g_, 1.0D).func_233815_a_(Attributes.field_233823_f_, 6.0D);
   }

   public boolean attackEntityAsMob(Entity entityIn) {
      if (!(entityIn instanceof LivingEntity)) {
         return false;
      } else {
         this.field_234357_bx_ = 10;
         this.world.setEntityState(this, (byte)4);
         this.playSound(SoundEvents.field_232714_fD_, 1.0F, this.getSoundPitch());
         HoglinTasks.func_234378_a_(this, (LivingEntity)entityIn);
         return IFlinging.func_234403_a_(this, (LivingEntity)entityIn);
      }
   }

   protected void constructKnockBackVector(LivingEntity entityIn) {
      if (this.func_234363_eJ_()) {
         IFlinging.func_234404_b_(this, entityIn);
      }

   }

   /**
    * Called when the entity is attacked.
    */
   public boolean attackEntityFrom(DamageSource source, float amount) {
      boolean flag = super.attackEntityFrom(source, amount);
      if (this.world.isRemote) {
         return false;
      } else {
         if (flag && source.getTrueSource() instanceof LivingEntity) {
            HoglinTasks.func_234384_b_(this, (LivingEntity)source.getTrueSource());
         }

         return flag;
      }
   }

   protected Brain.BrainCodec<HoglinEntity> func_230289_cH_() {
      return Brain.func_233705_a_(field_234355_bv_, field_234354_bu_);
   }

   protected Brain<?> createBrain(Dynamic<?> dynamicIn) {
      return HoglinTasks.func_234376_a_(this.func_230289_cH_().func_233748_a_(dynamicIn));
   }

   public Brain<HoglinEntity> getBrain() {
      return (Brain<HoglinEntity>) super.getBrain();
   }

   protected void updateAITasks() {
      this.world.getProfiler().startSection("hoglinBrain");
      this.getBrain().tick((ServerWorld)this.world, this);
      this.world.getProfiler().endSection();
      HoglinTasks.func_234377_a_(this);
      if (this.func_234364_eK_()) {
         ++this.field_234358_by_;
         if (this.field_234358_by_ > 300) {
            this.func_241412_a_(SoundEvents.field_232715_fE_);
            this.func_234360_a_((ServerWorld)this.world);
         }
      } else {
         this.field_234358_by_ = 0;
      }

   }

   /**
    * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
    * use this to react to sunlight and start to burn.
    */
   public void livingTick() {
      if (this.field_234357_bx_ > 0) {
         --this.field_234357_bx_;
      }

      super.livingTick();
   }

   /**
    * This is called when Entity's growing age timer reaches 0 (negative values are considered as a child, positive as
    * an adult)
    */
   protected void onGrowingAdult() {
      if (this.isChild()) {
         this.experienceValue = 3;
         this.getAttribute(Attributes.field_233823_f_).setBaseValue(0.5D);
      } else {
         this.experienceValue = 5;
         this.getAttribute(Attributes.field_233823_f_).setBaseValue(6.0D);
      }

   }

   public static boolean func_234361_c_(EntityType<HoglinEntity> p_234361_0_, IWorld p_234361_1_, SpawnReason p_234361_2_, BlockPos p_234361_3_, Random p_234361_4_) {
      return !p_234361_1_.getBlockState(p_234361_3_.down()).isIn(Blocks.NETHER_WART_BLOCK);
   }

   @Nullable
   public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
      if (worldIn.getRandom().nextFloat() < 0.2F) {
         this.setChild(true);
      }

      return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
   }

   public boolean canDespawn(double distanceToClosestPlayer) {
      return !this.isNoDespawnRequired();
   }

   public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
      if (HoglinTasks.func_234380_a_(this, pos)) {
         return -1.0F;
      } else {
         return worldIn.getBlockState(pos.down()).isIn(Blocks.field_235381_mu_) ? 10.0F : 0.0F;
      }
   }

   /**
    * Returns the Y offset from the entity's position for any entity riding this one.
    */
   public double getMountedYOffset() {
      return (double)this.getHeight() - (this.isChild() ? 0.2D : 0.15D);
   }

   public ActionResultType func_230254_b_(PlayerEntity p_230254_1_, Hand p_230254_2_) {
      ActionResultType actionresulttype = super.func_230254_b_(p_230254_1_, p_230254_2_);
      if (actionresulttype.isSuccessOrConsume()) {
         this.enablePersistence();
      }

      return actionresulttype;
   }

   /**
    * Handler for {@link World#setEntityState}
    */
   @OnlyIn(Dist.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 4) {
         this.field_234357_bx_ = 10;
         this.playSound(SoundEvents.field_232714_fD_, 1.0F, this.getSoundPitch());
      } else {
         super.handleStatusUpdate(id);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public int func_230290_eL_() {
      return this.field_234357_bx_;
   }

   /**
    * Entity won't drop items or experience points if this returns false
    */
   protected boolean canDropLoot() {
      return true;
   }

   /**
    * Get the experience points the entity currently has.
    */
   protected int getExperiencePoints(PlayerEntity player) {
      return this.experienceValue;
   }

   private void func_234360_a_(ServerWorld p_234360_1_) {
      ZoglinEntity zoglinentity = this.func_233656_b_(EntityType.field_233590_aW_, true);
      if (zoglinentity != null) {
         zoglinentity.addPotionEffect(new EffectInstance(Effects.NAUSEA, 200, 0));
      }

   }

   /**
    * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
    * the animal type)
    */
   public boolean isBreedingItem(ItemStack stack) {
      return stack.getItem() == Items.field_234722_bw_;
   }

   public boolean func_234363_eJ_() {
      return !this.isChild();
   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(field_234356_bw_, false);
   }

   public void writeAdditional(CompoundNBT compound) {
      super.writeAdditional(compound);
      if (this.func_234368_eV_()) {
         compound.putBoolean("IsImmuneToZombification", true);
      }

      compound.putInt("TimeInOverworld", this.field_234358_by_);
      if (this.field_234359_bz_) {
         compound.putBoolean("CannotBeHunted", true);
      }

   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      this.func_234370_t_(compound.getBoolean("IsImmuneToZombification"));
      this.field_234358_by_ = compound.getInt("TimeInOverworld");
      this.func_234371_u_(compound.getBoolean("CannotBeHunted"));
   }

   public void func_234370_t_(boolean p_234370_1_) {
      this.getDataManager().set(field_234356_bw_, p_234370_1_);
   }

   private boolean func_234368_eV_() {
      return this.getDataManager().get(field_234356_bw_);
   }

   public boolean func_234364_eK_() {
      return !this.world.func_230315_m_().func_241509_i_() && !this.func_234368_eV_() && !this.isAIDisabled();
   }

   private void func_234371_u_(boolean p_234371_1_) {
      this.field_234359_bz_ = p_234371_1_;
   }

   public boolean func_234365_eM_() {
      return this.func_234363_eJ_() && !this.field_234359_bz_;
   }

   @Nullable
   public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
      HoglinEntity hoglinentity = EntityType.field_233588_G_.create(p_241840_1_);
      if (hoglinentity != null) {
         hoglinentity.enablePersistence();
      }

      return hoglinentity;
   }

   public boolean canBreed() {
      return !HoglinTasks.func_234386_c_(this) && super.canBreed();
   }

   public SoundCategory getSoundCategory() {
      return SoundCategory.HOSTILE;
   }

   protected SoundEvent getAmbientSound() {
      return this.world.isRemote ? null : HoglinTasks.func_234398_h_(this).orElse((SoundEvent)null);
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.field_232717_fG_;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.field_232716_fF_;
   }

   protected SoundEvent getSwimSound() {
      return SoundEvents.ENTITY_HOSTILE_SWIM;
   }

   protected SoundEvent getSplashSound() {
      return SoundEvents.ENTITY_HOSTILE_SPLASH;
   }

   protected void playStepSound(BlockPos pos, BlockState blockIn) {
      this.playSound(SoundEvents.field_232719_fI_, 0.15F, 1.0F);
   }

   protected void func_241412_a_(SoundEvent p_241412_1_) {
      this.playSound(p_241412_1_, this.getSoundVolume(), this.getSoundPitch());
   }

   protected void sendDebugPackets() {
      super.sendDebugPackets();
      DebugPacketSender.sendLivingEntity(this);
   }
}