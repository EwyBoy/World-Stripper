package com.ewyboy.worldstripper.common.util;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by EwyBoy
 **/
public class Config {

    public static int chuckRadius;

    public static void init(File file) {
        Configuration config = new Configuration(file);

        config.load();
            chuckRadius = config.getInt("Chunk Radius", Reference.ModInfo.MOD_NAME, 3, 1, 12, "Sets the amount of chunks to clear (Chunk * Chunk)");
        config.save();
    }
}
