package mod.gate.utils;

import com.google.gson.GsonBuilder;

import java.io.*;

public class FileUtils {

    public static <T> T readJson(Class<T> tClass, File filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            return new GsonBuilder().create().fromJson(reader, tClass);
        }
    }

    public static <T> T readJson(Class<T> tClass, String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            return new GsonBuilder().create().fromJson(reader, tClass);
        }
    }

    public static void writeJson(Object object, File filePath) {
        String[] pathArray = filePath.toString().split("\\/");
        if (pathArray[pathArray.length - 1].matches(".*\\..*"))
            return;//not leading to a file

        filePath.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(object));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeJson(Object object, String filePath) {
        File file = new File(filePath);

        String[] pathArray = filePath.split("\\/");
        if (!pathArray[pathArray.length - 1].matches(".*\\..*"))
            return;//not leading to a file

        file.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(object));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
