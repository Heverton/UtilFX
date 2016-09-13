package com.utilfx.outros;

import com.utilfx.control.StageMDIControl;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 *
 * @author Heverton Cruz
 */
public class NewFXMain extends Application {

    @Override
    public void start(Stage stage) {

        //FXML
        FXML1Control fxml = new FXML1Control();
        fxml.init();
        
        Scene scene = new Scene(fxml.getRoot());
        //Adiciona o Stage principal buffer
        StageMDIControl.getInstance().setStageMain(stage);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
