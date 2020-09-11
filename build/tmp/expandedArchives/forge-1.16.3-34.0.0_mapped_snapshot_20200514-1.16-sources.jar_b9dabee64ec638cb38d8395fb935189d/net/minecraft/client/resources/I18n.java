package net.minecraft.client.resources;

import java.util.IllegalFormatException;
import net.minecraft.util.text.LanguageMap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class I18n {
   private static volatile LanguageMap field_239501_a_ = LanguageMap.getInstance();

   static void func_239502_a_(LanguageMap p_239502_0_) {
      field_239501_a_ = p_239502_0_;
      net.minecraftforge.fml.ForgeI18n.loadLanguageData(p_239502_0_.getLanguageData());
   }

   /**
    * Translates the given string and then formats it. Equivilant to String.format(translate(key), parameters).
    */
   public static String format(String translateKey, Object... parameters) {
      String s = field_239501_a_.func_230503_a_(translateKey);

      try {
         return String.format(s, parameters);
      } catch (IllegalFormatException illegalformatexception) {
         return "Format error: " + s;
      }
   }

   public static boolean hasKey(String key) {
      return field_239501_a_.func_230506_b_(key);
   }
}