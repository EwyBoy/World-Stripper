package net.minecraft.state.properties;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum StructureMode implements IStringSerializable {
   SAVE("save"),
   LOAD("load"),
   CORNER("corner"),
   DATA("data");

   private final String name;
   private final ITextComponent field_242702_f;

   private StructureMode(String name) {
      this.name = name;
      this.field_242702_f = new TranslationTextComponent("structure_block.mode_info." + name);
   }

   public String func_176610_l() {
      return this.name;
   }

   @OnlyIn(Dist.CLIENT)
   public ITextComponent func_242703_b() {
      return this.field_242702_f;
   }
}