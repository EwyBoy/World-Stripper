package com.ewyboy.worldstripper.client;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.client.gui.config.GuiConfigMain;
import com.ewyboy.worldstripper.common.config.ConfigOptions;
import com.ewyboy.worldstripper.common.network.MessageHandler;
import com.ewyboy.worldstripper.common.network.messages.MessageAddBlock;
import com.ewyboy.worldstripper.common.network.messages.MessageCycleProfile;
import com.ewyboy.worldstripper.common.network.messages.MessageRemoveBlock;
import com.ewyboy.worldstripper.common.network.messages.MessageDressWorker;
import com.ewyboy.worldstripper.common.network.messages.MessageStripWorker;
import net.minecraft.client.Minecraft;
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
    private static KeyBinding config;
    private static KeyBinding add;
    private static KeyBinding remove;
    private static KeyBinding profile;

    public static void setup() {
        initKeyBinding();
        clickEvent();
    }

    public static void initKeyBinding() {
        strip = new KeyBinding("Strip World", KeyConflictContext.IN_GAME, Type.KEYSYM, GLFW.GLFW_KEY_DELETE, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(strip);
        dress = new KeyBinding("Dress World", KeyConflictContext.IN_GAME, Type.KEYSYM, GLFW.GLFW_KEY_INSERT, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(dress);
        config = new KeyBinding("Open Config", KeyConflictContext.IN_GAME, Type.KEYSYM, GLFW.GLFW_KEY_HOME, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(config);
        add = new KeyBinding("Add Block", KeyConflictContext.IN_GAME, Type.KEYSYM, GLFW.GLFW_KEY_PAGE_UP, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(add);
        remove = new KeyBinding("Remove Block", KeyConflictContext.IN_GAME, Type.KEYSYM, GLFW.GLFW_KEY_PAGE_DOWN, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(remove);
        profile = new KeyBinding("Cycle Profile", KeyConflictContext.IN_GAME, Type.KEYSYM, GLFW.GLFW_KEY_END, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(profile);

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
        if(profile.consumeClick()) MessageHandler.CHANNEL.sendToServer(new MessageCycleProfile());

        //if(config.consumeClick()) Minecraft.getInstance().setScreen(new GuiConfigMain());
    }

}
