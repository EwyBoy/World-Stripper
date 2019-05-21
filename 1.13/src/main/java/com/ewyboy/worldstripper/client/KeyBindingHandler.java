package com.ewyboy.worldstripper.client;

import com.ewyboy.worldstripper.common.network.MessageHandler;
import com.ewyboy.worldstripper.common.network.messages.MessageDressWorld;
import com.ewyboy.worldstripper.common.network.messages.MessageStripWorld;
import com.ewyboy.worldstripper.common.util.Reference;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

/**
 * Created by EwyBoy
 **/
public class KeyBindingHandler {

    private static KeyBinding strip, dress;

    @OnlyIn(Dist.CLIENT)
    public static void initKeyBinding() {
        strip = new KeyBinding(Reference.Keybinding.KeybindingNameStrip, GLFW.GLFW_KEY_DELETE, Reference.Keybinding.KeybindingCategory);
        ClientRegistry.registerKeyBinding(strip);
        dress = new KeyBinding(Reference.Keybinding.KeybindingNameDress, GLFW.GLFW_KEY_INSERT, Reference.Keybinding.KeybindingCategory);
        ClientRegistry.registerKeyBinding(dress);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent()
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (strip.isPressed()) MessageHandler.CHANNEL.sendToServer(new MessageStripWorld());
        if (dress.isPressed()) MessageHandler.CHANNEL.sendToServer(new MessageDressWorld());
    }
}