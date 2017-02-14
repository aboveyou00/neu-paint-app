package tooearly.com.neu_paint_app.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import tooearly.com.neu_paint_app.Util.BrushShape;
import tooearly.com.neu_paint_app.Util.MatrixPaintCommand;
import tooearly.com.neu_paint_app.Util.PaintCommandStack;
import tooearly.com.neu_paint_app.Util.ShapePaintCommand;
import tooearly.com.neu_paint_app.Util.ShapeType;

public class PaintView extends View {

    public static final String TAG = "PaintView";

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.stack = new PaintCommandStack();
        setWillNotDraw(false);

        init();
    }

    private void init() {
        Paint filledBlue = new Paint();
        filledBlue.setColor(Color.BLUE);
        filledBlue.setStyle(Paint.Style.FILL);

        Paint outlinedRed = new Paint();
        outlinedRed.setColor(Color.RED);
        outlinedRed.setStyle(Paint.Style.STROKE);

        stack.push(new ShapePaintCommand("Draw Blue Rectangle", filledBlue, ShapeType.Oval, new RectF(100, 100, 200, 200)));
        stack.push(new MatrixPaintCommand("Shift Vertically", 1, 1, 0, 100));
        stack.push(new MatrixPaintCommand("Flip Vertically", 1, -1));
        stack.push(new ShapePaintCommand("Draw Red Oval", outlinedRed, ShapeType.Oval, new RectF(220, 120, 280, 180)));
    }

    public final PaintCommandStack stack;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(TAG, "Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE):
                Log.d(TAG, "Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP):
                Log.d(TAG, "Action was UP");
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.stack.render(this, canvas);
    }

    private int brushColor, brushSize;
    private BrushShape brushShape;

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
