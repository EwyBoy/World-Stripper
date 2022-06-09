package com.ewyboy.worldstripper.commands.server;

import com.ewyboy.worldstripper.json.StripListHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class CommandViewEntries {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("view").requires((commandSource) -> commandSource.hasPermission(2))
                .executes((commandSource) -> viewEntries(
                                commandSource.getSource()
                        )
                );
    }

    private static int viewEntries(CommandSourceStack source) {
        source.sendSuccess(Component.literal(ChatFormatting.RED + "" + ChatFormatting.BOLD + "Strip List {"), true);
        for (String entry : StripListHandler.stripList.getEntries()) {
            String[] entryObject = entry.split(":");
            source.sendSuccess(Component.literal(ChatFormatting.GOLD + "     [" + ChatFormatting.AQUA + entryObject[0] +  ChatFormatting.RED + ":" + ChatFormatting.GREEN + entryObject[1] + ChatFormatting.GOLD + "]"), true);
        }
        source.sendSuccess(Component.literal(ChatFormatting.RED + "" + ChatFormatting.BOLD + "}"), true);
        return 0;
    }

}
