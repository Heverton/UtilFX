package br.com.utilfx.dialog;

import br.com.utilfx.dialog.controller.PromptDialogController;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Caixa de Diálogo para entregar avisos ao usuário.
 *
 * @author Heverton Cruz
 * @version 1.1
 */
public class PromptDialog extends Dialog {

    /**
     * Armazena a instância do controlador
     */
    private PromptDialogController controller;

    public PromptDialog(Stage stageMain, String message) {
        this(stageMain, message, null, false);
    }

    public PromptDialog(Stage stageMain, String message, Color color) {
        this(stageMain, message, color, false);
    }

    private PromptDialog(Stage stageMain, String message, Color color, boolean st) {
        super(stageMain);

        try {
            Path fxml = Paths.get("src/main/java/com/dialogfx/view/PromptDialog.fxml");

            //Carrega o arquivo FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(fxml.toUri().toURL());
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = (Parent) loader.load(fxml.toUri().toURL().openStream());

            // Mudar a cor de fundo
            if (color != null) {
                root.setStyle("-fx-background-color: '"+color.desaturate()+"'; ");
            }

            Scene scene = new Scene(root);
            //Deixa o cenário transparente
            scene.setFill(null);
            //Adiciona o cenário ao Stage
            stage.setScene(scene);
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
     * Resgata a instância do textfield do Dialog.
     *
     * @return
     */
    public TextField getTextField() {
        return controller.getTextField();
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
    public static String open(Stage stageMain, String message) {
        return new PromptDialog(stageMain, message, null).executeShow().toString();
    }

    public static String open(Stage stageMain, String message, Color color) {
        return new PromptDialog(stageMain, message, color).executeShow().toString();
    }
}
