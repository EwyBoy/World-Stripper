package com.ewyboy.worldstripper.club;

import com.ewyboy.worldstripper.json.WSConfigLoader;

public class BlockUpdater {

    private static int notifyNeighbors() {
        return !WSConfigLoader.getInstance().getConfig().blockStates().notifyNeighbors() ? BlockFlags.NOTIFY_NEIGHBORS : 0;
    }

    private static int blockUpdate() {
        return !WSConfigLoader.getInstance().getConfig().blockStates().updateBlock() ? BlockFlags.BLOCK_UPDATE : 0;
    }

    private static int noRender() {
        return !WSConfigLoader.getInstance().getConfig().blockStates().noRender() ? BlockFlags.NO_RERENDER : 0;
    }

    private static int renderMainThread() {
        return !WSConfigLoader.getInstance().getConfig().blockStates().renderMainThread() ? BlockFlags.RERENDER_MAIN_THREAD : 0;
    }

    private static int updateNeighbors() {
        return !WSConfigLoader.getInstance().getConfig().blockStates().updateNeighbors() ? BlockFlags.UPDATE_NEIGHBORS : 0;
    }

    public static int getBlockUpdateFlag() {
        return notifyNeighbors() | blockUpdate() | noRender() | renderMainThread() | updateNeighbors();
    }

    public static class BlockFlags {
        /**
         * Calls
         * {@link Block#neighborChanged(BlockState, World, BlockPos, Block, BlockPos, boolean)
         * neighborChanged} on surrounding blocks (with isMoving as false). Also updates comparator output state.
         */
        public static final int NOTIFY_NEIGHBORS = (1 << 0);
        /**
         * Calls {@link Level#notifyBlockUpdate(BlockPos, BlockState, BlockState, int)}.<br>
         * Server-side, this updates all the path-finding navigators.
         */
        public static final int BLOCK_UPDATE = (1 << 1);
        /**
         * Stops the blocks from being marked for a render update
         */
        public static final int NO_RERENDER = (1 << 2);
        /**
         * Makes the block be re-rendered immediately, on the main thread.
         * If NO_RERENDER is set, then this will be ignored
         */
        public static final int RERENDER_MAIN_THREAD = (1 << 3);
        /**
         * Causes neighbor updates to be sent to all surrounding blocks (including
         * diagonals). This in turn will call
         * {@link Block#updateDiagonalNeighbors(BlockState, IWorld, BlockPos, int)
         * updateDiagonalNeighbors} on both old and new states, and
         * {@link Block#updateNeighbors(BlockState, IWorld, BlockPos, int)
         * updateNeighbors} on the new state.
         */
        public static final int UPDATE_NEIGHBORS = (1 << 4);

        /**
         * Prevents neighbor changes from spawning item drops, used by
         * {@link Block#replaceBlock(BlockState, BlockState, IWorld, BlockPos, int)}.
         */
        public static final int NO_NEIGHBOR_DROPS = (1 << 5);
    }

}