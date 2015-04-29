/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.utilfx.teste;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import br.com.utilfx.stage.control.StageMDIControl;

/**
 *
 * @author Heverton Cruz
 */
public class Index extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        //Adiciona o Stage principal buffer
        StageMDIControl.getInstance().setStageMain(primaryStage);
        
        try {
            //Pega a instância do controlador
            StageMDIControl control = StageMDIControl.getInstance();
            //Pega a instância do stage registrada anteriomente
            Stage stage = control.getStageMain();
            //Molde do layout da Aplicação
            BorderPane border = new BorderPane();
            //border.setId("border-index");
            //Topo
            border.setCenter((Node) FXMLLoader.load(Index.class.getResource("FXML1.fxml")));
            //Cria o StackPane base
            StackPane root = new StackPane();
            root.setId("stack-root");
            //Cenário
            Scene scene = new Scene(root);
            //Pega o tamanho da tela
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            //Formata as dimensões do Stage
            stage.setX(0);
            stage.setY(0);
            stage.setMinWidth(500);
            stage.setMinHeight(400);
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
            //Renderiza a tela padrão
            stage.setTitle("TESTE");
            stage.setScene(scene);
            //Adiciona o BorderPane ao StackPane
            root.getChildren().add(border);
            //Fecha todos os Stages abertos
            control.closeAll();
            //Exibe o Frame Principal
            stage.show();
        } catch (IOException ex) {
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
//    public static void main(String[] args) {
//        launch(args);
//    }
    
}
