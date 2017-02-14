package tooearly.com.neu_paint_app.Util;

import android.graphics.Paint;
import android.graphics.RectF;

import java.util.List;

/**
 * Created by Kyle Kacprzynski on 2/14/2017.
 */

public class LinePaintCommand extends PaintCommand {
    public LinePaintCommand(String name, Paint paint, ShapeType type, List<RectF> dimensAry) {
        super(name);
        this.paint = paint;
        this.type = type;
        this.dimensAry = dimensAry;
    }

    public final Paint paint;
    public final ShapeType type;
    public final List<RectF> dimensAry;

    @Override
    public void render(PaintFrame frame) {

        for(int i = 0; i < dimensAry.size(); i++) {
            frame.canvas.drawLine(dimensAry.get(i).left, dimensAry.get(i).top, dimensAry.get(i).right, dimensAry.get(i).bottom, paint);
        }
    }
}
