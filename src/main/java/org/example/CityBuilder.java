package org.example;

import java.util.ArrayList;

public class CityBuilder {
    private Block root;
    public CityBuilder(){
        root = new Block("", 0, null, new ArrayList<Block>());
    }

    public void generateCity(){
        generateBlockTree(root);
        root.setDimensions(0, 0, 0);
        root.generateWidth();
        root.placeSubBlocks();
    }

    public void generateBlockTree(Block parent){
        // loop through the file info repo, selecting for matching package beginnings
        //generate blocks for each new package at the level we are at.
        //add in Buildings if the package matches but doesn't reach that level
        for(int i = 0; i < FileInfoRepo.getFileInfos().size(); i++){
            // if package matches that of parent, add building.
            // if package matches that of parent but has subpackage, check if encountered
            // if not encountered, make a new Block. call generateBlockTree on that block.
            FileInfo fi = FileInfoRepo.getFileInfos().get(i);
            System.out.println(fi.getPackage());
            if(parent.getPackage().isEmpty() && fi.getPackage().isEmpty() || parent.getPackage().equals(fi.getPackage())){
                parent.getChildren().add(new Building(fi, parent));
            }else if(fi.getPackage().startsWith(parent.getPackage()) && !encountered(parent.getChildren(), fi, parent.getLevel())){
                String nextPack = "";
                if(fi.getPackage().contains(".")){
                    nextPack = fi.getPackage().split(".")[parent.getLevel()];
                }else{
                    nextPack = fi.getPackage();
                }
                Block bl = new Block(parent.getPackage()+"."+nextPack, parent.getLevel()+1, parent, new ArrayList<Block>());
                if(parent.getLevel() == 0){
                    bl = new Block(nextPack, 1, parent, new ArrayList<Block>());
                }
                generateBlockTree(bl);
            }
        }
    }

    public boolean encountered(ArrayList<Block> blocks, FileInfo fi, int level){
        for(int i = 0; i < blocks.size(); i++){
            if(!(blocks.get(i) instanceof Building)){
                String nextPack = blocks.get(i).getPackage().split(".")[level];
                if(nextPack.equals(fi.getPackage().split(".")[level])){
                    return true;
                }
            }
        }
        return false;
    }

    public Block getRoot(){
        return root;
    }
}
