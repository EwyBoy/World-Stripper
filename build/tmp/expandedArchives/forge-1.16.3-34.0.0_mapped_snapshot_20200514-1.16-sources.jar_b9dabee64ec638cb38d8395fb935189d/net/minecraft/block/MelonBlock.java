package net.minecraft.block;

public class MelonBlock extends StemGrownBlock {
   public MelonBlock(AbstractBlock.Properties builder) {
      super(builder);
   }

   public StemBlock getStem() {
      return (StemBlock)Blocks.MELON_STEM;
   }

   public AttachedStemBlock getAttachedStem() {
      return (AttachedStemBlock)Blocks.ATTACHED_MELON_STEM;
   }
}