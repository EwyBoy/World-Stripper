package com.ewyboy.worldstripper.json;

import com.ewyboy.worldstripper.WorldStripper;
import com.ewyboy.worldstripper.util.ModLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.architectury.platform.Platform;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StrippablesHandler {

    private static final Gson gson = new Gson();
    public static final File JSON_FILE = new File(Platform.getConfigFolder() + "/worldstripper/strippables.json");

    private static final List<String> STRIPPABLES = new ArrayList<>();

    static {
        STRIPPABLES.add("minecraft:dirt");
        STRIPPABLES.add("minecraft:short_grass");
        STRIPPABLES.add("minecraft:tall_grass");
        STRIPPABLES.add("minecraft:grass_path");
        STRIPPABLES.add("minecraft:tall_grass");
        STRIPPABLES.add("minecraft:grass_block");
        STRIPPABLES.add("minecraft:stone");
        STRIPPABLES.add("minecraft:diorite");
        STRIPPABLES.add("minecraft:granite");
        STRIPPABLES.add("minecraft:andesite");
        STRIPPABLES.add("minecraft:gravel");
        STRIPPABLES.add("minecraft:sand");
        STRIPPABLES.add("minecraft:sandstone");
        STRIPPABLES.add("minecraft:red_sand");
        STRIPPABLES.add("minecraft:red_sandstone");
        STRIPPABLES.add("minecraft:ice");
        STRIPPABLES.add("minecraft:snow");
        STRIPPABLES.add("minecraft:snow_block");
        STRIPPABLES.add("minecraft:powder_snow");
        STRIPPABLES.add("minecraft:oak_log");
        STRIPPABLES.add("minecraft:dark_oak_log");
        STRIPPABLES.add("minecraft:spruce_log");
        STRIPPABLES.add("minecraft:birch_log");
        STRIPPABLES.add("minecraft:jungle_log");
        STRIPPABLES.add("minecraft:acacia_log");
        STRIPPABLES.add("minecraft:oak_leaves");
        STRIPPABLES.add("minecraft:dark_oak_leaves");
        STRIPPABLES.add("minecraft:spruce_leaves");
        STRIPPABLES.add("minecraft:birch_leaves");
        STRIPPABLES.add("minecraft:jungle_leaves");
        STRIPPABLES.add("minecraft:acacia_leaves");
        STRIPPABLES.add("minecraft:water");
        STRIPPABLES.add("minecraft:flowing_water");
        STRIPPABLES.add("minecraft:lava");
        STRIPPABLES.add("minecraft:flowing_lava");
        STRIPPABLES.add("minecraft:netherrack");
        STRIPPABLES.add("minecraft:end_stone");
        STRIPPABLES.add("minecraft:podzol");
        STRIPPABLES.add("minecraft:mycelium");
        STRIPPABLES.add("minecraft:bamboo");
        STRIPPABLES.add("minecraft:seagrass");
        STRIPPABLES.add("minecraft:tall_seagrass");
        STRIPPABLES.add("minecraft:kelp");
        STRIPPABLES.add("minecraft:kelp_plant");
        STRIPPABLES.add("minecraft:deepslate");
        STRIPPABLES.add("minecraft:tuff");
        STRIPPABLES.add("minecraft:glow_lichen");
    }

    public static Strippables strippables = new Strippables(STRIPPABLES);

    public static void init() {
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
        return strippables.strippables().contains(entry);
    }

    public static boolean addEntry(String entry) {
        if (!containsEntry(entry)) {
            strippables.strippables().add(entry);
            reload();
            return true;
        }
        return false;
    }

    public static boolean removeEntry(String entry) {
        if (containsEntry(entry)) {
            strippables.strippables().removeIf(target -> target.equals(entry));
            reload();
            return true;
        }
        return false;
    }

    public static void writeJson(File jsonFile) {
        try(Writer writer = new FileWriter(jsonFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(strippables, writer);
        } catch(IOException e) {
            ModLogger.error("Failed to write to world-stripper json file", e);
        }
    }

    public static void readJson(File jsonFile) {
        try(Reader reader = new FileReader(jsonFile)) {
            strippables = gson.fromJson(reader, Strippables.class);
        } catch(IOException e) {
            ModLogger.error("Failed to read world-stripper json file", e);
        }
    }

    private static void createDirectory() {
        Path path = Paths.get(Platform.getConfigFolder().toAbsolutePath().toString(), WorldStripper.MOD_ID);
        try {
            ModLogger.info("Creating World Stripper directory");
            Files.createDirectory(path);
        } catch (FileAlreadyExistsException ignored) {
        } catch (IOException e) {
            ModLogger.error("Failed to create world-stripper directory", e);
        }
    }

}
