package tooearly.com.neu_paint_app.Util;

import android.graphics.Paint;
import android.graphics.RectF;

public class ShapePaintCommand extends PaintCommand {
    public ShapePaintCommand(String name, Paint paint, ShapeType type, RectF dimens) {
        super(name);
        this.paint = paint;
        this.type = type;
        this.dimens = dimens;
    }

    public final Paint paint;
    public final ShapeType type;
    public final RectF dimens;

    @Override
    public void render(PaintFrame frame) {
        switch (type) {
        case Rect:
            frame.canvas.drawRect(dimens, paint);
            break;

        case Oval:
            frame.canvas.drawOval(dimens, paint);
            break;

        case Line:
            frame.canvas.drawLine(dimens.left, dimens.top, dimens.right, dimens.bottom, paint);
            break;

        default:
            throw new IllegalStateException();
        }
    }
}
