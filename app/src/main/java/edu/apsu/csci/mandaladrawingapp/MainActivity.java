package edu.apsu.csci.mandaladrawingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements View.OnClickListener {

    public static final int SELECT_PICTURE = 1000;

    private int count = 1;

    private Button enableZoomBtn;
    private Button loadButton;
    private Button clearButton;
    private CustomImageView touchImageView;
    private DrawableView drawableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        touchImageView = (CustomImageView) findViewById(R.id.zoom_iv);
        drawableView = (DrawableView) findViewById(R.id.drawable_view);
        drawableView.setBackgroundColor(Color.TRANSPARENT);
        enableZoomBtn = (Button) findViewById(R.id.enable_zoom);
        loadButton = (Button) findViewById(R.id.load_button);
        clearButton = (Button) findViewById(R.id.clear_button);
        enableZoomBtn.setOnClickListener(this);
        loadButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.activity_main);
        int id = v.getId();
        if (id == R.id.enable_zoom) {
            if (count > 1) {
                drawableView.clearCanvas();
                // drawableView.otherCanvas();
            }
            rootLayout.removeView(drawableView);
            rootLayout.addView(drawableView);
            drawableView.setDrawingEnabled(true);
            touchImageView.setZoomEnable(false);
        } else if (id == R.id.load_button) {
            pickImage();
            drawableView.setDrawingEnabled(false);
        } else if (id == R.id.clear_button) {
            count++;
            rootLayout.removeView(touchImageView);
            rootLayout.addView(touchImageView);
        }
    }

    public void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageURI = data.getData();
                touchImageView.setImageURI(selectedImageURI);
            }
        }
    }
}