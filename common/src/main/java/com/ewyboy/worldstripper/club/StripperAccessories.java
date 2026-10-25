package com.ewyboy.worldstripper.club;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class StripperAccessories {

    /**
     * The block the player is looking at, ray-traced on the server.
     * (This used to read the client's Minecraft instance, which crashes dedicated servers.)
     * @return The targeted block state, or null when the player isn't looking at a block in reach.
     */
    public static BlockState getStateFromRaytrace(ServerPlayer player) {
        HitResult hit = player.pick(player.blockInteractionRange(), 1.0F, false);
        return hit instanceof BlockHitResult blockHit && hit.getType() == HitResult.Type.BLOCK
                ? player.level().getBlockState(blockHit.getBlockPos())
                : null;
    }

}
