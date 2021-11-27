package com.ewyboy.worldstripper.commands;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.commands.server.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;

public class CommandHandler {

    public static CommandHandler commandHandler = new CommandHandler();

    public static void setup() {
        commandHandler.register();
    }

    public void register() {
        CommandRegistrationCallback.EVENT.register(this :: registerCommands);
    }

    private void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(
                LiteralArgumentBuilder.<ServerCommandSource> literal(WorldStripper.MOD_ID)
                        .then(CommandReload.register())
                        .then(CommandAddEntry.register())
                        .then(CommandRemoveEntry.register())
                        .then(CommandEditEntry.register())
                        .then(CommandViewEntries.register())
                        .executes(ctx -> 0)
        );
    }

}
