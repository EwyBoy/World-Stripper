package com.ewyboy.worldstripper.common.network.packets;

import com.ewyboy.worldstripper.common.util.BlockCacher;
import com.ewyboy.worldstripper.common.util.BlocksToStrip;
import com.ewyboy.worldstripper.common.util.Config;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by EwyBoy
 **/
public class PacketStripWorld implements IMessage {

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<PacketStripWorld, IMessage> {

        @Override
        public IMessage onMessage(final PacketStripWorld message, final MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketStripWorld message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            World world = player.worldObj;

            double chunkClearSize = ((16 * Config.chuckRadius) / 2);

            player.addChatComponentMessage(new TextComponentString(TextFormatting.BOLD + "" + TextFormatting.RED + "WARNING! " + TextFormatting.WHITE + "World Stripping Initialized! Lag May Occur.."));

            if (player.isCreative()) {
                for (int x = (int)(player.getPosition().getX() - chunkClearSize); (double)x <= player.getPosition().getX() + chunkClearSize; x++) {
                    for (int y = 0; (double)y <= player.getPosition().getY() + chunkClearSize; ++y) {
                        for (int z = (int)(player.getPosition().getZ() - chunkClearSize); (double)z <= player.getPosition().getZ() + chunkClearSize; z++) {
                            BlockPos targetBlockPos = new BlockPos(x,y,z);
                            IBlockState targetBlockState = world.getBlockState(targetBlockPos);
                            Block targetBlock = targetBlockState.getBlock();
                            if (!targetBlock.equals(Blocks.BEDROCK) || !targetBlock.equals(Blocks.AIR)) {
                                BlocksToStrip.blockList.stream().filter(targetBlock :: equals).forEachOrdered(blockList -> {
                                    BlockCacher.hashedBlockCache.put(targetBlockPos, targetBlockState);
                                    world.setBlockToAir(targetBlockPos);
                                });
                            }
                        }
                    }
                }
                player.addChatComponentMessage(new TextComponentString("World Stripping Successfully Done!"));
            } else {
                player.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "Error: You have to be in creative mode to use this feature!"));
            }
        }
    }
}
