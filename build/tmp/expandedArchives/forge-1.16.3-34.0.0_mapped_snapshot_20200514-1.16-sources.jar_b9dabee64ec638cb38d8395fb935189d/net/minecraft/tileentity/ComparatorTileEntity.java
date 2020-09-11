package net.minecraft.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;

public class ComparatorTileEntity extends TileEntity {
   private int outputSignal;

   public ComparatorTileEntity() {
      super(TileEntityType.COMPARATOR);
   }

   public CompoundNBT write(CompoundNBT compound) {
      super.write(compound);
      compound.putInt("OutputSignal", this.outputSignal);
      return compound;
   }

   public void func_230337_a_(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
      super.func_230337_a_(p_230337_1_, p_230337_2_);
      this.outputSignal = p_230337_2_.getInt("OutputSignal");
   }

   public int getOutputSignal() {
      return this.outputSignal;
   }

   public void setOutputSignal(int outputSignalIn) {
      this.outputSignal = outputSignalIn;
   }
}