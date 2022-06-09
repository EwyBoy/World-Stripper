package com.ewyboy.worldstripper.client;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.network.MessageHandler;
import com.ewyboy.worldstripper.network.messages.MessageAddBlock;
import com.ewyboy.worldstripper.network.messages.MessageDressWorker;
import com.ewyboy.worldstripper.network.messages.MessageRemoveBlock;
import com.ewyboy.worldstripper.network.messages.MessageStripWorker;
import com.ewyboy.worldstripper.settings.Settings;
import com.ewyboy.worldstripper.stripclub.Translation;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

public class Keybindings {

    private static KeyMapping strip;
    private static KeyMapping dress;
    private static KeyMapping add;
    private static KeyMapping remove;
    private static KeyMapping increase;
    private static KeyMapping decrease;
    private static KeyMapping increaseBig;
    private static KeyMapping decreaseBig;

    public static void setup() {
        initKeyBinding();
        clickEvent();
    }

    public static void initKeyBinding() {
        strip = new KeyMapping(Translation.Key.STRIP, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_DELETE, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(strip);
        dress = new KeyMapping(Translation.Key.DRESS, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_INSERT, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(dress);
        add = new KeyMapping(Translation.Key.ADD, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_PAGE_UP, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(add);
        remove = new KeyMapping(Translation.Key.REMOVE, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_PAGE_DOWN, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(remove);
        increase = new KeyMapping(Translation.Key.INCREASE, KeyConflictContext.IN_GAME, KeyModifier.SHIFT, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_PAGE_UP, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(increase);
        decrease = new KeyMapping(Translation.Key.DECREASE, KeyConflictContext.IN_GAME, KeyModifier.SHIFT, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_PAGE_DOWN, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(decrease);
        increaseBig = new KeyMapping(Translation.Key.INCREASE_BIG, KeyConflictContext.IN_GAME, KeyModifier.ALT, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_PAGE_UP, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(increaseBig);
        decreaseBig = new KeyMapping(Translation.Key.DECREASE_BIG, KeyConflictContext.IN_GAME, KeyModifier.ALT, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_PAGE_DOWN, WorldStripper.NAME);
        ClientRegistry.registerKeyBinding(decreaseBig);
    }

    private static void clickEvent() {
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, Keybindings :: onKeyInput);
    }

    @SubscribeEvent
    public static void onKeyInput(KeyInputEvent event) {
        if(strip.consumeClick()) MessageHandler.CHANNEL.sendToServer(new MessageStripWorker());
        if(dress.consumeClick()) MessageHandler.CHANNEL.sendToServer(new MessageDressWorker());
        if(add.consumeClick()) MessageHandler.CHANNEL.sendToServer(new MessageAddBlock());
        if(remove.consumeClick()) MessageHandler.CHANNEL.sendToServer(new MessageRemoveBlock());
        if(increase.consumeClick()) { bumpRange(Settings.SETTINGS.stripRadiusX, 1); bumpRange(Settings.SETTINGS.stripRadiusZ, 1); }
        if(decrease.consumeClick()) { bumpRange(Settings.SETTINGS.stripRadiusX, -1); bumpRange(Settings.SETTINGS.stripRadiusZ, -1); }
        if(increaseBig.consumeClick()) { bumpRange(Settings.SETTINGS.stripRadiusX, 8); bumpRange(Settings.SETTINGS.stripRadiusZ, 8); }
        if(decreaseBig.consumeClick()) { bumpRange(Settings.SETTINGS.stripRadiusX, -8); bumpRange(Settings.SETTINGS.stripRadiusZ, -8); }
    }

    private static void bumpRange(ForgeConfigSpec.ConfigValue<Integer> setting, int change) {
        setting.set(setting.get() + change);
        if (Minecraft.getInstance().player != null) Minecraft.getInstance().player.sendSystemMessage(Component.literal(ChatFormatting.AQUA + setting.getPath().get(1) + ChatFormatting.WHITE +  " is now set to " +  ChatFormatting.LIGHT_PURPLE + setting.get()));
    }

}
