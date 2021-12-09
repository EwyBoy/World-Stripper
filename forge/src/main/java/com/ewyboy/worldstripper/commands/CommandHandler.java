package com.ewyboy.worldstripper.commands;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.commands.server.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public class CommandHandler {

    private static ArgumentBuilder<CommandSource, ?> commandAddEntry;
    private static ArgumentBuilder<CommandSource, ?> commandEditEntry;
    private static ArgumentBuilder<CommandSource, ?> commandRemoveEntry;
    private static ArgumentBuilder<CommandSource, ?> commandViewEntries;
    private static ArgumentBuilder<CommandSource, ?> commandReload;

    public static CommandHandler commandHandler = new CommandHandler();

    public CommandHandler() {}

    public static void setup() {
        commandHandler.register();
    }

    public void register() {
        MinecraftForge.EVENT_BUS.addListener(this :: onServerStart);
    }

    private void registerCommands() {
        commandAddEntry = CommandAddEntry.register();
        commandEditEntry = CommandEditEntry.register();
        commandRemoveEntry = CommandRemoveEntry.register();
        commandViewEntries = CommandViewEntries.register();
        commandReload = CommandReload.register();
    }

    public void onServerStart(FMLServerStartingEvent event) {
        registerCommands();
        new CommandHandler(event.getServer().getCommands().getDispatcher());
    }

    private int help(CommandContext<CommandSource> ctx) {
        ctx.getSource().sendSuccess(new StringTextComponent(""), false);
        ctx.getSource().sendSuccess(new StringTextComponent("[" + TextFormatting.AQUA + "Add" + TextFormatting.WHITE + "] (" + TextFormatting.LIGHT_PURPLE + "block" +  TextFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(new StringTextComponent("[" + TextFormatting.AQUA + "Edit" + TextFormatting.WHITE + "] (" + TextFormatting.LIGHT_PURPLE + "block" + TextFormatting.WHITE + ", " +  TextFormatting.LIGHT_PURPLE + "block" +  TextFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(new StringTextComponent("[" + TextFormatting.AQUA + "Remove" + TextFormatting.WHITE + "] (" + TextFormatting.LIGHT_PURPLE + "block" +  TextFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(new StringTextComponent("[" + TextFormatting.AQUA + "View" + TextFormatting.WHITE + "] (" + TextFormatting.LIGHT_PURPLE + "" +  TextFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(new StringTextComponent("[" + TextFormatting.AQUA + "Reload" + TextFormatting.WHITE + "] (" + TextFormatting.LIGHT_PURPLE + "" +  TextFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(new StringTextComponent(""), false);
        return 0;
    }

    public CommandHandler(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSource> literal(WorldStripper.MOD_ID)
                    .then(commandAddEntry)
                    .then(commandEditEntry)
                    .then(commandRemoveEntry)
                    .then(commandViewEntries)
                    .then(commandReload)
                    .executes(this :: help)
        );
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSource> literal("ws")
                        .then(commandAddEntry)
                        .then(commandEditEntry)
                        .then(commandRemoveEntry)
                        .then(commandViewEntries)
                        .then(commandReload)
                        .executes(this :: help)
        );
    }

}

