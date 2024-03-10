package com.ewyboy.worldstripper.command;

import com.ewyboy.worldstripper.json.StrippablesHandler;
import com.ewyboy.worldstripper.util.ModLogger;
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
            StrippablesHandler.readJson(StrippablesHandler.JSON_FILE);
            source.sendSuccess(() -> Component.literal(ChatFormatting.GREEN + "SUCCESS: " + ChatFormatting.WHITE + "Strip list reloaded"), true);
        } catch (Exception e) {
            ModLogger.error("Strip list failed to reload", e);
            source.sendSuccess(() -> Component.literal(ChatFormatting.DARK_RED + "ERROR: " + ChatFormatting.WHITE + "Strip list failed to reload"), true);
        }
        return 0;
    }

}
