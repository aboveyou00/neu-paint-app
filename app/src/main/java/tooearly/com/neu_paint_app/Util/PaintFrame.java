package tooearly.com.neu_paint_app.Util;

import android.graphics.Canvas;

import tooearly.com.neu_paint_app.Views.PaintView;

public class PaintFrame {
    public PaintFrame(PaintView view, Canvas canvas) {
        this.paintView = view;
        this.canvas = canvas;
    }

    public final PaintView paintView;
    public final Canvas canvas;
}
