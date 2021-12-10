package com.ewyboy.worldstripper.network.messages;

import com.ewyboy.worldstripper.json.StripListHandler;
import com.ewyboy.worldstripper.settings.Settings;
import com.ewyboy.worldstripper.stripclub.BlockUpdater;
import com.ewyboy.worldstripper.workers.StripWorker;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.WorldWorkerManager;

import net.minecraftforge.network.NetworkEvent;
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

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.z);
    }

    public static MessageStripWorker decode(FriendlyByteBuf buf) {
        return new MessageStripWorker(buf.readInt(), buf.readInt());
    }

    public static void handle(MessageStripWorker message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();

            int chunkClearSizeX = (Settings.SETTINGS.stripRadiusX.get() / 2);
            int chunkClearSizeZ = (Settings.SETTINGS.stripRadiusZ.get() / 2);

            BlockState replacementBlock = Objects.requireNonNull(Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Settings.SETTINGS.replacementBlock.get()))).defaultBlockState());
            List<String> stripList = StripListHandler.stripList.getEntries();

            if (player != null) {
                if (player.isSpectator() || player.isCreative()) {
                    player.sendMessage(new TextComponent(ChatFormatting.BOLD + "" + ChatFormatting.RED + "WARNING! " + ChatFormatting.WHITE + "World Stripping Initialized! Lag May Occur.."), ChatType.GAME_INFO, player.getUUID());
                    WorldWorkerManager.addWorker(new StripWorker(new BlockPos(player.position()), chunkClearSizeX, chunkClearSizeZ, player.getLevel(), 4096, BlockUpdater.getBlockUpdateFlag(), replacementBlock, stripList));
                } else {
                    player.sendMessage(new TextComponent(ChatFormatting.RED + "Error: You have to be in creative mode to use this feature!"), ChatType.GAME_INFO, player.getUUID());
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
