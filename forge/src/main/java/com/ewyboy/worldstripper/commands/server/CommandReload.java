package com.ewyboy.worldstripper.commands.server;

import com.ewyboy.worldstripper.json.StripListHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class CommandReload {

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("reload").requires((commandSource) -> commandSource.hasPermission(2))
                .executes((commandSource) -> reload(
                                commandSource.getSource()
                        )
                );
    }

    private static int reload(CommandSource source) {
        try {
            StripListHandler.readJson(StripListHandler.JSON_FILE);
            source.sendSuccess(new StringTextComponent(TextFormatting.GREEN + "SUCCESS: " + TextFormatting.WHITE + "Strip list reloaded"), true);
        } catch (Exception e) {
            e.printStackTrace();
            source.sendSuccess(new StringTextComponent(TextFormatting.DARK_RED + "ERROR: " + TextFormatting.WHITE + "Strip list failed to reload"), true);
        }
        return 0;
    }
}
