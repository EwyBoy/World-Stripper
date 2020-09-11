package net.minecraft.world.storage;

import java.io.File;
import javax.annotation.Nullable;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;

public class WorldSummary implements Comparable<WorldSummary> {
   private final WorldSettings field_237307_a_;
   private final VersionData field_237308_b_;
   private final String fileName;
   private final boolean requiresConversion;
   private final boolean field_237309_e_;
   private final File field_237310_f_;
   @Nullable
   @OnlyIn(Dist.CLIENT)
   private ITextComponent field_237311_g_;

   public WorldSummary(WorldSettings p_i232155_1_, VersionData p_i232155_2_, String p_i232155_3_, boolean p_i232155_4_, boolean p_i232155_5_, File p_i232155_6_) {
      this.field_237307_a_ = p_i232155_1_;
      this.field_237308_b_ = p_i232155_2_;
      this.fileName = p_i232155_3_;
      this.field_237309_e_ = p_i232155_5_;
      this.field_237310_f_ = p_i232155_6_;
      this.requiresConversion = p_i232155_4_;
   }

   /**
    * return the file name
    */
   @OnlyIn(Dist.CLIENT)
   public String getFileName() {
      return this.fileName;
   }

   /**
    * return the display name of the save
    */
   @OnlyIn(Dist.CLIENT)
   public String getDisplayName() {
      return StringUtils.isEmpty(this.field_237307_a_.func_234947_a_()) ? this.fileName : this.field_237307_a_.func_234947_a_();
   }

   @OnlyIn(Dist.CLIENT)
   public File func_237312_c_() {
      return this.field_237310_f_;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean requiresConversion() {
      return this.requiresConversion;
   }

   @OnlyIn(Dist.CLIENT)
   public long getLastTimePlayed() {
      return this.field_237308_b_.func_237325_b_();
   }

   public int compareTo(WorldSummary p_compareTo_1_) {
      if (this.field_237308_b_.func_237325_b_() < p_compareTo_1_.field_237308_b_.func_237325_b_()) {
         return 1;
      } else {
         return this.field_237308_b_.func_237325_b_() > p_compareTo_1_.field_237308_b_.func_237325_b_() ? -1 : this.fileName.compareTo(p_compareTo_1_.fileName);
      }
   }

   /**
    * Gets the EnumGameType.
    */
   @OnlyIn(Dist.CLIENT)
   public GameType getEnumGameType() {
      return this.field_237307_a_.func_234953_b_();
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isHardcoreModeEnabled() {
      return this.field_237307_a_.func_234954_c_();
   }

   /**
    * @return {@code true} if cheats are enabled for this world
    */
   @OnlyIn(Dist.CLIENT)
   public boolean getCheatsEnabled() {
      return this.field_237307_a_.func_234956_e_();
   }

   @OnlyIn(Dist.CLIENT)
   public IFormattableTextComponent func_237313_j_() {
      return (IFormattableTextComponent)(net.minecraft.util.StringUtils.isNullOrEmpty(this.field_237308_b_.func_237326_c_()) ? new TranslationTextComponent("selectWorld.versionUnknown") : new StringTextComponent(this.field_237308_b_.func_237326_c_()));
   }

   public VersionData func_237314_k_() {
      return this.field_237308_b_;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean markVersionInList() {
      return this.askToOpenWorld() || !SharedConstants.getVersion().isStable() && !this.field_237308_b_.func_237328_e_() || this.func_197731_n();
   }

   @OnlyIn(Dist.CLIENT)
   public boolean askToOpenWorld() {
      return this.field_237308_b_.func_237327_d_() > SharedConstants.getVersion().getWorldVersion();
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_197731_n() {
      return this.field_237308_b_.func_237327_d_() < SharedConstants.getVersion().getWorldVersion();
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_237315_o_() {
      return this.field_237309_e_;
   }

   @OnlyIn(Dist.CLIENT)
   public ITextComponent func_237316_p_() {
      if (this.field_237311_g_ == null) {
         this.field_237311_g_ = this.func_237317_q_();
      }

      return this.field_237311_g_;
   }

   @OnlyIn(Dist.CLIENT)
   private ITextComponent func_237317_q_() {
      if (this.func_237315_o_()) {
         return (new TranslationTextComponent("selectWorld.locked")).func_240699_a_(TextFormatting.RED);
      } else if (this.requiresConversion()) {
         return new TranslationTextComponent("selectWorld.conversion");
      } else {
         IFormattableTextComponent iformattabletextcomponent = (IFormattableTextComponent)(this.isHardcoreModeEnabled() ? (new StringTextComponent("")).func_230529_a_((new TranslationTextComponent("gameMode.hardcore")).func_240699_a_(TextFormatting.DARK_RED)) : new TranslationTextComponent("gameMode." + this.getEnumGameType().getName()));
         if (this.getCheatsEnabled()) {
            iformattabletextcomponent.func_240702_b_(", ").func_230529_a_(new TranslationTextComponent("selectWorld.cheats"));
         }

         IFormattableTextComponent iformattabletextcomponent1 = this.func_237313_j_();
         IFormattableTextComponent iformattabletextcomponent2 = (new StringTextComponent(", ")).func_230529_a_(new TranslationTextComponent("selectWorld.version")).func_240702_b_(" ");
         if (this.markVersionInList()) {
            iformattabletextcomponent2.func_230529_a_(iformattabletextcomponent1.func_240699_a_(this.askToOpenWorld() ? TextFormatting.RED : TextFormatting.ITALIC));
         } else {
            iformattabletextcomponent2.func_230529_a_(iformattabletextcomponent1);
         }

         iformattabletextcomponent.func_230529_a_(iformattabletextcomponent2);
         return iformattabletextcomponent;
      }
   }
}