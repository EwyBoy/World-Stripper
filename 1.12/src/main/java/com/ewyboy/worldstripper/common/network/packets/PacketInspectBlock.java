package com.ewyboy.worldstripper.common.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * Created by EwyBoy
 */
public class PacketInspectBlock implements IMessage {

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<PacketInspectBlock, IMessage> {

        @Override
        public IMessage onMessage(PacketInspectBlock message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(ctx));
            return null;
        }

        private void handle(MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().player;
            World world = player.world;

            BlockPos pos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
            IBlockState state = world.getBlockState(pos);

            String stateString;

            if (player.isCreative()) {
                stateString = player.isSneaking() ? state.toString() : state.getBlock().getRegistryName().toString();

                StringSelection selection = new StringSelection(stateString);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);

                player.sendMessage(new TextComponentString("Block:\n" + TextFormatting.GREEN + stateString +  "\nCopied to clipboard\n" + TextFormatting.GREEN + "----------------"));
            }
        }
    }
}
