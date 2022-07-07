package mod.gate;

import mod.gate.commands.CommandDispatcher;
import mod.gate.events.EventHandler;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Gate implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Gate");
	@Override
	public void onInitializeClient() {
		LOGGER.info("Hello Fabric world!");
		EventHandler.registerEvent(new CommandDispatcher());

	}
}
