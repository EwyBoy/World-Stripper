package com.ewyboy.worldstripper.common.network.messages;

import com.ewyboy.worldstripper.common.network.MessageHandler;
import com.ewyboy.worldstripper.common.util.Config;
import com.ewyboy.worldstripper.common.util.Profile;
import com.google.common.base.Stopwatch;
import javafx.scene.paint.Stop;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class MessageStripWorld {

    public static void encode(MessageStripWorld pkt, PacketBuffer buf) {}

    public static MessageStripWorld decode(PacketBuffer buf) {
        return new MessageStripWorld();
    }

    public static void handle(final MessageStripWorld message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {

            EntityPlayerMP player = ctx.get().getSender();
            World world = player != null ? player.getEntityWorld() : null;
            int profile = Config.COMMON.selectedProfile.get();

            double chunkClearSizeX = ((16 * Config.COMMON.chunkRadiusX.get()) / 2);
            double chunkClearSizeZ = ((16 * Config.COMMON.chunkRadiusZ.get()) / 2);

            if (player.isCreative()) {
                player.sendMessage(new TextComponentString(TextFormatting.BOLD + "" + TextFormatting.RED + "WARNING! " + TextFormatting.WHITE + "World Stripping Initialized! Lag May Occur.."));
                for (int x = (int) (player.getPosition().getX() - chunkClearSizeX); (double) x <= player.getPosition().getX() + chunkClearSizeX; x++) {
                    for (int y = 0; (double) y <= player.getPosition().getY() + 16; ++y) {
                        for (int z = (int) (player.getPosition().getZ() - chunkClearSizeZ); (double) z <= player.getPosition().getZ() + chunkClearSizeZ; z++) {
                            BlockPos targetBlockPos = new BlockPos(x, y, z);
                            IBlockState targetBlockState = world.getBlockState(targetBlockPos);
                            Block targetBlock = targetBlockState.getBlock();
                            if (!targetBlock.equals(Blocks.AIR) && !targetBlock.equals(Blocks.BEDROCK)) {
                                Arrays.stream(Profile.profileMapper.get(profile)).filter(targetBlock.getRegistryName().toString() :: equals).forEachOrdered(s -> {
                                    MessageHandler.hashedBlockCache.put(targetBlockPos, targetBlockState);
                                    world.setBlockState(targetBlockPos, Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Config.COMMON.replacementBlock.get()))).getDefaultState(), Config.COMMON.blockStateFlag.get());
                                });
                            }
                        }
                    }
                }
                player.sendMessage(new TextComponentString("World Stripping Successfully Done!"));
            } else {
                player.sendMessage(new TextComponentString(TextFormatting.RED + "Error: You have to be in creative mode to use this feature!"));
            }
        });

        ctx.get().setPacketHandled(true);
    }

}
