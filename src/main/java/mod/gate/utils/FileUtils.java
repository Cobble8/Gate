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
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(object));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeJson(Object object, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(object));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
