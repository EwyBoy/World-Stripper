package com.ewyboy.worldstripper.common.config;

import com.ewyboy.worldstripper.WorldStripper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.Map;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = WorldStripper.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigHelper {

    public static ModConfig config;

    static void refreshConfig(ModConfig modConfig) {
        config = modConfig;
        Config config = ConfigHolder.config;

        ConfigOptions.General.testInt1 = config.general.testInt1.get();
        ConfigOptions.General.testInt2 = config.general.testInt2.get();
        ConfigOptions.General.testBool = config.general.testBool.get();
        ConfigOptions.General.testList = config.general.testList.get().stream().map(entry -> new ResourceLocation(entry.toLowerCase())).collect(Collectors.toSet());

        ConfigOptions.Stripping.blocksToStripX = config.stripping.blocksToStripX.get();
        ConfigOptions.Stripping.blocksToStripZ = config.stripping.blocksToStripZ.get();
        ConfigOptions.Stripping.updateFlag = config.stripping.updateFlag.get();
        ConfigOptions.Stripping.replacementBlock = config.stripping.replacementBlock.get();

        ConfigOptions.Profiles.selectedProfile = config.profiles.selectedProfile.get();
        ConfigOptions.Profiles.profile1 = config.profiles.profile1.get().stream().map(entry -> new ResourceLocation(entry.toLowerCase())).collect(Collectors.toSet());
        ConfigOptions.Profiles.profile2 = config.profiles.profile1.get().stream().map(entry -> new ResourceLocation(entry.toLowerCase())).collect(Collectors.toSet());
        ConfigOptions.Profiles.profile3 = config.profiles.profile1.get().stream().map(entry -> new ResourceLocation(entry.toLowerCase())).collect(Collectors.toSet());
        ConfigOptions.Profiles.profile4 = config.profiles.profile1.get().stream().map(entry -> new ResourceLocation(entry.toLowerCase())).collect(Collectors.toSet());
        ConfigOptions.Profiles.profile5 = config.profiles.profile1.get().stream().map(entry -> new ResourceLocation(entry.toLowerCase())).collect(Collectors.toSet());
    }

    public static void reloadConfig() {
        refreshConfig(config);
    }

    public static void saveConfig() {
        config.save();
    }

    public static void reloadAndSaveConfig() {
        refreshConfig(config);
        config.save();
    }

    public static void saveAndReloadConfig() {
        config.save();
        refreshConfig(config);
    }

    public static void setValueAndSaveConfig(final String path, final Object newValue) {
        config.getConfigData().set(path, newValue);
        config.save();
        reloadConfig();
    }

    private static void setValuesAndSaveConfig(final Map<String,Object> values) {
        values.forEach((key, value) -> config.getConfigData().set(key, value));
        config.save();
        reloadConfig();
    }

}
