package mod.gate.core.config;

import mod.gate.Gate;
import mod.gate.utils.Reference;

import java.io.IOException;

import static mod.gate.utils.FileUtils.readJson;
import static mod.gate.utils.FileUtils.writeJson;

public class Configuration {
    public Configuration load(String path) throws IOException {
        return readJson(Configuration.class, Reference.CONFIG_PATH + path);
    }

    public void save(String path) {
        //trigger when update of config file happens
        writeJson(this, Reference.CONFIG_PATH + path);
    }

    //to add an option, define a public field (the value put behind the equal is the original value of the configuration)

    public boolean onSkyblockOnly = true;

    public boolean isDebugMode = false;


}
