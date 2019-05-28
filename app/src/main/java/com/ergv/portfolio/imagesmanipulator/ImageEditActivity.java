package com.ergv.portfolio.imagesmanipulator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.PrintWriter;

public class ImageEditActivity extends AppCompatActivity {

    private File f;
    private Context ctx;
    public static char[] hex = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_edit);
        Intent nti = getIntent();

        f= new File(nti.getStringExtra("img"));
        ctx = this;
        ImageView img =  findViewById(R.id.image_view_content);
        Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());

        img.setImageBitmap(bmp);

        Button rotateBtn = findViewById(R.id.image_view_btn_rotate);
        rotateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reverse = openFile(f.getAbsolutePath());
                Toast.makeText(ctx, reverse, Toast.LENGTH_LONG).show();
            }
        });

        Button scaleBtn = findViewById(R.id.image_view_btn_rscale);
        scaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                char[] bytes = getBytes(f.getAbsolutePath(), 4);

                StringBuilder sb = new StringBuilder();
                for(int i=0; i<bytes.length; i++){
                    do{
                        sb.append(hex[(bytes[i]&0xF000)>>12]);
                        bytes[i]<<=4;
                    }while(0!=bytes[i]);

                    sb.append("\n");
                }
                Toast.makeText(ctx, sb.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    native public String openFile(String filePath);
    native public char[] getBytes(String path, int nBytes);
    static {
        System.loadLibrary("openImage");
    }
}
