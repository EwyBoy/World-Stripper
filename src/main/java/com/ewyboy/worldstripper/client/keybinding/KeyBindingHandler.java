package com.ewyboy.worldstripper.client.keybinding;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.client.gui.config.GuiConfigMain;
import com.ewyboy.worldstripper.common.config.ConfigOptions;
import com.ewyboy.worldstripper.common.network.MessageHandler;
import com.ewyboy.worldstripper.common.network.messages.profile.MessageAddBlock;
import com.ewyboy.worldstripper.common.network.messages.profile.MessageCycleProfile;
import com.ewyboy.worldstripper.common.network.messages.profile.MessageRemoveBlock;
import com.ewyboy.worldstripper.common.network.messages.stripping.MessageDressWorker;
import com.ewyboy.worldstripper.common.network.messages.stripping.MessageDressWorld;
import com.ewyboy.worldstripper.common.network.messages.stripping.MessageStripWorker;
import com.ewyboy.worldstripper.common.network.messages.stripping.MessageStripWorld;
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
    private static KeyBinding add;
    private static KeyBinding remove;
    private static KeyBinding profile;

    public KeyBindingHandler() {}

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

    @SubscribeEvent
    public static void onKeyInput(KeyInputEvent event) {

        if(strip.isPressed()) {
            MessageHandler.CHANNEL.sendToServer(ConfigOptions.Stripping.liveStripping ? new MessageStripWorker() : new MessageStripWorld());
        }

        if(dress.isPressed()) {
            MessageHandler.CHANNEL.sendToServer(ConfigOptions.Stripping.liveStripping ? new MessageDressWorker() : new MessageDressWorld());
        }

        if(config.isPressed()) {
            Minecraft.getInstance().displayGuiScreen(new GuiConfigMain());
        }

        if(add.isPressed()) {
            MessageHandler.CHANNEL.sendToServer(new MessageAddBlock());
        }

        if(remove.isPressed()) {
            MessageHandler.CHANNEL.sendToServer(new MessageRemoveBlock());
        }

        if(profile.isPressed()) {
            MessageHandler.CHANNEL.sendToServer(new MessageCycleProfile());
        }

    }

}
