package com.utilfx.dialog;

import com.utilfx.dialog.controller.ConfirmDialogController;
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
public class ConfirmDialog extends Dialog {

    public static final int OK = 1;
    public static final int CANCEL = 0;

    /**
     * Constantes para o tipo de texto dos botões.
     */
    public enum ConfirmDialogType {

        /**
         * Exibe botões com as descrições "OK" e "Cancelar"
         */
        OK_CANCEL,
        /**
         * Exibe botões com as descrições "Sim" e "Não"
         */
        YES_NO
    };

    /**
     * Armazena a instância do controlador
     */
    private ConfirmDialogController controller;

    public ConfirmDialog(Stage stageMain, String message) {
        this(stageMain, message, null, false);
    }

    public ConfirmDialog(Stage stageMain, String message, Color color) {
        this(stageMain, message, color, false);
    }

    private ConfirmDialog(Stage stageMain, String message, Color color, boolean st) {
        super(stageMain);

        try {

//            Path fxml = Paths.get("src/br/com/utilfx/dialog/view/ConfirmDialog.fxml");
//            //Carrega o arquivo FXML
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(fxml.toUri().toURL());
//            loader.setBuilderFactory(new JavaFXBuilderFactory());
//            Parent root = (Parent) loader.load(fxml.toUri().toURL().openStream());
//
//            Scene scene = new Scene(root);
            ConfirmDialogController con = new ConfirmDialogController();
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
            showAndWait();
            //Pega o resultado antes de fechar
            Object result = controller.getResult();
            //Fecha o Dialog
            close();

            return result;
        }
    }

    @Override
    public void setMessage(String message) {
        controller.setMessage(message);
    }

    /**
     * Resgata a instância do botão esquerdo do Dialog.
     *
     * @return Button
     */
    public Button getButtonLeft() {
        return controller.getButtonLeft();
    }

    /**
     * Resgata a instância do botão direito do Dialog.
     *
     * @return Button
     */
    public Button getButtonRight() {
        return controller.getButtonRight();
    }

    /**
     * Abre uma caixa de Diálogo com a mensagem enviada.
     *
     * @param stageMain
     * @param message
     * @return
     */
    public static int open(Stage stageMain, String message) {
        return Integer.parseInt(new ConfirmDialog(stageMain, message).executeShow().toString());
    }

    /**
     * Abre uma caixa de Diálogo com a mensagem enviada.
     *
     * @param stageMain
     * @param message
     * @param textButtons
     * @return
     */
    public static int open(Stage stageMain, String message, String[] textButtons) {
        ConfirmDialog confirm = new ConfirmDialog(stageMain, message);
        if (textButtons.length >= 1) {
            confirm.getButtonLeft().setText(textButtons[0]);
        }
        if (textButtons.length >= 2) {
            confirm.getButtonRight().setText(textButtons[1]);
        }
        return Integer.parseInt(confirm.executeShow().toString());
    }

    /**
     * Abre uma caixa de Diálogo com a mensagem enviada.
     *
     * @param stageMain
     * @param message
     * @param type
     * @return
     */
    public static int open(Stage stageMain, String message, ConfirmDialogType type) {
        ConfirmDialog confirm = new ConfirmDialog(stageMain, message);
        //Verifica de tipo
        if (type == ConfirmDialogType.OK_CANCEL) {
            confirm.getButtonLeft().setText("OK");
            confirm.getButtonRight().setText("Cancelar");
        } else if (type == ConfirmDialogType.YES_NO) {
            confirm.getButtonLeft().setText("Sim");
            confirm.getButtonRight().setText("Não");
        }
        return Integer.parseInt(confirm.executeShow().toString());
    }
}
