package br.com.utilfx.dialog.controller;

import br.com.utilfx.stage.control.fxcontrol.FXMLControl;
import br.com.utilfx.stage.control.fxcontrol.FXMLInitializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controlador do FXML da caixa de Diálogo para entregar avisos ao usuário.
 *
 * @author Heverton Cruz
 * @version 1.1
 */
@FXMLControl(name = "ConfirmDialog.fxml")
public class ConfirmDialogController extends FXMLInitializable {

    private double x = 0;
    private double y = 0;
    //Resultado quando fechado
    private int result = -1;
    @FXML
    private Label lbText;
    @FXML
    private Button btButton1;
    @FXML
    private Button btButton2;

    @FXML
    private void pressMouse(MouseEvent event) {
        //Pega a instância do Stage
        Stage stageDialog = (Stage) lbText.getScene().getWindow();
        //Guarda as suas posições no monitor
        x = stageDialog.getX() - event.getScreenX();
        y = stageDialog.getY() - event.getScreenY();
    }

    @FXML
    private void dragMouse(MouseEvent event) {
        //Pega a instância do Stage
        Stage stageDialog = (Stage) lbText.getScene().getWindow();
        //Muda a posição do stage no monitor
        stageDialog.setX(event.getScreenX() + x);
        stageDialog.setY(event.getScreenY() + y);
    }

    @FXML
    private void btButton1(ActionEvent event) {
        //Resultado quando confirmado
        result = 1;
        //Pega a instância do Stage
        Stage stageDialog = (Stage) lbText.getScene().getWindow();
        //Fecha o Stage
        stageDialog.hide();
    }

    @FXML
    private void btButton2(ActionEvent event) {
        //Resultado quando cancelado
        result = 0;
        //Pega a instância do Stage
        Stage stageDialog = (Stage) lbText.getScene().getWindow();
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
        lbText.setText(message);
    }

    /**
     * Pega a mensgem que está no TextField.
     *
     * @return
     */
    public String getMessage() {
        return lbText.getText();
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
    public int getResult() {
        return result;
    }
}
