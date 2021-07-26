package com.ewyboy.worldstripper.common.commands.config;

import com.ewyboy.worldstripper.common.config.ConfigHelper;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.TextComponent;

public class CommandReloadConfig {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("reload")
            .requires(cs -> cs.hasPermission(2))
            .executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                try {
                    ConfigHelper.saveConfig();
                    ConfigHelper.syncConfig();
                    ConfigHelper.reloadConfig();
                    ConfigHelper.saveConfig();
                    player.displayClientMessage(new TextComponent("World Stripper config reloaded"), false);
                } catch (Exception e) {
                    e.printStackTrace();
                    player.displayClientMessage(new TextComponent("Error! World Stripper config failed to reload"), false);
                }
                return 0;
            });
    }
}
