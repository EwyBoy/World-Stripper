package com.ewyboy.worldstripper.common.commands;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.common.commands.config.CommandOpenConfig;
import com.ewyboy.worldstripper.common.commands.config.CommandReloadConfig;
import com.ewyboy.worldstripper.common.commands.stripping.CommandDress;
import com.ewyboy.worldstripper.common.commands.profile.CommandProfile;
import com.ewyboy.worldstripper.common.commands.stripping.CommandStrip;
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
                .then(CommandStrip.register())
                .then(CommandDress.register())
                .then(CommandProfile.register())
                .executes(ctx -> {
                    ctx.getSource().sendFeedback(new StringTextComponent("So I heard you like strippers, huh?"), false);
                    return 0;
                })
        );
    }

}

