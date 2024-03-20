package org.example;


import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.*;

/*
* Integration: Eric Berber
*/
public class Main {
    public static void main(String[] args) {

        String imagePath = "./src/main/java/org/example/code_city.png";

        // String recommendedTest = "https://github.com/spagdoon0411/code-city-test-remote";
        String remoteUrl = JOptionPane.showInputDialog("GitHub repository:");
        String downloadPath = JOptionPane.showInputDialog("Absolute download path:");

        if(remoteUrl == null || downloadPath == null) {
            System.exit(0);
        }

        SplashScreen splashScreen = new SplashScreen(imagePath);
        FileDownloader fileDownloader = new FileDownloader(remoteUrl, downloadPath);
        ProjectParser projectParser = new ProjectParser(downloadPath);
        FileViewer fileViewer = new FileViewer(downloadPath);
        CityBuilder cityBuilder = new CityBuilder();
        IEncoder encoder = new JsonBuilder();
        FileInfoRepo  fileInfoRepo = FileInfoRepo.getInstance();

        fileDownloader.downloadFiles();
        projectParser.generateInfos();
        cityBuilder.generateCity();

        ArrayList<BuildingInfo>  buildingInfos =  cityBuilder.getBuildingInfos();
        UnityViewport v = new UnityViewport(buildingInfos, encoder);

        // Wait for the frame to be disposed
        while (fileViewer.isVisible()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Run Unity process
        v.open();

        splashScreen.run(true, 60);
        splashScreen.remove();
    }
}