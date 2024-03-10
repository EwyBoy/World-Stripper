package com.ewyboy.worldstripper.command;

import com.ewyboy.worldstripper.WorldStripper;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class WSCommands {

    public static void init() {
        CommandRegistrationEvent.EVENT.register((dispatcher, registry, selection) -> dispatcher.register(
                LiteralArgumentBuilder.<CommandSourceStack>literal(WorldStripper.MOD_ID)
                        .then(CommandAdd.register(registry))
                        .then(CommandRemove.register(registry))
                        .then(CommandEdit.register(registry))
                        .then(CommandView.register())
                        .then(CommandReload.register())
                        .executes(WSCommands::help)
        ));
    }

    private static int help(CommandContext<CommandSourceStack> ctx) {
        ctx.getSource().sendSuccess(() -> Component.literal(""), false);
        ctx.getSource().sendSuccess(() -> Component.literal("[" + ChatFormatting.AQUA + "Add" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE + "block" +  ChatFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(() -> Component.literal("[" + ChatFormatting.AQUA + "Edit" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE + "block" + ChatFormatting.WHITE + ", " +  ChatFormatting.LIGHT_PURPLE + "block" +  ChatFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(() -> Component.literal("[" + ChatFormatting.AQUA + "Remove" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE + "block" +  ChatFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(() -> Component.literal("[" + ChatFormatting.AQUA + "View" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE +  ChatFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(() -> Component.literal("[" + ChatFormatting.AQUA + "Reload" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE +  ChatFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(() -> Component.literal(""), false);
        return 0;
    }

}
