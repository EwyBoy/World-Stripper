package com.ewyboy.worldstripper;

import com.ewyboy.worldstripper.proxy.ClientProxy;
import com.ewyboy.worldstripper.proxy.CommonProxy;
import com.ewyboy.worldstripper.proxy.IModProxy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.ewyboy.worldstripper.common.util.Reference.ModInfo.MOD_ID;

@Mod(MOD_ID)
public class WorldStripper {

    public static WorldStripper instance;

    public static final IModProxy proxy = DistExecutor.runForDist(() -> ClientProxy :: new, () -> CommonProxy :: new);

    public WorldStripper() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        proxy.construct();
    }

    @SubscribeEvent
    public void setup(FMLCommonSetupEvent event) {
        proxy.setup();
    }

}