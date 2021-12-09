package com.ewyboy.worldstripper.client;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.network.MessageHandler;
import com.ewyboy.worldstripper.network.messages.MessageAddBlock;
import com.ewyboy.worldstripper.network.messages.MessageDressWorker;
import com.ewyboy.worldstripper.network.messages.MessageRemoveBlock;
import com.ewyboy.worldstripper.network.messages.MessageStripWorker;
import com.ewyboy.worldstripper.stripclub.Translation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings.Type;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class Keybindings {

    private static KeyBinding strip;
    private static KeyBinding dress;
    private static KeyBinding add;
    private static KeyBinding remove;

    public static void setup() {
        initKeyBinding();
        clickEvent();
    }

    public static void initKeyBinding() {
        strip = new KeyBinding(Translation.Key.STRIP, KeyConflictContext.IN_GAME, Type.KEYSYM, GLFW.GLFW_KEY_DELETE, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(strip);
        dress = new KeyBinding(Translation.Key.DRESS, KeyConflictContext.IN_GAME, Type.KEYSYM, GLFW.GLFW_KEY_INSERT, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(dress);
        add = new KeyBinding(Translation.Key.ADD, KeyConflictContext.IN_GAME, Type.KEYSYM, GLFW.GLFW_KEY_PAGE_UP, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(add);
        remove = new KeyBinding(Translation.Key.REMOVE, KeyConflictContext.IN_GAME, Type.KEYSYM, GLFW.GLFW_KEY_PAGE_DOWN, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(remove);
    }

    private static void clickEvent() {
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, Keybindings :: onKeyInput);
    }

    @SubscribeEvent
    public static void onKeyInput(KeyInputEvent event) {
        if(strip.consumeClick()) MessageHandler.CHANNEL.sendToServer(new MessageStripWorker());
        if(dress.consumeClick()) MessageHandler.CHANNEL.sendToServer(new MessageDressWorker());
        if(add.consumeClick()) MessageHandler.CHANNEL.sendToServer(new MessageAddBlock());
        if(remove.consumeClick()) MessageHandler.CHANNEL.sendToServer(new MessageRemoveBlock());
    }

}
