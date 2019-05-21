package com.ewyboy.worldstripper;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.LinkedHashMap;
import java.util.Map;

public class RemovedBlockCache {

    private LinkedHashMap map = new LinkedHashMap();

    public RemovedBlockCache() {}

    public void addToCache(int x, int y, int z, Block block) {
        this.map.put(new Coordinates(x, y, z), new Object[]{block, 0});
    }

    public void addToCache(int x, int y, int z, Block block, int metadata) {
        this.map.put(new Coordinates(x, y, z), new Object[]{block, metadata});
    }

    public void replaceBlocks(World world) {
        for (Object o : this.map.entrySet()) {
            Map.Entry<RemovedBlockCache.Coordinates, Object[]> entry = (Map.Entry) o;
            RemovedBlockCache.Coordinates coords = entry.getKey();
            Object[] obj = entry.getValue();
            world.setBlock(coords.x, coords.y, coords.z, (Block) obj[0], (Integer) obj[1], 2);
        }
    }

    public class Coordinates {

        public int x;
        public int y;
        public int z;

        public Coordinates(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int hashCode() {
            return this.toString().hashCode();
        }

        public String toString() {
            return this.x + " " + this.y + " " + this.z;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Coordinates)) {
                return false;
            } else {
                Coordinates coord = (Coordinates) obj;
                return this.x == coord.x && this.y == coord.y && this.z == coord.z;
            }
        }
    }
}
