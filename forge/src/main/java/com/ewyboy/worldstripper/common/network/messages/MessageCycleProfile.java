package com.ewyboy.worldstripper.common.network.messages;

import com.ewyboy.worldstripper.common.config.Config;
import com.ewyboy.worldstripper.common.config.ConfigHelper;
import com.ewyboy.worldstripper.common.config.ConfigOptions;
import com.ewyboy.worldstripper.common.stripclub.ProfileManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Set;
import java.util.function.Supplier;

public class MessageCycleProfile {

    public MessageCycleProfile() {}

    public static void encode(MessageCycleProfile pkt, PacketBuffer buf) {}

    public static MessageCycleProfile decode(PacketBuffer buf) {
        return new MessageCycleProfile();
    }

    public static void handle(MessageCycleProfile message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if(player.isSpectator() || player.isCreative()) {

                Set<Config.Profiles.Profile> newProfile = ProfileManager.cycleProfile();
                ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + "profile", newProfile.toArray()[0]);
                player.displayClientMessage(new StringTextComponent(TextFormatting.GREEN + "Profile"+ TextFormatting.WHITE + " set to: " + ConfigOptions.Profiles.profile.toString()), false);

            } else {
                player.displayClientMessage(new StringTextComponent(TextFormatting.RED + "Error: You have to be in creative mode to use this feature!"), false);
            }
        });
        ctx.get().setPacketHandled(true);
    }


}
