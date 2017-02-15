package tooearly.com.neu_paint_app.Util;

import android.graphics.Paint;

public class ClearPaintCommand extends PaintCommand {
    public ClearPaintCommand(int color) {
        this(new Paint());
        this.brush.setColor(color);
    }
    public ClearPaintCommand(Paint brush) {
        super("Clear Canvas");
        this.brush = brush;
    }

    public final Paint brush;

    @Override
    public void render(PaintFrame frame) {
        frame.canvas.drawPaint(brush);
    }
}
