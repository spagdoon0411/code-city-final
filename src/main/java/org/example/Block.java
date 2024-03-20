package org.example;

import java.util.ArrayList;

public class Block {
    protected int posX;
    protected int posY;
    protected int width;
    protected int level;
    protected String pack;
    protected Block parent;
    private ArrayList<Block> children;
    
    public Block(String pack, int level, Block parent, ArrayList<Block> children){
        this.pack = pack;
        this.level = level;
        this.parent = parent;
        this.children = children;
        width = 0;
    }
    
    public void setDimensions(int x, int y, int width){
        posX = x;
        posY = y;
        this.width = width;
    }
    public int getPosX(){
        return posX;
    }
    public int getPosY(){
        return posY;
    }
    public int getWidth(){
        return width;
    }
    public int getLevel(){
        return level;
    }
    public Block getParent(){
        return parent;
    }
    public ArrayList<Block> getChildren(){
        return children;
    }

    public ArrayList<Block> getNonBuildings(){
        ArrayList<Block> nonBuilds = new ArrayList<Block>();
        for(Block b : children){
            if(!(b instanceof Building)){
                nonBuilds.add(b);
            }
        }
        return nonBuilds;
    }


    /**
     * this can be called on the root Block and it will
     * recursively generate widths for all subBlocks
     * @return the width of the block
     */
    public int generateWidth(){
        int spacing = maxWidthOfChildren();
        width = (1+spacing)*(int)Math.ceil(Math.sqrt(children.size()))+1;
        return width;
    }


    public int maxWidthOfChildren(){
        int wMax = 1;
        for(int i = 0; i < children.size(); i++){
            if(children.get(i) instanceof Building){
                if(children.get(i).getWidth() > wMax){
                    wMax = children.get(i).getWidth();
                }
            }
            else{
                if(children.get(i).getWidth() == 0){
                    children.get(i).generateWidth();
                }
                if(children.get(i).getWidth() > wMax){
                    wMax = children.get(i).getWidth();
                }
            }
        }
        return wMax;
    }

    /**
     * must be called after generateWidth
     * recursively assigns locations to Blocks
     */
    public void placeSubBlocks(){
        int sideLength = (int)Math.ceil(Math.sqrt(children.size()));
        int spacing = (int)(width/sideLength);
        for(int i = 0; i < children.size(); i++){
            children.get(i).posX = this.posX+(i%sideLength)*spacing+1;
            children.get(i).posY = posY+(int)(i/sideLength)*spacing+1;
            if(!(children.get(i) instanceof Building)){
                children.get(i).placeSubBlocks();
            }
        }
    }

    public String getPackage(){
        return pack;
    }

    @Override
    public String toString(){
        String str = "BLOCK ("+posX+", "+posY+") {\n";
        for(Block child : children){
            str += child.toString();
        }
        return str+"}\n";
    }
}
