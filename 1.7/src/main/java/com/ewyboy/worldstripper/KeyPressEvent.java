package com.ewyboy.worldstripper;

import com.ewyboy.worldstripper.packets.ClearBlocksPKT;
import com.ewyboy.worldstripper.packets.ReplaceBlocksPKT;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyPressEvent {

    public KeyPressEvent() {}

    @SubscribeEvent
    public void keyPress(KeyInputEvent event) {
        if (Utils.strip.getIsKeyPressed()) {
            Main.pktHandler.sendToServer(new ClearBlocksPKT(Keyboard.isKeyDown(42)));
        }
        if (Utils.replace.getIsKeyPressed()) {
            Main.pktHandler.sendToServer(new ReplaceBlocksPKT());
        }
    }
}
