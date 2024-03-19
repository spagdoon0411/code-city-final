package org.example;

import java.io.File;

public class ProjectParser {
    private String projPath;

    public ProjectParser(String projPath){
        this.projPath = projPath;
    }

    public void generateInfos(){
        File file = new File(projPath);
        if(file.exists() && file.isDirectory()){
            FileInfoFactory fif = new FileInfoFactory(projPath);
            fif.generateFileInfo("");
        }
    }
}
