package net.minecraft.world.storage;

import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import net.minecraft.util.SharedConstants;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VersionData {
   private final int field_237318_a_;
   private final long field_237319_b_;
   private final String field_237320_c_;
   private final int field_237321_d_;
   private final boolean field_237322_e_;

   public VersionData(int p_i232156_1_, long p_i232156_2_, String p_i232156_4_, int p_i232156_5_, boolean p_i232156_6_) {
      this.field_237318_a_ = p_i232156_1_;
      this.field_237319_b_ = p_i232156_2_;
      this.field_237320_c_ = p_i232156_4_;
      this.field_237321_d_ = p_i232156_5_;
      this.field_237322_e_ = p_i232156_6_;
   }

   public static VersionData func_237324_a_(Dynamic<?> p_237324_0_) {
      int i = p_237324_0_.get("version").asInt(0);
      long j = p_237324_0_.get("LastPlayed").asLong(0L);
      OptionalDynamic<?> optionaldynamic = p_237324_0_.get("Version");
      return optionaldynamic.result().isPresent() ? new VersionData(i, j, optionaldynamic.get("Name").asString(SharedConstants.getVersion().getName()), optionaldynamic.get("Id").asInt(SharedConstants.getVersion().getWorldVersion()), optionaldynamic.get("Snapshot").asBoolean(!SharedConstants.getVersion().isStable())) : new VersionData(i, j, "", 0, false);
   }

   public int func_237323_a_() {
      return this.field_237318_a_;
   }

   public long func_237325_b_() {
      return this.field_237319_b_;
   }

   @OnlyIn(Dist.CLIENT)
   public String func_237326_c_() {
      return this.field_237320_c_;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_237327_d_() {
      return this.field_237321_d_;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_237328_e_() {
      return this.field_237322_e_;
   }
}