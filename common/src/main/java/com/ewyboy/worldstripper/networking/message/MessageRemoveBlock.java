package com.ewyboy.worldstripper.networking.message;

import com.ewyboy.worldstripper.club.StripperAccessories;
import com.ewyboy.worldstripper.json.StrippablesHandler;
import com.ewyboy.worldstripper.networking.NetworkHandler;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseC2SMessage;
import dev.architectury.networking.simple.MessageType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;

public class MessageRemoveBlock extends BaseC2SMessage {

    public MessageRemoveBlock() {}

    public MessageRemoveBlock(FriendlyByteBuf friendlyByteBuf) {}

    @Override
    public MessageType getType() {
        return NetworkHandler.REMOVE_BLOCK;
    }

    @Override
    public void write(FriendlyByteBuf buf) {}

    @Override
    public void handle(NetworkManager.PacketContext ctx) {
        ServerPlayer player = (ServerPlayer) ctx.getPlayer();

        if(player.isCreative()) {
            BlockState state = StripperAccessories.getStateFromRaytrace();
            if (state != null) {
                String entry = BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();
                if (StrippablesHandler.removeEntry(entry)) {
                    player.displayClientMessage(Component.literal(ChatFormatting.RED + entry + ChatFormatting.WHITE + " removed from config"), true);
                } else {
                    player.displayClientMessage(Component.literal(ChatFormatting.DARK_RED + "ERROR: " + ChatFormatting.RED + entry + ChatFormatting.WHITE + " not found in config"), true);
                }
            }
        } else {
            player.displayClientMessage(Component.literal(ChatFormatting.DARK_RED + "Error: " + ChatFormatting.WHITE + "You have to be in creative mode to use this feature!"), true);
        }
    }
}
