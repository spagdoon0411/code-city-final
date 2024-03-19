package org.example;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class BuildingInfo {
    private String locX, locY, locZ;
    private String dimX, dimY, dimZ;
    private String fields, methods;
    private String name;
    private List<String> incomingReferences;
    private List<String> outgoingReferences;

    public BuildingInfo(String locX, String locY, String locZ, String dimX, String dimY, String dimZ, String fields, String methods, String name, List<String> incomingReferences, List<String> outgoingReferences) {
        this.locX = locX;
        this.locY = locY;
        this.locZ = locZ;
        this.dimX = dimX;
        this.dimY = dimY;
        this.dimZ = dimZ;
        this.fields = fields;
        this.methods = methods;
        this.name = name;
        this.incomingReferences = incomingReferences;
        this.outgoingReferences = outgoingReferences;
    }

    // Getters
    public String getLocX() { return locX; }
    public String getLocY() { return locY; }
    public String getLocZ() { return locZ; }
    public String getDimX() { return dimX; }
    public String getDimY() { return dimY; }
    public String getDimZ() { return dimZ; }
    public String getFields() { return fields; }
    public String getMethods() { return methods; }
    public String getName() { return name; }
    public List<String> getIncomingReferences() { return incomingReferences; }
    public List<String> getOutgoingReferences() { return outgoingReferences; }

    // Setters
    public void setLocX(String locX) { this.locX = locX; }
    public void setLocY(String locY) { this.locY = locY; }
    public void setLocZ(String locZ) { this.locZ = locZ; }
    public void setDimX(String dimX) { this.dimX = dimX; }
    public void setDimY(String dimY) { this.dimY = dimY; }
    public void setDimZ(String dimZ) { this.dimZ = dimZ; }
    public void setFields(String fields) { this.fields = fields; }
    public void setMethods(String methods) { this.methods = methods; }
    public void setName(String name) { this.name = name; }
    public void setIncomingReferences(List<String> incomingReferences) { this.incomingReferences = incomingReferences; }
    public void setOutgoingReferences(List<String> outgoingReferences) { this.outgoingReferences = outgoingReferences; }

}

public class JsonBuilder {

    public void createBuildingInfoJson(List<BuildingInfo> buildings) {
        Gson gson = new Gson();
        String json = gson.toJson(buildings);

        try (FileWriter writer = new FileWriter("buildings.json")) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
        // Test the JSON creation process -- Make createBuildingInfoJson method static during testing
//        List<BuildingInfo> buildings = new ArrayList<>();
//        buildings.add(new BuildingInfo("3.4", "4.5", "6.5", "3.4", "3.2", "12.7", "3", "12", "BuildingA", Arrays.asList("BuildingB"), Arrays.asList("BuildingC")));
//        createBuildingInfoJson(buildings);

    }
}