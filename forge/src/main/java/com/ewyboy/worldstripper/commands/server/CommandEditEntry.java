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

public class CommandEditEntry {

    public static ArgumentBuilder<CommandSource, ?> register() {
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

    private static int addEntry(CommandSource source, BlockStateInput oldBlock, BlockStateInput newBlock) {
        String oldEntry = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(oldBlock.getState().getBlock())).toString();
        String newEntry = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(newBlock.getState().getBlock())).toString();

        if (StripListHandler.containsEntry(oldEntry)) {
            StripListHandler.removeEntry(oldEntry);
            StripListHandler.addEntry(newEntry);
            source.sendSuccess(new StringTextComponent(TextFormatting.GREEN + oldEntry + TextFormatting.WHITE + " replaced with " + TextFormatting.GOLD + newEntry), true);
        } else {
            source.sendSuccess(new StringTextComponent(TextFormatting.DARK_RED + "ERROR: " + TextFormatting.RED +  oldEntry.toUpperCase() + TextFormatting.WHITE + " was not found in strip list"), true);
        }
        return 0;
    }

}
