/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.utilfx.teste.desktoppane;

import br.com.utilfx.components.desktoppane.JFXPanelFX;
import br.com.utilfx.teste.FXML1Control;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 *
 * @author c1278778
 */
public class NovoFXMain extends Application {

    public Stage desktoppane(Stage primaryStage) {

        //FXML
        FXML1Control fxml = new FXML1Control();
        fxml.init();
        
        final JFXPanelFX fXPanel = new JFXPanelFX(new Scene(fxml.getRoot()), 200, 100);
        fXPanel.setVisible(true);

        final JInternalFrame internalFrame = new JInternalFrame("TESTE1", true, true, true, true);
        internalFrame.setVisible(true);
        internalFrame.setSize(200, 100);
        internalFrame.add(fXPanel);

        final JDesktopPane desktopPane = new JDesktopPane();
        desktopPane.add(internalFrame);
        //desktopPane.add(internalFrame2);
        //desktopPane.add(internalFrame3);

        final SwingNode swingNode = new SwingNode();
        swingNode.setContent(desktopPane);

        final BorderPane borderPane = new BorderPane(swingNode);

        Button button = new Button("Botão no Rodape");
        borderPane.setBottom(button);

        primaryStage.setScene(new Scene(borderPane, 600, 500));        
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage = desktoppane(primaryStage);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
