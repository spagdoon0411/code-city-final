package org.example;

import java.io.File;
import java.util.List;

public interface IEncoder {
    public void encodeBuildingsInDir(List<TempBuildingInfo> buildingInfos, File tempDir);
}
