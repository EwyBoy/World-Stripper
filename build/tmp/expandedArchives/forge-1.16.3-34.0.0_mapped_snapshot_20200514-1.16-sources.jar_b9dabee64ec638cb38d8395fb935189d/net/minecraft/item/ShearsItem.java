package net.minecraft.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ShearsItem extends Item {
   public ShearsItem(Item.Properties builder) {
      super(builder);
   }

   /**
    * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
    */
   public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
      if (!worldIn.isRemote && !state.getBlock().isIn(BlockTags.field_232872_am_)) {
         stack.damageItem(1, entityLiving, (p_220036_0_) -> {
            p_220036_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
         });
      }

      return !state.func_235714_a_(BlockTags.LEAVES) && !state.isIn(Blocks.COBWEB) && !state.isIn(Blocks.GRASS) && !state.isIn(Blocks.FERN) && !state.isIn(Blocks.DEAD_BUSH) && !state.isIn(Blocks.VINE) && !state.isIn(Blocks.TRIPWIRE) && !state.func_235714_a_(BlockTags.WOOL) ? super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving) : true;
   }

   /**
    * Check whether this Item can harvest the given Block
    */
   public boolean canHarvestBlock(BlockState blockIn) {
      return blockIn.isIn(Blocks.COBWEB) || blockIn.isIn(Blocks.REDSTONE_WIRE) || blockIn.isIn(Blocks.TRIPWIRE);
   }

   public float getDestroySpeed(ItemStack stack, BlockState state) {
      if (!state.isIn(Blocks.COBWEB) && !state.func_235714_a_(BlockTags.LEAVES)) {
         return state.func_235714_a_(BlockTags.WOOL) ? 5.0F : super.getDestroySpeed(stack, state);
      } else {
         return 15.0F;
      }
   }

   /**
    * Returns true if the item can be used on the given entity, e.g. shears on sheep.
    */
   @Override
   public net.minecraft.util.ActionResultType itemInteractionForEntity(ItemStack stack, net.minecraft.entity.player.PlayerEntity playerIn, LivingEntity entity, net.minecraft.util.Hand hand) {
      if (entity.world.isRemote) return net.minecraft.util.ActionResultType.PASS;
      if (entity instanceof net.minecraftforge.common.IForgeShearable) {
          net.minecraftforge.common.IForgeShearable target = (net.minecraftforge.common.IForgeShearable)entity;
         BlockPos pos = new BlockPos(entity.getPosX(), entity.getPosY(), entity.getPosZ());
         if (target.isShearable(stack, entity.world, pos)) {
            java.util.List<ItemStack> drops = target.onSheared(playerIn, stack, entity.world, pos,
                    net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel(net.minecraft.enchantment.Enchantments.FORTUNE, stack));
            java.util.Random rand = new java.util.Random();
            drops.forEach(d -> {
               net.minecraft.entity.item.ItemEntity ent = entity.entityDropItem(d, 1.0F);
               ent.setMotion(ent.getMotion().add((double)((rand.nextFloat() - rand.nextFloat()) * 0.1F), (double)(rand.nextFloat() * 0.05F), (double)((rand.nextFloat() - rand.nextFloat()) * 0.1F)));
            });
            stack.damageItem(1, entity, e -> e.sendBreakAnimation(hand));
         }
         return net.minecraft.util.ActionResultType.SUCCESS;
      }
      return net.minecraft.util.ActionResultType.PASS;
   }
}