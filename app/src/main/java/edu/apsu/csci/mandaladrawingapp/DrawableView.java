package edu.apsu.csci.mandaladrawingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.ArrayList;

public class DrawableView extends ImageView {

    public int width;
    public int height;
    private Boolean isEditable;
    private Path drawPath;
    private Paint drawPaint;
    private Paint shapePaint;
    private Paint shapeBorderPaint;
    int shapeWidthSize = 0;
    int shapeHeightSize = 0;
    private Paint canvasPaint;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private int paintColor = Color.BLACK;
    private int lineThickness = 10;
    private float pixels;
    private float dpiPixels;
    private ArrayList<Rect> rectangles = new ArrayList<Rect>();
    private ArrayList<RectF> rectFS = new ArrayList<RectF>();
    private int shapeInt = 0;

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
        setup();
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
        drawPaint.setStrokeWidth(lineThickness);

        shapePaint = new Paint();
        shapePaint.setColor(Color.TRANSPARENT);
        shapePaint.setStyle(Paint.Style.FILL);

        shapeBorderPaint = new Paint();
        shapeBorderPaint.setColor(Color.BLACK);
        shapeBorderPaint.setStyle(Paint.Style.STROKE);
        shapeBorderPaint.setStrokeWidth(lineThickness / 2);

        pixels = 50;

        float dpiSize = 50;
        DisplayMetrics dm = getResources().getDisplayMetrics();
        dpiPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpiSize, dm);
    }

    public void setDrawingEnabled(boolean isEditable) {
        this.isEditable = isEditable;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);

        for (Rect rect : rectangles) {
            drawCanvas.drawRect(rect, shapePaint);
            drawCanvas.drawRect(rect, shapeBorderPaint);
        }

        for (RectF rectF: rectFS) {
            drawCanvas.drawOval(rectF, shapePaint);
            drawCanvas.drawOval(rectF, shapeBorderPaint);
        }
    }

    public void clearCanvas() {
        onSizeChanged(width, height, 0, 0);
        rectangles.clear();
        rectFS.clear();
    }

    public void drawLineColor(int lineColor) {
        drawPaint.setColor(lineColor);
        shapeInt = 0;
    }

    public void thickLineCanvas(int thickLine) {
        drawPaint.setStrokeWidth(lineThickness * thickLine);
        shapeInt = 0;
    }

    public void drawRect(int rectWidthValue, int rectHeightValue) {
        shapeWidthSize = rectWidthValue;
        shapeHeightSize = rectHeightValue;
        shapeInt = 0;
        shapeInt++;
    }

    public void drawColorRect(int colorValue) {
        rectangles.clear();
        rectFS.clear();
        shapePaint.setColor(colorValue);
        shapeInt = 0;
        shapeInt++;
    }

    public void drawCircle(int circleWidthValue, int circleHeightValue) {
        shapeWidthSize = circleWidthValue;
        shapeHeightSize = circleHeightValue;
        shapeInt = 0;
        shapeInt += 2;
    }

    public void drawColorCircle(int colorValue) {
        rectangles.clear();
        rectFS.clear();
        shapePaint.setColor(colorValue);
        shapeInt = 0;
        shapeInt += 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float offset = pixels;
        if (shapeInt == 0) {
            if (isEditable) {
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
            } else {
                return false;
            }
        } else if (shapeInt == 1) {
            if (isEditable) {
                int xCoor = (int) event.getX();
                int yCoor = (int) event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        rectangles.add(new Rect((int) offset + xCoor + shapeWidthSize, yCoor, xCoor, (int) dpiPixels + yCoor + shapeHeightSize));
                        break;
                    default:
                        return false;
                }
            } else {
                return false;
            }
        } else if (shapeInt == 2) {
            if (isEditable) {
                float xCoor = event.getX();
                float yCoor = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        rectFS.add(new RectF(offset + xCoor + shapeWidthSize, yCoor, xCoor, dpiPixels + yCoor + shapeHeightSize));
                        break;
                    default:
                        return false;
                }
            } else {
                return false;
            }
        }
        invalidate();
        return true;
    }
}