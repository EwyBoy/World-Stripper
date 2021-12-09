package com.ewyboy.worldstripper.network.messages;

import com.ewyboy.worldstripper.json.StripListHandler;
import com.ewyboy.worldstripper.settings.Settings;
import com.ewyboy.worldstripper.workers.StripWorker;
import com.ewyboy.worldstripper.stripclub.BlockUpdater;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.WorldWorkerManager;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class MessageStripWorker {

    public int x;
    public int z;

    public MessageStripWorker() {
        this.x = -1;
        this.z = -1;
    }

    public MessageStripWorker(int x, int z) {
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

    public void encode(PacketBuffer buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.z);
    }

    public static MessageStripWorker decode(PacketBuffer buf) {
        return new MessageStripWorker(buf.readInt(), buf.readInt());
    }

    public static void handle(MessageStripWorker message, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();

            int chunkClearSizeX = (Settings.SETTINGS.stripRadiusX.get() / 2);
            int chunkClearSizeZ = (Settings.SETTINGS.stripRadiusZ.get() / 2);

            BlockState replacementBlock = Objects.requireNonNull(Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Settings.SETTINGS.replacementBlock.get()))).defaultBlockState());
            List<String> stripList = StripListHandler.stripList.getEntries();

            if (player != null) {
                if (player.isSpectator() || player.isCreative()) {
                    player.sendMessage(new StringTextComponent(TextFormatting.BOLD + "" + TextFormatting.RED + "WARNING! " + TextFormatting.WHITE + "World Stripping Initialized! Lag May Occur.."), ChatType.GAME_INFO, player.getUUID());
                    WorldWorkerManager.addWorker(new StripWorker(new BlockPos(player.position()), chunkClearSizeX, chunkClearSizeZ, player.getLevel(), 4096, BlockUpdater.getBlockUpdateFlag(), replacementBlock, stripList));
                } else {
                    player.sendMessage(new StringTextComponent(TextFormatting.RED + "Error: You have to be in creative mode to use this feature!"), ChatType.GAME_INFO, player.getUUID());
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
