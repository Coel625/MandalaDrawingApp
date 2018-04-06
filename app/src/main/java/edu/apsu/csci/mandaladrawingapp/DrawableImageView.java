package edu.apsu.csci.mandaladrawingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class DrawableImageView extends ImageView implements View.OnTouchListener {
    float dwnX=0;
    float dwnY=0;
    float upX=0;
    float upY=0;

    Canvas canvas;
    Paint paint;
    Matrix matrix;

    public DrawableImageView(Context context){
        super(context);
        setOnTouchListener(this);
    }
    public DrawableImageView(Context context, AttributeSet attrs){
        super(context, attrs);
        setOnTouchListener(this);
    }    public DrawableImageView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
    }
    public void setNewImage(Bitmap alteredBmp, Bitmap bmp, int color, int sWidth){
        canvas = new Canvas(alteredBmp);
        paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(sWidth);
        matrix = new Matrix();
        canvas.drawBitmap(bmp,matrix,paint);
        setImageBitmap(alteredBmp);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event){
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                dwnX=getPointerCoords(event)[0];
                dwnY=getPointerCoords(event)[1];
                break;
            case MotionEvent.ACTION_MOVE:
                upX=getPointerCoords(event)[0];
                upY=getPointerCoords(event)[1];
                canvas.drawLine(dwnX, dwnY, upX, upY, paint);
                invalidate();
                dwnX=upX;
                dwnY=upY;
                break;
            case MotionEvent.ACTION_UP:
                upX=getPointerCoords(event)[0];
                upY=getPointerCoords(event)[1];
                canvas.drawLine(dwnX, dwnY, upX, upY, paint);
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
            return true;
        }
        final float[] getPointerCoords(MotionEvent e) {
        final int index = e.getActionIndex();
        final float[] coords = new float[] {e.getX(index), e.getY(index)};
        Matrix matrix = new Matrix();
        getImageMatrix().invert(matrix);
        matrix.postTranslate(getScrollX(), getScrollY());
        matrix.mapPoints(coords);
        return coords;
        }
    }
