package com.ewyboy.worldstripper.commands.server;

import com.ewyboy.worldstripper.json.StripListHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockStateArgument;
import net.minecraft.command.arguments.BlockStateInput;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.registries.ForgeRegistries;;import java.util.Objects;

public class CommandRemoveEntry {

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("remove").requires((commandSource) -> commandSource.hasPermission(2))
                .then(Commands.argument("block", BlockStateArgument.block())
                        .executes((commandSource) -> removeEntry(
                                commandSource.getSource(),
                                BlockStateArgument.getBlock(commandSource, "block")
                        ))
                );
    }

    private static int removeEntry(CommandSource source, BlockStateInput block) {
        String entry = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block.getState().getBlock())).toString();
        if (StripListHandler.removeEntry(entry)) {
            source.sendSuccess(new StringTextComponent(TextFormatting.RED + entry + TextFormatting.WHITE + " removed from config"), true);
        } else {
            source.sendSuccess(new StringTextComponent(TextFormatting.DARK_RED + "ERROR: " + TextFormatting.RED + entry + TextFormatting.WHITE + " not found in config"), true);
        }
        return 0;
    }

}
