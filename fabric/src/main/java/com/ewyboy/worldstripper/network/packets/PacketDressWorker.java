package com.ewyboy.worldstripper.network.packets;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.network.IPacket;
import com.ewyboy.worldstripper.network.MessageHandler;
import com.ewyboy.worldstripper.settings.Settings;
import com.ewyboy.worldstripper.settings.SettingsLoader;
import com.ewyboy.worldstripper.stripclub.BlockUpdater;
import com.ewyboy.worldstripper.workers.DressWorker;
import com.ewyboy.worldstripper.workers.WorldWorker;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.MessageType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class PacketDressWorker implements IPacket {

    public static final Identifier ID = new Identifier(WorldStripper.MOD_ID, "dress_worker_packet");

    public PacketDressWorker() {}

    @Override
    public void read(PacketByteBuf packetByteBuf) {}

    @Override
    public void write(PacketByteBuf packetByteBuf) {}

    @Override
    public Identifier getID() {
        return ID;
    }

    public static class Handler extends MessageHandler<PacketDressWorker> {

        @Override
        protected PacketDressWorker create() {
            return new PacketDressWorker();
        }

        @Override
        public void handle(PacketContext ctx, PacketDressWorker packetDressWorld) {
            Settings settings = SettingsLoader.SETTINGS;
            ServerPlayerEntity player = (ServerPlayerEntity) ctx.getPlayer();

            int fillSizeX = (settings.radiusX / 2);
            int fillSizeZ = (settings.radiusZ / 2);

            if (player != null) {
                if (player.isSpectator() || player.isCreative()) {
                    player.sendMessage(new LiteralText(Formatting.BOLD + "" + Formatting.RED + "WARNING! " + Formatting.WHITE + "World Dressing Initialized! Lag May Occur.."), MessageType.GAME_INFO, player.getUuid());
                    WorldWorker.addWorker(new DressWorker(new BlockPos(player.getPos()), fillSizeX, fillSizeZ, player.getServerWorld(), 4096, BlockUpdater.getBlockUpdateFlag()));
                } else {
                    player.sendMessage(new LiteralText(Formatting.RED + "Error: You have to be in creative mode to use this feature!"), MessageType.GAME_INFO, player.getUuid());
                }
            }
        }
    }

}
