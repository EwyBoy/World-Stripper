package com.ewyboy.worldstripper.client;

import com.ewyboy.worldstripper.common.network.MessageHandler;
import com.ewyboy.worldstripper.common.network.messages.MessageDressWorld;
import com.ewyboy.worldstripper.common.network.messages.MessageStripWorld;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBindingHandler {
    private static KeyBinding strip;
    private static KeyBinding dress;

    public KeyBindingHandler() {
    }

    @OnlyIn(Dist.CLIENT)
    public static void initKeyBinding() {
        strip = new KeyBinding("Strip World", KeyConflictContext.IN_GAME, Type.KEYSYM, 261, "World Stripper");
        ClientRegistry.registerKeyBinding(strip);
        dress = new KeyBinding("Dress World", KeyConflictContext.IN_GAME, Type.KEYSYM, 260, "World Stripper");
        ClientRegistry.registerKeyBinding(dress);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onKeyInput(KeyInputEvent event) {
        if (strip.isPressed()) {
            MessageHandler.CHANNEL.sendToServer(new MessageStripWorld());
        }

        if (dress.isPressed()) {
            MessageHandler.CHANNEL.sendToServer(new MessageDressWorld());
        }

    }
}
