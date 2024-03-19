package org.example;

import java.util.ArrayList;

public class FileInfoRepo {
    private static FileInfoRepo instance;
    private ArrayList<FileInfo> fileInfos;

    private FileInfoRepo(){
        fileInfos = new ArrayList<FileInfo>();
    }

    public static FileInfoRepo getInstance(){
        if(instance == null){
            instance = new FileInfoRepo();
        }
        return instance;
    }

    public static ArrayList<FileInfo> getFileInfos(){
        return getInstance().fileInfos;
    }

    public static void addFileInfo(FileInfo fIn){
        getInstance().fileInfos.add(fIn);
    }
}
