package com.ewyboy.worldstripper.common.stripclub;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

public class StripperAccessories {

    public static BlockState getStateFromRaytrace() {
        Minecraft instance = Minecraft.getInstance();

        if(instance.objectMouseOver.getType() != RayTraceResult.Type.BLOCK) { return null; }

        Vector3d blockVector = instance.objectMouseOver.getHitVec();

        double blockX = blockVector.getX();
        double blockY = blockVector.getY();
        double blockZ = blockVector.getZ();

        double playerX = instance.player.getPosX();
        double playerY = instance.player.getPosY();
        double playerZ = instance.player.getPosZ();

        if(blockX == Math.floor(blockX) && blockX <= playerX)        {blockX--;}
        if(blockY == Math.floor(blockY) && blockY <= playerY + 1)    {blockY--;}
        if(blockZ == Math.floor(blockZ) && blockZ <= playerZ)        {blockZ--;}

        BlockState blockState = instance.world.getBlockState(new BlockPos(blockX, blockY, blockZ));

        return blockState;
    }

}
