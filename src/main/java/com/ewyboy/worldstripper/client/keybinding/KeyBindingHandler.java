package com.ewyboy.worldstripper.client.keybinding;

import com.ewyboy.worldstripper.client.gui.config.GuiConfigMain;
import com.ewyboy.worldstripper.common.network.MessageHandler;
import com.ewyboy.worldstripper.common.network.messages.MessageDressWorld;
import com.ewyboy.worldstripper.common.network.messages.MessageStripWorker;
import com.ewyboy.worldstripper.common.network.messages.MessageStripWorld;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings.Type;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeyBindingHandler {

    private static KeyBinding strip;
    private static KeyBinding dress;
    private static KeyBinding config;
    private static KeyBinding worker;

    public KeyBindingHandler() {}

    public static void initKeyBinding() {
        strip = new KeyBinding("Strip World", KeyConflictContext.IN_GAME, Type.KEYSYM, GLFW.GLFW_KEY_DELETE, "World Stripper");
        ClientRegistry.registerKeyBinding(strip);
        dress = new KeyBinding("Dress World", KeyConflictContext.IN_GAME, Type.KEYSYM, GLFW.GLFW_KEY_INSERT, "World Stripper");
        ClientRegistry.registerKeyBinding(dress);
        config = new KeyBinding("Open Config", KeyConflictContext.IN_GAME, Type.KEYSYM, GLFW.GLFW_KEY_HOME, "World Stripper");
        ClientRegistry.registerKeyBinding(config);
        worker = new KeyBinding("Strip Worker", KeyConflictContext.IN_GAME, Type.KEYSYM, GLFW.GLFW_KEY_PAGE_UP, "World Stripper");
        ClientRegistry.registerKeyBinding(worker);
    }

    @SubscribeEvent
    public static void onKeyInput(KeyInputEvent event) {
        if (strip.isPressed()) {
            MessageHandler.CHANNEL.sendToServer(new MessageStripWorld());
        }

        if (dress.isPressed()) {
            MessageHandler.CHANNEL.sendToServer(new MessageDressWorld());
        }

        if (config.isPressed()) {
            Minecraft.getInstance().displayGuiScreen(new GuiConfigMain());
        }

        if (worker.isPressed()) {
            MessageHandler.CHANNEL.sendToServer(new MessageStripWorker());
        }
    }
}
