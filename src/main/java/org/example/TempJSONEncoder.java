package org.example;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TempJSONEncoder {

    public static void encodeBuildingsInDir(List<TempBuildingInfo> buildingInfos, File tempDir)
    {
        String buildingSerialization = serializeBuildings(buildingInfos);
        loadJson(buildingSerialization, tempDir);
    }

    public static String serializeBuildings(List<TempBuildingInfo> buildingInfos)
    {
        return "";
    }

    private static void loadJson(String buildingSerialization, File tempDir)
    {
        File buildingDataJson = new File("src/main/java/org/example/testData.json");
        /* On directory-creation success, copy the JSON data to the temporary directory */
        File tempBuildingDataJson = new File(tempDir.getPath() + File.separator + "BuildingData.json");
        // File tempBuildingDataJson = new File("src/main/java/org/example/BuildingData.json");
        try {
            FileUtils.copyFile(buildingDataJson, tempBuildingDataJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
