package com.ewyboy.worldstripper.json;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.json.objects.StripListObject;
import com.ewyboy.worldstripper.stripclub.ModLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StripListHandler {

    private static final Gson gson = new Gson();
    public static final File JSON_FILE = new File(FabricLoader.getInstance().getConfigDir() + "/worldstripper/stripper.json");

    private static final List<String> STRIP_ENTRIES = new ArrayList<>(); static {
        STRIP_ENTRIES.add("minecraft:dirt");
        STRIP_ENTRIES.add("minecraft:grass");
        STRIP_ENTRIES.add("minecraft:grass_path");
        STRIP_ENTRIES.add("minecraft:tall_grass");
        STRIP_ENTRIES.add("minecraft:grass_block");
        STRIP_ENTRIES.add("minecraft:stone");
        STRIP_ENTRIES.add("minecraft:diorite");
        STRIP_ENTRIES.add("minecraft:granite");
        STRIP_ENTRIES.add("minecraft:andesite");
        STRIP_ENTRIES.add("minecraft:gravel");
        STRIP_ENTRIES.add("minecraft:sand");
        STRIP_ENTRIES.add("minecraft:sandstone");
        STRIP_ENTRIES.add("minecraft:oak_log");
        STRIP_ENTRIES.add("minecraft:dark_oak_log");
        STRIP_ENTRIES.add("minecraft:spruce_log");
        STRIP_ENTRIES.add("minecraft:birch_log");
        STRIP_ENTRIES.add("minecraft:jungle_log");
        STRIP_ENTRIES.add("minecraft:acacia_log");
        STRIP_ENTRIES.add("minecraft:oak_leaves");
        STRIP_ENTRIES.add("minecraft:dark_oak_leaves");
        STRIP_ENTRIES.add("minecraft:spruce_leaves");
        STRIP_ENTRIES.add("minecraft:birch_leaves");
        STRIP_ENTRIES.add("minecraft:jungle_leaves");
        STRIP_ENTRIES.add("minecraft:acacia_leaves");
        STRIP_ENTRIES.add("minecraft:water");
        STRIP_ENTRIES.add("minecraft:flowing_water");
        STRIP_ENTRIES.add("minecraft:lava");
        STRIP_ENTRIES.add("minecraft:flowing_lava");
        STRIP_ENTRIES.add("minecraft:netherrack");
        STRIP_ENTRIES.add("minecraft:end_stone");
        STRIP_ENTRIES.add("minecraft:podzol");
        STRIP_ENTRIES.add("minecraft:bamboo");
        STRIP_ENTRIES.add("minecraft:seagrass");
        STRIP_ENTRIES.add("minecraft:tall_seagrass");
        STRIP_ENTRIES.add("minecraft:kelp");
        STRIP_ENTRIES.add("minecraft:kelp_plant");
    }

    public static StripListObject stripList = new StripListObject(STRIP_ENTRIES);

    public static void setup() {
        createDirectory();
        if(!JSON_FILE.exists()) {
            ModLogger.info("Creating World Stripper JSON file");
            writeJson(JSON_FILE);
        }
        ModLogger.info("Reading World Stripper JSON file");
        readJson(JSON_FILE);
    }

    public static void reload() {
        writeJson(JSON_FILE);
        readJson(JSON_FILE);
    }

    public static boolean containsEntry(String entry) {
        for (String block : stripList.getEntries()) {
            if (block.equals(entry)) {
                return true;
            }
        }
        return false;
    }

    public static boolean addEntry(String entry) {
        if (!containsEntry(entry)) {
            stripList.getEntries().add(entry);
            reload();
            return true;
        }
        return false;
    }

    public static boolean removeEntry(String entry) {
        if (containsEntry(entry)) {
            stripList.getEntries().removeIf(target -> target.equals(entry));
            reload();
            return true;
        }
        return false;
    }

    public static void writeJson(File jsonFile) {
        try(Writer writer = new FileWriter(jsonFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(stripList, writer);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void readJson(File jsonFile) {
        try(Reader reader = new FileReader(jsonFile)) {
            stripList = gson.fromJson(reader, StripListObject.class);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDirectory() {
        Path path = Paths.get(FabricLoader.getInstance().getConfigDir().toAbsolutePath().toString(), WorldStripper.MOD_ID);
        try {
            ModLogger.info("Creating World Stripper directory");
            Files.createDirectory(path);
        } catch (FileAlreadyExistsException ignored) {
        } catch (IOException e) {
            ModLogger.error("Failed to create world-stripper directory", e);
        }
    }
}
