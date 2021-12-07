package com.ewyboy.worldstripper.common.network.messages;

import com.ewyboy.worldstripper.common.settings.Settings;
import com.ewyboy.worldstripper.common.workers.DressWorker;
import com.ewyboy.worldstripper.common.stripclub.BlockUpdater;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.WorldWorkerManager;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class MessageDressWorker {

    public int x;
    public int z;

    public MessageDressWorker() {
        this.x = -1;
        this.z = -1;
    }

    public MessageDressWorker(int x, int z) {
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

    public static MessageDressWorker decode(PacketBuffer buf) {
        return new MessageDressWorker(buf.readInt(), buf.readInt());
    }

    public static void handle(MessageDressWorker message, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            int fillSizeX = (Settings.SETTINGS.stripRadiusX.get() / 2);
            int fillSizeZ = (Settings.SETTINGS.stripRadiusZ.get() / 2);

            if (player != null) {
                if (player.isSpectator() || player.isCreative()) {
                    player.sendMessage(new StringTextComponent(TextFormatting.BOLD + "" + TextFormatting.RED + "WARNING! " + TextFormatting.WHITE + "World Dressing Initialized! Lag May Occur.."), ChatType.GAME_INFO, player.getUUID());
                    WorldWorkerManager.addWorker(new DressWorker(new BlockPos(player.position()), fillSizeX, fillSizeZ, player.getLevel(), 4096, BlockUpdater.getBlockUpdateFlag()));
                } else {
                    player.sendMessage(new StringTextComponent(TextFormatting.RED + "Error: You have to be in creative mode to use this feature!"), ChatType.GAME_INFO, player.getUUID());
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
