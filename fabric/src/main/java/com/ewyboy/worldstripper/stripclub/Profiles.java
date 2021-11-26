package com.ewyboy.worldstripper.stripclub;

import com.ewyboy.worldstripper.settings.Settings;
import com.ewyboy.worldstripper.settings.SettingsLoader;

import java.util.HashMap;
import java.util.List;

public class Profiles {

    public static HashMap<Integer, List<String>> profileMapper = new HashMap<>();

    public static void setup() {
        Settings settings = SettingsLoader.SETTINGS;
        profileMapper.put(1, settings.stripProfile1);
        profileMapper.put(2, settings.stripProfile2);
        profileMapper.put(3, settings.stripProfile3);
        profileMapper.put(4, settings.stripProfile4);
        profileMapper.put(5, settings.stripProfile5);
    }

}
