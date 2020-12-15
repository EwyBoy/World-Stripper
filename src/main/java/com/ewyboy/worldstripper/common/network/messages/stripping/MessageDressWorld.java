package com.ewyboy.worldstripper.common.network.messages.stripping;

import com.ewyboy.worldstripper.common.config.ConfigOptions;
import com.ewyboy.worldstripper.common.network.MessageHandler;

import java.util.function.Supplier;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent.Context;

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

    public void encode(PacketBuffer buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.z);
    }

    public static MessageDressWorld decode(PacketBuffer buf) {
        return new MessageDressWorld(buf.readInt(), buf.readInt());
    }

    public static void handle(MessageDressWorld message, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            World world = player != null ? player.getEntityWorld() : null;

            double chunkClearSizeX = ConfigOptions.Stripping.blocksToStripX / 2;
            double chunkClearSizeZ = ConfigOptions.Stripping.blocksToStripZ / 2;

            if(player.isCreative() || player.isSpectator()) {
                player.sendStatusMessage(new StringTextComponent(TextFormatting.BOLD + "" + TextFormatting.RED + "WARNING! " + TextFormatting.WHITE + "World Dressing Initialized! Lag May Occur.."), false);

                for(int x = (int) (player.getPosX() - chunkClearSizeX); (double) x <= player.getPosX() + chunkClearSizeX; ++x) {
                    for(int y = 0; (double) y <= player.getPosY() + 16.0D; ++y) {
                        for(int z = (int) (player.getPosZ() - chunkClearSizeZ); (double) z <= player.getPosZ() + chunkClearSizeZ; ++z) {
                            BlockPos targetBlockPos = new BlockPos(x, y, z);
                            if(MessageHandler.hashedBlockCache.get(targetBlockPos) != null) {
                                world.setBlockState(targetBlockPos, MessageHandler.hashedBlockCache.get(targetBlockPos), 3);
                            }
                        }
                    }
                }
                player.sendStatusMessage(new StringTextComponent("World Dressing Successfully Executed!"), false);
            } else {
                player.sendStatusMessage(new StringTextComponent(TextFormatting.RED + "Error: You have to be in creative mode to use this feature!"), false);
            }

        });
        ctx.get().setPacketHandled(true);
    }

}
