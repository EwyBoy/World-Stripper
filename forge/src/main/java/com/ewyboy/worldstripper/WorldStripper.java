package com.ewyboy.worldstripper;

import com.electronwill.nightconfig.core.Config;
import com.ewyboy.worldstripper.client.Keybindings;
import com.ewyboy.worldstripper.common.commands.CommandHandler;
import com.ewyboy.worldstripper.common.config.ConfigHolder;
import com.ewyboy.worldstripper.common.json.JsonHandler;
import com.ewyboy.worldstripper.common.network.MessageHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.ewyboy.worldstripper.WorldStripper.MOD_ID;

@Mod(MOD_ID)
public class WorldStripper {

    public static final String MOD_ID = "worldstripper";
    public static final String NAME = "World Stripper";

    public WorldStripper() {
        Config.setInsertionOrderPreserved(true);
        JsonHandler.setup();
        CommandHandler.setup();
        MessageHandler.init();
        ConfigHolder.init();
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
    }

    @SubscribeEvent
    public void clientRegister(FMLClientSetupEvent event) {
        Keybindings.setup();
        // MinecraftForge.EVENT_BUS.register(new Display());
        // ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (mc, screen) -> new GuiConfigMain(screen));
    }

}
