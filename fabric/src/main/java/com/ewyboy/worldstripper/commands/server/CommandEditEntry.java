package com.ewyboy.worldstripper.commands.server;

import com.ewyboy.worldstripper.json.StripListHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TextComponent;

public class CommandEditEntry {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("edit").requires((commandSource) -> commandSource.hasPermission(2))
                .then(Commands.argument("old block", BlockStateArgument.block())
                .then(Commands.argument("new block", BlockStateArgument.block())
                        .executes((commandSource) -> addEntry(
                                commandSource.getSource(),
                                BlockStateArgument.getBlock(commandSource, "oldBlock"),
                                BlockStateArgument.getBlock(commandSource, "newBlock")
                        )))
                );
    }

    private static int addEntry(CommandSourceStack source, BlockInput oldBlock, BlockInput newBlock) {
        String oldEntry = Registry.BLOCK.getKey(oldBlock.getState().getBlock()).toString();
        String newEntry = Registry.BLOCK.getKey(newBlock.getState().getBlock()).toString();

        if (StripListHandler.containsEntry(oldEntry)) {
            StripListHandler.removeEntry(oldEntry);
            StripListHandler.addEntry(newEntry);
            source.sendSuccess(new TextComponent(ChatFormatting.GREEN + oldEntry + ChatFormatting.WHITE + " replaced with " + ChatFormatting.GOLD + newEntry), true);
        } else {
            source.sendSuccess(new TextComponent(ChatFormatting.DARK_RED + "ERROR: " + ChatFormatting.RED + oldEntry.toUpperCase() + ChatFormatting.WHITE + " was not found in strip list"), true);
        }
        return 0;
    }

}
