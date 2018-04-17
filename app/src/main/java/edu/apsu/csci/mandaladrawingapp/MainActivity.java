package edu.apsu.csci.mandaladrawingapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

            /*drawableView.buildDrawingCache();
            Bitmap iBitmap = drawableView.getDrawingCache();

            File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File file = new File(root.getAbsolutePath()+"/DCIM/img.jpg");
            try
            {
                file.createNewFile();
                FileOutputStream ostream = new FileOutputStream(file);
                iBitmap.compress(Bitmap.CompressFormat.JPEG, 90, ostream);
                ostream.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            } */


            /*StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            Intent cameraIntent = new Intent(MediaStore.EXTRA_MEDIA_ALBUM);
            File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String pictureName = getPictureName();
            File imageFile = new File(pictureDirectory, pictureName);
            Uri pictureUri = Uri.fromFile(imageFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
            startActivityForResult(cameraIntent, CAMERA_REQUEST); */

            //Toast.makeText(MainActivity.this, "Image Saved Successfully", Toast.LENGTH_LONG).show();
        }
    }

    public void saveImage() {
        //touchImageView.setDrawingCacheEnabled(true);
        //Bitmap bitmapImage1 = Bitmap.createBitmap(touchImageView.getDrawingCache());
        //Bitmap finalBitmap = Bitmap.createBitmap(bitmapImage2.getWidth(), bitmapImage2.getHeight(), bitmapImage2.getConfig());
        //Canvas canvas = new Canvas(finalBitmap);
        //canvas.drawBitmap(bitmapImage1, new Matrix(), null);
        //canvas.drawBitmap(bitmapImage2, new Matrix(), null);
        // finalBitmap will contain your background and its overlay
         Random gen = new Random();
         int n = 10000;
         n = gen.nextInt(n);
         String photo_name = "photo-"+ n +".jpg";
         drawableView.setDrawingCacheEnabled(true);
         Bitmap finalBitmap = Bitmap.createBitmap(drawableView.getDrawingCache());
         File newDir = getExternalFilesDir("imageDir");
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
        //touchImageView.setDrawingCacheEnabled(false);
        // Random gen = new Random();
        //int n = 10000;
        //n = gen.nextInt(n);
        //String photo_name = "photo-"+ n +".jpg";
        //File file = new File (newDir, photo_name);
        //if (file.exists ())
        //file.delete ();
        /*
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(getApplicationContext(), "saved to your folder", Toast.LENGTH_SHORT ).show();

        } catch (Exception e) {
            Log.e("SAVE","photo failed to save");
        }*/


    }


    private void showRadioButtonDialog() {

        /*final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_main);
        List<String> stringList=new ArrayList<>();  // here is list
        for(int i=0;i<5;i++) {
            stringList.add("RadioButton " + (i + 1));
        }
        RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);

        for(int i=0;i<stringList.size();i++){
            RadioButton rb=new RadioButton(mActivity); // dynamically creating RadioButton and adding to RadioGroup.
            rb.setText(stringList.get(i));
            rg.addView(rb);
        }

        dialog.show(); */

    }
/*
    public void saveImage() {
        /*Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String pictureName = getPictureName();
        File imageFile = new File(pictureDirectory, pictureName);
        Uri pictureUri = Uri.fromFile(imageFile);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST); */

        //Uri selectedImageURIs = data.getData();
        /*drawableView.buildDrawingCache();
        Bitmap iBitmap = drawableView.getDrawingCache(); */

        /*public Uri getImageUri(Context inContext, Bitmap inImage) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
            return Uri.parse(path);
        }

        Intent intent = new Intent();
        intent.setType("image/*");
        //intent.setAction(Intent.ACTION_CREATE_DOCUMENT);
        intent.setAction(Intent.ACTION_SEND);
        startActivityForResult(Intent.createChooser(intent, "Save Picture"), SAVE_PICTURE);
    }
*/
    private String getPictureName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return "Plane place image" + timestamp + ".jpg";
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