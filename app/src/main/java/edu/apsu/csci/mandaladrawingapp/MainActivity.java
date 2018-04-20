package edu.apsu.csci.mandaladrawingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener {

    public static final int SELECT_PICTURE = 1000;
    public static final int SAVE_PICTURE = 1500;

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
    private EditText titleView;
    private Button titleButton;

    private int functionID = 1;

    private int shapeColor = Color.TRANSPARENT;
    private int shapeWidth = 10;
    private int shapeHeight = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawableView = (DrawableView) findViewById(R.id.drawable_view);
        drawableView.setDrawingEnabled(true);
        titleView = (EditText) findViewById(R.id.title_view);
        titleButton = (Button) findViewById(R.id.title_button);
        loadButton = (Button) findViewById(R.id.load_button);
        clearButton = (Button) findViewById(R.id.clear_button);
        saveButton = (Button) findViewById(R.id.save_button);
        lineColorButton = (Button) findViewById(R.id.linecolor_button);
        lineThicknessButton = (Button) findViewById(R.id.linethickness_button);
        rectButton = (Button) findViewById(R.id.rectangle_button);
        rectColorButton = (Button) findViewById(R.id.rectanglecolor_button);
        circleButton = (Button) findViewById(R.id.circle_button);
        circleColorButton = (Button) findViewById(R.id.circlecolor_button);
        titleButton.setOnClickListener(this);
        loadButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        lineColorButton.setOnClickListener(this);
        lineThicknessButton.setOnClickListener(this);
        rectButton.setOnClickListener(this);
        rectColorButton.setOnClickListener(this);
        circleButton.setOnClickListener(this);
        circleColorButton.setOnClickListener(this);
        if (savedInstanceState != null) {
            Parcelable state=savedInstanceState.getParcelable("STATE_COLLECTION");
            if (state != null) {
                drawableView.onRestoreInstanceState(state);
            }
        }
    }
    @Override
    public void onClick(View v) {
        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.activity_main);
        int id = v.getId();
        if (id == R.id.title_button) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            LinearLayout layout = new LinearLayout(getApplicationContext());
            layout.setOrientation(LinearLayout.VERTICAL);

            final EditText editText = new EditText(getApplicationContext());
            layout.addView(editText);

            LinearLayout midLayout1 = new LinearLayout(getApplicationContext());
            midLayout1.setOrientation(LinearLayout.HORIZONTAL);

            final TextView titleTopView = new TextView(getApplicationContext());
            titleTopView.setText("Text Color:");
            midLayout1.addView(titleTopView);

            final TextView textFill1 = new TextView(getApplicationContext());
            textFill1.setWidth(130);
            midLayout1.addView(textFill1);

            final TextView titleTopView2 = new TextView(getApplicationContext());
            titleTopView2.setText("Background Color:");
            midLayout1.addView(titleTopView2);

            layout.addView(midLayout1);

            LinearLayout midLayout2 = new LinearLayout(getApplicationContext());
            midLayout2.setOrientation(LinearLayout.HORIZONTAL);

            final RadioButton radioButton = new RadioButton(getApplicationContext());
            radioButton.setText("RED");
            midLayout2.addView(radioButton);

            final TextView textFill2 = new TextView(getApplicationContext());
            textFill2.setWidth(200);
            midLayout2.addView(textFill2);

            final RadioButton radioButton2 = new RadioButton(getApplicationContext());
            radioButton2.setText("RED");
            midLayout2.addView(radioButton2);

            layout.addView(midLayout2);

            LinearLayout midLayout3 = new LinearLayout(getApplicationContext());
            midLayout3.setOrientation(LinearLayout.HORIZONTAL);

            final RadioButton radioButton3 = new RadioButton(getApplicationContext());
            radioButton3.setText("GREEN");
            midLayout3.addView(radioButton3);

            final TextView textFill3 = new TextView(getApplicationContext());
            textFill3.setWidth(173);
            midLayout3.addView(textFill3);

            final RadioButton radioButton4 = new RadioButton(getApplicationContext());
            radioButton4.setText("GREEN");
            midLayout3.addView(radioButton4);

            layout.addView(midLayout3);

            LinearLayout midLayout4 = new LinearLayout(getApplicationContext());
            midLayout4.setOrientation(LinearLayout.HORIZONTAL);

            final RadioButton radioButton5 = new RadioButton(getApplicationContext());
            radioButton5.setText("BLUE");
            midLayout4.addView(radioButton5);

            final TextView textFill4 = new TextView(getApplicationContext());
            textFill4.setWidth(188);
            midLayout4.addView(textFill4);

            final RadioButton radioButton6 = new RadioButton(getApplicationContext());
            radioButton6.setText("BLUE");
            midLayout4.addView(radioButton6);

            layout.addView(midLayout4);

            LinearLayout midLayout5 = new LinearLayout(getApplicationContext());
            midLayout5.setOrientation(LinearLayout.HORIZONTAL);

            final RadioButton radioButton7 = new RadioButton(getApplicationContext());
            radioButton7.setText("YELLOW");
            midLayout5.addView(radioButton7);

            final TextView textFill5 = new TextView(getApplicationContext());
            textFill5.setWidth(158);
            midLayout5.addView(textFill5);

            final RadioButton radioButton8 = new RadioButton(getApplicationContext());
            radioButton8.setText("YELLOW");
            midLayout5.addView(radioButton8);

            layout.addView(midLayout5);

            LinearLayout midLayout6 = new LinearLayout(getApplicationContext());
            midLayout6.setOrientation(LinearLayout.HORIZONTAL);

            final RadioButton radioButton9 = new RadioButton(getApplicationContext());
            radioButton9.setText("CYAN");
            midLayout6.addView(radioButton9);

            final TextView textFill6 = new TextView(getApplicationContext());
            textFill6.setWidth(180);
            midLayout6.addView(textFill6);

            final RadioButton radioButton10 = new RadioButton(getApplicationContext());
            radioButton10.setText("CYAN");
            midLayout6.addView(radioButton10);

            layout.addView(midLayout6);

            LinearLayout midLayout7 = new LinearLayout(getApplicationContext());
            midLayout7.setOrientation(LinearLayout.HORIZONTAL);

            final RadioButton radioButton11 = new RadioButton(getApplicationContext());
            radioButton11.setText("GRAY");
            midLayout7.addView(radioButton11);

            final TextView textFill7 = new TextView(getApplicationContext());
            textFill7.setWidth(180);
            midLayout7.addView(textFill7);

            final RadioButton radioButton12 = new RadioButton(getApplicationContext());
            radioButton12.setText("GRAY");
            midLayout7.addView(radioButton12);

            layout.addView(midLayout7);

            LinearLayout midLayout8 = new LinearLayout(getApplicationContext());
            midLayout8.setOrientation(LinearLayout.HORIZONTAL);

            final RadioButton radioButton13 = new RadioButton(getApplicationContext());
            radioButton13.setText("WHITE");
            midLayout8.addView(radioButton13);

            final TextView textFill8 = new TextView(getApplicationContext());
            textFill8.setWidth(168);
            midLayout8.addView(textFill8);

            final RadioButton radioButton14 = new RadioButton(getApplicationContext());
            radioButton14.setText("WHITE");
            midLayout8.addView(radioButton14);

            layout.addView(midLayout8);

            LinearLayout midLayout9 = new LinearLayout(getApplicationContext());
            midLayout9.setOrientation(LinearLayout.HORIZONTAL);

            final RadioButton radioButton15 = new RadioButton(getApplicationContext());
            radioButton15.setText("BLACK");
            midLayout9.addView(radioButton15);

            final TextView textFill9 = new TextView(getApplicationContext());
            textFill9.setWidth(168);
            midLayout9.addView(textFill9);

            final RadioButton radioButton16 = new RadioButton(getApplicationContext());
            radioButton16.setText("BLACK");
            midLayout9.addView(radioButton16);

            layout.addView(midLayout9);

            alert.setMessage("Enter the font size (in sp) and colors for the title:");
            alert.setTitle("Edit Title");

            alert.setView(layout);

            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    int colorInt = Color.BLACK;
                    if (radioButton.isChecked()) {
                        colorInt = Color.RED;
                    } else if (radioButton3.isChecked()) {
                        colorInt = Color.GREEN;
                    } else if (radioButton5.isChecked()) {
                        colorInt = Color.BLUE;
                    } else if (radioButton7.isChecked()) {
                        colorInt = Color.YELLOW;
                    } else if (radioButton9.isChecked()) {
                        colorInt = Color.CYAN;
                    } else if (radioButton11.isChecked()) {
                        colorInt = Color.GRAY;
                    } else if (radioButton13.isChecked()) {
                        colorInt = Color.WHITE;
                    } else if (radioButton15.isChecked()) {
                        colorInt = Color.BLACK;
                    }

                    int colorBackgroundInt = Color.WHITE;
                    if (radioButton2.isChecked()) {
                        colorBackgroundInt = Color.RED;
                    } else if (radioButton4.isChecked()) {
                        colorBackgroundInt = Color.GREEN;
                    } else if (radioButton6.isChecked()) {
                        colorBackgroundInt = Color.BLUE;
                    } else if (radioButton8.isChecked()) {
                        colorBackgroundInt = Color.YELLOW;
                    } else if (radioButton10.isChecked()) {
                        colorBackgroundInt = Color.CYAN;
                    } else if (radioButton12.isChecked()) {
                        colorBackgroundInt = Color.GRAY;
                    } else if (radioButton14.isChecked()) {
                        colorBackgroundInt = Color.WHITE;
                    } else if (radioButton16.isChecked()) {
                        colorBackgroundInt = Color.BLACK;
                    }
                    titleView.setTextColor(colorInt);
                    titleView.setBackgroundColor(colorBackgroundInt);

                    String titleSize = editText.getText().toString();
                    if (titleSize.equals("")) {
                        titleSize = "30";
                    }
                    int titleSizeInt = Integer.parseInt(titleSize);
                    if (titleSizeInt > 30) {
                        titleSizeInt = 30;
                    }
                    titleView.setTextSize(titleSizeInt);
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });

            alert.show();
        } else if (id == R.id.load_button) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
        } else if (id == R.id.clear_button) {
            drawableView.clearCanvas();
            rootLayout.removeView(drawableView);
            rootLayout.addView(drawableView);
        } else if (id == R.id.linecolor_button) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            LinearLayout layout = new LinearLayout(getApplicationContext());
            layout.setOrientation(LinearLayout.VERTICAL);

            final RadioButton radioButton = new RadioButton(getApplicationContext());
            radioButton.setText("RED");
            layout.addView(radioButton);

            final RadioButton radioButton2 = new RadioButton(getApplicationContext());
            radioButton2.setText("GREEN");
            layout.addView(radioButton2);

            final RadioButton radioButton3 = new RadioButton(getApplicationContext());
            radioButton3.setText("BLUE");
            layout.addView(radioButton3);

            final RadioButton radioButton4 = new RadioButton(getApplicationContext());
            radioButton4.setText("YELLOW");
            layout.addView(radioButton4);

            final RadioButton radioButton5 = new RadioButton(getApplicationContext());
            radioButton5.setText("CYAN");
            layout.addView(radioButton5);

            final RadioButton radioButton6 = new RadioButton(getApplicationContext());
            radioButton6.setText("GRAY");
            layout.addView(radioButton6);

            final RadioButton radioButton7 = new RadioButton(getApplicationContext());
            radioButton7.setText("WHITE");
            layout.addView(radioButton7);

            final RadioButton radioButton8 = new RadioButton(getApplicationContext());
            radioButton8.setText("BLACK");
            layout.addView(radioButton8);
            alert.setMessage("Select a color for your future rectangles:");
            alert.setTitle("Rectangle Color");

            alert.setView(layout);

            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    int colorInt = Color.BLACK;
                    if (radioButton.isChecked()) {
                        colorInt = Color.RED;
                    } else if (radioButton2.isChecked()) {
                        colorInt = Color.GREEN;
                    } else if (radioButton3.isChecked()) {
                        colorInt = Color.BLUE;
                    } else if (radioButton4.isChecked()) {
                        colorInt = Color.YELLOW;
                    } else if (radioButton5.isChecked()) {
                        colorInt = Color.CYAN;
                    } else if (radioButton6.isChecked()) {
                        colorInt = Color.GRAY;
                    } else if (radioButton7.isChecked()) {
                        colorInt = Color.WHITE;
                    } else if (radioButton8.isChecked()) {
                        colorInt = Color.BLACK;
                    }
                    drawableView.drawLineColor(colorInt);
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });

            alert.show();
        } else if (id == R.id.linethickness_button) {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            final EditText edittext = new EditText(getApplicationContext());
            edittext.setText("100");
            alert.setMessage("Enter the thickness for your lines(%):");
            alert.setTitle("Line Thickness");

            alert.setView(edittext);

            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String lineThickness = edittext.getText().toString();
                    if (lineThickness.equals("")) {
                        lineThickness = "100";
                    }
                    int lineThicknessNumber = Integer.parseInt(lineThickness);
                    lineThicknessNumber /= 100;
                    drawableView.thickLineCanvas(lineThicknessNumber);
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });

            alert.show();
        } else if (id == R.id.rectangle_button) {
            functionID = 1;
            shapeSetup();
        } else if (id == R.id.rectanglecolor_button) {
            functionID = 2;
            shapeColorSetup();
        } else if (id == R.id.circle_button) {
            functionID = 3;
            shapeSetup();
        } else if (id == R.id.circlecolor_button) {
            functionID = 4;
            shapeColorSetup();
        } else if (id == R.id.save_button) {
            saveImage();
        }
    }

    private void shapeSetup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        final TextView textView = new TextView(getApplicationContext());
        textView.setText("Width");
        layout.addView(textView);

        final EditText editText = new EditText(getApplicationContext());
        layout.addView(editText);

        final TextView textView2 = new TextView(getApplicationContext());
        textView2.setText("Height");
        layout.addView(textView2);

        final EditText editText2 = new EditText(getApplicationContext());
        layout.addView(editText2);

        if (functionID == 1) {
            alert.setMessage("Set the width and height for your future rectangles:");
            alert.setTitle("Rectangle Size");
        } else if (functionID == 3) {
            alert.setMessage("Set the width and height for your future circles:");
            alert.setTitle("Circle Size");
        }

        alert.setView(layout);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String shapeWidthString = editText.getText().toString();
                shapeWidth = Integer.parseInt(shapeWidthString);
                String shapeHeightString = editText2.getText().toString();
                shapeHeight = Integer.parseInt(shapeHeightString);
                if (functionID == 1) {
                    drawableView.drawRect(shapeWidth, shapeHeight);
                } else if (functionID == 3) {
                    drawableView.drawCircle(shapeWidth, shapeHeight);
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();
    }

    private void shapeColorSetup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        final RadioButton radioButton = new RadioButton(getApplicationContext());
        radioButton.setText("BLACK");
        layout.addView(radioButton);

        final RadioButton radioButton2 = new RadioButton(getApplicationContext());
        radioButton2.setText("RED");
        layout.addView(radioButton2);

        final RadioButton radioButton3 = new RadioButton(getApplicationContext());
        radioButton3.setText("GREEN");
        layout.addView(radioButton3);

        final RadioButton radioButton4 = new RadioButton(getApplicationContext());
        radioButton4.setText("BLUE");
        layout.addView(radioButton4);

        final RadioButton radioButton5 = new RadioButton(getApplicationContext());
        radioButton5.setText("GRAY");
        layout.addView(radioButton5);

        final RadioButton radioButton6 = new RadioButton(getApplicationContext());
        radioButton6.setText("YELLOW");
        layout.addView(radioButton6);

        final RadioButton radioButton7 = new RadioButton(getApplicationContext());
        radioButton7.setText("CYAN");
        layout.addView(radioButton7);

        final RadioButton radioButton8 = new RadioButton(getApplicationContext());
        radioButton8.setText("WHITE");
        layout.addView(radioButton8);

        final RadioButton radioButton9 = new RadioButton(getApplicationContext());
        radioButton9.setText("NONE");
        layout.addView(radioButton9);

        if (functionID == 2) {
            alert.setMessage("Select a color for your future rectangles:");
            alert.setTitle("Rectangle Color");
        } else if (functionID == 4) {
            alert.setMessage("Select a color for your future circles:");
            alert.setTitle("Circle Color");
        }

        alert.setView(layout);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (radioButton.isChecked()) {
                    shapeColor = Color.BLACK;
                } else if (radioButton2.isChecked()) {
                    shapeColor = Color.RED;
                } else if (radioButton3.isChecked()) {
                    shapeColor = Color.GREEN;
                } else if (radioButton4.isChecked()) {
                    shapeColor = Color.BLUE;
                } else if (radioButton5.isChecked()) {
                    shapeColor = Color.GRAY;
                } else if (radioButton6.isChecked()) {
                    shapeColor = Color.YELLOW;
                } else if (radioButton7.isChecked()) {
                    shapeColor = Color.CYAN;
                } else if (radioButton8.isChecked()) {
                    shapeColor = Color.WHITE;
                } else if (radioButton9.isChecked()) {
                    shapeColor = Color.TRANSPARENT;
                }
                if (functionID == 2) {
                    drawableView.drawColorRect(shapeColor);
                } else if (functionID == 4) {
                    drawableView.drawColorCircle(shapeColor);
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();
    }

    public void saveImage() {
        Random gen = new Random();
        int n = 10000;
        n = gen.nextInt(n);
        String photo_name = "photo-"+ n +".jpg";
        drawableView.setDrawingCacheEnabled(true);
        Bitmap finalBitmap = Bitmap.createBitmap(drawableView.getDrawingCache());
        File newDir = getExternalFilesDir("images");
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
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("STATE_COLLECTION", drawableView.onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

}