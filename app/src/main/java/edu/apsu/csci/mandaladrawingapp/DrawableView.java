package edu.apsu.csci.mandaladrawingapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class DrawableView extends ImageView {

    public static final int CANVAS_DATA = 2000;

    public int width;
    public  int height;
    private boolean isEditable;
    private Path drawPath;
    private Paint drawPaint;
    private Paint rectPaint;
    private Paint circlePaint;
    private Paint canvasPaint;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private int paintColor = Color.BLACK;
    private float pixels;
    private float dpiPixels;

    public DrawableView(Context context) {
        super(context);
    }
    public DrawableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.canvasPaint = new Paint(Paint.DITHER_FLAG);
        setup();
    }
    public DrawableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.height = h;
        this.width = w;
        canvasBitmap = Bitmap.createBitmap(w + 1, h + 1, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }
    private void setup() {
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setDither(true);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint.setStrokeWidth(10);

        rectPaint = new Paint();
        rectPaint.setColor(paintColor);
        rectPaint.setStyle(Paint.Style.FILL);

        circlePaint = new Paint();
        circlePaint.setColor(paintColor);
        circlePaint.setStyle(Paint.Style.FILL);

        pixels = 50;

        float dpiSize = 50;
        DisplayMetrics dm = getResources().getDisplayMetrics();
        dpiPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpiSize, dm);
    }
    public void setDrawingEnabled(boolean isEditable){
        this.isEditable = isEditable;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    public void clearCanvas() {
        onSizeChanged(width, height, 0, 0);
    }

    public void blueCanvas() {
        drawPaint.setColor(Color.BLUE);
    }

    public void thickLineCanvas() {
        drawPaint.setStrokeWidth(50);
    }

    public void drawRect() {
        rectPaint.setColor(Color.BLACK);
        float offset = pixels;
        drawCanvas.drawRect(offset, 0f, offset + dpiPixels, dpiPixels, rectPaint);
    }

    public void drawColorRect() {
        rectPaint.setColor(Color.BLUE);
        float offset = pixels;
        drawCanvas.drawRect(offset, 0f, offset + dpiPixels, dpiPixels, rectPaint);
    }

    public void drawCircle() {
        circlePaint.setColor(Color.BLACK);
        float offset = pixels;
        drawCanvas.drawOval(offset, dpiPixels, offset + dpiPixels, dpiPixels + dpiPixels, circlePaint);
    }

    public void drawColorCircle() {
        circlePaint.setColor(Color.BLUE);
        float offset = pixels;
        drawCanvas.drawOval(offset, dpiPixels, offset + dpiPixels, dpiPixels + dpiPixels, circlePaint);
    }

    private void saveImages(Bitmap finalBitmap) {

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
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isEditable){
            float touchX = event.getX();
            float touchY = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    drawPath.moveTo(touchX, touchY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    drawPath.lineTo(touchX, touchY);
                    break;
                case MotionEvent.ACTION_UP:
                    drawPath.lineTo(touchX, touchY);
                    drawCanvas.drawPath(drawPath, drawPaint);
                    drawPath = new Path();
                    break;
                default:
                    return false;
            }
        } else{
            return false;
        }
        invalidate();
        return true;
    }
}