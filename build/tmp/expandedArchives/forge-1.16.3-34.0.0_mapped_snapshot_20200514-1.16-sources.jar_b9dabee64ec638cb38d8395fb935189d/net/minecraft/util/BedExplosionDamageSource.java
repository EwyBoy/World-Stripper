package net.minecraft.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class BedExplosionDamageSource extends DamageSource {
   protected BedExplosionDamageSource() {
      super("badRespawnPoint");
      this.setDifficultyScaled();
      this.setExplosion();
   }

   /**
    * Gets the death message that is displayed when the player dies
    */
   public ITextComponent getDeathMessage(LivingEntity entityLivingBaseIn) {
      ITextComponent itextcomponent = TextComponentUtils.func_240647_a_(new TranslationTextComponent("death.attack.badRespawnPoint.link")).func_240700_a_((p_233545_0_) -> {
         return p_233545_0_.func_240715_a_(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://bugs.mojang.com/browse/MCPE-28723")).func_240716_a_(new HoverEvent(HoverEvent.Action.field_230550_a_, new StringTextComponent("MCPE-28723")));
      });
      return new TranslationTextComponent("death.attack.badRespawnPoint.message", entityLivingBaseIn.getDisplayName(), itextcomponent);
   }
}