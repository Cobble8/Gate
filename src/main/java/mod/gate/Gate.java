package mod.gate;

import com.google.gson.JsonParseException;
import mod.gate.core.config.Configuration;
import mod.gate.core.events.EventHandler;
import mod.gate.utils.FileUtils;
import mod.gate.utils.Reference;
import mod.gate.features.GuiTesting;
import mod.gate.features.NPCDialogue;
import net.fabricmc.api.ClientModInitializer;
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
