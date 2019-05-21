package com.ewyboy.worldstripper;

import com.ewyboy.worldstripper.packets.ClearBlocksPKT;
import com.ewyboy.worldstripper.packets.ReplaceBlocksPKT;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

@Mod(name = Main.NAME, modid = Main.ID, version = Main.VERSION)
public class Main {

    public static final String ID = "worldstripper";
    public static final String NAME = "World Stripper";
    public static final String VERSION = "1.0.0";

    @Instance("thestrippermod")
    public static Main instance;

    @SidedProxy(clientSide = "com.ewyboy.worldstripper.ClientProxy", serverSide = "com.ewyboy.worldstripper.CommonProxy")
    public static CommonProxy proxy;

    public static final SimpleNetworkWrapper pktHandler;

    public Main() {}

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        pktHandler.registerMessage(ClearBlocksPKT.class, ClearBlocksPKT.class, 0, Side.SERVER);
        pktHandler.registerMessage(ReplaceBlocksPKT.class, ReplaceBlocksPKT.class, 1, Side.SERVER);
        proxy.registerKeyBinds();
        proxy.registerClientOnlyEvents();
        Utils.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {}

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {}

    @EventHandler
    public void serverQuit(FMLServerStoppingEvent event) {
        CommonProxy.clearRemovedCache();
    }

    static {
        pktHandler = NetworkRegistry.INSTANCE.newSimpleChannel("thestrippermod");
    }
}
