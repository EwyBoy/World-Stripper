package com.ewyboy.worldstripper.client;

import com.ewyboy.worldstripper.common.network.PacketHandler;
import com.ewyboy.worldstripper.common.network.PacketStripWorld;
import com.ewyboy.worldstripper.common.util.Reference;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

/**
 * Created by EwyBoy
 **/
public class KeyBindingHandler {

    public static KeyBinding strip;

    public static void initKeyBinding() {
        strip = new KeyBinding(Reference.Keybinding.KeybindingName, Keyboard.KEY_DELETE, Reference.Keybinding.KeybindingCategory);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (strip.isPressed()) PacketHandler.INSTANCE.sendToServer(new PacketStripWorld());
    }

}
