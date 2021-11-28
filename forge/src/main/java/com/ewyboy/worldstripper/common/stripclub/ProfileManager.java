package com.ewyboy.worldstripper.common.stripclub;

import com.ewyboy.worldstripper.common.config.Config;
import com.ewyboy.worldstripper.common.config.ConfigOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ProfileManager {

    public static HashMap<Config.Profiles.Profile, String> profileNameMap = new HashMap<>();

    static {
        profileNameMap.put(Config.Profiles.Profile.PROFILE_1, "Profile 1");
        profileNameMap.put(Config.Profiles.Profile.PROFILE_2, "Profile 2");
        profileNameMap.put(Config.Profiles.Profile.PROFILE_3, "Profile 3");
        profileNameMap.put(Config.Profiles.Profile.PROFILE_4, "Profile 4");
        profileNameMap.put(Config.Profiles.Profile.PROFILE_5, "Profile 5");
    }

    public static HashMap<Config.Profiles.Profile, Integer> profileFromIdMap = new HashMap<>();

    static {
        profileFromIdMap.put(Config.Profiles.Profile.PROFILE_1, 1);
        profileFromIdMap.put(Config.Profiles.Profile.PROFILE_2, 2);
        profileFromIdMap.put(Config.Profiles.Profile.PROFILE_3, 3);
        profileFromIdMap.put(Config.Profiles.Profile.PROFILE_4, 4);
        profileFromIdMap.put(Config.Profiles.Profile.PROFILE_5, 5);
    }

    public static HashMap<Integer, Config.Profiles.Profile> idFromProfileMap = new HashMap<>();

    static {
        idFromProfileMap.put(1, Config.Profiles.Profile.PROFILE_1);
        idFromProfileMap.put(2, Config.Profiles.Profile.PROFILE_2);
        idFromProfileMap.put(3, Config.Profiles.Profile.PROFILE_3);
        idFromProfileMap.put(4, Config.Profiles.Profile.PROFILE_4);
        idFromProfileMap.put(5, Config.Profiles.Profile.PROFILE_5);
    }

    public static Enum<Config.Profiles.Profile> startProfile = ConfigOptions.Profiles.profile;
    public static int key = profileFromIdMap.get(startProfile);

    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), value)).map(Map.Entry :: getKey).collect(Collectors.toSet());
    }

    public static Set<Config.Profiles.Profile> cycleProfile() {
        if (profileFromIdMap.get(ConfigOptions.Profiles.profile) != key) key = profileFromIdMap.get(ConfigOptions.Profiles.profile);
        key = key < 5 ? key + 1 : 1;

        return getKeysByValue(profileFromIdMap, key);
    }

}
