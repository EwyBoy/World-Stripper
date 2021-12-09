package com.ewyboy.worldstripper.network.messages;


import com.ewyboy.worldstripper.json.StripListHandler;
import com.ewyboy.worldstripper.stripclub.StripperAccessories;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

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
                        String entry = Registry.BLOCK.getKey(state.getBlock()).toString();
                        if (StripListHandler.removeEntry(entry)) {
                            player.displayClientMessage(new TextComponent(ChatFormatting.RED + entry + ChatFormatting.WHITE + " removed from config"), true);
                        } else {
                            player.sendMessage(new TextComponent(ChatFormatting.DARK_RED + "ERROR: " + ChatFormatting.RED + entry + ChatFormatting.WHITE + " not found in config"), ChatType.GAME_INFO, player.getUUID());
                        }
                    }
                } else {
                    player.sendMessage(new TextComponent(ChatFormatting.DARK_RED + "Error: " + ChatFormatting.WHITE + "You have to be in creative mode to use this feature!"), ChatType.GAME_INFO, player.getUUID());
                }
            }

        });
        ctx.get().setPacketHandled(true);
    }

}
