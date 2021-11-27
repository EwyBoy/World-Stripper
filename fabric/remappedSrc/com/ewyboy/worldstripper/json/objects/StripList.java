package com.ewyboy.worldstripper.json.objects;

import java.util.List;

public class StripList {

    private List<String> entries;

    public StripList(List<String> entries) {
        this.entries = entries;
    }

    public List<String> getEntries() {
        return entries;
    }

    public void setEntries(List<String> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        return "StripList{" +
            "entries=" + entries +
        '}';
    }
}
