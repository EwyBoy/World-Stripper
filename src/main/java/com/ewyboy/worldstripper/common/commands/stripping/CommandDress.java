package com.ewyboy.worldstripper.common.commands.stripping;

import com.ewyboy.worldstripper.common.config.ConfigOptions;
import com.ewyboy.worldstripper.common.network.MessageHandler;
import com.ewyboy.worldstripper.common.network.messages.stripping.MessageDressWorker;
import com.ewyboy.worldstripper.common.network.messages.stripping.MessageDressWorld;
import com.ewyboy.worldstripper.common.network.messages.stripping.MessageStripWorker;
import com.ewyboy.worldstripper.common.network.messages.stripping.MessageStripWorld;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class CommandDress {

    public static ArgumentBuilder<CommandSource, ?> register() {

        return Commands.literal("dress").requires(cs -> cs.hasPermissionLevel(2)).executes(context -> {
            MessageHandler.CHANNEL.sendToServer(ConfigOptions.Stripping.liveStripping ? new MessageDressWorker() : new MessageDressWorld());
            return 0;
        }).then(Commands.argument("x", IntegerArgumentType.integer(0, 512)).then(Commands.argument("z", IntegerArgumentType.integer(0, 512)).executes(context -> {
            if (ConfigOptions.Stripping.liveStripping) {
                MessageHandler.CHANNEL.sendToServer(new MessageDressWorker(IntegerArgumentType.getInteger(context, "x"), IntegerArgumentType.getInteger(context, "z")));
            } else {
                MessageHandler.CHANNEL.sendToServer(new MessageDressWorld(IntegerArgumentType.getInteger(context, "x"), IntegerArgumentType.getInteger(context, "z")));
            }
            return 0;
        })));
    }

}

