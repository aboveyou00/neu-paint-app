package tooearly.com.neu_paint_app.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import tooearly.com.neu_paint_app.R;
import tooearly.com.neu_paint_app.Util.ClearPaintCommand;
import tooearly.com.neu_paint_app.Util.InvertPaintCommand;
import tooearly.com.neu_paint_app.Util.MatrixPaintCommand;
import tooearly.com.neu_paint_app.Util.ShapeType;
import tooearly.com.neu_paint_app.Views.PaintView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    public static final float ACCELEROMETER_THRESHOLD = 28.0f;
    private static int saveNum = 1;
    private File root = android.os.Environment.getExternalStorageDirectory();
    private File dir = new File (root.getAbsolutePath() + "/Download");
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ImageButton saveButton = (ImageButton)findViewById(R.id.saveButton);
//        ImageButton clearButton = (ImageButton)findViewById(R.id.clearButton);
//        ImageButton invertColorButton = (ImageButton)findViewById(R.id.invertColorButton);
//        ImageButton flipHorizontalButton = (ImageButton)findViewById(R.id.flipHorizontalButton);
//        ImageButton flipVerticalButton = (ImageButton)findViewById(R.id.flipVerticalButton);
//        ImageButton undoButton = (ImageButton)findViewById(R.id.undoButton);
//
//        Button blueButton = (Button)findViewById(R.id.blueButton); //#FF0026F9
//        Button redButton = (Button)findViewById(R.id.redButton); //#FFFF000D
//        Button greenButton = (Button)findViewById(R.id.greenButton); //#FF2DF800
//        Button yellowButton = (Button)findViewById(R.id.yellowButton); //#FFF7FF00
//        Button purpleButton = (Button)findViewById(R.id.purpleButton); //#FFB700FF
//        Button orangeButton = (Button)findViewById(R.id.orangeButton); //#FFFF8C00
//        Button pinkButton = (Button)findViewById(R.id.pinkButton); //#FFFF007B
//        Button brownButton = (Button)findViewById(R.id.brownButton); //#FF964B1F
//
//        ImageButton brush1ShapeButton = (ImageButton)findViewById(R.id.brush1ShapeButton);
//        ImageButton brush2ShapeButton = (ImageButton)findViewById(R.id.brush2ShapeButton);
//        ImageButton brush3ShapeButton = (ImageButton)findViewById(R.id.brush3ShapeButton);
//        ImageButton brush4ShapeButton = (ImageButton)findViewById(R.id.brush4ShapeButton);
//        ImageButton brush5ShapeButton = (ImageButton)findViewById(R.id.brush5ShapeButton);
//
//        ImageButton triangleShapeButton = (ImageButton)findViewById(R.id.triangleShapeButton);
//        ImageButton lineShapeButton = (ImageButton)findViewById(R.id.lineShapeButton);
//        ImageButton circleShapeButton = (ImageButton)findViewById(R.id.circleShapeButton);
//        ImageButton squareShapeButton = (ImageButton)findViewById(R.id.rectangleShapeButton);

        this.paintView = (PaintView)findViewById(R.id.paintView);

        isInitialized = false;
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private PaintView paintView;

    private boolean isInitialized;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float lastX, lastY, lastZ;

    public void setBrushSize(View view) {
        paintView.setBrushSize(Integer.parseInt(view.getTag().toString()));
    }

    public void setColor(View view) {
        paintView.setBrushColor(((ColorDrawable)view.getBackground()).getColor());
    }

    public void setShapeTriangle(View view) {
        paintView.setShapeType(ShapeType.Tri);
    }
    public void setShapeRectangle(View view) {
        paintView.setShapeType(ShapeType.Rect);
    }
    public void setShapeCircle(View view) {
        paintView.setShapeType(ShapeType.Oval);
    }
    public void setShapeLine(View view) {
        paintView.setShapeType(ShapeType.Line);
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
        file = new File(dir, "myPicture"+saveNum+".png");
        saveNum++;
        System.out.println("save file: " + file);
        dir.mkdirs();


        if(isExternalStorageAvailable()) {
            try {
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                paintView.getDrawingCache().compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("External Storage is not available.");
        }
    }

    public boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0],
              y = event.values[1],
              z = event.values[2];
        if (!isInitialized) {
            isInitialized = true;
            lastX = x;
            lastY = y;
            lastZ = z;
        }
        else {
            float dist = (float)Math.sqrt(Math.pow(lastX - x, 2) + Math.pow(lastY - y, 2) + Math.pow(lastZ - z, 2));
            if (dist > ACCELEROMETER_THRESHOLD) clearCanvas();
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        ;
    }
}
