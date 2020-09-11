package net.minecraft.block;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SilverfishBlock extends Block {
   private final Block mimickedBlock;
   private static final Map<Block, Block> field_196470_b = Maps.newIdentityHashMap();

   public SilverfishBlock(Block blockIn, AbstractBlock.Properties properties) {
      super(properties);
      this.mimickedBlock = blockIn;
      field_196470_b.put(blockIn, this);
   }

   public Block getMimickedBlock() {
      return this.mimickedBlock;
   }

   public static boolean canContainSilverfish(BlockState state) {
      return field_196470_b.containsKey(state.getBlock());
   }

   private void func_235505_a_(ServerWorld p_235505_1_, BlockPos p_235505_2_) {
      SilverfishEntity silverfishentity = EntityType.SILVERFISH.create(p_235505_1_);
      silverfishentity.setLocationAndAngles((double)p_235505_2_.getX() + 0.5D, (double)p_235505_2_.getY(), (double)p_235505_2_.getZ() + 0.5D, 0.0F, 0.0F);
      p_235505_1_.addEntity(silverfishentity);
      silverfishentity.spawnExplosionParticle();
   }

   /**
    * Perform side-effects from block dropping, such as creating silverfish
    */
   public void spawnAdditionalDrops(BlockState state, ServerWorld worldIn, BlockPos pos, ItemStack stack) {
      super.spawnAdditionalDrops(state, worldIn, pos, stack);
      if (worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
         this.func_235505_a_(worldIn, pos);
      }

   }

   /**
    * Called when this Block is destroyed by an Explosion
    */
   public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
      if (worldIn instanceof ServerWorld) {
         this.func_235505_a_((ServerWorld)worldIn, pos);
      }

   }

   public static BlockState infest(Block blockIn) {
      return field_196470_b.get(blockIn).getDefaultState();
   }
}