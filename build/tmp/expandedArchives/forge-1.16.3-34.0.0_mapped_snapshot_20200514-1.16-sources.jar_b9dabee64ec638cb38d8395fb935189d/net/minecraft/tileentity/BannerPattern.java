package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;

public enum BannerPattern implements net.minecraftforge.common.IExtensibleEnum {
   BASE("base", "b", false),
   SQUARE_BOTTOM_LEFT("square_bottom_left", "bl"),
   SQUARE_BOTTOM_RIGHT("square_bottom_right", "br"),
   SQUARE_TOP_LEFT("square_top_left", "tl"),
   SQUARE_TOP_RIGHT("square_top_right", "tr"),
   STRIPE_BOTTOM("stripe_bottom", "bs"),
   STRIPE_TOP("stripe_top", "ts"),
   STRIPE_LEFT("stripe_left", "ls"),
   STRIPE_RIGHT("stripe_right", "rs"),
   STRIPE_CENTER("stripe_center", "cs"),
   STRIPE_MIDDLE("stripe_middle", "ms"),
   STRIPE_DOWNRIGHT("stripe_downright", "drs"),
   STRIPE_DOWNLEFT("stripe_downleft", "dls"),
   STRIPE_SMALL("small_stripes", "ss"),
   CROSS("cross", "cr"),
   STRAIGHT_CROSS("straight_cross", "sc"),
   TRIANGLE_BOTTOM("triangle_bottom", "bt"),
   TRIANGLE_TOP("triangle_top", "tt"),
   TRIANGLES_BOTTOM("triangles_bottom", "bts"),
   TRIANGLES_TOP("triangles_top", "tts"),
   DIAGONAL_LEFT("diagonal_left", "ld"),
   DIAGONAL_RIGHT("diagonal_up_right", "rd"),
   DIAGONAL_LEFT_MIRROR("diagonal_up_left", "lud"),
   DIAGONAL_RIGHT_MIRROR("diagonal_right", "rud"),
   CIRCLE_MIDDLE("circle", "mc"),
   RHOMBUS_MIDDLE("rhombus", "mr"),
   HALF_VERTICAL("half_vertical", "vh"),
   HALF_HORIZONTAL("half_horizontal", "hh"),
   HALF_VERTICAL_MIRROR("half_vertical_right", "vhr"),
   HALF_HORIZONTAL_MIRROR("half_horizontal_bottom", "hhb"),
   BORDER("border", "bo"),
   CURLY_BORDER("curly_border", "cbo"),
   GRADIENT("gradient", "gra"),
   GRADIENT_UP("gradient_up", "gru"),
   BRICKS("bricks", "bri"),
   GLOBE("globe", "glb", true),
   CREEPER("creeper", "cre", true),
   SKULL("skull", "sku", true),
   FLOWER("flower", "flo", true),
   MOJANG("mojang", "moj", true),
   PIGLIN("piglin", "pig", true);

   private static final BannerPattern[] field_235647_S_ = values();
   public static final int field_222480_O = field_235647_S_.length;
   public static final int field_235646_Q_ = (int)Arrays.stream(field_235647_S_).filter((p_235649_0_) -> {
      return p_235649_0_.field_235648_T_;
   }).count();
   public static final int field_222481_P = field_222480_O - field_235646_Q_ - 1;
   private final boolean field_235648_T_;
   private final String fileName;
   private final String hashname;

   private BannerPattern(String fileNameIn, String hashNameIn) {
      this(fileNameIn, hashNameIn, false);
   }

   private BannerPattern(String p_i231861_3_, String p_i231861_4_, boolean p_i231861_5_) {
      this.fileName = p_i231861_3_;
      this.hashname = p_i231861_4_;
      this.field_235648_T_ = p_i231861_5_;
   }

   @OnlyIn(Dist.CLIENT)
   public ResourceLocation func_226957_a_(boolean p_226957_1_) {
      String s = p_226957_1_ ? "banner" : "shield";
      return new ResourceLocation("entity/" + s + "/" + this.getFileName());
   }

   @OnlyIn(Dist.CLIENT)
   public String getFileName() {
      return this.fileName;
   }

   public String getHashname() {
      return this.hashname;
   }

   @Nullable
   @OnlyIn(Dist.CLIENT)
   public static BannerPattern byHash(String hash) {
      for(BannerPattern bannerpattern : values()) {
         if (bannerpattern.hashname.equals(hash)) {
            return bannerpattern;
         }
      }

      return null;
   }

   public static BannerPattern create(String enumName, String fileNameIn, String hashNameIn) {
      throw new IllegalStateException("Enum not extended");
   }

   public static BannerPattern create(String enumName, String fileNameIn, String hashNameIn, boolean p_i231861_5_) {
      throw new IllegalStateException("Enum not extended");
   }

   public static class Builder {
      private final List<Pair<BannerPattern, DyeColor>> field_222478_a = Lists.newArrayList();

      public BannerPattern.Builder setPatternWithColor(BannerPattern p_222477_1_, DyeColor p_222477_2_) {
         this.field_222478_a.add(Pair.of(p_222477_1_, p_222477_2_));
         return this;
      }

      public ListNBT func_222476_a() {
         ListNBT listnbt = new ListNBT();

         for(Pair<BannerPattern, DyeColor> pair : this.field_222478_a) {
            CompoundNBT compoundnbt = new CompoundNBT();
            compoundnbt.putString("Pattern", (pair.getLeft()).hashname);
            compoundnbt.putInt("Color", pair.getRight().getId());
            listnbt.add(compoundnbt);
         }

         return listnbt;
      }
   }
}