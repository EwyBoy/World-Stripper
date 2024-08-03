package com.ewyboy.worldstripper.fabric;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.client.Keymappings;
import com.ewyboy.worldstripper.networking.PacketBase;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import java.util.function.BiConsumer;

public class WorldStripperClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Keymappings.registerKeymappings(KeyBindingHelper :: registerKeyBinding);
        ClientEvents.registerEvents();
        WorldStripper.registerClientPackets(true);
    }

    public static void sendPacketToServer(PacketBase packet) {
        ClientPlayNetworking.send(packet);
    }

    public static <T extends PacketBase> void registerClientboundPacket(CustomPacketPayload.Type<T> id, BiConsumer<T, Player> handler)
    {
        ClientPlayNetworking.registerGlobalReceiver(id, (T packet, ClientPlayNetworking.Context context) -> {
            context.client().execute(() -> handler.accept(packet, context.player()));
        });
    }

}