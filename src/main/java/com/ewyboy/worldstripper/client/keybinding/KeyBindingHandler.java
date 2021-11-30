package com.ewyboy.worldstripper.client.keybinding;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.common.config.ConfigOptions;
import com.ewyboy.worldstripper.common.network.MessageHandler;
import com.ewyboy.worldstripper.common.network.messages.profile.MessageAddBlock;
import com.ewyboy.worldstripper.common.network.messages.profile.MessageCycleProfile;
import com.ewyboy.worldstripper.common.network.messages.profile.MessageRemoveBlock;
import com.ewyboy.worldstripper.common.network.messages.stripping.MessageDressWorker;
import com.ewyboy.worldstripper.common.network.messages.stripping.MessageDressWorld;
import com.ewyboy.worldstripper.common.network.messages.stripping.MessageStripWorker;
import com.ewyboy.worldstripper.common.network.messages.stripping.MessageStripWorld;
import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

public class KeyBindingHandler {

    private static KeyMapping strip;
    private static KeyMapping dress;
    private static KeyMapping add;
    private static KeyMapping remove;
    private static KeyMapping profile;

    public KeyBindingHandler() {}

    public static void initKeyBinding() {

        strip = new KeyMapping("Strip World", KeyConflictContext.IN_GAME, Type.KEYSYM, GLFW.GLFW_KEY_DELETE, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(strip);
        dress = new KeyMapping("Dress World", KeyConflictContext.IN_GAME, Type.KEYSYM, GLFW.GLFW_KEY_INSERT, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(dress);
        add = new KeyMapping("Add Block", KeyConflictContext.IN_GAME, Type.KEYSYM, GLFW.GLFW_KEY_PAGE_UP, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(add);
        remove = new KeyMapping("Remove Block", KeyConflictContext.IN_GAME, Type.KEYSYM, GLFW.GLFW_KEY_PAGE_DOWN, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(remove);
        profile = new KeyMapping("Cycle Profile", KeyConflictContext.IN_GAME, Type.KEYSYM, GLFW.GLFW_KEY_END, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(profile);

    }

    @SubscribeEvent
    public static void onKeyInput(KeyInputEvent event) {

        if(strip.consumeClick()) {
            MessageHandler.CHANNEL.sendToServer(ConfigOptions.Stripping.liveStripping ? new MessageStripWorker() : new MessageStripWorld());
        }

        if(dress.consumeClick()) {
            MessageHandler.CHANNEL.sendToServer(ConfigOptions.Stripping.liveStripping ? new MessageDressWorker() : new MessageDressWorld());
        }

        if(add.consumeClick()) {
            MessageHandler.CHANNEL.sendToServer(new MessageAddBlock());
        }

        if(remove.consumeClick()) {
            MessageHandler.CHANNEL.sendToServer(new MessageRemoveBlock());
        }

        if(profile.consumeClick()) {
            MessageHandler.CHANNEL.sendToServer(new MessageCycleProfile());
        }

    }

}
