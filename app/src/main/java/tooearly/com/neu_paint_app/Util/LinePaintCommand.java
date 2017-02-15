package tooearly.com.neu_paint_app.Util;

import android.graphics.Paint;
import android.graphics.PointF;

import java.util.List;

public class LinePaintCommand extends PaintCommand {
    public LinePaintCommand(String name, Paint paint, List<PointF> pointsAry) {
        super(name);
        this.paint = paint;
        this.pointsAry = pointsAry;
    }

    public final Paint paint;
    public final List<PointF> pointsAry;

    @Override
    public void render(PaintFrame frame) {
        for (int i = 0; i < pointsAry.size() - 1; i++) {
            PointF from = pointsAry.get(i),
                   to = pointsAry.get(i + 1);
            frame.canvas.drawLine(from.x, from.y, to.x, to.y, paint);
        }
    }
}
