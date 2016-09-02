package com.ewyboy.worldstripper;

import com.ewyboy.worldstripper.common.network.PacketHandler;
import com.ewyboy.worldstripper.common.util.Config;
import com.ewyboy.worldstripper.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static com.ewyboy.worldstripper.common.util.Reference.ModInfo.*;
import static com.ewyboy.worldstripper.common.util.Reference.Paths.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = BUILD_VERSION)
public class WorldStripper {

    @SidedProxy(modId = MOD_ID, clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.init(event.getSuggestedConfigurationFile());
        PacketHandler.registerMessages(MOD_ID);
        proxy.registerKeybindings();
    }
}
