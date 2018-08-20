package paintclone;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Dan
 */
public class NewCanvas {
    
    private int width;
    private int height;
    private boolean answer;
    
    private GridPane grid;
    private Label labelTitle;
    private Label labelWidth;
    private TextField widthTextField;
    private Label labelHeight;
    private TextField heightTextField;
    private Button yesButton;
    private Button noButton;
    
    private static Stage window = new Stage();
    private static Stage alert = new Stage();
    
    private Label userLabel;
    private Button closeButton;
    private VBox alertLayout;
    
    public NewCanvas()
    {
        setMainScreen();
        setAlertScreen();
    }
    
    private void setMainScreen()
    {
        grid = new GridPane();
        labelTitle = new Label("Please create a new page");
        labelWidth = new Label("Width: ");
        widthTextField = new TextField();
        labelHeight = new Label("Height: ");
        heightTextField = new TextField();
        yesButton = new Button("Confirm");
        noButton = new Button("Cancel");
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("New Canvas");
        window.setMinWidth(250);
        
        /**This works by padding by 10 otherwise the grid is stuck to the window.*/
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        
        grid.setConstraints(labelTitle, 0, 0);
        grid.setConstraints(labelWidth, 1, 0);
        widthTextField.setPromptText("0");
        grid.setConstraints(widthTextField, 1, 1);
        
        grid.setConstraints(labelHeight, 2, 0);
        heightTextField.setPromptText("0");
        grid.setConstraints(heightTextField, 2, 1);
        
        grid.setConstraints(yesButton, 1, 2);
        grid.setConstraints(noButton, 2, 2);
        
        grid.getChildren().addAll(labelTitle, labelWidth, widthTextField, labelHeight, heightTextField, yesButton, noButton);
        
        Scene scene = new Scene(grid);
        window.setScene(scene);
    }
    
    private void setAlertScreen()
    {
        /**This stops the user from being able to use a different window while this is open.*/
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Error");
        alert.setMinWidth(250);
        alert.setMinHeight(250);
        
        userLabel = new Label("Please make sure you input numbers only, thank you");
        closeButton = new Button("Close");
        
        alertLayout = new VBox(20);
        alertLayout.getChildren().addAll(userLabel, closeButton);
        alertLayout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(alertLayout);
        alert.setScene(scene);
    }
    
    public boolean display()
    {
        
        /**Note: You can't Lambda with elements instances as members of a class*/
        yesButton.setOnAction(e -> 
        {
            if(isInt(widthTextField) && isInt(heightTextField))
            {
                width = Integer.parseInt(widthTextField.getText());
                height = Integer.parseInt(heightTextField.getText());
                window.hide();
                answer = true;
            }
            else
            {
                alertUserError();
            }
        });

        noButton.setOnAction(e ->         
        {
            window.hide();
            answer = false;
        });

        /**This displays the window before you return is needs to be closed**/
        window.showAndWait();
        
        return answer;
    }
    
    /**Makes sure the user input is an int*/
    private boolean isInt(TextField field)
    {
        try
        {
            int num = Integer.parseInt(field.getText());
            return true;
        }
        catch(NumberFormatException e)
        {
            
            System.out.println("Error: Not a number was not typed in!");
            return false;
        }
    }
    
    private void alertUserError()
    {
        closeButton.setOnAction(e -> {alert.close();});
        
        /**This displays the window before you return is needs to be closed**/
        alert.showAndWait();
    }
    
    public int getWight()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }

}
