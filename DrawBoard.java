/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintclone;

import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import javafx.scene.image.Image;
/**
 *
 * @author Dan
 */
public class DrawBoard{
    private static GraphicsContext graphicContext;
    private ArrayList<Image> images = new ArrayList<>();
    private int historyIndex = -1;
    
    public static void setContext(GraphicsContext con)
    {
        graphicContext = con;
    }
    
    public DrawBoard()
    {
    }

    public void addDrawOperation(DrawingOperation op)
    {
        images.subList(historyIndex + 1, images.size()).clear();

        images.add(op.draw(graphicContext));
        historyIndex++;
    }

    public void undo() {
        if (historyIndex >= 0)
        {
            graphicContext.drawImage(images.get(historyIndex), 0, 0);
            historyIndex--;
        }
    }

    public void redo() {
        if (historyIndex < images.size() - 1)
        {
            historyIndex++;
            graphicContext.drawImage(images.get(historyIndex), 0, 0);
        }
    }
    
}
