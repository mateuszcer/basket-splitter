package com.mateuszcer.basket.utils.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

public class FileParserTest {

    private FileParser fileParser;
    @BeforeEach
    public void setup() {
        fileParser = new FileParser();
    }

    @Test
    public void testLoadFromJSONFileList() {
        // Given
        String configFile = "src/test/resources/parsing/test-basket.json";

        // When
        List<String> data = fileParser.loadFromJSONFile(configFile);

        // Then
        assertNotNull(data);
        assertEquals(data.size(), 4);
        assertTrue(data.contains("Tart - Raisin And Pecan"));
    }

    @Test
    public void testLoadFromJSONFileMap() {
        // Given
        String configFile = "src/test/resources/parsing/test-config.json";

        // When
        Map<String, List<String>> data = fileParser.loadFromJSONFile(configFile);

        // Then
        assertNotNull(data);
        assertEquals(data.size(), 3);
        assertTrue(data.containsKey("English Muffin"));
    }
}
