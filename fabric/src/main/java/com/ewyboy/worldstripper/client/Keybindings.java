package com.ewyboy.worldstripper.client;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.network.PacketHandler;
import com.ewyboy.worldstripper.network.packets.PacketDressWorker;
import com.ewyboy.worldstripper.network.packets.PacketStripWorker;
import com.ewyboy.worldstripper.stripclub.Translation;
import com.terraformersmc.modmenu.gui.ModsScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Keybindings {

    private static KeyBinding strip;
    private static KeyBinding dress;

    public static void setup() {
        initKeyBinding();
        clickEvent();
    }

    private static void initKeyBinding() {
        strip = new KeyBinding(Translation.Key.STRIP, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_DELETE, WorldStripper.NAME);
        KeyBindingHelper.registerKeyBinding(strip);

        dress = new KeyBinding(Translation.Key.DRESS, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_INSERT, WorldStripper.NAME);
        KeyBindingHelper.registerKeyBinding(dress);
    }

    private static void clickEvent() {
        ClientTickEvents.END_CLIENT_TICK.register(Keybindings :: onEndTick);
    }

    private static void onEndTick(MinecraftClient client) {
        while (strip.wasPressed()) PacketHandler.sendToServer(new PacketStripWorker());
        while (dress.wasPressed()) PacketHandler.sendToServer(new PacketDressWorker());
    }
}
