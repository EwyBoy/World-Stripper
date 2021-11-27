package com.ewyboy.worldstripper.commands.server;

import com.ewyboy.worldstripper.json.JsonHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

public class CommandViewEntries {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("view").requires((commandSource) -> commandSource.hasPermission(2))
                .executes((commandSource) -> viewEntries(
                                commandSource.getSource()
                        )
                );
    }

    private static int viewEntries(CommandSourceStack source) {
        source.sendSuccess(new TextComponent(ChatFormatting.RED + "" + ChatFormatting.BOLD + "Strip List {"), true);
        for (String entry : JsonHandler.stripList.getEntries()) {
            String[] entryObject = entry.split(":");
            source.sendSuccess(new TextComponent(ChatFormatting.GOLD + "     [" + ChatFormatting.AQUA + entryObject[0] +  ChatFormatting.RED + ":" + ChatFormatting.GREEN + entryObject[1] + ChatFormatting.GOLD + "]"), true);
        }
        source.sendSuccess(new TextComponent(ChatFormatting.RED + "" + ChatFormatting.BOLD + "}"), true);
        return 0;
    }

}
