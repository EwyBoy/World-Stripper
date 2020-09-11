package net.minecraft.util;

import java.util.Random;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnchantmentNameParts {
   private static final ResourceLocation field_238814_a_ = new ResourceLocation("minecraft", "alt");
   private static final Style field_238815_b_ = Style.field_240709_b_.func_240719_a_(field_238814_a_);
   private static final EnchantmentNameParts INSTANCE = new EnchantmentNameParts();
   private final Random rand = new Random();
   private final String[] namePartsArray = new String[]{"the", "elder", "scrolls", "klaatu", "berata", "niktu", "xyzzy", "bless", "curse", "light", "darkness", "fire", "air", "earth", "water", "hot", "dry", "cold", "wet", "ignite", "snuff", "embiggen", "twist", "shorten", "stretch", "fiddle", "destroy", "imbue", "galvanize", "enchant", "free", "limited", "range", "of", "towards", "inside", "sphere", "cube", "self", "other", "ball", "mental", "physical", "grow", "shrink", "demon", "elemental", "spirit", "animal", "creature", "beast", "humanoid", "undead", "fresh", "stale", "phnglui", "mglwnafh", "cthulhu", "rlyeh", "wgahnagl", "fhtagn", "baguette"};

   private EnchantmentNameParts() {
   }

   public static EnchantmentNameParts getInstance() {
      return INSTANCE;
   }

   public ITextProperties func_238816_a_(FontRenderer p_238816_1_, int p_238816_2_) {
      StringBuilder stringbuilder = new StringBuilder();
      int i = this.rand.nextInt(2) + 3;

      for(int j = 0; j < i; ++j) {
         if (j != 0) {
            stringbuilder.append(" ");
         }

         stringbuilder.append(Util.func_240989_a_(this.namePartsArray, this.rand));
      }

      return p_238816_1_.func_238420_b_().func_238358_a_((new StringTextComponent(stringbuilder.toString())).func_240703_c_(field_238815_b_), p_238816_2_, Style.field_240709_b_);
   }

   /**
    * Resets the underlying random number generator using a given seed.
    */
   public void reseedRandomGenerator(long seed) {
      this.rand.setSeed(seed);
   }
}