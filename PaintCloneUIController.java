/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintclone;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
    @FXML
    private AnchorPane scrollAnchor;
    
    private static Stage mainStage;
    private double size;
    private FileHandling fileHandling;
    private NewCanvas setNewCanvas;
    private GraphicsContext graphicContext;
    private DrawBoard drawBoard;
    //private Undo undoImage;
    private boolean isCanvasCreated;
    private BrushOperation brushOp;
    
    private DrawingOperation draw;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        graphicContext = canvas.getGraphicsContext2D();
        drawBoard = new DrawBoard();
        drawBoard.setContext(graphicContext);
        fileHandling = new FileHandling();
        setNewCanvas = new NewCanvas();
        brushOp = new BrushOperation(colourPicker);
        isCanvasCreated = false;
        
        toolID.setText("Brush");
        
        Menu fileMenu = new Menu("File");
        MenuItem newCanvas = new MenuItem("New...");
        newCanvas.setOnAction( e -> {
            if(setNewCanvas.display())
            {
                resetCanvas(setNewCanvas.getWight(), setNewCanvas.getHeight());
                isCanvasCreated = true;
            }
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
        
        Menu editMenu = new Menu("Edit");
        MenuItem undo = new MenuItem("Undo");
        undo.setOnAction( e -> {
            if(isCanvasCreated)
                drawBoard.undo();
        });
        MenuItem redo = new MenuItem("Redo");
        redo.setOnAction( e -> {
            if(isCanvasCreated)
                drawBoard.redo();
        });
        
        editMenu.getItems().add(undo);
        editMenu.getItems().add(redo);
        
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
                brushOp.setSize(size);
            }

        });

        menuBar.getMenus().addAll(fileMenu, editMenu, toolMenu);
        
        canvas.setOnMousePressed(e -> {
            drawBoard.addDrawOperation(brushOp);
        });
    }
    
    private void resetCanvas(int width, int height)
    {
        canvas.setWidth(width);
        canvas.setHeight(height);
        
        graphicContext.setFill(Color.WHITE);
        graphicContext.fillRect(0, 0, width, height);
        
        scrollAnchor.setMinSize(width, height);
        scrollAnchor.setPrefSize(width, height);
        scrollAnchor.prefHeight(height);
        scrollAnchor.prefWidth(width);
        
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
