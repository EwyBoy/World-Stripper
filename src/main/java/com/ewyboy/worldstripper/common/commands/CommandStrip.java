package com.ewyboy.worldstripper.common.commands;

import com.ewyboy.worldstripper.common.network.MessageHandler;
import com.ewyboy.worldstripper.common.network.messages.MessageOpenConfig;
import com.ewyboy.worldstripper.common.network.messages.MessageStripWorld;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.PacketDistributor;

public class CommandStrip {

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("strips")
            .requires(cs -> cs.hasPermissionLevel(2))
            .executes(context -> {
                    MessageHandler.CHANNEL.sendToServer(new MessageStripWorld(2, 2));
                    return 0;
                }
            ).then(
                    Commands.argument("x", IntegerArgumentType.integer(0, 512)
            ).then(
                    Commands.argument("z", IntegerArgumentType.integer(0, 512)
                    ).executes(context -> {
                        System.out.println(IntegerArgumentType.getInteger(context, "x"));
                        System.out.println(IntegerArgumentType.getInteger(context, "z"));
                                MessageHandler.CHANNEL.sendToServer(new MessageStripWorld(IntegerArgumentType.getInteger(context,"x"), IntegerArgumentType.getInteger(context, "z")));
                                return 0;
                            }
                    )
                    )
                );
    }
}

