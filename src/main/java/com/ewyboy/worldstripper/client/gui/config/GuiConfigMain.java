package com.ewyboy.worldstripper.client.gui.config;

import com.ewyboy.worldstripper.client.gui.config.widget.OptionsListWidget;
import com.ewyboy.worldstripper.client.gui.config.widget.entries.*;
import com.ewyboy.worldstripper.common.config.Config;
import com.ewyboy.worldstripper.common.config.ConfigHelper;
import com.ewyboy.worldstripper.common.config.ConfigOptions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;
import java.util.stream.IntStream;

public class GuiConfigMain extends GuiConfigOptions {

    private final Screen parent;

    // Use in-game when you have no parent screen
    public GuiConfigMain() {
        super(null, new StringTextComponent("World Stripper"));
        this.parent = null;
    }

    // Used to take you back to the mod menu
    public GuiConfigMain(Screen parent) {
        super(parent, new StringTextComponent("World Stripper"));
        this.parent = parent;
    }

    @Override
    public OptionsListWidget getOptions() {

        OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30, ConfigHelper :: saveConfig);

        options.add(new OptionsEntryConfigButton("Stripper Settings", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
            minecraft.displayGuiScreen(new GuiConfigOptions(GuiConfigMain.this, new StringTextComponent("Stripping")) {
                @Override
                public OptionsListWidget getOptions() {
                    OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);

                    options.add(new OptionsEntryDescription(ConfigHelper.getComment(ConfigHelper.CategoryName.STRIPPING + "blocks_to_strip_x")));
                    options.add(new OptionsEntryValueInput<>("Blocks to strip X", ConfigOptions.Stripping.blocksToStripX, value ->
                            ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.STRIPPING + "blocks_to_strip_x", value),
                            OptionsEntryValueInput.INTEGER
                    ));

                    options.add(new OptionsEntryDescription(ConfigHelper.getComment(ConfigHelper.CategoryName.STRIPPING + "blocks_to_strip_z")));
                    options.add(new OptionsEntryValueInput<>("Blocks to strip Z", ConfigOptions.Stripping.blocksToStripZ, value ->
                            ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.STRIPPING + "blocks_to_strip_z", value),
                            OptionsEntryValueInput.INTEGER
                    ));

                    options.add(new OptionsEntryDescription(ConfigHelper.getComment(ConfigHelper.CategoryName.STRIPPING + "live_stripping")));
                    options.add(new OptionsEntryValueBoolean("Live Stripping", ConfigOptions.Stripping.liveStripping, value ->
                            ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.STRIPPING + "live_stripping", value)
                    ));

                    options.add(new OptionsEntryDescription(ConfigHelper.getComment(ConfigHelper.CategoryName.STRIPPING + "strip_bedrock")));
                    options.add(new OptionsEntryValueBoolean("Strip Bedrock", ConfigOptions.Stripping.stripBedrock, value ->
                            ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.STRIPPING + "strip_bedrock", value)
                    ));

                    options.add(new OptionsEntryDescription(ConfigHelper.getComment(ConfigHelper.CategoryName.STRIPPING + "replacement_block")));
                    options.add(new OptionsEntryValueInput<>("Replacement Block", ConfigOptions.Stripping.replacementBlock, value ->
                            ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.STRIPPING + "replacement_block", value),
                            OptionsEntryValueInput.ANY
                    ));

                    options.add(new OptionsEntryDescription("ADVANCED SETTINGS"));
                    options.add(new OptionsEntryDescription("Don't touch unless you know what you are doing"));
                    options.add(new OptionsEntryConfigButton("Block Update", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
                        minecraft.displayGuiScreen(new GuiConfigOptions(GuiConfigMain.this, new StringTextComponent("ADVANCED SETTINGS")) {
                            @Override
                            public OptionsListWidget getOptions() {
                                OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);

                                options.add(new OptionsEntryDescription(ConfigHelper.getComment(ConfigHelper.CategoryName.BLOCK_UPDATE + "notify_neighbors")));
                                options.add(new OptionsEntryValueBoolean("Notify Neighbors", ConfigOptions.BlockUpdate.notifyNeighbors, value ->
                                        ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.BLOCK_UPDATE + "notify_neighbors", value)
                                ));
                                options.add(new OptionsEntryDescription(ConfigHelper.getComment(ConfigHelper.CategoryName.BLOCK_UPDATE + "block_update")));
                                options.add(new OptionsEntryValueBoolean("Block Update", ConfigOptions.BlockUpdate.blockUpdate, value ->
                                        ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.BLOCK_UPDATE + "block_update", value)
                                ));
                                options.add(new OptionsEntryDescription(ConfigHelper.getComment(ConfigHelper.CategoryName.BLOCK_UPDATE + "no_render")));
                                options.add(new OptionsEntryValueBoolean("No Render", ConfigOptions.BlockUpdate.noRender, value ->
                                        ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.BLOCK_UPDATE + "no_render", value)
                                ));
                                options.add(new OptionsEntryDescription(ConfigHelper.getComment(ConfigHelper.CategoryName.BLOCK_UPDATE + "render_main_thread")));
                                options.add(new OptionsEntryValueBoolean("Render Main Thread", ConfigOptions.BlockUpdate.renderMainThread, value ->
                                        ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.BLOCK_UPDATE + "render_main_thread", value)
                                ));
                                options.add(new OptionsEntryDescription(ConfigHelper.getComment(ConfigHelper.CategoryName.BLOCK_UPDATE + "update_neighbors")));
                                options.add(new OptionsEntryValueBoolean("Update Neighbors", ConfigOptions.BlockUpdate.updateNeighbors, value ->
                                        ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.BLOCK_UPDATE + "update_neighbors", value)
                                ));

                                return options;
                            }
                        });
                    })));
                    return options;
                }
            });
        })));

        options.add(new OptionsEntryConfigButton("Profile Settings", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
            minecraft.displayGuiScreen(new GuiConfigOptions(GuiConfigMain.this, new StringTextComponent("Profiles")) {
                @Override
                public OptionsListWidget getOptions() {
                    OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);

                    options.add(new OptionsEntryValueEnum<>("Profile", Config.Profiles.Profile.values(), (Config.Profiles.Profile) ConfigOptions.Profiles.profile, value ->
                            ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile", value)
                    ));

                    options.add(new OptionsEntryConfigButton("Profile 1", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
                        minecraft.displayGuiScreen(new GuiConfigOptions(GuiConfigMain.this, new StringTextComponent("Profile 1")) {
                            @Override
                            public OptionsListWidget getOptions() {
                                OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);

                                List<String> currentList = ConfigOptions.Profiles.profile1;

                                IntStream.range(0, ConfigOptions.Profiles.profile1.size()).mapToObj(i ->
                                        new OptionsEntryValueResource<>("Block:", ConfigOptions.Profiles.profile1.get(i), value -> {
                                            if (!currentList.contains(value)) {
                                                currentList.add(value);
                                                minecraft.displayGuiScreen(minecraft.currentScreen);
                                                currentList.removeIf(String::isEmpty);
                                                ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile_1", currentList);
                                            }
                                        }, new Button(0, 0, 20, 20, new StringTextComponent("X"), buttonPressed -> {
                                            currentList.remove(i);
                                            ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile_1", currentList);
                                            minecraft.displayGuiScreen(minecraft.currentScreen);
                                        }), OptionsEntryValueInput.ANY
                                )).forEachOrdered(options :: add);

                                options.add(new OptionsEntryButton("Add", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
                                    options.save();
                                    currentList.add("");
                                    ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile_1", currentList);
                                    minecraft.displayGuiScreen(minecraft.currentScreen);
                                })));

                                return options;
                            }
                        });
                    })));

                    options.add(new OptionsEntryConfigButton("Profile 2", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
                        minecraft.displayGuiScreen(new GuiConfigOptions(GuiConfigMain.this, new StringTextComponent("Profile 2")) {
                            @Override
                            public OptionsListWidget getOptions() {
                                OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);

                                List<String> currentList = ConfigOptions.Profiles.profile2;

                                IntStream.range(0, ConfigOptions.Profiles.profile2.size()).mapToObj(i ->
                                        new OptionsEntryValueResource<>("Block:", ConfigOptions.Profiles.profile2.get(i), value -> {
                                            if (!currentList.contains(value)) {
                                                currentList.add(value);
                                                minecraft.displayGuiScreen(minecraft.currentScreen);
                                                currentList.removeIf(String::isEmpty);
                                                ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile_2", currentList);
                                            }
                                        }, new Button(0, 0, 20, 20, new StringTextComponent("X"), buttonPressed -> {
                                            currentList.remove(i);
                                            ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile_2", currentList);
                                            minecraft.displayGuiScreen(minecraft.currentScreen);
                                        }), OptionsEntryValueInput.ANY
                                        )).forEachOrdered(options :: add);

                                options.add(new OptionsEntryButton("Add", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
                                    options.save();
                                    currentList.add("");
                                    ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile_2", currentList);
                                    minecraft.displayGuiScreen(minecraft.currentScreen);
                                })));

                                return options;
                            }
                        });
                    })));

                    options.add(new OptionsEntryConfigButton("Profile 3", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
                        minecraft.displayGuiScreen(new GuiConfigOptions(GuiConfigMain.this, new StringTextComponent("Profile 3")) {
                            @Override
                            public OptionsListWidget getOptions() {
                                OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);

                                List<String> currentList = ConfigOptions.Profiles.profile3;

                                IntStream.range(0, ConfigOptions.Profiles.profile3.size()).mapToObj(i ->
                                        new OptionsEntryValueResource<>("Block:", ConfigOptions.Profiles.profile3.get(i), value -> {
                                            if (!currentList.contains(value)) {
                                                currentList.add(value);
                                                minecraft.displayGuiScreen(minecraft.currentScreen);
                                                currentList.removeIf(String::isEmpty);
                                                ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile_3", currentList);
                                            }
                                        }, new Button(0, 0, 20, 20, new StringTextComponent("X"), buttonPressed -> {
                                            currentList.remove(i);
                                            ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile_3", currentList);
                                            minecraft.displayGuiScreen(minecraft.currentScreen);
                                        }), OptionsEntryValueInput.ANY
                                        )).forEachOrdered(options :: add);

                                options.add(new OptionsEntryButton("Add", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
                                    options.save();
                                    currentList.add("");
                                    ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile_3", currentList);
                                    minecraft.displayGuiScreen(minecraft.currentScreen);
                                })));


                                return options;
                            }
                        });
                    })));

                    options.add(new OptionsEntryConfigButton("Profile 4", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
                        minecraft.displayGuiScreen(new GuiConfigOptions(GuiConfigMain.this, new StringTextComponent("Profile 4")) {
                            @Override
                            public OptionsListWidget getOptions() {
                                OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);

                                List<String> currentList = ConfigOptions.Profiles.profile4;

                                IntStream.range(0, ConfigOptions.Profiles.profile4.size()).mapToObj(i ->
                                        new OptionsEntryValueResource<>("Block:", ConfigOptions.Profiles.profile4.get(i), value -> {
                                            if (!currentList.contains(value)) {
                                                currentList.add(value);
                                                minecraft.displayGuiScreen(minecraft.currentScreen);
                                                currentList.removeIf(String::isEmpty);
                                                ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile_4", currentList);
                                            }
                                        }, new Button(0, 0, 20, 20, new StringTextComponent("X"), buttonPressed -> {
                                            currentList.remove(i);
                                            ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile_4", currentList);
                                            minecraft.displayGuiScreen(minecraft.currentScreen);
                                        }), OptionsEntryValueInput.ANY
                                        )).forEachOrdered(options :: add);

                                options.add(new OptionsEntryButton("Add", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
                                    options.save();
                                    currentList.add("");
                                    ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile_4", currentList);
                                    minecraft.displayGuiScreen(minecraft.currentScreen);
                                })));

                                return options;
                            }
                        });
                    })));

                    options.add(new OptionsEntryConfigButton("Profile 5", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
                        minecraft.displayGuiScreen(new GuiConfigOptions(GuiConfigMain.this, new StringTextComponent("Profile 5")) {
                            @Override
                            public OptionsListWidget getOptions() {
                                OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);

                                List<String> currentList = ConfigOptions.Profiles.profile5;

                                IntStream.range(0, ConfigOptions.Profiles.profile5.size()).mapToObj(i ->
                                        new OptionsEntryValueResource<>("Block:", ConfigOptions.Profiles.profile5.get(i), value -> {
                                            if (!currentList.contains(value)) {
                                                currentList.add(value);
                                                minecraft.displayGuiScreen(minecraft.currentScreen);
                                                currentList.removeIf(String::isEmpty);
                                                ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile_5", currentList);
                                            }
                                        }, new Button(0, 0, 20, 20, new StringTextComponent("X"), buttonPressed -> {
                                            currentList.remove(i);
                                            ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile_5", currentList);
                                            minecraft.displayGuiScreen(minecraft.currentScreen);
                                        }), OptionsEntryValueInput.ANY
                                )).forEachOrdered(options :: add);

                                options.add(new OptionsEntryButton("Add", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
                                    options.save();
                                    currentList.add("");
                                    ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile_5", currentList);
                                    minecraft.displayGuiScreen(minecraft.currentScreen);
                                })));

                                return options;
                            }
                        });
                    })));

                    return options;
                }
            });
        })));

        return options;
    }

}
