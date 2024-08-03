package com.ewyboy.worldstripper;

import com.ewyboy.worldstripper.command.WSCommands;
import com.ewyboy.worldstripper.json.DirectoryHandler;
import com.ewyboy.worldstripper.json.StrippablesHandler;
import com.ewyboy.worldstripper.json.WSConfigLoader;
import com.ewyboy.worldstripper.networking.packets.ServerboundAddBlockKeyPressedPacket;
import com.ewyboy.worldstripper.networking.packets.ServerboundDressWorldKeyPressedPacket;
import com.ewyboy.worldstripper.networking.packets.ServerboundRemoveBlockKeyPressedPacket;
import com.ewyboy.worldstripper.networking.packets.ServerboundStripWorldKeyPressedPacket;
import com.ewyboy.worldstripper.services.Services;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;

public class WorldStripper
{
	public static final String MOD_ID = "worldstripper";

	public static void init() {
		DirectoryHandler.init();
		StrippablesHandler.init();
		WSConfigLoader.init();
	}

	public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
		WSCommands.register(dispatcher, context);
	}

	public static void registerServerPackets(Object... args)
	{

		Services.PLATFORM.registerServerboundPacket(
				ServerboundAddBlockKeyPressedPacket.TYPE,
				ServerboundAddBlockKeyPressedPacket.class,
				ServerboundAddBlockKeyPressedPacket.CODEC,
				ServerboundAddBlockKeyPressedPacket :: handlePacket,
				args
		);

		Services.PLATFORM.registerServerboundPacket(
				ServerboundRemoveBlockKeyPressedPacket.TYPE,
				ServerboundRemoveBlockKeyPressedPacket.class,
				ServerboundRemoveBlockKeyPressedPacket.CODEC,
				ServerboundRemoveBlockKeyPressedPacket :: handlePacket,
				args
		);

		Services.PLATFORM.registerServerboundPacket(
				ServerboundStripWorldKeyPressedPacket.TYPE,
				ServerboundStripWorldKeyPressedPacket.class,
				ServerboundStripWorldKeyPressedPacket.CODEC,
				ServerboundStripWorldKeyPressedPacket :: handlePacket,
				args
		);

		Services.PLATFORM.registerServerboundPacket(
				ServerboundDressWorldKeyPressedPacket.TYPE,
				ServerboundDressWorldKeyPressedPacket.class,
				ServerboundDressWorldKeyPressedPacket.CODEC,
				ServerboundDressWorldKeyPressedPacket :: handlePacket,
				args
		);

	}

	public static void registerClientPackets(Object... args) { }


}
