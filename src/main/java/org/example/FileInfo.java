package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class FileInfo {
    private String name;
    private String path;
    private String pack;
    private HashMap<String, Integer> dependencies;
    private int numMethods;
    private int numFields;
    private int numLocalVars;
    private Integer numReferences;

    public FileInfo(String path, String name, String pack, ArrayList<String> deps, int nM, int nF, int nV){
        this.path = path;
        this.name = name;
        this.pack = pack;
        dependencies = new HashMap<String, Integer>();
        for(String dep : deps){
            Integer num = dependencies.get(dep);
            if(num == null){
                dependencies.put(dep, Integer.valueOf(1));
            }else{
                dependencies.put(dep, Integer.valueOf(num.intValue()+1));
            }
        }
        numMethods = nM;
        numFields = nF;
        numLocalVars = nV;
    }

    @Override
    public String toString(){
        return "Class name: "+name+"\n"+
                "Package name: "+pack+"\n"+
                "# Methods: "+numMethods+"\n"+
                "# Attributes: "+numFields+"\n"+
                "# Local Vars: "+numLocalVars+"\n"+
                "Dependencies: \n"+dependencies.toString()+"\n";
    }

    public String getPath(){
        return path;
    }

    public String getName(){
        return name;
    }
    
    public String getPackage(){
        return pack;
    }

    /**
     * this is used to assign locations to buildings representing files.
     * @return a hashmap of the other files referenced in this one.
     */
    public HashMap<String, Integer> getDependencies(){
        return dependencies;
    }

    /**
     * determines building height
     * @return number of methods in the file
     */
    public int getNumMethods(){
        return numMethods;
    }

    /**
     * determines building width
     * @return number of attributes in the file
     */
    public int getNumFields(){
        return numFields;
    }

    public int getNumLocalVars(){
        return numLocalVars;
    }

    public int getNumReferences(){
        if(numReferences != null){
            return numReferences.intValue();
        }
        int refCount = 0;
        for(int i = 0; i < FileInfoRepo.getFileInfos().size(); i++){
            Integer deps = FileInfoRepo.getFileInfos().get(i).getDependencies().get(name);
            if(deps != null){
                refCount += deps.intValue();
            }
        }
        return refCount;
    }
}
