package com.utilfx.dialog;

import com.utilfx.dialog.controller.AlertDialogController;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Caixa de Diálogo para entregar avisos ao usuário.
 *
 * @author Heverton Cruz
 * @version 1.1
 */
public class AlertDialog extends Dialog {

    /**
     * Armazena a instância do controlador
     */
    private AlertDialogController controller;

    public AlertDialog(Stage stageMain, String message) {
        this(stageMain, message, null, false);
    }

    public AlertDialog(Stage stageMain, String message, Color color) {
        this(stageMain, message, color, false);
    }

    private AlertDialog(Stage stageMain, String message, Color color, boolean st) {
        super(stageMain);

        try {
            //Path fxml = Paths.get("src/br/com/utilfx/dialog/view/AlertDialog.fxml");
            AlertDialogController con = new AlertDialogController();
            con.init();
            
            //Carrega o arquivo FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(con.getUrl());
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = (Parent) loader.load(con.getUrl().openStream());
            
            Scene scene = new Scene(root);
            //Deixa o cenário transparente
            scene.setFill(null);

            //Adiciona o cenário ao Stage
            stage.setScene(scene);

            // Mudar a cor de fundo
            if (color != null) {
                root.setStyle("-fx-background-color: '" + color.desaturate() + "'; ");
            }

            //Guarda a referência do controlador
            controller = loader.getController();

            //Seta a mensagem padrão
            controller.setMessage(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected Object executeShow() {
        if (controller.getMessage() == null) {
            throw new NullPointerException("Deve ser informada uma mensagem para ser exibida.");
        } else if (controller.getMessage().isEmpty()) {
            throw new NullPointerException("Deve ser informada uma mensagem para ser exibida.");
        } else {
            super.showAndWait();
            //Fecha o Dialog
            close();

            return null;
        }
    }

    @Override
    public void setMessage(String message) {
        controller.setMessage(message);
    }

    /**
     * Exibe o Dialog.
     */
    @Override
    public void show() {
        executeShow();
    }

    /**
     * Resgata a instância do botão do Dialog.
     *
     * @return Button
     */
    public Button getButton() {
        return controller.getButton();
    }

    /**
     * Abre uma caixa de Diálogo com a mensagem enviada.
     *
     * @param stageMain
     * @param message
     */
    public static void open(Stage stageMain, String message) {
        new AlertDialog(stageMain, message, null).show();
    }

    public static void open(Stage stageMain, String message, Color color) {
        new AlertDialog(stageMain, message, color).show();
    }

}
