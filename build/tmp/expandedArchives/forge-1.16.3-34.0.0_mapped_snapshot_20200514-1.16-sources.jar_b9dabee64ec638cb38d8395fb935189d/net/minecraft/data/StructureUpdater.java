package net.minecraft.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.DefaultTypeReferences;
import net.minecraft.world.gen.feature.template.Template;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructureUpdater implements SNBTToNBTConverter.ITransformer {
   private static final Logger field_240518_a_ = LogManager.getLogger();

   public CompoundNBT func_225371_a(String p_225371_1_, CompoundNBT p_225371_2_) {
      return p_225371_1_.startsWith("data/minecraft/structures/") ? func_240519_b_(p_225371_1_, func_225372_a(p_225371_2_)) : p_225371_2_;
   }

   private static CompoundNBT func_225372_a(CompoundNBT p_225372_0_) {
      if (!p_225372_0_.contains("DataVersion", 99)) {
         p_225372_0_.putInt("DataVersion", 500);
      }

      return p_225372_0_;
   }

   private static CompoundNBT func_240519_b_(String p_240519_0_, CompoundNBT p_240519_1_) {
      Template template = new Template();
      int i = p_240519_1_.getInt("DataVersion");
      int j = 2532;
      if (i < 2532) {
         field_240518_a_.warn("SNBT Too old, do not forget to update: " + i + " < " + 2532 + ": " + p_240519_0_);
      }

      CompoundNBT compoundnbt = NBTUtil.update(DataFixesManager.getDataFixer(), DefaultTypeReferences.STRUCTURE, p_240519_1_, i);
      template.read(compoundnbt);
      return template.writeToNBT(new CompoundNBT());
   }
}