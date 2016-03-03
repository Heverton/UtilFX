package br.com.utilfx.teste;

import br.com.utilfx.stage.control.StageMDIControl;
import java.io.BufferedReader;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author Heverton Cruz
 */
public class NewFXMain extends Application {

    @Override
    public void start(Stage stage) {

        FXML1Control fxml = new FXML1Control();
        fxml.init();
        
        Scene scene = new Scene(fxml.getRoot());
        //Adiciona o Stage principal buffer
        StageMDIControl.getInstance().setStageMain(stage);
        stage.setScene(scene);
        stage.show();
    }

    public static void displayDirectoryContents(File dir, String name) {
        try {
            File[] files = dir.listFiles();
            System.out.println("DIRETÓRIO:" + files);

            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println("directory:" + file.getCanonicalPath());
                    displayDirectoryContents(file, name);
                } else if (file.getName().equals(name)) {
                    System.out.println("file:" + file.getCanonicalPath());
                    System.out.println("file:> " + file.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    public static void main(String[] args) {
        launch(args);
    }

}
