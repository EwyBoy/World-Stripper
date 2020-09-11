package net.minecraft.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.IClearable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class JukeboxTileEntity extends TileEntity implements IClearable {
   private ItemStack record = ItemStack.EMPTY;

   public JukeboxTileEntity() {
      super(TileEntityType.JUKEBOX);
   }

   public void func_230337_a_(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
      super.func_230337_a_(p_230337_1_, p_230337_2_);
      if (p_230337_2_.contains("RecordItem", 10)) {
         this.setRecord(ItemStack.read(p_230337_2_.getCompound("RecordItem")));
      }

   }

   public CompoundNBT write(CompoundNBT compound) {
      super.write(compound);
      if (!this.getRecord().isEmpty()) {
         compound.put("RecordItem", this.getRecord().write(new CompoundNBT()));
      }

      return compound;
   }

   public ItemStack getRecord() {
      return this.record;
   }

   public void setRecord(ItemStack p_195535_1_) {
      this.record = p_195535_1_;
      this.markDirty();
   }

   public void clear() {
      this.setRecord(ItemStack.EMPTY);
   }
}