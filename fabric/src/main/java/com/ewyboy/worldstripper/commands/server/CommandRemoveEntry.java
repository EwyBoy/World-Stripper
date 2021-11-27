package com.ewyboy.worldstripper.commands.server;

import com.ewyboy.worldstripper.json.JsonHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TextComponent;

public class CommandRemoveEntry {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("remove").requires((commandSource) -> commandSource.hasPermission(2))
                .then(Commands.argument("block", BlockStateArgument.block())
                        .executes((commandSource) -> removeEntry(
                                commandSource.getSource(),
                                BlockStateArgument.getBlock(commandSource, "block")
                        ))
                );
    }

    private static int removeEntry(CommandSourceStack source, BlockInput block) {
        String entry = Registry.BLOCK.getKey(block.getState().getBlock()).toString();
        if (JsonHandler.removeEntry(entry)) {
            source.sendSuccess(new TextComponent(ChatFormatting.RED + entry + ChatFormatting.WHITE + " removed from config"), true);
        } else {
            source.sendSuccess(new TextComponent(ChatFormatting.RED + "ERROR: " + entry + ChatFormatting.WHITE + " not found in config"), true);
        }
        return 0;
    }

}
