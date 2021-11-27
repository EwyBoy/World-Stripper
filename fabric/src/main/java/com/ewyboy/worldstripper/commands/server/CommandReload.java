package com.ewyboy.worldstripper.commands.server;

import com.ewyboy.worldstripper.json.JsonHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

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
            JsonHandler.readJson(JsonHandler.JSON_FILE);
            source.sendSuccess(new TextComponent(ChatFormatting.GREEN + "SUCCESS: " + ChatFormatting.WHITE + "Strip list reloaded"), true);
        } catch (Exception e) {
            e.printStackTrace();
            source.sendSuccess(new TextComponent(ChatFormatting.RED + "ERROR: " + ChatFormatting.WHITE + "Strip list failed to reload"), true);
        }
        return 0;
    }

}
