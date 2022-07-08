package mod.gate;

import com.google.gson.JsonParseException;
import mod.gate.commands.CommandDispatcher;
import mod.gate.config.Configuration;
import mod.gate.events.EventHandler;
import mod.gate.utils.FileUtils;
import mod.gate.utils.Reference;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;

import mod.gate.utils.FileUtils.*;

public class Gate implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Gate");
    public static final String GLOBAL_CONFIG_PATH = "gate-config.json";

    public static Configuration config = new Configuration();

    @Override
    public void onInitializeClient() {
       LOGGER.info(Reference.NAME+" has been initialized!");
		   EventHandler.registerEvent(new CommandDispatcher());

       //load config
        initConfig();
    }

    public void initConfig() {
        try {
            config = config.load(GLOBAL_CONFIG_PATH);
        } catch (FileNotFoundException e) {
            FileUtils.writeJson(config, Reference.CONFIG_PATH + GLOBAL_CONFIG_PATH);//set global config if doesn't exist
        } catch (JsonParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
