package com.ewyboy.worldstripper.common.util;

import java.util.HashMap;
import java.util.Map;

public class Profile {

    public static Map<Integer, Object[]> profileMapper = new HashMap();

    public Profile() {}

    static {
        profileMapper.put(1, Config.SETTINGS.stripProfile1Config.get().toArray());
        profileMapper.put(2, Config.SETTINGS.stripProfile2Config.get().toArray());
        profileMapper.put(3, Config.SETTINGS.stripProfile3Config.get().toArray());
        profileMapper.put(4, Config.SETTINGS.stripProfile4Config.get().toArray());
        profileMapper.put(5, Config.SETTINGS.stripProfile5Config.get().toArray());
    }
}
