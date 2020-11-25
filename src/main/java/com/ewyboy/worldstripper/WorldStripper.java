package com.ewyboy.worldstripper;

import com.ewyboy.worldstripper.client.gui.config.GuiConfigMain;
import com.ewyboy.worldstripper.common.commands.CommandCenter;
import com.ewyboy.worldstripper.common.config.ConfigHolder;
import com.ewyboy.worldstripper.proxy.ClientProxy;
import com.ewyboy.worldstripper.proxy.CommonProxy;
import com.ewyboy.worldstripper.proxy.IModProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("worldstripper")
public class WorldStripper {

    public static final String MOD_ID = "worldstripper";
    public static final String NAME = "World Stripper";

    private static final IModProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public WorldStripper() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);

        ConfigHolder.init();

        MinecraftForge.EVENT_BUS.register(CommandCenter.class);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStart);

        // This enables the Config button inside the mod menu from the main screen
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (mc, screen) -> new GuiConfigMain(screen));

        proxy.construct();
    }

    public void onServerStart(FMLServerStartingEvent event){
        new CommandCenter(event.getServer().getCommandManager().getDispatcher());
    }

    @SubscribeEvent
    public void setup(FMLCommonSetupEvent event) {
        proxy.setup();
    }

}
