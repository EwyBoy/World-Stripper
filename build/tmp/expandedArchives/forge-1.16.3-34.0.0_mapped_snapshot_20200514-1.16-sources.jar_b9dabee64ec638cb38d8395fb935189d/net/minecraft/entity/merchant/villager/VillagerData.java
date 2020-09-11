package net.minecraft.entity.merchant.villager;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VillagerData {
   private static final int[] field_221136_a = new int[]{0, 10, 70, 150, 250};
   public static final Codec<VillagerData> field_234554_a_ = RecordCodecBuilder.create((p_234556_0_) -> {
      return p_234556_0_.group(Registry.VILLAGER_TYPE.fieldOf("type").orElseGet(() -> {
         return VillagerType.PLAINS;
      }).forGetter((p_234558_0_) -> {
         return p_234558_0_.type;
      }), Registry.VILLAGER_PROFESSION.fieldOf("profession").orElseGet(() -> {
         return VillagerProfession.NONE;
      }).forGetter((p_234557_0_) -> {
         return p_234557_0_.profession;
      }), Codec.INT.fieldOf("level").orElse(1).forGetter((p_234555_0_) -> {
         return p_234555_0_.level;
      })).apply(p_234556_0_, VillagerData::new);
   });
   private final VillagerType type;
   private final VillagerProfession profession;
   private final int level;

   public VillagerData(VillagerType type, VillagerProfession profession, int level) {
      this.type = type;
      this.profession = profession;
      this.level = Math.max(1, level);
   }

   public VillagerType getType() {
      return this.type;
   }

   public VillagerProfession getProfession() {
      return this.profession;
   }

   public int getLevel() {
      return this.level;
   }

   public VillagerData withType(VillagerType typeIn) {
      return new VillagerData(typeIn, this.profession, this.level);
   }

   public VillagerData withProfession(VillagerProfession professionIn) {
      return new VillagerData(this.type, professionIn, this.level);
   }

   public VillagerData withLevel(int levelIn) {
      return new VillagerData(this.type, this.profession, levelIn);
   }

   @OnlyIn(Dist.CLIENT)
   public static int func_221133_b(int p_221133_0_) {
      return func_221128_d(p_221133_0_) ? field_221136_a[p_221133_0_ - 1] : 0;
   }

   public static int func_221127_c(int p_221127_0_) {
      return func_221128_d(p_221127_0_) ? field_221136_a[p_221127_0_] : 0;
   }

   public static boolean func_221128_d(int p_221128_0_) {
      return p_221128_0_ >= 1 && p_221128_0_ < 5;
   }
}