package tooearly.com.neu_paint_app.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import tooearly.com.neu_paint_app.Util.BrushShape;
import tooearly.com.neu_paint_app.Util.LinePaintCommand;
import tooearly.com.neu_paint_app.Util.MatrixPaintCommand;
import tooearly.com.neu_paint_app.Util.PaintCommandStack;
import tooearly.com.neu_paint_app.Util.ShapePaintCommand;
import tooearly.com.neu_paint_app.Util.ShapeType;

public class PaintView extends View {

    public static final String TAG = "PaintView";
    private static float curX;
    private static float curY;
    private static Paint myBrush;
    private static ArrayList<RectF> lineCoordsAry;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.stack = new PaintCommandStack();
        setWillNotDraw(false);

        init();
    }

    private void init() {
        curX = 0;
        curY = 0;
        lineCoordsAry = new ArrayList<>();

        myBrush = new Paint();
        myBrush.setColor(Color.BLACK);
        myBrush.setStyle(Paint.Style.FILL);
        myBrush.setStrokeWidth(3);

        Paint filledBlue = new Paint();
        filledBlue.setColor(Color.BLUE);
        filledBlue.setStyle(Paint.Style.FILL);

        Paint outlinedRed = new Paint();
        outlinedRed.setColor(Color.RED);
        outlinedRed.setStyle(Paint.Style.STROKE);

        stack.push(new ShapePaintCommand("Draw Blue Rectangle", filledBlue, ShapeType.Oval, new RectF(100, 100, 200, 200)));
        stack.push(new MatrixPaintCommand("Shift Vertically", 1, 1, 0, 100));
        stack.push(new MatrixPaintCommand("Flip Vertically", 1, -1));
        stack.push(new ShapePaintCommand("Draw Red Oval", outlinedRed, ShapeType.Line, new RectF(220, 120, 280, 180)));
    }

    public final PaintCommandStack stack;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(TAG, "Action was DOWN");
                Log.d(TAG, event.getX() + "");
                Log.d(TAG, event.getY() + "");
                curX = event.getRawX();
                curY = event.getRawY();
                stack.push(new LinePaintCommand("Brush", cloneBrush(myBrush), lineCoordsAry));
                return true;
            case (MotionEvent.ACTION_MOVE):
                Log.d(TAG, "Action was MOVE");

                float newX = event.getRawX();
                float newY = event.getRawY();

                lineCoordsAry.add(new RectF(curX, curY, newX, newY));
                curX = newX;
                curY = newY;
                invalidate();

                return true;
            case (MotionEvent.ACTION_UP):
                Log.d(TAG, "Action was UP");

                float endX = event.getRawX();
                float endY = event.getRawY();

                lineCoordsAry.add(new RectF(curX, curY, endX, endY));
                invalidate();
                lineCoordsAry = new ArrayList<>();

                curX = endX;
                curY = endY;
                //reset lineCoordsAry

                invalidate();
                return true;
            case (MotionEvent.ACTION_CANCEL):
                Log.d(TAG, "Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE):
                Log.d(TAG, "Movement occurred outside bounds of current screen element");
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    private Paint cloneBrush(Paint old) {
        Paint brush = new Paint();

        brush.setColor(old.getColor());
        brush.setStyle(old.getStyle());
        brush.setAlpha(old.getAlpha());
        brush.setAntiAlias(old.isAntiAlias());
        brush.setDither(old.isDither());
        brush.setFakeBoldText(old.isFakeBoldText());
        brush.setFlags(old.getFlags());
        brush.setColorFilter(old.getColorFilter());
        brush.setFilterBitmap(old.isFilterBitmap());
        brush.setHinting(old.getHinting());
        brush.setLinearText(old.isLinearText());
        brush.setMaskFilter(old.getMaskFilter());
        brush.setPathEffect(old.getPathEffect());
        brush.setShader(old.getShader());
        brush.setStrikeThruText(old.isStrikeThruText());
        brush.setStrokeCap(old.getStrokeCap());
        brush.setStrokeJoin(old.getStrokeJoin());
        brush.setStrokeMiter(old.getStrokeMiter());
        brush.setStrokeWidth(old.getStrokeWidth());
        brush.setSubpixelText(old.isSubpixelText());
        brush.setTextAlign(old.getTextAlign());
        brush.setTextSize(old.getTextSize());
        brush.setTextScaleX(old.getTextScaleX());
        brush.setTextSkewX(old.getTextSkewX());
        brush.setUnderlineText(old.isUnderlineText());
        brush.setXfermode(old.getXfermode());
        brush.setStrikeThruText(old.isStrikeThruText());
        brush.setTypeface(old.getTypeface());

        if (Build.VERSION.SDK_INT >= 21) {
            brush.setElegantTextHeight(old.isElegantTextHeight());
            brush.setFontFeatureSettings(old.getFontFeatureSettings());
            brush.setLetterSpacing(old.getLetterSpacing());
        }
        if (Build.VERSION.SDK_INT >= 24) {
            brush.setTextLocales(old.getTextLocales());
        }

        return brush;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.stack.render(this, canvas);
    }

    private ShapeType shapeType = ShapeType.Rect;
    public void setShapeType(ShapeType type) {
        this.shapeType = type;
    }

    @SuppressWarnings("FieldCanBeLocal")
    private int brushColor, brushSize;
    private BrushShape brushShape;
    public void setBrushColor(int color) {
        this.brushColor = color;
        myBrush.setColor(this.brushColor);
    }
    public void setBrushSize(int size) {
        this.brushSize = size;
        myBrush.setStrokeWidth(this.brushSize);
    }

//    protected void onBrushStyleLineChange(Paint.Style newStyle) {
//        myBrush.setStyle(newStyle);
//    }
//
//    protected void onBrushStyleJoinChange(Paint.Join newJoinStyle) {
//        myBrush.setStrokeJoin(newJoinStyle);
//    }
//
//    protected void onBrushShapeChange(float newWidth) {
//        myBrush.setStrokeWidth(newWidth);
//    }
//
//    protected void onBrushSizeChange(float newWidth) {
//        myBrush.setStrokeWidth(newWidth);
//    }
//
//    protected void onBrushColorChange(int newColor) {
//        myBrush.setColor(newColor);
//    }
//
//    protected void flipCanvasHorizontally() {
//        //TODO: flip canvas horizontally
//    }
//
//    protected void flipCanvasVertically() {
//        //TODO: flip canvas vertically
//    }
//
//    protected void invertCanvas() {
//        //TODO: invert canvas colors
//    }
//
//    protected void undoAction() {
//        //TODO: undo last draw action
//    }
//
//    protected void saveCanvas() {
//        //TODO: save current canvas
//    }
//
//    protected void clearCanvas() {
//        //TODO: clear current canvas
//        this.clearCanvas();
//    }

}
