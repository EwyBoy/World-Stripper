package net.minecraft.client.settings;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum NarratorStatus {
   OFF(0, "options.narrator.off"),
   ALL(1, "options.narrator.all"),
   CHAT(2, "options.narrator.chat"),
   SYSTEM(3, "options.narrator.system");

   private static final NarratorStatus[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(NarratorStatus::getId)).toArray((p_216826_0_) -> {
      return new NarratorStatus[p_216826_0_];
   });
   private final int id;
   private final ITextComponent field_238232_g_;

   private NarratorStatus(int id, String resourceKeyIn) {
      this.id = id;
      this.field_238232_g_ = new TranslationTextComponent(resourceKeyIn);
   }

   public int getId() {
      return this.id;
   }

   public ITextComponent func_238233_b_() {
      return this.field_238232_g_;
   }

   public static NarratorStatus byId(int id) {
      return BY_ID[MathHelper.normalizeAngle(id, BY_ID.length)];
   }
}