package com.ewyboy.worldstripper.commands.server;

import com.ewyboy.worldstripper.json.StripListHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockStateArgument;
import net.minecraft.command.arguments.BlockStateInput;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class CommandAddEntry {

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("add").requires((commandSource) -> commandSource.hasPermission(2))
                .then(Commands.argument("block", BlockStateArgument.block())
                        .executes((commandSource) -> addEntry(
                                commandSource.getSource(),
                                BlockStateArgument.getBlock(commandSource, "block")
                        ))
                );
    }

    private static int addEntry(CommandSource source, BlockStateInput block) {
        String entry = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block.getState().getBlock())).toString();
        if (StripListHandler.addEntry(entry)) {
            source.sendSuccess(new StringTextComponent(TextFormatting.GREEN + entry + TextFormatting.WHITE + " added to strip list"), true);
        } else {
            source.sendSuccess(new StringTextComponent(TextFormatting.DARK_RED + "ERROR: " + TextFormatting.RED + entry + TextFormatting.WHITE + " is already found in strip list"), true);
        }
        return 0;
    }

}
