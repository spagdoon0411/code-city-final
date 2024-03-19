package org.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestViewportAndJson {
    @Test
    public void SimpleTest1()
    {
        TempBuildingInfoFactory fact = new TempBuildingInfoFactory();
        TempBuildingInfo b1 = fact.getCurrentTestBuilding();
        TempBuildingInfo b2 = fact.getCurrentTestBuilding();
        TempBuildingInfo b3 = fact.getCurrentTestBuilding();

        List<TempBuildingInfo> testBuildingInfos = new ArrayList<>();
        testBuildingInfos.add(b1);
        testBuildingInfos.add(b2);
        testBuildingInfos.add(b3);

        /* This defines how to encode buildings into JSON. */
        TempJSONEncoder encoder = new TempJSONEncoder();

        /* A Viewport takes BuildingInfos and a class that encodes them. */
        Viewport v = new Viewport(testBuildingInfos, encoder);

        v.open();
    }
}
