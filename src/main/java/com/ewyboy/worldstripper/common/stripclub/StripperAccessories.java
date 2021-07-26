package com.ewyboy.worldstripper.common.stripclub;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class StripperAccessories {

    public static BlockState getStateFromRaytrace() {
        Minecraft instance = Minecraft.getInstance();

        if(instance.hitResult.getType() != HitResult.Type.BLOCK) { return null; }

        Vec3 blockVector = instance.hitResult.getLocation();

        double blockX = blockVector.x();
        double blockY = blockVector.y();
        double blockZ = blockVector.z();

        double playerX = instance.player.getX();
        double playerY = instance.player.getY();
        double playerZ = instance.player.getZ();

        if(blockX == Math.floor(blockX) && blockX <= playerX)        {blockX--;}
        if(blockY == Math.floor(blockY) && blockY <= playerY + 1)    {blockY--;}
        if(blockZ == Math.floor(blockZ) && blockZ <= playerZ)        {blockZ--;}

        BlockState blockState = instance.level.getBlockState(new BlockPos(blockX, blockY, blockZ));

        return blockState;
    }

}
