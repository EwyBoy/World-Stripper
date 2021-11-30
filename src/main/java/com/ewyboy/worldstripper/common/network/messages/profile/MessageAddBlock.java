package com.ewyboy.worldstripper.common.network.messages.profile;

import com.ewyboy.worldstripper.common.config.ConfigHelper;
import com.ewyboy.worldstripper.common.config.ConfigOptions;
import com.ewyboy.worldstripper.common.stripclub.ProfileManager;
import com.ewyboy.worldstripper.common.stripclub.StripperAccessories;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class MessageAddBlock {

    public MessageAddBlock() {}

    public static void encode(MessageAddBlock pkt, FriendlyByteBuf buf) {}

    public static MessageAddBlock decode(FriendlyByteBuf buf) {

        return new MessageAddBlock();
    }

    public static void handle(MessageAddBlock message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if(player.isSpectator() || player.isCreative()) {

                BlockState state = StripperAccessories.getStateFromRaytrace();

                if (state != null) {
                    String blockString = Objects.requireNonNull(state.getBlock().getRegistryName()).toString();
                    String blockName = state.getBlock().getName().getString();

                    List<String> selectedList = ConfigHelper.profileMap.get(ConfigOptions.Profiles.profile);

                    if(!selectedList.contains(blockString)) {
                        selectedList.add(blockString);
                        ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.PROFILES + ConfigHelper.profilePathMap.get(ConfigOptions.Profiles.profile), selectedList);
                        player.displayClientMessage(new TextComponent(blockName + " was added to strip " + ProfileManager.profileNameMap.get(ConfigOptions.Profiles.profile)), false);
                    } else {
                        player.displayClientMessage(new TextComponent(ChatFormatting.RED + "Error: " + blockName + " is already in this profile."), false);
                    }
                }

            } else {
                player.displayClientMessage(new TextComponent(ChatFormatting.RED + "Error: You have to be in creative mode to use this feature!"), false);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
