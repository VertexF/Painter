/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintclone;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javax.imageio.ImageIO;
import javafx.stage.FileChooser;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

/**
 *
 * @author Dan
 */
public class FileHandling {
    
    private Stage mainStage;
    
    public FileHandling()
    {
        mainStage = new Stage();
    }
    
    public void saveFile(Canvas canvas)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        File file = fileChooser.showSaveDialog(mainStage);
        
        if(file != null)
        {
            try
            {
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT);
                canvas.snapshot(params, writableImage);
                BufferedImage canvasImage = SwingFXUtils.fromFXImage(writableImage, null);

                ImageIO.write(canvasImage, "png", file);
            } 
            catch (IOException ex)
            {
                //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("File did not save");
            }
        }
    }

    public void loadFile(Canvas canvas, GraphicsContext brushTool)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        File file = fileChooser.showOpenDialog(mainStage);
        
        Image image = new Image(file.toURI().toString());
        brushTool.drawImage(image, 0, 0);
    }
}
