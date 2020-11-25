package com.ewyboy.worldstripper.client.gui.config;

import com.ewyboy.worldstripper.client.gui.config.value.OptionsEntryButton;
import com.ewyboy.worldstripper.client.gui.config.value.OptionsEntryValueBoolean;
import com.ewyboy.worldstripper.client.gui.config.value.OptionsEntryValueInput;
import com.ewyboy.worldstripper.common.config.ConfigHelper;
import com.ewyboy.worldstripper.common.config.ConfigOptions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

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

        options.add(new OptionsEntryButton("General Settings", new Button(0, 0, 100, 20, new StringTextComponent(""), w -> {
            minecraft.displayGuiScreen(new GuiConfigOptions(GuiConfigMain.this, new StringTextComponent("General")) {
                @Override
                public OptionsListWidget getOptions() {
                    OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);

                    options.add(new OptionsEntryValueBoolean("Test Boolean", ConfigOptions.General.testBool, value ->
                            ConfigHelper.setValueAndSaveConfig("WorldStripper.General.test_bool", value)
                    ));
                    options.add(new OptionsEntryValueInput<>("Test Int 1", ConfigOptions.General.testInt1, value ->
                            ConfigHelper.setValueAndSaveConfig("WorldStripper.General.test_int_1", value)
                    ));
                    options.add(new OptionsEntryValueInput<>("Test Int 2", ConfigOptions.General.testInt2, value ->
                            ConfigHelper.setValueAndSaveConfig("WorldStripper.General.test_int_2", value)
                    ));

                    return options;
                }
            });
        })));

        options.add(new OptionsEntryButton("Stripper Settings", new Button(0, 0, 100, 20, new StringTextComponent(""), w -> {
            minecraft.displayGuiScreen(new GuiConfigOptions(GuiConfigMain.this, new StringTextComponent("Stripping")) {
                @Override
                public OptionsListWidget getOptions() {
                    OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);

                    options.add(new OptionsEntryValueInput<>("Blocks to strip x-axis", ConfigOptions.Stripping.blocksToStripX, value ->
                            ConfigHelper.setValueAndSaveConfig("WorldStripper.Stripping.blocks_to_strip_x", value)
                    ));

                    options.add(new OptionsEntryValueInput<>("Blocks to strip z-axis", ConfigOptions.Stripping.blocksToStripZ, value ->
                            ConfigHelper.setValueAndSaveConfig("WorldStripper.Stripping.blocks_to_strip_z", value)
                    ));

                    options.add(new OptionsEntryValueInput<>("Block Update Flag", ConfigOptions.Stripping.updateFlag, value ->
                            ConfigHelper.setValueAndSaveConfig("WorldStripper.Stripping.update_flag", value)
                    ));

                    options.add(new OptionsEntryValueInput<>("Block Update Flag", ConfigOptions.Stripping.replacementBlock, value ->
                            ConfigHelper.setValueAndSaveConfig("WorldStripper.Stripping.replacement_block", value)
                    ));

                    return options;
                }
            });
        })));
        options.add(new OptionsEntryButton("Profile Settings", new Button(0, 0, 100, 20, new StringTextComponent(""), w -> {
            minecraft.displayGuiScreen(new GuiConfigOptions(GuiConfigMain.this, new StringTextComponent("Profiles")) {
                @Override
                public OptionsListWidget getOptions() {
                    OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);
                    options.add(new OptionsEntryValueBoolean("Test Boolean", ConfigOptions.General.testBool, value ->
                            ConfigHelper.setValueAndSaveConfig("Profile.test_bool", value)
                    ));
                    return options;
                }
            });
        })));
        return options;
    }

}
