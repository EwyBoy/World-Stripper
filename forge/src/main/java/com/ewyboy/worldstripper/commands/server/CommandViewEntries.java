package com.ewyboy.worldstripper.commands.server;

import com.ewyboy.worldstripper.json.StripListHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class CommandViewEntries {

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("view").requires((commandSource) -> commandSource.hasPermission(2))
                .executes((commandSource) -> viewEntries(
                                commandSource.getSource()
                        )
                );
    }

    private static int viewEntries(CommandSource source) {
        source.sendSuccess(new StringTextComponent(TextFormatting.RED + "" + TextFormatting.BOLD + "Strip List {"), true);
        for (String entry : StripListHandler.stripList.getEntries()) {
            String[] entryObject = entry.split(":");
            source.sendSuccess(new StringTextComponent(TextFormatting.GOLD + "     [" + TextFormatting.AQUA + entryObject[0] +  TextFormatting.RED + ":" + TextFormatting.GREEN + entryObject[1] + TextFormatting.GOLD + "]"), true);
        }
        source.sendSuccess(new StringTextComponent(TextFormatting.RED + "" + TextFormatting.BOLD + "}"), true);
        return 0;
    }

}
