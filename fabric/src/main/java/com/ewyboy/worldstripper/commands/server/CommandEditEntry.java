package com.ewyboy.worldstripper.commands.server;

import com.ewyboy.worldstripper.json.JsonHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;

public class CommandEditEntry {

    public static ArgumentBuilder<ServerCommandSource, ?> register() {
        return CommandManager.literal("edit").requires((commandSource) -> commandSource.hasPermissionLevel(2))
                .then(CommandManager.argument("old block", BlockStateArgumentType.blockState())
                .then(CommandManager.argument("new block", BlockStateArgumentType.blockState())
                        .executes((commandSource) -> addEntry(
                                commandSource.getSource(),
                                BlockStateArgumentType.getBlockState(commandSource, "oldBlock"),
                                BlockStateArgumentType.getBlockState(commandSource, "newBlock")
                        )))
                );
    }

    private static int addEntry(ServerCommandSource source, BlockStateArgument oldBlock, BlockStateArgument newBlock) {
        String oldEntry = Registry.BLOCK.getId(oldBlock.getBlockState().getBlock()).toString();
        String newEntry = Registry.BLOCK.getId(newBlock.getBlockState().getBlock()).toString();

        if (JsonHandler.containsEntry(oldEntry)) {
            JsonHandler.removeEntry(oldEntry);
            JsonHandler.addEntry(newEntry);
            source.sendFeedback(new LiteralText(Formatting.GREEN + oldEntry + Formatting.WHITE + " replaced with " + Formatting.GOLD + newEntry), true);
        } else {
            source.sendFeedback(new LiteralText(Formatting.RED + "ERROR: " +  oldEntry.toUpperCase() + Formatting.WHITE + " was not found in strip list"), true);
        }
        return 0;
    }

}
