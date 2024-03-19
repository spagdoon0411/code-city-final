package org.example;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileInfoFactory {
    private String dirPath;

    public FileInfoFactory(String dp){
        dirPath = dp;
    }
    /**
     * generates a fileInfo object that has the specified name at
     * this factory's path field. Can be called on a single file
     * or can be recursively called on a directory
     * @param name
     */
    public void generateFileInfo(String name){
        String path = (name == "") ? dirPath : dirPath+"/"+name;
        File file = new File(path);
        Path fPath = Path.of(path);
        if(file.exists() && Files.isRegularFile(fPath) && isJavaFile(file)){
            addToRepo(path);
        }else if(file.exists() && Files.isDirectory(fPath)){
            File[] subFiles = file.listFiles();
            for(File f : subFiles){
                FileInfoFactory fif = new FileInfoFactory(path);
                fif.generateFileInfo(f.getName());
            }
        }
    }

    public void addToRepo(String path){
        FileVisitor fv = new FileVisitor(path);
        FileInfo fi = new FileInfo(
            path, 
            fv.getName(), 
            fv.getPackage(), 
            fv.getDependencyList(), 
            fv.getNumMethods(), 
            fv.getNumFields(), 
            fv.getNumLocalVars());
        FileInfoRepo.addFileInfo(fi);
    }

    /**
     * Identifies if the file has the .java extension
     * @return boolean value that is true iff file has a .java extension
     */
    public boolean isJavaFile(File file){
        String fName = file.getName();
        return fName.endsWith(".java");
    }
}
