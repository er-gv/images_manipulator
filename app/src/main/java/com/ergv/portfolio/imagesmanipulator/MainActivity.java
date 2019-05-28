package com.ergv.portfolio.imagesmanipulator;

import com.ergv.portfolio.imagesmanipulator.FileListItem;
import android.Manifest;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
    implements ActivityCompat.OnRequestPermissionsResultCallback {

    private File images_root;
    private FilesAdapter filesAdapter;
    private static int IO_PERMISSIONS_GRANTED = 0x100;
    private AdapterView.OnItemClickListener messageClickedHandler;
    ListView listView;

    Resources res;
    private Drawable collapseImg, expandImg, openImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
                // Do something in response to the click
                FileListItem item= (FileListItem) parent.getItemAtPosition(position);
                //indViewById(R.id.files_view_file_name);
                Toast.makeText(parent.getContext(), item.getPath(), Toast.LENGTH_LONG)
                        .show();

                if(FileListItem.ITEM_TYPE.FOLDER==item.getType()){
                       if( FileListItem.ITEM_STATE.UNOPENED==item.getState()){
                           filesAdapter.addSubfolder(new File(item.getPath()), item.getDepth(), position);
                       }
                    item.updateState();
                    filesAdapter.notifyDataSetChanged();
                }
                else if (FileListItem.ITEM_TYPE.FILE == item.getType())
                    openEditImageActivity(item.getPath());


            }
        };
        initUI();

    }

    private void openEditImageActivity(String path){
        Intent itn = new Intent(this, ImageEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("img", path);
        itn.putExtras(bundle);
        startActivity(itn);

    }


    private void requestFilesPermissions(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                IO_PERMISSIONS_GRANTED);

    }

    private File setupPicturesRoot() throws FileNotFoundException{
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            throw new FileNotFoundException();
        }

        return new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_PICTURES);

    }

    private void initUI(){

        try{
            images_root= setupPicturesRoot();
        }
        catch (FileNotFoundException fnfe)
        {
            Toast.makeText(this, R.string.sdcard_failed_err, Toast.LENGTH_LONG)
                    .show();
            finish();
        }


        Button btn = findViewById(R.id.act_main_btn_preview);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestFilesPermissions();
            }
        });

    }


    public void onRequestPermissionsResult (int requestCode,
                                String[] permissions,
                                int[] grantResults){
        int[] expected={0, 0};
        if(grantResults.length==2 && Arrays.equals(grantResults, expected)) {
            setupListView();

        }
        else
                Toast.makeText(this, R.string.sdcard_failed_err, Toast.LENGTH_LONG)
                .show();
        return;



    }

    private void setupListView() {
        filesAdapter = new FilesAdapter(this, images_root, R.layout.tree_item_layout, R.id.files_view_file_name);
        //filesAdapter.setRootFolder(images_root);
        listView = this.findViewById(R.id.filesViewContainer);
        listView.setAdapter(filesAdapter);


        listView.setOnItemClickListener(messageClickedHandler);

    }









}
