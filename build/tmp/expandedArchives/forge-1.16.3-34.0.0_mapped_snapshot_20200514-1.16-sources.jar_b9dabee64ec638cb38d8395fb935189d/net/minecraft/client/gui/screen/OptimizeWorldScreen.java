package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.Util;
import net.minecraft.util.WorldOptimizer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.IServerConfiguration;
import net.minecraft.world.storage.SaveFormat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class OptimizeWorldScreen extends Screen {
   private static final Logger field_239024_a_ = LogManager.getLogger();
   private static final Object2IntMap<RegistryKey<World>> PROGRESS_BAR_COLORS = Util.make(new Object2IntOpenCustomHashMap<>(Util.identityHashStrategy()), (p_212346_0_) -> {
      p_212346_0_.put(World.field_234918_g_, -13408734);
      p_212346_0_.put(World.field_234919_h_, -10075085);
      p_212346_0_.put(World.field_234920_i_, -8943531);
      p_212346_0_.defaultReturnValue(-2236963);
   });
   private final BooleanConsumer field_214332_b;
   private final WorldOptimizer optimizer;

   @Nullable
   public static OptimizeWorldScreen func_239025_a_(Minecraft p_239025_0_, BooleanConsumer p_239025_1_, DataFixer p_239025_2_, SaveFormat.LevelSave p_239025_3_, boolean p_239025_4_) {
      DynamicRegistries.Impl dynamicregistries$impl = DynamicRegistries.func_239770_b_();

      try (Minecraft.PackManager minecraft$packmanager = p_239025_0_.func_238189_a_(dynamicregistries$impl, Minecraft::func_238180_a_, Minecraft::func_238181_a_, false, p_239025_3_)) {
         IServerConfiguration iserverconfiguration = minecraft$packmanager.func_238226_c_();
         p_239025_3_.func_237287_a_(dynamicregistries$impl, iserverconfiguration);
         ImmutableSet<RegistryKey<World>> immutableset = iserverconfiguration.func_230418_z_().func_236226_g_();
         return new OptimizeWorldScreen(p_239025_1_, p_239025_2_, p_239025_3_, iserverconfiguration.func_230408_H_(), p_239025_4_, immutableset);
      } catch (Exception exception) {
         field_239024_a_.warn("Failed to load datapacks, can't optimize world", (Throwable)exception);
         return null;
      }
   }

   private OptimizeWorldScreen(BooleanConsumer p_i232319_1_, DataFixer p_i232319_2_, SaveFormat.LevelSave p_i232319_3_, WorldSettings p_i232319_4_, boolean p_i232319_5_, ImmutableSet<RegistryKey<World>> p_i232319_6_) {
      super(new TranslationTextComponent("optimizeWorld.title", p_i232319_4_.func_234947_a_()));
      this.field_214332_b = p_i232319_1_;
      this.optimizer = new WorldOptimizer(p_i232319_3_, p_i232319_2_, p_i232319_6_, p_i232319_5_);
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 4 + 150, 200, 20, DialogTexts.field_240633_d_, (p_214331_1_) -> {
         this.optimizer.cancel();
         this.field_214332_b.accept(false);
      }));
   }

   public void func_231023_e_() {
      if (this.optimizer.isFinished()) {
         this.field_214332_b.accept(true);
      }

   }

   public void func_231175_as__() {
      this.field_214332_b.accept(false);
   }

   public void func_231164_f_() {
      this.optimizer.cancel();
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 20, 16777215);
      int i = this.field_230708_k_ / 2 - 150;
      int j = this.field_230708_k_ / 2 + 150;
      int k = this.field_230709_l_ / 4 + 100;
      int l = k + 10;
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.optimizer.getStatusText(), this.field_230708_k_ / 2, k - 9 - 2, 10526880);
      if (this.optimizer.getTotalChunks() > 0) {
         func_238467_a_(p_230430_1_, i - 1, k - 1, j + 1, l + 1, -16777216);
         func_238475_b_(p_230430_1_, this.field_230712_o_, new TranslationTextComponent("optimizeWorld.info.converted", this.optimizer.getConverted()), i, 40, 10526880);
         func_238475_b_(p_230430_1_, this.field_230712_o_, new TranslationTextComponent("optimizeWorld.info.skipped", this.optimizer.getSkipped()), i, 40 + 9 + 3, 10526880);
         func_238475_b_(p_230430_1_, this.field_230712_o_, new TranslationTextComponent("optimizeWorld.info.total", this.optimizer.getTotalChunks()), i, 40 + (9 + 3) * 2, 10526880);
         int i1 = 0;

         for(RegistryKey<World> registrykey : this.optimizer.func_233533_c_()) {
            int j1 = MathHelper.floor(this.optimizer.func_233531_a_(registrykey) * (float)(j - i));
            func_238467_a_(p_230430_1_, i + i1, k, i + i1 + j1, l, PROGRESS_BAR_COLORS.getInt(registrykey));
            i1 += j1;
         }

         int k1 = this.optimizer.getConverted() + this.optimizer.getSkipped();
         func_238471_a_(p_230430_1_, this.field_230712_o_, k1 + " / " + this.optimizer.getTotalChunks(), this.field_230708_k_ / 2, k + 2 * 9 + 2, 10526880);
         func_238471_a_(p_230430_1_, this.field_230712_o_, MathHelper.floor(this.optimizer.getTotalProgress() * 100.0F) + "%", this.field_230708_k_ / 2, k + (l - k) / 2 - 9 / 2, 10526880);
      }

      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }
}