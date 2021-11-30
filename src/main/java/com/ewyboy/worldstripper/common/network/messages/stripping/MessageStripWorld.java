package com.ewyboy.worldstripper.common.network.messages.stripping;

import com.ewyboy.worldstripper.common.config.ConfigHelper;
import com.ewyboy.worldstripper.common.config.ConfigOptions;
import com.ewyboy.worldstripper.common.network.MessageHandler;
import com.ewyboy.worldstripper.common.stripclub.BlockUpdater;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MessageStripWorld {

    public int x;
    public int z;

    public MessageStripWorld() {
        this.x = -1;
        this.z = -1;
    }

    public MessageStripWorld(int x, int z) {
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

    public static MessageStripWorld decode(FriendlyByteBuf buf) {
        return new MessageStripWorld(buf.readInt(), buf.readInt());
    }

    public static void handle(MessageStripWorld message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            Level world = player != null ? player.getCommandSenderWorld() : null;
            List<String> profileList = ConfigHelper.profileMap.get(ConfigOptions.Profiles.profile);

            int blockUpdateFlag = BlockUpdater.getBlockUpdateFlag();

            double chunkClearSizeX = message.getX() < 0 ? ConfigOptions.Stripping.blocksToStripX : message.getX();
            double chunkClearSizeZ = message.getZ() < 0 ? ConfigOptions.Stripping.blocksToStripZ : message.getZ();

            if(player.isCreative()) {
                player.displayClientMessage(new TextComponent(ChatFormatting.BOLD + "" + ChatFormatting.RED + "WARNING! " + ChatFormatting.WHITE + "World Stripping Initialized! Lag May Occur.."), false);
                for(int x = (int) (player.getX() - chunkClearSizeX); (double) x <= player.getX() + chunkClearSizeX; ++x) {
                    for(int y = (int) (player.getY() + 16.00D); (double) y >= 0; y--) {
                        for(int z = (int) (player.getZ() - chunkClearSizeZ); (double) z <= player.getZ() + chunkClearSizeZ; ++z) {
                            BlockPos targetBlockPos = new BlockPos(x, y, z);
                            BlockState targetBlockState = world.getBlockState(targetBlockPos);
                            Block targetBlock = targetBlockState.getBlock();
                            if(!targetBlock.equals(Blocks.AIR) || !targetBlock.equals(Blocks.TALL_GRASS)) {

                                Stream<String> stream = Arrays.stream(profileList.toArray(new String[0]));
                                ResourceLocation targetBlockResource = new ResourceLocation(Objects.requireNonNull(targetBlock.getRegistryName()).toString());

                                stream.map(ResourceLocation :: new).filter(targetBlockResource :: equals).forEachOrdered((s) -> {
                                    MessageHandler.hashedBlockCache.put(targetBlockPos, targetBlockState);
                                    BlockState newState = Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ConfigOptions.Stripping.replacementBlock))).defaultBlockState();
                                    world.setBlock(targetBlockPos, newState, blockUpdateFlag);
                                });
                            }
                        }
                    }
                }
                player.displayClientMessage(new TextComponent("World Stripping Successfully Executed!"), false);
            } else {
                player.displayClientMessage(new TextComponent(ChatFormatting.RED + "Error: You have to be in creative mode to use this feature!"), false);
            }

        });
        ctx.get().setPacketHandled(true);
    }

}
