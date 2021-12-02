package com.ewyboy.worldstripper.common.config;

import com.ewyboy.worldstripper.WorldStripper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = WorldStripper.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigHelper {

    public static ModConfig config;

    public static final class CategoryName {
        public static final String STRIPPING    = "WorldStripper.Stripping.";
        public static final String PROFILES     = "WorldStripper.Profiles.";
        public static final String BLOCK_UPDATE = "WorldStripper.BlockUpdate.";
    }

    public static HashMap<Config.Profiles.Profile, List<String>> profileMap = new HashMap<>();
    public static HashMap<Config.Profiles.Profile, String> profilePathMap = new HashMap<>();

    static {
        profilePathMap.put(Config.Profiles.Profile.PROFILE_1, "profile_1");
        profilePathMap.put(Config.Profiles.Profile.PROFILE_2, "profile_2");
        profilePathMap.put(Config.Profiles.Profile.PROFILE_3, "profile_3");
        profilePathMap.put(Config.Profiles.Profile.PROFILE_4, "profile_4");
        profilePathMap.put(Config.Profiles.Profile.PROFILE_5, "profile_5");
    }

    private static void updateMap(Config.Profiles.Profile profile, List<String> profileList) {
        if (profileMap.containsKey(profile)) {
            profileMap.replace(profile, profileList);
        } else {
            profileMap.put(profile, profileList);
        }
    }

    static void readConfigToMemory(ModConfig modConfig) {
        config = modConfig;
        Config config = ConfigHolder.config;

        ConfigOptions.Stripping.blocksToStripX = config.stripping.blocksToStripX.get();
        ConfigOptions.Stripping.blocksToStripZ = config.stripping.blocksToStripZ.get();
        ConfigOptions.Stripping.liveStripping = config.stripping.liveStripping.get();
        ConfigOptions.Stripping.stripBedrock = config.stripping.stripBedrock.get();
        ConfigOptions.Stripping.replacementBlock = config.stripping.replacementBlock.get();
        ConfigOptions.Stripping.stripStartY = config.stripping.stripStartY.get();
        ConfigOptions.Stripping.stripStopY = config.stripping.stripStopY.get();

        ConfigOptions.Profiles.profile = (Enum<Config.Profiles.Profile>) config.profiles.profile.get();

        ConfigOptions.Profiles.profile1 = config.profiles.profile1.get().stream().map(entry -> entry.toLowerCase()).collect(Collectors.toList());
        ConfigOptions.Profiles.profile2 = config.profiles.profile2.get().stream().map(entry -> entry.toLowerCase()).collect(Collectors.toList());
        ConfigOptions.Profiles.profile3 = config.profiles.profile3.get().stream().map(entry -> entry.toLowerCase()).collect(Collectors.toList());
        ConfigOptions.Profiles.profile4 = config.profiles.profile4.get().stream().map(entry -> entry.toLowerCase()).collect(Collectors.toList());
        ConfigOptions.Profiles.profile5 = config.profiles.profile5.get().stream().map(entry -> entry.toLowerCase()).collect(Collectors.toList());

        updateMap(Config.Profiles.Profile.PROFILE_1, ConfigOptions.Profiles.profile1);
        updateMap(Config.Profiles.Profile.PROFILE_2, ConfigOptions.Profiles.profile2);
        updateMap(Config.Profiles.Profile.PROFILE_3, ConfigOptions.Profiles.profile3);
        updateMap(Config.Profiles.Profile.PROFILE_4, ConfigOptions.Profiles.profile4);
        updateMap(Config.Profiles.Profile.PROFILE_5, ConfigOptions.Profiles.profile5);

        ConfigOptions.BlockUpdate.notifyNeighbors = config.blockUpdate.notifyNeighbors.get();
        ConfigOptions.BlockUpdate.blockUpdate = config.blockUpdate.blockUpdate.get();
        ConfigOptions.BlockUpdate.noRender = config.blockUpdate.noRender.get();
        ConfigOptions.BlockUpdate.renderMainThread = config.blockUpdate.renderMainThread.get();
        ConfigOptions.BlockUpdate.updateNeighbors = config.blockUpdate.updateNeighbors.get();

    }

    static void writeConfigFromMemory(ModConfig modConfig) {
        config = modConfig;
        Config config = ConfigHolder.config;

        config.stripping.blocksToStripX.set(ConfigOptions.Stripping.blocksToStripX);
        config.stripping.blocksToStripZ.set(ConfigOptions.Stripping.blocksToStripZ);
        config.stripping.liveStripping.set(ConfigOptions.Stripping.liveStripping);
        config.stripping.replacementBlock.set(ConfigOptions.Stripping.replacementBlock);
        config.stripping.stripBedrock.set(ConfigOptions.Stripping.stripBedrock);

        config.stripping.stripStartY.set(ConfigOptions.Stripping.stripStartY);
        config.stripping.stripStopY.set(ConfigOptions.Stripping.stripStopY);

        config.profiles.profile.set(ConfigOptions.Profiles.profile);
        config.profiles.profile1.set(ConfigOptions.Profiles.profile1);
        config.profiles.profile2.set(ConfigOptions.Profiles.profile2);
        config.profiles.profile3.set(ConfigOptions.Profiles.profile3);
        config.profiles.profile4.set(ConfigOptions.Profiles.profile4);
        config.profiles.profile5.set(ConfigOptions.Profiles.profile5);

        config.blockUpdate.notifyNeighbors.set(ConfigOptions.BlockUpdate.notifyNeighbors);
        config.blockUpdate.blockUpdate.set(ConfigOptions.BlockUpdate.blockUpdate);
        config.blockUpdate.noRender.set(ConfigOptions.BlockUpdate.noRender);
        config.blockUpdate.renderMainThread.set(ConfigOptions.BlockUpdate.renderMainThread);
        config.blockUpdate.updateNeighbors.set(ConfigOptions.BlockUpdate.updateNeighbors);
    }

    public static void reloadConfig() {
        readConfigToMemory(config);
    }

    public static void syncConfig() {
        writeConfigFromMemory(config);
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

    public static void syncAndSaveConfig() {
        writeConfigFromMemory(config);
        config.save();
    }

    public static void saveAndSyncConfig() {
        config.save();
        writeConfigFromMemory(config);
    }

    public static void setValueAndSaveConfig(final String path, final Object newValue) {
        config.getConfigData().set(path, newValue);
        config.save();
        reloadConfig();
    }

    public static String getComment(final String path) {
        return config.getConfigData().getComment(path).replaceAll("\n", " - ");
    }


}
