package edu.apsu.csci.mandaladrawingapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener {

    public static final int SELECT_PICTURE = 1000;
    public static final int SAVE_PICTURE = 1500;

    private int count = 1;

    private Button loadButton;
    private Button clearButton;
    private Button saveButton;
    private Button lineColorButton;
    private Button lineThicknessButton;
    private Button rectButton;
    private Button rectColorButton;
    private Button circleButton;
    private Button circleColorButton;
    private DrawableView drawableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawableView = (DrawableView) findViewById(R.id.drawable_view);
        drawableView.setDrawingEnabled(true);
        loadButton = (Button) findViewById(R.id.load_button);
        clearButton = (Button) findViewById(R.id.clear_button);
        saveButton = (Button) findViewById(R.id.save_button);
        lineColorButton = (Button) findViewById(R.id.linecolor_button);
        lineThicknessButton = (Button) findViewById(R.id.linethickness_button);
        rectButton = (Button) findViewById(R.id.rectangle_button);
        rectColorButton = (Button) findViewById(R.id.rectanglecolor_button);
        circleButton = (Button) findViewById(R.id.circle_button);
        circleColorButton = (Button) findViewById(R.id.circlecolor_button);
        loadButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        lineColorButton.setOnClickListener(this);
        lineThicknessButton.setOnClickListener(this);
        rectButton.setOnClickListener(this);
        rectColorButton.setOnClickListener(this);
        circleButton.setOnClickListener(this);
        circleColorButton.setOnClickListener(this);
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
            drawableView.clearCanvas();
            rootLayout.removeView(drawableView);
            rootLayout.addView(drawableView);
        } else if (id == R.id.linecolor_button) {
            drawableView.blueCanvas();
        } else if (id == R.id.linethickness_button) {
            drawableView.thickLineCanvas();
            //showRadioButtonDialog();
        } else if (id == R.id.rectangle_button) {
            drawableView.drawRect();
        } else if (id == R.id.rectanglecolor_button) {
            drawableView.drawColorRect();
        } else if (id == R.id.circle_button) {
            drawableView.drawCircle();
        } else if (id == R.id.circlecolor_button) {
            drawableView.drawColorCircle();
        } else if (id == R.id.save_button) {
            saveImage();
        }
    }

    public void saveImage() {
    Random gen = new Random();
     int n = 10000;
     n = gen.nextInt(n);
     String photo_name = "photo-"+ n +".jpg";
     drawableView.setDrawingCacheEnabled(true);
     Bitmap finalBitmap = Bitmap.createBitmap(drawableView.getDrawingCache());
     File newDir = getExternalFilesDir("DCIM");
     File myPath = new File(newDir, photo_name);
     FileOutputStream fos=null;
    try {
            fos=new FileOutputStream(myPath);
            // Use the compress method on the BitMap object to write image to the OutputStream
                    finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Toast.makeText(getApplicationContext(), "saved " + photo_name + " to application folder", Toast.LENGTH_SHORT ).show();
        } catch (Exception e) {
            Log.e("SAVE", "photo failed to save");
        } finally {
            try {
                    fos.close();
                } catch (IOException e) {
                    Log.e("SAVE", "FOS failed to close");
                }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageURI = data.getData();
                drawableView.setImageURI(selectedImageURI);
            } else if (requestCode == SAVE_PICTURE) {

            }
        }
    }
}