package net.minecraft.entity.ai.attributes;

import net.minecraft.util.registry.Registry;

public class Attributes {
   public static final Attribute field_233818_a_ = func_233831_a_("generic.max_health", (new RangedAttribute("attribute.name.generic.max_health", 20.0D, 1.0D, 1024.0D)).func_233753_a_(true));
   public static final Attribute field_233819_b_ = func_233831_a_("generic.follow_range", new RangedAttribute("attribute.name.generic.follow_range", 32.0D, 0.0D, 2048.0D));
   public static final Attribute field_233820_c_ = func_233831_a_("generic.knockback_resistance", new RangedAttribute("attribute.name.generic.knockback_resistance", 0.0D, 0.0D, 1.0D));
   public static final Attribute field_233821_d_ = func_233831_a_("generic.movement_speed", (new RangedAttribute("attribute.name.generic.movement_speed", (double)0.7F, 0.0D, 1024.0D)).func_233753_a_(true));
   public static final Attribute field_233822_e_ = func_233831_a_("generic.flying_speed", (new RangedAttribute("attribute.name.generic.flying_speed", (double)0.4F, 0.0D, 1024.0D)).func_233753_a_(true));
   public static final Attribute field_233823_f_ = func_233831_a_("generic.attack_damage", new RangedAttribute("attribute.name.generic.attack_damage", 2.0D, 0.0D, 2048.0D));
   public static final Attribute field_233824_g_ = func_233831_a_("generic.attack_knockback", new RangedAttribute("attribute.name.generic.attack_knockback", 0.0D, 0.0D, 5.0D));
   public static final Attribute field_233825_h_ = func_233831_a_("generic.attack_speed", (new RangedAttribute("attribute.name.generic.attack_speed", 4.0D, 0.0D, 1024.0D)).func_233753_a_(true));
   public static final Attribute field_233826_i_ = func_233831_a_("generic.armor", (new RangedAttribute("attribute.name.generic.armor", 0.0D, 0.0D, 30.0D)).func_233753_a_(true));
   public static final Attribute field_233827_j_ = func_233831_a_("generic.armor_toughness", (new RangedAttribute("attribute.name.generic.armor_toughness", 0.0D, 0.0D, 20.0D)).func_233753_a_(true));
   public static final Attribute field_233828_k_ = func_233831_a_("generic.luck", (new RangedAttribute("attribute.name.generic.luck", 0.0D, -1024.0D, 1024.0D)).func_233753_a_(true));
   public static final Attribute field_233829_l_ = func_233831_a_("zombie.spawn_reinforcements", new RangedAttribute("attribute.name.zombie.spawn_reinforcements", 0.0D, 0.0D, 1.0D));
   public static final Attribute field_233830_m_ = func_233831_a_("horse.jump_strength", (new RangedAttribute("attribute.name.horse.jump_strength", 0.7D, 0.0D, 2.0D)).func_233753_a_(true));

   private static Attribute func_233831_a_(String p_233831_0_, Attribute p_233831_1_) {
      return Registry.register(Registry.field_239692_aP_, p_233831_0_, p_233831_1_);
   }
}