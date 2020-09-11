package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TranslationTextComponent;

public class MeCommand {
   public static void register(CommandDispatcher<CommandSource> dispatcher) {
      dispatcher.register(Commands.literal("me").then(Commands.argument("action", StringArgumentType.greedyString()).executes((p_198365_0_) -> {
         TranslationTextComponent translationtextcomponent = new TranslationTextComponent("chat.type.emote", p_198365_0_.getSource().getDisplayName(), StringArgumentType.getString(p_198365_0_, "action"));
         Entity entity = p_198365_0_.getSource().getEntity();
         if (entity != null) {
            p_198365_0_.getSource().getServer().getPlayerList().func_232641_a_(translationtextcomponent, ChatType.CHAT, entity.getUniqueID());
         } else {
            p_198365_0_.getSource().getServer().getPlayerList().func_232641_a_(translationtextcomponent, ChatType.SYSTEM, Util.field_240973_b_);
         }

         return 1;
      })));
   }
}