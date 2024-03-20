package org.example;

import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class UnityViewport extends Viewport {

    private static final Platform platform = Platform.WINDOWS;

    private static int idAssignCounter = 0;

    private File viewportExe;

    private final int id;

    public int getId()
    {
        return this.id;
    }

    private final List<BuildingInfo> buildingInfos;

    private final String templateExePath;

    private IEncoder encoder;

    private String sourceStub;

    public UnityViewport(List<BuildingInfo> buildingInfos, IEncoder encoder)
    {
        super(buildingInfos, encoder);
        this.id = idAssignCounter++;
        this.buildingInfos = buildingInfos;
        this.sourceStub = "src"
                + File.separator + "main"
                + File.separator + "java"
                + File.separator + "org"
                + File.separator + "example";
        this.templateExePath = this.sourceStub + File.separator + getPlatformExeName();

        this.viewportExe = new File(templateExePath);
        this.encoder = encoder;
    }

    private String getPlatformExeName()
    {
        String name = "";
        switch (platform) {
            case MAC:
                name = "ViewportMac.app";
                break;
            case WINDOWS:
                name = "ViewportWindows.exe";
                break;
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

    private Process  launchViewportExeInTempDir(File viewportExe, File viewportDirectory)
    {
        /* Launch the viewport instance in the temporary directory. The viewport exe is responsible for
         * reading the building JSON data in its current working directory, so set the CWD to the
         * temporary folder, launch the viewport executable, then return to the old working directory. */

        try {
            if(platform == Platform.MAC) {
                String previousCWD = System.getProperty("user.dir");
                System.setProperty("user.dir", viewportDirectory.getAbsolutePath());
                Desktop.getDesktop().open(viewportExe);
                System.setProperty("user.dir", previousCWD);
            }
            else if(platform == Platform.WINDOWS) {
                return Runtime.getRuntime().exec(viewportExe.getAbsolutePath(), null, viewportDirectory);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private File copyViewportToTempDirectory(File viewportTemplateExe, File viewportDirectory)
    {
        File viewportTempExe = new File(viewportDirectory.getPath() + File.separator + getPlatformExeName());

        try {
            if(platform == Platform.MAC)
                FileUtils.copyDirectory(viewportTemplateExe, viewportTempExe);
            else if(platform == Platform.WINDOWS)
            {
                /* Windows has more files to copy over. Prepare the source and destinations for those. */
                File crashHandlerFile = new File(sourceStub
                        + File.separator
                        + "UnityCrashHandler64.exe");

                File crashHandlerTempFile = new File(viewportDirectory.getAbsolutePath()
                        + File.separator
                        + "UnityCrashHandler64.exe");

                File dllFile = new File(sourceStub
                        + File.separator
                        + "UnityPlayer.dll");

                File dllTempFile = new File(viewportDirectory.getAbsolutePath()
                        + File.separator
                        + "UnityPlayer.dll");

                File dataFolder = new File(sourceStub
                        + File.separator
                        + "ViewportWindows_Data");

                File dataTempFolder = new File(viewportDirectory.getAbsolutePath()
                        + File.separator
                        + "ViewportWindows_Data");

                File monoFolder = new File(sourceStub
                        + File.separator
                        + "MonoBleedingEdge");

                File monoTempFolder = new File(viewportDirectory.getAbsolutePath()
                        + File.separator
                        + "MonoBleedingEdge");

                /* Copy the three necessary files to the new temporary directory. */
                FileUtils.copyFile(viewportTemplateExe, viewportTempExe);
                FileUtils.copyFile(crashHandlerFile, crashHandlerTempFile);
                FileUtils.copyFile(dllFile, dllTempFile);
                FileUtils.copyDirectory(dataFolder, dataTempFolder);
                FileUtils.copyDirectory(monoFolder, monoTempFolder);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return viewportTempExe;
    }

    public void open() {

        File tempDir = FileUtils.getTempDirectory();

        File viewportDirectory = new File(tempDir.getPath() + File.separator + "Viewport" + id);

        makeTempViewportDirectory(viewportDirectory);

        File viewportTempExe = copyViewportToTempDirectory(viewportExe, viewportDirectory);

        encoder.encodeBuildingsInDir(buildingInfos, viewportDirectory);

        launchViewportExeInTempDir(viewportTempExe, viewportDirectory);
    }
}
