package tooearly.com.neu_paint_app.Util;

import android.graphics.ColorFilter;

public class InvertPaintCommand extends PaintCommand {
    public InvertPaintCommand() {
        super("Invert Colors");
    }

    @Override
    public void setColorFilter(ColorFilter filter) {
        ;
    }

    @Override
    public void render(PaintFrame frame) {
        ;
    }
}
