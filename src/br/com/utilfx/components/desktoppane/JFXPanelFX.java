/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.utilfx.components.desktoppane;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;

/**
 *
 * @author Heverton Cruz
 */
public class JFXPanelFX extends JFXPanel{

    public JFXPanelFX(Scene scene, int width, int height) {
        this.setScene(scene);
        this.setSize(width, height);
    }
    
}
