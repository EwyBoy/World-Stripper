package com.ewyboy.worldstripper.common.config.temp;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.common.ForgeConfigSpec.Builder;

public class ConfigTest {

    private static final Builder BUILDER = new Builder();

    public static final class CategoryGeneral {

        public final ForgeConfigSpec.ConfigValue<List<? extends String>> test_1;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> test_2;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> test_3;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> test_4;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> test_5;

        public static List<String> myList = new ArrayList<>();

        static {
            myList.add("Test 1");
            myList.add("Test 2");
            myList.add("Test 3");
        }

        private CategoryGeneral() {

            BUILDER.comment("General mod settings").push("general");

            test_1 = BUILDER.defineList("test_1", Lists.newArrayList(myList), o -> o instanceof String);
            test_2 = BUILDER.defineList("test_2", Lists.newArrayList(myList), o -> o instanceof String);
            test_3 = BUILDER.defineList("test_3", Lists.newArrayList(myList), o -> o instanceof String);
            test_4 = BUILDER.defineList("test_4", Lists.newArrayList(myList), o -> o instanceof String);
            test_5 = BUILDER.defineList("test_5", Lists.newArrayList(myList), o -> o instanceof String);

            BUILDER.pop();
        }
    }

    public static final ForgeConfigSpec CONFIG = BUILDER.build();

}
