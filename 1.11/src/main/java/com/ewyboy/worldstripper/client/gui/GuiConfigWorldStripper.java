package com.ewyboy.worldstripper.client.gui;

import com.ewyboy.worldstripper.common.util.Config;
import com.ewyboy.worldstripper.common.util.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

/**
 * Created by EwyBoy
 */
public class GuiConfigWorldStripper extends GuiConfig {

    public GuiConfigWorldStripper(GuiScreen parent) {
        super(
                parent,
                new ConfigElement(Config.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                Reference.ModInfo.MOD_ID,
                false,
                false,
                Reference.ModInfo.MOD_NAME + " Config"
        );
    }
}
