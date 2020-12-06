package com.ewyboy.worldstripper.common.config;

import com.ewyboy.worldstripper.WorldStripper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.Map;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = WorldStripper.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigHelper {

    public static ModConfig config;

    static void readConfigToMemory(ModConfig modConfig) {
        config = modConfig;
        Config config = ConfigHolder.config;

        ConfigOptions.General.testInt1 = config.general.testInt1.get();
        ConfigOptions.General.testInt2 = config.general.testInt2.get();
        ConfigOptions.General.testBool = config.general.testBool.get();
        ConfigOptions.General.testList = config.general.testList.get().stream().map(entry -> entry.toLowerCase()).collect(Collectors.toList());
        ConfigOptions.General.testEnum = (Enum<Config.General.Test>) config.general.testEnum.get();

        ConfigOptions.Stripping.blocksToStripX = config.stripping.blocksToStripX.get();
        ConfigOptions.Stripping.blocksToStripZ = config.stripping.blocksToStripZ.get();
        ConfigOptions.Stripping.updateFlag = config.stripping.updateFlag.get();
        ConfigOptions.Stripping.replacementBlock = config.stripping.replacementBlock.get();

        ConfigOptions.Profiles.profile = (Enum<Config.Profiles.Profile>) config.profiles.profile.get();
        ConfigOptions.Profiles.profile1 = config.profiles.profile1.get().stream().map(entry -> entry.toLowerCase()).collect(Collectors.toList());
        ConfigOptions.Profiles.profile2 = config.profiles.profile2.get().stream().map(entry -> entry.toLowerCase()).collect(Collectors.toList());
        ConfigOptions.Profiles.profile3 = config.profiles.profile3.get().stream().map(entry -> entry.toLowerCase()).collect(Collectors.toList());
        ConfigOptions.Profiles.profile4 = config.profiles.profile4.get().stream().map(entry -> entry.toLowerCase()).collect(Collectors.toList());
        ConfigOptions.Profiles.profile5 = config.profiles.profile5.get().stream().map(entry -> entry.toLowerCase()).collect(Collectors.toList());
    }

    static void writeConfigFromMemory(ModConfig modConfig) {
        config = modConfig;
        Config config = ConfigHolder.config;

        config.general.testInt1.set(ConfigOptions.General.testInt1);
        config.general.testInt2.set(ConfigOptions.General.testInt2);
        config.general.testBool.set(ConfigOptions.General.testBool);
        config.general.testList.set(ConfigOptions.General.testList);
        config.general.testEnum.set(ConfigOptions.General.testEnum);

        config.stripping.blocksToStripX.set(ConfigOptions.Stripping.blocksToStripX);
        config.stripping.blocksToStripZ.set(ConfigOptions.Stripping.blocksToStripZ);
        config.stripping.replacementBlock.set(ConfigOptions.Stripping.replacementBlock);
        config.stripping.updateFlag.set(ConfigOptions.Stripping.updateFlag);

        config.profiles.profile.set(ConfigOptions.Profiles.profile);
        config.profiles.profile1.set(ConfigOptions.Profiles.profile1);
        config.profiles.profile2.set(ConfigOptions.Profiles.profile2);
        config.profiles.profile3.set(ConfigOptions.Profiles.profile3);
        config.profiles.profile4.set(ConfigOptions.Profiles.profile4);
        config.profiles.profile5.set(ConfigOptions.Profiles.profile5);
    }

    public static void reloadConfig() {
        readConfigToMemory(config);
    }

    public static void saveConfig() {
        config.save();
    }

    public static void reloadAndSaveConfig() {
        readConfigToMemory(config);
        config.save();
    }

    public static void saveAndReloadConfig() {
        config.save();
        readConfigToMemory(config);
    }

    public static void setValueAndSaveConfig(final String path, final Object newValue) {
        config.getConfigData().set(path, newValue);
        config.save();
        reloadConfig();
    }

    public static void setValuesAndSaveConfig(final Map<String,Object> values) {
        values.forEach((key, value) -> config.getConfigData().set(key, value));
        config.save();
        reloadConfig();
    }

    public static String getComment(final String path) {
        return config.getConfigData().getComment(path).replaceAll("\n", " - ");
    }

}
