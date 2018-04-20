package edu.apsu.csci.mandaladrawingapp;

/*  First additional feature: Adding circles to a drawing. Users can set the width and height of their
    ovals before being allowed to draw the ovals on any spot of the ImageView that they touch. This
    feature uses a button called circleButton as well as a function called shapeSetup() that calls the
    drawCircle(int, int) function inside DrawableView to take advantage of OnTouchEvent(Canvas) and an arrayList
    called rectFS to draw an unlimited number of ovals that use the heights and widths specified through
    the alertDialog that makes users insert the height and width values for the ovals they are about to draw.

    Second additional feature: Adding filled, colored circles to a drawing. Users can pick a color
    from a radioGroup of color options to set the filling color for the future circles that they
    draw on the canvas. This feature uses a button called circleColorButton as well as a function
    called shapeColorSetup that calls a function called drawColorCircle(int) from DrawableView to
    take advantage of OnTouchEvent(Canvas) and an arrayList called rectFS to draw an unlimited number
    of ovals that are filled with the int-type color value supplied through the AlertDialog that allows
    users to pick a color from a radioGroup of choices for their canvas.

    Third additional feature: A clear button that resets everything on an image. The button removes
    all rectangles and ovals, and it resets the ImageView's canvas to completely remove all edits
    made to the given image. This feature uses a button called clearButton as well as a function
    called clearCanvas() that activates a function in DrawableView that resets onSizeChanged(), the
    rectangles arrayList, the rectFS arrayList, and the ImageBitmap.

    Fourth additional feature: A title for the images. Users can click on the EditText to write
    a title for their image. There is also a small edit button that allows users to set the size,
    font color and background color for the image's title. This feature uses an EditText called
    titleView and a button called titleButton, and these widgets are used to load an alertDialog
    that presents the options that can be applied to the editable text in the EditText.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener {

    public static final int SELECT_PICTURE = 1000;

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

    private Context context;

    private int functionID = 1;
    private int saveCounter = 0;
    private int screenChangeInt = 0;

    private int shapeColor = Color.TRANSPARENT;
    private int shapeWidth = 10;
    private int shapeHeight = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

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
    }
    @Override
    public void onClick(View v) {
        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.activity_main);
        int id = v.getId();
        if (id == R.id.title_button) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            LinearLayout layout = new LinearLayout(getApplicationContext());
            layout.setOrientation(LinearLayout.VERTICAL);

            RadioGroup radioGroup1 = new RadioGroup(getApplicationContext());
            RadioGroup radioGroup2 = new RadioGroup(getApplicationContext());

            final EditText editText = new EditText(getApplicationContext());
            layout.addView(editText);

            LinearLayout subLayout = new LinearLayout(getApplicationContext());
            subLayout.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout midLayout = new LinearLayout(getApplicationContext());
            midLayout.setOrientation(LinearLayout.HORIZONTAL);

            final TextView titleTopView = new TextView(getApplicationContext());
            titleTopView.setText("Text Color:");
            midLayout.addView(titleTopView);

            final TextView textFillTop = new TextView(getApplicationContext());
            textFillTop.setWidth(130);
            midLayout.addView(textFillTop);

            final TextView titleTopView2 = new TextView(getApplicationContext());
            titleTopView2.setText("Background Color:");
            midLayout.addView(titleTopView2);

            final RadioButton radioButton = new RadioButton(getApplicationContext());
            radioButton.setText("RED");
            radioGroup1.addView(radioButton);

            final RadioButton radioButton2 = new RadioButton(getApplicationContext());
            radioButton2.setText("RED");
            radioGroup2.addView(radioButton2);

            final RadioButton radioButton3 = new RadioButton(getApplicationContext());
            radioButton3.setText("GREEN");
            radioGroup1.addView(radioButton3);

            final RadioButton radioButton4 = new RadioButton(getApplicationContext());
            radioButton4.setText("GREEN");
            radioGroup2.addView(radioButton4);

            final RadioButton radioButton5 = new RadioButton(getApplicationContext());
            radioButton5.setText("BLUE");
            radioGroup1.addView(radioButton5);

            final RadioButton radioButton6 = new RadioButton(getApplicationContext());
            radioButton6.setText("BLUE");
            radioGroup2.addView(radioButton6);

            final RadioButton radioButton7 = new RadioButton(getApplicationContext());
            radioButton7.setText("YELLOW");
            radioGroup1.addView(radioButton7);

            final RadioButton radioButton8 = new RadioButton(getApplicationContext());
            radioButton8.setText("YELLOW");
            radioGroup2.addView(radioButton8);

            final RadioButton radioButton9 = new RadioButton(getApplicationContext());
            radioButton9.setText("CYAN");
            radioGroup1.addView(radioButton9);

            final RadioButton radioButton10 = new RadioButton(getApplicationContext());
            radioButton10.setText("CYAN");
            radioGroup2.addView(radioButton10);

            final RadioButton radioButton11 = new RadioButton(getApplicationContext());
            radioButton11.setText("GRAY");
            radioGroup1.addView(radioButton11);

            final RadioButton radioButton12 = new RadioButton(getApplicationContext());
            radioButton12.setText("GRAY");
            radioGroup2.addView(radioButton12);

            final RadioButton radioButton13 = new RadioButton(getApplicationContext());
            radioButton13.setText("WHITE");
            radioGroup1.addView(radioButton13);

            final RadioButton radioButton14 = new RadioButton(getApplicationContext());
            radioButton14.setText("WHITE");
            radioGroup2.addView(radioButton14);

            final RadioButton radioButton15 = new RadioButton(getApplicationContext());
            radioButton15.setText("BLACK");
            radioGroup1.addView(radioButton15);

            final TextView textFillMain = new TextView(getApplicationContext());
            textFillMain.setWidth(168);

            final RadioButton radioButton16 = new RadioButton(getApplicationContext());
            radioButton16.setText("BLACK");
            radioGroup2.addView(radioButton16);

            subLayout.addView(radioGroup1);
            subLayout.addView(textFillMain);
            subLayout.addView(radioGroup2);

            layout.addView(midLayout);
            layout.addView(subLayout);

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
            if (screenChangeInt > 0) {
                drawableView.setImageBitmap(null);
            }
            drawableView.clearCanvas();
            rootLayout.removeView(drawableView);
            rootLayout.addView(drawableView);
        } else if (id == R.id.linecolor_button) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            LinearLayout layout = new LinearLayout(getApplicationContext());
            layout.setOrientation(LinearLayout.VERTICAL);

            final RadioGroup radioGroup = new RadioGroup(getApplicationContext());

            final RadioButton radioButton = new RadioButton(getApplicationContext());
            radioButton.setText("RED");
            radioGroup.addView(radioButton);

            final RadioButton radioButton2 = new RadioButton(getApplicationContext());
            radioButton2.setText("GREEN");
            radioGroup.addView(radioButton2);

            final RadioButton radioButton3 = new RadioButton(getApplicationContext());
            radioButton3.setText("BLUE");
            radioGroup.addView(radioButton3);

            final RadioButton radioButton4 = new RadioButton(getApplicationContext());
            radioButton4.setText("YELLOW");
            radioGroup.addView(radioButton4);

            final RadioButton radioButton5 = new RadioButton(getApplicationContext());
            radioButton5.setText("CYAN");
            radioGroup.addView(radioButton5);

            final RadioButton radioButton6 = new RadioButton(getApplicationContext());
            radioButton6.setText("GRAY");
            radioGroup.addView(radioButton6);

            final RadioButton radioButton7 = new RadioButton(getApplicationContext());
            radioButton7.setText("WHITE");
            radioGroup.addView(radioButton7);

            final RadioButton radioButton8 = new RadioButton(getApplicationContext());
            radioButton8.setText("BLACK");
            radioGroup.addView(radioButton8);

            layout.addView(radioGroup);

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

            saveCounter++;

            if (saveCounter % 5 == 0) {
                String titleString = titleView.getText().toString();
                Intent shareIntent = new Intent();
                shareIntent.putExtra(Intent.EXTRA_TEXT, titleString);
                shareIntent.setType("text/plain");
                shareIntent.setAction(Intent.ACTION_SEND);
                startActivity(Intent.createChooser(shareIntent, "Share images to.."));
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            screenChangeInt++;
            Bitmap b = Screenshot.takescreenshotOfRootView(drawableView);
            drawableView.setImageBitmap(b);
            drawableView.setBackgroundResource(R.drawable.ic_launcher_background);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            screenChangeInt++;
            Bitmap b = Screenshot.takescreenshotOfRootView(drawableView);
            drawableView.setImageBitmap(b);
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
                if (shapeWidthString.equals("")) {
                    shapeWidthString = "10";
                }
                shapeWidth = Integer.parseInt(shapeWidthString);
                String shapeHeightString = editText2.getText().toString();
                if (shapeHeightString.equals("")) {
                    shapeHeightString = "10";
                }
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

        final RadioGroup radioGroup = new RadioGroup(getApplicationContext());

        final RadioButton radioButton = new RadioButton(getApplicationContext());
        radioButton.setText("BLACK");
        radioGroup.addView(radioButton);

        final RadioButton radioButton2 = new RadioButton(getApplicationContext());
        radioButton2.setText("RED");
        radioGroup.addView(radioButton2);

        final RadioButton radioButton3 = new RadioButton(getApplicationContext());
        radioButton3.setText("GREEN");
        radioGroup.addView(radioButton3);

        final RadioButton radioButton4 = new RadioButton(getApplicationContext());
        radioButton4.setText("BLUE");
        radioGroup.addView(radioButton4);

        final RadioButton radioButton5 = new RadioButton(getApplicationContext());
        radioButton5.setText("GRAY");
        radioGroup.addView(radioButton5);

        final RadioButton radioButton6 = new RadioButton(getApplicationContext());
        radioButton6.setText("YELLOW");
        radioGroup.addView(radioButton6);

        final RadioButton radioButton7 = new RadioButton(getApplicationContext());
        radioButton7.setText("CYAN");
        radioGroup.addView(radioButton7);

        final RadioButton radioButton8 = new RadioButton(getApplicationContext());
        radioButton8.setText("WHITE");
        radioGroup.addView(radioButton8);

        final RadioButton radioButton9 = new RadioButton(getApplicationContext());
        radioButton9.setText("NONE");
        radioGroup.addView(radioButton9);

        layout.addView(radioGroup);

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
                drawableView.setBackgroundResource(0);
            }
        }
    }
}