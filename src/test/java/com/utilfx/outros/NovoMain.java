/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utilfx.outros;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Heverton Cruz
 */
public class NovoMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //System.err.println(loadFile("Index.java"));
        //File[] fs = f.listFiles();
        //File[] filetudo = new File[500];
        File currentDir = new File("src");
        String name = "FXML1.fxml";
        displayDirectoryContents(currentDir, name);
    }

    public void teste() {
        try {
            FileInputStream arq = new FileInputStream(getClass().getResource("./").getPath());
            System.err.println(arq.toString());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NovoMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void displayDirectoryContents(File dir, String name) {
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    //System.out.println("directory:" + file.getCanonicalPath());
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
}
