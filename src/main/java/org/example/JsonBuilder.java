package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Responsible for encoding a list of BuildingInfo objects into a JSON format
 * and saving it to a specified directory.
 */
public class JsonBuilder implements IEncoder {

    /**
     * Encodes a list of BuildingInfo objects into a JSON file in the specified directory.
     *
     * @param buildingInfos List of BuildingInfo objects to encode.
     * @param tempDir The directory where the JSON file will be saved.
     */
    @Override
    public void encodeBuildingsInDir(List<BuildingInfo> buildingInfos, File tempDir) {
        String buildingSerialization = serializeBuildings(buildingInfos);
        loadJson(buildingSerialization, tempDir);
    }

    /**
     * Converts a list of BuildingInfo objects into a JSON string.
     * Each numeric value is converted to a string, ensuring whole numbers do not contain '.0'.
     *
     * @param buildingInfos List of BuildingInfo objects to serialize.
     * @return A string representing the JSON serialization of the buildingInfos.
     */
    private String serializeBuildings(List<BuildingInfo> buildingInfos) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Map<String, String>> serializedBuildings = new ArrayList<>();

        for (BuildingInfo buildingInfo : buildingInfos) {
            Map<String, String> buildingMap = new LinkedHashMap<>();
            buildingMap.put("name", buildingInfo.getName());
            buildingMap.put("methods", Integer.toString(buildingInfo.getMethods()));
            buildingMap.put("fields", Integer.toString(buildingInfo.getFields()));
            buildingMap.put("locX", formatNumberAsString(buildingInfo.getLocX()));
            buildingMap.put("locY", formatNumberAsString(buildingInfo.getLocY()));
            buildingMap.put("locZ", formatNumberAsString(buildingInfo.getLocZ()));
            buildingMap.put("dimX", formatNumberAsString(buildingInfo.getDimX()));
            buildingMap.put("dimY", formatNumberAsString(buildingInfo.getDimY()));
            buildingMap.put("dimZ", formatNumberAsString(buildingInfo.getDimZ()));
            serializedBuildings.add(buildingMap);
        }

        return gson.toJson(serializedBuildings);
    }

    /**
     * Formats a numeric value as a string. If the number is a whole number,
     * it is formatted without a decimal point. Otherwise, it is formatted
     * with a single decimal place.
     *
     * @param number The number to format.
     * @return A string representation of the number.
     */
    private String formatNumberAsString(float number) {
        if (number == (int) number) {
            return Integer.toString((int) number);
        } else {
            return String.format("%.1f", number);
        }
    }

    /**
     * Creates a JSON file from a given string and saves it to a specified directory.
     * If the file already exists, it is overwritten.
     *
     * @param buildingSerialization The JSON string to save.
     * @param tempJsonFile The directory where the JSON file will be saved.
     */
    private void createJsonFileFromString(String buildingSerialization, File tempJsonFile) {
        try {
            if (!tempJsonFile.getParentFile().exists()) {
                tempJsonFile.getParentFile().mkdirs();
            }
            try (PrintWriter writer = new PrintWriter(tempJsonFile)) {
                writer.println(buildingSerialization);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to file: " + tempJsonFile.getAbsolutePath(), e);
        }
    }

    /**
     * Helper method to manage the creation of the JSON file within the specified directory.
     *
     * @param buildingSerialization The JSON string to be saved.
     * @param tempDir The directory where the JSON file will be saved.
     */
    private void loadJson(String buildingSerialization, File tempDir) {
        File tempJsonFile = new File(tempDir.getAbsolutePath() + File.separator + "BuildingData.json");
        createJsonFileFromString(buildingSerialization, tempJsonFile);

    }
}
