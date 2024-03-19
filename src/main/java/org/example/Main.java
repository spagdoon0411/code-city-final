package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<TempBuildingInfo> testBuildingInfos = new ArrayList<>();
        Viewport v = new Viewport(testBuildingInfos);

        v.open();

    }
}