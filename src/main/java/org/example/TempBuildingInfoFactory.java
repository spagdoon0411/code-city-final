package org.example;

import java.util.ArrayList;

public class TempBuildingInfoFactory {

    ArrayList<TempBuildingInfo> testBuildings = new ArrayList<>();

    private int currentTestIndex = 0;

    public TempBuildingInfoFactory()
    {
        TempBuildingInfo b1 = new TempBuildingInfo();
        b1.name = "Temp1";
        b1.methods = 3;
        b1.fields = 4;
        b1.locX = 0;
        b1.locY = 0;
        b1.locZ = 0;
        b1.dimX = 200.0f;
        b1.dimY = 2.5f;
        b1.dimZ = 4.0f;

        TempBuildingInfo b2 = new TempBuildingInfo();
        b2.name = "Temp2";
        b2.methods = 12;
        b2.fields = 1;
        b2.locX = 0;
        b2.locY = 10;
        b2.locZ = 0;
        b2.dimX = 3.5f;
        b2.dimY = 4.0f;
        b2.dimZ = 7.0f;

        TempBuildingInfo b3 = new TempBuildingInfo();
        b3.name = "Temp3";
        b3.methods = 2000;
        b3.fields = 43;
        b3.locX = -2f;
        b3.locY = -2.5f;
        b3.locZ = 0;
        b3.dimX = 2;
        b3.dimY = 2;
        b3.dimZ = 3;

        testBuildings.add(b1);
        testBuildings.add(b2);
        testBuildings.add(b3);
    }

    public TempBuildingInfo getCurrentTestBuilding() {
        return testBuildings.get(currentTestIndex++);
    }

}
