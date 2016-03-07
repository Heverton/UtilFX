package br.com.utilfx.dialog.controller;

import br.com.utilfx.stage.control.fxcontrol.FXMLControl;
import br.com.utilfx.stage.control.fxcontrol.FXMLInitializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
@FXMLControl(name = "AlertDialog.fxml")
public class AlertDialogController extends FXMLInitializable {

    private double x = 0;
    private double y = 0;
    @FXML
    private Label lbText;
    @FXML
    private Button btButton;

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
    private void btButton(ActionEvent event) {
        //Pega a instância do Stage
        Stage stageDialog = (Stage) lbText.getScene().getWindow();
        
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
     * Resgata a instância do botão do Dialog.
     * 
     * @return Button
     */
    public Button getButton() {
        return btButton;
    }
}
