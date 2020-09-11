package net.minecraft.world.gen.layer;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.util.Util;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset1Transformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum HillsLayer implements IAreaTransformer2, IDimOffset1Transformer {
   INSTANCE;

   private static final Logger LOGGER = LogManager.getLogger();
   private static final Int2IntMap field_242940_c = Util.make(new Int2IntOpenHashMap(), (p_242941_0_) -> {
      p_242941_0_.put(1, 129);
      p_242941_0_.put(2, 130);
      p_242941_0_.put(3, 131);
      p_242941_0_.put(4, 132);
      p_242941_0_.put(5, 133);
      p_242941_0_.put(6, 134);
      p_242941_0_.put(12, 140);
      p_242941_0_.put(21, 149);
      p_242941_0_.put(23, 151);
      p_242941_0_.put(27, 155);
      p_242941_0_.put(28, 156);
      p_242941_0_.put(29, 157);
      p_242941_0_.put(30, 158);
      p_242941_0_.put(32, 160);
      p_242941_0_.put(33, 161);
      p_242941_0_.put(34, 162);
      p_242941_0_.put(35, 163);
      p_242941_0_.put(36, 164);
      p_242941_0_.put(37, 165);
      p_242941_0_.put(38, 166);
      p_242941_0_.put(39, 167);
   });

   public int apply(INoiseRandom p_215723_1_, IArea p_215723_2_, IArea p_215723_3_, int p_215723_4_, int p_215723_5_) {
      int i = p_215723_2_.getValue(this.func_215721_a(p_215723_4_ + 1), this.func_215722_b(p_215723_5_ + 1));
      int j = p_215723_3_.getValue(this.func_215721_a(p_215723_4_ + 1), this.func_215722_b(p_215723_5_ + 1));
      if (i > 255) {
         LOGGER.debug("old! {}", (int)i);
      }

      int k = (j - 2) % 29;
      if (!LayerUtil.isShallowOcean(i) && j >= 2 && k == 1) {
         return field_242940_c.getOrDefault(i, i);
      } else {
         if (p_215723_1_.random(3) == 0 || k == 0) {
            int l = i;
            if (i == 2) {
               l = 17;
            } else if (i == 4) {
               l = 18;
            } else if (i == 27) {
               l = 28;
            } else if (i == 29) {
               l = 1;
            } else if (i == 5) {
               l = 19;
            } else if (i == 32) {
               l = 33;
            } else if (i == 30) {
               l = 31;
            } else if (i == 1) {
               l = p_215723_1_.random(3) == 0 ? 18 : 4;
            } else if (i == 12) {
               l = 13;
            } else if (i == 21) {
               l = 22;
            } else if (i == 168) {
               l = 169;
            } else if (i == 0) {
               l = 24;
            } else if (i == 45) {
               l = 48;
            } else if (i == 46) {
               l = 49;
            } else if (i == 10) {
               l = 50;
            } else if (i == 3) {
               l = 34;
            } else if (i == 35) {
               l = 36;
            } else if (LayerUtil.areBiomesSimilar(i, 38)) {
               l = 37;
            } else if ((i == 24 || i == 48 || i == 49 || i == 50) && p_215723_1_.random(3) == 0) {
               l = p_215723_1_.random(2) == 0 ? 1 : 4;
            }

            if (k == 0 && l != i) {
               l = field_242940_c.getOrDefault(l, i);
            }

            if (l != i) {
               int i1 = 0;
               if (LayerUtil.areBiomesSimilar(p_215723_2_.getValue(this.func_215721_a(p_215723_4_ + 1), this.func_215722_b(p_215723_5_ + 0)), i)) {
                  ++i1;
               }

               if (LayerUtil.areBiomesSimilar(p_215723_2_.getValue(this.func_215721_a(p_215723_4_ + 2), this.func_215722_b(p_215723_5_ + 1)), i)) {
                  ++i1;
               }

               if (LayerUtil.areBiomesSimilar(p_215723_2_.getValue(this.func_215721_a(p_215723_4_ + 0), this.func_215722_b(p_215723_5_ + 1)), i)) {
                  ++i1;
               }

               if (LayerUtil.areBiomesSimilar(p_215723_2_.getValue(this.func_215721_a(p_215723_4_ + 1), this.func_215722_b(p_215723_5_ + 2)), i)) {
                  ++i1;
               }

               if (i1 >= 3) {
                  return l;
               }
            }
         }

         return i;
      }
   }
}