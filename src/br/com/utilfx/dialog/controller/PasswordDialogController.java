package br.com.utilfx.dialog.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controlador do FXML da caixa de Diálogo para entregar avisos ao usuário.
 *
 * @author Heverton Cruz
 * @version 1.1
 */
public class PasswordDialogController implements Initializable {

    private double x = 0;
    private double y = 0;
    //Resultado da caixa de texto
    private String result = "";
    @FXML
    private Label lbPassword;
    @FXML
    private PasswordField txPassword;
    @FXML
    private Button btButton1;
    @FXML
    private Button btButton2;

    @FXML
    private void pressMouse(MouseEvent event) {
        //Pega a instância do Stage
        Stage stageDialog = (Stage) lbPassword.getScene().getWindow();
        //Guarda as suas posições no monitor
        x = stageDialog.getX() - event.getScreenX();
        y = stageDialog.getY() - event.getScreenY();
    }

    @FXML
    private void dragMouse(MouseEvent event) {
        //Pega a instância do Stage
        Stage stageDialog = (Stage) lbPassword.getScene().getWindow();
        //Muda a posição do stage no monitor
        stageDialog.setX(event.getScreenX() + x);
        stageDialog.setY(event.getScreenY() + y);
    }

    @FXML
    private void submit(ActionEvent event) {
        //Resultado quando confirmado
        result = txPassword.getText();
        //Pega a instância do Stage
        Stage stageDialog = (Stage) lbPassword.getScene().getWindow();
        //Fecha o Stage
        stageDialog.hide();
    }

    @FXML
    private void btButton2(ActionEvent event) {
        //Pega a instância do Stage
        Stage stageDialog = (Stage) lbPassword.getScene().getWindow();
        //Fecha o Stage
        stageDialog.hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Seta uma mensagem no TextField.
     *
     * @param message
     */
    public void setMessage(String message) {
        lbPassword.setText(message);
    }

    /**
     * Pega a mensgem que está no TextField.
     *
     * @return
     */
    public String getMessage() {
        return lbPassword.getText();
    }
    
    /**
     * Resgata a instância do textfield do Dialog.
     */
    public PasswordField getPasswordField() {
        return txPassword;
    }
    
    /**
     * Resgata a instância do botão esquerdo do Dialog.
     * 
     * @return Button
     */
    public Button getButtonLeft() {
        return btButton1;
    }
    
    /**
     * Resgata a instância do botão direito do Dialog.
     * 
     * @return Button
     */
    public Button getButtonRight() {
        return btButton2;
    }

    /**
     * Pega o valor do resultado
     */
    public String getResult() {
        return result;
    }
}
