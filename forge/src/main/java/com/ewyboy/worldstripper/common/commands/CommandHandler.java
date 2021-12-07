package com.ewyboy.worldstripper.common.commands;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.common.commands.server.CommandReloadConfig;
import com.ewyboy.worldstripper.common.commands.server.CommandDress;
import com.ewyboy.worldstripper.common.commands.server.CommandStrip;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public class CommandHandler {

    public static CommandHandler commandHandler = new CommandHandler();

    public CommandHandler() {}

    public static void setup() {
        commandHandler.register();
    }

    public void register() {
        MinecraftForge.EVENT_BUS.addListener(this :: onServerStart);
    }

    public void onServerStart(FMLServerStartingEvent event) {
        new CommandHandler(event.getServer().getCommands().getDispatcher());
    }

    public CommandHandler(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSource> literal(WorldStripper.MOD_ID)
                .then(CommandReloadConfig.register())
                .then(CommandStrip.register())
                .then(CommandDress.register())
                .executes(ctx -> {
                    // TODO: Funny text here
                    ctx.getSource().sendSuccess(new StringTextComponent("So I heard you like strippers, huh?"), false);
                    return 0;
                })
        );
    }

}

