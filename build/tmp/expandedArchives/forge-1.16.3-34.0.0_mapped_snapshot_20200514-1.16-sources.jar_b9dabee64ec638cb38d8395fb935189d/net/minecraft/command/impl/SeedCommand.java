package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class SeedCommand {
   public static void func_241067_a_(CommandDispatcher<CommandSource> p_241067_0_, boolean p_241067_1_) {
      p_241067_0_.register(Commands.literal("seed").requires((p_198673_1_) -> {
         return !p_241067_1_ || p_198673_1_.hasPermissionLevel(2);
      }).executes((p_198672_0_) -> {
         long i = p_198672_0_.getSource().getWorld().getSeed();
         ITextComponent itextcomponent = TextComponentUtils.func_240647_a_((new StringTextComponent(String.valueOf(i))).func_240700_a_((p_211752_2_) -> {
            return p_211752_2_.func_240712_a_(TextFormatting.GREEN).func_240715_a_(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, String.valueOf(i))).func_240716_a_(new HoverEvent(HoverEvent.Action.field_230550_a_, new TranslationTextComponent("chat.copy.click"))).func_240714_a_(String.valueOf(i));
         }));
         p_198672_0_.getSource().sendFeedback(new TranslationTextComponent("commands.seed.success", itextcomponent), false);
         return (int)i;
      }));
   }
}