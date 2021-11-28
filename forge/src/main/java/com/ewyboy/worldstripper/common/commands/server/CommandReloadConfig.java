package com.ewyboy.worldstripper.common.commands.server;

import com.ewyboy.worldstripper.common.config.ConfigHelper;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class CommandReloadConfig {

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("reload")
            .requires(cs -> cs.hasPermission(2))
            .executes(context -> {
                ServerPlayerEntity player = context.getSource().getPlayerOrException();
                try {
                    ConfigHelper.reloadAndSaveConfig();
                    player.displayClientMessage(new StringTextComponent("World Stripper config reloaded"), false);
                } catch (Exception e) {
                    e.printStackTrace();
                    player.displayClientMessage(new StringTextComponent("Error! World Stripper config failed to reload"), false);
                }
                return 0;
            });
    }
}
