package net.mrlas;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.mrlas.command.SetarmsCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mrlas implements ModInitializer {
	public static final String id = "mrlas";
    public static final Logger LOGGER = LoggerFactory.getLogger(id);

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((SetarmsCommand::register));
	}
}