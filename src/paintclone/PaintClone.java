package paintclone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Dan
 */
public class PaintClone extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        PaintCloneUIController.getStage(stage);
        Parent root = FXMLLoader.load(getClass().getResource("PaintCloneUI.fxml"));
        
        try
        {   
            Image icon = new Image("file:./res/brushIcon.png");
            stage.getIcons().add(icon);
        }
        catch(Exception e)
        {
            System.out.println("Image failed to load!");
        }
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Paint");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
