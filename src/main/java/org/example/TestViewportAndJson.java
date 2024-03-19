package org.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestViewportAndJson {
    @Test
    public void SimpleTest1()
    {
        /* We'd use a factory method to obtain and build a list of buildings somewhere. We
        * probably wouldn't call this exact method, however. */
        TempBuildingInfoFactory fact = new TempBuildingInfoFactory();
        TempBuildingInfo b1 = fact.getCurrentTestBuilding();
        TempBuildingInfo b2 = fact.getCurrentTestBuilding();
        TempBuildingInfo b3 = fact.getCurrentTestBuilding();

        List<TempBuildingInfo> testBuildingInfos = new ArrayList<>();
        testBuildingInfos.add(b1);
        testBuildingInfos.add(b2);
        testBuildingInfos.add(b3);

        /* This defines how to encode buildings into JSON and follows the dependency
        * injection pattern. */
        TempJSONEncoder encoder = new TempJSONEncoder();

        /* A Viewport takes BuildingInfos and a class that encodes them. The viewport
        * follows the facade pattern by abstracting away the (quite complex!) details of
        * the 3D renderer implementation. */
        UnityViewport v = new UnityViewport(testBuildingInfos, encoder);

        v.open();
    }
}
