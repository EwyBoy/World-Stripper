package net.minecraft.dispenser;

import java.util.List;
import java.util.Random;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.TNTBlock;
import net.minecraft.block.WitherSkeletonSkullBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.ExperienceBottleEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public interface IDispenseItemBehavior {
   IDispenseItemBehavior NOOP = (p_210297_0_, p_210297_1_) -> {
      return p_210297_1_;
   };

   ItemStack dispense(IBlockSource p_dispense_1_, ItemStack p_dispense_2_);

   static void init() {
      DispenserBlock.registerDispenseBehavior(Items.ARROW, new ProjectileDispenseBehavior() {
         /**
          * Return the projectile entity spawned by this dispense behavior.
          */
         protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
            ArrowEntity arrowentity = new ArrowEntity(worldIn, position.getX(), position.getY(), position.getZ());
            arrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
            return arrowentity;
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.TIPPED_ARROW, new ProjectileDispenseBehavior() {
         /**
          * Return the projectile entity spawned by this dispense behavior.
          */
         protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
            ArrowEntity arrowentity = new ArrowEntity(worldIn, position.getX(), position.getY(), position.getZ());
            arrowentity.setPotionEffect(stackIn);
            arrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
            return arrowentity;
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.SPECTRAL_ARROW, new ProjectileDispenseBehavior() {
         /**
          * Return the projectile entity spawned by this dispense behavior.
          */
         protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
            AbstractArrowEntity abstractarrowentity = new SpectralArrowEntity(worldIn, position.getX(), position.getY(), position.getZ());
            abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
            return abstractarrowentity;
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.EGG, new ProjectileDispenseBehavior() {
         /**
          * Return the projectile entity spawned by this dispense behavior.
          */
         protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
            return Util.make(new EggEntity(worldIn, position.getX(), position.getY(), position.getZ()), (p_218408_1_) -> {
               p_218408_1_.setItem(stackIn);
            });
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.SNOWBALL, new ProjectileDispenseBehavior() {
         /**
          * Return the projectile entity spawned by this dispense behavior.
          */
         protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
            return Util.make(new SnowballEntity(worldIn, position.getX(), position.getY(), position.getZ()), (p_218409_1_) -> {
               p_218409_1_.setItem(stackIn);
            });
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.EXPERIENCE_BOTTLE, new ProjectileDispenseBehavior() {
         /**
          * Return the projectile entity spawned by this dispense behavior.
          */
         protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
            return Util.make(new ExperienceBottleEntity(worldIn, position.getX(), position.getY(), position.getZ()), (p_218410_1_) -> {
               p_218410_1_.setItem(stackIn);
            });
         }

         protected float getProjectileInaccuracy() {
            return super.getProjectileInaccuracy() * 0.5F;
         }

         protected float getProjectileVelocity() {
            return super.getProjectileVelocity() * 1.25F;
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.SPLASH_POTION, new IDispenseItemBehavior() {
         public ItemStack dispense(IBlockSource p_dispense_1_, ItemStack p_dispense_2_) {
            return (new ProjectileDispenseBehavior() {
               /**
                * Return the projectile entity spawned by this dispense behavior.
                */
               protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                  return Util.make(new PotionEntity(worldIn, position.getX(), position.getY(), position.getZ()), (p_218411_1_) -> {
                     p_218411_1_.setItem(stackIn);
                  });
               }

               protected float getProjectileInaccuracy() {
                  return super.getProjectileInaccuracy() * 0.5F;
               }

               protected float getProjectileVelocity() {
                  return super.getProjectileVelocity() * 1.25F;
               }
            }).dispense(p_dispense_1_, p_dispense_2_);
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.LINGERING_POTION, new IDispenseItemBehavior() {
         public ItemStack dispense(IBlockSource p_dispense_1_, ItemStack p_dispense_2_) {
            return (new ProjectileDispenseBehavior() {
               /**
                * Return the projectile entity spawned by this dispense behavior.
                */
               protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                  return Util.make(new PotionEntity(worldIn, position.getX(), position.getY(), position.getZ()), (p_218413_1_) -> {
                     p_218413_1_.setItem(stackIn);
                  });
               }

               protected float getProjectileInaccuracy() {
                  return super.getProjectileInaccuracy() * 0.5F;
               }

               protected float getProjectileVelocity() {
                  return super.getProjectileVelocity() * 1.25F;
               }
            }).dispense(p_dispense_1_, p_dispense_2_);
         }
      });
      DefaultDispenseItemBehavior defaultdispenseitembehavior = new DefaultDispenseItemBehavior() {
         /**
          * Dispense the specified stack, play the dispense sound and spawn particles.
          */
         public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            Direction direction = source.getBlockState().get(DispenserBlock.FACING);
            EntityType<?> entitytype = ((SpawnEggItem)stack.getItem()).getType(stack.getTag());
            entitytype.spawn(source.getWorld(), stack, (PlayerEntity)null, source.getBlockPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
            stack.shrink(1);
            return stack;
         }
      };

      for(SpawnEggItem spawneggitem : SpawnEggItem.getEggs()) {
         DispenserBlock.registerDispenseBehavior(spawneggitem, defaultdispenseitembehavior);
      }

      DispenserBlock.registerDispenseBehavior(Items.ARMOR_STAND, new DefaultDispenseItemBehavior() {
         /**
          * Dispense the specified stack, play the dispense sound and spawn particles.
          */
         public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            Direction direction = source.getBlockState().get(DispenserBlock.FACING);
            BlockPos blockpos = source.getBlockPos().offset(direction);
            World world = source.getWorld();
            ArmorStandEntity armorstandentity = new ArmorStandEntity(world, (double)blockpos.getX() + 0.5D, (double)blockpos.getY(), (double)blockpos.getZ() + 0.5D);
            EntityType.applyItemNBT(world, (PlayerEntity)null, armorstandentity, stack.getTag());
            armorstandentity.rotationYaw = direction.getHorizontalAngle();
            world.addEntity(armorstandentity);
            stack.shrink(1);
            return stack;
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.SADDLE, new OptionalDispenseBehavior() {
         /**
          * Dispense the specified stack, play the dispense sound and spawn particles.
          */
         public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
            List<LivingEntity> list = source.getWorld().getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(blockpos), (p_239789_0_) -> {
               if (!(p_239789_0_ instanceof IEquipable)) {
                  return false;
               } else {
                  IEquipable iequipable = (IEquipable)p_239789_0_;
                  return !iequipable.isHorseSaddled() && iequipable.func_230264_L__();
               }
            });
            if (!list.isEmpty()) {
               ((IEquipable)list.get(0)).func_230266_a_(SoundCategory.BLOCKS);
               stack.shrink(1);
               this.func_239796_a_(true);
               return stack;
            } else {
               return super.dispenseStack(source, stack);
            }
         }
      });
      DefaultDispenseItemBehavior defaultdispenseitembehavior1 = new OptionalDispenseBehavior() {
         /**
          * Dispense the specified stack, play the dispense sound and spawn particles.
          */
         protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));

            for(AbstractHorseEntity abstracthorseentity : source.getWorld().getEntitiesWithinAABB(AbstractHorseEntity.class, new AxisAlignedBB(blockpos), (p_239790_0_) -> {
               return p_239790_0_.isAlive() && p_239790_0_.func_230276_fq_();
            })) {
               if (abstracthorseentity.isArmor(stack) && !abstracthorseentity.func_230277_fr_() && abstracthorseentity.isTame()) {
                  abstracthorseentity.replaceItemInInventory(401, stack.split(1));
                  this.func_239796_a_(true);
                  return stack;
               }
            }

            return super.dispenseStack(source, stack);
         }
      };
      DispenserBlock.registerDispenseBehavior(Items.LEATHER_HORSE_ARMOR, defaultdispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.IRON_HORSE_ARMOR, defaultdispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.GOLDEN_HORSE_ARMOR, defaultdispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.DIAMOND_HORSE_ARMOR, defaultdispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.WHITE_CARPET, defaultdispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.ORANGE_CARPET, defaultdispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.CYAN_CARPET, defaultdispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.BLUE_CARPET, defaultdispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.BROWN_CARPET, defaultdispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.BLACK_CARPET, defaultdispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.GRAY_CARPET, defaultdispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.GREEN_CARPET, defaultdispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.LIGHT_BLUE_CARPET, defaultdispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.LIGHT_GRAY_CARPET, defaultdispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.LIME_CARPET, defaultdispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.MAGENTA_CARPET, defaultdispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.PINK_CARPET, defaultdispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.PURPLE_CARPET, defaultdispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.RED_CARPET, defaultdispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.YELLOW_CARPET, defaultdispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.CHEST, new OptionalDispenseBehavior() {
         /**
          * Dispense the specified stack, play the dispense sound and spawn particles.
          */
         public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));

            for(AbstractChestedHorseEntity abstractchestedhorseentity : source.getWorld().getEntitiesWithinAABB(AbstractChestedHorseEntity.class, new AxisAlignedBB(blockpos), (p_239791_0_) -> {
               return p_239791_0_.isAlive() && !p_239791_0_.hasChest();
            })) {
               if (abstractchestedhorseentity.isTame() && abstractchestedhorseentity.replaceItemInInventory(499, stack)) {
                  stack.shrink(1);
                  this.func_239796_a_(true);
                  return stack;
               }
            }

            return super.dispenseStack(source, stack);
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.FIREWORK_ROCKET, new DefaultDispenseItemBehavior() {
         /**
          * Dispense the specified stack, play the dispense sound and spawn particles.
          */
         public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            Direction direction = source.getBlockState().get(DispenserBlock.FACING);
            FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(source.getWorld(), stack, source.getX(), source.getY(), source.getX(), true);
            IDispenseItemBehavior.func_239785_a_(source, fireworkrocketentity, direction);
            fireworkrocketentity.shoot((double)direction.getXOffset(), (double)direction.getYOffset(), (double)direction.getZOffset(), 0.5F, 1.0F);
            source.getWorld().addEntity(fireworkrocketentity);
            stack.shrink(1);
            return stack;
         }

         /**
          * Play the dispense sound from the specified block.
          */
         protected void playDispenseSound(IBlockSource source) {
            source.getWorld().playEvent(1004, source.getBlockPos(), 0);
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.FIRE_CHARGE, new DefaultDispenseItemBehavior() {
         /**
          * Dispense the specified stack, play the dispense sound and spawn particles.
          */
         public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            Direction direction = source.getBlockState().get(DispenserBlock.FACING);
            IPosition iposition = DispenserBlock.getDispensePosition(source);
            double d0 = iposition.getX() + (double)((float)direction.getXOffset() * 0.3F);
            double d1 = iposition.getY() + (double)((float)direction.getYOffset() * 0.3F);
            double d2 = iposition.getZ() + (double)((float)direction.getZOffset() * 0.3F);
            World world = source.getWorld();
            Random random = world.rand;
            double d3 = random.nextGaussian() * 0.05D + (double)direction.getXOffset();
            double d4 = random.nextGaussian() * 0.05D + (double)direction.getYOffset();
            double d5 = random.nextGaussian() * 0.05D + (double)direction.getZOffset();
            world.addEntity(Util.make(new SmallFireballEntity(world, d0, d1, d2, d3, d4, d5), (p_229425_1_) -> {
               p_229425_1_.setStack(stack);
            }));
            stack.shrink(1);
            return stack;
         }

         /**
          * Play the dispense sound from the specified block.
          */
         protected void playDispenseSound(IBlockSource source) {
            source.getWorld().playEvent(1018, source.getBlockPos(), 0);
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.OAK_BOAT, new DispenseBoatBehavior(BoatEntity.Type.OAK));
      DispenserBlock.registerDispenseBehavior(Items.SPRUCE_BOAT, new DispenseBoatBehavior(BoatEntity.Type.SPRUCE));
      DispenserBlock.registerDispenseBehavior(Items.BIRCH_BOAT, new DispenseBoatBehavior(BoatEntity.Type.BIRCH));
      DispenserBlock.registerDispenseBehavior(Items.JUNGLE_BOAT, new DispenseBoatBehavior(BoatEntity.Type.JUNGLE));
      DispenserBlock.registerDispenseBehavior(Items.DARK_OAK_BOAT, new DispenseBoatBehavior(BoatEntity.Type.DARK_OAK));
      DispenserBlock.registerDispenseBehavior(Items.ACACIA_BOAT, new DispenseBoatBehavior(BoatEntity.Type.ACACIA));
      IDispenseItemBehavior idispenseitembehavior1 = new DefaultDispenseItemBehavior() {
         private final DefaultDispenseItemBehavior field_239793_b_ = new DefaultDispenseItemBehavior();

         /**
          * Dispense the specified stack, play the dispense sound and spawn particles.
          */
         public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            BucketItem bucketitem = (BucketItem)stack.getItem();
            BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
            World world = source.getWorld();
            if (bucketitem.tryPlaceContainedLiquid((PlayerEntity)null, world, blockpos, (BlockRayTraceResult)null)) {
               bucketitem.onLiquidPlaced(world, stack, blockpos);
               return new ItemStack(Items.BUCKET);
            } else {
               return this.field_239793_b_.dispense(source, stack);
            }
         }
      };
      DispenserBlock.registerDispenseBehavior(Items.LAVA_BUCKET, idispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.WATER_BUCKET, idispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.SALMON_BUCKET, idispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.COD_BUCKET, idispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.PUFFERFISH_BUCKET, idispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.TROPICAL_FISH_BUCKET, idispenseitembehavior1);
      DispenserBlock.registerDispenseBehavior(Items.BUCKET, new DefaultDispenseItemBehavior() {
         private final DefaultDispenseItemBehavior field_239794_b_ = new DefaultDispenseItemBehavior();

         /**
          * Dispense the specified stack, play the dispense sound and spawn particles.
          */
         public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            IWorld iworld = source.getWorld();
            BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
            BlockState blockstate = iworld.getBlockState(blockpos);
            Block block = blockstate.getBlock();
            if (block instanceof IBucketPickupHandler) {
               Fluid fluid = ((IBucketPickupHandler)block).pickupFluid(iworld, blockpos, blockstate);
               if (!(fluid instanceof FlowingFluid)) {
                  return super.dispenseStack(source, stack);
               } else {
                  Item item = fluid.getFilledBucket();
                  stack.shrink(1);
                  if (stack.isEmpty()) {
                     return new ItemStack(item);
                  } else {
                     if (source.<DispenserTileEntity>getBlockTileEntity().addItemStack(new ItemStack(item)) < 0) {
                        this.field_239794_b_.dispense(source, new ItemStack(item));
                     }

                     return stack;
                  }
               }
            } else {
               return super.dispenseStack(source, stack);
            }
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.FLINT_AND_STEEL, new OptionalDispenseBehavior() {
         /**
          * Dispense the specified stack, play the dispense sound and spawn particles.
          */
         protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            World world = source.getWorld();
            this.func_239796_a_(true);
            Direction direction = source.getBlockState().get(DispenserBlock.FACING);
            BlockPos blockpos = source.getBlockPos().offset(direction);
            BlockState blockstate = world.getBlockState(blockpos);
            if (AbstractFireBlock.func_241465_a_(world, blockpos, direction)) {
               world.setBlockState(blockpos, AbstractFireBlock.func_235326_a_(world, blockpos));
            } else if (CampfireBlock.func_241470_h_(blockstate)) {
               world.setBlockState(blockpos, blockstate.with(BlockStateProperties.LIT, Boolean.valueOf(true)));
            } else if (blockstate.isFlammable(world, blockpos, source.getBlockState().get(DispenserBlock.FACING).getOpposite())) {
               blockstate.catchFire(world, blockpos, source.getBlockState().get(DispenserBlock.FACING).getOpposite(), null);
               if (blockstate.getBlock() instanceof TNTBlock)
               world.removeBlock(blockpos, false);
            } else {
               this.func_239796_a_(false);
            }

            if (this.func_239795_a_() && stack.attemptDamageItem(1, world.rand, (ServerPlayerEntity)null)) {
               stack.setCount(0);
            }

            return stack;
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.BONE_MEAL, new OptionalDispenseBehavior() {
         /**
          * Dispense the specified stack, play the dispense sound and spawn particles.
          */
         protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            this.func_239796_a_(true);
            World world = source.getWorld();
            BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
            if (!BoneMealItem.applyBonemeal(stack, world, blockpos) && !BoneMealItem.growSeagrass(stack, world, blockpos, (Direction)null)) {
               this.func_239796_a_(false);
            } else if (!world.isRemote) {
               world.playEvent(2005, blockpos, 0);
            }

            return stack;
         }
      });
      DispenserBlock.registerDispenseBehavior(Blocks.TNT, new DefaultDispenseItemBehavior() {
         /**
          * Dispense the specified stack, play the dispense sound and spawn particles.
          */
         protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            World world = source.getWorld();
            BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
            TNTEntity tntentity = new TNTEntity(world, (double)blockpos.getX() + 0.5D, (double)blockpos.getY(), (double)blockpos.getZ() + 0.5D, (LivingEntity)null);
            world.addEntity(tntentity);
            world.playSound((PlayerEntity)null, tntentity.getPosX(), tntentity.getPosY(), tntentity.getPosZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            stack.shrink(1);
            return stack;
         }
      });
      IDispenseItemBehavior idispenseitembehavior = new OptionalDispenseBehavior() {
         /**
          * Dispense the specified stack, play the dispense sound and spawn particles.
          */
         protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            this.func_239796_a_(ArmorItem.func_226626_a_(source, stack));
            return stack;
         }
      };
      DispenserBlock.registerDispenseBehavior(Items.CREEPER_HEAD, idispenseitembehavior);
      DispenserBlock.registerDispenseBehavior(Items.ZOMBIE_HEAD, idispenseitembehavior);
      DispenserBlock.registerDispenseBehavior(Items.DRAGON_HEAD, idispenseitembehavior);
      DispenserBlock.registerDispenseBehavior(Items.SKELETON_SKULL, idispenseitembehavior);
      DispenserBlock.registerDispenseBehavior(Items.PLAYER_HEAD, idispenseitembehavior);
      DispenserBlock.registerDispenseBehavior(Items.WITHER_SKELETON_SKULL, new OptionalDispenseBehavior() {
         /**
          * Dispense the specified stack, play the dispense sound and spawn particles.
          */
         protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            World world = source.getWorld();
            Direction direction = source.getBlockState().get(DispenserBlock.FACING);
            BlockPos blockpos = source.getBlockPos().offset(direction);
            if (world.isAirBlock(blockpos) && WitherSkeletonSkullBlock.canSpawnMob(world, blockpos, stack)) {
               world.setBlockState(blockpos, Blocks.WITHER_SKELETON_SKULL.getDefaultState().with(SkullBlock.ROTATION, Integer.valueOf(direction.getAxis() == Direction.Axis.Y ? 0 : direction.getOpposite().getHorizontalIndex() * 4)), 3);
               TileEntity tileentity = world.getTileEntity(blockpos);
               if (tileentity instanceof SkullTileEntity) {
                  WitherSkeletonSkullBlock.checkWitherSpawn(world, blockpos, (SkullTileEntity)tileentity);
               }

               stack.shrink(1);
               this.func_239796_a_(true);
            } else {
               this.func_239796_a_(ArmorItem.func_226626_a_(source, stack));
            }

            return stack;
         }
      });
      DispenserBlock.registerDispenseBehavior(Blocks.CARVED_PUMPKIN, new OptionalDispenseBehavior() {
         /**
          * Dispense the specified stack, play the dispense sound and spawn particles.
          */
         protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            World world = source.getWorld();
            BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
            CarvedPumpkinBlock carvedpumpkinblock = (CarvedPumpkinBlock)Blocks.CARVED_PUMPKIN;
            if (world.isAirBlock(blockpos) && carvedpumpkinblock.canDispenserPlace(world, blockpos)) {
               if (!world.isRemote) {
                  world.setBlockState(blockpos, carvedpumpkinblock.getDefaultState(), 3);
               }

               stack.shrink(1);
               this.func_239796_a_(true);
            } else {
               this.func_239796_a_(ArmorItem.func_226626_a_(source, stack));
            }

            return stack;
         }
      });
      DispenserBlock.registerDispenseBehavior(Blocks.SHULKER_BOX.asItem(), new ShulkerBoxDispenseBehavior());

      for(DyeColor dyecolor : DyeColor.values()) {
         DispenserBlock.registerDispenseBehavior(ShulkerBoxBlock.getBlockByColor(dyecolor).asItem(), new ShulkerBoxDispenseBehavior());
      }

      DispenserBlock.registerDispenseBehavior(Items.GLASS_BOTTLE.asItem(), new OptionalDispenseBehavior() {
         private final DefaultDispenseItemBehavior field_229423_c_ = new DefaultDispenseItemBehavior();

         private ItemStack func_229424_a_(IBlockSource p_229424_1_, ItemStack p_229424_2_, ItemStack p_229424_3_) {
            p_229424_2_.shrink(1);
            if (p_229424_2_.isEmpty()) {
               return p_229424_3_.copy();
            } else {
               if (p_229424_1_.<DispenserTileEntity>getBlockTileEntity().addItemStack(p_229424_3_.copy()) < 0) {
                  this.field_229423_c_.dispense(p_229424_1_, p_229424_3_.copy());
               }

               return p_229424_2_;
            }
         }

         /**
          * Dispense the specified stack, play the dispense sound and spawn particles.
          */
         public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            this.func_239796_a_(false);
            ServerWorld serverworld = source.getWorld();
            BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
            BlockState blockstate = serverworld.getBlockState(blockpos);
            if (blockstate.func_235715_a_(BlockTags.BEEHIVES, (p_239787_0_) -> {
               return p_239787_0_.func_235901_b_(BeehiveBlock.HONEY_LEVEL);
            }) && blockstate.get(BeehiveBlock.HONEY_LEVEL) >= 5) {
               ((BeehiveBlock)blockstate.getBlock()).takeHoney(serverworld, blockstate, blockpos, (PlayerEntity)null, BeehiveTileEntity.State.BEE_RELEASED);
               this.func_239796_a_(true);
               return this.func_229424_a_(source, stack, new ItemStack(Items.HONEY_BOTTLE));
            } else if (serverworld.getFluidState(blockpos).isTagged(FluidTags.WATER)) {
               this.func_239796_a_(true);
               return this.func_229424_a_(source, stack, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER));
            } else {
               return super.dispenseStack(source, stack);
            }
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.GLOWSTONE, new OptionalDispenseBehavior() {
         /**
          * Dispense the specified stack, play the dispense sound and spawn particles.
          */
         public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            Direction direction = source.getBlockState().get(DispenserBlock.FACING);
            BlockPos blockpos = source.getBlockPos().offset(direction);
            World world = source.getWorld();
            BlockState blockstate = world.getBlockState(blockpos);
            this.func_239796_a_(true);
            if (blockstate.isIn(Blocks.field_235400_nj_)) {
               if (blockstate.get(RespawnAnchorBlock.field_235559_a_) != 4) {
                  RespawnAnchorBlock.func_235564_a_(world, blockpos, blockstate);
                  stack.shrink(1);
               } else {
                  this.func_239796_a_(false);
               }

               return stack;
            } else {
               return super.dispenseStack(source, stack);
            }
         }
      });
      DispenserBlock.registerDispenseBehavior(Items.SHEARS.asItem(), new BeehiveDispenseBehavior());
   }

   static void func_239785_a_(IBlockSource p_239785_0_, Entity p_239785_1_, Direction p_239785_2_) {
      p_239785_1_.setPosition(p_239785_0_.getX() + (double)p_239785_2_.getXOffset() * (0.5000099999997474D - (double)p_239785_1_.getWidth() / 2.0D), p_239785_0_.getY() + (double)p_239785_2_.getYOffset() * (0.5000099999997474D - (double)p_239785_1_.getHeight() / 2.0D) - (double)p_239785_1_.getHeight() / 2.0D, p_239785_0_.getZ() + (double)p_239785_2_.getZOffset() * (0.5000099999997474D - (double)p_239785_1_.getWidth() / 2.0D));
   }
}