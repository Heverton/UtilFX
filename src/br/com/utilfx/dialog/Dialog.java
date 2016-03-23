package br.com.utilfx.dialog;

import br.com.utilfx.stage.control.fxcontrol.FXMLInitializable;
import com.sun.javafx.css.StyleManager;
import com.sun.javafx.css.Stylesheet;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Interface de toda caixa de Diálogo.
 *
 * @author Heverton Cruz
 * @version 1.1
 */
public abstract class Dialog {

    protected Stage stage;
    private Stage background;

    public Dialog(Stage stageMain) {
        this(stageMain, null, false);
    }

    public Dialog(Stage stageMain, Color color) {
        this(stageMain, color, false);
    }

    private Dialog(Stage stageMain, Color color, boolean st) {
        //Stages
        stage = new Stage(StageStyle.TRANSPARENT);

        background = new Stage(StageStyle.TRANSPARENT);
        //Faz com que o Stage bloquei os demais
        background.initModality(Modality.APPLICATION_MODAL);
        //Especifica que o Stage é filho do Stage prinicipal
        background.initOwner(stageMain);

        //Pega o tamanho da tela
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        //Formata as dimensões do Stage
        background.setX(0);
        background.setY(0);
        background.setWidth(bounds.getWidth());
        background.setHeight(bounds.getHeight());

        //Definando componente da Raiz
        AnchorPane root = new AnchorPane();
        root.getStyleClass().add("background-black");

        // Mudar a cor de fundo
        if (color != null) {
            root.setStyle("-fx-background-color: '" + color.desaturate() + "'; ");
        }

        //Pega o caminho absoluto do CSS do Dialog
        String path = getClass().getResource("view/css/dialog.css").getPath();
        path = path.substring(path.indexOf("!/") + 2);
        
        //Seta o style padrão
        root.getStylesheets().add(path);
        
        //Caso for linux a opacidade será zero
        //Fundo trasparente
        if (System.getProperty("os.name").equals("Linux")) {
            background.setOpacity(0.7);
        }

        //Definindo o cenário
        Scene scene = new Scene(root, 500, 400);
        //Deixa o cenário transparente
        scene.setFill(null);

        //scene.getStylesheets().add("br/com/utilfx/dialog/view/css/dialog.css");
        
        //Adicionando o cenário
        background.setScene(scene);

        //Faz com que o Stage bloquei os demais
        stage.initModality(Modality.APPLICATION_MODAL);
        //Especifica que o Stage é filho do Stage prinicipal
        stage.initOwner(background);
    }

    /**
     * Exibe a caixa de Diálogo.
     */
    protected void showAndWait() {
        background.show();
        //Mostra o Dialog e para a execução do programa até que se feche o Stage
        stage.showAndWait();
    }

    /**
     * Exibe o Dialog.
     */
    public void show() {
        executeShow();
    }

    /**
     * Fecha a caixa de Diálogo.
     */
    public final void close() {
        stage.close();
        background.close();
    }

    /**
     * Exibe a caixa de Diálogo.
     *
     * @return Object
     */
    abstract protected Object executeShow();

    /**
     * Exibe a caixa de Diálogo.
     *
     * @param message
     */
    abstract public void setMessage(String message);

}
