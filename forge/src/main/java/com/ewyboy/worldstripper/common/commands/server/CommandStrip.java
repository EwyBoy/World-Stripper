package com.ewyboy.worldstripper.common.commands.server;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class CommandStrip {

    public static ArgumentBuilder<CommandSource, ?> register() {

        return Commands.literal("strip").requires(cs -> cs.hasPermission(2)).executes(context -> {
            //MessageHandler.CHANNEL.sendToServer(ConfigOptions.Stripping.liveStripping ? new MessageStripWorker() : new MessageStripWorld());
            return 0;
        }).then(Commands.argument("x", IntegerArgumentType.integer(0, 512)).then(Commands.argument("z", IntegerArgumentType.integer(0, 512)).executes(context -> {
            /*if (ConfigOptions.Stripping.liveStripping) {
                MessageHandler.CHANNEL.sendToServer(new MessageStripWorker(IntegerArgumentType.getInteger(context, "x"), IntegerArgumentType.getInteger(context, "z")));
            } else {
                MessageHandler.CHANNEL.sendToServer(new MessageStripWorld(IntegerArgumentType.getInteger(context, "x"), IntegerArgumentType.getInteger(context, "z")));
            }*/
            return 0;
        })));
    }

}

