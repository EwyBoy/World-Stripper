package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.passive.fish.TropicalFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FishBucketItem extends BucketItem {
   private final EntityType<?> fishType;

   @Deprecated
   public FishBucketItem(EntityType<?> fishTypeIn, Fluid p_i49022_2_, Item.Properties builder) {
      super(p_i49022_2_, builder);
      this.fishType = fishTypeIn;
      this.fishTypeSupplier = () -> fishTypeIn;
   }

   public FishBucketItem(java.util.function.Supplier<? extends EntityType<?>> fishTypeIn, java.util.function.Supplier<? extends Fluid> p_i49022_2_, Item.Properties builder) {
      super(p_i49022_2_, builder);
      this.fishType = null;
      this.fishTypeSupplier = fishTypeIn;
   }

   public void onLiquidPlaced(World worldIn, ItemStack p_203792_2_, BlockPos pos) {
      if (worldIn instanceof ServerWorld) {
         this.placeFish((ServerWorld)worldIn, p_203792_2_, pos);
      }

   }

   protected void playEmptySound(@Nullable PlayerEntity player, IWorld worldIn, BlockPos pos) {
      worldIn.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
   }

   private void placeFish(ServerWorld worldIn, ItemStack p_205357_2_, BlockPos pos) {
      Entity entity = this.fishType.spawn(worldIn, p_205357_2_, (PlayerEntity)null, pos, SpawnReason.BUCKET, true, false);
      if (entity != null) {
         ((AbstractFishEntity)entity).setFromBucket(true);
      }

   }

   /**
    * allows items to add custom lines of information to the mouseover description
    */
   @OnlyIn(Dist.CLIENT)
   public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
      if (this.fishType == EntityType.TROPICAL_FISH) {
         CompoundNBT compoundnbt = stack.getTag();
         if (compoundnbt != null && compoundnbt.contains("BucketVariantTag", 3)) {
            int i = compoundnbt.getInt("BucketVariantTag");
            TextFormatting[] atextformatting = new TextFormatting[]{TextFormatting.ITALIC, TextFormatting.GRAY};
            String s = "color.minecraft." + TropicalFishEntity.func_212326_d(i);
            String s1 = "color.minecraft." + TropicalFishEntity.func_212323_p(i);

            for(int j = 0; j < TropicalFishEntity.SPECIAL_VARIANTS.length; ++j) {
               if (i == TropicalFishEntity.SPECIAL_VARIANTS[j]) {
                  tooltip.add((new TranslationTextComponent(TropicalFishEntity.func_212324_b(j))).func_240701_a_(atextformatting));
                  return;
               }
            }

            tooltip.add((new TranslationTextComponent(TropicalFishEntity.func_212327_q(i))).func_240701_a_(atextformatting));
            IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent(s);
            if (!s.equals(s1)) {
               iformattabletextcomponent.func_240702_b_(", ").func_230529_a_(new TranslationTextComponent(s1));
            }

            iformattabletextcomponent.func_240701_a_(atextformatting);
            tooltip.add(iformattabletextcomponent);
         }
      }

   }

   private final java.util.function.Supplier<? extends EntityType<?>> fishTypeSupplier;
   protected EntityType<?> getFishType() {
       return fishTypeSupplier.get();
   }
}