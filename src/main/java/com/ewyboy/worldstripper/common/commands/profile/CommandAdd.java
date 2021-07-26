package com.ewyboy.worldstripper.common.commands.profile;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.TextComponent;

public class CommandAdd {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {

        return (Commands.literal("add").requires((commandSource) -> {
            return commandSource.hasPermission(2);
        }).then(Commands.argument("profile", StringArgumentType.string())).then(Commands.argument("block", BlockStateArgument.block()).executes((commandSource) -> {
            ServerPlayer player = commandSource.getSource().getPlayerOrException();
            player.displayClientMessage(new TextComponent(BlockStateArgument.getBlock(commandSource, "block").getState().getBlock().getRegistryName().toString()), false);
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
