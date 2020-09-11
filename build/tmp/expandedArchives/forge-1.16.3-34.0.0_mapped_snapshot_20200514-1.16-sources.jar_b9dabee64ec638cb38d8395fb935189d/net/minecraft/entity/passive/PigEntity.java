package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.BoostHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.IRideable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TransportationHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PigEntity extends AnimalEntity implements IRideable, IEquipable {
   private static final DataParameter<Boolean> SADDLED = EntityDataManager.createKey(PigEntity.class, DataSerializers.BOOLEAN);
   private static final DataParameter<Integer> BOOST_TIME = EntityDataManager.createKey(PigEntity.class, DataSerializers.VARINT);
   private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.CARROT, Items.POTATO, Items.BEETROOT);
   private final BoostHelper field_234214_bx_ = new BoostHelper(this.dataManager, BOOST_TIME, SADDLED);

   public PigEntity(EntityType<? extends PigEntity> p_i50250_1_, World p_i50250_2_) {
      super(p_i50250_1_, p_i50250_2_);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new SwimGoal(this));
      this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
      this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
      this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.fromItems(Items.CARROT_ON_A_STICK), false));
      this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, false, TEMPTATION_ITEMS));
      this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
      this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
      this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
      this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
   }

   public static AttributeModifierMap.MutableAttribute func_234215_eI_() {
      return MobEntity.func_233666_p_().func_233815_a_(Attributes.field_233818_a_, 10.0D).func_233815_a_(Attributes.field_233821_d_, 0.25D);
   }

   /**
    * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
    * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
    */
   @Nullable
   public Entity getControllingPassenger() {
      return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
   }

   /**
    * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
    * by a player and the player is holding a carrot-on-a-stick
    */
   public boolean canBeSteered() {
      Entity entity = this.getControllingPassenger();
      if (!(entity instanceof PlayerEntity)) {
         return false;
      } else {
         PlayerEntity playerentity = (PlayerEntity)entity;
         return playerentity.getHeldItemMainhand().getItem() == Items.CARROT_ON_A_STICK || playerentity.getHeldItemOffhand().getItem() == Items.CARROT_ON_A_STICK;
      }
   }

   public void notifyDataManagerChange(DataParameter<?> key) {
      if (BOOST_TIME.equals(key) && this.world.isRemote) {
         this.field_234214_bx_.func_233616_a_();
      }

      super.notifyDataManagerChange(key);
   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(SADDLED, false);
      this.dataManager.register(BOOST_TIME, 0);
   }

   public void writeAdditional(CompoundNBT compound) {
      super.writeAdditional(compound);
      this.field_234214_bx_.func_233618_a_(compound);
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      this.field_234214_bx_.func_233621_b_(compound);
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_PIG_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_PIG_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_PIG_DEATH;
   }

   protected void playStepSound(BlockPos pos, BlockState blockIn) {
      this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
   }

   public ActionResultType func_230254_b_(PlayerEntity p_230254_1_, Hand p_230254_2_) {
      boolean flag = this.isBreedingItem(p_230254_1_.getHeldItem(p_230254_2_));
      if (!flag && this.isHorseSaddled() && !this.isBeingRidden() && !p_230254_1_.isSecondaryUseActive()) {
         if (!this.world.isRemote) {
            p_230254_1_.startRiding(this);
         }

         return ActionResultType.func_233537_a_(this.world.isRemote);
      } else {
         ActionResultType actionresulttype = super.func_230254_b_(p_230254_1_, p_230254_2_);
         if (!actionresulttype.isSuccessOrConsume()) {
            ItemStack itemstack = p_230254_1_.getHeldItem(p_230254_2_);
            return itemstack.getItem() == Items.SADDLE ? itemstack.func_111282_a_(p_230254_1_, this, p_230254_2_) : ActionResultType.PASS;
         } else {
            return actionresulttype;
         }
      }
   }

   public boolean func_230264_L__() {
      return this.isAlive() && !this.isChild();
   }

   protected void dropInventory() {
      super.dropInventory();
      if (this.isHorseSaddled()) {
         this.entityDropItem(Items.SADDLE);
      }

   }

   public boolean isHorseSaddled() {
      return this.field_234214_bx_.func_233620_b_();
   }

   public void func_230266_a_(@Nullable SoundCategory p_230266_1_) {
      this.field_234214_bx_.func_233619_a_(true);
      if (p_230266_1_ != null) {
         this.world.playMovingSound((PlayerEntity)null, this, SoundEvents.ENTITY_PIG_SADDLE, p_230266_1_, 0.5F, 1.0F);
      }

   }

   public Vector3d func_230268_c_(LivingEntity p_230268_1_) {
      Direction direction = this.getAdjustedHorizontalFacing();
      if (direction.getAxis() == Direction.Axis.Y) {
         return super.func_230268_c_(p_230268_1_);
      } else {
         int[][] aint = TransportationHelper.func_234632_a_(direction);
         BlockPos blockpos = this.func_233580_cy_();
         BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

         for(Pose pose : p_230268_1_.func_230297_ef_()) {
            AxisAlignedBB axisalignedbb = p_230268_1_.func_233648_f_(pose);

            for(int[] aint1 : aint) {
               blockpos$mutable.setPos(blockpos.getX() + aint1[0], blockpos.getY(), blockpos.getZ() + aint1[1]);
               double d0 = this.world.func_242403_h(blockpos$mutable);
               if (TransportationHelper.func_234630_a_(d0)) {
                  Vector3d vector3d = Vector3d.func_237490_a_(blockpos$mutable, d0);
                  if (TransportationHelper.func_234631_a_(this.world, p_230268_1_, axisalignedbb.offset(vector3d))) {
                     p_230268_1_.setPose(pose);
                     return vector3d;
                  }
               }
            }
         }

         return super.func_230268_c_(p_230268_1_);
      }
   }

   public void func_241841_a(ServerWorld p_241841_1_, LightningBoltEntity p_241841_2_) {
      if (p_241841_1_.getDifficulty() != Difficulty.PEACEFUL) {
         ZombifiedPiglinEntity zombifiedpiglinentity = EntityType.field_233592_ba_.create(p_241841_1_);
         zombifiedpiglinentity.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
         zombifiedpiglinentity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
         zombifiedpiglinentity.setNoAI(this.isAIDisabled());
         zombifiedpiglinentity.setChild(this.isChild());
         if (this.hasCustomName()) {
            zombifiedpiglinentity.setCustomName(this.getCustomName());
            zombifiedpiglinentity.setCustomNameVisible(this.isCustomNameVisible());
         }

         zombifiedpiglinentity.enablePersistence();
         p_241841_1_.addEntity(zombifiedpiglinentity);
         this.remove();
      } else {
         super.func_241841_a(p_241841_1_, p_241841_2_);
      }

   }

   public void travel(Vector3d p_213352_1_) {
      this.func_233622_a_(this, this.field_234214_bx_, p_213352_1_);
   }

   public float func_230265_N__() {
      return (float)this.func_233637_b_(Attributes.field_233821_d_) * 0.225F;
   }

   public void func_230267_a__(Vector3d p_230267_1_) {
      super.travel(p_230267_1_);
   }

   public boolean boost() {
      return this.field_234214_bx_.func_233617_a_(this.getRNG());
   }

   public PigEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
      return EntityType.PIG.create(p_241840_1_);
   }

   /**
    * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
    * the animal type)
    */
   public boolean isBreedingItem(ItemStack stack) {
      return TEMPTATION_ITEMS.test(stack);
   }

   @OnlyIn(Dist.CLIENT)
   public Vector3d func_241205_ce_() {
      return new Vector3d(0.0D, (double)(0.6F * this.getEyeHeight()), (double)(this.getWidth() * 0.4F));
   }
}