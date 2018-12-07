package paintclone;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javax.imageio.ImageIO;
import javafx.stage.FileChooser;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

/*
 * @author Dan
 */
public class FileHandling {
    
    private final Stage mainStage;
    
    public FileHandling()
    {
        mainStage = new Stage();
    }
    
    public void saveFile(Canvas canvas)
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(filter);
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
                System.out.println("File did not save");
            }
        }
    }

    public void loadFile(Canvas canvas, GraphicsContext brushTool)
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setTitle("Save Image");
        File file = fileChooser.showOpenDialog(mainStage);
        
        Image image = new Image(file.toURI().toString());
        brushTool.drawImage(image, 0, 0);
    }
}
