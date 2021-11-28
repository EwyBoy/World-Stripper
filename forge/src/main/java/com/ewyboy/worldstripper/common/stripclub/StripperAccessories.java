package com.ewyboy.worldstripper.common.stripclub;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

public class StripperAccessories {

    public static BlockState getStateFromRaytrace() {
        Minecraft instance = Minecraft.getInstance();

        if (instance.hitResult != null && instance.hitResult.getType() != RayTraceResult.Type.BLOCK) {
            return null;
        }

        Vector3d blockVector;

        if (instance.hitResult != null) {
            blockVector = instance.hitResult.getLocation();

            double blockX = blockVector.x();
            double blockY = blockVector.y();
            double blockZ = blockVector.z();

            double playerX = 0;
            double playerY = 0;
            double playerZ = 0;

            if (instance.player != null) playerX = instance.player.getX();
            if (instance.player != null) playerY = instance.player.getY();
            if (instance.player != null) playerZ = instance.player.getZ();

            if(blockX == Math.floor(blockX) && blockX <= playerX)     {blockX--;}
            if(blockY == Math.floor(blockY) && blockY <= playerY + 1) {blockY--;}
            if(blockZ == Math.floor(blockZ) && blockZ <= playerZ)     {blockZ--;}

            if (instance.level != null) {
                return instance.level.getBlockState(new BlockPos(blockX, blockY, blockZ));
            }
        }

        return null;
    }

}
