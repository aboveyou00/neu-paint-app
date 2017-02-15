package tooearly.com.neu_paint_app.Util;

import android.graphics.Paint;
import android.graphics.Path;

public class PathPaintCommand extends PaintCommand {
    public PathPaintCommand(String name, Paint brush, Path path) {
        super(name);
        this.brush = brush;
        this.path = path;
    }

    public final Paint brush;
    public final Path path;

    @Override
    public void render(PaintFrame frame) {
        frame.canvas.drawPath(path, brush);
    }
}
