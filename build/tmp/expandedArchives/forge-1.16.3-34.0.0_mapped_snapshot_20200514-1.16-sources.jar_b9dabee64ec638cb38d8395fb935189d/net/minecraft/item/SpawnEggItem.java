package net.minecraft.item;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.AbstractSpawner;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpawnEggItem extends Item {
   private static final Map<EntityType<?>, SpawnEggItem> EGGS = Maps.newIdentityHashMap();
   private final int primaryColor;
   private final int secondaryColor;
   private final EntityType<?> typeIn;

   public SpawnEggItem(EntityType<?> typeIn, int primaryColorIn, int secondaryColorIn, Item.Properties builder) {
      super(builder);
      this.typeIn = typeIn;
      this.primaryColor = primaryColorIn;
      this.secondaryColor = secondaryColorIn;
      EGGS.put(typeIn, this);
   }

   /**
    * Called when this item is used when targetting a Block
    */
   public ActionResultType onItemUse(ItemUseContext context) {
      World world = context.getWorld();
      if (!(world instanceof ServerWorld)) {
         return ActionResultType.SUCCESS;
      } else {
         ItemStack itemstack = context.getItem();
         BlockPos blockpos = context.getPos();
         Direction direction = context.getFace();
         BlockState blockstate = world.getBlockState(blockpos);
         if (blockstate.isIn(Blocks.SPAWNER)) {
            TileEntity tileentity = world.getTileEntity(blockpos);
            if (tileentity instanceof MobSpawnerTileEntity) {
               AbstractSpawner abstractspawner = ((MobSpawnerTileEntity)tileentity).getSpawnerBaseLogic();
               EntityType<?> entitytype1 = this.getType(itemstack.getTag());
               abstractspawner.setEntityType(entitytype1);
               tileentity.markDirty();
               world.notifyBlockUpdate(blockpos, blockstate, blockstate, 3);
               itemstack.shrink(1);
               return ActionResultType.CONSUME;
            }
         }

         BlockPos blockpos1;
         if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
            blockpos1 = blockpos;
         } else {
            blockpos1 = blockpos.offset(direction);
         }

         EntityType<?> entitytype = this.getType(itemstack.getTag());
         if (entitytype.spawn((ServerWorld)world, itemstack, context.getPlayer(), blockpos1, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP) != null) {
            itemstack.shrink(1);
         }

         return ActionResultType.CONSUME;
      }
   }

   /**
    * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
    * {@link #onItemUse}.
    */
   public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
      ItemStack itemstack = playerIn.getHeldItem(handIn);
      RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
      if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
         return ActionResult.resultPass(itemstack);
      } else if (!(worldIn instanceof ServerWorld)) {
         return ActionResult.resultSuccess(itemstack);
      } else {
         BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
         BlockPos blockpos = blockraytraceresult.getPos();
         if (!(worldIn.getBlockState(blockpos).getBlock() instanceof FlowingFluidBlock)) {
            return ActionResult.resultPass(itemstack);
         } else if (worldIn.isBlockModifiable(playerIn, blockpos) && playerIn.canPlayerEdit(blockpos, blockraytraceresult.getFace(), itemstack)) {
            EntityType<?> entitytype = this.getType(itemstack.getTag());
            if (entitytype.spawn((ServerWorld)worldIn, itemstack, playerIn, blockpos, SpawnReason.SPAWN_EGG, false, false) == null) {
               return ActionResult.resultPass(itemstack);
            } else {
               if (!playerIn.abilities.isCreativeMode) {
                  itemstack.shrink(1);
               }

               playerIn.addStat(Stats.ITEM_USED.get(this));
               return ActionResult.resultConsume(itemstack);
            }
         } else {
            return ActionResult.resultFail(itemstack);
         }
      }
   }

   public boolean hasType(@Nullable CompoundNBT p_208077_1_, EntityType<?> type) {
      return Objects.equals(this.getType(p_208077_1_), type);
   }

   @OnlyIn(Dist.CLIENT)
   public int getColor(int tintIndex) {
      return tintIndex == 0 ? this.primaryColor : this.secondaryColor;
   }

   @Nullable
   @OnlyIn(Dist.CLIENT)
   public static SpawnEggItem getEgg(@Nullable EntityType<?> type) {
      return EGGS.get(type);
   }

   public static Iterable<SpawnEggItem> getEggs() {
      return Iterables.unmodifiableIterable(EGGS.values());
   }

   public EntityType<?> getType(@Nullable CompoundNBT p_208076_1_) {
      if (p_208076_1_ != null && p_208076_1_.contains("EntityTag", 10)) {
         CompoundNBT compoundnbt = p_208076_1_.getCompound("EntityTag");
         if (compoundnbt.contains("id", 8)) {
            return EntityType.byKey(compoundnbt.getString("id")).orElse(this.typeIn);
         }
      }

      return this.typeIn;
   }

   public Optional<MobEntity> func_234809_a_(PlayerEntity p_234809_1_, MobEntity p_234809_2_, EntityType<? extends MobEntity> p_234809_3_, ServerWorld p_234809_4_, Vector3d p_234809_5_, ItemStack p_234809_6_) {
      if (!this.hasType(p_234809_6_.getTag(), p_234809_3_)) {
         return Optional.empty();
      } else {
         MobEntity mobentity;
         if (p_234809_2_ instanceof AgeableEntity) {
            mobentity = ((AgeableEntity)p_234809_2_).func_241840_a(p_234809_4_, (AgeableEntity)p_234809_2_);
         } else {
            mobentity = p_234809_3_.create(p_234809_4_);
         }

         if (mobentity == null) {
            return Optional.empty();
         } else {
            mobentity.setChild(true);
            if (!mobentity.isChild()) {
               return Optional.empty();
            } else {
               mobentity.setLocationAndAngles(p_234809_5_.getX(), p_234809_5_.getY(), p_234809_5_.getZ(), 0.0F, 0.0F);
               p_234809_4_.func_242417_l(mobentity);
               if (p_234809_6_.hasDisplayName()) {
                  mobentity.setCustomName(p_234809_6_.getDisplayName());
               }

               if (!p_234809_1_.abilities.isCreativeMode) {
                  p_234809_6_.shrink(1);
               }

               return Optional.of(mobentity);
            }
         }
      }
   }
}