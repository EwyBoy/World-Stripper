package com.ewyboy.worldstripper;

import com.ewyboy.worldstripper.client.Keymappings;
import com.ewyboy.worldstripper.networking.Packets;
import com.ewyboy.worldstripper.networking.packets.ServerboundAddBlockKeyPressedPacket;
import com.ewyboy.worldstripper.networking.packets.ServerboundDressWorldKeyPressedPacket;
import com.ewyboy.worldstripper.networking.packets.ServerboundRemoveBlockKeyPressedPacket;
import com.ewyboy.worldstripper.networking.packets.ServerboundStripWorldKeyPressedPacket;
import com.ewyboy.worldstripper.services.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class WorldStripperClient {

    public static void checkForKeymappings() {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null) {
            return;
        }

        if (Keymappings.strip_world.consumeClick()) {
            Services.PLATFORM.sendPacketToServer(Packets.PACKET_ID_STRIP_KEY_PRESSED, new ServerboundStripWorldKeyPressedPacket(true));
        } else if (Keymappings.dress_world.consumeClick()) {
            Services.PLATFORM.sendPacketToServer(Packets.PACKET_ID_DRESS_KEY_PRESSED, new ServerboundDressWorldKeyPressedPacket(true));
        } else if (Keymappings.add_block.consumeClick()) {
            Services.PLATFORM.sendPacketToServer(Packets.PACKET_ID_ADD_BLOCK_KEY_PRESSED, new ServerboundAddBlockKeyPressedPacket(true));
        } else if (Keymappings.remove_block.consumeClick()) {
            Services.PLATFORM.sendPacketToServer(Packets.PACKET_ID_REMOVE_BLOCK_KEY_PRESSED, new ServerboundRemoveBlockKeyPressedPacket(true));
        }
    }

}
