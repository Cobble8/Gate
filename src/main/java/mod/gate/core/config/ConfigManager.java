package mod.gate.core.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import mod.gate.utils.Reference;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ConfigManager {
    //region useConfig
    private static boolean useConfig = true;

    public static void setUseConfig(boolean enabled) {
        useConfig = enabled;
    }
    //endregion

    //region DebugMode
    private static boolean debugMode = false;

    public static boolean inDebugMode() {
        return debugMode;
    }
    //endregion

    private static final File CONFIG_FILE = new File(Reference.CONFIG_PATH, "gate-config");
    private static File USER_FILE;
    private static final String FILE_SUFFIX = ".json";


    private static final ArrayList<ConfigHolder> configs = new ArrayList<>();
    private static JsonObject loadedConfigs;

    private static Gson gson;

    public static void registerConfig(Object feature) {
        for (Field field : FieldUtils.getFieldsWithAnnotation(feature.getClass(), Config.class)) {
            try {
                configs.add(new ConfigHolder(FieldUtils.readField(field, feature, true), field, feature));
                loadConfig(configs.get(configs.size() - 1));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void init() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        if (!useConfig) return;

        CONFIG_FILE.mkdirs();

        USER_FILE = new File(CONFIG_FILE, MinecraftClient.getInstance().getSession().getUuid() + FILE_SUFFIX);

        if (USER_FILE.exists()) {
            try {
                FileReader reader = new FileReader(USER_FILE);
                JsonElement fileElement = JsonParser.parseReader(gson.newJsonReader(reader));
                reader.close();
                if (!fileElement.isJsonObject()) return;

                loadedConfigs = fileElement.getAsJsonObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadConfig(ConfigHolder config) {
        if (loadedConfigs == null || !loadedConfigs.has(config.toString())) return;

        config.setConfig(
                gson.fromJson(loadedConfigs.get(config.toString()), config.getField().getType())
        );
    }

    public static void save() {
        try {
            // create file if necessary
            if (!useConfig) return;
            if (!USER_FILE.exists()) USER_FILE.createNewFile();

            JsonObject configJson = new JsonObject();

            for (ConfigHolder config : configs) {
                if (!FieldUtils.readField(config.getField(), config.getParent()).equals(config.getDefault()))
                    configJson.add(
                            config.toString(),
                            gson.toJsonTree(FieldUtils.readField(config.getField(), config.getParent()))
                    );
            }

            // write json to file
            OutputStreamWriter fileWriter =
                    new OutputStreamWriter(new FileOutputStream(USER_FILE), StandardCharsets.UTF_8);
            gson.toJson(configJson, fileWriter);
            fileWriter.close();
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
