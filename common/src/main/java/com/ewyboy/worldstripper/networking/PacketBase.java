package com.ewyboy.worldstripper.networking;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public interface PacketBase extends CustomPacketPayload {

    void handlePacket(Player player);

}