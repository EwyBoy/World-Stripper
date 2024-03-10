package com.ewyboy.worldstripper.client;

import com.ewyboy.worldstripper.networking.message.MessageAddBlock;
import com.ewyboy.worldstripper.networking.message.MessageDressWorld;
import com.ewyboy.worldstripper.networking.message.MessageRemoveBlock;
import com.ewyboy.worldstripper.networking.message.MessageStripWorld;
import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.KeyMapping;

public class Keymappings {

    public static void init() {
        KeyMappingRegistry.register(STRIP_WORLD);
        KeyMappingRegistry.register(DRESS_WORLD);
        KeyMappingRegistry.register(ADD_BLOCK);
        KeyMappingRegistry.register(REMOVE_BLOCK);

        ClientTickEvent.CLIENT_POST.register(minecraft -> {
            while (STRIP_WORLD.consumeClick()) {
                new MessageStripWorld().sendToServer();
            }
            while (DRESS_WORLD.consumeClick()) {
                new MessageDressWorld().sendToServer();
            }
            while (ADD_BLOCK.consumeClick()) {
                new MessageAddBlock().sendToServer();
            }
            while (REMOVE_BLOCK.consumeClick()) {
                new MessageRemoveBlock().sendToServer();
            }
        });
    }

    public static final KeyMapping STRIP_WORLD = new KeyMapping(
            "key.worldstripper.strip",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_DELETE,
            "key.categories.worldstripper"
    );

    public static final KeyMapping DRESS_WORLD = new KeyMapping(
            "key.worldstripper.dress",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_INSERT,
            "key.categories.worldstripper"
    );

    public static final KeyMapping ADD_BLOCK = new KeyMapping(
            "key.worldstripper.add_block",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_PAGEUP,
            "key.categories.worldstripper"
    );

    public static final KeyMapping REMOVE_BLOCK = new KeyMapping(
            "key.worldstripper.remove_block",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_PAGEDOWN,
            "key.categories.worldstripper"
    );

}
