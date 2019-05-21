package com.ewyboy.worldstripper;

import net.minecraft.block.Block;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class BlockCounter {

    private LinkedHashMap counter = new LinkedHashMap();

    public BlockCounter() {}

    public void increaseCount(Block block) {
        String name = block.getLocalizedName();
        if (name != null) {
            if (!this.counter.containsKey(name)) {
                this.counter.put(name, 1);
            } else {
                this.counter.put(name, (Integer) this.counter.get(name) + 1);
            }

        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        int counter = 0;
        for(Iterator i$ = this.counter.entrySet().iterator(); i$.hasNext(); ++counter) {
            Entry entry = (Entry)i$.next();
            result.append(entry.getKey()).append(" (").append(entry.getValue()).append(")");
            if (counter < this.counter.size() - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }
}
