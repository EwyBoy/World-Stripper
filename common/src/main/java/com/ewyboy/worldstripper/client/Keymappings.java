package com.ewyboy.worldstripper.client;

import com.ewyboy.worldstripper.WorldStripper;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

import java.util.function.Consumer;

public class Keymappings {

    /** 26.x categories are Identifiers; the label comes from "key.category.worldstripper.worldstripper". */
    public static final KeyMapping.Category CATEGORY = new KeyMapping.Category(Identifier.fromNamespaceAndPath(WorldStripper.MOD_ID, WorldStripper.MOD_ID));

    public static KeyMapping strip_world;
    public static KeyMapping dress_world;
    public static KeyMapping add_block;
    public static KeyMapping remove_block;

    public static void registerKeymappings(Consumer<KeyMapping> registrar) {
        registrar.accept(strip_world = new KeyMapping("key.worldstripper.strip", InputConstants.Type.KEYBOARD, InputConstants.KEY_DELETE, CATEGORY));
        registrar.accept(dress_world = new KeyMapping("key.worldstripper.dress", InputConstants.Type.KEYBOARD, InputConstants.KEY_INSERT, CATEGORY));
        registrar.accept(add_block = new KeyMapping("key.worldstripper.add_block", InputConstants.Type.KEYBOARD, InputConstants.KEY_PAGEUP, CATEGORY));
        registrar.accept(remove_block = new KeyMapping("key.worldstripper.remove_block", InputConstants.Type.KEYBOARD, InputConstants.KEY_PAGEDOWN, CATEGORY));
    }

}
