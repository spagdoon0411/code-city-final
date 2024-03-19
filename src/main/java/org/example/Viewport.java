package org.example;

import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Viewport {

    private static final Platform platform = Platform.MAC;

    private static int idAssignCounter = 0;

    private final int id;

    public int getId()
    {
        return this.id;
    }

    private final List<TempBuildingInfo> buildingInfos;

    private final String templateExePath;

    public Viewport(List<TempBuildingInfo> buildingInfos)
    {
        this.id = idAssignCounter++;
        this.buildingInfos = buildingInfos;
        templateExePath = "src"
                + File.separator + "main"
                + File.separator + "java"
                + File.separator + "org"
                + File.separator + "example"
                + File.separator  + getPlatformExeName();
    }

    private String getPlatformExeName()
    {
        String name = "";
        switch (platform) {
            case MAC: name = "ViewportMac.app";
            case WINDOWS: name = "ViewportWindows.app";
        }
        return name;
    }

    private void clearPriorViewportDirectory(File viewportDirectory)
    {
        /* Ensures no viewport directory already exists */
        if(viewportDirectory.exists())
        {
            try {
                if(viewportDirectory.isDirectory())
                {
                    FileUtils.cleanDirectory(viewportDirectory);
                    FileUtils.deleteDirectory(viewportDirectory);
                }
                else if(viewportDirectory.isFile())
                {
                    FileUtils.delete(viewportDirectory);
                }
            } catch(IOException e)
            {
                System.err.println("Could not clear viewport directory.\n" + e.getMessage());
            }
        }
    }

    private void makeTempViewportDirectory(File viewportDirectory)
    {
        clearPriorViewportDirectory(viewportDirectory);
        if(!viewportDirectory.mkdirs())
        {
            System.err.println("Temporary directory for viewport could not be created.");
        }
    }

    private void launchViewportExeInTempDir(File viewportExe, File viewportDirectory)
    {
        /* Launch the viewport instance in the temporary directory. The viewport exe is responsible for
         * reading the building JSON data in its current working directory, so set the CWD to the
         * temporary folder, launch the viewport executable, then return to the old working directory. */
        String previousCWD = System.getProperty("user.dir");
        System.setProperty("user.dir", viewportDirectory.getAbsolutePath());
        try {
            Desktop.getDesktop().open(viewportExe);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.setProperty("user.dir", previousCWD);

    }

    private File copyViewportToTempDirectory(File viewportTemplateExe, File viewportDirectory)
    {
        File viewportTempExe = new File(viewportDirectory.getPath() + File.separator + "Viewport.app");
        try {
            FileUtils.copyDirectory(viewportTemplateExe, viewportTempExe);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return viewportTempExe;
    }

    public void open() {

        File tempDir = FileUtils.getTempDirectory();
        File viewportExe = new File(templateExePath);
        File viewportDirectory = new File(tempDir.getPath() + File.separator + "Viewport" + id);

        makeTempViewportDirectory(viewportDirectory);

        File viewportTempExe = copyViewportToTempDirectory(viewportExe, viewportDirectory);

        TempJSONEncoder.encodeBuildingsInDir(buildingInfos, viewportDirectory);

        launchViewportExeInTempDir(viewportTempExe, viewportDirectory);
    }
}
