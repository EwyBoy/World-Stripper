package com.ewyboy.worldstripper.network.messages;


import com.ewyboy.worldstripper.json.StripListHandler;
import com.ewyboy.worldstripper.stripclub.StripperAccessories;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageRemoveBlock {

    public MessageRemoveBlock() {}

    public static void encode(MessageRemoveBlock pkt, FriendlyByteBuf buf) {}

    public static MessageRemoveBlock decode(FriendlyByteBuf buf) {
        return new MessageRemoveBlock();
    }

    public static void handle(MessageRemoveBlock message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                if(player.isSpectator() || player.isCreative()) {
                    BlockState state = StripperAccessories.getStateFromRaytrace();
                    if (state != null) {
                        String entry = BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();
                        if (StripListHandler.removeEntry(entry)) {
                            player.sendSystemMessage(Component.literal(ChatFormatting.RED + entry + ChatFormatting.WHITE + " removed from config"));
                        } else {
                            player.sendSystemMessage(Component.literal(ChatFormatting.DARK_RED + "ERROR: " + ChatFormatting.RED + entry + ChatFormatting.WHITE + " not found in config"));
                        }
                    }
                } else {
                    player.sendSystemMessage(Component.literal(ChatFormatting.DARK_RED + "Error: " + ChatFormatting.WHITE + "You have to be in creative mode to use this feature!"));
                }
            }

        });
        ctx.get().setPacketHandled(true);
    }

}
