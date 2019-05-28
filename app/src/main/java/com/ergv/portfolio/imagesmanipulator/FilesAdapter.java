package com.ergv.portfolio.imagesmanipulator;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;




class FilesAdapter extends ArrayAdapter<FileListItem>{

    private File root;
    private StringBuilder stringBuilder;
    public static final int FILE_LIST_ROW_TAG = 9;
    public static final int BTN_STATE_TAG = 10;
    public enum BTN_STATE {COLLAPSE, OPEN, EXPAND};
    private static Resources res;
    private static Drawable collapseImg, expandImg, openImg;
   // private static Map<FileListItem.ITEM_STATE, Drawable> stateIcons;

    public FilesAdapter(Context ctx, File root, int layoutID, int textviewID){
        super(ctx, layoutID, textviewID);
        //this.root = root;
        this.stringBuilder  = new StringBuilder();
        setRootFolder(root);
        res = getContext().getResources();
        collapseImg = ResourcesCompat.getDrawable(res, R.drawable.collapse_btn16, null);
        expandImg = ResourcesCompat.getDrawable(res, R.drawable.expand_btn16, null);
        openImg = ResourcesCompat.getDrawable(res, R.drawable.open_btn16, null);
        //stateIcons.put(FileListItem.ITEM_STATE.COLLAPSED, collapseImg);

    }

    /***
     * Create or populate a view object with content from the dataColection.
     * @param position: index in the data collection.
     * @param view: the textView
     * @param group parent viewGroup
     * @return a view representing content from dataCollection[position]
     */
    public View getView(int position, android.view.View view, android.view.ViewGroup group){

        if (view == null) {
            view= LayoutInflater.from(getContext()).inflate(R.layout.tree_item_layout, group, false);
            Log.e("FileViewManager::getView", "Content view is NULL");

        }

        FileListItem item= this.getItem(position);
        TextView tv = view.findViewById(R.id.files_view_file_name);
        ImageView img =  view.findViewById(R.id.files_view_img_collapse_state);

        stringBuilder.delete(0, stringBuilder.length());
        stringBuilder.append("   ");
        switch(item.getType()) {
            case ROOT:
                renderRootItem(item, tv, img);
                break;
            case FILE:
                renderFileItem(item, tv, img);
                img.setImageDrawable(openImg);
                break;

            case FOLDER:
                renderFolderItem(item, tv, img, position);
                break;

        }
        tv.append("   pos:  "+position);
        return view;
    }

    private void renderRootItem(FileListItem item, TextView tv, ImageView img){
        tv.setText(stringBuilder.toString()+"|");
        img.setImageDrawable(expandImg);
    }

    private void renderFileItem(FileListItem item, TextView tv, ImageView img) {
        for(short k =0; k<item.getDepth(); k++)
            stringBuilder.append("   ");
        stringBuilder.append(item.getName());
        tv.setText(stringBuilder.toString());
        img.setImageDrawable(openImg);


    }

    private void  renderFolderItem(FileListItem item, TextView tv, ImageView img, int position ){
        for(short k =0; k<item.getDepth(); k++)
            stringBuilder.append("---");
        stringBuilder.append(item.getName());
        tv.setText(stringBuilder.toString());
        Drawable dr = (FileListItem.ITEM_STATE.OPENED == item.getState()?   collapseImg:expandImg);
        img.setImageDrawable(dr);
    }
    /***
     * ad the content of subfolser parent into the list of files to display
     * @param parent
     */
    public void addSubfolder(File parent, short depth, int pos){

        ArrayList<FileListItem> subFolders = new ArrayList<>();
        ArrayList<FileListItem> files = new ArrayList<>();
        int counter =0;
        depth++;
        for (File obj: parent.listFiles() ) {

            if(obj.isDirectory()){
                subFolders.add(new FileListItem(obj.getName(), obj.getAbsolutePath(),  depth, FileListItem.ITEM_TYPE.FOLDER));
                Log.i("ADDING DIRECTORY", parent.getAbsolutePath()+"/"+Integer.toString(++counter)+"/"+obj.getName()+"/");
            }
            else if(obj.isFile()){
                files.add(new FileListItem(obj.getName(), obj.getAbsolutePath(),  depth, FileListItem.ITEM_TYPE.FILE));

                Log.i("ADDING FILE", parent.getAbsolutePath()+"/"+Integer.toString(++counter)+"/"+obj.getName());
            }
            else{
                Log.w("ADDING_UNKNOWN", obj.toString());
            }

        }
        for(int k=0; k<subFolders.size();k++)
            this.insert(subFolders.get(k), pos+k);
        int offset = subFolders.size()+pos;
        for(int k=0; k<files.size();k++)
            this.insert(files.get(k), offset+k);
        int newParentPos = subFolders.size()+files.size()+pos;
        FileListItem parentItem = this.getItem(newParentPos);

        this.remove(parentItem);
        this.insert(parentItem, pos);
    }

    /***
     *
     * @param s File/Folder name to indent
     * @return Indented text
     */
    private String getIndent(String s){
        int counter =0;
        stringBuilder.delete(0, stringBuilder.length());
        stringBuilder.append("|");
        for (char ch: s.toCharArray())
            if(ch =='/')
                stringBuilder.append("-----");

        stringBuilder.append("  ");
        return stringBuilder.toString();
    }

    /***
     *  @return root of current files tree.
     * will return NULL if  the root is null
     */
    public File getRoot(){
        return root;
    }

    public boolean isRootNULL(){
        return null==root;
    }


    /***
     * deplete the adapter from any previous content and then set a new root folder
     * and append it's sub content.
     * @param root
     */
    void setRootFolder(File root) {
        this.clear();
        this.root = root;
        short depth = 0;
        this.add(new FileListItem("/", root.getAbsolutePath(), depth, FileListItem.ITEM_TYPE.ROOT));
        addSubfolder(root, depth, 0);

    }




}
