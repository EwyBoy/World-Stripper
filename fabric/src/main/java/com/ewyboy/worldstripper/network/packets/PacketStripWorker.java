package com.ewyboy.worldstripper.network.packets;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.network.IPacket;
import com.ewyboy.worldstripper.network.MessageHandler;
import com.ewyboy.worldstripper.settings.Settings;
import com.ewyboy.worldstripper.settings.SettingsLoader;
import com.ewyboy.worldstripper.stripclub.BlockUpdater;
import com.ewyboy.worldstripper.stripclub.Profiles;
import com.ewyboy.worldstripper.workers.StripWorker;
import com.ewyboy.worldstripper.workers.WorldWorker;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.block.BlockState;
import net.minecraft.network.MessageType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.Objects;

public class PacketStripWorker implements IPacket {

    public static final Identifier ID = new Identifier(WorldStripper.MOD_ID, "strip_worker_packet");

    public PacketStripWorker() {}

    @Override
    public void read(PacketByteBuf packetByteBuf) {}

    @Override
    public void write(PacketByteBuf packetByteBuf) {}

    @Override
    public Identifier getID() {
        return ID;
    }

    public static class Handler extends MessageHandler<PacketStripWorker> {

        @Override
        protected PacketStripWorker create() {
            return new PacketStripWorker();
        }

        @Override
        public void handle(PacketContext ctx, PacketStripWorker packetStripWorker) {
            Settings settings = SettingsLoader.SETTINGS;
            ServerPlayerEntity player = (ServerPlayerEntity) ctx.getPlayer();

            int chunkClearSizeX = (settings.radiusX / 2);
            int chunkClearSizeZ = (settings.radiusZ / 2);

            BlockState replacementBlock = Objects.requireNonNull(Registry.BLOCK.get(new Identifier(settings.replacementBlock))).getDefaultState();
            List<String> stripList = Profiles.profileMapper.get(settings.selectedProfile);

            if (player != null) {
                if (player.isSpectator() || player.isCreative()) {
                    player.sendMessage(new LiteralText(Formatting.BOLD + "" + Formatting.RED + "WARNING! " + Formatting.WHITE + "World Stripping Initialized! Lag May Occur.."), MessageType.GAME_INFO, player.getUuid());
                    WorldWorker.addWorker(new StripWorker(new BlockPos(player.getPos()), chunkClearSizeX, chunkClearSizeZ, player.getServerWorld(), 4096, BlockUpdater.getBlockUpdateFlag(), replacementBlock, stripList));
                } else {
                    player.sendMessage(new LiteralText(Formatting.RED + "Error: You have to be in creative mode to use this feature!"), MessageType.GAME_INFO, player.getUuid());
                }
            }
        }
    }
}
