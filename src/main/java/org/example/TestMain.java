package org.example;

public class TestMain {
    public static void main(String[] args) {
        ProjectParser pp = new ProjectParser("./dummyClasses");
        pp.generateInfos();
        for(FileInfo f : FileInfoRepo.getFileInfos()){
            System.out.println(f.toString());
        }
    }
}