package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SimpleSound extends LocatableSound {
   public SimpleSound(SoundEvent soundIn, SoundCategory categoryIn, float volumeIn, float pitchIn, BlockPos pos) {
      this(soundIn, categoryIn, volumeIn, pitchIn, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D);
   }

   public static SimpleSound master(SoundEvent soundIn, float pitchIn) {
      return master(soundIn, pitchIn, 0.25F);
   }

   public static SimpleSound master(SoundEvent soundIn, float pitchIn, float volumeIn) {
      return new SimpleSound(soundIn.getName(), SoundCategory.MASTER, volumeIn, pitchIn, false, 0, ISound.AttenuationType.NONE, 0.0D, 0.0D, 0.0D, true);
   }

   public static SimpleSound music(SoundEvent soundIn) {
      return new SimpleSound(soundIn.getName(), SoundCategory.MUSIC, 1.0F, 1.0F, false, 0, ISound.AttenuationType.NONE, 0.0D, 0.0D, 0.0D, true);
   }

   public static SimpleSound record(SoundEvent soundIn, double xIn, double zIn, double p_184372_5_) {
      return new SimpleSound(soundIn, SoundCategory.RECORDS, 4.0F, 1.0F, false, 0, ISound.AttenuationType.LINEAR, xIn, zIn, p_184372_5_);
   }

   public static SimpleSound func_239532_b_(SoundEvent p_239532_0_, float p_239532_1_, float p_239532_2_) {
      return new SimpleSound(p_239532_0_.getName(), SoundCategory.AMBIENT, p_239532_2_, p_239532_1_, false, 0, ISound.AttenuationType.NONE, 0.0D, 0.0D, 0.0D, true);
   }

   public static SimpleSound func_239530_b_(SoundEvent p_239530_0_) {
      return func_239532_b_(p_239530_0_, 1.0F, 1.0F);
   }

   public static SimpleSound func_239531_b_(SoundEvent p_239531_0_, double p_239531_1_, double p_239531_3_, double p_239531_5_) {
      return new SimpleSound(p_239531_0_, SoundCategory.AMBIENT, 1.0F, 1.0F, false, 0, ISound.AttenuationType.LINEAR, p_239531_1_, p_239531_3_, p_239531_5_);
   }

   public SimpleSound(SoundEvent p_i232490_1_, SoundCategory p_i232490_2_, float p_i232490_3_, float p_i232490_4_, double p_i232490_5_, double p_i232490_7_, double p_i232490_9_) {
      this(p_i232490_1_, p_i232490_2_, p_i232490_3_, p_i232490_4_, false, 0, ISound.AttenuationType.LINEAR, p_i232490_5_, p_i232490_7_, p_i232490_9_);
   }

   private SimpleSound(SoundEvent p_i232491_1_, SoundCategory p_i232491_2_, float p_i232491_3_, float p_i232491_4_, boolean p_i232491_5_, int p_i232491_6_, ISound.AttenuationType p_i232491_7_, double p_i232491_8_, double p_i232491_10_, double p_i232491_12_) {
      this(p_i232491_1_.getName(), p_i232491_2_, p_i232491_3_, p_i232491_4_, p_i232491_5_, p_i232491_6_, p_i232491_7_, p_i232491_8_, p_i232491_10_, p_i232491_12_, false);
   }

   public SimpleSound(ResourceLocation p_i232492_1_, SoundCategory p_i232492_2_, float p_i232492_3_, float p_i232492_4_, boolean p_i232492_5_, int p_i232492_6_, ISound.AttenuationType p_i232492_7_, double p_i232492_8_, double p_i232492_10_, double p_i232492_12_, boolean p_i232492_14_) {
      super(p_i232492_1_, p_i232492_2_);
      this.volume = p_i232492_3_;
      this.pitch = p_i232492_4_;
      this.x = p_i232492_8_;
      this.y = p_i232492_10_;
      this.z = p_i232492_12_;
      this.repeat = p_i232492_5_;
      this.repeatDelay = p_i232492_6_;
      this.attenuationType = p_i232492_7_;
      this.global = p_i232492_14_;
   }
}