package com.ewyboy.worldstripper.commands;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.commands.server.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;

public class CommandHandler {

    public static CommandHandler commandHandler = new CommandHandler();

    public static void setup() {
        commandHandler.register();
    }

    public void register() {
        CommandRegistrationCallback.EVENT.register(this :: registerCommands);
    }

    private void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, boolean dedicated) {
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSourceStack> literal(WorldStripper.MOD_ID)
                        .then(CommandReload.register())
                        .then(CommandAddEntry.register())
                        .then(CommandRemoveEntry.register())
                        .then(CommandEditEntry.register())
                        .then(CommandViewEntries.register())
                        .executes(ctx -> 0)
        );
    }

}
