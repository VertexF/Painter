package paintclone;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author Dan
 */
public interface DrawingOperation {
    public void draw(GraphicsContext graphicContext);
    public Image getImage(GraphicsContext graphicContext);
}
