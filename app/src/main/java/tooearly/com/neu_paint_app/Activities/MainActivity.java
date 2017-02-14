package tooearly.com.neu_paint_app.Activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import tooearly.com.neu_paint_app.R;
import tooearly.com.neu_paint_app.Util.ClearPaintCommand;
import tooearly.com.neu_paint_app.Util.InvertPaintCommand;
import tooearly.com.neu_paint_app.Util.MatrixPaintCommand;
import tooearly.com.neu_paint_app.Util.ShapeType;
import tooearly.com.neu_paint_app.Views.PaintView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.paintView = (PaintView)findViewById(R.id.paintView);
    }

    private PaintView paintView;

    public void setColor(View view) {
        paintView.brushColor = ((ColorDrawable)view.getBackground()).getColor();
    }

    public void setShapeTriangle(View view) {
        paintView.shapeType = ShapeType.Tri;
    }
    public void setShapeRectangle(View view) {
        paintView.shapeType = ShapeType.Rect;
    }
    public void setShapeCircle(View view) {
        paintView.shapeType = ShapeType.Oval;
    }
    public void setShapeLine(View view) {
        paintView.shapeType = ShapeType.Line;
    }

    public void undoPressed(View view) {
        undo();
    }
    public void redoPressed(View view) {
        redo();
    }
    public void undo() {
        if (paintView.stack.canUndo()) paintView.stack.undo();
        paintView.invalidate();
    }
    public void redo() {
        if (paintView.stack.canRedo()) paintView.stack.redo();
        paintView.invalidate();
    }

    public void invertPressed(View view) {
        invert();
    }
    public void invert() {
        paintView.stack.push(new InvertPaintCommand());
        paintView.invalidate();
    }

    public void flipHorizontalPressed(View view) {
        scaleCanvas("Flip Horizontally", -1, 1);
    }
    public void flipVerticalPressed(View view) {
        scaleCanvas("Flip Vertically", 1, -1);
    }
    public void scaleCanvas(String name, float scaleX, float scaleY) {
        paintView.stack.push(new MatrixPaintCommand(name, scaleX, scaleY));
        paintView.invalidate();
    }

    public void clearCanvasPressed(View view) {
        clearCanvas();
    }
    public void clearCanvas() {
        paintView.stack.push(new ClearPaintCommand(Color.WHITE));
        paintView.invalidate();
    }

    public void saveCanvasPressed(View view) {
        saveCanvas();
    }
    public void saveCanvas() {

    }
}
