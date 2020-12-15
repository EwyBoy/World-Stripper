package com.ewyboy.worldstripper.common.commands.profile;

import com.ewyboy.worldstripper.common.config.Config;
import com.ewyboy.worldstripper.common.config.ConfigHelper;
import com.ewyboy.worldstripper.common.config.ConfigOptions;
import com.ewyboy.worldstripper.common.stripclub.ProfileManager;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.Set;

public class CommandProfile {

    public static ArgumentBuilder<CommandSource, ?> register() {

        return Commands.literal("profile").requires(cs -> cs.hasPermissionLevel(2)).executes(context -> {
            ServerPlayerEntity player = context.getSource().asPlayer();
            Set<Config.Profiles.Profile> newProfile = ProfileManager.cycleProfile();
            ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile", newProfile.toArray()[0]);
            player.sendStatusMessage(new StringTextComponent(TextFormatting.GREEN + "Profile" + TextFormatting.WHITE + " set to: " + ProfileManager.profileNameMap.get(ConfigOptions.Profiles.profile)), false);
            return 0;
        }).then(Commands.argument("Profile ID", IntegerArgumentType.integer(1, 5)).executes(context -> {
            ServerPlayerEntity player = context.getSource().asPlayer();
            ConfigOptions.Profiles.profile = ProfileManager.idFromProfileMap.get(IntegerArgumentType.getInteger(context, "Profile ID"));
            ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile", ConfigOptions.Profiles.profile);
            player.sendStatusMessage(new StringTextComponent(TextFormatting.GREEN + "Profile" + TextFormatting.WHITE + " set to: " + ProfileManager.profileNameMap.get(ConfigOptions.Profiles.profile)), false);
            return 0;
        }));
    }

}

