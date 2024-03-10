package com.ewyboy.worldstripper.command;

import com.ewyboy.worldstripper.json.StrippablesHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;

public class CommandEdit {

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandBuildContext ctx) {
        return Commands.literal("edit").requires((commandSource) -> commandSource.hasPermission(2))
                .then(Commands.argument("old block", BlockStateArgument.block(ctx))
                        .then(Commands.argument("new block", BlockStateArgument.block(ctx))
                                .executes((commandSource) -> addEntry(
                                        commandSource.getSource(),
                                        BlockStateArgument.getBlock(commandSource, "oldBlock"),
                                        BlockStateArgument.getBlock(commandSource, "newBlock")
                                )))
                );
    }

    private static int addEntry(CommandSourceStack source, BlockInput oldBlock, BlockInput newBlock) {
        String oldEntry = BuiltInRegistries.BLOCK.getKey(oldBlock.getState().getBlock()).toString();
        String newEntry = BuiltInRegistries.BLOCK.getKey(newBlock.getState().getBlock()).toString();

        if (StrippablesHandler.containsEntry(oldEntry)) {
            StrippablesHandler.removeEntry(oldEntry);
            StrippablesHandler.addEntry(newEntry);
            source.sendSuccess(() -> Component.literal(ChatFormatting.GREEN + oldEntry + ChatFormatting.WHITE + " replaced with " + ChatFormatting.GOLD + newEntry), true);
        } else {
            source.sendSuccess(() -> Component.literal(ChatFormatting.DARK_RED + "ERROR: " + ChatFormatting.RED + oldEntry.toUpperCase() + ChatFormatting.WHITE + " was not found in strip list"), true);
        }
        return 0;
    }

}
