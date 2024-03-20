package org.example;

public class Building extends Block{
    private int height;
    private FileInfo details;
    public Building(FileInfo fi, Block parent){
        super(fi.getPackage(), parent.getLevel()+1, parent, null);
        details = fi;
        width = 1+2*fi.getNumFields();
        height = 1+2*fi.getNumMethods();
    }
    public int getHeight(){
        return height;
    }
    public void setHeight(int h){
        height = h;
    }
    public FileInfo getDetails(){
        return details;
    }

    @Override
    public String toString(){
        return "Name: "+details.getName()+" ("+posX+", "+posY+") Width: "+width+"\n";
    }
}
