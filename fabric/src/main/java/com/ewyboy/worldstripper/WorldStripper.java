package com.ewyboy.worldstripper;

import com.ewyboy.worldstripper.commands.CommandHandler;
import com.ewyboy.worldstripper.json.StripListHandler;
import com.ewyboy.worldstripper.network.PacketHandler;
import com.ewyboy.worldstripper.workers.WorldWorker;
import net.fabricmc.api.ModInitializer;

public class WorldStripper implements ModInitializer {

	public static final String NAME = "World Stripper";
	public static final String MOD_ID = "worldstripper";

	@Override
	public void onInitialize() {
		WorldWorker.setup();
		PacketHandler.setup();
		CommandHandler.setup();
		StripListHandler.setup();
	}

}