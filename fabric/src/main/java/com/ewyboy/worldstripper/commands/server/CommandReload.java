package com.ewyboy.worldstripper.commands.server;

import com.ewyboy.worldstripper.json.StripListHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class CommandReload {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("reload").requires((commandSource) -> commandSource.hasPermission(2))
                .executes((commandSource) -> reload(
                                commandSource.getSource()
                        )
                );
    }

    private static int reload(CommandSourceStack source) {
        try {
            StripListHandler.readJson(StripListHandler.JSON_FILE);
            source.sendSuccess(() -> Component.literal(ChatFormatting.GREEN + "SUCCESS: " + ChatFormatting.WHITE + "Strip list reloaded"), true);
        } catch (Exception e) {
            e.printStackTrace();
            source.sendSuccess(() -> Component.literal(ChatFormatting.DARK_RED + "ERROR: " + ChatFormatting.WHITE + "Strip list failed to reload"), true);
        }
        return 0;
    }

}
