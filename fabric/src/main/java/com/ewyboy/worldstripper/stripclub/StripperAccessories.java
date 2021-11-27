package com.ewyboy.worldstripper.stripclub;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class StripperAccessories {

    public static BlockState getStateFromRaytrace() {
        MinecraftClient instance = MinecraftClient.getInstance();

        if (instance.crosshairTarget != null && instance.crosshairTarget.getType() != HitResult.Type.BLOCK) {
            return null;
        }

        Vec3d blockVector;

        if (instance.crosshairTarget != null) {
            blockVector = instance.crosshairTarget.getPos();

            double blockX = blockVector.getX();
            double blockY = blockVector.getY();
            double blockZ = blockVector.getZ();

            double playerX = 0;
            double playerY = 0;
            double playerZ = 0;

            if (instance.player != null) playerX = instance.player.getX();
            if (instance.player != null) playerY = instance.player.getY();
            if (instance.player != null) playerZ = instance.player.getZ();

            if(blockX == Math.floor(blockX) && blockX <= playerX)        {blockX--;}
            if(blockY == Math.floor(blockY) && blockY <= playerY + 1)    {blockY--;}
            if(blockZ == Math.floor(blockZ) && blockZ <= playerZ)        {blockZ--;}

            if (instance.world != null) {
                return instance.world.getBlockState(new BlockPos(blockX, blockY, blockZ));
            }
        }

        return null;
    }


}
