package mod.gate;

import com.google.gson.JsonParseException;
import mod.gate.commands.CommandDispatcher;
import mod.gate.config.Configuration;
import mod.gate.events.EventHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;

import static mod.gate.utils.FileUtils.writeJson;

public class Gate implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Gate");
    public static final String CONFIG_PATH = "gate-config.json";

    public static Configuration config = new Configuration();

    @Override
    public void onInitializeClient() {
        //load config
        try {
            config = config.load(CONFIG_PATH);
        } catch (FileNotFoundException e) {
            writeJson(config, FabricLoader.getInstance().getConfigDir().resolve(CONFIG_PATH).toFile());
        } catch (JsonParseException | IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("Hello Fabric world!");
        EventHandler.registerEvent(new CommandDispatcher());
    }
}
