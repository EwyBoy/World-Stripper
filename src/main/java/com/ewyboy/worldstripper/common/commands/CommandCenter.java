package com.ewyboy.worldstripper.common.commands;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.common.commands.config.CommandReloadConfig;
import com.ewyboy.worldstripper.common.commands.stripping.CommandDress;
import com.ewyboy.worldstripper.common.commands.profile.CommandProfile;
import com.ewyboy.worldstripper.common.commands.stripping.CommandStrip;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

public class CommandCenter {

    public CommandCenter(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSourceStack> literal(WorldStripper.MOD_ID)
                .then(CommandReloadConfig.register())
                .then(CommandStrip.register())
                .then(CommandDress.register())
                .then(CommandProfile.register())
                .executes(ctx -> {
                    ctx.getSource().sendSuccess(new TextComponent("So I heard you like strippers, huh?"), false);
                    return 0;
                })
        );
    }

}

