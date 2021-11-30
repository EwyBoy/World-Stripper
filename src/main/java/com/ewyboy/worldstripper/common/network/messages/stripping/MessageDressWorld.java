package com.ewyboy.worldstripper.common.network.messages.stripping;

import com.ewyboy.worldstripper.common.config.ConfigOptions;
import com.ewyboy.worldstripper.common.network.MessageHandler;

import java.util.function.Supplier;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

public class MessageDressWorld {

    public int x;
    public int z;

    public MessageDressWorld() {
        this.x = -1;
        this.z = -1;
    }

    public MessageDressWorld(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.z);
    }

    public static MessageDressWorld decode(FriendlyByteBuf buf) {
        return new MessageDressWorld(buf.readInt(), buf.readInt());
    }

    public static void handle(MessageDressWorld message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            Level world = player != null ? player.getCommandSenderWorld() : null;

            double chunkClearSizeX = ConfigOptions.Stripping.blocksToStripX / 2;
            double chunkClearSizeZ = ConfigOptions.Stripping.blocksToStripZ / 2;

            if(player.isCreative() || player.isSpectator()) {
                player.displayClientMessage(new TextComponent(ChatFormatting.BOLD + "" + ChatFormatting.RED + "WARNING! " + ChatFormatting.WHITE + "World Dressing Initialized! Lag May Occur.."), false);

                for(int x = (int) (player.getX() - chunkClearSizeX); (double) x <= player.getX() + chunkClearSizeX; ++x) {
                    for(int y = 0; (double) y <= player.getY() + 16.0D; ++y) {
                        for(int z = (int) (player.getZ() - chunkClearSizeZ); (double) z <= player.getZ() + chunkClearSizeZ; ++z) {
                            BlockPos targetBlockPos = new BlockPos(x, y, z);
                            if(MessageHandler.hashedBlockCache.get(targetBlockPos) != null) {
                                world.setBlock(targetBlockPos, MessageHandler.hashedBlockCache.get(targetBlockPos), 3);
                            }
                        }
                    }
                }
                player.displayClientMessage(new TextComponent("World Dressing Successfully Executed!"), false);
            } else {
                player.displayClientMessage(new TextComponent(ChatFormatting.RED + "Error: You have to be in creative mode to use this feature!"), false);
            }

        });
        ctx.get().setPacketHandled(true);
    }

}
