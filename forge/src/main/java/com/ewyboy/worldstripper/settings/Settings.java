package com.ewyboy.worldstripper.settings;

import com.electronwill.nightconfig.core.Config;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class Settings {

    public static final ForgeConfigSpec settingSpec;
    public static final CommonSettings SETTINGS;

    static {
        Pair<CommonSettings, ForgeConfigSpec> specPair = (new ForgeConfigSpec.Builder()).configure(CommonSettings :: new);
        settingSpec = specPair.getRight();
        SETTINGS = specPair.getLeft();
    }

    public static class CommonSettings {

        public final ForgeConfigSpec.ConfigValue<Integer> stripRadiusX;
        public final ForgeConfigSpec.ConfigValue<Integer> stripRadiusZ;
        public final ForgeConfigSpec.ConfigValue<Integer> stripStartY;
        public final ForgeConfigSpec.ConfigValue<Integer> stripStopY;

        public final ForgeConfigSpec.ConfigValue<String> replacementBlock;

        public final ForgeConfigSpec.ConfigValue<Boolean> notifyNeighbors;
        public final ForgeConfigSpec.ConfigValue<Boolean> blockUpdate;
        public final ForgeConfigSpec.ConfigValue<Boolean> noRender;
        public final ForgeConfigSpec.ConfigValue<Boolean> renderMainThread;
        public final ForgeConfigSpec.ConfigValue<Boolean> updateNeighbors;

        public static void setFalse(ForgeConfigSpec.ConfigValue<Boolean> booleanConfigValue) {
            if (booleanConfigValue.get()) booleanConfigValue.set(Boolean.FALSE);
        }

        CommonSettings(ForgeConfigSpec.Builder builder) {
            builder.comment("World Stripper - Settings File");
            builder.push("SETTINGS");

                this.stripRadiusX = builder
                    .comment("Strip Radius: X")
                    .translation("settings.worldstripper.strip_radius_x")
                    .defineInRange("stripRadiusX", 48, 0, 256
                );

                this.stripRadiusZ = builder
                    .comment("Strip Radius: Z")
                    .translation("settings.worldstripper.strip_radius_z")
                    .defineInRange("stripRadiusZ", 48, 0, 256
                );

                this.stripStartY = builder
                    .comment("Strip Start: Y")
                    .translation("settings.worldstripper.strip_start_y")
                    .defineInRange("stripStartY", 256, -10240, 10240
                );

                this.stripStopY = builder
                    .comment("Strip Stop: Y")
                    .translation("settings.worldstripper.strip_stop_y")
                    .defineInRange("stripStopY", 0, -10240, 10240
                );

                this.replacementBlock = builder
                    .comment("Replacement Block")
                    .translation("settings.worldstripper.replacement_block")
                    .define("replacementBlock", "minecraft:air"
                );

                builder.push("Block-Update-Settings");
                    this.notifyNeighbors = builder
                        .comment("Call Neighbors")
                        .translation("settings.worldstripper.updatesettings.notify_neighbors")
                        .define("notifyNeighbors", false
                    );
                    this.blockUpdate = builder
                        .comment("Update Block")
                        .translation("settings.worldstripper.update_settings.block_update")
                        .define("blockUpdate", true
                    );
                    this.noRender = builder
                        .comment("Don't Update Renderer")
                        .translation("settings.worldstripper.update_settings.no_render")
                        .define("noRender", false
                    );
                    this.renderMainThread = builder
                        .comment("Do Update Renderer")
                        .translation("settings.worldstripper.update_settings.render_main_thread")
                        .define("renderMainThread", false
                    );
                    this.updateNeighbors = builder
                        .comment("Block Update Neighbors")
                        .translation("settings.worldstripper.update_settings.update_neighbors")
                        .define("updateNeighbors", true
                    );
                    builder.pop();
            builder.build();
        }
    }

    public static void setup() {
        Config.setInsertionOrderPreserved(true);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Settings.settingSpec, "worldstripper/settings.toml");
    }

}
