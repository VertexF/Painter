/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintclone;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;

/**
 *
 * @author Dan
 */
public class DrawingOperations {
    private double size;
    private static GraphicsContext graphicContext;
    
    boolean toolSelected = true;
    
    public static void setContext(GraphicsContext con)
    {
        graphicContext = con;
	size = 10.0;
    }
    
    public DrawingOperations()
    {
        
    }
    
    public void draw(Canvas canvas, Label toolID, ColorPicker colourPicker)
    {
        canvas.setOnMouseDragged( e -> {
        if(size != 0)
        {
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;
            
            if(toolID.getText().equals("Brush"))
            {
                graphicContext.setFill(colourPicker.getValue());
                graphicContext.fillRoundRect(x, y, size, size, size, size);
            }
            
            System.out.println(size);
        }
        });
    }
    
    public void setSize(double s)
    {
        size = s;
    }
}
