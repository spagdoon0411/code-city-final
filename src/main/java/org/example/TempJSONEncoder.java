package org.example;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringJoiner;

public class TempJSONEncoder implements IEncoder {

    public void encodeBuildingsInDir(List<TempBuildingInfo> buildingInfos, File tempDir)
    {
        String buildingSerialization = serializeBuildings(buildingInfos);
        loadJson(buildingSerialization, tempDir);
    }

    private static String serializeBuildings(List<TempBuildingInfo> buildingInfos)
    {
        ObjectWriter ow = new ObjectMapper()
                .enable(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS)
                .writer().withDefaultPrettyPrinter();
        StringJoiner serializedBuildingInfos = new StringJoiner(",\n");

        buildingInfos.forEach(b -> {
            try {
                serializedBuildingInfos.add(ow.writeValueAsString(b));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        return "[" + serializedBuildingInfos + "]";
    }

    private void createJsonFileFromString(File tempJsonFile, String buildingSerialization) {
         if(tempJsonFile.exists())
         {
            tempJsonFile.delete();
         }
         else
         {
             try {
                 tempJsonFile.createNewFile();
                 PrintWriter writer = new PrintWriter(tempJsonFile.getAbsolutePath());
                 writer.println(buildingSerialization);
                 writer.close();
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
         }
    }

    private void loadJson(String buildingSerialization, File tempDir)
    {
        /* On directory-creation success, copy the JSON data to the temporary directory */
        File tempJsonFile = new File(tempDir.getAbsolutePath() + File.separator + "BuildingData.json");

        createJsonFileFromString(tempJsonFile, buildingSerialization);
    }


}
