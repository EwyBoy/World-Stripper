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

public class CommandAdd {

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandBuildContext context) {
        return Commands.literal("add").requires((commandSource) -> commandSource.hasPermission(2))
                .then(Commands.argument("block", BlockStateArgument.block(context))
                        .executes((commandSource) -> addEntry(
                                        commandSource.getSource(),
                                        BlockStateArgument.getBlock(commandSource, "block")
                                )
                        )
                );
    }

    private static int addEntry(CommandSourceStack source, BlockInput block) {
        String entry = BuiltInRegistries.BLOCK.getKey(block.getState().getBlock()).toString();
        if (StrippablesHandler.addEntry(entry)) {
            source.sendSuccess(() -> Component.literal(ChatFormatting.GREEN + entry + ChatFormatting.WHITE + " added to strip list"), true);
        } else {
            source.sendSuccess(() -> Component.literal(ChatFormatting.DARK_RED + "ERROR: " + ChatFormatting.RED + entry + ChatFormatting.WHITE + " is already found in strip list"), true);
        }
        return 0;
    }


}
