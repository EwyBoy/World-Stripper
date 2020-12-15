package com.ewyboy.worldstripper.common.commands;

import com.ewyboy.worldstripper.common.config.ConfigHelper;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class CommandReloadConfig {

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("reload")
            .requires(cs -> cs.hasPermissionLevel(2))
            .executes(context -> {
                ServerPlayerEntity player = context.getSource().asPlayer();
                try {
                    ConfigHelper.reloadAndSaveConfig();
                    player.sendStatusMessage(new StringTextComponent("World Stripper config reloaded"), false);
                } catch (Exception e) {
                    e.printStackTrace();
                    player.sendStatusMessage(new StringTextComponent("Error! World Stripper config failed to reload"), false);
                }
                return 0;
            });
    }
}
