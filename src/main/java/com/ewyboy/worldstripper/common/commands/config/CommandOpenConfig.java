package com.ewyboy.worldstripper.common.commands.config;

import com.ewyboy.worldstripper.common.network.MessageHandler;
import com.ewyboy.worldstripper.common.network.messages.config.MessageOpenConfig;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.PacketDistributor;

public class CommandOpenConfig {

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("config")
            .requires(cs -> cs.hasPermissionLevel(2))
            .executes(context -> {
                ServerPlayerEntity player = context.getSource().asPlayer();
                MessageHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new MessageOpenConfig());

                return 0;
            });
    }
}
