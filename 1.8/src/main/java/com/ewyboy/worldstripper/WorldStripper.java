package com.ewyboy.worldstripper;

import com.ewyboy.worldstripper.common.network.PacketHandler;
import com.ewyboy.worldstripper.common.util.Config;
import com.ewyboy.worldstripper.common.util.Reference;
import com.ewyboy.worldstripper.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.ewyboy.worldstripper.common.util.Reference.ModInfo.*;
import static com.ewyboy.worldstripper.common.util.Reference.Paths.*;

@Mod(modid = MOD_ID, name = MOD_NAME, guiFactory = GUI_PATH)
public class WorldStripper {

    @Mod.Instance(MOD_ID)
    public static WorldStripper instance;

    @SidedProxy(modId = MOD_ID, clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
    public static CommonProxy proxy;

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if(eventArgs.modID.equals(Reference.ModInfo.MOD_ID)) Config.syncConfig();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(instance);
        Config.config = new Configuration(event.getSuggestedConfigurationFile());
        Config.syncConfig();
        PacketHandler.registerMessages(MOD_ID);
        proxy.registerKeybindings();
    }
}