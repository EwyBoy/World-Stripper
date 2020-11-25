package com.ewyboy.worldstripper.common.network.messages;

import com.ewyboy.worldstripper.common.config.ConfigOptions;
import com.ewyboy.worldstripper.common.network.MessageHandler;
import com.ewyboy.worldstripper.common.config.temp.ConfigOld;
import com.ewyboy.worldstripper.common.util.Profile;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

public class MessageStripWorld {

    public MessageStripWorld() {}

    public static void encode(MessageStripWorld pkt, PacketBuffer buf) {}

    public static MessageStripWorld decode(PacketBuffer buf) {
        return new MessageStripWorld();
    }

    public static void handle(MessageStripWorld message, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            World world = player != null ? player.getEntityWorld() : null;
            // int profile = ConfigOld.SETTINGS.selectedProfile.get();
            double chunkClearSizeX = ConfigOptions.Stripping.blocksToStripX / 2;
            double chunkClearSizeZ = ConfigOptions.Stripping.blocksToStripZ / 2;

            if (player.isCreative()) {
                player.sendStatusMessage(new StringTextComponent(TextFormatting.BOLD + "" + TextFormatting.RED + "WARNING! " + TextFormatting.WHITE + "World Stripping Initialized! Lag May Occur.."), false);
                for(int x = (int)(player.getPosX() - chunkClearSizeX); (double)x <= player.getPosX() + chunkClearSizeX; ++x) {
                    for(int y = (int) (player.getPosY() + 16.00D); (double)y >= 0; y--) {
                        for(int z = (int)(player.getPosZ() - chunkClearSizeZ); (double)z <= player.getPosZ() + chunkClearSizeZ; ++z) {
                            BlockPos targetBlockPos = new BlockPos(x, y, z);
                            BlockState targetBlockState = world.getBlockState(targetBlockPos);
                            Block targetBlock = targetBlockState.getBlock();
                            if (!targetBlock.equals(Blocks.AIR)  || !targetBlock.equals(Blocks.TALL_GRASS)) {

                                //Stream stream = Arrays.stream(Profile.profileMapper.get(profile));

                                Stream stream = Arrays.stream(ConfigOptions.Profiles.profile1.toArray(new ResourceLocation[0]));
                                ResourceLocation targetBlockResource = new ResourceLocation(Objects.requireNonNull(targetBlock.getRegistryName()).toString());

                                stream.filter(targetBlockResource :: equals).forEachOrdered((s) -> {
                                    MessageHandler.hashedBlockCache.put(targetBlockPos, targetBlockState);
                                    world.setBlockState(targetBlockPos, Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ConfigOptions.Stripping.replacementBlock))).getDefaultState(), ConfigOptions.Stripping.updateFlag);
                                });
                            }
                        }
                    }
                }
                player.sendStatusMessage(new StringTextComponent("World Stripping Successfully Done!"), false);
            } else {
                player.sendStatusMessage(new StringTextComponent(TextFormatting.RED + "Error: You have to be in creative mode to use this feature!"), false);
            }

        });
        ctx.get().setPacketHandled(true);
    }
}
