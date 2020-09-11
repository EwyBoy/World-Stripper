package net.minecraft.world.gen.feature.template;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;

public class TagMatchRuleTest extends RuleTest {
   public static final Codec<TagMatchRuleTest> field_237161_a_ = ITag.func_232947_a_(() -> {
      return TagCollectionManager.func_242178_a().func_241835_a();
   }).fieldOf("tag").xmap(TagMatchRuleTest::new, (p_237162_0_) -> {
      return p_237162_0_.tag;
   }).codec();
   private final ITag<Block> tag;

   public TagMatchRuleTest(ITag<Block> tag) {
      this.tag = tag;
   }

   public boolean test(BlockState p_215181_1_, Random p_215181_2_) {
      return p_215181_1_.func_235714_a_(this.tag);
   }

   protected IRuleTestType<?> getType() {
      return IRuleTestType.TAG_MATCH;
   }
}