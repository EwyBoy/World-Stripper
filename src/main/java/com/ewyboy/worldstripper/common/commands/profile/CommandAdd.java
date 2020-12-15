package com.ewyboy.worldstripper.common.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockStateArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class CommandAdd {

    public static ArgumentBuilder<CommandSource, ?> register() {

        return (Commands.literal("add").requires((commandSource) -> {
            return commandSource.hasPermissionLevel(2);
        }).then(Commands.argument("profile", StringArgumentType.string())).then(Commands.argument("block", BlockStateArgument.blockState()).executes((commandSource) -> {
            ServerPlayerEntity player = commandSource.getSource().asPlayer();
            player.sendStatusMessage(new StringTextComponent(BlockStateArgument.getBlockState(commandSource, "block").getState().getBlock().getRegistryName().toString()), false);
            return 1;
        })));

       /* return Commands.literal("add").requires(cs -> cs.hasPermissionLevel(2)).executes(context -> {
            return 0;
        }).then(Commands.argument("block", BlockStateArgument.blockState())).executes(context -> {
            ServerPlayerEntity player = context.getSource().asPlayer();
            BlockState state = BlockStateArgument.getBlockState(context, "block").getState();
            player.sendStatusMessage(new StringTextComponent(state.getBlock().getRegistryName().toString()), false);
            return 0;
        });*/

       /*

       return (Commands.literal("add").requires((commandSource) -> {
            return commandSource.hasPermissionLevel(2);
        }).then(Commands.argument("pos", BlockPosArgument.blockPos()).then(Commands.argument("block", BlockStateArgument.blockState()).executes((commandSource) -> {
            return setBlock(commandSource.getSource(), BlockPosArgument.getLoadedBlockPos(commandSource, "pos"), BlockStateArgument.getBlockState(commandSource, "block"), SetBlockCommand.Mode.REPLACE, (Predicate<CachedBlockInfo>) null);
        }).then(Commands.literal("destroy").executes((commandSource) -> {
            return setBlock(commandSource.getSource(), BlockPosArgument.getLoadedBlockPos(commandSource, "pos"), BlockStateArgument.getBlockState(commandSource, "block"), SetBlockCommand.Mode.DESTROY, (Predicate<CachedBlockInfo>) null);
        })).then(Commands.literal("keep").executes((commandSource) -> {
            return setBlock(commandSource.getSource(), BlockPosArgument.getLoadedBlockPos(commandSource, "pos"), BlockStateArgument.getBlockState(commandSource, "block"), SetBlockCommand.Mode.REPLACE, (p_198687_0_) -> {
                return p_198687_0_.getWorld().isAirBlock(p_198687_0_.getPos());
            });
        })).then(Commands.literal("replace").executes((commandSource) -> {
            return setBlock(commandSource.getSource(), BlockPosArgument.getLoadedBlockPos(commandSource, "pos"), BlockStateArgument.getBlockState(commandSource, "block"), SetBlockCommand.Mode.REPLACE, (Predicate<CachedBlockInfo>) null);
        })))));

        */
    }

}
