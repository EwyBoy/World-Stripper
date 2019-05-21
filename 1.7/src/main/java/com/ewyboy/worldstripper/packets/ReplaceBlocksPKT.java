package com.ewyboy.worldstripper.packets;

import com.ewyboy.worldstripper.CommonProxy;
import com.ewyboy.worldstripper.RemovedBlockCache;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ReplaceBlocksPKT implements IMessage, IMessageHandler<ReplaceBlocksPKT, IMessage> {

    public ReplaceBlocksPKT() {}

    public IMessage onMessage(ReplaceBlocksPKT message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        World world = player.worldObj;
        RemovedBlockCache cache = CommonProxy.getPlayerRemovedBlockCache(player);
        if (cache != null) {
            System.out.println("Replacing cached blocks...");
            cache.replaceBlocks(world);
            System.out.println("Done!");
        } else {
            System.out.println("No cached blocks to replace!");
        }
        return null;
    }

    public void fromBytes(ByteBuf buf) {}

    public void toBytes(ByteBuf buf) {}
}