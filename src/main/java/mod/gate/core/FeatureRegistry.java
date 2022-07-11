package mod.gate.core;

import mod.gate.core.config.ConfigManager;
import mod.gate.core.events.EventHandler;
import mod.gate.features.HudTesting;
import mod.gate.features.NPCDialogue;

public class FeatureRegistry {
    private void registerFeature(Object feature) {
        EventHandler.registerEvent(feature);
        ConfigManager.registerConfig(feature);
    }

    public FeatureRegistry() {
        registerFeature(new NPCDialogue());
        registerFeature(new HudTesting());
    }
}
