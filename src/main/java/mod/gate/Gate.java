package mod.gate;

import mod.gate.core.FeatureRegistry;
import mod.gate.utils.Reference;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Gate implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(Reference.NAME);

    @Override
    public void onInitializeClient() {
        LOGGER.info(Reference.NAME+" has been initialized!");

        new FeatureRegistry();
    }
}
