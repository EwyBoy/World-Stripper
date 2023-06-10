package com.ewyboy.worldstripper.commands;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.commands.server.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

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
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            commandAddEntry = CommandAddEntry.register(registryAccess);
            commandEditEntry = CommandEditEntry.register(registryAccess);
            commandRemoveEntry = CommandRemoveEntry.register(registryAccess);
            commandViewEntries = CommandViewEntries.register();
            commandReload = CommandReload.register();
            registerCommands(dispatcher);
        });
    }


    private int help(CommandContext<CommandSourceStack> ctx) {
        ctx.getSource().sendSuccess(() -> Component.literal(""), false);
        ctx.getSource().sendSuccess(() -> Component.literal("[" + ChatFormatting.AQUA + "Add" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE + "block" +  ChatFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(() -> Component.literal("[" + ChatFormatting.AQUA + "Edit" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE + "block" + ChatFormatting.WHITE + ", " +  ChatFormatting.LIGHT_PURPLE + "block" +  ChatFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(() -> Component.literal("[" + ChatFormatting.AQUA + "Remove" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE + "block" +  ChatFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(() -> Component.literal("[" + ChatFormatting.AQUA + "View" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE +  ChatFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(() -> Component.literal("[" + ChatFormatting.AQUA + "Reload" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE +  ChatFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(() -> Component.literal(""), false);
        return 0;
    }

    private void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
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
