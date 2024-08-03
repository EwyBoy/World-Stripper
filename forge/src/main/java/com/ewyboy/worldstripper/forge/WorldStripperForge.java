package com.ewyboy.worldstripper.forge;

import com.ewyboy.worldstripper.WorldStripper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;

@Mod(WorldStripper.MOD_ID)
public class WorldStripperForge {

    public static SimpleChannel network;

    public WorldStripperForge() {
        WorldStripper.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        network = ChannelBuilder.named(ResourceLocation.fromNamespaceAndPath(WorldStripper.MOD_ID, "worldstripperpackets")).simpleChannel();
        WorldStripper.registerServerPackets(network);
        WorldStripper.registerClientPackets(network);
    }

}