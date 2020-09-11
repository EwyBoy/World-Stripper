package net.minecraft.util;

import java.util.Random;
import java.util.UUID;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RandomObjectDescriptor {
   private static final String[] field_218811_a = new String[]{"Slim", "Far", "River", "Silly", "Fat", "Thin", "Fish", "Bat", "Dark", "Oak", "Sly", "Bush", "Zen", "Bark", "Cry", "Slack", "Soup", "Grim", "Hook", "Dirt", "Mud", "Sad", "Hard", "Crook", "Sneak", "Stink", "Weird", "Fire", "Soot", "Soft", "Rough", "Cling", "Scar"};
   private static final String[] field_218812_b = new String[]{"Fox", "Tail", "Jaw", "Whisper", "Twig", "Root", "Finder", "Nose", "Brow", "Blade", "Fry", "Seek", "Wart", "Tooth", "Foot", "Leaf", "Stone", "Fall", "Face", "Tongue", "Voice", "Lip", "Mouth", "Snail", "Toe", "Ear", "Hair", "Beard", "Shirt", "Fist"};

   public static String func_229748_a_(UUID p_229748_0_) {
      Random random = func_218808_b(p_229748_0_);
      return func_218809_a(random, field_218811_a) + func_218809_a(random, field_218812_b);
   }

   private static String func_218809_a(Random p_218809_0_, String[] p_218809_1_) {
      return Util.func_240989_a_(p_218809_1_, p_218809_0_);
   }

   private static Random func_218808_b(UUID p_218808_0_) {
      return new Random((long)(p_218808_0_.hashCode() >> 2));
   }
}