package com.ewyboy.worldstripper.common.network.messages;

import com.ewyboy.worldstripper.common.network.MessageHandler;
import com.ewyboy.worldstripper.common.util.Config;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageDressWorld {

    public static void encode(MessageDressWorld pkt, PacketBuffer buf) {}

    public static MessageDressWorld decode(PacketBuffer buf) {
        return new MessageDressWorld();
    }

    public static void handle(final MessageDressWorld message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {

            EntityPlayerMP player = ctx.get().getSender();
            World world = player != null ? player.getEntityWorld() : null;

            double chunkClearSizeX = ((16 * Config.COMMON.chunkRadiusX.get()) / 2);
            double chunkClearSizeZ = ((16 * Config.COMMON.chunkRadiusZ.get()) / 2);

            if (player.isCreative()) {
                player.sendMessage(new TextComponentString(TextFormatting.BOLD + "" + TextFormatting.RED + "WARNING! " + TextFormatting.WHITE + "World Dressing Initialized! Lag May Occur.."));
                for (int x = (int) (player.getPosition().getX() - chunkClearSizeX); (double) x <= player.getPosition().getX() + chunkClearSizeX; x++) {
                    for (int y = 0; (double) y <= player.getPosition().getY() + 16; ++y) {
                        for (int z = (int) (player.getPosition().getZ() - chunkClearSizeZ); (double) z <= player.getPosition().getZ() + chunkClearSizeZ; z++) {
                            BlockPos targetBlockPos = new BlockPos(x, y, z);
                            if (MessageHandler.hashedBlockCache.get(targetBlockPos) != null) {
                                world.setBlockState(targetBlockPos, MessageHandler.hashedBlockCache.get(targetBlockPos), 3);
                            }
                        }
                    }
                }
                player.sendMessage(new TextComponentString("World Dressing Successfully Done!"));
            } else {
                player.sendMessage(new TextComponentString(TextFormatting.RED + "Error: You have to be in creative mode to use this feature!"));
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
