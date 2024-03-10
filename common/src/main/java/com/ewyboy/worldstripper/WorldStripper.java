package com.ewyboy.worldstripper;

import com.ewyboy.worldstripper.client.Keymappings;
import com.ewyboy.worldstripper.command.WSCommands;
import com.ewyboy.worldstripper.json.StrippablesHandler;
import com.ewyboy.worldstripper.json.WSConfigLoader;
import com.ewyboy.worldstripper.networking.NetworkHandler;
import com.ewyboy.worldstripper.workers.WorldWorker;

public class WorldStripper
{
	public static final String MOD_ID = "worldstripper";

	public static void init() {
		WSCommands.init();
		StrippablesHandler.init();
		WSConfigLoader.init();
		Keymappings.init();
		NetworkHandler.init();
		WorldWorker.init();
	}

}
