package com.ewyboy.worldstripper.client;

import com.ewyboy.worldstripper.common.network.PacketHandler;
import com.ewyboy.worldstripper.common.network.packets.PacketInspectBlock;
import com.ewyboy.worldstripper.common.network.packets.PacketDressWorld;
import com.ewyboy.worldstripper.common.network.packets.PacketStripWorld;
import com.ewyboy.worldstripper.common.util.Reference;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

/**
 * Created by EwyBoy
 **/
public class KeyBindingHandler {

    public static KeyBinding strip, dress, inspect;

    @SideOnly(Side.CLIENT)
    public static void initKeyBinding() {
        strip = new KeyBinding(Reference.Keybinding.KeybindingNameStrip, Keyboard.KEY_DELETE, Reference.Keybinding.KeybindingCategory);
        dress = new KeyBinding(Reference.Keybinding.KeybindingNameDress, Keyboard.KEY_INSERT, Reference.Keybinding.KeybindingCategory);
        inspect = new KeyBinding(Reference.Keybinding.KeybindingNameInspect, Keyboard.KEY_HOME, Reference.Keybinding.KeybindingCategory);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (strip.isPressed()) PacketHandler.INSTANCE.sendToServer(new PacketStripWorld());
        if (dress.isPressed()) PacketHandler.INSTANCE.sendToServer(new PacketDressWorld());
        if (inspect.isPressed()) PacketHandler.INSTANCE.sendToServer(new PacketInspectBlock());
    }
}
