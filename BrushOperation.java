/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintclone;

import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * @author Dan
 */
public class BrushOperation implements DrawingOperation {
    
    private double size;
    private ColorPicker colours;
    double x;
    double y;
    Image image;
    
    public BrushOperation(ColorPicker col)
    {
        size = 10.0;
        colours = col;
    }
    
    @Override
    public Image draw(GraphicsContext graphicContext)
    {
        graphicContext.getCanvas().setOnMouseDragged( e -> {
        if(size != 0)
        {
            x = e.getX() - size / 2;
            y = e.getY() - size / 2;

            graphicContext.setFill(colours.getValue());
            graphicContext.fillRoundRect(x, y, size, size, size, size);
        }
        
        });
        
        graphicContext.getCanvas().setOnMouseClicked(e -> {
        if(size != 0)
        {
            x = e.getX() - size / 2;
            y = e.getY() - size / 2;
            
            graphicContext.setFill(colours.getValue());
            graphicContext.fillRoundRect(x, y, size, size, size, size);
        }
        });
        
        Canvas can = graphicContext.getCanvas();
        
        WritableImage writableImage = new WritableImage((int) can.getWidth(), (int) can.getHeight());
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        can.snapshot(params, writableImage);
        BufferedImage canvasImage = SwingFXUtils.fromFXImage(writableImage, null);
        image = SwingFXUtils.toFXImage(canvasImage, null);
        
        return image;
    }
    
    public void setSize(double s)
    {
        size = s;
    }
}
