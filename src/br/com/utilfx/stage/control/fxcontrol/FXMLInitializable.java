/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.utilfx.stage.control.fxcontrol;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author Heverton Cruz
 */
public abstract class FXMLInitializable implements Initializable {

    private File fxml;
    private URI uri;
    private Parent root;

    public void init() {
        try {
            List<FXMLObject> lists = preperClassContents(this.getClass());

            String value = null;
            for (FXMLObject list : lists) {
                value = (list.getKey().equals("name")) ? list.getValue() : "";
            }

            displayDirectoryContentsIDE(new File("src"), value);

            if (fxml != null) {
                root = FXMLLoader.load(fxml.toURL());
            } else if (uri != null) {
                root = FXMLLoader.load(uri.toURL());
            } else {
                System.err.println("ATENÇÃO::: Nome do FXML não encontrado nos diretórios! Verifique o nome do arquivo!");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static List<FXMLObject> preperClassContents(Class classe) {
        List<FXMLObject> lists = null;

        // Ler todos os campos da class
        for (Annotation annotation : classe.getAnnotations()) {
            lists = preperStringContents(annotation);
        }

        return lists;
    }

    private static List<FXMLObject> preperStringContents(Annotation annotation) {

        String name = annotation.toString();

        name = name.replace("@", "");
        name = name.replace(annotation.annotationType().getName(), "");
        name = name.replace("(", "");
        name = name.replace(")", "");

        String[] sts = name.split("=");

        String sta1 = "";
        for (String st : sts) {
            sta1 += " " + st;
        }

        List<FXMLObject> lists = new ArrayList<>();

        String[] sta2 = sta1.split(",");
        for (String st : sta2) {
            String[] a = st.trim().split(" ");
            lists.add(new FXMLObject(a[0], a[1]));
        }

        return lists;
    }

    private void displayDirectoryContentsIDE(File dir, String name) {
        try {
            if (dir.listFiles() != null) {
                File[] files = dir.listFiles();

                for (File file : files) {
                    if (file.isDirectory()) {
                        displayDirectoryContentsIDE(file, name);
                    } else if (file.getName().equals(name)) {
                        fxml = file;
                    }
                }
            } else {
                displayDirectoryContentsJar(name);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void displayDirectoryContentsJar(String name) {
        try {
            JarFile jarFile = new JarFile(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());

            for (Enumeration<JarEntry> entries = jarFile.entries(); entries.hasMoreElements();) {
                JarEntry entry = entries.nextElement();
                String nameclass = entry.getName();

                if (nameclass.contains(name)) {
                    uri = new URI("jar:" + getClass().getProtectionDomain().getCodeSource().getLocation().toURI().toString() + "!/" + nameclass);
                }
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    public File getFxml() {
        return fxml;
    }

    public Scene getScene() {
        return new Scene(root);
    }

    public Parent getRoot() {
        return root;
    }
}
