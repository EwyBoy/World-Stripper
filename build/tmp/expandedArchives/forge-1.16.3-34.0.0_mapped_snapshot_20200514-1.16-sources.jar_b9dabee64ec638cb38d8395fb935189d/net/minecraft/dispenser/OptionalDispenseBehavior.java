package net.minecraft.dispenser;

public abstract class OptionalDispenseBehavior extends DefaultDispenseItemBehavior {
   private boolean successful = true;

   public boolean func_239795_a_() {
      return this.successful;
   }

   public void func_239796_a_(boolean p_239796_1_) {
      this.successful = p_239796_1_;
   }

   /**
    * Play the dispense sound from the specified block.
    */
   protected void playDispenseSound(IBlockSource source) {
      source.getWorld().playEvent(this.func_239795_a_() ? 1000 : 1001, source.getBlockPos(), 0);
   }
}