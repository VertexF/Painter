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
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
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
    private Slider sizeSlider;
    @FXML
    private Label labelSize;
    @FXML
    private AnchorPane scrollAnchor;
    @FXML
    private MenuItem newMenuItem;
    @FXML
    private MenuItem openMenuItem;
    @FXML
    private MenuItem saveAsMenuItem;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private MenuItem undoMenuItem;
    @FXML
    private MenuItem redoMenuItem;
    @FXML
    private MenuItem brushMenuItem;
    
    private static Stage mainStage;
    private double size;
    private FileHandling fileHandling;
    private NewCanvas setNewCanvas;
    private GraphicsContext graphicContext;
    private DrawBoard drawBoard;
    private boolean isCanvasCreated;
    private BrushOperation brushOp;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        graphicContext = canvas.getGraphicsContext2D();
        fileHandling = new FileHandling();
        setNewCanvas = new NewCanvas();
        isCanvasCreated = false;
        
        toolID.setText("Brush");
        
        canvas.setOnMousePressed(e -> {
            drawBoard.addDrawOperation(brushOp, graphicContext);
        });
        
        newMenuItem.setOnAction( e -> {
            if(setNewCanvas.display())
            {
                resetCanvas(setNewCanvas.getWight(), setNewCanvas.getHeight());
                brushOp = new BrushOperation(colourPicker);
                drawBoard = new DrawBoard();
                drawBoard.addDrawOperation(brushOp, graphicContext);
                isCanvasCreated = true;
            }
        });
        
        openMenuItem.setOnAction( e -> {
            fileHandling.loadFile(canvas, graphicContext);
        });

        saveAsMenuItem.setOnAction( e -> {
            fileHandling.saveFile(canvas);
        });
        MenuItem exit = new MenuItem("Exit");
        exitMenuItem.setOnAction( e -> {
            System.exit(0);
        });
        
        undoMenuItem.setOnAction( e -> {
            if(isCanvasCreated)
                drawBoard.undo(graphicContext);
        });

        redoMenuItem.setOnAction( e -> {
            if(isCanvasCreated)
                drawBoard.redo(graphicContext);
        });

        brushMenuItem.setOnAction( e -> {
            toolID.setText("Brush");
        });
        
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
