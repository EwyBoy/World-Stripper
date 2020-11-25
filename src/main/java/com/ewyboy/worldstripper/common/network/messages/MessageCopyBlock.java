package com.ewyboy.worldstripper.common.network.messages;

import com.ewyboy.worldstripper.common.config.temp.ConfigOld;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Supplier;

public class MessageCopyBlock {

    public MessageCopyBlock() {}

    public static void encode(MessageCopyBlock pkt, PacketBuffer buf) {}

    public static MessageCopyBlock decode(PacketBuffer buf) {
        return new MessageCopyBlock();
    }

    public static void handle(MessageCopyBlock message, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            World world = player != null ? player.getEntityWorld() : null;

            if (player.isCreative()) {
                BlockPos targetBlockPos = new BlockPos(player.getPosX(), player.getPosY(), player.getPosZ()).down();
                BlockState targetBlockState = world.getBlockState(targetBlockPos);
                Block targetBlock = targetBlockState.getBlock();
                if (!targetBlock.equals(Blocks.AIR)) {
                    world.setBlockState(targetBlockPos, Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ConfigOld.SETTINGS.replacementBlock.get()))).getDefaultState(), ConfigOld.SETTINGS.blockStateFlag.get());
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
