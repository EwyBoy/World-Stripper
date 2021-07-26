package com.ewyboy.worldstripper.common.commands.profile;

import com.ewyboy.worldstripper.common.config.Config;
import com.ewyboy.worldstripper.common.config.ConfigHelper;
import com.ewyboy.worldstripper.common.config.ConfigOptions;
import com.ewyboy.worldstripper.common.stripclub.ProfileManager;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;

import java.util.Set;

public class CommandProfile {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {

        return Commands.literal("profile").requires(cs -> cs.hasPermission(2)).executes(context -> {
            ServerPlayer player = context.getSource().getPlayerOrException();
            Set<Config.Profiles.Profile> newProfile = ProfileManager.cycleProfile();
            ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile", newProfile.toArray()[0]);
            player.displayClientMessage(new TextComponent(ChatFormatting.GREEN + "Profile" + ChatFormatting.WHITE + " set to: " + ProfileManager.profileNameMap.get(ConfigOptions.Profiles.profile)), false);
            return 0;
        }).then(Commands.argument("Profile ID", IntegerArgumentType.integer(1, 5)).executes(context -> {
            ServerPlayer player = context.getSource().getPlayerOrException();
            ConfigOptions.Profiles.profile = ProfileManager.idFromProfileMap.get(IntegerArgumentType.getInteger(context, "Profile ID"));
            ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile", ConfigOptions.Profiles.profile);
            player.displayClientMessage(new TextComponent(ChatFormatting.GREEN + "Profile" + ChatFormatting.WHITE + " set to: " + ProfileManager.profileNameMap.get(ConfigOptions.Profiles.profile)), false);
            return 0;
        }));
    }

}

