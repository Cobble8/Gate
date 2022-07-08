package mod.gate.config;

import mod.gate.Gate;
import mod.gate.utils.Reference;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;

import static mod.gate.utils.FileUtils.readJson;
import static mod.gate.utils.FileUtils.writeJson;

public class Configuration {
    public Configuration load(String path) throws IOException {
        Configuration newConfig = readJson(Configuration.class, Reference.CONFIG_PATH + path);
        if (!newConfig.useGlobalConfig)
            return  readJson(Configuration.class, Reference.CONFIG_PATH + path);

        return newConfig;
    }

    public void save(String path) {
        //trigger when update of config file happens
        if (!this.useGlobalConfig) {
            writeJson(this, Reference.CONFIG_PATH + path);
            return;
        }
        writeJson(this, Reference.CONFIG_PATH + path);
    }

    //to add an option, define a public field (the value put behind the equal is the original value of the configuration)

    public boolean onSkyblockOnly = true;

    public boolean isDebugMode = false;

    public boolean useGlobalConfig = true;//if set to false will get the config from a uuid specific config file

    public String configPath = Gate.GLOBAL_CONFIG_PATH;//if useGlobalConfig is off it will take the config path from here


}
