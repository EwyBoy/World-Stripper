package com.ewyboy.worldstripper.commands.server;

import com.ewyboy.worldstripper.json.JsonHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

public class CommandReload {

    public static ArgumentBuilder<ServerCommandSource, ?> register() {
        return CommandManager.literal("reload").requires((commandSource) -> commandSource.hasPermissionLevel(2))
                .executes((commandSource) -> reload(
                                commandSource.getSource()
                        )
                );
    }

    private static int reload(ServerCommandSource source) {
        try {
            JsonHandler.readJson(JsonHandler.JSON_FILE);
            source.sendFeedback(new LiteralText(Formatting.GREEN + "SUCCESS: " + Formatting.WHITE + "Strip list reloaded"), true);
        } catch (Exception e) {
            e.printStackTrace();
            source.sendFeedback(new LiteralText(Formatting.RED + "ERROR: " + Formatting.WHITE + "Strip list failed to reload"), true);
        }
        return 0;
    }

}
