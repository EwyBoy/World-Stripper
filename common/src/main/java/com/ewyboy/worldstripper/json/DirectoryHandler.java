package com.ewyboy.worldstripper.json;

import com.ewyboy.worldstripper.services.Services;
import com.ewyboy.worldstripper.util.ModLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DirectoryHandler {

    public static void init() {
        CreateDirectory();
    }

    private static void CreateDirectory() {
        File directory = new File(Services.PLATFORM.getPlatformConfigDir().toString() + "/worldstripper");
        if (!directory.exists()) {
            try {
                Files.createDirectories(Paths.get(directory.getPath()));
            } catch (FileAlreadyExistsException e) {
                ModLogger.info("Directory already exists");
            } catch (IOException e) {
                ModLogger.error("Failed to create directory");
            }
        }
    }

}
