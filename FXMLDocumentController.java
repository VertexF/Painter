/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package painter;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Dan
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private ColorPicker colourPicker;
    @FXML
    private TextField bsize;
    @FXML
    private Canvas canvas;
    @FXML
    private Pane mainPane;
    
    boolean toolSelected = false;
    GraphicsContext brushTool;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        brushTool = canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged( e -> {
        if(!bsize.getText().isEmpty())
        {
            double size = Double.parseDouble(bsize.getText());
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;
            
            if(toolSelected)
            {
                brushTool.setFill(colourPicker.getValue());
                brushTool.fillRoundRect(x, y, size, size, size, size);
            }
                
        }
        });
    }
    
    @FXML
    public void newCanvas(ActionEvent evt)
    {
        TextField getCanvasWidth = new TextField();
        getCanvasWidth.setPromptText("Width");
        getCanvasWidth.setPrefWidth(150);
        getCanvasWidth.setAlignment(Pos.CENTER);
        
        TextField getCanvasHeight = new TextField();
        getCanvasHeight.setPromptText("Height");
        getCanvasHeight.setPrefWidth(150);
        getCanvasHeight.setAlignment(Pos.CENTER);
        
        Button createButton = new Button();
        createButton.setText("Create Canvas");
        
        VBox vb = new VBox();
        vb.setSpacing(5);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(getCanvasWidth, getCanvasHeight, createButton);
        
        Stage createStage = new Stage();
        AnchorPane root = new AnchorPane();
        root.setPrefWidth(200);
        root.setPrefHeight(200);
        root.getChildren().add(vb);
        
        Scene canvasScene = new Scene(root);
        createStage.setTitle("Create Canvas");
        createStage.setScene(canvasScene);
        createStage.show();
        
        createButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent evt)
            {
                double newCanvasWidth = Double.parseDouble(getCanvasWidth.getText());
                double newCanvasHeight = Double.parseDouble(getCanvasHeight.getText());
                
                canvas = new Canvas();
                canvas.setWidth(newCanvasWidth);
                canvas.setHeight(newCanvasHeight);
                //mainPane.getChildren().add(canvas);
                mainPane.getChildren().remove(canvas);
                
                createStage.close();
            }
        });
        
    }
    
    @FXML
    private void toolSelected(ActionEvent evt)
    {
        toolSelected = true;
    }
    
}
