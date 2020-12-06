package com.ewyboy.worldstripper.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigHolder {

    public static Config config;
    public static ForgeConfigSpec configSpec;

    public static void init() {
        final Pair<Config, ForgeConfigSpec> spec = new ForgeConfigSpec.Builder().configure(Config :: new);
        config = spec.getLeft();
        configSpec = spec.getRight();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHolder.configSpec);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ConfigHolder :: modConfig);
    }

    private static void modConfig(final ModConfig.ModConfigEvent event) {
        ModConfig config = event.getConfig();

        if (config.getSpec() == ConfigHolder.configSpec) {
            ConfigHelper.readConfigToMemory(config);
        }
    }


}
