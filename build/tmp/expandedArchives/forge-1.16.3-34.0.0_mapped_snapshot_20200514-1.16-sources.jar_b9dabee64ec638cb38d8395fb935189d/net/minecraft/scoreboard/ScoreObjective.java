package net.minecraft.scoreboard;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ScoreObjective {
   private final Scoreboard scoreboard;
   private final String name;
   private final ScoreCriteria objectiveCriteria;
   private ITextComponent displayName;
   private ITextComponent field_237496_e_;
   private ScoreCriteria.RenderType renderType;

   public ScoreObjective(Scoreboard p_i49788_1_, String p_i49788_2_, ScoreCriteria p_i49788_3_, ITextComponent p_i49788_4_, ScoreCriteria.RenderType p_i49788_5_) {
      this.scoreboard = p_i49788_1_;
      this.name = p_i49788_2_;
      this.objectiveCriteria = p_i49788_3_;
      this.displayName = p_i49788_4_;
      this.field_237496_e_ = this.func_237498_g_();
      this.renderType = p_i49788_5_;
   }

   @OnlyIn(Dist.CLIENT)
   public Scoreboard getScoreboard() {
      return this.scoreboard;
   }

   public String getName() {
      return this.name;
   }

   public ScoreCriteria getCriteria() {
      return this.objectiveCriteria;
   }

   public ITextComponent getDisplayName() {
      return this.displayName;
   }

   private ITextComponent func_237498_g_() {
      return TextComponentUtils.func_240647_a_(this.displayName.func_230532_e_().func_240700_a_((p_237497_1_) -> {
         return p_237497_1_.func_240716_a_(new HoverEvent(HoverEvent.Action.field_230550_a_, new StringTextComponent(this.name)));
      }));
   }

   public ITextComponent func_197890_e() {
      return this.field_237496_e_;
   }

   public void setDisplayName(ITextComponent p_199864_1_) {
      this.displayName = p_199864_1_;
      this.field_237496_e_ = this.func_237498_g_();
      this.scoreboard.onObjectiveChanged(this);
   }

   public ScoreCriteria.RenderType getRenderType() {
      return this.renderType;
   }

   public void setRenderType(ScoreCriteria.RenderType p_199866_1_) {
      this.renderType = p_199866_1_;
      this.scoreboard.onObjectiveChanged(this);
   }
}