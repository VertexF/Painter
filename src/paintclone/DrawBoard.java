package paintclone;

import javafx.scene.canvas.GraphicsContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 * @author Dan
 */
public class DrawBoard{
    //private static GraphicsContext graphicContext;
    private ObservableList<Image> images;
    private int historyIndex = -1;
    
    public static void setContext(GraphicsContext con)
    {
        //graphicContext = con;
    }
    
    public DrawBoard()
    {
        images = FXCollections.observableArrayList();
    }

    public void addDrawOperation(DrawingOperation op, GraphicsContext graphicContext)
    {
        //clear history after current postion
        images.subList(historyIndex + 1, images.size()).clear();

        //add new operation
        historyIndex++;
        op.draw(graphicContext);
        images.add(op.getImage(graphicContext));
    }

    public void undo(GraphicsContext graphicContext)
    {
        if(historyIndex >= 0)
        {
            graphicContext.drawImage(images.get(historyIndex), 0, 0);
            //historyIndex--;
        }
    }

    public void redo(GraphicsContext graphicContext)
    {
        if(historyIndex < images.size() - 1)
        {
            historyIndex++;
            graphicContext.drawImage(images.get(historyIndex), 0, 0);
        }
    }
}
