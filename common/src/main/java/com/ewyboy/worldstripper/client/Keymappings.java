package com.ewyboy.worldstripper.client;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;

import java.util.function.Consumer;

public class Keymappings {

    public static KeyMapping strip_world;
    public static KeyMapping dress_world;
    public static KeyMapping add_block;
    public static KeyMapping remove_block;

    public static void registerKeymappings(Consumer<KeyMapping> registrar) {
        registrar.accept(strip_world = new KeyMapping("key.worldstripper.strip", InputConstants.Type.KEYSYM, InputConstants.KEY_DELETE, "key.categories.worldstripper"));
        registrar.accept(dress_world = new KeyMapping("key.worldstripper.dress", InputConstants.Type.KEYSYM, InputConstants.KEY_INSERT, "key.categories.worldstripper"));
        registrar.accept(add_block = new KeyMapping("key.worldstripper.add_block", InputConstants.Type.KEYSYM, InputConstants.KEY_PAGEUP, "key.categories.worldstripper"));
        registrar.accept(remove_block = new KeyMapping("key.worldstripper.remove_block", InputConstants.Type.KEYSYM, InputConstants.KEY_PAGEDOWN, "key.categories.worldstripper"));
    }

}
