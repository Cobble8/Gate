package mod.gate;

import mod.gate.core.FeatureRegistry;
import mod.gate.core.command.CommandRegistry;
import mod.gate.core.config.ConfigManager;
import mod.gate.core.events.TickEvent;
import mod.gate.core.events.EventHandler;
import mod.gate.utils.Reference;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Gate implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(Reference.NAME);

    @Override
    public void onInitializeClient() {
        LOGGER.info(Reference.NAME+" has been initialized!");

        ConfigManager.init();
        new FeatureRegistry();

        ClientTickEvents.END_WORLD_TICK.register((world) -> {
            EventHandler.run(new TickEvent(world));
        });

        CommandRegistry.init();
    }
}
