package com.ergv.portfolio.imagesmanipulator;

public class FileListItem {

    public enum ITEM_TYPE {FILE, FOLDER, ROOT, UNKNOWN};
    public enum ITEM_STATE {COLLAPSED, OPENED, UNOPENED};
    private String name, path;
    private short depth;
    private ITEM_STATE state;
    private ITEM_TYPE type;
    private boolean visible;


    FileListItem(String name, String  path, short depth, ITEM_TYPE type){
        this.name=name;
        this.depth=depth;
        this.type =type;
        this.state= ITEM_STATE.UNOPENED;
        this.path = path;
        this.visible = true;
    }



    public void updateState(){
        if(ITEM_STATE.COLLAPSED==state || ITEM_STATE.UNOPENED==state)
            state=ITEM_STATE.OPENED;
        else
            state = ITEM_STATE.COLLAPSED;
    }

    public void toggleVisibility(){
        visible = !visible;
    }

    public void display(){  visible=true;  }
    public void undisplay(){ visible = false; }
    public boolean isVisible(){return visible;}

    public ITEM_TYPE getType(){return  type;}
    public ITEM_STATE getState(){return  state;}
    public String getName(){return  name;}
    public String getPath(){return  path;}
    public short getDepth(){return  depth;}




}
