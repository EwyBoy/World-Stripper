package com.ewyboy.worldstripper.commands.server;

import com.ewyboy.worldstripper.json.JsonHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

public class CommandViewEntries {

    public static ArgumentBuilder<ServerCommandSource, ?> register() {
        return CommandManager.literal("view").requires((commandSource) -> commandSource.hasPermissionLevel(2))
                .executes((commandSource) -> viewEntries(
                                commandSource.getSource()
                        )
                );
    }

    private static int viewEntries(ServerCommandSource source) {
        source.sendFeedback(new LiteralText(Formatting.RED + "" + Formatting.BOLD + "Strip List {"), true);
        for (String entry : JsonHandler.stripList.getEntries()) {
            String[] entryObject = entry.split(":");
            source.sendFeedback(new LiteralText(Formatting.GOLD + "     [" + Formatting.AQUA + entryObject[0] +  Formatting.RED + ":" + Formatting.GREEN + entryObject[1] + Formatting.GOLD + "]"), true);
        }
        source.sendFeedback(new LiteralText(Formatting.RED + "" + Formatting.BOLD + "}"), true);
        return 0;
    }

}
