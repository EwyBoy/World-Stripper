package com.ewyboy.worldstripper.command;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.json.WSConfigLoader;
import com.ewyboy.worldstripper.workers.DressWorker;
import com.ewyboy.worldstripper.workers.StripWorker;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public class WSCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
        LiteralArgumentBuilder<CommandSourceStack> literalargumentbuilder = Commands.literal(WorldStripper.MOD_ID)
                .then(Commands.literal("add").requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                        .then(Commands.argument("block", BlockStateArgument.block(context))
                                .executes((commandSource) -> CommandAdd.addEntry(
                                        commandSource.getSource(),
                                        BlockStateArgument.getBlock(commandSource, "block")
                                ))
                        )
                )
                .then(CommandRemove.register(context))
                .then(CommandEdit.register(context))
                .then(CommandView.register())
                .then(CommandReload.register())
                .then(Commands.literal("strip").requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                        .executes(ctx -> strip(ctx.getSource(), -1))
                        .then(Commands.argument("radius", IntegerArgumentType.integer(0, 4096))
                                .executes(ctx -> strip(ctx.getSource(), IntegerArgumentType.getInteger(ctx, "radius")))))
                .then(Commands.literal("dress").requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                        .executes(ctx -> dress(ctx.getSource(), -1))
                        .then(Commands.argument("radius", IntegerArgumentType.integer(0, 4096))
                                .executes(ctx -> dress(ctx.getSource(), IntegerArgumentType.getInteger(ctx, "radius")))))
                .executes(WSCommands::help);

        dispatcher.register(literalargumentbuilder);
    }

    /*public static void init() {
     *//*CommandBuildContext.register((dispatcher, registry, selection) -> dispatcher.register(
                LiteralArgumentBuilder.<CommandSourceStack>literal(WorldStripper.MOD_ID)
                        .then(CommandAdd.register(registry))
                        .then(CommandRemove.register(registry))
                        .then(CommandEdit.register(registry))
                        .then(CommandView.register())
                        .then(CommandReload.register())
                        .executes(WSCommands::help)
        ));*//*
    }*/

    /** radius in blocks around the command position; -1 = the configured radius (same box as the keybind). */
    private static int strip(CommandSourceStack source, int radius) {
        var config = WSConfigLoader.getInstance().getConfig();
        int rx = radius < 0 ? config.stripRadiusX() / 2 : radius;
        int rz = radius < 0 ? config.stripRadiusZ() / 2 : radius;
        StripWorker.start(source.getLevel(), BlockPos.containing(source.getPosition()), rx, rz);
        source.sendSuccess(() -> Component.literal("Stripping " + (rx * 2 + 1) + "x" + (rz * 2 + 1) + " blocks around " + BlockPos.containing(source.getPosition()).toShortString()), true);
        return 1;
    }

    private static int dress(CommandSourceStack source, int radius) {
        var config = WSConfigLoader.getInstance().getConfig();
        int rx = radius < 0 ? config.stripRadiusX() / 2 : radius;
        int rz = radius < 0 ? config.stripRadiusZ() / 2 : radius;
        DressWorker.start(source.getLevel(), BlockPos.containing(source.getPosition()), rx, rz);
        source.sendSuccess(() -> Component.literal("Dressing " + (rx * 2 + 1) + "x" + (rz * 2 + 1) + " blocks around " + BlockPos.containing(source.getPosition()).toShortString()), true);
        return 1;
    }

    private static int help(CommandContext<CommandSourceStack> ctx) {
        ctx.getSource().sendSuccess(() -> Component.literal(""), false);
        ctx.getSource().sendSuccess(() -> Component.literal("[" + ChatFormatting.AQUA + "Add" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE + "block" + ChatFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(() -> Component.literal("[" + ChatFormatting.AQUA + "Edit" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE + "block" + ChatFormatting.WHITE + ", " + ChatFormatting.LIGHT_PURPLE + "block" + ChatFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(() -> Component.literal("[" + ChatFormatting.AQUA + "Remove" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE + "block" + ChatFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(() -> Component.literal("[" + ChatFormatting.AQUA + "View" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE + ChatFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(() -> Component.literal("[" + ChatFormatting.AQUA + "Strip" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE + "radius" + ChatFormatting.WHITE + ") strip the world around you (key: Delete)"), false);
        ctx.getSource().sendSuccess(() -> Component.literal("[" + ChatFormatting.AQUA + "Dress" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE + "radius" + ChatFormatting.WHITE + ") put stripped blocks back (key: Insert)"), false);
        ctx.getSource().sendSuccess(() -> Component.literal("[" + ChatFormatting.AQUA + "Reload" + ChatFormatting.WHITE + "] (" + ChatFormatting.LIGHT_PURPLE + ChatFormatting.WHITE + ") to strip list"), false);
        ctx.getSource().sendSuccess(() -> Component.literal(""), false);
        return 0;
    }

}
