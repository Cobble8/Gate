package mod.gate.config;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;

import static mod.gate.utils.FileUtils.readJson;
import static mod.gate.utils.FileUtils.writeJson;

public class Configuration {
    public Configuration load(String path) throws IOException {
        return readJson(Configuration.class, FabricLoader.getInstance().getConfigDir().resolve(path).toFile());
    }

    public void save(String path) {
        //trigger when update of config file happens
        writeJson(this, FabricLoader.getInstance().getConfigDir().resolve(path).toFile());
    }

    //to add an option, define a public field (the value put behind the equal is the original value of the configuration)

    public boolean onSkyblockOnly = true;

    public boolean isDebugMode = false;


}
