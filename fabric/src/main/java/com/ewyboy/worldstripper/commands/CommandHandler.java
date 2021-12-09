package com.ewyboy.worldstripper.commands;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.commands.server.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

public class CommandHandler {

    private static ArgumentBuilder<CommandSourceStack, ?> commandAddEntry;
    private static ArgumentBuilder<CommandSourceStack, ?> commandEditEntry;
    private static ArgumentBuilder<CommandSourceStack, ?> commandRemoveEntry;
    private static ArgumentBuilder<CommandSourceStack, ?> commandViewEntries;
    private static ArgumentBuilder<CommandSourceStack, ?> commandReload;

    public static CommandHandler commandHandler = new CommandHandler();

    public static void setup() {
        commandHandler.register();
    }

    public void register() {
        registerCommands();
        CommandRegistrationCallback.EVENT.register(this :: registerCommands);
    }

    private void registerCommands() {
        commandAddEntry = CommandAddEntry.register();
        commandEditEntry = CommandEditEntry.register();
        commandRemoveEntry = CommandRemoveEntry.register();
        commandViewEntries = CommandViewEntries.register();
        commandReload = CommandReload.register();
    }

    private int help(CommandContext<CommandSourceStack> ctx) {
        ctx.getSource().sendSuccess(new TextComponent(""), false);
        ctx.getSource().sendSuccess(new TextComponent("[" + ChatFormatting.AQUA + "Add" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE + "block" +  ChatFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(new TextComponent("[" + ChatFormatting.AQUA + "Edit" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE + "block" + ChatFormatting.WHITE + ", " +  ChatFormatting.LIGHT_PURPLE + "block" +  ChatFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(new TextComponent("[" + ChatFormatting.AQUA + "Remove" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE + "block" +  ChatFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(new TextComponent("[" + ChatFormatting.AQUA + "View" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE + "" +  ChatFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(new TextComponent("[" + ChatFormatting.AQUA + "Reload" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE + "" +  ChatFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(new TextComponent(""), false);
        return 0;
    }

    private void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, boolean dedicated) {
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSourceStack> literal(WorldStripper.MOD_ID)
                        .then(commandAddEntry)
                        .then(commandEditEntry)
                        .then(commandRemoveEntry)
                        .then(commandViewEntries)
                        .then(commandReload)
                        .executes(this :: help)
        );
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSourceStack> literal("ws")
                        .then(commandAddEntry)
                        .then(commandEditEntry)
                        .then(commandRemoveEntry)
                        .then(commandViewEntries)
                        .then(commandReload)
                        .executes(this :: help)
        );
    }

}
