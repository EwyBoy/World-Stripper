package com.ewyboy.worldstripper.common.commands;

import com.ewyboy.worldstripper.WorldStripper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;

public class CommandCenter {

    public CommandCenter(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSource> literal(WorldStripper.MOD_ID)
                .then(CommandReloadConfig.register())
                .then(CommandOpenConfig.register())
                .executes(ctx -> {
                    // TODO add something meaningful here
                    ctx.getSource().sendFeedback(new StringTextComponent("Placeholder Text"), false);
                    return 0;
                })
        );
    }

}

