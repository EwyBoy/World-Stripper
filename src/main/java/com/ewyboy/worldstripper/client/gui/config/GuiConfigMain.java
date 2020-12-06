package com.ewyboy.worldstripper.client.gui.config;

import com.ewyboy.worldstripper.client.gui.config.widget.OptionsListWidget;
import com.ewyboy.worldstripper.client.gui.config.widget.entries.*;
import com.ewyboy.worldstripper.common.config.Config;
import com.ewyboy.worldstripper.common.config.ConfigHelper;
import com.ewyboy.worldstripper.common.config.ConfigOptions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

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

        options.add(new OptionsEntryConfigButton("General Settings", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
            minecraft.displayGuiScreen(new GuiConfigOptions(GuiConfigMain.this, new StringTextComponent("General")) {
                @Override
                public OptionsListWidget getOptions() {
                    OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);

                    options.add(new OptionsEntryConfigButton("Test List", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
                        minecraft.displayGuiScreen(new GuiConfigOptions(GuiConfigMain.this, new StringTextComponent("Test List")) {
                            @Override
                            public OptionsListWidget getOptions() {
                                OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);

                                IntStream.range(0, ConfigOptions.Profiles.profile1.size()).mapToObj(i ->
                                    new OptionsEntryValueResource<>("Block:", ConfigOptions.Profiles.profile1.get(i), System.out::println, new Button(0, 0, 20, 20, new StringTextComponent("X"), x -> {
                                        ConfigOptions.Profiles.profile1.remove(i);
                                        ConfigHelper.reloadAndSaveConfig();
                                        ConfigHelper.saveAndReloadConfig();
                                    }), OptionsEntryValueInput.ANY
                                )).forEachOrdered(options::add);

                                options.add(new OptionsEntryButton("Add", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
                                    ConfigHelper.setValueAndSaveConfig("WorldStripper.Profiles.profile_1", ConfigOptions.Profiles.profile1);
                                    ConfigHelper.reloadAndSaveConfig();
                                    ConfigHelper.saveAndReloadConfig();
                                })));

                                return options;
                            }
                        });
                    })));

                    options.add(new OptionsEntryValueBoolean("Test Boolean", ConfigOptions.General.testBool, value ->
                            ConfigHelper.setValueAndSaveConfig("WorldStripper.General.test_bool", value)
                    ));
                    options.add(new OptionsEntryValueInput<>("Test Int 1", ConfigOptions.General.testInt1, value ->
                            ConfigHelper.setValueAndSaveConfig("WorldStripper.General.test_int_1", value),
                            OptionsEntryValueInput.INTEGER
                    ));
                    options.add(new OptionsEntryValueInput<>("Test Int 2", ConfigOptions.General.testInt2, value ->
                            ConfigHelper.setValueAndSaveConfig("WorldStripper.General.test_int_2", value),
                            OptionsEntryValueInput.INTEGER
                    ));
                    options.add(new OptionsEntryValueEnum<>("Test Enum", Config.General.Test.values(), (Config.General.Test) ConfigOptions.General.testEnum, value ->
                            ConfigHelper.setValueAndSaveConfig("WorldStripper.General.test_enum", value)
                    ));

                    return options;
                }
            });
        })));

        options.add(new OptionsEntryConfigButton("Stripper Settings", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
            minecraft.displayGuiScreen(new GuiConfigOptions(GuiConfigMain.this, new StringTextComponent("Stripping")) {
                @Override
                public OptionsListWidget getOptions() {
                    OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);

                    options.add(new OptionsEntryDescription(ConfigHelper.getComment("WorldStripper.Stripping.blocks_to_strip_x")));
                    options.add(new OptionsEntryValueInput<>("Blocks to strip X", ConfigOptions.Stripping.blocksToStripX, value ->
                            ConfigHelper.setValueAndSaveConfig("WorldStripper.Stripping.blocks_to_strip_x", value),
                            OptionsEntryValueInput.INTEGER
                    ));

                    options.add(new OptionsEntryDescription(ConfigHelper.getComment("WorldStripper.Stripping.blocks_to_strip_z")));
                    options.add(new OptionsEntryValueInput<>("Blocks to strip Z", ConfigOptions.Stripping.blocksToStripZ, value ->
                            ConfigHelper.setValueAndSaveConfig("WorldStripper.Stripping.blocks_to_strip_z", value),
                            OptionsEntryValueInput.INTEGER
                    ));

                    options.add(new OptionsEntryDescription(ConfigHelper.getComment("WorldStripper.Stripping.replacement_block")));
                    options.add(new OptionsEntryValueInput<>("Replacement Block", ConfigOptions.Stripping.replacementBlock, value ->
                            ConfigHelper.setValueAndSaveConfig("WorldStripper.Stripping.replacement_block", value),
                            OptionsEntryValueInput.ANY
                    ));

                    options.add(new OptionsEntryConfigButton("Update-Flag Settings", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
                        minecraft.displayGuiScreen(new GuiConfigOptions(GuiConfigMain.this, new StringTextComponent("Test List")) {
                            @Override
                            public OptionsListWidget getOptions() {
                                OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);

                                options.add(new OptionsEntryValueBoolean("Update Client", ConfigOptions.General.testBool, value ->
                                        System.out.println("I work!")
                                        //ConfigHelper.setValueAndSaveConfig("WorldStripper.General.test_bool", value)
                                ));
                                options.add(new OptionsEntryValueBoolean("Update Server", ConfigOptions.General.testBool, value -> {}
                                        //ConfigHelper.setValueAndSaveConfig("WorldStripper.General.test_bool", value)
                                ));
                                options.add(new OptionsEntryValueBoolean("Block Pushed", ConfigOptions.General.testBool, value -> {}
                                        //ConfigHelper.setValueAndSaveConfig("WorldStripper.General.test_bool", value)
                                ));
                                options.add(new OptionsEntryValueBoolean("Block Die", ConfigOptions.General.testBool, value -> {}
                                        //ConfigHelper.setValueAndSaveConfig("WorldStripper.General.test_bool", value)
                                ));
                                options.add(new OptionsEntryValueBoolean("Update Nothing", ConfigOptions.General.testBool, value -> {}
                                        //ConfigHelper.setValueAndSaveConfig("WorldStripper.General.test_bool", value)
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
                            ConfigHelper.setValueAndSaveConfig("WorldStripper.Profiles.profile", value)
                    ));

                    options.add(new OptionsEntryConfigButton("Profile 1", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
                        minecraft.displayGuiScreen(new GuiConfigOptions(GuiConfigMain.this, new StringTextComponent("Profile 1")) {
                            @Override
                            public OptionsListWidget getOptions() {
                                OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);
                                return options;
                            }
                        });
                    })));

                    options.add(new OptionsEntryConfigButton("Profile 2", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
                        minecraft.displayGuiScreen(new GuiConfigOptions(GuiConfigMain.this, new StringTextComponent("Profile 2")) {
                            @Override
                            public OptionsListWidget getOptions() {
                                OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);
                                return options;
                            }
                        });
                    })));

                    options.add(new OptionsEntryConfigButton("Profile 3", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
                        minecraft.displayGuiScreen(new GuiConfigOptions(GuiConfigMain.this, new StringTextComponent("Profile 3")) {
                            @Override
                            public OptionsListWidget getOptions() {
                                OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);
                                return options;
                            }
                        });
                    })));

                    options.add(new OptionsEntryConfigButton("Profile 4", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
                        minecraft.displayGuiScreen(new GuiConfigOptions(GuiConfigMain.this, new StringTextComponent("Profile 4")) {
                            @Override
                            public OptionsListWidget getOptions() {
                                OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);
                                return options;
                            }
                        });
                    })));

                    options.add(new OptionsEntryConfigButton("Profile 5", new Button(0, 0, 100, 20, new StringTextComponent(""), buttonPressed -> {
                        minecraft.displayGuiScreen(new GuiConfigOptions(GuiConfigMain.this, new StringTextComponent("Profile 5")) {
                            @Override
                            public OptionsListWidget getOptions() {
                                OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);
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
