/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintclone;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author Dan
 */
public class PaintCloneUIController implements Initializable {
    
    @FXML
    private Label toolID;
    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker colourPicker;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Slider sizeSlider;
    @FXML
    private Label labelSize;
    //@FXML
    //private Button undo;
    @FXML
    private BorderPane borderPane;
    
    private static Stage mainStage;
    boolean toolSelected = true;
    private double size = 10.0;
    private FileHandling fileHandling;
    private NewCanvas setNewCanvas;
    private GraphicsContext graphicContext;
    //private Undo undoImage;
    
    private DrawingOperations draw;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        graphicContext = canvas.getGraphicsContext2D();
        DrawingOperations.setContext(graphicContext);
        fileHandling = new FileHandling();
        draw = new DrawingOperations();
        setNewCanvas = new NewCanvas();
        
        toolID.setText("Brush");
        
        Menu fileMenu = new Menu("File");
        MenuItem newCanvas = new MenuItem("New...");
        newCanvas.setOnAction( e -> {
           setNewCanvas.display();
           updateCanvas(setNewCanvas.getWight(), setNewCanvas.getHeight());
        });
        MenuItem open = new MenuItem("Open...");
        open.setOnAction( e -> {
            fileHandling.loadFile(canvas, graphicContext);
        });
        MenuItem save = new MenuItem("Save...");
        save.setOnAction( e -> {
            fileHandling.saveFile(canvas);
        });
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction( e -> {
            System.exit(0);
        });
        fileMenu.getItems().add(newCanvas);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(open);
        fileMenu.getItems().add(save);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(exit);
        
        Menu toolMenu = new Menu("Tools");
        MenuItem brush = new MenuItem("Brush");
        brush.setOnAction( e -> {
            toolID.setText("Brush");
        });
        
        toolMenu.getItems().add(brush);
        
        sizeSlider.setMin(0);
        sizeSlider.setMax(100);
        sizeSlider.setValue(10);
        sizeSlider.setBlockIncrement(10);
        
        sizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
        @Override
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val)
            {
                size = new_val.doubleValue();
                labelSize.setText("Size: " + String.format("%.2f", new_val));
            }

        });
        
        draw.draw(canvas, toolID, colourPicker);
        
        //undoImage.loadImageOnStack(canvas, brushTool);
        //undoImage.undoAction(brushTool, undo);
        
        menuBar.getMenus().addAll(fileMenu, toolMenu);
    }
    
    private void updateCanvas(int width, int height)
    {
        canvas.setWidth(width);
        canvas.setHeight(height);
        
        graphicContext.clearRect(0, 0, width, height);
        graphicContext.setStroke(Color.BLACK);
        graphicContext.strokeRect(1, 1, width - 1, height - 1);
        
        borderPane.getCenter().prefWidth(width + 20);
        borderPane.getCenter().prefHeight(height + 20);
        
        if((mainStage.getWidth() - 50) < width){
        mainStage.setWidth(width + 100);
        }
        if((mainStage.getHeight() - 50) < height){
        mainStage.setHeight(height + 100);
        }
    }

    public static void getStage(Stage stage)
    {
        mainStage = stage;
    }
    
}
