package com.mateuszcer.basket.utils.io;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mateuszcer.basket.exception.FileParserException;

public class FileParser {

    /**
     * Loads data from a JSON file.
     *
     * @param <T>        The type of the object to be returned.
     * @param filePath The path to the JSON file.
     * @return The object loaded from the JSON file
     * @throws FileParserException If an error occurs during file parsing.
     */
    public static <T> T loadFromJSONFile(String filePath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            Type type = new TypeToken<T>() {}.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            throw new FileParserException("Error reading JSON file: " + e.getMessage(), e);
        } catch (JsonSyntaxException e) {
            throw new FileParserException("Error parsing JSON: " + e.getMessage(), e);
        }
    }
}
