package com.ewyboy.worldstripper.client;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.network.PacketHandler;
import com.ewyboy.worldstripper.network.packets.PacketAddBlock;
import com.ewyboy.worldstripper.network.packets.PacketDressWorker;
import com.ewyboy.worldstripper.network.packets.PacketRemoveBlock;
import com.ewyboy.worldstripper.network.packets.PacketStripWorker;
import com.ewyboy.worldstripper.settings.Settings;
import com.ewyboy.worldstripper.stripclub.Translation;
import com.mojang.blaze3d.platform.InputConstants;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class Keybindings {

    private static KeyMapping strip;
    private static KeyMapping dress;
    private static KeyMapping add;
    private static KeyMapping remove;
    private static KeyMapping config;

    public static void setup() {
        initKeyBinding();
        clickEvent();
    }

    private static void initKeyBinding() {
        strip = new KeyMapping(Translation.Key.STRIP, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_DELETE, WorldStripper.NAME);
        KeyBindingHelper.registerKeyBinding(strip);

        dress = new KeyMapping(Translation.Key.DRESS, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_INSERT, WorldStripper.NAME);
        KeyBindingHelper.registerKeyBinding(dress);

        add = new KeyMapping(Translation.Key.ADD, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_PAGE_UP, WorldStripper.NAME);
        KeyBindingHelper.registerKeyBinding(add);

        remove = new KeyMapping(Translation.Key.REMOVE, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_PAGE_DOWN, WorldStripper.NAME);
        KeyBindingHelper.registerKeyBinding(remove);

        config = new KeyMapping(Translation.Key.CONFIG, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_HOME, WorldStripper.NAME);
        KeyBindingHelper.registerKeyBinding(config);
    }

    private static void clickEvent() {
        ClientTickEvents.END_CLIENT_TICK.register(Keybindings :: onEndTick);
    }

    private static void onEndTick(Minecraft client) {
        while (strip.consumeClick()) PacketHandler.sendToServer(new PacketStripWorker());
        while (dress.consumeClick()) PacketHandler.sendToServer(new PacketDressWorker());
        while (add.consumeClick()) PacketHandler.sendToServer(new PacketAddBlock());
        while (remove.consumeClick()) PacketHandler.sendToServer(new PacketRemoveBlock());
        while (config.consumeClick()) Minecraft.getInstance().setScreen(AutoConfig.getConfigScreen(Settings.class, Minecraft.getInstance().screen).get());
    }
}
