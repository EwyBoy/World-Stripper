package com.ewyboy.worldstripper;

import com.ewyboy.worldstripper.client.Keybindings;
import com.ewyboy.worldstripper.client.hud.ProgressBar;
import com.ewyboy.worldstripper.common.commands.CommandHandler;
import com.ewyboy.worldstripper.common.json.StripListHandler;
import com.ewyboy.worldstripper.common.network.MessageHandler;
import com.ewyboy.worldstripper.common.settings.Settings;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.ewyboy.worldstripper.WorldStripper.MOD_ID;

@Mod(MOD_ID)
public class WorldStripper {

    public static final String MOD_ID = "worldstripper";
    public static final String NAME = "World Stripper";

    public WorldStripper() {
        StripListHandler.setup();
        CommandHandler.setup();
        MessageHandler.setup();
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
    }

    @SubscribeEvent
    public void clientRegister(FMLClientSetupEvent event) {
        Keybindings.setup();
        MinecraftForge.EVENT_BUS.register(new ProgressBar());
    }

}