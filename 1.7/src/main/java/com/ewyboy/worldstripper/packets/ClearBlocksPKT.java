package com.ewyboy.worldstripper.packets;

import com.ewyboy.worldstripper.BlockCounter;
import com.ewyboy.worldstripper.CommonProxy;
import com.ewyboy.worldstripper.RemovedBlockCache;
import com.ewyboy.worldstripper.Utils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ClearBlocksPKT implements IMessage, IMessageHandler<ClearBlocksPKT, IMessage> {

    private boolean isSneaking;

    public ClearBlocksPKT() {}

    public ClearBlocksPKT(boolean isSneaking) {
        this.isSneaking = isSneaking;
    }

    public IMessage onMessage(ClearBlocksPKT pkt, MessageContext ctx) {
        System.out.println("Starting world stripping.");

        World world = ctx.getServerHandler().playerEntity.worldObj;
        EntityPlayer player = ctx.getServerHandler().playerEntity;

        if (!pkt.isSneaking) {
            player.addChatComponentMessage(new ChatComponentText("Chunk-stripping mode: default"));
        } else {
            player.addChatComponentMessage(new ChatComponentText("Chunk-stripping mode: ore-dictionary"));
        }

        RemovedBlockCache cache = new RemovedBlockCache();
        BlockCounter blockCounter = new BlockCounter();
        long counter = 0L;

        for(int x = (int)(player.posX - 32.0D); (double)x <= player.posX + 32.0D; ++x) {
            for(int y = 0; (double)y <= player.posY + 32.0D; ++y) {
                for(int z = (int)(player.posZ - 32.0D); (double)z <= player.posZ + 32.0D; ++z) {
                    Block b = world.getBlock(x, y, z);
                    if (b != Blocks.bedrock && b != Blocks.air) {
                        boolean toRemove = true;
                        if (pkt.isSneaking) {
                            int[] arr$ = OreDictionary.getOreIDs(new ItemStack(b));
                            for (int id : arr$) {
                                if (OreDictionary.getOreName(id).startsWith("ore")) {
                                    blockCounter.increaseCount(b);
                                    toRemove = false;
                                    break;
                                }
                            }
                        } else if (!Utils.BLOCK_LIST.contains(b)) {
                            blockCounter.increaseCount(b);
                            toRemove = false;
                        } if (toRemove) {
                            cache.addToCache(x, y, z, b, world.getBlockMetadata(x, y, z));
                            world.setBlockToAir(x, y, z);
                            ++counter;
                        }
                    }
                }
            }
        }
        CommonProxy.addPlayerRemovedBlockCache(player, cache);
        player.addChatComponentMessage(new ChatComponentText(blockCounter.toString()));
        System.out.println("Done! Removed: " + counter + " blocks");
        return null;
    }

    public void fromBytes(ByteBuf buf) {
        this.isSneaking = buf.readBoolean();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.isSneaking);
    }
}