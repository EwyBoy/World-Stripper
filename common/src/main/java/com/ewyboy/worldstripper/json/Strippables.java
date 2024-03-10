package com.ewyboy.worldstripper.json;

import java.util.List;

public record Strippables(List<String> strippables) {

    public String toJson() {
        return "Strippables{" +
                "strippables=" + strippables +
                '}';
    }
}
