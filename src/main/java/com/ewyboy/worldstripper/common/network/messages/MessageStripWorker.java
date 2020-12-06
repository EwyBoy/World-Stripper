package com.ewyboy.worldstripper.common.network.messages;

import com.ewyboy.worldstripper.common.WorldStrippingWorker;
import com.ewyboy.worldstripper.common.config.ConfigOptions;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.WorldWorkerManager;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class MessageStripWorker {

    public MessageStripWorker() {}

    public static void encode(MessageStripWorker pkt, PacketBuffer buf) {}

    public static MessageStripWorker decode(PacketBuffer buf) {
        return new MessageStripWorker();
    }

    public static void handle(MessageStripWorker message, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();

            if (player.isSpectator() || player.isCreative()) {
                // Start
                player.sendStatusMessage(new StringTextComponent(TextFormatting.BOLD + "" + TextFormatting.RED + "WARNING! " + TextFormatting.WHITE + "World Stripping Initialized! Lag May Occur.."), false);

                WorldWorkerManager.addWorker(new WorldStrippingWorker(player.getCommandSource(), player.getPosition(), ConfigOptions.Stripping.blocksToStripX / 2, player.getServerWorld(), 4096)); // 16^3 sends a message every X blocks i think

                // Done
                player.sendStatusMessage(new StringTextComponent("World Stripping Successfully Done!"), false);
            } else {
                player.sendStatusMessage(new StringTextComponent(TextFormatting.RED + "Error: You have to be in creative mode to use this feature!"), false);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
