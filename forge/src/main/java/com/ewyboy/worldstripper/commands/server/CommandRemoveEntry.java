package com.ewyboy.worldstripper.commands.server;

import com.ewyboy.worldstripper.json.StripListHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.network.chat.Component;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class CommandRemoveEntry {

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandBuildContext ctx) {
        return Commands.literal("remove").requires((commandSource) -> commandSource.hasPermission(2))
                .then(Commands.argument("block", BlockStateArgument.block(ctx))
                        .executes((commandSource) -> removeEntry(
                                commandSource.getSource(),
                                BlockStateArgument.getBlock(commandSource, "block")
                        ))
                );
    }

    private static int removeEntry(CommandSourceStack source, BlockInput block) {
        String entry = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block.getState().getBlock())).toString();
        if (StripListHandler.removeEntry(entry)) {
            source.sendSuccess(Component.literal(ChatFormatting.RED + entry + ChatFormatting.WHITE + " removed from config"), true);
        } else {
            source.sendSuccess(Component.literal(ChatFormatting.DARK_RED + "ERROR: " + ChatFormatting.RED + entry + ChatFormatting.WHITE + " not found in config"), true);
        }
        return 0;
    }

}
