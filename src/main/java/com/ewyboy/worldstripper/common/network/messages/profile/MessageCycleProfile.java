package com.ewyboy.worldstripper.common.network.messages.profile;

import com.ewyboy.worldstripper.common.config.ConfigHelper;
import com.ewyboy.worldstripper.common.config.ConfigOptions;
import com.ewyboy.worldstripper.common.stripclub.StripperAccessories;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class MessageAddBlock {

    public MessageAddBlock() {}

    public static void encode(MessageAddBlock pkt, PacketBuffer buf) {}

    public static MessageAddBlock decode(PacketBuffer buf) {

        return new MessageAddBlock();
    }

    public static void handle(MessageAddBlock message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if(player.isSpectator() || player.isCreative()) {

                BlockState state = StripperAccessories.getStateFromRaytrace();

                if (state != null) {
                    String blockString = Objects.requireNonNull(state.getBlock().getRegistryName()).toString();
                    String blockName = state.getBlock().getTranslatedName().getString();

                    List<String> selectedList = ConfigOptions.General.testList;

                    if(!selectedList.contains(blockString)) {
                        selectedList.add(blockString);
                        ConfigHelper.setValueAndSaveConfig(ConfigHelper.CategoryName.GENERAL + "test_list", selectedList);
                        player.sendStatusMessage(new StringTextComponent(blockName + " was added to strip profile."), false);
                    } else {
                        player.sendStatusMessage(new StringTextComponent(TextFormatting.RED + "Error: " + blockName + " is already in this profile."), false);
                    }
                }

            } else {
                player.sendStatusMessage(new StringTextComponent(TextFormatting.RED + "Error: You have to be in creative mode to use this feature!"), false);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
