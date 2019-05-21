package com.ewyboy.worldstripper.common.network.packets;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketInspectBlock message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(ctx));
            return null;
        }

        @SideOnly(Side.CLIENT)
        private void handle(MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            World world = player.worldObj;

            BlockPos pos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
            IBlockState state = world.getBlockState(pos);

            String stateString;

            if (player.capabilities.isCreativeMode) {
                stateString = player.isSneaking() ? state.toString() : state.getBlock().getRegistryName();

                StringSelection selection = new StringSelection(stateString);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);

                player.addChatMessage(new ChatComponentText("Block:\n" + ChatFormatting.GREEN + stateString + "\nCopied to clipboard\n" + ChatFormatting.GREEN + "----------------"));
            }
        }
    }
}
