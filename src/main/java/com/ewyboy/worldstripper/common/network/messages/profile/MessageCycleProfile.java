package com.ewyboy.worldstripper.common.network.messages.profile;

import com.ewyboy.worldstripper.common.config.Config;
import com.ewyboy.worldstripper.common.config.ConfigHelper;
import com.ewyboy.worldstripper.common.config.ConfigOptions;
import com.ewyboy.worldstripper.common.stripclub.ProfileManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.Set;
import java.util.function.Supplier;

public class MessageCycleProfile {

    public MessageCycleProfile() {}

    public static void encode(MessageCycleProfile pkt, FriendlyByteBuf buf) {}

    public static MessageCycleProfile decode(FriendlyByteBuf buf) {
        return new MessageCycleProfile();
    }

    public static void handle(MessageCycleProfile message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if(player.isSpectator() || player.isCreative()) {

                Set<Config.Profiles.Profile> newProfile = ProfileManager.cycleProfile();
                ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile", newProfile.toArray()[0]);
                player.displayClientMessage(new TextComponent(ChatFormatting.GREEN + "Profile"+ ChatFormatting.WHITE + " set to: " + ConfigOptions.Profiles.profile.toString()), false);

            } else {
                player.displayClientMessage(new TextComponent(ChatFormatting.RED + "Error: You have to be in creative mode to use this feature!"), false);
            }
        });
        ctx.get().setPacketHandled(true);
    }


}
