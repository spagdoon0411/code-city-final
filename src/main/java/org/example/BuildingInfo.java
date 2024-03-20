package org.example;

import java.util.List;

public class BuildingInfo {
    private float locX, locY, locZ; // Location coordinates
    private float dimX, dimY, dimZ; // Dimensions
    private int fields, methods; // Additional attributes
    private String name; // Name of the building

    /**
     * Constructs a new BuildingInfo object.
     *
     * @param locX X-coordinate of the building's location.
     * @param locY Y-coordinate of the building's location.
     * @param locZ Z-coordinate of the building's location.
     * @param dimX Width of the building.
     * @param dimY Height of the building.
     * @param dimZ Depth of the building.
     * @param fields Number of fields.
     * @param methods Number of methods.
     * @param name Name of the building.
     */
    public BuildingInfo(float locX, float locY, float locZ, float dimX, float dimY, float dimZ, int fields, int methods, String name) {
        this.locX = locX;
        this.locY = locY;
        this.locZ = locZ;
        this.dimX = dimX;
        this.dimY = dimY;
        this.dimZ = dimZ;
        this.fields = fields;
        this.methods = methods;
        this.name = name;
    }

    // Getters and Setters
    public float getLocX() { return locX; }
    public float getLocY() { return locY; }
    public float getLocZ() { return locZ; }
    public float getDimX() { return dimX; }
    public float getDimY() { return dimY; }
    public float getDimZ() { return dimZ; }
    public int getFields() { return fields; }
    public int getMethods() { return methods; }
    public String getName() { return name; }

    public void setLocX(float locX) { this.locX = locX; }
    public void setLocY(float locY) { this.locY = locY; }
    public void setLocZ(float locZ) { this.locZ = locZ; }
    public void setDimX(float dimX) { this.dimX = dimX; }
    public void setDimY(float dimY) { this.dimY = dimY; }
    public void setDimZ(float dimZ) { this.dimZ = dimZ; }
    public void setFields(int fields) { this.fields = fields; }
    public void setMethods(int methods) { this.methods = methods; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString(){
        return "("+locX+", "+locY+", "+locZ+") Name: "+name;
    }
}
