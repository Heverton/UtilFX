package br.com.utilfx.teste;

import javafx.application.Application;
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

        FXML1Control fxml = new FXML1Control();
        fxml.init();                
        Scene scene = new Scene(fxml.getRoot());

        //Adiciona o Stage principal buffer
        StageMDIControl.getInstance().setStageMain(stage);

        stage.setScene(scene);
        stage.show();
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
