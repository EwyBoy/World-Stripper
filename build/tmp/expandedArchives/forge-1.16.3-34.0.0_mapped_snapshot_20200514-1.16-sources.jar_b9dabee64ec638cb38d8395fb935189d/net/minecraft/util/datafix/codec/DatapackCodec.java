package net.minecraft.util.datafix.codec;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class DatapackCodec {
   public static final DatapackCodec field_234880_a_ = new DatapackCodec(ImmutableList.of("vanilla"), ImmutableList.of());
   public static final Codec<DatapackCodec> field_234881_b_ = RecordCodecBuilder.create((p_234886_0_) -> {
      return p_234886_0_.group(Codec.STRING.listOf().fieldOf("Enabled").forGetter((p_234888_0_) -> {
         return p_234888_0_.field_234882_c_;
      }), Codec.STRING.listOf().fieldOf("Disabled").forGetter((p_234885_0_) -> {
         return p_234885_0_.field_234883_d_;
      })).apply(p_234886_0_, DatapackCodec::new);
   });
   private final List<String> field_234882_c_;
   private final List<String> field_234883_d_;

   public DatapackCodec(List<String> p_i231607_1_, List<String> p_i231607_2_) {
      this.field_234882_c_ = com.google.common.collect.Lists.newArrayList(p_i231607_1_);
      this.field_234883_d_ = ImmutableList.copyOf(p_i231607_2_);
   }

   public List<String> func_234884_a_() {
      return this.field_234882_c_;
   }

   public List<String> func_234887_b_() {
      return this.field_234883_d_;
   }

   public void addModPacks(List<String> modPacks) {
      field_234882_c_.addAll(modPacks.stream().filter(p->!field_234882_c_.contains(p)).collect(java.util.stream.Collectors.toList()));
   }
}