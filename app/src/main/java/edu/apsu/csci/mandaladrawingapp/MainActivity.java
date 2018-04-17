package edu.apsu.csci.mandaladrawingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener {

    public static final int SELECT_PICTURE = 1000;
    public static final int SAVE_PICTURE = 1500;

    private int count = 1;

    private Button loadButton;
    private Button clearButton;
    private Button saveButton;
    private CustomImageView touchImageView;
    private DrawableView drawableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        touchImageView = (CustomImageView) findViewById(R.id.zoom_iv);
        drawableView = (DrawableView) findViewById(R.id.drawable_view);
        drawableView.setDrawingEnabled(true);
        loadButton = (Button) findViewById(R.id.load_button);
        clearButton = (Button) findViewById(R.id.clear_button);
        saveButton = (Button) findViewById(R.id.save_button);
        loadButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.activity_main);
        int id = v.getId();
        if (id == R.id.load_button) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
        } else if (id == R.id.clear_button) {
            count++;
            drawableView.clearCanvas();
            rootLayout.removeView(drawableView);
            rootLayout.addView(drawableView);
        } else if (id == R.id.save_button) {

            saveImage();
            /*Drawable drawable;
            Bitmap bitmap;
            String ImagePath;
            Uri URI;

            drawable = getResources().getDrawable(R.drawable.ic_launcher_background);

            bitmap = ((BitmapDrawable)drawable).getBitmap();

            ImagePath = MediaStore.Images.Media.insertImage(
                    getContentResolver(),
                    bitmap,
                    "demo_image",
                    "demo_image"
            );

            URI = Uri.parse(ImagePath);

            Toast.makeText(MainActivity.this, "Image Saved Successfully", Toast.LENGTH_LONG).show(); */
        }
    }

    public void saveImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        //intent.setAction(Intent.ACTION_SEND);
        intent.setAction(Intent.ACTION_CREATE_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SAVE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageURI = data.getData();
                touchImageView.setImageURI(selectedImageURI);
            } else if (requestCode == SAVE_PICTURE) {
                String root = Environment.getRootDirectory().toString();
                File myDir = new File(root + "/downloads");
                myDir.mkdirs();
                Random generator = new Random();
                int n = 10000;
                n = generator.nextInt(n);
                String fname = "Image-"+ n +".jpg";
                File file = new File (myDir, fname);
                if (file.exists ()) file.delete ();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    out.flush();
                    out.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}