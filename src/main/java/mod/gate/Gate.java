package mod.gate;

import mod.gate.core.FeatureRegistry;
import mod.gate.core.config.ConfigManager;
import mod.gate.core.events.EndTickEvent;
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

        new FeatureRegistry();
        ConfigManager.init();

        ClientTickEvents.END_WORLD_TICK.register((world) -> {
            EventHandler.run(new EndTickEvent(world));
        });
    }
}
