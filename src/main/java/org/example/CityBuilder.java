package org.example;

import java.util.ArrayList;
import java.util.StringTokenizer;

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

    /**
     * this method contains the algorithm for generating a tree structure composed
     * of blocks and buildings. Blocks can be nested inside other blocks corresponding to
     * the nesting of packages. this function is recursive.
     * @param parent the root of the tree that will be generated.
     */
    public void generateBlockTree(Block parent){
        // loop through the file info repo, selecting for matching package beginnings
        //generate blocks for each new package at the level we are at.
        //add in Buildings if the package matches but doesn't reach that level
        for(int i = 0; i < FileInfoRepo.getFileInfos().size(); i++){
            // if package matches that of parent, add building.
            // if package matches that of parent but has subpackage, check if encountered
            // if not encountered, make a new Block. call generateBlockTree on that block.
            FileInfo fi = FileInfoRepo.getFileInfos().get(i);
            //System.out.println(fi.getPackage()+"\n"+parent.getLevel());
            if(parent.getPackage().isEmpty() && fi.getPackage().isEmpty() || parent.getPackage().equals(fi.getPackage())){
                parent.getChildren().add(new Building(fi, parent));
            }else if(fi.getPackage().startsWith(parent.getPackage()) && !encountered(parent.getChildren(), fi, parent.getLevel())){
                String nextPack = "";
                if(fi.getPackage().contains(".")){
                    StringTokenizer st = new StringTokenizer(fi.getPackage(), ".");
                    System.out.println(st.countTokens());
                    int n = 0;
                    while(n <= parent.getLevel() && st.hasMoreTokens()){
                        nextPack = st.nextToken();
                        n++;
                    }
                }else{
                    nextPack = fi.getPackage();
                }
                Block bl = new Block(parent.getPackage()+"."+nextPack, parent.getLevel()+1, parent, new ArrayList<Block>());
                if(parent.getLevel() == 0){
                    bl = new Block(nextPack, 1, parent, new ArrayList<Block>());
                }
                parent.getChildren().add(bl);
                generateBlockTree(bl);
            }
        }
    }

    public boolean encountered(ArrayList<Block> blocks, FileInfo fi, int level){
        for(int i = 0; i < blocks.size(); i++){
            if(!(blocks.get(i) instanceof Building)){
                StringTokenizer st1 = new StringTokenizer(blocks.get(i).getPackage(), ".");
                StringTokenizer st2 = new StringTokenizer(fi.getPackage(), ".");
                String blockPack = "";
                String filePack = "";
                int n = 0;
                while(n <= level && st1.hasMoreTokens() && st2.hasMoreTokens()){
                    blockPack = st1.nextToken();
                    filePack = st2.nextToken();
                    n++;
                }
                if(blockPack.equals(filePack)){
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
