package com.ewyboy.worldstripper.network.messages;

import com.ewyboy.worldstripper.json.StripListHandler;
import com.ewyboy.worldstripper.stripclub.StripperAccessories;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Supplier;

public class MessageAddBlock {

    public static void encode(MessageAddBlock pkt, PacketBuffer buf) {}

    public static MessageAddBlock decode(PacketBuffer buf) {
        return new MessageAddBlock();
    }

    public static void handle(MessageAddBlock message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null) {
                if(player.isSpectator() || player.isCreative()) {
                    BlockState state = StripperAccessories.getStateFromRaytrace();
                    if (state != null) {
                        String entry = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(state.getBlock())).toString();
                        if (StripListHandler.addEntry(entry)) {
                            player.sendMessage(new StringTextComponent(TextFormatting.GREEN + entry + TextFormatting.WHITE + " added to strip list"), ChatType.GAME_INFO, player.getUUID());
                        } else {
                            player.sendMessage(new StringTextComponent(TextFormatting.DARK_RED + "ERROR: " + TextFormatting.RED + entry + TextFormatting.WHITE + " is already found in strip list"), ChatType.GAME_INFO, player.getUUID());
                        }
                    }
                } else {
                    player.sendMessage(new StringTextComponent(TextFormatting.DARK_RED + "Error: " + TextFormatting.WHITE + "You have to be in creative mode to use this feature!"), ChatType.GAME_INFO, player.getUUID());
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
