package net.minecraft.entity;

import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.util.IStringSerializable;

public enum EntityClassification implements IStringSerializable, net.minecraftforge.common.IExtensibleEnum {
   MONSTER("monster", 70, false, false, 128),
   CREATURE("creature", 10, true, true, 128),
   AMBIENT("ambient", 15, true, false, 128),
   WATER_CREATURE("water_creature", 5, true, false, 128),
   WATER_AMBIENT("water_ambient", 20, true, false, 64),
   MISC("misc", -1, true, true, 128);

   public static final Codec<EntityClassification> field_233667_g_ = IStringSerializable.func_233023_a_(EntityClassification::values, EntityClassification::func_233670_a_);
   private static final Map<String, EntityClassification> VALUES_MAP = Arrays.stream(values()).collect(Collectors.toMap(EntityClassification::getName, (p_220362_0_) -> {
      return p_220362_0_;
   }));
   private final int maxNumberOfCreature;
   private final boolean isPeacefulCreature;
   private final boolean isAnimal;
   private final String name;
   private final int field_233668_m_ = 32;
   private final int field_233669_n_;

   private EntityClassification(String p_i231492_3_, int p_i231492_4_, boolean p_i231492_5_, boolean p_i231492_6_, int p_i231492_7_) {
      this.name = p_i231492_3_;
      this.maxNumberOfCreature = p_i231492_4_;
      this.isPeacefulCreature = p_i231492_5_;
      this.isAnimal = p_i231492_6_;
      this.field_233669_n_ = p_i231492_7_;
   }

   public String getName() {
      return this.name;
   }

   public String func_176610_l() {
      return this.name;
   }

   public static EntityClassification func_233670_a_(String p_233670_0_) {
      return VALUES_MAP.get(p_233670_0_);
   }

   public int getMaxNumberOfCreature() {
      return this.maxNumberOfCreature;
   }

   /**
    * Gets whether or not this creature type is peaceful.
    */
   public boolean getPeacefulCreature() {
      return this.isPeacefulCreature;
   }

   /**
    * Return whether this creature type is an animal.
    */
   public boolean getAnimal() {
      return this.isAnimal;
   }

   public static EntityClassification create(String name, String id, int maxNumberOfCreatureIn, boolean isPeacefulCreatureIn, boolean isAnimalIn, int despawnDistance) {
      throw new IllegalStateException("Enum not extended");
   }

   public int func_233671_f_() {
      return this.field_233669_n_;
   }

   public int func_233672_g_() {
      return 32;
   }
}