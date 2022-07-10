package mod.gate;

import com.google.gson.JsonParseException;
import com.mojang.blaze3d.systems.RenderSystem;
import mod.gate.config.Configuration;
import mod.gate.events.EventHandler;
import mod.gate.features.GuiTesting;
import mod.gate.utils.FileUtils;
import mod.gate.features.NPCDialogue;
import mod.gate.utils.Reference;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.mixin.client.rendering.MixinInGameHud;
import net.minecraft.client.gui.hud.InGameHud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Gate implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(Reference.NAME);
    public static final String GLOBAL_CONFIG_PATH = "gate-config.json";

    public static Configuration config = new Configuration();

    @Override
    public void onInitializeClient() {
        LOGGER.info(Reference.NAME+" has been initialized!");
        EventHandler.registerEvent(new NPCDialogue());
        EventHandler.registerEvent(new GuiTesting());
        //load config
        try {
            config = config.load(GLOBAL_CONFIG_PATH);
        } catch (FileNotFoundException e) {
            FileUtils.writeJson(config, Reference.CONFIG_PATH + GLOBAL_CONFIG_PATH);//set global config if doesn't exist
        } catch (JsonParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
