package br.com.utilfx.teste;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import br.com.utilfx.stage.control.StageMDIControl;

/**
 *
 * @author Heverton Cruz
 */
public class NewFXMain extends Application {
    
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(NewFXMain.class.getResource("FXML1.fxml"));
            Scene scene = new Scene(root);
            //Adiciona o Stage principal buffer
            StageMDIControl.getInstance().setStageMain(stage);

            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Ero");
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
