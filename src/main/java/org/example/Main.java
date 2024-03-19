package org.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {


        List<TempBuildingInfo> testBuildingInfos = new ArrayList<>();
        Viewport v = new Viewport(testBuildingInfos);
        v.launch();

    }
}