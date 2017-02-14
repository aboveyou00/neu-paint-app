package tooearly.com.neu_paint_app.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class PaintView extends View {

    public static final String TAG = "PaintView";

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        this.attrs = attrs;

        myBrush = new Paint();
        myBrush.setAntiAlias(true);
        myBrush.setColor(Color.BLACK);
        myBrush.setStyle(Paint.Style.STROKE);
        myBrush.setStrokeJoin(Paint.Join.ROUND);
        myBrush.setStrokeWidth(4f);

        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.FILL);

        setWillNotDraw(false);
    }

    private Context context;
    private AttributeSet attrs;
    private Paint myBrush, redPaint;

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

        canvas.drawColor(Color.GREEN);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int drawWidth = getWidth() - paddingLeft - paddingRight;
        int drawHeight = getHeight() - paddingTop - paddingBottom;

        canvas.drawRect(paddingLeft + 32, paddingTop + 32, paddingLeft + drawWidth - 32, paddingTop + drawHeight - 32, redPaint);
    }

    protected void onBrushStyleLineChange(Paint.Style newStyle) {
        myBrush.setStyle(newStyle);
    }

    protected void onBrushStyleJoinChange(Paint.Join newJoinStyle) {
        myBrush.setStrokeJoin(newJoinStyle);
    }

    protected void onBrushShapeChange(float newWidth) {
        myBrush.setStrokeWidth(newWidth);
    }

    protected void onBrushSizeChange(float newWidth) {
        myBrush.setStrokeWidth(newWidth);
    }

    protected void onBrushColorChange(int newColor) {
        myBrush.setColor(newColor);
    }

    protected void flipCanvasHorizontally() {
        //TODO: flip canvas horizontally
    }

    protected void flipCanvasVertically() {
        //TODO: flip canvas vertically
    }

    protected void invertCanvas() {
        //TODO: invert canvas colors
    }

    protected void undoAction() {
        //TODO: undo last draw action
    }

    protected void saveCanvas() {
        //TODO: save current canvas
    }

    protected void clearCanvas() {
        //TODO: clear current canvas
        this.clearCanvas();
    }

}
