package com.ewyboy.worldstripper.common.util;

import java.util.HashMap;
import java.util.Map;

public class Profile {

    public static Map<Integer, Object[]> profileMapper = new HashMap<>();

    static {
        profileMapper.put(1, Config.COMMON.stripProfile1Config.get().toArray());
        profileMapper.put(2, Config.COMMON.stripProfile2Config.get().toArray());
        profileMapper.put(3, Config.COMMON.stripProfile3Config.get().toArray());
        profileMapper.put(4, Config.COMMON.stripProfile4Config.get().toArray());
        profileMapper.put(5, Config.COMMON.stripProfile5Config.get().toArray());
    }
}
