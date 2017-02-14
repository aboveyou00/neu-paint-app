package tooearly.com.neu_paint_app.Util;

public class ClearPaintCommand extends PaintCommand {
    public ClearPaintCommand(int color) {
        super("Clear Canvas");
        this.color = color;
    }

    public final int color;

    @Override
    public void render(PaintFrame frame) {
        frame.canvas.drawColor(color);
    }
}
