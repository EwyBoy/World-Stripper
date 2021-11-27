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

public class CommandAddEntry {

    public static ArgumentBuilder<ServerCommandSource, ?> register() {
        return CommandManager.literal("add").requires((commandSource) -> commandSource.hasPermissionLevel(2))
                .then(CommandManager.argument("block", BlockStateArgumentType.blockState())
                        .executes((commandSource) -> addEntry(
                                commandSource.getSource(),
                                BlockStateArgumentType.getBlockState(commandSource, "block")
                        ))
                );
    }

    private static int addEntry(ServerCommandSource source, BlockStateArgument block) {
        String entry = Registry.BLOCK.getId(block.getBlockState().getBlock()).toString();
        if (JsonHandler.addEntry(entry)) {
            source.sendFeedback(new LiteralText(Formatting.GREEN + entry + Formatting.WHITE + " added to strip list"), true);
        } else {
            source.sendFeedback(new LiteralText(Formatting.RED + "ERROR: " + entry + Formatting.WHITE + " is already found in strip list"), true);
        }
        return 0;
    }

}
