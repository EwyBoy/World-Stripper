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

public class CommandRemoveEntry {

    public static ArgumentBuilder<ServerCommandSource, ?> register() {
        return CommandManager.literal("remove").requires((commandSource) -> commandSource.hasPermissionLevel(2))
                .then(CommandManager.argument("block", BlockStateArgumentType.blockState())
                        .executes((commandSource) -> removeEntry(
                                commandSource.getSource(),
                                BlockStateArgumentType.getBlockState(commandSource, "block")
                        ))
                );
    }

    private static int removeEntry(ServerCommandSource source, BlockStateArgument block) {
        String entry = Registry.BLOCK.getId(block.getBlockState().getBlock()).toString();
        if (JsonHandler.removeEntry(entry)) {
            source.sendFeedback(new LiteralText(Formatting.RED + entry + Formatting.WHITE + " removed from config"), true);
        } else {
            source.sendFeedback(new LiteralText(Formatting.RED + "ERROR: " + entry + Formatting.WHITE + " not found in config"), true);
        }
        return 0;
    }

}
